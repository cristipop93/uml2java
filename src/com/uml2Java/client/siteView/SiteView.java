package com.uml2Java.client.siteView;

import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.chart.client.draw.DrawComponent;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer;

/**
 * Created by Cristi on 3/21/2016.
 */
public class SiteView implements SiteViewController.ISiteView {
  private DrawComponent drawComponent;
  private BorderLayoutContainer mainContainer;
  private ContentPanel leftPanel;


  public SiteView() {
    initGUI();
  }

  private void initGUI() {
    mainContainer = new BorderLayoutContainer();
    leftPanel = new ContentPanel();
    leftPanel.setHeaderVisible(true);
    leftPanel.setHeadingHtml("<div align='center'>Utils</div>");
    leftPanel.setResize(false);
    leftPanel.setBorders(false);
    leftPanel.setBodyBorder(false);

    BorderLayoutContainer.BorderLayoutData layoutData = new BorderLayoutContainer.BorderLayoutData(70);
    layoutData.setSplit(false);
    layoutData.setCollapsible(false);
    layoutData.setCollapseMini(false);
    layoutData.setMaxSize(150);
    layoutData.setMinSize(50);
    layoutData.setMargins(new Margins(0, 5, 0, 0));
    mainContainer.setWestWidget(leftPanel, layoutData);

    drawComponent = new DrawComponent();
    drawComponent.setAllowTextSelection(false);

    mainContainer.setCenterWidget(drawComponent);
  }

  @Override
  public Widget asWidget() {
    return mainContainer;
  }

  @Override
  public DrawComponent getDrawComponent() {
    return drawComponent;
  }

  @Override
  public ContentPanel getLeftPanel() {
    return leftPanel;
  }
}
