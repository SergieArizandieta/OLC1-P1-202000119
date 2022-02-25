package analizadores;

import java.util.ArrayList;
import java.util.List;


public class Estados {

	
	Integer Estado;
	Boolean Aceptacion;
	List<Integer> Valores = new ArrayList<>();
	SimpleSiguientesTransiciones listado;
	List<String> CaracteresAceptados = new ArrayList<>();

	
	public Estados(Integer Estado , Boolean Aceptacion, List<Integer> Siguientes,SimpleCalcSiguientes siguientes ) {
		this.Estado =Estado;
		this.Aceptacion =Aceptacion;
		this.Valores =Siguientes;
		this.listado = new SimpleSiguientesTransiciones(siguientes,0);
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
		System.out.println("==== editando valores de estado: " +  this.Estado + " ====" );
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
		listado.showList();
		listado.AgregarDatos_Aceptados();
		listado.showList();
	}

	

}
