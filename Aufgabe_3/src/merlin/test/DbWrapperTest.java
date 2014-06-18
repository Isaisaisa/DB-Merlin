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
		database.setLoginData("<a-kennung>", "<password>");
		database.connect();
	}

	@Test
	public void isEmptyResultSetTest() throws SQLException, Exception {
		assertTrue(database.hasResults(database.getResultVector(database.sendQuery("SELECT * FROM Birdwatcher"))));
	}

}
