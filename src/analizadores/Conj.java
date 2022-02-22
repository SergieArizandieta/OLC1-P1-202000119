package analizadores;

public class Conj {
	String tipo, nombre,var1,var2,text;
	String[] letras;
	
	public Conj(String tipo , String nombre,String var1,String var2,String text) {
	
		this.tipo=tipo;
		this.nombre=nombre;
		this.var1=var1;
		this.var2=var2;
		this.text=text;
		this.define();
	
	}
	
	public void define() {
		if(isRank() == false) {
			letras = text.split(",");
		}
	}
	
	public Boolean isRank() {
		if(this.tipo.equals("rango")) {
			return true;
			
		}else if(this.tipo.equals("comas")) {
			return false;
		}
		return null;
	}

	public String show() {
		String data = "";
		data += "\nnombre:" + String.valueOf(nombre);
		data += "\ntipo:" + tipo;
		if(isRank()) {
			data += "\nrango: " + var1 + " a " + var2;
		}else{
			data += "\nletras: ";
			for (String contenido : letras) {
				data +=   contenido + ",";
			}
		}
		return data;
	}
}
