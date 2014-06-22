package merlin.gui;

import java.awt.CardLayout;
import java.awt.Color;
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
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.Vector;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
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
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import merlin.base.Application;
import merlin.data.BirdwatcherRepository;
import merlin.logic.impl.MainWindowLogic;
import merlin.utils.ConstantElems;

import com.toedter.calendar.JDateChooser;

import merlin.data.enums.SpeciesCategoryEnum;


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
	private JTable table_1;
	private static JComboBox<String> cbRegionBeo;
	private static JComboBox<String> cbLandBeo;
	private static JComboBox<String> cbGebietBeo;
	private String level_1;
	private String level_2;
	private String level_3;
	private JSpinner uhrzeitVom;
	private JSpinner uhrzeitBis;
	private JDateChooser datumVom;
	private JDateChooser datumBis;

	private String stringBirdId;
	
	public String stringBirdId(){
		return stringBirdId;
	}
	public void stringBirdId(String s){
		
	}

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
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	
	

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
		tglbtnBeobachtungsliste.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent arg0) {
				tglbtnBeobachtungsliste.setForeground(btnNormalHover);
			}
			@Override
			public void mouseExited(MouseEvent arg0) {
				tglbtnBeobachtungsliste.setForeground(btnNormalNoHover);
			}
		});
		tglbtnBeobachtungsliste.setFont(new Font("Tahoma", Font.PLAIN, 15));
		
		
		tglbtnCheckliste = new JToggleButton("Checkliste");
		tglbtnCheckliste.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent arg0) {
				tglbtnCheckliste.setForeground(btnNormalHover);
			}
			@Override
			public void mouseExited(MouseEvent arg0) {
				tglbtnCheckliste.setForeground(btnNormalNoHover);
			}
		});
		tglbtnCheckliste.setFont(new Font("Tahoma", Font.PLAIN, 15));
		tglbtnVerwaltung = new JToggleButton("Verwaltung");
		tglbtnVerwaltung.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent arg0) {
				tglbtnVerwaltung.setForeground(btnNormalHover);
			}
			@Override
			public void mouseExited(MouseEvent arg0) {
				tglbtnVerwaltung.setForeground(btnNormalNoHover);
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
		tglbtnCheckliste.setBounds(180, 9, 160, 25);
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
		tglbtnVerwaltung.setBounds(350, 9, 160, 25);
		panelUser.add(tglbtnVerwaltung);
		
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
		panelOrtsfilter.setBounds(10, 11, 750, 68);
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
		scrollPane.setBounds(20, 95, 740, 471);
		panelCheckliste.add(scrollPane);
		
		tableCheckliste = new JTable();
		tableCheckliste.setVerifyInputWhenFocusTarget(false);
		scrollPane.setViewportView(tableCheckliste);
		
		
		
		
		
		
		
		
		//BEOBACHTUNGSLISTE
		
		
		
		JPanel panelBeobachtungsliste = new JPanel();
		panelBeobachtungsliste.setBackground(SystemColor.menu);
		panelMain.add(panelBeobachtungsliste, "panel_beobachtungsliste");
		panelBeobachtungsliste.setLayout(null);
		
		JPanel panelCoreDataTable = new JPanel();
		panelCoreDataTable.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Stammdaten", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panelCoreDataTable.setBounds(4, 11, 810, 350);
		panelBeobachtungsliste.add(panelCoreDataTable);
		panelCoreDataTable.setLayout(null);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(10, 59, 790, 280);
		panelCoreDataTable.add(scrollPane_1);
		scrollPane_1.setBackground(SystemColor.menu);
		scrollPane_1.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		
		
		tblStammdatenBeob = new JTable();
		tblStammdatenBeob.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tblStammdatenBeob.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				//VogelId aus der Tabelle holen
				stringBirdId(tblStammdatenBeob.getValueAt(tblStammdatenBeob.getSelectedRow(), 0).toString());
					
			}
		});
		tblStammdatenBeob.setBackground(SystemColor.menu);
		
		scrollPane_1.setViewportView(tblStammdatenBeob);
		tblStammdatenBeob.setModel(MainWindowLogic.loadTableDataIntoGui());
		
		txtFilterCoreData = new JTextField();
		txtFilterCoreData.setBounds(82, 28, 230, 20);
		panelCoreDataTable.add(txtFilterCoreData);
		txtFilterCoreData.setColumns(10);
		
		JLabel lblVolltextfilter = new JLabel("Volltextfilter:");
		lblVolltextfilter.setBounds(10, 31, 62, 14);
		panelCoreDataTable.add(lblVolltextfilter);
		tblStammdatenBeob.getColumnModel().getColumn(0).setPreferredWidth(0);
		tblStammdatenBeob.getColumnModel().getColumn(0).setMinWidth(0);
		tblStammdatenBeob.getColumnModel().getColumn(0).setMaxWidth(0);
		tblStammdatenBeob.getColumnModel().getColumn(0).setWidth(0);
		tblStammdatenBeob.getColumnModel().getColumn(0).setResizable(false);
		try {
		} catch (Exception e) {
			e.printStackTrace();
//			showMsgBox(e);
		}
		
		JPanel panelObservationTable = new JPanel();
		panelObservationTable.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Beobachtungen", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panelObservationTable.setBounds(6, 369, 808, 350);
		panelBeobachtungsliste.add(panelObservationTable);
		panelObservationTable.setLayout(null);
		
		
		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(10, 59, 788, 280);
		panelObservationTable.add(scrollPane_2);
		scrollPane_2.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		
		tblBeobachtungsliste = new JTable();
		scrollPane_2.setViewportView(tblBeobachtungsliste);
		tblBeobachtungsliste.setModel(MainWindowLogic.getDataObservation());
		
		JLabel label = new JLabel("Volltextfilter:");
		label.setBounds(10, 31, 62, 14);
		panelObservationTable.add(label);
		
		txtFilterObservation = new JTextField();
		txtFilterObservation.setColumns(10);
		txtFilterObservation.setBounds(82, 28, 230, 20);
		panelObservationTable.add(txtFilterObservation);
		
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
		lbRegion.setBounds(10, 18, 65, 20);
		panelAddObLocation.add(lbRegion);
		lbRegion.setFont(new Font("Tahoma", Font.PLAIN, 13));
		
		JLabel lblLand = new JLabel("Land:");
		lblLand.setBounds(10, 45, 65, 20);
		panelAddObLocation.add(lblLand);
		lblLand.setFont(new Font("Tahoma", Font.PLAIN, 13));
		
		JLabel lblGebiet = new JLabel("Lokation:");
		lblGebiet.setBounds(10, 72, 65, 20);
		panelAddObLocation.add(lblGebiet);
		lblGebiet.setFont(new Font("Tahoma", Font.PLAIN, 13));
	
		
		

		cbRegionBeo = new  JComboBox<String>();
		cbRegionBeo.setBounds(85, 18, 152, 20);
		panelAddObLocation.add(cbRegionBeo);
		
		
		
		cbLandBeo = new JComboBox<String>();
		cbLandBeo.setBounds(85, 46, 152, 20);
		panelAddObLocation.add(cbLandBeo);
		
		
		
		cbGebietBeo = new JComboBox<String>();
		cbGebietBeo.setBounds(85, 73, 152, 20);
		panelAddObLocation.add(cbGebietBeo);
		cbGebietBeo.addPopupMenuListener(new PopupMenuListener() {
			public void popupMenuCanceled(PopupMenuEvent arg0) {
			}
			public void popupMenuWillBecomeInvisible(PopupMenuEvent arg0) {
			}
			public void popupMenuWillBecomeVisible(PopupMenuEvent arg0) {
				cbGebietBeo.setModel( new DefaultComboBoxModel<String>(MainWindowLogic.loadArea(level_1, level_2)));
			}
		});
		cbGebietBeo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				level_3 = cbGebietBeo.getSelectedItem().toString();
				MainWindowLogic.selectLocation(level_1, level_2, level_3);
			}
		});
		cbLandBeo.addPopupMenuListener(new PopupMenuListener() {
			public void popupMenuCanceled(PopupMenuEvent arg0) {
			}
			public void popupMenuWillBecomeInvisible(PopupMenuEvent arg0) {
			}
			public void popupMenuWillBecomeVisible(PopupMenuEvent arg0) {
				cbLandBeo.setModel( new DefaultComboBoxModel<String>(MainWindowLogic.loadLand(level_1)));
				
			}
		});
		cbLandBeo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				level_2 = cbLandBeo.getSelectedItem().toString();
//				MainWindowLogic.selectLocation(level_1, level_2);
				
			}
		});
		cbRegionBeo.addPopupMenuListener(new PopupMenuListener() {
			public void popupMenuCanceled(PopupMenuEvent arg0) {
			}
			public void popupMenuWillBecomeInvisible(PopupMenuEvent arg0) {
			}
			public void popupMenuWillBecomeVisible(PopupMenuEvent arg0) {
				cbRegionBeo.setModel( new DefaultComboBoxModel<String>(MainWindowLogic.loadRegion()) );
			}
		});
		cbRegionBeo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				level_1 = cbRegionBeo.getSelectedItem().toString();
//				MainWindowLogic.selectLocation(level_1);
			}
		});
		
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
		uhrzeitBis.setEnabled(false);
		uhrzeitBis.setBounds(185, 48, 52, 20);
		panelAddObDate.add(uhrzeitBis);
		uhrzeitBis.setModel(model2);
		uhrzeitBis.setEditor(new JSpinner.DateEditor(uhrzeitBis, "HH:mm"));
		
		final JCheckBox chkDatumBis = new JCheckBox("");
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
		
		
		
		JButton btnAddObservation = new JButton("Eintrag Hinzuf\u00FCgen");
		btnAddObservation.setBounds(20, 316, 226, 23);
		panelAddObservation.add(btnAddObservation);
		btnAddObservation.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				// holt Vogel ID aus JTable
				String birdId = (String)tblStammdatenBeob.getValueAt(tblStammdatenBeob.getSelectedRow(), 0);
				System.out.println("676 MerlinMainWindow : "+ birdId);
			
				// Datum- und Zeitformat
				SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
				SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
				
				System.out.println("682 MerlinMainWindow : " + dateFormat);
				System.out.println("683 MerlinMainWindow : " + timeFormat);
				
				// DatumVon und UhrzeitVon konkatiniert als String
				String formatVon = dateFormat.format(datumVom.getDate()) + " " + timeFormat.format((Date)uhrzeitVom.getValue());
				
				String formatBis;
				if (chkDatumBis.isEnabled()){
					formatBis = null;
				}else{
					// DatumBis und UhrzeitBis konkatiniert als String
					formatBis = dateFormat.format(datumBis.getDate()) + " " + timeFormat.format((Date)uhrzeitBis.getValue());
					System.out.println("694 MerlinMainWindow : " + datumBis.getDate().toString());
					System.out.println("695 MerlinMainWindow : " + ((Date)uhrzeitBis.getValue()).toString());
					System.out.println("696 MerlinMainWindow : " + formatVon);
					System.out.println("697 MerlinMainWindow : " + formatBis);
				}
				
				// holt Notizen/Bemerkungen aus Textfeld	
				String notice = txtComment.getText();
				System.out.println("702 MerlinMainWindow : " + notice);

				MainWindowLogic.addObservation(birdId, level_1, level_2, level_3,  formatVon, formatBis, notice);
				
				
				/* Die Tabelle Beobachtet aus der Datenbank in die Gui laden.
				 * ein update muss durchgeführt werden.
				 * */
				tblBeobachtungsliste.setModel(MainWindowLogic.getDataObservation());
				
			}
		});
		
		
		btnAddObservation.setFocusable(false);
		
		
		JPanel panelMaintainObservation = new JPanel();
		panelMaintainObservation.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Beobachtung verwalten", TitledBorder.LEFT, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panelMaintainObservation.setBounds(824, 369, 266, 327);
		panelBeobachtungsliste.add(panelMaintainObservation);
		panelMaintainObservation.setLayout(null);
		
		
		JButton btnDeleteObservation = new JButton("Eintrag l\u00F6schen");
		btnDeleteObservation.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				
				//Beo-ID
				String beoId = (String)tblBeobachtungsliste.getValueAt(tblBeobachtungsliste.getSelectedRow(), 0);
			
				MainWindowLogic.deleteDataObservation(beoId);
				tblBeobachtungsliste.setModel(MainWindowLogic.getDataObservation());
				
						
			}
		});
		btnDeleteObservation.setFocusable(false);
		btnDeleteObservation.setBounds(20, 285, 226, 23);
		panelMaintainObservation.add(btnDeleteObservation);
		
		
		JButton btnChangeObservation = new JButton("Eintrag bearbeiten");
		btnChangeObservation.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		btnChangeObservation.setFocusable(false);
		btnChangeObservation.setBounds(20, 251, 226, 23);
		panelMaintainObservation.add(btnChangeObservation);
		
		
		
		
		
		
		
		
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
				tglbtnAdminStammdaten.setForeground(btnCriticalHover);
			}
			@Override
			public void mouseExited(MouseEvent arg0) {
				tglbtnAdminStammdaten.setForeground(btnCriticalNoHover);
			}
		});
		tglbtnAdminStammdaten.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				CardLayout cl = (CardLayout)(panelAdminCards.getLayout());
				cl.show(panelAdminCards, "panel_admin_stammdaten");
				
			    toggleAdminCardButtons(true, false, false, false, false);
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
				tglbtnAdminOrte.setForeground(btnCriticalHover);
			}
			@Override
			public void mouseExited(MouseEvent arg0) {
				tglbtnAdminOrte.setForeground(btnCriticalNoHover);
			}
		});
		tglbtnAdminOrte.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				CardLayout cl = (CardLayout)(panelAdminCards.getLayout());
				cl.show(panelAdminCards, "panel_admin_lokationen");
				toggleAdminCardButtons(false, true, false, false, false);
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
				tglbtnAdminChecklisten.setForeground(btnCriticalHover);
			}
			@Override
			public void mouseExited(MouseEvent arg0) {
				tglbtnAdminChecklisten.setForeground(btnCriticalNoHover);
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
				tglbtnBenutzer.setForeground(btnCriticalHover);
			}
			@Override
			public void mouseExited(MouseEvent arg0) {
				tglbtnBenutzer.setForeground(btnCriticalNoHover);
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
		tableAdminCoreData.setBackground(SystemColor.menu);
		
		txtAdminCoreDataFilter = new JTextField();
		txtAdminCoreDataFilter.setColumns(10);
		txtAdminCoreDataFilter.setBounds(82, 28, 230, 20);
		panelAdminCoreDataTable.add(txtAdminCoreDataFilter);
		
		JLabel lblAdminCoreDataFilter = new JLabel("Volltextfilter:");
		lblAdminCoreDataFilter.setBounds(10, 28, 62, 20);
		panelAdminCoreDataTable.add(lblAdminCoreDataFilter);
		
		JComboBox cmbAdminCoreDataSpecType = new JComboBox();
		cmbAdminCoreDataSpecType.setModel(new DefaultComboBoxModel(new String[] {"Alle", "Oberarten", "Unterarten"}));
		cmbAdminCoreDataSpecType.setBounds(559, 28, 120, 20);
		panelAdminCoreDataTable.add(cmbAdminCoreDataSpecType);
		
		JLabel lblAdminCoreDataArtentyp = new JLabel("Artentyp:");
		lblAdminCoreDataArtentyp.setBounds(502, 28, 47, 20);
		panelAdminCoreDataTable.add(lblAdminCoreDataArtentyp);
		
		JButton btnAdminCoreDataFilter = new JButton("Filtern");
		btnAdminCoreDataFilter.setBounds(322, 27, 89, 23);
		panelAdminCoreDataTable.add(btnAdminCoreDataFilter);
		
		JPanel panelAdminAddBird = new JPanel();
		panelAdminAddBird.setBorder(new TitledBorder(null, "Vogel hinzuf\u00FCgen", TitledBorder.LEADING, TitledBorder.TOP, null, null));
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
		
		JComboBox cmbAdminAddBirdSpecType = new JComboBox();
		cmbAdminAddBirdSpecType.setModel(new DefaultComboBoxModel(new String[] {"Oberart", "Unterart"}));
		cmbAdminAddBirdSpecType.setBounds(117, 127, 200, 20);
		panelAdminAddBird.add(cmbAdminAddBirdSpecType);
		
		JButton btnNewButton = new JButton("Hinzuf\u00FCgen");
		btnNewButton.setBounds(117, 166, 200, 23);
		panelAdminAddBird.add(btnNewButton);
		
		JLabel lblpflichtangabe = new JLabel("*Pflichtangabe");
		lblpflichtangabe.setForeground(new Color(0, 0, 128));
		lblpflichtangabe.setBounds(10, 194, 71, 14);
		panelAdminAddBird.add(lblpflichtangabe);
		
		JPanel panelAdminEditBird = new JPanel();
		panelAdminEditBird.setLayout(null);
		panelAdminEditBird.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Vogel bearbeiten (Tabellenauswahl)", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panelAdminEditBird.setBounds(761, 346, 329, 325);
		panelAdminStammdaten.add(panelAdminEditBird);
		
		JLabel label_2 = new JLabel("*Lateinischer Name:");
		label_2.setForeground(new Color(0, 0, 128));
		label_2.setBounds(10, 34, 97, 20);
		panelAdminEditBird.add(label_2);
		
		textField = new JTextField();
		textField.setColumns(10);
		textField.setBounds(117, 34, 200, 20);
		panelAdminEditBird.add(textField);
		
		JLabel label_3 = new JLabel("Deutscher Name:");
		label_3.setBounds(10, 65, 91, 20);
		panelAdminEditBird.add(label_3);
		
		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(117, 65, 200, 20);
		panelAdminEditBird.add(textField_1);
		
		JLabel label_4 = new JLabel("Englischer Name:");
		label_4.setBounds(10, 96, 91, 20);
		panelAdminEditBird.add(label_4);
		
		textField_2 = new JTextField();
		textField_2.setColumns(10);
		textField_2.setBounds(117, 96, 200, 20);
		panelAdminEditBird.add(textField_2);
		
		JLabel label_5 = new JLabel("Artentyp:");
		label_5.setBounds(10, 127, 47, 20);
		panelAdminEditBird.add(label_5);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setBounds(117, 127, 200, 20);
		panelAdminEditBird.add(comboBox);
		
		JButton btnBearbeiten = new JButton("\u00DCbernehmen");
		btnBearbeiten.setBounds(117, 166, 200, 23);
		panelAdminEditBird.add(btnBearbeiten);
		
		JButton btnLschen = new JButton("L\u00F6schen");
		btnLschen.setForeground(new Color(0, 0, 0));
		btnLschen.setBackground(Color.PINK);
		btnLschen.setBounds(117, 200, 200, 23);
		panelAdminEditBird.add(btnLschen);
		
		JLabel label_6 = new JLabel("*Pflichtangabe");
		label_6.setForeground(new Color(0, 0, 128));
		label_6.setBounds(10, 231, 71, 14);
		panelAdminEditBird.add(label_6);
		
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
		scrollPane_5.setViewportView(tableAdminCoreDataConflicts);
		
		JButton btnAdminCoreDataConflicts = new JButton("Ermitteln");
		btnAdminCoreDataConflicts.setBounds(10, 21, 89, 23);
		panelAdminCoreDataConflicts.add(btnAdminCoreDataConflicts);
		
		JPanel panelAdminLokationen = new JPanel();
		panelAdminCards.add(panelAdminLokationen, "panel_admin_lokationen");
		panelAdminLokationen.setLayout(null);
		
		JPanel panelAdminChecklisten = new JPanel();
		panelAdminCards.add(panelAdminChecklisten, "panel_admin_checklisten");
		panelAdminChecklisten.setLayout(null);
		
		JPanel panelAdminBenutzer = new JPanel();
		panelAdminCards.add(panelAdminBenutzer, "panel_admin_benutzer");
		panelAdminBenutzer.setLayout(null);
		
		JPanel panelAdminManStatement = new JPanel();
		panelAdminCards.add(panelAdminManStatement, "panel_admin_manstatement");
		panelAdminManStatement.setLayout(null);
		
		JPanel panelManStatementsFrame = new JPanel();
		panelManStatementsFrame.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Manuelle Anfragen senden", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panelManStatementsFrame.setBounds(10, 11, 1074, 660);
		panelAdminManStatement.add(panelManStatementsFrame);
		panelManStatementsFrame.setLayout(null);
		
		JScrollPane scrollPane_4 = new JScrollPane();
		scrollPane_4.setBounds(10, 65, 1054, 584);
		panelManStatementsFrame.add(scrollPane_4);
		scrollPane_4.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPane_4.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		
		table_1 = new JTable();
		scrollPane_4.setViewportView(table_1);
		
		final JTextPane textPane = new JTextPane();
		textPane.setBounds(10, 16, 464, 38);
		panelManStatementsFrame.add(textPane);
		textPane.setText("SELECT * FROM Birdwatcher");
		textPane.setFont(new Font("Courier New", Font.PLAIN, 12));
		
		JButton btnSendManQuery = new JButton("Senden");
		btnSendManQuery.setBounds(484, 16, 89, 38);
		panelManStatementsFrame.add(btnSendManQuery);
		btnSendManQuery.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ResultSet resultSet;
				try {
					// TODO bereiningen
//					String query = "SELECT * FROM BIRDWATCHER WHERE lower(Benutzername) LIKE lower(demo)";
//					
//					PreparedStatement ps = Application.getInstance().database().prepareStatement(query);
//					ps.setString(1, textPane.getText());
//					resultSet = ps.executeQuery();
					resultSet = Application.getInstance().database().sendQuery(textPane.getText());
					DefaultTableModel resultTableModel = Application.getInstance().database().getTableModel(resultSet);
					resultSet.getStatement().close();
					
					table_1.setModel(resultTableModel);
					
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		});
		btnSendManQuery.setFocusable(false);
		
		/*
		 * AFTER-INITIALIZATION AREA
		 */
		
		tglbtnBeobachtungsliste.doClick();
		
//		showMsgBox(applyRolePermissions());
		applyRolePermissions();
		
		
	}
	
	// Abhängig vom Attribut 'Role' des aktiven Users, Felder (in)aktiv schalten oder aus-/einblenden
	public String applyRolePermissions() {
		String role = BirdwatcherRepository.getActiveUser().role();
		
		// Einrichten, welche Funktionen freigeschaltet bzw. deaktiviert werden sollen
//		userIsAdmin(role);
//		userIsContentAdmin(role);
//		userIsBirdwatcher(role);
		
		return role;
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
//			rs.afterLast();
//			int rowCount = rs.getRow();
//			int rowsDone = 0;
//			rs.beforeFirst();
//			System.out.println(rowCount + " Zeilen");
			/*
			 * 
			 * 
			 */
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
//					progressBar.setValue(rowsDone++ / rowCount * 100);
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

	public void debugPrint(String message) {
		// TODO nachricht zu debug fenster hinzufügen können
	}
}
