package prj.clark.pl.a3;

public class PostfixVisitor extends MathBaseVisitor<String> {
    private StringBuilder output;

    public PostfixVisitor() {
        output = new StringBuilder();
    }

    @Override
    public String visitFile(MathParser.FileContext ctx) {
        for (MathParser.StmtContext sctx : ctx.stmt()) {
            visit(sctx);
            output.append("\n");
        }

        return output.toString();
    }

    @Override
    public String visitAssignment(MathParser.AssignmentContext ctx) {
        output.append(ctx.ID().getText());
        output.append(" ");
        visit(ctx.expr());
        output.append(" =");

        return output.toString();
    }

    @Override
    public String visitNegative(MathParser.NegativeContext ctx) {
        // TODO(matthew-c21): Figure it out. Possibly remove from spec.
        return output.toString();
    }

    @Override
    public String visitNum(MathParser.NumContext ctx) {
        output.append(ctx.getText());

        return output.toString();
    }

    @SuppressWarnings("Duplicates")
    @Override
    public String visitAddsub(MathParser.AddsubContext ctx) {
        visit(ctx.expr(0));
        output.append(" ");
        visit(ctx.expr(1));
        output.append(" ");
        output.append(ctx.op.getText());

        return output.toString();
    }

    @Override
    public String visitId(MathParser.IdContext ctx) {
        output.append(ctx.ID().getText());

        return output.toString();
    }

    @SuppressWarnings("Duplicates")
    @Override
    public String visitMuldiv(MathParser.MuldivContext ctx) {
        visit(ctx.expr(0));
        output.append(" ");
        visit(ctx.expr(1));
        output.append(" ");
        output.append(ctx.op.getText());

        return output.toString();
    }

    @Override
    public String toString() {
        return output.toString();
    }
}
