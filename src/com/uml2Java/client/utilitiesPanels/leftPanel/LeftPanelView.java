package com.uml2Java.client.utilitiesPanels.leftPanel;

import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.core.client.util.ToggleGroup;
import com.sencha.gxt.widget.core.client.button.ToggleButton;
import com.sencha.gxt.widget.core.client.container.BoxLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VBoxLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;

/**
 * Created by Cristi on 3/13/2016.
 */
public class LeftPanelView implements LeftPanelController.ILeftPanelView {
  private ToggleButton selectButton;
  private ToggleButton classButton;
  private ToggleButton interfaceButton;

  public LeftPanelView() {
    initGUI();
  }

  private VerticalLayoutContainer mainContainer;

  private void initGUI() {
    mainContainer = new VerticalLayoutContainer();
//    VBoxLayoutContainer vBoxLayoutContainer = new VBoxLayoutContainer();

    ToggleGroup group = new ToggleGroup();
    selectButton = new ToggleButton("Select");
    classButton = new ToggleButton("Class");
    interfaceButton = new ToggleButton("Interface");
    group.add(selectButton);
    group.add(classButton);
    group.add(interfaceButton);
    group.setValue(selectButton);

//    vBoxLayoutContainer.add(selectButton, new BoxLayoutContainer.BoxLayoutData(new Margins(5,0,0,10)));
//    vBoxLayoutContainer.add(classButton, new BoxLayoutContainer.BoxLayoutData(new Margins(5,0,0,10)));
    mainContainer.add(selectButton, new VerticalLayoutContainer.VerticalLayoutData(-1, -1, new Margins(5, 0, 0, 10)));
    mainContainer.add(classButton, new VerticalLayoutContainer.VerticalLayoutData(-1, -1, new Margins(5, 0, 0, 10)));
    mainContainer.add(interfaceButton, new VerticalLayoutContainer.VerticalLayoutData(-1, -1, new Margins(5, 0, 0, 10)));
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
}
