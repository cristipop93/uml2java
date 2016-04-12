package com.uml2Java.client.siteView.utilitiesPanels.leftPanel;

import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.widget.core.client.button.ToggleButton;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.uml2Java.client.siteView.SiteViewController;
import com.uml2Java.client.siteView.siteUtils.SiteMouseState;

/**
 * Created by Cristi on 3/22/2016.
 */
public class LeftSitePanelController {
  public interface ILeftSitePanelView {
    ToggleButton getSelectButton();
    ToggleButton getPageButton();
    ToggleButton getSimpleListButton();
    ToggleButton getFormButton();
    ToggleButton getDetailsButton();
    ToggleButton getActionButton();
    ToggleButton getFlowButton();
    Widget asWidget();
  }
  ILeftSitePanelView view;

  public LeftSitePanelController(ILeftSitePanelView view) {
    this.view = view;
    addListeners();
  }

  private void addListeners() {
    view.getSelectButton().addSelectHandler(new SelectEvent.SelectHandler() {
      @Override
      public void onSelect(SelectEvent event) {
        SiteViewController.getInstance().setSiteMouseState(SiteMouseState.SELECT);
      }
    });

    view.getPageButton().addSelectHandler(new SelectEvent.SelectHandler() {
      @Override
      public void onSelect(SelectEvent event) {
        SiteViewController.getInstance().setSiteMouseState(SiteMouseState.PAGE);
      }
    });

    view.getSimpleListButton().addSelectHandler(new SelectEvent.SelectHandler() {
      @Override
      public void onSelect(SelectEvent event) {
        SiteViewController.getInstance().setSiteMouseState(SiteMouseState.SIMPLE_LIST);
      }
    });

    view.getFormButton().addSelectHandler(new SelectEvent.SelectHandler() {
      @Override
      public void onSelect(SelectEvent event) {
        SiteViewController.getInstance().setSiteMouseState(SiteMouseState.FORM);
      }
    });

    view.getDetailsButton().addSelectHandler(new SelectEvent.SelectHandler() {
      @Override
      public void onSelect(SelectEvent event) {
        SiteViewController.getInstance().setSiteMouseState(SiteMouseState.DETAILS);
      }
    });

    view.getActionButton().addSelectHandler(new SelectEvent.SelectHandler() {
      @Override
      public void onSelect(SelectEvent event) {
        SiteViewController.getInstance().setSiteMouseState(SiteMouseState.ACTION);
      }
    });

    view.getFlowButton().addSelectHandler(new SelectEvent.SelectHandler() {
      @Override
      public void onSelect(SelectEvent event) {
        SiteViewController.getInstance().setSiteMouseState(SiteMouseState.FLOW);
      }
    });
  }
}
