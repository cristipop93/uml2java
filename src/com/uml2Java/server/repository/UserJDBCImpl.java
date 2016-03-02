package com.uml2Java.server.repository;

import com.uml2Java.server.jdbc.JDBCUtil;
import com.uml2Java.shared.UserData;
import com.uml2Java.shared.exception.Uml2JavaException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Cristi on 2/5/2016.
 */
public class UserJDBCImpl {
  private Connection dbConnection;

  public UserJDBCImpl() {
  }

  private PreparedStatement prepareGetLoginDataStatement() throws SQLException {
    return dbConnection
        .prepareStatement("select id, username, password, email from users where username = ? and password = ?");
  }

  public UserData getUserData(String username, String password) throws Uml2JavaException {
    ResultSet resultSet;
    PreparedStatement loginDataStatement;
    try {
      dbConnection = JDBCUtil.getNewConnection();
      loginDataStatement = prepareGetLoginDataStatement();
      loginDataStatement.setString(1, username);
      loginDataStatement.setString(2, password);
      resultSet = loginDataStatement.executeQuery();

      if(resultSet.next()){
        long id = resultSet.getLong(1);
        String uName = resultSet.getString(2);
        String pwd = resultSet.getString(3);
        String email = resultSet.getString(4);
        return new UserData(id, uName, password, email);
      } else {
        throw new Uml2JavaException("User and password combination is invalid.");
      }
    } catch (SQLException e) {
      throw new Uml2JavaException(e.getMessage());
    } finally {
      JDBCUtil.closeConnection(dbConnection);
    }
  }

  private PreparedStatement prepareInsertUserStatement() throws SQLException{
    return dbConnection.prepareStatement("INSERT INTO users (username, password, email) VALUES (?, ?, ?)");
  }

  public void registerUser(String username, String password, String email) throws Uml2JavaException{
    PreparedStatement insertUserStatement;
    try {
      dbConnection = JDBCUtil.getNewConnection();
      insertUserStatement = prepareInsertUserStatement();
      insertUserStatement.setString(1, username);
      insertUserStatement.setString(2, password);
      insertUserStatement.setString(3, email);
      insertUserStatement.executeUpdate();
    } catch (SQLException e) {
      throw new Uml2JavaException(e.getMessage());
    }
  }
}
