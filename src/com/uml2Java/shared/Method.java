package com.uml2Java.shared;

import com.google.gwt.user.client.rpc.IsSerializable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Cristi on 2/16/2016.
 */
public class Method implements IsSerializable {
  private long id;
  private String displayName;
  private AccessType accessType;
  private DataTypes dataType;
  private List<Attribute> attributes;
  private boolean isStatic;
  private boolean isFinal;

  public Method(String displayName, AccessType accessType, DataTypes dataType, List<Attribute> attributes) {
    this.displayName = displayName;
    this.accessType = accessType;
    this.dataType = dataType;
    this.attributes = new ArrayList<Attribute>(attributes);
  }

  public Method(long id, String displayName, AccessType accessType, DataTypes dataType, List<Attribute> attributes) {
    this.id = id;
    this.displayName = displayName;
    this.accessType = accessType;
    this.dataType = dataType;
    this.attributes = new ArrayList<Attribute>(attributes);
  }

  public Method() {
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

  public AccessType getAccessType() {
    return accessType;
  }

  public void setAccessType(AccessType accessType) {
    this.accessType = accessType;
  }

  public DataTypes getDataType() {
    return dataType;
  }

  public void setDataType(DataTypes dataType) {
    this.dataType = dataType;
  }

  public List<Attribute> getAttributes() {
    return attributes;
  }

  public void setAttributes(List<Attribute> attributes) {
    this.attributes = new ArrayList<Attribute>(attributes);
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
  public String toString() {
    String toString = accessType.getSymbol() + " " + displayName + " (";
    for(Attribute attribute : attributes) {
      toString += attribute.displayAsParameter() + ", ";
    }
    if(attributes != null && attributes.size() > 0){
      toString = toString.substring(0, toString.length() - 2);
    }
    toString += "): " + dataType.getDisplayName();
    return toString;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;

    Method method = (Method) o;

    if (isStatic != method.isStatic)
      return false;
    if (isFinal != method.isFinal)
      return false;
    if (displayName != null ? !displayName.equals(method.displayName) : method.displayName != null)
      return false;
    if (accessType != method.accessType)
      return false;
    if (dataType != null ? !dataType.equals(method.dataType) : method.dataType != null)
      return false;
    return !(attributes != null ? !attributes.equals(method.attributes) : method.attributes != null);

  }

  @Override
  public int hashCode() {
    int result = displayName != null ? displayName.hashCode() : 0;
    result = 31 * result + (accessType != null ? accessType.hashCode() : 0);
    result = 31 * result + (dataType != null ? dataType.hashCode() : 0);
    result = 31 * result + (attributes != null ? attributes.hashCode() : 0);
    result = 31 * result + (isStatic ? 1 : 0);
    result = 31 * result + (isFinal ? 1 : 0);
    return result;
  }
}
