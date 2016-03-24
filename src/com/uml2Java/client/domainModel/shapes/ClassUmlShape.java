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
import com.uml2Java.shared.Attribute;
import com.uml2Java.shared.Method;
import com.uml2Java.shared.ObjectDataTypes;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by Cristi on 2/8/2016.
 */
public class ClassUmlShape extends UmlShape {
  private static final int BRACKETS_SIZE = 4;

  private RectangleSprite rectangle;
  private PathSprite line2;
  private PathSprite line3;
  private TextSprite titleSprite;
  private List<TextSprite> attributeTextSprite;
  private List<TextSprite> methodTextSprite;
  private List<Method> methods;
  private List<Attribute> attributes;
  private int originalWidth, originalHeight;
  private Logger logger = Logger.getLogger(ClassUmlShape.class.getName());

  public ClassUmlShape(DrawComponent drawComponent, int x, int y, int width, int height, String title,
      List<Attribute> attribs, List<Method> methods) {
    this.id = shapeId++;
    this.x = x;
    this.y = y;
    this.width = width;
    this.height = height;
    this.drawComponent = drawComponent;
    if (title == null || title.isEmpty()) {
      title = "Class" + id;
    }
    this.title = title;
    this.methods = methods;
    this.attributes = attribs;
    this.dataTpe = new ObjectDataTypes(id, title);
    this.links = new ArrayList<Link>();
    draw();
  }

  private void drawBoxAndTitle() {
    rectangle = new RectangleSprite();

    rectangle.setX(0);
    rectangle.setY(0);
    rectangle.setWidth(width);
    rectangle.setHeight(height);

    rectangle.setStroke(new Color("#000"));
    rectangle.setStrokeWidth(0.5);
    rectangle.setFill(new Color("#EB0"));
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

  private void translateTextAndLine() {
    double auxY = y + (25 + 5) * scaleFactor;

    for (TextSprite attributeSprite : attributeTextSprite) {
      attributeSprite.setTranslation(x + (5 * scaleFactor), auxY);
      auxY += (17 * scaleFactor);
    }
    line3.setTranslation(x, auxY);
    auxY += (5 * scaleFactor);
    for (TextSprite methodSprite : methodTextSprite) {
      methodSprite.setTranslation(x + (5 * scaleFactor), auxY);
      auxY += (17 * scaleFactor);
    }
  }

  private void scaleTextAndLine(double scale) {
    for (TextSprite methodSprite : methodTextSprite)
      methodSprite.setScaling(scale);

    for (TextSprite attributeSprite : attributeTextSprite)
      attributeSprite.setScaling(scale);
  }

  private void draw() {
    determineMaxWidth();
    attributeTextSprite = new ArrayList<TextSprite>();
    methodTextSprite = new ArrayList<TextSprite>();
    this.height = (int) ((25 + 5 + 5 + 5 + methods.size() * 17 + attributes.size() * 17)); //* scaleFactor);
    // 25 -> title;  5 space after line title; 5 space before second line; 5 space after second line
    this.originalHeight = height;
    this.originalWidth = width;

    for (Attribute attribute : attributes) {
      TextSprite attributeText = new TextSprite();
      attributeText.setText(attribute.toString());
      attributeText.setFontSize(12);
      attributeText.setX(0);
      attributeText.setY(0);
      if(attribute.isFinal()) {
        attributeText.setFill(RGB.PURPLE);
      } else {
        attributeText.setFill(RGB.BLACK);
      }
      if(attribute.isStatic()) {
        attributeText.setFontStyle(Style.FontStyle.ITALIC);
      }
      attributeTextSprite.add(attributeText);
      drawComponent.addSprite(attributeText);
    }

    line3 = new PathSprite();
    line3.addCommand(new MoveTo(0, 0));
    line3.addCommand(new LineTo(width, 0));
    line3.setStroke(new Color("#000"));
    line3.setStrokeWidth(0.5);

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
    drawComponent.addSprite(line3);
  }

  public void remove() {
    drawComponent.remove(rectangle);
    drawComponent.remove(titleSprite);
    drawComponent.remove(line2);
    drawComponent.remove(line3);
    for (TextSprite attributeTS : attributeTextSprite)
      drawComponent.remove(attributeTS);
    for (TextSprite methodTS : methodTextSprite)
      drawComponent.remove(methodTS);
  }

  @Override
  public void redraw() {
    remove();
    draw();
    drawComponent.redrawSurface();
  }

  private void determineMaxWidth() {
    int max = 0;
    if ((title.length() + BRACKETS_SIZE) * 7 + 20 > max) {
      max = (title.length() + BRACKETS_SIZE) * 7 + 20;
    }
    for (Attribute attribute : attributes) {
      if (attribute.toString().length() * 6 + 15 > max)
        max = attribute.toString().length() * 6 + 15;
    }
    for (Method method : methods) {
      if (method.toString().length() * 6 + 15> max) {
        max = method.toString().length() * 6 + 15;
      }
    }
    width = max;
  }

  public boolean canBeDragged(int mouseX, int mouseY) {
    return mouseX >= x && mouseX <= x + width &&
        mouseY >= y && mouseY <= y + height;
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

    drawLinks();
  }


  @Override
  public void scaleTo(double scale) {
    this.width = (int) (originalWidth * scale);
    this.height = (int) (originalHeight * scale);

    this.scaleFactor = scale;
    rectangle.setScaling(scale);
    line2.setScaling(scale);
    line3.setScaling(scale);
    titleSprite.setScaling(scale);
    scaleTextAndLine(scale);
    translateTo(x, y);
  }

  public int getX() {
    return x;
  }

  public void setX(int x) {
    this.x = x;
  }

  public int getY() {
    return y;
  }

  public void setY(int y) {
    this.y = y;
  }

  public List<Attribute> getAttributesList() {
    return attributes;
  }

  public void setAttributesList(List<Attribute> attributes) {
    this.attributes = new ArrayList<Attribute>(attributes);
  }

  public List<Method> getMethodsList() {
    return methods;
  }

  public void setMethodsList(List<Method> methods) {
    this.methods = new ArrayList<Method>(methods);
  }
}
