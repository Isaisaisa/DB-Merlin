package merlin.gui;

import java.awt.AWTEvent;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Vector;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.JToggleButton;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.SpinnerDateModel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import merlin.base.Application;
import merlin.data.BirdwatcherRepository;
import merlin.data.enums.SpeciesCategoryEnum;
import merlin.logic.impl.MainWindowLogic;
import merlin.utils.ConstantElems;

import org.eclipse.wb.swing.FocusTraversalOnArray;

import com.toedter.calendar.JDateChooser;

import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import java.awt.event.ContainerAdapter;
import java.awt.event.ContainerEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;


public class MerlinMainWindow {

	private JFrame frmMerlinMain;
	private JTable tableCheckliste;
	private JTable tblStammdatenBeob;
	private JPanel panelMain;
	private JPanel panelAdminCards;
	private String level1;
	private String level2;
	private String level3;
	private static JComboBox<String> cmbLevel1;
	private static JComboBox<String> cmbLevel2;
	private static JComboBox<String> cmbLevel3;
	private JTable tblBeobachtungsliste;
	private JTable tableManQuery;
	private static JComboBox<String> cmbRegionAdd;
	private static JComboBox<String> cmbLandAdd;
	private static JComboBox<String> cmbGebietAdd;
	private String level_1;
	private String level_2;
	private String level_3;
	private JSpinner uhrzeitVom;
	private JSpinner uhrzeitBis;
	private JDateChooser datumVom;
	private JDateChooser datumBis;
	private SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
	private SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
	private String formatVon;
	private String formatBis;
	private String notice;

	private JTextArea txtComment;
	private JTextField txtFilterCoreData;
	private JTextField txtFilterObservation;
	
	private final Color   btnNormalHover 	 = new Color(34,153,176);
	private final Color   btnNormalNoHover 	 = new Color(0,0,0);
	private final Color   btnCriticalHover	 = new Color(255,0,0);
	private final Color   btnCriticalNoHover = new Color(0,0,0);
	private JToggleButton tglbtnBeobachtungsliste;
	private JToggleButton tglbtnCheckliste;
	private JToggleButton tglbtnVerwaltung;
	
	private JToggleButton tglbtnAdminStammdaten;
	private JToggleButton tglbtnAdminOrte;
	private JToggleButton tglbtnAdminChecklisten;
	private JToggleButton tglbtnBenutzer;
	private JToggleButton tglbtnAdminManQuery;
	private JTextField txtAdminCoreDataFilter;
	private JTable tableAdminCoreData;
	private JTable tableAdminCoreDataConflicts;
	private JTextField txtAdminAddBirdNameLat;
	private JTextField txtAdminAddBirdNameDe;
	private JTextField txtAdminAddBirdNameEng;
	private JTextField txtAdminEditBirdNameLat;
	private JTextField txtAdminEditBirdNameDe;
	private JTextField txtAdminEditBirdNameEng;
	private JTable tableAdminLokationen;
	private JTextField txtAddRegion;
	private JTextField txtAddLand;
	private JTextField txtAddLokation;
	private JTextField textField_3;
	private JTextField textField_4;
	private JTextField textField_5;
	private JTextField txtAdminChecklistCoreDataFilter;
	private JTable tableAdminChecklistSelection;
	private JTable tableAdminChecklistCoreData;
	private JTable tableAdminUser;
	private JButton btnDeleteObservation;
	private JButton btnAddObservation;
	private JLabel lblWillkommenstext;
	private JCheckBox chkDatumBis;
	private JCheckBox chkFilterLifer;
	private JCheckBox chkFilterTicks;
	private String filter;
	private boolean ticks;
	private boolean lifer;
	private JComboBox<String> cmbAdminCoreDataSpecType;
	private JComboBox<String> cmbAdminAddBirdSpecType;
	

	/**
	 * Launch the application.
	 * @throws UnsupportedLookAndFeelException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 * @throws ClassNotFoundException 
	 */
	public static void main() throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
		
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MerlinMainWindow window = new MerlinMainWindow();
					
					// center window on screen and make it visible
					Application.getInstance().centerWindow(window.frmMerlinMain);
					window.frmMerlinMain.setVisible(true);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
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
		frmMerlinMain.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				try {
					Application.getInstance().closeMerlinYesNo(frmMerlinMain);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		frmMerlinMain.setIconImage(Toolkit.getDefaultToolkit().getImage(MerlinMainWindow.class.getResource("/javax/swing/plaf/metal/icons/ocean/hardDrive.gif")));
		frmMerlinMain.setTitle(ConstantElems.windowMainTitle);
		frmMerlinMain.setBounds(50, 50, 1116, 806);
		frmMerlinMain.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmMerlinMain.getContentPane().setLayout(null);
		
		JPanel panelUser = new JPanel();
		panelUser.setBackground(SystemColor.inactiveCaption);
		panelUser.setBounds(0, 0, 1100, 43);
		frmMerlinMain.getContentPane().add(panelUser);
		panelUser.setLayout(null);

		final JButton btnBeenden = new JButton("Beenden");
		btnBeenden.setToolTipText("Sie wollen also wirklich schon gehen?");
		btnBeenden.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent arg0) {
				btnBeenden.setForeground(btnCriticalHover);
			}
			@Override
			public void mouseExited(MouseEvent arg0) {
				btnBeenden.setForeground(btnCriticalNoHover);
			}
		});
		btnBeenden.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnBeenden.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					Application.getInstance().closeMerlinYesNo(frmMerlinMain);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		btnBeenden.setFocusable(false);
		
		btnBeenden.setBounds(930, 9, 160, 25);
		panelUser.add(btnBeenden);
		
		tglbtnBeobachtungsliste = new JToggleButton("Beobachtungsliste");
		tglbtnBeobachtungsliste.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				tglbtnBeobachtungsliste.setToolTipText( ( tglbtnBeobachtungsliste.isEnabled() )?(null):("Nur für Birdwatcher!") );
			}
		});
		tglbtnBeobachtungsliste.setEnabled(false);
		tglbtnBeobachtungsliste.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent arg0) {
				if (tglbtnBeobachtungsliste.isEnabled()) tglbtnBeobachtungsliste.setForeground(btnNormalHover);
			}
			@Override
			public void mouseExited(MouseEvent arg0) {
				if (tglbtnBeobachtungsliste.isEnabled()) tglbtnBeobachtungsliste.setForeground(btnNormalNoHover);
			}
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if (!tglbtnBeobachtungsliste.isEnabled()) {
					if (arg0.getClickCount() == 3) {
						tglbtnBeobachtungsliste.setEnabled(true);
						initBirdwatcherPanel();
					}
				}
			}
		});
		tglbtnBeobachtungsliste.setFont(new Font("Tahoma", Font.PLAIN, 15));
		
		
		tglbtnCheckliste = new JToggleButton("Checkliste");
		tglbtnCheckliste.setVisible(false);
		tglbtnCheckliste.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				tglbtnCheckliste.setToolTipText( ( tglbtnBeobachtungsliste.isEnabled() )?(null):("Nur für Birdwatcher!") );
			}
		});
		tglbtnCheckliste.setEnabled(false);
		tglbtnCheckliste.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent arg0) {
				if (tglbtnCheckliste.isEnabled()) tglbtnCheckliste.setForeground(btnNormalHover);
			}
			@Override
			public void mouseExited(MouseEvent arg0) {
				if (tglbtnCheckliste.isEnabled()) tglbtnCheckliste.setForeground(btnNormalNoHover);
			}
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if (!tglbtnCheckliste.isEnabled()) {
					ConstantElems.showMsgBox("Nur für Birdwatcher!");
				}
			}
		});
		tglbtnCheckliste.setFont(new Font("Tahoma", Font.PLAIN, 15));
		
		tglbtnVerwaltung = new JToggleButton("Verwaltung");
		tglbtnVerwaltung.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				tglbtnVerwaltung.setToolTipText( ( tglbtnBeobachtungsliste.isEnabled() )?(null):("Nur für (Content-)Admins!") );
			}
		});
		tglbtnVerwaltung.setEnabled(false);
		tglbtnVerwaltung.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent arg0) {
				if (tglbtnVerwaltung.isEnabled()) tglbtnVerwaltung.setForeground(btnNormalHover);
			}
			@Override
			public void mouseExited(MouseEvent arg0) {
				if (tglbtnVerwaltung.isEnabled()) tglbtnVerwaltung.setForeground(btnNormalNoHover);
			}
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if (!tglbtnVerwaltung.isEnabled()) {
					if (arg0.getClickCount() == 3) {
						tglbtnVerwaltung.setEnabled(true);
						initAdminPanel();
					}
				}
			}
		});
		tglbtnVerwaltung.setFont(new Font("Tahoma", Font.PLAIN, 15));
		
		tglbtnBeobachtungsliste.setFocusable(false);
		tglbtnBeobachtungsliste.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				 CardLayout cl = (CardLayout)(panelMain.getLayout());
			     cl.show(panelMain, "panel_beobachtungsliste");
			     
//			     tglbtnBeobachtungsliste.setSelected(true);
//			     tglbtnCheckliste.setSelected(false);
//			     tglbtnVerwaltung.setSelected(false);
			     toggleCardButtons(true, false, false);
			}
		});
		tglbtnBeobachtungsliste.setBounds(10, 9, 160, 25);
		panelUser.add(tglbtnBeobachtungsliste);
		
		
		
		tglbtnCheckliste.setFocusable(false);
		tglbtnCheckliste.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				 CardLayout cl = (CardLayout)(panelMain.getLayout());
			     cl.show(panelMain, "panel_checkliste");
			     
//			     tglbtnBeobachtungsliste.setSelected(false);
//			     tglbtnCheckliste.setSelected(true);
//			     tglbtnVerwaltung.setSelected(false);
			     toggleCardButtons(false, true, false);
			    
			}
		});
		tglbtnCheckliste.setBounds(350, 9, 160, 25);
		panelUser.add(tglbtnCheckliste);
		
		
		tglbtnVerwaltung.setFocusable(false);
		tglbtnVerwaltung.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				CardLayout cl = (CardLayout)(panelMain.getLayout());
			    cl.show(panelMain, "panel_verwaltung");
			    
//			    tglbtnBeobachtungsliste.setSelected(false);
//			    tglbtnCheckliste.setSelected(false);
//			    tglbtnVerwaltung.setSelected(true);
			    toggleCardButtons(false, false, true);
			}
		});
		tglbtnVerwaltung.setBounds(180, 9, 160, 25);
		panelUser.add(tglbtnVerwaltung);
		
		lblWillkommenstext = new JLabel("Herzlich Willkommen, Vorname!");
		lblWillkommenstext.setHorizontalAlignment(SwingConstants.RIGHT);
		lblWillkommenstext.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblWillkommenstext.setBounds(520, 9, 384, 25);
		panelUser.add(lblWillkommenstext);
		
		panelMain = new JPanel();
		panelMain.setBounds(0, 43, 1100, 725);
		frmMerlinMain.getContentPane().add(panelMain);
		panelMain.setLayout(new CardLayout(0, 0));
		
		
		//CHECKLISTE
		
		
		JPanel panelCheckliste = new JPanel();
		panelMain.add(panelCheckliste, "panel_checkliste");
		panelCheckliste.setLayout(null);
		
		JPanel panelOrtsfilter = new JPanel();
		panelOrtsfilter.setBorder(new TitledBorder(null, "Nach Ort filtern", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelOrtsfilter.setBounds(10, 11, 810, 703);
		panelCheckliste.add(panelOrtsfilter);
		panelOrtsfilter.setLayout(null);
		
		JLabel lblLevel1 = new JLabel("Zoo\u00F6kologische Region");
		lblLevel1.setBounds(14, 16, 132, 20);
		panelOrtsfilter.add(lblLevel1);
		
		JLabel lblLevel2 = new JLabel("Land");
		lblLevel2.setBounds(155, 16, 132, 20);
		panelOrtsfilter.add(lblLevel2);
		
		JLabel lblLevel3 = new JLabel("Lokation");
		lblLevel3.setBounds(297, 16, 131, 20);
		panelOrtsfilter.add(lblLevel3);
		
		cmbLevel1 = new JComboBox<String>();
		cmbLevel1.addPopupMenuListener(new PopupMenuListener() {
			public void popupMenuCanceled(PopupMenuEvent arg0) {
			}
			public void popupMenuWillBecomeInvisible(PopupMenuEvent arg0) {
			}
			public void popupMenuWillBecomeVisible(PopupMenuEvent arg0) {
				 cmbLevel1.setModel( new DefaultComboBoxModel<String>(MainWindowLogic.loadRegion()) );	
			}
		});
		cmbLevel1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				level1 = cmbLevel1.getSelectedItem().toString();
//				MainWindowLogic.selectLocation(level1);
			}
		});
		cmbLevel1.setBounds(10, 36, 136, 20);
		panelOrtsfilter.add(cmbLevel1);
		
		
		
		cmbLevel2 = new JComboBox<String>();
		cmbLevel2.addPopupMenuListener(new PopupMenuListener() {
			public void popupMenuCanceled(PopupMenuEvent arg0) {
			}
			public void popupMenuWillBecomeInvisible(PopupMenuEvent arg0) {
			}
			public void popupMenuWillBecomeVisible(PopupMenuEvent arg0) {
				cmbLevel2.setModel( new DefaultComboBoxModel<String>(MainWindowLogic.loadLand(level1)));	
			}
		});
		cmbLevel2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				level2 = cmbLevel2.getSelectedItem().toString();
//				MainWindowLogic.selectLocation(level1, level2);
			}
		});
		cmbLevel2.setBounds(151, 36, 136, 20);
		panelOrtsfilter.add(cmbLevel2);
		
		
		
		cmbLevel3 = new JComboBox<String>();
		cmbLevel3.addPopupMenuListener(new PopupMenuListener() {
			public void popupMenuCanceled(PopupMenuEvent arg0) {
			}
			public void popupMenuWillBecomeInvisible(PopupMenuEvent arg0) {
			}
			public void popupMenuWillBecomeVisible(PopupMenuEvent arg0) {
				cmbLevel3.setModel( new DefaultComboBoxModel<String>(MainWindowLogic.loadArea(level1, level2)));
			}
		});
		cmbLevel3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				level3 = cmbLevel3.getSelectedItem().toString();
				// Glaube das wird hier nciht benötigt
//				MainWindowLogic.selectLocation(level1, level2, level3);
			}
		});
		cmbLevel3.setBounds(292, 36, 136, 20);
		panelOrtsfilter.add(cmbLevel3);
		
		JButton btnChecklisteFiltern = new JButton("Filtern");
		btnChecklisteFiltern.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				System.out.println(level1);
				System.out.println(level2); //evtl. null 
				System.out.println(level3); //evtl. null 
				
				tableCheckliste.setModel(MainWindowLogic.selectLocation(level1, level2, level3));
			}
		});
		btnChecklisteFiltern.setBounds(438, 35, 89, 23);
		panelOrtsfilter.add(btnChecklisteFiltern);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 67, 790, 625);
		panelOrtsfilter.add(scrollPane);
		
		tableCheckliste = new JTable();
		tableCheckliste.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tableCheckliste.setVerifyInputWhenFocusTarget(false);
		tableCheckliste.setBackground(Color.WHITE);
		scrollPane.setViewportView(tableCheckliste);
		
		
		
		
		
		
		
		
		//BEOBACHTUNGSLISTE
		
		
		
		JPanel panelBeobachtungsliste = new JPanel();
		panelBeobachtungsliste.setBackground(SystemColor.menu);
		panelMain.add(panelBeobachtungsliste, "panel_beobachtungsliste");
		panelBeobachtungsliste.setLayout(null);
		
		JPanel panelCoreDataTable = new JPanel();
		panelCoreDataTable.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Checkliste", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panelCoreDataTable.setBounds(4, 11, 810, 350);
		panelBeobachtungsliste.add(panelCoreDataTable);
		panelCoreDataTable.setLayout(null);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(10, 59, 790, 280);
		panelCoreDataTable.add(scrollPane_1);
		scrollPane_1.setBackground(SystemColor.menu);
		scrollPane_1.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		
		// anmeldung des buttons getrennt von der konifguration an dieser stelle, damit referenz des buttons gültig ist, da tblStammdatenBeob darauf zugreift
		btnAddObservation = new JButton("Eintrag hinzuf\u00FCgen");
		
		tblStammdatenBeob = new JTable();
		tblStammdatenBeob.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent arg0) {
				btnAddObservation.setEnabled( (tblStammdatenBeob.getSelectedRow() > -1) );
			}
		});
		tblStammdatenBeob.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tblStammdatenBeob.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				//VogelId aus der Tabelle holen
				btnAddObservation.setEnabled( (tblStammdatenBeob.getSelectedRow() > -1) );
					
			}
			@Override
			public void mouseReleased(MouseEvent arg0) {
				//VogelId aus der Tabelle holen
				btnAddObservation.setEnabled( (tblStammdatenBeob.getSelectedRow() > -1) );
			}
		});
		tblStammdatenBeob.setBackground(Color.WHITE);
		
		scrollPane_1.setViewportView(tblStammdatenBeob);
//		tblStammdatenBeob.setModel(MainWindowLogic.loadTableDataIntoGui());
		
		txtFilterCoreData = new JTextField();
		txtFilterCoreData.setBounds(82, 28, 230, 20);
		panelCoreDataTable.add(txtFilterCoreData);
		txtFilterCoreData.setColumns(10);
		
		JLabel lblVolltextfilter = new JLabel("Volltextfilter:");
		lblVolltextfilter.setBounds(10, 28, 62, 20);
		panelCoreDataTable.add(lblVolltextfilter);
		
		JLabel label_17 = new JLabel("Artentyp:");
		label_17.setBounds(433, 28, 47, 20);
		panelCoreDataTable.add(label_17);
		
		JComboBox<String> cmbFilterCoreDataSpecType = new JComboBox<String>();
		cmbFilterCoreDataSpecType.setModel(new DefaultComboBoxModel<String>(new String[] {"Alle", "Oberarten", "Unterarten"}));
		cmbFilterCoreDataSpecType.setBounds(490, 28, 120, 20);
		panelCoreDataTable.add(cmbFilterCoreDataSpecType);
		
		JButton btnFilterCoreData = new JButton("Filtern");
		btnFilterCoreData.setBounds(322, 27, 89, 23);
		panelCoreDataTable.add(btnFilterCoreData);
		// TODO: säubern, wenn nicht mehr weiter benötigt
//		tblStammdatenBeob.getColumnModel().getColumn(0).setPreferredWidth(0);
//		tblStammdatenBeob.getColumnModel().getColumn(0).setMinWidth(0);
//		tblStammdatenBeob.getColumnModel().getColumn(0).setMaxWidth(0);
//		tblStammdatenBeob.getColumnModel().getColumn(0).setWidth(0);
//		tblStammdatenBeob.getColumnModel().getColumn(0).setResizable(false);
		
		JPanel panelObservationTable = new JPanel();
		panelObservationTable.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Beobachtungen", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panelObservationTable.setBounds(6, 369, 1084, 350);
		panelBeobachtungsliste.add(panelObservationTable);
		panelObservationTable.setLayout(null);
		
		
		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(10, 61, 1064, 280);
		panelObservationTable.add(scrollPane_2);
		scrollPane_2.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		
		btnDeleteObservation = new JButton("Gew\u00E4hlten Eintrag l\u00F6schen");
		btnDeleteObservation.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if (tblBeobachtungsliste.getSelectedColumn() <= -1) dlgDoSelectionFirst();
			}
		});
		btnDeleteObservation.setEnabled(false);
		btnDeleteObservation.setBackground(Color.PINK);
		btnDeleteObservation.setBounds(564, 27, 226, 23);
		panelObservationTable.add(btnDeleteObservation);
		btnDeleteObservation.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				if (dlgYesNo("Wollen Sie die gewählte Beobachtung wirklich löschen?", "Beobachtung löschen") == JOptionPane.NO_OPTION) {
					return;
				}
				
				//Beo-ID
				String beoId = (String)tblBeobachtungsliste.getValueAt(tblBeobachtungsliste.getSelectedRow(), 0);
			
				MainWindowLogic.deleteDataObservation(beoId);
				tblBeobachtungsliste.setModel(MainWindowLogic.getDataObservation());
				
						
			}
		});
		btnDeleteObservation.setFocusable(false);
		
		tblBeobachtungsliste = new JTable();
		tblBeobachtungsliste.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent arg0) {
				if (tblBeobachtungsliste.getSelectedRow() > -1) {
					if (arg0.getKeyCode() == KeyEvent.VK_DELETE || arg0.getKeyCode() == KeyEvent.VK_BACK_SPACE) btnDeleteObservation.doClick();
				}
			}
		});
		tblBeobachtungsliste.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent arg0) {
				btnDeleteObservation.setEnabled( (tblBeobachtungsliste.getSelectedRow() > -1) );
			}
		});
		tblBeobachtungsliste.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				btnDeleteObservation.setEnabled( (tblBeobachtungsliste.getSelectedRow() > -1) );
			}
		});
		tblBeobachtungsliste.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPane_2.setViewportView(tblBeobachtungsliste);
		tblBeobachtungsliste.setModel(MainWindowLogic.getDataObservation());
//		filterSelection();
		
		JLabel label = new JLabel("Volltextfilter:");
		label.setBounds(10, 28, 62, 20);
		panelObservationTable.add(label);
		
		txtFilterObservation = new JTextField();
		txtFilterObservation.getDocument().addDocumentListener(new DocumentListener() {
			
			public void removeUpdate(DocumentEvent arg0) {
				filterSelection();				
			}
			
			public void insertUpdate(DocumentEvent arg0) {
				filterSelection();
			}
			
			public void changedUpdate(DocumentEvent arg0) {
				filterSelection();
			}
		});
		txtFilterObservation.setColumns(10);
		txtFilterObservation.setBounds(82, 28, 230, 20);
		panelObservationTable.add(txtFilterObservation);
		filter = txtFilterObservation.getText();
		
		JButton btnFilterObservation = new JButton("Filtern");
		btnFilterObservation.setBounds(322, 27, 89, 23);
		panelObservationTable.add(btnFilterObservation);
		
		chkFilterLifer = new JCheckBox("Lifer");
		chkFilterLifer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(chkFilterLifer.isSelected()){
					filterSelection();
				}
			}
		});
		chkFilterLifer.setToolTipText("Akivieren ");
		chkFilterLifer.setBounds(446, 27, 55, 23);
		panelObservationTable.add(chkFilterLifer);
				
		chkFilterTicks = new JCheckBox("Ticks");
		chkFilterTicks.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(chkFilterTicks.isSelected()){
					filterSelection();
				}
			}
		});
		chkFilterTicks.setToolTipText("Akivieren ");
		chkFilterTicks.setBounds(503, 27, 55, 23);
		panelObservationTable.add(chkFilterTicks);
		
		JPanel panelAddObservation = new JPanel();
		panelAddObservation.setBackground(SystemColor.control);
		panelAddObservation.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Beobachtung hinzuf\u00FCgen", TitledBorder.LEFT, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panelAddObservation.setBounds(824, 11, 266, 350);
		panelBeobachtungsliste.add(panelAddObservation);
		panelAddObservation.setLayout(null);
		
		JPanel panelAddObLocation = new JPanel();
		panelAddObLocation.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Ort", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panelAddObLocation.setBounds(10, 20, 247, 103);
		panelAddObservation.add(panelAddObLocation);
		panelAddObLocation.setLayout(null);
		
		JLabel lbRegion = new JLabel("Z.-Region:");
		lbRegion.setFocusTraversalKeysEnabled(false);
		lbRegion.setFocusable(false);
		lbRegion.setBounds(10, 18, 65, 20);
		panelAddObLocation.add(lbRegion);
		lbRegion.setFont(new Font("Tahoma", Font.PLAIN, 13));
		
		JLabel lblLand = new JLabel("Land:");
		lblLand.setFocusable(false);
		lblLand.setFocusTraversalKeysEnabled(false);
		lblLand.setBounds(10, 45, 65, 20);
		panelAddObLocation.add(lblLand);
		lblLand.setFont(new Font("Tahoma", Font.PLAIN, 13));
		
		JLabel lblGebiet = new JLabel("Lokation:");
		lblGebiet.setBounds(10, 72, 65, 20);
		panelAddObLocation.add(lblGebiet);
		lblGebiet.setFont(new Font("Tahoma", Font.PLAIN, 13));
	
		
		

		cmbRegionAdd = new  JComboBox<String>();
		cmbRegionAdd.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				level_1 = cmbRegionAdd.getSelectedItem().toString();
				level_2 = "";
				level_3 = "";
				cmbLandAdd.setModel(new DefaultComboBoxModel<String>(MainWindowLogic.loadLand(level_1)));
				cmbGebietAdd.setModel(new DefaultComboBoxModel<String>(MainWindowLogic.loadArea(level_1, level_2)));
				tblStammdatenBeob.setModel(MainWindowLogic.selectLocation(level_1, level_2, level_3));
				filterSelection();
			}
		});
		cmbRegionAdd.setBounds(85, 18, 152, 20);
		panelAddObLocation.add(cmbRegionAdd);
		
		
		
		cmbLandAdd = new JComboBox<String>();
		cmbLandAdd.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				level_2 = cmbLandAdd.getSelectedItem().toString();
				level_3 = "";
				cmbGebietAdd.setModel(new DefaultComboBoxModel<String>(MainWindowLogic.loadArea(level_1, level_2)));
				tblStammdatenBeob.setModel(MainWindowLogic.selectLocation(level_1, level_2, level_3));
				filterSelection();
			}
		});
		cmbLandAdd.setBounds(85, 46, 152, 20);
		panelAddObLocation.add(cmbLandAdd);
		
		
		
		cmbGebietAdd = new JComboBox<String>();
		cmbGebietAdd.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				level_3 = cmbGebietAdd.getSelectedItem().toString();
				tblStammdatenBeob.setModel(MainWindowLogic.selectLocation(level_1, level_2, level_3));
				filterSelection();
			}
		});
		cmbGebietAdd.setBounds(85, 73, 152, 20);
		panelAddObLocation.add(cmbGebietAdd);
		panelAddObLocation.setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[]{cmbRegionAdd, cmbLandAdd, cmbGebietAdd, lbRegion, lblLand, lblGebiet}));
		
		JPanel panelAddObDate = new JPanel();
		panelAddObDate.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Datum", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panelAddObDate.setBounds(10, 134, 247, 79);
		panelAddObservation.add(panelAddObDate);
		panelAddObDate.setLayout(null);
		
		JLabel lblVom = new JLabel("Vom:");
		lblVom.setBounds(31, 18, 31, 20);
		panelAddObDate.add(lblVom);
		lblVom.setHorizontalAlignment(SwingConstants.RIGHT);
		lblVom.setFont(new Font("Tahoma", Font.PLAIN, 13));
		
		final JLabel lblBis = new JLabel("Bis:");
		lblBis.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if (!lblBis.isEnabled()) chkDatumBis.doClick();
			}
		});
		lblBis.setEnabled(false);
		lblBis.setBounds(31, 48, 21, 20);
		panelAddObDate.add(lblBis);
		lblBis.setHorizontalAlignment(SwingConstants.RIGHT);
		lblBis.setFont(new Font("Tahoma", Font.PLAIN, 13));
		
		datumVom = new JDateChooser();
		datumVom.setBounds(86, 18, 89, 20);
		panelAddObDate.add(datumVom);
		
		datumBis = new JDateChooser();
		datumBis.getCalendarButton().setEnabled(false);
		datumBis.setEnabled(false);
		datumBis.getCalendarButton().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if (!datumBis.getCalendarButton().isEnabled()) chkDatumBis.doClick();
			}
		});
		datumBis.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if (!datumBis.isEnabled()) chkDatumBis.doClick();
			}
		});
		datumBis.setBounds(86, 48, 89, 20);
		panelAddObDate.add(datumBis);
		
		SpinnerDateModel model1 = new SpinnerDateModel();
		model1.setCalendarField(Calendar.MINUTE);
		
		uhrzeitVom = new JSpinner();
		uhrzeitVom.setBounds(185, 18, 52, 20);
		panelAddObDate.add(uhrzeitVom);
		uhrzeitVom.setModel(model1);
		uhrzeitVom.setEditor(new JSpinner.DateEditor(uhrzeitVom, "HH:mm"));
		
		SpinnerDateModel model2 = new SpinnerDateModel();
		model2.setCalendarField(Calendar.MINUTE);
		
		uhrzeitBis = new JSpinner();
		uhrzeitBis.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if (!uhrzeitBis.isEnabled()) chkDatumBis.doClick();
			}
		});
		uhrzeitBis.setEnabled(false);
		uhrzeitBis.setBounds(185, 48, 52, 20);
		panelAddObDate.add(uhrzeitBis);
		uhrzeitBis.setModel(model2);
		uhrzeitBis.setEditor(new JSpinner.DateEditor(uhrzeitBis, "HH:mm"));
		
		chkDatumBis = new JCheckBox("");
		chkDatumBis.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				lblBis.setEnabled(chkDatumBis.isSelected());
				datumBis.setEnabled(chkDatumBis.isSelected());
				uhrzeitBis.setEnabled(chkDatumBis.isSelected());
			}
		});
		chkDatumBis.setBounds(6, 48, 20, 20);
		panelAddObDate.add(chkDatumBis);
		chkDatumBis.setToolTipText("Akivieren ");
		
		JCheckBox checkBox = new JCheckBox("");
		checkBox.setEnabled(false);
		checkBox.setSelected(true);
		checkBox.setToolTipText("Akivieren ");
		checkBox.setBounds(6, 18, 20, 20);
		panelAddObDate.add(checkBox);
		panelAddObDate.setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[]{checkBox, datumVom, uhrzeitVom, chkDatumBis, datumBis, uhrzeitBis, lblVom, lblBis, datumVom.getCalendarButton(), datumBis.getCalendarButton()}));
		
		JPanel panelComment = new JPanel();
		panelComment.setBounds(10, 224, 247, 87);
		panelAddObservation.add(panelComment);
		panelComment.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Bemerkung", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panelComment.setLayout(null);
		
		JScrollPane scrollpanelComment = new JScrollPane();
		scrollpanelComment.setBounds(10, 18, 226, 58);
		panelComment.add(scrollpanelComment);
		
		txtComment = new JTextArea();
		txtComment.setWrapStyleWord(true);
		txtComment.setLineWrap(true);
		txtComment.setFont(new Font("Arial", Font.PLAIN, 11));
		scrollpanelComment.setViewportView(txtComment);
		panelComment.setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[]{scrollpanelComment, txtComment}));
		
		
		
//		btnAddObservation = new JButton("Eintrag hinzuf\u00FCgen");
		btnAddObservation.setEnabled(false);
		btnAddObservation.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if (tblStammdatenBeob.getSelectedColumn() <= -1) dlgDoSelectionFirst();
			}
		});
		btnAddObservation.setBounds(20, 316, 226, 23);
		btnAddObservation.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				addObservation();
			}
		});
		panelAddObservation.add(btnAddObservation);
		panelAddObservation.setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[]{cmbRegionAdd, cmbLandAdd, cmbGebietAdd, checkBox, datumVom, uhrzeitVom, chkDatumBis, datumBis, uhrzeitBis, txtComment, btnAddObservation, panelAddObLocation, lbRegion, lblLand, lblGebiet, panelAddObDate, lblVom, lblBis, datumVom.getCalendarButton(), datumBis.getCalendarButton(), panelComment, scrollpanelComment}));
		
		
		
		//STAMMDATEN
		
		
		
		JPanel panelVerwaltung = new JPanel();
		panelMain.add(panelVerwaltung, "panel_verwaltung");
		panelVerwaltung.setLayout(null);
		
		JPanel panelAdminButtons = new JPanel();
		panelAdminButtons.setBackground(SystemColor.activeCaption);
		panelAdminButtons.setBounds(0, 0, 1100, 43);
		panelVerwaltung.add(panelAdminButtons);
		panelAdminButtons.setLayout(null);
		
		tglbtnAdminStammdaten = new JToggleButton("Stammdaten");
		tglbtnAdminStammdaten.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent arg0) {
				tglbtnAdminStammdaten.setForeground(btnNormalHover);
			}
			@Override
			public void mouseExited(MouseEvent arg0) {
				tglbtnAdminStammdaten.setForeground(btnNormalNoHover);
			}
		});
		tglbtnAdminStammdaten.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				CardLayout cl = (CardLayout)(panelAdminCards.getLayout());
				cl.show(panelAdminCards, "panel_admin_stammdaten");
				
			    toggleAdminCardButtons(true, false, false, false, false);
//			    getCoreData(); // too expensive to load core data each and every f****** time.
			}
		});
		
		tglbtnAdminStammdaten.setFont(new Font("Tahoma", Font.PLAIN, 15));
		tglbtnAdminStammdaten.setFocusable(false);
		tglbtnAdminStammdaten.setBounds(10, 9, 160, 25);
		panelAdminButtons.add(tglbtnAdminStammdaten);
		
		tglbtnAdminOrte = new JToggleButton("Orte");
		tglbtnAdminOrte.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent arg0) {
				tglbtnAdminOrte.setForeground(btnNormalHover);
			}
			@Override
			public void mouseExited(MouseEvent arg0) {
				tglbtnAdminOrte.setForeground(btnNormalNoHover);
			}
		});
		tglbtnAdminOrte.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				CardLayout cl = (CardLayout)(panelAdminCards.getLayout());
				cl.show(panelAdminCards, "panel_admin_lokationen");
				toggleAdminCardButtons(false, true, false, false, false);
				getLocations();
			}
		});
		tglbtnAdminOrte.setFont(new Font("Tahoma", Font.PLAIN, 15));
		tglbtnAdminOrte.setFocusable(false);
		tglbtnAdminOrte.setBounds(180, 9, 160, 25);
		panelAdminButtons.add(tglbtnAdminOrte);
		
		tglbtnAdminChecklisten = new JToggleButton("Checklisten");
		tglbtnAdminChecklisten.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent arg0) {
				tglbtnAdminChecklisten.setForeground(btnNormalHover);
			}
			@Override
			public void mouseExited(MouseEvent arg0) {
				tglbtnAdminChecklisten.setForeground(btnNormalNoHover);
			}
		});
		tglbtnAdminChecklisten.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				CardLayout cl = (CardLayout)(panelAdminCards.getLayout());
				cl.show(panelAdminCards, "panel_admin_checklisten");
				toggleAdminCardButtons(false, false, true, false, false);
			}
		});
		tglbtnAdminChecklisten.setFont(new Font("Tahoma", Font.PLAIN, 15));
		tglbtnAdminChecklisten.setFocusable(false);
		tglbtnAdminChecklisten.setBounds(350, 9, 160, 25);
		panelAdminButtons.add(tglbtnAdminChecklisten);
		
		tglbtnBenutzer = new JToggleButton("Benutzer");
		tglbtnBenutzer.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent arg0) {
				tglbtnBenutzer.setForeground(btnNormalHover);
			}
			@Override
			public void mouseExited(MouseEvent arg0) {
				tglbtnBenutzer.setForeground(btnNormalNoHover);
			}
		});
		tglbtnBenutzer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				CardLayout cl = (CardLayout)(panelAdminCards.getLayout());
				cl.show(panelAdminCards, "panel_admin_benutzer");
				toggleAdminCardButtons(false, false, false, true, false);
			}
		});
		tglbtnBenutzer.setFont(new Font("Tahoma", Font.PLAIN, 15));
		tglbtnBenutzer.setFocusable(false);
		tglbtnBenutzer.setBounds(520, 9, 160, 25);
		panelAdminButtons.add(tglbtnBenutzer);
		
		tglbtnAdminManQuery = new JToggleButton("Manuelle Queries");
		tglbtnAdminManQuery.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent arg0) {
				tglbtnAdminManQuery.setForeground(btnCriticalHover);
			}
			@Override
			public void mouseExited(MouseEvent arg0) {
				tglbtnAdminManQuery.setForeground(btnCriticalNoHover);
			}
		});
		tglbtnAdminManQuery.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				CardLayout cl = (CardLayout)(panelAdminCards.getLayout());
				cl.show(panelAdminCards, "panel_admin_manstatement");
				toggleAdminCardButtons(false, false, false, false, true);
			}
		});
		tglbtnAdminManQuery.setFont(new Font("Tahoma", Font.PLAIN, 15));
		tglbtnAdminManQuery.setFocusable(false);
		tglbtnAdminManQuery.setBounds(690, 9, 160, 25);
		panelAdminButtons.add(tglbtnAdminManQuery);
		
		panelAdminCards = new JPanel();
		panelAdminCards.setBounds(0, 43, 1100, 682);
		panelVerwaltung.add(panelAdminCards);
		panelAdminCards.setLayout(new CardLayout(0, 0));
		
		JPanel panelAdminStammdaten = new JPanel();
		panelAdminCards.add(panelAdminStammdaten, "panel_admin_stammdaten");
		panelAdminStammdaten.setLayout(null);
		
		JPanel panelAdminCoreDataTable = new JPanel();
		panelAdminCoreDataTable.setLayout(null);
		panelAdminCoreDataTable.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Stammdaten", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panelAdminCoreDataTable.setBounds(10, 11, 741, 519);
		panelAdminStammdaten.add(panelAdminCoreDataTable);
		
		JScrollPane scrollPane_3 = new JScrollPane();
		scrollPane_3.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane_3.setBounds(10, 59, 721, 449);
		panelAdminCoreDataTable.add(scrollPane_3);
		
		tableAdminCoreData = new JTable();
		scrollPane_3.setViewportView(tableAdminCoreData);
		tableAdminCoreData.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tableAdminCoreData.setBackground(Color.WHITE);
		
		txtAdminCoreDataFilter = new JTextField();
		txtAdminCoreDataFilter.setColumns(10);
		txtAdminCoreDataFilter.setBounds(82, 28, 230, 20);
		panelAdminCoreDataTable.add(txtAdminCoreDataFilter);
		
		JLabel lblAdminCoreDataFilter = new JLabel("Volltextfilter:");
		lblAdminCoreDataFilter.setBounds(10, 28, 62, 20);
		panelAdminCoreDataTable.add(lblAdminCoreDataFilter);
		
		cmbAdminCoreDataSpecType = new JComboBox<String>();
		cmbAdminCoreDataSpecType.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				System.out.println();
				getCoreData(txtAdminCoreDataFilter.getText(), cmbAdminCoreDataSpecType.getSelectedItem().toString());
			}
		});
		cmbAdminCoreDataSpecType.setModel(new DefaultComboBoxModel<String>(new String[] {"Alle", "Oberarten", "Unterarten"}));
		cmbAdminCoreDataSpecType.setBounds(611, 28, 120, 20);
		panelAdminCoreDataTable.add(cmbAdminCoreDataSpecType);
		
		JLabel lblAdminCoreDataArtentyp = new JLabel("Artentyp:");
		lblAdminCoreDataArtentyp.setBounds(554, 28, 47, 20);
		panelAdminCoreDataTable.add(lblAdminCoreDataArtentyp);
		
		JButton btnAdminCoreDataFilter = new JButton("Filtern");
		btnAdminCoreDataFilter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.out.println();
				getCoreData(txtAdminCoreDataFilter.getText(), cmbAdminCoreDataSpecType.getSelectedItem().toString());
			}
		});
		btnAdminCoreDataFilter.setBounds(322, 27, 89, 23);
		panelAdminCoreDataTable.add(btnAdminCoreDataFilter);
		
		JPanel panelAdminAddBird = new JPanel();
		panelAdminAddBird.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Vogelart hinzuf\u00FCgen", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panelAdminAddBird.setBounds(761, 11, 329, 325);
		panelAdminStammdaten.add(panelAdminAddBird);
		panelAdminAddBird.setLayout(null);
		
		JLabel lblLateinischerName = new JLabel("*Lateinischer Name:");
		lblLateinischerName.setForeground(new Color(0, 0, 128));
		lblLateinischerName.setBounds(10, 34, 97, 20);
		panelAdminAddBird.add(lblLateinischerName);
		
		txtAdminAddBirdNameLat = new JTextField();
		txtAdminAddBirdNameLat.setBounds(117, 34, 200, 20);
		panelAdminAddBird.add(txtAdminAddBirdNameLat);
		txtAdminAddBirdNameLat.setColumns(10);
		
		JLabel lblDeutscherName = new JLabel("Deutscher Name:");
		lblDeutscherName.setBounds(10, 65, 91, 20);
		panelAdminAddBird.add(lblDeutscherName);
		
		txtAdminAddBirdNameDe = new JTextField();
		txtAdminAddBirdNameDe.setColumns(10);
		txtAdminAddBirdNameDe.setBounds(117, 65, 200, 20);
		panelAdminAddBird.add(txtAdminAddBirdNameDe);
		
		JLabel lblEnglischerName = new JLabel("Englischer Name:");
		lblEnglischerName.setBounds(10, 96, 91, 20);
		panelAdminAddBird.add(lblEnglischerName);
		
		txtAdminAddBirdNameEng = new JTextField();
		txtAdminAddBirdNameEng.setColumns(10);
		txtAdminAddBirdNameEng.setBounds(117, 96, 200, 20);
		panelAdminAddBird.add(txtAdminAddBirdNameEng);
		
		JLabel label_1 = new JLabel("Artentyp:");
		label_1.setBounds(10, 127, 47, 20);
		panelAdminAddBird.add(label_1);
		
		cmbAdminAddBirdSpecType = new JComboBox<String>();
		cmbAdminAddBirdSpecType.setModel(new DefaultComboBoxModel<String>(new String[] {"Oberart", "Unterart"}));
		cmbAdminAddBirdSpecType.setBounds(117, 127, 200, 20);
		panelAdminAddBird.add(cmbAdminAddBirdSpecType);
		
		JButton btnAdminAddBird = new JButton("Hinzuf\u00FCgen");
		btnAdminAddBird.setBounds(117, 166, 200, 23);
		panelAdminAddBird.add(btnAdminAddBird);
		
		JLabel lblpflichtangabe = new JLabel("*Pflichtangabe");
		lblpflichtangabe.setForeground(new Color(0, 0, 128));
		lblpflichtangabe.setBounds(10, 197, 71, 14);
		panelAdminAddBird.add(lblpflichtangabe);
		
		JLabel label_18 = new JLabel("<html>Artentyp automatisch anhand lat. namen festlegen. textfeld lat. name rot markieren, wenn name ung\u00FCltig und \u00FCbernehmen ausgrauen</html>");
		label_18.setBounds(117, 200, 200, 51);
		panelAdminAddBird.add(label_18);
		
		JPanel panelAdminEditBird = new JPanel();
		panelAdminEditBird.setLayout(null);
		panelAdminEditBird.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Vogelart bearbeiten (Tabellenauswahl)", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panelAdminEditBird.setBounds(761, 346, 329, 325);
		panelAdminStammdaten.add(panelAdminEditBird);
		
		JLabel label_2 = new JLabel("*Lateinischer Name:");
		label_2.setForeground(new Color(0, 0, 128));
		label_2.setBounds(10, 34, 97, 20);
		panelAdminEditBird.add(label_2);
		
		txtAdminEditBirdNameLat = new JTextField();
		txtAdminEditBirdNameLat.setColumns(10);
		txtAdminEditBirdNameLat.setBounds(117, 34, 200, 20);
		panelAdminEditBird.add(txtAdminEditBirdNameLat);
		
		JLabel label_3 = new JLabel("Deutscher Name:");
		label_3.setBounds(10, 65, 91, 20);
		panelAdminEditBird.add(label_3);
		
		txtAdminEditBirdNameDe = new JTextField();
		txtAdminEditBirdNameDe.setColumns(10);
		txtAdminEditBirdNameDe.setBounds(117, 65, 200, 20);
		panelAdminEditBird.add(txtAdminEditBirdNameDe);
		
		JLabel label_4 = new JLabel("Englischer Name:");
		label_4.setBounds(10, 96, 91, 20);
		panelAdminEditBird.add(label_4);
		
		txtAdminEditBirdNameEng = new JTextField();
		txtAdminEditBirdNameEng.setColumns(10);
		txtAdminEditBirdNameEng.setBounds(117, 96, 200, 20);
		panelAdminEditBird.add(txtAdminEditBirdNameEng);
		
		JLabel label_5 = new JLabel("Artentyp:");
		label_5.setBounds(10, 127, 47, 20);
		panelAdminEditBird.add(label_5);
		
		JComboBox<String> cmbAdminEditBirdSpecType = new JComboBox<String>();
		cmbAdminEditBirdSpecType.setBounds(117, 127, 200, 20);
		panelAdminEditBird.add(cmbAdminEditBirdSpecType);
		
		JButton btnAdminEditBird = new JButton("\u00DCbernehmen");
		btnAdminEditBird.setBounds(117, 166, 200, 23);
		panelAdminEditBird.add(btnAdminEditBird);
		
		JButton btnAdminDeleteBird = new JButton("L\u00F6schen");
		btnAdminDeleteBird.setVisible(false);
		btnAdminDeleteBird.setForeground(new Color(0, 0, 0));
		btnAdminDeleteBird.setBackground(Color.PINK);
		btnAdminDeleteBird.setBounds(117, 200, 200, 23);
		panelAdminEditBird.add(btnAdminDeleteBird);
		
		JLabel label_6 = new JLabel("*Pflichtangabe");
		label_6.setForeground(new Color(0, 0, 128));
		label_6.setBounds(10, 200, 71, 14);
		panelAdminEditBird.add(label_6);
		
		JLabel lblArtentypAutomatischAnhand = new JLabel("<html>Artentyp automatisch anhand lat. namen festlegen. textfeld lat. name rot markieren, wenn name ung\u00FCltig und \u00FCbernehmen ausgrauen</html>");
		lblArtentypAutomatischAnhand.setBounds(117, 234, 200, 80);
		panelAdminEditBird.add(lblArtentypAutomatischAnhand);
		
		JPanel panelAdminCoreDataConflicts = new JPanel();
		panelAdminCoreDataConflicts.setBorder(new TitledBorder(null, "Konflikte", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelAdminCoreDataConflicts.setBounds(10, 541, 741, 130);
		panelAdminStammdaten.add(panelAdminCoreDataConflicts);
		panelAdminCoreDataConflicts.setLayout(null);
		
		JScrollPane scrollPane_5 = new JScrollPane();
		scrollPane_5.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane_5.setBounds(10, 54, 721, 65);
		panelAdminCoreDataConflicts.add(scrollPane_5);
		
		tableAdminCoreDataConflicts = new JTable();
		tableAdminCoreDataConflicts.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tableAdminCoreDataConflicts.setBackground(Color.WHITE);
		scrollPane_5.setViewportView(tableAdminCoreDataConflicts);
		
		JButton btnAdminCoreDataConflicts = new JButton("Ermitteln");
		btnAdminCoreDataConflicts.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// TODO: zB prüfen, ob Lateinischer name 1 oder 2 leerzeichen enthält, also ob der lateinische namen zum angegebenen artentyp passt.
			}
		});
		btnAdminCoreDataConflicts.setBounds(10, 21, 89, 23);
		panelAdminCoreDataConflicts.add(btnAdminCoreDataConflicts);
		
		JPanel panelAdminLokationen = new JPanel();
		panelAdminCards.add(panelAdminLokationen, "panel_admin_lokationen");
		panelAdminLokationen.setLayout(null);
		
		JPanel panelAdminLokationenFrame = new JPanel();
		panelAdminLokationenFrame.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Orte verwalten", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panelAdminLokationenFrame.setBounds(10, 11, 741, 660);
		panelAdminLokationen.add(panelAdminLokationenFrame);
		panelAdminLokationenFrame.setLayout(null);
		
		JScrollPane scrollPane_6 = new JScrollPane();
		scrollPane_6.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane_6.setBounds(10, 59, 721, 590);
		panelAdminLokationenFrame.add(scrollPane_6);
		
		tableAdminLokationen = new JTable();
		tableAdminLokationen.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tableAdminLokationen.setBackground(Color.WHITE);
		scrollPane_6.setViewportView(tableAdminLokationen);
		
		JPanel panelAdminAddLokation = new JPanel();
		panelAdminAddLokation.setLayout(null);
		panelAdminAddLokation.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Ort hinzuf\u00FCgen", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panelAdminAddLokation.setBounds(761, 11, 329, 325);
		panelAdminLokationen.add(panelAdminAddLokation);
		
		JLabel lblzookkRegion = new JLabel("*Zook\u00F6kol. Region:");
		lblzookkRegion.setForeground(new Color(0, 0, 128));
		lblzookkRegion.setBounds(10, 34, 97, 20);
		panelAdminAddLokation.add(lblzookkRegion);
		
		txtAddRegion = new JTextField();
		txtAddRegion.setColumns(10);
		txtAddRegion.setBounds(117, 34, 200, 20);
		panelAdminAddLokation.add(txtAddRegion);
		
		JLabel lblLand_2 = new JLabel("Land:");
		lblLand_2.setBounds(10, 65, 91, 20);
		panelAdminAddLokation.add(lblLand_2);
		
		txtAddLand = new JTextField();
		txtAddLand.setColumns(10);
		txtAddLand.setBounds(117, 65, 200, 20);
		panelAdminAddLokation.add(txtAddLand);
		
		JLabel lblLokation_1 = new JLabel("Lokation:");
		lblLokation_1.setBounds(10, 96, 91, 20);
		panelAdminAddLokation.add(lblLokation_1);
		
		txtAddLokation = new JTextField();
		txtAddLokation.setColumns(10);
		txtAddLokation.setBounds(117, 96, 200, 20);
		panelAdminAddLokation.add(txtAddLokation);
		
		JButton btnAddLocation = new JButton("Hinzuf\u00FCgen");
		btnAddLocation.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				addLocation();
				getLocations();
			}
		});
		btnAddLocation.setBounds(117, 127, 200, 23);
		panelAdminAddLokation.add(btnAddLocation);
		
		JLabel label_11 = new JLabel("*Pflichtangabe");
		label_11.setForeground(new Color(0, 0, 128));
		label_11.setBounds(10, 153, 71, 14);
		panelAdminAddLokation.add(label_11);
		
		JPanel panelAdminEditLokation = new JPanel();
		panelAdminEditLokation.setLayout(null);
		panelAdminEditLokation.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Ort bearbeiten (Tabellenauswahl)", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panelAdminEditLokation.setBounds(761, 346, 329, 325);
		panelAdminLokationen.add(panelAdminEditLokation);
		
		textField_3 = new JTextField();
		textField_3.setColumns(10);
		textField_3.setBounds(117, 34, 200, 20);
		panelAdminEditLokation.add(textField_3);
		
		textField_4 = new JTextField();
		textField_4.setColumns(10);
		textField_4.setBounds(117, 65, 200, 20);
		panelAdminEditLokation.add(textField_4);
		
		textField_5 = new JTextField();
		textField_5.setColumns(10);
		textField_5.setBounds(117, 96, 200, 20);
		panelAdminEditLokation.add(textField_5);
		
		JButton button_1 = new JButton("\u00DCbernehmen");
		button_1.setBounds(117, 127, 200, 23);
		panelAdminEditLokation.add(button_1);
		
		JButton button_2 = new JButton("L\u00F6schen");
		button_2.setForeground(Color.BLACK);
		button_2.setBackground(Color.PINK);
		button_2.setBounds(117, 161, 200, 23);
		panelAdminEditLokation.add(button_2);
		
		JLabel label_12 = new JLabel("*Pflichtangabe");
		label_12.setForeground(new Color(0, 0, 128));
		label_12.setBounds(10, 192, 71, 14);
		panelAdminEditLokation.add(label_12);
		
		JLabel label_7 = new JLabel("*Zook\u00F6kol. Region:");
		label_7.setForeground(new Color(0, 0, 128));
		label_7.setBounds(10, 34, 97, 20);
		panelAdminEditLokation.add(label_7);
		
		JLabel label_8 = new JLabel("Land:");
		label_8.setBounds(10, 65, 91, 20);
		panelAdminEditLokation.add(label_8);
		
		JLabel label_9 = new JLabel("Lokation:");
		label_9.setBounds(10, 96, 91, 20);
		panelAdminEditLokation.add(label_9);
		
		JPanel panelAdminChecklisten = new JPanel();
		panelAdminCards.add(panelAdminChecklisten, "panel_admin_checklisten");
		panelAdminChecklisten.setLayout(null);
		
		JPanel panelAdminChecklistCoreData = new JPanel();
		panelAdminChecklistCoreData.setLayout(null);
		panelAdminChecklistCoreData.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Stammdaten", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panelAdminChecklistCoreData.setBounds(10, 11, 741, 325);
		panelAdminChecklisten.add(panelAdminChecklistCoreData);
		
		txtAdminChecklistCoreDataFilter = new JTextField();
		txtAdminChecklistCoreDataFilter.setColumns(10);
		txtAdminChecklistCoreDataFilter.setBounds(82, 28, 155, 20);
		panelAdminChecklistCoreData.add(txtAdminChecklistCoreDataFilter);
		
		JLabel label_10 = new JLabel("Volltextfilter:");
		label_10.setBounds(10, 28, 62, 20);
		panelAdminChecklistCoreData.add(label_10);
		
		JComboBox<String> cmbAdminChecklistCoreDataSpecType = new JComboBox<String>();
		cmbAdminChecklistCoreDataSpecType.setModel(new DefaultComboBoxModel<String>(new String[] {"Alle", "Oberarten", "Unteraten"}));
		cmbAdminChecklistCoreDataSpecType.setBounds(408, 28, 120, 20);
		panelAdminChecklistCoreData.add(cmbAdminChecklistCoreDataSpecType);
		
		JLabel label_13 = new JLabel("Artentyp:");
		label_13.setBounds(350, 28, 47, 20);
		panelAdminChecklistCoreData.add(label_13);
		
		JButton btnAdminChecklistCoreDataFilter = new JButton("Filtern");
		btnAdminChecklistCoreDataFilter.setBounds(247, 27, 89, 23);
		panelAdminChecklistCoreData.add(btnAdminChecklistCoreDataFilter);
		
		JButton btnAdminChecklistAdd = new JButton("Auswahl hinzuf\u00FCgen");
		btnAdminChecklistAdd.setBounds(551, 27, 180, 23);
		panelAdminChecklistCoreData.add(btnAdminChecklistAdd);
		
		JScrollPane scrollPane_7 = new JScrollPane();
		scrollPane_7.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane_7.setBounds(10, 59, 721, 255);
		panelAdminChecklistCoreData.add(scrollPane_7);
		
		tableAdminChecklistCoreData = new JTable();
		tableAdminChecklistCoreData.setBackground(Color.WHITE);
		tableAdminChecklistCoreData.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPane_7.setViewportView(tableAdminChecklistCoreData);
		
		JPanel panelAdminChecklistSelection = new JPanel();
		panelAdminChecklistSelection.setLayout(null);
		panelAdminChecklistSelection.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Checkliste", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panelAdminChecklistSelection.setBounds(10, 347, 741, 324);
		panelAdminChecklisten.add(panelAdminChecklistSelection);
		
		JLabel label_14 = new JLabel("Zoo\u00F6kologische Region");
		label_14.setBounds(14, 16, 132, 20);
		panelAdminChecklistSelection.add(label_14);
		
		JLabel label_15 = new JLabel("Land");
		label_15.setBounds(155, 16, 132, 20);
		panelAdminChecklistSelection.add(label_15);
		
		JLabel label_16 = new JLabel("Lokation");
		label_16.setBounds(297, 16, 131, 20);
		panelAdminChecklistSelection.add(label_16);
		
		JComboBox<String> cmbAdminChecklisteL1 = new JComboBox<String>();
		cmbAdminChecklisteL1.setBounds(10, 36, 136, 20);
		panelAdminChecklistSelection.add(cmbAdminChecklisteL1);
		
		JComboBox<String> cmbAdminChecklisteL2 = new JComboBox<String>();
		cmbAdminChecklisteL2.setBounds(151, 36, 136, 20);
		panelAdminChecklistSelection.add(cmbAdminChecklisteL2);
		
		JComboBox<String> cmbAdminChecklisteL3 = new JComboBox<String>();
		cmbAdminChecklisteL3.setBounds(292, 36, 136, 20);
		panelAdminChecklistSelection.add(cmbAdminChecklisteL3);
		
		JScrollPane scrollPane_8 = new JScrollPane();
		scrollPane_8.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane_8.setBounds(10, 67, 721, 246);
		panelAdminChecklistSelection.add(scrollPane_8);
		
		tableAdminChecklistSelection = new JTable();
		tableAdminChecklistSelection.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tableAdminChecklistSelection.setBackground(Color.WHITE);
		scrollPane_8.setViewportView(tableAdminChecklistSelection);
		
		JButton btnAdminChecklistRemove = new JButton("Auswahl entfernen");
		btnAdminChecklistRemove.setBounds(551, 35, 180, 23);
		panelAdminChecklistSelection.add(btnAdminChecklistRemove);
		
		JPanel panelAdminBenutzer = new JPanel();
		panelAdminCards.add(panelAdminBenutzer, "panel_admin_benutzer");
		panelAdminBenutzer.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Benutzer", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel.setBounds(10, 11, 741, 660);
		panelAdminBenutzer.add(panel);
		
		JScrollPane scrollPane_9 = new JScrollPane();
		scrollPane_9.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane_9.setBounds(10, 59, 721, 590);
		panel.add(scrollPane_9);
		
		tableAdminUser = new JTable();
		tableAdminUser.setBackground(Color.WHITE);
		scrollPane_9.setColumnHeaderView(tableAdminUser);
		
		JPanel panelAdminManStatement = new JPanel();
		panelAdminCards.add(panelAdminManStatement, "panel_admin_manstatement");
		panelAdminManStatement.setLayout(null);
		
		JPanel panelManStatementsFrame = new JPanel();
		panelManStatementsFrame.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Manuelle Anfragen senden", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panelManStatementsFrame.setBounds(10, 11, 1074, 660);
		panelAdminManStatement.add(panelManStatementsFrame);
		panelManStatementsFrame.setLayout(null);
		
		JScrollPane scrollPane_4 = new JScrollPane();
		scrollPane_4.setBounds(10, 208, 1054, 441);
		panelManStatementsFrame.add(scrollPane_4);
		scrollPane_4.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPane_4.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		
		tableManQuery = new JTable();
		tableManQuery.setBackground(Color.WHITE);
		scrollPane_4.setViewportView(tableManQuery);
		
		JScrollPane scrollPane_10 = new JScrollPane();
		scrollPane_10.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane_10.setBounds(217, 39, 464, 158);
		panelManStatementsFrame.add(scrollPane_10);
		
		final JTextPane textPane = new JTextPane();
		textPane.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				textPane.setBackground(Color.WHITE);
			}
		});
		textPane.setBackground(Color.WHITE);
		scrollPane_10.setViewportView(textPane);
		textPane.setText("SELECT * FROM Birdwatcher");
		textPane.setFont(new Font("Courier New", Font.PLAIN, 12));
		
		JButton btnSendManQuery = new JButton("Senden");
		btnSendManQuery.setBounds(691, 41, 89, 156);
		panelManStatementsFrame.add(btnSendManQuery);
		btnSendManQuery.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ResultSet resultSet;
					// TODO bereiningen
//					String query = "SELECT * FROM BIRDWATCHER WHERE lower(Benutzername) LIKE lower(demo)";
//					
//					PreparedStatement ps = Application.getInstance().database().prepareStatement(query);
//					ps.setString(1, textPane.getText());
//					resultSet = ps.executeQuery();
				try {
					if (textPane.getText().toLowerCase().contains("select")) {
						System.out.println("textPane Query:");
						System.out.println(textPane.getText() + "\n");
						resultSet = Application.getInstance().database().sendQuery(textPane.getText());
						
						DefaultTableModel resultTableModel = Application.getInstance().database().getTableModel(resultSet);
						resultSet.getStatement().close();
						tableManQuery.setModel(resultTableModel);
						
					} else {
						System.out.println("textPane Update:");
						System.out.println(textPane.getText() + "\n");
						Application.getInstance().database().sendUpdate(textPane.getText());
						
					}
					textPane.setBackground(new Color(152, 251, 152));
				} catch (SQLException e) {
					e.printStackTrace();
					ConstantElems.showMsgBox(e);
					textPane.setBackground(Color.PINK);
				} catch (Exception e) {
					e.printStackTrace();
					ConstantElems.showMsgBox(e);
					textPane.setBackground(Color.PINK);
				}
			}
		});
		btnSendManQuery.setFocusable(false);
		
		JLabel lblOhneAbschlieendesSemikolon = new JLabel("Nur ein Statement pro Anfrage und ohne abschlie\u00DFendes Semikolon!");
		lblOhneAbschlieendesSemikolon.setForeground(Color.BLACK);
		lblOhneAbschlieendesSemikolon.setFont(new Font("Tahoma", Font.BOLD, 10));
		lblOhneAbschlieendesSemikolon.setBounds(218, 16, 463, 14);
		panelManStatementsFrame.add(lblOhneAbschlieendesSemikolon);
		
		/*
		 * AFTER-INITIALIZATION AREA
		 */
		
		applyRolePermissions();
		greetActiveUser();
		
		JLabel label_19 = new JLabel("<-- egal");
		label_19.setBounds(620, 31, 46, 14);
		panelCoreDataTable.add(label_19);
		
		
	}
	
	private void addLocation() {
		String l1, l2, l3;
		
		l1 = trimText(txtAddRegion); 
		l2 = trimText(txtAddLand);
		l3 = trimText(txtAddLokation);
		
		isLocationInputValid(l1, l2, l3);
		
		MainWindowLogic.addLocation(l1,l2,l3);
	}
	
	private void getLocations() {
		try {
			tableAdminLokationen.setModel(MainWindowLogic.getLocations());
		} catch (Exception e) {
			e.printStackTrace();
			ConstantElems.showMsgBox(e, "Ortsliste konnte nicht geladen werden.");
		}
	}
	
	private void getCoreData() {
		getCoreData("","");
	}
	
	private void getCoreData(String filter, String spec) {
		try {
			tableAdminCoreData.setModel(MainWindowLogic.getCoreData(filter, spec));
		} catch (Exception e) {
			e.printStackTrace();
			ConstantElems.showMsgBox(e, "Stammdaten konnten nicht geladen werden.");
		}
	}
	
	public String trimText(JTextField textField) {
		textField.setText(textField.getText().trim());
		return textField.getText();
	}
	
	public String trimText(String text) {
		return text.trim();
	}
	
	private boolean isLocationInputValid(String l1, String l2, String l3) {
		return !l1.isEmpty() && validLocationName(l1) && validLocationName(l2) && validLocationName(l3);
	}
	
	private boolean validLocationName(String string) {
		return matches(string);
	}
	
	private boolean matches(String string) {
		return matches(string, "[a-zA-Z- üöäÜÖÄ]*");
	}
	
	private boolean matches(String string, String regEx) {
		return string.matches(regEx);
	}

	//Hilfsfunktion um Filerdaten zu holen
	private void filterSelection(){
		filter = txtFilterObservation.getText();
		ticks = chkFilterTicks.isSelected();
		lifer = chkFilterLifer.isSelected();
		tblBeobachtungsliste.setModel(MainWindowLogic.showLiferTicks(level_1, level_2, level_3, filter, ticks, lifer));
	}
	
	public void addObservation() {
		// holt Vogel ID aus JTable
		String birdId = (String)tblStammdatenBeob.getValueAt(tblStammdatenBeob.getSelectedRow(), 0);
		System.out.println("676 MerlinMainWindow : "+ birdId);
	
		// Datum- und Zeitformat (auf Klassenebene verschoben, da allg. gültig)
//		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
//		SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
		
		System.out.println("682 MerlinMainWindow : " + dateFormat);
		System.out.println("683 MerlinMainWindow : " + timeFormat);
		level_1 = cmbRegionAdd.getSelectedItem().toString();
		level_2 = cmbLandAdd.getSelectedItem().toString();	
		level_3 = cmbGebietAdd.getSelectedItem().toString();
		formatVon = dateFormat.format(datumVom.getDate()) + " " + timeFormat.format((Date)uhrzeitVom.getValue());
		
		if (!datumBis.isEnabled()){
			formatBis = null;
		} else {
			// DatumBis und UhrzeitBis konkatiniert als String
			formatBis = dateFormat.format(datumBis.getDate()) + " " + timeFormat.format((Date)uhrzeitBis.getValue());
			System.out.println("694 MerlinMainWindow : " + datumBis.getDate().toString());
			System.out.println("695 MerlinMainWindow : " + ((Date)uhrzeitBis.getValue()).toString());
			System.out.println("696 MerlinMainWindow : " + formatVon.toString());
			System.out.println("697 MerlinMainWindow : " + formatBis.toString());
		}
		
		notice = txtComment.getText();
		System.out.println("702 MerlinMainWindow : " + notice);

		MainWindowLogic.addObservation(birdId, level_1, level_2, level_3,  formatVon, formatBis, notice);
		
		
		/* Die Tabelle Beobachtet aus der Datenbank in die Gui laden.
		 * ein update muss durchgeführt werden.
		 * */
		tblBeobachtungsliste.setModel(MainWindowLogic.getDataObservation());
	}
	
	// Abhängig vom Attribut 'Role' des aktiven Users, Felder (in)aktiv schalten oder aus-/einblenden
	public void applyRolePermissions() {
		String role = BirdwatcherRepository.getActiveUser().role();
		
		// Einrichten, welche Funktionen freigeschaltet bzw. deaktiviert werden sollen
		if (userIsAdmin(role)) {
			applyRolePermissions(false, true);
			initAdminPanel();
		} else if (userIsContentAdmin(role)) {
			applyRolePermissions(false, true);
			initAdminPanel();
		} else if (userIsBirdwatcher(role)) {
			applyRolePermissions(true, false);
			initBirdwatcherPanel();
		}
	}
	
	private void initAdminPanel() {
		getCoreData();
		tglbtnVerwaltung.doClick();
		tglbtnAdminStammdaten.doClick();
	}
	
	private void initBirdwatcherPanel() {
		cmbRegionAdd.setModel(MainWindowLogic.getLevel1Data());
		tblStammdatenBeob.setModel(MainWindowLogic.selectLocation(level_1, level_2, level_3));
		tglbtnBeobachtungsliste.doClick();
	}
	
	public void greetActiveUser() {
		String firstname = BirdwatcherRepository.getActiveUser().firstname();
		lblWillkommenstext.setText("Herzlich Willkommen, " + firstname);
	}
	
	private void applyRolePermissions(boolean beob, boolean verwaltung) {
		tglbtnBeobachtungsliste.setEnabled(beob);
		tglbtnVerwaltung.setEnabled(verwaltung);
	}
	
	public boolean userIsAdmin(String role) {
		
		return (role.toLowerCase().equals("r01"))?(true):(false);
	}
	
	public boolean userIsContentAdmin(String role) {
		return (role.toLowerCase().equals("r02"))?(true):(false);
	}
	
	public boolean userIsBirdwatcher(String role) {
		return (role.toLowerCase().equals("r03"))?(true):(false);
	}
	
	public void hideFirstColumn(JTable table) {
		table.getColumnModel().getColumn(0).setWidth(0);
		table.getColumnModel().getColumn(0).setMaxWidth(0);
		table.getColumnModel().getColumn(0).setMinWidth(0);
		table.getColumnModel().getColumn(0).setPreferredWidth(0);
		table.getColumnModel().getColumn(0).setResizable(false);
	}	
	
	public DefaultTableModel getTableModel(ResultSet resultSet) throws SQLException {
		DefaultTableModel dtm = new DefaultTableModel();
		ResultSetMetaData meta = resultSet.getMetaData();
        int numberOfColumns = meta.getColumnCount();
        while (resultSet.next())
        {
            Object [] rowData = new Object[numberOfColumns];
            for (int i = 0; i < rowData.length; ++i)
            {
                rowData[i] = resultSet.getObject(i+1);
            }
            dtm.addRow(rowData);
        }
        return dtm;		
	}
	
	public Vector<Object> getColumnNames(ResultSet resultSet) throws SQLException {
		ResultSetMetaData metaData = resultSet.getMetaData();
		int columns = metaData.getColumnCount();
		Vector<Object> columnNames = new Vector<Object>();
                
		// Spaltennamen ermitteln
		for (int i = 1; i <= columns; i++) {
			String colname = metaData.getColumnName(i);
			columnNames.addElement( colname );
		}
		
		return columnNames;
	}


	public Vector<Vector<Object>> getResultTable(ResultSet resultSet) throws SQLException {
		ResultSetMetaData metaData = resultSet.getMetaData();
		int columns = metaData.getColumnCount();
		Vector<Object> columnNames = new Vector<Object>();
		Vector<Vector<Object>> data = new Vector<Vector<Object>>();
                
		// Spaltennamen ermitteln
		for (int i = 1; i <= columns; i++) {
			String colname = metaData.getColumnName(i);
			columnNames.addElement( colname );
		}
		// Zeileninhalt ermitteln
		while (resultSet.next()) {
			Vector<Object> row = new Vector<Object>(columns);
			for (int i = 1; i <= columns; i++) {
				row.addElement( resultSet.getObject(i) );
			}
			
			data.addElement( row );
		}
		return data;
	}
	
	public TableModel resultSetToTableModel(ResultSet rs) {
		try {
			ResultSetMetaData metaData = rs.getMetaData();
			/*
			
			 * 
			 */
			System.out.println("beginne verarbeitung im TableModel");
			int numberOfColumns = metaData.getColumnCount();
			Vector<String> columnNames = new Vector<String>();

			// Get the column names
			for (int column = 0; column < numberOfColumns; column++) {
				columnNames.addElement(metaData.getColumnLabel(column + 1));
			}

			// Get all rows.
			Vector<Vector<Object>> rows = new Vector<Vector<Object>>();

			while (rs.next()) {
				Vector<Object> newRow = new Vector<Object>();

				for (int i = 1; i <= numberOfColumns; i++) {
					newRow.addElement(rs.getObject(i));
				}

				rows.addElement(newRow);
			}

			System.out.println("verarbeitung abgeschlossen");
			return new DefaultTableModel(rows, columnNames);
		} catch (Exception e) {
			e.printStackTrace();

			return null;
		}
	}
	
	private void toggleCardButtons(boolean beob, boolean check, boolean admin) {
		 tglbtnBeobachtungsliste.setSelected(beob);
	     tglbtnCheckliste.setSelected(check);
	     tglbtnVerwaltung.setSelected(admin);
	}
	
	private void toggleAdminCardButtons(boolean stamm, boolean lok, boolean check, boolean user, boolean manstate) {
		tglbtnAdminStammdaten.setSelected(stamm);
		tglbtnAdminOrte.setSelected(lok);
		tglbtnAdminChecklisten.setSelected(check);
		tglbtnBenutzer.setSelected(user);
		tglbtnAdminManQuery.setSelected(manstate);

	}
	
//	public void dlgYesNo(Frame frame, String message, String headline) {
//		((JFrame) frame).setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
//		
//		int diagAnswer = JOptionPane.showConfirmDialog(frame, 
//				message, headline, 
//                JOptionPane.YES_NO_OPTION); 
//        
//        if (diagAnswer == JOptionPane.YES_OPTION) {
//        	
//        }
//	}
	public int dlgYesNo(String message, String headline) {
		int diagAnswer = JOptionPane.showConfirmDialog(null, 
				message, headline, 
                JOptionPane.YES_NO_OPTION); 
        
        return diagAnswer;
	}
	
	public void dlgDoSelectionFirst() {
		ConstantElems.showMsgBox("Wählen Sie zuerst einen Eintrag aus!", "Hinweis");
	}

	public void debugPrint(String message) {
		// TODO nachricht zu debug fenster hinzufügen können
	}
}
