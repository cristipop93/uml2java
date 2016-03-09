package com.uml2Java.client.shapes;

import com.sencha.gxt.chart.client.draw.Color;
import com.sencha.gxt.chart.client.draw.DrawComponent;
import com.sencha.gxt.chart.client.draw.RGB;
import com.sencha.gxt.chart.client.draw.path.LineTo;
import com.sencha.gxt.chart.client.draw.path.MoveTo;
import com.sencha.gxt.chart.client.draw.path.PathSprite;
import com.sencha.gxt.chart.client.draw.sprite.TextSprite;

import java.util.List;

/**
 * Created by Cristi on 3/5/2016.
 */
public class AssociationLink extends Link {

  public AssociationLink(Shape firstShape, Shape secondShape) {
    super(firstShape, secondShape);
  }

  private PathSprite sprite;
  private TextSprite textSprite;

  @Override
  public void draw(DrawComponent drawComponent) {
    if (sprite == null) {
      sprite = new PathSprite();
    }
    if(textSprite == null) {
      textSprite = new TextSprite();
    }
    sprite.clearCommands();

    List<Point> points = getLinesPoints();
    if (points.size() >= 2) {
      Point point1 = points.get(0);
      textSprite.setText("1..*");
      textSprite.setX(point1.getX());
      textSprite.setY(point1.getY());
      textSprite.setFontSize(12);
      textSprite.setFill(RGB.BLACK);
      drawComponent.addSprite(textSprite);
      for (int i = 1; i < points.size(); i++) {
        Point point2 = points.get(i);
        sprite.addCommand(new MoveTo(point1.getX(), point1.getY()));
        sprite.addCommand(new LineTo(point2.getX(), point2.getY()));
        sprite.setStroke(new Color("#000"));
        drawComponent.addSprite(sprite);
        point1 = point2;
      }
    }
  }
}
