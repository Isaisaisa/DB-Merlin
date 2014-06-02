package merlin.utils;

import java.io.File;
import java.util.Properties;

public final class ConstantElems {
	public static final String defaultDbURL = "oracle.informatik.haw-hamburg.de";
	public static final String defaultDbPort = "1521";
	public static final String defaultDbSID = "inf09";
	
	public static final String aKennungPropKey = "ak";
	public static final String passwordPropKey  = "pd";
	
	public static Properties properties   = new Properties();
	public static String     propFilePath = new File("").getAbsolutePath()
									 + System.getProperty("file.separator")
									 + "config.properties";
	public static File propFile = new File(propFilePath);
}
