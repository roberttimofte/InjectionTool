package it.univr.injectiontool.java;

import org.antlr.v4.runtime.tree.*;
import org.antlr.v4.runtime.*;

public class MyVisitor extends JavaParserBaseVisitor<String> {
    @Override
    public String visitCompilationUnit(JavaParser.CompilationUnitContext ctx) {
        for (int i = 0; i < ctx.children.size(); i++) {
            visit(ctx.getChild(i));
        }
        return ctx.getText();
    }

    @Override
    public String visitMethodCall(JavaParser.MethodCallContext ctx) {
        if (ctx.getText().contains("copyProperties")) {
            ParseTree arguments = ctx.getChild(1);
            System.out.println(arguments.getText());
            visit(arguments);
            System.out.println(arguments.getText());
        }

        return ctx.getText();
    }

    @Override
    public String visitArguments(JavaParser.ArgumentsContext ctx) {
        if (ctx.getParent().getText().contains("copyProperties")) {
            visit(ctx.getChild(1));
        }

        return ctx.getText();
    }

    @Override
    public String visitExpressionList(JavaParser.ExpressionListContext ctx) {
        int max = ctx.children.size()-3;
        for (int i = 0; i < max; i++) {
            ctx.removeLastChild();
        }
        return ctx.getText();
    }

    /*@Override
    public String visitLiteral(JavaParser.LiteralContext ctx) {
        if (ctx.getText().equals("\"/api\"")) {
            ctx.removeLastChild();
            ctx.addChild(new TerminalNodeImpl(new CommonToken(Token.DEFAULT_CHANNEL, "\"/apis\"")));
        }
        return ctx.getText();
    }*/
}