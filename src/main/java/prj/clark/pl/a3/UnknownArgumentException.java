package prj.clark.pl.a3;

import java.util.Set;

public class UnknownArgumentException extends RuntimeException {

    public UnknownArgumentException(String arg, Set<String> legalKeys) {
        super(formMessage(arg, legalKeys));
    }

    private static String formMessage(String arg, Set<String> legalKeys) {
        StringBuilder sb = new StringBuilder("Unkown argument '")
                .append(arg)
                .append("'. Did you mean one of: ");

        for (String key : legalKeys) {
            sb.append(key)
                    .append(" ");
        }

        return sb.append("?").toString();
    }
}
