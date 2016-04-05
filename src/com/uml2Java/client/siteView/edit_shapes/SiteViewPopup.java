package com.uml2Java.client.siteView.edit_shapes;

import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.cell.core.client.form.ComboBoxCell;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.data.shared.LabelProvider;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.BoxLayoutContainer;
import com.sencha.gxt.widget.core.client.container.HBoxLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.form.ComboBox;
import com.sencha.gxt.widget.core.client.form.TextField;
import com.uml2Java.client.MainController;
import com.uml2Java.shared.DataTypes;

/**
 * Created by Cristi on 4/5/2016.
 */
public class SiteViewPopup implements SiteViewPopupController.ISiteViewPopup {
  private ContentPanel contentPanel;
  private VerticalLayoutContainer mainContainer;
  private TextField titleField;
  private ComboBox<DataTypes> dataTypesComboBox;
  private ListStore<DataTypes> dataTypesListStore;
  private HBoxLayoutContainer hBoxLayoutContainer;
  private TextButton applyButton;
  private TextButton cancelButton;

  public SiteViewPopup() {
    initGUI();
  }

  private void initGUI() {
    contentPanel = new ContentPanel();
    contentPanel.setHeadingText("Details");
    mainContainer = new VerticalLayoutContainer();
    titleField = new TextField();
    titleField.setWidth(150);
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

    applyButton = new TextButton("Apply", MainController.ICONS.apply());
    cancelButton = new TextButton("Cancel", MainController.ICONS.cancel());

    hBoxLayoutContainer = new HBoxLayoutContainer(HBoxLayoutContainer.HBoxLayoutAlign.MIDDLE);
    hBoxLayoutContainer.setPack(BoxLayoutContainer.BoxLayoutPack.END);
    hBoxLayoutContainer.add(applyButton, new BoxLayoutContainer.BoxLayoutData(new Margins(5)));
    hBoxLayoutContainer.add(cancelButton, new BoxLayoutContainer.BoxLayoutData(new Margins(5)));

    contentPanel.add(mainContainer);
  }

  @Override
  public HBoxLayoutContainer getHBoxlayoutContainer() {
    return hBoxLayoutContainer;
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
  public VerticalLayoutContainer getMainContainer() {
    return mainContainer;
  }

  @Override
  public TextField getTitleField() {
    return titleField;
  }

  @Override
  public ComboBox<DataTypes> getDataTypesCombo() {
    return dataTypesComboBox;
  }

  @Override
  public Widget asWidget() {
    return contentPanel;
  }
}
