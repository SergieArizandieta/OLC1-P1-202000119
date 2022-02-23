package analizadores;

public class SimpleER {

	Nodo_Simple_ER primero;
	String name;

	public SimpleER() {
		this.primero = null;
	}

	public void insert(Object info, String tipo) {
		Nodo_Simple_ER new_node = new Nodo_Simple_ER(info, tipo);
		if (isNone()) {
			this.primero = new_node;
		} else {
			Nodo_Simple_ER actual = this.primero;
			Nodo_Simple_ER anterior = null;

			while (actual.next != null) {
				anterior = actual;
				actual = actual.next;
				actual.previous = anterior;
				
			}

			actual.next = new_node;
		}
	}

	public void showList() {
		if (isNone() == false) {
			Nodo_Simple_ER actual = this.primero;
			System.out.println(this.name);
			while (actual != null) {
			
					System.out.println(actual.info + " - " + actual.tipo + " id: " + actual.id );
	
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

	public void GestionArbol() {
		Nodo_Simple_ER aux1 = null;
		//Nodo_Simple_ER OpAnterior = null;
		Nodo_Simple_ER Op = null;
		//Nodo_Simple_ER actual = this.primero;
		Integer hijos = 0;
		Boolean validdacionOp = true;
		//Boolean validdacionGeneral = true;
		Boolean validdacionHijo2 = true;
		Op = this.primero;

		Integer idActual = 1;
		Integer AuxContado = 0;

		while (Op!= null) {

			while (validdacionOp) {

				if (Op.tipo.equals("OP") && Op.IDPadre == 0) {
					hijos = Operator(Op.info);
					validdacionOp = false;
					Op.IDPadre = idActual;
					System.out.println(Op.tipo + " = " + Op.info + " padre: " + Op.IDPadre);
					continue;
				}
				Op = Op.next;
			}

			aux1 = Op.previous;
			AuxContado = 1;
			while (validdacionHijo2) {
				if (AuxContado <= hijos) {
					if (aux1.IDHijo == 0) {
						aux1.IDHijo = idActual;
						AuxContado++;
						System.out.println(aux1.tipo + " = " + aux1.info + " hijo: " + aux1.IDHijo);
					}
					aux1 = aux1.previous;
				} else {
					validdacionHijo2 = false;
					continue;
				}
			}
			
			validdacionOp = true;
			validdacionHijo2 = true;
			idActual += 1;
		}
	}

	public void SearchArbolData(Integer hijos,Integer id) {
		
		//sad
	}

	public int Operator(String text) {
		if (text.equals("|") || text.equals(".")) {
			return 2;
		} else if (text.equals("*") || text.equals("+") || text.equals("?")) {
			return 1;
		}
		System.out.println("Error");
		return -1;

	}

	public Boolean isNone() {
		return this.primero == null;
	}

	public void initialize() {
		Integer countador = 1;
		if (isNone() == false) {
			Nodo_Simple_ER actual = this.primero;
			System.out.println(this.name);
			while (actual != null) {
				actual.id = countador;
				actual = actual.next;
				countador++;
			}
		}
	}
}

class Nodo_Simple_ER {

	Nodo_Simple_ER next, previous;
	String info;
	String tipo;
	Integer IDPadre = 0;
	Integer IDHijo = 0;
	Integer id = 0;

	public Nodo_Simple_ER(Object info, String tipo) {
		this.next =null;
		this.previous = null;
		this.info = (String) info;
		this.tipo = tipo;
		this.IDHijo = 0;
	}
}
