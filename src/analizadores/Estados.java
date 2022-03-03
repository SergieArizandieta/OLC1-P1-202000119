package analizadores;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.sound.midi.Soundbank;

public class Estados {
	static Cadenas cadenai;
	static LinkedList<Conj> ListaConjuntos;
	static Boolean CadenaValida;
	static Nodo_SimpleSiguientesTransiciones ActualValidacion;
	static String dot;
	static Integer estadosGestion = 0;
	static Nodo_SimpleSiguientesTransiciones encabezadoEstado;
	static Nodo_SimpleSiguientesTransiciones anteriorEstado = null;

	String name;
	Integer Estado;
	Boolean Aceptacion;
	List<Integer> Valores = new ArrayList<>();
	SimpleSiguientesTransiciones listado;
	List<String> CaracteresAceptados = new ArrayList<>();

	public Estados(Integer Estado, Boolean Aceptacion, List<Integer> Siguientes, SimpleCalcSiguientes siguientes,
			String name) {
		this.Estado = Estado;
		this.Aceptacion = Aceptacion;
		this.Valores = Siguientes;
		this.listado = new SimpleSiguientesTransiciones(siguientes);
		this.name = name;
	}

	public void show() {
		String data = "";
		data += "\nEstado:" + this.Estado;
		data += "\nAceptacion:" + this.Aceptacion;
		data += "\nValores:" + this.Valores;
		data += "\nTransiciones:";

		System.out.println(data);
		this.listado.Calcsiguientes.showList();
		;
	}

	public void Inciando_tabla_transiciones(SimpleCalcSiguientes siguientes) {
		dot = "";

		// System.out.println("==== editando valores de estado: " + this.Estado + "
		// ====" );
		String Tipo, Valor;
		Valor_Tipo valor_tipo;
		List<Integer> Primeros;

		for (Integer i : this.Valores) {
			Tipo = siguientes.SerachTipo(i);
			if (Tipo.equals("Finalizacion")) {
				this.Aceptacion = true;
			} else {
				Valor = siguientes.SerachInfo(i);
				valor_tipo = new Valor_Tipo(Valor, Tipo);
				Primeros = siguientes.SerachPrimeros(i);
				listado.insert(valor_tipo, Primeros, siguientes);

			}
		}
		encabezadoEstado = listado.primero;
		listado.AgregarDatos_Aceptados();

		System.out.println("==============Mostrando Arbol==============");
		estadosGestion = 0;
		System.out.println(this.name);
		listado.verArbolMain(this.Aceptacion, this.name);
		listado.AgregarAceptacionAvReporte();
		listado.verReporte(this.name);
		listado.Draw_Graphiz(this.name);

		// listado.verArbol(this.Aceptacion);

	}

	public String Generar_transiciones() {
		String dot = "";
		// System.out.println("==== ========= COPIA =================");
		listado.DotTraniscisiones(this.name, this.Aceptacion);
		listado.Draw_GraphizTransiciones(this.name);
		// listado.openimgTransiciones(this.name);

		return dot;
	}

	public void validadarCadena(String cadena, LinkedList<Conj> conjList, Cadenas cadenai) {
		Estados.cadenai = cadenai;
		ListaConjuntos = conjList;
		CadenaValida = true;
		anteriorEstado = null;
		ActualValidacion = listado.primero;
		// cadena+="ƒ";

		//System.out.println(cadena);
		if (cadena != "") {
			for (int i = 0; i < cadena.length(); i++) {
				String letter = String.valueOf(cadena.charAt(i));
				String letterSig;
				// System.out.println(letter);
				if (CadenaValida) {

					if (i + 1 < cadena.length()) {
						letterSig = String.valueOf(cadena.charAt(i + 1));
					} else {
						letterSig = null;
					}
					ValidacionGuia(ActualValidacion, letter, letterSig);
				}else {
					break;
				}
			}

			if (CadenaValida) {
				
				
				if (ActualValidacion.Aceptacion) {
					cadenai.validacion = true;
					if(anteriorEstado.EstadoRepetido) {
						System.out.println("Estado en: " + ActualValidacion.EstadoDestino);
					}else {
						System.out.println("Estado en: " + ActualValidacion.Estado);
					}
				}
				
				
			} else {
				if (anteriorEstado == null) {
					System.out.println("Fallo en : 0");
				} else {
					if(anteriorEstado.EstadoRepetido) {
						System.out.println("Fallo en anterior: " + anteriorEstado.EstadoDestino);
					}else {
						System.out.println("Fallo en anterior: " + anteriorEstado.Estado);
					}
					
				}
			}

		} else {
			if (Aceptacion) {
				cadenai.validacion = true;
				System.out.println("cadena aceptada");
			} else {
				System.out.println("fallo");
			}
		}

	}

	public void ValidacionGuia(Nodo_SimpleSiguientesTransiciones actual, String letter, String letterSig) {
		// System.out.println("____________________________________________________");
		// System.out.println("Estado " + actual.Estado + " letra: " + letter);
		// System.out.println("=====================================================");
		if(anteriorEstado == null) {
			actual.listado.ValidacionPivote(actual, letter, letterSig,true);
		}else {
			actual.listado.ValidacionPivote(actual, letter, letterSig,false);
		}
		

	}

	public void verGrafo() {
		listado.openimg(this.name);
	}

}
