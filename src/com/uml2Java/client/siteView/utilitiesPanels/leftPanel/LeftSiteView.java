package com.uml2Java.client.siteView.utilitiesPanels.leftPanel;

import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.core.client.util.ToggleGroup;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.button.ToggleButton;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.uml2Java.client.MainController;

/**
 * Created by Cristi on 3/22/2016.
 */
public class LeftSiteView implements LeftSitePanelController.ILeftSitePanelView {
  private VerticalLayoutContainer mainContainer;
  private ToggleButton selectButton;
  private ToggleButton pageButton;
  private ToggleButton simpleListButton;
  private ToggleButton formButton;
  private ToggleButton detailsButton;
  private ToggleButton actionButton;
  private ToggleButton flowButton;
  private ToggleButton okflowButton;
  private ToggleButton koflowButton;

  public LeftSiteView() {
    initGUI();
  }

  private void initGUI() {
    mainContainer = new VerticalLayoutContainer();
    selectButton = new ToggleButton();
    selectButton.setIcon(MainController.ICONS.select());
    selectButton.setToolTip("Select");
    selectButton.setAllowDepress(false);
    pageButton = new ToggleButton();
    pageButton.setAllowDepress(false);
    pageButton.setIcon(MainController.ICONS.page());
    pageButton.setToolTip("Page");
    simpleListButton = new ToggleButton();
    simpleListButton.setAllowDepress(false);
    simpleListButton.setIcon(MainController.ICONS.simpleList());
    simpleListButton.setToolTip("Simple List");
    formButton = new ToggleButton();
    formButton.setAllowDepress(false);
    formButton.setIcon(MainController.ICONS.form());
    formButton.setToolTip("Form");
    detailsButton = new ToggleButton();
    detailsButton.setAllowDepress(false);
    detailsButton.setIcon(MainController.ICONS.details());
    detailsButton.setToolTip("Details");
    actionButton = new ToggleButton();
    actionButton.setAllowDepress(false);
    actionButton.setIcon(MainController.ICONS.action());
    actionButton.setToolTip("Action");
    flowButton = new ToggleButton();
    flowButton.setAllowDepress(false);
    flowButton.setIcon(MainController.ICONS.flow());
    flowButton.setToolTip("Flow");
    okflowButton = new ToggleButton();
    okflowButton.setAllowDepress(false);
    okflowButton.setIcon(MainController.ICONS.okFlow());
    okflowButton.setToolTip("OK Flow");
    koflowButton = new ToggleButton();
    koflowButton.setAllowDepress(false);
    koflowButton.setIcon(MainController.ICONS.koFlow());
    koflowButton.setToolTip("KO Flow");
    ToggleGroup group = new ToggleGroup();
    group.add(selectButton);
    group.add(pageButton);
    group.add(simpleListButton);
    group.add(formButton);
    group.add(detailsButton);
    group.add(actionButton);
    group.add(flowButton);
    group.add(okflowButton);
    group.add(koflowButton);

    mainContainer.add(selectButton, new VerticalLayoutContainer.VerticalLayoutData(-1, -1, new Margins(5)));
    mainContainer.add(pageButton, new VerticalLayoutContainer.VerticalLayoutData(-1, -1, new Margins(5)));
    mainContainer.add(simpleListButton, new VerticalLayoutContainer.VerticalLayoutData(-1, -1, new Margins(5)));
    mainContainer.add(formButton, new VerticalLayoutContainer.VerticalLayoutData(-1, -1, new Margins(5)));
    mainContainer.add(detailsButton, new VerticalLayoutContainer.VerticalLayoutData(-1, -1, new Margins(5)));
    mainContainer.add(actionButton, new VerticalLayoutContainer.VerticalLayoutData(-1, -1, new Margins(5)));
    mainContainer.add(flowButton, new VerticalLayoutContainer.VerticalLayoutData(-1, -1, new Margins(5)));
    mainContainer.add(okflowButton, new VerticalLayoutContainer.VerticalLayoutData(-1, -1, new Margins(5)));
    mainContainer.add(koflowButton, new VerticalLayoutContainer.VerticalLayoutData(-1, -1, new Margins(5)));
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
  public ToggleButton getFormButton() {
    return formButton;
  }

  @Override
  public ToggleButton getDetailsButton() {
    return detailsButton;
  }

  @Override
  public ToggleButton getActionButton() {
    return actionButton;
  }

  @Override
  public ToggleButton getFlowButton() {
    return flowButton;
  }

  @Override
  public ToggleButton getOKFlowButton() {
    return okflowButton;
  }

  @Override
  public ToggleButton getKOFlowButton() {
    return koflowButton;
  }

  @Override
  public Widget asWidget() {
    return mainContainer;
  }
}
