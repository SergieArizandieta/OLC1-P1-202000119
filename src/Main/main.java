package Main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.DataOutput;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.rmi.Naming;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.json.JSONArray;
import org.json.JSONObject;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;

import analizadores.Analizador_Lexico;
import analizadores.Analizador_sintactico;
import analizadores.Cadenas;
import analizadores.Conj;
import analizadores.Reportes;
import analizadores.SimpleER;
import analizadores.errorList;
import javax.swing.JTextArea;

public class main extends JFrame {

	boolean analizado = false;
	boolean generado = false;
	boolean AutomataCreado = false;
	private JPanel contentPane;

	Analizador_Lexico lexico;
	Analizador_sintactico sintactico;
	private JButton button_ComprobarCadeas;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					main frame = new main();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public main() {
		JFileChooser fc = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter("*.exp", "exp");
		fc.setFileFilter(filter);
		fc.setCurrentDirectory(new File("./Test"));

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1438, 676);

		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu Menu_archive = new JMenu("Archivo");
		menuBar.add(Menu_archive);

		JMenuItem Item_Open = new JMenuItem("Abrir");
		Menu_archive.add(Item_Open);

		JMenuItem Item_SaveHow = new JMenuItem("Guardar Como");
		Menu_archive.add(Item_SaveHow);

		JMenuItem Item_Save = new JMenuItem("Guardar");
		Menu_archive.add(Item_Save);

		JMenuItem Item_New = new JMenuItem("Nuevo");
		Menu_archive.add(Item_New);
		contentPane = new JPanel();
		contentPane.setBackground(Color.LIGHT_GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JButton button_analice = new JButton("Analizar");
		button_analice.setBounds(607, 54, 89, 23);
		contentPane.add(button_analice);

		JButton button_generate = new JButton("Generar Automatas");

		button_generate.setBounds(607, 88, 145, 23);
		contentPane.add(button_generate);

		JComboBox comboBoxImage = new JComboBox();
		comboBoxImage.setModel(new DefaultComboBoxModel(new String[] { "Arbol", "Siguientes" }));
		comboBoxImage.setBounds(1007, 54, 193, 22);
		contentPane.add(comboBoxImage);

		JButton button_view = new JButton("Ver");
		button_view.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		button_view.setBounds(1210, 54, 67, 23);
		contentPane.add(button_view);

		JLabel lblNewLabel = new JLabel("Ruta:");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel.setForeground(Color.BLACK);
		lblNewLabel.setBounds(14, 23, 46, 14);
		contentPane.add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("Salida");
		lblNewLabel_1.setForeground(Color.BLACK);
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel_1.setBounds(607, 453, 46, 14);
		contentPane.add(lblNewLabel_1);

		JPanel panel = new JPanel();
		panel.setBounds(959, 88, 427, 361);
		contentPane.add(panel);
		panel.setLayout(null);

		JLabel Label_img = new JLabel("");
		Label_img.setHorizontalAlignment(SwingConstants.LEFT);
		Label_img.setBounds(10, 11, 407, 339);
		panel.add(Label_img);

		TextArea textEditable = new TextArea();
		textEditable.setBounds(14, 50, 569, 555);
		contentPane.add(textEditable);

		JLabel label_ruta = new JLabel("Null");
		label_ruta.setForeground(Color.BLACK);
		label_ruta.setFont(new Font("Tahoma", Font.PLAIN, 14));
		label_ruta.setBounds(52, 23, 569, 14);
		contentPane.add(label_ruta);

		button_ComprobarCadeas = new JButton("Comprobar Cadenas");

		button_ComprobarCadeas.setBounds(607, 122, 161, 23);
		contentPane.add(button_ComprobarCadeas);

		TextArea textOut = new TextArea();
		textOut.setEditable(false);
		textOut.setBounds(607, 472, 779, 133);
		contentPane.add(textOut);

		// Acciones------------------------------------------------------------------------------------
		// Menu------------------------------------------------------------------------------------
		// Abrir
		Item_Open.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (fc.showOpenDialog(Item_Open) == JFileChooser.APPROVE_OPTION) {
					try {
						// System.out.println("Archivo seleccionado de: " + fc.getSelectedFile());
						// String text = Files.readString(Path.of(fc.getSelectedFile().toString()));
						// System.out.println(fc.getSelectedFile());

						String text = readUnicodeClassic(fc.getSelectedFile().toString());
						textEditable.setText(text);
						label_ruta.setText(fc.getSelectedFile().toString());
						analizado = false;
						generado = false;
						AutomataCreado = false;
					} catch (Exception e2) {

					}
				} else {
					System.out.println("NO se ecogio");
				}
			}
		});

		// Guardar como
		Item_SaveHow.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (fc.showSaveDialog(Item_SaveHow) == JFileChooser.APPROVE_OPTION) {
					try {
						System.out.println(" se ecogio");
						System.out.println(fc.getSelectedFile());

						File fichero = fc.getSelectedFile();
						try (FileWriter fw = new FileWriter(fichero)) {
							fw.write(textEditable.getText());
							JOptionPane.showMessageDialog(null, "Se guardo el nuevo archivo.");
							analizado = false;
							AutomataCreado = false;
							generado = false;
						}
						label_ruta.setText(fc.getSelectedFile().toString());
					} catch (Exception e2) {
						System.out.println("paso");
					}
				} else {
					System.out.println("NO se ecogio");
				}
			}
		});

		// Guardar
		Item_Save.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (label_ruta.getText() == "Null") {
					System.out.println("Primero debe guardar el archivo en el sistema");
					JOptionPane.showMessageDialog(null, "Primero debe guardar el archivo en el sistema");
				} else {
					try (FileWriter fw = new FileWriter(label_ruta.getText())) {
						analizado = false;
						AutomataCreado = false;
						generado = false;
						fw.write(textEditable.getText());
						JOptionPane.showMessageDialog(null, "Archivo guardado.");
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			}
		});

		// Nurvo
		Item_New.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				analizado = false;
				AutomataCreado = false;
				generado = false;
				label_ruta.setText("Null");
				textEditable.setText("");
			}
		});

		// Botones------------------------------------------------------------------------------------
		button_analice.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent e) {
				if (label_ruta.getText() != "Null") {
					try {
						Reportes reportes = new Reportes();
						Boolean Errores = false;
						System.out.println("\n\n\n");
						lexico = new Analizador_Lexico(new BufferedReader(new FileReader(label_ruta.getText())));

						sintactico = new Analizador_sintactico(lexico);
						sintactico.parse();
						System.out.println("===============ERRORES ===================");
						try {
							if (lexico.errores.size() > 0 || sintactico.errores.size() > 0) {
								if (lexico.errores.size() > 0) {
									System.out.println("=======ERRORES LEXICOS=========");
									JOptionPane.showMessageDialog(null,
											"Ocurrio un error Lexico, revisar tabla de errores");
									for (errorList errore : lexico.errores) {

										System.out.println(errore.show());
									}
								}
								if (sintactico.errores.size() > 0) {
									System.out.println("=======ERRORES SINTACTICOS=========");
									JOptionPane.showMessageDialog(null,
											"Ocurrio un error Sintactico, revisar tabla de errores");
									for (errorList errore : sintactico.errores) {

										System.out.println(errore.show());
									}
								}
								Errores = true;
								analizado = false;
								AutomataCreado = false;

							} else {
								System.out.println("Todo Bien!!!");
								Errores = false;

							}

							System.out.println("=======ERRORES FIN=========");

							reportes.GenerarReporte(lexico.errores, sintactico.errores);
							// System.out.println("\n\n ***Reporte de errores encontrados ");
							/*
							 * 
							 * 
							 * 
							 * 
							 * for (Conj conjunto : sintactico.ConjList) {
							 * System.out.println(conjunto.show()); } for (Cadenas cadenas :
							 * sintactico.CadenasList) { System.out.println(cadenas.show()); }
							 * 
							 * 
							 * 
							 */
							/*
							 * System.out.println("Tokens"); for (tokens token : lexico.TokensList) {
							 * System.out.println(token.show()); }
							 */

							if (Errores == false) {
								System.out.println("\n\nMostrando ERs");

								for (SimpleER er : sintactico.ERList) {
									// er.estado_inicial();
									// System.out.println("\nArbol tiene hojas: " + er.hojas);
									System.out.println("=========ER=========  " + er.name);
									// er.initialize();
									// er.showList();

									er.GestionArbol();

									// er.showListInverse();
								}
								System.out.println("=====Analidis completado=====");
								analizado = true;

								// System.out.println("=====================================");
								JOptionPane.showMessageDialog(null, "Archivo analizado con exito");
							}
						} catch (Exception e2) {
							JOptionPane.showMessageDialog(null,
									"Ocurrio un error, revisar archivo de entreda y tabla de errores");
							System.out.println(e2);
						}
					} catch (Exception e1) {
						System.out.println(e1);
					}

				} else {
					JOptionPane.showMessageDialog(null, "Se debe abrir un archivo primero");
				}
			}
		});
		// Genrar grafos
		button_generate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (analizado) {
					if (generado == false) {
						JOptionPane.showMessageDialog(null, "Se crearon Automatas correctamente");
						for (SimpleER er : sintactico.ERList) {
							System.out.println("=========ER=========  " + er.name);
							// er.GenrarGrafo();
							// er.verGrafo();
						}
						generado = true;
						System.out.println("=====Creacion de Automatas finalizada=====");
						AutomataCreado = true;
					} else {
						JOptionPane.showMessageDialog(null, "Ya se han generado Automatas");
					}
				} else {
					JOptionPane.showMessageDialog(null, "Debe analisar el archivo primero");
				}
			}
		});

		// comprobar cadenas
		button_ComprobarCadeas.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent e) {

				try {

					if (AutomataCreado) {
						System.out.println("==== Validando Cadenas ====");

						if (sintactico.CadenasList.size() > 0) {

							boolean encontrado = false;

							for (Cadenas i : sintactico.CadenasList) {
								encontrado = false;

								for (SimpleER er : sintactico.ERList) {
									if (i.name.equals(er.name)) {
										System.out.println("===== cadena: " + i.string + " para: " + er.name + "=====");
										er.ValidarCadena(i.string, sintactico.ConjList, i);
										encontrado = true;
										break;
									}

								}
								if (encontrado == false) {
									System.out.println("No se encontro er: " + i.name + " para la cadena: " + i.string);

								}
							}
							String text = "";
							JSONArray Main = new JSONArray();
							JSONObject sub;
							for (Cadenas i : sintactico.CadenasList) {
								sub = new JSONObject();
								
								if (i.validacion) {
									text += "La cadena: " + i.string + " es VALIDA con la ER: " + i.name + "\n";
									System.out.println("La cadena: " + i.string + " es VALIDA con la ER: " + i.name);
									sub.put("Resultado", "Cadena Valida");
									sub.put("ExpresionRegular", i.name);
									sub.put("Valor", i.string);
									
									
								} else {
									text += "La cadena: " + i.string + " es INVALIDA con la ER: " + i.name + "\n";
									System.out.println("La cadena: " + i.string + " es INVALIDA con la ER: " + i.name);
									sub.put("Resultado", "Cadena Invalida");
									sub.put("ExpresionRegular", i.name);
									sub.put("Valor", i.string);
									
								}
								
								Main.put(sub);
							}

							


							String Salida = new GsonBuilder().setPrettyPrinting().create().toJson(new JsonParser().parse(Main.toString()));
							
							//System.out.println(Salida);
							//System.out.println(json);
							//System.out.println(ja);
							
							Create_File("SALIDAS_202000119\\Salida.JSON", Salida);
							JOptionPane.showMessageDialog(null, "Se creo archivo de salida");
							System.out.println("======= Se creo archivo de salida ======");
							
							
							

							textOut.setText(text);

						} else {
							System.out.println("no hay cadenas");
						}
					} else {
						JOptionPane.showMessageDialog(null, "Se deben genera Automatas para validar cadenas");
					}
				} catch (Exception e2) {
					System.out.println(e2);
				}
			}

		});

	}

	public String readUnicodeClassic(String fileName) {

		File file = new File(fileName);

		try (FileInputStream fis = new FileInputStream(file);
				InputStreamReader isr = new InputStreamReader(fis, StandardCharsets.UTF_8);
				BufferedReader reader = new BufferedReader(isr)) {

			String str;
			String cadea = "";
			while ((str = reader.readLine()) != null) {
				cadea += str + "\n";

			}
			return cadea;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}
	
	private void Create_File(String route, String contents) {

		FileWriter fw = null;
		PrintWriter pw = null;
		try {
			fw = new FileWriter(route);
			pw = new PrintWriter(fw);
			pw.write(contents);
			pw.close();
			fw.close();
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		} finally {
			if (pw != null)
				pw.close();
		}

	}

}
