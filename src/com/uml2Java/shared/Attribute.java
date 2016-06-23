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
  private boolean isPassword;
  private boolean isShowInList;

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

  public boolean isPassword() {
    return isPassword;
  }

  public void setIsStatic(boolean isStatic) {
    this.isPassword = isStatic;
  }

  public boolean isShowInList() {
    return isShowInList;
  }

  public void setIsFinal(boolean isFinal) {
    this.isShowInList = isFinal;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;

    Attribute attribute = (Attribute) o;

    if (isPassword != attribute.isPassword)
      return false;
    if (isShowInList != attribute.isShowInList)
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
    result = 31 * result + (isPassword ? 1 : 0);
    result = 31 * result + (isShowInList ? 1 : 0);
    return result;
  }
}
