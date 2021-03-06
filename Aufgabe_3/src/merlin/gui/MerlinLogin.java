package merlin.gui;


import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

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
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.BevelBorder;
import javax.swing.border.EtchedBorder;

import merlin.base.Application;
import merlin.logic.exception.IllegalPasswordException;
import merlin.logic.impl.MerlinLogic;
import merlin.utils.ConstantElems;
import static merlin.utils.ConstantElems.showMsgBox;
import org.eclipse.wb.swing.FocusTraversalOnArray;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;


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

	private JLabel lblNewLabel;
	private JPanel panelRegistration;
	private JPanel panelLogin;
	private JLabel lblbereitsRegistriert;
	private JPanel panel;
	private JMenu mnDatei;
	private JMenuItem mntmBeenden;
	private static boolean proceedToNextDialog = false;
	private JPanel panel_AfterRegister;
	private JLabel lblAfterRegister;
	private JButton btnRegister;

	/**
	 * Launch the application.
	 * 
	 * @throws UnsupportedLookAndFeelException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 * @throws ClassNotFoundException
	 * @throws IOException 
	 */
	public static void main() throws
			ClassNotFoundException, InstantiationException,
			IllegalAccessException, UnsupportedLookAndFeelException, IOException {

		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MerlinLogin window = new MerlinLogin();
					
					// center window on screen and make it visible
					Application.getInstance().centerWindow(window.frmLogin);
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
		frmLogin.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				try {
					Application.getInstance().closeMerlinYesNo(frmLogin);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			@Override
			public void windowClosed(WindowEvent arg0) {
				if (!proceedToNextDialog) {
					try {
						Application.getInstance().shutdown();
					} catch (Exception exc) {
						exc.printStackTrace();
						System.out.println(exc.getMessage());
					}
				}
			}
		});
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
		panelRegistration.setBackground(SystemColor.control);
		/*
		 * 
		 * 
		 */
		panelRegistration.setBorder(new EtchedBorder(EtchedBorder.LOWERED,
				null, null));
		panelRegistration.setBounds(330, 161, 332, 276);
		frmLogin.getContentPane().add(panelRegistration);
		panelRegistration.setLayout(null);

		txtVorname = new JTextField();
		txtVorname.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent arg0) {
				if (arg0.getKeyCode() == KeyEvent.VK_ENTER) {
					txtName.requestFocus();
				};
			}
		});
		txtVorname.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent arg0) {
				txtVorname.setBackground(Color.WHITE);
				txtVorname.selectAll();
			}
		});
		txtVorname.setBounds(117, 79, 204, 20);
		panelRegistration.add(txtVorname);
		txtVorname.setColumns(10);

		JLabel lblEmail = new JLabel("E-Mail");
		lblEmail.setFocusable(false);
		lblEmail.setBounds(10, 141, 70, 14);
		panelRegistration.add(lblEmail);
		lblEmail.setLabelFor(txtName);

		txtEmail = new JTextField();
		txtEmail.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent arg0) {
				if (arg0.getKeyCode() == KeyEvent.VK_ENTER) {
					txtPasswordReg.requestFocus();
				};
			}
		});
		txtEmail.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent arg0) {
				txtEmail.setBackground(Color.WHITE);
				txtEmail.selectAll();
			}
		});
		txtEmail.setBounds(117, 138, 204, 20);
		panelRegistration.add(txtEmail);
		txtEmail.setColumns(10);

		JLabel lblName = new JLabel("Name");
		lblName.setFocusable(false);
		lblName.setBounds(10, 113, 70, 14);
		panelRegistration.add(lblName);

		JLabel lblUsernameReg = new JLabel("Benutzername");
		lblUsernameReg.setFocusable(false);
		lblUsernameReg.setBounds(10, 57, 70, 14);
		panelRegistration.add(lblUsernameReg);

		txtUsernameReg = new JTextField();
		txtUsernameReg.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent arg0) {
				if (arg0.getKeyCode() == KeyEvent.VK_ENTER) {
					txtVorname.requestFocus();
				};
			}
		});
		txtUsernameReg.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent arg0) {
				txtUsernameReg.setBackground(Color.WHITE);
				txtUsernameReg.selectAll();
			}
		});
		txtUsernameReg.setBounds(117, 54, 204, 20);
		panelRegistration.add(txtUsernameReg);
		txtUsernameReg.setColumns(10);

		JLabel lblPasswortReg = new JLabel("Passwort");
		lblPasswortReg.setFocusable(false);
		lblPasswortReg.setBounds(10, 169, 70, 14);
		panelRegistration.add(lblPasswortReg);

		txtPasswordReg = new JPasswordField();
		txtPasswordReg.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent arg0) {
				if (arg0.getKeyCode() == KeyEvent.VK_ENTER) {
					txtPasswordRegBest.requestFocus();
				};
			}
		});
		txtPasswordReg.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent arg0) {
				txtPasswordReg.setBackground(Color.WHITE);
				txtPasswordReg.selectAll();
			}
		});
		txtPasswordReg.setBounds(117, 166, 204, 20);
		panelRegistration.add(txtPasswordReg);

		JLabel lblPasswortBestReg = new JLabel("Passwort best\u00E4tigen");
		lblPasswortBestReg.setFocusable(false);
		lblPasswortBestReg.setBounds(10, 197, 101, 14);
		panelRegistration.add(lblPasswortBestReg);

		txtPasswordRegBest = new JPasswordField();
		txtPasswordRegBest.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent arg0) {
				if (arg0.getKeyCode() == KeyEvent.VK_ENTER) {
					if (checkRegistrationData()) btnRegister.doClick();
				};
			}
		});
		txtPasswordRegBest.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent arg0) {
				txtPasswordRegBest.setBackground(Color.WHITE);
				txtPasswordRegBest.selectAll();
			}
		});
		txtPasswordRegBest.setBounds(117, 194, 204, 20);
		panelRegistration.add(txtPasswordRegBest);

		lblNewLabel = new JLabel(
				"<html><b><p style=\"font-size: 12px; text-align:center;\">Als Beobachter registrieren</p></b></html>");
		lblNewLabel.setBounds(10, 11, 311, 32);
		panelRegistration.add(lblNewLabel);

		btnRegister = new JButton("Registrieren");
		btnRegister.setBounds(117, 225, 204, 39);
		panelRegistration.add(btnRegister);

		JLabel lblVorname = new JLabel("Vorname");
		lblVorname.setFocusable(false);
		lblVorname.setBounds(10, 85, 70, 14);
		panelRegistration.add(lblVorname);

		txtName = new JTextField();
		txtName.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent arg0) {
				if (arg0.getKeyCode() == KeyEvent.VK_ENTER) {
					txtEmail.requestFocus();
				};
			}
		});
		txtName.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent arg0) {
				txtName.setBackground(Color.WHITE);
				txtName.selectAll();
			}
		});
		txtName.setBounds(117, 107, 204, 20);
		panelRegistration.add(txtName);
		txtName.setColumns(10);
		
		
		panel_AfterRegister = new JPanel();
		panel_AfterRegister.setBounds(330, 161, 332, 276);
		frmLogin.getContentPane().add(panel_AfterRegister);
		panel_AfterRegister.setVisible(false);

		lblAfterRegister = new JLabel("<html><b><p style=\"font-size: 12px; text-align:center;\">Sie k\u00F6nnen sich jetzt <br/> mit Ihrem Benutzernamen <br/> und Passwort anmelden</p></b>");
		lblAfterRegister.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblAfterRegister.setForeground(new Color(50, 205, 50));
		lblAfterRegister.setHorizontalAlignment(SwingConstants.LEFT);
		panel_AfterRegister.add(lblAfterRegister);
				
		
		
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
		txtPasswordLog.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent arg0) {
				if (arg0.getKeyCode() == KeyEvent.VK_ENTER) {
					btnLogin.doClick();
				};
			}
		});
		txtPasswordLog.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent arg0) {
				txtPasswordLog.setBackground(Color.WHITE);
				txtPasswordLog.selectAll();
			}
		});
		txtPasswordLog.setText(ConstantElems.demoPassword);
		txtPasswordLog.setBounds(126, 74, 99, 20);
		panelLogin.add(txtPasswordLog);

		txtUsernameLog = new JTextField();
		txtUsernameLog.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent arg0) {
				if (arg0.getKeyCode() == KeyEvent.VK_ENTER) {
					txtPasswordLog.requestFocus();
				};
			}
		});
		txtUsernameLog.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent arg0) {
				txtUsernameLog.setBackground(Color.WHITE);
				txtUsernameLog.selectAll();
			}
		});
		txtUsernameLog.setText(ConstantElems.demoUsername);
		txtUsernameLog.setBounds(10, 74, 99, 20);
		panelLogin.add(txtUsernameLog);
		txtUsernameLog.setColumns(10);

		JLabel lblUsername = new JLabel("Benutzername");
		lblUsername.setBounds(10, 55, 86, 14);
		panelLogin.add(lblUsername);

		final JCheckBox chkRememberUser = new JCheckBox(
				"Anmeldedaten merken (AES verschl\u00FCsselt)");
		chkRememberUser.setVisible(false);
		chkRememberUser.setEnabled(false);
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

		
		
//		// Wollte Hintergrundbild haben :(
//		JLabel j1 = new JLabel();
//		try {
//			j1.setIcon(new ImageIcon(ImageIO.read(new File("C:\\Users\\Louisa\\Desktop"))));
//		} catch (IOException e1) {
//			System.out.println("Bild existiert nicht, Image does not exctist");
//		}
//		panel.add(j1);
//		j1.validate();
		
		
		lblWelcomeText = new JLabel(
				"<html><b><p style=\"font-size: 16px; text-align:center;\">MERLIN</p></b>\r\n<p style=\"font-size: 8px; text-align:center;\">\r\n\t<b>Ein integriertes Informationssystem f\u00FCr die Verwaltung und Auswertung von Naturbeobachtungen</b></p>\r\n<br/>\r\nHerzlich willkommen zu MERLIN!<br/>\r\nMERLIN ist das ideale Online-Werkzeug f\u00FCr Naturbeobachter!\r\nSchauen Sie mit Hilfe von regionalen Checklisten, was es zu Entdecken gibt und f\u00FChren Sie Ihre eigene Beobachtungsliste, die Sie einfach verwalten und auswerten k\u00F6nnen.<br/><br/>\r\n\r\nRegistrieren Sie sich jetzt und legen sofort los, oder probieren Sie unseren Demo-Login aus, um sich einen Eindruck zu verschaffen!<br/><br/>\r\nBenutzername: <b>" + ConstantElems.demoUsername + "</b><br/>\r\nPasswort: <b>" + ConstantElems.demoPassword + "</b><br/><br/>\r\n\r\nSie sind bereits registrierter Beobachter? Dann k\u00F6nnen Sie sich jetzt einloggen!");
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
				try {
					Application.getInstance().closeMerlinYesNo(frmLogin);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		mntmBeenden.setIcon(new ImageIcon(MerlinLogin.class
				.getResource("/javax/swing/plaf/metal/icons/ocean/close.gif")));
		mnDatei.add(mntmBeenden);
		frmLogin.setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[]{txtUsernameLog, txtPasswordLog, btnLogin, chkRememberUser, txtUsernameReg, txtVorname, txtName, txtEmail, txtPasswordReg, txtPasswordRegBest, btnRegister, frmLogin.getContentPane(), label, panelRegistration, lblEmail, lblName, lblUsernameReg, lblPasswortReg, lblPasswortBestReg, lblNewLabel, lblVorname, panel_AfterRegister, lblAfterRegister, panelLogin, lblPasswort, lblUsername, lblbereitsRegistriert, panel, lblWelcomeText, menuBar, mnDatei, mntmBeenden}));
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			
				boolean[] ret = MerlinLogic.loginToMerlin(txtUsernameLog.getText(), new String(txtPasswordLog.getPassword()), chkRememberUser.isSelected());
				
				if (ret[0] && ret[1] && ret[2]) {
					frmLogin.dispose();
					proceedToNextDialog = true;
					try {
						MerlinMainWindow.main();
					} catch (Exception e) {
						e.printStackTrace();
					}
				} else {
					showMsgBox(new Exception(), "Die Logindaten sind ungültig");
				}
				
				
//				try {
//					MerlinLogic.isRegistered(txtUsernameLog.getText().trim(), txtPasswordLog.getPassword());
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
				
			}
		});
		btnRegister.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					boolean isBirdwatcherInserted = MerlinLogic.insertBirdwatcher(
							txtName.getText().trim(),
							txtVorname.getText().trim(),
							txtUsernameReg.getText().trim(),
							new String(txtPasswordReg.getPassword()),
							new String(txtPasswordRegBest.getPassword()),
							txtEmail.getText().trim());
					System.out.println("391 MerlinLogin#actionPerformed Register" + isBirdwatcherInserted );
					if (isBirdwatcherInserted == true){
						panelRegistration.setVisible(false);
						panel_AfterRegister.setVisible(true);
						lblAfterRegister.setVisible(true);
					}
				} catch (IllegalPasswordException e) {
					System.out.println("Beide Passwörter müssen übereinstimmten!");
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				} catch (InstantiationException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (UnsupportedLookAndFeelException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

	}

	private boolean checkRegistrationData() {
		return true;
	}
}
