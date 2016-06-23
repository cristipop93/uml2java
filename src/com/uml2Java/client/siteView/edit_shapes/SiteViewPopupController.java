package com.uml2Java.client.siteView.edit_shapes;

import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.core.client.util.Padding;
import com.sencha.gxt.widget.core.client.Popup;
import com.sencha.gxt.widget.core.client.box.MessageBox;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.BoxLayoutContainer;
import com.sencha.gxt.widget.core.client.container.HBoxLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.form.ComboBox;
import com.sencha.gxt.widget.core.client.form.TextField;
import com.uml2Java.client.MainController;
import com.uml2Java.client.domainModel.UmlController;
import com.uml2Java.client.domainModel.shapes.ClassUmlShape;
import com.uml2Java.client.domainModel.shapes.UmlShape;
import com.uml2Java.client.domainModel.uml2javaUtils.TextUtil;
import com.uml2Java.client.siteView.shapes.*;
import com.uml2Java.shared.DataTypes;

import java.util.List;

/**
 * Created by Cristi on 4/5/2016.
 */
public class SiteViewPopupController {
  public interface ISiteViewPopup {
    TextField getTitleField();
    ComboBox<DataTypes> getDataTypesCombo();
    VerticalLayoutContainer getMainContainer();
    HBoxLayoutContainer getHBoxlayoutContainer();
    TextButton getApplyButton();
    TextButton getCancelButton();
    Widget asWidget();
  }
  private ISiteViewPopup view;
  private SiteShape siteShape;
  private Popup popup;

  public SiteViewPopupController(ISiteViewPopup view, SiteShape siteShape, Popup popup) {
    this.view = view;
    this.siteShape = siteShape;
    this.popup = popup;
    load();
    addListeners();
  }

  private void load() {
    view.getMainContainer().add(view.getTitleField(),
        new VerticalLayoutContainer.VerticalLayoutData(-1, -1, new Margins(5)));
    view.getTitleField().setValue(siteShape.getTitle());

    if (siteShape instanceof ViewComponentShape) {
      view.getMainContainer().add(view.getDataTypesCombo(),
          new VerticalLayoutContainer.VerticalLayoutData(-1, -1, new Margins(5)));
      List<UmlShape> umlShapes = UmlController.getInstance().getUmlShapes();
      for (UmlShape umlShape : umlShapes) {
        if (umlShape instanceof ClassUmlShape) {
          view.getDataTypesCombo().getStore().add(umlShape.getDataTpe());
        }
      }
      view.getDataTypesCombo().setValue(((ViewComponentShape) siteShape).getDataType());
    }
    view.getMainContainer().add(view.getHBoxlayoutContainer(),
        new VerticalLayoutContainer.VerticalLayoutData(-1, -1, new Margins(3)));

    List<Flow> flows = siteShape.getFlows();
    if (flows.size() != 0) {
      Label lbl = new Label("Flows: ");
      lbl.setStyleName("boldLabel");
      view.getMainContainer().add(lbl, new VerticalLayoutContainer.VerticalLayoutData(-1, -1, new Margins(0, 0, 0, 7)));
    }
    for (final Flow flow : flows) {
      Label lbl = new Label();
      if (flow.getFirstUmlShape().equals(siteShape)) {
        lbl.setText(flow.getSecondUmlShape().getTitle());
      } else {
        lbl.setText(flow.getFirstUmlShape().getTitle());
      }
      lbl.setStyleName("myLabel2");
      if (flow instanceof KoFlow)
        lbl.getElement().getStyle().setColor("red");
      else if (flow instanceof OkFlow)
        lbl.getElement().getStyle().setColor("green");
      HBoxLayoutContainer hBoxLayoutContainer = new HBoxLayoutContainer(HBoxLayoutContainer.HBoxLayoutAlign.MIDDLE);
      //        hBoxLayoutContainer.setPack(BoxLayoutContainer.BoxLayoutPack.START);
      hBoxLayoutContainer.setPadding(new Padding(2));
      hBoxLayoutContainer.add(lbl, new BoxLayoutContainer.BoxLayoutData(new Margins(0, 3, 0, 6)));
      TextButton delete = new TextButton();
      delete.setIcon(MainController.ICONS.delete());
      delete.setToolTip("Delete Flow");
      BoxLayoutContainer.BoxLayoutData flex = new BoxLayoutContainer.BoxLayoutData(new Margins(0, 5, 0, 0));
      flex.setFlex(1);
      hBoxLayoutContainer.add(new Label(), flex);
      hBoxLayoutContainer.add(delete, new BoxLayoutContainer.BoxLayoutData(new Margins(0, 10, 0, 0)));

      view.getMainContainer().add(hBoxLayoutContainer, new VerticalLayoutContainer.VerticalLayoutData(1, -1));
      delete.addSelectHandler(new SelectEvent.SelectHandler() {
        @Override
        public void onSelect(SelectEvent event) {
          addDeleteHandler(siteShape, flow);
        }
      });
    }


  }

  private void addDeleteHandler(SiteShape siteShape, Flow flow) {
    siteShape.removeAFlow(flow);
    popup.hide();
  }

  private void addListeners() {
    view.getCancelButton().addSelectHandler(new SelectEvent.SelectHandler() {
      @Override
      public void onSelect(SelectEvent event) {
        popup.hide();
      }
    });

    view.getApplyButton().addSelectHandler(new SelectEvent.SelectHandler() {
      @Override
      public void onSelect(SelectEvent event) {
        if (isValid()) {
          siteShape.setTitle(view.getTitleField().getValue());
          if (siteShape instanceof  ViewComponentShape) {
            ((ViewComponentShape) siteShape).setDataType(view.getDataTypesCombo().getValue());
          }
          siteShape.redraw();
          popup.hide();
        }
      }
    });
  }

  private boolean isValid() {
    if (TextUtil.isEmptyString(view.getTitleField().getValue())) {
      new MessageBox("Info", "Title cannot be empty").show();
      return false;
    }
    return true;
  }
}
