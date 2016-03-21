package com.uml2Java.client.domainModel.shapes;

import com.uml2Java.shared.Attribute;
import com.uml2Java.shared.Method;

import java.util.List;

/**
 * Created by Cristi on 2/21/2016.
 */
public interface IClassShape {
  String getTitle();

  void setTitle(String title);

  List<Attribute> getAttributesList();

  void setAttributesList(List<Attribute> attributes);

  List<Method> getMethodsList();

  void setMethodsList(List<Method> methods);
}
