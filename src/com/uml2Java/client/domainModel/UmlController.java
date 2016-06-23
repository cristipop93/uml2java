package com.uml2Java.client.domainModel;

import com.google.gwt.event.dom.client.*;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.chart.client.draw.Color;
import com.sencha.gxt.chart.client.draw.DrawComponent;
import com.sencha.gxt.chart.client.draw.path.LineTo;
import com.sencha.gxt.chart.client.draw.path.MoveTo;
import com.sencha.gxt.chart.client.draw.path.PathSprite;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer;
import com.sencha.gxt.widget.core.client.container.CenterLayoutContainer;
import com.sencha.gxt.widget.core.client.container.HBoxLayoutContainer;
import com.uml2Java.client.domainModel.shapes.*;
import com.uml2Java.client.toolbar.ToolbarView;
import com.uml2Java.client.domainModel.uml2javaUtils.DomainMouseState;
import com.uml2Java.client.domainModel.utilitiesPanels.leftPanel.LeftDomainPanelController;
import com.uml2Java.client.domainModel.utilitiesPanels.leftPanel.LeftDomainPanelView;
import com.uml2Java.client.domainModel.utilitiesPanels.rightPanel.RightPanelController;
import com.uml2Java.client.domainModel.utilitiesPanels.rightPanel.RightPanelView;
import com.uml2Java.shared.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
  private LeftDomainPanelController leftDomainPanelController;

  private UserData currentUser;
  private DomainMouseState domainMouseState = DomainMouseState.SELECT;
  private static IUmlView view;

  private int lastClickX, lastClickY;
  private int lastDraggedX, lastDraggedY;
  private double scaleFactor = 1; // TODO load from users saved
  private List<UmlShape> umlShapes;
  private UmlShape clickedUmlShape;
  private boolean firstShapeSelected = false;
  private UmlShape firstShape, secondShape;
  private int firstLinkClickX;
  private int firstLinkClickY;
  private boolean previewLinkAdded;
  private PathSprite previewLink;
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

    LeftDomainPanelController.ILeftPanelView leftPanelView = new LeftDomainPanelView();
    leftDomainPanelController = new LeftDomainPanelController(leftPanelView);
    view.getLeftPanel().add(leftPanelView.asWidget());
    umlShapes = new ArrayList<UmlShape>();
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
    Attribute user0 = new Attribute("id", PrimitiveDataTypes.INT, AccessType.PRIVATE);
    Attribute user1 = new Attribute("name", dataType1, AccessType.PRIVATE);
    Attribute user2 = new Attribute("address", dataType1, AccessType.PRIVATE);
    Attribute user3 = new Attribute("telephone", dataType1, AccessType.PRIVATE);
//    user1.setIsStatic(true);
    user1.setIsFinal(true);
    attributes.add(user0); attributes.add(user1); attributes.add(user2); attributes.add(user3);
    ClassUmlShape shape = new ClassUmlShape(view.getDrawComponent(), 300, 200, 100, 65, "User", attributes, methods);
    //    shape2.scaleTo(0.8);
    //    shape.scaleTo(0.8);
    umlShapes.add(shape);

//    view.getDrawComponent().clearSurface();
    for (UmlShape umlShapeTemp : umlShapes) {
      umlShapeTemp.redraw();
    }
    view.getDrawComponent().redrawSurface();
    MouseDownHandler mouseDownHandler = new MouseDownHandler() {
      @Override
      public void onMouseDown(MouseDownEvent event) {
        if (domainMouseState == DomainMouseState.SELECT) {
          for (UmlShape shape : umlShapes) {
            if (shape.canBeDragged(event.getRelativeX(view.getDrawComponent().getElement()),
                event.getRelativeY(view.getDrawComponent().getElement()))) {
              clickedUmlShape = shape;
              lastClickX = event.getRelativeX(view.getDrawComponent().getElement());
              lastClickY = event.getRelativeY(view.getDrawComponent().getElement());
              lastDraggedX = shape.getX();
              lastDraggedY = shape.getY();
              // populate right panel with clicked shape content
              rightPanelController.load(shape);
//              log.info("nrLinks = " + shape.getLinks().size());
              return;
            }
          }
        } else if (domainMouseState == DomainMouseState.CLASS) {
          ClassUmlShape tempShape = new ClassUmlShape(view.getDrawComponent(),
              event.getRelativeX(view.getDrawComponent().getElement()),
              event.getRelativeY(view.getDrawComponent().getElement()), 100, 100, "", new ArrayList<Attribute>(),
              new ArrayList<Method>());
          tempShape.scaleTo(scaleFactor);
          umlShapes.add(tempShape);
          view.getDrawComponent().redrawSurface();
        } else if (domainMouseState == DomainMouseState.INTERFACE) {
          InterfaceUmlShape tempShape = new InterfaceUmlShape(view.getDrawComponent(), event.getRelativeX(view.getDrawComponent().getElement()),
              event.getRelativeY(view.getDrawComponent().getElement()), 100, 100, "", new ArrayList<Method>());
          tempShape.scaleTo(scaleFactor);
          umlShapes.add(tempShape);
        } else if (domainMouseState == DomainMouseState.GENERALIZATION_LINK || domainMouseState == DomainMouseState.ASSOCIATION_LINK) {
          UmlShape shape = getClickedShapeDown(event);
          if (shape != null) {
            if (!firstShapeSelected) {
              firstShape = shape;
              firstShapeSelected = true;
              firstLinkClickX = event.getRelativeX(view.getDrawComponent().getElement());
              firstLinkClickY = event.getRelativeY(view.getDrawComponent().getElement());
              previewLink = new PathSprite();
            }
          }
        }
      }
    };
    MouseUpHandler mouseUpHandler = new MouseUpHandler() {
      @Override
      public void onMouseUp(MouseUpEvent event) {
        if (domainMouseState == DomainMouseState.GENERALIZATION_LINK || domainMouseState == DomainMouseState.ASSOCIATION_LINK) {
          UmlShape shape = getClickedShapeUp(event);
          if (firstShapeSelected && shape != null) {
            secondShape = shape;
            if (secondShape != firstShape) {
              //check if the flow exists
              boolean found = false;
              for (Link link : secondShape.getLinks()) {
                if ( (link.getFirstUmlShape() == secondShape && link.getSecondUmlShape() == firstShape) || (link.getSecondUmlShape() == secondShape && link.getFirstUmlShape() == firstShape)) {
                  found = true;
                }
              }
              if (!found) {
                if (domainMouseState == DomainMouseState.GENERALIZATION_LINK) {
                  GeneralizationLink link = new GeneralizationLink(firstShape, secondShape);
                  firstShape.getLinks().add(link);
                  secondShape.getLinks().add(link);
                } else if (domainMouseState == DomainMouseState.ASSOCIATION_LINK) {
                  AssociationLink link = new AssociationLink(firstShape, secondShape);
                  firstShape.getLinks().add(link);
                  secondShape.getLinks().add(link);
                }
              }
            }
          }
          firstShape = null;
          secondShape = null;
          firstShapeSelected = false;
          previewLink.clearCommands();
          if (previewLinkAdded)
            view.getDrawComponent().remove(previewLink);
          previewLinkAdded = false;
          previewLink.redraw();

        }


        clickedUmlShape = null;
        for (UmlShape shape1 : umlShapes)
          shape1.drawLinks(scaleFactor);
        view.getDrawComponent().redrawSurface();
      }
    };

    MouseMoveHandler mouseMoveHandler = new MouseMoveHandler() {
      @Override
      public void onMouseMove(MouseMoveEvent event) {
        if (domainMouseState == DomainMouseState.SELECT || domainMouseState == DomainMouseState.GENERALIZATION_LINK || domainMouseState == DomainMouseState.ASSOCIATION_LINK) {
          if (clickedUmlShape == null) {
            boolean found = false;
            for (UmlShape shape : umlShapes) {
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
          if (domainMouseState == DomainMouseState.GENERALIZATION_LINK || domainMouseState == DomainMouseState.ASSOCIATION_LINK) {
            if (firstShapeSelected) {
              previewLink.clearCommands();
              previewLink.addCommand(new MoveTo(firstLinkClickX, firstLinkClickY));
              previewLink.addCommand(new LineTo(event.getRelativeX(view.getDrawComponent().getElement()),
                  event.getRelativeY(view.getDrawComponent().getElement())));
              previewLink.setStrokeWidth(0.5);
              previewLink.setStroke(new Color("#000"));
              view.getDrawComponent().addSprite(previewLink);
              previewLink.redraw();
              previewLinkAdded = true;
            }
          }
        }
        if (clickedUmlShape != null) {
          int mouseX = event.getRelativeX(view.getDrawComponent().getElement());
          int mouseY = event.getRelativeY(view.getDrawComponent().getElement());
          if (mouseX > 0 && mouseX < view.getDrawComponent().getOffsetWidth() && mouseY > 0 && mouseY < view
              .getDrawComponent().getOffsetHeight()) {
            clickedUmlShape.translateTo(lastDraggedX - (lastClickX - mouseX), lastDraggedY - (lastClickY - mouseY));
            view.getDrawComponent().redrawSurfaceForced();
          } else {
            clickedUmlShape = null;
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
        for (UmlShape umlShape : umlShapes) {
          umlShape.scaleTo(scaleFactor);
        }
        view.getDrawComponent().redrawSurface();
      }
    });
  }

  private UmlShape getClickedShapeUp(MouseUpEvent event) {
    int minArea = 20000 * 10000;
    UmlShape result = null;
    for (UmlShape shape : umlShapes) {
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

  private UmlShape getClickedShapeDown(MouseDownEvent event) {
    int minArea = 20000 * 10000;
    UmlShape result = null;
    for (UmlShape shape : umlShapes) {
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

  public List<UmlShape> getUmlShapes() {
    return umlShapes;
  }

  public BorderLayoutContainer getMainContainer() {
    return mainContainer;
  }

  public HBoxLayoutContainer getButtonsContainer() {
    return buttonsContainer;
  }

  public DomainMouseState getDomainMouseState() {
    return domainMouseState;
  }

  public void setDomainMouseState(DomainMouseState domainMouseState) {
    this.domainMouseState = domainMouseState;
  }

  public static IUmlView getView() {
    return view;
  }

  public Map<String, ClassDTO> getClassDtos() {
    Map<String, ClassDTO> classDTOMap = new HashMap<String, ClassDTO>();

    for (UmlShape umlShape : umlShapes) {
      if (umlShape instanceof ClassUmlShape) {
        ClassUmlShape classUmlShape = (ClassUmlShape) umlShape;
        ClassDTO temp = new ClassDTO(classUmlShape.getId(), classUmlShape.getTitle(), classUmlShape.getAttributesList(), classUmlShape.getMethodsList());

        classDTOMap.put(classUmlShape.getTitle(), temp);
      }
    }
    return classDTOMap;
  }

  public RightPanelController getRightPanelController() {
    return rightPanelController;
  }
}
