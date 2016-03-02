package com.uml2Java.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.*;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.chart.client.draw.DrawComponent;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.button.ToggleButton;
import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer;
import com.sencha.gxt.widget.core.client.container.CenterLayoutContainer;
import com.sencha.gxt.widget.core.client.container.HBoxLayoutContainer;
import com.uml2Java.client.icons.Icons;
import com.uml2Java.client.login.LoginController;
import com.uml2Java.client.login.LoginView;
import com.uml2Java.client.shapes.ClassShape;
import com.uml2Java.client.shapes.IClassShape;
import com.uml2Java.client.shapes.Shape;
import com.uml2Java.client.toolbar.ToolbarView;
import com.uml2Java.client.uml2javaUtils.MouseState;
import com.uml2Java.client.utilitiesPanels.rightPanel.RightPanelController;
import com.uml2Java.client.utilitiesPanels.rightPanel.RightPanelView;
import com.uml2Java.shared.*;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by Cristi on 2/4/2016.
 */
public class Uml2JavaController {
  public interface IUml2JavaView {
    Widget asWidget();
    DrawComponent getDrawComponent();
    CenterLayoutContainer getCenterLayoutContainer();
    ToggleButton getSelectButton();
    ToggleButton getClassButton();
    ContentPanel getRightPanel();
  }

  private static Uml2JavaController INSTANCE;
  public static final Icons ICONS = GWT.create(Icons.class);

  private BorderLayoutContainer mainContainer;
  private HBoxLayoutContainer buttonsContainer;
  private ToolbarView toolbarView;
  private RightPanelController rightPanelController;

  private UserData currentUser;
  private MouseState mouseState = MouseState.SELECT;
  private IUml2JavaView view;

  private int lastClickX, lastClickY;
  private int lastDraggedX, lastDraggedY;
  private double scaleFactor = 1; // TODO load from users saved
  private List<Shape> shapes;
  private Shape clickedShape;
  Logger log = Logger.getLogger(Uml2JavaController.class.getName());

  public Uml2JavaController() {
  }

  public static Uml2JavaController getInstance() {
    if (INSTANCE == null)
      INSTANCE = new Uml2JavaController();
    return INSTANCE;
  }

  public void setViews(BorderLayoutContainer mainContainer, HBoxLayoutContainer buttonsContainer) {
    this.mainContainer = mainContainer;
    this.buttonsContainer = buttonsContainer;
  }

  public void displayLoginWindow() {
    LoginController.ILoginView loginView = new LoginView();
    LoginController loginController = new LoginController(loginView);
    mainContainer.setCenterWidget(loginView.asWidget());
    loginController.bind();
  }

  public void onSuccessLogin(UserData result) {
    this.currentUser = result;
    mainContainer.setCenterWidget(null);
    initMainWindow();
  }

  private void initMainWindow() {
    this.view = new Uml2JavaView();
    mainContainer.setCenterWidget(view.asWidget());
    toolbarView = new ToolbarView();
    buttonsContainer.add(toolbarView.asWidget());
    buttonsContainer.forceLayout();
    RightPanelController.IRightPanelView rightPanelView = new RightPanelView();
    rightPanelController = new RightPanelController(rightPanelView, null);
    view.getRightPanel().add(rightPanelView.asWidget());
    mainContainer.forceLayout();
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
    //    shape2.scaleTo(0.8);
    //    shape.scaleTo(0.8);
    shapes.add(shape);
    shapes.add(shape2);
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
              rightPanelController.load((IClassShape) shape);
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
        }
      }
    };
    MouseUpHandler mouseUpHandler = new MouseUpHandler() {
      @Override
      public void onMouseUp(MouseUpEvent event) {
        clickedShape = null;
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
            view.getDrawComponent().redrawSurface();
          } else {
            clickedShape = null;
          }
        }
      }
    };

    view.getDrawComponent().addDomHandler(mouseDownHandler, MouseDownEvent.getType());
    view.getDrawComponent().addDomHandler(mouseUpHandler, MouseUpEvent.getType());
    view.getDrawComponent().addDomHandler(mouseMoveHandler, MouseMoveEvent.getType());

    view.getClassButton().addValueChangeHandler(new ValueChangeHandler<Boolean>() {
      @Override
      public void onValueChange(ValueChangeEvent<Boolean> event) {
        mouseState = MouseState.CLASS;
      }
    });
    view.getSelectButton().addValueChangeHandler(new ValueChangeHandler<Boolean>() {
      @Override
      public void onValueChange(ValueChangeEvent<Boolean> event) {
        mouseState = MouseState.SELECT;
      }
    });

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

}
