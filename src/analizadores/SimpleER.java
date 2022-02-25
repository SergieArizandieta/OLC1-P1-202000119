package analizadores;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SimpleER {
	Estados Estado_Inicial;
	Nodo_Simple_ER primero, ultimo;
	public String name;
	SimpleCalcSiguientes siguientes = new SimpleCalcSiguientes();
	public Integer hojas = 0;

	public SimpleER() {
		this.primero = null;
		this.ultimo = null;
	}

	public void insert(String info, String tipo, Boolean hoja) {
		Nodo_Simple_ER new_node = new Nodo_Simple_ER(info, tipo, hoja);

		if (isNone()) {
			this.primero = new_node;
			this.ultimo = this.primero;
			if (hoja) {
				SetHojas();
				new_node.noHoja = this.hojas;
				new_node.Anulable = false;
			}
		} else {
			this.ultimo = new_node;
			Nodo_Simple_ER actual = this.primero;
			Nodo_Simple_ER anterior = null;
			if (hoja) {
				SetHojas();
				new_node.noHoja = this.hojas;
				new_node.Anulable = false;
			}

			while (actual.next != null) {
				anterior = actual;
				actual = actual.next;
				actual.previous = anterior;

			}

			actual.next = new_node;
			actual.next.previous = actual;
		}
	}

	public void insertHead(String info, String tipo, Boolean hoja) {
		Nodo_Simple_ER new_node = new Nodo_Simple_ER(info, tipo, hoja);
		if (hoja) {
			SetHojas();
			new_node.noHoja = this.hojas;
			new_node.Anulable = false;
		}
		new_node.next = this.primero;
		this.primero = new_node;

	}

	public void showList() {
		System.out.println("======Show list ======");
		if (isNone() == false) {
			Nodo_Simple_ER actual = this.primero;
			System.out.println(this.name);
			while (actual != null) {
				if (actual.hoja) {
					System.out.println(actual.info + " - " + actual.tipo + " hoja " + actual.noHoja);
				} else {
					System.out.println(actual.info + " - " + actual.tipo);
				}

				actual = actual.next;
			}
		}
	}

	public void showListInverse() {
		System.out.println("======Show list Inverse ======");
		if (isNoneLast() == false) {
			Nodo_Simple_ER actual = this.ultimo;

			while (actual != null) {
				if (actual.hoja) {
					System.out.println(actual.info + " - " + actual.tipo + " hoja " + actual.noHoja);
				} else {
					System.out.println(actual.info + " - " + actual.tipo);
				}

				actual = actual.previous;
			}
		}
	}

	public void GestionArbol() {
		this.insertHead("$", "Finalizacion", true);
		this.insert(".", "OP", false);
		// Nodo_Simple_ER OpAnterior = null;
		// Nodo_Simple_ER actual = this.primero;
		// Boolean validdacionGeneral = true;
		// Nodo_Simple_ER padre = null;
		// Nodo_Simple_ER hijo = null;

		Nodo_Simple_ER aux1 = null;
		Nodo_Simple_ER Op = null;

		Integer hijos = 0;
		Integer idActual = 1;
		Integer AuxContado = 0;

		Boolean validdacionOp = true;
		Boolean validdacionHijo2 = true;

		Op = this.primero;

		while (true) {
			if (Op.next == null) {
				break;
			}
			while (validdacionOp) {

				if (Op.tipo.equals("OP") && Op.IDPadre == 0) {
					hijos = Operator(Op.info);
					validdacionOp = false;
					Op.IDPadre = idActual;

					// System.out.println(Op.tipo + " = " + Op.info + " padre: " + Op.IDPadre );
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

						if (AuxContado == 1) {
							Op.hijo1 = aux1;
						} else if (AuxContado == 2) {
							Op.hijo2 = aux1;
							Op.hijo1 = Op.hijo1;
						}

						AuxContado++;
						// System.out.println(aux1.tipo + " = " + aux1.info + " hijo: " + aux1.IDHijo +
						// " hijo de: " );
					}

					aux1 = aux1.previous;
				} else {
					validdacionHijo2 = false;
					continue;
				}
			}
			if (Op.hijo2 != null) {
				System.out.println(Op.info + " Padre de: 1:" + Op.hijo1.info + " 2: " + Op.hijo2.info);
			} else {
				System.out.println(Op.info + " Padre de: 1:" + Op.hijo1.info);
			}

			validdacionOp = true;
			validdacionHijo2 = true;
			idActual += 1;
		}

		AsignarAnulables();
		verNoAnulables();
		verAnulables();
	}

	public void AsignarAnulables() {
		System.out.println("====== Asignando Anulables ======");
		if (isNone() == false) {
			Nodo_Simple_ER actual = this.primero;

			while (actual != null) {
				if (actual.tipo.equals("OP")) {

					if (actual.info.equals("|")) {
						if (actual.hijo1.Anulable || actual.hijo2.Anulable) {
							actual.Anulable = true;
						} else {
							actual.Anulable = false;
						}
					}

					if (actual.info.equals(".")) {
						if (actual.hijo1.Anulable && actual.hijo2.Anulable) {
							actual.Anulable = true;
						} else {
							actual.Anulable = false;
						}
					}

					if (actual.info.equals("*") || actual.info.equals("?")) {
						actual.Anulable = true;
					}

					if (actual.info.equals("+")) {
						if (actual.hijo1.Anulable) {
							actual.Anulable = true;
						} else {
							actual.Anulable = false;
						}
					}
				}
				actual = actual.next;
			}
		}
		// probando();
		RedefiniendoHojas();
		Primero_Ultimos_Hojas();
		InsertarPrimeros();

	}

	public void RedefiniendoHojas() {
		System.out.println("________________________________________");
		
		

		System.out.println("======Show list Inverse ======");
		
		if (isNone() == false) {
			Nodo_Simple_ER actual = this.primero;

			while (actual != null) {
				if (actual.hoja) {
					System.out.println( this.hojas + " : " + actual.info);
					actual.noHoja = this.hojas;
					this.hojas--;
				} 

				actual = actual.next;
			}
		}
		System.out.println("________________________________________");
	}

	public void probando() {
		List<Integer> asd = new ArrayList<>();
		for (int i = 0; i < 10; i++) {
			asd.add(i);
		}
		asd.add(1);
		asd.add(2);
		asd.add(3);
		asd.add(4);

		for (Integer integer : asd) {
			System.out.println("valor: " + integer);
		}

		asd = asd.stream().distinct().collect(Collectors.toList());
		asd.forEach(System.out::println);

	}

	public void Primero_Ultimos_Hojas() {
		if (isNone() == false) {
			Nodo_Simple_ER actual = this.primero;

			while (actual != null) {
				if (actual.hoja) {
					this.siguientes.insert(actual.info, actual.tipo, actual.noHoja);
					actual.primeros.add(actual.noHoja);
					actual.ultimos.add(actual.noHoja);
				}

				actual = actual.next;
			}
			// this.siguientes.showList();
		}
	}

	public void InsertarPrimeros() {
		System.out.println("====== Asignando Primeros ======");
		if (isNone() == false) {
			Nodo_Simple_ER actual = this.primero;

			while (actual != null) {
				if (actual.tipo.equals("OP")) {

					if (actual.info.equals("|")) {
						for (Integer i : actual.hijo1.primeros) {
							actual.primeros.add(i);
						}
						for (Integer i : actual.hijo2.primeros) {
							actual.primeros.add(i);
						}
					}

					if (actual.info.equals(".")) {
						if (actual.hijo1.Anulable) {
							for (Integer i : actual.hijo1.primeros) {
								actual.primeros.add(i);
							}
							for (Integer i : actual.hijo2.primeros) {
								actual.primeros.add(i);
							}
						} else {
							for (Integer i : actual.hijo1.primeros) {
								actual.primeros.add(i);
							}
						}
					}

					if (actual.info.equals("*") || actual.info.equals("?") || actual.info.equals("+")) {
						for (Integer i : actual.hijo1.primeros) {
							actual.primeros.add(i);
						}
					}
					QuitarDupicados(actual.primeros);
				}
				actual = actual.next;
			}
		}
		showListPrimeros();
		InsertarUltimos();
	}

	public void InsertarUltimos() {
		System.out.println("====== Asignando Ultimos ======");
		if (isNone() == false) {
			Nodo_Simple_ER actual = this.primero;

			while (actual != null) {
				if (actual.tipo.equals("OP")) {

					if (actual.info.equals("|")) {
						for (Integer i : actual.hijo1.ultimos) {
							actual.ultimos.add(i);
						}
						for (Integer i : actual.hijo2.ultimos) {
							actual.ultimos.add(i);
						}
					}

					if (actual.info.equals(".")) {
						if (actual.hijo2.Anulable) {
							for (Integer i : actual.hijo1.ultimos) {
								actual.ultimos.add(i);
							}
							for (Integer i : actual.hijo2.ultimos) {
								actual.ultimos.add(i);
							}
						} else {
							for (Integer i : actual.hijo2.ultimos) {
								actual.ultimos.add(i);
							}
						}
					}

					if (actual.info.equals("*") || actual.info.equals("?") || actual.info.equals("+")) {
						for (Integer i : actual.hijo1.ultimos) {
							actual.ultimos.add(i);
						}
					}
					QuitarDupicados(actual.primeros);
				}
				actual = actual.next;
			}
		}
		showListUltimos();
		InsertarSiguientes();
	}

	public void InsertarSiguientes() {
		System.out.println("====== Asignando Siguientes ======");
		if (isNone() == false) {
			Nodo_Simple_ER actual = this.primero;

			while (actual != null) {
				if (actual.tipo.equals("OP")) {

					if (actual.info.equals(".")) {
						for (Integer primeroHijo2 : actual.hijo2.primeros) {
							for (Integer UltimosHijo1 : actual.hijo1.ultimos) {
								this.siguientes.InsertarSIguiente(UltimosHijo1, primeroHijo2);
							}
						}
					}
					if (actual.info.equals("*") || actual.info.equals("+")) {
						for (Integer ultimoH : actual.ultimos) {
							for (Integer primeroH : actual.ultimos) {
								this.siguientes.InsertarSIguiente(primeroH, ultimoH);
							}
						}
					}
				}
				actual = actual.next;
			}
		}
		// this.siguientes.InsertarSIguiente(6, -1);
		// showListUltimos();
		this.siguientes.showList();
		TablaTransiciones();
	}

	public void TablaTransiciones() {
		System.out.println("====== Tabla de transiciones ======");
		System.out.println(this.ultimo.primeros);

		Estado_Inicial = new Estados(0, false, this.ultimo.primeros, this.siguientes, this.name);
		Estado_Inicial.Inciando_tabla_transiciones(this.siguientes);
		// Estado_Inicial.show();
		/*
		 * List<Integer> asdsda = new ArrayList<>(); asdsda.add(1); asdsda.add(2);
		 * asdsda.add(3); asdsda.add(4); asdsda.add(6); System.out.println(asdsda);
		 * if(asdsda.equals(this.ultimo.primeros) ) { System.out.println("Son iguales");
		 * }else { System.out.println("son distintos"); }
		 */

	}

	public void showListUltimos() {
		System.out.println("======Show list ultimos ======");
		if (isNone() == false) {
			Nodo_Simple_ER actual = this.primero;

			while (actual != null) {
				System.out.println("====== " + actual.info + " ====== ");
				actual.ultimos.forEach(System.out::println);

				actual = actual.next;
			}
		}
	}

	public void showListPrimeros() {
		System.out.println("======Show list primeros ======");
		if (isNone() == false) {
			Nodo_Simple_ER actual = this.primero;

			while (actual != null) {
				System.out.println("====== " + actual.info + " ====== ");
				actual.primeros.forEach(System.out::println);

				actual = actual.next;
			}
		}
	}

	public void QuitarDupicados(List<Integer> lista) {
		lista = lista.stream().distinct().collect(Collectors.toList());
	}

	public void verNoAnulables() {
		if (isNone() == false) {
			Nodo_Simple_ER actual = this.primero;

			System.out.println("=======No anulables ========");
			while (actual != null) {
				if (actual.Anulable != null) {
					if (actual.Anulable == false) {
						System.out.println(actual.info + " - " + actual.tipo);
					}
				}

				actual = actual.next;
			}
		}
	}

	public void verAnulables() {
		if (isNone() == false) {
			Nodo_Simple_ER actual = this.primero;

			System.out.println("======= anulables ========");
			while (actual != null) {
				if (actual.Anulable != null) {
					if (actual.Anulable) {
						System.out.println(actual.info + " - " + actual.tipo);
					}
				}

				actual = actual.next;
			}
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

	public Boolean isNoneLast() {
		return this.ultimo == null;
	}

	public void SetHojas() {
		this.hojas++;
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

}

class Nodo_Simple_ER {

	Nodo_Simple_ER next, previous;
	// nodos hijos y padres
	Nodo_Simple_ER hijo1, hijo2;

	// ===ARBOLL======
	// primeros
	Integer noHoja;
	List<Integer> primeros = new ArrayList<>();
	List<Integer> ultimos = new ArrayList<>();
	Boolean Anulable;

	// validacion
	Boolean hoja;

	// info
	String info;
	String tipo;

	// correlativos
	Integer IDPadre = 0;
	Integer IDHijo = 0;

	public Nodo_Simple_ER(String info, String tipo, Boolean hoja) {
		this.next = null;
		this.previous = null;
		this.info = info;
		this.tipo = tipo;
		this.hoja = hoja;

		// this.Padre =null;
		this.hijo1 = null;
		this.hijo2 = null;

		this.IDHijo = 0;
		this.IDPadre = 0;

	}
}
