package com.uml2Java.client.toolbar;

import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.widget.core.client.Slider;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.BoxLayoutContainer;
import com.sencha.gxt.widget.core.client.container.HBoxLayoutContainer;
import com.uml2Java.client.Uml2Java;
import com.uml2Java.client.Uml2JavaController;

/**
 * Created by Cristi on 2/13/2016.
 */
public class ToolbarView {
  private HBoxLayoutContainer toolbar;
  private Slider zoomSlider;
  private TextButton logoutButton;

  public ToolbarView() {
    initGUI();
  }

  private void initGUI() {
    toolbar = new HBoxLayoutContainer();
    toolbar.setHBoxLayoutAlign(HBoxLayoutContainer.HBoxLayoutAlign.MIDDLE);
    zoomSlider = new Slider();
    zoomSlider.setIncrement(1);
    zoomSlider.setMaxValue(150);
    zoomSlider.setMinValue(50);
    zoomSlider.setShowMessage(true);
    zoomSlider.setValue(100);

    toolbar.add(new Image(Uml2JavaController.ICONS.smallClass()),
        new BoxLayoutContainer.BoxLayoutData(new Margins(0, 5, 0, 0)));
    toolbar.add(zoomSlider, new BoxLayoutContainer.BoxLayoutData(new Margins(0, 0, 0, 0)));
    toolbar.add(new Image(Uml2JavaController.ICONS.largeClass()), new BoxLayoutContainer.BoxLayoutData(new Margins(0, 100, 3, 5)));
    logoutButton = new TextButton("Log out", Uml2JavaController.ICONS.logout());
    toolbar.add(logoutButton, new BoxLayoutContainer.BoxLayoutData(new Margins(0, 250, 0, 0)));



  }

  public Widget asWidget(){
    return toolbar;
  }

  public Slider getZoomSlider() {
    return zoomSlider;
  }
}
