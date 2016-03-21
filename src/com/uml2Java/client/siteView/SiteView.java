package com.uml2Java.client.siteView;

import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.chart.client.draw.DrawComponent;
import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer;

/**
 * Created by Cristi on 3/21/2016.
 */
public class SiteView implements SiteViewController.ISiteView {
  private DrawComponent drawComponent;
  private BorderLayoutContainer mainContainer;


  public SiteView() {
    initGUI();
  }

  private void initGUI() {
    mainContainer = new BorderLayoutContainer();
  }

  @Override
  public Widget asWidget() {
    return mainContainer;
  }

  @Override
  public DrawComponent getDrawComponent() {
    return drawComponent;
  }
}
