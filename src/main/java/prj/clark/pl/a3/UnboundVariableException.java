package prj.clark.pl.a3;

public class UnboundVariableException extends RuntimeException {
    public UnboundVariableException(String variable) {
        super("No such variable '" + variable + "'.");
    }
}
