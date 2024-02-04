package it.univr.injectiontool.javascript;

import org.antlr.v4.runtime.tree.*;
import org.antlr.v4.runtime.*;

public class PickPatternVisitor extends JavaScriptParserBaseVisitor<String> {
    @Override
    public String visitProgram(JavaScriptParser.ProgramContext ctx) {
        for (int i = 0; i < ctx.children.size(); i++) {
            visit(ctx.getChild(i));
        }
        return ctx.getText();
    }

    @Override
    public String visitVariableDeclaration(JavaScriptParser.VariableDeclarationContext ctx) {
        if (ctx.getText().contains("_.pick")) {
            String arguments = ctx.getText().split("_.pick\\(")[1];
            String first_parameter = arguments.split(",")[0];

            ctx.removeLastChild();
            ctx.addChild(new TerminalNodeImpl(new CommonToken(Token.DEFAULT_CHANNEL, first_parameter)));
        }

        return ctx.getText();
    }
}