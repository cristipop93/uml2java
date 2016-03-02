package com.uml2Java.client.service;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("Uml2JavaService")
public interface Uml2JavaService extends RemoteService {
  // Sample interface method of remote interface
  String getMessage(String msg);

  /**
   * Utility/Convenience class.
   * Use Uml2JavaService.App.getInstance() to access static instance of Uml2JavaServiceAsync
   */
  public static class App {
    private static Uml2JavaServiceAsync ourInstance = GWT.create(Uml2JavaService.class);

    public static synchronized Uml2JavaServiceAsync getInstance() {
      return ourInstance;
    }
  }
}
