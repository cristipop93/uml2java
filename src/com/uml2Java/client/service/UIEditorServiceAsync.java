package com.uml2Java.client.service;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.uml2Java.shared.ActionDTO;
import com.uml2Java.shared.ClassDTO;
import com.uml2Java.shared.ComponentDTO;
import com.uml2Java.shared.PageDTO;

import java.util.Map;

public interface UIEditorServiceAsync {
  void getMessage(String msg, AsyncCallback<String> async);

  void generateCode(Map<Long, PageDTO> pageDTOMap, Map<Long, ComponentDTO> componentDTOMap,
      Map<Long, ActionDTO> actionDTOMap, Map<String, ClassDTO> classDTOMap, AsyncCallback<Void> async);
}
