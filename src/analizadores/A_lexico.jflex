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

letra = [a-zA-Z]
id = {letra}+
loQuesea = [\"][^\"]*[\"]
%state ESTADOCADENA
%%


<YYINITIAL> "," {
System.out.println("Reconocio token:<coma> lexema: "+yytext ());
return new Symbol(Simbolos.coma, yycolumn, yyline, yytext());
}

<YYINITIAL> {id} {
System.out.println("Reconocio token:<id> lexema:"+yytext());
return new Symbol(Simbolos.id, yycolumn, yyline, yytext());
}


<YYINITIAL> {loQuesea} {

    System.out.println("Entra estado cadena: " +yytext());
    return new Symbol(Simbolos.cadena, yycolumn, yyline, yytext());

}


[ \t\r\n\f] { /* Espacios en blanco, se ignoran */ }

.   {
    errorList tmp= new errorList("Lexico", yytext(),"Caracter no encontrado", yyline, yycolumn);
    System.out.println("Error Lexico : "+yytext()+"Linea"+yyline+" Columna "+yycolumn);
    errores.add(tmp);
    }
