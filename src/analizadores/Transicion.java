
package analizadores;
@SuppressWarnings({ "rawtypes"})
public class Transicion<T> {
    private Estado inicial;
    private Estado fin;
    private T simbolo;

    public Transicion(Estado inicial, Estado fin, T simbolo) {
        this.inicial = inicial;
        this.fin = fin;
        this.simbolo = simbolo;
    }

    public Estado getInicial() {return inicial;}
    
    public void setInicial(Estado inicial) { this.inicial = inicial;}

    public Estado getFin() {return fin;}

    public void setFin(Estado fin) {this.fin = fin; }

    public T getSimbolo() {return simbolo;}

    public void setSimbolo(T simbolo) {this.simbolo = simbolo;}
    
    @Override
    public String toString(){return "(" + inicial.getId() + "-" + simbolo + "-" + fin.getId() + ")";}
    
    public String DOT_String(){return (this.inicial+" -> "+this.fin+" [label=\""+this.simbolo+"\"];");}
   
}
