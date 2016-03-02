package com.uml2Java.shared;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * Created by Cristi on 2/17/2016.
 */
public enum PrimitiveDataTypes implements IsSerializable, DataTypes{
  BYTE(-1, "byte"),
  SHORT(-2, "short"),
  INT(-3, "int"),
  LONG(-4, "long"),
  FLOAT(-5, "float"),
  DOUBLE(-6, "double"),
  BOOLEAN(-7, "boolean"),
  CHAR(-8, "char"),
  STRING(-9, "String"),
  VOID(-10, "void");

  int id;
  String displayName;

  PrimitiveDataTypes(int id, String displayName) {
    this.id = id;
    this.displayName = displayName;
  }

  @Override
  public int getId() {
    return id;
  }

  @Override
  public String getDisplayName() {
    return displayName;
  }

  @Override
  public void setId(int id) { }

  @Override
  public void setDisplayName(String displayName) { }
}
