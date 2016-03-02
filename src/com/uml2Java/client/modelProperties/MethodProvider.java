package com.uml2Java.client.modelProperties;

import com.google.gwt.editor.client.Editor;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;
import com.uml2Java.shared.AccessType;
import com.uml2Java.shared.Attribute;
import com.uml2Java.shared.DataTypes;
import com.uml2Java.shared.Method;

import java.util.List;

/**
 * Created by Cristi on 2/20/2016.
 */
public interface MethodProvider extends PropertyAccess<Method> {
  @Editor.Path("displayName")
  ModelKeyProvider<Method> key();

//  ValueProvider<Method, Long> id();

  ValueProvider<Method, String> displayName();

  ValueProvider<Method, DataTypes> dataType();

  ValueProvider<Method, AccessType> accessType();
}
