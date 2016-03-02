package com.uml2Java.client;

import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.chart.client.draw.DrawComponent;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.core.client.util.ToggleGroup;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.button.ToggleButton;
import com.sencha.gxt.widget.core.client.container.*;
import com.uml2Java.client.utilitiesPanels.rightPanel.RightPanelController;
import com.uml2Java.client.utilitiesPanels.rightPanel.RightPanelView;

/**
 * Created by Cristi on 2/7/2016.
 */
public class Uml2JavaView implements Uml2JavaController.IUml2JavaView{
  private BorderLayoutContainer mainContainer;
  private ToggleButton selectButton;
  private ToggleButton classButton;

  private DrawComponent drawComponent;
  private CenterLayoutContainer centerLayoutContainer;
  private ContentPanel rightPanel;


  public Uml2JavaView() {
    initGUI();
  }
  private void initGUI() {
    mainContainer = new BorderLayoutContainer();

    ContentPanel leftPanel = new ContentPanel();

    leftPanel.setHeaderVisible(true);
    leftPanel.setHeadingHtml("<div align='center'>Utilities</div>");
    leftPanel.setAnimCollapse(true);
    leftPanel.setResize(false);
    VBoxLayoutContainer vBoxLayoutContainer = new VBoxLayoutContainer();
    leftPanel.add(vBoxLayoutContainer);

    ToggleGroup group = new ToggleGroup();
    selectButton = new ToggleButton("Select");
    classButton = new ToggleButton("Class");
    group.add(selectButton);
    group.add(classButton);
    group.setValue(selectButton);

    vBoxLayoutContainer.add(selectButton, new BoxLayoutContainer.BoxLayoutData(new Margins(5,0,0,10)));
    vBoxLayoutContainer.add(classButton, new BoxLayoutContainer.BoxLayoutData(new Margins(5,0,0,10)));

    //TODO add utilities to left panel

    BorderLayoutContainer.BorderLayoutData layoutData = new BorderLayoutContainer.BorderLayoutData(250);
    layoutData.setSplit(false);
    layoutData.setCollapsible(false);
    layoutData.setCollapseMini(false);
    layoutData.setMinSize(150);
    layoutData.setMaxSize(450);
    layoutData.setMargins(new Margins(0, 5, 0, 0));
    mainContainer.setWestWidget(leftPanel, layoutData);

    rightPanel = new ContentPanel();
    layoutData = new BorderLayoutContainer.BorderLayoutData(250);
    layoutData.setSplit(true);
    layoutData.setCollapsible(true);
    layoutData.setCollapseMini(false);
    layoutData.setMinSize(150);
    layoutData.setMaxSize(450);
    layoutData.setMargins(new Margins(0, 0, 0, 5));
    rightPanel.setHeaderVisible(true);
    rightPanel.setHeadingHtml("<div align='center'>Properties</div>");
    rightPanel.setAnimCollapse(true);
    rightPanel.setStyleName("panelBackground");
    mainContainer.setEastWidget(rightPanel, layoutData);

    drawComponent = new DrawComponent();
    drawComponent.setAllowTextSelection(false);

    centerLayoutContainer = new CenterLayoutContainer();

    mainContainer.setCenterWidget(drawComponent);
  }

  @Override
  public DrawComponent getDrawComponent() {
    return drawComponent;
  }

  @Override
  public CenterLayoutContainer getCenterLayoutContainer() {
    return centerLayoutContainer;
  }

  @Override
  public ToggleButton getSelectButton() {
    return selectButton;
  }

  @Override
  public ToggleButton getClassButton() {
    return classButton;
  }

  @Override
  public ContentPanel getRightPanel() {
    return rightPanel;
  }

  @Override
  public Widget asWidget(){
    return mainContainer;
  }


}
