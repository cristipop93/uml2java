package com.uml2Java.client.siteView.shapes;

import com.sencha.gxt.chart.client.draw.DrawComponent;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by Cristi on 3/22/2016.
 */
public abstract class SiteShape {
  protected int id;
  protected int x, y, width, height;
  protected double scaleFactor = 1;
  protected String title;
  protected DrawComponent drawComponent;
  protected Logger log = Logger.getLogger(SiteShape.class.getName());
  protected List<Flow> flows = new ArrayList<Flow>();

  public abstract void translateTo(int mouseX, int mouseY);

  public boolean canBeDragged(int mouseX, int mouseY){
    return mouseX >= x && mouseX <= x + width &&
        mouseY >= y && mouseY <= y + height;
  }

  public abstract int getId();

  public int getX() {
    return x;
  }

  public void setX(int x) {
    this.x = x;
  }

  public int getY() {
    return y;
  }

  public void setY(int y) {
    this.y = y;
  }

  public int getWidth() {
    return width;
  }

  public void setWidth(int width) {
    this.width = width;
  }

  public int getHeight() {
    return height;
  }

  public void setHeight(int height) {
    this.height = height;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public abstract void scaleTo(double scaleFactor);

  public abstract void redraw();

  public abstract void remove();

  protected void drawFlows(double scaleFactor) {
    for(Flow flow : flows) {
      flow.draw(drawComponent, scaleFactor);
    }
  }

  public List<Flow> getFlows() {
    return flows;
  }

  public void addFlow(Flow flow) {
    this.flows.add(flow);
    drawFlows(scaleFactor);
  }

  public abstract void setSelected(boolean isSelected);

  public void removeAllFlows() {
    for(Flow flow : flows) {
      // remove the flow from the drawing component
      flow.remove(drawComponent);
      // remove the flow from the other end shapes flow list.
      if (flow.getFirstUmlShape().equals(this)) {
        flow.getSecondUmlShape().removeFlow(flow);
      } else {
        flow.getFirstUmlShape().removeFlow(flow);
      }
    }
    this.flows.clear();
  }

  private void removeFlow(Flow flow) {
    this.flows.remove(flow);
  }
}
