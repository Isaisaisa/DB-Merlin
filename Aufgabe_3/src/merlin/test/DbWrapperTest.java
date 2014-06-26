package merlin.test;

import java.sql.SQLException;

import merlin.base.DbWrapper;
import static merlin.utils.ConstantElems.*;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class DbWrapperTest {
	
	private DbWrapper database;

	@Before
	public void setUp() throws Exception {
		database = DbWrapper.getInstance();
		database.setConnectionData(defaultDbURL, defaultDbPort, defaultDbSID);
		database.setLoginData("<a-kennung>", "<passwort>"); //TODO LOGIN DATEN EINTRAGEN
		database.connect();
	}

	@Test
	public void isEmptyResultSetTest() throws SQLException, Exception {
		String query = "SELECT * FROM Birdwatcher";
		this.hasResultTest(query);
	}
	
	@Test
	public void MerlinLoginTest() throws SQLException {
		String benutzername = "demo";
		String passwort = "merlin";
		String query = "SELECT Bw_ID FROM Birdwatcher WHERE Benutzername = '" + benutzername + "' AND Passwort = '" + passwort + "'";
		
		this.hasResultTest(query);
	}
	
	private void hasResultTest(String query) throws SQLException {
		assertTrue(database.hasResults(database.getResultVector(database.sendQuery(query))));
	}

}
