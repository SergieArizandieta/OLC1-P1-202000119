package analizadores;

import java.util.ArrayList;
import java.util.List;


public class Valor_Tipo {
	String valor;
	String tipo;
	Integer Estado;
		
	public Valor_Tipo(String valor, String tipo) {
		this.valor =valor;
		this.tipo =tipo;
		this.Estado=null;
	}

	public void show() {
		String data = "";
		data += "\nValor:" + this.valor;
		data += "\nTipo:" +  this.tipo;
		System.out.println(data);
	}
		

}
