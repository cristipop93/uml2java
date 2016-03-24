package com.uml2Java.client.siteView.utilitiesPanels.leftPanel;

import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.core.client.util.ToggleGroup;
import com.sencha.gxt.widget.core.client.button.ToggleButton;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;

/**
 * Created by Cristi on 3/22/2016.
 */
public class LeftSiteView implements LeftSitePanelController.ILeftSitePanelView {
  private VerticalLayoutContainer mainContainer;
  private ToggleButton selectButton;
  private ToggleButton pageButton;
  private ToggleButton simpleListButton;

  public LeftSiteView() {
    initGUI();
  }

  private void initGUI() {
    mainContainer = new VerticalLayoutContainer();
    selectButton = new ToggleButton("Select");
    selectButton.setAllowDepress(false);
    pageButton = new ToggleButton("Page");
    pageButton.setAllowDepress(false);
    simpleListButton = new ToggleButton("Simple List");
    simpleListButton.setAllowDepress(false);
    ToggleGroup group = new ToggleGroup();
    group.add(selectButton);
    group.add(pageButton);
    group.add(simpleListButton);
    group.setValue(selectButton);

    mainContainer.add(selectButton, new VerticalLayoutContainer.VerticalLayoutData(-1, -1, new Margins(5)));
    mainContainer.add(pageButton, new VerticalLayoutContainer.VerticalLayoutData(-1, -1, new Margins(5)));
    mainContainer.add(simpleListButton, new VerticalLayoutContainer.VerticalLayoutData(-1, -1, new Margins(5)));
  }

  @Override
  public ToggleButton getSelectButton() {
    return selectButton;
  }

  @Override
  public ToggleButton getPageButton() {
    return pageButton;
  }

  @Override
  public ToggleButton getSimpleListButton() {
    return simpleListButton;
  }

  @Override
  public Widget asWidget() {
    return mainContainer;
  }
}
