package merlin.base;

import static merlin.gui.enums.ExitCode.*;
import static merlin.utils.ConstantElems.*;

import java.io.IOException;
import java.sql.SQLException;

import javax.swing.UnsupportedLookAndFeelException;

import merlin.gui.DatabaseSetup;
import merlin.gui.MerlinLogin;
import merlin.gui.MerlinMainWindow;
import merlin.gui.enums.ExitCode;

public final class Application {
	
	public DbWrapper database;
	public ExitCode exitCode;
	private static Application instance;

	private Application() throws ClassNotFoundException, InstantiationException, IllegalAccessException, IOException, UnsupportedLookAndFeelException {
		
	}

	public static Application getInstance() throws ClassNotFoundException, InstantiationException, IllegalAccessException, IOException, UnsupportedLookAndFeelException {
		if (instance == null) {
			instance = new Application();
		}
		return instance;
	}
	
	public void run() throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
		// Initialisierungen
		
		loadProperties();
		database = DbWrapper.getInstance();
		
		exitCode = null;
		DatabaseSetup.showDialog();
		System.out.println("ExitCode = " + exitCode);
		
		if (exitCode == QUIT_DIALOG || exitCode == CANCEL_BUTTON_PUSHED) {
			shutdown();
		} else if (exitCode == OK_BUTTON_PUSHED) {
			// Datenbankeinrichtung wurde bestätigt
			// GUI - Login Screen anzeigen
			exitCode = null;
			System.out.println("Exitcode auf null setzen --> " + exitCode);
			MerlinLogin.main();
			System.out.println("ExitCode = " + exitCode);
			
			if (exitCode == LOGIN_BUTTON_PUSHED) {
				exitCode = MerlinMainWindow.main();
			} else if (exitCode == REGISTER_BUTTON_PUSHED) {
				
			}
		}
//		exitCode = DatabaseSetup.showDialog();
//		System.out.println("ExitCode = " + exitCode);
//		if (exitCode == QUIT_DIALOG || exitCode == CANCEL_BUTTON_PUSHED) {
//			shutdown();
//		} else if (exitCode == OK_BUTTON_PUSHED) {
//			// Datenbankeinrichtung wurde bestätigt
//			// GUI - Login Screen anzeigen
//			exitCode = MerlinLogin.main();
//			
//			if (exitCode == LOGIN_BUTTON_PUSHED) {
//				exitCode = MerlinMainWindow.main();
//			} else if (exitCode == REGISTER_BUTTON_PUSHED) {
//				
//			}
//		}
		
		System.out.println("ups. doch schon hier? Das schreit nach: korrekte Modalitäten Behandlungen!");
		
	}
	
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
		}
		
		// finally
		database = null;
		
		if (saveProperties) {
			try {
				saveProperties();
			} catch (IOException e) {
				e.printStackTrace();
				System.err.println("Properties cannot be saved on shutdown. Unsaved changes are being lost.");
			}
		}
		
		System.err.println("SYSTEM SHUTDOWN");
		System.exit(0);
	}
	
	public void shutdown() {
		shutdown(false);
	}
	
	
	/* PROPERTIES-OBJECT ACCESSORS */
	
	public void saveProp(String property, String value) {
		properties.setProperty(property, value);
	}
	
	public void saveProp(String property, Integer value) {
		saveProp(property, value.toString());
	}
	
	public String getProp(String property) {
		return properties.getProperty(property);
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
		String loginData   = getEncProp(encLoginData);		
		String decUsername = loginData.substring(0, loginData.indexOf(loginDataSplitString));
		String decPwd	   = loginData.substring(decUsername.length()).replaceAll(loginDataSplitString, "");
		
		String[] result = {decUsername, decPwd};
		
		return result;
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
	
	public String getURL(String[] connectionData) {
		return connectionData[0];
	}
	
	public String getPort(String[] connectionData) {
		return connectionData[1];
	}
	
	public String getSID(String[] connectionData) {
		return connectionData[2];
	}
	
	
	public void saveBirdwatcherLogin(String username, String password) throws Exception {
		saveLogin(loginDataBirdwatcherPropKey, username, password);
	}

	public String[] getBirdwatcherLogin() throws Exception {
		return getLogin(loginDataBirdwatcherPropKey);
	}

	
	public void initDatabaseCompletely(String dbURL, String dbPort, String dbSID, String dbUsername, String dbPassword) throws Exception {
		database = DbWrapper.getInstance();
		database.setConnectionData(dbURL, dbPort, dbSID);
		database.setLoginData(dbUsername, dbPassword);
	}

}
