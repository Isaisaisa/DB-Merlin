package merlin.base;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

public class ResultContainer {
	
	private List<HashMap<String, Object>> resultTable;	
//	private Vector<Vector<Object>> resultTable;	
	private Vector<String> columnNames;
	
	// Bereitet aus dem ResultSet alle nötigen Daten auf, die für eine Tabelle notwendig sind //
	public Object processResultSet(ResultSet resultSet) throws SQLException {
		resultTable = resultSetToArrayList(resultSet);
		resultSet.close();
		return resultTable();
	}
	
	
	
	
	
//	public Vector<Vector<Object>> processResultSet(ResultSet resultSet) throws SQLException {
//		resultTable = getRowData(resultSet);
//		resultSet.close();
//		return resultTable();
//	}
	
	public List<HashMap<String, Object>> resultTable() {
		return resultTable;
	}
//	public Vector<Vector<Object>> resultTable() {
//		return resultTable;
//	}
	
//	// TODO DRINGEND FUNKTIONSFÄHIGKEIT TESTEN
//	public ArrayList<Object> arrayListOfColumn(int columnNumber) {
//		return new ArrayList<Object>( resultTable().get(columnNumber) );
//	}
	
	private Vector<Vector<Object>> getRowData(ResultSet resultSet) throws SQLException {
		
		Vector<Vector<Object>> rowData = new Vector<Vector<Object>>();
		
		// Spaltenanzahl ermitteln
		int columns = resultSet.getMetaData().getColumnCount(); 
		
		// Zeileninhalt ermitteln
		while (resultSet.next()) {
			
			// neue Spalte erzeugen
			Vector<Object> row = new Vector<Object>(columns);
			
			// Spalte mit Zeilen füllen
			for (int i = 1; i <= columns; i++) {
				
				// Zeile mit Werten füllen
				row.addElement(resultSet.getObject(i));
			}
			// Zeile in Spalte eintragen		
			rowData.addElement(row);
		}
		
		// Ergebnis-Tabelle zurückliefern
		return rowData;
	}
	
	private List<HashMap<String, Object>> resultSetToArrayList(ResultSet rs) throws SQLException {
        ResultSetMetaData md = rs.getMetaData();
        int columns = md.getColumnCount();
        ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>(50);
        while (rs.next()) {
            HashMap<String, Object> row = new HashMap<String, Object>(columns);
            for (int i = 1; i <= columns; ++i) {
                row.put(md.getColumnName(i), rs.getObject(i));
            }
            list.add(row);
        }

        return list;
    }
	
    public Vector<String> columnNames(ResultSetMetaData resultSetMetaData) throws SQLException {
        Vector<String> columnNames = new Vector<String>();
        int columns = resultSetMetaData.getColumnCount();

//        time.tell();
//        debugPrint("Spaltennamen ermitteln");
        
        // Spaltennamen ermitteln
        for (int i = 1; i <= columns; i++) {
            String colname = resultSetMetaData.getColumnName(i);
            columnNames.addElement(colname);
        }
        this.columnNames = columnNames;
        return columnNames;
    }
    
    public Vector<String> columnNames() {
        return columnNames;
    }
}