package merlin.gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import java.awt.Color;
import java.awt.Toolkit;

import javax.swing.JButton;

import merlin.gui.enums.ExitCode;
import static merlin.gui.enums.ExitCode.*;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class MerlinMain {

	private static ExitCode exitCode = DIALOG_ABORTED;
	private JFrame frmMerlinMain;

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
					MerlinMain window = new MerlinMain();
					window.frmMerlinMain.setVisible(true);
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
	public MerlinMain() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmMerlinMain = new JFrame();
		frmMerlinMain.setIconImage(Toolkit.getDefaultToolkit().getImage(MerlinMain.class.getResource("/javax/swing/plaf/metal/icons/ocean/hardDrive.gif")));
		frmMerlinMain.setTitle("MERLIN Vogelbeobachtungen");
		frmMerlinMain.setBounds(50, 50, 1024, 800);
		frmMerlinMain.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmMerlinMain.getContentPane().setLayout(null);
		
		JPanel userPanel = new JPanel();
		userPanel.setBackground(Color.LIGHT_GRAY);
		userPanel.setBounds(0, 0, 1018, 43);
		frmMerlinMain.getContentPane().add(userPanel);
		userPanel.setLayout(null);
		
		JButton btnAusloggen = new JButton("Ausloggen");
		btnAusloggen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				/* ABFRAGE, OB WIRKLICH BEENDET WERDEN SOLL.
				 * Dementsprechend exitCode setzen oder nicht.
				 */
				// exitCode = LOGOUT_BUTTON_PUSHED;				
			}
		});
		btnAusloggen.setBounds(913, 11, 95, 23);
		userPanel.add(btnAusloggen);
		
		JButton btnEinstellungen = new JButton("Einstellungen");
		btnEinstellungen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				/* TODO Einstellungen Dialog anzeigen */
				/* TODO Einstlelungen Dialog GUI-Design und Implementation */
			}
		});
		btnEinstellungen.setBounds(808, 11, 95, 23);
		userPanel.add(btnEinstellungen);
	}
}
