package prj.clark.pl.a3;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

import java.io.IOException;

public class PrefixMain {
    public static void main(String[] args) throws IOException {
        CharStream cs = CharStreams.fromFileName("data/test.math");
        MathLexer lex = new MathLexer(cs);
        CommonTokenStream cts = new CommonTokenStream(lex);
        MathParser parse = new MathParser(cts);

        PrefixVisitor prefix = new PrefixVisitor();

        prefix.visit(parse.file());

        System.out.println(prefix);
    }
}
