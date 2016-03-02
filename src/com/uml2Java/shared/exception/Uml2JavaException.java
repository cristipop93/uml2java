package com.uml2Java.shared.exception;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * Created by Cristi on 2/5/2016.
 */
public class Uml2JavaException extends RuntimeException implements IsSerializable {
  public Uml2JavaException() {
  }

  public Uml2JavaException(String message) {
    super(message);
  }

  public Uml2JavaException(String message, Exception cause) {
    super(message, cause);
  }

  public Uml2JavaException(Throwable cause) {
    super(cause);
  }
}
