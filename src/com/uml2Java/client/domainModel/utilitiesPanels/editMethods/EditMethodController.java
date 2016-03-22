package com.uml2Java.client.domainModel.utilitiesPanels.editMethods;

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.widget.core.client.Window;
import com.sencha.gxt.widget.core.client.box.MessageBox;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.form.CheckBox;
import com.sencha.gxt.widget.core.client.form.ComboBox;
import com.sencha.gxt.widget.core.client.form.TextField;
import com.sencha.gxt.widget.core.client.grid.Grid;
import com.uml2Java.client.domainModel.UmlController;
import com.uml2Java.client.domainModel.shapes.ClassUmlShape;
import com.uml2Java.client.domainModel.shapes.UmlShape;
import com.uml2Java.client.domainModel.uml2javaUtils.TextUtil;
import com.uml2Java.client.domainModel.utilitiesPanels.editAttributes.EditAttributesController;
import com.uml2Java.client.domainModel.utilitiesPanels.editAttributes.EditAttributesView;
import com.uml2Java.shared.*;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by Cristi on 2/26/2016.
 */
public class EditMethodController {
  private Window contentWindow;

  public interface IEditMethod {
    TextButton getApplyButton();
    TextButton getCancelButton();
    TextField getDisplayNameField();
    ComboBox<AccessType> getAccessTypeComboBox();
    ComboBox<DataTypes> getDataTypesComboBox();
    Grid<Attribute> getAttributeGrid();
    TextButton getAddAttribute();
    TextButton getEditAttribute();
    TextButton getRemoveAttribute();
    CheckBox getIsStaticCB();
    CheckBox getIsFinalCB();
    Widget asWidget();

  }
  private IEditMethod view;
  private Grid<Method> methodGrid;
  private Method selectedMethod;
  Logger log = Logger.getLogger(EditMethodController.class.getName());

  public EditMethodController(IEditMethod editMethodView, Grid<Method> methodGrid, Method method) {
    this.view = editMethodView;
    this.methodGrid = methodGrid;
    this.selectedMethod = method;
    load();
    if(method != null) {
      loadMethod();
    }
    addListeners();
  }

  private void loadMethod() {
    view.getDisplayNameField().setValue(selectedMethod.getDisplayName());
    view.getAccessTypeComboBox().setValue(selectedMethod.getAccessType());
    view.getDataTypesComboBox().setValue(selectedMethod.getDataType());
    view.getIsStaticCB().setValue(selectedMethod.isStatic());
    view.getIsFinalCB().setValue(selectedMethod.isFinal());
    if(selectedMethod.getAttributes() != null) {
      view.getAttributeGrid().getStore().clear();
      view.getAttributeGrid().getStore().addAll(selectedMethod.getAttributes());
    }
  }

  private void load() {
    // load the primitive dataTypes in the combo
    for (PrimitiveDataTypes dataType : PrimitiveDataTypes.values()) {
      view.getDataTypesComboBox().getStore().add(dataType);
    }
    List<UmlShape> umlShapes = UmlController.getInstance().getUmlShapes();
    for (UmlShape umlShape : umlShapes) {
      if (umlShape instanceof ClassUmlShape) {
        view.getDataTypesComboBox().getStore().add(umlShape.getDataTpe());
      }
    }

    //load the access types in the combo
    for (AccessType accessType : AccessType.values()) {
      view.getAccessTypeComboBox().getStore().add(accessType);
    }
  }

  private void addListeners() {
    view.getAddAttribute().addSelectHandler(new SelectEvent.SelectHandler() {
      @Override
      public void onSelect(SelectEvent event) {
        createEditAttributesWindow(null, true);
      }
    });

    view.getEditAttribute().addSelectHandler(new SelectEvent.SelectHandler() {
      @Override
      public void onSelect(SelectEvent event) {
        if (view.getAttributeGrid().getSelectionModel().getSelectedItems().size() == 1) {
          createEditAttributesWindow(view.getAttributeGrid().getSelectionModel().getSelectedItem(), true);
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
    view.getApplyButton().addSelectHandler(new SelectEvent.SelectHandler() {
      @Override
      public void onSelect(SelectEvent event) {
        if (isValid() && isUnique()) {
          if(selectedMethod == null) {
            Method item = new Method(view.getDisplayNameField().getValue(), view.getAccessTypeComboBox().getValue(),
                view.getDataTypesComboBox().getValue(), view.getAttributeGrid().getStore().getAll() != null ?
                view.getAttributeGrid().getStore().getAll() :
                new ArrayList<Attribute>());
            item.setIsFinal(view.getIsFinalCB().getValue());
            item.setIsStatic(view.getIsStaticCB().getValue());
            methodGrid.getStore().add(item);
          }
          else {
            selectedMethod.setDisplayName(view.getDisplayNameField().getValue());
            selectedMethod.setDataType(view.getDataTypesComboBox().getValue());
            selectedMethod.setAccessType(view.getAccessTypeComboBox().getValue());
            selectedMethod.setAttributes(view.getAttributeGrid().getStore().getAll() != null ? view.getAttributeGrid().getStore().getAll() : new ArrayList<Attribute>());
            selectedMethod.setIsFinal(view.getIsFinalCB().getValue());
            selectedMethod.setIsStatic(view.getIsStaticCB().getValue());
          }
          methodGrid.getView().refresh(false);
          contentWindow.hide();
        }
      }
    });
    view.getCancelButton().addSelectHandler(new SelectEvent.SelectHandler() {
      @Override
      public void onSelect(SelectEvent event) {
        contentWindow.hide();
      }
    });
    view.getIsFinalCB().addValueChangeHandler(new ValueChangeHandler<Boolean>() {
      @Override
      public void onValueChange(ValueChangeEvent<Boolean> event) {
        view.getIsStaticCB().setValue(false);
      }
    });
    view.getIsStaticCB().addValueChangeHandler(new ValueChangeHandler<Boolean>() {
      @Override
      public void onValueChange(ValueChangeEvent<Boolean> event) {
        view.getIsFinalCB().setValue(false);
      }
    });
  }

  private boolean isUnique() {
    for(Method method : methodGrid.getStore().getAll()) {
      if(method.getDisplayName().equals(view.getDisplayNameField().getValue()) && !method.equals(selectedMethod)) {
        new MessageBox("Info", "An attribute with this name already exists").show();
        return false;
      }
    }
    return true;
  }

  private boolean isValid() {
    if (TextUtil.isEmptyString(view.getDisplayNameField().getValue())) {
      new MessageBox("Info", "Display name field can't be empty.").show();
      return false;
    }
    if (view.getAccessTypeComboBox().getValue() == null) {
      new MessageBox("Info", "The access type can't be empty.").show();
      return false;
    }
    if(view.getDataTypesComboBox().getValue() == null) {
      new MessageBox("Info", "The data type can't be empty").show();
      return false;
    }

    return true;
  }

  private void createEditAttributesWindow(Attribute attribute, boolean isParameter) {
    EditAttributesController.IEditAttributes editAttributesView = new EditAttributesView(isParameter);
    EditAttributesController editAttributesController = new EditAttributesController(editAttributesView,
        view.getAttributeGrid(), attribute, true);
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

  public void setContentWindow(Window contentWindow) {
    this.contentWindow = contentWindow;
  }

}
