package prj.clark.pl.a3;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CommonTokenStream;

import java.util.*;

public final class A3Utils {
    public static class Config {
        public final Set<String> files;
        public final Map<String, Boolean> flags;

        private Config(Set<String> files, Map<String, Boolean> flags) {
            this.files = files;
            this.flags = flags;
        }
    }

    private A3Utils() {}

    public static Config parseArgs(String[] args, Set<String> defaultFiles) {
        Map<String, Boolean> flags = getDefaultFlags();
        Set<String> files = new HashSet<>();

        for (String arg : args) {
            if (arg.startsWith("--") && flags.containsKey(arg.substring(2))) {
                flags.put(arg.substring(2), true);
            } else if (arg.startsWith("--")) {
                throw new UnknownArgumentException(arg, flags.keySet());
            } else {
                files.add(arg);
            }
        }

        if (flags.get("use-stdin")) {
            files = Collections.emptySet();
        } else if (files.size() == 0) {
            files = defaultFiles;
        }

        return new Config(files, flags);
    }

    public static void executeInfix(CharStream cs, Map<String, MathVisitor> visitorMap) {
        MathLexer lex = new MathLexer(cs);
        CommonTokenStream cts = new CommonTokenStream(lex);
        MathParser parse = new MathParser(cts);

        for (Map.Entry<String, MathVisitor> entry : visitorMap.entrySet()) {
            System.out.println(entry.getKey());
            System.out.println(entry.getValue().visit(parse.file()));
            parse.reset();
        }
    }

    private static Map<String, Boolean> getDefaultFlags() {
        Map<String, Boolean> flags = new HashMap<>();
        flags.put("save-output", false);
        flags.put("use-stdin", false);

        return flags;
    }
}
