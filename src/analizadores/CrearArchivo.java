/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package analizadores;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 *
 * @author Carlos
 */
public class CrearArchivo {
    String direccion;
    String tipo;
    Automata automata;
    String salida;
    
    /*
    * COSNTRUCTOR DE LA CLASE
    * @param {direccion} nombre del archivo con su extension
    * @param {tipo} AFN| AFD
    * @param {automata} automata creado desde la expresion regular
    */
    public CrearArchivo(String direccion, String tipo, Automata automata) {
        this.direccion = direccion;
        this.tipo = tipo;
        this.automata = automata;
    }
    
    
    /*
    * METODO QUE SE ENCARGARA DE CREAR EL ARCHIVO DOT Y GENERAR LA IMAGEN CON GRAPHVIZ
    */
    
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
        File file = new File(path  + direccion);
        FileWriter out;
        salida = texto;
        try{
            out = new FileWriter(file);
            out.write(texto);
            out.close();
        }catch(Exception e){}
       
        String comando = "dot -Tpng \"" + path + direccion + "\" -o \"" + path  + tipo + ".png\"";
        try
        {
            ProcessBuilder pbuilder;
            pbuilder = new ProcessBuilder("cmd.exe", "/c", comando);
            pbuilder.redirectErrorStream( true );
            //Ejecuta el proceso
            Process p = pbuilder.start();
            BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line;
            while (true) {
                line = r.readLine();
                if (line == null) { break; }
                System.out.println(line);
            }
            
            comando = path + tipo + ".png";
             pbuilder = new ProcessBuilder("cmd.exe", "/c", comando);
            pbuilder.redirectErrorStream( true );
            //Ejecuta el proceso
            p = pbuilder.start();
            r = new BufferedReader(new InputStreamReader(p.getInputStream()));
            while (true) {
                line = r.readLine();
                if (line == null) { break; }
                System.out.println(line);
            }

        }
        catch (IOException e)
        {
           System.out.println("Erro crear imagen " +  e.getMessage());
        }
    }

    //---------------------------------------- OBTENEMOS EL TEXTO PARA EL DOT --------------------------
    public String getSalida() {
        return salida;
    }

    
}
