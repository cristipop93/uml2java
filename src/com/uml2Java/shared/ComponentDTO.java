package com.uml2Java.shared;

import com.google.gwt.user.client.rpc.IsSerializable;

import java.util.List;

/**
 * Created by Cristi on 6/5/2016.
 */
public class ComponentDTO implements IsSerializable {
  private long id;
  private String title;
  private ShapeType type;
  private DataTypes dataTypes;
  private List<FlowDTO> flows;
  private long parentId;

  public ComponentDTO() {
  }

  public ComponentDTO(long id, String title, ShapeType type, DataTypes dataTypes, List<FlowDTO> flows, long parentId) {
    this.id = id;
    this.title = title;
    this.type = type;
    this.dataTypes = dataTypes;
    this.flows = flows;
    this.parentId = parentId;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public ShapeType getType() {
    return type;
  }

  public void setType(ShapeType type) {
    this.type = type;
  }

  public List<FlowDTO> getFlows() {
    return flows;
  }

  public void setFlows(List<FlowDTO> flows) {
    this.flows = flows;
  }

  public DataTypes getDataTypes() {
    return dataTypes;
  }

  public void setDataTypes(DataTypes dataTypes) {
    this.dataTypes = dataTypes;
  }

  public long getParentId() {
    return parentId;
  }

  public void setParentId(long parentId) {
    this.parentId = parentId;
  }
}
