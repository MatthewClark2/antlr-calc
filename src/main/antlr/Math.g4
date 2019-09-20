grammar Math;

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
    | (INT | FLOAT)             # num
    | ID                        # id
    ;


fragment DIGIT: [0-9];
fragment ID_START: [a-zA-Z_] ;
fragment ID_PART: ID_START | DIGIT ;

// TODO(matthew-c21): Replace ints with floats.
ID: ID_START ID_PART* ;
INT: DIGIT+;

fragment BASE_FLOAT: DIGIT* '.' DIGIT+ ;

FLOAT: BASE_FLOAT ;  // TODO(matthew-c21): Add scientific notation and limit floating point definition.

MUL: '*' ;
DIV: '/' ;
ADD: '+' ;
SUB: '-' ;

ASSIGN: '=' ;
LPAR: '(' ;
RPAR: ')' ;

NEWLINE: '\n' ;
WS: [ \t] -> skip ;
