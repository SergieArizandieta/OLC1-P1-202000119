/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package analizadores;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;
import java.util.Queue;
import Main.*;
/**
 *
 * @author Carlos
 */
public class AFND<T> {
    private Automata afn;
   
    private  List<String> Elementos;

    public AFND(List<String> Elementos) {
        this.Elementos = Elementos;
    }
    
    /*
    * METODO QUE CONSTRUIRA EL AUTOMATA A PARTIR DE LA EXPRESION MANDADA
    */
    public void construir(){
        try{
            Stack<Automata> pila = new Stack<Automata>();
            
            for(String c : Elementos){
            	System.out.print(c + " ");
            	
                switch(c){
                case "*":
                    Automata kleene = cerraduraKleene((Automata)pila.pop()); 
                    pila.push(kleene);
                    this.afn = kleene;
                    break;
                
                case ".":
                    Automata op1 = (Automata)pila.pop();
                    Automata op2 = (Automata)pila.pop();
                    Automata concatenar = concatenacion(op1,op2);
                    pila.push(concatenar);
                    this.afn = concatenar;
                    break;
                    
                case "|":
                    Automata op1Or = (Automata)pila.pop();
                    Automata op2Or = (Automata)pila.pop();
                    Automata OR = union(op1Or,op2Or);
                    pila.push(OR);
                    this.afn = OR;
                    break;
                 
                default:
                    Automata simple = automataSimple((T)c);
                    pila.push(simple);
                    this.afn = simple;
                    break;
            }
               
                }
                
            
            /*switch(c){
            case "*":
                Automata kleene = cerraduraKleene((Automata)Cola.peek());
                Cola.remove();
                Cola.add(kleene);
                this.afn = kleene;
                break;
            
            case ".":
                Automata op1 = (Automata)Cola.peek();
                Cola.remove();
                Automata op2 = (Automata)Cola.peek();
                Cola.remove();
                Automata concatenar = concatenacion(op1,op2);
                Cola.add(concatenar);
                this.afn = concatenar;
                break;
                
            case "|":
                Automata op1Or = (Automata)Cola.peek();
                Cola.remove();
                Automata op2Or = (Automata)Cola.peek();
                Cola.remove();
                Automata OR = union(op1Or,op2Or);
                Cola.add(OR);
                this.afn = OR;
                break;
             
            default:
                Automata simple = automataSimple((T)c);
                Cola.add(simple);
                this.afn = simple;
                break;*/
            
            
            this.afn.crearSimbolos(Elementos);
            this.afn.setTipoAutomata(0);
            
        }catch(Exception e){
            System.out.println("ERROR CONSTRUIR AUTOMATA: " + e.getMessage());
        }
    }
    
    public Automata concatenacion(Automata op1, Automata op2){
        Automata a = new Automata();
        int i = 0;
        for ( i = 0; i < op2.getEstados().size(); i++){
            Estado temp = (Estado) op2.getEstados().get(i);
            temp.setId(i);
            if(i == 0) a.setInicial(temp);
            if(i == op2.getEstados().size() - 1) 
            {
                
                for(int k = 0; k < op2.getAceptacion().size(); k++){
                    temp.setTransiciones(new Transicion((Estado) op2.getAceptacion().get(k),op1.getInicial(),Main.EPSILON));
                }
            }
            a.addEstados(temp);
        }
        
        for (int j = 0; j < op1.getEstados().size(); j++){
            Estado temp = (Estado) op1.getEstados().get(j);
            temp.setId(i);
            if(op1.getEstados().size() -1  == j) a.addEstadoAceptacion(temp);
            a.addEstados(temp);
            i++;
        }
        
        HashSet simbolos = new HashSet();
        simbolos.addAll(op1.getSimbolos());
        simbolos.addAll(op2.getSimbolos());
        a.setSimbolos(simbolos);
        a.setLenguaje(op1.getLenguaje() + " " + op2.getLenguaje());
        
        return a;
    }

    public Automata automataSimple(T simbolo){
        Automata a = new Automata();
        
        Estado inicio = new Estado(0);
        Estado fin = new Estado(1);
        
        Transicion tran = new Transicion(inicio,fin,simbolo);
        
        inicio.setTransiciones(tran);
        
        a.addEstados(inicio);
        a.addEstados(fin);
        a.setInicial(inicio);
        a.addEstadoAceptacion(fin);
        a.setLenguaje(simbolo + "");
        
        
        return a;
    }

    public Automata cerraduraKleene(Automata automata){
        Automata a = new Automata();
        
        Estado inicio = new Estado(0);
        a.addEstados(inicio);
        a.setInicial(inicio);
        
        for(int i = 0; i < automata.getEstados().size(); i++){
            Estado temp = (Estado)automata.getEstados().get(i);
            temp.setId(i + 1);
            a.addEstados(temp);
        }
        
        Estado fin = new Estado(automata.getEstados().size() + 1);
        a.addEstados(fin);
        a.addEstadoAceptacion(fin);
        
        Estado oldInicio = automata.getInicial();
        
        ArrayList<Estado> oldFin = automata.getAceptacion();
        
        inicio.getTransiciones().add(new Transicion(inicio,oldInicio,Main.EPSILON));
        inicio.getTransiciones().add(new Transicion(inicio,fin,Main.EPSILON));
        
        for(int i = 0; i < oldFin.size(); i++){
            oldFin.get(i).getTransiciones().add(new Transicion(oldFin.get(i),oldInicio,Main.EPSILON));
            oldFin.get(i).getTransiciones().add(new Transicion(oldFin.get(i),fin,Main.EPSILON));
        }
        a.setSimbolos(automata.getSimbolos());
        a.setLenguaje(automata.getLenguaje());
        
        return a;
    }

    public Automata union(Automata AFN1, Automata AFN2){
        Automata afnunion = new Automata();
        Estado inicioNuevo = new Estado(0);
        inicioNuevo.setTransiciones(new Transicion(inicioNuevo,AFN2.getInicial(),Main.EPSILON));

        afnunion.addEstados(inicioNuevo);
        afnunion.setInicial(inicioNuevo);
        int i=0;//llevar el contador del identificador de estados
        //agregar los estados del segundo automata
        for (i=0; i < AFN1.getEstados().size(); i++) {
            Estado tmp = (Estado) AFN1.getEstados().get(i);
            tmp.setId(i + 1);
            afnunion.addEstados(tmp);
        }
        //agregar los estados del primer automata
        for (int j=0; j < AFN2.getEstados().size(); j++) {
            Estado tmp = (Estado) AFN2.getEstados().get(j);
            tmp.setId(i + 1);
            afnunion.addEstados(tmp);
            i++;
        }
        
        Estado nuevoFin = new Estado(AFN1.getEstados().size() +AFN2.getEstados().size()+ 1);
        afnunion.addEstados(nuevoFin);
        afnunion.addEstadoAceptacion(nuevoFin);
        
       
        Estado anteriorInicio = AFN1.getInicial();
        ArrayList<Estado> anteriorFin    = AFN1.getAceptacion();
        ArrayList<Estado> anteriorFin2    = AFN2.getAceptacion();
        
        inicioNuevo.getTransiciones().add(new Transicion(inicioNuevo, anteriorInicio, Main.EPSILON));
        
        for (int k =0; k<anteriorFin.size();k++)
            anteriorFin.get(k).getTransiciones().add(new Transicion(anteriorFin.get(k), nuevoFin, Main.EPSILON));
        for (int k =0; k<anteriorFin.size();k++)
            anteriorFin2.get(k).getTransiciones().add(new Transicion(anteriorFin2.get(k),nuevoFin,Main.EPSILON));
        
        HashSet alfabeto = new HashSet();
        alfabeto.addAll(AFN1.getSimbolos());
        alfabeto.addAll(AFN2.getSimbolos());
        afnunion.setSimbolos(alfabeto);
        afnunion.setLenguaje(AFN1.getLenguaje()+" " + AFN2.getLenguaje()); 
        return afnunion;
    }

    public Automata getAfn() {
        return afn;
    }
    
    
    
}
