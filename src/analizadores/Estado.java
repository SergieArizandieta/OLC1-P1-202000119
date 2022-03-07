
package analizadores;

import java.util.ArrayList;
@SuppressWarnings({ "rawtypes" })
public class Estado<T> {
    
    private T id;
    private ArrayList<Transicion> transiciones = new ArrayList<>();

    public Estado(T id, ArrayList<Transicion> transiciones) {
        this.id = id;
        this.transiciones = transiciones;
    }

    public Estado(T id) {this.id = id;}

    public T getId() { return id;}

    public void setId(T id) {this.id = id;}

    public ArrayList<Transicion> getTransiciones() {return transiciones; }

    public void setTransiciones(Transicion tran) {this.transiciones.add(tran);}
    
    @Override
    public String toString(){return this.id.toString(); }  
    
}
