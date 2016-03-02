package com.uml2Java.shared;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * Created by Cristi on 2/5/2016.
 */
public class UserData implements IsSerializable {
  private long id;
  private String userName;
  private String password;
  private String email;

  public UserData() {
  }

  public UserData(long id, String userName, String password, String email) {
    this.id = id;
    this.userName = userName;
    this.password = password;
    this.email = email;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }
}
