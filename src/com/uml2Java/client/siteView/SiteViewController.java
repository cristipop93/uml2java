package com.uml2Java.client.siteView;

import com.google.gwt.event.dom.client.*;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.chart.client.draw.Color;
import com.sencha.gxt.chart.client.draw.DrawComponent;
import com.sencha.gxt.chart.client.draw.path.LineTo;
import com.sencha.gxt.chart.client.draw.path.MoveTo;
import com.sencha.gxt.chart.client.draw.path.PathSprite;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.Popup;
import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer;
import com.sencha.gxt.widget.core.client.container.HBoxLayoutContainer;
import com.sencha.gxt.widget.core.client.event.BeforeHideEvent;
import com.uml2Java.client.siteView.edit_shapes.SiteViewPopup;
import com.uml2Java.client.siteView.edit_shapes.SiteViewPopupController;
import com.uml2Java.client.siteView.shapes.*;
import com.uml2Java.client.siteView.siteUtils.Framework;
import com.uml2Java.client.siteView.siteUtils.SiteMouseState;
import com.uml2Java.client.siteView.utilitiesPanels.leftPanel.LeftSitePanelController;
import com.uml2Java.client.siteView.utilitiesPanels.leftPanel.LeftSiteView;
import com.uml2Java.client.toolbar.ToolbarView;
import com.uml2Java.shared.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
  private SiteShape selectedShape = null;
  private int lastClickX, lastClickY;
  private int lastDraggedX, lastDraggedY;
  private double scaleFactor = 1; // TODO load from users saved
  private DataTypes defaultDataType = new ObjectDataTypes(-1, "Default Data Type");
  private boolean firstShapeSelected = false;
  private SiteShape firstShape, secondShape;
  private PathSprite previewFlow;
  private int firstFlowClickX;
  private int firstFlowClickY;
  private boolean previewFlowAdded;
  private Framework selectedFramework = null;

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
    ViewComponentShape list1 = new SimpleListShape(view.getDrawComponent(), 100, 100, 80, 90, "", defaultDataType, page1);
    ActionShape action1 = new ActionShape(view.getDrawComponent(), 700, 400, 80, 60, "");
//    Flow flow1 = new NavigationFlow(page1, page2);
//    page1.addFlow(flow1);
//    page2.addFlow(flow1);
//    Flow flow2 = new NavigationFlow(page2, list1);
//    page2.addFlow(flow2);
//    list1.addFlow(flow2);
//    Flow flow3 = new NavigationFlow(page2, action1);
//    page2.addFlow(flow3);
//    action1.addFlow(flow3);
    siteShapes.add(page1);
    siteShapes.add(page2);
    siteShapes.add(list1);
    siteShapes.add(action1);
    final MouseDownHandler mouseDownHandler = new MouseDownHandler() {
      @Override
      public void onMouseDown(MouseDownEvent event) {
        if (siteMouseState == SiteMouseState.SELECT) {
          int minArea = 20000 * 10000;
          boolean found = false;
          for (SiteShape shape : siteShapes) {
            if (shape.canBeDragged(event.getRelativeX(view.getDrawComponent().getElement()),
                event.getRelativeY(view.getDrawComponent().getElement()))) {
              if (shape.getWidth() * shape.getHeight() < minArea) {
                minArea = shape.getWidth() * shape.getHeight();
                clickedShape = shape;
//                selectedShape = shape;
//                selectedShape.setSelected(true);
                setSelectedShape(shape);
                found = true;
                lastClickX = event.getRelativeX(view.getDrawComponent().getElement());
                lastClickY = event.getRelativeY(view.getDrawComponent().getElement());
                lastDraggedX = shape.getX();
                lastDraggedY = shape.getY();
                log.info(shape.getFlows().size() + " ");
              }
              //TODO display popup with info about that shape
            }
          }
          if (found == false) {
            setSelectedShape(null);
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
        if (siteMouseState == SiteMouseState.FLOW) {
          SiteShape shape = getClickedShapeDown(event);
          if (selectedFramework == Framework.IONIC2) {
            if (shape instanceof PageShape || shape instanceof ViewComponentShape)
              mouseDownFlowHandler(shape, event);
          } else {
            mouseDownFlowHandler(shape, event);
          }
        }

        if (siteMouseState == SiteMouseState.OK_FLOW || siteMouseState == SiteMouseState.KO_FLOW) {
          SiteShape shape = getClickedShapeDown(event);
          if (selectedFramework == Framework.IONIC2) {
            if (shape instanceof ActionShape)
              mouseDownFlowHandler(shape, event);
          } else {
            mouseDownFlowHandler(shape, event);
          }
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
        if (hoveredPage != null && ((selectedFramework == Framework.IONIC2 && (hoveredPage.getComponents() == null || hoveredPage.getComponents().size() == 0)) || selectedFramework == Framework.BASIC)) {  // remove this if you want more than one component per page
          if (siteMouseState == SiteMouseState.SIMPLE_LIST) {
              addSimpleList(event, hoveredPage);
          } else if (siteMouseState == SiteMouseState.FORM) {
              addForm(event, hoveredPage);
          } else if (siteMouseState == SiteMouseState.DETAILS) {
              addDetails(event, hoveredPage);
          }
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
        } else if (siteMouseState == SiteMouseState.FLOW || siteMouseState == SiteMouseState.OK_FLOW || siteMouseState == SiteMouseState.KO_FLOW) {
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
          if (firstShapeSelected) {
            previewFlow.clearCommands();
            previewFlow.addCommand(new MoveTo(firstFlowClickX, firstFlowClickY));
            previewFlow.addCommand(new LineTo(event.getRelativeX(view.getDrawComponent().getElement()),
                event.getRelativeY(view.getDrawComponent().getElement())));
            previewFlow.setStrokeWidth(0.5);
            if (siteMouseState == SiteMouseState.OK_FLOW) {
              previewFlow.setStroke(new Color("#0F0"));
            } else if (siteMouseState == SiteMouseState.KO_FLOW) {
              previewFlow.setStroke(new Color("#F00"));
            } else {
              previewFlow.setStroke(new Color("#000"));
            }
            view.getDrawComponent().addSprite(previewFlow);
            previewFlow.redraw();
            previewFlowAdded = true;
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
          } else {
            clickedShape = null;
          }
        }
      }
    };

    MouseUpHandler mouseUpHandler = new MouseUpHandler() {
      @Override
      public void onMouseUp(MouseUpEvent event) {
        if (siteMouseState == SiteMouseState.FLOW || siteMouseState == SiteMouseState.OK_FLOW || siteMouseState == SiteMouseState.KO_FLOW) {
          SiteShape shape = getClickedShapeUp(event);
          if (firstShapeSelected && shape != null) {
            secondShape = shape;
            if (secondShape != firstShape) {
              //check if the flow exists
              boolean found = false;
              for (Flow flow : secondShape.getFlows()) {
                if ( (flow.getFirstUmlShape() == secondShape && flow.getSecondUmlShape() == firstShape) || (flow.getSecondUmlShape() == secondShape && flow.getFirstUmlShape() == firstShape)) {
                  found = true;
                }
              }
              if (!found) {
                if (siteMouseState == SiteMouseState.FLOW) {
                  if (selectedFramework == Framework.IONIC2) {
                    if (secondShape instanceof PageShape || secondShape instanceof ActionShape) {
                      Flow flow = new NavigationFlow(firstShape, secondShape);
                      firstShape.addFlow(flow);
                      secondShape.addFlow(flow);
                    }
                  } else {
                    Flow flow = new NavigationFlow(firstShape, secondShape);
                    firstShape.addFlow(flow);
                    secondShape.addFlow(flow);
                  }
                } else if (siteMouseState == SiteMouseState.OK_FLOW) {
                  if (selectedFramework == Framework.IONIC2) {
                    if (secondShape instanceof PageShape) {
                      Flow flow = new OkFlow(firstShape, secondShape);
                      firstShape.addFlow(flow);
                      secondShape.addFlow(flow);
                    }
                  } else {
                    Flow flow = new OkFlow(firstShape, secondShape);
                    firstShape.addFlow(flow);
                    secondShape.addFlow(flow);
                  }
                } else if (siteMouseState == SiteMouseState.KO_FLOW) {
                  if (selectedFramework == Framework.IONIC2) {
                    if (secondShape instanceof PageShape) {
                      Flow flow = new KoFlow(firstShape, secondShape);
                      firstShape.addFlow(flow);
                      secondShape.addFlow(flow);
                    }
                  } else {
                    Flow flow = new KoFlow(firstShape, secondShape);
                    firstShape.addFlow(flow);
                    secondShape.addFlow(flow);
                  }

                }
              }
            }
          }
          firstShape = null;
          secondShape = null;
          firstShapeSelected = false;
          previewFlow.clearCommands();
          if (previewFlowAdded)
            view.getDrawComponent().remove(previewFlow);
          previewFlowAdded = false;
          previewFlow.redraw();
        }
        clickedShape = null;
      }
    };

    DoubleClickHandler doubleClickHandler = new DoubleClickHandler() {
      @Override
      public void onDoubleClick(DoubleClickEvent event) {
        SiteShape doubleClickShape = null;
        if (siteMouseState == SiteMouseState.SELECT) {
          int minArea = 20000 * 10000;
          for (SiteShape shape : siteShapes) {
            if (shape.canBeDragged(event.getRelativeX(view.getDrawComponent().getElement()),
                event.getRelativeY(view.getDrawComponent().getElement()))) {
              if (shape.getWidth() * shape.getHeight() < minArea) {
                minArea = shape.getWidth() * shape.getHeight();
                doubleClickShape = shape;
              }
            }
          }
        }
        if (doubleClickShape != null) {
          Popup popup = new Popup();
          SiteViewPopupController.ISiteViewPopup siteViewPopup = new SiteViewPopup();
          SiteViewPopupController siteViewPopupController = new SiteViewPopupController(siteViewPopup, doubleClickShape, popup);
          popup.setWidget(siteViewPopup.asWidget());
          popup.setAutoHide(false);
          popup.showAt(event.getX() + 50, event.getY());
        }
      }
    };

    KeyDownHandler keyDownHandler = new KeyDownHandler() {
      @Override
      public void onKeyDown(KeyDownEvent event) {
        if (event.getNativeKeyCode() == KeyCodes.KEY_DELETE) {
          if (selectedShape != null && !(selectedShape instanceof PageShape && selectedShape.getId() == 1)) {
            log.info("delete " + (selectedShape != null ? selectedShape.getTitle() : "null"));
            selectedShape.removeAllFlows();
            selectedShape.remove();
            setSelectedShape(null);
          }
        }
      }
    };
    RootPanel.get().addDomHandler(keyDownHandler, KeyDownEvent.getType());
    view.getDrawComponent().addDomHandler(doubleClickHandler, DoubleClickEvent.getType());

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
      }
    });

  }

  private void mouseDownFlowHandler(SiteShape shape, MouseDownEvent event) {

    if (shape != null) {
      if (!firstShapeSelected) {
        firstShape = shape;
        firstShapeSelected = true;
        firstFlowClickX = event.getRelativeX(view.getDrawComponent().getElement());
        firstFlowClickY = event.getRelativeY(view.getDrawComponent().getElement());
        previewFlow = new PathSprite();
      }
    }
  }

  private SiteShape getClickedShapeDown(MouseDownEvent event) {
    int minArea = 20000 * 10000;
    SiteShape result = null;
    for (SiteShape shape : siteShapes) {
      if (shape.canBeDragged(event.getRelativeX(view.getDrawComponent().getElement()),
          event.getRelativeY(view.getDrawComponent().getElement()))) {
        if (shape.getWidth() * shape.getHeight() < minArea) {
          minArea = shape.getWidth() * shape.getHeight();
          result = shape;
        }
      }
    }
    return result;
  }

  private SiteShape getClickedShapeUp(MouseUpEvent event) {
    int minArea = 20000 * 10000;
    SiteShape result = null;
    for (SiteShape shape : siteShapes) {
      if (shape.canBeDragged(event.getRelativeX(view.getDrawComponent().getElement()),
          event.getRelativeY(view.getDrawComponent().getElement()))) {
        if (shape.getWidth() * shape.getHeight() < minArea) {
          minArea = shape.getWidth() * shape.getHeight();
          result = shape;
        }
      }
    }
    return result;
  }

  private void addPage(MouseDownEvent event) {
    PageShape tempShape = new PageShape(view.getDrawComponent(),
        event.getRelativeX(view.getDrawComponent().getElement()),
        event.getRelativeY(view.getDrawComponent().getElement()), 120, 110, "");
    tempShape.scaleTo(scaleFactor);
    siteShapes.add(tempShape);
  }

  private void addAction(MouseDownEvent event) {
    ActionShape tempShape = new ActionShape(view.getDrawComponent(), event.getRelativeX(view.getDrawComponent().getElement()),
        event.getRelativeY(view.getDrawComponent().getElement()), 80, 60, "");
    tempShape.scaleTo(scaleFactor);
    siteShapes.add(tempShape);
  }

  private void addSimpleList(MouseDownEvent event, PageShape parent) {
    SimpleListShape tempShape = new SimpleListShape(view.getDrawComponent(), event.getRelativeX(view.getDrawComponent().getElement()),
        event.getRelativeY(view.getDrawComponent().getElement()), 80, 90, "", defaultDataType, parent);
    tempShape.scaleTo(scaleFactor);
    siteShapes.add(tempShape);
  }

  private void addForm(MouseDownEvent event, PageShape parent) {
    FormShape tempShape = new FormShape(view.getDrawComponent(), event.getRelativeX(view.getDrawComponent().getElement()),
        event.getRelativeY(view.getDrawComponent().getElement()), 80, 90, "", defaultDataType, parent);
    tempShape.scaleTo(scaleFactor);
    siteShapes.add(tempShape);
  }

  private void addDetails(MouseDownEvent event, PageShape parent) {
    DetailsShape tempShape = new DetailsShape(view.getDrawComponent(), event.getRelativeX(view.getDrawComponent().getElement()),
        event.getRelativeY(view.getDrawComponent().getElement()), 80, 90, "", defaultDataType, parent);
    tempShape.scaleTo(scaleFactor);
    siteShapes.add(tempShape);
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

  public void setSelectedShape(SiteShape selectedShape) {
    if (this.selectedShape != null) {
      this.selectedShape.setSelected(false);
    }
    this.selectedShape = selectedShape;
    if(selectedShape != null)
      this.selectedShape.setSelected(true);
  }

  public List<SiteShape> getSiteShapes() {
    return siteShapes;
  }

  public Map<Long, PageDTO> getPagesDTO() {
    Map<Long, PageDTO> pageDTOs = new HashMap<Long, PageDTO>();

    for(SiteShape siteShape : siteShapes) {
      if (siteShape instanceof PageShape) {
        PageShape page = (PageShape) siteShape;
        List<Long> componentId = new ArrayList<Long>();
        for (ViewComponentShape component : page.getComponents()) {
          componentId.add((long) component.getId());
        }
        List<FlowDTO> flowDTOs = getFlowDTOsForShape(page);
        PageDTO temp = new PageDTO(page.getId(), page.getTitle(), componentId, flowDTOs);
        pageDTOs.put((long) page.getId(), temp);
      }
    }

    return pageDTOs;
  }

  public Map<Long, ComponentDTO> getComponentsDTO() {
    Map<Long, ComponentDTO> componentDTOsMap = new HashMap<Long, ComponentDTO>();

    for (SiteShape siteShape : siteShapes) {
      if (siteShape instanceof ViewComponentShape) {
        ShapeType type;
        if (siteShape instanceof SimpleListShape) {
          type = ShapeType.LIST;
        } else if (siteShape instanceof DetailsShape) {
          type = ShapeType.DETAILS;
        } else {
          type = ShapeType.FORM;
        }
        ViewComponentShape componentShape = (ViewComponentShape) siteShape;
        List<FlowDTO> flowDTOs = getFlowDTOsForShape(componentShape);
        ComponentDTO temp = new ComponentDTO(componentShape.getId(), componentShape.getTitle(), type, componentShape.getDataType(), flowDTOs, componentShape.getParentId());

        componentDTOsMap.put(temp.getParentId(), temp);
      }
    }

    return componentDTOsMap;
  }

  public Map<Long, ActionDTO> getActionDTO() {
    Map<Long, ActionDTO> actionDTOs = new HashMap<Long, ActionDTO>();

    for (SiteShape siteShape : siteShapes) {
      if (siteShape instanceof ActionShape) {
        ActionShape actionShape = (ActionShape) siteShape;
        List<FlowDTO> flowDTOs = getFlowDTOsForShape(actionShape);
        ActionDTO temp = new ActionDTO(actionShape.getId(), actionShape.getTitle(), flowDTOs);
        actionDTOs.put(temp.getId(), temp);
      }
    }
    return actionDTOs;
  }

  /**
   * Get flows only from that shape (coming out of that shape)
   * @param shape
   * @return
   */
  private List<FlowDTO> getFlowDTOsForShape(SiteShape shape) {
    List<FlowDTO> flowDTOs = new ArrayList<FlowDTO>();
    for (Flow flow : shape.getFlows()) {
      ShapeType type;
      if (flow instanceof OkFlow) {
        type = ShapeType.OKFLOW;
      } else if (flow instanceof KoFlow) {
        type = ShapeType.KOFLOW;
      } else {
        type = ShapeType.NAVIGATIONFLOW;
      }
      ShapeType firstShapeType = getShapeType(flow.getFirstUmlShape());
      ShapeType secondShapeType = getShapeType(flow.getSecondUmlShape());
      // get flows only where the first shape is the given one (shape)
      if (flow.getFirstUmlShape().equals(shape)) // remove this if if you want all the flows
        flowDTOs.add(new FlowDTO(flow.getFirstUmlShape().getId(), firstShapeType, flow.getSecondUmlShape().getId(), secondShapeType, type));
    }
    return flowDTOs;
  }

  public boolean validOneComponentPerPage_Ionic2 () {
    for(SiteShape siteShape : siteShapes) {
      if (siteShape instanceof PageShape) {
        PageShape page = (PageShape) siteShape;
        if (page.getComponents() != null && page.getComponents().size() > 1)
          return false;
      }
    }
    return true;
  }

  /**
   * returns PAGE, COMPONENT, ACTION
   */
  private ShapeType getShapeType(SiteShape siteShape) {
    if (siteShape instanceof PageShape)
      return ShapeType.PAGE;
    else if (siteShape instanceof ViewComponentShape)
      return ShapeType.COMPONENT;
    else
      return ShapeType.ACTION;
  }

  public Framework getSelectedFramework() {
    return selectedFramework;
  }

  public void setSelectedFramework(Framework selectedFramework) {
    this.selectedFramework = selectedFramework;
  }
}
