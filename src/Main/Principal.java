package Main;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.awt.event.ActionEvent;
import javax.swing.JTextPane;
import java.awt.Color;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import java.awt.TextArea;

import analizadores.Analizador_Lexico;
import analizadores.Analizador_sintactico;
import analizadores.errorList;
import analizadores.tokens;

@SuppressWarnings("serial")
public class Principal extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Principal frame = new Principal();
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
	public Principal() {
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

		JTextPane textOut = new JTextPane();
		textOut.setEnabled(false);
		textOut.setBounds(607, 478, 776, 127);
		contentPane.add(textOut);

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

		// Acciones------------------------------------------------------------------------------------
		// Menu------------------------------------------------------------------------------------
		// Abrir
		Item_Open.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (fc.showOpenDialog(Item_Open) == JFileChooser.APPROVE_OPTION) {
					try {
						// System.out.println("Archivo seleccionado de: " + fc.getSelectedFile());
						String text = Files.readString(Path.of(fc.getSelectedFile().toString()));
						//System.out.println(text);
						System.out.println(fc.getSelectedFile());
						textEditable.setText(text);
						label_ruta.setText(fc.getSelectedFile().toString());
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
				label_ruta.setText("Null");
				textEditable.setText("");
			}
		});

		// Botones------------------------------------------------------------------------------------
		button_analice.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (label_ruta.getText() != "Null") {
					try {
						System.out.println("\n\n\n");
						Analizador_Lexico lexico = new Analizador_Lexico(
								new BufferedReader(new FileReader(label_ruta.getText())));
						Analizador_sintactico sintactico = new Analizador_sintactico(lexico);
						sintactico.parse();
						//System.out.println("\n\n ***Reporte de errores encontrados ");
						/*
						 * for (errorList errore : Analizador_Lexico.errores) {
						 * System.out.println(errore.show()); }
						 */
						System.out.println("Tokens");
						for (tokens token : Analizador_Lexico.TokensList) {
							System.out.println(token.show());
						}
						for (errorList errore : Analizador_sintactico.errores) {
							System.out.println(errore.show());
						}
						
					} catch (Exception e1) {
					}

				} else {
					JOptionPane.showMessageDialog(null, "Se debe abrir un archivo primero");
				}
			}
		});
	}
}
