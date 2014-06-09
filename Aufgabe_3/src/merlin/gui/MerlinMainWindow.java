package merlin.gui;

import java.awt.EventQueue;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.JButton;

import merlin.base.Application;
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
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JProgressBar;

import java.awt.Component;

public class MerlinMainWindow {

	private JFrame frmMerlinMain;
	private JTextPane txtpnStatement;
	private JTable table;
	private JProgressBar progressBar;
	private JTable tableCheckliste;

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
		
		JButton btnEinstellungen = new JButton("Einstellungen");
		btnEinstellungen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				/* TODO Einstellungen Dialog anzeigen */
				/* TODO Einstlelungen Dialog GUI-Design und Implementation */
			}
		});
		btnEinstellungen.setBounds(798, 11, 95, 23);
		panelUser.add(btnEinstellungen);
		
		JPanel panelMain = new JPanel();
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
		
		JComboBox<?> cmbLevel1 = new JComboBox<Object>();
		cmbLevel1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.out.println();
			}
		});
		cmbLevel1.setBounds(10, 36, 136, 20);
		panelOrtsfilter.add(cmbLevel1);
		
		JComboBox<?> cmbLevel2 = new JComboBox<Object>();
		cmbLevel2.setBounds(151, 36, 136, 20);
		panelOrtsfilter.add(cmbLevel2);
		
		JComboBox<?> cmbLevel3 = new JComboBox<Object>();
		cmbLevel3.setBounds(292, 36, 136, 20);
		panelOrtsfilter.add(cmbLevel3);
		
		JPanel panelOptions = new JPanel();
		panelOptions.setBorder(new TitledBorder(null, "Optionen", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelOptions.setBounds(780, 90, 218, 493);
		panelCheckliste.add(panelOptions);
		panelOptions.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Manuelles Statement", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(458, 11, 540, 68);
		panelCheckliste.add(panel);
		panel.setLayout(null);
		
		final JTextPane txtpnStatement = new JTextPane();
		txtpnStatement.setFont(new Font("Courier New", Font.PLAIN, 12));
		txtpnStatement.setText("SELECT * FROM Birdwatcher");
		txtpnStatement.setBounds(10, 21, 421, 38);
		panel.add(txtpnStatement);
		
		JButton btnStatement = new JButton("Ab daf\u00FCr");
		btnStatement.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String query = txtpnStatement.getText();
				
				if (!query.isEmpty()) {
					try {
						System.out.println("Sende: " + txtpnStatement.getText());
						ResultSet rs = Application.getInstance().database().sendQuery(query);
						
						tableCheckliste.setModel(getTableModel(rs));
						
					} catch (Exception e) {
						e.printStackTrace();
						System.err.println(e.getMessage());
					}
				}
			}
		});
		btnStatement.setBounds(441, 21, 89, 38);
		panel.add(btnStatement);
		
		
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
		tableCheckliste.setBounds(10, 11, 740, 471);
		panel_2.add(tableCheckliste);
		
		JPanel panelBeobachtungsliste = new JPanel();
		panelMain.add(panelBeobachtungsliste, "name_117830304432961");
		panelBeobachtungsliste.setLayout(null);
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
