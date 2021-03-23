package app.dao;
import java.sql.*;

import org.apache.commons.dbcp2.BasicDataSource;

public class ConnectionHandler {
    protected Connection conn;
    private static BasicDataSource dataSource = new BasicDataSource();
    
    static {
		dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
		dataSource.setUrl("jdbc:mysql://127.0.0.1/db?allowPublicKeyRetrieval=true&autoReconnect=true&useSSL=false");
		dataSource.setUsername("admin");
		dataSource.setPassword("123456");
		dataSource.setMaxIdle(3);
	    dataSource.setMaxWaitMillis(20000); //wait 10 seconds to get new connection
	    dataSource.setMaxTotal(6);
	    dataSource.setMinIdle(0);
	    dataSource.setInitialSize(3);
	    dataSource.setTestOnBorrow(true);
	    dataSource.setValidationQuery("select 1");
	    dataSource.setValidationQueryTimeout(10); //The value is in seconds

	    dataSource.setTimeBetweenEvictionRunsMillis(600000); // 10 minutes wait to run evictor process
	    dataSource.setSoftMinEvictableIdleTimeMillis(600000); // 10 minutes wait to run evictor process
	    dataSource.setMinEvictableIdleTimeMillis(60000); // 60 seconds to wait before idle connection is evicted
	    dataSource.setMaxConnLifetimeMillis(600000); // 10 minutes is max life time
	    dataSource.setNumTestsPerEvictionRun(10);
		}
	
	
	/**
	 * Close connection pool.
	 */
	public static void closePool() {
		try {
			dataSource.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Retrieves an idle connection from the connection pool
	 *
	 * @return the connection
	 * @throws SQLException the SQL exception
	 */
	public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }
	
	/**
	 * Instantiates a new connection handler.
	 */
	private ConnectionHandler() {}
}
