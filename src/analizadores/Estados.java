package analizadores;

import java.util.ArrayList;
import java.util.List;


public class Estados {
	SimpleCalcSiguientes Calcsiguientes = new SimpleCalcSiguientes();
	
	Integer Estado;
	Boolean Aceptacion;
	List<Integer> Siguientes = new ArrayList<>();
	SimpleSiguientesTransiciones listado;

	
	public Estados(Integer Estado , Boolean Aceptacion, List<Integer> Siguientes ) {
		this.Estado =Estado;
		this.Aceptacion =Aceptacion;
		this.Siguientes =Siguientes;
		this.listado = null;
		this.Calcsiguientes = null;
	}

	public void show() {
		String data = "";
		data += "\nEstado:" + Estado;
		data += "\nAceptacion:" + Aceptacion;
		data += "\nSiguientes:" + Siguientes;
		System.out.println(data);
	}
	
	public void shoInitial() {
		String data = "";
		data += "\nEstado:" + Estado;
		data += "\nAceptacion:" + Aceptacion;
		data += "\nSiguientes:" + Siguientes;
		data += "\nTransiciones:"; 
		
		System.out.println(data);
		Calcsiguientes.showList();;
	}
	
	public void insertTransiciones(SimpleCalcSiguientes siguientes) {
		this.Calcsiguientes = siguientes;
	}
}
