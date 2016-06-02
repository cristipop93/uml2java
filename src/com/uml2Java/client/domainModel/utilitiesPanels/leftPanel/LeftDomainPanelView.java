package com.uml2Java.client.domainModel.utilitiesPanels.leftPanel;

import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.core.client.util.ToggleGroup;
import com.sencha.gxt.widget.core.client.button.ToggleButton;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.uml2Java.client.MainController;

/**
 * Created by Cristi on 3/13/2016.
 */
public class LeftDomainPanelView implements LeftDomainPanelController.ILeftPanelView {
  private ToggleButton selectButton;
  private ToggleButton classButton;
  private ToggleButton interfaceButton;
  private ToggleButton generalizationButton;
  private ToggleButton associationButton;

  public LeftDomainPanelView() {
    initGUI();
  }

  private VerticalLayoutContainer mainContainer;

  private void initGUI() {
    mainContainer = new VerticalLayoutContainer();
//    VBoxLayoutContainer vBoxLayoutContainer = new VBoxLayoutContainer();

    ToggleGroup group = new ToggleGroup();
    selectButton = new ToggleButton();
    selectButton.setIcon(MainController.ICONS.select());
    selectButton.setToolTip("Select");
    selectButton.setAllowDepress(false);
    classButton = new ToggleButton();
    classButton.setIcon(MainController.ICONS.classIcon());
    classButton.setToolTip("Class");
    classButton.setAllowDepress(false);
    interfaceButton = new ToggleButton();
    interfaceButton.setIcon(MainController.ICONS.interfaceIcon());
    interfaceButton.setToolTip("Interface");
    interfaceButton.setAllowDepress(false);
    generalizationButton = new ToggleButton();
    generalizationButton.setIcon(MainController.ICONS.generalization());
    generalizationButton.setToolTip("Generalization");
    generalizationButton.setAllowDepress(false);
    associationButton = new ToggleButton();
    associationButton.setIcon(MainController.ICONS.association());
    associationButton.setToolTip("Association");
    associationButton.setAllowDepress(false);
    group.add(selectButton);
    group.add(classButton);
    group.add(interfaceButton);
    group.add(generalizationButton);
    group.add(associationButton);
    group.setValue(selectButton);

//    vBoxLayoutContainer.add(selectButton, new BoxLayoutContainer.BoxLayoutData(new Margins(5,0,0,10)));
//    vBoxLayoutContainer.add(classButton, new BoxLayoutContainer.BoxLayoutData(new Margins(5,0,0,10)));
    mainContainer.add(selectButton, new VerticalLayoutContainer.VerticalLayoutData(-1, -1, new Margins(5)));
    mainContainer.add(classButton, new VerticalLayoutContainer.VerticalLayoutData(-1, -1, new Margins(5)));
    mainContainer.add(interfaceButton, new VerticalLayoutContainer.VerticalLayoutData(-1, -1, new Margins(5)));
    mainContainer.add(generalizationButton, new VerticalLayoutContainer.VerticalLayoutData(-1, -1, new Margins(5)));
    mainContainer.add(associationButton, new VerticalLayoutContainer.VerticalLayoutData(-1, -1, new Margins(5)));
  }

  @Override
  public Widget asWidget() {
    return mainContainer;
  }

  @Override
  public ToggleButton getSelectButton() {
    return selectButton;
  }

  @Override
  public ToggleButton getClassButton() {
    return classButton;
  }

  @Override
  public ToggleButton getInterfaceButton() {
    return interfaceButton;
  }

  @Override
  public ToggleButton getGeneralizationButton() {
    return generalizationButton;
  }

  @Override
  public ToggleButton getAssociationButton() {
    return associationButton;
  }
}
