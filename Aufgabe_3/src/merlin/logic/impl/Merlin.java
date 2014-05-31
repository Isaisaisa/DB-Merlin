package merlin.logic.impl;

import merlin.data.BirdwatcherRepository;

public class Merlin {

	public static void insertBirdwatcher(String name, String vorname, String email) {
		if (name == "Arsch") {
			throw new IllegalArgumentException("Ein Arsch ist nicht erlaubt!");
		}
		// Anlegen des Birdwatchers
		if (BirdwatcherRepository.create(name, vorname, email) == null) {
			throw new IllegalArgumentException("Birdwatcher konnte nicht angelegt werden.");
		}
	}
	
}
