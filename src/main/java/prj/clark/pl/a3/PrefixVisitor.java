package prj.clark.pl.a3;

public class PrefixVisitor extends MathBaseVisitor<String> {
    private StringBuilder output;

    public PrefixVisitor() {
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
        output.append("= ")
                .append(ctx.ID().getText())
                .append(" ");

        visit(ctx.expr());

        return output.toString();
    }

    @Override
    public String visitNegative(MathParser.NegativeContext ctx) {
        output.append("(-");
        visit(ctx.expr());
        output.append(")");
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
        output.append(ctx.op.getText())
                .append(" ");

        visit(ctx.expr(0));
        output.append(" ");
        visit(ctx.expr(1));

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
        output.append(ctx.op.getText())
                .append(" ");

        visit(ctx.expr(0));
        output.append(" ");
        visit(ctx.expr(1));

        return output.toString();
    }

    @Override
    public String toString() {
        return output.toString();
    }
}
