package com.uml2Java.client.shapes;

import com.sencha.gxt.chart.client.draw.Color;
import com.sencha.gxt.chart.client.draw.DrawComponent;
import com.sencha.gxt.chart.client.draw.path.LineTo;
import com.sencha.gxt.chart.client.draw.path.MoveTo;
import com.sencha.gxt.chart.client.draw.path.PathSprite;

import java.util.List;

/**
 * Created by Cristi on 3/5/2016.
 */
public class AssociationLink extends Link {

  public AssociationLink(Shape firstShape, Shape secondShape) {
    super(firstShape, secondShape);
  }

  private PathSprite sprite;

  @Override
  public void draw(DrawComponent drawComponent) {
    if(sprite == null) {
      sprite = new PathSprite();
    }
    sprite.clearCommands();

    List<Point> points = getLinesPoints();
    if (points.size() >= 2) {
      Point point1 = points.get(0);
      for (int i=1; i<points.size(); i++) {
        Point point2 = points.get(i);
        sprite.addCommand(new MoveTo(point1.getX(),point1.getY()));
        sprite.addCommand(new LineTo(point2.getX(), point2.getY()));
        sprite.setStroke(new Color("#000"));
        drawComponent.addSprite(sprite);
        point1 = point2;
      }
    }
  }
}
