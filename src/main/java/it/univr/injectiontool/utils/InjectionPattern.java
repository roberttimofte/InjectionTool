package it.univr.injectiontool.utils;
import it.univr.injectiontool.java.CopyPropertiesPatternVisitor;
import it.univr.injectiontool.javascript.PickPatternVisitor;
import it.univr.injectiontool.typescript.DtoPatternVisitor;
import org.antlr.v4.runtime.tree.AbstractParseTreeVisitor;
public enum InjectionPattern {
    DTO(new DtoPatternVisitor()),
    PICK(new PickPatternVisitor()),
    COPY_PROPERTIES(new CopyPropertiesPatternVisitor());

    private final AbstractParseTreeVisitor<String> visitor;

    InjectionPattern(AbstractParseTreeVisitor<String> visitor) {
        this.visitor = visitor;
    }

    public AbstractParseTreeVisitor<String> getValue() {
        return this.visitor;
    }
}
