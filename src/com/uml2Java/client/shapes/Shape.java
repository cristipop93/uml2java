package com.uml2Java.client.shapes;

import com.sencha.gxt.chart.client.draw.DrawComponent;
import com.uml2Java.shared.DataTypes;

import java.util.List;

/**
 * Created by Cristi on 2/10/2016.
 */
public abstract class Shape {
  protected static int shapeId = 1;
  protected int id;
  protected int x, y, width, height;
  protected String title;
  protected DataTypes dataTpe;
  //  protected List<Shape> binds;

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
    this.dataTpe.setDisplayName(title);
  }

  public abstract void scaleTo(double scaleFactor);

  public DataTypes getDataTpe() {
    return dataTpe;
  }

  public abstract void redraw();

  public abstract void drawLinks();
}
