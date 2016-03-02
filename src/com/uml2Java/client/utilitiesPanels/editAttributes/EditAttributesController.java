package com.uml2Java.client.utilitiesPanels.editAttributes;

import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.widget.core.client.Window;
import com.sencha.gxt.widget.core.client.box.MessageBox;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.form.CheckBox;
import com.sencha.gxt.widget.core.client.form.ComboBox;
import com.sencha.gxt.widget.core.client.form.TextField;
import com.sencha.gxt.widget.core.client.grid.Grid;
import com.uml2Java.client.Uml2JavaController;
import com.uml2Java.client.shapes.ClassShape;
import com.uml2Java.client.shapes.Shape;
import com.uml2Java.client.uml2javaUtils.TextUtil;
import com.uml2Java.shared.*;

import java.util.List;

/**
 * Created by Cristi on 2/22/2016.
 */
public class EditAttributesController {
  public interface IEditAttributes {
    TextButton getApplyButton();
    TextButton getCancelButton();
    TextField getDisplayNameField();
    ComboBox<AccessType> getAccessTypeComboBox();
    ComboBox<DataTypes> getDataTypesComboBox();
    CheckBox getIsStaticCheckBox();
    CheckBox getIsFinalCheckBox();
    Widget asWidget();
  }

  IEditAttributes view;
  private Window contentWindow;
  private Grid<Attribute> attributeGrid;
  private Attribute selectedAttribute;
  private boolean isParameter;

  public EditAttributesController(IEditAttributes view, Grid<Attribute> attributeGrid, Attribute selectedAttribute) {
    this.view = view;
    this.attributeGrid = attributeGrid;
    this.selectedAttribute = selectedAttribute;
    load();
    if(selectedAttribute != null) {
      loadAttribute();
    }
    addListeners();
  }

  public EditAttributesController(IEditAttributes view, Grid<Attribute> attributeGrid, Attribute selectedAttribute, boolean isParameter) {
    this.view = view;
    this.attributeGrid = attributeGrid;
    this.selectedAttribute = selectedAttribute;
    this.isParameter = isParameter;
    load();
    if(selectedAttribute != null) {
      loadAttribute();
    }
    addListeners();
  }

  private void loadAttribute() {
    view.getDisplayNameField().setValue(selectedAttribute.getDisplayName());
    if(!isParameter) {
      view.getAccessTypeComboBox().setValue(selectedAttribute.getAccessType());
    }
    view.getDataTypesComboBox().setValue(selectedAttribute.getDataType());
    view.getIsStaticCheckBox().setValue(selectedAttribute.isStatic());
    view.getIsFinalCheckBox().setValue(selectedAttribute.isFinal());
  }

  private void load() {
    // load the primitive dataTypes in the combo
    for (PrimitiveDataTypes dataType : PrimitiveDataTypes.values()) {
      view.getDataTypesComboBox().getStore().add(dataType);
    }
    List<Shape> shapes = Uml2JavaController.getInstance().getShapes();
    for (Shape shape : shapes) {
      if (shape instanceof ClassShape) {
        view.getDataTypesComboBox().getStore().add(shape.getDataTpe());
      }
    }

    //load the access types in the combo
    for (AccessType accessType : AccessType.values()) {
      view.getAccessTypeComboBox().getStore().add(accessType);
    }
  }

  private void addListeners() {
    view.getCancelButton().addSelectHandler(new SelectEvent.SelectHandler() {
      @Override
      public void onSelect(SelectEvent event) {
        contentWindow.hide();
      }
    });

    view.getApplyButton().addSelectHandler(new SelectEvent.SelectHandler() {
      @Override
      public void onSelect(SelectEvent event) {
        if (isValid() && isUnique()) {
          if(selectedAttribute == null) {
            Attribute item = new Attribute(view.getDisplayNameField().getValue(),
                view.getDataTypesComboBox().getValue(), view.getAccessTypeComboBox().getValue());
            item.setIsFinal(view.getIsFinalCheckBox().getValue());
            item.setIsStatic(view.getIsStaticCheckBox().getValue());
            if(item.isFinal() && !isParameter) {
              item.setDisplayName(item.getDisplayName().toUpperCase());
            }
            attributeGrid.getStore().add(item);
          }
          else {
            selectedAttribute.setDisplayName(view.getDisplayNameField().getValue());
            selectedAttribute.setDataType(view.getDataTypesComboBox().getValue());
            selectedAttribute.setAccessType(view.getAccessTypeComboBox().getValue());
            selectedAttribute.setIsFinal(view.getIsFinalCheckBox().getValue());
            selectedAttribute.setIsStatic(view.getIsStaticCheckBox().getValue());
            if(selectedAttribute.isFinal() && !isParameter) {
              selectedAttribute.setDisplayName(selectedAttribute.getDisplayName().toUpperCase());
            }
          }
          attributeGrid.getView().refresh(false);
          contentWindow.hide();
        }
      }
    });

  }

  private boolean isUnique() {
    for(Attribute attribute : attributeGrid.getStore().getAll()) {
      if(attribute.getDisplayName().equals(view.getDisplayNameField().getValue()) && !attribute.equals(selectedAttribute)) {
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
    if (view.getAccessTypeComboBox().getValue() == null && !isParameter) {
      new MessageBox("Info", "The access type can't be empty.").show();
      return false;
    }
    if(view.getDataTypesComboBox().getValue() == null) {
      new MessageBox("Info", "The data type can't be empty").show();
      return false;
    }

    return true;
  }

  public void setContentWindow(Window contentWindow) {
    this.contentWindow = contentWindow;
  }
}
