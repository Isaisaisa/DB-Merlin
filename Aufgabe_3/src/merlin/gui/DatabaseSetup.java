package merlin.gui;

import static merlin.gui.enums.ExitCode.CANCEL_BUTTON_PUSHED;
import static merlin.gui.enums.ExitCode.OK_BUTTON_PUSHED;
import static merlin.gui.enums.ExitCode.QUIT_DIALOG;
import static merlin.utils.ConstantElems.defaultDbPort;
import static merlin.utils.ConstantElems.defaultDbSID;
import static merlin.utils.ConstantElems.defaultDbURL;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
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
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import merlin.base.Application;
import merlin.base.DbWrapper;
import merlin.gui.enums.ExitCode;
import merlin.logic.impl.MerlinLogic;

public class DatabaseSetup extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1060776611286417913L;
	private static ExitCode exitCode = CANCEL_BUTTON_PUSHED;
	private final JPanel panelDatabaseSetup = new JPanel();
	
	private JTextField 		txtAKennung;
	private JPasswordField 	pwdPasswort;
	private JTextField 		txtURL;
	private JTextField 		txtPort;
	private JTextField 		txtSID;
	private JCheckBox 		chkRememberLogin;
	private JLabel 	lblConnectionState;
	protected DbWrapper 	database;
	
	/**
	 * Launch the application.
	 * @param modalityType 
	 * @throws UnsupportedLookAndFeelException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 * @throws ClassNotFoundException 
	 * @throws IOException 
	 */
	public static ExitCode showDialog(ModalityType modalityType) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException, IOException {
		
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		
		try {
			DatabaseSetup dialog = new DatabaseSetup(modalityType);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		Application.getInstance().exitCode = exitCode;
		return exitCode ;
	}
	
	public static ExitCode showDialog() throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException, IOException {
		return showDialog(ModalityType.APPLICATION_MODAL);
	}
	
	public static DatabaseSetup valueOf(ModalityType mt) throws ClassNotFoundException, InstantiationException, IllegalAccessException, IOException, UnsupportedLookAndFeelException {
		return new DatabaseSetup(mt);
	}
	
	public static DatabaseSetup valueOf() throws ClassNotFoundException, InstantiationException, IllegalAccessException, IOException, UnsupportedLookAndFeelException {
		return new DatabaseSetup(ModalityType.APPLICATION_MODAL);
	}
	
	/**
	 * Create the dialog.
	 * @throws UnsupportedLookAndFeelException 
	 * @throws IOException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 * @throws ClassNotFoundException 
	 */
	public DatabaseSetup(ModalityType modalityType) throws ClassNotFoundException, InstantiationException, IllegalAccessException, IOException, UnsupportedLookAndFeelException {
//		setModalExclusionType(ModalExclusionType.APPLICATION_EXCLUDE);
		setTitle("Datenbankverbindung einrichten");
		setModalityType(modalityType);
//		setModalityType(ModalityType.APPLICATION_MODAL);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setIconImage(Toolkit.getDefaultToolkit().getImage(DatabaseSetup.class.getResource("/javax/swing/plaf/metal/icons/ocean/minimize.gif")));
		setResizable(false);
		setBounds(100, 100, 402, 329);
		getContentPane().setLayout(new BorderLayout());
		panelDatabaseSetup.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(panelDatabaseSetup, BorderLayout.CENTER);
		panelDatabaseSetup.setLayout(null);
		
		database = Application.getInstance().database;
		
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
			txtAKennung.addFocusListener(new FocusAdapter() {
				@Override
				public void focusGained(FocusEvent arg0) {
					txtAKennung.setBackground(Color.WHITE);
					txtAKennung.selectAll();
				}
			});
			txtAKennung.setColumns(10);
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
			txtURL.setText(defaultDbURL);
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
			txtPort.setText(defaultDbPort);
			txtPort.setColumns(10);
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
			txtSID.setText(defaultDbSID);
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
						
						try {
							if (MerlinLogic.loginToDatabase(txtURL.getText(),
									txtPort.getText(), txtSID.getText(),
									txtAKennung.getText(), new String(
									pwdPasswort.getPassword()),
									chkRememberLogin.isSelected()))
							{
								exitCode = OK_BUTTON_PUSHED;
								dispose();
							}
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}

				});
				{
					lblConnectionState = new JLabel("Verbindung wird hergestellt. Bitte warten!");
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
						exitCode = QUIT_DIALOG;
						dispose();
					}
				});
				btnBeenden.setActionCommand("Cancel");
				buttonPane.add(btnBeenden);
			}
		} // buttonPane
	} // DatabaseSetup()
}
