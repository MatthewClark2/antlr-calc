package prj.clark.pl.a3;

import org.antlr.v4.runtime.CharStreams;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static prj.clark.pl.a3.A3Utils.FileProducingVisitor;
import static prj.clark.pl.a3.A3Utils.parseArgs;
import static prj.clark.pl.a3.A3Utils.executeInfix;

public class Part1 {
    public static void main(String[] args) throws IOException {
        A3Utils.Config conf = parseArgs(args, Collections.singleton("data/test.math"));

        boolean fileProducing = conf.flags.get("save-output");

        if (conf.flags.get("use-stdin")) {
            Map<String, FileProducingVisitor> visitors;

            if (fileProducing) {
                visitors = getVisitors("");
            } else {
                visitors = getVisitors();
            }

            executeInfix(CharStreams.fromStream(System.in), visitors);
        }

        // This is a safe operation as files is empty if use-stdin is unset.
        for (String filename : conf.files) {
            Map<String, FileProducingVisitor> visitors;

            if (fileProducing) {
                visitors = getVisitors(Paths.get(filename).getFileName().toString());
            } else {
                visitors = getVisitors();
            }

            System.out.println("Working File: " + filename);
            executeInfix(CharStreams.fromFileName(filename), visitors);
        }

    }

    @SuppressWarnings("unchecked")
    private static Map<String, FileProducingVisitor> getVisitors() {
        return getVisitors(null);
    }

    @SuppressWarnings("unchecked")
    private static Map<String, FileProducingVisitor> getVisitors(String filename) {
        Map<String, FileProducingVisitor> visitors = new HashMap<>();
        visitors.put("Execution", new FileProducingVisitor(new EvaluationVisitor()));
        visitors.put("To Prefix", new FileProducingVisitor(new PrefixVisitor(), "prefixed_" + filename));
        visitors.put("To Postfix", new FileProducingVisitor(new PostfixVisitor(), "postfixed_" + filename));
        return visitors;
    }
}
