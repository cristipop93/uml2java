package com.uml2Java.shared;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * Created by Cristi on 2/15/2016.
 */
public class ObjectDataTypes implements IsSerializable, DataTypes {
  private int id;
  private String displayName;

  public ObjectDataTypes(int id, String displayName) {
    this.id = id;
    this.displayName = displayName;
  }

  public ObjectDataTypes(String displayName) {
    this.displayName = displayName;
  }

  public ObjectDataTypes() {
  }

  @Override
  public int getId() {
    return id;
  }

  @Override
  public void setId(int id) {
    this.id = id;
  }

  @Override
  public String getDisplayName() {
    return displayName;
  }

  @Override
  public void setDisplayName(String displayName) {
    this.displayName = displayName;
  }
}
