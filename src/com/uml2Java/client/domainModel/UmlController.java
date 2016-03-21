package com.uml2Java.client.domainModel;

import com.google.gwt.event.dom.client.*;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.chart.client.draw.DrawComponent;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer;
import com.sencha.gxt.widget.core.client.container.CenterLayoutContainer;
import com.sencha.gxt.widget.core.client.container.HBoxLayoutContainer;
import com.uml2Java.client.domainModel.shapes.*;
import com.uml2Java.client.toolbar.ToolbarView;
import com.uml2Java.client.domainModel.uml2javaUtils.MouseState;
import com.uml2Java.client.domainModel.utilitiesPanels.leftPanel.LeftPanelController;
import com.uml2Java.client.domainModel.utilitiesPanels.leftPanel.LeftPanelView;
import com.uml2Java.client.domainModel.utilitiesPanels.rightPanel.RightPanelController;
import com.uml2Java.client.domainModel.utilitiesPanels.rightPanel.RightPanelView;
import com.uml2Java.shared.*;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by Cristi on 2/4/2016.
 */
public class UmlController {
  public interface IUmlView {
    Widget asWidget();
    DrawComponent getDrawComponent();
    CenterLayoutContainer getCenterLayoutContainer();
    ContentPanel getRightPanel();
    ContentPanel getLeftPanel();
  }

  private static UmlController INSTANCE;

  private BorderLayoutContainer mainContainer;
  private HBoxLayoutContainer buttonsContainer;
  private ToolbarView toolbarView;
  private RightPanelController rightPanelController;
  private LeftPanelController leftPanelController;

  private UserData currentUser;
  private MouseState mouseState = MouseState.SELECT;
  private static IUmlView view;

  private int lastClickX, lastClickY;
  private int lastDraggedX, lastDraggedY;
  private double scaleFactor = 1; // TODO load from users saved
  private List<Shape> shapes;
  private Shape clickedShape;
  private static Logger log = Logger.getLogger(UmlController.class.getName());

  public UmlController() {
  }

  public static UmlController getInstance() {
    if (INSTANCE == null) {
      log.info("on craete umlController");
      view = new UmlView();
      INSTANCE = new UmlController();
    }
    return INSTANCE;
  }

  public void setViews(BorderLayoutContainer mainContainer, HBoxLayoutContainer buttonsContainer, ToolbarView toolbarView) {
    this.mainContainer = mainContainer;
    this.buttonsContainer = buttonsContainer;
    this.toolbarView = toolbarView;
  }

  // initialize both the uml and the site view
  public void initMainWindow() {
    RightPanelController.IRightPanelView rightPanelView = new RightPanelView();
    rightPanelController = new RightPanelController(rightPanelView, null);
    view.getRightPanel().add(rightPanelView.asWidget());

    LeftPanelController.ILeftPanelView leftPanelView = new LeftPanelView();
    leftPanelController = new LeftPanelController(leftPanelView);
    view.getLeftPanel().add(leftPanelView.asWidget());
    shapes = new ArrayList<Shape>();
    addListeners();
  }

  private void addListeners() {

    DataTypes dataType1 = PrimitiveDataTypes.STRING;
    DataTypes dataType3 = PrimitiveDataTypes.VOID;
    List<Method> methods = new ArrayList<Method>();
    methods.add(new Method("getUser", AccessType.PUBLIC, dataType1, new ArrayList<Attribute>()));
    List<Attribute> attr = new ArrayList<Attribute>();
    Attribute user = new Attribute("user", dataType1);
    user.setIsFinal(true);
    attr.add(user);
    methods.add(new Method("setUser", AccessType.PRIVATE, dataType3, attr));

    List<Attribute> attributes = new ArrayList<Attribute>();
    Attribute user1 = new Attribute("user", dataType1, AccessType.PRIVATE);
//    user1.setIsStatic(true);
    user1.setIsFinal(true);
    attributes.add(user1);
    ClassShape shape = new ClassShape(view.getDrawComponent(), 300, 200, 100, 65, "User", attributes, methods);
    ClassShape shape2 = new ClassShape(view.getDrawComponent(), 600, 300, 100, 65, "classExample",
        new ArrayList<Attribute>(), new ArrayList<Method>());
    ClassShape shape3 = new ClassShape(view.getDrawComponent(), 600, 100, 100, 65, "AClass", new ArrayList<Attribute>(), new ArrayList<Method>());
    ClassShape shape4 = new ClassShape(view.getDrawComponent(), 200, 450, 100, 65, "BClass", new ArrayList<Attribute>(), new ArrayList<Method>());
    ClassShape shape5 = new ClassShape(view.getDrawComponent(), 150, 100, 100, 65, "CClass", new ArrayList<Attribute>(), new ArrayList<Method>());
    InterfaceShape shape6 = new InterfaceShape(view.getDrawComponent(), 700, 450, 100, 65, "Interface", new ArrayList<Method>());
    //    shape2.scaleTo(0.8);
    //    shape.scaleTo(0.8);
    shapes.add(shape);
    shapes.add(shape2);
    shapes.add(shape3);
    shapes.add(shape4);
    shapes.add(shape5);
    shapes.add(shape6);
    Link link = new GeneralizationLink(shape2, shape);
    shape.getLinks().add(link);
    shape2.getLinks().add(link);
    Link link11 = new AssociationLink(shape, shape3);
    shape.getLinks().add(link11);
    shape3.getLinks().add(link11);
    Link link2 = new AssociationLink(shape, shape4);
    shape.getLinks().add(link2);
    shape4.getLinks().add(link2);
    Link link3 = new AssociationLink(shape4, shape5);
    shape4.getLinks().add(link3);
    shape5.getLinks().add(link3);
    Link link4 = new AssociationLink(shape, shape6);
    shape.getLinks().add(link4);
    shape6.getLinks().add(link4);
//    view.getDrawComponent().clearSurface();
    for (Shape shapeTemp : shapes) {
      shapeTemp.redraw();
    }
    view.getDrawComponent().redrawSurface();
    MouseDownHandler mouseDownHandler = new MouseDownHandler() {
      @Override
      public void onMouseDown(MouseDownEvent event) {
        if (mouseState == MouseState.SELECT) {
          for (Shape shape : shapes) {
            if (shape.canBeDragged(event.getRelativeX(view.getDrawComponent().getElement()),
                event.getRelativeY(view.getDrawComponent().getElement()))) {
              clickedShape = shape;
              lastClickX = event.getRelativeX(view.getDrawComponent().getElement());
              lastClickY = event.getRelativeY(view.getDrawComponent().getElement());
              lastDraggedX = shape.getX();
              lastDraggedY = shape.getY();
              // populate right panel with clicked shape content
              rightPanelController.load(shape);
              log.info("nrLinks = " + shape.getLinks().size());
              return;
            }
          }
        } else if (mouseState == MouseState.CLASS) {
          ClassShape tempShape = new ClassShape(view.getDrawComponent(),
              event.getRelativeX(view.getDrawComponent().getElement()),
              event.getRelativeY(view.getDrawComponent().getElement()), 100, 100, "", new ArrayList<Attribute>(),
              new ArrayList<Method>());
          tempShape.scaleTo(scaleFactor);
          shapes.add(tempShape);
          view.getDrawComponent().redrawSurface();
        } else if (mouseState == MouseState.INTERFACE) {
          InterfaceShape tempShape = new InterfaceShape(view.getDrawComponent(), event.getRelativeX(view.getDrawComponent().getElement()),
              event.getRelativeY(view.getDrawComponent().getElement()), 100, 100, "", new ArrayList<Method>());
          tempShape.scaleTo(scaleFactor);
          shapes.add(tempShape);
        }
      }
    };
    MouseUpHandler mouseUpHandler = new MouseUpHandler() {
      @Override
      public void onMouseUp(MouseUpEvent event) {
        clickedShape = null;
        for (Shape shape1 : shapes)
          shape1.drawLinks();
        view.getDrawComponent().redrawSurface();
      }
    };

    MouseMoveHandler mouseMoveHandler = new MouseMoveHandler() {
      @Override
      public void onMouseMove(MouseMoveEvent event) {
        if (mouseState == MouseState.SELECT) {
          if (clickedShape == null) {
            boolean found = false;
            for (Shape shape : shapes) {
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

    view.getDrawComponent().addDomHandler(mouseDownHandler, MouseDownEvent.getType());
    view.getDrawComponent().addDomHandler(mouseUpHandler, MouseUpEvent.getType());
    view.getDrawComponent().addDomHandler(mouseMoveHandler, MouseMoveEvent.getType());

    toolbarView.getZoomSlider().addValueChangeHandler(new ValueChangeHandler<Integer>() {
      @Override
      public void onValueChange(ValueChangeEvent<Integer> event) {
        scaleFactor = (double) event.getValue() / 100d;
        log.info(scaleFactor + "");
        for (Shape shape : shapes) {
          shape.scaleTo(scaleFactor);
        }
        view.getDrawComponent().redrawSurface();
      }
    });
  }

  public List<Shape> getShapes() {
    return shapes;
  }

  public BorderLayoutContainer getMainContainer() {
    return mainContainer;
  }

  public HBoxLayoutContainer getButtonsContainer() {
    return buttonsContainer;
  }

  public MouseState getMouseState() {
    return mouseState;
  }

  public void setMouseState(MouseState mouseState) {
    this.mouseState = mouseState;
  }

  public static IUmlView getView() {
    return view;
  }
}
