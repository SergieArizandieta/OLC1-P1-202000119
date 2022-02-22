package analizadores;


public class SimpleER {

	Nodo_Simple_ER primero;
	String name;
	public SimpleER() {
		this.primero = null;
	}

	public void insert(Object info,String tipo) {
		Nodo_Simple_ER new_node = new Nodo_Simple_ER(info,tipo);
		if (isNone()) {
			this.primero = new_node;
		} else {
			Nodo_Simple_ER actual = this.primero;

			while (actual.next != null) {

				actual = actual.next;
			}

			actual.next = new_node;
	}
		}

	public void showList() {
		if (isNone() == false) {
			Nodo_Simple_ER actual = this.primero;
			System.out.println(this.name);
			while (actual != null) {
				System.out.println(actual.info + " - " + actual.tipo);
				actual = actual.next;
			}
		}
	}

	public void Search(Object data) {
		if (isNone() == false) {
			Nodo_Simple_ER actual = this.primero;
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

	public void Delete(Object data) {
		if (isNone() == false) {
			Nodo_Simple_ER actual = this.primero;
			Nodo_Simple_ER anterior = null;

			while (actual != null && actual.info != data) {
				anterior = actual;
				actual = actual.next;
			}

			if (anterior == null) {
				this.primero = actual.next;
				System.out.println("Se elimino el dato: " + data);
			} else if (actual != null) {
				anterior.next = actual.next;
				actual.next = null;
				System.out.println("Se elimino el dato: " + data);
			} else {
				System.out.println("No se encontro el dato a eliminar: " + data);
			}
		}
	}

	public Boolean isNone() {
		return this.primero == null;
	}
}

class Nodo_Simple_ER {

	Nodo_Simple_ER next;
	Object info;
	String tipo;

	public Nodo_Simple_ER(Object info,String tipo) {
		this.next = null;
		this.info = info;
		this.tipo = tipo;
	}
}
