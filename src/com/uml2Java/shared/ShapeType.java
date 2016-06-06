package com.uml2Java.shared;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * Created by Cristi on 6/4/2016.
 */
public enum ShapeType implements IsSerializable {
  PAGE, ACTION, COMPONENT, CLASS, INTERFACE, LIST, FORM, DETAILS, OKFLOW, KOFLOW, NAVIGATIONFLOW;
}
