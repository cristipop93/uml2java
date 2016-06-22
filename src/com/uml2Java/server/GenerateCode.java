package com.uml2Java.server;

import com.uml2Java.client.siteView.siteUtils.Framework;
import com.uml2Java.shared.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * Created by Cristi on 6/6/2016.
 */
public class GenerateCode {
  private Map<Long, PageDTO> pageDTOMap;
  private Map<Long, ComponentDTO> componentDTOMap;
  private Map<Long, ActionDTO> actionDTOMap;
  private Map<String, ClassDTO> classDTOMap;
  private boolean isAddMockData;
  private String username;
  private final Framework selectedFramework;
  public static final String SOURCE_FOLDER = "C:\\Users\\Cristi\\";

  private Map<String, String> finalResult;

  public GenerateCode(Map<Long, PageDTO> pageDTOMap, Map<Long, ComponentDTO> componentDTOMap,
      Map<Long, ActionDTO> actionDTOMap, Map<String, ClassDTO> classDTOMap, boolean isAddMockData, String username,
      Framework selectedFramework) {
    this.pageDTOMap = pageDTOMap;
    this.componentDTOMap = componentDTOMap;
    this.actionDTOMap = actionDTOMap;
    this.classDTOMap = classDTOMap;
    this.finalResult = new HashMap<String, String>();
    this.isAddMockData = isAddMockData;
    this.username = username;
    this.selectedFramework = selectedFramework;
  }

  public void generate() {
    for (String key : classDTOMap.keySet()) {
      ClassDTO classDTO = classDTOMap.get(key);
      String className = classDTO.getTitle() + ".ts";
      String classContent = GenerateIonic2Utils.classTS(classDTO.getTitle(), classDTO.getAttributes());
      finalResult.put(className, classContent);
    }

    for (Long key : pageDTOMap.keySet()) {
      generatePage(key);
    }

    // set HomePage 1l
    String appTsName = "app.ts";
    String appTsContent = GenerateIonic2Utils.appTS(pageDTOMap.get(1l).getTitle());

    finalResult.put(appTsName, appTsContent);

    zipFiles();
  }

  private void generatePage(Long key) {
    PageDTO page = pageDTOMap.get(key);
    ComponentDTO component = componentDTOMap.get(page.getId());
    List<Attribute> attributeList = classDTOMap.get(component.getDataTypes().getDisplayName()).getAttributes();

    if (component.getType() == ShapeType.LIST) {
      generateList(page, component, attributeList);
    } else if (component.getType() == ShapeType.FORM) {
      generateForm(page, component, attributeList);
    } else if (component.getType() == ShapeType.DETAILS) {
      generateDetails(page, component, attributeList);
    }

  }

  private void generateDetails(PageDTO page, ComponentDTO component, List<Attribute> attributeList) {
    String tsName = page.getTitle() + ".ts";
    String tsContent = "";
    if (selectedFramework == Framework.IONIC2)
      tsContent = GenerateIonic2Utils.detailsTS(page.getTitle(), component.getDataTypes().getDisplayName());

    String htmlName = page.getTitle() + ".html";
    String htmlContent = "";
    if (selectedFramework == Framework.IONIC2)
      htmlContent = GenerateIonic2Utils
        .detailsHTML(page.getTitle(), component.getDataTypes().getDisplayName(), attributeList);

    finalResult.put(tsName, tsContent);
    finalResult.put(htmlName, htmlContent);
  }

  private void generateForm(PageDTO page, ComponentDTO component, List<Attribute> attributeList) {
    boolean isList = false, isDetails = false, isAction = false, isOkAction = false;
    String listName = "", detailsName = "", actionName = "", okActionName = "", typeName = component.getDataTypes()
        .getDisplayName();

    // establish the flows from the page and component
    FlowDTO componentFlow = null;
    if (component.getFlows().size() > 0)
      componentFlow = component.getFlows().get(0);

    //    FlowDTO pageFlow = null;
    //    if (page.getFlows().size() > 0)
    //      pageFlow = page.getFlows().get(0);

    if (componentFlow != null) {
      if (componentFlow.getSecondComponentType() == ShapeType.PAGE) {
        String title = pageDTOMap.get(componentFlow.getSecondComponentId()).getTitle();
        ShapeType componentType = componentDTOMap.get(componentFlow.getSecondComponentId()).getType();
        if (componentType == ShapeType.LIST) {
          isList = true;
          listName = title;
        } else if (componentType == ShapeType.DETAILS) {
          isDetails = true;
          detailsName = title;
        }
      } else if (componentFlow.getSecondComponentType() == ShapeType.ACTION) {
        isAction = true;
        actionName = actionDTOMap.get(componentFlow.getSecondComponentId()).getTitle();
        FlowDTO okFlow = getOkFlow(actionDTOMap.get(componentFlow.getSecondComponentId()));
        if (okFlow != null) {
          isOkAction = true;
          okActionName = pageDTOMap.get(okFlow.getSecondComponentId()).getTitle();
        }
      }
    }

    //pageFlow ...

    String tsName = page.getTitle() + ".ts";
    String tsContent = "";
    if (selectedFramework == Framework.IONIC2)
      tsContent = GenerateIonic2Utils
          .formTS(page.getTitle(), typeName, attributeList, isList, listName, isAction, actionName, isOkAction,
              okActionName, isDetails, detailsName, page.getId());

    String htmlName = page.getTitle() + ".html";
    String htmlContent = "";
    if (selectedFramework == Framework.IONIC2)
      htmlContent = GenerateIonic2Utils
        .formHTML(page.getTitle(), typeName, attributeList, isAction, actionName, page.getId());

    finalResult.put(tsName, tsContent);
    finalResult.put(htmlName, htmlContent);
  }

  private void generateList(PageDTO page, ComponentDTO component, List<Attribute> attributeList) {
    boolean isAdd = false, isDetails = false, isAction = false, isForm = false, isList = false, isOkAction = false;
    String addFormName = "", detailsName = "", actionName = "", formName = "", otherListName = "", isOkActionName = "", typeName = component
        .getDataTypes().getDisplayName();

    // establish the flows from the page and component
    FlowDTO componentFlow = null;
    if (component.getFlows().size() > 0)
      componentFlow = component.getFlows().get(0);

    FlowDTO pageFlow = null;
    if (page.getFlows().size() > 0)
      pageFlow = page.getFlows().get(0);

    if (componentFlow != null) {
      if (componentFlow.getSecondComponentType() == ShapeType.PAGE) {
        // get the title of the page
        String title = pageDTOMap.get(componentFlow.getSecondComponentId()).getTitle();
        ShapeType componentType = componentDTOMap.get(componentFlow.getSecondComponentId()).getType();
        if (componentType == ShapeType.DETAILS) { // if details, set the details flag and title
          isDetails = true;
          detailsName = title;
        } else if (componentType == ShapeType.FORM) {
          isForm = true;
          formName = title;
        } else {
          isList = true;
          otherListName = title;
        }
      } else if (componentFlow.getSecondComponentType() == ShapeType.ACTION) {
        // get the title of the action
        isAction = true;
        actionName = actionDTOMap.get(componentFlow.getSecondComponentId()).getTitle();
        FlowDTO okFlow = getOkFlow(actionDTOMap.get(componentFlow.getSecondComponentId()));
        if (okFlow != null) {
          isOkAction = true;
          isOkActionName = pageDTOMap.get(okFlow.getSecondComponentId()).getTitle();
        }
      }
    }

    if (pageFlow != null) {
      if (pageFlow.getSecondComponentType() == ShapeType.PAGE) {
        String title = pageDTOMap.get(pageFlow.getSecondComponentId()).getTitle();
        ShapeType componentType = componentDTOMap.get(pageFlow.getSecondComponentId()).getType();
        if (componentType == ShapeType.FORM) {
          isAdd = true;
          addFormName = title;
        }
      }
    }

    String tsFileName = page.getTitle() + ".ts";
    String tsContent = "";
    if (selectedFramework == Framework.IONIC2)
      tsContent = GenerateIonic2Utils
          .listTS(isDetails, isAdd, isAction, isOkAction, isList, isForm, detailsName, addFormName, page.getTitle(),
              actionName, otherListName, formName, isOkActionName, typeName, isAddMockData, attributeList);

    String fieldToDisplay = "";
    if (attributeList.size() >= 2) {
      fieldToDisplay = attributeList.get(1).getDisplayName();
    } else if (attributeList.size() == 1) {
      fieldToDisplay = attributeList.get(0).getDisplayName();
    }

    String htmlFileName = page.getTitle() + ".html";
    String htmlContent = "";
    if (selectedFramework == Framework.IONIC2)
      htmlContent = GenerateIonic2Utils.listHTML(page.getTitle(), fieldToDisplay, actionName, isAdd, isAction);

    finalResult.put(tsFileName, tsContent);
    finalResult.put(htmlFileName, htmlContent);

  }

  private FlowDTO getOkFlow(ActionDTO actionDTO) {
    List<FlowDTO> allFlows = actionDTO.getFlows();
    for (FlowDTO flow : allFlows) {
      if (flow.getType() == ShapeType.OKFLOW)
        return flow;
    }
    return null;
  }

  private void zipFiles() {
    try {

      FileOutputStream fos = new FileOutputStream(SOURCE_FOLDER + this.username + ".zip");
      ZipOutputStream zos = new ZipOutputStream(fos);

      System.out.println("Output to Zip : " + SOURCE_FOLDER + this.username + ".zip");

      for (String file : finalResult.keySet()) {

        System.out.println("File Added : " + file);
        ZipEntry ze = new ZipEntry(file);
        zos.putNextEntry(ze);

        String fileContent = finalResult.get(file);
        byte[] bytes = fileContent.getBytes();
        int len;
        zos.write(bytes, 0, bytes.length);
      }

      zos.closeEntry();
      //remember close it
      zos.close();

      System.out.println("Done");
    } catch (IOException ex) {
      ex.printStackTrace();
    }
  }

  public Map<String, String> getFinalResult() {
    return finalResult;
  }
}
