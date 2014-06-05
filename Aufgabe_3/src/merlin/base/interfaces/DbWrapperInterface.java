package merlin.base.interfaces;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface DbWrapperInterface {
	abstract DbWrapperInterface getInstance();
	abstract void setConnectionData(String dbURL, int dbPort, String dbSID, String dbUsername, char[] dbPassword);
	abstract void connect();
	abstract ResultSet sendQuery(String query) throws SQLException;
	abstract void sendUpdate(String query) throws SQLException;
	}
