package prj.clark.pl.a3;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.junit.Test;

import static org.junit.Assert.*;

public class PostfixVisitorTest {

    private String toPostfix(String input) {
        CharStream cs = CharStreams.fromString(input + "\n");
        MathLexer lex = new MathLexer(cs);
        CommonTokenStream cts = new CommonTokenStream(lex);
        MathParser parse = new MathParser(cts);

        PostfixVisitor postfix = new PostfixVisitor();

        return postfix.visit(parse.file());
    }

    @Test
    public void singleLinePostfix() {
        assertEquals("a", toPostfix("a").trim());
        assertEquals("a b =", toPostfix("a = b").trim());
        assertEquals("a b + c -", toPostfix("a + b - c").trim());
    }

    @Test
    public void operatorPrecedenceRespected() {
        assertEquals("2 3 * 1 +", toPostfix("2 * 3 + 1").trim());
    }

    @Test
    public void parenthesesRespected() {
        assertEquals("1 2 + 3 *", toPostfix("(1 + 2) * 3").trim());
    }

    @Test
    public void multiLinePostfix() {
        assertEquals("1 2 +\n3 5 / 1 +", toPostfix("1 + 2\n3 / 5 + 1").trim());
    }
}