package it.univr.injectiontool;

import it.univr.injectiontool.java.*;
import it.univr.injectiontool.javascript.JavaScriptLexer;
import it.univr.injectiontool.javascript.JavaScriptParser;
import it.univr.injectiontool.javascript.PickPatternVisitor;
import it.univr.injectiontool.typescript.DtoPatternVisitor;
import it.univr.injectiontool.typescript.TypeScriptLexer;
import it.univr.injectiontool.typescript.TypeScriptParser;
import it.univr.injectiontool.utils.InjectionPattern;
import it.univr.injectiontool.utils.ProgrammingLanguage;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;
import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class InjectionTool {

    static boolean show_trace = false;
    static boolean show_diagnostic = false;
    static boolean quiet = false;
    static boolean file = false;

    private static final InjectionPattern injectionPattern = InjectionPattern.DTO;
    private static final ProgrammingLanguage programmingLanguage = ProgrammingLanguage.TYPESCRIPT;

    public static void main(String[] args) throws IOException {
        ClassLoader classLoader = InjectionTool.class.getClassLoader();
        URL inputUrl = classLoader.getResource("input/");

        if (inputUrl != null) {
            String inputPath = inputUrl.getPath();
            File inputFolder = new File(inputPath);

            if (inputFolder.isDirectory()) {
                File[] files = inputFolder.listFiles();
                if (files != null) {
                    CharStream str;
                    for (File file : files) {
                        str = CharStreams.fromFileName(file.getPath());
                        DoParse(str, file.getName());
                    }
                } else {
                    System.out.println("No files found in the input folder.");
                }
            } else {
                System.out.println("The input path is not a directory.");
            }
        } else {
            System.out.println("Unable to locate the input folder.");
        }
    }

    static void DoParse(CharStream str, String input_name) {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        URL outputUrl = classLoader.getResource("output/");

        if (outputUrl != null) {
            String outputPath = outputUrl.getPath();

            Lexer lexer = null;
            CommonTokenStream tokens = null;
            Parser parser = null;

            if (programmingLanguage == ProgrammingLanguage.TYPESCRIPT) {
                lexer = new TypeScriptLexer(str);
                tokens = new CommonTokenStream(lexer);
                parser = new TypeScriptParser(tokens);
            } else if (programmingLanguage == ProgrammingLanguage.JAVASCRIPT) {
                lexer = new JavaScriptLexer(str);
                tokens = new CommonTokenStream(lexer);
                parser = new JavaScriptParser(tokens);
            } else if (programmingLanguage == ProgrammingLanguage.JAVA) {
                lexer = new JavaLexer(str);
                tokens = new CommonTokenStream(lexer);
                parser = new JavaParser(tokens);
            }

            PrintStream errorOutput;
            try {
                errorOutput = file ? new PrintStream(outputPath + input_name + ".errors") : System.out;
            } catch (NullPointerException | FileNotFoundException e) {
                errorOutput = System.err;
            }

            ErrorListener listener_lexer = new ErrorListener(quiet, file, errorOutput);
            ErrorListener listener_parser = new ErrorListener(quiet, file, errorOutput);
            if (parser != null) {
                parser.removeErrorListeners();
                parser.addErrorListener(listener_parser);
            }
            if (lexer != null) {
                lexer.removeErrorListeners();
                lexer.addErrorListener(listener_lexer);

            }

            if (show_diagnostic)
            {
                if (parser != null) {
                    parser.addErrorListener(new MyDiagnosticErrorListener());
                }
            }

            if (show_trace)
            {
                if (parser != null) {
                    parser.setTrace(true);
                }
            }

            ParseTree tree = null;

            if (programmingLanguage == ProgrammingLanguage.TYPESCRIPT) {
                tree = ((TypeScriptParser) parser).program();
            } else if (programmingLanguage == ProgrammingLanguage.JAVASCRIPT) {
                tree = ((JavaScriptParser) parser).program();
            } else if (programmingLanguage == ProgrammingLanguage.JAVA) {
                tree = ((JavaParser) parser).compilationUnit();
            }

            //System.out.println(tree.toStringTree(parser));

            String injected_code = "";

            if (injectionPattern == InjectionPattern.DTO) {
                DtoPatternVisitor visitor = new DtoPatternVisitor(tokens);
                visitor.visit(tree);
                injected_code = visitor.rewriter.getText();
            } else if (injectionPattern == InjectionPattern.PICK) {
                PickPatternVisitor visitor = new PickPatternVisitor(tokens);
                visitor.visit(tree);
                injected_code = visitor.rewriter.getText();
            } else if (injectionPattern == InjectionPattern.COPY_PROPERTIES) {
                CopyPropertiesPatternVisitor visitor = new CopyPropertiesPatternVisitor(tokens);
                visitor.visit(tree);
                injected_code = visitor.rewriter.getText();
            }

            //System.out.println(injected_code);

            PrintWriter resultOutput;
            try {
                resultOutput = new PrintWriter(new OutputStreamWriter(new FileOutputStream(outputPath + input_name), StandardCharsets.UTF_8), true);
            } catch (NullPointerException | FileNotFoundException e) {
                resultOutput = new PrintWriter(new OutputStreamWriter(System.out, StandardCharsets.UTF_8), true);
            }

            resultOutput.print(injected_code);
            resultOutput.close();

            if (file) errorOutput.close();
        }
    }
}
