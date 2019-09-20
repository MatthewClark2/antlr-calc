package prj.clark.pl.a3;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.junit.Test;

import static org.junit.Assert.*;

public class EvaluationVisitorTest {
    private static String floatVal(int val) {
        return String.valueOf((double) val);
    }

    private String eval(String input) {
        CharStream cs = CharStreams.fromString(input + "\n");
        MathLexer lex = new MathLexer(cs);
        CommonTokenStream cts = new CommonTokenStream(lex);
        MathParser parse = new MathParser(cts);
        EvaluationVisitor eval = new EvaluationVisitor();

        eval.visit(parse.file());

        return eval.toString().trim();
    }

    @Test(expected = UnboundVariableException.class)
    public void accessingUnboundVariableFails() {
        eval("a");
    }

    @Test
    public void boundVariableUsable() {
        String input = ("var = 10\nvar");
        assertEquals(floatVal(10), eval(input));
    }

    @Test
    public void literalPrintedCorrectly() {
        String input = ("2");
        assertEquals(floatVal(2), eval(input));
    }

    @Test
    public void additionPrintedCorrectly() {
        String input = ("3 + 7");
        assertEquals(floatVal(10), eval(input));
    }

    @Test
    public void subtractionPrintedCorrectly() {
        String input = ("3 - 7");
        assertEquals(floatVal(-4), eval(input));
    }

    @Test
    public void multiplicationPrintedCorrectly() {
        String input = ("4 * 8");
        assertEquals(floatVal(32), eval(input));
    }

    @Test
    public void divisionPrintedCorrectly() {
        String input = ("4 / 8");
        assertEquals(String.valueOf(0.5), eval(input));
    }

    @Test
    public void orderOfOperationsRespected() {
        String input = ("1 + 3 * 2");
        assertEquals(floatVal(7), eval(input));
    }

    @Test
    public void parenthesesRespected() {
        String input = ("(1 + 3) * 2");
        assertEquals(floatVal(8), eval(input));
    }

    @Test
    public void multiLineTest() {
        String input = ("a = 1\n" +
                "b = 2\n" +
                "c = a - b\n" +
                "a * 3 + c\n" +
                "b / c");

        assertEquals(floatVal(2) + "\n" + floatVal(-2), eval(input));
    }

    @Test
    public void assignmentsDoNotPrint() {
        String input = ("a = 11");
        assertEquals("", eval(input));
    }

    @Test
    public void standardFloatValues() {
        String input = ("a = 1.5\n" +
                "b = .25\n" +
                "a + b\n" +
                "a - b\n");

        assertEquals(1.75 +"\n" + 1.25, eval(input));
    }

    @Test
    public void scientificNotation() {
        String input = ("1e3\n" +
                "2.5e-1\n" +
                "4E+2");

        assertEquals(1e3 + "\n" + 2.5e-1 + "\n" + 4e2, eval(input));
    }
}