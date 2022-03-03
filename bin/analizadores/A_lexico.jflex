package analizadores;
import java_cup.runtime.*;
import java.util.LinkedList;

%%
%{
    public  LinkedList<errorList> errores  = new LinkedList<errorList>();
    public  LinkedList<tokens> TokensList  = new LinkedList<tokens>();
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
ONE_LINE_COMMENT = "//" [^"\n"]* 
MULTILINE_COMMENT =	"<!" [^"!>"]* "!>"
S_LA = "{"
S_LLC = "}"
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
SPACE = "\" \""
CONJ = ["c"|"C"]["o"|"O"]["n"|"N"]["j"|"J"]
IDENTIFICADOR = {LETTER}({LETTER}|{DIGIT}|"_")*
PHRASE = "\"" [^"\""]* "\""


%state ESTADOCADENA
%%

<YYINITIAL>{
    {LETTER}    {//System.out.println("Reconocio token: <LETTER>: "+yytext());
     tokens tmp= new tokens( yytext(), yyline, yycolumn);
     TokensList.add(tmp);
        return new Symbol(Simbolos.LETTER, yycolumn, yyline, yytext());}
    {NUMBER}    {//System.out.println("Reconocio token: <NUMBER>: "+yytext());
        tokens tmp= new tokens( yytext(), yyline, yycolumn);
        TokensList.add(tmp);
            return new Symbol(Simbolos.NUMBER, yycolumn, yyline, yytext());}   

    {ONE_LINE_COMMENT}    {System.out.println("Reconocio token: <ONE_LINE_COMMENT>: "+yytext());
        tokens tmp= new tokens( yytext(), yyline, yycolumn);
        TokensList.add(tmp);}
    {MULTILINE_COMMENT}    {System.out.println("Reconocio token: <MULTILINE_COMMENT>: "+yytext());
                tokens tmp= new tokens( yytext(), yyline, yycolumn);
                TokensList.add(tmp);}

    {S_LA}    {tokens tmp= new tokens( yytext(), yyline, yycolumn);
        TokensList.add(tmp);//System.out.println("Reconocio token: <S_LA>: "+yytext());
            return new Symbol(Simbolos.S_LA, yycolumn, yyline, yytext());}
    {S_LLC}    {tokens tmp= new tokens( yytext(), yyline, yycolumn);
        TokensList.add(tmp);//System.out.println("Reconocio token: <S_LLC>: "+yytext());
            return new Symbol(Simbolos.S_LLC, yycolumn, yyline, yytext());}
    
    {S_DOTS}    {tokens tmp= new tokens( yytext(), yyline, yycolumn);
        TokensList.add(tmp);//System.out.println("Reconocio token: <S_DOTS>: "+yytext());
            return new Symbol(Simbolos.S_DOTS, yycolumn, yyline, yytext());}
    
    {S_SEMICOLON}    {tokens tmp= new tokens( yytext(), yyline, yycolumn);
        TokensList.add(tmp);//System.out.println("Reconocio token: <S_SEMICOLON>: "+yytext());
            return new Symbol(Simbolos.S_SEMICOLON, yycolumn, yyline, yytext());}

    {S_ARROW}    {tokens tmp= new tokens( yytext(), yyline, yycolumn);
        TokensList.add(tmp);//System.out.println("Reconocio token: <S_ARROW>: "+yytext());
            return new Symbol(Simbolos.S_ARROW, yycolumn, yyline, yytext());}

    {S_PCENTS}    {tokens tmp= new tokens( yytext(), yyline, yycolumn);
        TokensList.add(tmp);//System.out.println("Reconocio token: <S_PCENTS>: "+yytext());
            return new Symbol(Simbolos.S_PCENTS, yycolumn, yyline, yytext());}

    {S_RANK}    {tokens tmp= new tokens( yytext(), yyline, yycolumn);
        TokensList.add(tmp);//System.out.println("Reconocio token: <S_RANK>: "+yytext());
            return new Symbol(Simbolos.S_RANK, yycolumn, yyline, yytext());}

    {S_COLON}    {tokens tmp= new tokens( yytext(), yyline, yycolumn);
        TokensList.add(tmp);//System.out.println("Reconocio token: <S_COLON>: "+yytext());
            return new Symbol(Simbolos.S_COLON, yycolumn, yyline, yytext());}
    
    {S_DOT}    {tokens tmp= new tokens( yytext(), yyline, yycolumn);
        TokensList.add(tmp);//System.out.println("Reconocio token: <S_DOT>: "+yytext());
            return new Symbol(Simbolos.S_DOT, yycolumn, yyline, yytext());}

    {S_LINE}    {tokens tmp= new tokens( yytext(), yyline, yycolumn);
        TokensList.add(tmp);//System.out.println("Reconocio token: <S_LINE>: "+yytext());
            return new Symbol(Simbolos.S_LINE, yycolumn, yyline, yytext());}

    {S_ASTERISK}    {//System.out.println("Reconocio token: <S_ASTERISK>: "+yytext());
            return new Symbol(Simbolos.S_ASTERISK, yycolumn, yyline, yytext());}

    {S_PLUS}    {tokens tmp= new tokens( yytext(), yyline, yycolumn);
        TokensList.add(tmp);//System.out.println("Reconocio token: <S_PLUS>: "+yytext());
            return new Symbol(Simbolos.S_PLUS, yycolumn, yyline, yytext());}

    {S_QMARK}    {tokens tmp= new tokens( yytext(), yyline, yycolumn);
        TokensList.add(tmp);//System.out.println("Reconocio token: <S_QMARK>: "+yytext());
            return new Symbol(Simbolos.S_QMARK, yycolumn, yyline, yytext());}
    
    {S_LBREAK}    {tokens tmp= new tokens( yytext(), yyline, yycolumn);
        TokensList.add(tmp);//System.out.println("Reconocio token: <S_LBREAK>: "+yytext());
            return new Symbol(Simbolos.S_LBREAK, yycolumn, yyline, yytext());}

    {S_QUOTE}   { tokens tmp= new tokens( yytext(), yyline, yycolumn);
        TokensList.add(tmp);//System.out.println("Reconocio token: <S_QUOTE>: "+yytext());
            return new Symbol(Simbolos.S_QUOTE, yycolumn, yyline, yytext());}

    {S_DQUOTES}    {tokens tmp= new tokens( yytext(), yyline, yycolumn);
        TokensList.add(tmp);//System.out.println("Reconocio token: <S_DQUOTES>: "+yytext());
            return new Symbol(Simbolos.S_DQUOTES, yycolumn, yyline, yytext());}

    {RANGE}    {tokens tmp= new tokens( yytext(), yyline, yycolumn);
        TokensList.add(tmp);//System.out.println("Reconocio token: <RANGE>: "+yytext());
            return new Symbol(Simbolos.RANGE, yycolumn, yyline, yytext());}

    {SPACE}    {tokens tmp= new tokens( yytext(), yyline, yycolumn);
        TokensList.add(tmp);//System.out.println("Reconocio token: <SPACE>: "+yytext());
            return new Symbol(Simbolos.SPACE, yycolumn, yyline, yytext());}

    {CONJ}    {tokens tmp= new tokens( yytext(), yyline, yycolumn);
        TokensList.add(tmp);//System.out.println("Reconocio token: <CONJ>: "+yytext());
            return new Symbol(Simbolos.CONJ, yycolumn, yyline, yytext());}

    {IDENTIFICADOR}    {tokens tmp= new tokens( yytext(), yyline, yycolumn);
        TokensList.add(tmp);//System.out.println("Reconocio token: <IDENTIFICADOR>: "+yytext());
            return new Symbol(Simbolos.IDENTIFICADOR, yycolumn, yyline, yytext());}

    {PHRASE}    {tokens tmp= new tokens( yytext(), yyline, yycolumn);
        TokensList.add(tmp);//System.out.println("Reconocio token: <PHRASE>: "+yytext());
            return new Symbol(Simbolos.PHRASE, yycolumn, yyline, yytext());}
    

    


    


}


[ \t\r\n\f] { /* Espacios en blanco, se ignoran */ }

.   {
    errorList tmp= new errorList("Lexico", yytext(),"Caracter no encontrado", yyline, yycolumn);
    System.out.println("Error Lexico : "+yytext()+"Linea"+yyline+" Columna "+yycolumn);
    errores.add(tmp);
    }
