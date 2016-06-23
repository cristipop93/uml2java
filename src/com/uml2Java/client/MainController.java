package com.uml2Java.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.sencha.gxt.widget.core.client.Dialog;
import com.sencha.gxt.widget.core.client.box.AlertMessageBox;
import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer;
import com.sencha.gxt.widget.core.client.container.HBoxLayoutContainer;
import com.sencha.gxt.widget.core.client.event.CheckChangeEvent;
import com.sencha.gxt.widget.core.client.event.HideEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.menu.CheckMenuItem;
import com.sencha.gxt.widget.core.client.tree.Tree;
import com.uml2Java.client.domainModel.UmlController;
import com.uml2Java.client.icons.Icons;
import com.uml2Java.client.login.LoginController;
import com.uml2Java.client.login.LoginView;
import com.uml2Java.client.service.UIEditorService;
import com.uml2Java.client.service.UIEditorServiceAsync;
import com.uml2Java.client.siteView.SiteViewController;
import com.uml2Java.client.siteView.siteUtils.Framework;
import com.uml2Java.client.toolbar.ToolbarView;
import com.uml2Java.shared.*;

import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Created by Cristi on 3/21/2016.
 */
public class MainController {
  public static final Icons ICONS = GWT.create(Icons.class);
  private static MainController INSTANCE;
  private BorderLayoutContainer mainContainer;
  private HBoxLayoutContainer buttonsContainer;
  private UserData currentUser;
  private ToolbarView toolbarView;
  private UIEditorServiceAsync service = GWT.create(UIEditorService.class);
  private Logger logger = Logger.getLogger(MainController.class.getName());
  private boolean isDomain = true;

  public static MainController getInstance() {
    if (INSTANCE == null)
      INSTANCE = new MainController();
    return INSTANCE;
  }

  public void setViews(BorderLayoutContainer mainContainer, HBoxLayoutContainer buttonsPanel) {
    this.mainContainer = mainContainer;
    this.buttonsContainer = buttonsPanel;
  }

  public void displayLoginWindow() {
    LoginController.ILoginView loginView = new LoginView();
    LoginController loginController = new LoginController(loginView);
    mainContainer.setCenterWidget(loginView.asWidget());
    loginController.bind();
  }

  public void onSuccessLogin(UserData result) {
    this.currentUser = result;
    mainContainer.setCenterWidget(null);
    initWindow();
    addListeners();
  }

  private void initWindow() {
    toolbarView = new ToolbarView();
    toolbarView.getBasic().setChecked(true);  // enable the basic framework
    buttonsContainer.add(toolbarView.asWidget());

    UmlController.getInstance().setViews(mainContainer, buttonsContainer, toolbarView);
    UmlController.getInstance().initMainWindow();

    SiteViewController.getInstance().setViews(mainContainer, buttonsContainer, toolbarView);
    SiteViewController.getInstance().setSelectedFramework(Framework.BASIC);
    SiteViewController.getInstance().initMainWindow();

    mainContainer.setCenterWidget(UmlController.getView().asWidget());
    mainContainer.forceLayout();
  }

  private void addListeners() {
    toolbarView.getDomainButton().addSelectHandler(new SelectEvent.SelectHandler() {
      @Override
      public void onSelect(SelectEvent event) {
        mainContainer.setCenterWidget(UmlController.getView().asWidget());
        mainContainer.forceLayout();
        SiteViewController.getInstance().setSelectedShape(null);
        isDomain = true;
      }
    });

    toolbarView.getSiteButton().addSelectHandler(new SelectEvent.SelectHandler() {
      @Override
      public void onSelect(SelectEvent event) {
        mainContainer.setCenterWidget(SiteViewController.getView().asWidget());
        mainContainer.forceLayout();
        isDomain = false;
      }
    });

    toolbarView.getLogoutButton().addSelectHandler(new SelectEvent.SelectHandler() {
      @Override
      public void onSelect(SelectEvent event) {
        Window.Location.reload();
      }
    });

    toolbarView.getBasic().addCheckChangeHandler(new CheckChangeEvent.CheckChangeHandler<CheckMenuItem>() {
      @Override
      public void onCheckChange(CheckChangeEvent<CheckMenuItem> event) {
        if (event.getChecked() == Tree.CheckState.CHECKED) {

          SiteViewController.getInstance().setSelectedFramework(Framework.BASIC);
          toolbarView.getIonic().setChecked(false);
        }
      }
    });

    toolbarView.getIonic().addCheckChangeHandler(new CheckChangeEvent.CheckChangeHandler<CheckMenuItem>() {
      @Override
      public void onCheckChange(CheckChangeEvent<CheckMenuItem> event) {
        if (event.getChecked() == Tree.CheckState.CHECKED) {
          if (!SiteViewController.getInstance().validOneComponentPerPage_Ionic2()) {
            new AlertMessageBox("Info", "For Ionic 2, all pages must contain only one component.").show();
            toolbarView.getIonic().setChecked(false);
            return;
          }

          SiteViewController.getInstance().setSelectedFramework(Framework.IONIC2);
          toolbarView.getBasic().setChecked(false);
        }
      }
    });

    toolbarView.getPlayButton().addSelectHandler(new SelectEvent.SelectHandler() {
      @Override
      public void onSelect(SelectEvent event) {
        if (SiteViewController.getInstance().getSelectedFramework() == Framework.BASIC) {
          new AlertMessageBox("Info", "Basic option is meant only for designing the model, and not generating code.")
              .show();
          return;
        }
        final Map<Long, PageDTO> pagesDTO = SiteViewController.getInstance().getPagesDTO();
        final Map<Long, ComponentDTO> componentsDTO = SiteViewController.getInstance().getComponentsDTO();
        final Map<Long, ActionDTO> actionDTO = SiteViewController.getInstance().getActionDTO();
        final Map<String, ClassDTO> classDtos = UmlController.getInstance().getClassDtos();
        if (!validComponentDataTypes(componentsDTO)) {
          new AlertMessageBox("Error", "All components must have valid data types assigned.").show();
          return;
        }
        if (!validPages(pagesDTO)) {
          new AlertMessageBox("Error", "All pages must have components assigned.").show();
          return;
        }
        if (!validClasses(classDtos)) {
          new AlertMessageBox("Error", "All classes must have at least two attributes.").show();
          return;
        }
        if (!validAttributes(classDtos)) {
          new AlertMessageBox("Error", "Some attributes have invalid data types.").show();
          return;
        }
        final Dialog dialog = new Dialog();
        dialog.setHeadingText("Info");
        dialog.setWidth(300);
        dialog.setResizable(false);
        dialog.setHideOnButtonClick(true);
        dialog.setPredefinedButtons(Dialog.PredefinedButton.YES, Dialog.PredefinedButton.NO);
        dialog.add(new Label("Add mock data ?"));
        dialog.addHideHandler(new HideEvent.HideHandler() {
          @Override
          public void onHide(HideEvent event) {
            boolean isAddMockData = false;
            isAddMockData = dialog.getHideButton() == dialog.getButtonById(Dialog.PredefinedButton.YES.name());
            service
                .generateCode(pagesDTO, componentsDTO, actionDTO, classDtos, isAddMockData, currentUser.getUserName(),
                    SiteViewController.getInstance().getSelectedFramework(), new AsyncCallback<Void>() {
                      @Override
                      public void onFailure(Throwable caught) {
                        new AlertMessageBox("Error", "Error while generating the code.").show();
                      }

                      @Override
                      public void onSuccess(Void result) {
                        String url = GWT.getModuleBaseURL() + "ZipDownloadService?user=" + currentUser.getUserName();
                        Window.open(url, "_self", "");
                      }
                    });
          }
        });
        dialog.show();

      }
    });

    KeyDownHandler keyDownHandler = new KeyDownHandler() {
      @Override
      public void onKeyDown(KeyDownEvent event) {
        if (event.getNativeKeyCode() == KeyCodes.KEY_DELETE) {
          if (isDomain)
            UmlController.getInstance().getRightPanelController().removeShape();
          else {
            SiteViewController.getInstance().onDelete();
          }
        }
      }
    };

    RootPanel.get().addDomHandler(keyDownHandler, KeyDownEvent.getType());
  }

  private boolean validPages(Map<Long, PageDTO> pagesDTO) {
    for (Long id : pagesDTO.keySet()) {
      if (pagesDTO.get(id).getComponentsId().isEmpty())
        return false;
    }
    return true;
  }

  private boolean validComponentDataTypes(Map<Long, ComponentDTO> componentsDTO) {
    for (Long id : componentsDTO.keySet()) {
      if(componentsDTO.get(id).getDataTypes().getId() == -1) {
        return false;
      }
    }
    return true;
  }

  private boolean validClasses(Map<String, ClassDTO> classDTOMap) {
    for (String id : classDTOMap.keySet()) {
      if (classDTOMap.get(id).getAttributes().size() < 2) {
        return false;
      }
    }
    return true;
  }

  private boolean validAttributes(Map<String, ClassDTO> classDTOMap) {
    for (String id : classDTOMap.keySet()) {
      List<Attribute> attributeList = classDTOMap.get(id).getAttributes();
      for (Attribute attr : attributeList) {
        if (attr.getDataType() != PrimitiveDataTypes.STRING && attr.getDataType() != PrimitiveDataTypes.INT)
          return false;
      }
    }
    return true;
  }
}
