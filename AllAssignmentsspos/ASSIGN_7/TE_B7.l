%{
#include<stdio.h>
int lines=0, words=0,s_letters=0,c_letters=0, num=0, spl_char=0,total=0; // Declaration of header files and variables
%}


%%
\n { lines++; words++;}
[\t ' '] words++;
[A-Z] c_letters++; // Pattern Matching Rules
[a-z] s_letters++;
[0-9] num++;
. spl_char++;
%%


int main(void) // Subroutines
{
yyin= fopen("TE_B7.txt","r");
yylex();
total=s_letters+c_letters+num+spl_char;
printf(" This File contains ...");
printf("\n\t%d lines", lines);
printf("\n\t%d words",words);
printf("\n\t%d small letters", s_letters);
printf("\n\t%d capital letters",c_letters);
printf("\n\t%d digits", num);
printf("\n\t%d special characters",spl_char);
printf("\n\tIn total %d characters.\n",total);
}
 

int yywrap()
{
return(1); // Return 1 to indicate that Input string/expression or input file is ended or Reading of input file is completed.
}











