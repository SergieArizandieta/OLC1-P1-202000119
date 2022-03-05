package analizadores;

public class Cadenas {
	public String string;
	public String name;
	public boolean validacion;
	
	public Cadenas(String name , String string) {
		this.name=name;
		this.string=string;
		this.validacion = false;
	}



	public String show() {
		String data = "";
		data += "\nnombre:" + name;
		data += "\ncadena:" + string.length();
		return data;
	}
}
