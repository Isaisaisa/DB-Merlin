package merlin.base;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

public final class DbWrapper {
	
	private final String dbURL, dbSID, dbUsername, dbPassword;
	private final int dbPort;
	private Connection connection;
	
	/* PRIVATE CONTRUCTOR */
	private DbWrapper(String dbUrl, int dbPort, String dbSID, String dbUsername, String dbPassword) throws ClassNotFoundException, SQLException {
		this.dbURL      = dbUrl;
        this.dbPort     = dbPort;
        this.dbSID      = dbSID;      // Security Identifier
		this.dbUsername = dbUsername;
		this.dbPassword = dbPassword;
		
		// Initialisierungen
		initDriver();
		connectToDatabase();
	}
	
	public static DbWrapper instanceOf(String dbURL, int dbPort, String dbSID, String dbUsername, String dbPassword) throws ClassNotFoundException, SQLException {
		return new DbWrapper(dbURL, dbPort, dbSID, dbUsername, dbPassword);
	}
	
	public static DbWrapper instanceOf(String username, String password) throws ClassNotFoundException, SQLException {
		return instanceOf("oracle.informatik.haw-hamburg.de", 1521, "inf09", username, password);
	}
	
	/* ACCESSORS */
	
	public String dbUsername() {
		return dbUsername;
	}
	
	public Connection connection() {
		return connection;
	}
	
	private void connection(Connection connection) {
		this.connection = connection;
	}
	
	
	/* OPERATIONS */
	
	private boolean initDriver() throws ClassNotFoundException {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			return true;
		} catch (ClassNotFoundException e) {
			System.err.println("Class oracle.jdbc.driver.OracleDriver not found");
			return false;
		}
	}
	
	//TODO: eventuell public machen, falls man versuchen will, die Verbindung wiederherzustellen
	private boolean connectToDatabase() throws SQLException {
		try {
			connection(DriverManager.getConnection("jdbc:oracle:thin:@" + dbURL + ":" + dbPort + ":" + dbSID, dbUsername(), dbPassword));
		    System.out.println("Connection to database '" + dbURL + "' on Port " + dbPort + " established");
		    return true;
		    
		} catch (SQLException e) {
			System.err.println("Connection to database could not be established");
			e.printStackTrace();
			return false;
		}
	}
	
	/* Methode, um Query zu senden und anschließend das Ergebnis in Vektoren aufzuarbeiten - in einem Aufruf. */
//	public Vector<Vector<Object>> getResultOfQuery(String query) throws SQLException {
//		try {
//			return processResult(sendQuery(query));
//		} catch (Exception e) {
//			System.err.println("The query could not be processed.");
//			e.printStackTrace();
//			
//			/* return empty table at least */
//			return new Vector<Vector<Object>>();
//		}
//	}
	
//	public Vector<Vector<Object>> getResultOfQuery(String query) {
//		try {
//			return processResult(sendQuery(query));
//		} catch (Exception e) {
//			System.err.println("The query could not be processed.");
//			e.printStackTrace();
//			
//			/* return empty table at least */
//			return new Vector<Vector<Object>>();
//		}
//	}
	
	/* Query an Datenbank senden */
	public ResultSet sendQuery(String query) throws SQLException {
		Statement statement = connection().createStatement();
		ResultSet resultSet;
		
		resultSet = statement.executeQuery(query);
		return resultSet;
	}
        
        public Vector<String> getColumnNames(ResultSet resultSet) throws SQLException {
            return getColumnNames(resultSet.getMetaData());
        }
        
        public Vector<String> getColumnNames(ResultSetMetaData metaData) throws SQLException {
            Vector<String> columnNames = new Vector<String>();
            int columns = metaData.getColumnCount();
        
            /* Spaltennamen ermitteln */
            for (int i = 1; i <= columns; i++) {
                    String colname = metaData.getColumnName(i);
                    columnNames.addElement(colname);
            }
            return columnNames;
        }
    
	/* Das Ergebnis eines Queries in Vektoren schreiben*/
	
	/* processResult BASE METHOD */
	public Vector<Vector<Object>> processResult(ResultSet resultSet) throws SQLException {
		
		ResultSetMetaData metaData = resultSet.getMetaData();
		Vector<Vector<Object>> resultTable = new Vector<Vector<Object>>();
//                Vector<String> columnNames = new Vector<String>();
		int columns = metaData.getColumnCount();
//        
//		/* Spaltennamen ermitteln */
//		for (int i = 1; i <= columns; i++) {
//			String colname = metaData.getColumnName(i);
//			columnNames.addElement(colname);
//		}
                
                
		
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
			resultTable.addElement(row);
		}
		
		/* Ergebnis-Tabelle zurückliefern */
		return resultTable;
	}
}
