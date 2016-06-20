package com.uml2Java.client.siteView.siteUtils;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * Created by Cristi on 6/20/2016.
 */
public enum Framework implements IsSerializable {
  BASIC ("Basic"),
  IONIC2 ("Ionic 2");

  String name;

  Framework(String name) {
    this.name = name;
  }
}
