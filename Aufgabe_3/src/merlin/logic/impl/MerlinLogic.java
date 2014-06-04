package merlin.logic.impl;

import merlin.base.AES;
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

//	TODO login �berpr�fen
//	
	public static boolean isRegistered(String benutzername, char[] passwort) {
		BirdwatcherRepository.isRegistered(benutzername, passwort);
		return true;
	}
	


	public static void loginBirdwatcher(String benutzername, char[] passwort) {

	}
	
	public static boolean loginToDatabase(String dbURL, String dbPort, String dbSID, String dbUsername, String dbPassword, boolean saveLoginData) {
		/* Erh�lt die Eingaben aus DatabaseSetup und delegiert Diese an eine Pr�fmethode.
		 * Falls Eingaben OK ==> Verbindungsdaten in Properties ablegen,
		 * und Anmeldedaten, falls gew�nscht, verschl�sselt ablegen.
		 * Anschlie�end DbWrapper initialisieren und DB Verbindung aufbauen.
		 */
		
		if (checkInputOfSetup(dbURL, dbPort, dbSID, dbUsername, dbPassword)) {
			;	
		}
		
		// falls Anmeldedaten gemerkt werden sollen ==> verschl�sselt in Properties ablegen.
		if (saveLoginData) {
			;
		}
		
		
		return true;
		
	}
	
	public static boolean checkInputOfSetup(String dbURL, String dbPort, String dbSID, String dbUsername, String dbPassword) {
		/* Eingaben aus DatabaseSetup (delegiert durch loginToDatabase) pr�fen */
		
		
		
		return true;
	}
	
	
	public static boolean loginToMerlin(String username, String password) {
		return false;
	}
	
	public static boolean checkInputOfMerlinLogin() {
		
		
		return false;
	}
	

}
