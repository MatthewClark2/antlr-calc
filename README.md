# Programming Languages
## Assignemnt 3

Given how I like to configure Antlr, the build system of choice this time is [Gradle](https://gradle.org/) since it
allowed for easier to use custom main methods and a generated-src directory.

### Syntax

The syntax is pretty similar to what we went over in class. There are, however, a few very important distinctions.
First, unary negation exists. Second, all values are interpreted as floating point numbers rather than integers. As
such, values may be given in standard decimal floating point format or in scientific notation. See 
src/main/antlr/CommonLexerRules` for specifics. Note that files should have a trailing newline in order to be processed
properly, though this isn't a concern for the generated formats.

### Running

Due to the presence of two independent main methods, there is no generic `run` task for the project. Rather, each part 
has its own task that can be ran. `part1` takes file(s) in infix notation, executes them, then converts them to infix 
and postfix format. `part2` is similar, but starts with programs in prefix notation.

From the command line, use

```bash
$ ./gradlew part1
```

to run with default arguments. If you'd like to parse your own files rather than the included test file, call it with

```bash
$ ./gradlew part1 --args="..."
```

The command line arguments for both parts are relatively straightforward, and follow the format:

```
part [flag | file]*
```

Available flags include
* `--save-output`: save output of modified forms to disk. These are in the form `infixed/prefixed/postfixed_file`, where
file refers to the input file. They are produced in the directory where the program was ran from
* `--use-stdin`: read a program from stdin rather than from files. If this flag is set, any provided files are ignored.

Any other argument not prefixed with `--` will be considered as a filename to be parsed. If any other arguments starting
with `--` *are* provided, an exception will be thrown. 

### Testing

Included tests can be ran with

```bash
$ ./gradlew test
```

If desired, extra tests can be placed in the `src/test/java` directory.
