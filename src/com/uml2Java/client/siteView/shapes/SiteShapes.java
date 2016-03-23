package com.uml2Java.client.siteView.shapes;

import com.sencha.gxt.chart.client.draw.DrawComponent;

/**
 * Created by Cristi on 3/22/2016.
 */
public abstract class SiteShapes {
  protected static int shapeId = 1;
  protected int id;
  protected int x, y, width, height;
  protected double scaleFactor = 1;
  protected String title;
//  protected List<Link> links;
  protected DrawComponent drawComponent;

  public abstract void translateTo(int mouseX, int mouseY);

  public boolean canBeDragged(int mouseX, int mouseY){
    return mouseX >= x && mouseX <= x + width &&
        mouseY >= y && mouseY <= y + height;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

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
}