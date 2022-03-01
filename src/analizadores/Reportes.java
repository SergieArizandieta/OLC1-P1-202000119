package analizadores;

import java.io.FileWriter;
import java.util.LinkedList;

public class Reportes {
	//Reportes Start

	
	 String ReporteInicio="",ReporteFinal="",ReporteJuntar="";
	 String ReporteLexico="",ReporteSintactico="";
	 String ReporteMamiferos="",ReporteMoluscos="";
	
	
	public void GenerarReporte(LinkedList<errorList> errores, LinkedList<errorList> errores2) {
		Starrt();
		ReporteLexico(errores);
		ReporteSintactico(errores2);
		ReporteJuntar();
		ReporteMostrar();
	}
	
	public void ReporteLexico(LinkedList<errorList> errores) {
		//String tipo,String lexema,String descripcion, int linea, int columna
		
		ReporteLexico= "<table class=\"steelBlueCols\">\r\n"
				+ "<thead>\r\n"
				+ "<tr>\r\n"
				+ "<th>Linea</th>\r\n"
				+ "<th>Columna</th>\r\n"
				+ "<th>Lexema</th>\r\n"
				+ "<th>Tipo</th>\r\n"
				+ "<th>Descripcion</th>\r\n"
				+ "</tr>\r\n"
				+ "</thead>\r\n"
				+ "<tbody>\r\n"
				+ "<tr>\r\n";
		
		for (errorList errorList : errores) {
			ReporteLexico+= "<tr>\r\n";
			
			ReporteLexico+="<td>" + errorList.linea +" </td> " ;
				
			ReporteLexico+="<td>" + errorList.columna +" </td> " ;
	 		
			ReporteLexico+="<td>" + errorList.lexema+" </td> " ;
			
			ReporteLexico+="<td>" + errorList.tipo +" </td> " ;
			
			ReporteLexico+="<td>" + errorList.descripcion +" </td> " ;
			
			ReporteLexico+= "</tr>\r\n";
			
		}
		ReporteLexico += "</tbody></table><br> ";
	}
	
	public void ReporteSintactico(LinkedList<errorList> errores) {
		ReporteSintactico= "</br></br></br><table class=\"steelBlueCols\">\r\n"
				+ "<thead>\r\n"
				+ "<tr>\r\n"
				+ "<th>Linea</th>\r\n"
				+ "<th>Columna</th>\r\n"
				+ "<th>Lexema</th>\r\n"
				+ "<th>Tipo</th>\r\n"
				+ "<th>Descripcion</th>\r\n"
				+ "</tr>\r\n"
				+ "</thead>\r\n"
				+ "<tbody>\r\n"
				+ "<tr>\r\n";
		
		for (errorList errorList : errores) {
			ReporteSintactico+= "<tr>\r\n";
			
			ReporteSintactico+="<td>" + errorList.linea +" </td> " ;
				
			ReporteSintactico+="<td>" + errorList.columna +" </td> " ;
	 		
	 	
			ReporteSintactico+="<td>" + errorList.lexema+" </td> " ;
			
			ReporteSintactico+="<td>" + errorList.tipo +" </td> " ;
			
			ReporteSintactico+="<td>" + errorList.descripcion +" </td> " ;
			
			ReporteSintactico+= "</tr>\r\n";
			
		}
		ReporteSintactico += "</tbody></table><br></br></br> ";
	}
	

	
	
	//REPORTE JUNTA
	public  void ReporteJuntar() {
	
		ReporteJuntar = ReporteLexico + ReporteSintactico;

			
	}
	
	//Reporte mostrear 
	public  void ReporteMostrar() {

		try{
			
			FileWriter archivo = new FileWriter("./ERRORES_202000119/Errores.html");
			archivo.write(ReporteInicio + ReporteJuntar +   ReporteFinal );
			
			archivo.close();

			}catch(Exception e) {}
			
		}
	
	//Reportes incio
		public  void Starrt() {

					ReporteInicio="<!DOCTYPE html>\r\n"
							+ "<html>\r\n"
							+ "<head>\r\n"
							+ "<meta charset=\"UTF-8\">\r\n"
							+ "<meta name=\"name\" content=\"Sergie Daniel Arizandieta Yol\">\r\n"
							+ "<meta name=\"description\" content=\"Reportes de Errores\">\r\n"
							+ "<meta name=\"keywods\" content=\"Creacion de AFDS\">\r\n"
							+ "<meta name=\"robots\" content=\"Index, Follow\">\r\n"
							+ "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\r\n"
							+ "<link rel=\"stylesheet\" type=\"text/css\" href=\"css/styles.css\"/>\r\n"
							+ "<title>Reporte</title>\r\n"
							+ "</head>\r\n"
							+ "\r\n"
							+ "<body>\r\n"
							+ "\r\n"
							+ "<center>\r\n"
							+ "<h1 class=\"titulos\"><font size=\"30px\"><b>Reportes de Errores </b></h1></center><center>";
					
					ReporteFinal="</center> <marquee behavior=\"alternate\">\r\n"
							+ "   Sergie Daniel Arizandieta Yol - 202000119\r\n"
							+ "  </marquee></body>\r\n"
							+ "</html>";
					
				}
}
