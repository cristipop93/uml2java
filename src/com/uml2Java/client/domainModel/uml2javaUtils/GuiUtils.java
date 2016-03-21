package com.uml2Java.client.domainModel.uml2javaUtils;

import com.google.gwt.core.client.GWT;
import com.sencha.gxt.core.client.IdentityValueProvider;
import com.sencha.gxt.core.client.Style;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.widget.core.client.grid.CheckBoxSelectionModel;
import com.sencha.gxt.widget.core.client.grid.ColumnConfig;
import com.sencha.gxt.widget.core.client.grid.ColumnModel;
import com.sencha.gxt.widget.core.client.grid.Grid;
import com.uml2Java.client.domainModel.modelProperties.AttributeProvider;
import com.uml2Java.client.domainModel.modelProperties.MethodProvider;
import com.uml2Java.shared.Attribute;
import com.uml2Java.shared.Method;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Cristi on 2/26/2016.
 */
public class GuiUtils {
  private static MethodProvider methodProvider = GWT.create(MethodProvider.class);
  private static AttributeProvider attributeProvider = GWT.create(AttributeProvider.class);

  public static Grid<Method> initMethodGrid() {
    IdentityValueProvider<Method> identityValueProvider = new IdentityValueProvider<Method>("sm");
    CheckBoxSelectionModel<Method> selectionModel = new CheckBoxSelectionModel<Method>(identityValueProvider);
    selectionModel.setSelectionMode(Style.SelectionMode.MULTI);

    List<ColumnConfig<Method, ?>> columnConfigList = new ArrayList<ColumnConfig<Method, ?>>();
    columnConfigList.add(selectionModel.getColumn());
    //    columnConfigList.add(new ColumnConfig<Method, Long>(methodProvider.id(), 20, "ID"));
    columnConfigList.add(new ColumnConfig<Method, String>(methodProvider.displayName(), 60, "Name"));
    columnConfigList.add(new ColumnConfig<Method, String>(new ValueProvider<Method, String>() {
      @Override
      public String getValue(Method object) {
        return object.getDataType().getDisplayName();
      }

      @Override
      public void setValue(Method object, String value) {

      }

      @Override
      public String getPath() {
        return null;
      }
    }, 60, "ReturnType"));
    columnConfigList.add(new ColumnConfig<Method, String>(new ValueProvider<Method, String>() {
      @Override
      public String getValue(Method object) {
        return object.getAccessType().getDisplayName();
      }

      @Override
      public void setValue(Method object, String value) {

      }

      @Override
      public String getPath() {
        return null;
      }
    }, 60, "Access Type"));
    ColumnModel<Method> columnModel = new ColumnModel<Method>(columnConfigList);

    ListStore<Method> methodListStore = new ListStore<Method>(methodProvider.key());
    Grid<Method> methGrid = new Grid<Method>(methodListStore, columnModel);
    methGrid.setSelectionModel(selectionModel);
    methGrid.getView().setAutoFill(true);

    return methGrid;
  }
  public static Grid<Attribute> initAttributeGrid() {
    return initAttributeGrid(false);
  }

  public static Grid<Attribute> initAttributeGrid(boolean isParameter) {
    IdentityValueProvider<Attribute> identityValueProvider = new IdentityValueProvider<Attribute>();
    CheckBoxSelectionModel<Attribute> selectionModel = new CheckBoxSelectionModel<Attribute>(identityValueProvider);
    selectionModel.setSelectionMode(Style.SelectionMode.MULTI);

    List<ColumnConfig<Attribute, ?>> columnConfigList = new ArrayList<ColumnConfig<Attribute, ?>>();
    columnConfigList.add(selectionModel.getColumn());
    //    columnConfigList.add(new ColumnConfig<Attribute, Long>(attributeProvider.id(), 20, "ID"));
    columnConfigList.add(new ColumnConfig<Attribute, String>(attributeProvider.displayName(), 60, "Name"));
    columnConfigList.add(new ColumnConfig<Attribute, String>(new ValueProvider<Attribute, String>() {
      @Override
      public String getValue(Attribute object) {
        return object.getDataType().getDisplayName();
      }

      @Override
      public void setValue(Attribute object, String value) {

      }

      @Override
      public String getPath() {
        return null;
      }
    }, 60, "DataType"));
    if(!isParameter) {
      columnConfigList.add(new ColumnConfig<Attribute, String>(new ValueProvider<Attribute, String>() {
        @Override
        public String getValue(Attribute object) {
          return object.getAccessType().getDisplayName();
        }

        @Override
        public void setValue(Attribute object, String value) {

        }

        @Override
        public String getPath() {
          return null;
        }
      }, 40, "Access Type"));
    }
    ColumnModel<Attribute> columnModel = new ColumnModel<Attribute>(columnConfigList);

    ListStore<Attribute> attributeListStore = new ListStore<Attribute>(attributeProvider.key());
    Grid<Attribute> attrGrid = new Grid<Attribute>(attributeListStore, columnModel);
    attrGrid.setSelectionModel(selectionModel);
    attrGrid.getView().setAutoFill(true);

    return attrGrid;
  }
}
