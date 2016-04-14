package com.uml2Java.client.siteView.shapes;

import com.sencha.gxt.chart.client.draw.Color;
import com.sencha.gxt.chart.client.draw.DrawComponent;
import com.sencha.gxt.chart.client.draw.path.LineTo;
import com.sencha.gxt.chart.client.draw.path.MoveTo;
import com.sencha.gxt.chart.client.draw.path.PathSprite;
import com.sencha.gxt.chart.client.draw.sprite.CircleSprite;
import com.uml2Java.client.domainModel.shapes.Position;
import com.uml2Java.client.domainModel.uml2javaUtils.Point;

import java.util.List;

/**
 * Created by Cristi on 4/14/2016.
 */
public class KoFlow extends Flow{
  private PathSprite line;
  private PathSprite triangle;
  private CircleSprite circle;
  private static final int halfWidth = 5;
  private static final int height = 15;

  public KoFlow(SiteShape firstShape, SiteShape secondShape) {
    super(firstShape, secondShape);
  }

  @Override
  public void draw(DrawComponent drawComponent, double scaleFactor) {
    if (line == null) {
      line = new PathSprite();
    }
    if (triangle == null) {
      triangle = new PathSprite();
    }
    if (circle == null) {
      circle = new CircleSprite();
    }
    int halfWidth =(int) (this.halfWidth * scaleFactor);
    int height = (int) (this.height * scaleFactor);
    line.clearCommands();
    line.setStrokeWidth(0.7);
    triangle.clearCommands();
    List<Point> points = getLinesPoints();
    if (points == null || points.size() < 2)
      return;
    Point point1 = points.get(0);
    Point point2 = points.get(1);

    if (point1.getPosition() == Position.N) {
      triangle.addCommand(new MoveTo(point1.getX() - halfWidth, point1.getY() - height));
      triangle.addCommand(new LineTo(point1.getX() + halfWidth, point1.getY() - height));
      triangle.addCommand(new LineTo(point1.getX(), point1.getY()));
      triangle.addCommand(new LineTo(point1.getX() - halfWidth, point1.getY() - height));
      line.addCommand(new MoveTo(point1.getX(), point1.getY() - height));
    } else if (point1.getPosition() == Position.S) {
      triangle.addCommand(new MoveTo(point1.getX() - halfWidth, point1.getY() + height));
      triangle.addCommand(new LineTo(point1.getX() + halfWidth, point1.getY() + height));
      triangle.addCommand(new LineTo(point1.getX(), point1.getY()));
      triangle.addCommand(new LineTo(point1.getX() - halfWidth, point1.getY() + height));
      line.addCommand(new MoveTo(point1.getX(), point1.getY() + height));
    } else if (point1.getPosition() == Position.E) {
      triangle.addCommand(new MoveTo(point1.getX() + height, point1.getY() - halfWidth));
      triangle.addCommand(new LineTo(point1.getX() + height, point1.getY() + halfWidth));
      triangle.addCommand(new LineTo(point1.getX(), point1.getY()));
      triangle.addCommand(new LineTo(point1.getX() + height, point1.getY() - halfWidth));
      line.addCommand(new MoveTo(point1.getX() + height, point1.getY()));
    } else if (point1.getPosition() == Position.W) {
      triangle.addCommand(new MoveTo(point1.getX() - height, point1.getY() - halfWidth));
      triangle.addCommand(new LineTo(point1.getX() - height, point1.getY() + halfWidth));
      triangle.addCommand(new LineTo(point1.getX(), point1.getY()));
      triangle.addCommand(new LineTo(point1.getX() - height, point1.getY() - halfWidth));
      line.addCommand(new MoveTo(point1.getX() - height, point1.getY()));
    }

    line.addCommand(new LineTo(point2.getX(), point2.getY()));
    line.setStroke(new Color("#D00"));
    line.setStrokeWidth(0.5);

    circle.setCenterX(point2.getX());
    circle.setCenterY(point2.getY());
    circle.setRadius(5);
    circle.setScaling(scaleFactor);
    circle.setStroke(new Color("#D00"));
    circle.setFill(new Color("#D00"));

    triangle.setStroke(new Color("#D00"));
    triangle.setFill(new Color("#D00"));

    drawComponent.addSprite(line);
    drawComponent.addSprite(triangle);
    drawComponent.addSprite(circle);

    line.redraw();
    triangle.redraw();
    circle.redraw();
  }

  @Override
  public void remove(DrawComponent drawComponent) {
    drawComponent.remove(line);
    drawComponent.remove(triangle);
    drawComponent.remove(circle);
  }

}
