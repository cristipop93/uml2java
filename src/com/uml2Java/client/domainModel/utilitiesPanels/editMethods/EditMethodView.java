package com.uml2Java.client.domainModel.utilitiesPanels.editMethods;

import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.cell.core.client.form.ComboBoxCell;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.data.shared.LabelProvider;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.*;
import com.sencha.gxt.widget.core.client.form.*;
import com.sencha.gxt.widget.core.client.grid.Grid;
import com.sencha.gxt.widget.core.client.toolbar.ToolBar;
import com.uml2Java.client.MainController;
import com.uml2Java.client.domainModel.UmlController;
import com.uml2Java.client.domainModel.uml2javaUtils.GuiUtils;
import com.uml2Java.shared.AccessType;
import com.uml2Java.shared.Attribute;
import com.uml2Java.shared.DataTypes;

/**
 * Created by Cristi on 2/26/2016.
 */
public class EditMethodView implements EditMethodController.IEditMethod {
  private BorderLayoutContainer mainContainer;
  private TextButton applyButton;
  private TextButton cancelButton;
  private TextField displayNameField;
  private ComboBox<AccessType> accessTypeComboBox;
  private ListStore<AccessType> accessTypeListStore;
  private ComboBox<DataTypes> dataTypesComboBox;
  private ListStore<DataTypes> dataTypesListStore;
  private Grid<Attribute> attributeGrid;
  private TextButton addAttribute;
  private TextButton editAttribute;
  private TextButton removeAttribute;
  private CheckBox isStaticCB;
  private CheckBox isFinalCB;

  public EditMethodView() {
    initGUI();
  }

  private void initGUI() {
    mainContainer = new BorderLayoutContainer();

    ToolBar toolBar = new ToolBar();
    applyButton = new TextButton("Apply", MainController.ICONS.apply());
    cancelButton = new TextButton("Cancel", MainController.ICONS.cancel());
    toolBar.add(applyButton);
    toolBar.add(cancelButton);

    BorderLayoutContainer.BorderLayoutData leftLayoutData = new BorderLayoutContainer.BorderLayoutData(30);
    leftLayoutData.setMargins(new Margins(0, 5, 0, 0));
    mainContainer.setNorthWidget(toolBar, leftLayoutData);

    VBoxLayoutContainer vBoxLayoutContainer = new VBoxLayoutContainer();
    vBoxLayoutContainer.setVBoxLayoutAlign(VBoxLayoutContainer.VBoxLayoutAlign.CENTER);

    displayNameField = new TextField();
    displayNameField.setWidth(150);

    accessTypeListStore = new ListStore<AccessType>(new ModelKeyProvider<AccessType>() {
      @Override
      public String getKey(AccessType item) {
        return item.getId() + "";
      }
    });
    accessTypeComboBox = new ComboBox<AccessType>(accessTypeListStore, new LabelProvider<AccessType>() {
      @Override
      public String getLabel(AccessType item) {
        return item.getSymbol() + " : " + item.getDisplayName();
      }
    });
    accessTypeComboBox.setTriggerAction(ComboBoxCell.TriggerAction.ALL);
    accessTypeComboBox.setTypeAhead(false);
    accessTypeComboBox.setEditable(false);
    accessTypeComboBox.setForceSelection(true);
    accessTypeComboBox.setWidth(150);

    dataTypesListStore = new ListStore<DataTypes>(new ModelKeyProvider<DataTypes>() {
      @Override
      public String getKey(DataTypes item) {
        return item.getId() + "";
      }
    });
    dataTypesComboBox = new ComboBox<DataTypes>(dataTypesListStore, new LabelProvider<DataTypes>() {
      @Override
      public String getLabel(DataTypes item) {
        return item.getDisplayName();
      }
    });
    dataTypesComboBox.setTriggerAction(ComboBoxCell.TriggerAction.ALL);
    dataTypesComboBox.setTypeAhead(false);
    dataTypesComboBox.setEditable(false);
    dataTypesComboBox.setForceSelection(true);
    dataTypesComboBox.setWidth(150);

    VerticalLayoutContainer verticalLayoutContainer = new VerticalLayoutContainer();

    verticalLayoutContainer.add(new FieldLabel(displayNameField, "Name"),
        new VerticalLayoutContainer.VerticalLayoutData(.9, -1, new Margins(5, 0, 0, 30)));
    verticalLayoutContainer.add(new FieldLabel(accessTypeComboBox, "Access Type"),
        new VerticalLayoutContainer.VerticalLayoutData(.9, -1, new Margins(0, 0, 0, 30)));
    verticalLayoutContainer.add(new FieldLabel(dataTypesComboBox, "Return type"),
        new VerticalLayoutContainer.VerticalLayoutData(.9, -1, new Margins(0, 0, 5, 30)));

    HBoxLayoutContainer hBoxLayoutContainer = new HBoxLayoutContainer(HBoxLayoutContainer.HBoxLayoutAlign.MIDDLE);
    isStaticCB = new CheckBox();
    isFinalCB = new CheckBox();
    isStaticCB.setBoxLabel("Static");
    hBoxLayoutContainer.add(isStaticCB);
    isFinalCB.setBoxLabel("Final");
    hBoxLayoutContainer.add(isFinalCB);
    hBoxLayoutContainer.setPack(BoxLayoutContainer.BoxLayoutPack.END);
    verticalLayoutContainer.add(hBoxLayoutContainer, new VerticalLayoutContainer.VerticalLayoutData(.9, -1, new Margins(0, 0, 5, 0)));


    attributeGrid = GuiUtils.initAttributeGrid(true);
    verticalLayoutContainer.add(attributeGrid, new VerticalLayoutContainer.VerticalLayoutData(1,1));
    addAttribute = new TextButton("Add", MainController.ICONS.add());
    editAttribute = new TextButton("Edit", MainController.ICONS.edit());
    removeAttribute = new TextButton("Remove", MainController.ICONS.delete());

    BoxLayoutContainer.BoxLayoutData flex = new BoxLayoutContainer.BoxLayoutData(new Margins(0, 5, 0, 0));
    flex.setFlex(1);
    HBoxLayoutContainer attributeButtonsContainer = new HBoxLayoutContainer();
    attributeButtonsContainer.add(addAttribute, new BoxLayoutContainer.BoxLayoutData(new Margins(5, 0, 0, 5)));
    attributeButtonsContainer.add(editAttribute, new BoxLayoutContainer.BoxLayoutData(new Margins(5, 0, 0, 5)));
    attributeButtonsContainer.add(new Label(), flex);
    attributeButtonsContainer.add(removeAttribute, new BoxLayoutContainer.BoxLayoutData(new Margins(5, 0, 0, 5)));
    verticalLayoutContainer.add(attributeButtonsContainer, new VerticalLayoutContainer.VerticalLayoutData(1, -1, new Margins(0, 0, 10, 0)));
    mainContainer.setCenterWidget(verticalLayoutContainer);
  }


  @Override
  public TextButton getApplyButton() {
    return applyButton;
  }

  @Override
  public TextButton getCancelButton() {
    return cancelButton;
  }

  @Override
  public TextField getDisplayNameField() {
    return displayNameField;
  }

  @Override
  public ComboBox<AccessType> getAccessTypeComboBox() {
    return accessTypeComboBox;
  }

  @Override
  public ComboBox<DataTypes> getDataTypesComboBox() {
    return dataTypesComboBox;
  }

  @Override
  public Grid<Attribute> getAttributeGrid() {
    return attributeGrid;
  }

  @Override
  public TextButton getAddAttribute() {
    return addAttribute;
  }

  @Override
  public TextButton getEditAttribute() {
    return editAttribute;
  }

  @Override
  public TextButton getRemoveAttribute() {
    return removeAttribute;
  }

  @Override
  public CheckBox getIsStaticCB() {
    return isStaticCB;
  }

  @Override
  public CheckBox getIsFinalCB() {
    return isFinalCB;
  }

  @Override
  public Widget asWidget() {
    return mainContainer;
  }
}
