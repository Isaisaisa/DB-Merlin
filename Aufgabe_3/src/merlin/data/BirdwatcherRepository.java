package merlin.data;

import static merlin.utils.ConstantElems.showMsgBox;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Vector;

import merlin.base.Application;
import merlin.base.DbWrapper;
import merlin.data.entities.Birdwatcher;
import merlin.data.entities.BirdwatcherImpl;


//Inserts, abfragen, update, delete usw.
public class BirdwatcherRepository {
	
	private static Birdwatcher activeUser;
	
	public static void setActiveUser(Birdwatcher activeUser) {
		BirdwatcherRepository.activeUser = activeUser;
	}

	public static Birdwatcher getActiveUser() {
		return activeUser;
	}
	
	public static Birdwatcher create(String name, String vorname, String benutzername, String passwort, String email) throws Exception {

		DbWrapper database = Application.getInstance().database();
		String id = "";
		String role = "";
		try {
			database.sendUpdate("INSERT INTO Birdwatcher (Name, Vorname, Benutzername, Passwort, Email, Rolle) " +
					 "VALUES ('" + name + "', '" + vorname + "', '" + benutzername + "', '" + passwort + "', '" + email + "', '" + "R03" + "')");
			 id   = database.getSingleValue("SELECT Bw_ID FROM Birdwatcher WHERE Benutzername = " + benutzername);
			 role = database.getSingleValue("SELECT Rolle FROM Birdwatcher WHERE Benutzername = " + benutzername); 
			 return BirdwatcherImpl.valueOf(id, name, vorname, benutzername, new String(passwort), email, role);
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
		
		
	} 
	
	
	public static boolean loginToMerlin(String benutzername, String passwort) throws Exception {
		
		DbWrapper database = Application.getInstance().database();
		ResultSet loginResult;
		
		try {
			loginResult = database.sendQuery("SELECT Bw_ID, Name, Vorname, Benutzername, Passwort, Email, Rolle FROM Birdwatcher WHERE Benutzername = '" + benutzername + "' AND Passwort = '" + passwort + "'");
			Vector<Vector<String>> resultVector = database.getResultVector(loginResult);
			
			if (database.hasResults(resultVector)) {
				
				int i = 0;
				
				BirdwatcherRepository.setActiveUser(BirdwatcherImpl.valueOf(resultVector.get(0).get(i++), resultVector.get(0).get(i++), resultVector.get(0).get(i++), resultVector.get(0).get(i++), resultVector.get(0).get(i++), resultVector.get(0).get(i++), resultVector.get(0).get(i++)));
				return true;
			} else {
				return false;
			}
			
			// Wenn ein Eintrag zur Kombination aus Benutzernamen und Passwort gibt, sind die Logindaten korrekt und das ResultSet hat einen Eintrag ==> nicht leer
		
		} catch (SQLException e) {
			e.printStackTrace();
			showMsgBox(e); //TODO
			return false;
		}
	}
	
	
	public static List<Birdwatcher> findByName(String name) {
		return null;
	}

}
