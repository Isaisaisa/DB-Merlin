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
import merlin.logic.impl.MainWindowLogic;
import merlin.utils.ConstantElems;


import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ListSelectionModel;

import com.toedter.calendar.JDateChooser;


public class MerlinMainWindow {

	private JFrame frmMerlinMain;
	private JTable tableCheckliste;
	private JTable tblStammdatenBeob;
	private JPanel panelMain;
	private String level1;
	private String level2;
	private String level3;
	private static JComboBox<String> cmbLevel1;
	private static JComboBox<String> cmbLevel2;
	private static JComboBox<String> cmbLevel3;
	private JTable tblBeobachtungsliste;
	private JTable table_1;
	private JTextField textField_6;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_4;
	private JTextField textField_5;
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

	private JTextArea txtNotice;
	private String stringBirdId;
	
	public String stringBirdId(){
		return stringBirdId;
	}
	public void stringBirdId(String s){
		
	}

	private JTextArea txtComment;
	private JTextField txtFilterCoreData;
	private JTextField txtFilterObservation;
	
	private final Color btnNormalHover 		= new Color(34,153,176);
	private final Color btnNormalNoHover 	= new Color(0,0,0);
	private final Color btnCriticalHover	= new Color(255,0,0);
	private final Color btnCriticalNoHover 	= new Color(0,0,0);

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
		frmMerlinMain.setBounds(50, 50, 1105, 796);
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
		
		final JToggleButton tglbtnBeobachtungsliste = new JToggleButton("Beobachtungsliste");
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
		final JToggleButton tglbtnCheckliste = new JToggleButton("Checkliste");
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
		final JToggleButton tglbtnVerwaltung = new JToggleButton("Verwaltung");
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
			     cl.show(panelMain, "name_117830304432961");
			     
			     tglbtnBeobachtungsliste.setSelected(true);
			     tglbtnCheckliste.setSelected(false);
			     tglbtnVerwaltung.setSelected(false);
			}
		});
		tglbtnBeobachtungsliste.setBounds(7, 9, 160, 25);
		panelUser.add(tglbtnBeobachtungsliste);
		
		
		
		tglbtnCheckliste.setFocusable(false);
		tglbtnCheckliste.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				 CardLayout cl = (CardLayout)(panelMain.getLayout());
			     cl.show(panelMain, "name_117825363666024");
			     
			     tglbtnBeobachtungsliste.setSelected(false);
			     tglbtnCheckliste.setSelected(true);
			     tglbtnVerwaltung.setSelected(false);
			    
			    
			}
		});
		tglbtnCheckliste.setBounds(177, 9, 160, 25);
		panelUser.add(tglbtnCheckliste);
		
		
		tglbtnVerwaltung.setFocusable(false);
		tglbtnVerwaltung.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				CardLayout cl = (CardLayout)(panelMain.getLayout());
			    cl.show(panelMain, "name_524734224155361");
			    
			    tglbtnBeobachtungsliste.setSelected(false);
			    tglbtnCheckliste.setSelected(false);
			    tglbtnVerwaltung.setSelected(true);
			}
		});
		tglbtnVerwaltung.setBounds(347, 9, 160, 25);
		panelUser.add(tglbtnVerwaltung);
		
		panelMain = new JPanel();
		panelMain.setBounds(0, 43, 1100, 725);
		frmMerlinMain.getContentPane().add(panelMain);
		panelMain.setLayout(new CardLayout(0, 0));
		
		
		
		
		
		
		
		
		//CHECKLISTE
		
		
		JPanel panelCheckliste = new JPanel();
		panelMain.add(panelCheckliste, "name_117825363666024");
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
		
		JButton btnFiltern = new JButton("Filtern");
		btnFiltern.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				System.out.println(level1);
				System.out.println(level2); //evtl. null 
				System.out.println(level3); //evtl. null 
				
				tableCheckliste.setModel(MainWindowLogic.selectLocation(level1, level2, level3));
			}
		});
		btnFiltern.setBounds(438, 35, 89, 23);
		panelOrtsfilter.add(btnFiltern);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(20, 95, 740, 471);
		panelCheckliste.add(scrollPane);
		
		tableCheckliste = new JTable();
		tableCheckliste.setVerifyInputWhenFocusTarget(false);
		scrollPane.setViewportView(tableCheckliste);
		
		
		
		
		
		
		
		
		//BEOBACHTUNGSLISTE
		
		
		
		JPanel panelBeobachtungsliste = new JPanel();
		panelBeobachtungsliste.setBackground(SystemColor.menu);
		panelMain.add(panelBeobachtungsliste, "name_117830304432961");
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
				

				tblStammdatenBeob.getSelectedRow();

			
				//TODO Eintrag korrekt hinzufügen
				SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
				SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
				
				System.out.println("684 MerlinMainWindow : " + dateFormat);
				System.out.println("685 MerlinMainWindow : " + timeFormat);
				
				String formatVon = dateFormat.format(datumVom.getDate()) + " " + timeFormat.format((Date)uhrzeitVom.getValue());
//				if (datumBis.getDate() == null && uhrzeitBis.getValue() == null){
//					String formatBis = null;
//				}
				String formatBis = dateFormat.format(datumBis.getDate()) + " " + timeFormat.format((Date)uhrzeitBis.getValue());
				System.out.println("692 MerlinMainWindow : " + datumBis.getDate().toString());
				System.out.println("693 MerlinMainWindow : " + ((Date)uhrzeitBis.getValue()).toString());
				System.out.println("694 MerlinMainWindow : " + formatVon);
				System.out.println("695 MerlinMainWindow : " + formatBis);
				
					
				String notice = txtComment.getText();
				System.out.println("699 MerlinMainWindow : " + notice);

				MainWindowLogic.addObservation("300", "WPA", "GER", "Hamburg", "06-05-2014 11:02", "07-05-2014 11:02", "hallihallo");
//				MainWindowLogic.addObservation("300", "WPA", "GER", "Hamburg", "06-05-2014 11:02", null, "hallihallo");
//				MainWindowLogic.addObservation("300", level_1, level_2, level_3,  formatVon, null, notice);
//				MainWindowLogic.addObservation(stringBirdId(), level_1, level_2, level_3,  formatVon, formatBis, notice);
				
			}
		});
		
		
		btnAddObservation.setFocusable(false);
		
		
		JPanel panelMaintainObservation = new JPanel();
		panelMaintainObservation.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Beobachtung verwalten", TitledBorder.LEFT, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panelMaintainObservation.setBounds(824, 369, 266, 350);
		panelBeobachtungsliste.add(panelMaintainObservation);
		panelMaintainObservation.setLayout(null);
		
		
		JButton btnDeleteObservation = new JButton("Eintrag l\u00F6schen");
		btnDeleteObservation.setFocusable(false);
		btnDeleteObservation.setBounds(20, 227, 226, 23);
		panelMaintainObservation.add(btnDeleteObservation);
		
		
		JButton btnChangeObservation = new JButton("Eintrag bearbeiten");
		btnChangeObservation.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		btnChangeObservation.setFocusable(false);
		btnChangeObservation.setBounds(20, 193, 226, 23);
		panelMaintainObservation.add(btnChangeObservation);
		
		
		
		
		
		
		
		
		//STAMMDATEN
		
		
		
		JPanel panelStammdaten = new JPanel();
		panelMain.add(panelStammdaten, "name_524734224155361");
		panelStammdaten.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setBorder(new TitledBorder(null, "Manuelles Statement", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(24, 11, 540, 68);
		panelStammdaten.add(panel);
		
		JTextPane textPane = new JTextPane();
		textPane.setText("SELECT * FROM Birdwatcher");
		textPane.setFont(new Font("Courier New", Font.PLAIN, 12));
		textPane.setBounds(10, 21, 421, 38);
		panel.add(textPane);
		
		JButton button = new JButton("Ab daf\u00FCr");
		button.setFocusable(false);
		button.setBounds(441, 21, 89, 38);
		panel.add(button);
		
		JPanel panel_3 = new JPanel();
		panel_3.setLayout(null);
		panel_3.setBorder(new TitledBorder(null, "Optionen", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_3.setBounds(780, 90, 218, 543);
		panelStammdaten.add(panel_3);
		
		JToggleButton toggleButton = new JToggleButton("Hinzuf\u00FCgen");
		toggleButton.setFocusable(false);
		toggleButton.setBounds(47, 376, 121, 23);
		panel_3.add(toggleButton);
		
		JToggleButton toggleButton_1 = new JToggleButton("L\u00F6schen");
		toggleButton_1.setFocusable(false);
		toggleButton_1.setBounds(47, 410, 121, 23);
		panel_3.add(toggleButton_1);
		
		JToggleButton toggleButton_2 = new JToggleButton("Abbrechen");
		toggleButton_2.setFocusable(false);
		toggleButton_2.setBounds(47, 444, 121, 23);
		panel_3.add(toggleButton_2);
		
		textField_6 = new JTextField();
		textField_6.setColumns(10);
		textField_6.setBounds(26, 63, 170, 29);
		panel_3.add(textField_6);
		
		JLabel lblVogelId = new JLabel("Vogel ID");
		lblVogelId.setBounds(26, 38, 170, 14);
		panel_3.add(lblVogelId);
		
		JLabel lblArtentyp = new JLabel("Artentyp");
		lblArtentyp.setBounds(26, 103, 170, 14);
		panel_3.add(lblArtentyp);
		
		JLabel lblDeutscherName = new JLabel("Deutscher Name");
		lblDeutscherName.setBounds(26, 168, 170, 14);
		panel_3.add(lblDeutscherName);
		
		JLabel lblEnglischerName = new JLabel("Englischer Name");
		lblEnglischerName.setBounds(26, 233, 170, 14);
		panel_3.add(lblEnglischerName);
		
		JLabel lblLateinischerName = new JLabel("Lateinischer Name");
		lblLateinischerName.setBounds(26, 298, 170, 14);
		panel_3.add(lblLateinischerName);
		
		textField_2 = new JTextField();
		textField_2.setColumns(10);
		textField_2.setBounds(26, 128, 170, 29);
		panel_3.add(textField_2);
		
		textField_3 = new JTextField();
		textField_3.setColumns(10);
		textField_3.setBounds(26, 193, 170, 29);
		panel_3.add(textField_3);
		
		textField_4 = new JTextField();
		textField_4.setColumns(10);
		textField_4.setBounds(26, 258, 170, 29);
		panel_3.add(textField_4);
		
		textField_5 = new JTextField();
		textField_5.setColumns(10);
		textField_5.setBounds(26, 323, 170, 29);
		panel_3.add(textField_5);
		
		JScrollPane scrollPane_4 = new JScrollPane();
		scrollPane_4.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPane_4.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane_4.setBounds(24, 90, 746, 543);
		panelStammdaten.add(scrollPane_4);
		
		table_1 = new JTable();
		scrollPane_4.setViewportView(table_1);
		
		/*
		 * AFTER-INITIALIZATION AREA
		 */
		
		tglbtnBeobachtungsliste.doClick();
		
		
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

	public void debugPrint(String message) {
		// TODO nachricht zu debug fenster hinzufügen können
	}
}
