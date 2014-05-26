import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.BorderLayout;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JTable;
import java.awt.Color;
import javax.swing.JMenu;
import javax.swing.JTextArea;
import javax.swing.JLabel;


public class GUI {

	private JFrame frame;
	private JTextField textField;
	private JButton btnEinloggen;
	private JLabel label;
	private JLabel lblHerzlichWillkommenAuf;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI window = new GUI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public GUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setForeground(Color.WHITE);
		frame.setBackground(Color.WHITE);
		frame.setBounds(100, 100, 494, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JButton btnBeobachtung = new JButton("Registrieren");
		btnBeobachtung.setBounds(268, 96, 107, 23);
		frame.getContentPane().add(btnBeobachtung);
		
		btnEinloggen = new JButton("Einloggen");
		btnEinloggen.setBounds(98, 96, 107, 23);
		frame.getContentPane().add(btnEinloggen);
		
		label = new JLabel("");
		label.setBounds(39, 11, 46, 14);
		frame.getContentPane().add(label);
		
		lblHerzlichWillkommenAuf = new JLabel("<html> Herzlich Willkommen auf der Merlinseite f\u00FCr Vogelbeobachtungen Sie haben einen Vogel beobachtet und wollen dies abspecihern? <br> Dann registrieren Sie sich jetzt oder loggen Sie sich mit Ihrem Username und Passwort ein </html>");
		lblHerzlichWillkommenAuf.setBounds(49, 11, 396, 74);
		frame.getContentPane().add(lblHerzlichWillkommenAuf);
		
	}
	
	
	private void isPressed(){
		
	}
}
