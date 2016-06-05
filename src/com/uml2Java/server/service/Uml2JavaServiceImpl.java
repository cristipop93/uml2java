package com.uml2Java.server.service;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.uml2Java.client.service.Uml2JavaService;

public class Uml2JavaServiceImpl extends RemoteServiceServlet implements Uml2JavaService {
  // Implementation of sample interface method
  public String getMessage(String msg) {
    String str = String.format("string: %s.html astaswdq %s \n asda %s", "test", "test2", "test3 asd");
    return "Client said: \"" + msg + "\"<br>Server answered: " + str;
  }
}