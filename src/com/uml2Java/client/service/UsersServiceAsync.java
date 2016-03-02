package com.uml2Java.client.service;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.uml2Java.shared.UserData;

public interface UsersServiceAsync {
  void login(String username, String password, AsyncCallback<UserData> async);

  void register(String username, String password, String email, AsyncCallback<Void> async);
}
