package com.steema.teechart.tools;

import com.steema.teechart.events.ChartDrawEvent;
import com.steema.teechart.languages.Language;

import com.steema.teechart.Rectangle;
import com.steema.teechart.drawing.ChartBrush;
import com.steema.teechart.IBaseChart;
import com.steema.teechart.drawing.Color;
import com.steema.teechart.drawing.Graphics3D;
import com.steema.teechart.drawing.IGraphics3D;
import com.steema.teechart.drawing.HatchStyle;

/**
 *
 * <p>Title: GridBand class</p>
 *
 * <p>Description: Grid Band tool, use it to display a coloured rectangles
 * (bands) at the grid lines of the specified axis and position.</p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public class GridBand extends ToolAxis {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ChartBrush band1;
    private ChartBrush band2;
    private ChartBrush tmpBand;

    public GridBand() {
        this((IBaseChart)null);
    }

    public GridBand(IBaseChart c) {
        super(c);

        getBand1().setColor(Color.BLACK);
        getBand2().setColor(Color.BLACK);
    }

    /**
     * Gets descriptive text.
     *
     * @return String
     */
    public String getDescription() {
        return Language.getString("GridBandTool");
    }

    /**
     * Gets detailed descriptive text.
     *
     * @return String
     */
    public String getSummary() {
        return Language.getString("GridBandSummary");
    }

    /**
     * The Brush characteristics of the first GridBand tool Band.
     *
     * @return ChartBrush
     */
    public ChartBrush getBand1()
    {
        if (band1 == null) {
            band1 = new ChartBrush(chart);
        }
        if (band1.getSolid()) {
            band1.setStyle(HatchStyle.BACKWARDDIAGONAL); //to avoid Form serialisation issues
        }
        if (chart != null) {
            chart.invalidate(); //CDI because firing BeforeDrawSeriesEventArgs?
        }
        return band1;
    }

    /**
     * The Brush characteristics of the second GridBand tool Band.
     *
     * @return ChartBrush
     */
    public ChartBrush getBand2()
    {
        if (band2 == null) {
            band2 = new ChartBrush(chart);
        }
        if (band2.getSolid()) {
            band2.setStyle(HatchStyle.BACKWARDDIAGONAL); //to avoid Form serialisation issues
        }
        if (chart != null) {
            chart.invalidate(); //CDI because firing BeforeDrawSeriesEventArgs?
        }
        return band2;
    }

    private void drawBand(int tmpPos1, int tmpPos2) {
          Rectangle tmpR;
      Rectangle chartRect = chart.getChartRect();

      Graphics3D g = (Graphics3D) chart.getGraphics3D();
      g.setBrush(tmpBand);
      boolean oldVisible = g.getPen().getVisible();
      g.getPen().setVisible(false);

      if (iAxis.getHorizontal())
      {
        tmpR = Rectangle.fromLTRB(tmpPos1, chartRect.getTop(), tmpPos2, chartRect.getBottom());
      }
      else
      {
        tmpR = Rectangle.fromLTRB(chartRect.getLeft(), tmpPos1-1, chartRect.getRight()-1, tmpPos2);
      }

      chart.getGraphics3D().rectangle(tmpR, chart.getAspect().getWidth3D());

      g.getPen().setVisible(oldVisible);
    }

    public void chartEvent(ChartDrawEvent e) {
        super.chartEvent(e);
        if ((e.getID()==ChartDrawEvent.PAINTING) &&
            (e.getDrawPart()==ChartDrawEvent.SERIES)) {
            if (chart != null && getAxis() != null) {
                drawGrids();
            }
        }
    }

    private void drawGrids() {
          int t, tmp;

      if (!getActive()) return;

      tmp = iAxis.axisDraw.getNumTicks();

      if (tmp > 0)
      {
        tmpBand = getBand1();


        int tmpValue = 0;

        if (tmp>1)
           tmpValue = ((iAxis.axisDraw.ticks[tmp - 1] - iAxis.axisDraw.ticks[tmp - 2]) / 2);

        if (iAxis.getHorizontal())
        {
          if (iAxis.getInverted())
          {
            if (iAxis.getGridCentered())
            {
                if (iAxis.axisDraw.ticks[tmp - 1] + tmpValue < iAxis.iEndPos)
                {
                    drawBand(iAxis.axisDraw.ticks[tmp-1] - tmpValue, iAxis.axisDraw.ticks[tmp-1] + tmpValue);
                    if (tmpBand == getBand1()) tmpBand = getBand2();
                    else tmpBand = getBand1();
                    drawBand(iAxis.axisDraw.ticks[tmp - 1] + tmpValue, iAxis.iEndPos - 1);
                }
                else
                {
                    drawBand(iAxis.axisDraw.ticks[tmp-1] - tmpValue, iAxis.iEndPos - 1);
                    if (tmpBand == getBand1()) tmpBand = getBand2();
                    else tmpBand = getBand1();
                }
            }
            else
            if (iAxis.axisDraw.ticks[tmp - 1] < iAxis.iEndPos)
            {
                drawBand(iAxis.iEndPos - 1, iAxis.axisDraw.ticks[tmp - 1]);
                tmpBand = getBand2();
            }
          }
          else
          {
            if (iAxis.getGridCentered())
            {
                if (iAxis.axisDraw.ticks[0] - tmpValue > iAxis.iEndPos)
                {
                    if (tmpBand == getBand1()) tmpBand = getBand2();
                    else tmpBand = getBand1();
                    drawBand(iAxis.axisDraw.ticks[0] + tmpValue, iAxis.iEndPos - 1);
                    if (tmpBand == getBand1()) tmpBand = getBand2();
                    else tmpBand = getBand1();
                }
                else
                {
                    drawBand(iAxis.axisDraw.ticks[0] + tmpValue, iAxis.axisDraw.ticks[0] - tmpValue);
                    if (tmpBand == getBand1()) tmpBand = getBand2();
                    else tmpBand = getBand1();
                    drawBand(iAxis.axisDraw.ticks[0] - tmpValue, iAxis.iEndPos - 1);
                }
            }
            else
            if (iAxis.axisDraw.ticks[0] < iAxis.iEndPos)
            {
              drawBand(iAxis.iEndPos - 1, iAxis.axisDraw.ticks[0]);
              tmpBand = getBand2();
             }
          }
        }
        else
        {
          if (iAxis.getInverted())
          {
            if (iAxis.getGridCentered())
            {
                if (iAxis.axisDraw.ticks[tmp - 1] + tmpValue > iAxis.iStartPos + 1)
                {
                    drawBand(iAxis.iStartPos + 1, iAxis.axisDraw.ticks[tmp - 1] + tmpValue);
                    if (tmpBand == getBand1()) tmpBand = getBand2();
                    else tmpBand = getBand1();

                    drawBand(iAxis.axisDraw.ticks[tmp - 1] + tmpValue, iAxis.axisDraw.ticks[tmp - 1] - tmpValue);
                    if (tmpBand == getBand1()) tmpBand = getBand2();
                    else tmpBand = getBand1();
                }
                else
                {
                    drawBand(iAxis.iStartPos + 1, iAxis.axisDraw.ticks[tmp - 1] - tmpValue);
                    if (tmpBand == getBand1()) tmpBand = getBand2();
                    else tmpBand = getBand1();
                }
            }
            else
            if (iAxis.axisDraw.ticks[tmp - 1] > iAxis.iStartPos)
            {
              drawBand(iAxis.iStartPos + 1, iAxis.axisDraw.ticks[tmp - 1]);
              tmpBand = getBand2();
            }
          }
          else
          {
            if (iAxis.getGridCentered())
            {
                if (iAxis.axisDraw.ticks[0] - tmpValue > iAxis.iStartPos + 1)
                {
                    drawBand(iAxis.iStartPos + 1, iAxis.axisDraw.ticks[0] - tmpValue);
                    tmpBand = getBand2();
                    drawBand(iAxis.axisDraw.ticks[0] - tmpValue, iAxis.axisDraw.ticks[0] + tmpValue);
                    if (tmpBand == getBand1()) tmpBand = getBand2();
                    else tmpBand = getBand1();
                }
                else
                {
                    drawBand(iAxis.iStartPos + 1, iAxis.axisDraw.ticks[0] + tmpValue);
                    if (tmpBand == getBand1()) tmpBand = getBand2();
                    else tmpBand = getBand1();
                }
            }
            else
            if (iAxis.axisDraw.ticks[0] > iAxis.iStartPos)
            {
              drawBand(iAxis.iStartPos + 1, iAxis.axisDraw.ticks[0]);
              tmpBand = getBand2();
            }
          }
        }

        if (iAxis.getInverted())
        {
          if (iAxis.getGridCentered())
          {
              for (t = tmp - 1; t > 1; --t)
              {
                  if (iAxis.axisDraw.ticks[t - 1] - tmpValue < iAxis.iEndPos)
                      drawBand(iAxis.axisDraw.ticks[t - 1] - tmpValue, iAxis.axisDraw.ticks[t] - tmpValue);
                  else
                      drawBand(iAxis.axisDraw.ticks[t] - tmpValue,iAxis.iEndPos - 1);

                  if (tmpBand == getBand1()) tmpBand = getBand2();
                  else tmpBand = getBand1();
              }
          }
          else
          for (t = tmp - 1; t > 0; --t)
          {
            drawBand(iAxis.axisDraw.ticks[t - 1], iAxis.axisDraw.ticks[t]);
            if (tmpBand == getBand1()) tmpBand = getBand2();
            else tmpBand = getBand1();
          }
        }
        else
        {
          if (iAxis.getGridCentered())
          {
            for (t = 2; t < tmp; ++t)
            {
                if (iAxis.axisDraw.ticks[t - 1]-tmpValue > iAxis.iStartPos)
                  drawBand(iAxis.axisDraw.ticks[t - 1]-tmpValue, iAxis.axisDraw.ticks[t]-tmpValue);
                else
                    drawBand(iAxis.iStartPos+1, iAxis.axisDraw.ticks[t] - tmpValue);

                if (tmpBand == getBand1()) tmpBand = getBand2();
                else tmpBand = getBand1();
            }
          }
          else
          for (t = 1; t < tmp; ++t)
          {
            drawBand(iAxis.axisDraw.ticks[t - 1], iAxis.axisDraw.ticks[t]);
            if (tmpBand == getBand1()) tmpBand = getBand2();
            else tmpBand = getBand1();
          }
        }

        if (iAxis.getHorizontal())
        {
          if (iAxis.getInverted())
          {
            if (iAxis.getGridCentered())
            {
                if (iAxis.axisDraw.ticks[0] - tmpValue < iAxis.iStartPos)
                {
                    drawBand(iAxis.iStartPos, iAxis.axisDraw.ticks[0] + tmpValue);
                }
                else
                {
                    drawBand(iAxis.axisDraw.ticks[0] - tmpValue, iAxis.axisDraw.ticks[0] + tmpValue);
                    if (tmpBand == getBand1()) tmpBand = getBand2();
                    else tmpBand = getBand1();
                    drawBand(iAxis.iStartPos, iAxis.axisDraw.ticks[0] - tmpValue);
                }
            }
            else
            if (iAxis.axisDraw.ticks[0] > iAxis.iStartPos)
              drawBand(iAxis.axisDraw.ticks[0], iAxis.iStartPos);
          }
          else
          {
            if (iAxis.getGridCentered())
            {
                if (iAxis.axisDraw.ticks[tmp - 1] + tmpValue > iAxis.iStartPos)
                {
                    drawBand(iAxis.axisDraw.ticks[tmp-1] + tmpValue, iAxis.axisDraw.ticks[tmp-1] - tmpValue);
                    if (tmpBand == getBand1()) tmpBand = getBand2();
                    else tmpBand = getBand1();
                    drawBand(iAxis.axisDraw.ticks[tmp - 1] + tmpValue, iAxis.iStartPos + 1);
                    if (tmpBand == getBand1()) tmpBand = getBand2();
                    else tmpBand = getBand1();
                }
                else
                {
                    drawBand(iAxis.iStartPos + 1, iAxis.axisDraw.ticks[tmp-1] - tmpValue);
                    if (tmpBand == getBand1()) tmpBand = getBand2();
                    else tmpBand = getBand1();
                }
            }
            else
            if (iAxis.axisDraw.ticks[tmp - 1] > iAxis.iStartPos)
              drawBand(iAxis.axisDraw.ticks[tmp - 1], iAxis.iStartPos);
          }
        }
        else
        {
          if (iAxis.getInverted())
          {
            if (iAxis.getGridCentered())
            {
                if (iAxis.axisDraw.ticks[0]-tmpValue < iAxis.iEndPos-1)
                {
                    drawBand(iAxis.axisDraw.ticks[0]-tmpValue, iAxis.iEndPos-1);
                }
            }
            else
            if (iAxis.axisDraw.ticks[0] < iAxis.iEndPos)
            {
              drawBand(iAxis.axisDraw.ticks[0], iAxis.iEndPos);
            }
          }
          else
          {
            if (iAxis.getGridCentered())
            {
                if (iAxis.axisDraw.ticks[tmp - 1] - tmpValue < iAxis.iEndPos - 1)
                {
                    drawBand(iAxis.axisDraw.ticks[tmp - 1] - tmpValue, iAxis.iEndPos - 1);
                }
                if (iAxis.axisDraw.ticks[tmp - 1] + tmpValue < iAxis.iEndPos - 1)
                {
                    if (tmpBand == getBand1()) tmpBand = getBand2();
                    else tmpBand = getBand1();
                    drawBand(iAxis.axisDraw.ticks[tmp - 1] + tmpValue, iAxis.iEndPos - 1);
                }
            }
            else
            if (iAxis.axisDraw.ticks[tmp - 1] < iAxis.iEndPos)
              drawBand(iAxis.axisDraw.ticks[tmp - 1], iAxis.iEndPos);
          }
        }
        }
    }
}

