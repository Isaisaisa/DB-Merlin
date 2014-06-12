package merlin.base;

import static merlin.utils.ConstantElems.*;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Toolkit;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.UnsupportedLookAndFeelException;

import merlin.gui.DatabaseSetup;
import merlin.gui.enums.ExitCode;

public final class Application {
	
	private DbWrapper database;
	public ExitCode exitCode;
	private static Application instance;

	private Application() {
		try {
			loadProperties();
			database = DbWrapper.getInstance();
		} catch (Exception e) {
			e.printStackTrace();
			errorMessageBox(e);
		}
		/*
		 * testings	 area	
		 */

		/*
		 * testings area end
		 */
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
	 */
	public void run() throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
		// ersten Dialog anzeigen
		DatabaseSetup.showDialog();
	}
	/*
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
		shutdown(false);
	}
	
	
	/* PROPERTIES-OBJECT ACCESSORS */
	
	public void saveProp(String property, String value) {
		properties.setProperty(property, value);
	}
	
	public String getProp(String property) {
		String result;
		result = properties.getProperty(property);
		
		// Nullreferenzen vermeiden, falls Schlüssel nicht vorhanden
		if (result == null) { result = ""; }
		
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
		System.out.println("propKey: " + propKey);
		System.out.println("username: " + username);
		System.out.println("password: " + "<hidden>");
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
		return getProp(userPropKey);
//TODO		return getUsername(getLogin(loginDataPropKey));
	}
	
	public String getDbPassword() throws Exception {
		return getProp(pwdPropKey);
//TODO		return getPassword(getLogin(loginDataPropKey));
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
	
	
	public void centerWindow(JFrame jframe) {
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		jframe.setLocation(dim.width/2 - jframe.getSize().width/2, dim.height/2 - jframe.getSize().height/2);
	}
	
	public void closeMerlinYesNo(Frame frame) {
		closeMerlinYesNo(frame, true);
	}
	
	public void closeMerlinYesNo(Frame frame, boolean saveProperties) {
		((JFrame) frame).setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		
		int diagAnswer = JOptionPane.showConfirmDialog(frame, 
                "Möchten Sie MERLIN wirklich beenden?", "MERLIN beenden", 
                JOptionPane.YES_NO_OPTION); 
        
        // Close if user confirmed 
        if (diagAnswer == JOptionPane.YES_OPTION) {
        	try {
        		((JFrame) frame).setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				Application.getInstance().shutdown(saveProperties);
			} catch (Exception e) {
				e.printStackTrace();
			}
        }
	}
}
