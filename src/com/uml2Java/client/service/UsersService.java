package com.uml2Java.client.service;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.uml2Java.shared.UserData;
import com.uml2Java.shared.exception.Uml2JavaException;

/**
 * Created by Cristi on 2/5/2016.
 */
@RemoteServiceRelativePath("UsersService")
public interface UsersService extends RemoteService {
  UserData login(String username, String password) throws Uml2JavaException;
  void register(String username, String password, String email) throws Uml2JavaException;
}
