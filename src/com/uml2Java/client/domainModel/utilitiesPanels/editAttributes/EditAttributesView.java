package com.uml2Java.client.domainModel.utilitiesPanels.editAttributes;

import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.cell.core.client.form.ComboBoxCell;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.data.shared.LabelProvider;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.*;
import com.sencha.gxt.widget.core.client.form.CheckBox;
import com.sencha.gxt.widget.core.client.form.ComboBox;
import com.sencha.gxt.widget.core.client.form.FieldLabel;
import com.sencha.gxt.widget.core.client.form.TextField;
import com.sencha.gxt.widget.core.client.toolbar.ToolBar;
import com.uml2Java.client.MainController;
import com.uml2Java.client.domainModel.UmlController;
import com.uml2Java.shared.AccessType;
import com.uml2Java.shared.DataTypes;

/**
 * Created by Cristi on 2/22/2016.
 */
public class EditAttributesView implements EditAttributesController.IEditAttributes{
  private BorderLayoutContainer mainContainer;
  private TextButton applyButton;
  private TextButton cancelButton;
  private TextField displayNameField;
  private ComboBox<AccessType> accessTypeComboBox;
  private ListStore<AccessType> accessTypeListStore;
  private ComboBox<DataTypes> dataTypesComboBox;
  private ListStore<DataTypes> dataTypesListStore;
  private CheckBox isStaticCB;
  private CheckBox isFinalCB;
  private boolean isParameter;

  public EditAttributesView() {
    initGUI();
  }

  public EditAttributesView(boolean isParameter) {
    this.isParameter = isParameter;
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

//    VBoxLayoutContainer vBoxLayoutContainer = new VBoxLayoutContainer();
//    vBoxLayoutContainer.setVBoxLayoutAlign(VBoxLayoutContainer.VBoxLayoutAlign.CENTER);

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
    verticalLayoutContainer.add(new FieldLabel(displayNameField, "Name"));
    if (!isParameter) {
      verticalLayoutContainer.add(new FieldLabel(accessTypeComboBox, "Access Type"));
    }
    HBoxLayoutContainer hBoxLayoutContainer = new HBoxLayoutContainer(HBoxLayoutContainer.HBoxLayoutAlign.MIDDLE);
    isStaticCB = new CheckBox();
    isFinalCB = new CheckBox();
    isStaticCB.setBoxLabel("Password");
    hBoxLayoutContainer.add(isStaticCB);
    isFinalCB.setBoxLabel("Show in List");
    hBoxLayoutContainer.add(isFinalCB);
    hBoxLayoutContainer.setPack(BoxLayoutContainer.BoxLayoutPack.END);
    verticalLayoutContainer.add(hBoxLayoutContainer, new VerticalLayoutContainer.VerticalLayoutData(1, -1));

    verticalLayoutContainer.add(new FieldLabel(dataTypesComboBox, "Data type"));

    CenterLayoutContainer centerLayoutContainer = new CenterLayoutContainer();

    if(isParameter) {
      isStaticCB.setEnabled(false);
    }

    centerLayoutContainer.add(verticalLayoutContainer);
    mainContainer.setCenterWidget(centerLayoutContainer);

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
  public CheckBox getIsStaticCheckBox() {
    return isStaticCB;
  }

  @Override
  public CheckBox getIsFinalCheckBox() {
    return isFinalCB;
  }

  @Override
  public Widget asWidget() {
    return mainContainer;
  }
}
