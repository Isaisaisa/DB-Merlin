package merlin.data;

import java.sql.SQLException;
import java.util.List;


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
		System.out.println("should be not null");
		return BirdwatcherImpl.valueOf(name, vorname, benutzername, new String(passwort), email);
	} 
	
	
	//TODO funktionionsfähig mit den tests machen
//	public static boolean isRegistered(String benutzername, char[] passwort) throws Exception{
//		
//		DbWrapper database = Application.getInstance().database();
//		
//		try {
//			String pwd = new String(passwort);
//			
//			ResultSet rs = database.sendQuery("SELECT Bw_ID FROM Birdwatcher WHERE Benutzername = '" + benutzername + "' AND Passwort = '" + pwd + "'");
//			if (rs != null && !rs.next()){
//				return true;
//			}
//			return false;
//		} catch (SQLException e) {
//			e.printStackTrace();
//			return false;
//		}
//		
//	}
	
	
	public static List<Birdwatcher> findByName(String name) {
		
		return null;
	}

}
