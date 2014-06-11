package merlin.base;

import static merlin.utils.ConstantElems.defaultDbPort;
import static merlin.utils.ConstantElems.defaultDbSID;
import static merlin.utils.ConstantElems.defaultDbURL;

import java.io.PrintStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;





import java.util.Vector;

import merlin.base.interfaces.DbWrapperInterface;
import merlin.logic.exception.*;

public final class DbWrapper implements DbWrapperInterface {

	
	private static DbWrapper 	instance;
	private Connection 		 	connection;
	private boolean			 	isDriverInitialized;
	
	private String				dbURL  = defaultDbURL;
	private String  			dbPort = defaultDbPort;
	private String 				dbSID  = defaultDbSID;
	private String				dbUsername;
	private byte[]  			dbPassword; // setter auto-encrypts and getter auto-decrypts
	
	private Hashtable<String, PreparedStatement> preparedStatements;

	
	// PRIVATE CONSTRUCTOR USED BY SINGLETON PATTERN: DbWrapper nur instanziieren und Treiber initialisieren
	private DbWrapper() throws ClassNotFoundException {
//		this.resultContainer = null;
		initDriver();
		prepareStatements();
	}
	
	// SINGLETON PATTERN INIT
	public static DbWrapper getInstance() throws ClassNotFoundException {
		if (instance == null) {
			instance = new DbWrapper();
		}
		return instance;
	}
	
	private void prepareStatements() {
		System.out.println("Preparing statements...");
		
		
	}
	

	 ///////////////
	// ACCESSORS //
	public void setConnectionData(String dbURL, String dbPort, String dbSID, String dbUsername, char[] dbPassword) throws Exception {
		if (areConnectionDataValid(dbURL, dbPort, dbSID)) {
			this.dbURL = dbURL;
			this.dbPort = dbPort;
			this.dbSID = dbSID;
		} else {
			throw new InvalidConnectionDataException();
		}
		if (dbUsername != null && dbPassword != null) {
			setLoginData(dbUsername, dbPassword);
		}
	}
	
	public void setConnectionData(String dbURL, String dbPort, String dbSID) throws Exception {
		setConnectionData(dbURL, dbPort, dbSID, null, null);
	}

	public void setLoginData(String dbUsername, String dbPassword) throws Exception {
		dbUsername(dbUsername);
		dbPassword(dbPassword);
	}

	public void setLoginData(String dbUsername, char[] dbPassword) throws Exception {
		setLoginData(dbUsername, new String(dbPassword));
	}

	/* ACCESSORS */

	public String dbUsername() {
		return dbUsername;
	}

	private void dbUsername(String dbUsername) {
		this.dbUsername = dbUsername;
	}

	private void dbPassword(String dbPassword) throws Exception {
		this.dbPassword = AES.encrypt(dbPassword);
	}

//	private void dbPassword(char[] dbPassword) throws Exception {
//		dbPassword(new String(dbPassword));
//	}

	private String dbPassword() throws Exception {
		return AES.decrypt(dbPassword);
	}

	private Connection connection() throws SQLException {
		if (connection != null && connection.isClosed()) {
			return connection; 
		} else {
			return connection;
		}
	}
	
	private void connection(Connection connection) {
		this.connection = connection;
		System.out.println("Connection to database '" + dbURL + "' on Port " + dbPort + " established");
	}
	
	/*
	 * 
	 * 
	 * 
	 * 
	 */
	public ArrayList<HashMap<String, Object>> resultSetToArrayList(ResultSet rs) throws SQLException {
		System.err.println("Beginne ResultSet Verarbeitung...");

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

		System.err.println("ResultSet verarbeitet!");

		return list;
	}
	
	public Vector<Vector<Object>> getVectorOf(ResultSet resultSet) throws SQLException {
		Vector<String> columnNames = new Vector<String>();
		Vector<Vector<Object>> result = new Vector<Vector<Object>>();
		
		ResultSetMetaData metaData = resultSet.getMetaData();
		int columns = metaData.getColumnCount();
		
		for (int i = 1; i <= columns; i++) {
			String colname = metaData.getColumnName(i);
			columnNames.addElement( colname );
		}
		// Zeileninhalt ermitteln
		while (resultSet.next()) {
			Vector<Object> row = new Vector<Object>(columns);
			for (int i = 1; i <= columns; i++) {
				row.addElement(resultSet.getObject(i));
			}
			result.addElement(row);
		}

		System.err.println("ResultSet verarbeitet!");
		
		return result;
	}
	
	
	public List<String> getListOfQuery(String query) throws Exception {
		ResultSet rs = sendQuery(query);
		return resultSetToList(rs);
	}
	
	
	// kompakter Accessor, der sich der ersten Spalte bedient
	public List<String> resultSetToList(ResultSet resultSet) {
		return resultSetToList(resultSet, 1);
	}
	
	// bedient sich der <column>. Spalte des Resultsets und fügt sie der resultList hinzu
	public List<String> resultSetToList(ResultSet resultSet, int column) {
		List<String> resultList = new ArrayList<String>();
		
		try {
			while (resultSet.next()) {
				try {
					resultList.add(resultSet.getString(column));
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return resultList;
	}
	
	
	// TODO UNFINISHED!!
	public List<HashMap<String, Object>> processResultSet(ResultSet resultSet) throws SQLException {
		List<HashMap<String, Object>> resultTable = resultSetToArrayList(resultSet);
		resultSet.close();
		return resultTable;
	}
	
	
	
	/*
	 * 
	 * 
	 * 
	 * 
	 * 
	 */
	
	 ////////////////
	// PREDICATES //
	public boolean areDataValid() throws InvalidConnectionDataException, InvalidLoginDataException {
		return areConnectionDataValid() && areLoginDataValid();
	}
	
	public boolean areConnectionDataValid() throws InvalidConnectionDataException {
		if (areConnectionDataValid(dbURL, dbPort, dbSID)) {
			
		} else {
			throw new InvalidConnectionDataException();
		}
		
		return true;
	}
	
	public boolean areConnectionDataValid(String dbURL, String dbPort, String dbSID) throws InvalidConnectionDataException {
		if (dbURL != null && dbPort != null && dbSID != null) {
			return true;
		} else {
			throw new InvalidConnectionDataException();
		}
	}
	
	public boolean areLoginDataValid() throws InvalidLoginDataException {
		if (dbUsername != null && dbPassword.length > 0) {
			return true;
		} else {
			throw new InvalidLoginDataException();
		}
	}
	
	public boolean isConnectionValid() throws SQLException {
		if (connection == null) {
			return false;
		} else {
			return !(connection.isClosed());
		}
	}
	
	public boolean isConnectionClosed() throws SQLException {
		if (connection == null) {
			return false;
		} else {
			return connection.isClosed();
		}
	}
	
	public boolean isConnectionReadOnly() throws SQLException {
		if (connection == null) {
			return false;
		} else {
			return connection.isClosed();
		}
	}
	
	 ///////////////////////////////////
	// CONNECTION RELATED OPERATIONS //
	private void initDriver() throws ClassNotFoundException {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			isDriverInitialized = true;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.err.println(e.getMessage());
			System.err.println("Cannot initialize driver: Class oracle.jdbc.driver.OracleDriver not found");
			isDriverInitialized = false;
//			throws ClassNotFoundException;  // wird trotzdem noch von initDriver() der Fehler nach außerdem geworfen, wenn try catch angewandt wird?
		}
	}
	
	public void connect() throws Exception {
		// Sicherstellen, dass Datenbanktreiber initialisiert wurde
		// (falls nicht => nachträglich initialisieren)
		if (!isDriverInitialized) {
			try {
				initDriver();
			}
			catch (ClassNotFoundException e) {
				e.printStackTrace();
				System.err.println(e.getMessage());
			}
		}
		
		// Datenkonsistenz prüfen
		try {
			if (areDataValid()) {
				try {
					debugPrint("jdbc:oracle:thin:@" + dbURL + ":" + dbPort + ":" + dbSID +  dbUsername() +  dbPassword);
					connection( DriverManager.getConnection("jdbc:oracle:thin:@" + dbURL + ":" + dbPort + ":" + dbSID, dbUsername(), dbPassword()) );
				} catch (SQLException e) {
					System.err.println("Connection to database could not be established");
					e.printStackTrace();
					throw new SQLException();
				}
			}
		} catch (InvalidConnectionDataException e) {
			e.printStackTrace();
			System.err.println("Invalid connection data.");
			System.err.println("URL: " + dbURL + "\n"
							 + "Port: " + dbPort + "\n" 
							 + "SID: " + dbSID);
		} catch (InvalidLoginDataException e) {
			e.printStackTrace();
			System.err.println("Invalid login data.");
			System.err.println("Username: " + dbUsername);
			//System.err.println("Pwd: " + dbPassword());
		}
	}
	
	public void close() throws SQLException {
		if (connection != null) {
			connection.close();
		}
	}
	
	 ///////////////////////////
	// DATA(BASE) OPERATIONS //
   //-----------------------//
	// Gibt eine Object-Matrix zurück, die aus der Ergebnismenge des Query's berechnet wird.
	// Kann zur Erstellung von Tabellen bzw. Befüllung von JTables u.ä. benutzt werden.
	public List<HashMap<String, Object>> getResultOfQuery(String query) throws Exception {
		ResultSet resultSet = sendQuery(query);
		List<HashMap<String, Object>> result = processResultSet( resultSet );
		resultSet.close();
		return result;
	}
	
	
//	public Vector<Vector<Object>> getResultOfQuery(String query) throws Exception {
//		return new ResultContainer().processResultSet( sendQuery(query) );
//	}
	
	private boolean preSendChecks() throws InvalidConnectionDataException, InvalidLoginDataException, SQLException, Exception {
		if (!isConnectionValid() && !areDataValid()) {
			throw new Exception("The attempt to connect to database, before sending, failed.");
		}
		return true;
	}
	
	// Query an Datenbank senden
	public ResultSet sendQuery(String query) throws Exception {
//		if (preSendChecks()) {
//			connect();
//		}
		
		Statement statement = connection().createStatement();
//		statement.closeOnCompletion(); // Statement automatischen schließen lassen, sobald alle referenzierten Ergebnismengen (ResultSets) geschlossen wurden
		return statement.executeQuery(query);
	}
	
	// Update an Datenbank senden
	public void sendUpdate(String query) throws SQLException {
		PreparedStatement statement = connection().prepareStatement(query); // FRAGE: Warum ein PreparedStatement statt normalem Statement? 
		statement.executeUpdate();
	}
	
	private void debugPrint(String debugThis, PrintStream ps) {
		ps.println("DbWrapper: " + debugThis);
	}
	
	private void debugPrint(String debugThis) {
		debugPrint(debugThis, System.out);
	}
}
