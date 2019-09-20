package prj.clark.pl.a3;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.junit.After;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static org.junit.Assert.*;

public class EvaluationVisitorTest {
    private ByteArrayOutputStream os;

    private static String floatVal(int val) {
        return String.valueOf((double) val);
    }

    @After
    public void tearDown() {
        os = null;
    }

    private void init(String input) throws IOException {
        CharStream cs = CharStreams.fromString(input + "\n");
        MathLexer lex = new MathLexer(cs);
        CommonTokenStream cts = new CommonTokenStream(lex);
        MathParser parse = new MathParser(cts);

        os = new ByteArrayOutputStream();
        EvaluationVisitor eval = new EvaluationVisitor(os);

        eval.visit(parse.file());
    }

    @Test(expected = UnboundVariableException.class)
    public void accessingUnboundVariableFails() throws IOException {
        init("a");
    }

    @Test
    public void boundVariableUsable() throws IOException {
        init("var = 10\nvar");
        assertEquals(floatVal(10), os.toString().trim());
    }

    @Test
    public void literalPrintedCorrectly() throws IOException {
        init("2");
        assertEquals(floatVal(2), os.toString().trim());
    }

    @Test
    public void additionPrintedCorrectly() throws IOException {
        init("3 + 7");
        assertEquals(floatVal(10), os.toString().trim());
    }

    @Test
    public void subtractionPrintedCorrectly() throws IOException {
        init("3 - 7");
        assertEquals(floatVal(-4), os.toString().trim());
    }

    @Test
    public void multiplicationPrintedCorrectly() throws IOException {
        init("4 * 8");
        assertEquals(floatVal(32), os.toString().trim());
    }

    @Test
    public void divisionPrintedCorrectly() throws IOException {
        init("4 / 8");
        assertEquals(String.valueOf(0.5), os.toString().trim());
    }

    @Test
    public void orderOfOperationsRespected() throws IOException {
        init("1 + 3 * 2");
        assertEquals(floatVal(7), os.toString().trim());
    }

    @Test
    public void parenthesesRespected() throws IOException {
        init("(1 + 3) * 2");
        assertEquals(floatVal(8), os.toString().trim());
    }

    @Test
    public void multiLineTest() throws IOException {
        init("a = 1\n" +
                "b = 2\n" +
                "c = a - b\n" +
                "a * 3 + c\n" +
                "b / c");

        assertEquals(floatVal(2) + "\n" + floatVal(-2), os.toString().trim());
    }

    @Test
    public void assignmentsDoNotPrint() throws IOException {
        init("a = 11");
        assertEquals("", os.toString());
    }
}