package com.uml2Java.client.login;

import com.google.gwt.dom.client.Style;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.*;
import com.sencha.gxt.widget.core.client.form.FieldLabel;
import com.sencha.gxt.widget.core.client.form.PasswordField;
import com.sencha.gxt.widget.core.client.form.TextField;
import com.uml2Java.client.MainController;
import com.uml2Java.client.domainModel.UmlController;

/**
 * Created by Cristi on 2/4/2016.
 */
public class LoginView implements LoginController.ILoginView {
  private TextField nameField;
  private PasswordField passwordField;
  private TextButton loginButton;
  private TextButton registerButton;
  private FieldLabel loginFailedLabel;
  private Label forgotPasswordButton;

  private BorderLayoutContainer mainContainer;
  private VBoxLayoutContainer vBoxLayoutContainer;
  private BoxLayoutContainer.BoxLayoutData layoutData;
  private Label label;

  public LoginView() {
    initGUI();
  }

  private void initGUI() {
    /* initialize fields */
    nameField = new TextField();
    passwordField = new PasswordField();
    loginButton = new TextButton("Login", MainController.ICONS.login());
    registerButton = new TextButton("Register", MainController.ICONS.register());
    mainContainer = new BorderLayoutContainer();
    label = new Label("");
    loginFailedLabel = new FieldLabel(label);
    loginFailedLabel.getElement().getStyle().setColor("red");
    forgotPasswordButton = new Label("Forgot Password");
    forgotPasswordButton.getElement().getStyle().setCursor(Style.Cursor.POINTER);
    forgotPasswordButton.setStyleName("forgotPasswordLabelBackground");
    CenterLayoutContainer centerLayoutContainer = new CenterLayoutContainer();
    vBoxLayoutContainer = new VBoxLayoutContainer();
    HBoxLayoutContainer buttonsContainer = new HBoxLayoutContainer();

    /* buttons container */
//    buttonsContainer.setHBoxLayoutAlign(HBoxLayoutContainer.HBoxLayoutAlign.MIDDLE);
//    buttonsContainer.add(forgotPasswordButton, new BoxLayoutContainer.BoxLayoutData(new Margins(0)));
    BoxLayoutContainer.BoxLayoutData flex = new BoxLayoutContainer.BoxLayoutData(new Margins(0));
    flex.setFlex(1);
    buttonsContainer.add(new Label(), flex);
    buttonsContainer.add(registerButton, new BoxLayoutContainer.BoxLayoutData(new Margins(0,5,0,0)));
    buttonsContainer.add(loginButton, new BoxLayoutContainer.BoxLayoutData(new Margins(0,8,0,0)));

    /* form */
    layoutData = new BoxLayoutContainer.BoxLayoutData(new Margins(5, 0, 0, 0));
    vBoxLayoutContainer.setVBoxLayoutAlign(VBoxLayoutContainer.VBoxLayoutAlign.STRETCH);
    vBoxLayoutContainer.add(new FieldLabel(nameField, "User"), layoutData);
    vBoxLayoutContainer.add(new FieldLabel(passwordField, "Password"), layoutData);
    vBoxLayoutContainer.add(buttonsContainer, layoutData);
    vBoxLayoutContainer.add(loginFailedLabel, layoutData);
    centerLayoutContainer.add(vBoxLayoutContainer);

    mainContainer.setCenterWidget(centerLayoutContainer);
    mainContainer.setStyleName("mainContainer");
  }

  @Override
  public TextField getNameField() {
    return nameField;
  }

  @Override
  public PasswordField getPasswordField() {
    return passwordField;
  }

  @Override
  public TextButton getLoginButton() {
    return loginButton;
  }

  @Override
  public TextButton getRegisterButton() {
    return registerButton;
  }

  @Override
  public void setErrorLabelText(String val) {
    if (vBoxLayoutContainer.getWidgetIndex(loginFailedLabel) >= 0)
      vBoxLayoutContainer.remove(loginFailedLabel);
    label.setText(val);
    vBoxLayoutContainer.add(loginFailedLabel, layoutData);
    vBoxLayoutContainer.forceLayout();
  }

  @Override
  public FieldLabel getLoginFailedLabel() {
    return loginFailedLabel;
  }

  @Override
  public Widget asWidget() {
    return mainContainer;
  }

  @Override
  public Label getForgotPasswordButton() {
    return forgotPasswordButton;
  }

}
