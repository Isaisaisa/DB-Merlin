package merlin.logic.impl;

import java.io.IOException;

import javax.swing.UnsupportedLookAndFeelException;

import merlin.base.AES;
import merlin.base.Application;
import merlin.base.DbWrapper;
import merlin.data.BirdwatcherRepository;
import merlin.logic.exception.IllegalPasswordException;

public class MerlinLogic {
	
	//TODO Zeichen der Eingaben abfangen und auswerten
	public static void insertBirdwatcher(String name, String vorname, String benutzername, char[] passwort, char[] passwortBest, String email) throws IllegalPasswordException {
		
		if (name == "Arsch") {
			throw new IllegalArgumentException("Ein 'Arsch' ist nicht erlaubt!");
		}
		// Anlegen des Birdwatchers
		if (BirdwatcherRepository.create(name, vorname, benutzername, passwort, email) == null) {
			if (passwort == passwortBest) {
				throw new IllegalArgumentException(
						"Birdwatcher konnte nicht angelegt werden.");
			} else {
				throw new IllegalPasswordException("Passwort ungleich");
			}
		}
	}

//	TODO login überprüfen
//	
	public static boolean isRegistered(String benutzername, char[] passwort) {
		BirdwatcherRepository.isRegistered(benutzername, passwort);
		return true;
	}
	


	public static void loginBirdwatcher(String benutzername, char[] passwort) {

	}
	
	public static boolean loginToDatabase(String dbURL, String dbPort, String dbSID, String dbUsername, String dbPassword, boolean saveLoginData) throws ClassNotFoundException, InstantiationException, IllegalAccessException, IOException, UnsupportedLookAndFeelException, Exception {

		DbWrapper database = Application.getInstance().database;
		boolean cios[] = checkInputOfSetup(dbURL, dbPort, dbSID, dbUsername, dbPassword);
		
		boolean isValidInput = cios[0] && cios[1] && cios[2] && cios[3] && cios[4];
		
		// falls Anmeldedaten gemerkt werden sollen ==> verschlüsselt in Properties ablegen.
		if (isValidInput) {
			Application.getInstance().saveConnectionData(dbURL, dbPort, dbSID);
			
			if (saveLoginData) {
				Application.getInstance().saveDatabaseLogin(dbUsername, dbPassword);
			}
		}
		
		// SETTING DATA
		database.setConnectionData(dbURL, dbPort, dbSID);
		database.setLoginData(dbUsername, dbPassword);
		
		// CONNECT
		try {
			database.connect();
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getMessage());
			System.err.println("MerlinLogic: loginToDatabase failed.");
			return false;
		}
		
		return true;
	}
	
	public static boolean[] checkInputOfSetup(String dbURL, String dbPort, String dbSID, String dbUsername, String dbPassword) {
		/* Eingaben aus DatabaseSetup (delegiert durch loginToDatabase) prüfen */
		boolean[] result = new boolean[5];
		
		result[0] = isValidURL(dbURL);
		result[1] = isValidPort(dbPort);
		result[2] = isValidSID(dbSID);
		result[3] = isValidUsername(dbUsername);
		result[4] = isValidPassword(dbPassword);
		
		return result;
	}
	
	private static boolean isValidURL(String url) {
		if (url != null &&
			!url.isEmpty())
		{
			return true;
		} else {
			return false;
		}
	}
	
	private static boolean isValidPort(String port) {
		if (port != null &&
			!port.isEmpty() &&
			Integer.parseInt(port) > 0)
		{
			return true;
		} else {
			return false;
		}
	}
	
	private static boolean isValidSID(String sid) {
		if (sid != null &&
			!sid.isEmpty())
		{
			return true;
		} else {
			return false;
		}
	}
	
	private static boolean isValidUsername(String username) {
		if (username != null &&
			!username.isEmpty())
		{
			return true;
		} else {
			return false;
		}
	}
	
	private static boolean isValidPassword(String password) {
		if (password != null &&
			!password.isEmpty())
		{
			return true;
		} else {
			return false;
		}
	}
	
	
	
	public static boolean loginToMerlin(String username, String password) {
		return true;
	}
	
	public static boolean checkInputOfMerlinLogin() {
		
		
		return true;
	}
	

}
