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

import merlin.logic.exception.IllegalPasswordException;
import merlin.logic.impl.MerlinLogic;

import java.awt.Font;
import java.awt.Dialog.ModalExclusionType;
import merlin.gui.enums.ExitCode;
import static merlin.gui.enums.ExitCode.*;


public class MerlinLogin {

	private JFrame frmLogin;
	private JTextField txtUsernameReg;
	private JButton btnLogin;
	private JLabel label;
	private JLabel lblWelcomeText;
	private JTextField txtVorname;
	private JTextField txtEmail;
	private JTextField txtName;
	private JPasswordField txtPasswordLog;
	private JPasswordField txtPasswordRegBest;
	private JPasswordField txtPasswordReg;
	private JTextField txtUsernameLog;
	
	private static ExitCode exitCode = DIALOG_ABORTED;
	
	/**
	 * Launch the application.
	 * @throws UnsupportedLookAndFeelException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 * @throws ClassNotFoundException
	 */
	public static ExitCode main() throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
		
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MerlinLogin window = new MerlinLogin();
					DatabaseSetup.showDialog();
					window.frmLogin.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		return exitCode;
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
		frmLogin.setModalExclusionType(ModalExclusionType.APPLICATION_EXCLUDE);
		frmLogin.setTitle("MERLIN - Anmeldung");
		frmLogin.setResizable(false);
		frmLogin.setForeground(Color.WHITE);
		frmLogin.setBackground(Color.WHITE);
		frmLogin.setBounds(200, 200, 600, 400);
		frmLogin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmLogin.getContentPane().setLayout(null);
		
		//TODO GUI f�r IllegalPasswordException
		JButton btnRegister = new JButton("Registrieren");
		btnRegister.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					MerlinLogic.insertBirdwatcher(txtName.getText().trim(), txtVorname.getText().trim(), txtUsernameReg.getText().trim(), txtPasswordReg.getPassword(), txtPasswordRegBest.getPassword(), txtEmail.getText().trim());
				} catch (IllegalPasswordException e) {
					System.out.println("Passw�rter sind nicht identische, bitte neu eingeben");
					e.printStackTrace();
				}
				exitCode = REGISTER_BUTTON_PUSHED;
			}
		});
		btnRegister.setBounds(308, 323, 107, 23);
		frmLogin.getContentPane().add(btnRegister);
		
		btnLogin = new JButton("Einloggen");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
//				TODO login �berpr�fen
//				Merlin.isRegistered(txtUsernameLog.getText().trim(), txtPasswordLog.getPassword());
				exitCode = LOGIN_BUTTON_PUSHED;
			}
		});
		btnLogin.setBounds(406, 29, 86, 23);
		frmLogin.getContentPane().add(btnLogin);
		
		label = new JLabel("");
		label.setBounds(39, 11, 46, 14);
		frmLogin.getContentPane().add(label);
		
		lblWelcomeText = new JLabel("<html> Herzlich Willkommen auf der Merlinseite <br> f\u00FCr Vogelbeobachtungen Sie haben einen Vogel beobachtet und wollen dies abspeichern? <br> Dann registrieren Sie sich jetzt oder loggen Sie sich mit Ihrem Username und Passwort ein </html>");
		lblWelcomeText.setFont(new Font("Arial", Font.PLAIN, 14));
		lblWelcomeText.setBounds(10, -9, 161, 265);
		frmLogin.getContentPane().add(lblWelcomeText);

		
		
		JLabel lblVorname = new JLabel("Vorname");
		lblVorname.setBounds(181, 148, 70, 14);
		frmLogin.getContentPane().add(lblVorname);
		
		txtVorname = new JTextField();
		txtVorname.setBounds(308, 145, 184, 20);
		frmLogin.getContentPane().add(txtVorname);
		txtVorname.setColumns(10);
		
		
		
		JLabel lblEmail = new JLabel("E-Mail");
		lblEmail.setLabelFor(txtName);
		lblEmail.setBounds(181, 179, 70, 14);
		frmLogin.getContentPane().add(lblEmail);
		
		txtEmail = new JTextField();
		txtEmail.setBounds(308, 176, 184, 20);
		frmLogin.getContentPane().add(txtEmail);
		txtEmail.setColumns(10);

		
		
		JLabel lblName = new JLabel("Name");
		lblName.setBounds(181, 117, 70, 14);
		frmLogin.getContentPane().add(lblName);
				
		txtName = new JTextField();
		txtName.setBounds(308, 114, 184, 20);
		frmLogin.getContentPane().add(txtName);
		txtName.setColumns(10);
				
		
		
		JLabel lblPasswort = new JLabel("Passwort");
		lblPasswort.setBounds(297, 11, 84, 14);
		frmLogin.getContentPane().add(lblPasswort);

		txtPasswordLog = new JPasswordField();
		txtPasswordLog.setBounds(297, 30, 99, 20);
		frmLogin.getContentPane().add(txtPasswordLog);
		
		
		
		txtUsernameLog = new JTextField();
		txtUsernameLog.setBounds(181, 30, 99, 20);
		frmLogin.getContentPane().add(txtUsernameLog);
		txtUsernameLog.setColumns(10);
		
		JLabel lblUsername = new JLabel("Username");
		lblUsername.setBounds(181, 11, 86, 14);
		frmLogin.getContentPane().add(lblUsername);
		
		
		
		JLabel lblUsernameReg = new JLabel("Username");
		lblUsernameReg.setBounds(181, 210, 70, 14);
		frmLogin.getContentPane().add(lblUsernameReg);

		txtUsernameReg = new JTextField();
		txtUsernameReg.setColumns(10);
		txtUsernameReg.setBounds(308, 207, 184, 20);
		frmLogin.getContentPane().add(txtUsernameReg);

		
		
		JLabel lblPasswortReg = new JLabel("Passwort");
		lblPasswortReg.setBounds(181, 244, 70, 14);
		frmLogin.getContentPane().add(lblPasswortReg);

		txtPasswordReg = new JPasswordField();
		txtPasswordReg.setBounds(307, 241, 185, 20);
		frmLogin.getContentPane().add(txtPasswordReg);
		
		
		
		JLabel lblPasswortBestReg = new JLabel("Passwort best.");
		lblPasswortBestReg.setBounds(181, 280, 101, 14);
		frmLogin.getContentPane().add(lblPasswortBestReg);
		
		txtPasswordRegBest = new JPasswordField();
		txtPasswordRegBest.setBounds(307, 277, 185, 20);
		frmLogin.getContentPane().add(txtPasswordRegBest);
		
		JCheckBox chkRememberUser = new JCheckBox("Nutzernamen merken");
		chkRememberUser.setBounds(209, 57, 139, 23);
		frmLogin.getContentPane().add(chkRememberUser);
		
	}
}
