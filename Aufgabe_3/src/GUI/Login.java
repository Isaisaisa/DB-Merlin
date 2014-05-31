package GUI;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JTextField;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JPanel;
import org.eclipse.wb.swing.FocusTraversalOnArray;
import java.awt.Component;
import javax.swing.JScrollPane;


public class Login {

	private JFrame frame;
	private JTextField textField;
	private JButton btnEinloggen;
	private JLabel label;
	private JLabel lblHerzlichWillkommenAuf;
	private JTextField txtVorname;
	private JTextField txtEmail;
	private JTextField txtName;
	private JTextField txtPasswort;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login window = new Login();
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
	public Login() {
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
		btnEinloggen.setBounds(392, 29, 101, 23);
		frame.getContentPane().add(btnEinloggen);

		label = new JLabel("");
		label.setBounds(39, 11, 46, 14);
		frame.getContentPane().add(label);

		lblHerzlichWillkommenAuf = new JLabel("<html> Herzlich Willkommen auf der Merlinseite f\u00FCr Vogelbeobachtungen Sie haben einen Vogel beobachtet und wollen dies abspecihern? <br> Dann registrieren Sie sich jetzt oder loggen Sie sich mit Ihrem Username und Passwort ein </html>");
		lblHerzlichWillkommenAuf.setBounds(23, 61, 184, 149);
		frame.getContentPane().add(lblHerzlichWillkommenAuf);

		txtVorname = new JTextField();
		txtVorname.setBounds(308, 114, 184, 20);
		frame.getContentPane().add(txtVorname);
		txtVorname.setColumns(10);

		txtEmail = new JTextField();
		txtEmail.setBounds(308, 145, 184, 20);
		frame.getContentPane().add(txtEmail);
		txtEmail.setColumns(10);

		txtName = new JTextField();
		txtName.setBounds(308, 83, 184, 20);
		frame.getContentPane().add(txtName);
		txtName.setColumns(10);

		JLabel lblName = new JLabel("Name");
		lblName.setBounds(228, 86, 70, 14);
		frame.getContentPane().add(lblName);

		JLabel lblVorname = new JLabel("Vorname");
		lblVorname.setBounds(228, 117, 70, 14);
		frame.getContentPane().add(lblVorname);

		JLabel lblEmail = new JLabel("E-Mail");
		lblEmail.setLabelFor(txtName);
		lblEmail.setBounds(228, 148, 70, 14);
		frame.getContentPane().add(lblEmail);

		txtPasswort = new JTextField();
		txtPasswort.setBounds(290, 30, 86, 20);
		frame.getContentPane().add(txtPasswort);
		txtPasswort.setColumns(10);

		JLabel lblPasswort = new JLabel("Passwort");
		lblPasswort.setBounds(222, 15, 46, -3);
		frame.getContentPane().add(lblPasswort);

		JLabel lblPasswort_1 = new JLabel("Passwort");
		lblPasswort_1.setBounds(290, 11, 84, 14);
		frame.getContentPane().add(lblPasswort_1);

		JTextField txtUsername = new JTextField();
		txtUsername.setBounds(194, 30, 86, 20);
		frame.getContentPane().add(txtUsername);
		txtUsername.setColumns(10);

		JLabel lblUsername = new JLabel("Username");
		lblUsername.setBounds(194, 11, 86, 14);
		frame.getContentPane().add(lblUsername);
		frame.setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[]{btnBeobachtung, btnEinloggen, txtVorname, label, txtEmail, txtName, lblHerzlichWillkommenAuf, lblName, frame.getContentPane(), lblVorname, lblEmail, txtPasswort, lblPasswort, lblPasswort_1, txtUsername, lblUsername}));

	}
}