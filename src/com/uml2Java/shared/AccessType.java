package com.uml2Java.shared;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * Created by Cristi on 2/15/2016.
 */
public enum AccessType implements IsSerializable {
  PRIVATE(1, "-", "private"),
  PUBLIC(2, "+", "public"),
  PROTECTED(3, "#", "protected");

  int id;
  String symbol;
  String displayName;

  AccessType(int id, String symbol, String displayName) {
    this.id = id;
    this.symbol = symbol;
    this.displayName = displayName;
  }

  public int getId() {
    return id;
  }

  public String getSymbol() {
    return symbol;
  }

  public String getDisplayName() {
    return displayName;
  }
}
