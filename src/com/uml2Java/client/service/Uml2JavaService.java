package com.uml2Java.client.service;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.uml2Java.shared.ActionDTO;
import com.uml2Java.shared.ClassDTO;
import com.uml2Java.shared.ComponentDTO;
import com.uml2Java.shared.PageDTO;
import com.uml2Java.shared.exception.Uml2JavaException;

import java.util.Map;

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

  void generateCode(Map<Long, PageDTO> pageDTOMap, Map<Long, ComponentDTO> componentDTOMap,
      Map<Long, ActionDTO> actionDTOMap, Map<String, ClassDTO> classDTOMap) throws Uml2JavaException;
}
