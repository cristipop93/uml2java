package com.uml2Java.client.service;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface Uml2JavaServiceAsync {
  void getMessage(String msg, AsyncCallback<String> async);
}
