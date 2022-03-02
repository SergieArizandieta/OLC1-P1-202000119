package analizadores;

import java.util.ArrayList;
import java.util.List;


public class Estados {
	
	static Nodo_SimpleSiguientesTransiciones ActualValidacion;
	static String dot;
	static Integer estadosGestion=0;
	static Nodo_SimpleSiguientesTransiciones encabezadoEstado;
	static Boolean EstadoAceptacion =false;
	String name;
	Integer Estado;
	Boolean Aceptacion;
	List<Integer> Valores = new ArrayList<>();
	SimpleSiguientesTransiciones listado;
	List<String> CaracteresAceptados = new ArrayList<>();

	
	public Estados(Integer Estado , Boolean Aceptacion, List<Integer> Siguientes,SimpleCalcSiguientes siguientes,String name ) {
		this.Estado =Estado;
		this.Aceptacion =Aceptacion;
		this.Valores =Siguientes;
		this.listado = new SimpleSiguientesTransiciones(siguientes);
		this.name = name;
	}

	public void show() {
		String data = "";
		data += "\nEstado:" +  this.Estado;
		data += "\nAceptacion:" +  this.Aceptacion;
		data += "\nValores:" +  this.Valores;
		data += "\nTransiciones:"; 
		
		System.out.println(data);
		this.listado.Calcsiguientes.showList();;
	}
	
	public void Inciando_tabla_transiciones(SimpleCalcSiguientes siguientes) {
		dot = "";
		
		//System.out.println("==== editando valores de estado: " +  this.Estado + " ====" );
		String Tipo,Valor;
		Valor_Tipo valor_tipo;
		List<Integer> Primeros;
		
		for (Integer i :  this.Valores) {
			Tipo =siguientes.SerachTipo(i);
			if(Tipo.equals("Finalizacion")) {
				this.Aceptacion=true;
			}else {
				Valor = siguientes.SerachInfo(i);
				valor_tipo = new Valor_Tipo(Valor, Tipo);
				Primeros = siguientes.SerachPrimeros(i);
				listado.insert(valor_tipo, Primeros, siguientes);	
				
			}
		}
		encabezadoEstado = listado.primero;
		listado.AgregarDatos_Aceptados();


		System.out.println("==============Mostrando Arbol==============");
		estadosGestion=0;
		System.out.println(this.name);
		listado.verArbolMain(this.Aceptacion,this.name);
		listado.AgregarAceptacionAvReporte();
		listado.verReporte(this.name);
		listado.Draw_Graphiz(this.name);
		
		//listado.verArbol(this.Aceptacion);
		

	}
	
	public String Generar_transiciones() {
		String dot="";
		//System.out.println("==== ========= COPIA =================");
		listado.DotTraniscisiones(this.name,this.Aceptacion);
		listado.Draw_GraphizTransiciones(this.name);
		//listado.openimgTransiciones(this.name);
	
		
		return dot;
	}
	
	public void validadarCadena(String cadena) {
		EstadoAceptacion = Aceptacion;
		ActualValidacion = listado.primero;
		//cadena+="ƒ";
		
		System.out.println(cadena);
		for (int i = 0; i < cadena.length(); i++) {
			String letter = String.valueOf(cadena.charAt(i));	
			String letterSig;
			//System.out.println(letter);
			
			if(i+1<cadena.length()) {
				letterSig = String.valueOf(cadena.charAt(i+1));	
			}else {
				letterSig = null;
			}
			ValidacionGuia(ActualValidacion,letter,letterSig);
		}
		if(ActualValidacion == encabezadoEstado) {
			System.out.println("Estado en: S0");
		}else {
			System.out.println("Estado en: " + ActualValidacion.Estado );
		}
		
		
	}
	
	public void ValidacionGuia(Nodo_SimpleSiguientesTransiciones actual, String letter, String letterSig) {
		//System.out.println("____________________________________________________");
		//System.out.println("Estado " + actual.Estado + " letra: " + letter);
		//System.out.println("=====================================================");
		actual.listado.ValidacionPivote(actual,letter,letterSig);
		
		

	}
	
	
	public void verGrafo() {
		listado.openimg(this.name);
	}
	
}
