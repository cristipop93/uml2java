package com.uml2Java.client.domainModel.utilitiesPanels.rightPanel;

import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.Window;
import com.sencha.gxt.widget.core.client.box.MessageBox;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.AccordionLayoutContainer;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.form.TextField;
import com.sencha.gxt.widget.core.client.grid.Grid;
import com.uml2Java.client.domainModel.UmlController;
import com.uml2Java.client.domainModel.shapes.ClassUmlShape;
import com.uml2Java.client.domainModel.shapes.InterfaceUmlShape;
import com.uml2Java.client.domainModel.shapes.UmlShape;
import com.uml2Java.client.domainModel.utilitiesPanels.editAttributes.EditAttributesController;
import com.uml2Java.client.domainModel.utilitiesPanels.editAttributes.EditAttributesView;
import com.uml2Java.client.domainModel.utilitiesPanels.editMethods.EditMethodController;
import com.uml2Java.client.domainModel.utilitiesPanels.editMethods.EditMethodView;
import com.uml2Java.shared.Attribute;
import com.uml2Java.shared.Method;

import java.util.logging.Logger;

/**
 * Created by Cristi on 2/20/2016.
 */
public class RightPanelController {
  public interface IRightPanelView {
    TextButton getApplyButton();
    TextButton getCancelButton();
    TextButton getRemoveButton();
    TextField getTitleField();
    Grid<Attribute> getAttributeGrid();
    TextButton getAddAttribute();
    TextButton getEditAttribute();
    TextButton getRemoveAttribute();
    Grid<Method> getMethodGrid();
    TextButton getAddMethod();
    TextButton getEditMethod();
    TextButton getRemoveMethod();
    ContentPanel getAttributePanel();
    ContentPanel getMethodPanel();
    ContentPanel getLinkPanel();
    AccordionLayoutContainer getAccordion();
    Widget asWidget();
  }

  private IRightPanelView view;
  private UmlShape umlShape;
  private Logger log = Logger.getLogger(RightPanelController.class.getName());

  public RightPanelController(IRightPanelView view, UmlShape umlShape) {
    this.view = view;
    this.umlShape = umlShape;
    if (umlShape != null)
      load(umlShape);
    addListeners();
  }

  public void load(UmlShape umlShape) {
    this.umlShape = umlShape;
    if (umlShape instanceof ClassUmlShape) {
      ClassUmlShape classShape = (ClassUmlShape) umlShape;
      view.getTitleField().setValue(classShape.getTitle());
      view.getAttributeGrid().getStore().clear();
      for (Attribute attribute : classShape.getAttributesList()) {
        view.getAttributeGrid().getStore().add(attribute);
      }
      view.getMethodGrid().getStore().clear();
      for (Method method : classShape.getMethodsList()) {
        view.getMethodGrid().getStore().add(method);
      }
      shapeLoaded(true);
    } else if (umlShape instanceof InterfaceUmlShape) {
      InterfaceUmlShape interfaceShape = (InterfaceUmlShape) umlShape;
      view.getTitleField().setValue(interfaceShape.getTitle());
      view.getMethodGrid().getStore().clear();
      for (Method method : interfaceShape.getMethodsList()) {
        view.getMethodGrid().getStore().add(method);
      }
      shapeLoaded(false);
    }

  }

  private void shapeLoaded(boolean showAllPanels) {
    if (showAllPanels) {
      view.getAttributePanel().setEnabled(true);
      view.getAttributePanel().expand();
    } else {
      view.getAttributePanel().setEnabled(false);
      view.getAttributePanel().collapse();
      view.getMethodPanel().expand();
    }
  }

  private void addListeners() {
    view.getApplyButton().addSelectHandler(new SelectEvent.SelectHandler() {
      @Override
      public void onSelect(SelectEvent event) {
        doOnApplyButton();
      }
    });

    view.getCancelButton().addSelectHandler(new SelectEvent.SelectHandler() {
      @Override
      public void onSelect(SelectEvent event) {
        onCancelButton();
      }
    });

    view.getRemoveButton().addSelectHandler(new SelectEvent.SelectHandler() {
      @Override
      public void onSelect(SelectEvent event) {
        UmlController.getInstance().getUmlShapes().remove(umlShape);
        umlShape.remove();
        umlShape.removeLinks();
        onCancelButton();
      }
    });
    view.getAddAttribute().addSelectHandler(new SelectEvent.SelectHandler() {
      @Override
      public void onSelect(SelectEvent event) {
        createEditAttributesWindow(null);
      }
    });
    view.getEditAttribute().addSelectHandler(new SelectEvent.SelectHandler() {
      @Override
      public void onSelect(SelectEvent event) {
        if (view.getAttributeGrid().getSelectionModel().getSelectedItems().size() == 1) {
          createEditAttributesWindow(view.getAttributeGrid().getSelectionModel().getSelectedItem());
        } else {
          new MessageBox("Info", "Please select at least one attribute before editing.").show();
        }
        view.getAttributeGrid().getView().refresh(false);
      }
    });
    view.getRemoveAttribute().addSelectHandler(new SelectEvent.SelectHandler() {
      @Override
      public void onSelect(SelectEvent event) {
        for (Attribute selectedAttribute : view.getAttributeGrid().getSelectionModel().getSelectedItems()) {
          view.getAttributeGrid().getStore().remove(selectedAttribute);
        }
      }
    });
    view.getAddMethod().addSelectHandler(new SelectEvent.SelectHandler() {
      @Override
      public void onSelect(SelectEvent event) {
        createEditMethodWindow(null);
      }
    });
    view.getEditMethod().addSelectHandler(new SelectEvent.SelectHandler() {
      @Override
      public void onSelect(SelectEvent event) {
        if(view.getMethodGrid().getSelectionModel().getSelectedItems().size() == 1) {
          createEditMethodWindow(view.getMethodGrid().getSelectionModel().getSelectedItem());
        } else {
          new MessageBox("Info", "Please select at least one method before editing").show();
        }
//        view.getMethodGrid().getView().refresh(false);
      }
    });
    view.getRemoveMethod().addSelectHandler(new SelectEvent.SelectHandler() {
      @Override
      public void onSelect(SelectEvent event) {
        for(Method selectedMethod : view.getMethodGrid().getSelectionModel().getSelectedItems()) {
          view.getMethodGrid().getStore().remove(selectedMethod);
        }
      }
    });
  }

  private void createEditMethodWindow(Method method) {
    EditMethodController.IEditMethod editMethodView = new EditMethodView();
    EditMethodController editMethodController = new EditMethodController(editMethodView, view.getMethodGrid(), method);
    Window window = new Window();
    window.setPixelSize(300, 350);
    window.setModal(true);
    window.setBlinkModal(true);
    if (method != null)
      window.setHeadingHtml("Edit Method");
    else
      window.setHeadingHtml("Add Method");
    window.add(editMethodView.asWidget());
    editMethodController.setContentWindow(window);
    window.setResizable(false);
    window.show();
  }

  private void createEditAttributesWindow(Attribute attribute) {
    EditAttributesController.IEditAttributes editAttributesView = new EditAttributesView();
    EditAttributesController editAttributesController = new EditAttributesController(editAttributesView,
        view.getAttributeGrid(), attribute);
    Window window = new Window();
    window.setPixelSize(300, 180);
    window.setModal(true);
    window.setBlinkModal(true);
    if (attribute != null)
      window.setHeadingHtml("Edit Attribute");
    else
      window.setHeadingHtml("Add Attribute");
    window.add(editAttributesView.asWidget());
    editAttributesController.setContentWindow(window);
    window.setResizable(false);
    window.show();
  }

  private void onCancelButton() {
    umlShape = null;
    view.getTitleField().setValue("");
    view.getAttributeGrid().getStore().clear();
    view.getMethodGrid().getStore().clear();
  }

  private void doOnApplyButton() {
    if (umlShape instanceof ClassUmlShape) {
      ClassUmlShape classShape = (ClassUmlShape) umlShape;
      classShape.setTitle(view.getTitleField().getValue());
      classShape.setAttributesList(view.getAttributeGrid().getStore().getAll());
      classShape.setMethodsList(view.getMethodGrid().getStore().getAll());
    } else if (umlShape instanceof InterfaceUmlShape) {
      InterfaceUmlShape interfaceShape = (InterfaceUmlShape) umlShape;
      interfaceShape.setTitle(view.getTitleField().getValue());
      interfaceShape.setMethodsList(view.getMethodGrid().getStore().getAll());
    }
//    ((ClassUmlShape) classShape).redraw();
    for(UmlShape umlShape : UmlController.getInstance().getUmlShapes()) {
      umlShape.redraw();
    }
  }

}
