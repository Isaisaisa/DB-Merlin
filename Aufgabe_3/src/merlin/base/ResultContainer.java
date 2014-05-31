package merlin.base;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Vector;

public class ResultContainer {
	private ResultSet resultSet;
	private ResultSetMetaData resultSetMetaData;
	private Vector<String> columnNames;
	private Vector<Vector<Object>> rowData;
	
//	public ResultContainer(ResultSet resultSet, ResultSetMetaData resultSetMetaData, Vector<String> columnNames, Vector<Vector<Object>> rowData) {
//		this.resultSet			= resultSet;
//		this.resultSetMetaData	= resultSetMetaData;
//		this.columnNames		= columnNames;
//		this.rowData			= rowData;
//	}
	
	public ResultContainer() {}
	
	
	/* Bereitet aus dem ResultSet alle nötigen Daten auf, die für eine Tabelle notwendig sind */
	public Vector<Vector<Object>> resultSet(ResultSet resultSet) throws SQLException {
		this.resultSet 		= resultSet;
		resultSetMetaData 	= resultSet.getMetaData();
		columnNames 		= columnNames(resultSetMetaData);
		rowData 			= rowData(resultSet, columnNames);
		return rowData;
	}
	
	public ResultSet resultSet() {
		return resultSet;
	}
	
	public ResultSetMetaData resultSetMetaData() {
		return resultSetMetaData;
	}
	
	public Vector<String> columnNames() {
		return columnNames;
	}
	
	public Vector<Vector<Object>> rowData() {
		return rowData;
	}
	
	
	/* processResult BASE METHOD */
	public Vector<Vector<Object>> rowData(ResultSet resultSet, Vector<String> columnNames) throws SQLException {
		
		Vector<Vector<Object>> resultRowData = new Vector<Vector<Object>>();
		int columns = columnNames.size();

		/* Zeileninhalt ermitteln */
		while (resultSet.next()) {
			
			/* neue Spalte erzeugen */
			Vector<Object> row = new Vector<Object>(columns);
			
			/* Spalte mit Zeilen füllen */
			for (int i = 1; i <= columns; i++) {
				
				/* Zeile mit Werten füllen */
				row.addElement(resultSet.getObject(i));
			}
			/* Zeile in Spalte eintragen */			
			resultRowData.addElement(row);
		}
		
		/* Ergebnis-Tabelle zurückliefern */
		return resultRowData;
	}
	
	
	public Vector<String> columnNames(ResultSetMetaData resultSetMetaData) throws SQLException {
        Vector<String> columnNames = new Vector<String>();
        int columns = resultSetMetaData.getColumnCount();
    
        /* Spaltennamen ermitteln */
        for (int i = 1; i <= columns; i++) {
            String colname = resultSetMetaData.getColumnName(i);
            columnNames.addElement(colname);
        }
        return columnNames;
    }
	
}