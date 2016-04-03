package com.uml2Java.client.domainModel.uml2javaUtils;

import com.uml2Java.client.domainModel.shapes.Position;

/**
 * Created by Cristi on 3/5/2016.
 */
public class Point {
  private int x, y;
  //Position relative to the shape
  private Position position;

  public Point(int x, int y, Position position) {
    this.x = x;
    this.y = y;
    this.position = position;
  }

  public int getX() {
    return x;
  }

  public int getY() {
    return y;
  }

  public Position getPosition() {
    return position;
  }

  public void setPosition(Position position) {
    this.position = position;
  }
}
