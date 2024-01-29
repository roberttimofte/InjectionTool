package it.univr.injectiontool.typescript;

import org.antlr.v4.runtime.tree.*;
import org.antlr.v4.runtime.*;

public class DtoPatternVisitor extends TypeScriptParserBaseVisitor<String> {
    @Override
    public String visitProgram(TypeScriptParser.ProgramContext ctx) {
        for (int i = 0; i < ctx.children.size(); i++) {
            visit(ctx.getChild(i));
        }
        return ctx.getText();
    }
    @Override
    public String visitClassDeclaration(TypeScriptParser.ClassDeclarationContext ctx) {
        for (int i = 0; i < ctx.children.size(); i++) {
            visit(ctx.getChild(i));
        }
        return ctx.getText();
    }
    @Override
    public String visitClassElement(TypeScriptParser.ClassElementContext ctx) {
        for (int i = 0; i < ctx.children.size(); i++) {
            visit(ctx.getChild(i));
        }
        return ctx.getText();
    }
    @Override
    public String visitTypeName(TypeScriptParser.TypeNameContext ctx) {
        if (ctx.getText().toLowerCase().contains("dto")) {
            ctx.removeLastChild();
            ctx.addChild(new TerminalNodeImpl(new CommonToken(Token.DEFAULT_CHANNEL, "any")));
        }
        return ctx.getText();
    }
}