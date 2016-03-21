package com.uml2Java.client.domainModel.modelProperties;

import com.google.gwt.editor.client.Editor;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;
import com.uml2Java.shared.AccessType;
import com.uml2Java.shared.Attribute;
import com.uml2Java.shared.DataTypes;

/**
 * Created by Cristi on 2/20/2016.
 */
public interface AttributeProvider extends PropertyAccess<Attribute> {
  @Editor.Path("displayName")
  ModelKeyProvider<Attribute> key();

//  ValueProvider<Attribute, Long> id();

  ValueProvider<Attribute, String> displayName();

  ValueProvider<Attribute, DataTypes> dataType();

  ValueProvider<Attribute, AccessType> accessType();

}
