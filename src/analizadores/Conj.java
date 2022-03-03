package analizadores;

public class Conj {
	String tipo, nombre,var1,var2,text;
	Integer CharVar1,CharVar2;
	String[] letras;
	Boolean validado = false;
	
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
		}else {
			if(var1.equals(" ")) {
				this.CharVar1 = 32;
			}else {
				char character1=this.var1.charAt(0);
				this.CharVar1 = (int) character1;
			}
			
			char character2=this.var1.charAt(0);
			this.CharVar2 = (int) character2;
			
			if(CharVar1>CharVar2) {
				System.out.println("Rango invalido");
			}
		}
	}
	
	public Boolean validar(String letter) {
		validado = false;
		if(isRank()) {
			ValidacionRango(letter);
		}else {
			validacionComas(letter);
		}
		return validado;
	}
	
	public void validacionComas(String letter) {
		for (String i : letras) {
			if(i.equals(letter)) {
				validado = true;
				break;
			}
		}
	}
	
	public void ValidacionRango(String letter) {
		System.out.println("________________________");

		System.out.println(var1);
		System.out.println(var2);
		char character=letter.charAt(0);
		int numberLetter = (int) character;
		System.out.println(character + "/////" + numberLetter);
		System.out.println("________________________");
	}
	
	public void isNum() {
		
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
				data +=   contenido + "-";
			}
		}
		return data;
	}
}
