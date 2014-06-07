package merlin.gui;

import static merlin.gui.enums.ExitCode.DIALOG_ABORTED;
import static merlin.gui.enums.ExitCode.LOGIN_BUTTON_PUSHED;
import static merlin.gui.enums.ExitCode.REGISTER_BUTTON_PUSHED;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dialog.ModalExclusionType;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.BevelBorder;
import javax.swing.border.EtchedBorder;

import merlin.gui.enums.ExitCode;
import merlin.gui.enums.WindowState;
import merlin.logic.exception.IllegalPasswordException;
import merlin.logic.impl.MerlinLogic;

import org.eclipse.wb.swing.FocusTraversalOnArray;

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
	private JLabel lblNewLabel;
	private JPanel panelRegistration;
	private JPanel panelLogin;
	private JLabel lblbereitsRegistriert;
	private JPanel panel;
	private JMenu mnDatei;
	private JMenuItem mntmBeenden;
	private JMenu mnEintellungen;
	private JMenuItem mntmVerbindungseinstellungen;
	private JCheckBox chkRememberUser;
	private JLabel lblPasswort;
	private JLabel lblUsername;
	private JMenuBar menuBar;

	/**
	 * Launch the application.
	 * 
	 * @throws UnsupportedLookAndFeelException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 * @throws ClassNotFoundException
	 */
	public static ExitCode main() throws ClassNotFoundException,
			InstantiationException, IllegalAccessException,
			UnsupportedLookAndFeelException {

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
		frmLogin.setBounds(200, 200, 676, 498);
		frmLogin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmLogin.getContentPane().setLayout(null);

		label = new JLabel("");
		label.setBounds(39, 11, 46, 14);
		frmLogin.getContentPane().add(label);

		panelRegistration = new JPanel();
		panelRegistration.setBorder(new EtchedBorder(EtchedBorder.LOWERED,
				null, null));
		panelRegistration.setBounds(330, 161, 332, 276);
		frmLogin.getContentPane().add(panelRegistration);
		panelRegistration.setLayout(null);

		txtVorname = new JTextField();
		txtVorname.setBounds(117, 110, 204, 20);
		panelRegistration.add(txtVorname);
		txtVorname.setColumns(10);

		JLabel lblEmail = new JLabel("E-Mail");
		lblEmail.setBounds(10, 141, 70, 14);
		panelRegistration.add(lblEmail);
		lblEmail.setLabelFor(txtName);

		txtEmail = new JTextField();
		txtEmail.setBounds(117, 138, 204, 20);
		panelRegistration.add(txtEmail);
		txtEmail.setColumns(10);

		JLabel lblName = new JLabel("Name");
		lblName.setBounds(10, 113, 70, 14);
		panelRegistration.add(lblName);

		JLabel lblUsernameReg = new JLabel("Benutzername");
		lblUsernameReg.setBounds(10, 57, 70, 14);
		panelRegistration.add(lblUsernameReg);

		txtUsernameReg = new JTextField();
		txtUsernameReg.setBounds(117, 54, 204, 20);
		panelRegistration.add(txtUsernameReg);
		txtUsernameReg.setColumns(10);

		JLabel lblPasswortReg = new JLabel("Passwort");
		lblPasswortReg.setBounds(10, 169, 70, 14);
		panelRegistration.add(lblPasswortReg);

		txtPasswordReg = new JPasswordField();
		txtPasswordReg.setBounds(117, 166, 204, 20);
		panelRegistration.add(txtPasswordReg);

		JLabel lblPasswortBestReg = new JLabel("Passwort best\u00E4tigen");
		lblPasswortBestReg.setBounds(10, 197, 101, 14);
		panelRegistration.add(lblPasswortBestReg);

		txtPasswordRegBest = new JPasswordField();
		txtPasswordRegBest.setBounds(117, 194, 204, 20);
		panelRegistration.add(txtPasswordRegBest);

		lblNewLabel = new JLabel(
				"<html><b><p style=\"font-size: 12px; text-align:center;\">Als Beobachter registrieren</p></b></html>");
		lblNewLabel.setBounds(10, 11, 311, 32);
		panelRegistration.add(lblNewLabel);

		// TODO GUI für IllegalPasswordException
		JButton btnRegister = new JButton("Registrieren");
		btnRegister.setBounds(117, 225, 204, 39);
		panelRegistration.add(btnRegister);

		JLabel lblVorname = new JLabel("Vorname");
		lblVorname.setBounds(10, 85, 70, 14);
		panelRegistration.add(lblVorname);
		frmLogin.setFocusTraversalPolicy(new FocusTraversalOnArray(
				new Component[] { txtUsernameLog, txtPasswordLog,
						chkRememberUser, btnLogin, txtUsernameReg, txtVorname,
						txtEmail, txtName, txtPasswordReg, txtPasswordRegBest,
						btnRegister, lblNewLabel, label,
						frmLogin.getContentPane(), lblVorname, lblEmail,
						lblName, lblUsernameReg, lblPasswortReg,
						lblPasswortBestReg, panelLogin, lblPasswort,
						lblUsername, lblbereitsRegistriert, panel, menuBar,
						panelRegistration, lblWelcomeText }));

		txtName = new JTextField();
		txtName.setBounds(117, 82, 204, 20);
		panelRegistration.add(txtName);
		txtName.setColumns(10);

		panelLogin = new JPanel();
		panelLogin.setBorder(new BevelBorder(BevelBorder.RAISED, null, null,
				null, null));
		panelLogin.setBounds(330, 11, 332, 139);
		frmLogin.getContentPane().add(panelLogin);
		panelLogin.setLayout(null);

		btnLogin = new JButton("Einloggen");
		btnLogin.setBounds(235, 73, 86, 53);
		panelLogin.add(btnLogin);

		JLabel lblPasswort = new JLabel("Passwort");
		lblPasswort.setBounds(126, 55, 84, 14);
		panelLogin.add(lblPasswort);

		txtPasswordLog = new JPasswordField();
		txtPasswordLog.setBounds(126, 74, 99, 20);
		panelLogin.add(txtPasswordLog);

		txtUsernameLog = new JTextField();
		txtUsernameLog.setBounds(10, 74, 99, 20);
		panelLogin.add(txtUsernameLog);
		txtUsernameLog.setColumns(10);

		JLabel lblUsername = new JLabel("Benutzername");
		lblUsername.setBounds(10, 55, 86, 14);
		panelLogin.add(lblUsername);

		JCheckBox chkRememberUser = new JCheckBox(
				"Anmeldedaten merken (AES verschl\u00FCsselt)");
		chkRememberUser.setBounds(6, 103, 227, 23);
		panelLogin.add(chkRememberUser);

		lblbereitsRegistriert = new JLabel(
				"<html><b><p style=\"font-size: 12px; text-align:center;\">Bereits registriert?</p></b></html>");
		lblbereitsRegistriert.setBounds(10, 11, 311, 32);
		panelLogin.add(lblbereitsRegistriert);

		panel = new JPanel();
		panel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel.setBounds(10, 11, 310, 426);
		frmLogin.getContentPane().add(panel);
		panel.setLayout(null);

		lblWelcomeText = new JLabel(
				"<html><b><p style=\"font-size: 16px; text-align:center;\">MERLIN</p></b>\r\n<p style=\"font-size: 8px; text-align:center;\">\r\n\t<b>Ein integriertes Informationssystem f\u00FCr die Verwaltung und Auswertung von Naturbeobachtungen</b></p>\r\n<br/>\r\nHerzlich willkommen zu MERLIN!<br/>\r\nMERLIN ist das ideale Online-Werkzeug f\u00FCr Naturbeobachter!\r\nSchauen Sie mit Hilfe von regionalen Checklisten, was es zu Entdecken gibt und f\u00FChren Sie Ihre eigene Beobachtungsliste, die Sie einfach verwalten und auswerten k\u00F6nnen.<br/><br/>\r\n\r\nRegistrieren Sie sich jetzt und legen sofort los, oder probieren Sie unseren Demo-Login aus, um sich einen Eindruck zu verschaffen!<br/><br/>\r\nBenutzername: <b>demo</b><br/>\r\nPasswort: <b>merlindemo</b><br/><br/>\r\n\r\nSie sind bereits registrierter Beobachter? Dann k\u00F6nnen Sie sich jetzt einloggen!");
		lblWelcomeText.setBounds(24, 0, 263, 328);
		panel.add(lblWelcomeText);
		lblWelcomeText.setFont(new Font("Candara", Font.PLAIN, 12));

		JMenuBar menuBar = new JMenuBar();
		frmLogin.setJMenuBar(menuBar);

		mnDatei = new JMenu("Datei");
		menuBar.add(mnDatei);

		mntmBeenden = new JMenuItem("Beenden");
		mntmBeenden.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.out.println("Beenden?");
			}
		});
		mntmBeenden.setIcon(new ImageIcon(MerlinLogin.class
				.getResource("/javax/swing/plaf/metal/icons/ocean/close.gif")));
		mnDatei.add(mntmBeenden);

		mnEintellungen = new JMenu("Eintellungen");
		menuBar.add(mnEintellungen);

		mntmVerbindungseinstellungen = new JMenuItem("Verbindungseinstellungen");
		mntmVerbindungseinstellungen
				.setIcon(new ImageIcon(
						MerlinLogin.class
								.getResource("/javax/swing/plaf/metal/icons/ocean/minimize.gif")));
		mnEintellungen.add(mntmVerbindungseinstellungen);
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// TODO login überprüfen
				
				//nachhher löschen
//				String alpha = txtUsernameLog.getText().trim();
//				String beta =  new String(txtPasswordLog.getPassword());
//				System.out.println(alpha);
//				System.out.println(beta);
				
				try {
					MerlinLogic.isRegistered(txtUsernameLog.getText().trim(), txtPasswordLog.getPassword());
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				exitCode = LOGIN_BUTTON_PUSHED;
				WindowState.BIRDWATCHERLOGIN.nextState();
			}
		});
		btnRegister.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					MerlinLogic.insertBirdwatcher(txtName.getText().trim(),
							txtVorname.getText().trim(), txtUsernameReg
									.getText().trim(), txtPasswordReg
									.getPassword(), txtPasswordRegBest
									.getPassword(), txtEmail.getText().trim());
				} catch (IllegalPasswordException e) {
					System.out
							.println("Passwörter sind nicht identische, bitte neu eingeben");
					e.printStackTrace();
				}
				exitCode = REGISTER_BUTTON_PUSHED;
			}
		});

	}
}
