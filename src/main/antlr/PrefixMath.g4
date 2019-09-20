grammar PrefixMath;

import CommonLexerRules;

@header {
package prj.clark.pl.a3;
}

file: stmt+ ;

stmt
    : ASSIGN ID expr NEWLINE    # assignment
    | expr NEWLINE              # print
    | NEWLINE                   # empty
    ;

expr
    : op=(MUL | DIV | ADD | SUB) expr expr  # operation
    | SUB expr                              # negative
    | (FLOAT | INT)                         # num
    | ID                                    # id
    ;
