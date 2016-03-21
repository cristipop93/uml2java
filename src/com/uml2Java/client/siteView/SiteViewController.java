package com.uml2Java.client.siteView;

import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.chart.client.draw.DrawComponent;
import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer;
import com.sencha.gxt.widget.core.client.container.HBoxLayoutContainer;
import com.uml2Java.client.toolbar.ToolbarView;

import java.util.logging.Logger;

/**
 * Created by Cristi on 3/21/2016.
 */
public class SiteViewController {
  public interface ISiteView {
    Widget asWidget();
    DrawComponent getDrawComponent();
  }
  private static Logger log = Logger.getLogger(SiteViewController.class.getName());
  private static SiteViewController INSTANCE;
  private static ISiteView view;
  private BorderLayoutContainer mainContainer;
  private HBoxLayoutContainer buttonsContainer;
  private ToolbarView toolbarView;

  public static SiteViewController getInstance() {
    if (INSTANCE == null) {
      log.info("on create siteView");
      INSTANCE = new SiteViewController();
      view = new SiteView();
    }
    return INSTANCE;
  }

  public void setViews(BorderLayoutContainer mainContainer, HBoxLayoutContainer buttonsContainer,
      ToolbarView toolbarView) {
    this.mainContainer = mainContainer;
    this.buttonsContainer = buttonsContainer;
    this.toolbarView = toolbarView;
  }

  public void initMainWindow() {

  }

  public static ISiteView getView() {
    return view;
  }
}
