package com.uml2Java.client.siteView;

import com.google.gwt.event.dom.client.*;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.chart.client.draw.DrawComponent;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer;
import com.sencha.gxt.widget.core.client.container.HBoxLayoutContainer;
import com.uml2Java.client.siteView.shapes.PageShape;
import com.uml2Java.client.siteView.shapes.SiteShapes;
import com.uml2Java.client.siteView.siteUtils.SiteMouseState;
import com.uml2Java.client.siteView.utilitiesPanels.leftPanel.LeftSitePanelController;
import com.uml2Java.client.siteView.utilitiesPanels.leftPanel.LeftSiteView;
import com.uml2Java.client.toolbar.ToolbarView;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by Cristi on 3/21/2016.
 */
public class SiteViewController {
  public interface ISiteView {
    Widget asWidget();
    DrawComponent getDrawComponent();
    ContentPanel getLeftPanel();
  }
  private static Logger log = Logger.getLogger(SiteViewController.class.getName());
  private static SiteViewController INSTANCE;
  private static ISiteView view;
  private SiteMouseState siteMouseState = SiteMouseState.SELECT;
  private BorderLayoutContainer mainContainer;
  private HBoxLayoutContainer buttonsContainer;
  private List<SiteShapes> siteShapes;
  private ToolbarView toolbarView;
  private SiteShapes clickedShape = null;
  private int lastClickX, lastClickY;
  private int lastDraggedX, lastDraggedY;
  private double scaleFactor = 1; // TODO load from users saved

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
    LeftSitePanelController.ILeftSitePanelView leftSitePanelView = new LeftSiteView();
    LeftSitePanelController leftSitePanelController = new LeftSitePanelController(leftSitePanelView);
    view.getLeftPanel().add(leftSitePanelView.asWidget());
    siteShapes = new ArrayList<SiteShapes>();
    addListeners();
  }

  private void addListeners() {
//    PageShape page1 = new PageShape(view.getDrawComponent(), 200, 200, 200, 90, "");
//    log.info("page1: " + page1.getId());
//    siteShapes.add(page1);
    MouseDownHandler mouseDownHandler = new MouseDownHandler() {
      @Override
      public void onMouseDown(MouseDownEvent event) {
        if (siteMouseState == SiteMouseState.SELECT) {
          for (SiteShapes shape : siteShapes) {
            if (shape.canBeDragged(event.getRelativeX(view.getDrawComponent().getElement()),
                event.getRelativeY(view.getDrawComponent().getElement()))) {
              clickedShape = shape;
              lastClickX = event.getRelativeX(view.getDrawComponent().getElement());
              lastClickY = event.getRelativeY(view.getDrawComponent().getElement());
              lastDraggedX = shape.getX();
              lastDraggedY = shape.getY();
              //TODO display popup with info about that shape

              return;
            }
          }
        } else if (siteMouseState == SiteMouseState.PAGE) {
          PageShape tempShape = new PageShape(view.getDrawComponent(),
              event.getRelativeX(view.getDrawComponent().getElement()),
              event.getRelativeY(view.getDrawComponent().getElement()), 100, 100, "");
          tempShape.scaleTo(scaleFactor);
          siteShapes.add(tempShape);
          view.getDrawComponent().redrawSurface();
        }
      }
    };

    MouseMoveHandler mouseMoveHandler = new MouseMoveHandler() {
      @Override
      public void onMouseMove(MouseMoveEvent event) {
        if (siteMouseState == SiteMouseState.SELECT) {
          if (clickedShape == null) {
            boolean found = false;
            for (SiteShapes shape : siteShapes) {
              if (shape.canBeDragged(event.getRelativeX(view.getDrawComponent().getElement()),
                  event.getRelativeY(view.getDrawComponent().getElement()))) {
                DOM.setStyleAttribute(RootPanel.getBodyElement(), "cursor", "hand");
                found = true;
                break;
              }
            }
            if (!found) {
              DOM.setStyleAttribute(RootPanel.getBodyElement(), "cursor", "default");
            }
          }
        }
        if (clickedShape != null) {
          int mouseX = event.getRelativeX(view.getDrawComponent().getElement());
          int mouseY = event.getRelativeY(view.getDrawComponent().getElement());
          if (mouseX > 0 && mouseX < view.getDrawComponent().getOffsetWidth() && mouseY > 0 && mouseY < view
              .getDrawComponent().getOffsetHeight()) {
            clickedShape.translateTo(lastDraggedX - (lastClickX - mouseX), lastDraggedY - (lastClickY - mouseY));
            view.getDrawComponent().redrawSurfaceForced();
          } else {
            clickedShape = null;
          }
        }
      }
    };

    MouseUpHandler mouseUpHandler = new MouseUpHandler() {
      @Override
      public void onMouseUp(MouseUpEvent event) {
        clickedShape = null;
      }
    };

    view.getDrawComponent().addDomHandler(mouseDownHandler, MouseDownEvent.getType());
    view.getDrawComponent().addDomHandler(mouseUpHandler, MouseUpEvent.getType());
    view.getDrawComponent().addDomHandler(mouseMoveHandler, MouseMoveEvent.getType());

    toolbarView.getZoomSlider().addValueChangeHandler(new ValueChangeHandler<Integer>() {
      @Override
      public void onValueChange(ValueChangeEvent<Integer> event) {
        scaleFactor = (double) event.getValue() / 100d;
        log.info(scaleFactor + "");
        for (SiteShapes siteShape : siteShapes) {
          siteShape.scaleTo(scaleFactor);
        }
        view.getDrawComponent().redrawSurface();
      }
    });

  }

  public static ISiteView getView() {
    return view;
  }

  public SiteMouseState getSiteMouseState() {
    return siteMouseState;
  }

  public void setSiteMouseState(SiteMouseState siteMouseState) {
    this.siteMouseState = siteMouseState;
  }
}
