package merlin.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.Toolkit;
import javax.swing.JLabel;
import javax.swing.JTextField;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

public class DatabaseSetup extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTextField txtURL;
	private JTextField txtPort;
	private JTextField txtSID;

	/**
	 * Launch the application.
	 */
	public static void showDialog(ArrayList<String> dbSettings) {
		try {
			DatabaseSetup dialog = new DatabaseSetup(dbSettings);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Create the dialog.
	 */
	public DatabaseSetup(final ArrayList<String> dbSettings) {
		setTitle("Datenbankverbindung einrichten");
		setModalityType(ModalityType.APPLICATION_MODAL);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setIconImage(Toolkit.getDefaultToolkit().getImage(DatabaseSetup.class.getResource("/javax/swing/plaf/metal/icons/ocean/minimize.gif")));
		setResizable(false);
		setBounds(100, 100, 343, 150);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JLabel label = new JLabel("URL");
			label.setBounds(10, 11, 29, 20);
			contentPanel.add(label);
		}
		{
			txtURL = new JTextField();
			txtURL.setText("oracle.informatik.haw-hamburg.de");
			txtURL.setColumns(10);
			txtURL.setBounds(40, 11, 287, 20);
			contentPanel.add(txtURL);
		}
		{
			JLabel label = new JLabel("Port");
			label.setBounds(10, 35, 29, 20);
			contentPanel.add(label);
		}
		{
			txtPort = new JTextField();
			txtPort.setText("1521");
			txtPort.setColumns(10);
			txtPort.setBounds(40, 35, 287, 20);
			contentPanel.add(txtPort);
		}
		{
			JLabel label = new JLabel("SID");
			label.setBounds(10, 59, 29, 20);
			contentPanel.add(label);
		}
		{
			txtSID = new JTextField();
			txtSID.setText("inf09");
			txtSID.setColumns(10);
			txtSID.setBounds(40, 59, 287, 20);
			contentPanel.add(txtSID);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						dbSettings.add(txtURL.getText());
						dbSettings.add(txtPort.getText());
						dbSettings.add(txtSID.getText());
					    dispose();
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Abbrechen");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}

}
