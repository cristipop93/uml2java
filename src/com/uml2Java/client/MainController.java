package com.uml2Java.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Label;
import com.sencha.gxt.widget.core.client.Dialog;
import com.sencha.gxt.widget.core.client.box.AlertMessageBox;
import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer;
import com.sencha.gxt.widget.core.client.container.HBoxLayoutContainer;
import com.sencha.gxt.widget.core.client.event.HideEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.uml2Java.client.domainModel.UmlController;
import com.uml2Java.client.icons.Icons;
import com.uml2Java.client.login.LoginController;
import com.uml2Java.client.login.LoginView;
import com.uml2Java.client.service.UIEditorService;
import com.uml2Java.client.service.UIEditorServiceAsync;
import com.uml2Java.client.siteView.SiteViewController;
import com.uml2Java.client.toolbar.ToolbarView;
import com.uml2Java.shared.UserData;

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
    buttonsContainer.add(toolbarView.asWidget());

    UmlController.getInstance().setViews(mainContainer, buttonsContainer, toolbarView);
    UmlController.getInstance().initMainWindow();

    SiteViewController.getInstance().setViews(mainContainer, buttonsContainer, toolbarView);
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
      }
    });

    toolbarView.getSiteButton().addSelectHandler(new SelectEvent.SelectHandler() {
      @Override
      public void onSelect(SelectEvent event) {
        mainContainer.setCenterWidget(SiteViewController.getView().asWidget());
        mainContainer.forceLayout();
      }
    });

    toolbarView.getLogoutButton().addSelectHandler(new SelectEvent.SelectHandler() {
      @Override
      public void onSelect(SelectEvent event) {
        //TODO
      }
    });

    toolbarView.getPlayButton().addSelectHandler(new SelectEvent.SelectHandler() {
      @Override
      public void onSelect(SelectEvent event) {
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
            if (dialog.getHideButton() == dialog.getButtonById(Dialog.PredefinedButton.YES.name())) {
              isAddMockData = true;
            } else {
              isAddMockData = false;
            }
            service.generateCode(SiteViewController.getInstance().getPagesDTO(),
                SiteViewController.getInstance().getComponentsDTO(), SiteViewController.getInstance().getActionDTO(),
                UmlController.getInstance().getClassDtos(), isAddMockData, currentUser.getUserName(), new AsyncCallback<Void>() {
                  @Override
                  public void onFailure(Throwable caught) {
                    new AlertMessageBox("Error", "Error while generating the code.").show();
                  }

                  @Override
                  public void onSuccess(Void result) {
                    String url = GWT.getModuleBaseURL() + "ZipDownloadService?user="+currentUser.getUserName();
                    Window.open(url, "_self", "");
                  }
                });
          }
        });
        dialog.show();

      }
    });
  }
}
