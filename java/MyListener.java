package it.univr.injectiontool.java;

import org.antlr.v4.runtime.tree.*;
import org.antlr.v4.runtime.*;

public class MyListener extends JavaParserBaseListener {
    private static MyVisitor visitor = new MyVisitor();

    @Override
    public void enterLiteral(JavaParser.LiteralContext ctx) {
        if (ctx.getText().equals("\"/api\"")) {
            visitor.visitLiteral(ctx);
        }
    }
}