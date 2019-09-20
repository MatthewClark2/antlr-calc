package prj.clark.pl.a3;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

import java.io.IOException;
import java.util.Collections;

import static prj.clark.pl.a3.A3Utils.parseArgs;

public class Part1 {
    public static void main(String[] args) throws IOException {
        A3Utils.Config conf = parseArgs(args, Collections.singleton("data/test.math"));

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
        MathLexer lex = new MathLexer(cs);
        CommonTokenStream cts = new CommonTokenStream(lex);
        MathParser parse = new MathParser(cts);

        EvaluationVisitor eval = new EvaluationVisitor();
        PrefixVisitor prefix = new PrefixVisitor();
        PostfixVisitor postfix = new PostfixVisitor();

        execute(parse, eval, "Outright Execution");
        execute(parse, prefix, "To Prefix");
        execute(parse, postfix, "To Postfix");
    }

    private static <T> void execute(MathParser parse, MathVisitor<T> visitor, String msg) {
        System.out.println(msg);
        visitor.visit(parse.file());
        System.out.println(visitor);
        parse.reset();
    }
}
