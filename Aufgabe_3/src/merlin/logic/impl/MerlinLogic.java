package merlin.logic.impl;

import static merlin.utils.ConstantElems.rememberLoginPropKey;
import static merlin.utils.ConstantElems.showMsgBox;

import java.io.IOException;

import javax.swing.UnsupportedLookAndFeelException;

import merlin.base.Application;
import merlin.base.DbWrapper;
import merlin.data.BirdwatcherRepository;
import merlin.logic.exception.IllegalPasswordException;

public class MerlinLogic {

	//TODO Zeichen der Eingaben abfangen und auswerten
	public static boolean insertBirdwatcher(String name, String vorname, String benutzername, String passwort, String passwortBest, String email) throws Exception {

		// Anlegen des Birdwatchers
		if (passwort.equals(passwortBest)) {
			if (BirdwatcherRepository.create(name, vorname, benutzername, passwort, email) == null) {
				throw new IllegalArgumentException(
						"Birdwatcher konnte nicht angelegt werden.");	
			}
		}else{
			throw new IllegalPasswordException("Passw�rter ungleich");
		}
		return true;
	}
	
//	TODO login �berpr�fen
//
	public static boolean[] loginToDatabase(String dbURL, String dbPort, String dbSID, String dbUsername, String dbPassword, boolean saveLoginData) throws ClassNotFoundException, InstantiationException, IllegalAccessException, IOException, UnsupportedLookAndFeelException, Exception {
		boolean loginSucceed = false;
		
		DbWrapper database = Application.getInstance().database();
		boolean cios[] = checkInputOfSetup(dbURL, dbPort, dbSID, dbUsername, dbPassword);

		boolean isValidInput = cios[0] && cios[1] && cios[2] && cios[3] && cios[4];

		
		if (isValidInput) {
			Application.getInstance().saveConnectionData(dbURL, dbPort, dbSID);

			if (saveLoginData) {
				Application.getInstance().saveDatabaseLogin(dbUsername, dbPassword);
				Application.getInstance().saveProp(rememberLoginPropKey, "true");
			} else {
				Application.getInstance().saveDatabaseLogin("", "");
				Application.getInstance().saveProp(rememberLoginPropKey, "false");
			}
		}

		// SETTING DATA
		database.setConnectionData(dbURL, dbPort, dbSID);
		database.setLoginData(dbUsername, dbPassword);

		// CONNECT
		try {
			database.connect();
			loginSucceed = true;
		} catch (Exception e) {
			e.printStackTrace();
			showMsgBox(e, "MerlinLogic#loginToDatabase: Verbindung konnte nicht hergestellt werden");
			loginSucceed = false;
		}
		
		boolean[] result = {cios[0], cios[1], cios[2], cios[3], cios[4], loginSucceed};

		return result;

	}
	
	
	private static boolean[] checkInputOfRegisterBirdwatcher(String name, String vorname, String benutzername, String passwort, String passwortBest, String email) {
		boolean[] result = new boolean[6];
		
		result[0] = isValidUsername(benutzername);
		result[1] = isValidName(name);
		result[2] = isValidName(vorname);
		result[3] = isValidEmail(email);
		result[4] = isValidPassword(passwort);
		result[5] = isValidPassword(passwortBest);
		
		return result;
	}
	
	public static boolean[] checkInputOfSetup(String dbURL, String dbPort, String dbSID, String dbUsername, String dbPassword) {
		// Eingaben aus DatabaseSetup (delegiert durch loginToDatabase) pr�fen
		boolean[] result = new boolean[5];

		result[0] = isValidURL(dbURL);
		result[1] = isValidPort(dbPort);
		result[2] = isValidSID(dbSID);
		result[3] = isValidUsername(dbUsername);
		result[4] = isValidPassword(dbPassword);

		return result;
	}
	
	public static boolean[] checkInputOfMerlinLogin(String username, String password) {
		// Eingaben aus MerlinLogin pr�fen
		boolean[] result = new boolean[2];

		result[0] = isValidUsername(username);
		result[1] = isValidPassword(password);

		return result;
	}

	private static boolean isValidURL(String string) {
		if (string != null &&
			!string.isEmpty())
		{
			return true;
		} else {
			return false;
		}
	}

	private static boolean isValidPort(String string) {
		if (string != null &&
			!string.isEmpty() &&
			Integer.parseInt(string) > 0)
		{
			return true;
		} else {
			return false;
		}
	}

	private static boolean isValidSID(String string) {
		if (string != null &&
			!string.isEmpty())
		{
			return true;
		} else {
			return false;
		}
	}

	private static boolean isValidUsername(String string) {
		if (string != null &&
			!string.isEmpty())
		{
			return true;
		} else {
			return false;
		}
	}

	private static boolean isValidPassword(String string) {
		if (string != null &&
			!string.isEmpty())
		{
			return true;
		} else {
			return false;
		}
	}
	
	private static boolean isValidName(String string) {
		if (string != null &&
				!string.isEmpty())
			{
				if (string.toLowerCase() == "arsch") {
					throw new IllegalArgumentException("'�rsche' sind hier nicht erlaubt!");
				}
				return true;
			} else {
				return false;
		}
	}
	
	private static boolean isValidEmail(String string) {
		if (string != null &&
				!string.isEmpty())
			{
				return true;
			} else {
				return false;
		}
	}

	
	public static boolean[] loginToMerlin(String username, String password, boolean rememberLogin) {
		boolean loginSucceed = false;
		// TODO eigentlichen login an merlin durchf�hren
		boolean ciom[] = checkInputOfMerlinLogin(username, password);
		
		boolean isValidInput = ciom[0] && ciom[1];
		
		if (isValidInput) {
			
			if (rememberLogin) {
				
			}
			
		}
		
		try {
			boolean isRegistered = BirdwatcherRepository.loginToMerlin(username, password);
			if (isRegistered) {
				loginSucceed = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// Ergebnisse kumulieren
		boolean[] result = {ciom[0], ciom[1], loginSucceed};

		return result;
		
	}
	
	public boolean loginBirdwatcher(String username, String password) {
		
		return true;
	}
}
