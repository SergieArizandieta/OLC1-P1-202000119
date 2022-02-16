package Main;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextPane;
import java.awt.Color;

public class Main extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main frame = new Main();
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
	public Main() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1162, 648);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnNewMenu = new JMenu("Archivo");
		menuBar.add(mnNewMenu);
		
		JMenuItem Item_Open = new JMenuItem("Abrir");
		Item_Open.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {System.out.println("Esta vivo");
			}
		});
		mnNewMenu.add(Item_Open);
		
		JMenuItem Item_SaveHow = new JMenuItem("Guardar Como");
		mnNewMenu.add(Item_SaveHow);
		
		JMenuItem Item_Save = new JMenuItem("Guardar");
		mnNewMenu.add(Item_Save);
		
		JMenuItem Item_New = new JMenuItem("Nuevo");
		mnNewMenu.add(Item_New);
		
		JMenu mnNewMenu_1 = new JMenu("Generar");
		menuBar.add(mnNewMenu_1);
		
		JMenuItem Item_Automatas = new JMenuItem("Automatas");
		mnNewMenu_1.add(Item_Automatas);
		contentPane = new JPanel();
		contentPane.setBackground(Color.LIGHT_GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JTextPane textPane = new JTextPane();
		textPane.setBounds(10, 21, 456, 500);
		contentPane.add(textPane);
		
		JButton btnNewButton = new JButton("New button");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {System.out.println("Que onda");
			}
		});
		btnNewButton.setBounds(493, 31, 89, 23);
		contentPane.add(btnNewButton);
	}
}
