package com.uml2Java.client.domainModel.shapes;

import com.sencha.gxt.chart.client.draw.DrawComponent;
import com.uml2Java.shared.DataTypes;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Cristi on 2/10/2016.
 */
public abstract class UmlShape {
  protected static int shapeId = 1;
  protected int id;
  protected int x, y, width, height;
  protected double scaleFactor = 1;
  protected String title;
  protected DataTypes dataTpe;
  protected List<Link> links;
  protected DrawComponent drawComponent;

  public abstract void translateTo(int mouseX, int mouseY);

  public boolean canBeDragged(int mouseX, int mouseY){
    return mouseX >= x && mouseX <= x + width &&
        mouseY >= y && mouseY <= y + height;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
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

  public int getWidth() {
    return width;
  }

  public void setWidth(int width) {
    this.width = width;
  }

  public int getHeight() {
    return height;
  }

  public void setHeight(int height) {
    this.height = height;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
    this.dataTpe.setDisplayName(title);
  }

  public abstract void scaleTo(double scaleFactor);

  public DataTypes getDataTpe() {
    return dataTpe;
  }

  public abstract void redraw();

  public abstract void remove();

  public void drawLinks() {
    // establish if this class is first or not
    // count the links on each side (N, S, E, W)
    // establish for each side how many links are, than set for each one an offset where should be drawn
    List<Link> NLinks = new ArrayList<Link>();
    List<Link> SLinks = new ArrayList<Link>();
    List<Link> ELinks = new ArrayList<Link>();
    List<Link> WLinks = new ArrayList<Link>();

    for (Link link : links) {
      Position linkPosition = link.getLinkPosition(this);
      if (linkPosition == Position.N)
        NLinks.add(link);
      else if (linkPosition == Position.S)
        SLinks.add(link);
      else if (linkPosition == Position.E)
        ELinks.add(link);
      else if (linkPosition == Position.W)
        WLinks.add(link);
    }

    setLinkOffsets(NLinks);
    setLinkOffsets(SLinks);
    setLinkOffsets(ELinks);
    setLinkOffsets(WLinks);

    for(Link link : links) {
      link.draw(drawComponent);
    }
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

  public List<Link> getLinks() {
    return links;
  }

  public void removeLinks() {
    Iterator<Link> iterator = links.iterator();
    while (iterator.hasNext()) {
      Link link = iterator.next();
      if(link.isFirst(this)) {
        link.getSecondUmlShape().removeLinksForShape(this);
      } else {
        link.getFirstUmlShape().removeLinksForShape(this);
      }
      link.remove(drawComponent);
      iterator.remove();
    }
  }

  private void removeLinksForShape(UmlShape umlShape) {
    Iterator<Link> iterator = links.iterator();
    while (iterator.hasNext()) {
      Link link = iterator.next();
      if(link.getSecondUmlShape().getId() == umlShape.getId() || link.getFirstUmlShape().getId() == umlShape.getId())
        iterator.remove();
    }
  }
}
