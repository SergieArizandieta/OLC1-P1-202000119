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
inicio = "<!"
final = "!>"
%state ESTADOCADENA
%%

<YYINITIAL> {inicio}{
System.out.println("Reconocio token:<testing> lexema: "+yytext ());
return new Symbol(Simbolos.teting, yycolumn, yyline, yytext());
}

[ \t\r\n\f] { /* Espacios en blanco, se ignoran */ }

.   {
    errorList tmp= new errorList("Lexico", yytext(),"Caracter no encontrado", yyline, yycolumn);
    System.out.println("Error Lexico : "+yytext()+"Linea"+yyline+" Columna "+yycolumn);
    errores.add(tmp);
    }
