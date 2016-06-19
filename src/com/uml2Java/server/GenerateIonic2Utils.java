package com.uml2Java.server;

import com.uml2Java.shared.Attribute;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Cristi on 2/5/2016.
 */
public class GenerateIonic2Utils {
  private static final int MOCKS_NO= 10;

  public static String listHTML(String title, String fieldToDisplay, String actionName, boolean isAdd,
      boolean isAction) {
    String str = String.format(
        "<ion-navbar *navbar>\n" + "    <ion-title>\n" + "        %s\n" + "    </ion-title>\n" + "\n"
            + "</ion-navbar>\n" + "\n" + "<ion-content class=\"home\">\n" + "    <ion-row>\n" + "        <ion-col>\n"
            + "            <ion-list>\n"
            + "                <ion-item *ngFor = \"#myData of data\" (click)=\"%s(myData)\">\n"
            + "                    <h2>{{myData.%s}}</h2>\n" + "                </ion-item>\n"
            + "            </ion-list>\n" + "        </ion-col>\n" + "    </ion-row>\n" + "</ion-content>", title,
        (isAction ? actionName : "goToDetails"), fieldToDisplay);
    if (isAdd) {
      str += "<button fab fab-bottom fab-right style=\"z-index: 999; margin-right: 10px;\" (click)=\"addData()\">\n"
          + "    <ion-icon name=\"add\"></ion-icon>\n" + "</button>";
    }
    return str;
  }

  public static String listTS(boolean isDetails, boolean isAdd, boolean isAction, boolean isActionOK, boolean isList,
      boolean isForm, String detailsName, String addFormName, String listName, String actionName, String otherListName,
      String formName, String actionOkName, String typeName, boolean isAddMockData, List<Attribute> attributeList) {
    String result = "import {Page, NavController, Platform, NavParams, ViewController, Modal, IonicApp} from 'ionic-angular';";
    if (isDetails)
      result += String.format("import {%s} from './%s';", detailsName, detailsName);
    if (isAdd)
      result += String.format("import {%s} from './%s';", addFormName, addFormName);
    if (isList)
      result += String.format("import {%s} from './%s';", otherListName, otherListName);
    if (isForm)
      result += String.format("import {%s} from './%s';", formName, formName);
    if (isActionOK)
      result += String.format("import {%s} from './%s';", actionOkName, actionOkName);

    result += String.format("import {%s} from './%s';", typeName, typeName);

    result += String.format("@Page({\n" + "    templateUrl: 'build/%s.html'\n" + "})\n" + "\n" + "export class %s {\n"
            + "    public data: %s;\n" + "\n" + "    constructor(public nav : NavController) {\n"
            + "        this.load();\n" + "    }\n" + "\n" + "    private load() {\n", listName,
        listName, typeName);
    if (isAddMockData) {
      result += "        this.data = [";
      for (int i=0; i<MOCKS_NO; i++) {
        result += "\n            new " + typeName + "(";
        for (Attribute attribute : attributeList) {
          result += (attribute.getDataType().getDisplayName().equals("int") ? i : "\"" + attribute.getDisplayName() + i + "\"") + ", ";
        }
        result = result.substring(0, result.length() - 2);
        result += "), ";
      }
      result = result.substring(0, result.length() - 2);
      result += "\n        ]\n";
    }

    result += "    }\n";
    if (isAction) {
      if (isActionOK) {
        result += String.format("public %s(item) {\n this.nav.push(%s); }", actionName, actionOkName);
      } else {
        result += String.format("public %s(item) {\n  }", actionName);
      }
    } else if (isDetails) {
      result += String
          .format("    public goToDetails(item) {\n" + "        this.nav.push(%s, {item: item});\n" + "    }", detailsName);
    } else if (isForm) {
      result += String
          .format("    public goToDetails(item) {\n" + "        this.nav.push(%s, {item: item});\n" + "    }", formName);
    } else if (isList) {
      result += String.format("    public goToDetails(item) {\n" + "        this.nav.push(%s);\n" + "    }", otherListName);
    } else {
      result += "    public goToDetails(item) { \n } \n";
    }

    if (isAdd) {
      result += String
          .format(" \n    public addData() {\n" + "        this.nav.push(%s, {item: new %s()});\n" + "    }", addFormName,
              typeName);
    }
    result += "\n }";
    return result;
  }

  public static String formHTML(String title, String typeName, List<Attribute> attributes, boolean isAction,
      String actionName, long id) {
    String result = String.format(
        "<ion-navbar *navbar>\n" + "    <ion-row>\n" + "        <ion-title>Add %s</ion-title>\n" + "    </ion-row>\n"
            + "</ion-navbar>", typeName);
    result += "<ion-content padding class=\"has-header add-person\">\n" + "    <ion-list>";

    for (Attribute attribute : attributes) {
      result += String.format("<ion-item>\n" + "            <ion-label floating>%s</ion-label>\n"
          + "            <ion-input type=\"text\" id = \"%s\" name = \"%s\" %s></ion-input>\n"
          + "        </ion-item> \n", attribute.getDisplayName(), attribute.getDisplayName(),
          attribute.getDisplayName(), (id != 1l ? "[(ngModel)]=\"data." + attribute.getDisplayName() + "\"" : ""));
    }
    result += String.format("<ion-buttons start>\n" + "            <button (click) = \"%s()\">\n"
        + "                <ion-icon name=\"add\" ></ion-icon>\n" + "                Submit\n"
        + "            </button>\n" + "        </ion-buttons>\n" + "    </ion-list>\n" + "</ion-content>",
        (isAction ? actionName : "addData"));

    return result;
  }

  public static String formTS(String title, String typeName, List<Attribute> attributes, boolean isList,
      String listName, boolean isAction, String actionName, boolean isOkAction, String okAction, boolean isDetails,
      String detailsName, long id) {
    String result = "import {Page, Alert, NavController, NavParams} from 'ionic-angular'; \n";
    result += String.format("import {%s} from './%s'; \n", typeName, typeName);

    if (isOkAction)
      result += String.format("import {%s} from './%s'; \n", okAction, okAction);
    if (isList)
      result += String.format("import {%s} from './%s'; \n", listName, listName);
    if (isDetails)
      result += String.format("import {%s} from './%s'; \n", detailsName, detailsName);

    result += String.format("@Page({\n" + "    templateUrl: 'build/%s.html'\n" + "}) \n", title);
    result += String.format("export class %s {\n" + "\t public item : %s;\n", title, typeName);
    result += "constructor(public nav: NavController, public params: NavParams) { \n";
    if (id != 1l) {
      result += "this.item = params.data.item; \n this.data = {};";
      for (Attribute attribute : attributes) {
        result += String
            .format("this.data.%s = this.item.%s; \n", attribute.getDisplayName(), attribute.getDisplayName());
      }
    }
    result += "} \n";

    if (isAction) {
      if (isOkAction) {
        result += String.format("public %s() { \n this.nav.push(%s); \n } \n", actionName, okAction);
      } else {
        result += String.format("public %s() { \n } \n", actionName);
      }
    } else if (isList) {
      result += String.format("public addData() { \n this.nav.push(%s); \n } \n", listName);
    } else if (isDetails) {
      String fields = "";
      for (Attribute attribute : attributes) {
        fields += String.format("this.data.%s, ", attribute.getDisplayName());
      }
      fields = fields.substring(0, fields.length() - 2);  // cut the ", " fomr the last attribute

      result += String.format("public addData() { \n this.nav.push(%s, { item: new %s (%s) }) \n }", detailsName, typeName, fields);

    } else {
      result += "public addData() { \n }";
    }

    result += "}";
    return result;
  }

  public static String detailsHTML(String title, String typeName, List<Attribute> attributes) {
    String result = String.format(
        "<ion-navbar *navbar>\n" + "    <ion-row>\n" + "        <ion-title> %s details</ion-title>\n"
            + "    </ion-row>\n" + "</ion-navbar>\n" + "\n" + "<ion-content padding class=\"about\">\n" + "\n"
            + "    <!-- Primary infos -->\n" + "    <ion-list>\n" + "        <ion-list-header>Info</ion-list-header>",
        typeName);
    for (Attribute attribute : attributes) {
      result += String.format("<ion-row center>\n" + "            <ion-col width-25><b>%s: </b></ion-col>\n"
          + "            <ion-col>{{item.%s}}</ion-col>\n" + "        </ion-row> \n", attribute.getDisplayName(),
          attribute.getDisplayName());
    }
    result += " </ion-list>\n" + "</ion-content>";

    return result;
  }

  public static String detailsTS(String title, String typeName) {
    String result = String.format("import {Page, Alert, NavController, NavParams} from 'ionic-angular';");
    result += String.format("import {%s} from './%s';", typeName, typeName);

    result += String.format("@Page({\n" + "    templateUrl: 'build/%s.html'\n" + "}) \n", title);

    result += String.format("export class %s {\n" + "    public item: %s;\n" + "\n"
        + "    constructor(public nav: NavController, public params: NavParams) {\n"
        + "        this.item = params.data.item;\n" + "    }\n" + "}", title, typeName);

    return result;
  }

  public static String appTS(String homePage) {
    String result = String.format(
        "import {App, Platform} from 'ionic-angular';\n" + "import {StatusBar} from 'ionic-native';\n"
            + "import {%s} from './%s';\n" + "\n" + "\n" + "@App({\n"
            + "  template: '<ion-nav [root]=\"rootPage\"></ion-nav>',\n"
            + "  config: {} // http://ionicframework.com/docs/v2/api/config/Config/\n" + "})\n"
            + "export class MyApp {\n" + "  rootPage: any = %s;\n" + "\n" + "  constructor(platform: Platform) {\n"
            + "    platform.ready().then(() => {\n"
            + "      // Okay, so the platform is ready and our plugins are available.\n"
            + "      // Here you can do any higher level native things you might need.\n"
            + "      StatusBar.styleDefault();\n" + "    });\n" + "  }\n" + "}\n", homePage, homePage, homePage);

    return result;
  }

  public static String classTS(String title, List<Attribute> attributes) {
    String result = String.format("export class %s { \n", title);
    for (Attribute attribute : attributes) {
      result += String.format("\t public %s: %s; \n", attribute.getDisplayName(),
          (attribute.getDataType().getDisplayName().equals("int") || attribute.getDataType().getDisplayName()
              .equals("double") || attribute.getDataType().getDisplayName().equals("long") ?
              "number" : attribute.getDataType().getDisplayName()));
    }

    List<String> fields = new ArrayList<String>();
    for (Attribute attribute : attributes) {
      fields.add(attribute.getDisplayName());
    }

    result += "\n \t constructor(";
    for (String field : fields) {
      result += field + ", ";
    }
    result = result.substring(0, result.length() - 2);
    result += ") {";
    for (String field : fields) {
      result += String.format("\t \t this.%s = %s;", field, field);
    }

    result += "\t } \n }";

    return result;
  }
}
