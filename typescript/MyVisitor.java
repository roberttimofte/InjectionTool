package it.univr.injectiontool.typescript;

import org.antlr.v4.runtime.tree.*;
import org.antlr.v4.runtime.*;

public class MyVisitor extends TypeScriptParserBaseVisitor<String> {
    @Override
    public String visitProgram(TypeScriptParser.ProgramContext ctx) {
        for (int i = 0; i < ctx.children.size(); i++) {
            visit(ctx.getChild(i));
        }
        return ctx.getText();
    }

    @Override
    public String visitVariableDeclaration(TypeScriptParser.VariableDeclarationContext ctx) {
        System.out.println(ctx.getText());
        return ctx.getText();
    }
}