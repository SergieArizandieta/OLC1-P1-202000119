package analizadores;

public class errorList {
	String tipo, lexema, descripcion;
	int linea, columna;
	
	public errorList(String tipo,String lexema,String descripcion, int linea, int columna ) {
		this.tipo=tipo;
		this.lexema=lexema;
		this.descripcion=descripcion;
		this.linea=linea+1;
		this.columna = columna+1;
	}

	public String show() {
		String data = "";
		data += "\ntipo:" + tipo;
		data += "\nlexema:" + lexema;
		data += "\ndescripcion:" + descripcion;
		data += "\nlinea:" + String.valueOf(linea);
		data += "\ncolumna:" + String.valueOf(columna);
		return data;
	}
}
