package com.uml2Java.client.siteView.shapes;

import com.uml2Java.client.domainModel.shapes.Position;
import com.uml2Java.client.domainModel.uml2javaUtils.Point;

/**
 * Created by Cristi on 4/2/2016.
 */
public class LinesIntersection {
  private static int Ax1, Ay1, Ax2, Ay2;
  private static int Bx1, By1, Bx2, By2;

  private static float a1 = 0, b1 = 0, c1 = 0;
  private static float a2 = 0, b2 = 0, c2 = 0;

  /**
   * ax1, ay1, ax2, ay2 = points from the first line
   * bx1, by1, bx2, by2 = points from the last line
   */
  public LinesIntersection() {

  }

  private static void solveFunctionParams() {
    a1 = Ay2 - Ay1; // y2 - y1
    b1 = Ax1 - Ax2; // x2 - x1
    c1 = Ax1 * a1 + Ay1 * b1;

    a2 = By2 - By1;
    b2 = Bx1 - Bx2;
    c2 = Bx1 * a2 + By1 * b2;
  }

  private static void setParams(int ax1, int ay1, int ax2, int ay2, int bx1, int by1, int bx2, int by2) {
    Ax1 = ax1;
    Ay1 = ay1;
    Ax2 = ax2;
    Ay2 = ay2;
    Bx1 = bx1;
    By1 = by1;
    Bx2 = bx2;
    By2 = by2;
  }

  public static Point getLinesIntersection(int ax1, int ay1, int ax2, int ay2, int bx1, int by1, int bx2, int by2) {
    setParams(ax1, ay1, ax2, ay2, bx1, by1, bx2, by2);
    solveFunctionParams();
    float delta = (a1 * b2) - (a2 * b1);
    System.out.println(delta);
    if (delta == 0) {
      // lines are parallel
      return null;
    }
    float x = ((b2 * c1) - (b1 * c2)) / delta;
    float y = ((a1 * c2) - (a2 * c1)) / delta;
    return new Point((int) x, (int) y, Position.HORV);
  }

  public static void main(String[] args) {
    Point point = LinesIntersection.getLinesIntersection(5, 1, 5, 4, 3, 2, 1, 3);
    if(point != null)
      System.out.println(point.getX() + " " + point.getY());
    else
      System.out.println("lines are parallel");
  }
}
