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
    public Void visitArguments(JavaScriptParser.ArgumentsContext ctx) {
        visitChildren(ctx);

        for (int i = 0; i < ctx.getChildCount(); i++) {
            String expression = ctx.getChild(i).getText();
            if (expression.startsWith("_.pick")) {
                System.out.println("pattern identificato: "+ ctx.getText());

                String[] declaration = ctx.getText().split("_.pick\\(");
                String[] parameters = declaration[1].split(",");
                String first_parameter = parameters[0];

                Interval source_interval = ctx.getSourceInterval();

                System.out.println("vulnerabilità iniettata");
                System.out.println("-------------------------------");
            }
        }

        return null;
    }

    @Override
    protected String aggregateResult(String aggregate, String nextResult) {
        return nextResult;
    }
}