package prj.clark.pl.a3;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static prj.clark.pl.a3.A3Utils.FileProducingVisitor;
import static prj.clark.pl.a3.A3Utils.executeInfix;
import static prj.clark.pl.a3.A3Utils.parseArgs;

public class Part2 {
    public static void main(String[] args) throws IOException {
        A3Utils.Config conf = parseArgs(args, Collections.singleton("data/prefixed_test.math"));

        boolean fileProducing = conf.flags.get("save-output");

        if (conf.flags.get("use-stdin")) {
            execute(CharStreams.fromStream(System.in), "", fileProducing);
        }

        // This is a safe operation as files is empty if use-stdin is unset.
        for (String filename : conf.files) {
            System.out.println("Working File: " + filename);
            execute(CharStreams.fromFileName(filename), filename, fileProducing);
        }
    }

    private static void execute(CharStream cs, String filename, boolean fileProducing) throws IOException {
        PrefixMathLexer lex = new PrefixMathLexer(cs);
        CommonTokenStream cts = new CommonTokenStream(lex);
        PrefixMathParser parse = new PrefixMathParser(cts);

        PrefixToInfixVisitor p2i = new PrefixToInfixVisitor();

        String infix = p2i.visit(parse.file());

        System.out.println("To Infix");
        System.out.println(infix);

        Map<String, FileProducingVisitor> visitors;

        if (fileProducing) {
            visitors = getVisitors(filename);
        } else {
            visitors = getVisitors();
        }

        executeInfix(CharStreams.fromString(infix), visitors);
    }

    private static Map<String, FileProducingVisitor> getVisitors() {
        return getVisitors(null);
    }

    @SuppressWarnings("unchecked")
    private static Map<String, FileProducingVisitor> getVisitors(String filename) {
        Map<String, FileProducingVisitor> visitors = new HashMap<>();
        visitors.put("Execution", new FileProducingVisitor(new EvaluationVisitor()));
        visitors.put("To Postfix", new FileProducingVisitor(new PostfixVisitor(), filename != null ? "postfixed_" + filename : null));
        return visitors;
    }
}
