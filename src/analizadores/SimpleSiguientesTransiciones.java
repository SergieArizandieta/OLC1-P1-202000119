package analizadores;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SimpleSiguientesTransiciones {
	Integer RegulacionEstado;
	Integer RegulacionNumeroacion = 0;
	Nodo_SimpleSiguientesTransiciones primero, ultimo;
	SimpleCalcSiguientes Calcsiguientes = new SimpleCalcSiguientes();

	public SimpleSiguientesTransiciones(SimpleCalcSiguientes siguientes, Integer RegulacionEstado) {
		this.primero = null;
		this.primero = null;
		this.Calcsiguientes = siguientes;
		this.RegulacionEstado = RegulacionEstado;
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
		System.out.println("====== agregar aceptados ======");
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

		agregarValorEstado();
	}

	public void agregarValorEstado() {
		if (isNone() == false) {
			Nodo_SimpleSiguientesTransiciones actual = this.primero;
			while (actual != null) {
				RegulacionEstado++;
				actual.Estado = RegulacionEstado;
				actual = actual.next;
			}
		}

		CrearEstadosnuevos();
	}

	public void CrearEstadosnuevos() {
		if (isNone() == false) {
			Nodo_SimpleSiguientesTransiciones actual = this.primero;
			while (actual != null) {

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
				System.out.println(actual.Estado + " datos de aceptacion ");
				for (Valor_Tipo i : actual.DatosAceptados) {
					System.out.println(i.valor);
				}
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

	Integer Estado;
	Valor_Tipo data;
	List<Valor_Tipo> DatosAceptados = new ArrayList<>();
	List<Integer> primeros = new ArrayList<>();
	SimpleSiguientesTransiciones listado;

	public Nodo_SimpleSiguientesTransiciones(Valor_Tipo data, List<Integer> siguinetes,
			SimpleCalcSiguientes siguientes) {
		this.next = null;
		this.previous = null;
		this.data = data;
		this.primeros = siguinetes;
		this.listado = new SimpleSiguientesTransiciones(siguientes, 0);
	}
}
