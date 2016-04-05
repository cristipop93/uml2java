package com.uml2Java.client.siteView.shapes;

import com.sencha.gxt.chart.client.draw.DrawComponent;
import com.uml2Java.client.domainModel.shapes.Position;
import com.uml2Java.client.domainModel.uml2javaUtils.Point;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Cristi on 4/2/2016.
 */
public abstract class Flow {
  SiteShape firstShape, secondShape;

  public Flow(SiteShape firstShape, SiteShape secondShape) {
    this.firstShape = firstShape;
    this.secondShape = secondShape;
  }

  public int length(int x1, int y1, int x2, int y2) {
    return (int) Math.sqrt( ((x2-x1) * (x2-x1)) + ((y2 - y1) * (y2 - y1)) );
  }

  public List<Point> getLinesPoints() {
    int fx1 = firstShape.getX();
    int fy1 = firstShape.getY();
    int fx2 = fx1 + firstShape.getWidth();
    int fy2 = fy1 + firstShape.getHeight();
    int sx1 = secondShape.getX();
    int sy1 = secondShape.getY();
    int sx2 = sx1 + secondShape.getWidth();
    int sy2 = sy1 + secondShape.getHeight();

    int fmx = (fx1 + fx2) / 2;
    int fmy = (fy1 + fy2) / 2;

    int smx = (sx1 + sx2) / 2;
    int smy = (sy1 + sy2) / 2;

    int mLength = length(fmx, fmy, smx, smy);

    // first shape
    // determine the intersection between the middle of the shape and one of its side
    Point fPoint;
    // top side
    fPoint = LinesIntersection.getLinesIntersection(fmx, fmy, smx, smy, fx1, fy1, fx2, fy1);
    if (fPoint != null)
      fPoint.setPosition(Position.N);
    if (fPoint == null || fPoint.getX() > fx2 || fPoint.getX() < fx1 || fPoint.getY() > fy2 || fPoint.getY() < fy1 || length(smx, smy, fPoint.getX(), fPoint.getY()) > mLength) {
      // right side
      fPoint = LinesIntersection.getLinesIntersection(fmx, fmy, smx, smy, fx2, fy1, fx2, fy2);
      if (fPoint != null)
        fPoint.setPosition(Position.E);
      if (fPoint == null || fPoint.getX() > fx2 || fPoint.getX() < fx1 || fPoint.getY() > fy2 || fPoint.getY() < fy1 || length(
          smx, smy, fPoint.getX(), fPoint.getY()) > mLength) {
        // bottom side
        fPoint = LinesIntersection.getLinesIntersection(fmx, fmy, smx, smy, fx2, fy2, fx1, fy2);
        if (fPoint != null)
          fPoint.setPosition(Position.S);
        if (fPoint == null || fPoint.getX() > fx2 || fPoint.getX() < fx1 || fPoint.getY() > fy2 || fPoint.getY() < fy1 || length(
            smx, smy, fPoint.getX(), fPoint.getY()) > mLength) {
          // left side
          fPoint = LinesIntersection.getLinesIntersection(fmx, fmy, smx, smy, fx1, fy2, fx1, fy1);
          if (fPoint != null)
            fPoint.setPosition(Position.W);
        }
      }
    }

    // second shape
    // top side
    Point sPoint;
    sPoint = LinesIntersection.getLinesIntersection(fmx, fmy, smx, smy, sx1, sy1, sx2, sy1);
    if (sPoint != null)
      sPoint.setPosition(Position.N);
    if (sPoint == null || sPoint.getX() > sx2 || sPoint.getX() < sx1 || sPoint.getY() > sy2 || sPoint.getY() < sy1 || length(fmx, fmy, sPoint.getX(), sPoint.getY()) > mLength) {
      // right side
      sPoint = LinesIntersection.getLinesIntersection(fmx, fmy, smx, smy, sx2, sy1, sx2, sy2);
      if (sPoint != null)
        sPoint.setPosition(Position.E);
      if (sPoint == null || sPoint.getX() > sx2 || sPoint.getX() < sx1 || sPoint.getY() > sy2 || sPoint.getY() < sy1 || length(
          fmx, fmy, sPoint.getX(), sPoint.getY()) > mLength) {
        // bottom side
        sPoint = LinesIntersection.getLinesIntersection(fmx, fmy, smx, smy, sx2, sy2, sx1, sy2);
        if (sPoint != null)
          sPoint.setPosition(Position.S);
        if (sPoint == null || sPoint.getX() > sx2 || sPoint.getX() < sx1 || sPoint.getY() > sy2 || sPoint.getY() < sy1 || length(
            fmx, fmy, sPoint.getX(), sPoint.getY()) > mLength) {
          // left side
          sPoint = LinesIntersection.getLinesIntersection(fmx, fmy, smx, smy, sx1, sy2, sx1, sy1);
          if (sPoint != null)
            sPoint.setPosition(Position.W);
        }
      }
    }

    List<Point> result = new ArrayList<Point>();
    if (! (fPoint == null || fPoint.getX() > fx2 || fPoint.getX() < fx1 || fPoint.getY() > fy2 || fPoint.getY() < fy1 || length(
        smx, smy, fPoint.getX(), fPoint.getY()) > mLength) )
      result.add(fPoint);
    if (! (sPoint == null || sPoint.getX() > sx2 || sPoint.getX() < sx1 || sPoint.getY() > sy2 || sPoint.getY() < sy1 || length(fmx, fmy, sPoint.getX(), sPoint.getY()) > mLength) )
      result.add(sPoint);

    return result;
  }

  public SiteShape getFirstUmlShape() {
    return firstShape;
  }

  public SiteShape getSecondUmlShape() {
    return secondShape;
  }

  public abstract void draw(DrawComponent drawComponent, double scaleFactor);

  public abstract void remove(DrawComponent drawComponent);
}
