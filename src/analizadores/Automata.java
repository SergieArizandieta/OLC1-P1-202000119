
package analizadores;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
@SuppressWarnings({ "rawtypes", "unchecked" })

public class Automata {
	
    private Estado inicial;
    
    private final  ArrayList<Estado> aceptacion;
    
    private final ArrayList<Estado> estados;
    
    private HashSet simbolos;
    
    private int tipoAutomata;
    
    private String[] resultadoRegex;
    
    private String lenguaje;
    
    public Automata() {
        this.aceptacion = new ArrayList<>();
        this.estados = new ArrayList<>();
        this.simbolos = new HashSet();
        this.resultadoRegex = new String[3];
    }
    
	public Estado getInicial() { return inicial;}


    public void setInicial(Estado inicial) {this.inicial = inicial;}


    public HashSet getSimbolos() {return simbolos; }


    public void setSimbolos(HashSet simbolos) {this.simbolos = simbolos; }


    public int getTipoAutomata() {return tipoAutomata;}


    public void setTipoAutomata(int tipoAutomata) {this.tipoAutomata = tipoAutomata;}


    public String[] getResultadoRegex() {return resultadoRegex;}


    public void setResultadoRegex(String[] resultadoRegex) {this.resultadoRegex = resultadoRegex;}


    public String getLenguaje() { return lenguaje;}


    public void setLenguaje(String lenguaje) {this.lenguaje = lenguaje;}

    public ArrayList<Estado> getAceptacion() {return aceptacion; }

    public ArrayList<Estado> getEstados() {return estados;}
    
    public Estado getEstado(int index){ return estados.get(index);}

    public void addEstadoAceptacion(Estado estado){this.aceptacion.add(estado);}

    public void addEstados(Estado estado){ this.estados.add(estado); }
    
	public void  crearSimbolos(List<String> Elementos){
        for(String c: Elementos){
            if( !(c.equals("|") || c.equals(".") || c.equals("*") || c.equals("epsilon"))) this.simbolos.add(c);
        }
    }

    public void addResultadoRegex(int key, String value){this.resultadoRegex[key] = value;}   
    
}
