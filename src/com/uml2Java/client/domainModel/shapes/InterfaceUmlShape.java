package com.uml2Java.client.domainModel.shapes;

import com.google.gwt.dom.client.Style;
import com.sencha.gxt.chart.client.draw.Color;
import com.sencha.gxt.chart.client.draw.DrawComponent;
import com.sencha.gxt.chart.client.draw.RGB;
import com.sencha.gxt.chart.client.draw.path.LineTo;
import com.sencha.gxt.chart.client.draw.path.MoveTo;
import com.sencha.gxt.chart.client.draw.path.PathSprite;
import com.sencha.gxt.chart.client.draw.sprite.RectangleSprite;
import com.sencha.gxt.chart.client.draw.sprite.TextSprite;
import com.uml2Java.shared.Method;
import com.uml2Java.shared.ObjectDataTypes;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Cristi on 3/13/2016.
 */
public class InterfaceUmlShape extends UmlShape {
  private static final int BRACKETS_SIZE = 4;
  private List<Method> methods;
  private List<TextSprite> methodTextSprite;
  private int originalHeight;
  private int originalWidth;
  private RectangleSprite rectangle;
  private TextSprite titleSprite;
  private PathSprite line2;

  public InterfaceUmlShape(DrawComponent drawComponent, int x, int y, int width, int height, String title,
      List<Method> methods) {
    this.id = shapeId++;
    this.x = x;
    this.y = y;
    this.width = width;
    this.height = height;
    this.drawComponent = drawComponent;
    if (title == null || title.isEmpty()) {
      title = "Interface" + id;
    }
    this.methods = methods;
    this.title = title;
    this.dataTpe = new ObjectDataTypes(id, title);
    this.links = new ArrayList<Link>();
    draw();
  }
  private void determineMaxWidth() {
    int max = 0;
    if ((title.length() + BRACKETS_SIZE) * 7 + 20 > max) {
      max = (title.length() + BRACKETS_SIZE) * 7 + 20;
    }
    for (Method method : methods) {
      if (method.toString().length() * 6 + 15> max) {
        max = method.toString().length() * 6 + 15;
      }
    }
    width = max;
  }

  private void draw() {
    determineMaxWidth();
    methodTextSprite = new ArrayList<TextSprite>();
    this.height = (int) ((25 + 5 + 5 + methods.size() * 17)); //* scaleFactor);
    // 25 -> title;  5 space after line title; 5 space before second line; 5 space after second line
    this.originalHeight = height;
    this.originalWidth = width;

    for (Method method : methods) {
      TextSprite methodText = new TextSprite();
      methodText.setText(method.toString());
      methodText.setFontSize(12);
      methodText.setX(0);
      methodText.setY(0);
      if(method.isFinal()) {
        methodText.setFill(RGB.PURPLE);
      } else {
        methodText.setFill(RGB.BLACK);
      }
      if(method.isStatic()) {
        methodText.setFontStyle(Style.FontStyle.ITALIC);
      }
      methodTextSprite.add(methodText);
      drawComponent.addSprite(methodText);
    }

    drawBoxAndTitle();
    scaleTo(scaleFactor);
    drawComponent.addSprite(rectangle);
    drawComponent.addSprite(titleSprite);
    drawComponent.addSprite(line2);

  }

  private void drawBoxAndTitle() {
    rectangle = new RectangleSprite();

    rectangle.setX(0);
    rectangle.setY(0);
    rectangle.setWidth(width);
    rectangle.setHeight(height);

    rectangle.setStroke(new Color("#000"));
    rectangle.setStrokeWidth(0.5);
    rectangle.setFill(new Color("#9CF"));
    rectangle.setFillOpacity(0.25);
    rectangle.setRadius(6);

    titleSprite = new TextSprite();
    titleSprite.setText("<<" + title + ">>");
    titleSprite.setFontSize(12);
    titleSprite.setX(0);
    titleSprite.setY(0);
    titleSprite.setFill(RGB.BLACK);

    line2 = new PathSprite();
    line2.addCommand(new MoveTo(0, 0));
    line2.addCommand(new LineTo(width, 0));
    line2.setStroke(new Color("#000"));
    line2.setStrokeWidth(0.5);
  }

  @Override
  public void translateTo(int mouseX, int mouseY) {
    this.x = mouseX;
    this.y = mouseY;
    rectangle.setTranslation(x, y);
    line2.setTranslation(x, y + 25 * scaleFactor);
    titleSprite
        .setTranslation(x + (width - ((title.length() + BRACKETS_SIZE) * 7) * scaleFactor) / 2, y + 5 * scaleFactor);
    translateTextAndLine();

    drawLinks(scaleFactor);
  }

  private void translateTextAndLine() {
    double auxY = y + (25 + 5) * scaleFactor;

    for (TextSprite methodSprite : methodTextSprite) {
      methodSprite.setTranslation(x + (5 * scaleFactor), auxY);
      auxY += (17 * scaleFactor);
    }
  }

  @Override
  public void scaleTo(double scaleFactor) {
    this.width = (int) (originalWidth * scaleFactor);
    this.height = (int) (originalHeight * scaleFactor);

    this.scaleFactor = scaleFactor;
    rectangle.setScaling(scaleFactor);
    line2.setScaling(scaleFactor);
    titleSprite.setScaling(scaleFactor);
    scaleTextAndLine(scaleFactor);
    translateTo(x, y);
  }
  private void scaleTextAndLine(double scale) {
    for (TextSprite methodSprite : methodTextSprite)
      methodSprite.setScaling(scale);
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
    drawComponent.remove(line2);
    for (TextSprite methodTS : methodTextSprite)
      drawComponent.remove(methodTS);
  }

  private void setLinkOffsets(List<Link> links) {
    for (int i=0; i < links.size(); i++) {
      Link link = links.get(i);
      if (link.isFirst(this)) {
        link.setOffsetFirst(i+1);
        link.setTotalFirst(links.size());
      } else {
        link.setOffsetSecond(i+1);
        link.setTotalSecond(links.size());
      }
    }
  }

  public List<Method> getMethodsList() {
    return methods;
  }

  public void setMethodsList(List<Method> methods) {
    this.methods = new ArrayList<Method>(methods);
  }
}
