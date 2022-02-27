package analizadores;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import analizadores.Estados;

public class SimpleSiguientesTransiciones {
	// Integer RegulacionEstado;

	List<Integer> EstadosAceptacion ;
	
	Integer RegulacionNumeroacion = 0;
	Nodo_SimpleSiguientesTransiciones primero, ultimo;
	SimpleCalcSiguientes Calcsiguientes = new SimpleCalcSiguientes();

	public SimpleSiguientesTransiciones(SimpleCalcSiguientes siguientes) {
		this.primero = null;
		this.primero = null;
		this.Calcsiguientes = siguientes;
		// this.RegulacionEstado = RegulacionEstado;
	}

	public void insert(Valor_Tipo valor_tipo, List<Integer> siguinetes, SimpleCalcSiguientes siguientes) {

		RegulacionNumeroacion++;
		Nodo_SimpleSiguientesTransiciones new_node = new Nodo_SimpleSiguientesTransiciones(valor_tipo, siguinetes,
				siguientes);
		new_node.Numeracion = RegulacionNumeroacion;
		new_node.DatosAceptados.add(new_node.data);
		if (isNone()) {

			this.primero = new_node;
			this.ultimo = this.primero;
		} else {

			Nodo_SimpleSiguientesTransiciones actual = this.primero;

			if (BuscarRepetido(new_node)) {
				this.ultimo = new_node;
				while (actual.next != null) {
					actual = actual.next;
				}
				actual.next = new_node;
				actual.next.previous = actual;
			}

		}
	}

	public boolean BuscarRepetido(Nodo_SimpleSiguientesTransiciones actualagregar) {

		Nodo_SimpleSiguientesTransiciones actual = this.primero;
		while (actual != null && actual.data.valor != actualagregar.data.valor) {
			if (actual.data.valor.equals(actualagregar.data.valor)) {
				break;
			} else {
				actual = actual.next;
				if (actual == null) {
					return true;
				}
			}

		}
		if (actual != null && actual.data.valor.equals(actualagregar.data.valor)) {
			for (Integer i : actualagregar.primeros) {
				actual.primeros.add(i);
			}

			actual.primeros = QuitarDupicados(actual.primeros);
			return false;
		}
		return true;

	}

	public void AgregarDatos_Aceptados() {
		// System.out.println("====== agregar aceptados ======");

		if (isNoneLast() == false) {
			Nodo_SimpleSiguientesTransiciones actualReverse = this.ultimo;
			if (isNone() == false) {
				while (actualReverse != null) {
					// ======================================
					Nodo_SimpleSiguientesTransiciones actual = this.primero;
					while (actual != null && actual.primeros != actualReverse.primeros) {

						if (actual.Numeracion > actualReverse.Numeracion) {

							if (actual.hashCode() != actualReverse.hashCode()) {
								if (actual.primeros.equals(actualReverse.primeros)) {
									break;
								} else {
									actual = actual.next;
									if (actual == null) {
										break;
									}
								}

							} else {
								actual = actual.next;
							}
						} else {
							break;
						}
					}
					if (actual != null && actual.primeros.equals(actualReverse.primeros)) {
						if (actual.hashCode() != actualReverse.hashCode()) {
							for (Valor_Tipo i : actualReverse.DatosAceptados) {
								actual.DatosAceptados.add(i);
							}
							Delete(actualReverse);
							actual.DatosAceptados = QuitarDupicadosAceptacion(actual.DatosAceptados);
						}
					}

					// =========================================
					actualReverse = actualReverse.previous;

				}
			}
		}
		verificadionEstadosRepetidos();
		agregarValorEstado();
	}

	public void verificadionEstadosRepetidos() {
		if (isNone() == false) {

			if (Estados.encabezadoEstado == this.primero) {
				System.out.println("cabezera");
			} else {

				Nodo_SimpleSiguientesTransiciones actual = this.primero;
				while (actual != null) {
					verificacionDesdeEncabezado(actual.primeros, actual);
					actual = actual.next;
				}
			}
		}
	}

	public void verificacionDesdeEncabezado(List<Integer> primeros,
			Nodo_SimpleSiguientesTransiciones ActualVerificando) {
		// System.out.println("_____________________PROBANDO_____________________");
		if (isNone() == false) {

			Nodo_SimpleSiguientesTransiciones actual = Estados.encabezadoEstado;
			while (actual != null) {
				if (actual != ActualVerificando && actual.EstadoRepetido == false) {
					// System.out.println(actual.Estado + " datos de aceptacion: " +
					// actual.primeros);

					if (primeros.equals(actual.primeros)) {
						if(actual.Aceptacion) {
							ActualVerificando.Aceptacion = actual.Aceptacion;
						}
						ActualVerificando.EstadoRepetido = true;
						ActualVerificando.EstadoDestino = actual.Estado;
					}

					verificacionDesdeOtraparte(primeros, ActualVerificando, actual);
				}
				actual = actual.next;
			}
		}

		// System.out.println("_____________________FIN PROBANDO_____________________");
	}

	public void verificacionDesdeOtraparte(List<Integer> primeros, Nodo_SimpleSiguientesTransiciones ActualVerificando,
			Nodo_SimpleSiguientesTransiciones actualAnterior) {
		// System.out.println("_____________________PROBANDO_____________________");
		if (isNone() == false) {

			Nodo_SimpleSiguientesTransiciones actual = actualAnterior.listado.primero;

			while (actual != null) {
				if (actual != ActualVerificando && actual.EstadoRepetido == false) {
					// System.out.println(actual.Estado + " datos de aceptacion: " +
					// actual.primeros);
					if(actual.Aceptacion) {
						ActualVerificando.Aceptacion = actual.Aceptacion;
					}
					if (primeros.equals(actual.primeros)) {
						ActualVerificando.EstadoRepetido = true;
						ActualVerificando.EstadoDestino = actual.Estado;
					}
					verificacionDesdeOtraparte(primeros, ActualVerificando, actual);

				}
				actual = actual.next;
			}
		}
		// System.out.println("_____________________FIN PROBANDO_____________________");
	}

	public void agregarValorEstado() {
		if (isNone() == false) {
			Nodo_SimpleSiguientesTransiciones actual = this.primero;
			while (actual != null) {
				if (actual.EstadoRepetido == false) {
					Estados.estadosGestion++;

					actual.Estado = Estados.estadosGestion;
				}
				actual = actual.next;

			}
		}
		if (Estados.encabezadoEstado == this.primero) {
			tabla_transiciones_EstadosNuevos();
		} else {
			/*
			 * Nodo_SimpleSiguientesTransiciones actual = this.primero;
			 * System.out.println("________________________LLEGAN________________________");
			 * System.out.println(actual.Estado + " datos de aceptacion: " + actual.primeros
			 * + " DESTINO " + actual.EstadoDestino + " finalizacion: " + actual.Aceptacion
			 * + " REPETIDO " + actual.EstadoRepetido ); for (Valor_Tipo i :
			 * actual.DatosAceptados) { System.out.println(i.valor); }
			 * System.out.println("______SE VA ________________________");
			 */
			tabla_transiciones_EstadosNuevos();
		}
	}

	public void tabla_transiciones_EstadosNuevos() {

		System.out.println("==== Tabla de transiciones para nuevos estados ====");
		String Tipo, Valor;
		Valor_Tipo valor_tipo;
		List<Integer> Primeros;

		if (isNone() == false) {
			Nodo_SimpleSiguientesTransiciones actual = this.primero;
			while (actual != null) {
				if (actual.EstadoRepetido == false) {

					if (actual.Verificado == false) {
						actual.Verificado = true;
						for (Integer i : actual.primeros) {
							Tipo = this.Calcsiguientes.SerachTipo(i);
							if (Tipo.equals("Finalizacion")) {
								actual.Aceptacion = true;
							} else {
								Valor = this.Calcsiguientes.SerachInfo(i);
								valor_tipo = new Valor_Tipo(Valor, Tipo);
								Primeros = this.Calcsiguientes.SerachPrimeros(i);
								actual.listado.insert(valor_tipo, Primeros, this.Calcsiguientes);
							}
						}
						if (this.Calcsiguientes.SerachTipo(actual.primeros.get(0)) == "Finalizacion") {

							actual.Aceptacion = true;
						}
						actual.listado.AgregarDatos_Aceptados();

						actual.listado.showList();
						if (actual.Estado == 5) {
							System.out.println("Aqui es");
						}
						System.out.println(actual.Estado);
					}

				}
				actual = actual.next;
			}
		}
	}

	public void AgregarAceptacionAvReporte() {
		Integer contador=1;
		
		for (Integer integer : EstadosAceptacion) {
			Estados.dot.append(integer);
			if(contador< EstadosAceptacion.size()) {
				Estados.dot.append(",");
			}else {
				Estados.dot.append(";");
			}
			
			contador++;
		}
		Estados.dot.append("node [shape = circle, color = \"#2CB5FF\" fillcolor=\"#E3FFFA\" style =filled];\n\n");
		verReporte();
	}
	
	public void verReporte() {
		System.out.println(Estados.dot);
	
	}
	
	public void verArbolMain(Boolean Aceptacion,String Name) {
		Estados.dot = new StringBuilder();
		Estados.dot.append("digraph finite_state_machine {\n");
		Estados.dot.append("bgcolor = \"#F6FFE3\"\n");
		Estados.dot.append("	node [fontname=\"Helvetica,Arial,sans-serif\" ]\n");
		Estados.dot.append("	edge [fontname=\"Helvetica,Arial,sans-serif\"]\n");
		Estados.dot.append("	rankdir=LR;\n");
		Estados.dot.append("	node [shape = doublecircle, color = gold fillcolor=\"#EBE3FF\" style =filled];");		
		
		EstadosAceptacion = new ArrayList<>();
		
		if (Aceptacion) {
			System.out.println("S0* a:");
			EstadosAceptacion.add(0);
		} else {
			System.out.println("S0 a:");
		}
		
		if (isNone() == false) {
			Nodo_SimpleSiguientesTransiciones actual = this.primero;
			while (actual != null) {

				if (actual.Aceptacion) {
					System.out.print("\tS" + actual.Estado + "* con ");
					EstadosAceptacion.add( actual.Estado);
					EstadosAceptacion = QuitarDupicados(EstadosAceptacion);
				} else {
					System.out.print("\tS" + actual.Estado + " con ");
				}
				for (Valor_Tipo i : actual.DatosAceptados) {
					System.out.print(i.valor + ",");
				}
				System.out.println("");
				actual = actual.next;
			}
		}
		ShowArbol(this.primero);
	}

	public void ShowArbol(Nodo_SimpleSiguientesTransiciones cabezera) {
		if (cabezera != null) {
			Nodo_SimpleSiguientesTransiciones actual = cabezera;
			while (actual != null) {

				if (actual.Aceptacion) {
					System.out.println("S" + actual.Estado + "* a:");
					EstadosAceptacion.add( actual.Estado);
					EstadosAceptacion = QuitarDupicados(EstadosAceptacion);
				} else {
					System.out.println("S" + actual.Estado + " a:");
				}
				if (actual.Estado == 5) {
					System.out.print("");
				}

				System.out.println("");

				ShowAceptaciones(actual.listado.primero);
				actual = actual.next;
			}
		}
	}

	public void ShowAceptaciones(Nodo_SimpleSiguientesTransiciones cabezera) {

		if (cabezera != null) {
			Nodo_SimpleSiguientesTransiciones actual = cabezera;
			while (actual != null) {

				if (actual.EstadoRepetido == false) {

					if (actual.Aceptacion) {
						System.out.print("\tS" + actual.Estado + "* con ");
						EstadosAceptacion.add( actual.Estado);
						EstadosAceptacion = QuitarDupicados(EstadosAceptacion);
					} else {
						System.out.print("\tS" + actual.Estado + " con ");
					}

				} else {

					if (actual.Aceptacion) {
						System.out.print("\tS" + actual.EstadoDestino + "* con ");
						EstadosAceptacion.add( actual.EstadoDestino);
						EstadosAceptacion = QuitarDupicados(EstadosAceptacion);
					} else {
						System.out.print("\tS" + actual.EstadoDestino + " con ");
					}

				}

				for (Valor_Tipo i : actual.DatosAceptados) {
					System.out.print(i.valor + ",");
				}

				System.out.println("");
		
				if (actual.EstadoRepetido == false) {
					ShowArbol(actual);
				}
				actual = actual.next;
			}
		}
	}

	public void Delete(Nodo_SimpleSiguientesTransiciones actualReverse) {
		if (isNone() == false) {
			Nodo_SimpleSiguientesTransiciones actual = this.primero;

			while (actual != null && actual != actualReverse) {
				actual = actual.next;
			}

			if (actual == null) {
			} else {

				if (actual.previous == null) {
					this.primero = actual.next;
				} else if (actual != null) {
					actual.previous.next = actual.next;
					actual.next = null;
				}
			}

		}
	}

	public List<Valor_Tipo> QuitarDupicadosAceptacion(List<Valor_Tipo> lista) {
		lista = lista.stream().distinct().collect(Collectors.toList());
		return lista;
	}

	public List<Integer> QuitarDupicados(List<Integer> lista) {
		lista = lista.stream().distinct().collect(Collectors.toList());
		return lista;
	}

	public void showList() {
		if (isNone() == false) {
			Nodo_SimpleSiguientesTransiciones actual = this.primero;
			while (actual != null) {

				if (actual.EstadoRepetido) {
					System.out.print("Repedtido datos de aceptacion: " + actual.primeros + " DESTINO "
							+ actual.EstadoDestino + " finalizacion: " + actual.Aceptacion);
				} else {
					System.out.print(actual.Estado + " datos de aceptacion: " + actual.primeros + " finalizacion: "
							+ actual.Aceptacion);
				}
				System.out.print("::::");
				for (Valor_Tipo i : actual.DatosAceptados) {
					System.out.print(i.valor);
				}
				System.out.println("");

				// System.out.println(actual.Estado + " DESTINO " + actual.EstadoDestino + "
				// finalizacion: " + actual.Aceptacion);

				actual = actual.next;
			}
		}
	}

	public void Search(Valor_Tipo data) {
		if (isNone() == false) {
			Nodo_SimpleSiguientesTransiciones actual = this.primero;
			while (actual != null && actual.data != data) {
				actual = actual.next;
				if (actual == null) {
					System.out.println("No se encontro el dato: " + data);
					break;
				}
			}
			if (actual != null && actual.data == data) {
				System.out.println("Dato encontrado: " + data);
			}
		}
	}

	public Boolean isNone() {

		return this.primero == null;
	}

	public Boolean isNoneLast() {
		return this.ultimo == null;
	}
}

class Nodo_SimpleSiguientesTransiciones {
	Nodo_SimpleSiguientesTransiciones next, previous;

	Integer Numeracion;
	Boolean Verificado = false;
	Integer Estado;
	Valor_Tipo data;
	List<Valor_Tipo> DatosAceptados = new ArrayList<>();
	List<Integer> primeros = new ArrayList<>();
	SimpleSiguientesTransiciones listado;
	Boolean Aceptacion = false;
	Boolean EstadoRepetido = false;
	Integer EstadoDestino = 0;

	public Nodo_SimpleSiguientesTransiciones(Valor_Tipo data, List<Integer> siguinetes,
			SimpleCalcSiguientes siguientes) {
		this.next = null;
		this.previous = null;
		this.data = data;
		this.primeros = siguinetes;
		this.listado = new SimpleSiguientesTransiciones(siguientes);
	}

	public void ShowAceptacionesNodos(Nodo_SimpleSiguientesTransiciones cabezera) {

		if (cabezera != null) {
			Nodo_SimpleSiguientesTransiciones actual = cabezera;
			while (actual != null) {

				if (actual.EstadoRepetido == false) {

					if (actual.Aceptacion) {
						System.out.print("\tS" + actual.Estado + "* con ");
					} else {
						System.out.print("\tS" + actual.Estado + " con ");
					}

				} else {

					if (actual.Aceptacion) {
						System.out.print("\tS" + actual.EstadoDestino + "* con ");
					} else {
						System.out.print("\tS" + actual.EstadoDestino + " con ");
					}

				}

				for (Valor_Tipo i : actual.DatosAceptados) {
					System.out.print(i.valor + ",");
				}

				System.out.println("");

				// ShowArbol(actual.listado.primero);
				actual = actual.next;
			}
		}
	}

}
