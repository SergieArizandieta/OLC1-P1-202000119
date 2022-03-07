package analizadores;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import java.util.Stack;

import java_cup.reduce_action;
import net.miginfocom.layout.AC;

public class SimpleER {
	Automata automata = null;
	String TransicionesDot = "";
	String SiguinetesDot = "";
	String DOT = "digraph structs {\n    node [shape=Mrecord];\n";
	Estados Estado_Inicial;
	Nodo_Simple_ER primero, ultimo;
	public String name;
	SimpleCalcSiguientes siguientes = new SimpleCalcSiguientes();
	public Integer hojas = 0;
	List<String> ErTemp = new ArrayList<>();
	List<String> Temp;
	List<String> Temp2;

	public SimpleER() {
		this.primero = null;
		this.ultimo = null;
	}

	public void GenerarHermano() {
		System.out.println("Hemrnao");
		postorden(this.ultimo.hijo1);
		//preorden(this.ultimo.hijo1);
		//preordenOriginal(this.ultimo.hijo1);
		System.out.println(ErTemp);
		
	}
	
    private void postorden(Nodo_Simple_ER n) {
        if (n != null) {
            postorden(n.hijo1);
            postorden(n.hijo2);
            if(n.info.equals("+")) {
            	GestionConversor(n);
            }else if (n.info.equals("?")) {
            	ErTemp.add("ε");
            	ErTemp.add("|");
       
			}else {
				ErTemp.add(n.info);
			}
            //System.out.println(n.info);
           
        }
    }
    
    public void GestionConversor(Nodo_Simple_ER n) {
    	Temp = new ArrayList<>();
    	Temp2 = new ArrayList<>();
    	
    	postordenSimbolos(n.hijo1);
    	Temp.add("*");
    	Temp.add(".");
    	
    	for (String string : Temp) {
    		ErTemp.add(string);
		}
    	//System.out.println(Temp);
	}
    
    private void postordenSimbolos(Nodo_Simple_ER n) {
        if (n != null) {
        	postordenSimbolos(n.hijo1);
        	postordenSimbolos(n.hijo2);
            Temp.add(n.info);
        }
    }
    

	private void preordenOriginal(Nodo_Simple_ER n) {
		if (n != null) {
			ErTemp.add(n.info);
			preordenOriginal(n.hijo1);
			preordenOriginal(n.hijo2);
			
		}
	}

	private void preorden(Nodo_Simple_ER n) {
		if (n != null) {
			if (n.info.equals("+")) {

				Temp = new ArrayList<>();
				Temp2 = new ArrayList<>();
				gestionmas(n.hijo1);
				
				for (String string : Temp) {
					Temp2.add(string);
				}

				Temp.add("*");

				for (String string : Temp2) {
					Temp.add(string);
				}
			
				List<String> TempFinal = new ArrayList<>();
				TempFinal.add(".");
				
			
				for (String string : Temp) {
					TempFinal.add(string);
				}
				
				System.out.println(Temp);
				for (String string : TempFinal) {
					ErTemp.add(string);
				}

			} else if (n.info.equals("?")) {
				Temp = new ArrayList<>();
				Temp.add("|");
				gestionmas(n.hijo1);
				Temp.add("epsilon");

				// System.out.println(Temp);
				for (String string : Temp) {
					ErTemp.add(string);
				}

			} else {
				ErTemp.add(n.info);
				//System.out.println("Nodo: " + n.info);
				preorden(n.hijo1);
				preorden(n.hijo2);
			}
			//System.out.println("Nodo: " + n.info);
		}
	}

	private void gestionmas(Nodo_Simple_ER n) {
		if (n != null) {
			Temp.add(n.info);
			gestionmas(n.hijo1);
			gestionmas(n.hijo2);
		}
	}

	public void GenraraAFND() {
		System.out.println(ErTemp);
		//Collections.reverse(ErTemp);
		
		//System.out.println(ErTemp);
		
		AFND afn = new AFND(ErTemp);
		afn.construir();
		automata = afn.getAfn();
		System.out.println(automata);
		String tipo = "AFDN";
		
		CrearArchivo crear = new CrearArchivo(tipo + ".dot",tipo,automata);
        crear.crearImagen();
      

	}

	public void insert(String info, String tipo, Boolean hoja) {
		Nodo_Simple_ER new_node = new Nodo_Simple_ER(info, tipo, hoja);
		if (comprobarCaderna(new_node) == false) {
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

				while (actual.next != null) {
					anterior = actual;
					actual = actual.next;
					actual.previous = anterior;

				}

				if (hoja) {
					SetHojas();

					new_node.noHoja = this.hojas;
					new_node.Anulable = false;
				}
				actual.next = new_node;
				actual.next.previous = actual;

			}
		} else {
			QuitarCadenas(new_node);
		}
	}

	public void insertNormal(String info, String tipo, Boolean hoja) {
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

			while (actual.next != null) {
				anterior = actual;
				actual = actual.next;
				actual.previous = anterior;

			}

			if (hoja) {
				SetHojas();

				new_node.noHoja = this.hojas;
				new_node.Anulable = false;
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

	public Boolean comprobarCaderna(Nodo_Simple_ER actual) {
		boolean velidado = false;
		if (actual.tipo.equals("PHRASE")) {
			String temp = actual.info.replace("\"", "");
			if (temp.length() > 1) {
				velidado = true;
			}
		}

		return velidado;
	}

	public void showList() {
		System.out.println("======Show list ======");
		if (isNone() == false) {
			Nodo_Simple_ER actual = this.primero;
			// System.out.println(this.name);
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

	public void QuitarCadenas(Nodo_Simple_ER actual) {

		String temp = actual.info.replace("\"", "");
		Integer contador = 0;
		Boolean primeraVez = true;
		// System.out.println(temp);
		SimpleER SimpleTemp = new SimpleER();

		for (int i = temp.length() - 1; i >= 0; i--) {
			String letter = String.valueOf(temp.charAt(i));
			// System.out.println(letter);

			if (letter.equals(" ")) {
				this.insertNormal("\"" + letter + "\"", "SPACE", true);
				// SimpleTemp.insertNormal("\"" + letter + "\"", "SPACE", true);
			} else {
				this.insertNormal("\"" + letter + "\"", "PHRASE", true);
				// SimpleTemp.insertNormal("\"" + letter + "\"", "PHRASE", true);
			}

			if (primeraVez == false) {
				this.insertNormal(".", "OP", false);
			}
			if (primeraVez) {
				contador++;
			}
			if (primeraVez) {
				if (contador == 2) {
					this.insertNormal(".", "OP", false);
					primeraVez = false;
					contador = null;
				}
			}

		}

		SimpleTemp.showList();
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
				// System.out.println(Op.info + " Padre de: 1:" + Op.hijo1.info + " 2: " +
				// Op.hijo2.info);
			} else {
				// System.out.println(Op.info + " Padre de: 1:" + Op.hijo1.info);
			}

			validdacionOp = true;
			validdacionHijo2 = true;
			idActual += 1;
		}

		AsignarAnulables();
		// verNoAnulables();
		// verAnulables();
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
		// System.out.println("________________________________________");

		// System.out.println("======Show list Inverse ======");

		if (isNone() == false) {
			Nodo_Simple_ER actual = this.primero;

			while (actual != null) {
				if (actual.hoja) {
					// System.out.println( this.hojas + " : " + actual.info);
					actual.noHoja = this.hojas;
					this.hojas--;
				}

				actual = actual.next;
			}
		}
		// System.out.println("________________________________________");
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
		// showListPrimeros();
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
		// showListUltimos();
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
						for (Integer ultimoH : actual.primeros) {
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
		// this.siguientes.showList();
		TablaTransiciones();
	}

	public void TablaTransiciones() {
		System.out.println("====== Tabla de transiciones ======");
		// System.out.println(this.ultimo.primeros);
		DOT = "digraph structs {\n  bgcolor = \"#E3FFFA\"\n   node [shape=Mrecord fillcolor=\"#FFE3FF\" style =filled];\n";
		DOT += "label =\"" + this.name + "\"\n";

		Estado_Inicial = new Estados(0, false, this.ultimo.primeros, this.name);

		Estado_Inicial.Inciando_tabla_transiciones(this.siguientes);
		;

		if (isNoneLast() == false) {
			Nodo_Simple_ER actual = this.ultimo;
			GenerarArbol(actual);

		}
		if (this.siguientes.isNone() == false) {
			SiguinetesDot = this.siguientes.CrearGrafo(this.name);
		}

		if (Estado_Inicial.listado.isNone() == false) {
			TransicionesDot = Estado_Inicial.Generar_transiciones();
		}

		System.out.println(TransicionesDot);

		DOT += "\n}";

		Draw_GraphizSiguientes(this.name);
		Draw_GraphizArbol(this.name);
		// openimgSiguientes(this.name);
		// openimgArbol(this.name);

	}

	public void GenerarArbol(Nodo_Simple_ER actual) {

		DOT += "    struct" + actual.hashCode() + "    [label=\"{{" + actual.primeros + "|<here>";
		Valor_Tipo data = new Valor_Tipo(actual.info, actual.tipo);
		DOT += validacionTipo(data);
		DOT += "|" + actual.ultimos + "}|";

		if (actual.Anulable) {
			DOT += "Anulable}\"];\n";
		} else {
			DOT += "No Anulable}\"];\n";
		}

		if (actual.hoja) {

		} else {

			if (actual.hijo1 != null) {
				DOT += "    struct" + actual.hashCode() + "-> struct" + actual.hijo1.hashCode() + "\n";
				GenerarArbol(actual.hijo1);
			}
			if (actual.hijo2 != null) {
				DOT += "    struct" + actual.hashCode() + "-> struct" + actual.hijo2.hashCode() + "\n";
				GenerarArbol(actual.hijo2);
			}

		}

	}

	public void Draw_GraphizSiguientes(String name) {

		try {
			if (isNone()) {
				String graph = "digraph L {\r\n" + "node[shape=note fillcolor=\"#A181FF\" style =filled]\r\n"
						+ "subgraph cluster_p{\r\n" + "    bgcolor = \"#FF7878\"\r\n"
						+ "Nodo1008925772[label=\"Vacio\",fillcolor=\"#81FFDA\"]\r\n" + "\r\n" + "}}";
				Create_File("SIGUIENTES_202000119\\Siguientes_" + name + ".dot", graph);
			} else {

				Create_File("SIGUIENTES_202000119\\Siguientes_" + name + ".dot", SiguinetesDot);
			}

			// System.out.println(Text_Graphivz());
			ProcessBuilder pb;
			// AFD_202000119
			pb = new ProcessBuilder("dot", "-Tpng", "-o", "SIGUIENTES_202000119\\Siguientes_" + name + ".png",
					"SIGUIENTES_202000119\\Siguientes_" + name + ".dot");
			pb.redirectErrorStream(true);
			pb.start();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void openimgSiguientes(String name) {
		try {
			String url = "SIGUIENTES_202000119\\Siguientes_" + name + ".png";
			ProcessBuilder p = new ProcessBuilder();
			p.command("cmd.exe", "/c", url);
			p.start();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	// -----------------------------------------------------------

	// Creacion arbol graphivz
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

	public void Draw_GraphizArbol(String name) {

		try {
			if (isNone()) {
				String graph = "digraph L {\r\n" + "node[shape=note fillcolor=\"#A181FF\" style =filled]\r\n"
						+ "subgraph cluster_p{\r\n" + "    bgcolor = \"#FF7878\"\r\n"
						+ "Nodo1008925772[label=\"Vacio\",fillcolor=\"#81FFDA\"]\r\n" + "\r\n" + "}}";
				Create_File("ARBOLES_202000119\\Arbol_" + name + ".dot", graph);
			} else {

				Create_File("ARBOLES_202000119\\Arbol_" + name + ".dot", DOT);
			}

			// System.out.println(Text_Graphivz());
			ProcessBuilder pb;
			// AFD_202000119
			pb = new ProcessBuilder("dot", "-Tpng", "-o", "ARBOLES_202000119\\Arbol_" + name + ".png",
					"ARBOLES_202000119\\Arbol_" + name + ".dot");
			pb.redirectErrorStream(true);
			pb.start();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void openimgArbol(String name) {
		try {
			String url = "ARBOLES_202000119\\Arbol_" + name + ".png";
			ProcessBuilder p = new ProcessBuilder();
			p.command("cmd.exe", "/c", url);
			p.start();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	// -----------------------------------------------------------

	public String validacionTipo(Valor_Tipo text) {
		String DOT = "";
		if (text.tipo.equals("PHRASE") || text.tipo.equals("SPACE")) {
			for (int letra = 0; letra < text.valor.length(); letra++) {
				if (letra == 0) {
					DOT += ("\\\"");
				} else if (letra + 1 == text.valor.length()) {
					DOT += ("\\\"");
				} else {
					DOT += (text.valor.charAt(letra));
				}
			}

		} else if (text.tipo.equals("S_DQUOTES")) {

			DOT += ("\\\\\\\"");
		} else if (text.tipo.equals("S_QUOTE")) {
			DOT += ("\\\\'");
		} else if (text.tipo.equals("S_LBREAK")) {
			DOT += ("\\\\n");
		} else if (text.valor.equals("|")) {
			DOT += ("\\|");

		} else {
			DOT += (text.valor);
		}
		return DOT;
	}

	public void GenrarGrafo() {
		// Estado_Inicial.Inciando_tabla_transiciones(this.siguientes);
		verGrafo();
	}

	public void verGrafo() {
		Estado_Inicial.verGrafo();
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

	public void ValidarCadena(String cadena, LinkedList<Conj> conjList, Cadenas i) {

		Estado_Inicial.validadarCadena(cadena, conjList, i);
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
