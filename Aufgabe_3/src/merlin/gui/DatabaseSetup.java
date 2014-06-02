package merlin.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;

import java.awt.Toolkit;

import javax.swing.JLabel;
import javax.swing.JTextField;

import merlin.gui.enums.ExitCode;
import static merlin.gui.enums.ExitCode.*;

import static merlin.utils.ConstantElems.*;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JPasswordField;

import java.awt.Color;

import javax.swing.border.TitledBorder;
import javax.swing.JCheckBox;

import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class DatabaseSetup extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static ExitCode exitCode = CANCEL_BUTTON_PUSHED;
	private final JPanel contentPanel = new JPanel();
	
	private JTextField txtAKennung;
	private JPasswordField pwdPasswort;
	private JTextField txtURL;
	private JTextField txtPort;
	private JTextField txtSID;
	
	/**
	 * Launch the application.
	 * @throws UnsupportedLookAndFeelException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 * @throws ClassNotFoundException 
	 */
	public static ExitCode showDialog() throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
		
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		
		try {
			DatabaseSetup dialog = new DatabaseSetup();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return exitCode ;
	}
	
	/**
	 * Create the dialog.
	 */
	public DatabaseSetup() {
		setModal(true);
		setModalExclusionType(ModalExclusionType.APPLICATION_EXCLUDE);
		setTitle("Datenbankverbindung einrichten");
		setModalityType(ModalityType.APPLICATION_MODAL);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setIconImage(Toolkit.getDefaultToolkit().getImage(DatabaseSetup.class.getResource("/javax/swing/plaf/metal/icons/ocean/minimize.gif")));
		setResizable(false);
		setBounds(100, 100, 402, 329);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JLabel label = new JLabel("");
		label.setBounds(10, 11, 29, 20);
		contentPanel.add(label);
		
		JPanel loginPanel = new JPanel();
		loginPanel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Anmeldedaten", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		loginPanel.setBounds(10, 169, 377, 79);
		contentPanel.add(loginPanel);
		loginPanel.setLayout(null);
		{
			txtAKennung = new JTextField();
			txtAKennung.addFocusListener(new FocusAdapter() {
				@Override
				public void focusGained(FocusEvent arg0) {
					txtAKennung.setBackground(Color.WHITE);
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
				}
			});
			pwdPasswort.setToolTipText("Sie k\u00F6nnen uns vertrauen...!");
			pwdPasswort.setBounds(223, 19, 144, 20);
			loginPanel.add(pwdPasswort);
		}
		{
			JLabel lblAkennung = new JLabel("a-Kennung:");
			lblAkennung.setBounds(10, 19, 61, 20);
			loginPanel.add(lblAkennung);
		}
		{
			JCheckBox chkDatenMerken = new JCheckBox("Anmeldedaten merken (AES verschl\u00FCsselt)");
			chkDatenMerken.setBounds(6, 46, 357, 23);
			loginPanel.add(chkDatenMerken);
		}
		
		JPanel dbPanel = new JPanel();
		dbPanel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Verbindungsdaten", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		dbPanel.setBounds(10, 11, 377, 147);
		contentPanel.add(dbPanel);
		dbPanel.setLayout(null);
		{
			JLabel label_1 = new JLabel("URL:");
			label_1.setBounds(9, 19, 29, 20);
			dbPanel.add(label_1);
		}
		{
			txtURL = new JTextField();
			txtURL.addFocusListener(new FocusAdapter() {
				@Override
				public void focusGained(FocusEvent arg0) {
					txtURL.setBackground(Color.WHITE);
				}
			});
			txtURL.setBackground(Color.WHITE);
			txtURL.setText(defaultDbURL);
			txtURL.setColumns(10);
			txtURL.setBounds(48, 19, 319, 20);
			dbPanel.add(txtURL);
		}
		{
			JLabel label_1 = new JLabel("Port:");
			label_1.setBounds(9, 50, 29, 20);
			dbPanel.add(label_1);
		}
		{
			txtPort = new JTextField();
			txtPort.addFocusListener(new FocusAdapter() {
				@Override
				public void focusGained(FocusEvent arg0) {
					txtPort.setBackground(Color.WHITE);
				}
			});
			txtPort.setText(defaultDbPort);
			txtPort.setColumns(10);
			txtPort.setBounds(48, 50, 319, 20);
			dbPanel.add(txtPort);
		}
		{
			JLabel label_1 = new JLabel("SID:");
			label_1.setBounds(9, 81, 29, 20);
			dbPanel.add(label_1);
		}
		{
			txtSID = new JTextField();
			txtSID.addFocusListener(new FocusAdapter() {
				@Override
				public void focusGained(FocusEvent arg0) {
					txtSID.setBackground(Color.WHITE);
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
			contentPanel.add(lblError);
			lblError.setVisible(false);
		
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton btnAnmelden = new JButton("OK");
				btnAnmelden.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						
						boolean badInput = false;
						Color badColor = Color.RED;
						
						if (txtURL.getText().isEmpty()) {
							txtURL.setBackground(badColor);
							badInput = true;
						}
						if (txtPort.getText().isEmpty()) {
							txtPort.setBackground(badColor);
							badInput = true;
						}
						if (txtSID.getText().isEmpty()) {
							txtSID.setBackground(badColor);
							badInput = true;
						}
						if (txtAKennung.getText().isEmpty()) {
							txtAKennung.setBackground(badColor);
							badInput = true;
						}
						if (pwdPasswort.getPassword().length == 0) {
							pwdPasswort.setBackground(badColor);
							badInput = true;
						}
						
						if (badInput) {
							// Eingaben nicht OK, auf Fehler hinweisen und Dialog nicht abbrechen
							lblError.setVisible(true);
						} else {
							// Eingaben OK, Werte übernehmen und fortfahren
							
							// Anmeldedaten ablegen
							
							
							exitCode = OK_BUTTON_PUSHED;
							dispose();
						}
						
//						Main.saveProp("dbURL", txtURL.getText());
//						Main.saveProp("dbPort", txtPort.getText());
//						Main.saveProp("dbSID", txtSID.getText());
//					    
					}
				});
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
