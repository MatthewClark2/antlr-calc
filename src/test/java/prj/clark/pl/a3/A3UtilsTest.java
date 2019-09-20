package prj.clark.pl.a3;

import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;
import static prj.clark.pl.a3.A3Utils.parseArgs;

public class A3UtilsTest {

    private static Set<String> files(String... files) {
        return new HashSet<>(Arrays.asList(files));
    }

    private static String[] args(String... args) {
        return args;
    }

    @Test
    public void emptyArgsResultsInDefault() {
        A3Utils.Config conf = parseArgs(args(), files());

        assertTrue(conf.flags.values().stream().noneMatch(x -> x));
        assertEquals(0, conf.files.size());
    }

    @Test
    public void setsFlags() {
        A3Utils.Config conf = parseArgs(args("--save-output"), files("foo", "bar"));

        assertTrue(conf.flags.get("save-output"));
        assertFalse(conf.flags.get("use-stdin"));
        assertTrue(conf.files.contains("foo"));
        assertTrue(conf.files.contains("bar"));
        assertEquals(2, conf.files.size());
    }

    @Test
    public void useStdinRemovesAllFiles() {
        A3Utils.Config conf = parseArgs(args("--save-output", "--use-stdin"), files("foo", "bar"));

        assertTrue(conf.flags.get("save-output"));
        assertTrue(conf.flags.get("use-stdin"));
        assertEquals(0, conf.files.size());
    }

    @Test(expected = UnknownArgumentException.class)
    public void unrecognizedFlagThrowsException() {
        parseArgs(args("--foobar"), files());
    }

    @Test
    public void setsFiles() {
        A3Utils.Config conf = parseArgs(args("hello", "foo", "bar"), files("err"));

        assertTrue(conf.flags.values().stream().noneMatch(x -> x));
        assertTrue(conf.files.containsAll(Arrays.asList("hello", "foo", "bar")));
        assertEquals(3, conf.files.size());
    }

    @Test
    public void setsFlagsAndFiles() {
        A3Utils.Config conf = parseArgs(args("hello", "--save-output"), files());

        assertTrue(conf.flags.get("save-output"));
        assertFalse(conf.flags.get("use-stdin"));
        assertTrue(conf.files.contains("hello"));
        assertEquals(1, conf.files.size());
    }
}