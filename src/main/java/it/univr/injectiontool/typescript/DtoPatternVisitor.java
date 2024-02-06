package it.univr.injectiontool.typescript;

import org.antlr.v4.runtime.*;

public class DtoPatternVisitor extends TypeScriptParserBaseVisitor<Void> {
    BufferedTokenStream tokens;
    public TokenStreamRewriter rewriter;

    public DtoPatternVisitor(BufferedTokenStream tokens) {
        this.tokens = tokens;
        rewriter = new TokenStreamRewriter(tokens);
    }

    @Override
    public Void visitTypeName(TypeScriptParser.TypeNameContext ctx) {
        if (ctx.getText().toLowerCase().contains("dto")) {
            rewriter.replace(ctx.start.getTokenIndex(), "any");
        }
        return null;
    }

    @Override
    protected String aggregateResult(String aggregate, String nextResult) {
        return nextResult;
    }
}