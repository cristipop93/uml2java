package com.uml2Java.client.domainModel.utilitiesPanels.leftPanel;

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.widget.core.client.button.ToggleButton;
import com.uml2Java.client.domainModel.UmlController;
import com.uml2Java.client.domainModel.uml2javaUtils.DomainMouseState;

/**
 * Created by Cristi on 3/13/2016.
 */
public class LeftDomainPanelController {
  public interface ILeftPanelView {
    Widget asWidget();
    ToggleButton getSelectButton();
    ToggleButton getClassButton();
    ToggleButton getInterfaceButton();
  }
  ILeftPanelView view;

  public LeftDomainPanelController(ILeftPanelView view) {
    this.view = view;
    addListeners();
  }

  private void addListeners() {
    view.getSelectButton().addValueChangeHandler(new ValueChangeHandler<Boolean>() {
      @Override
      public void onValueChange(ValueChangeEvent<Boolean> event) {
        UmlController.getInstance().setDomainMouseState(DomainMouseState.SELECT);
      }
    });
    view.getClassButton().addValueChangeHandler(new ValueChangeHandler<Boolean>() {
      @Override
      public void onValueChange(ValueChangeEvent<Boolean> event) {
        UmlController.getInstance().setDomainMouseState(DomainMouseState.CLASS);
      }
    });
    view.getInterfaceButton().addValueChangeHandler(new ValueChangeHandler<Boolean>() {
      @Override
      public void onValueChange(ValueChangeEvent<Boolean> event) {
        UmlController.getInstance().setDomainMouseState(DomainMouseState.INTERFACE);
      }
    });
  }
}
