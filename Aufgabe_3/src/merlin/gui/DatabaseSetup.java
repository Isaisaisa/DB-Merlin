package merlin.gui;

import static merlin.utils.ConstantElems.defaultDbPort;
import static merlin.utils.ConstantElems.defaultDbSID;
import static merlin.utils.ConstantElems.defaultDbURL;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import merlin.base.Application;
import merlin.logic.impl.MerlinLogic;


public class DatabaseSetup extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1060776611286417913L;
	private final JPanel panelDatabaseSetup = new JPanel();
	
	private JTextField 		txtAKennung;
	private JPasswordField 	pwdPasswort;
	private JTextField 		txtURL;
	private JTextField 		txtPort;
	private JTextField 		txtSID;
	private JCheckBox 		chkRememberLogin;
	private JLabel 			lblConnectionState;
	private static boolean 	proceedToNextDialog = false;
	
	private Application		app = Application.getInstance();
	
	/**
	 * Launch the application.
	 * @param modalityType 
	 * @throws UnsupportedLookAndFeelException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 * @throws ClassNotFoundException 
	 * @throws IOException 
	 */
	public static void showDialog(ModalityType modalityType)
			throws ClassNotFoundException, InstantiationException,
			IllegalAccessException, UnsupportedLookAndFeelException,
			IOException {

		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

		try {
			DatabaseSetup dialog = new DatabaseSetup(modalityType);
			dialog.addWindowListener(new WindowAdapter() {
				@Override
				public void windowClosed(WindowEvent e) {
					if (!proceedToNextDialog) {
						try {
							Application.getInstance().shutdown();
						} catch (Exception exc) {
							exc.printStackTrace();
							System.out.println(exc.getMessage());
						}
					}
				}

				@Override
				public void windowClosing(WindowEvent e) {
				}
			});
			dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			
			// center window on screen and make it visible
			Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
			dialog.setLocation(dim.width/2 - dialog.getSize().width/2, dim.height/2 - dialog.getSize().height/2);
			dialog.setVisible(true);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static DatabaseSetup valueOf(ModalityType mt) throws Exception {
		return new DatabaseSetup(mt);
	}
	
	public static DatabaseSetup valueOf() throws Exception {
		return new DatabaseSetup(ModalityType.APPLICATION_MODAL);
	}
	
	public static void showDialog() throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException, IOException {
		showDialog(ModalityType.APPLICATION_MODAL);
	}
	
	
	/**
	 * Create the dialog.
	 * @throws Exception 
	 */
	public DatabaseSetup(ModalityType modalityType) throws Exception {
//		setModalExclusionType(ModalExclusionType.APPLICATION_EXCLUDE);
		setTitle("Datenbankverbindung einrichten");
		setModalityType(modalityType);
//		setModalityType(ModalityType.APPLICATION_MODAL);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setIconImage(Toolkit.getDefaultToolkit().getImage(DatabaseSetup.class.getResource("/javax/swing/plaf/metal/icons/ocean/minimize.gif")));
		setResizable(false);
		setBounds(100, 100, 402, 329);
		getContentPane().setLayout(new BorderLayout());
		panelDatabaseSetup.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(panelDatabaseSetup, BorderLayout.CENTER);
		panelDatabaseSetup.setLayout(null);
		
		JLabel label = new JLabel("");
		label.setBounds(10, 11, 29, 20);
		panelDatabaseSetup.add(label);
		
		JPanel loginPanel = new JPanel();
		loginPanel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Anmeldedaten", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		loginPanel.setBounds(10, 169, 377, 79);
		panelDatabaseSetup.add(loginPanel);
		loginPanel.setLayout(null);
		{
			txtAKennung = new JTextField();
			// TODO Zugriff auf verschlüsselte Daten fixen 
			/* at merlin.base.Application.getEncProp(Application.java:97)
			 * at merlin.base.Application.getLogin(Application.java:107)
			 * at merlin.base.Application.getDbUsername(Application.java:117)
			 */
			txtAKennung.setText(app.getDbUsername());
			txtAKennung.addFocusListener(new FocusAdapter() {
				@Override
				public void focusGained(FocusEvent arg0) {
					txtAKennung.setBackground(Color.WHITE);
					txtAKennung.selectAll();
				}
			});
			txtAKennung.setColumns(20);
			txtAKennung.setBounds(81, 19, 61, 20);
			loginPanel.add(txtAKennung);
		}
		{
			JLabel lblPasswort = new JLabel("Passwort:");
			lblPasswort.setBounds(152, 19, 61, 20);
			loginPanel.add(lblPasswort);
		}
		{
			pwdPasswort = new JPasswordField();
			pwdPasswort.addFocusListener(new FocusAdapter() {
				@Override
				public void focusGained(FocusEvent arg0) {
					pwdPasswort.setBackground(Color.WHITE);
					pwdPasswort.selectAll();
				}
			});
			// TODO Zugriff auf verschlüsselte Daten fixen
			pwdPasswort.setText(app.getDbPassword());
			pwdPasswort.setToolTipText("Sie k\u00F6nnen uns vertrauen...!");
			pwdPasswort.setBounds(223, 19, 144, 20);
			loginPanel.add(pwdPasswort);
		}
		{
			JLabel lblAKennung = new JLabel("a-Kennung:");
			lblAKennung.setBounds(10, 19, 61, 20);
			loginPanel.add(lblAKennung);
		}
		{
			chkRememberLogin = new JCheckBox("Anmeldedaten merken (AES verschl\u00FCsselt)");
			chkRememberLogin.setVisible(false);
			chkRememberLogin.setEnabled(false);
			chkRememberLogin.setBounds(6, 46, 357, 23);
			loginPanel.add(chkRememberLogin);
		}
		
		JPanel dbPanel = new JPanel();
		dbPanel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Verbindungsdaten", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		dbPanel.setBounds(10, 11, 377, 147);
		panelDatabaseSetup.add(dbPanel);
		dbPanel.setLayout(null);
		{
			JLabel lblURL = new JLabel("URL:");
			lblURL.setBounds(9, 19, 29, 20);
			dbPanel.add(lblURL);
		}
		{
			txtURL = new JTextField();
			txtURL.addFocusListener(new FocusAdapter() {
				@Override
				public void focusGained(FocusEvent arg0) {
					txtURL.setBackground(Color.WHITE);
					txtURL.selectAll();
				}
			});
			txtURL.setBackground(Color.WHITE);
			txtURL.setText(app.getDbURL());
			txtURL.setColumns(10);
			txtURL.setBounds(48, 19, 319, 20);
			dbPanel.add(txtURL);
		}
		{
			JLabel lblPort = new JLabel("Port:");
			lblPort.setBounds(9, 50, 29, 20);
			dbPanel.add(lblPort);
		}
		{
			txtPort = new JTextField();
			txtPort.addFocusListener(new FocusAdapter() {
				@Override
				public void focusGained(FocusEvent arg0) {
					txtPort.setBackground(Color.WHITE);
					txtPort.selectAll();
				}
			});
			txtPort.setText(app.getDbPort());
			txtPort.setColumns(5);
			txtPort.setBounds(48, 50, 319, 20);
			dbPanel.add(txtPort);
		}
		{
			JLabel lblSID = new JLabel("SID:");
			lblSID.setBounds(9, 81, 29, 20);
			dbPanel.add(lblSID);
		}
		{
			txtSID = new JTextField();
			txtSID.addFocusListener(new FocusAdapter() {
				@Override
				public void focusGained(FocusEvent arg0) {
					txtSID.setBackground(Color.WHITE);
					txtSID.selectAll();
				}
			});
			txtSID.setText(app.getDbSID());
			txtSID.setColumns(10);
			txtSID.setBounds(48, 81, 319, 20);
			dbPanel.add(txtSID);
		}
		
		JButton btnReset = new JButton("Standartwerte wiederherstellen");
		btnReset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				txtURL.setText(defaultDbURL);
				txtPort.setText(defaultDbPort);
				txtSID.setText(defaultDbSID);
			}
		});
		btnReset.setBounds(182, 112, 185, 23);
		dbPanel.add(btnReset);
		
			final JLabel lblError = new JLabel("Markierte Felder enthalten fehlerhafte Eingaben oder sind leer");
			lblError.setEnabled(false);
			lblError.setForeground(Color.RED);
			lblError.setBounds(43, 250, 309, 14);
			panelDatabaseSetup.add(lblError);
			lblError.setVisible(false);
		
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton btnAnmelden = new JButton("OK");
				btnAnmelden.addActionListener(new ActionListener() {

					public void actionPerformed(ActionEvent arg0) {
						
						// Fehlerhafte Felder einfärben
						boolean badInput = false;
						Color badColor = Color.RED;
						
						// TODO boolean array auswerten, um festzustellen, welche Felder aufgrund von fehlern eingefärbt werden müssen
						try {
							boolean[] loginResult = MerlinLogic.loginToDatabase(
									txtURL.getText(),
									txtPort.getText(), txtSID.getText(),
									txtAKennung.getText(), new String(
									pwdPasswort.getPassword()),
									chkRememberLogin.isSelected());
							
							if (loginResult[0] && loginResult[1] && loginResult[2] && loginResult[3] && loginResult[4] && loginResult[5]) {
								dispose();
								proceedToNextDialog = true;
								MerlinLogin.main();
							} else {
								//login fehlgeschlagen
								if (loginResult[0]) txtURL.setBackground(badColor);
								if (loginResult[1]) txtPort.setBackground(badColor);
								if (loginResult[2]) txtSID.setBackground(badColor);
								if (loginResult[3]) txtAKennung.setBackground(badColor);
								if (loginResult[4]) pwdPasswort.setBackground(badColor);
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
					}

				});
				{
					lblConnectionState = new JLabel("Verbindung wird hergestellt. Bitte warten!");
					lblConnectionState.setEnabled(false);
					buttonPane.add(lblConnectionState);
					lblConnectionState.setVisible(false);
				}
				btnAnmelden.setActionCommand("OK");
				buttonPane.add(btnAnmelden);
				getRootPane().setDefaultButton(btnAnmelden);
			}
			{
				JButton btnBeenden = new JButton("Beenden");
				btnBeenden.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						dispose();
						try {
							Application.getInstance().shutdown();
						} catch (Exception e) {
							e.printStackTrace();
							System.err.println("Fehler beim Beenden des DatabaseSetup Dialogs.");
						}
					}
				});
				btnBeenden.setActionCommand("Cancel");
				buttonPane.add(btnBeenden);
			}
		} // buttonPane
	} // DatabaseSetup()
}
