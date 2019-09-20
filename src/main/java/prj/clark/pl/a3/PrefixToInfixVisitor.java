package prj.clark.pl.a3;

public class PrefixToInfixVisitor extends PrefixMathBaseVisitor<String> {
    private StringBuilder output;

    public PrefixToInfixVisitor() {
        output = new StringBuilder();
    }


    @Override
    public String visitFile(PrefixMathParser.FileContext ctx) {
        for (PrefixMathParser.StmtContext sctx : ctx.stmt()) {
            visit(sctx);
            output.append("\n");
        }

        return output.toString();
    }

    @Override
    public String visitAssignment(PrefixMathParser.AssignmentContext ctx) {
        output.append(ctx.ID().getText())
                .append(" = ");
        visit(ctx.expr());

        return output.toString();
    }

    @Override
    public String visitMuldiv(PrefixMathParser.MuldivContext ctx) {
        visit(ctx.expr(0));
        output.append(" ")
                .append(ctx.op.getText())
                .append(" ");
        visit(ctx.expr(1));

        return output.toString();
    }

    @Override
    public String visitAddsub(PrefixMathParser.AddsubContext ctx) {
        // TODO(matthew-c21): Check if there's a way to determine if a child does anything but addition.
        output.append("(");
        visit(ctx.expr(0));
        output.append(" ")
                .append(ctx.op.getText())
                .append(" ");
        visit(ctx.expr(1));
        output.append(")");

        return output.toString();
    }

    @Override
    public String visitNegative(PrefixMathParser.NegativeContext ctx) {
        output.append("-");
        visit(ctx.expr());

        return output.toString();
    }

    @Override
    public String visitNum(PrefixMathParser.NumContext ctx) {
        output.append(ctx.getText());

        return output.toString();
    }

    @Override
    public String visitId(PrefixMathParser.IdContext ctx) {
        output.append(ctx.ID().getText());

        return output.toString();
    }
}
