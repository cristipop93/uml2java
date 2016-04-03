package com.uml2Java.client.siteView.shapes;

import com.sencha.gxt.chart.client.draw.Color;
import com.sencha.gxt.chart.client.draw.DrawComponent;
import com.sencha.gxt.chart.client.draw.path.LineTo;
import com.sencha.gxt.chart.client.draw.path.MoveTo;
import com.sencha.gxt.chart.client.draw.path.PathSprite;
import com.uml2Java.client.domainModel.shapes.Position;
import com.uml2Java.client.domainModel.uml2javaUtils.Point;

import java.util.List;

/**
 * Created by Cristi on 4/3/2016.
 */
public class NavigationFlow extends Flow {
  private PathSprite line;
  private PathSprite triangle;
  private static final int halfWidth = 5;
  private static final int height = 15;

  public NavigationFlow(SiteShape firstShape, SiteShape secondShape) {
    super(firstShape, secondShape);
  }

  @Override
  public void draw(DrawComponent drawComponent) {
    if (line == null) {
      line = new PathSprite();
    }
    if (triangle == null) {
      triangle = new PathSprite();
    }
    line.clearCommands();
    line.setStrokeWidth(0.7);
    triangle.clearCommands();
    List<Point> points = getLinesPoints();
    if (points == null || points.size() < 2)
      return;
    Point point1 = points.get(0);
    Point point2 = points.get(1);
    line.addCommand(new MoveTo(point1.getX(), point1.getY()));
    line.addCommand(new LineTo(point2.getX(), point2.getY()));
    line.setStroke(new Color("#000"));
    drawComponent.addSprite(line);
    if (point1.getPosition() == Position.N) {
      triangle.addCommand(new MoveTo(point1.getX() - halfWidth, point1.getY() - height));
      triangle.addCommand(new LineTo(point1.getX() + halfWidth, point1.getY() - height));
      triangle.addCommand(new LineTo(point1.getX(), point1.getY()));
      triangle.addCommand(new LineTo(point1.getX() - halfWidth, point1.getY() - height));
    } else if (point1.getPosition() == Position.S) {
      triangle.addCommand(new MoveTo(point1.getX() - halfWidth, point1.getY() + height));
      triangle.addCommand(new LineTo(point1.getX() + halfWidth, point1.getY() + height));
      triangle.addCommand(new LineTo(point1.getX(), point1.getY()));
      triangle.addCommand(new LineTo(point1.getX() - halfWidth, point1.getY() + height));
    } else if (point1.getPosition() == Position.E) {
      triangle.addCommand(new MoveTo(point1.getX() + height, point1.getY() - halfWidth));
      triangle.addCommand(new LineTo(point1.getX() + height, point1.getY() + halfWidth));
      triangle.addCommand(new LineTo(point1.getX(), point1.getY()));
      triangle.addCommand(new LineTo(point1.getX() + height, point1.getY() - halfWidth));
    } else if (point1.getPosition() == Position.W) {
      triangle.addCommand(new MoveTo(point1.getX() - height, point1.getY() - halfWidth));
      triangle.addCommand(new LineTo(point1.getX() - height, point1.getY() + halfWidth));
      triangle.addCommand(new LineTo(point1.getX(), point1.getY()));
      triangle.addCommand(new LineTo(point1.getX() - height, point1.getY() - halfWidth));
    }
    drawComponent.addSprite(triangle);

  }

  @Override
  public void remove(DrawComponent drawComponent) {
    drawComponent.remove(line);
    drawComponent.remove(triangle);
  }
}
