package analizadores;

import java.util.ArrayList;
import java.util.List;

public class SimpleCalcSiguientes {

	Nodo_Simple primero;

	public SimpleCalcSiguientes() {
		this.primero = null;
	}

	public void insert(String info,String tipo,Integer Hoja) {
		Nodo_Simple new_node = new Nodo_Simple(info,tipo,Hoja);
		if (isNone()) {
			this.primero = new_node;
		} else {
			Nodo_Simple actual = this.primero;
			while (actual.next != null) {
				actual = actual.next;
			}
			actual.next = new_node;
		}
	}

	public void showList() {
		if (isNone() == false) {
			Nodo_Simple actual = this.primero;
			while (actual != null) {
				System.out.println(actual.info + " Hoja: " + actual.Hoja + " tipo: " + actual.tipo);
				for (Integer i : actual.siguientes) {
					System.out.println(i);
				}
				actual = actual.next;
			}
		}
	}

	public void Search(Object data) {
		if (isNone() == false) {
			Nodo_Simple actual = this.primero;
			while (actual != null && actual.info != data) {
				actual = actual.next;
				if (actual == null) {
					System.out.println("No se encontro el dato: " + data);
					break;
				}
			}
			if (actual != null && actual.info == data) {
				System.out.println("Dato encontrado: " + data);
			}
		}
	}
	
	
	public void InsertarSIguiente(Integer HojaBusqueda,Integer siguiente) {
		if (isNone() == false) {
			Nodo_Simple actual = this.primero;
			while (actual != null && actual.Hoja != HojaBusqueda) {
				actual = actual.next;
				if (actual == null) {
					System.out.println("No se encontro el dato: " + HojaBusqueda);
					break;
				}
			}
			if (actual != null && actual.Hoja == HojaBusqueda) {
				actual.siguientes.add(siguiente);
				System.out.println("Dato encontrado: " + HojaBusqueda);
			}
		}
	}



	public Boolean isNone() {
		
		return this.primero == null;
	}
}

class Nodo_Simple {

	Nodo_Simple next;
	
	Integer estado;
	
	
	String info;
	String tipo;
	Integer Hoja;
	List<Integer> siguientes = new ArrayList<>();
	

	public Nodo_Simple(String info,String tipo,Integer Hoja) {
		this.next = null;
		this.info = info;
		this.tipo = tipo;
		this.Hoja = Hoja;
	}
}
