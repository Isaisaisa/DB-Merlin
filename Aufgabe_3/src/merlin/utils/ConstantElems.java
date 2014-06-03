package merlin.utils;

import java.io.File;
import java.util.Properties;

public final class ConstantElems {
	public static final String 	defaultDbURL = "oracle.informatik.haw-hamburg.de";
	public static final String 	defaultDbPort = "1521";
	public static final String 	defaultDbSID = "inf09";
	
	/* Properties Key Constants */
	public static final String 	loginDataPropKey 			= "LD"; // value encrypted!
	public static final String 	loginDataBirdwatcherPropKey = "BW"; // value encrypted!
	public static final String 	loginDataSplitString 		= "\0";
	public static final String 	dbURLPropKey				= "dbURL";
	public static final String 	dbPortPropKey				= "dbPort";
	public static final String 	dbSIDPropKey				= "dbSID";
		
	public static Properties 	properties   = new Properties();
	public static String     	propFilePath = new File("").getAbsolutePath()
									 + System.getProperty("file.separator")
									 + "config.properties";
	public static File propFile = new File(propFilePath);
}
