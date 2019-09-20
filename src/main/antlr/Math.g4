grammar Math;

import CommonLexerRules;

@header {
package prj.clark.pl.a3;
}

file: stmt+ ;

stmt:
    ID ASSIGN expr NEWLINE  # assignment
    | expr NEWLINE          # print
    | NEWLINE               # empty
    ;

expr:
    LPAR expr RPAR              # paren
    | expr op=(MUL | DIV) expr  # muldiv
    | expr op=(ADD | SUB) expr  # addsub
    | SUB expr                  # negative
    | (FLOAT | INT)             # num
    | ID                        # id
    ;

