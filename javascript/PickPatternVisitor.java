package it.univr.injectiontool.javascript;

import org.antlr.v4.runtime.tree.*;
import org.antlr.v4.runtime.*;

public class PickPattern extends JavaScriptParserBaseVisitor<String> {
    @Override
    public String visitProgram(JavaScriptParser.ProgramContext ctx) {
        return ctx.getText();
    }
}