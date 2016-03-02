package com.uml2Java.shared;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * Created by Cristi on 2/16/2016.
 */
public class Attribute implements IsSerializable {
  private long id;
  private String displayName;
  private DataTypes dataType;
  private AccessType accessType;
  private boolean isStatic;
  private boolean isFinal;

  public Attribute(String displayName, DataTypes dataType, AccessType accessType) {
    this.displayName = displayName;
    this.dataType = dataType;
    this.accessType = accessType;
  }

  public Attribute(String displayName, DataTypes dataType) {
    this.displayName = displayName;
    this.dataType = dataType;
  }

  public Attribute(long id, String displayName, DataTypes dataType) {
    this.id = id;
    this.displayName = displayName;
    this.dataType = dataType;
  }

  public Attribute(long id, String displayName, DataTypes dataType, AccessType accessType) {
    this.id = id;
    this.displayName = displayName;
    this.dataType = dataType;
    this.accessType = accessType;
  }

  public Attribute() {
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getDisplayName() {
    return displayName;
  }

  public void setDisplayName(String displayName) {
    this.displayName = displayName;
  }

  public DataTypes getDataType() {
    return dataType;
  }

  public void setDataType(DataTypes dataType) {
    this.dataType = dataType;
  }

  public AccessType getAccessType() {
    return accessType;
  }

  public void setAccessType(AccessType accessType) {
    this.accessType = accessType;
  }

  @Override
  public String toString() {
    return accessType.getSymbol() + " " + displayName + ": " + dataType.getDisplayName();
  }

  public String displayAsParameter() {
    return displayName + ": " + dataType.getDisplayName();
  }

  public boolean isStatic() {
    return isStatic;
  }

  public void setIsStatic(boolean isStatic) {
    this.isStatic = isStatic;
  }

  public boolean isFinal() {
    return isFinal;
  }

  public void setIsFinal(boolean isFinal) {
    this.isFinal = isFinal;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;

    Attribute attribute = (Attribute) o;

    if (isStatic != attribute.isStatic)
      return false;
    if (isFinal != attribute.isFinal)
      return false;
    if (displayName != null ? !displayName.equals(attribute.displayName) : attribute.displayName != null)
      return false;
    if (dataType != null ? !dataType.equals(attribute.dataType) : attribute.dataType != null)
      return false;
    return accessType == attribute.accessType;

  }

  @Override
  public int hashCode() {
    int result = displayName != null ? displayName.hashCode() : 0;
    result = 31 * result + (dataType != null ? dataType.hashCode() : 0);
    result = 31 * result + (accessType != null ? accessType.hashCode() : 0);
    result = 31 * result + (isStatic ? 1 : 0);
    result = 31 * result + (isFinal ? 1 : 0);
    return result;
  }
}
