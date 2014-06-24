package merlin.base;

import static merlin.utils.ConstantElems.defaultDbPort;
import static merlin.utils.ConstantElems.defaultDbSID;
import static merlin.utils.ConstantElems.defaultDbURL;
import static merlin.utils.ConstantElems.showMsgBox;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;

import javax.swing.DefaultComboBoxModel;
import javax.swing.table.DefaultTableModel;

import merlin.base.interfaces.DbWrapperInterface;
import merlin.logic.exception.InvalidConnectionDataException;
import merlin.logic.exception.InvalidLoginDataException;
import merlin.utils.DefaultTableModelNoEdit;

public final class DbWrapper implements DbWrapperInterface {

	
	private static DbWrapper 	instance;
	private Connection 		 	connection;
//	private DatabaseMetaData	databaseMetaData;
	private boolean			 	isDriverInitialized;
	
	private String				dbURL  = defaultDbURL;
	private String  			dbPort = defaultDbPort;
	private String 				dbSID  = defaultDbSID;
	private String				dbUsername;
	private byte[]  			dbPassword; // setter auto-encrypts and getter auto-decrypts
	
	// PRIVATE CONSTRUCTOR USED BY SINGLETON PATTERN: DbWrapper nur instanziieren und Treiber initialisieren
	private DbWrapper() throws ClassNotFoundException {
		initDriver();
	}
	
	// SINGLETON PATTERN INIT
	public static DbWrapper getInstance() throws ClassNotFoundException {
		if (instance == null) {
			instance = new DbWrapper();
		}
		return instance;
	}
	
	public PreparedStatement prepareStatement(String query) throws SQLException {
		return connection().prepareStatement(query);
	}
	

	 ///////////////
	// ACCESSORS //
	public void setConnectionData(String dbURL, String dbPort, String dbSID, String dbUsername, char[] dbPassword) throws InvalidConnectionDataException, Exception {
		if (areConnectionDataValid(dbURL, dbPort, dbSID)) {
			this.dbURL = dbURL;
			this.dbPort = dbPort;
			this.dbSID = dbSID;
		} else {
			throw new InvalidConnectionDataException();
		}

		if (dbUsername != null && dbPassword != null) {
			setLoginData(dbUsername, new String(dbPassword));
		}
	}
	
	public void setConnectionData(String dbURL, String dbPort, String dbSID) throws Exception {
		setConnectionData(dbURL, dbPort, dbSID, null, null);
	}

	public void setLoginData(String dbUsername, String dbPassword) throws Exception {
		this.dbUsername(dbUsername);
		this.dbPassword(dbPassword);
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
		// TODO debug, whats going on
//		System.out.println("DbWrapper#dbPassword(): encrypted PW: " + this.dbPassword);
	}

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
	
	/**
	 * @throws SQLException *************************************************************************************************************/
	
	public boolean hasResults(Vector<?> resultVector) throws SQLException {
		return resultVector.size() > 0;
	}
	
	// Gibt einen einzigen Wert zurück ==> erster Attributwert des ersten Tupels des Abfrageergebnisses 
	public String getSingleValue(ResultSet rs) throws SQLException {
		String result = getList(rs).get(0);
		try {
			rs.getStatement().close(); // Automatisches Schließen des Statements nach Verarbeitung des ResultSets erwirken
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("DbWrapper#getSingleValue(ResultSet rs): Statement konnte nicht geschlossen werden.");
			throw e;
		} 
		return result;
	}
	
	public String getSingleValue(PreparedStatement ps) throws SQLException {
		return getSingleValue( sendQuery(ps) );
	}
	
	
	public String getSingleValue(String query) throws SQLException {
		return getSingleValue( sendQuery(query) );
	}
	
	
	public Vector<Vector<String>> getResultVector(ResultSet resultSet) throws SQLException {
		Vector<Vector<String>> result = new Vector<Vector<String>>();
		
		ResultSetMetaData metaData = resultSet.getMetaData();
		int columns = metaData.getColumnCount();
		
		// Zeileninhalt ermitteln
		while (resultSet.next()) {
			Vector<String> row = new Vector<String>(columns);
			for (int i = 1; i <= columns; i++) {
				row.addElement(resultSet.getString(i));
			}
			result.addElement(row);
		}

		return result;
	}
	
	public Vector<String> getResultVector(ResultSet resultSet, int column) {
		try {
			Vector<Vector<String>> resultVector = getResultVector(resultSet);
			System.out.println(resultVector.size());
			
			return null; 
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("DbWrapper#getResultVector/2: Fehler \"" + e.getMessage() + "\" - leerer Vector wird zurückgegeben.");
			return new Vector<String>();
		}
	}
	
	
	public Vector<String> getColumnNames(ResultSet resultSet) throws SQLException {
		Vector<String> columnNames = new Vector<String>();
		
		ResultSetMetaData metaData = resultSet.getMetaData();
		int columns = metaData.getColumnCount();
		
		for (int i = 1; i <= columns; i++) {
			String colname = metaData.getColumnName(i);
			columnNames.addElement( colname );
		}
		
		return columnNames;
	}
	
	// Direkteste Methode, um direkt von einem Query das volle Ergebnis in Form
	// eines TableModels zu erhalten
	public DefaultTableModel getTableModelOfQuery (String query) {
		try {
			ResultSet resultSet = sendQuery(query);
			DefaultTableModel resultTableModel = getTableModel(resultSet);
			resultSet.getStatement().close(); // automatisches Schließen, da ResultSet nur für diesen Aufruf gebraucht wird.
			return resultTableModel;
		} catch (Exception e) {
			e.printStackTrace();
			// showMsgBox(e); // TODO im Auge behalten
			return new DefaultTableModel();
		}
	}
	
	public DefaultTableModel getTableModel(ResultSet resultSet) {
		try {
			return new DefaultTableModelNoEdit(getResultVector(resultSet), getColumnNames(resultSet));
		} catch (SQLException e) {
			e.printStackTrace();
			System.err.println(e.getMessage());
			return new DefaultTableModelNoEdit();
		} 
	}
	
	
	public DefaultTableModel getTableModel(Vector<Vector<String>> resultVector, Vector<String> columnNames) {
		return new DefaultTableModel(resultVector, columnNames);
	}
	
	
	public DefaultComboBoxModel<Object> getComboBoxModel(Vector<Object> input) {
		return new DefaultComboBoxModel<Object>(input);
	}
	
//	public List<String> getSchemas() throws SQLException {
//		return getList(databaseMetaData.getSchemas());
//	}
	
//	public boolean areSchemasValid() throws SQLException {
//		if (databaseMetaData == null) { return false; }
//		
//		List<String> schemas = new ArrayList<String>();
//		schemas.add("Vogelart");
//		schemas.add("Birdwatcher");
//		schemas.add("Beobachtunsgebiet");
//		schemas.add("beobachtet");
//		schemas.add("kommtVor");
//		
//		return getList(databaseMetaData.getSchemas()).containsAll(schemas);
//	}
	
	public List<String> getListOfQuery(String query) throws Exception {
		ResultSet resultSet = sendQuery(query);
		List<String> resultList = getList(resultSet);
		// TODO: Validieren, dass das Schließen des Statements nach Abarbeitung zu keinen Fehlern führt.
		resultSet.getStatement().close();
		return resultList;
	}
	
	
	// kompakter Accessor, der sich der ersten Spalte bedient
	public List<String> getList(ResultSet resultSet) {
		return getList(resultSet, 1);
	}
	
	// bedient sich der <column>. Spalte des Resultsets und fügt sie der resultList hinzu
	public List<String> getList(ResultSet resultSet, int column) {
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
			showMsgBox(e, "Cannot initialize driver: Class oracle.jdbc.driver.OracleDriver not found"); // TODO nicht validiert, dass problemlos läuft
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
				showMsgBox(e); // TODO nicht validiert, dass problemlos läuft
			}
		}
		
		// Datenkonsistenz prüfen
		try {
			if (areDataValid()) {
				try {
					System.out.println("jdbc:oracle:thin:@" + dbURL + ":" + dbPort + ":" + dbSID +  dbUsername() +  dbPassword);
					connection( DriverManager.getConnection("jdbc:oracle:thin:@" + dbURL + ":" + dbPort + ":" + dbSID, dbUsername(), dbPassword()) );
				} catch (SQLException e) {
					showMsgBox(e, "DbWrapper#connect: Connection to database could not be established"); // TODO nicht validiert, dass problemlos läuft
					e.printStackTrace();
					throw e;
				}
			}
		} catch (InvalidConnectionDataException e) {
			showMsgBox(e, "Invalid connection data.\n" + "URL: " + dbURL + "\n" + "Port: " + dbPort + "\n" + "SID: " + dbSID); // TODO nicht validiert, dass problemlos läuft
			e.printStackTrace();
//			System.err.println("Invalid connection data.");
//			System.err.println("URL: " + dbURL + "\n"
//							 + "Port: " + dbPort + "\n" 
//							 + "SID: " + dbSID);
		} catch (InvalidLoginDataException e) {
			showMsgBox(e, "Invalid login data.\n" + "Username: " + dbUsername); // TODO nicht validiert, dass problemlos läuft
			e.printStackTrace();
//			System.err.println("Invalid login data.");
//			System.err.println("Username: " + dbUsername);
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
	private boolean preSendChecks() throws InvalidConnectionDataException, InvalidLoginDataException, SQLException, Exception {
		if (!isConnectionValid() && !areDataValid()) {
			throw new Exception("DbWrapper#preSendChecks(): The attempt to connect to database, before sending, failed.");
		}
		return true;
	}
	
	// Query an Datenbank senden
	
	public ResultSet sendQuery(String query) throws SQLException {
		try {
			Statement statement = connection().createStatement();
			return statement.executeQuery(query);
		} catch (SQLException e) {
			e.printStackTrace();
			showMsgBox(e);
			throw e;
		}
	}
	
	public ResultSet sendQuery(PreparedStatement prepStatement) throws SQLException {
		try {
			return prepStatement.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
			showMsgBox(e);
			throw e;
		}
	}

	public void sendUpdate(String query) throws SQLException {
		try {
			connection().createStatement().executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
			showMsgBox(e);
			throw e;
		}
	}
	
	public void sendUpdate(PreparedStatement prepStatement) throws SQLException {
		try {
			prepStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			showMsgBox(e);
			throw e;
		}
	}
}
