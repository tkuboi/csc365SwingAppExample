package edu.calpoly.csc365.examples.dao1.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Connect to Database
 */
public class ConnectionFactory {
  /**
   * Get a connection to database
   * @return Connection object
   */
  public static Connection getConnection(String driver, String url, String user, String pass)
  {
    try {
      Class.forName(driver);
      return DriverManager.getConnection(url, user, pass);
    } catch (SQLException ex) {
      throw new RuntimeException("Error connecting to the database", ex);
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }
    return null;
  }
}
