package com.uml2Java.client;

import com.google.gwt.core.client.EntryPoint;

import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.core.client.util.Padding;
import com.sencha.gxt.widget.core.client.box.MessageBox;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.*;
import com.sencha.gxt.widget.core.client.event.SelectEvent;

/**
 * Entry point classes define <code>onModuleLoad()</code>
 */
public class Uml2Java implements EntryPoint {

  /**
   * This is the entry point method.
   */
  public void onModuleLoad() {
    Viewport viewport = new Viewport();
    RootPanel.get("uml2java").add(viewport);

    CenterLayoutContainer centerPanel = new CenterLayoutContainer();

    BoxLayoutContainer.BoxLayoutData flex = new BoxLayoutContainer.BoxLayoutData(new Margins(0, 5, 0, 20));
    BoxLayoutContainer.BoxLayoutData flex2 = new BoxLayoutContainer.BoxLayoutData(new Margins(0, 5, 0, 0));
    flex.setFlex(1);
    flex2.setFlex(3);
    HBoxLayoutContainer northPanel = new HBoxLayoutContainer();
    HBoxLayoutContainer buttonsPanel = new HBoxLayoutContainer();
    northPanel.setHBoxLayoutAlign(HBoxLayoutContainer.HBoxLayoutAlign.MIDDLE);
    northPanel.setStyleName("headerPanel");
    buttonsPanel.setHBoxLayoutAlign(HBoxLayoutContainer.HBoxLayoutAlign.MIDDLE);
    buttonsPanel.setPack(BoxLayoutContainer.BoxLayoutPack.END);
    buttonsPanel.setHeight(50);
    buttonsPanel.setPadding(new Padding(0, 10, 0, 0));

    northPanel.add(new Image(Uml2JavaController.ICONS.logo()), flex);
    northPanel.add(buttonsPanel, flex2);

    BorderLayoutContainer mainContainer = new BorderLayoutContainer();
    mainContainer.setNorthWidget(northPanel, new BorderLayoutContainer.BorderLayoutData(50));
    mainContainer.setCenterWidget(centerPanel);

//    mainContainer.setStyleName("");
    viewport.add(mainContainer);

    Uml2JavaController.getInstance().setViews(mainContainer, buttonsPanel);
    Uml2JavaController.getInstance().displayLoginWindow();

//    final TextButton button = new TextButton("Click me");
//    final Label label = new Label();
//
//    button.addSelectHandler(new SelectEvent.SelectHandler() {
//      @Override
//      public void onSelect(SelectEvent event) {
//        if (label.getText().equals("")) {
//          Uml2JavaService.App.getInstance().getMessage("Hello, World!", new MyAsyncCallback(label));
//        } else {
//          label.setText("");
//        }
//      }
//    });
//
//    // Assume that the host HTML has elements defined whose
//    // IDs are "slot1", "slot2".  In a real app, you probably would not want
//    // to hard-code IDs.  Instead, you could, for example, search for all
//    // elements with a particular CSS class and replace them with widgets.
//    //
//    RootPanel.get("slot1").add(button);
//    RootPanel.get("slot2").add(label);

//    MouseDownHandler handler = new MouseDownHandler() {
//      @Override
//      public void onMouseDown(MouseDownEvent event) {
//        new MessageBox("",event.getClientX() + " " + event.getClientY()).show();
//      }
//    };
  }

}
