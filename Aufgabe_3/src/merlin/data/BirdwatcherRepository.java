package merlin.data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import merlin.base.Main;
import merlin.data.entities.Birdwatcher;
import merlin.data.entities.BirdwatcherImpl;


//Inserts, abfragen, update, delete usw.
public class BirdwatcherRepository {
	
	//NUR �BERGANGSWEISE
	public static final String BWROLE = "R03";
	
	
	public static Birdwatcher create(String name, String vorname, String benutzername, char[] passwort, String email) {

		try {
			Main.database.sendUpdate("INSERT INTO Birdwatcher (Name, Vorname, Benutzername, Passwort, Email, Rolle) " +
					 "VALUES ('" + name + "', '" + vorname + "', '" + benutzername + "', '" + new String(passwort) + "', '" + email + "', '" + "R03" + "')");
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		int id =  Integer.valueOf("SELECT Bw_ID FROM Birdwatcher WHERE Benutzername = " + benutzername);
		String role = ("SELECT Rolle FROM Birdwatcher WHERE Benutzername = " + benutzername);
		return BirdwatcherImpl.valueOf(id, name, vorname, benutzername, new String(passwort), email, role);
	
	} 
	
	
	//TODO funktionionsf�hig mit den tests machen
	public static boolean isRegistered(String benutzername, char[] passwort ){
		try {
			String psw = new String(passwort);
			ResultSet rs = Main.database.sendQuery("SELECT Bw_ID FROM Birdwatcher WHERE Benutzername = " + "'" + benutzername + "'" + "AND" + "Passwort = " + "'" + psw + "'");
			if (!rs.next()){
				return true;
			}
			return false;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}

		
		
	}
	
	
	public static List<Birdwatcher> findByName(String name) {
		
		return null;
	}

}
