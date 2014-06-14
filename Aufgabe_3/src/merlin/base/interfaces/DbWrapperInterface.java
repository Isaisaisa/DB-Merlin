package merlin.base.interfaces;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface DbWrapperInterface {
    public void setConnectionData(String dbURL, String dbPort, String dbSID, String dbUsername, char[] dbPassword) throws Exception;
	public void setLoginData(String dbUsername, String dbPassword) throws Exception;
	public void connect() throws Exception;
	public ResultSet sendQuery(String query) throws SQLException;
	public void sendUpdate(String query) throws SQLException;
	public String dbUsername();
}
