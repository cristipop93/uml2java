package com.uml2Java.shared;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * Created by Cristi on 6/5/2016.
 */
public class FlowDTO implements IsSerializable {
  private long firstComponentId;
  private ShapeType firstComponentType;
  private long secondComponentId;
  private ShapeType secondComponentType;
  private ShapeType type;

  public FlowDTO() {
  }

  public FlowDTO(long firstComponentId, ShapeType firstComponentType, long secondComponentId,
      ShapeType secondComponentType, ShapeType type) {
    this.firstComponentId = firstComponentId;
    this.firstComponentType = firstComponentType;
    this.secondComponentId = secondComponentId;
    this.secondComponentType = secondComponentType;
    this.type = type;
  }

  public long getFirstComponentId() {
    return firstComponentId;
  }

  public void setFirstComponentId(long firstComponentId) {
    this.firstComponentId = firstComponentId;
  }

  public long getSecondComponentId() {
    return secondComponentId;
  }

  public void setSecondComponentId(long secondComponentId) {
    this.secondComponentId = secondComponentId;
  }

  public ShapeType getType() {
    return type;
  }

  public void setType(ShapeType type) {
    this.type = type;
  }

  public ShapeType getFirstComponentType() {
    return firstComponentType;
  }

  public void setFirstComponentType(ShapeType firstComponentType) {
    this.firstComponentType = firstComponentType;
  }

  public ShapeType getSecondComponentType() {
    return secondComponentType;
  }

  public void setSecondComponentType(ShapeType secondComponentType) {
    this.secondComponentType = secondComponentType;
  }
}
