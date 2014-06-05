package merlin.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public final class ConstantElems {
	public static final String 	defaultDbURL  = "oracle.informatik.haw-hamburg.de";
	public static final String 	defaultDbPort = "1521";
	public static final String 	defaultDbSID  = "inf09";
	
	/* Properties Key Constants */
	public static final String 	loginDataPropKey 			= "LD"; // value encrypted!
	public static final String 	loginDataBirdwatcherPropKey = "BW"; // value encrypted!
	public static final String 	loginDataSplitString 		= "\0";
	public static final String 	dbURLPropKey				= "dbURL";
	public static final String 	dbPortPropKey				= "dbPort";
	public static final String 	dbSIDPropKey				= "dbSID";
		
	public static Properties 	properties   = new Properties();
	public static String     	propFilePath = new File("").getAbsolutePath() + System.getProperty("file.separator") + "config.properties";
	public static File 			propFile 	 = new File(propFilePath);

	public static boolean loadProperties() throws IOException {
		FileInputStream input = null;
		
		if (propFile.exists()) {
			try {
				properties.load(new FileInputStream(propFile));
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
			propFile.createNewFile();
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
