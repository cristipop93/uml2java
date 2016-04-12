package com.uml2Java.client.siteView.shapes;

import com.sencha.gxt.chart.client.draw.Color;
import com.sencha.gxt.chart.client.draw.DrawComponent;
import com.sencha.gxt.chart.client.draw.RGB;
import com.sencha.gxt.chart.client.draw.path.LineTo;
import com.sencha.gxt.chart.client.draw.path.MoveTo;
import com.sencha.gxt.chart.client.draw.path.PathSprite;
import com.sencha.gxt.chart.client.draw.sprite.TextSprite;

/**
 * Created by Cristi on 3/25/2016.
 */
public class ActionShape extends SiteShape{
  private static int shapeId = 1;
  private int originalHeight;
  private int originalWidth;
  private PathSprite path;
  private TextSprite titleSprite;

  public ActionShape(DrawComponent drawComponent, int x, int y, int width, int height, String title) {
    this.id = shapeId++;
    this.x = x;
    this.y = y;
    this.width = width;
    this.height = height;
    this.drawComponent = drawComponent;
    if (title == null || title.isEmpty()) {
      title = "Action" + id;
    }
    this.title = title;
    path = new PathSprite();
    titleSprite = new TextSprite();
    draw();
  }

  private void draw() {
    int max = this.width;
    if ( (title.length() * 7 + 10) * scaleFactor > max) {
      max = (int) ((title.length() * 7 + 10) * scaleFactor);
    }
    this.width = max;
    this.originalHeight = height;
    this.originalWidth = width;

    path.addCommand(new MoveTo(0, 0));
    path.addCommand(new LineTo(width, 0));
    path.addCommand(new LineTo(width + 20, height/2));
    path.addCommand(new LineTo(width, height));
    path.addCommand(new LineTo(0, height));
    path.addCommand(new LineTo(-20, height/2));
    path.addCommand(new LineTo(0, 0));
    path.setStrokeWidth(0.5);
    path.setFillOpacity(0.25);
    path.setFill(new Color("#111"));
    path.setStroke(new Color("#000"));

    titleSprite.setText(title);
    titleSprite.setFontSize(12);
    titleSprite.setX(0);
    titleSprite.setY(0);
    titleSprite.setFill(RGB.BLACK);

    drawComponent.addSprite(path);
    drawComponent.addSprite(titleSprite);

    scaleTo(scaleFactor);
  }

  @Override
  public void translateTo(int mouseX, int mouseY) {
    this.x = mouseX;
    this.y = mouseY;
    path.setTranslation(x, y);
    titleSprite
        .setTranslation(x + (width - (title.length() * 7) * scaleFactor) / 2, y + height / 2 - 7 * scaleFactor);

    drawFlows(scaleFactor);

    path.redraw();
    titleSprite.redraw();
  }

  @Override
  public int getId() {
    return shapeId;
  }

  @Override
  public void scaleTo(double scaleFactor) {
    this.width = (int) (originalWidth * scaleFactor);
    this.height = (int) (originalHeight * scaleFactor);

    this.scaleFactor = scaleFactor;
    path.setScaling(scaleFactor);
    titleSprite.setScaling(scaleFactor);
    translateTo(x, y);
  }

  @Override
  public void redraw() {
    remove();
    draw();
  }

  @Override
  public void remove() {
    path.clearCommands();
  }
  @Override
  public boolean canBeDragged(int mouseX, int mouseY){
    return mouseX >= x - 20 && (mouseX <= x + width + 20) &&
        mouseY >= y && mouseY <= y + height;
  }

  @Override
  public void setSelected(boolean isSelected) {
    if (isSelected) {
      path.setStrokeWidth(1.5);
      path.setStroke(new Color("#AA3"));
    } else {
      path.setStrokeWidth(0.5);
      path.setStroke(new Color("#000"));
    }
    path.redraw();
  }
}
