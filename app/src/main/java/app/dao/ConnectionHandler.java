package app.dao;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.dbcp2.BasicDataSource;

/**
 * The Class ConnectionHandler. Manages all connections to the database-server in a pool of
 * connections.
 */
public class ConnectionHandler {
  protected Connection conn;
  private static BasicDataSource dataSource = new BasicDataSource();

  /**
   * Self initializing method. Connects to the database with username: "admin" and password:
   * "123456".
   */

  static {
    dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
    dataSource.setUrl(
        "jdbc:mysql://127.0.0.1/db?allowPublicKeyRetrieval=true&autoReconnect=true&useSSL=false");
    dataSource.setUsername("admin");
    dataSource.setPassword("123456");
    dataSource.setMaxIdle(3);
    dataSource.setMaxWaitMillis(20000); // wait 10 seconds to get new connection
    dataSource.setMaxTotal(6);
    dataSource.setMinIdle(0);
    dataSource.setInitialSize(3);
    dataSource.setTestOnBorrow(true);
    dataSource.setValidationQuery("select 1");
    dataSource.setValidationQueryTimeout(10); // The value is in seconds

    dataSource.setTimeBetweenEvictionRunsMillis(600000); // 10 minutes wait to run evictor process
    dataSource.setSoftMinEvictableIdleTimeMillis(600000); // 10 minutes wait to run evictor process
    dataSource.setMinEvictableIdleTimeMillis(60000); // 60 seconds to wait before idle connection is
                                                     // evicted
    dataSource.setMaxConnLifetimeMillis(600000); // 10 minutes is max life time
    dataSource.setNumTestsPerEvictionRun(10);
  }

  /**
   * Close connection pool.
   * 
   * @throws SQLException
   */
  public static void closePool() throws SQLException {
    System.out.println("Closing connection to the database...");
    dataSource.close();
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
  private ConnectionHandler() {
  }
}
