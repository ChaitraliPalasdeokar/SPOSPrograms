
%{
#include<stdio.h>
#include<stdlib.h>
%}

%token DIGIT LETTER UND NL

%%
stmt: variable NL {printf("Valid Identifiers :)\n"); exit(0);}
;
variable: LETTER alphanumeric
;
alphanumeric: LETTER alphanumeric | DIGIT alphanumeric | UND alphanumeric | LETTER | DIGIT | UND
;

%%

int yyerror(char *msg)
{
 printf("Invalid Variable :(\n");
 exit(0);
}

main()
{
 printf("Enter the variable: \n");
 yyparse();
}

