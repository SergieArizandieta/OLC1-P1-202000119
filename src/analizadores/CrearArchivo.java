
package analizadores;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;


public class CrearArchivo {
    String direccion;
    String tipo;
    Automata automata;
    String salida;
    

    public CrearArchivo(String direccion, String tipo, Automata automata) {
        this.direccion = direccion;
        this.tipo = tipo;
        this.automata = automata;
    }
    
 
    
    public void crearImagen(){
        String path = "AFND_202000119\\";
        String texto = "digraph finite_state_machine{\n";
        texto +="\tbgcolor = \"#F6FFE3\""+"\n";
        texto +="\trankdir=LR;"+"\n";
        texto+="\tnode [fontname=\"Helvetica,Arial,sans-serif\" ]\n";
        texto+="\tedge [fontname=\"Helvetica,Arial,sans-serif\"]\n";
        texto +="\tnode [shape = doublecircle, color = gold fillcolor=\"#EBE3FF\" style =filled];";
        
        for(int i = 0; i < automata.getAceptacion().size(); i++){
            texto += " " + automata.getAceptacion().get(i);
        }
        
        texto += ";" + "\n";
        texto +="\tnode [shape = circle, color = \"#2CB5FF\" fillcolor=\"#E3FFFA\" style =filled];\n";
        
       
        texto += "\tflechainicio [style=invis];\n" + "	flechainicio -> " + automata.getInicial() + " [label=\"inicio\"];" + "\n";
        
        for(int i = 0; i < automata.getEstados().size(); i++){
            ArrayList<Transicion> temp = automata.getEstados().get(i).getTransiciones();
            for (int j = 0; j < temp.size(); j++){
                texto += "\t" + temp.get(j).DOT_String() + "\n";
            }
        }
        texto += "}";
       
        salida = texto;
             
    }

    public String getSalida() {
        return salida;
    }

    
}
