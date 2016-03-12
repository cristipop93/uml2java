package com.uml2Java.client.shapes;

import com.sencha.gxt.chart.client.draw.Color;
import com.sencha.gxt.chart.client.draw.DrawComponent;
import com.sencha.gxt.chart.client.draw.path.LineTo;
import com.sencha.gxt.chart.client.draw.path.MoveTo;
import com.sencha.gxt.chart.client.draw.path.PathSprite;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Cristi on 3/5/2016.
 */
public abstract class Link {
  private Shape firstShape, secondShape;
  private int offsetFirst, totalFirst;
  private int offsetSecond, totalSecond; //offset >= 1, total >=offset

  public Link(Shape firstShape, Shape secondShape) {
    this.firstShape = firstShape;
    this.secondShape = secondShape;
  }

  // Establish where the link is relative to the given shape
  public LinkPosition getLinkPosition(Shape relativeToShape) {
    boolean isFirst = false;
    if(firstShape.getId() == relativeToShape.getId())
      isFirst = true;

    LinkPosition result = LinkPosition.HORV;

    int fx1 = firstShape.getX();
    int fy1 = firstShape.getY();
    int fx2 = fx1 + firstShape.getWidth();
    int fy2 = fy1 + firstShape.getHeight();
    int sx1 = secondShape.getX();
    int sy1 = secondShape.getY();
    int sx2 = sx1 + secondShape.getWidth();
    int sy2 = sy1 + secondShape.getHeight();

    /** horizontal intersection fx1 <= sx1 <= fx2 (I) */
    if (sx1 >= fx1 && sx1 <= fx2) {
      /* s is below f */
      if (sy1 >= fy2) {
        result = LinkPosition.HORV;
      }
      /* s is above f */
      else if (fy1 >= sy2) {
        result = LinkPosition.HORV;
      }
    }
    /** horizontal intersection sx1 <= fx1 <= sx2 (II) */
    else if (fx1 >= sx1 && fx1 <= sx2) {
      /* f is below */
      if (fy1 >= sy2) {
        result = LinkPosition.HORV;
      }
      /* f is above */
      else if (fy2 <= sy1) {
        result = LinkPosition.HORV;      }
    }
    /** vertical intersection fy1 <= sy1 <= fy2 (I) */
    else if (sy1 >= fy1 && sy1 <= fy2) {
      /* s is to the right */
      if (sx1 >= fx2) {
        result = LinkPosition.HORV;
      }
      /* s is to the left */
      else if (sx2 <= fx1) {
        result = LinkPosition.HORV;
      }
    }
    /** vertical intersection sy1 <= fy1 <= sy2 (II) */
    else if (fy1 >= sy1 && fy1 <= sy2) {
      /* f is to the right */
      if (fx1 >= sx2) {
        result = LinkPosition.HORV;
      }
      /* f is to the left */
      else if (fx2 <= sx1) {
        result = LinkPosition.HORV;
      }
    }
    /** s is below */
    else if (sy1 > fy2) {
      /* s is to the right */
      if (sx1 >= fx2) {
        result = LinkPosition.SE;
      }
      /* s is to the left */
      else if (sx2 <= fx1) {
        result = LinkPosition.SW;
      }
    }
    /** s is above */
    else if (sy2 < fy1) {
      /* s is to the right */
      if (sx1 >= fx2) {
        result = LinkPosition.NE;
      }
      /* s is to the left */
      else if (sx2 <= fx1) {
        result = LinkPosition.NW;
      }
    }
    if(!isFirst){
      if (result == LinkPosition.NE)
        result = LinkPosition.SW;
      else if (result == LinkPosition.NW)
        result = LinkPosition.SE;
      else if (result == LinkPosition.SE)
        result = LinkPosition.NW;
      else if (result == LinkPosition.SW)
        result = LinkPosition.NE;
    }

    return result;
  }

  public List<Point> getLinesPoints() {
    return getLinesPoints(firstShape, secondShape);
  }

  public List<Point> getLinesPoints(Shape firstShape, Shape secondShape) {
    int fx1 = firstShape.getX();
    int fy1 = firstShape.getY();
    int fx2 = fx1 + firstShape.getWidth();
    int fy2 = fy1 + firstShape.getHeight();
    int sx1 = secondShape.getX();
    int sy1 = secondShape.getY();
    int sx2 = sx1 + secondShape.getWidth();
    int sy2 = sy1 + secondShape.getHeight();

    List<Point> result = new ArrayList<Point>();

    /** horizontal intersection fx1 <= sx1 <= fx2 (I) */
    if (sx1 >= fx1 && sx1 <= fx2) {
      int diff;
      if (sx2 <= fx2) {
        diff = (sx2 - sx1) / 2;
      } else {
        diff = (fx2 - sx1) / 2;
      }
      /* s is below f */
      if (sy1 >= fy2) {
        result.add(new Point(sx1 + diff, fy2));
        result.add(new Point(sx1 + diff, sy1));
      }
      /* s is above f */
      else if (fy1 >= sy2) {
        result.add(new Point(sx1 + diff, fy1));
        result.add(new Point(sx1 + diff, sy2));
      }
    }
    /** horizontal intersection sx1 <= fx1 <= sx2 (II) */
    else if (fx1 >= sx1 && fx1 <= sx2) {
      int diff;
      if (fx2 <= sx2) {
        diff = (fx2 - fx1) / 2;
      } else {
        diff = (sx2 - fx1) / 2;
      }
      /* f is below */
      if (fy1 >= sy2) {
        result.add(new Point(fx1 + diff, fy1));
        result.add(new Point(fx1 + diff, sy2));
      }
      /* f is above */
      else if (fy2 <= sy1) {
        result.add(new Point(fx1 + diff, fy2));
        result.add(new Point(fx1 + diff, sy1));
      }
    }
    /** vertical intersection fy1 <= sy1 <= fy2 (I) */
    else if (sy1 >= fy1 && sy1 <= fy2) {
      int diff;
      if (sy2 <= fy2) {
        diff = (sy2 - sy1) / 2;
      } else {
        diff = (fy2 - sy1) / 2;
      }
      /* s is to the right */
      if (sx1 >= fx2) {
        result.add(new Point(fx2, sy1 + diff));
        result.add(new Point(sx1, sy1 + diff));
      }
      /* s is to the left */
      else if (sx2 <= fx1) {
        result.add(new Point(fx1, sy1 + diff));
        result.add(new Point(sx2, sy1 + diff));
      }
    }
    /** vertical intersection sy1 <= fy1 <= sy2 (II) */
    else if (fy1 >= sy1 && fy1 <= sy2) {
      int diff;
      if (fy2 <= sy2) {
        diff = (fy2 - fy1) / 2;
      } else {
        diff = (sy2 - fy1) / 2;
      }
      /* f is to the right */
      if (fx1 >= sx2) {
        result.add(new Point(fx1, fy1 + diff));
        result.add(new Point(sx2, fy1 + diff));
      }
      /* f is to the left */
      else if (fx2 <= sx1) {
        result.add(new Point(fx2, fy1 + diff));
        result.add(new Point(sx1, fy1 + diff));
      }
    }
    /** s is below */
    else if (sy1 > fy2) {
      int mfY = fy1 + (fy2 - fy1) / (totalFirst+1) * offsetFirst;
      int msX = sx1 + (sx2 - sx1) / (totalSecond+1) * offsetSecond;
      /* s is to the right */
      if (sx1 >= fx2) {
        result.add(new Point(fx2, mfY));
        result.add(new Point(msX, mfY));
        result.add(new Point(msX, sy1));
      }
      /* s is to the left */
      else if (sx2 <= fx1) {
        result.add(new Point(fx1, mfY));
        result.add(new Point(msX, mfY));
        result.add(new Point(msX, sy1));
      }
    }
    /** s is above */
    else if (sy2 < fy1) {
      int msY = sy1 + (sy2 - sy1) / (totalSecond+1) * offsetSecond;
      int mfX = fx1 + (fx2 - fx1) / (totalFirst+1) * offsetFirst;
      /* s is to the right */
      if (sx1 >= fx2) {
        result.add(new Point(mfX, fy1));
        result.add(new Point(mfX, msY));
        result.add(new Point(sx1, msY));
      }
      /* s is to the left */
      else if (sx2 <= fx1) {
        result.add(new Point(mfX, fy1));
        result.add(new Point(mfX, msY));
        result.add(new Point(sx2, msY));
      }
    }

    return result;
  }

  public Shape getFirstShape() {
    return firstShape;
  }

  public Shape getSecondShape() {
    return secondShape;
  }

  public abstract void draw(DrawComponent drawComponent);

  public int getOffsetFirst() {
    return offsetFirst;
  }

  public void setOffsetFirst(int offsetFirst) {
    this.offsetFirst = offsetFirst;
  }

  public int getTotalFirst() {
    return totalFirst;
  }

  public void setTotalFirst(int totalFirst) {
    this.totalFirst = totalFirst;
  }

  public int getOffsetSecond() {
    return offsetSecond;
  }

  public void setOffsetSecond(int offsetSecond) {
    this.offsetSecond = offsetSecond;
  }

  public int getTotalSecond() {
    return totalSecond;
  }

  public void setTotalSecond(int totalSecond) {
    this.totalSecond = totalSecond;
  }

  public boolean isFirst(Shape shape) {
    return firstShape.getId() == shape.getId();
  }
}
