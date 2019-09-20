package prj.clark.pl.a3;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.*;

public class PrefixToInfixVisitorTest {
    private String p2i(String input) {
        CharStream cs = CharStreams.fromString(input + "\n");
        MathLexer lex = new MathLexer(cs);
        CommonTokenStream cts = new CommonTokenStream(lex);
        PrefixMathParser parse = new PrefixMathParser(cts);

        PrefixToInfixVisitor prefixToInfix = new PrefixToInfixVisitor();
        return prefixToInfix.visitFile(parse.file()).trim();
    }

    @Test
    public void singleValue() {
        assertEquals("a", p2i("a"));
    }

    @Test
    public void singleExpression() {
        assertEquals("a = b", p2i("= a b"));
    }

    @Test
    public void orderOfOperationsCarriesThrough() {
        // (1 + 3) * 2 -> * + 1 3 2
        assertEquals("(1 + 3) * 2", p2i("* + 1 3 2"));
    }

    @Ignore
    @Test
    public void stackedExpressions() {
        assertEquals("((1 + 2) - 3) / 4 * 5", p2i("* / - + 1 2 3 4 5"));
    }

    @Ignore
    @Test
    public void multipleLines() {
        assertEquals("a = b\n(b + 10)\n(c / 2 + 3)", p2i("= a b\n+ b 10\n+ / c 2 3"));
    }

    @Test
    public void fixedStackedExpressions() {
        assertEquals("(1 + 2 - 3) / 4 * 5", p2i("* / - + 1 2 3 4 5"));
    }

    @Test
    public void fixedMultipleLines() {
        assertEquals("a = b\nb + 10\nc / 2 + 3", p2i("= a b\n+ b 10\n+ / c 2 3"));
    }
}