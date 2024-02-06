package it.univr.injectiontool.javascript;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.Interval;

public class PickPatternVisitor extends JavaScriptParserBaseVisitor<Void> {
    BufferedTokenStream tokens;
    public TokenStreamRewriter rewriter;

    public PickPatternVisitor(BufferedTokenStream tokens) {
        this.tokens = tokens;
        rewriter = new TokenStreamRewriter(tokens);
    }

    @Override
    public Void visitVariableDeclaration(JavaScriptParser.VariableDeclarationContext ctx) {
        if (ctx.getText().contains("_.pick")) {
            String[] declaration = ctx.getText().split("_.pick\\(");
            String[] parameters = declaration[1].split(",");
            String first_parameter = parameters[0];

            Interval source_interval = ctx.getSourceInterval();

            int a = 0, b = 0;
            for (int i = source_interval.a; i <= source_interval.b; i++) {
                if (tokens.get(i).getText().equals("_")) {
                    a = tokens.get(i).getTokenIndex();
                }

                if (tokens.get(i).getText().equals(")")) {
                    b = tokens.get(i).getTokenIndex();
                }

                if (a != 0 && b != 0) break;
            }

            rewriter.replace(a, b, first_parameter);
        }

        return null;
    }

    @Override
    protected String aggregateResult(String aggregate, String nextResult) {
        return nextResult;
    }
}