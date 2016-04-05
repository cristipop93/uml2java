package com.uml2Java.client.siteView.shapes;

import com.sencha.gxt.chart.client.draw.Color;
import com.sencha.gxt.chart.client.draw.DrawComponent;
import com.sencha.gxt.chart.client.draw.RGB;
import com.sencha.gxt.chart.client.draw.path.LineTo;
import com.sencha.gxt.chart.client.draw.path.MoveTo;
import com.sencha.gxt.chart.client.draw.path.PathSprite;
import com.sencha.gxt.chart.client.draw.sprite.ImageSprite;
import com.sencha.gxt.chart.client.draw.sprite.RectangleSprite;
import com.sencha.gxt.chart.client.draw.sprite.TextSprite;
import com.uml2Java.client.MainController;
import com.uml2Java.shared.DataTypes;

/**
 * Created by Cristi on 3/25/2016.
 */
public class FormShape extends ViewComponentShape{
  private static int shapeId = 1;

  public FormShape(DrawComponent drawComponent, int x, int y, int width, int height, String title, DataTypes dataType,
      PageShape parent) {
    this.id = shapeId++;
    this.x = x;
    this.y = y;
    this.width = width;
    this.height = height;
    this.drawComponent = drawComponent;
    if (title == null || title.isEmpty()) {
      title = "Form" + id;
    }
    this.title = title;
    this.dataType = dataType;
    this.parentShape = parent;
    parent.getComponents().add(this);
    draw();
    imageSprite.setResource(MainController.ICONS.form());
    imageResource = MainController.ICONS.form();
  }

  @Override
  public int getId() {
    return shapeId;
  }
}
