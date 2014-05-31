package merlin.base;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import javax.swing.UnsupportedLookAndFeelException;

import merlin.gui.*;

public class Main {
	
	// Properties-Object, das von überall aus zugänglich ist
	static Properties properties = new Properties();
	
	// TODO DbWrapper eventuell auslagern, falls Zugriffsprobleme entstehen
	static DbWrapper database;
	
	public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
		
		/* Initialisierungen */
		
		String fileSeperator = System.getProperty("file.separator");
		String path = new java.io.File("").getAbsolutePath();
		String propFile = path + fileSeperator + "config.properties";
		String propFile = path + fileSeperator + "dbconfig.properties";
		
		try {
			properties.load(new FileInputStream(propFile));
			
			if (				 getProp("dbURL")  == "" ||
				Integer.parseInt(getProp("dbPort")) == 0 ||
				                 getProp("dbSID")  == "") {
				throw new IOException();
			}
			
		} catch (IOException e) {
			DatabaseSetup.showDialog();
		}
		
		/* GUI anzeigen */
		MerlinLogin.main(null);
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
}
