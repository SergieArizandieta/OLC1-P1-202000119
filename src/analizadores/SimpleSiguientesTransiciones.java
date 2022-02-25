package analizadores;



public class SimpleSiguientesTransiciones {

	Nodo_SimpleSiguientesTransiciones primero;
	SimpleCalcSiguientes Calcsiguientes = new SimpleCalcSiguientes();
	
	public SimpleSiguientesTransiciones(SimpleCalcSiguientes siguientes ) {
		this.primero = null;
		this.Calcsiguientes = siguientes;
	}

	public void insert(Estados nuevoEstado) {
		Nodo_SimpleSiguientesTransiciones new_node = new Nodo_SimpleSiguientesTransiciones(nuevoEstado);
		if (isNone()) {
			this.primero = new_node;
		} else {
			Nodo_SimpleSiguientesTransiciones actual = this.primero;
			while (actual.next != null) {
				actual = actual.next;
			}
			actual.next = new_node;
		}
	}

	public void showList() {
		if (isNone() == false) {
			Nodo_SimpleSiguientesTransiciones actual = this.primero;
			while (actual != null) {
				System.out.println(actual.data.Estado + " : " + actual.data.Siguientes);
				/*for (Integer i : actual.siguientes) {
					System.out.println(i);
				}*/
				actual = actual.next;
			}
		}
	}

	public void Search(Estados data) {
		if (isNone() == false) {
			Nodo_SimpleSiguientesTransiciones actual = this.primero;
			while (actual != null && actual.data != data) {
				actual = actual.next;
				if (actual == null) {
					System.out.println("No se encontro el dato: " + data);
					break;
				}
			}
			if (actual != null && actual.data  == data) {
				System.out.println("Dato encontrado: " + data);
			}
		}
	}
	
	public Boolean isNone() {
		
		return this.primero == null;
	}
}

class Nodo_SimpleSiguientesTransiciones{
	Nodo_SimpleSiguientesTransiciones next;
	
	Estados data;
	
	public Nodo_SimpleSiguientesTransiciones(Estados Estado) {
		this.next = null;
		this.data = Estado;

	}
}
