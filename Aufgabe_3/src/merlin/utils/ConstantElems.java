package merlin.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Hashtable;
import java.util.Properties;

import javax.swing.JOptionPane;
import javax.swing.UnsupportedLookAndFeelException;

import merlin.base.Application;

public final class ConstantElems {
	
	// Connection default values
	public static final String 	defaultDbURL  				= "oracle.informatik.haw-hamburg.de";
	public static final String 	defaultDbPort 				= "1521";
	public static final String 	defaultDbSID  				= "inf09";
	public static final String	defaultRememberLogin 		= "false";
	public static final String  demoUsername				= "demo";
	public static final String  demoPassword				= "merlin";
	
	// Properties Key Constants
	public static final String 	loginDataPropKey 			= "LD"; 	// value encrypted!
	public static final String 	loginDataBirdwatcherPropKey = "BW"; 	// value encrypted!
	public static final String 	userPropKey					= "User";
	public static final String 	pwdPropKey 					= "Pwd";
	public static final String 	loginDataSplitString 		= "\0";
	public static final String 	rememberLoginPropKey		= "RL";
	public static final String 	dbURLPropKey				= "dbURL";
	public static final String 	dbPortPropKey				= "dbPort";
	public static final String 	dbSIDPropKey				= "dbSID";
	
	public static final Hashtable<String, String> propDefaults = new Hashtable<String, String>();
		
	// Properties Object + Filepath
	public static final Properties 	properties   = new Properties();
	public static final String		propFileName = "config.properties";
	public static final String     	propFilePath = new File("").getAbsolutePath() + System.getProperty("file.separator") + propFileName;
	public static final File 		propFile 	 = new File(propFilePath);
	
	// GUI related constants (Strings etc.)
	public static final String	windowMainTitle = "MERLIN Naturbeobachtungen";
	
	public static boolean loadProperties() throws Exception {
		FileInputStream input = null;
		putPropDefaults();
		
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
			// => Datei anlegen und mit Standartwerten bef�llen.
			propFile.createNewFile();
			Application.getInstance().saveConnectionData(defaultDbURL, defaultDbPort, defaultDbSID);
			Application.getInstance().saveProp(defaultRememberLogin, "false");
			saveProperties();
			
		}
		
		ensurePropConsistency();
		
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
					showMsgBox(e);
					e.printStackTrace();
				}
			}
		}
		return true;
	}
	
	public static void putPropDefaults() {
		Hashtable<String, String> pd = propDefaults; // convenience shortcut...
		
		pd.put(dbURLPropKey, 				defaultDbURL);
		pd.put(dbPortPropKey, 				defaultDbPort);
		pd.put(dbSIDPropKey, 				defaultDbSID);
		pd.put(rememberLoginPropKey, 		"false");
		pd.put(loginDataPropKey, 			"");
		pd.put(loginDataBirdwatcherPropKey, "");
		pd.put(userPropKey, 				"");
		pd.put(pwdPropKey, 					"");
	}
	
	public static void ensurePropConsistency() {
		Hashtable<String, String> pd = propDefaults; // ...same here
		
		if (properties.getProperty(dbURLPropKey) 				== null) {properties.setProperty(dbURLPropKey, pd.get(dbURLPropKey));}
		if (properties.getProperty(dbPortPropKey) 				== null) {properties.setProperty(dbPortPropKey, pd.get(dbPortPropKey));}
		if (properties.getProperty(dbSIDPropKey) 				== null) {properties.setProperty(dbSIDPropKey, pd.get(dbSIDPropKey));}
		if (properties.getProperty(rememberLoginPropKey) 		== null) {properties.setProperty(rememberLoginPropKey, pd.get(rememberLoginPropKey));}
		if (properties.getProperty(loginDataPropKey) 			== null) {properties.setProperty(loginDataPropKey, pd.get(loginDataPropKey));}
		if (properties.getProperty(loginDataBirdwatcherPropKey) == null) {properties.setProperty(loginDataBirdwatcherPropKey, pd.get(loginDataBirdwatcherPropKey));}
		if (properties.getProperty(userPropKey) 				== null) {properties.setProperty(userPropKey, pd.get(userPropKey));}
		if (properties.getProperty(pwdPropKey) 					== null) {properties.setProperty(pwdPropKey, pd.get(pwdPropKey));}
		
		try {
			saveProperties();
		} catch (IOException e) {
			e.printStackTrace();
			System.err.println(e.getMessage());
		}
	}
	
	// TODO stelle finden, durch die die ganze zeit die nullpointer exception im windowbuilder geworfen wird
	
	public static void showMsgBox(Exception e, String additionalMsg) {
		String breakStr;
		
		if (additionalMsg == null) {
			additionalMsg = "";
			breakStr = "";
		} else {
			breakStr = "\n";
		}
		
		JOptionPane.showMessageDialog(
				null,
			    e.getMessage() + breakStr + additionalMsg,
			    e.getClass().getName(),
			    JOptionPane.ERROR_MESSAGE);
	}
	
	public static void showMsgBox(Exception e) {
		showMsgBox(e, null);
	}
	
	public static void showMsgBox(SQLException e) {
		JOptionPane.showMessageDialog(
				null,
			    "Fehler: " + e.getErrorCode() + "\n" +
			    e.getMessage(),
			    e.getClass().getName(),
			    JOptionPane.ERROR_MESSAGE);
	}
	
	public static void showMsgBox(String msg, boolean modal) {
		if (modal) {
			try {
				DialogExample.show(msg);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
				showMsgBox(e);
			} catch (InstantiationException e) {
				e.printStackTrace();
				showMsgBox(e);
			} catch (IllegalAccessException e) {
				e.printStackTrace();
				showMsgBox(e);
			} catch (UnsupportedLookAndFeelException e) {
				e.printStackTrace();
				showMsgBox(e);
			}
		} else {
			JOptionPane.showMessageDialog(null, msg);
		}
	}
	
	public static void showMsgBox(String msg, String headline) {
		JOptionPane.showMessageDialog(
				null,
			    msg, headline,
			    JOptionPane.INFORMATION_MESSAGE);
	}
	
	public static void showMsgBox(String msg) {
		showMsgBox(msg, "");
	}
}
