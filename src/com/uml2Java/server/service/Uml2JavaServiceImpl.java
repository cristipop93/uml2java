package com.uml2Java.server.service;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.uml2Java.client.service.Uml2JavaService;
import com.uml2Java.server.GeneretaeCodeUtil;
import com.uml2Java.server.Ionic2Generator;
import com.uml2Java.shared.*;
import com.uml2Java.shared.exception.Uml2JavaException;

import java.util.List;
import java.util.Map;

public class Uml2JavaServiceImpl extends RemoteServiceServlet implements Uml2JavaService {
  // Implementation of sample interface method
  public String getMessage(String msg) {
    String str = String.format("string: %s.html astaswdq %s \n asda %s", "test", "test2", "test3 asd");
    return "Client said: \"" + msg + "\"<br>Server answered: " + str;
  }

  @Override
  public void generateCode(Map<Long, PageDTO> pageDTOMap, Map<Long, ComponentDTO> componentDTOMap,
      Map<Long, ActionDTO> actionDTOMap, Map<String, ClassDTO> classDTOMap) throws Uml2JavaException {
    Ionic2Generator generator = new Ionic2Generator(pageDTOMap, componentDTOMap, actionDTOMap, classDTOMap);
    generator.generate();

    Map<String, String> filesMap = generator.getFinalResult();
  }
}