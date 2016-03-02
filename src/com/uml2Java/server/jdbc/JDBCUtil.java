package com.uml2Java.server.jdbc;

import com.uml2Java.shared.exception.Uml2JavaException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by Cristi on 2/5/2016.
 */
public class JDBCUtil {
  public static Connection getNewConnection() throws Uml2JavaException {
    try {
      Class.forName("com.mysql.jdbc.Driver");
      return DriverManager.getConnection("jdbc:mysql://localhost/uml2java", "root", "root");
    } catch (Exception ex) {
      ex.printStackTrace();
      throw new Uml2JavaException(ex);
    }
  }

  public static void closeConnection(Connection connection) throws Uml2JavaException {
    try {
      if (connection != null) {
        connection.close();
      }
    } catch (SQLException e) {
      e.printStackTrace();
      throw new Uml2JavaException("Could not close db connection", e);
    }
  }
}
