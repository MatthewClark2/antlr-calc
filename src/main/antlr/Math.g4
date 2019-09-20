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
    | (FLOAT | INT)             # num
    | ID                        # id
    ;


fragment DIGIT: [0-9];
fragment ID_START: [a-zA-Z_] ;
fragment ID_PART: ID_START | DIGIT ;

ID: ID_START ID_PART* ;
INT: DIGIT+;

fragment BASE_FLOAT: DIGIT* '.' DIGIT+ ;
fragment EXP_FLOAT: ([1-9] | DIGIT? '.' DIGIT+) ('e'|'E') (ADD | SUB)? (BASE_FLOAT | INT) ;

FLOAT: BASE_FLOAT | EXP_FLOAT ;

MUL: '*' ;
DIV: '/' ;
ADD: '+' ;
SUB: '-' ;

ASSIGN: '=' ;
LPAR: '(' ;
RPAR: ')' ;

NEWLINE: '\n' ;
WS: [ \t] -> skip ;
