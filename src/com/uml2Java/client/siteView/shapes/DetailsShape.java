package com.uml2Java.client.siteView.shapes;

import com.sencha.gxt.chart.client.draw.DrawComponent;
import com.uml2Java.client.MainController;
import com.uml2Java.shared.DataTypes;

/**
 * Created by Cristi on 3/25/2016.
 */
public class DetailsShape extends ViewComponentShape {
  private static int shapeId = 1;

  public DetailsShape(DrawComponent drawComponent, int x, int y, int width, int height, String title, DataTypes dataType,
      PageShape parent) {
    this.id = shapeId++;
    this.x = x;
    this.y = y;
    this.width = width;
    this.height = height;
    this.drawComponent = drawComponent;
    if (title == null || title.isEmpty()) {
      title = "Details" + id;
    }
    this.title = title;
    this.dataType = dataType;
    this.parentShape = parent;
    parent.getComponents().add(this);
    draw();
    imageSprite.setResource(MainController.ICONS.details());
    imageResource = MainController.ICONS.details();
  }

  @Override
  public int getId() {
    return shapeId;
  }
}
