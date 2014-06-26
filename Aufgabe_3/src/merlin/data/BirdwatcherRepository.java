package merlin.data;

import static merlin.utils.ConstantElems.showMsgBox;
import static merlin.base.PreparedStatementKeyEnum.*;
import static merlin.data.SpeciesRepository.getPreparedStatement;

import java.sql.PreparedStatement;
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
		PreparedStatement psRegisterBW, psBwId, psUserRole;
		try {
			psRegisterBW = getPreparedStatement(REGISTER_BIRDWATCHER);
			psRegisterBW.setString(1, name);
			psRegisterBW.setString(2, vorname);
			psRegisterBW.setString(3, benutzername);
			psRegisterBW.setString(4, passwort);
			psRegisterBW.setString(5, email);
			psRegisterBW.setString(6, "R03");
			
			database.sendUpdate(psRegisterBW);
			
			psBwId = getPreparedStatement(GET_BW_ID);
			psBwId.setString(1, benutzername);
			
			psUserRole = getPreparedStatement(GET_USER_ROLE);
			psUserRole.setString(1, benutzername);
			
			id = database.getSingleValue(psBwId);
			role = database.getSingleValue(psUserRole);
			
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
			PreparedStatement psLogin = getPreparedStatement(LOGIN_TO_MERLIN);
			psLogin.setString(1, benutzername);
			psLogin.setString(2, passwort);
			loginResult = database.sendQuery(psLogin);
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
