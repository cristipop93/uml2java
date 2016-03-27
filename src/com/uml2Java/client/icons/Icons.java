package com.uml2Java.client.icons;

import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;

/**
 * Created by Cristi on 2/4/2016.
 */
public interface Icons extends ClientBundle {


  @ClientBundle.Source("login.png")
  ImageResource login();

  @ClientBundle.Source("logout.png")
  ImageResource logout();

  @ClientBundle.Source("register.png")
  ImageResource register();

  @ClientBundle.Source("logo1.png")
  ImageResource logo();

  @ClientBundle.Source("apply.png")
  ImageResource apply();

  @ClientBundle.Source("cancel.png")
  ImageResource cancel();

  @ClientBundle.Source("edit.png")
  ImageResource edit();

  @ClientBundle.Source("delete.png")
  ImageResource delete();

  @ClientBundle.Source("add.png")
  ImageResource add();

  @ClientBundle.Source("zoomOut.png")
  ImageResource zoomOut();

  @ClientBundle.Source("zoomIn.png")
  ImageResource zoomIn();

  @ClientBundle.Source("home.png")
  ImageResource home();

  @ClientBundle.Source("simpleList.png")
  ImageResource simpleList();

  @ClientBundle.Source("form.png")
  ImageResource form();

  @ClientBundle.Source("details.png")
  ImageResource details();

  @ClientBundle.Source("select.png")
  ImageResource select();

  @ClientBundle.Source("page.png")
  ImageResource page();
}
