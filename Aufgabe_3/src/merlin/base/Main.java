package merlin.base;

import java.io.FileInputStream;
import java.io.IOException;

import javax.swing.UnsupportedLookAndFeelException;

import merlin.gui.*;
import merlin.gui.enums.ExitCode;
import static merlin.gui.enums.ExitCode.*;
import static merlin.utils.ConstantElems.*;

public class Main {
	
	// TODO DbWrapper eventuell auslagern, falls Zugriffsprobleme entstehen
	static DbWrapper database;
	private static ExitCode exitCode;
	
	public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException, IOException {
		
		/* Initialisierungen */
		
		/* Konfigurationsdatei anlegen, falls noch nicht vorhanden */
		if (propFile.exists()) {
			try {
				properties.load(new FileInputStream(propFile));
				
				/* Beschaffenheit der Properties-Datei prüfen */
				
				
				
				// TODO Prüfungen auslagern??
				// TODO PRÜFUNGEN VERVOLLSTÄNDIGEN
				if (getProp(dbURLPropKey) == ""
						|| Integer.parseInt(getProp(dbPortPropKey)) == 0
						|| getProp(dbSIDPropKey) == "") {
				}
			} catch (IOException e) {}
		} else {
			propFile.createNewFile();
		}

		exitCode = DatabaseSetup.showDialog();
		if (exitCode == CANCEL_BUTTON_PUSHED) {
			System.exit(0);
		} else if (exitCode == OK_BUTTON_PUSHED) {
			// Datenbankeinrichtung wurde bestätigt
		}
		
		
		/* GUI - Login Screen anzeigen */
		exitCode = MerlinLogin.main();
		
		if (exitCode == LOGIN_BUTTON_PUSHED) {
			exitCode = MerlinMain.main();
		} else if (exitCode == REGISTER_BUTTON_PUSHED) {
			
		} else {
			System.exit(0);
		}
		
	}
	
	/* PROPERTIES-OBJECT ACCESSORS */
	
	public static void saveProp(String property, String value) {
		properties.setProperty(property, value);
	}
	
	public static void saveProp(String property, Integer value) {
		saveProp(property, value.toString());
	}
	
	public static void saveEncProp(String property, String value) throws Exception {
		saveProp(property, AES.encrypt(value).toString());
	}
	
	public static String getProp(String property) {
		return properties.getProperty(property);
	}
	
	public static String getEncProp(String property) throws Exception {
		byte[] cipher = getProp(property).getBytes();
		return AES.decrypt(cipher);
	}
	
	public static String[] getLogin(String encLoginData) throws Exception {
		String loginData   = getEncProp(encLoginData);		
		String decUsername = loginData.substring(0, loginData.indexOf(loginDataSplitString));
		String decPwd	   = loginData.substring(decUsername.length()).replaceAll(loginDataSplitString, "");
		
		String[] result = {decUsername, decPwd};
		
		return result;
	}
	
	public static String[] getDatabaseLogin() throws Exception {
		return getLogin(loginDataPropKey);
	}

	public static String[] getBirdwatcherLogin() throws Exception {
		return getLogin(loginDataBirdwatcherPropKey);
	}

	public static void saveLogin(String propKey, String username, String password) throws Exception {
		saveEncProp(propKey, username + loginDataSplitString + password);
	}

	public static void saveDatabaseLogin(String username, String password) throws Exception { 
		saveEncProp(loginDataPropKey, username + loginDataSplitString + password);
	}

	public static void saveBirdwatcherLogin(String username, String password) throws Exception {
		saveLogin(loginDataBirdwatcherPropKey, username, password);
	}

	public static String getUsername(String[] loginData) {
		return loginData[0];
	}

	public static String getPassword(String[] loginData) {
		return loginData[1]; 
	}
}
