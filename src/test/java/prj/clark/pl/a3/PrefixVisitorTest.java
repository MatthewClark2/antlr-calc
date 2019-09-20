package prj.clark.pl.a3;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.junit.Test;

import static org.junit.Assert.*;

public class PrefixVisitorTest {
    private String toPrefix(String input) {
        CharStream cs = CharStreams.fromString(input + "\n");
        MathLexer lex = new MathLexer(cs);
        CommonTokenStream cts = new CommonTokenStream(lex);
        MathParser parse = new MathParser(cts);

        PrefixVisitor prefix = new PrefixVisitor();

        return prefix.visit(parse.file());
    }

    @Test
    public void singleLinePrefix() {
        assertEquals("a", toPrefix("a").trim());
        assertEquals("= a b", toPrefix("a = b").trim());
        assertEquals("- + a b c", toPrefix("a + b - c").trim());
    }

    @Test
    public void operatorPrecedenceRespected() {
        assertEquals("+ * 2 3 1", toPrefix("2 * 3 + 1").trim());
    }

    @Test
    public void parenthesesRespected() {
        assertEquals("* + 1 2 3", toPrefix("(1 + 2) * 3").trim());
    }

    @Test
    public void multiLinePrefix() {
        assertEquals("+ 1 2\n+ / 3 5 1", toPrefix("1 + 2\n3 / 5 + 1").trim());
    }
}