package com.uml2Java.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer;
import com.sencha.gxt.widget.core.client.container.HBoxLayoutContainer;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.uml2Java.client.domainModel.UmlController;
import com.uml2Java.client.icons.Icons;
import com.uml2Java.client.login.LoginController;
import com.uml2Java.client.login.LoginView;
import com.uml2Java.client.service.Uml2JavaService;
import com.uml2Java.client.service.Uml2JavaServiceAsync;
import com.uml2Java.client.siteView.SiteView;
import com.uml2Java.client.siteView.SiteViewController;
import com.uml2Java.client.toolbar.ToolbarView;
import com.uml2Java.shared.UserData;

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
  private Uml2JavaServiceAsync service = GWT.create(Uml2JavaService.class);

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

      }
    });
  }
}
