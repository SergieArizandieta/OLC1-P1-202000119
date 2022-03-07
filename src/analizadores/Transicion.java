/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package analizadores;

/**
 *
 * @author Carlos
 */
public class Transicion<T> {
    private Estado inicial;
    private Estado fin;
    private T simbolo;

    /*
    * CONSTRUCTOR DE LA CLASE
    * @param {incial} estado de inicio
    * @param {fin} estado final
    * @param {simbolo} string o caracter actual
    */
    public Transicion(Estado inicial, Estado fin, T simbolo) {
        this.inicial = inicial;
        this.fin = fin;
        this.simbolo = simbolo;
    }

    /*
    GET DEL ATRIBUTO inicial
    */
    public Estado getInicial() {
        return inicial;
    }

    /*
    SET DEL ATRIBUTO fin
    */
    public void setInicial(Estado inicial) {
        this.inicial = inicial;
    }

    /*
    GET DEL ATRIBUTO fin
    */
    public Estado getFin() {
        return fin;
    }

    /*
    SET DEL ATRIBUTO fin
    */
    public void setFin(Estado fin) {
        this.fin = fin;
    }

    /*
    GET DEL ATRIBUTO simbolo
    */
    public T getSimbolo() {
        return simbolo;
    }
    /*
    SET DEL ATRIBUTO simbolo
    */
    public void setSimbolo(T simbolo) {
        this.simbolo = simbolo;
    }
    
    /*
    * METODOS PARA MOSTRAR LA TRANSICION STRING O EN DOT
    */
    @Override
    public String toString(){
        return "(" + inicial.getId() + "-" + simbolo + "-" + fin.getId() + ")";
    }
    
    public String DOT_String(){
        return (this.inicial+" -> "+this.fin+" [label=\""+this.simbolo+"\"];");
    }

    
        
    
}
