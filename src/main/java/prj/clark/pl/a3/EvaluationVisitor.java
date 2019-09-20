package prj.clark.pl.a3;


import java.io.OutputStream;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;

public class EvaluationVisitor extends MathBaseVisitor<Double> {
    private PrintStream out;
    private Map<String, Double> variables;

    public EvaluationVisitor() {
        this(System.out);
    }

    public EvaluationVisitor(OutputStream os) {
        this(new PrintStream(os));
    }

    public EvaluationVisitor(PrintStream out) {
        this.out = out;
        variables = new HashMap<>();
    }

    @Override
    public Double visitAssignment(MathParser.AssignmentContext ctx) {
        String id = ctx.ID().getText();
        double val = visit(ctx.expr());
        variables.put(id, val);
        return val;
    }

    @Override
    public Double visitPrint(MathParser.PrintContext ctx) {
        double val = visit(ctx.expr());

        out.println(val);

        return val;
    }

    @Override
    public Double visitParen(MathParser.ParenContext ctx) {
        return visit(ctx.expr());
    }

    @Override
    public Double visitAddsub(MathParser.AddsubContext ctx) {
        double left = visit(ctx.expr(0));
        double right = visit(ctx.expr(1));

        if (ctx.ADD() != null) {
            return left + right;
        }

        return left - right;
    }

    @Override
    public Double visitId(MathParser.IdContext ctx) {
        String id = ctx.getText();

        if (variables.containsKey(id)) {
            return variables.get(id);
        }

        throw new UnboundVariableException(id);
    }

    @Override
    public Double visitNum(MathParser.NumContext ctx) {
        return Double.valueOf(ctx.getText());
    }

    @Override
    public Double visitMuldiv(MathParser.MuldivContext ctx) {
        double left = visit(ctx.expr(0));
        double right = visit(ctx.expr(1));

        if (ctx.MUL() != null) {
            return left * right;
        }

        return left / right;
    }
}
