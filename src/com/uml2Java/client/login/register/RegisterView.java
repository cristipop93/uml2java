package com.uml2Java.client.login.register;

import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.BoxLayoutContainer;
import com.sencha.gxt.widget.core.client.container.HBoxLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VBoxLayoutContainer;
import com.sencha.gxt.widget.core.client.form.FieldLabel;
import com.sencha.gxt.widget.core.client.form.PasswordField;
import com.sencha.gxt.widget.core.client.form.TextField;
import com.uml2Java.client.MainController;
import com.uml2Java.client.domainModel.UmlController;

/**
 * Created by Cristi on 2/5/2016.
 */
public class RegisterView implements RegisterController.IRegisterView {
  private VBoxLayoutContainer mainContainer;
  private TextField nameField;
  private PasswordField passwordField;
  private PasswordField confirmPasswordField;
  private TextField emailField;
  private TextButton applyButton;
  private TextButton cancelButton;

  public RegisterView(){
    initGUI();
  }

  private void initGUI() {
    mainContainer = new VBoxLayoutContainer();
    nameField = new TextField();
    passwordField = new PasswordField();
    confirmPasswordField = new PasswordField();
    emailField = new TextField();
    applyButton = new TextButton("Register", MainController.ICONS.apply());
    cancelButton = new TextButton("Cancel", MainController.ICONS.cancel());

    mainContainer.setVBoxLayoutAlign(VBoxLayoutContainer.VBoxLayoutAlign.CENTER);
    mainContainer.add(new FieldLabel(nameField, "Username"),
        new BoxLayoutContainer.BoxLayoutData(new Margins(8, 0, 5, 0)));
    mainContainer.add(new FieldLabel(passwordField, "Password"),
        new BoxLayoutContainer.BoxLayoutData(new Margins(0, 0, 5, 0)));
    mainContainer.add(new FieldLabel(confirmPasswordField, "Confirm password"), new BoxLayoutContainer.BoxLayoutData(new Margins(0,0,5,0)));
    mainContainer.add(new FieldLabel(emailField, "Email"), new BoxLayoutContainer.BoxLayoutData(new Margins(0,0,5,0)));
    HBoxLayoutContainer buttonsContainer = new HBoxLayoutContainer();
//    BoxLayoutContainer.BoxLayoutData flex = new BoxLayoutContainer.BoxLayoutData(new Margins(0));
//    flex.setFlex(1);
    buttonsContainer.setHBoxLayoutAlign(HBoxLayoutContainer.HBoxLayoutAlign.MIDDLE);
    buttonsContainer.setPack(BoxLayoutContainer.BoxLayoutPack.CENTER);
    buttonsContainer.add(applyButton, new BoxLayoutContainer.BoxLayoutData(new Margins(0, 10, 0, 0)));
    buttonsContainer.add(cancelButton, new BoxLayoutContainer.BoxLayoutData(new Margins(0,0,0,10)));
    mainContainer.add(buttonsContainer, new BoxLayoutContainer.BoxLayoutData(new Margins(10,0,0,0)));

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
  public PasswordField getConfirmPasswordField() {
    return confirmPasswordField;
  }

  @Override
  public TextField getEmailField() {
    return emailField;
  }

  @Override
  public TextButton getApplyButton() {
    return applyButton;
  }

  @Override
  public TextButton getCancelButton() {
    return cancelButton;
  }

  @Override
  public Widget asWidget() {
    return mainContainer;
  }


}
