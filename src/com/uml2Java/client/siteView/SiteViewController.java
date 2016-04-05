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
import com.uml2Java.client.siteView.shapes.*;
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
  private List<SiteShape> siteShapes;
  private ToolbarView toolbarView;
  private SiteShape clickedShape = null;
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
    siteShapes = new ArrayList<SiteShape>();
    addListeners();
  }

  private void addListeners() {
    PageShape page1 = new PageShape(view.getDrawComponent(), 200, 200, 200, 90, "");
    PageShape page2 = new PageShape(view.getDrawComponent(), 400, 500, 200, 90, "");
    ViewComponentShape list1 = new SimpleListShape(view.getDrawComponent(), 100, 100, 80, 90, "", "", page1);
//    log.info("page1: " + page1.getId());
    Flow flow1 = new NavigationFlow(page1, page2);
    page1.addFlow(flow1);
    page2.addFlow(flow1);
    Flow flow2 = new NavigationFlow(page2, list1);
    page2.addFlow(flow2);
    list1.addFlow(flow2);
    siteShapes.add(page1);
    siteShapes.add(page2);
    siteShapes.add(list1);
    MouseDownHandler mouseDownHandler = new MouseDownHandler() {
      @Override
      public void onMouseDown(MouseDownEvent event) {
        if (siteMouseState == SiteMouseState.SELECT) {
          int minArea = 20000 * 10000;
          for (SiteShape shape : siteShapes) {
            if (shape.canBeDragged(event.getRelativeX(view.getDrawComponent().getElement()),
                event.getRelativeY(view.getDrawComponent().getElement()))) {
              if (shape.getWidth() * shape.getHeight() < minArea) {
                minArea = shape.getWidth() * shape.getHeight();
                clickedShape = shape;
                lastClickX = event.getRelativeX(view.getDrawComponent().getElement());
                lastClickY = event.getRelativeY(view.getDrawComponent().getElement());
                lastDraggedX = shape.getX();
                lastDraggedY = shape.getY();
              }
              //TODO display popup with info about that shape
            }
          }
          return;
        }
        if (siteMouseState == SiteMouseState.PAGE) {
          addPage(event);
          return;
        }
        if (siteMouseState == SiteMouseState.ACTION) {
          addAction(event);
          return;
        }
        //  adding a component
        PageShape hoveredPage = null;
        for (SiteShape shape : siteShapes) {
          if (shape instanceof PageShape && shape.canBeDragged(event.getRelativeX(view.getDrawComponent().getElement()),
              event.getRelativeY(view.getDrawComponent().getElement()))) {
            DOM.setStyleAttribute(RootPanel.getBodyElement(), "cursor", "hand");
            hoveredPage = (PageShape) shape;
            break;
          }
        }

        if (siteMouseState == SiteMouseState.SIMPLE_LIST) {
          if (hoveredPage != null)
            addSimpleList(event, hoveredPage);
        } else if (siteMouseState == SiteMouseState.FORM) {
          if (hoveredPage != null)
            addForm(event, hoveredPage);
        } else if (siteMouseState == SiteMouseState.DETAILS) {
          if (hoveredPage != null)
            addDetails(event, hoveredPage);
        }
      }
    };

    MouseMoveHandler mouseMoveHandler = new MouseMoveHandler() {
      @Override
      public void onMouseMove(MouseMoveEvent event) {
        if (siteMouseState == SiteMouseState.SELECT) {
          if (clickedShape == null) {
            boolean found = false;
            for (SiteShape shape : siteShapes) {
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
        } else if (siteMouseState == SiteMouseState.SIMPLE_LIST || siteMouseState == SiteMouseState.FORM || siteMouseState == SiteMouseState.DETAILS) {
          boolean found = false;
          for (SiteShape shape : siteShapes) {
            if (shape instanceof PageShape && shape.canBeDragged(event.getRelativeX(view.getDrawComponent().getElement()),
                event.getRelativeY(view.getDrawComponent().getElement()))) {
              DOM.setStyleAttribute(RootPanel.getBodyElement(), "cursor", "hand");
              found = true;
              break;
            }
          }
          if (!found) {
            DOM.setStyleAttribute(RootPanel.getBodyElement(), "cursor", "no-drop");
          }
        } else {
          DOM.setStyleAttribute(RootPanel.getBodyElement(), "cursor", "default");
        }
        if (clickedShape != null) {
          int mouseX = event.getRelativeX(view.getDrawComponent().getElement());
          int mouseY = event.getRelativeY(view.getDrawComponent().getElement());
          if (mouseX > 0 && mouseX < view.getDrawComponent().getOffsetWidth() && mouseY > 0 && mouseY < view
              .getDrawComponent().getOffsetHeight()) {
            clickedShape.translateTo(lastDraggedX - (lastClickX - mouseX), lastDraggedY - (lastClickY - mouseY));
//            view.getDrawComponent().redrawSurfaceForced();
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
        for (SiteShape siteShape : siteShapes) {
          siteShape.scaleTo(scaleFactor);
        }
//        view.getDrawComponent().redrawSurface();
      }
    });

  }

  private void addPage(MouseDownEvent event) {
    PageShape tempShape = new PageShape(view.getDrawComponent(),
        event.getRelativeX(view.getDrawComponent().getElement()),
        event.getRelativeY(view.getDrawComponent().getElement()), 120, 110, "");
    tempShape.scaleTo(scaleFactor);
    siteShapes.add(tempShape);
//    view.getDrawComponent().redrawSurface();
  }

  private void addAction(MouseDownEvent event) {
    ActionShape tempShape = new ActionShape(view.getDrawComponent(), event.getRelativeX(view.getDrawComponent().getElement()),
        event.getRelativeY(view.getDrawComponent().getElement()), 80, 60, "");
    tempShape.scaleTo(scaleFactor);
    siteShapes.add(tempShape);
//    view.getDrawComponent().redrawSurface();
  }

  private void addSimpleList(MouseDownEvent event, PageShape parent) {
    SimpleListShape tempShape = new SimpleListShape(view.getDrawComponent(), event.getRelativeX(view.getDrawComponent().getElement()),
        event.getRelativeY(view.getDrawComponent().getElement()), 80, 90, "", "", parent);
    tempShape.scaleTo(scaleFactor);
    siteShapes.add(tempShape);
//    view.getDrawComponent().redrawSurface();
  }

  private void addForm(MouseDownEvent event, PageShape parent) {
    FormShape tempShape = new FormShape(view.getDrawComponent(), event.getRelativeX(view.getDrawComponent().getElement()),
        event.getRelativeY(view.getDrawComponent().getElement()), 80, 90, "", "", parent);
    tempShape.scaleTo(scaleFactor);
    siteShapes.add(tempShape);
//    view.getDrawComponent().redrawSurface();
  }

  private void addDetails(MouseDownEvent event, PageShape parent) {
    DetailsShape tempShape = new DetailsShape(view.getDrawComponent(), event.getRelativeX(view.getDrawComponent().getElement()),
        event.getRelativeY(view.getDrawComponent().getElement()), 80, 90, "", "", parent);
    tempShape.scaleTo(scaleFactor);
    siteShapes.add(tempShape);
//    view.getDrawComponent().redrawSurface();
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
