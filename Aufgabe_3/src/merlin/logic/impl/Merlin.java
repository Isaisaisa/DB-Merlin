package merlin.logic.impl;

import merlin.data.BirdwatcherRepository;
import merlin.logic.exception.IllegalPasswordException;

public class Merlin {
	
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
	
	
	public static boolean checkBirdwatcherRegistration(String benutzername, char[] passwort) {
		BirdwatcherRepository.checkRegrestration(benutzername, passwort);
		return true;
	}
	
}
