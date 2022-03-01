package analizadores;

import java.io.FileWriter;

public class Reportes {
	//Reportes Start

	
	static String ReporteInicio="",ReporteFinal="",ReporteJuntar="";
	static String ReporteMamiferos="",ReporteMoluscos="";
	
	
	//Mami
	public static void Mami(String nombre,String nombre_cientigfico, String Reino, String huesos,int hueso,String Desplazamiento, String Sangre) {
		

		
		ReporteMamiferos= "<table class=\"steelBlueCols\">\r\n"
				+ "<thead>\r\n"
				+ "<tr>\r\n"
				+ "<th>Reino</th>\r\n"
				+ "<th>Nombre</th>\r\n"
				+ "<th>Nombre Cientifico</th>\r\n"
				+ "<th>Huesos</th>\r\n"
				+ "<th>Cantidad de Huesos</th>\r\n"
				+ "<th>Dezplasamiento</th>\r\n"
				+ "<th>Sangre</th>\r\n"
				+ "</tr>\r\n"
				+ "</thead>\r\n"
				+ "<tbody>\r\n"
				+ "<tr>\r\n";
		
		
	
		ReporteMamiferos+="<td>" + Reino +" </td> " ;
 					
		ReporteMamiferos+="<td>" + nombre +" </td> " ;
 		
		ReporteMamiferos+="<td>" +nombre_cientigfico  +" </td> " ;
 	
		ReporteMamiferos+="<td>" + huesos+" </td> " ;
		
		ReporteMamiferos+="<td>" + hueso +" </td> " ;
		
		ReporteMamiferos+="<td>" + Desplazamiento +" </td> " ;
		
		ReporteMamiferos+="<td>" + Sangre +" </td> " ;

		ReporteMamiferos += "</tr></tbody></table><br> ";
		}
	
	
	
	//moluscos
		public static void molu(String nombre,String nombre_cientigfico, String Reino , String Carece, String ExoEsqueleto,
				 String  Respiracion, String Concha) {
			

			
			ReporteMoluscos= "<table class=\"steelBlueCols\">\r\n"
					+ "<thead>\r\n"
					+ "<tr>\r\n"
					+ "<th>Reino</th>\r\n"
					+ "<th>Nombre</th>\r\n"
					+ "<th>Nombre Cientifico</th>\r\n"
					+ "<th>Huesos</th>\r\n"
					+ "<th>Cuerpos</th>\r\n"
					+ "<th>Respiracion</th>\r\n"
					+ "<th>Concha</th>\r\n"
					+ "</tr>\r\n"
					+ "</thead>\r\n"
					+ "<tbody>\r\n"
					+ "<tr>\r\n";
			
			
		
			ReporteMoluscos+="<td>" + Reino +" </td> " ;
	 					
			ReporteMoluscos+="<td>" + nombre +" </td> " ;
	 		
			ReporteMoluscos+="<td>" +nombre_cientigfico  +" </td> " ;
	 	
			ReporteMoluscos+="<td>" + Carece+" </td> " ;
			
			ReporteMoluscos+="<td>" + ExoEsqueleto +" </td> " ;
			
			ReporteMoluscos+="<td>" + Respiracion +" </td> " ;
			
			ReporteMoluscos+="<td>" + Concha +" </td> " ;
			
			ReporteMoluscos += "</tr></tbody></table><br> ";
			}
	
	
	//REPORTE JUNTA
	public static void ReporteJuntar() {
	
		ReporteJuntar = ReporteMamiferos + ReporteMoluscos;
		ReporteMostrar();
		
		
	}
	
	//Reporte mostrear 
	public static void ReporteMostrar() {

		try{
			
			FileWriter archivo = new FileWriter("ERRORES_202000119/Errores.html");
			archivo.write(ReporteInicio + ReporteJuntar +   ReporteFinal );
			
			archivo.close();

			}catch(Exception e) {}
			
		}
	
	//Reportes incio
		public static void Starrt() {

					ReporteInicio="<!DOCTYPE html>\r\n"
							+ "<html>\r\n"
							+ "<head>\r\n"
							+ "<meta charset=\"UTF-8\">\r\n"
							+ "<meta name=\"name\" content=\"Sergie Daniel Arizandieta Yol\">\r\n"
							+ "<meta name=\"description\" content=\"Reportes sobre opraciones de la practica 2\">\r\n"
							+ "<meta name=\"keywods\" content=\"Opraciones de usuarios en Java\">\r\n"
							+ "<meta name=\"robots\" content=\"Index, Follow\">\r\n"
							+ "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\r\n"
							+ "<link rel=\"stylesheet\" type=\"text/css\" href=\"css/styles.css\"/>\r\n"
							+ "<title>Reporte</title>\r\n"
							+ "</head>\r\n"
							+ "\r\n"
							+ "<body>\r\n"
							+ "\r\n"
							+ "<center>\r\n"
							+ "<h1 class=\"titulos\"><font size=\"30px\"><b> Reporte: por Sergie Arizandieta, 202000119</b></h1></center>";
					
					ReporteFinal="</body>\r\n"
							+ "</html>";
					
				}
}
