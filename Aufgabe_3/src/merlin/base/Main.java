package merlin.base;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;

import javax.swing.UnsupportedLookAndFeelException;

import merlin.gui.*;

public class Main {
	
	static String dbURL  = "";
	static String dbSID  = "";
	static int    dbPort =  0;

	public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
		
		/* Initialisierungen */
		ArrayList<String> dbSettings = new ArrayList<String>();
		
		Properties properties = new Properties();
		String fileSeperator = System.getProperty("file.separator");
		String path = new java.io.File("").getAbsolutePath();
		String propFile = path + fileSeperator + "dbconfig.properties";
		
		try {
			properties.load(new FileInputStream(propFile));
			
			dbURL  = properties.getProperty("dbURL");
			dbPort = Integer.parseInt(properties.getProperty("dbPort"));
			dbSID  = properties.getProperty("dbSID");
			
			if (dbURL  == "" ||
				dbPort == 0  ||
				dbSID  == "") {
				throw new IOException();
			}
			
		} catch (IOException e) {
			DatabaseSetup.showDialog(dbSettings);
			
			dbURL  = dbSettings.get(0);
			dbPort = Integer.parseInt(dbSettings.get(1));
			dbSID  = dbSettings.get(2);
		}
		
		/* GUI anzeigen */
		// TODO Login anzeigen
		MerlinLogin.main(null);
	}

}
