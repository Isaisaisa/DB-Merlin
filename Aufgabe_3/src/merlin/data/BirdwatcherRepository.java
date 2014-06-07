package merlin.data;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.swing.UnsupportedLookAndFeelException;

import merlin.base.Application;
import merlin.base.DbWrapper;
import merlin.base.Main;
import merlin.data.entities.Birdwatcher;
import merlin.data.entities.BirdwatcherImpl;


//Inserts, abfragen, update, delete usw.
public class BirdwatcherRepository {
	
	// NUR ÜBERGANGSWEISE
	public static final String BWROLE = "R03";
	
	
	public static Birdwatcher create(String name, String vorname, String benutzername, char[] passwort, String email) throws ClassNotFoundException, InstantiationException, IllegalAccessException, IOException, UnsupportedLookAndFeelException {

		DbWrapper database = Application.getInstance().database;
		
		try {
			database.sendUpdate("INSERT INTO Birdwatcher (Name, Vorname, Benutzername, Passwort, Email, Rolle) " +
					 "VALUES ('" + name + "', '" + vorname + "', '" + benutzername + "', '" + new String(passwort) + "', '" + email + "', '" + "R03" + "')");
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		int id =  Integer.valueOf("SELECT Bw_ID FROM Birdwatcher WHERE Benutzername = " + benutzername);
		String role = ("SELECT Rolle FROM Birdwatcher WHERE Benutzername = " + benutzername);
		return BirdwatcherImpl.valueOf(name, vorname, benutzername, new String(passwort), email);
	
	} 
	
	
	//TODO funktionionsfähig mit den tests machen
	public static boolean isRegistered(String benutzername, char[] passwort ) throws Exception{
		
		DbWrapper database = Application.getInstance().database;
		
		try {
			String psw = new String(passwort);
			
			ResultSet rs = database.sendQuery("SELECT Bw_ID FROM Birdwatcher WHERE Benutzername = '" + benutzername + "' AND Passwort = '" + psw + "'");
			if (rs != null && !rs.next()){
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
