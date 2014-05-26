import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JTextField;
import java.awt.Color;
import javax.swing.JLabel;


public class GUI {

	private JFrame frame;
	private JTextField textField;
	private JButton btnEinloggen;
	private JLabel label;
	private JLabel lblHerzlichWillkommenAuf;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_4;
	private JTextField textField_5;

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
		frame.setBounds(100, 100, 600, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JButton btnBeobachtung = new JButton("Registrieren");
		btnBeobachtung.setBounds(334, 176, 107, 23);
		frame.getContentPane().add(btnBeobachtung);
		
		btnEinloggen = new JButton("Einloggen");
		btnEinloggen.setBounds(406, 29, 86, 23);
		frame.getContentPane().add(btnEinloggen);
		
		label = new JLabel("");
		label.setBounds(39, 11, 46, 14);
		frame.getContentPane().add(label);
		
		lblHerzlichWillkommenAuf = new JLabel("<html> Herzlich Willkommen auf der Merlinseite f\u00FCr Vogelbeobachtungen Sie haben einen Vogel beobachtet und wollen dies abspecihern? <br> Dann registrieren Sie sich jetzt oder loggen Sie sich mit Ihrem Username und Passwort ein </html>");
		lblHerzlichWillkommenAuf.setBounds(23, 61, 184, 149);
		frame.getContentPane().add(lblHerzlichWillkommenAuf);
		
		textField_1 = new JTextField();
		textField_1.setBounds(308, 114, 184, 20);
		frame.getContentPane().add(textField_1);
		textField_1.setColumns(10);
		
		textField_2 = new JTextField();
		textField_2.setBounds(308, 145, 184, 20);
		frame.getContentPane().add(textField_2);
		textField_2.setColumns(10);
		
		textField_3 = new JTextField();
		textField_3.setBounds(308, 83, 184, 20);
		frame.getContentPane().add(textField_3);
		textField_3.setColumns(10);
		
		JLabel lblName = new JLabel("Name");
		lblName.setBounds(228, 86, 70, 14);
		frame.getContentPane().add(lblName);
		
		JLabel lblVorname = new JLabel("Vorname");
		lblVorname.setBounds(228, 117, 70, 14);
		frame.getContentPane().add(lblVorname);
		
		JLabel lblEmail = new JLabel("E-mail");
		lblEmail.setLabelFor(textField_3);
		lblEmail.setBounds(228, 148, 70, 14);
		frame.getContentPane().add(lblEmail);
		
		textField_4 = new JTextField();
		textField_4.setBounds(310, 30, 86, 20);
		frame.getContentPane().add(textField_4);
		textField_4.setColumns(10);
		
		JLabel lblPasswort = new JLabel("Passwort");
		lblPasswort.setBounds(222, 15, 46, -3);
		frame.getContentPane().add(lblPasswort);
		
		JLabel lblPasswort_1 = new JLabel("Passwort");
		lblPasswort_1.setBounds(312, 11, 84, 14);
		frame.getContentPane().add(lblPasswort_1);
		
		textField_5 = new JTextField();
		textField_5.setBounds(210, 30, 86, 20);
		frame.getContentPane().add(textField_5);
		textField_5.setColumns(10);
		
		JLabel lblUsername = new JLabel("Username");
		lblUsername.setBounds(210, 11, 86, 14);
		frame.getContentPane().add(lblUsername);
		
	}
	
	
	
}
