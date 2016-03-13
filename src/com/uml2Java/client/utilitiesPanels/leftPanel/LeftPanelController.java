package com.uml2Java.client.utilitiesPanels.leftPanel;

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.widget.core.client.button.ToggleButton;
import com.uml2Java.client.Uml2JavaController;
import com.uml2Java.client.uml2javaUtils.MouseState;

/**
 * Created by Cristi on 3/13/2016.
 */
public class LeftPanelController {
  public interface ILeftPanelView {
    Widget asWidget();
    ToggleButton getSelectButton();
    ToggleButton getClassButton();
    ToggleButton getInterfaceButton();
  }
  ILeftPanelView view;

  public LeftPanelController(ILeftPanelView view) {
    this.view = view;
    addListeners();
  }

  private void addListeners() {
    view.getSelectButton().addValueChangeHandler(new ValueChangeHandler<Boolean>() {
      @Override
      public void onValueChange(ValueChangeEvent<Boolean> event) {
        Uml2JavaController.getInstance().setMouseState(MouseState.SELECT);
      }
    });
    view.getClassButton().addValueChangeHandler(new ValueChangeHandler<Boolean>() {
      @Override
      public void onValueChange(ValueChangeEvent<Boolean> event) {
        Uml2JavaController.getInstance().setMouseState(MouseState.CLASS);
      }
    });
    view.getInterfaceButton().addValueChangeHandler(new ValueChangeHandler<Boolean>() {
      @Override
      public void onValueChange(ValueChangeEvent<Boolean> event) {
        Uml2JavaController.getInstance().setMouseState(MouseState.INTERFACE);
      }
    });
  }
}
