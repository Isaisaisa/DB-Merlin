package merlin.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Properties;

import javax.swing.UnsupportedLookAndFeelException;

import merlin.base.Application;

public final class ConstantElems {
	
	// Connection default values
	public static final String 	defaultDbURL  				= "oracle.informatik.haw-hamburg.de";
	public static final String 	defaultDbPort 				= "1521";
	public static final String 	defaultDbSID  				= "inf09";
	public static final String	defaultRememberLogin 		= "false";
	
	// Properties Key Constants
	public static final String 	loginDataPropKey 			= "LD"; // value encrypted!
	public static final String 	loginDataBirdwatcherPropKey = "BW"; // value encrypted!
	public static final String 	loginDataSplitString 		= "\0";
	public static final String 	rememberLoginPropKey		= "RL";
	public static final String 	dbURLPropKey				= "dbURL";
	public static final String 	dbPortPropKey				= "dbPort";
	public static final String 	dbSIDPropKey				= "dbSID";
	
	public static final Hashtable<String, String> propDefaults = new Hashtable<String, String>();
		
	// Properties Object + Filepath
	public static final Properties 	properties   = new Properties();
	public static final String     	propFilePath = new File("").getAbsolutePath() + System.getProperty("file.separator") + "config.properties";
	public static final File 		propFile 	 = new File(propFilePath);
	
	// GUI related constants (Strings etc.)
	public static final String	windowMainTitle = "MERLIN Naturbeobachtungen";
	
	public static boolean loadProperties() throws Exception {
		FileInputStream input = null;
		
		if (propFile.exists()) {
			try {
				// Versuche die Einstellungen aus der Datei zu laden
				input = new FileInputStream(propFile);
				properties.load(input);
			} catch (IOException e) {
				System.out.println("Cannot load properties. (" + propFile.getAbsolutePath() + ")" + "\n"
									+ "(" + e.getMessage() + ")");
			} finally {
				
				if (input != null) {
					try {
						input.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		} else {
			// falls Einstellungen-Datei noch nicht existiert
			// => Datei anlegen und mit Standartwerten befüllen.
			propFile.createNewFile();
			Application.getInstance().saveConnectionData(defaultDbURL, defaultDbPort, defaultDbSID);
			Application.getInstance().saveProp(defaultRememberLogin, "false");
			saveProperties();
			
		}
		return true;
	}
	
	public static boolean saveProperties() throws IOException {
		FileOutputStream output = null;
		
		try {
			output = new FileOutputStream(propFile);
			properties.store(output, null);
		} catch (Exception e) {
			System.err.println("Cannot write properties to file (" + propFile.getAbsolutePath() + "). Check for administrator rights!" + "\n"
								+ "(" + e.getMessage() + ")");
		} finally {
			if (output != null) {
				try {
					output.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return true;
	}
}
