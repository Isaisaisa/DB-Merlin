package merlin.data;

import java.sql.SQLException;
import java.util.List;

import merlin.base.DbWrapper;
import merlin.data.entities.Birdwatcher;
import merlin.data.entities.BirdwatcherImpl;


//Inserts, abfragen, update, delete usw.
public class BirdwatcherRepository {
	
	//NUR ÜBERGANGSWEISE
	public static final String BWROLE = "R03";
	
	public static Birdwatcher create(String name, String vorname, String benutzername, char[] passwort, String email) {

		try {
			DbWrapper dbWrapper = DbWrapper.valueOf();
			dbWrapper.sendUpdate("INSERT INTO Birdwatcher (Name, Vorname, Benutzername, Passwort, Email, Rolle) " +
					 "VALUES ('" + name + "', '" + vorname + "', '" + benutzername + "', '" + new String(passwort) + "', '" + email + "', '" + "R03" + "')");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return new BirdwatcherImpl(name, vorname, email);
	}
	
	public static List<Birdwatcher> findByName(String name) {
		
		return null;
	}

}
