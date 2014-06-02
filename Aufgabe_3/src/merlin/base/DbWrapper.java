package merlin.base;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

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
		
		this.resultContainer = new ResultContainer();
		
		// Initialisierungen
		initDriver();
		connectToDatabase();
	}
	
	public static DbWrapper valueOf(String dbURL, int dbPort, String dbSID, String dbUsername, String dbPassword) throws ClassNotFoundException, SQLException {
		return new DbWrapper(dbURL, dbPort, dbSID, dbUsername, dbPassword);
	}
	
	public static DbWrapper valueOf(String username, String password) throws ClassNotFoundException, SQLException {
		return valueOf("oracle.informatik.haw-hamburg.de", 1521, "inf09", username, password);
	}
	//A-Kennung und Passwort hier
	public static DbWrapper valueOf() throws ClassNotFoundException, SQLException {
		return valueOf("","" );
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
	

	/* GIBT DIE ERGEBNISTABELLE EINE QUERY'S ZURÜCK (columnNames müssen separat per DbWrapper.columnNames() geholt werden);
	 * Man übergibt getResultOfQuery einen String in Form eines Queries,
	 * der Query wird delegiert an sendQuery, um die Anfrage an die Datenbank zu senden.
	 * Das Ergebnis (ResultSet) wird innerhalb der Methode resultSet des ResultContainer's
	 * weiterverarbeitet, dass am Ende alle nötigen Informationen zur Erstellung einer Tabelle
	 * vorhanden sind. Zusätzlich gibt die Methode eine Matrix rowData zurück,
	 * die eine Tabelle (ohne Spaltenüberschriften repräsentiert).
	 */
	public Vector<Vector<Object>> getResultOfQuery(String query) throws SQLException {
		return resultContainer().resultSet(sendQuery(query));
	}
	
	
	public ResultContainer resultContainer() {
		return resultContainer;
	}
	
	/* Query an Datenbank senden */
	public ResultSet sendQuery(String query) throws SQLException {
		Statement statement = connection().createStatement();
		ResultSet resultSet = statement.executeQuery(query);
		return resultSet;
	}
	
	public void sendUpdate(String query) throws SQLException {
		PreparedStatement statement = connection().prepareStatement(query);
		statement.executeUpdate();
	}
	
	public boolean send(String query) throws SQLException {
		PreparedStatement statement = connection().prepareStatement(query);
		return statement.execute();
	}
	
	public Vector<String> columnNames() {
		return resultContainer.columnNames();
	}
	
	public ResultSetMetaData resultSetMetaData() {
		return resultContainer.resultSetMetaData();
	}
}
