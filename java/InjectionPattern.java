package it.univr.injectiontool.java;
import org.antlr.v4.runtime.tree.AbstractParseTreeVisitor;
public enum InjectionPattern {
    COPY_PROPERTIES(new CopyPropertiesPatternVisitor());

    private final AbstractParseTreeVisitor<String> visitor;

    InjectionPattern(AbstractParseTreeVisitor<String> visitor) {
        this.visitor = visitor;
    }

    public AbstractParseTreeVisitor<String> getValue() {
        return this.visitor;
    }
}
