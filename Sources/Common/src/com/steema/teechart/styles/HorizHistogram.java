package com.steema.teechart.styles;

import com.steema.teechart.Rectangle;
import com.steema.teechart.IBaseChart;

/**
 *
 * @author Shunyata Kharg
 */
public class HorizHistogram extends Histogram {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public HorizHistogram() {
        this((IBaseChart)null);
    }

    public HorizHistogram(IBaseChart c) {
        super(c);
        SetHorizontal();
        getXValues().setOrder(ValueListOrder.NONE);
        getYValues().setOrder(ValueListOrder.ASCENDING);
    }

    private void SetHorizontal() {
        this.mandatory = this.vxValues;
        this.notMandatory = this.vyValues;
        this.yMandatory = false;
    }
    
    protected void drawMark(int valueIndex, String st,
                            SeriesMarksPosition aPosition) {
        int difH = aPosition.height / 2;
        int difW = getMarks().getCallout().getLength() + getMarks().getCallout().getDistance();
        
        aPosition.leftTop.y = aPosition.arrowTo.y - difH;
        aPosition.leftTop.x += difW + (aPosition.width / 2);
        aPosition.arrowTo.x += difW;
        aPosition.arrowFrom.y = aPosition.arrowTo.y;
        aPosition.arrowFrom.x += getMarks().getCallout().getDistance();
         
        super.drawMark(valueIndex, st, aPosition);      
    }
    
    protected Rectangle calcRectangle(int valueIndex)
    {
      Rectangle r = new Rectangle();

      if (visiblePoints() > 0)
      {
        double tmp;
        if (visiblePoints() > 1)
        {
          if(drawValuesForward()) {
            if (valueIndex == firstDisplayed())
            {
                tmp = (calcYPos(valueIndex) - calcYPos(valueIndex + 1)) / 2.0;
            }
            else
            {
                tmp = (calcYPos(valueIndex - 1) - calcYPos(valueIndex)) / 2.0;
            } 
          } 
          else {
            if (valueIndex == firstDisplayed())
            {
                tmp = (calcYPos(valueIndex - 1) - calcYPos(valueIndex)) / 2.0;
            }
            else
            {
                tmp = (calcYPos(valueIndex ) - calcYPos(valueIndex + 1)) / 2.0;
            }               
          }
        }
        else
        {
          tmp = getVertAxis().iAxisSize / visiblePoints();
        }

        if (valueIndex == firstDisplayed())
        {
          double Y;
          if(drawValuesForward()) {
             Y = calcYPos(valueIndex) - tmp; 
          }
          else {
             Y = calcYPos(valueIndex) + tmp; 
          }
          
          double height = tmp * 2.0;

          r.height = (int)Math.round(Math.abs(height));
          r.y = (int)Math.round(Y);
        }
        else
        {
          if (drawValuesForward())
            r.y = (int) Math.round((calcYPos(valueIndex) - tmp));
          else
            r.y = (int) Math.round((calcYPos(valueIndex) + tmp));
          
          r.height = previous - r.y;
        }

        previous = r.getTop();
        r.x = getHorizAxis().getInverted() ? getHorizAxis().iEndPos : getHorizAxis().iStartPos;
        
        int width;
        
        width = calcXPos(valueIndex) - r.x;
        
        if(width < 0) {
           r.x += width;
           r.width = -width;
        }
        else {
            r.width = width;
        }
        
        r = chart.getGraphics3D().calcRect3D(r, middleZ);
      }
      else
        r = new Rectangle(0,0,0,0);

      return r;
    }

}
