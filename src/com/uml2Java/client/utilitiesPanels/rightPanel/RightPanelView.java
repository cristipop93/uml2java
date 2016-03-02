package com.uml2Java.client.utilitiesPanels.rightPanel;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.core.client.IdentityValueProvider;
import com.sencha.gxt.core.client.Style;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.*;
import com.sencha.gxt.widget.core.client.form.FieldLabel;
import com.sencha.gxt.widget.core.client.form.TextField;
import com.sencha.gxt.widget.core.client.grid.CheckBoxSelectionModel;
import com.sencha.gxt.widget.core.client.grid.ColumnConfig;
import com.sencha.gxt.widget.core.client.grid.ColumnModel;
import com.sencha.gxt.widget.core.client.grid.Grid;
import com.sencha.gxt.widget.core.client.toolbar.ToolBar;
import com.uml2Java.client.Uml2JavaController;
import com.uml2Java.client.modelProperties.AttributeProvider;
import com.uml2Java.client.modelProperties.MethodProvider;
import com.uml2Java.client.uml2javaUtils.GuiUtils;
import com.uml2Java.shared.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Cristi on 2/20/2016.
 */
public class RightPanelView implements RightPanelController.IRightPanelView {
  private VerticalLayoutContainer mainContainer;
  private TextButton applyButton;
  private TextButton cancelButton;
  private TextButton removeButton;
  private TextField titleField;
  private Grid<Attribute> attributeGrid;
  private TextButton addAttribute;
  private TextButton editAttribute;
  private TextButton removeAttribute;
  private Grid<Method> methodGrid;
  private TextButton addMethod;
  private TextButton editMethod;
  private TextButton removeMethod;


  public RightPanelView() {
    initGUI();
  }

  private void initGUI() {
    AccordionLayoutContainer.AccordionLayoutAppearance appearance = GWT
        .create(AccordionLayoutContainer.AccordionLayoutAppearance.class);
    BoxLayoutContainer.BoxLayoutData flex = new BoxLayoutContainer.BoxLayoutData(new Margins(0, 5, 0, 0));
    flex.setFlex(1);
    mainContainer = new VerticalLayoutContainer();
    mainContainer.setStyleName("panelBackground");
    titleField = new TextField();

    ToolBar toolBar = new ToolBar();
    applyButton = new TextButton("Apply", Uml2JavaController.ICONS.apply());
    cancelButton = new TextButton("Cancel", Uml2JavaController.ICONS.cancel());
    removeButton = new TextButton("Remove", Uml2JavaController.ICONS.delete());
    toolBar.add(applyButton);
    toolBar.add(cancelButton);
    toolBar.add(new Label(), flex);
    toolBar.add(removeButton);

    Label attributeLabel = new Label("Attributes:");
    Label methodLabel = new Label("Methods:");
    Label linkLabel = new Label("Links:");
    attributeLabel.setStyleName("myLabel");
    methodLabel.setStyleName("myLabel");
    linkLabel.setStyleName("myLabel");
    attributeGrid = GuiUtils.initAttributeGrid();

    addAttribute = new TextButton("Add", Uml2JavaController.ICONS.add());
    editAttribute = new TextButton("Edit", Uml2JavaController.ICONS.edit());
    removeAttribute = new TextButton("Remove", Uml2JavaController.ICONS.delete());

    HBoxLayoutContainer attributeButtonsContainer = new HBoxLayoutContainer();
    attributeButtonsContainer.add(addAttribute, new BoxLayoutContainer.BoxLayoutData(new Margins(5, 0, 0, 5)));
    attributeButtonsContainer.add(editAttribute, new BoxLayoutContainer.BoxLayoutData(new Margins(5, 0, 0, 5)));
    attributeButtonsContainer.add(new Label(), flex);
    attributeButtonsContainer.add(removeAttribute, new BoxLayoutContainer.BoxLayoutData(new Margins(5, 0, 0, 5)));

    methodGrid = GuiUtils.initMethodGrid();

    addMethod = new TextButton("Add", Uml2JavaController.ICONS.add());
    editMethod = new TextButton("Edit", Uml2JavaController.ICONS.edit());
    removeMethod = new TextButton("Remove", Uml2JavaController.ICONS.delete());

    HBoxLayoutContainer methodButtonsContainer = new HBoxLayoutContainer();
    methodButtonsContainer.add(addMethod, new BoxLayoutContainer.BoxLayoutData(new Margins(5, 0, 0, 5)));
    methodButtonsContainer.add(editMethod, new BoxLayoutContainer.BoxLayoutData(new Margins(5, 0, 0, 5)));
    methodButtonsContainer.add(new Label(), flex);
    methodButtonsContainer.add(removeMethod, new BoxLayoutContainer.BoxLayoutData(new Margins(5, 0, 0, 5)));

    mainContainer.add(toolBar, new VerticalLayoutContainer.VerticalLayoutData(1, -1));
    mainContainer.add(new FieldLabel(titleField, "Class name"),
        new VerticalLayoutContainer.VerticalLayoutData(1, -1, new Margins(5, 5, 10, 5)));

    AccordionLayoutContainer accordion = new AccordionLayoutContainer();
    accordion.setExpandMode(AccordionLayoutContainer.ExpandMode.SINGLE_FILL);

    mainContainer.add(accordion, new VerticalLayoutContainer.VerticalLayoutData(1, 1));
    ContentPanel attributePanel = new ContentPanel(appearance);
    attributePanel.setHeadingText("Attributes");
    VerticalLayoutContainer attributesVContainer = new VerticalLayoutContainer();
    attributesVContainer
        .add(attributeGrid, new VerticalLayoutContainer.VerticalLayoutData(1, 1, new Margins(7, 0, 0, 0)));
    attributesVContainer.add(attributeButtonsContainer,
        new VerticalLayoutContainer.VerticalLayoutData(1, -1, new Margins(0, 0, 10, 0)));
    attributePanel.add(attributesVContainer);

    ContentPanel methodPanel = new ContentPanel(appearance);
    methodPanel.setHeadingText("Methods");
    VerticalLayoutContainer methodsVContainer = new VerticalLayoutContainer();
    methodsVContainer.add(methodGrid, new VerticalLayoutContainer.VerticalLayoutData(1, 1, new Margins(7, 0, 0, 0)));
    methodsVContainer
        .add(methodButtonsContainer, new VerticalLayoutContainer.VerticalLayoutData(1, -1, new Margins(0, 0, 10, 0)));
    methodPanel.add(methodsVContainer);

    ContentPanel linkPanel = new ContentPanel(appearance);
    linkPanel.setHeadingText("Links");

    accordion.add(attributePanel);
    accordion.add(methodPanel);
    accordion.add(linkPanel);
    accordion.setActiveWidget(attributePanel);

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
  public TextButton getRemoveButton() {
    return removeButton;
  }

  @Override
  public TextField getTitleField() {
    return titleField;
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
  public Grid<Method> getMethodGrid() {
    return methodGrid;
  }

  @Override
  public TextButton getAddMethod() {
    return addMethod;
  }

  @Override
  public TextButton getEditMethod() {
    return editMethod;
  }

  @Override
  public TextButton getRemoveMethod() {
    return removeMethod;
  }

  @Override
  public Widget asWidget() {
    return mainContainer;
  }
}
