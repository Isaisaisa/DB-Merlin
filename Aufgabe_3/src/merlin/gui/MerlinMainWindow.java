package merlin.gui;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
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
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import merlin.base.Application;
import merlin.data.BirdwatcherRepository;
import merlin.logic.impl.MainWindowLogic;
import merlin.utils.ConstantElems;

import org.eclipse.wb.swing.FocusTraversalOnArray;

import com.toedter.calendar.JDateChooser;


public class MerlinMainWindow {

	private JFrame frmMerlinMain;
	private JTable tblStammdatenBeob;
	private JPanel panelMain;
	private JPanel panelAdminCards;
	private JTable tblBeobachtungsliste;
	private JTable tableManQuery;
	private static JComboBox<String> cmbRegionAdd;
	private static JComboBox<String> cmbLandAdd;
	private static JComboBox<String> cmbGebietAdd;
	private String level_1;
	private String level_2;
	private String level_3;
	private String level_1_admin;
	private String level_2_admin; 
	private String level_3_admin;
	private JSpinner uhrzeitVom;
	private JSpinner uhrzeitBis;
	private JDateChooser datumVom;
	private JDateChooser datumBis;
	private SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
	private SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
	private String formatVon;
	private String formatBis;
	private String notice;
	private boolean isAdminChecklistenCoreDataInitialized = false;

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
	private JTextField txtAdminAddRegion;
	private JTextField txtAdminAddLand;
	private JTextField txtAdminAddLokation;
	private JTextField txtAdminEditRegion;
	private JTextField txtAdminEditLand;
	private JTextField txtAdminEditLokation;
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
	private JTextField txtAdminAddBirdSpecType;
	private JTextField txtAdminEditBirdSpecType;
	private JButton btnAdminEditBird;
	private JButton btnAdminCoreDataFilter;
	private JButton btnAdminAddBird;
	private JComboBox<String> cmbAdminChecklistCoreDataSpecType;
	private JComboBox<String> cmbAdminChecklisteL1;
	private JComboBox<String> cmbAdminChecklisteL2;
	private JComboBox<String> cmbAdminChecklisteL3;
	private JButton btnAdminChecklistCoreDataFilter;
	private JButton btnAdminChecklistAdd;
	private JButton btnAdminChecklistRemove;
	private JButton btnAdminEditLocation;
	private JCheckBox chkNichtGesichtete;
	

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
//				 CardLayout cl = (CardLayout)(panelMain.getLayout());
//			     cl.show(panelMain, "panel_beobachtungsliste");
			     
			     toggleCardButtons(true, false);
			     initBirdwatcherPanel();
			}
		});
		tglbtnBeobachtungsliste.setBounds(10, 9, 160, 25);
		panelUser.add(tglbtnBeobachtungsliste);
		
		
		
		tglbtnCheckliste.setFocusable(false);
		tglbtnCheckliste.setBounds(350, 9, 160, 25);
		panelUser.add(tglbtnCheckliste);
		
		
		tglbtnVerwaltung.setFocusable(false);
		tglbtnVerwaltung.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				CardLayout cl = (CardLayout)(panelMain.getLayout());
			    cl.show(panelMain, "panel_verwaltung");
			    
			    toggleCardButtons(false, true);
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
		
		txtFilterCoreData = new JTextField();
		txtFilterCoreData.getDocument().addDocumentListener(new DocumentListener() {
			
			public void removeUpdate(DocumentEvent arg0) {
				selectLocationFilterOnly();
			}
			
			public void insertUpdate(DocumentEvent arg0) {
				selectLocationFilterOnly();
			}
			
			public void changedUpdate(DocumentEvent arg0) {
			}
		});
		txtFilterCoreData.setBounds(82, 28, 230, 20);
		panelCoreDataTable.add(txtFilterCoreData);
		txtFilterCoreData.setColumns(10);
		
		JLabel lblVolltextfilter = new JLabel("Volltextfilter:");
		lblVolltextfilter.setBounds(10, 28, 62, 20);
		panelCoreDataTable.add(lblVolltextfilter);
		
		JButton btnFilterCoreData = new JButton("Filtern");
		btnFilterCoreData.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				selectLocationFilterOnly();
			}
		});
		btnFilterCoreData.setBounds(322, 27, 89, 23);
		panelCoreDataTable.add(btnFilterCoreData);
		
		chkNichtGesichtete = new JCheckBox("nur nicht beobachtete Vogelarten");
		chkNichtGesichtete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// TODO
				selectLocation();
			}
		});
		chkNichtGesichtete.setBounds(430, 28, 193, 20);
		panelCoreDataTable.add(chkNichtGesichtete);
		
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
		btnDeleteObservation.setBounds(594, 27, 226, 23);
		panelObservationTable.add(btnDeleteObservation);
		btnDeleteObservation.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				if (dlgYesNo("Wollen Sie die gewählte Beobachtung wirklich löschen?", "Beobachtung löschen") == JOptionPane.NO_OPTION) {
					return;
				}
				
				//Beo-ID
				String beoId = (String)tblBeobachtungsliste.getValueAt(tblBeobachtungsliste.getSelectedRow(), 0);
			
				MainWindowLogic.deleteDataObservation(beoId);
				filterSelection();
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
				filterSelection();
				
			}
		});
		chkFilterLifer.setToolTipText("Akivieren ");
		chkFilterLifer.setBounds(460, 27, 55, 23);
		panelObservationTable.add(chkFilterLifer);
				
		chkFilterTicks = new JCheckBox("Ticks");
		chkFilterTicks.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				filterSelection();
				
			}
		});
		chkFilterTicks.setToolTipText("Akivieren ");
		chkFilterTicks.setBounds(517, 27, 55, 23);
		panelObservationTable.add(chkFilterTicks);
		
		JLabel lblNur = new JLabel("Nur");
		lblNur.setBounds(438, 28, 22, 20);
		panelObservationTable.add(lblNur);
		
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
				selectLocation();
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
				selectLocation();
			}
		});
		cmbLandAdd.setBounds(85, 46, 152, 20);
		panelAddObLocation.add(cmbLandAdd);
		
		
		
		cmbGebietAdd = new JComboBox<String>();
		cmbGebietAdd.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				level_3 = cmbGebietAdd.getSelectedItem().toString();
				selectLocation();
				// TODO
//				tblStammdatenBeob.setModel(MainWindowLogic.selectLocation(level_1, level_2, level_3, txtFilterCoreData.getText()));
//				hideFirstColumn(tblStammdatenBeob);
//				filterSelection();
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
				
				if (!isAdminChecklistenCoreDataInitialized) {
					tableAdminChecklistCoreData.setModel(getCoreData("", "")); //TODO initialfilter - setzbar, falls startperformance verbessert werden soll
					hideFirstColumn(tableAdminChecklistCoreData);
					isAdminChecklistenCoreDataInitialized = true;
				}
				
//				cmbAdminChecklisteL1.setModel(MainWindowLogic.getLevel1Data_Checklist());
			}
		});
		tglbtnAdminChecklisten.setFont(new Font("Tahoma", Font.PLAIN, 15));
		tglbtnAdminChecklisten.setFocusable(false);
		tglbtnAdminChecklisten.setBounds(350, 9, 160, 25);
		panelAdminButtons.add(tglbtnAdminChecklisten);
		
		tglbtnBenutzer = new JToggleButton("Benutzer");
		tglbtnBenutzer.setEnabled(false);
		tglbtnBenutzer.setVisible(false);
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
		tglbtnBenutzer.setBounds(690, 9, 160, 25);
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
		tglbtnAdminManQuery.setBounds(520, 9, 160, 25);
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
		panelAdminCoreDataTable.setBounds(10, 11, 741, 660);
		panelAdminStammdaten.add(panelAdminCoreDataTable);
		
		JScrollPane scrollPane_3 = new JScrollPane();
		scrollPane_3.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane_3.setBounds(10, 59, 721, 590);
		panelAdminCoreDataTable.add(scrollPane_3);
		
		tableAdminCoreData = new JTable();
		tableAdminCoreData.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if (tableAdminCoreData.getSelectedRow() > -1) {
					enableBirdEditOnSelection(true);
					txtAdminEditBirdNameLat.setText((String)tableAdminCoreData.getValueAt(tableAdminCoreData.getSelectedRow(), 1));
					txtAdminEditBirdNameDe.setText((String)tableAdminCoreData.getValueAt(tableAdminCoreData.getSelectedRow(), 2));
					txtAdminEditBirdNameEng.setText((String)tableAdminCoreData.getValueAt(tableAdminCoreData.getSelectedRow(), 3));
				} else {
					enableBirdEditOnSelection(false);
				}
			}
			@Override
			public void mouseReleased(MouseEvent arg0) {
				if (tableAdminCoreData.getSelectedRow() > -1) {
					enableBirdEditOnSelection(true);
					txtAdminEditBirdNameLat.setText((String)tableAdminCoreData.getValueAt(tableAdminCoreData.getSelectedRow(), 1));
					txtAdminEditBirdNameDe.setText((String)tableAdminCoreData.getValueAt(tableAdminCoreData.getSelectedRow(), 2));
					txtAdminEditBirdNameEng.setText((String)tableAdminCoreData.getValueAt(tableAdminCoreData.getSelectedRow(), 3));
				} else {
					enableBirdEditOnSelection(false);
				}
			}
		});
		scrollPane_3.setViewportView(tableAdminCoreData);
		tableAdminCoreData.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tableAdminCoreData.setBackground(Color.WHITE);
		
		txtAdminCoreDataFilter = new JTextField();
		txtAdminCoreDataFilter.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent arg0) {
				if (arg0.getKeyCode() == KeyEvent.VK_ENTER) btnAdminCoreDataFilter.doClick();
			}
		});
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
				btnAdminCoreDataFilter.doClick();
			}
		});
		cmbAdminCoreDataSpecType.setModel(new DefaultComboBoxModel<String>(new String[] {"Alle", "Oberarten", "Unterarten"}));
		cmbAdminCoreDataSpecType.setBounds(611, 28, 120, 20);
		panelAdminCoreDataTable.add(cmbAdminCoreDataSpecType);
		
		JLabel lblAdminCoreDataArtentyp = new JLabel("Artentyp:");
		lblAdminCoreDataArtentyp.setBounds(554, 28, 47, 20);
		panelAdminCoreDataTable.add(lblAdminCoreDataArtentyp);
		
		btnAdminCoreDataFilter = new JButton("Filtern");
		btnAdminCoreDataFilter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.out.println();
//				getCoreData(txtAdminCoreDataFilter.getText(), cmbAdminCoreDataSpecType.getSelectedItem().toString());
				tableAdminCoreData.setModel(getCoreData(txtAdminCoreDataFilter.getText(), cmbAdminCoreDataSpecType.getSelectedItem().toString()));
				hideFirstColumn(tableAdminCoreData);
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
		txtAdminAddBirdNameLat.getDocument().addDocumentListener(new DocumentListener() {
			
			public void removeUpdate(DocumentEvent arg0) {
				checkLatBirdName(txtAdminAddBirdNameLat, txtAdminAddBirdSpecType);
			}
			
			public void insertUpdate(DocumentEvent arg0) {
				checkLatBirdName(txtAdminAddBirdNameLat, txtAdminAddBirdSpecType);
			}

			public void changedUpdate(DocumentEvent arg0) {
			}
		});
		txtAdminAddBirdNameLat.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent arg0) {
				txtAdminAddBirdNameLat.selectAll();
			}
			
			@Override
			public void focusLost(FocusEvent arg0) {
				trimText(txtAdminAddBirdNameLat);
			}
		});
		txtAdminAddBirdNameLat.setBounds(117, 34, 200, 20);
		panelAdminAddBird.add(txtAdminAddBirdNameLat);
		txtAdminAddBirdNameLat.setColumns(10);
		
		JLabel lblDeutscherName = new JLabel("Deutscher Name:");
		lblDeutscherName.setBounds(10, 65, 91, 20);
		panelAdminAddBird.add(lblDeutscherName);
		
		txtAdminAddBirdNameDe = new JTextField();
		txtAdminAddBirdNameDe.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent arg0) {
				txtAdminAddBirdNameDe.selectAll();
			}
			
			@Override
			public void focusLost(FocusEvent arg0) {
				trimText(txtAdminAddBirdNameDe);
			}
		});
		txtAdminAddBirdNameDe.setColumns(10);
		txtAdminAddBirdNameDe.setBounds(117, 65, 200, 20);
		panelAdminAddBird.add(txtAdminAddBirdNameDe);
		
		JLabel lblEnglischerName = new JLabel("Englischer Name:");
		lblEnglischerName.setBounds(10, 96, 91, 20);
		panelAdminAddBird.add(lblEnglischerName);
		
		txtAdminAddBirdNameEng = new JTextField();
		txtAdminAddBirdNameEng.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent arg0) {
				txtAdminAddBirdNameEng.selectAll();
			}
			
			@Override
			public void focusLost(FocusEvent arg0) {
				trimText(txtAdminAddBirdNameEng);
			}
		});
		txtAdminAddBirdNameEng.setColumns(10);
		txtAdminAddBirdNameEng.setBounds(117, 96, 200, 20);
		panelAdminAddBird.add(txtAdminAddBirdNameEng);
		
		JLabel label_1 = new JLabel("Artentyp:");
		label_1.setBounds(10, 127, 47, 20);
		panelAdminAddBird.add(label_1);
		
		btnAdminAddBird = new JButton("Hinzuf\u00FCgen");
		btnAdminAddBird.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (checkLatBirdName(txtAdminAddBirdNameLat, txtAdminAddBirdSpecType)) {
					addBird();
				};
			}
		});
		btnAdminAddBird.setBounds(117, 166, 200, 23);
		panelAdminAddBird.add(btnAdminAddBird);
		
		JLabel lblpflichtangabe = new JLabel("*Pflichtangabe");
		lblpflichtangabe.setForeground(new Color(0, 0, 128));
		lblpflichtangabe.setBounds(10, 197, 71, 14);
		panelAdminAddBird.add(lblpflichtangabe);
		
		txtAdminAddBirdSpecType = new JTextField();
		txtAdminAddBirdSpecType.setEditable(false);
		txtAdminAddBirdSpecType.setBounds(117, 127, 200, 20);
		panelAdminAddBird.add(txtAdminAddBirdSpecType);
		txtAdminAddBirdSpecType.setColumns(10);
		
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
		txtAdminEditBirdNameLat.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent arg0) {
				trimText(txtAdminEditBirdNameLat);
			}
		});
		txtAdminEditBirdNameLat.setEnabled(false);
		txtAdminEditBirdNameLat.getDocument().addDocumentListener(new DocumentListener() {
			
			public void removeUpdate(DocumentEvent arg0) {
				checkLatBirdName(txtAdminEditBirdNameLat, txtAdminEditBirdSpecType);
			}
			
			public void insertUpdate(DocumentEvent arg0) {
				checkLatBirdName(txtAdminEditBirdNameLat, txtAdminEditBirdSpecType);
			}

			public void changedUpdate(DocumentEvent arg0) {
			}
		});
		txtAdminEditBirdNameLat.setColumns(10);
		txtAdminEditBirdNameLat.setBounds(117, 34, 200, 20);
		panelAdminEditBird.add(txtAdminEditBirdNameLat);
		
		JLabel label_3 = new JLabel("Deutscher Name:");
		label_3.setBounds(10, 65, 91, 20);
		panelAdminEditBird.add(label_3);
		
		txtAdminEditBirdNameDe = new JTextField();
		txtAdminEditBirdNameDe.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent arg0) {
				trimText(txtAdminEditBirdNameDe);
			}
		});
		txtAdminEditBirdNameDe.setEnabled(false);
		txtAdminEditBirdNameDe.setColumns(10);
		txtAdminEditBirdNameDe.setBounds(117, 65, 200, 20);
		panelAdminEditBird.add(txtAdminEditBirdNameDe);
		
		JLabel label_4 = new JLabel("Englischer Name:");
		label_4.setBounds(10, 96, 91, 20);
		panelAdminEditBird.add(label_4);
		
		txtAdminEditBirdNameEng = new JTextField();
		txtAdminEditBirdNameEng.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent arg0) {
				trimText(txtAdminEditBirdNameEng);
			}
		});
		txtAdminEditBirdNameEng.setEnabled(false);
		txtAdminEditBirdNameEng.setColumns(10);
		txtAdminEditBirdNameEng.setBounds(117, 96, 200, 20);
		panelAdminEditBird.add(txtAdminEditBirdNameEng);
		
		JLabel label_5 = new JLabel("Artentyp:");
		label_5.setBounds(10, 127, 47, 20);
		panelAdminEditBird.add(label_5);
		
		btnAdminEditBird = new JButton("\u00DCbernehmen");
		btnAdminEditBird.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String va_id = (String)tableAdminCoreData.getValueAt(tableAdminCoreData.getSelectedRow(), 0);
//				String name_lat = txtAdminEditBirdNameLat.getText();
//				ConstantElems.showMsgBox("muss noch implementiert werden", "BirdId = " + birdId);
				updateBird(va_id);
			}
		});
		btnAdminEditBird.setEnabled(false);
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
		
		txtAdminEditBirdSpecType = new JTextField();
		txtAdminEditBirdSpecType.setEditable(false);
		txtAdminEditBirdSpecType.setColumns(10);
		txtAdminEditBirdSpecType.setBounds(117, 127, 200, 20);
		panelAdminEditBird.add(txtAdminEditBirdSpecType);
		
		JPanel panelAdminCoreDataConflicts = new JPanel();
		panelAdminCoreDataConflicts.setVisible(false);
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
		tableAdminLokationen.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if (tableAdminLokationen.getSelectedRow() > -1) {
					enableLocationEditOnSelection(true);
					txtAdminEditRegion.setText((String)tableAdminLokationen.getValueAt(tableAdminLokationen.getSelectedRow(), 1));
					txtAdminEditLand.setText((String)tableAdminLokationen.getValueAt(tableAdminLokationen.getSelectedRow(), 2));
					txtAdminEditLokation.setText((String)tableAdminLokationen.getValueAt(tableAdminLokationen.getSelectedRow(), 3));
				} else {
					enableLocationEditOnSelection(false);
				}
			}
			@Override
			public void mouseReleased(MouseEvent arg0) {
				if (tableAdminLokationen.getSelectedRow() > -1) {
					enableLocationEditOnSelection(true);
					txtAdminEditRegion.setText((String)tableAdminLokationen.getValueAt(tableAdminLokationen.getSelectedRow(), 1));
					txtAdminEditLand.setText((String)tableAdminLokationen.getValueAt(tableAdminLokationen.getSelectedRow(), 2));
					txtAdminEditLokation.setText((String)tableAdminLokationen.getValueAt(tableAdminLokationen.getSelectedRow(), 3));
				} else {
					enableLocationEditOnSelection(false);
				}
			}
		});
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
		
		txtAdminAddRegion = new JTextField();
		txtAdminAddRegion.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent arg0) {
				trimText(txtAdminAddRegion);
			}
		});
		txtAdminAddRegion.getDocument().addDocumentListener(new DocumentListener() {
			
			public void removeUpdate(DocumentEvent arg0) {
				checkLocationName(txtAdminAddRegion);
			}
			
			public void insertUpdate(DocumentEvent arg0) {
				checkLocationName(txtAdminAddRegion);
			}

			public void changedUpdate(DocumentEvent arg0) {
			}
		});
		txtAdminAddRegion.setColumns(10);
		txtAdminAddRegion.setBounds(117, 34, 200, 20);
		panelAdminAddLokation.add(txtAdminAddRegion);
		
		JLabel lblLand_2 = new JLabel("Land:");
		lblLand_2.setBounds(10, 65, 91, 20);
		panelAdminAddLokation.add(lblLand_2);
		
		txtAdminAddLand = new JTextField();
		txtAdminAddLand.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent arg0) {
				trimText(txtAdminAddLand);
			}
		});
		txtAdminAddLand.getDocument().addDocumentListener(new DocumentListener() {
			
			public void removeUpdate(DocumentEvent arg0) {
				checkLocationName(txtAdminAddLand);
			}
			
			public void insertUpdate(DocumentEvent arg0) {
				checkLocationName(txtAdminAddLand);
			}

			public void changedUpdate(DocumentEvent arg0) {
			}
		});
		txtAdminAddLand.setColumns(10);
		txtAdminAddLand.setBounds(117, 65, 200, 20);
		panelAdminAddLokation.add(txtAdminAddLand);
		
		JLabel lblLokation_1 = new JLabel("Lokation:");
		lblLokation_1.setBounds(10, 96, 91, 20);
		panelAdminAddLokation.add(lblLokation_1);
		
		txtAdminAddLokation = new JTextField();
		txtAdminAddLokation.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent arg0) {
				trimText(txtAdminAddLokation);
			}
		});
		txtAdminAddLokation.getDocument().addDocumentListener(new DocumentListener() {
			
			public void removeUpdate(DocumentEvent arg0) {
				checkLocationName(txtAdminAddLokation);
			}
			
			public void insertUpdate(DocumentEvent arg0) {
				checkLocationName(txtAdminAddLokation);
			}

			public void changedUpdate(DocumentEvent arg0) {
			}
		});
		txtAdminAddLokation.setColumns(10);
		txtAdminAddLokation.setBounds(117, 96, 200, 20);
		panelAdminAddLokation.add(txtAdminAddLokation);
		
		JButton btnAdminAddLocation = new JButton("Hinzuf\u00FCgen");
		btnAdminAddLocation.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				addLocation();
				getLocations();
				cmbAdminChecklisteL1.setModel(MainWindowLogic.getLevel1Data_Checklist());
			}
		});
		btnAdminAddLocation.setBounds(117, 127, 200, 23);
		panelAdminAddLokation.add(btnAdminAddLocation);
		
		JLabel label_11 = new JLabel("*Pflichtangabe");
		label_11.setForeground(new Color(0, 0, 128));
		label_11.setBounds(10, 153, 71, 14);
		panelAdminAddLokation.add(label_11);
		
		JPanel panelAdminEditLokation = new JPanel();
		panelAdminEditLokation.setLayout(null);
		panelAdminEditLokation.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Ort bearbeiten (Tabellenauswahl)", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panelAdminEditLokation.setBounds(761, 346, 329, 325);
		panelAdminLokationen.add(panelAdminEditLokation);
		
		txtAdminEditRegion = new JTextField();
		txtAdminEditRegion.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent arg0) {
				trimText(txtAdminEditRegion);
			}
		});
		txtAdminEditRegion.getDocument().addDocumentListener(new DocumentListener() {
			
			public void removeUpdate(DocumentEvent arg0) {
				checkLocationName(txtAdminEditRegion);
			}
			
			public void insertUpdate(DocumentEvent arg0) {
				checkLocationName(txtAdminEditRegion);
			}

			public void changedUpdate(DocumentEvent arg0) {
			}
		});
		txtAdminEditRegion.setEnabled(false);
		txtAdminEditRegion.setColumns(10);
		txtAdminEditRegion.setBounds(117, 34, 200, 20);
		panelAdminEditLokation.add(txtAdminEditRegion);
		
		txtAdminEditLand = new JTextField();
		txtAdminEditLand.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent arg0) {
				trimText(txtAdminEditLand);
			}
		});
		txtAdminEditLand.getDocument().addDocumentListener(new DocumentListener() {
			
			public void removeUpdate(DocumentEvent arg0) {
				checkLocationName(txtAdminEditLand);
			}
			
			public void insertUpdate(DocumentEvent arg0) {
				checkLocationName(txtAdminEditLand);
			}

			public void changedUpdate(DocumentEvent arg0) {
			}
		});
		txtAdminEditLand.setEnabled(false);
		txtAdminEditLand.setColumns(10);
		txtAdminEditLand.setBounds(117, 65, 200, 20);
		panelAdminEditLokation.add(txtAdminEditLand);
		
		txtAdminEditLokation = new JTextField();
		txtAdminEditLokation.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent arg0) {
				trimText(txtAdminEditLokation);
			}
		});
		txtAdminEditLokation.getDocument().addDocumentListener(new DocumentListener() {
			
			public void removeUpdate(DocumentEvent arg0) {
				checkLocationName(txtAdminEditLokation);
			}
			
			public void insertUpdate(DocumentEvent arg0) {
				checkLocationName(txtAdminEditLokation);
			}

			public void changedUpdate(DocumentEvent arg0) {
			}
		});
		txtAdminEditLokation.setEnabled(false);
		txtAdminEditLokation.setColumns(10);
		txtAdminEditLokation.setBounds(117, 96, 200, 20);
		panelAdminEditLokation.add(txtAdminEditLokation);
		
		btnAdminEditLocation = new JButton("\u00DCbernehmen");
		btnAdminEditLocation.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String ort_id = (String) tableAdminLokationen.getValueAt(tableAdminLokationen.getSelectedRow(), 0);
				updateLocation(ort_id);
				getLocations();
				cmbAdminChecklisteL1.setModel(MainWindowLogic.getLevel1Data_Checklist());
			}
		});
		btnAdminEditLocation.setEnabled(false);
		btnAdminEditLocation.setBounds(117, 127, 200, 23);
		panelAdminEditLokation.add(btnAdminEditLocation);
		
		JButton button_2 = new JButton("L\u00F6schen");
		button_2.setVisible(false);
		button_2.setForeground(Color.BLACK);
		button_2.setBackground(Color.PINK);
		button_2.setBounds(117, 161, 200, 23);
		panelAdminEditLokation.add(button_2);
		
		JLabel label_12 = new JLabel("*Pflichtangabe");
		label_12.setForeground(new Color(0, 0, 128));
		label_12.setBounds(10, 161, 71, 14);
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
		txtAdminChecklistCoreDataFilter.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent arg0) {
//				btnAdminChecklistCoreDataFilter.doClick();
			}
		});
		txtAdminChecklistCoreDataFilter.setColumns(10);
		txtAdminChecklistCoreDataFilter.setBounds(82, 28, 155, 20);
		panelAdminChecklistCoreData.add(txtAdminChecklistCoreDataFilter);
		
		JLabel label_10 = new JLabel("Volltextfilter:");
		label_10.setBounds(10, 28, 62, 20);
		panelAdminChecklistCoreData.add(label_10);
		
		cmbAdminChecklistCoreDataSpecType = new JComboBox<String>();
		cmbAdminChecklistCoreDataSpecType.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				btnAdminChecklistCoreDataFilter.doClick();
			}
		});
		cmbAdminChecklistCoreDataSpecType.setModel(new DefaultComboBoxModel<String>(new String[] {"Alle", "Oberarten", "Unteraten"}));
		cmbAdminChecklistCoreDataSpecType.setBounds(408, 28, 120, 20);
		panelAdminChecklistCoreData.add(cmbAdminChecklistCoreDataSpecType);
		
		JLabel label_13 = new JLabel("Artentyp:");
		label_13.setBounds(350, 28, 47, 20);
		panelAdminChecklistCoreData.add(label_13);
		
		btnAdminChecklistCoreDataFilter = new JButton("Filtern");
		btnAdminChecklistCoreDataFilter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				tableAdminChecklistCoreData.setModel(getCoreData(txtAdminChecklistCoreDataFilter.getText(), cmbAdminChecklistCoreDataSpecType.getSelectedItem().toString()));
				hideFirstColumn(tableAdminChecklistCoreData);
			}
		});
		btnAdminChecklistCoreDataFilter.setBounds(247, 27, 89, 23);
		panelAdminChecklistCoreData.add(btnAdminChecklistCoreDataFilter);
		
		btnAdminChecklistAdd = new JButton("Auswahl hinzuf\u00FCgen");
		btnAdminChecklistAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String va_id = (String) tableAdminChecklistCoreData.getValueAt(tableAdminChecklistCoreData.getSelectedRow(), 0);
				try {
					MainWindowLogic.addChecklistEntry(va_id, level_1_admin, level_2_admin, level_3_admin);
					ConstantElems.showMsgBox("Vogel zur Checkliste hinzugefügt!", "Erfolg");
					tableAdminChecklistSelection.setModel(MainWindowLogic.selectLocation(level_1_admin, level_2_admin, level_3_admin, ""));
					hideFirstColumn(tableAdminChecklistSelection);
				} catch (Exception e) {
					e.printStackTrace();
					ConstantElems.showMsgBox(e, "Vogelart konnte nicht zur Checkliste hinzugefügt werden.");
				}
			}
		});
		btnAdminChecklistAdd.setEnabled(false);
		btnAdminChecklistAdd.setBounds(551, 27, 180, 23);
		panelAdminChecklistCoreData.add(btnAdminChecklistAdd);
		
		JScrollPane scrollPane_7 = new JScrollPane();
		scrollPane_7.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane_7.setBounds(10, 59, 721, 255);
		panelAdminChecklistCoreData.add(scrollPane_7);
		
		tableAdminChecklistCoreData = new JTable();
		tableAdminChecklistCoreData.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				btnAdminChecklistAdd.setEnabled(tableAdminChecklistCoreData.getSelectedRow() > -1);
				if (btnAdminChecklistAdd.isEnabled()) {
					if (arg0.getClickCount() == 2) {
						btnAdminChecklistAdd.doClick();
					}
				}
			}
			@Override
			public void mouseReleased(MouseEvent arg0) {
				btnAdminChecklistAdd.setEnabled(tableAdminChecklistCoreData.getSelectedRow() > -1);
			}
		});
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
		
		cmbAdminChecklisteL1 = new JComboBox<String>();
		cmbAdminChecklisteL1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				selectLevel1_AdminChecklist();
			}
		});
		cmbAdminChecklisteL1.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				selectLevel1_AdminChecklist();
//				level_1_admin = cmbAdminChecklisteL1.getSelectedItem().toString();
//				level_2_admin = "";
//				level_3_admin = "";
//				cmbAdminChecklisteL2.setModel(new DefaultComboBoxModel<String>(MainWindowLogic.loadLand(level_1_admin)));
//				cmbAdminChecklisteL3.setModel(new DefaultComboBoxModel<String>(MainWindowLogic.loadArea(level_1_admin, level_2_admin)));
//				tableAdminChecklistSelection.setModel(MainWindowLogic.selectLocation(level_1_admin, level_2_admin, level_3_admin));
			}
		});
		
		cmbAdminChecklisteL1.setBounds(10, 36, 136, 20);
		panelAdminChecklistSelection.add(cmbAdminChecklisteL1);
		
		cmbAdminChecklisteL2 = new JComboBox<String>();
		cmbAdminChecklisteL2.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				level_2_admin = cmbAdminChecklisteL2.getSelectedItem().toString();
				level_3_admin = "";
				cmbAdminChecklisteL3.setModel(new DefaultComboBoxModel<String>(MainWindowLogic.loadArea(level_1_admin, level_2_admin)));
				tableAdminChecklistSelection.setModel(MainWindowLogic.selectLocation(level_1_admin, level_2_admin, level_3_admin, ""));
				hideFirstColumn(tableAdminChecklistSelection);
			}
		});
		cmbAdminChecklisteL2.setBounds(151, 36, 136, 20);
		panelAdminChecklistSelection.add(cmbAdminChecklisteL2);
		
		cmbAdminChecklisteL3 = new JComboBox<String>();
		cmbAdminChecklisteL3.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				level_3_admin = cmbAdminChecklisteL3.getSelectedItem().toString();
				tableAdminChecklistSelection.setModel(MainWindowLogic.selectLocation(level_1_admin, level_2_admin, level_3_admin, ""));
				hideFirstColumn(tableAdminChecklistSelection);
			}
		});
		cmbAdminChecklisteL3.setBounds(292, 36, 136, 20);
		panelAdminChecklistSelection.add(cmbAdminChecklisteL3);
		
		JScrollPane scrollPane_8 = new JScrollPane();
		scrollPane_8.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane_8.setBounds(10, 67, 721, 246);
		panelAdminChecklistSelection.add(scrollPane_8);
		
		tableAdminChecklistSelection = new JTable();
		tableAdminChecklistSelection.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent arg0) {
				if (tableAdminChecklistSelection.getSelectedRow() > -1) {
					if (arg0.getKeyCode() == KeyEvent.VK_DELETE || arg0.getKeyCode() == KeyEvent.VK_BACK_SPACE) btnAdminChecklistRemove.doClick();
				}
			}
		});
		tableAdminChecklistSelection.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				btnAdminChecklistRemove.setEnabled(tableAdminChecklistSelection.getSelectedRow() > -1);
			}
			@Override
			public void mouseReleased(MouseEvent arg0) {
				btnAdminChecklistRemove.setEnabled(tableAdminChecklistSelection.getSelectedRow() > -1);
			}
		});
		tableAdminChecklistSelection.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tableAdminChecklistSelection.setBackground(Color.WHITE);
		scrollPane_8.setViewportView(tableAdminChecklistSelection);
		
		btnAdminChecklistRemove = new JButton("Auswahl entfernen");
		btnAdminChecklistRemove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (dlgYesNo("Wollen Sie den gewählten Vogel wirklich von der Checkliste entfernen?", "Eintrag löschen") == JOptionPane.NO_OPTION) {
					return;
				}

				String va_id = (String) tableAdminChecklistSelection.getValueAt(tableAdminChecklistSelection.getSelectedRow(), 0);
				try {
					MainWindowLogic.deleteChecklistEntry(va_id, level_1_admin, level_2_admin, level_3_admin);
					tableAdminChecklistSelection.setModel(MainWindowLogic.selectLocation(level_1_admin, level_2_admin, level_3_admin, ""));
					hideFirstColumn(tableAdminChecklistSelection);
					
				} catch (Exception e) {
					e.printStackTrace();
					ConstantElems.showMsgBox(e, "Vogel konnte nicht von der Checkliste gelöscht werden");
				}
				
			}
		});
		btnAdminChecklistRemove.setEnabled(false);
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
		 * AFTER-INIT AREA
		 */
		
		applyRolePermissions();
		greetActiveUser();
		
	}
	
	private boolean checkLatBirdName(JTextField txtName, JTextField txtSpec) {
		String birdName = txtName.getText().trim().replaceAll("\\s+", " ");
		int birdNameLength = birdName.split(" ").length;
		
		if (birdName.isEmpty()) {
			txtName.setBackground(Color.PINK);
			txtSpec.setText("-ungültiger lat. Name-");
			return false;
		} else {
			txtName.setBackground(Color.WHITE);
			if (birdNameLength == 2) {
				txtSpec.setText("Oberart");
				return true;
			} else if (birdNameLength == 3) {
				txtSpec.setText("Unterart");
				return true;
			} else {
				txtName.setBackground(Color.PINK);
				txtSpec.setText("-ungültiger lat. Name-");
				return false;
			}
		}
	}
	
	private void enableBirdEditOnSelection(boolean enabled) {
		txtAdminEditBirdNameLat.setEnabled(enabled);
		txtAdminEditBirdNameDe.setEnabled(enabled);
		txtAdminEditBirdNameEng.setEnabled(enabled);
		btnAdminEditBird.setEnabled(enabled);
	}
	
	private void enableLocationEditOnSelection(boolean enabled) {
		txtAdminEditRegion.setEnabled(enabled);
		txtAdminEditLand.setEnabled(enabled);
		txtAdminEditLokation.setEnabled(enabled);
		btnAdminEditLocation.setEnabled(enabled);
	}
	
	private void selectLevel1_AdminChecklist() {
		level_1_admin = cmbAdminChecklisteL1.getSelectedItem().toString();
		level_2_admin = "";
		level_3_admin = "";
		cmbAdminChecklisteL2.setModel(new DefaultComboBoxModel<String>(MainWindowLogic.loadLand(level_1_admin)));
		cmbAdminChecklisteL3.setModel(new DefaultComboBoxModel<String>(MainWindowLogic.loadArea(level_1_admin, level_2_admin)));
		tableAdminChecklistSelection.setModel(MainWindowLogic.selectLocation(level_1_admin, level_2_admin, level_3_admin, ""));
		hideFirstColumn(tableAdminChecklistSelection);
	}
	
	private void addLocation() {
		String l1, l2, l3;
		
		l1 = trimText(txtAdminAddRegion); 
		l2 = trimText(txtAdminAddLand);
		l3 = trimText(txtAdminAddLokation);
		
		if (isLocationInputValid(l1, l2, l3)) {
			MainWindowLogic.addLocation(l1,l2,l3);
		} else {
			ConstantElems.showMsgBox(new Exception("Ungültige Ortsangaben!"));
		}
	}
	
	private void updateLocation(String ort_id) {
		String l1, l2, l3;
		
		l1 = trimText(txtAdminEditRegion); 
		l2 = trimText(txtAdminEditLand);
		l3 = trimText(txtAdminEditLokation);
		
		if (isLocationInputValid(l1, l2, l3)) {
			MainWindowLogic.updateLocation(ort_id,l1,l2,l3);
		} else {
			ConstantElems.showMsgBox(new Exception("Ungültige Ortsangaben!"));
		}
	}
	
	private void getLocations() {
		try {
			tableAdminLokationen.setModel(MainWindowLogic.getLocations());
			hideFirstColumn(tableAdminLokationen);
		} catch (Exception e) {
			e.printStackTrace();
			ConstantElems.showMsgBox(e, "Ortsliste konnte nicht geladen werden.");
		}
	}
	
	private void selectLocation() {
		if (chkNichtGesichtete.isSelected()) {
			selectedChecklistView();
		} else {
			tblStammdatenBeob.setModel(MainWindowLogic.selectLocation(level_1, level_2, level_3, txtFilterCoreData.getText()));
			hideFirstColumn(tblStammdatenBeob);
			filterSelection();
		}
	}
	
	private void selectLocationFilterOnly() {
		if (chkNichtGesichtete.isSelected()) {
			try {
				tblStammdatenBeob.setModel(MainWindowLogic.selectedChecklistView(level_1, level_2, level_3, txtFilterCoreData.getText()));
			} catch (Exception e) {
				e.printStackTrace();
				ConstantElems.showMsgBox(e);
			}
			hideFirstColumn(tblStammdatenBeob);
		} else {
			tblStammdatenBeob.setModel(MainWindowLogic.selectLocation(level_1, level_2, level_3, txtFilterCoreData.getText()));
			hideFirstColumn(tblStammdatenBeob);
		}
	}
	
	//noch nicht beobachtete Vogelarten ausgeben
	private void selectedChecklistView() {
		
		try {
			tblStammdatenBeob.setModel(MainWindowLogic.selectedChecklistView(level_1, level_2, level_3, txtFilterCoreData.getText()));
		} catch (Exception e) {
			e.printStackTrace();
			ConstantElems.showMsgBox(e);
			chkNichtGesichtete.setSelected(false);
		}
		hideFirstColumn(tblStammdatenBeob);
		filterSelection();
	}
	
	private DefaultTableModel getCoreData() {
		return getCoreData("","");
	}
	
	private DefaultTableModel getCoreData(String filter, String spec) {
		try {
			return MainWindowLogic.getCoreData(filter, spec);
//			tableAdminCoreData.setModel(MainWindowLogic.getCoreData(filter, spec));
//			hideFirstColumn(tableAdminCoreData);
		} catch (Exception e) {
			e.printStackTrace();
			ConstantElems.showMsgBox(e, "Stammdaten konnten nicht geladen werden.");
			return new DefaultTableModel();
		}
	}
	
	private void addBird() {
		String lat, de, eng, spec;
		
		lat = trimText(txtAdminAddBirdNameLat.getText()); // no need to check further after trimming due to checkLatBirdName()
		de  = trimText(txtAdminAddBirdNameDe);
		eng = trimText(txtAdminAddBirdNameEng);
		spec = txtAdminAddBirdSpecType.getText(); // no need to trim
		if (spec.toLowerCase().equals("oberart")) { // Eingabe konvertieren
			spec = "species";
		} else if (spec.toLowerCase().equals("unterart")) {
			spec = "subspecies";
		}
		
		if (isBirdInputValid(de, eng)) {
			try {
				MainWindowLogic.addBird(lat, de, eng, spec);
				tableAdminCoreData.setModel(getCoreData(lat, "Alle"));
				hideFirstColumn(tableAdminCoreData);
				ConstantElems.showMsgBox("Vogelart hinzugefügt!","Erfolg");
			} catch (Exception e) {
				e.printStackTrace();
				ConstantElems.showMsgBox(e, "Vogelart konnte nicht hinzugefügt werden.");
			}
		} else {
			ConstantElems.showMsgBox(new Exception("Ungültige Vogelangaben!"));
		}
		
	}
	
	private void updateBird(String va_id) {
		String lat, de, eng, spec;
		
		lat = trimText(txtAdminEditBirdNameLat.getText()); // no need to check further after trimming due to checkLatBirdName()
		de  = trimText(txtAdminEditBirdNameDe);
		eng = trimText(txtAdminEditBirdNameEng);
		spec = txtAdminEditBirdSpecType.getText(); // no need to trim
		if (spec.toLowerCase().equals("oberart")) { // Eingabe konvertieren
			spec = "species";
		} else if (spec.toLowerCase().equals("unterart")) {
			spec = "subspecies";
		}
		
		if (isBirdInputValid(de, eng)) {
			try {
				MainWindowLogic.updateBird(va_id, lat, de, eng, spec);
				tableAdminCoreData.setModel(getCoreData(lat, "Alle"));
				hideFirstColumn(tableAdminCoreData);
				ConstantElems.showMsgBox("Vogelart aktualisiert!","Erfolg");
			} catch (Exception e) {
				e.printStackTrace();
				ConstantElems.showMsgBox(e, "Vogelart konnte nicht aktualisiert werden.");
			}
		} else {
			ConstantElems.showMsgBox(new Exception("Ungültige Vogelangaben!"));
		} 
		
	}
	
	private boolean isBirdInputValid(String de, String eng) {
		return validBirdName(de) && validBirdName(eng);
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
	
	private void checkLocationName(JTextField textField) {
		if (validLocationName(textField.getText())) {
			textField.setBackground(Color.WHITE);
		} else {
			textField.setBackground(Color.PINK);
		}
	}
	
	private boolean validBirdName(String string) {
		return matches(string);
	}
	
	private boolean matches(String string) {
		return matches(string, "[a-zA-Z- üöäÜÖÄ]*");
	}
	
	private boolean matches(String string, String regEx) {
		return string.matches(regEx);
	}

	// Hilfsfunktion um Filter anzuwenden und Daten neu zu laden
	private void filterSelection(){
		filter = txtFilterObservation.getText();
		ticks = chkFilterTicks.isSelected();
		lifer = chkFilterLifer.isSelected();
		level_1 = cmbRegionAdd.getSelectedItem().toString();
		level_2 = (cmbLandAdd.getItemCount() > 0)?(cmbLandAdd.getSelectedItem().toString()):("");
		level_3 = (cmbGebietAdd.getItemCount() > 0)?(cmbGebietAdd.getSelectedItem().toString()):("");
		tblBeobachtungsliste.setModel(MainWindowLogic.showLiferTicks(level_1, level_2, level_3, filter, ticks, lifer));
		hideFirstColumn(tblBeobachtungsliste);
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
		if (level_1.trim().isEmpty()) {
			ConstantElems.showMsgBox("Sie müssen eine zooökologische Region auswählen!","Ungültige Eingabe");
			return;
		}
		if (datumVom.getDate() == null) {
			ConstantElems.showMsgBox("Sie müssen ein Datum auswählen!","Ungültige Eingabe");
			return;
		}
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

		
		try {
			MainWindowLogic.addObservation(birdId, level_1, level_2, level_3,  formatVon, formatBis, notice);
			ConstantElems.showMsgBox("Beobachtung hinzugefügt!", "Erfolg");
		} catch (Exception e) {
			e.printStackTrace();
			ConstantElems.showMsgBox(e);
		}

		filterSelection();
	}
	
	// Abhängig vom Attribut 'Role' des aktiven Users, Felder (in)aktiv schalten oder aus-/einblenden
	public void applyRolePermissions() {
		String role = BirdwatcherRepository.getActiveUser().role();
		
		// Einrichten, welche Funktionen freigeschaltet bzw. deaktiviert werden sollen
		if (userIsAdmin(role)) {
			applyRolePermissions(true, true);
			initAdminPanel();
		} else if (userIsContentAdmin(role)) {
			applyRolePermissions(true, true);
			initAdminPanel();
		} else if (userIsBirdwatcher(role)) {
			applyRolePermissions(true, false);
			initBirdwatcherPanel();
		}
	}
	
	private void initAdminPanel() {
		tableAdminCoreData.setModel(getCoreData("", "Alle")); // TODO initialfilter - setzbar, falls startperformance verbessert werden soll
		hideFirstColumn(tableAdminCoreData);
		cmbAdminChecklisteL1.setModel(MainWindowLogic.getLevel1Data_Checklist());
		if (cmbAdminChecklisteL1.getItemCount() > 0) selectLevel1_AdminChecklist();
		//-------------//
		tglbtnVerwaltung.doClick();
		tglbtnAdminStammdaten.doClick();
	}
	
	private void initBirdwatcherPanel() {
		cmbRegionAdd.setModel(MainWindowLogic.getLevel1Data());
		if (cmbRegionAdd.getItemCount() == 0) {
			String addMsg = (userIsAdmin(BirdwatcherRepository.getActiveUser().role()))?("Fügen Sie Orte über die Verwaltung hinzu!"):("Kontaktieren Sie einen Administrator, um dies zu ändern.");
			ConstantElems.showMsgBox(new Exception("Es existieren noch keine Orte."), addMsg);
		}
		selectLocation();
		//-------------//
		CardLayout cl = (CardLayout)(panelMain.getLayout());
	    cl.show(panelMain, "panel_beobachtungsliste");
//		tglbtnBeobachtungsliste.doClick();
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
	
	private void toggleCardButtons(boolean beob, boolean admin) {
		 tglbtnBeobachtungsliste.setSelected(beob);
	     tglbtnVerwaltung.setSelected(admin);
	}
	
	private void toggleAdminCardButtons(boolean stamm, boolean lok, boolean check, boolean user, boolean manstate) {
		tglbtnAdminStammdaten.setSelected(stamm);
		tglbtnAdminOrte.setSelected(lok);
		tglbtnAdminChecklisten.setSelected(check);
		tglbtnBenutzer.setSelected(user);
		tglbtnAdminManQuery.setSelected(manstate);

	}
	
	public int dlgYesNo(String message, String headline) {
		int diagAnswer = JOptionPane.showConfirmDialog(null, 
				message, headline, 
                JOptionPane.YES_NO_OPTION); 
        
        return diagAnswer;
	}
	
	public void dlgDoSelectionFirst() {
		ConstantElems.showMsgBox("Wählen Sie zuerst einen Eintrag aus!", "Hinweis");
	}
}
