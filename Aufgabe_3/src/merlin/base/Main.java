package merlin.base;

import java.io.FileInputStream;
import java.io.IOException;
import javax.swing.UnsupportedLookAndFeelException;

import merlin.gui.*;
import merlin.gui.enums.ExitCode;
import static merlin.gui.enums.ExitCode.*;

import static merlin.utils.ConstantElems.*;

public class Main {
	
	// Properties-Object, das von überall aus zugänglich ist	
	
	
	
	
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
				
				
				
				if (				 getProp("dbURL")   == "" ||
					Integer.parseInt(getProp("dbPort")) == 0  ||
					                 getProp("dbSID")   == "") {
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
	
	public static String getProp(String property) {
		return properties.getProperty(property);
	}
	
	public static String getAKennung() throws Exception {
		return getEncProp(aKennungPropKey);
	}
	
	public static String getPassword() throws Exception {
		return getEncProp(passwordPropKey);
	}
	
	public static void saveAKennung(String value) throws Exception {
		saveEncProp(aKennungPropKey, value);
	}
	
	public static void savePassword(String value) throws Exception {
		saveEncProp(passwordPropKey, value);
	}
	
	public static void saveEncProp(String property, String value) throws Exception {
		saveProp(property, AES.encrypt(value).toString());
	}
	
	public static String getEncProp(String property) throws Exception {
		byte[] cipher = getProp(property).getBytes();
		return AES.decrypt(cipher);
	}
}
