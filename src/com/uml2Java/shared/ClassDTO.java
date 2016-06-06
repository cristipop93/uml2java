package com.uml2Java.shared;

import com.google.gwt.user.client.rpc.IsSerializable;

import java.util.List;

/**
 * Created by Cristi on 6/5/2016.
 */
public class ClassDTO implements IsSerializable {
  private long id;
  private String title;
  private List<Attribute> attributes;
  private List<Method> methods;

  public ClassDTO() {
  }

  public ClassDTO(long id, String title, List<Attribute> attributes, List<Method> methods) {
    this.id = id;
    this.title = title;
    this.attributes = attributes;
    this.methods = methods;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public List<Attribute> getAttributes() {
    return attributes;
  }

  public void setAttributes(List<Attribute> attributes) {
    this.attributes = attributes;
  }

  public List<Method> getMethods() {
    return methods;
  }

  public void setMethods(List<Method> methods) {
    this.methods = methods;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }
}
