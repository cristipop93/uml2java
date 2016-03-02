package com.uml2Java.client.login.register;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.widget.core.client.Window;
import com.sencha.gxt.widget.core.client.box.AlertMessageBox;
import com.sencha.gxt.widget.core.client.box.MessageBox;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.form.PasswordField;
import com.sencha.gxt.widget.core.client.form.TextField;
import com.uml2Java.client.service.UsersService;
import com.uml2Java.client.service.UsersServiceAsync;
import com.uml2Java.client.uml2javaUtils.TextUtil;

/**
 * Created by Cristi on 2/5/2016.
 */
public class RegisterController {
  public interface IRegisterView {
    TextField getNameField();

    PasswordField getPasswordField();

    PasswordField getConfirmPasswordField();

    TextField getEmailField();

    TextButton getApplyButton();

    TextButton getCancelButton();

    Widget asWidget();
  }

  private Window window;
  private IRegisterView view;
  private UsersServiceAsync usersService = GWT.create(UsersService.class);

  public RegisterController(IRegisterView view) {
    this.view = view;
    addListeners();
  }

  private void addListeners() {
    view.getApplyButton().addSelectHandler(new SelectEvent.SelectHandler() {
      @Override
      public void onSelect(SelectEvent event) {
        register();
      }
    });

    view.getCancelButton().addSelectHandler(new SelectEvent.SelectHandler() {
      @Override
      public void onSelect(SelectEvent event) {
        window.hide();
      }
    });
  }

  private void register() {
    String username = view.getNameField().getValue();
    String password = view.getPasswordField().getValue();
    String confirmPassword = view.getConfirmPasswordField().getValue();
    String email = view.getEmailField().getValue();

    if (isValid(username, password, confirmPassword, email)) {
      usersService.register(username, password, email, new AsyncCallback<Void>() {
        @Override
        public void onFailure(Throwable caught) {
          new AlertMessageBox("Error", caught.getMessage()).show();
        }

        @Override
        public void onSuccess(Void result) {
          new MessageBox("Info", "Registration successful").show();
          window.hide();
        }
      });
    }

  }

  private boolean isValid(String username, String password, String confirmPassword, String email) {
    if (TextUtil.isEmptyString(username)) {
      new MessageBox("Info", "User name can not be empty").show();
      return false;
    }
    if (TextUtil.isEmptyString(password)) {
      new MessageBox("Info", "Password can not be empty").show();
      return false;
    }
    if(TextUtil.isEmptyString(confirmPassword) || !password.equals(confirmPassword)) {
      new MessageBox("Info", "Password and the confirmed password does not match").show();
      return false;
    }
    if (TextUtil.isEmptyString(email)) {
      new MessageBox("Info", "Email can not be empty").show();
      return false;
    }
    if (!email.matches("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")) {
      new MessageBox("Info", "Invalid email address").show();
      return false;
    }

    return true;
  }

  public void setContentWindow(Window window) {
    this.window = window;
  }
}
