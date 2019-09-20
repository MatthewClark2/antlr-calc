package prj.clark.pl.a3;


import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.Map;

public class EvaluationVisitor extends MathBaseVisitor<Integer> {
    private OutputStreamWriter osw;
    private Map<String, Integer> variables;

    public EvaluationVisitor() {
        this(System.out);
    }

    public EvaluationVisitor(OutputStream os) {
        this.osw = new OutputStreamWriter(os);
        variables = new HashMap<>();
    }

    @Override
    public Integer visitAssignment(MathParser.AssignmentContext ctx) {
        String id = ctx.ID().getText();
        int val = visit(ctx.expr());
        variables.put(id, val);
        return val;
    }

    @Override
    public Integer visitPrint(MathParser.PrintContext ctx) {
        int val = visit(ctx.expr());

        try {
            osw.write(val);
        } catch (IOException e) {
            throw new RuntimeException(e);  // God bless Java's checked exceptions /s.
        }

        return val;
    }

    @Override
    public Integer visitParen(MathParser.ParenContext ctx) {
        return visit(ctx.expr());
    }

    @Override
    public Integer visitAddsub(MathParser.AddsubContext ctx) {
        int left = visit(ctx.expr(0));
        int right = visit(ctx.expr(1));

        if (ctx.ADD() != null) {
            return left + right;
        }

        return left - right;
    }

    @Override
    public Integer visitId(MathParser.IdContext ctx) {
        String id = ctx.getText();

        if (variables.containsKey(id)) {
            return variables.get(id);
        }

        throw new UnboundVariableException(id);
    }

    @Override
    public Integer visitInt(MathParser.IntContext ctx) {
        return Integer.valueOf(ctx.getText());
    }

    @Override
    public Integer visitMuldiv(MathParser.MuldivContext ctx) {
        int left = visit(ctx.expr(0));
        int right = visit(ctx.expr(1));

        if (ctx.MUL() != null) {
            return left * right;
        }

        return left / right;
    }
}
