package merlin.base;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Vector;

import static merlin.utils.ConstantElems.*;

public final class DbWrapper {
	
	private final String dbURL, dbSID, dbUsername, dbPassword;
	private final int dbPort;
	private Connection connection;
	
	private ResultContainer resultContainer;
	
	/* PRIVATE CONTRUCTOR */
	private DbWrapper(String dbUrl, int dbPort, String dbSID, String dbUsername, String dbPassword) throws ClassNotFoundException, SQLException {
		this.dbURL      = dbUrl;
        this.dbPort     = dbPort;
        this.dbSID      = dbSID;      // Security Identifier
		this.dbUsername = dbUsername;
		this.dbPassword = dbPassword;
		
		this.resultContainer = null;
		
		// Initialisierungen
		initDriver();
		connectToDatabase();
	}
	
	public static DbWrapper valueOf(String dbURL, int dbPort, String dbSID, String dbUsername, String dbPassword) throws ClassNotFoundException, SQLException {
		return new DbWrapper(dbURL, dbPort, dbSID, dbUsername, dbPassword);
	}
	
	public static DbWrapper valueOf(String username, String password) throws ClassNotFoundException, SQLException {
		return valueOf(defaultDbURL, Integer.parseInt(defaultDbPort), defaultDbSID, username, password);
	}
	
	// A-Kennung und Passwort hier
	public static DbWrapper valueOf() throws ClassNotFoundException, SQLException {
		return valueOf("","");
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
	
	public ResultContainer resultContainer() {
		return resultContainer;
	}
	
	public Vector<Vector<Object>> resultTable() {
		return resultContainer().resultTable();
	}
	
	// TODO DRINGEND FUNKTIONSFÄHIGKEIT TESTEN
	public ArrayList<Object> arrayListOfColumn(int columnNumber) {
		return resultContainer().arrayListOfColumn(columnNumber);
	}
	
	/* OPERATIONS */
	
	private boolean initDriver() throws ClassNotFoundException {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			return true;
		} catch (ClassNotFoundException e) {
			System.err.println(e.getMessage());
			//System.err.println("Class oracle.jdbc.driver.OracleDriver not found");
			return false;
		}
	}
	
	private boolean connectToDatabase() throws SQLException {
		try {
			System.out.println("jdbc:oracle:thin:@" + dbURL + ":" + dbPort + ":" + dbSID +  dbUsername() +  dbPassword);
			connection(DriverManager.getConnection("jdbc:oracle:thin:@" + dbURL + ":" + dbPort + ":" + dbSID, dbUsername(), dbPassword));
		    System.out.println("Connection to database '" + dbURL + "' on Port " + dbPort + " established");
		    return true;
		    
		} catch (SQLException e) {
			System.err.println("Connection to database could not be established");
			e.printStackTrace();
			return false;
		}
	}
	

	// Gibt eine Object-Matrix zurück, die aus der Ergebnismenge des Query's berechnet wird.
	// Kann zur Erstellung von Tabellen bzw. Befüllung von JTables u.ä. benutzt werden.
	public Vector<Vector<Object>> getResultOfQuery(String query) throws SQLException {
		return new ResultContainer().processResultSet( sendQuery(query) );
	}
	
	// Query an Datenbank senden
	public ResultSet sendQuery(String query) throws SQLException {
		Statement statement = connection().createStatement();
		statement.closeOnCompletion(); // Statement automatischen schließen lassen, sobald alle referenzierten Ergebnismengen (ResultSets) geschlossen wurden
		return statement.executeQuery(query);
	}
	
	public void sendUpdate(String query) throws SQLException {
		PreparedStatement statement = connection().prepareStatement(query);
		statement.executeUpdate();
	}
	
	public boolean send(String query) throws SQLException {
		PreparedStatement statement = connection().prepareStatement(query);
		return statement.execute();
	}
}
