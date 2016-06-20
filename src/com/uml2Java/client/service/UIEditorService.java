package com.uml2Java.client.service;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.uml2Java.client.siteView.siteUtils.Framework;
import com.uml2Java.shared.ActionDTO;
import com.uml2Java.shared.ClassDTO;
import com.uml2Java.shared.ComponentDTO;
import com.uml2Java.shared.PageDTO;
import com.uml2Java.shared.exception.Uml2JavaException;

import java.util.Map;

@RemoteServiceRelativePath("Uml2JavaService")
public interface UIEditorService extends RemoteService {
  // Sample interface method of remote interface
  String getMessage(String msg);

  /**
   * Utility/Convenience class.
   * Use UIEditorService.App.getInstance() to access static instance of Uml2JavaServiceAsync
   */
  public static class App {
    private static UIEditorServiceAsync ourInstance = GWT.create(UIEditorService.class);

    public static synchronized UIEditorServiceAsync getInstance() {
      return ourInstance;
    }
  }

  void generateCode(Map<Long, PageDTO> pageDTOMap, Map<Long, ComponentDTO> componentDTOMap,
      Map<Long, ActionDTO> actionDTOMap, Map<String, ClassDTO> classDTOMap, boolean isAddMockData, String username, Framework selectedFramework) throws Uml2JavaException;
}
