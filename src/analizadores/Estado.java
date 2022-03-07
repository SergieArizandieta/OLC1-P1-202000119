/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package analizadores;

import java.util.ArrayList;

/**
 *
 * @author Carlos
 */
public class Estado<T> {
    
    private T id;
    private ArrayList<Transicion> transiciones = new ArrayList<>();

    /*
    * CONSTRUCTOR DE LA CLASE
    * param {id} El id del Estado actual
    * param {transiciones} lista de proximos estados
    */

    public Estado(T id, ArrayList<Transicion> transiciones) {
        this.id = id;
        this.transiciones = transiciones;
    }

    /*
    * CONSTRUCTOR DE LA CLASE
    * param {id} El id del Estado actual
    */
    public Estado(T id) {
        this.id = id;
    }

    /*
    GET DEL ATRIBUTO ID
    */
    public T getId() {
        return id;
    }

    /*
    SET DEL ATRIBUTO ID
    */
    public void setId(T id) {
        this.id = id;
    }

    /*
    GET DEL ATRIBUTO transiciones
    */
    public ArrayList<Transicion> getTransiciones() {
        return transiciones;
    }

    /*
    SET DEL ATRIBUTO transiciones
    */
    public void setTransiciones(Transicion tran) {
        this.transiciones.add(tran);
    }
    
    /*
    * METODO QUE MUESTRA EL ESTADO DEL ID
    * @return String
    */
    @Override
    public String toString(){
        return this.id.toString();
    }
    
    
    
   
    
    
    
    
    
}
