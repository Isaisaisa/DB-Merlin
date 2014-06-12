package merlin.gui;

import java.awt.EventQueue;
import java.awt.Toolkit;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SpinnerDateModel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.JButton;

import merlin.base.Application;
import merlin.logic.impl.MainWindowLogic;
import merlin.utils.ConstantElems;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.SystemColor;
import java.awt.CardLayout;

import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextPane;

import java.awt.Font;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Vector;

import javax.swing.JProgressBar;
import javax.swing.JTabbedPane;
import javax.swing.JScrollBar;
import javax.swing.JToggleButton;

import java.awt.Color;

import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.JTextArea;
import javax.swing.event.PopupMenuListener;
import javax.swing.event.PopupMenuEvent;

import com.toedter.calendar.JDateChooser;

import javax.swing.JSpinner;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ListSelectionModel;

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
	private JSpinner uhrzeitVon;
	private JSpinner uhrzeitBis;
	private JDateChooser datumVon;
	private JDateChooser datumBis;
	private JTextArea txtNotice;
	private String stringBirdId;
	
	public String stringBirdId(){
		return stringBirdId;
	}
	public void stringBirdId(String s){
		
	}
	
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
		frmMerlinMain.setBounds(50, 50, 1024, 800);
		frmMerlinMain.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmMerlinMain.getContentPane().setLayout(null);
		
		JPanel panelUser = new JPanel();
		panelUser.setBackground(SystemColor.inactiveCaption);
		panelUser.setBounds(0, 0, 1008, 43);
		frmMerlinMain.getContentPane().add(panelUser);
		panelUser.setLayout(null);
		
		JButton btnAusloggen = new JButton("Ausloggen");
		btnAusloggen.setFocusable(false);
		
		btnAusloggen.setBounds(903, 11, 95, 23);
		panelUser.add(btnAusloggen);
		
		final JToggleButton tglbtnBeobachtungsliste = new JToggleButton("Beobachtungsliste");
		final JToggleButton tglbtnCheckliste = new JToggleButton("Checkliste");
		final JToggleButton tglbtnStammdaten = new JToggleButton("Verwaltung");
		
		tglbtnBeobachtungsliste.setFocusable(false);
		tglbtnBeobachtungsliste.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				 CardLayout cl = (CardLayout)(panelMain.getLayout());
			     cl.show(panelMain, "name_117830304432961");
			     
			     tglbtnBeobachtungsliste.setSelected(true);
			     tglbtnCheckliste.setSelected(false);
			     tglbtnStammdaten.setSelected(false);
			}
		});
		tglbtnBeobachtungsliste.setBounds(7, 11, 121, 23);
		panelUser.add(tglbtnBeobachtungsliste);
		
		
		
		tglbtnCheckliste.setFocusable(false);
		tglbtnCheckliste.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				 CardLayout cl = (CardLayout)(panelMain.getLayout());
			     cl.show(panelMain, "name_117825363666024");
			     
			     tglbtnBeobachtungsliste.setSelected(false);
			     tglbtnCheckliste.setSelected(true);
			     tglbtnStammdaten.setSelected(false);
			    
			    
			}
		});
		tglbtnCheckliste.setBounds(138, 11, 121, 23);
		panelUser.add(tglbtnCheckliste);
		
		
		tglbtnStammdaten.setFocusable(false);
		tglbtnStammdaten.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				CardLayout cl = (CardLayout)(panelMain.getLayout());
			    cl.show(panelMain, "name_524734224155361");
			    
			    tglbtnBeobachtungsliste.setSelected(false);
			    tglbtnCheckliste.setSelected(false);
			    tglbtnStammdaten.setSelected(true);
			}
		});
		tglbtnStammdaten.setBounds(267, 11, 121, 23);
		panelUser.add(tglbtnStammdaten);
		
		panelMain = new JPanel();
		panelMain.setBounds(0, 43, 1008, 719);
		frmMerlinMain.getContentPane().add(panelMain);
		panelMain.setLayout(new CardLayout(0, 0));
		
		JPanel panelCheckliste = new JPanel();
		panelMain.add(panelCheckliste, "name_117825363666024");
		panelCheckliste.setLayout(null);
		
		JPanel panelOrtsfilter = new JPanel();
		panelOrtsfilter.setBorder(new TitledBorder(null, "Nach Ort filtern", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelOrtsfilter.setBounds(10, 11, 438, 68);
		panelCheckliste.add(panelOrtsfilter);
		panelOrtsfilter.setLayout(null);
		
		JLabel lblLevel1 = new JLabel("Region");
		lblLevel1.setBounds(14, 16, 132, 20);
		panelOrtsfilter.add(lblLevel1);
		
		JLabel lblLevel2 = new JLabel("Land");
		lblLevel2.setBounds(155, 16, 132, 20);
		panelOrtsfilter.add(lblLevel2);
		
		JLabel lblLevel3 = new JLabel("Gebiet");
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
				MainWindowLogic.selectLocation(level1);
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
				MainWindowLogic.selectLocation(level1, level2);
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
				MainWindowLogic.selectLocation(level1, level2, level3);
			}
		});
		cmbLevel3.setBounds(292, 36, 136, 20);
		panelOrtsfilter.add(cmbLevel3);
		
		
		JProgressBar progressBar = new JProgressBar();
		progressBar.setStringPainted(true);
		progressBar.setBounds(10, 694, 760, 14);
		panelCheckliste.add(progressBar);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBounds(10, 90, 760, 493);
		panelCheckliste.add(panel_2);
		panel_2.setLayout(null);
		
		tableCheckliste = new JTable();
		tableCheckliste.setBounds(10, 22, 740, 471);
		panel_2.add(tableCheckliste);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(0, 0, 5, 5);
		panel_2.add(tabbedPane);
		
		JScrollBar scrollBar = new JScrollBar();
		scrollBar.setBounds(733, 22, 17, 471);
		panel_2.add(scrollBar);
		
		JButton btnFiltern = new JButton("Filtern");
		btnFiltern.setBounds(458, 45, 89, 23);
		panelCheckliste.add(btnFiltern);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Bearbeitung", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel_1.setBounds(780, 95, 218, 488);
		panelCheckliste.add(panel_1);
		panel_1.setLayout(null);
		
		
		
		
		
		JPanel panelBeobachtungsliste = new JPanel();
		panelBeobachtungsliste.setBackground(SystemColor.menu);
		panelMain.add(panelBeobachtungsliste, "name_117830304432961");
		panelBeobachtungsliste.setLayout(null);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBackground(SystemColor.menu);
		scrollPane_1.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane_1.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPane_1.setBounds(10, 22, 713, 320);
		panelBeobachtungsliste.add(scrollPane_1);
		
		
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
		try {
			tblStammdatenBeob.setModel(MainWindowLogic.loadTableDataIntoGui());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane_2.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPane_2.setBounds(10, 373, 713, 320);
		panelBeobachtungsliste.add(scrollPane_2);
		
		tblBeobachtungsliste = new JTable();
		scrollPane_2.setViewportView(tblBeobachtungsliste);
		
		JPanel panel_4 = new JPanel();
		panel_4.setBackground(SystemColor.control);
		panel_4.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Beobachtung hinzuf\u00FCgen", TitledBorder.LEFT, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel_4.setBounds(733, 22, 265, 320);
		panelBeobachtungsliste.add(panel_4);
		panel_4.setLayout(null);
		
		JLabel lbRegion = new JLabel("Region");
		lbRegion.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lbRegion.setBounds(11, 26, 65, 16);
		panel_4.add(lbRegion);
		
		JLabel lblLand = new JLabel("Land");
		lblLand.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblLand.setBounds(11, 53, 65, 16);
		panel_4.add(lblLand);
		
		JLabel lblGebiet = new JLabel("Gebiet");
		lblGebiet.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblGebiet.setBounds(11, 80, 65, 16);
		panel_4.add(lblGebiet);
	
		
		

		cbRegionBeo = new  JComboBox<String>();
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
				MainWindowLogic.selectLocation(level_1);
			}
		});
		cbRegionBeo.setBounds(81, 27, 152, 18);
		panel_4.add(cbRegionBeo);
		
		
		
		cbLandBeo = new JComboBox<String>();
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
				MainWindowLogic.selectLocation(level_1, level_2);
				
			}
		});
		cbLandBeo.setBounds(81, 53, 152, 18);
		panel_4.add(cbLandBeo);
		
		
		
		cbGebietBeo = new JComboBox<String>();
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
		cbGebietBeo.setBounds(81, 81, 152, 18);
		panel_4.add(cbGebietBeo);
		
		
		
		
		
		
		
		JLabel lblDatum = new JLabel("Datum");
		lblDatum.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblDatum.setBounds(11, 107, 46, 14);
		panel_4.add(lblDatum);
		
		JLabel lblVon = new JLabel("Von");
		lblVon.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblVon.setBounds(11, 132, 46, 14);
		panel_4.add(lblVon);
		
		JLabel lblBis = new JLabel("Bis");
		lblBis.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblBis.setBounds(11, 157, 46, 14);
		panel_4.add(lblBis);
		
		JLabel lblBemerkung = new JLabel("Bemerkung");
		lblBemerkung.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblBemerkung.setBounds(11, 187, 83, 17);
		panel_4.add(lblBemerkung);
		
		JScrollPane scrollPane_3 = new JScrollPane();
		scrollPane_3.setBounds(81, 187, 150, 74);
		panel_4.add(scrollPane_3);
		
		txtNotice = new JTextArea();
		scrollPane_3.setViewportView(txtNotice);
		
		
		
		
		
		
		
		JButton btnHinzufgen = new JButton("Hinzuf\u00FCgen");
		btnHinzufgen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				tblStammdatenBeob.getSelectedRow();
				
				SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
				SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
				
				String formatVon = dateFormat.format(datumVon.getDate()) + " " + timeFormat.format((Date)uhrzeitVon.getValue());
				String formatBis = dateFormat.format(datumBis.getDate()) + " " + timeFormat.format((Date)uhrzeitBis.getValue());
				
				System.out.println(formatVon);
				System.out.println(formatBis);
				
				String notice = txtNotice.getText();
				
				MainWindowLogic.addObservation(stringBirdId(), level_1, level_2, level_3,  formatVon, formatBis, notice);
				
			}
		});
		
		
		
		
		
		btnHinzufgen.setFocusable(false);
		btnHinzufgen.setBounds(56, 272, 89, 23);
		panel_4.add(btnHinzufgen);
		
		JToggleButton toggleButton_3 = new JToggleButton("Abbrechen");
		toggleButton_3.setBounds(155, 272, 93, 23);
		panel_4.add(toggleButton_3);
		
		datumVon = new JDateChooser();
		datumVon.setBounds(81, 123, 89, 23);
		panel_4.add(datumVon);
		
		datumBis = new JDateChooser();
		datumBis.setBounds(81, 153, 89, 23);
		panel_4.add(datumBis);
		
		
		
		
		
		
		SpinnerDateModel model1 = new SpinnerDateModel();
		model1.setCalendarField(Calendar.MINUTE);
		
		uhrzeitVon = new JSpinner();
		uhrzeitVon.setBounds(197, 130, 58, 20);
		uhrzeitVon.setModel(model1);
		uhrzeitVon.setEditor(new JSpinner.DateEditor(uhrzeitVon, "HH:mm"));
		panel_4.add(uhrzeitVon);
		
		SpinnerDateModel model2 = new SpinnerDateModel();
		model2.setCalendarField(Calendar.MINUTE);
		
		uhrzeitBis = new JSpinner();
		uhrzeitBis.setBounds(197, 155, 58, 20);
		uhrzeitBis.setModel(model2);
		uhrzeitBis.setEditor(new JSpinner.DateEditor(uhrzeitBis, "HH:mm"));
		panel_4.add(uhrzeitBis);
		
		
		
		
		
		
		JPanel panelBeoVerwalten = new JPanel();
		panelBeoVerwalten.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Beobachtung verwalten", TitledBorder.LEFT, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panelBeoVerwalten.setBounds(733, 374, 265, 233);
		panelBeobachtungsliste.add(panelBeoVerwalten);
		panelBeoVerwalten.setLayout(null);
		
		
		JButton btnBeobachtungLoeschen = new JButton("Beobachtung l\u00F6schen");
		btnBeobachtungLoeschen.setFocusable(false);
		btnBeobachtungLoeschen.setBounds(43, 141, 176, 23);
		panelBeoVerwalten.add(btnBeobachtungLoeschen);
		
		
		JButton btnBeobachtungBearbeiten = new JButton("Beobachtung bearbeiten");
		btnBeobachtungBearbeiten.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		btnBeobachtungBearbeiten.setFocusable(false);
		btnBeobachtungBearbeiten.setBounds(43, 103, 176, 23);
		panelBeoVerwalten.add(btnBeobachtungBearbeiten);
		
		
		
		
		
		
		
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
