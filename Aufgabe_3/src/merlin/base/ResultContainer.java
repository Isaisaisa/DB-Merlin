package merlin.base;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;

public class ResultContainer {
	
	private Vector<Vector<Object>> resultTable;	
	
	// Bereitet aus dem ResultSet alle n�tigen Daten auf, die f�r eine Tabelle notwendig sind //
	public Vector<Vector<Object>> processResultSet(ResultSet resultSet) throws SQLException {
		resultTable = getRowData(resultSet);
		resultSet.close();
		return resultTable();
	}
	
	public Vector<Vector<Object>> resultTable() {
		return resultTable;
	}
	
	// TODO DRINGEND FUNKTIONSF�HIGKEIT TESTEN
	public ArrayList<Object> arrayListOfColumn(int columnNumber) {
		return new ArrayList<Object>( resultTable().get(columnNumber) );
	}
	
	private Vector<Vector<Object>> getRowData(ResultSet resultSet) throws SQLException {
		
		Vector<Vector<Object>> rowData = new Vector<Vector<Object>>();
		
		// Spaltenanzahl ermitteln
		int columns = resultSet.getMetaData().getColumnCount(); 
		
		// Zeileninhalt ermitteln
		while (resultSet.next()) {
			
			// neue Spalte erzeugen
			Vector<Object> row = new Vector<Object>(columns);
			
			// Spalte mit Zeilen f�llen
			for (int i = 1; i <= columns; i++) {
				
				// Zeile mit Werten f�llen
				row.addElement(resultSet.getObject(i));
			}
			// Zeile in Spalte eintragen		
			rowData.addElement(row);
		}
		
		// Ergebnis-Tabelle zur�ckliefern
		return rowData;
	}
}