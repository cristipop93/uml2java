package com.uml2Java.client.shapes;

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
}
