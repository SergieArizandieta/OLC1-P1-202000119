package analizadores;
import java_cup.runtime.*;
import java.util.LinkedList;

%%
%{
    public static LinkedList<errorList> errores  = new LinkedList<errorList>();
%}
                                                    
%public
%class Analizador_Lexico
%cupsym Simbolos
%cup
%char
%column
%full
%ignorecase
%line
%unicode
LETTER = [a-zA-Z]
DIGIT = [0-9]
NUMBER = {DIGIT}+
MULTILINE_COMMENT =	"<!" [^"!>"]* "!>"
ONE_LINE_COMMENT = "//" [^"\n"]* 
S_LA = "{"
S_LL = "}"
S_DOTS = ":"
S_SEMICOLON =	";"
S_ARROW =	"->"
S_PCENTS =	"%%"
S_RANK	 =	"~"
S_COLON	 =	","
S_DOT = "."
S_LINE = "|"
S_ASTERISK = "*"
S_PLUS = "+"
S_QMARK = "?"
S_LBREAK = "\\n"
S_QUOTE = "\\\'"
S_DQUOTES = "\\\""
RANGE = [!-/] | [:-@] | [\[-`] | [\{-\}]

CONJ = ["c"|"C"]["o"|"O"]["n"|"N"]["j"|"J"]
IDENTIFICADOR = {LETTER}({LETTER}|{DIGIT}|"_")*
PHRASE = "\"" [^"\""]* "\""

%state ESTADOCADENA
%%

<YYINITIAL> {PHRASE} {
    System.out.println("Reconocio token: "+yytext());
    return new Symbol(Simbolos.teting, yycolumn, yyline, yytext());
}

<YYINITIAL> {S_ASTERISK} {
    System.out.println("Reconocio token normal: " +yytext());
}

<YYINITIAL> {S_PLUS} {
    System.out.println("Reconocio token normal: " +yytext());
}

<YYINITIAL> {S_COLON} {
    System.out.println("Reconocio token normal: " +yytext());
}

<YYINITIAL> {S_DOT} {
    System.out.println("Reconocio token normal: " +yytext());
}

<YYINITIAL> {S_DOTS} {
    System.out.println("Reconocio token normal: " +yytext());
}

<YYINITIAL> {S_SEMICOLON} {
    System.out.println("Reconocio token normal: " +yytext());
}

<YYINITIAL> {S_QMARK} {
    System.out.println("Reconocio token normal: " +yytext());
}

<YYINITIAL> {S_LA} {
    System.out.println("Reconocio token normal: " +yytext());
}

<YYINITIAL> {S_LINE} {
    System.out.println("Reconocio token normal: " +yytext());
}

<YYINITIAL> {S_LL} {
    System.out.println("Reconocio token normal: " +yytext());
}
<YYINITIAL> {RANGE} {
    System.out.println("Reconocio tokenDDDD: " +yytext());
}






[ \t\r\n\f] { /* Espacios en blanco, se ignoran */ }

.   {
    errorList tmp= new errorList("Lexico", yytext(),"Caracter no encontrado", yyline, yycolumn);
    System.out.println("Error Lexico : "+yytext()+"Linea"+yyline+" Columna "+yycolumn);
    errores.add(tmp);
    }
