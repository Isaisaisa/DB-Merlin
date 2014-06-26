package merlin.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;

import merlin.gui.enums.ExitCode;
import static merlin.gui.enums.ExitCode.*;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JLabel;

@SuppressWarnings("unused")
public class SaveExitDialog extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static ExitCode exitCode;
	private final JPanel contentPanel = new JPanel();

	/**
	 * Launch the application.
	 */
	public static ExitCode main() {
		try {
			SaveExitDialog dialog = new SaveExitDialog();
			dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return exitCode;
	}

	/**
	 * Create the dialog.
	 */
	
	public SaveExitDialog() {
		setModal(true);
		setModalityType(ModalityType.APPLICATION_MODAL);
		setModalExclusionType(ModalExclusionType.APPLICATION_EXCLUDE);
		setBounds(100, 100, 475, 109);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setLayout(new FlowLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		{
			JLabel lblNewLabel = new JLabel("M\u00F6chten Sie die \u00C4nderungen an der Datenbank festschreiben, bevor Sie MERLIN beenden?");
			contentPanel.add(lblNewLabel);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("Beenden ohne Festschreiben");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						exitCode = OK_BUTTON_PUSHED;
					}
				});
				{
					JButton btnNewButton = new JButton("Festschreiben und beenden");
					btnNewButton.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent arg0) {
							exitCode = COMMIT_AND_QUIT_BUTTON_PUSHED;
						}
					});
					buttonPane.add(btnNewButton);
				}
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Abbrechen");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						exitCode = CANCEL_BUTTON_PUSHED;
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}

}
