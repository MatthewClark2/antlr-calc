package prj.clark.pl.a3;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static prj.clark.pl.a3.A3Utils.executeInfix;
import static prj.clark.pl.a3.A3Utils.parseArgs;

public class Part2 {
    public static void main(String[] args) throws IOException {
        A3Utils.Config conf = parseArgs(args, Collections.singleton("data/prefixed_test.math"));

        if (conf.flags.get("use-stdin")) {
            execute(CharStreams.fromStream(System.in));
        }

        // This is a safe operation as files is empty if use-stdin is unset.
        for (String filename : conf.files) {
            System.out.println("Working File: " + filename);
            execute(CharStreams.fromFileName(filename));
        }
    }

    private static void execute(CharStream cs) {
        PrefixMathLexer lex = new PrefixMathLexer(cs);
        CommonTokenStream cts = new CommonTokenStream(lex);
        PrefixMathParser parse = new PrefixMathParser(cts);

        PrefixToInfixVisitor p2i = new PrefixToInfixVisitor();

        String infix = p2i.visit(parse.file());

        System.out.println("To Infix");
        System.out.println(infix);

        executeInfix(CharStreams.fromString(infix), getVisitors());
    }

    private static Map<String, MathVisitor> getVisitors() {
        Map<String, MathVisitor> visitors = new HashMap<>();
        visitors.put("To Postfix", new PostfixVisitor());
        visitors.put("Execute", new EvaluationVisitor());
        return visitors;
    }
}
