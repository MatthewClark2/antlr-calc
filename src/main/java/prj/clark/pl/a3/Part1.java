package prj.clark.pl.a3;

import org.antlr.v4.runtime.CharStreams;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static prj.clark.pl.a3.A3Utils.parseArgs;
import static prj.clark.pl.a3.A3Utils.executeInfix;

public class Part1 {
    public static void main(String[] args) throws IOException {
        A3Utils.Config conf = parseArgs(args, Collections.singleton("data/test.math"));

        if (conf.flags.get("use-stdin")) {
            executeInfix(CharStreams.fromStream(System.in), getVisitors());
        }

        // This is a safe operation as files is empty if use-stdin is unset.
        for (String filename : conf.files) {
            System.out.println("Working File: " + filename);
            executeInfix(CharStreams.fromFileName(filename), getVisitors());
        }

    }

    private static Map<String, MathVisitor> getVisitors() {
        Map<String, MathVisitor> visitors = new HashMap<>();
        visitors.put("Execution", new EvaluationVisitor());
        visitors.put("To Prefix", new PrefixVisitor());
        visitors.put("To Postfix", new PostfixVisitor());
        return visitors;
    }
}
