package com.uml2Java.shared;

import com.google.gwt.user.client.rpc.IsSerializable;

import java.util.List;

/**
 * Created by Cristi on 6/5/2016.
 */
public class ActionDTO implements IsSerializable {
  private long id;
  private String title;
  private List<FlowDTO> flows;

  public ActionDTO() {
  }

  public ActionDTO(long id, String title, List<FlowDTO> flows) {
    this.id = id;
    this.title = title;
    this.flows = flows;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public List<FlowDTO> getFlows() {
    return flows;
  }

  public void setFlows(List<FlowDTO> flows) {
    this.flows = flows;
  }
}
