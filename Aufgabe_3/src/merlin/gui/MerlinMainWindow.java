package merlin.gui;

import static merlin.gui.enums.ExitCode.DIALOG_ABORTED;
import static merlin.utils.ConstantElems.windowMainTitle;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dialog.ModalExclusionType;
import java.awt.EventQueue;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import merlin.gui.enums.ExitCode;

public class MerlinMainWindow {

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
					MerlinMainWindow window = new MerlinMainWindow();
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
	public MerlinMainWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmMerlinMain = new JFrame();
		frmMerlinMain.setModalExclusionType(ModalExclusionType.APPLICATION_EXCLUDE);
		frmMerlinMain.setResizable(false);
		frmMerlinMain.setIconImage(Toolkit.getDefaultToolkit().getImage(MerlinMainWindow.class.getResource("/javax/swing/plaf/metal/icons/ocean/hardDrive.gif")));
		frmMerlinMain.setTitle(windowMainTitle);
		frmMerlinMain.setBounds(50, 50, 1024, 800);
		frmMerlinMain.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmMerlinMain.getContentPane().setLayout(null);
		
		JPanel panelUserBox = new JPanel();
		panelUserBox.setBackground(Color.LIGHT_GRAY);
		panelUserBox.setBounds(0, 0, 1008, 43);
		frmMerlinMain.getContentPane().add(panelUserBox);
		panelUserBox.setLayout(null);
		
		JButton btnAusloggen = new JButton("Ausloggen");
		btnAusloggen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				/* ABFRAGE, OB WIRKLICH BEENDET WERDEN SOLL.
				 * Dementsprechend exitCode setzen oder nicht.
				 */
				// exitCode = LOGOUT_BUTTON_PUSHED;				
			}
		});
		btnAusloggen.setBounds(903, 11, 95, 23);
		panelUserBox.add(btnAusloggen);
		
		JButton btnEinstellungen = new JButton("Einstellungen");
		btnEinstellungen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				/* TODO Einstellungen Dialog anzeigen */
				/* TODO Einstlelungen Dialog GUI-Design und Implementation */
			}
		});
		btnEinstellungen.setBounds(798, 11, 95, 23);
		panelUserBox.add(btnEinstellungen);
		
		JButton btnPrev = new JButton("<");
		btnPrev.setBounds(10, 11, 89, 23);
		panelUserBox.add(btnPrev);
		
		JButton btnNext = new JButton(">");
		btnNext.setBounds(109, 11, 89, 23);
		panelUserBox.add(btnNext);
		
		JComboBox<String> comboBox = new JComboBox<String>();
		comboBox.setModel(new DefaultComboBoxModel<String>(new String[] {"Checkliste", "Beobachtungsliste"}));
		comboBox.setBounds(208, 11, 139, 22);
		panelUserBox.add(comboBox);
		
		JPanel panelMainCard = new JPanel();
		panelMainCard.setBounds(0, 43, 1008, 719);
		frmMerlinMain.getContentPane().add(panelMainCard);
		panelMainCard.setLayout(new CardLayout(0, 0));
		
		JPanel panelChecklist = new JPanel();
		panelChecklist.setBackground(SystemColor.inactiveCaptionText);
		panelMainCard.add(panelChecklist, "name_7272742590096");
		panelChecklist.setLayout(null);
		
		JPanel panelTest = new JPanel();
		panelTest.setBounds(154, 102, 194, 121);
		panelChecklist.add(panelTest);
		
		JPanel panelWatchlist = new JPanel();
		panelWatchlist.setBackground(SystemColor.inactiveCaption);
		panelMainCard.add(panelWatchlist, "name_7282106867844");
		panelWatchlist.setLayout(null);
		
		JPanel panelTest2 = new JPanel();
		panelTest2.setBounds(578, 313, 194, 121);
		panelWatchlist.add(panelTest2);
		
		JPanel panelDatabaseSetup = new JPanel();
		panelMainCard.add(panelDatabaseSetup, "name_908496143132");
	}
}
