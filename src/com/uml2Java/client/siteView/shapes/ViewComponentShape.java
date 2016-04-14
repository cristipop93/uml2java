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
import com.uml2Java.shared.DataTypes;

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
  protected DataTypes dataType;
  protected ImageResource imageResource = null;
  protected PageShape parentShape = null;

  protected void draw() {
    int max = 0;
    if (title.length() * 7 + 20 > max) {
      max = title.length() * 7 + 20;
    }
    if (dataType.getDisplayName().length() * 7 + 20 > max) {
      max = dataType.getDisplayName().length() * 7 + 20;
      log.info("max = " + max + " displayName: " + dataType.getDisplayName().length());
    }
    if (max < 100) {
      max = 100;
    }
    this.width = max;
    this.originalHeight = height;
    this.originalWidth = width;
    if (rectangle == null)
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

    if (imageSprite == null)
      imageSprite = new ImageSprite();
    if(imageResource != null) {
      imageSprite.setResource(imageResource);
    }
    imageSprite.setX(0);
    imageSprite.setY(0);

    if (titleSprite == null)
      titleSprite = new TextSprite();
    titleSprite.setText(title);
    titleSprite.setFontSize(12);
    titleSprite.setFont("Times New Roman");
    titleSprite.setX(0);
    titleSprite.setY(0);
    titleSprite.setFill(RGB.BLACK);

    if (line == null)
      line = new PathSprite();
    line.addCommand(new MoveTo(0, 0));
    line.addCommand(new LineTo(width, 0));
    line.setStroke(new Color("#000"));
    line.setStrokeWidth(0.5);

    if (dataTypeSprite == null)
      dataTypeSprite = new TextSprite();
    dataTypeSprite.setText("<" + dataType.getDisplayName() + ">");
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
    if (mouseX <= parentShape.getX() + 20 * scaleFactor)
      mouseX = (int) (parentShape.getX() + 20 * scaleFactor);
    if (mouseY <= parentShape.getY() + 35 * scaleFactor)
      mouseY = (int) (parentShape.getY() + 35 * scaleFactor);
    this.x = mouseX;
    this.y = mouseY;
    rectangle.setTranslation(x, y);
    line.setTranslation(x, y + 25 * scaleFactor);
    titleSprite.setTranslation(x + 10 * scaleFactor, y + 5 * scaleFactor);
    imageSprite.setTranslation(x + width / 2 - 16 * scaleFactor, height / 2 + y + 5 * scaleFactor);
    dataTypeSprite.setTranslation(x + 10 * scaleFactor, y + 30 * scaleFactor);
    parentShape.resize();

    rectangle.redraw();
    line.redraw();
    titleSprite.redraw();
    imageSprite.redraw();
    dataTypeSprite.redraw();

    drawFlows(scaleFactor);
  }

  @Override
  public void scaleTo(double scaleFactor) {
    this.width = (int) (originalWidth * scaleFactor);
    this.height = (int) (originalHeight * scaleFactor);

    this.scaleFactor = scaleFactor;
    rectangle.setScaling(scaleFactor);
    rectangle.setStrokeWidth(0.5);
    line.setScaling(scaleFactor);
    line.setStrokeWidth(0.5);
    titleSprite.setScaling(scaleFactor);
    imageSprite.setScaling(scaleFactor);
    dataTypeSprite.setScaling(scaleFactor);
    translateTo(x, y);
  }

  @Override
  public void redraw() {
    remove();
    draw();
  }

  @Override
  public void remove() {
    line.clearCommands();
  }

  public void translateToParentsCoords(int x, int y) {
    this.x = x;
    this.y = y;
    rectangle.setTranslation(x, y);
    line.setTranslation(x, y + 25 * scaleFactor);
    titleSprite.setTranslation(x + 10 * scaleFactor, y + 5 * scaleFactor);
    imageSprite.setTranslation(x + width / 2 - 16 * scaleFactor, height / 2 + y + 5 * scaleFactor);
    dataTypeSprite.setTranslation(x + 10 * scaleFactor, y + 30 * scaleFactor);

    rectangle.redraw();
    line.redraw();
    titleSprite.redraw();
    imageSprite.redraw();
    dataTypeSprite.redraw();

    drawFlows(scaleFactor);
  }

  public DataTypes getDataType() {
    return dataType;
  }

  public void setDataType(DataTypes dataType) {
    this.dataType = dataType;
  }

  @Override
  public void setSelected(boolean isSelected) {
    if (isSelected) {
      rectangle.setStrokeWidth(1.5);
      rectangle.setStroke(new Color("#AA3"));
    } else {
      rectangle.setStrokeWidth(0.5);
      rectangle.setStroke(new Color("#000"));
    }
    rectangle.redraw();
  }
}
