package com.uml2Java.server.service;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.uml2Java.client.service.Uml2JavaService;

public class Uml2JavaServiceImpl extends RemoteServiceServlet implements Uml2JavaService {
  // Implementation of sample interface method
  public String getMessage(String msg) {
    return "Client said: \"" + msg + "\"<br>Server answered: \"Hi!\"";
  }
}