package com.uml2Java.client.shapes;

import com.sencha.gxt.chart.client.draw.Color;
import com.sencha.gxt.chart.client.draw.DrawComponent;
import com.sencha.gxt.chart.client.draw.path.LineTo;
import com.sencha.gxt.chart.client.draw.path.MoveTo;
import com.sencha.gxt.chart.client.draw.path.PathSprite;
import com.sencha.gxt.chart.client.draw.sprite.TextSprite;

import java.util.List;

/**
 * Created by Cristi on 3/12/2016.
 */
public class GeneralizationLink extends Link {

  private PathSprite line;
  private PathSprite triangle;
  private static final int halfWidth = 5;
  private static final int height = 15;


  public GeneralizationLink(Shape firstShape, Shape secondShape) {
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
    triangle.clearCommands();
    List<Point> points = getLinesPoints();
    if (points.size() >= 2) {
      Point point1 = points.get(0);
      for (int i = 1; i < points.size(); i++) {
        Point point2 = points.get(i);
        line.addCommand(new MoveTo(point1.getX(), point1.getY()));
        line.addCommand(new LineTo(point2.getX(), point2.getY()));
        line.setStroke(new Color("#000"));
        drawComponent.addSprite(line);
        point1 = point2;
      }
      // point1 = las point (near second class)
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
  }

  public void remove(DrawComponent drawComponent) {
    drawComponent.remove(line);
    drawComponent.remove(triangle);
  }
}
