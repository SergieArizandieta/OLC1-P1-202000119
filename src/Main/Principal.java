package Main;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.File;
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
import javax.swing.JScrollPane;
import java.awt.TextArea;

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
		setBounds(100, 100, 1162, 648);

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
		button_analice.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Que onda");
			}
		});
		button_analice.setBounds(476, 11, 89, 23);
		contentPane.add(button_analice);

		JButton button_generate = new JButton("Generar Automatas");
		button_generate.setBounds(476, 42, 145, 23);
		contentPane.add(button_generate);

		JComboBox comboBoxImage = new JComboBox();
		comboBoxImage.setModel(new DefaultComboBoxModel(new String[] { "Arbol", "Siguientes" }));
		comboBoxImage.setBounds(767, 42, 193, 22);
		contentPane.add(comboBoxImage);

		JButton button_view = new JButton("Ver");
		button_view.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		button_view.setBounds(970, 42, 67, 23);
		contentPane.add(button_view);

		JTextPane textOut = new JTextPane();
		textOut.setEnabled(false);
		textOut.setBounds(489, 449, 636, 127);
		contentPane.add(textOut);

		JLabel lblNewLabel = new JLabel("Archivo de entrada");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel.setForeground(Color.BLACK);
		lblNewLabel.setBounds(342, 0, 124, 14);
		contentPane.add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("Salida");
		lblNewLabel_1.setForeground(Color.BLACK);
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel_1.setBounds(499, 424, 46, 14);
		contentPane.add(lblNewLabel_1);

		JPanel panel = new JPanel();
		panel.setBounds(767, 81, 367, 331);
		contentPane.add(panel);
		panel.setLayout(null);

		JLabel Label_img = new JLabel("");
		Label_img.setHorizontalAlignment(SwingConstants.LEFT);
		Label_img.setBounds(10, 11, 347, 309);
		panel.add(Label_img);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(42, 42, 2, 2);
		contentPane.add(scrollPane);
		
		TextArea textEditable = new TextArea();
		textEditable.setBounds(10, 21, 456, 555);
		contentPane.add(textEditable);
		
		Item_Open.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (fc.showOpenDialog(Item_Open) == JFileChooser.APPROVE_OPTION) {
					try {
						//System.out.println("Archivo seleccionado de: " + fc.getSelectedFile());
						String text = Files.readString(Path.of(fc.getSelectedFile().toString()));
						System.out.println(text);
						textEditable.setText(text);
					} catch (Exception e2) {

					}
				} else {
					System.out.println("NO se ecogio");
				}
			}
		});
		
	}
}
