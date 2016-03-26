package com.uml2Java.client.siteView.shapes;

import com.google.gwt.resources.client.ImageResource;
import com.sencha.gxt.chart.client.draw.Color;
import com.sencha.gxt.chart.client.draw.RGB;
import com.sencha.gxt.chart.client.draw.path.LineTo;
import com.sencha.gxt.chart.client.draw.path.MoveTo;
import com.sencha.gxt.chart.client.draw.path.PathSprite;
import com.sencha.gxt.chart.client.draw.sprite.ImageSprite;
import com.sencha.gxt.chart.client.draw.sprite.RectangleSprite;
import com.sencha.gxt.chart.client.draw.sprite.TextSprite;

/**
 * Created by Cristi on 3/26/2016.
 */
public abstract class ViewComponentShape extends SiteShape {
  protected RectangleSprite rectangle;
  protected TextSprite titleSprite;
  protected TextSprite dataTypeSprite;
  protected ImageSprite imageSprite;
  protected PathSprite line;
  protected int originalHeight;
  protected int originalWidth;
  protected String dataType;
  protected ImageResource imageResource = null;

  protected void draw() {
    this.originalHeight = height;
    this.originalWidth = width;
    rectangle = new RectangleSprite();

    rectangle.setX(0);
    rectangle.setY(0);
    rectangle.setWidth(width);
    rectangle.setHeight(height);

    rectangle.setStroke(new Color("#000"));
    rectangle.setStrokeWidth(0.5);
    rectangle.setFill(new Color("#DCC"));
    rectangle.setFillOpacity(1);
    rectangle.setRadius(6);

    imageSprite = new ImageSprite();
    if(imageResource != null) {
      imageSprite.setResource(imageResource);
    }
    imageSprite.setX(0);
    imageSprite.setY(0);

    titleSprite = new TextSprite();
    titleSprite.setText(title);
    titleSprite.setFontSize(12);
    titleSprite.setFont("Times New Roman");
    titleSprite.setX(0);
    titleSprite.setY(0);
    titleSprite.setFill(RGB.BLACK);

    line = new PathSprite();
    line.addCommand(new MoveTo(0, 0));
    line.addCommand(new LineTo(width, 0));
    line.setStroke(new Color("#000"));
    line.setStrokeWidth(0.5);

    dataTypeSprite = new TextSprite();
    dataTypeSprite.setText(dataType);
    dataTypeSprite.setFontSize(12);
    dataTypeSprite.setFont("Times New Roman");
    dataTypeSprite.setX(0);
    dataTypeSprite.setY(0);
    dataTypeSprite.setFill(RGB.BLACK);

    drawComponent.addSprite(rectangle);
    drawComponent.addSprite(imageSprite);
    drawComponent.addSprite(titleSprite);
    drawComponent.addSprite(line);
    drawComponent.addSprite(dataTypeSprite);

    scaleTo(scaleFactor);
  }

  @Override
  public void translateTo(int mouseX, int mouseY) {
    this.x = mouseX;
    this.y = mouseY;
    rectangle.setTranslation(x, y);
    line.setTranslation(x, y + 25 * scaleFactor);
    titleSprite.setTranslation(x + 10 * scaleFactor, y + 5 * scaleFactor);
    imageSprite.setTranslation(x + width / 2 - 16 * scaleFactor, height / 2 + y + 5 * scaleFactor);
    dataTypeSprite.setTranslation(x + 10 * scaleFactor, y + 30 * scaleFactor);
  }

  @Override
  public void scaleTo(double scaleFactor) {
    this.width = (int) (originalWidth * scaleFactor);
    this.height = (int) (originalHeight * scaleFactor);

    this.scaleFactor = scaleFactor;
    rectangle.setScaling(scaleFactor);
    line.setScaling(scaleFactor);
    titleSprite.setScaling(scaleFactor);
    imageSprite.setScaling(scaleFactor);
    dataTypeSprite.setScaling(scaleFactor);
    translateTo(x, y);
  }

  @Override
  public void redraw() {
    remove();
    draw();
    drawComponent.redrawSurface();
  }

  @Override
  public void remove() {
    drawComponent.remove(rectangle);
    drawComponent.remove(titleSprite);
    drawComponent.remove(line);
    drawComponent.remove(imageSprite);
    drawComponent.remove(dataTypeSprite);
  }
}
