package merlin.gui;

import java.awt.EventQueue;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;
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
import java.util.Vector;

import javax.swing.JProgressBar;

import java.awt.Component;

import javax.swing.JTabbedPane;
import javax.swing.JScrollBar;
import javax.swing.JToggleButton;

public class MerlinMainWindow {

	private JFrame frmMerlinMain;
	private JTable table;
	private JTable tableCheckliste;
	private JTable tbCheckliste;
	private JPanel panelMain;
	private String level1;
	private String level2;
	private String level3;
	private static JComboBox<String> cmbLevel1;
	private static JComboBox<String> cmbLevel2;
	private static JComboBox<String> cmbLevel3;
	
	
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
		
		btnAusloggen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					Application.getInstance().closeMerlinYesNo(frmMerlinMain);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
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
			     MainWindowLogic.loadRegion();
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
		cmbLevel1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				level1 = cmbLevel1.getSelectedItem().toString();
				if (cmbLevel1.getSelectedItem().toString().equals(level1)){
					MainWindowLogic.loadLand(level1);
					System.out.println("cmbLevel2 MerlinMainWindow : "+ level1);
				}
				MainWindowLogic.selectLocation(level1);
			}
		});
		cmbLevel1.setBounds(10, 36, 136, 20);
		panelOrtsfilter.add(cmbLevel1);
		
		
		
		cmbLevel2 = new JComboBox<String>();
		cmbLevel2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				level2 = cmbLevel2.getSelectedItem().toString();
				MainWindowLogic.selectLocation(level1, level2);
			}
		});
		cmbLevel2.setBounds(151, 36, 136, 20);
		panelOrtsfilter.add(cmbLevel2);
		
		cmbLevel3 = new JComboBox<String>();
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
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(10, 594, 760, 89);
		panelCheckliste.add(panel_1);
		panel_1.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setAlignmentY(Component.TOP_ALIGNMENT);
		scrollPane.setAlignmentX(Component.LEFT_ALIGNMENT);
		scrollPane.setDoubleBuffered(true);
		scrollPane.setLayout(null);
		scrollPane.setBounds(0, 0, 760, 89);
		panel_1.add(scrollPane);
		
		table = new JTable();
		scrollPane.setColumnHeaderView(table);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBounds(10, 90, 760, 493);
		panelCheckliste.add(panel_2);
		panel_2.setLayout(null);
		
		tableCheckliste = new JTable();
		tableCheckliste.setModel(new DefaultTableModel(
			new Object[][] {
				{null, null},
				{null, null},
			},
			new String[] {
				"New column", "New column"
			}
		));
		tableCheckliste.setBounds(10, 22, 740, 471);
		panel_2.add(tableCheckliste);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(0, 0, 5, 5);
		panel_2.add(tabbedPane);
		
		JScrollBar scrollBar = new JScrollBar();
		scrollBar.setBounds(733, 22, 17, 471);
		panel_2.add(scrollBar);
		
		JPanel panelBeobachtungsliste = new JPanel();
		panelMain.add(panelBeobachtungsliste, "name_117830304432961");
		panelBeobachtungsliste.setLayout(null);
		
		tbCheckliste = new JTable();
		tbCheckliste.setBounds(10, 22, 740, 471);
		panelBeobachtungsliste.add(tbCheckliste);
		
		JPanel panelStammdaten = new JPanel();
		panelMain.add(panelStammdaten, "name_524734224155361");
		panelStammdaten.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setBorder(new TitledBorder(null, "Manuelles Statement", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(458, 11, 540, 68);
		panelStammdaten.add(panel);
		
		JTextPane textPane = new JTextPane();
		textPane.setText("SELECT * FROM Birdwatcher");
		textPane.setFont(new Font("Courier New", Font.PLAIN, 12));
		textPane.setBounds(10, 21, 421, 38);
		panel.add(textPane);
		
		JButton button = new JButton("Ab daf\u00FCr");
		button.setBounds(441, 21, 89, 38);
		panel.add(button);
		
		JButton btnKonflikteErmitteln = new JButton("Konflikte ermitteln ");
		btnKonflikteErmitteln.setBounds(303, 27, 145, 29);
		panelStammdaten.add(btnKonflikteErmitteln);
		
		JPanel panel_3 = new JPanel();
		panel_3.setLayout(null);
		panel_3.setBorder(new TitledBorder(null, "Optionen", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_3.setBounds(780, 90, 218, 493);
		panelStammdaten.add(panel_3);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setBounds(25, 60, 172, 20);
		panel_3.add(comboBox);
		
		JLabel label = new JLabel("Sprache");
		label.setFont(new Font("Tahoma", Font.PLAIN, 12));
		label.setBounds(25, 38, 172, 20);
		panel_3.add(label);
		
		JLabel label_1 = new JLabel("Vogel");
		label_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		label_1.setBounds(25, 91, 172, 14);
		panel_3.add(label_1);
		
		JComboBox comboBox_1 = new JComboBox();
		comboBox_1.setBounds(25, 114, 172, 20);
		panel_3.add(comboBox_1);
		
		JToggleButton toggleButton = new JToggleButton("Hinzuf\u00FCgen");
		toggleButton.setBounds(47, 168, 121, 23);
		panel_3.add(toggleButton);
		
		JToggleButton toggleButton_1 = new JToggleButton("L\u00F6schen");
		toggleButton_1.setBounds(47, 201, 121, 23);
		panel_3.add(toggleButton_1);
		
		JToggleButton toggleButton_2 = new JToggleButton("Abbrechen");
		toggleButton_2.setBounds(47, 235, 121, 23);
		panel_3.add(toggleButton_2);
	}
	
	
	public static void loadLevel1(String string) {
		cmbLevel1.addItem(string);
	}
	public static void loadLevel2(String string) {
		cmbLevel2.addItem(string);
		System.out.println("this is loadLevel2 : " + string);
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
