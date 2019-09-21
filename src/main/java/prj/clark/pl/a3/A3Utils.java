package prj.clark.pl.a3;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
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

    public static class FileProducingVisitor {
        private final ParseTreeVisitor visitor;
        private final String filename;

        public FileProducingVisitor(ParseTreeVisitor visitor) {
            this.visitor = visitor;
            filename = null;
        }

        public FileProducingVisitor(ParseTreeVisitor visitor, String filename) {
            this.visitor = visitor;

            if (filename != null) {
                this.filename = Paths.get(filename).getFileName().toString();
            } else {
                this.filename = null;
            }
        }

        public ParseTreeVisitor getVisitor() {
            return visitor;
        }

        public Optional<String> getFilename() {
            return Optional.ofNullable(filename);
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

    public static void executeInfix(CharStream cs, Map<String, FileProducingVisitor> visitorMap) throws IOException {
        MathLexer lex = new MathLexer(cs);
        CommonTokenStream cts = new CommonTokenStream(lex);
        MathParser parse = new MathParser(cts);

        for (Map.Entry<String, FileProducingVisitor> entry : visitorMap.entrySet()) {
            System.out.println(entry.getKey());

            ParseTreeVisitor visitor = entry.getValue().getVisitor();
            visitor.visit(parse.file());
            System.out.println(visitor);

            Optional<String> filename = entry.getValue().getFilename();

            if (filename.isPresent()) {
                try(BufferedWriter bw = new BufferedWriter(new FileWriter(filename.get()))) {
                    bw.write(visitor.toString());
                }
            }

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
