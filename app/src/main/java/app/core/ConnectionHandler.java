package app.core;

import java.sql.*;
import java.util.Properties;

public class ConnectionHandler {
    protected Connection conn;
    public ConnectionHandler () {
    }
    public void connect() {
    	try {
	    Class.forName("com.mysql.cj.jdbc.Driver"); 
            Properties p = new Properties();
            p.put("user", "admin");
            p.put("password", "123456");           
            conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1/db?allowPublicKeyRetrieval=true&autoReconnect=true&useSSL=false",p);
        } catch (Exception e)
    	{
            throw new RuntimeException("Unable to connect", e);
    	}
    }
}