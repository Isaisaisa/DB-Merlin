package merlin.base;

import static merlin.utils.ConstantElems.*;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Toolkit;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Hashtable;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.UnsupportedLookAndFeelException;

import merlin.gui.DatabaseSetup;
import merlin.gui.enums.ExitCode;

public final class Application {
	
	private DbWrapper database;
	public ExitCode exitCode;
	private static Application instance;

	private Application() throws Exception {
		loadProperties();
		putPropDefaults();
		ensurePropConsistency();
		
		String ret = getEncProp(loginDataPropKey);
		System.out.println(ret);
		
		database = DbWrapper.getInstance();
	}

	public static Application getInstance() throws Exception {
		if (instance == null) {
			instance = new Application();
		}
		return instance;
	}
	
	/*
	 * 
	 * 
	 * 
	 * 
	 * 
	 */
	public void run() throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
		DatabaseSetup.showDialog();
		
//		System.out.println("hash map");
//		
//		System.out.println(propDefaults);
//		
//		System.out.println(getProp(dbURLPropKey));
//		System.out.println(getProp(dbPortPropKey));
//		System.out.println(getProp(dbSIDPropKey));
//		System.out.println(getProp(rememberLoginPropKey));
//		System.out.println(getProp(loginDataPropKey));
//		System.out.println(getProp(loginDataBirdwatcherPropKey));
//		
//		shutdown();
	}
	/*
	 * 
	 * 
	 * 
	 * 
	 * 
	 */
	
	public void shutdown(boolean saveProperties) {
		// anything that remains to be shut down properly - will be shut down here
		// zb. datenbank verbindung trennen
		
		try {
			if (database != null && !database.isConnectionClosed()) {
				try {
					database.close();
				} catch (SQLException e) {
					e.printStackTrace();
					System.err.println(e.getMessage());
					System.err.println(e.getSQLState());
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.err.println("SQL Exception on shutdown.");
		} finally {
			database = null;
		}
		
		if (saveProperties) {
			try {
				saveProperties();
			} catch (IOException e) {
				e.printStackTrace();
				System.err.println("Properties cannot be saved on shutdown. Unsaved changes are being lost.");
			}
		}
		
		System.err.println("SYSTEM SHUTDOWN");
		/*****/ System.exit(0); /*****/
	}
	
	public void shutdown() {
		shutdown(true);
	}
	
	
	/* PROPERTIES-OBJECT ACCESSORS */
	
	private void putPropDefaults() {
		Hashtable<String, String> pd = propDefaults;
		
		pd.put(dbURLPropKey, "oracle.informatik.haw-hamburg.de");
		pd.put(dbPortPropKey, "1521");
		pd.put(dbSIDPropKey, "inf09");
		pd.put(rememberLoginPropKey, "false");
		pd.put(loginDataPropKey, "");
		pd.put(loginDataBirdwatcherPropKey, "");
	}
	
	public void ensurePropConsistency() {
		Hashtable<String, String> pd = propDefaults;
		
		if (getProp(dbURLPropKey) == null) {saveProp(dbURLPropKey, pd.get(dbURLPropKey));}
		if (getProp(dbPortPropKey) == null) {saveProp(dbPortPropKey, pd.get(dbPortPropKey));}
		if (getProp(dbSIDPropKey) == null) {saveProp(dbSIDPropKey, pd.get(dbSIDPropKey));}
		if (getProp(rememberLoginPropKey) == null) {saveProp(rememberLoginPropKey, pd.get(rememberLoginPropKey));}
		if (getProp(loginDataPropKey) == null) {saveProp(loginDataPropKey, pd.get(loginDataPropKey));}
		if (getProp(loginDataBirdwatcherPropKey) == null) {saveProp(loginDataBirdwatcherPropKey, pd.get(loginDataPropKey));}
	}
	
	public void saveProp(String property, String value) {
		properties.setProperty(property, value);
	}
	
	public void saveProp(String property, Integer value) {
		saveProp(property, value.toString());
	}
	
	public String getProp(String property) {
		String result = properties.getProperty(property);
		return result;
	}
		
	
	
	public void saveEncProp(String property, String value) throws Exception {
		saveProp(property, AES.encrypt(value).toString());
	}
	
	public String getEncProp(String property) throws Exception {
		byte[] cipher = getProp(property).getBytes();
		return AES.decrypt(cipher);
	}
	
	
	public void saveLogin(String propKey, String username, String password) throws Exception {
		saveEncProp(propKey, username + loginDataSplitString + password);
	}
	
	public String[] getLogin(String encLoginData) throws Exception {
		// Login Daten String entschlüsseln
		String loginData   = getEncProp(encLoginData);

		if (!loginData.isEmpty()) {
			String decUsername = loginData.substring(0, loginData.indexOf(loginDataSplitString));
			String decPwd	   = loginData.substring(decUsername.length()).replaceAll(loginDataSplitString, "");
			
			String result[] = {decUsername, decPwd};
			return result;
		} else {
			String result[] = {"",""};
			return result;
		}
	}
	
	public String getDbUsername() throws Exception {
		return getUsername(getLogin(loginDataPropKey));
	}
	
	public String getDbPassword() throws Exception {
		return getPassword(getLogin(loginDataPropKey));
	}
	
	public String getUsername(String[] loginData) {
		return loginData[0];
	}

	public String getPassword(String[] loginData) {
		return loginData[1]; 
	}
	
	
	public void saveDatabaseLogin(String username, String password) throws Exception { 
		saveLogin(loginDataPropKey, username, password);
	}
	
	public String[] getDatabaseLogin() throws Exception {
		return getLogin(loginDataPropKey);
	}
	
	
	public void saveConnectionData(String dbURL, String dbPort, String dbSID) {
		saveProp(dbURLPropKey,  dbURL);
		saveProp(dbPortPropKey, dbPort);
		saveProp(dbSIDPropKey,  dbSID);
	}
	
	public String[] getConnectionData() {
		String[] result = {getProp(dbURLPropKey), getProp(dbPortPropKey), getProp(dbSIDPropKey)};
		return result;
	}
	
	public String getDbURL() {
		return getConnectionData()[0];
	}
	
	public String getDbPort() {
		return getConnectionData()[1];
	}
	
	public String getDbSID() {
		return getConnectionData()[2];
	}
	
	
	public void saveBirdwatcherLogin(String username, String password) throws Exception {
		saveLogin(loginDataBirdwatcherPropKey, username, password);
	}

	public String[] getBirdwatcherLogin() throws Exception {
		return getLogin(loginDataBirdwatcherPropKey);
	}
	
	
	public DbWrapper database() {
		return this.database;
	}

	
	public void initDatabaseCompletely(String dbURL, String dbPort, String dbSID, String dbUsername, String dbPassword) throws Exception {
		database = DbWrapper.getInstance();
		database.setConnectionData(dbURL, dbPort, dbSID);
		database.setLoginData(dbUsername, dbPassword);
	}
	
	
	public void closeMerlinYesNo(Frame frame) {
		((JFrame) frame).setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		
		int diagAnswer = JOptionPane.showConfirmDialog(frame, 
                "Möchten Sie MERLIN wirklich beenden?", "MERLIN beenden", 
                JOptionPane.YES_NO_OPTION); 
        
        // Close if user confirmed 
        if (diagAnswer == JOptionPane.YES_OPTION) {
        	try {
        		((JFrame) frame).setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				Application.getInstance().shutdown(false);
			} catch (Exception e) {
				e.printStackTrace();
			}
        }
	}

}
