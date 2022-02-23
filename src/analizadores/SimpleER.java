package analizadores;

public class SimpleER {

	Nodo_Simple_ER primero;
	String name;
	public Integer hojas=0;

	public SimpleER() {
		this.primero = null;
	}

	public void insert(String info, String tipo,Boolean hoja) {
		Nodo_Simple_ER new_node = new Nodo_Simple_ER(info, tipo,hoja);
		if(hoja) {
			SetHojas();
		}
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
			actual.next.previous = actual;
		}
	}

	public void showList() {
		if (isNone() == false) {
			Nodo_Simple_ER actual = this.primero;
			System.out.println(this.name);
			while (actual != null) {
			
					System.out.println(actual.info + " - " + actual.tipo + " HOJA: " + actual.hoja );
	
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
		//this.insert('$', "Finalizacion", true);
		//this.insert('.', "OP", false);
		//Nodo_Simple_ER OpAnterior = null;
		//Nodo_Simple_ER actual = this.primero;
		//Boolean validdacionGeneral = true;
		
		Nodo_Simple_ER aux1 = null;
		Nodo_Simple_ER Op = null;
		Nodo_Simple_ER padre = null;
		Nodo_Simple_ER hijo = null;
		
		Integer hijos = 0;
		Integer idActual = 1;
		Integer AuxContado = 0;
		
		Boolean validdacionOp = true;
		Boolean validdacionHijo2 = true;
		
		Op = this.primero;

	

		while (true) {
			if(Op.next == null ) {
				break;
			}
			while (validdacionOp) {

				if (Op.tipo.equals("OP") && Op.IDPadre == 0) {
					hijos = Operator(Op.info);
					validdacionOp = false;
					Op.IDPadre = idActual;
					padre = Op;
					//System.out.println(Op.tipo + " = " + Op.info + " padre: " + Op.IDPadre );
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
						
						if(AuxContado==1) {
							Op.hijo1 = aux1;
						}else if (AuxContado ==2 ) {
							Op.hijo2 = aux1;
						}
						
						AuxContado++;
						//System.out.println(aux1.tipo + " = " + aux1.info + " hijo: " + aux1.IDHijo + " hijo de: " );
					}
					
					aux1 = aux1.previous;
				} else {
					validdacionHijo2 = false;
					continue;
				}
			}
			if( Op.hijo2 != null) {
				System.out.println(Op.info + " Padre de: 1:" + Op.hijo1.info + " 2: " + Op.hijo2.info );
			}else {
				System.out.println(Op.info + " Padre de: 1:" + Op.hijo1.info );
			}
			
			validdacionOp = true;
			validdacionHijo2 = true;
			idActual += 1;
		}
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
	/*
	public void estado_inicial() {
		if (isNone() == false) {
			Nodo_Simple_ER actual = this.primero;
			System.out.println(this.name);
			while (actual != null) {
			
				actual.IDPadre=0;
				actual.IDHijo=0;
				actual.hijo1 =null;
				actual.hijo2 =null;
	
				actual = actual.next;
			}
		}
	}*/
	
	public void SetHojas() {
		this.hojas ++;
	}

}

class Nodo_Simple_ER {

	Nodo_Simple_ER next, previous;
	String info;
	String tipo;
	Boolean hoja;
	
	//nodos hijos y padres
	Nodo_Simple_ER hijo1,hijo2;
	
	//correlativos
	Integer IDPadre = 0;
	Integer IDHijo = 0;
	


	public Nodo_Simple_ER(String info, String tipo,Boolean hoja) {
		this.next =null;
		this.previous = null;
		this.info =  info;
		this.tipo = tipo;
		this.hoja=hoja;
		
		//this.Padre =null;
		this.hijo1 =null;
		this.hijo2 =null;
		
		this.IDHijo = 0;
		this.IDPadre = 0;
		
		
	}
}
