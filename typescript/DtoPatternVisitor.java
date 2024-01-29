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
    public String visitVariableDeclaration(TypeScriptParser.VariableDeclarationContext ctx) {
        return ctx.getText();
    }
}