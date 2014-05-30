package merlin.gui;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import java.awt.Color;

import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JPasswordField;
import javax.swing.JCheckBox;


public class MerlinLogin {

	private JFrame frmLogin;
	private JTextField textField;
	private JButton btnLogin;
	private JLabel label;
	private JLabel lblWelcomeText;
	private JTextField txtVorname;
	private JTextField txtEmail;
	private JTextField txtName;
	private JPasswordField txtPasswort;
	
	/**
	 * Launch the application.
	 * @throws UnsupportedLookAndFeelException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 * @throws ClassNotFoundException 
	 */
	public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
		
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MerlinLogin window = new MerlinLogin();
					window.frmLogin.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MerlinLogin() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmLogin = new JFrame();
		frmLogin.setTitle("MERLIN - Anmeldung");
		frmLogin.setResizable(false);
		frmLogin.setForeground(Color.WHITE);
		frmLogin.setBackground(Color.WHITE);
		frmLogin.setBounds(200, 200, 600, 300);
		frmLogin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmLogin.getContentPane().setLayout(null);
		
		JButton btnRegister = new JButton("Registrieren");
		btnRegister.setBounds(334, 198, 107, 23);
		frmLogin.getContentPane().add(btnRegister);
		
		btnLogin = new JButton("Einloggen");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				
				/* An DB Anmelden */ 
			}
		});
		btnLogin.setBounds(406, 29, 86, 23);
		frmLogin.getContentPane().add(btnLogin);
		
		label = new JLabel("");
		label.setBounds(39, 11, 46, 14);
		frmLogin.getContentPane().add(label);
		
		lblWelcomeText = new JLabel("<html> Herzlich Willkommen auf der Merlinseite f\u00FCr Vogelbeobachtungen Sie haben einen Vogel beobachtet und wollen dies abspecihern? <br> Dann registrieren Sie sich jetzt oder loggen Sie sich mit Ihrem Username und Passwort ein </html>");
		lblWelcomeText.setBounds(15, 83, 184, 149);
		frmLogin.getContentPane().add(lblWelcomeText);
		
		txtVorname = new JTextField();
		txtVorname.setBounds(308, 136, 184, 20);
		frmLogin.getContentPane().add(txtVorname);
		txtVorname.setColumns(10);
		
		txtEmail = new JTextField();
		txtEmail.setBounds(308, 167, 184, 20);
		frmLogin.getContentPane().add(txtEmail);
		txtEmail.setColumns(10);
		
		txtName = new JTextField();
		txtName.setBounds(308, 105, 184, 20);
		frmLogin.getContentPane().add(txtName);
		txtName.setColumns(10);
		
		JLabel lblName = new JLabel("Name");
		lblName.setBounds(228, 108, 70, 14);
		frmLogin.getContentPane().add(lblName);
		
		JLabel lblVorname = new JLabel("Vorname");
		lblVorname.setBounds(228, 139, 70, 14);
		frmLogin.getContentPane().add(lblVorname);
		
		JLabel lblEmail = new JLabel("E-Mail");
		lblEmail.setLabelFor(txtName);
		lblEmail.setBounds(228, 170, 70, 14);
		frmLogin.getContentPane().add(lblEmail);
		
		JLabel lblPasswort = new JLabel("Passwort");
		lblPasswort.setBounds(222, 15, 46, -3);
		frmLogin.getContentPane().add(lblPasswort);
		
		JLabel lblPasswort_1 = new JLabel("Passwort");
		lblPasswort_1.setBounds(312, 11, 84, 14);
		frmLogin.getContentPane().add(lblPasswort_1);
		
		JTextField txtUsername = new JTextField();
		txtUsername.setBounds(210, 30, 86, 20);
		frmLogin.getContentPane().add(txtUsername);
		txtUsername.setColumns(10);
		
		JLabel lblUsername = new JLabel("Username");
		lblUsername.setBounds(210, 11, 86, 14);
		frmLogin.getContentPane().add(lblUsername);
		
		txtPasswort = new JPasswordField();
		txtPasswort.setBounds(308, 30, 86, 20);
		frmLogin.getContentPane().add(txtPasswort);
		
		JCheckBox chkRememberUser = new JCheckBox("Nutzernamen merken");
		chkRememberUser.setBounds(209, 57, 139, 23);
		frmLogin.getContentPane().add(chkRememberUser);
		
	}
}
