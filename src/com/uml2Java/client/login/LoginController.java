package com.uml2Java.client.login;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.*;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.widget.core.client.Window;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.form.FieldLabel;
import com.sencha.gxt.widget.core.client.form.PasswordField;
import com.sencha.gxt.widget.core.client.form.TextField;
import com.uml2Java.client.MainController;
import com.uml2Java.client.domainModel.UmlController;
import com.uml2Java.client.login.register.RegisterController;
import com.uml2Java.client.login.register.RegisterView;
import com.uml2Java.client.service.UsersService;
import com.uml2Java.client.service.UsersServiceAsync;
import com.uml2Java.client.domainModel.uml2javaUtils.TextUtil;
import com.uml2Java.shared.UserData;

/**
 * Created by Cristi on 2/4/2016.
 */
public class LoginController {
  public interface ILoginView {
    TextField getNameField();
    PasswordField getPasswordField();
    TextButton getLoginButton();
    TextButton getRegisterButton();
    Label getForgotPasswordButton();
    FieldLabel getLoginFailedLabel();
    void setErrorLabelText(String val);
    Widget asWidget();
  }

  private static UsersServiceAsync usersService = GWT.create(UsersService.class);

  private ILoginView view;

  public LoginController(ILoginView view) {
    this.view = view;
  }
  public void bind() {
    addListeners();
  }

  private void login(){
    String user = view.getNameField().getText();
    String password = view.getPasswordField().getText();
    if (TextUtil.isEmptyString(user) || TextUtil.isEmptyString(password)) {
      view.setErrorLabelText("Invalid input.");
      return;
    }
    usersService.login(user, password, new AsyncCallback<UserData>() {
      @Override
      public void onFailure(Throwable caught) {
        view.setErrorLabelText("Username or password are incorrect!");
      }

      @Override
      public void onSuccess(UserData result) {
        MainController.getInstance().onSuccessLogin(result);
        view.setErrorLabelText("logged in" + result.getId() + " " + result.getUserName() + " " + result.getPassword() + result.getEmail());
      }
    });
  }


  private void addListeners() {
    view.getLoginButton().addSelectHandler(new SelectEvent.SelectHandler() {
      @Override
      public void onSelect(SelectEvent event) {
        login();
      }
    });

    view.getPasswordField().addKeyDownHandler(new KeyDownHandler() {
      @Override
      public void onKeyDown(KeyDownEvent event) {
        if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER)
          login();
      }
    });

    view.getNameField().addKeyDownHandler(new KeyDownHandler() {
      @Override
      public void onKeyDown(KeyDownEvent event) {
        if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
          login();
        }
      }
    });

    view.getRegisterButton().addSelectHandler(new SelectEvent.SelectHandler() {
      @Override
      public void onSelect(SelectEvent event) {
        RegisterController.IRegisterView registerView = new RegisterView();
        RegisterController controller = new RegisterController(registerView);
        Window window = new Window();
        window.setPixelSize(300,180);
        window.setModal(true);
        window.setHeadingHtml("Register");
        window.add(registerView.asWidget());
        controller.setContentWindow(window);
        window.setResizable(false);
        window.show();
      }
    });

//    view.getForgotPasswordButton().addClickHandler(new ClickHandler() {
//      @Override
//      public void onClick(ClickEvent event) {
//        ForgotPasswordController.IForgotPasswordView forgotPasswordView = new ForgotPasswordView();
//        ForgotPasswordController controller = new ForgotPasswordController(forgotPasswordView);
//        controller.bind();
//        MasterWindow window = new MasterWindow();
//        window.setContent(forgotPasswordView.asWidget(), "Forgot Password");
//        window.setModal(true);
//        window.setPixelSize(300, 150);
//        window.show();
//        controller.setContentWindow(window);
//      }
//    });
  }

}
