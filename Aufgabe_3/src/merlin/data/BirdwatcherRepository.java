package merlin.data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import static merlin.utils.ConstantElems.errorMessageBox;



import merlin.base.Application;
import merlin.base.DbWrapper;
import merlin.data.entities.Birdwatcher;
import merlin.data.entities.BirdwatcherImpl;


//Inserts, abfragen, update, delete usw.
public class BirdwatcherRepository {
	
	public static Birdwatcher create(String name, String vorname, String benutzername, String passwort, String email) throws Exception {

		DbWrapper database = Application.getInstance().database();
		
		try {
			database.sendUpdate("INSERT INTO Birdwatcher (Name, Vorname, Benutzername, Passwort, Email, Rolle) " +
					 "VALUES ('" + name + "', '" + vorname + "', '" + benutzername + "', '" + passwort + "', '" + email + "', '" + "R03" + "')");
			System.out.println("is it null?");
		} catch (SQLException e) {
			e.printStackTrace();
			return BirdwatcherImpl.valueOf(name, vorname, benutzername, new String(passwort), email);
		}
		
//		ID und ROLE kommen nicht mehr beim BirdwatcherImpl vor, trotzdem hier einkommentiert dringelassen
//		String id   = ("SELECT Bw_ID FROM Birdwatcher WHERE Benutzername = " + benutzername);
//		String role = ("SELECT Rolle FROM Birdwatcher WHERE Benutzername = " + benutzername);
//		System.out.println("should not be null");
		return BirdwatcherImpl.valueOf(name, vorname, benutzername, new String(passwort), email);
	} 
	
	
	public static boolean isRegistered(String benutzername, String passwort) throws Exception {
		
		DbWrapper database = Application.getInstance().database();
		
		try {
			// Wenn ein Eintrag zur Kombination aus Benutzernamen und Passwort gibt, sind die Logindaten korrekt und das ResultSet hat einen Eintrag ==> nicht leer
//			return database.hasResults(database.sendQuery("SELECT Bw_ID FROM Birdwatcher WHERE Benutzername = 'demo' AND Passwort = 'merlin'"));
			return database.hasResults(database.sendQuery("SELECT Bw_ID FROM Birdwatcher WHERE Benutzername = '" + benutzername + "' AND Passwort = '" + passwort + "'"));
		
		} catch (SQLException e) {
			e.printStackTrace();
			errorMessageBox(e);
			return false;
		}
	}
	
	
	public static List<Birdwatcher> findByName(String name) {
		
		return null;
	}

}
