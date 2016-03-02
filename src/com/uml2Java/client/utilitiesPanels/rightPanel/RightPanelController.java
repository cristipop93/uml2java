package com.uml2Java.client.utilitiesPanels.rightPanel;

import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.widget.core.client.Window;
import com.sencha.gxt.widget.core.client.box.MessageBox;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.form.TextField;
import com.sencha.gxt.widget.core.client.grid.Grid;
import com.uml2Java.client.Uml2JavaController;
import com.uml2Java.client.shapes.ClassShape;
import com.uml2Java.client.shapes.IClassShape;
import com.uml2Java.client.shapes.Shape;
import com.uml2Java.client.utilitiesPanels.editAttributes.EditAttributesController;
import com.uml2Java.client.utilitiesPanels.editAttributes.EditAttributesView;
import com.uml2Java.client.utilitiesPanels.editMethods.EditMethodController;
import com.uml2Java.client.utilitiesPanels.editMethods.EditMethodView;
import com.uml2Java.shared.AccessType;
import com.uml2Java.shared.Attribute;
import com.uml2Java.shared.Method;
import com.uml2Java.shared.PrimitiveDataTypes;

import java.util.List;
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

    Widget asWidget();
  }

  private IRightPanelView view;
  private IClassShape classShape;
  private Logger log = Logger.getLogger(RightPanelController.class.getName());

  public RightPanelController(IRightPanelView view, IClassShape classShape) {
    this.view = view;
    this.classShape = classShape;
    if (classShape != null)
      load(classShape);
    addListeners();
  }

  public void load(IClassShape classShape) {
    this.classShape = classShape;
    view.getTitleField().setValue(this.classShape.getTitle());

    view.getAttributeGrid().getStore().clear();
    for (Attribute attribute : this.classShape.getAttributesList()) {
      view.getAttributeGrid().getStore().add(attribute);
    }

    view.getMethodGrid().getStore().clear();
    for (Method method : this.classShape.getMethodsList()) {
      view.getMethodGrid().getStore().add(method);
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
        ClassShape selectedClass = (ClassShape) classShape;
        Uml2JavaController.getInstance().getShapes().remove(selectedClass);
        selectedClass.remove();
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
    classShape = null;
    view.getTitleField().setValue("");
    view.getAttributeGrid().getStore().clear();
    view.getMethodGrid().getStore().clear();
  }

  private void doOnApplyButton() {
    classShape.setTitle(view.getTitleField().getValue());
    classShape.setAttributesList(view.getAttributeGrid().getStore().getAll());
    classShape.setMethodsList(view.getMethodGrid().getStore().getAll());
//    ((ClassShape) classShape).redraw();
    for(Shape shape : Uml2JavaController.getInstance().getShapes()) {
      shape.redraw();
    }
  }

}
