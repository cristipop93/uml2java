package com.uml2Java.shared;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * Created by Cristi on 2/17/2016.
 */
public interface DataTypes extends IsSerializable {
  int getId();

  String getDisplayName();

  void setId(int id);

  void setDisplayName(String displayName);
}
