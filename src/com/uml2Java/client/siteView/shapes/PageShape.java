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
import com.uml2Java.client.siteView.SiteViewController;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Cristi on 3/22/2016.
 */
public class PageShape extends SiteShape {
  private RectangleSprite rectangle;
  private TextSprite titleSprite;
  private ImageSprite imageSprite;
  private PathSprite line;
  private int originalHeight;
  private int originalWidth;
  private static int shapeId = 1;
  private List<ViewComponentShape> components;

  public PageShape(DrawComponent drawComponent, int x, int y, int width, int height, String title) {
    this.id = shapeId++;
    this.x = x;
    this.y = y;
    this.width = width;
    this.height = height;
    this.drawComponent = drawComponent;
    if (title == null || title.isEmpty()) {
      title = "Page" + id;
    }
    this.title = title;
    this.components = new ArrayList<ViewComponentShape>();
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
    rectangle = new RectangleSprite();

    rectangle.setX(0);
    rectangle.setY(0);
    rectangle.setWidth(width);
    rectangle.setHeight(height);

    rectangle.setStroke(new Color("#000"));
    rectangle.setStrokeWidth(0.5);
    rectangle.setFill(new Color("#CFF"));
    rectangle.setFillOpacity(0.25);
//    rectangle.setRadius(6);

    //if it is the first shape, draw the home icon
    if (id == 1) {
      imageSprite = new ImageSprite(MainController.ICONS.home());
      imageSprite.setX(0);
      imageSprite.setY(0);
    }
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

    drawComponent.addSprite(rectangle);
    if (id == 1) {
      drawComponent.addSprite(imageSprite);
    }
    drawComponent.addSprite(titleSprite);
    drawComponent.addSprite(line);

    scaleTo(scaleFactor);
  }

  @Override
  public void translateTo(int mouseX, int mouseY) {
    int sx = x;
    int sy = y;
    this.x = mouseX;
    this.y = mouseY;
    rectangle.setTranslation(x, y);
    line.setTranslation(x, y + 25 * scaleFactor);

    if (id == 1) {
      imageSprite.setTranslation(x + 7 * scaleFactor, y + 5 * scaleFactor);
      titleSprite.setTranslation(x + 26 * scaleFactor, y + 5 * scaleFactor);
    } else {
      titleSprite.setTranslation(x + 10 * scaleFactor, y + 5 * scaleFactor);
    }
    // translate it's components
    for (ViewComponentShape componentShape : components) {
      componentShape.translateToParentsCoords(x + componentShape.getX() - sx, y + componentShape.getY() - sy);
    }

    rectangle.redraw();
    line.redraw();
    if (id == 1) {
      imageSprite.redraw();
    }
    titleSprite.redraw();

    drawFlows(scaleFactor);
  }

  @Override
  public void scaleTo(double scaleFactor) {
    this.width = (int) (originalWidth * scaleFactor);
    this.height = (int) (originalHeight * scaleFactor);

    this.scaleFactor = scaleFactor;
    rectangle.setScaling(scaleFactor);
    line.setScaling(scaleFactor);
    titleSprite.setScaling(scaleFactor);
    if (id == 1) {
      imageSprite.setScaling(scaleFactor);
    }
    translateTo(x, y);
  }

  @Override
  public void redraw() {
    rectangle.setX(0);
    rectangle.setY(0);
    rectangle.setWidth(width);
    rectangle.setHeight(height);

    rectangle.setStroke(new Color("#000"));
    rectangle.setStrokeWidth(0.5);
    rectangle.setFill(new Color("#CFF"));
    rectangle.setFillOpacity(0.25);

    if (id == 1) {
      imageSprite.setX(0);
      imageSprite.setY(0);
    }

    titleSprite.setText(title);
    titleSprite.setFontSize(12);
    titleSprite.setFont("Times New Roman");
    titleSprite.setX(0);
    titleSprite.setY(0);
    titleSprite.setFill(RGB.BLACK);
    line.clearCommands();
    line.addCommand(new MoveTo(0, 0));
    line.addCommand(new LineTo(width, 0));
    line.setStroke(new Color("#000"));
    line.setStrokeWidth(0.5);

    scaleTo(scaleFactor);
//    log.info("pageShape redraw");
//    remove();
//    draw();
//    drawComponent.redrawSurface();
    rectangle.redraw();
    line.redraw();
    if (id == 1) {
      imageSprite.redraw();
    }
    titleSprite.redraw();
  }

  @Override
  public void remove() {
    drawComponent.remove(rectangle);
    drawComponent.remove(titleSprite);
    drawComponent.remove(line);
    if(id == 1) {
      drawComponent.remove(imageSprite);
    }
    for (ViewComponentShape componentShape : components) {
      componentShape.removeAllFlows();
      componentShape.removeFromPage();
    }
    SiteViewController.getInstance().getSiteShapes().remove(this);

  }

  @Override
  public int getId() {
    return id;
  }

  public void resize() {
    int maxX = 0, maxY = 0;
    for (ViewComponentShape component : components) {
      if (component.getX() + component.getWidth() > maxX) {
        maxX = component.getX() + component.getWidth();
      }
      if (component.getY() + component.getHeight() > maxY) {
        maxY = component.getY() + component.getHeight();
      }
    }
    int max = (int) (maxX - x + 20 * scaleFactor);
    if ( (title.length() * 7 + 10) * scaleFactor > max) {
      max = (int) ((title.length() * 7 + 10) * scaleFactor);
    }
    this.width = max;
    this.height = (int) (maxY - y + 10 * scaleFactor);
    this.width = (int) (width / scaleFactor);
    this.height = (int)(height / scaleFactor);
    this.originalWidth = width;
    this.originalHeight = height;
    if (width < 0 || height < 0) {
      this.width = 120;
      this.height = 110;
      this.originalWidth = 120;
      this.originalHeight = 110;
      max = this.width;
      if ( (title.length() * 7 + 10) * scaleFactor > max) {
        max = (int) ((title.length() * 7 + 10) * scaleFactor);
      }
      width = max;
    }
    redraw();
  }

  public List<ViewComponentShape> getComponents() {
    return components;
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

  public void removeChild(ViewComponentShape viewComponentShape) {
    components.remove(viewComponentShape);
  }
}
