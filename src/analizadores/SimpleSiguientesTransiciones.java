package analizadores;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import javax.sound.midi.Soundbank;

import analizadores.Estados;
import jflex.exceptions.MacroException;

public class SimpleSiguientesTransiciones {
	// Integer RegulacionEstado;
	Integer ContadorGeneral = 0;
	String TransicionesDot;
	List<Valor_Tipo> Terminales;
	List<Integer> EstadosAceptacion;

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
				// System.out.println("cabezera");
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
						if (actual.Aceptacion) {
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
					if (actual.Aceptacion) {
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

		// System.out.println("==== Tabla de transiciones para nuevos estados ====");
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

						// actual.listado.showList();

						// System.out.println(actual.Estado);
					}

				}
				actual = actual.next;
			}
		}
	}

	public void AgregarAceptacionAvReporte() {
		Integer contador = 1;

		for (Integer integer : EstadosAceptacion) {
			Estados.dot += integer;
			if (contador < EstadosAceptacion.size()) {
				Estados.dot += ",";
			} else {
				Estados.dot += ";\n";
			}

			contador++;
		}
		Estados.dot += ("	node [shape = circle, color = \"#2CB5FF\" fillcolor=\"#E3FFFA\" style =filled];\n\n");
		verArbolMainReporte();

	}

	public void verArbolMainReporte() {

		if (isNone() == false) {
			Nodo_SimpleSiguientesTransiciones actual = this.primero;
			while (actual != null) {
				Integer contador = 1;
				Estados.dot += ("0->");
				Estados.dot += (actual.Estado);
				Estados.dot += ("[label = \"");

				for (Valor_Tipo i : actual.DatosAceptados) {

					validacionTipo(i);
					if (contador < actual.DatosAceptados.size()) {
						Estados.dot += (",");
					} else {
						Estados.dot += ("\"];\n");
					}
					contador++;
				}

				actual = actual.next;
			}
		}
		ShowArbolReporte(this.primero);

	}

	public void validacionTipo(Valor_Tipo text) {
		if (text.tipo.equals("PHRASE") || text.tipo.equals("SPACE")) {
			for (int letra = 0; letra < text.valor.length(); letra++) {
				if (letra == 0) {
					Estados.dot += ("\\\"");
				} else if (letra + 1 == text.valor.length()) {
					Estados.dot += ("\\\"");
				} else {
					Estados.dot += (text.valor.charAt(letra));
				}
			}

		} else if (text.tipo.equals("S_DQUOTES")) {

			Estados.dot += ("\\\\\\\"");
		} else if (text.tipo.equals("S_QUOTE")) {
			Estados.dot += ("\\\\'");
		} else if (text.tipo.equals("S_LBREAK")) {
			Estados.dot += ("\\\\n");

		} else {
			Estados.dot += (text.valor);
		}
	}

	public void ShowArbolReporte(Nodo_SimpleSiguientesTransiciones cabezera) {
		if (cabezera != null) {
			Nodo_SimpleSiguientesTransiciones actual = cabezera;
			while (actual != null) {

				ShowAceptacionesReporte(actual.listado.primero, actual.Estado);
				actual = actual.next;
			}
		}
	}

	public void ShowAceptacionesReporte(Nodo_SimpleSiguientesTransiciones cabezera, Integer estado) {

		if (cabezera != null) {
			Nodo_SimpleSiguientesTransiciones actual = cabezera;
			while (actual != null) {
				Integer contador = 1;
				Estados.dot += (estado + "->");
				if (actual.EstadoRepetido == false) {
					Estados.dot += (actual.Estado);

				} else {
					Estados.dot += (actual.EstadoDestino);

				}

				Estados.dot += ("[label = \"");

				for (Valor_Tipo i : actual.DatosAceptados) {

					validacionTipo(i);
					if (contador < actual.DatosAceptados.size()) {
						Estados.dot += (",");
					} else {
						Estados.dot += ("\"];\n");
					}
					contador++;
				}

				System.out.println("");

				if (actual.EstadoRepetido == false) {
					ShowArbolReporte(actual);
				}
				actual = actual.next;
			}
		}
	}

	public void verReporte(String name) {
		Estados.dot += "label= " + name;
		Estados.dot += ("}");

		// System.out.println(Estados.dot);

	}

	// create the dot dile
	private void Create_File(String route, String contents) {

		FileWriter fw = null;
		PrintWriter pw = null;
		try {
			fw = new FileWriter(route);
			pw = new PrintWriter(fw);
			pw.write(contents);
			pw.close();
			fw.close();
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		} finally {
			if (pw != null)
				pw.close();
		}

	}

	// draw the graph
	public void Draw_Graphiz(String name) {

		try {
			if (isNone()) {
				String graph = "digraph L {\r\n" + "node[shape=note fillcolor=\"#A181FF\" style =filled]\r\n"
						+ "subgraph cluster_p{\r\n" + "    bgcolor = \"#FF7878\"\r\n"
						+ "Nodo1008925772[label=\"Vacio\",fillcolor=\"#81FFDA\"]\r\n" + "\r\n" + "}}";
				Create_File("AFD_202000119\\" + name + ".dot", graph);
			} else {

				Create_File("AFD_202000119\\" + name + ".dot", Estados.dot);
			}

			// System.out.println(Text_Graphivz());
			ProcessBuilder pb;
			// AFD_202000119
			pb = new ProcessBuilder("dot", "-Tpng", "-o", "AFD_202000119\\" + name + ".png",
					"AFD_202000119\\" + name + ".dot");
			pb.redirectErrorStream(true);
			pb.start();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void openimg(String name) {
		try {
			String url = "AFD_202000119\\" + name + ".png";
			ProcessBuilder p = new ProcessBuilder();
			p.command("cmd.exe", "/c", url);
			p.start();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void verArbolMain(Boolean Aceptacion, String Name) {
		Estados.dot = "";
		Estados.dot += ("digraph finite_state_machine {\n");
		Estados.dot += ("	bgcolor = \"#F6FFE3\"\n");
		Estados.dot += ("	node [fontname=\"Helvetica,Arial,sans-serif\" ]\n");
		Estados.dot += ("	edge [fontname=\"Helvetica,Arial,sans-serif\"]\n");
		Estados.dot += ("	rankdir=LR;\n");
		Estados.dot += ("	node [shape = doublecircle, color = gold fillcolor=\"#EBE3FF\" style =filled];");

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
					EstadosAceptacion.add(actual.Estado);
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
					EstadosAceptacion.add(actual.Estado);
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
						EstadosAceptacion.add(actual.Estado);
						EstadosAceptacion = QuitarDupicados(EstadosAceptacion);
					} else {
						System.out.print("\tS" + actual.Estado + " con ");
					}

				} else {

					System.out.print("\tS" + actual.EstadoDestino + " con ");

					/*
					 * if (actual.Aceptacion) { System.out.print("\tS" + actual.EstadoDestino +
					 * "* con "); EstadosAceptacion.add(actual.EstadoDestino); EstadosAceptacion =
					 * QuitarDupicados(EstadosAceptacion); } else { System.out.print("\tS" +
					 * actual.EstadoDestino + " con "); }
					 */

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

	public void DotTraniscisiones(String name, Boolean Aceptacion) {
		TransicionesDot = "";
		TransicionesDot += "digraph G { bgcolor=\"black\"\n ";
		TransicionesDot += "  fontname=\"Helvetica,Arial,sans-serif\"\n";
		TransicionesDot += "  edge [fontname=\"Helvetica,Arial,sans-serif\"]\n";
		TransicionesDot += "  subgraph cluster1 {fillcolor=\"skyblue\" style=\"filled\"\n";
		TransicionesDot += "  node [shape=Msquare fillcolor=\"gold:brown\" style=\"radial\" gradientangle=180]\n";
		TransicionesDot += "  label = \" Tabla de transiciones de: " + name + "\"\n";
		TransicionesDot += "  a0 [label=<  \n";
		TransicionesDot += "  <TABLE border=\"10\" cellspacing=\"10\" cellpadding=\"10\" style=\"rounded\" gradientangle=\"315\">\n";

		Terminales = new ArrayList<>();
		Terminales = this.Calcsiguientes.CrearCopiaTerminales();

		TransicionesDot += "  <TR>\n";
		TransicionesDot += "      <TD border=\"3\" bgcolor=\"#FFF97B\">Estados\\Terminales  </TD>\n";
		for (Valor_Tipo i : Terminales) {
			TransicionesDot += "      <TD border=\"3\" bgcolor=\"#FFF97B\">";
			TransicionesDot += i.valor;
			TransicionesDot += "</TD>\n";

			// System.out.println(i.valor);
			ContadorGeneral++;
		}
		TransicionesDot += "  </TR>\n";
		verArbolMainTransiciones(Aceptacion);
		System.out.println("=============das=============");
		TransicionesDot += "\n</TABLE>>];}}";
		System.out.println(TransicionesDot);
	}

	public List<String> QuitarDupicadosString(List<String> lista) {
		lista = lista.stream().distinct().collect(Collectors.toList());
		return lista;
	}

	public void verArbolMainTransiciones(Boolean Aceptacion) {
		List<Valor_Tipo> CopyTerminales = new ArrayList<>();
		CopyTerminales = obtenerListaTerminalesNulas(this.primero);

		TransicionesDot += "  <TR>\n";

		if (Aceptacion) {
			TransicionesDot += "      <TD border=\"3\" bgcolor=\"#FFF97B\">0/$ S0</TD>\n";
		} else {
			TransicionesDot += "      <TD border=\"3\" bgcolor=\"#FFF97B\">0 S0</TD>\n";
		}

		CopyTerminales = obtenerListaTerminalesNulas(this.primero);
		Boolean hayDatos = false;

		for (Valor_Tipo k : Terminales) {
			hayDatos = false;
			for (Valor_Tipo valor_Tipo : CopyTerminales) {
				if (k.valor.equals(valor_Tipo.valor)) {

					TransicionesDot += "      <TD border=\"3\" > S" + valor_Tipo.Estado + "</TD>\n";
					CopyTerminales.remove(valor_Tipo);
					hayDatos = true;
					break;
				}
			}
			if (hayDatos == false) {
				TransicionesDot += "      <TD border=\"3\" > -- </TD>\n";
			}

		}
		TransicionesDot += "  </TR>\n";
		ShowAceptacionesTransiociones(this.primero);

	}

	public List<Valor_Tipo> obtenerListaTerminalesNulas(Nodo_SimpleSiguientesTransiciones primero) {
		Boolean descartado = true;
		List<String> Temp = new ArrayList<>();
		List<Valor_Tipo> CopyTerminales = new ArrayList<>();
		List<Valor_Tipo> probando = new ArrayList<>();
		if (primero != null) {
			Nodo_SimpleSiguientesTransiciones actual = primero;

			while (actual != null) {
				for (Valor_Tipo i : actual.DatosAceptados) {
					Temp.add(i.valor);
				}
				for (Valor_Tipo i : actual.DatosAceptados) {
					Valor_Tipo data = new Valor_Tipo(i.valor, i.tipo);
					if (actual.EstadoRepetido) {
						data.Estado = actual.EstadoDestino;
					} else {
						data.Estado = actual.Estado;
					}

					probando.add(data);
				}
				actual = actual.next;
			}
			probando = QuitarDupicadosAceptacion(probando);
			System.out.println("_________________________________________");
			for (Valor_Tipo valor_Tipo : probando) {
				// System.out.println(valor_Tipo.valor + " = " + valor_Tipo.Estado);
			}
			System.out.println("_________________________________________");
			Temp = QuitarDupicadosString(Temp);

			Boolean continuar = true;

			for (Valor_Tipo i : Terminales) {
				CopyTerminales.add(i);
			}
			System.out.println("==========================");
			while (descartado) {

				if (CopyTerminales.size() != 0) {
					for (Valor_Tipo i : CopyTerminales) {
						continuar = true;
						if (Temp.size() != 0) {
							for (String string : Temp) {
								if (i.valor.equals(string)) {
									Temp.remove(string);
									CopyTerminales.remove(i);
									continuar = false;
									break;
								}
							}
						} else {
							descartado = false;
						}
						if (continuar == false) {
							break;
						}
					}
				} else {
					descartado = false;
				}
			}

			for (Valor_Tipo valor_Tipo : CopyTerminales) {
				System.out.println(valor_Tipo.valor);
			}

		}
		// return CopyTerminales;

		return probando;

	}

	public void ShowAceptacionesTransiociones(Nodo_SimpleSiguientesTransiciones cabezera) {

		if (cabezera != null) {
			Nodo_SimpleSiguientesTransiciones actual = cabezera;
			while (actual != null) {

				TransicionesDot += "  <TR>\n";
				if (actual.EstadoRepetido) {
					if (actual.Aceptacion) {
						TransicionesDot += "      <TD border=\"3\" bgcolor=\"#FFF97B\">$  S" + actual.EstadoDestino
								+ "</TD>\n";
					} else {
						TransicionesDot += "      <TD border=\"3\" bgcolor=\"#FFF97B\">S" + actual.EstadoDestino
								+ "</TD>\n";
					}

				} else {
					if (actual.Aceptacion) {
						TransicionesDot += "      <TD border=\"3\" bgcolor=\"#FFF97B\">$  S" + actual.Estado + "</TD>\n";
					} else {
						TransicionesDot += "      <TD border=\"3\" bgcolor=\"#FFF97B\">S" + actual.Estado + "</TD>\n";
					}
				}
				Nodo_SimpleSiguientesTransiciones temp = actual;
				actual = actual.next;

				if (temp.EstadoRepetido == false) {
					subpartes(temp.listado.primero);
				}

			}
		}

	}

	public void subpartes(Nodo_SimpleSiguientesTransiciones cabezera) {
		List<Valor_Tipo> CopyTerminales = new ArrayList<>();
		if (cabezera != null) {
			CopyTerminales = obtenerListaTerminalesNulas(cabezera);
			Boolean hayDatos = false;

			for (Valor_Tipo k : Terminales) {
				hayDatos = false;
				for (Valor_Tipo valor_Tipo : CopyTerminales) {
					if (k.valor.equals(valor_Tipo.valor)) {

						TransicionesDot += "      <TD border=\"3\" > S" + valor_Tipo.Estado + "</TD>\n";
						CopyTerminales.remove(valor_Tipo);
						hayDatos = true;
						break;
					}
				}
				if (hayDatos == false) {
					TransicionesDot += "      <TD border=\"3\" > -- </TD>\n";
				}

			}
			TransicionesDot += "  </TR>\n";
			if (cabezera.EstadoRepetido == false) {
				ShowAceptacionesTransiociones(cabezera);
			}

		} else {
			for (int i = 0; i < ContadorGeneral; i++) {
				TransicionesDot += "      <TD border=\"3\" > -- </TD>\n";

			}
			TransicionesDot += "  </TR>\n";
		}
	}

	public void Draw_GraphizTransiciones(String name) {

		try {
			if (isNone()) {
				String graph = "digraph L {\r\n" + "node[shape=note fillcolor=\"#A181FF\" style =filled]\r\n"
						+ "subgraph cluster_p{\r\n" + "    bgcolor = \"#FF7878\"\r\n"
						+ "Nodo1008925772[label=\"Vacio\",fillcolor=\"#81FFDA\"]\r\n" + "\r\n" + "}}";
				Create_File("TRANSICIONES_202000119\\Transiciones" + name + ".dot", graph);
			} else {

				Create_File("TRANSICIONES_202000119\\Transiciones" + name + ".dot", TransicionesDot);
			}

			// System.out.println(Text_Graphivz());
			ProcessBuilder pb;
			// AFD_202000119
			pb = new ProcessBuilder("dot", "-Tpng", "-o", "TRANSICIONES_202000119\\Transiciones" + name + ".png",
					"TRANSICIONES_202000119\\Transiciones" + name + ".dot");
			pb.redirectErrorStream(true);
			pb.start();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void openimgTransiciones(String name) {
		try {
			String url = "TRANSICIONES_202000119\\Transiciones" + name + ".png";
			ProcessBuilder p = new ProcessBuilder();
			p.command("cmd.exe", "/c", url);
			p.start();

		} catch (Exception e) {
			e.printStackTrace();
		}
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
}