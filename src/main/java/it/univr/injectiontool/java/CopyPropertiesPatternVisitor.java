package it.univr.injectiontool.java;

import org.antlr.v4.runtime.BufferedTokenStream;
import org.antlr.v4.runtime.TokenStreamRewriter;
import org.antlr.v4.runtime.misc.Interval;

public class CopyPropertiesPatternVisitor extends JavaParserBaseVisitor<Void> {
    BufferedTokenStream tokens;
    public TokenStreamRewriter rewriter;

    public CopyPropertiesPatternVisitor(BufferedTokenStream tokens) {
        this.tokens = tokens;
        rewriter = new TokenStreamRewriter(tokens);
    }

    @Override
    public Void visitExpressionList(JavaParser.ExpressionListContext ctx) {
        String method_call = ctx.getParent().getParent().getText();
        if (method_call.contains("copyProperties")) {
            Interval source_interval = null;
            for (int i = 3; i < ctx.getChildCount(); i++) {
                source_interval = ctx.getChild(i).getSourceInterval();
                rewriter.delete(source_interval.a, source_interval.b);
            }
        }

        return null;
    }

    @Override
    protected String aggregateResult(String aggregate, String nextResult) {
        return nextResult;
    }
}