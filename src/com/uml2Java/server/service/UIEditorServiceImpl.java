package com.uml2Java.server.service;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.uml2Java.client.service.UIEditorService;
import com.uml2Java.server.GenerateCode;
import com.uml2Java.shared.*;
import com.uml2Java.shared.exception.Uml2JavaException;

import java.util.Map;

public class UIEditorServiceImpl extends RemoteServiceServlet implements UIEditorService {
  // Implementation of sample interface method
  public String getMessage(String msg) {
    String str = String.format("string: %s.html astaswdq %s \n asda %s", "test", "test2", "test3 asd");
    return "Client said: \"" + msg + "\"<br>Server answered: " + str;
  }

  @Override
  public void generateCode(Map<Long, PageDTO> pageDTOMap, Map<Long, ComponentDTO> componentDTOMap,
      Map<Long, ActionDTO> actionDTOMap, Map<String, ClassDTO> classDTOMap) throws Uml2JavaException {
    GenerateCode generator = new GenerateCode(pageDTOMap, componentDTOMap, actionDTOMap, classDTOMap);
    generator.generate();

    Map<String, String> filesMap = generator.getFinalResult();
  }
}