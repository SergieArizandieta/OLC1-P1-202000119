package analizadores;

public class Cadenas {
	public String string;
	public String name;

	
	public Cadenas(String name , String string) {
		this.name=name;
		this.string=string;
	}



	public String show() {
		String data = "";
		data += "\nnombre:" + name;
		data += "\ncadena:" + string;
		return data;
	}
}
