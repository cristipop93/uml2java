package com.uml2Java.client.toolbar;

import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.core.client.util.ToggleGroup;
import com.sencha.gxt.widget.core.client.Slider;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.button.ToggleButton;
import com.sencha.gxt.widget.core.client.container.BoxLayoutContainer;
import com.sencha.gxt.widget.core.client.container.HBoxLayoutContainer;
import com.uml2Java.client.MainController;

/**
 * Created by Cristi on 2/13/2016.
 */
public class ToolbarView {
  private HBoxLayoutContainer toolbar;
  private Slider zoomSlider;
  private TextButton logoutButton;
  private ToggleButton domainButton;
  private ToggleButton siteButton;
  private TextButton playButton;

  public ToolbarView() {
    initGUI();
  }

  private void initGUI() {
    BoxLayoutContainer.BoxLayoutData flex = new BoxLayoutContainer.BoxLayoutData(new Margins(0, 5, 0, 20));
    BoxLayoutContainer.BoxLayoutData flex3 = new BoxLayoutContainer.BoxLayoutData(new Margins(0, 5, 0, 20));
    flex.setFlex(1);
    flex3.setFlex(3);
    toolbar = new HBoxLayoutContainer();
    toolbar.setHBoxLayoutAlign(HBoxLayoutContainer.HBoxLayoutAlign.MIDDLE);

    domainButton = new ToggleButton("Domain Model");
    siteButton = new ToggleButton("Site View");
    playButton = new TextButton("Play");
    playButton.setIcon(MainController.ICONS.play());
    domainButton.setAllowDepress(false);
    siteButton.setAllowDepress(false);
    ToggleGroup group = new ToggleGroup();
    group.add(domainButton);
    group.add(siteButton);
    group.setValue(domainButton);

    toolbar.add(new Label(""), flex);
    toolbar.add(domainButton, flex);
    toolbar.add(siteButton, flex);
    toolbar.add(playButton, flex);
    toolbar.add(new Label(""), flex3);

    zoomSlider = new Slider();
    zoomSlider.setIncrement(1);
    zoomSlider.setMaxValue(150);
    zoomSlider.setMinValue(50);
    zoomSlider.setShowMessage(true);
    zoomSlider.setValue(100);
    toolbar.add(new Image(MainController.ICONS.zoomOut()),
        new BoxLayoutContainer.BoxLayoutData(new Margins(0, 5, 0, 0)));
    toolbar.add(zoomSlider, new BoxLayoutContainer.BoxLayoutData(new Margins(0, 0, 0, 0)));
    toolbar.add(new Image(MainController.ICONS.zoomIn()), new BoxLayoutContainer.BoxLayoutData(new Margins(0, 30, 3, 5)));
    logoutButton = new TextButton("Log out", MainController.ICONS.logout());
    toolbar.add(logoutButton, new BoxLayoutContainer.BoxLayoutData(new Margins(0, 0, 0, 0)));

  }

  public Widget asWidget(){
    return toolbar;
  }

  public Slider getZoomSlider() {
    return zoomSlider;
  }

  public TextButton getLogoutButton() {
    return logoutButton;
  }

  public ToggleButton getDomainButton() {
    return domainButton;
  }

  public ToggleButton getSiteButton() {
    return siteButton;
  }

  public TextButton getPlayButton() {
    return playButton;
  }
}
