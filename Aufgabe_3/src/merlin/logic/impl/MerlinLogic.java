package merlin.logic.impl;

import java.io.IOException;

import javax.swing.UnsupportedLookAndFeelException;

import merlin.base.Application;
import merlin.base.DbWrapper;
import merlin.data.BirdwatcherRepository;
import merlin.data.entities.BirdwatcherImpl;
import merlin.logic.exception.IllegalPasswordException;
import static merlin.utils.ConstantElems.*;

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
			throw new IllegalPasswordException("Passwörter ungleich");
		}
		return true;
	}
	
//	TODO login überprüfen
//
	public static boolean[] loginToDatabase(String dbURL, String dbPort, String dbSID, String dbUsername, String dbPassword, boolean saveLoginData) throws ClassNotFoundException, InstantiationException, IllegalAccessException, IOException, UnsupportedLookAndFeelException, Exception {
		boolean loginSucceed = false;
		
		DbWrapper database = Application.getInstance().database();
		boolean cios[] = checkInputOfSetup(dbURL, dbPort, dbSID, dbUsername, dbPassword);

		boolean isValidInput = cios[0] && cios[1] && cios[2] && cios[3] && cios[4];

		
		if (isValidInput) {
			Application.getInstance().saveConnectionData(dbURL, dbPort, dbSID);

			// falls Anmeldedaten gemerkt werden sollen ==> verschlüsselt in Properties ablegen.
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
		// Eingaben aus DatabaseSetup (delegiert durch loginToDatabase) prüfen
		boolean[] result = new boolean[5];

		result[0] = isValidURL(dbURL);
		result[1] = isValidPort(dbPort);
		result[2] = isValidSID(dbSID);
		result[3] = isValidUsername(dbUsername);
		result[4] = isValidPassword(dbPassword);

		return result;
	}
	
	public static boolean[] checkInputOfMerlinLogin(String username, String password) {
		// Eingaben aus MerlinLogin prüfen
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
					throw new IllegalArgumentException("'Ärsche' sind hier nicht erlaubt!");
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
		// TODO eigentlichen login an merlin durchführen
		boolean ciom[] = checkInputOfMerlinLogin(username, password);
		
		boolean isValidInput = ciom[0] && ciom[1];

		
		if (isValidInput) {
			// TODO falls Login Daten gültig ==> einloggen, sprich, per Select-Statement alle nötigen Daten des BWs holen (id...) + im BW Objekt zwischenspeichern
			
			
			
			if (rememberLogin) {
				// TODO Merlin Login Daten merken (inkl. dem gesetzen Häkchen)
			}
			
		}
		
		try {
			boolean isRegistered = BirdwatcherRepository.loginToMerlin(username, password);
//			System.out.println("209 MerlinLogic#loginToMerlin " + BirdwatcherRepository.getActiveUser().toString());
			if (isRegistered) {
				loginSucceed = true;
			} else {
				// TODO Sinnvollere Maßnahmen oder Fehler werfen (?)
//				System.err.println("MerlinLogic#loginToMerlin: Birdwatcher login failed. Passwort falsch oder Birdwatcher nicht vorhanden.");
//				loginSucceed = false;
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
