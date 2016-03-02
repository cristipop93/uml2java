package com.uml2Java.server.service;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.uml2Java.client.service.UsersService;
import com.uml2Java.server.repository.UserJDBCImpl;
import com.uml2Java.shared.UserData;
import com.uml2Java.shared.exception.Uml2JavaException;

/**
 * Created by Cristi on 2/5/2016.
 */
public class UsersServiceImpl extends RemoteServiceServlet implements UsersService {
  @Override
  public UserData login(String username, String password) throws Uml2JavaException {
    UserJDBCImpl userJDBC = new UserJDBCImpl();
    return userJDBC.getUserData(username, password);
  }

  @Override
  public void register(String username, String password, String email) throws Uml2JavaException {
    UserJDBCImpl userJDBC = new UserJDBCImpl();
    userJDBC.registerUser(username, password, email);
  }
}
