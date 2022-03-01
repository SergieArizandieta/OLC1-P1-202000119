package analizadores;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SimpleCalcSiguientes {
	
	Nodo_Simple primero,ultimo;

	public SimpleCalcSiguientes() {
		this.primero = null;
		this.ultimo = null;
	}

	public void insert(String info,String tipo,Integer Hoja) {
		Nodo_Simple new_node = new Nodo_Simple(info,tipo,Hoja);
		if (isNone()) {
			this.primero = new_node;
			this.ultimo = this.primero;
		} else {
			Nodo_Simple actual = this.primero;
			this.ultimo = new_node;
			while (actual.next != null) {
				actual = actual.next;
			}
			actual.next = new_node;
			actual.next.previous = actual;
		}
	}

	public void showList() {
		if (isNone() == false) {
			Nodo_Simple actual = this.primero;
			while (actual != null) {
				System.out.println(actual.info + " Hoja: " + actual.Hoja + " tipo: " + actual.tipo + " sig: " + actual.siguientes);
				/*for (Integer i : actual.siguientes) {
					System.out.println(i);
				}*/
				actual = actual.next;
			}
		}
	}
	
	public String CrearGrafo(String name) {
		String Dot="";
		Dot = "digraph structs { \n  bgcolor = \"#FFE8E3\"   \n node [shape=Mrecord fillcolor=\"#E3EBFF\" style =filled];\n";
		Dot+= "label =\"" + name + "\"\n";
		Dot+="struct1 [label=\"SIGUIENTES| { Valor | Hoja| Siguinetes } | \n";
	
		if (this.ultimo != null) {
			Nodo_Simple actual = this.ultimo;
			
			
			while (actual != null) {
				String temp= "";
				temp+="{ " 
						;
				Valor_Tipo data = new Valor_Tipo(actual.info,actual.tipo);
				temp+=validacionTipo(data);
	
				
				temp+= "| " + actual.Hoja + " | " + actual.siguientes + "}";
				actual = actual.previous;
				
				if(actual != null) {
					temp+="|";
				}
				temp+="\n";
				Dot += temp;
			}
			
			Dot += "\"];\n }";
			return Dot;
		}
		return null;
	}
	

	public List<Valor_Tipo> CrearCopiaTerminales() {
		List<Valor_Tipo> Terminales = new ArrayList<>();
		
		if (this.ultimo != null) {
			Nodo_Simple actual = this.ultimo;
			
			while (actual != null) {
				
				Boolean agregar = true;
				
				for (Valor_Tipo i : Terminales) {
					if(actual.info.equals(i.valor)) {
						agregar =false;
					}
				}
				
				if(agregar) {
					Valor_Tipo data= new Valor_Tipo(actual.info,actual.tipo);
					if(data.tipo.equals("Finalizacion")){
						
					}else {
						Terminales.add(data);
					}
						
					
				}
				
				actual= actual.previous;
			}
			
			return Terminales;
		}
		return null;
	}

	public String validacionTipo(Valor_Tipo text) {
		String DOT ="";
		if (text.tipo.equals("PHRASE") || text.tipo.equals("SPACE")) {
			for (int letra = 0; letra < text.valor.length(); letra++) {
				if (letra == 0) {
					DOT+=("\\\"");
				} else if (letra + 1 == text.valor.length()) {
					DOT+=("\\\"");
				} else {
					DOT+=(text.valor.charAt(letra));
				}
			}

		} else if (text.tipo.equals("S_DQUOTES")) {
			
			DOT+=("\\\\\\\"");
		} else if (text.tipo.equals("S_QUOTE")) {
			DOT+=("\\\\'");
		} else if (text.tipo.equals("S_LBREAK")) {
			DOT+=("\\\\n");
		} else if (text.valor.equals("|")) {
			DOT+=("\\|");
			
		} else {
			DOT+=(text.valor);
		}
		return DOT;
	}
	
	public void Search(Object data) {
		if (isNone() == false) {
			Nodo_Simple actual = this.primero;
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

	public String SerachInfo(Integer data) {
		if (isNone() == false) {
			Nodo_Simple actual = this.primero;
			while (actual != null && actual.Hoja != data) {
				actual = actual.next;
				if (actual == null) {
					//System.out.println("No se encontro el dato: " + data);
					break;
				}
			}
			if (actual != null && actual.Hoja == data) {
				//System.out.println("Dato encontrado: " + data);
				return actual.info;
			}
		}
		return null;
	}
	
	public String SerachTipo(Integer data) {
		if (isNone() == false) {
			Nodo_Simple actual = this.primero;
			while (actual != null && actual.Hoja != data) {
				actual = actual.next;
				if (actual == null) {
					//System.out.println("No se encontro el dato: " + data);
					break;
				}
			}
			if (actual != null && actual.Hoja == data) {
				//System.out.println("Dato encontrado: " + data);
				return actual.tipo;
			}
		}
		return null;
	}
	
	public List<Integer> SerachPrimeros(Integer data) {
		if (isNone() == false) {
			Nodo_Simple actual = this.primero;
			while (actual != null && actual.Hoja != data) {
				actual = actual.next;
				if (actual == null) {
					//System.out.println("No se encontro el dato: " + data);
					break;
				}
			}
			if (actual != null && actual.Hoja == data) {
				//System.out.println("Dato encontrado: " + data);
				return actual.siguientes;
			}
		}
		return null;
	}
		
	public void InsertarSIguiente(Integer HojaBusqueda,Integer siguiente) {
		if (isNone() == false) {
			Nodo_Simple actual = this.primero;
			while (actual != null && actual.Hoja != HojaBusqueda) {
				actual = actual.next;
				if (actual == null) {
					//System.out.println("No se encontro el dato: " + HojaBusqueda);
					break;
				}
			}
			if (actual != null && actual.Hoja == HojaBusqueda) {
				actual.siguientes.add(siguiente);
				//System.out.println("Dato encontrado: " + HojaBusqueda);
			}
		}
	}

	public Boolean isNone() {
		
		return this.primero == null;
	}
}

class Nodo_Simple {

	Nodo_Simple next,previous;
	
	Integer estado;
	
	
	String info;
	String tipo;
	Integer Hoja;
	List<Integer> siguientes = new ArrayList<>();
	

	public Nodo_Simple(String info,String tipo,Integer Hoja) {
		this.next = null;
		this.previous = null;
		this.info = info;
		this.tipo = tipo;
		this.Hoja = Hoja;
	}
}
