package com.steema.teechart.axis;

import com.steema.teechart.IBaseChart;
import com.steema.teechart.TextShape;
import com.steema.teechart.DateTime;
import com.steema.teechart.Rectangle;
import com.steema.teechart.Aspect;
import com.steema.teechart.DateTimeStep;
import com.steema.teechart.MultiLine;
import com.steema.teechart.TeeBase;

import com.steema.teechart.styles.Series;
import com.steema.teechart.styles.SeriesCollection;
import com.steema.teechart.misc.Utils;
import com.steema.teechart.drawing.IGraphics3D;
import com.steema.teechart.drawing.ChartPen;
import com.steema.teechart.drawing.Color;

/**
 *
 * <p>Title: AxisDraw class</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights
 * Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public class AxisDraw extends TeeBase
{

	private static final long serialVersionUID = 1L;
	
    private static final int MAXAXISTICKS = 2000;
    //CDI GridBand
    public int[] ticks = new int[MAXAXISTICKS];
    protected Axis axis;
    protected double tmpValue;
    public transient TicksGridDraw drawTicksAndGrid;
    private int tmpNumTicks;
    public boolean tmpAlternate;
    private IBaseChart chart;

    /**
     *
     *
     * @param a Axis
     */
    public AxisDraw(Axis a)
    {
        super(a.getChart());

        if (a != null)
        {
            axis = a;
            setChart(axis.getChart());
        }

        drawTicksAndGrid = new TicksGridDraw(axis);
    }

    public AxisDraw()
    {
        this(null);
    }

    //CDI GridBand
    public int getNumTicks()
    {
        return tmpNumTicks;
    }

    private int getDepthAxisPos()
    {
        int result;
        if (axis.getOtherSide())
        {
            result = chart.getChartRect().getBottom() - axis.calcZPos();
        }
        else
        {
            result = chart.getChartRect().getTop() + axis.calcZPos();
        }

        return result;
    }

    private int tryDrawLogLabels(boolean b, int i, double logarithmicBase) {
       int result = 0;

        double tmp = Math.pow(axis.getLogarithmicBase(), logBaseN(axis.getLogarithmicBase(), tmpValue) - i);
        if (logarithmicBase > 1)
        {
          while (tmpValue <= axis.iMaximum)
          {
            if (tmpValue >= axis.iMinimum)
            {
              internalDrawLabel(false);
              result++;
            }

            if (b)
            {
              tmpValue *= logarithmicBase;
            }
            else
            {
              tmpValue += tmp;
            }
          }
        }

        return result;
    }

    public final class TicksGridDraw
    {

        private Axis axis;
        //private int tmpNumTicks;
        private int[] tmpTicks;
        private int tmpWallSize;
        private IBaseChart c;
        private IGraphics3D g;
        private Aspect a;
        private boolean is3D;
        private Rectangle r;

        public TicksGridDraw(Axis a)
        {
            super();

            if (a != null)
            {
                axis = a;
                setChart(axis.chart);
            }
        }

        public void setChart(IBaseChart chart)
        {
            c = chart;
        }

        private int getLimit(boolean max)
        {
            if ((axis.getUsePartnerAxis()) && (axis.getPartnerAxis() != null))
            {
                if (((max) && (!axis.getPartnerAxis().getInverted()))
                        || ((!max) && (axis.getPartnerAxis().getInverted())))
                {
                    return axis.getPartnerAxis().calcPosValue(axis.getPartnerAxis().getMaximum());
                }
                else
                {
                    return axis.getPartnerAxis().calcPosValue(axis.getPartnerAxis().getMinimum());
                }
            }
            else
            {
                if (axis.horizontal)
                {
                    if (max)
                    {
                        return r.getTop();
                    }
                    else
                    {
                        return r.getBottom();
                    }
                }
                else if (max)
                {
                    //CDI TF02014289
                    return r.getRight() - 1;
                }
                else
                {
                    return r.getLeft();
                }
            }
        }

        private int getZGridPosition()
        {
            return (int) (a.getWidth3D() * axis.getGrid().getZPosition() * 0.01); // 6.0
        }

        private void drawGridLine(int tmp)
        {
            int topAX = r.getTop();

            if (chart.getAxes().getCustom().size() > 0)
            {
            topAX = chart.getAxes().getCustom().getAxis(0).calcPosValue(chart.getAxes().getCustom().getAxis(0).getMaximum());

            }
            //int rightAX=chart.axes.Bottom.CalcPosValue(chart.axes.Bottom.Maximum);
            //int bottomAX = chart.axes.Bottom.Position;
            //g.ClipRectangle(new Rectangle(leftAX, topAX, rightAX - leftAX, bottomAX - topAX));

            if ((tmp > axis.iStartPos) && (tmp < axis.iEndPos))
            {
                if (axis.isDepthAxis)
                {
                    g.verticalLine(r.getLeft(), r.getTop(), r.getBottom(), tmp);
                    g.horizontalLine(r.getLeft(), r.getRight(), r.getBottom(), tmp);
                }
                else if (axis.horizontal)
                {
                    if (is3D)
                    {
                        if (axis.getOtherSide())
                        {
                            g.verticalLine(tmp, r.getTop(), r.getBottom(), a.getWidth3D());
                        }
                        else
                        {
                            if (c.getAxes().getDrawBehind())
                            {
                                g.zLine(tmp, axis.posAxis, 0, a.getWidth3D());
                                //g.VerticalLine(tmp, r.Y, r.Bottom, a.Width3D);
                                if ((axis.horizontal) && (!axis.getOtherSide()))
                                {
                                    g.verticalLine(tmp, topAX, r.getBottom(), a.getWidth3D());
                                }
                                else
                                {
                                    g.verticalLine(tmp, r.getTop(), r.getBottom(), a.getWidth3D());
                                }
                            }
                            else
                            {
                                g.verticalLine(tmp, r.getTop(), r.getBottom(), 0);
                            }
                        }
                    }
                    else
                    //             if (!axis.otherSide)
                    {
                        g.verticalLine(tmp, getLimit(true), getLimit(false));
                    }
                    //            else
                    //              g.VerticalLine(tmp, r.Y + 1, r.Bottom);
                }
                else if (is3D)
                {
                    if (axis.getOtherSide())
                    {
                        g.horizontalLine(r.getLeft(), r.getRight(), tmp, a.getWidth3D());
                    }
                    else
                    {
                        if (c.getAxes().getDrawBehind())
                        {
                            g.zLine(axis.posAxis, tmp, 0, a.getWidth3D());
                            if (!axis.hideBackGrid)
                            {
                                if (axis.horizontal)
                                {
                                    g.verticalLine(tmp, r.getTop(), r.getBottom(), a.getWidth3D());
                                }
                                else
                                {
                                    g.horizontalLine(r.getLeft(), r.getRight(), tmp, a.getWidth3D());
                                }
                            }

                            // Pep
                            if (!axis.hideSideGrid)
                            {

                                int tmpZ = Utils.round(a.getWidth3D() * axis.grid.getZPosition() * 0.01);
                                if (tmpZ != a.getWidth3D())
                                {
                                    if (axis.horizontal)
                                    {
                                        g.zLine(tmp, axis.posAxis, tmpZ, a.getWidth3D());
                                    }
                                    else
                                    {
                                        g.zLine(axis.posAxis, tmp, tmpZ, a.getWidth3D());
                                    }
                                }
                            }
                        }
                        else
                        {
                            g.horizontalLine(r.getLeft(), r.getRight(), tmp, 0);
                        }
                    }
                }
                else
                {
                    g.horizontalLine(getLimit(false), getLimit(true), tmp);
                }
            }
        }

        public void drawGrids(int numTicks)
        {
            if (g == null)
                g = c.getGraphics3D();

            g.setPen(axis.getGrid());
            if (g.getPen().getColor().isEmpty())
            {
                g.getPen().setColor(Color.WHITE_SMOKE);
            }

            // GeneralPath p=new GeneralPath(); speed-up ?

            for (int t = 0; t < tmpNumTicks; t++)
            {
                if (0 == (t % axis.getGrid().getDrawEvery()))
                {
                    if (axis.getGrid().centered)
                    {
                        if (t > 0)
                        {
                            drawGridLine((int) (0.5
                                    * (tmpTicks[t] + tmpTicks[t - 1])));
                        }
                    }
                    else
                    {
                        drawGridLine(tmpTicks[t]);
                    }
                }
            }
        }

        private void internalDrawTick(int tmp, int delta,
                int tmpTickLength)
        {
            if (axis.isDepthAxis)
            {
                if (axis.getOtherSide())
                {
                    if (is3D)
                    {
                        g.horizontalLine(axis.posAxis + delta,
                                axis.posAxis + delta + tmpTickLength,
                                getDepthAxisPos(), tmp);
                    }
                    else
                    {
                        g.horizontalLine(axis.posAxis + delta,
                                axis.posAxis + delta + tmpTickLength,
                                getDepthAxisPos());
                    }
                }
                else
                {
                    if (is3D)
                    {
                        g.horizontalLine(axis.posAxis - delta,
                                axis.posAxis - delta - tmpTickLength,
                                getDepthAxisPos(), tmp);
                    }
                    else
                    {
                        g.horizontalLine(axis.posAxis - delta,
                                axis.posAxis - delta - tmpTickLength,
                                getDepthAxisPos());
                    }

                }
            }
            else if (axis.getOtherSide())
            {
                if (axis.horizontal)
                {
                    if (is3D)
                    {
                        g.verticalLine(tmp,
                                axis.posAxis - delta,
                                axis.posAxis - delta
                                - tmpTickLength, axis.iZPos);
                    }
                    else
                    {
                        g.verticalLine(tmp,
                                axis.posAxis - delta,
                                axis.posAxis - delta
                                - tmpTickLength);
                    }
                }
                else if (is3D)
                {
                    g.horizontalLine(axis.posAxis + delta,
                            axis.posAxis + delta
                            + tmpTickLength, tmp, axis.iZPos);
                }
                else
                {
                    g.horizontalLine(axis.posAxis + delta,
                            axis.posAxis + delta
                            + tmpTickLength, tmp);
                }
            }
            else
            {
                delta += tmpWallSize;
                if (axis.horizontal)
                {
                    if (is3D)
                    {
                        g.verticalLine(tmp,
                                axis.posAxis + delta,
                                axis.posAxis + delta
                                + tmpTickLength, axis.iZPos);
                    }
                    else
                    {
                        g.verticalLine(tmp,
                                axis.posAxis + delta,
                                axis.posAxis + delta
                                + tmpTickLength);
                    }
                }
                else if (is3D)
                {
                    g.horizontalLine(axis.posAxis - delta,
                            axis.posAxis - delta
                            - tmpTickLength, tmp, axis.iZPos);
                }
                else
                {
                    g.horizontalLine(axis.posAxis - delta,
                            axis.posAxis - delta
                            - tmpTickLength, tmp);
                }
            }
        }

        private void drawAxisLine()
        {
            if (axis.isDepthAxis)
            {
                int tmp;
                if (axis.getOtherSide())
                {
                    tmp = r.getBottom()
                            + c.getWalls().calcWallSize(c.getAxes().getBottom())
                            - axis.iZPos;
                }
                else
                {
                    tmp = r.getTop() - axis.iZPos;
                }
                g.line(axis.posAxis, tmp, axis.iStartPos,
                        axis.posAxis, tmp, axis.iEndPos);
            }
            else if (axis.horizontal)
            {
                if (axis.getOtherSide())
                {
                    g.horizontalLine(axis.iStartPos,
                            axis.iEndPos, axis.posAxis,
                            axis.iZPos);
                }
                else
                {
                    g.horizontalLine(axis.iStartPos
                            - c.getWalls().calcWallSize(c.getAxes().
                            getLeft()),
                            axis.iEndPos,
                            axis.posAxis + tmpWallSize,
                            axis.iZPos);
                }
            }
            else
            {
                int tmp = axis.getOtherSide() ? tmpWallSize
                        : -tmpWallSize;
                g.verticalLine(axis.posAxis + tmp,
                        axis.iStartPos,
                        axis.iEndPos
                        + c.getWalls().
                        calcWallSize(c.getAxes().getBottom()),
                        axis.iZPos);
            }
        }

        private void aProc(int aPos, boolean isGrid)
        {
            if ((aPos > axis.iStartPos) && (aPos < axis.iEndPos))
            {
                if (isGrid)
                {
                    ChartPen pen = axis.getMinorGrid();
                    drawGridLine(aPos);
                }
                else
                {
                    internalDrawTick(aPos, 1,
                            axis.getMinorTicks().length);
                }
            }
        }

        private void processMinorTicks(boolean isGrid)
        {
            double tmpDelta;
            double tmpInvCount = 1.0 / (axis.getMinorTickCount() + 1);

            if (tmpNumTicks > 1)
            {
                if (!axis.getLogarithmic())
                {
                    tmpDelta = 1.0 * (tmpTicks[1] - tmpTicks[0])
                            * tmpInvCount;
                    for (int t = 1; t <= axis.getMinorTickCount(); t++)
                    {
                        aProc(tmpTicks[0] - (int) (t * tmpDelta),
                                isGrid);
                        aProc(tmpTicks[tmpNumTicks - 1]
                                + (int) (t * tmpDelta), isGrid);
                    }
                }

                for (int t = 1; t <= tmpNumTicks - 1; t++)
                {
                    if (axis.getLogarithmic())
                    {

                        double tmpValue = axis.calcPosPoint(tmpTicks[t - 1]);
                        tmpDelta = ((tmpValue
                                * axis.getLogarithmicBase())
                                - tmpValue) * tmpInvCount;

                        for (int tt = 1;
                                tt < axis.getMinorTickCount(); tt++)
                        {
                            tmpValue += tmpDelta;
                            if (tmpValue <= axis.iMaximum)
                            {
                                aProc(axis.calcPosValue(tmpValue), isGrid);
                            }
                            else
                            {
                                break;
                            }
                        }
                    }
                    else
                    {
                        tmpDelta = 1.0
                                * (tmpTicks[t] - tmpTicks[t - 1])
                                * tmpInvCount;
                        for (int tt = 1;
                                tt <= axis.getMinorTickCount(); tt++)
                        {
                            aProc(tmpTicks[t]
                                    - (int) (tt * tmpDelta), isGrid);
                        }
                    }
                }
            }
        }

        private void processTicks(ChartPen aPen, int aOffset,
                int aLength)
        {
            if (aPen.getVisible() && (aLength != 0))
            {
                g.setPen(aPen);
                for (int t = 0; t < tmpNumTicks; t++)
                {
                    internalDrawTick(tmpTicks[t], aOffset,
                            aLength);
                }
            }
        }

        private void processMinor(ChartPen aPen, boolean isGrid)
        {
            if ((tmpNumTicks > 0) && aPen.getVisible())
            {
                g.setPen(aPen);
                processMinorTicks(isGrid);
            }
        }

        /**
         * Internal use.
         * 
         * @param tempTicks
         * @param tempNumTicks
         * @param tempValue
         */
        public void drawTicksGrid(int[] tempTicks,
                int tempNumTicks,
                double tempValue)
        {
            c = axis.chart;
            g = c.getGraphics3D();
            a = c.getAspect();
            is3D = a.getView3D();
            tmpTicks = tempTicks;
            tmpNumTicks = tempNumTicks;
            r = c.getChartRect();

            g.getBrush().setVisible(false);

            tmpWallSize = c.getWalls().calcWallSize(axis);
            if (axis.getAxisPen().getVisible())
            {
                g.setPen(axis.axispen);
                drawAxisLine();
            }
            processTicks(axis.getTicks(), 1, axis.getTicks().length);

            if (axis.getGrid().getVisible())
            {
                drawGrids(ticks.length);
            }

            processTicks(axis.getTicksInner(), -1, -axis.getTicksInner().length);
            processMinor(axis.getMinorTicks(), false);

            // speed optimization
            if (axis.minorGrid != null)
            {
                processMinor(axis.getMinorGrid(), true);
            }
        }
    }

    private void drawAxisTitle()
    {
        if ((axis.axisTitle != null) && axis.axisTitle.getVisible()
                && (axis.axisTitle.getCaption().length() != 0))
        {
            Rectangle tmp = axis.getChart().getChartRect();
            if (axis.isDepthAxis)
            {
                axis.drawTitle(axis.posTitle, tmp.getBottom());
            }
            else
            {
                if (axis.horizontal)
                {
                    axis.drawTitle(axis.iCenterPos, axis.posTitle);
                }
                else
                {
                    axis.drawTitle(axis.posTitle, axis.iCenterPos);
                }
            }
        }
    }

    private void addTick(int aPos)
    {
        ticks[tmpNumTicks] = aPos;
        tmpNumTicks++;
    }
    private double iIncrement;
    private int tmpWhichDatetime; // DateTimeStep

    private void internalDrawLabel(boolean decValue)
    {
        int tmp = axis.calcPosValue(tmpValue);
        if (axis.getLabels().bOnAxis
                || ((tmp > axis.iStartPos) && (tmp < axis.iEndPos)))
        {
            if (!axis.getTickOnLabelsOnly())
            {
                addTick(tmp);
            }
            if (axis.getLabels().getVisible())
            {
                drawThisLabel(tmp,
                        axis.getLabels().labelValue(tmpValue), null);
            }
        }
        if (decValue)
        {
            tmpValue = axis.incDecDateTime(false, tmpValue, iIncrement,
                    tmpWhichDatetime);
        }
    }

    private void drawThisLabel(int labelPos, String tmpSt,
            TextShape labelItem)
    {
        int tmpPos;

        if (axis.getTickOnLabelsOnly())
        {
            addTick(labelPos);
        }

        IBaseChart c = axis.chart;
        IGraphics3D g = c.getGraphics3D();
        g.setFont((labelItem == null) ? axis.getLabels().getFont()
                : labelItem.getFont());

        g.getBrush().setVisible(false);

        if (axis.isDepthAxis)
        {
            g.setTextAlign(axis.getDepthAxisAlign());

            int tmpZ = labelPos;

            if ((c.getAspect().getRotation() == 360)
                    || c.getAspect().getOrthogonal())
            {
                tmpZ += (g.getFontHeight() / 2);
            }

            if (axis.getOtherSide())
            {
                tmpPos = axis.getLabels().position;
            }
            else
            {
                tmpPos = axis.getLabels().position - 2 - (chart.getGraphics3D().textWidth("W") / 2);
            }

            g.textOut(tmpPos, getDepthAxisPos(),
                    tmpZ, tmpSt);
        }
        else
        {
            if (axis.getLabels().getAlternate())
            {
                if (tmpAlternate)
                {
                    tmpPos = axis.getLabels().position;
                }
                else
                {
                    if (axis.horizontal)
                    {
                        if (axis.getOtherSide())
                        {
                            tmpPos = axis.getLabels().position
                                    - g.getFontHeight();
                        }
                        else
                        {
                            tmpPos = axis.getLabels().position
                                    + g.getFontHeight();
                        }
                    }
                    else
                    {
                        if (axis.getOtherSide())
                        {
                            tmpPos = axis.getLabels().position
                                    + axis.maxLabelsWidth();
                        }
                        else
                        {
                            tmpPos = axis.getLabels().position
                                    - axis.maxLabelsWidth();
                        }
                    }
                }
                tmpAlternate = !tmpAlternate;
            }
            else
            {
                tmpPos = axis.getLabels().position;
            }

            if (axis.horizontal)
            {
                axis.drawAxisLabel(labelPos,
                        tmpPos,
                        axis.getLabels().iAngle, tmpSt, labelItem);
            }
            else
            {
                axis.drawAxisLabel(tmpPos,
                        labelPos, axis.getLabels().iAngle, tmpSt,
                        labelItem);
            }
        }
    }

    static private double intPower(double numBase, int exponent)
    {
        return Utils.pow(numBase, exponent);
    }

    static private double logBaseN(double numBase, double x)
    {
        return Utils.log(x, numBase);
    }

    static private DateTime roundDate(DateTime aDate,
            int aStep)
    {
        if (aDate.toDouble() <= DateTime.MINVALUE)
        {
            return aDate;
        }
        else
        {
            int year = aDate.getYear();
            int month = aDate.getMonth();
            int day = aDate.getDay();

            switch (aStep)
            {
                case DateTimeStep.HALFMONTH:
                    if (day >= 15)
                    {
                        day = 15;
                    }
                    else
                    {
                        day = 1;
                    }
                    break;
                case DateTimeStep.ONEMONTH:
                case DateTimeStep.TWOMONTHS:
                case DateTimeStep.THREEMONTHS:
                case DateTimeStep.FOURMONTHS:
                case DateTimeStep.SIXMONTHS:
                    day = 1;
                    break;
                case DateTimeStep.ONEYEAR:
                    day = 1;
                    month = 1;
                    break;
                default:
                    break;
            }
            return new DateTime(year, month, day);
        }
    }

    private void doDefaultLabels()
    {
        double tmp = 0;

        if (Math.abs(axis.iRange / iIncrement) < 10000) {
            tmpValue = axis.iMaximum / iIncrement;
            /* if less than 10000 labels... */
            /* calculate the maximum value... */
            if (axis.iAxisDateTime
                    && axis.getLabels().getExactDateTime()
                    && (tmpWhichDatetime != DateTimeStep.NONE)
                    && (tmpWhichDatetime > DateTimeStep.ONEDAY)) {
                tmpValue = roundDate(new DateTime(axis.iMaximum),
                        tmpWhichDatetime).toDouble();

            }
            else if ((Double.compare(axis.iMinimum, axis.iMaximum) == 0)
                    || (!axis.getLabels().getRoundFirstLabel())) {
                tmpValue = axis.iMaximum;
            }
            else {
                tmpValue = iIncrement * (int)tmpValue;
                if (Double.isInfinite(tmpValue))
                	tmpValue = (tmpValue<0) ? axis.iMinimum : axis.iMaximum;
            }

            /* adjust the maximum value to be inside "IMinimum" and "IMaximum" */
            /*while (tmpValue > axis.iMaximum)
            {
                tmpValue = axis.incDecDateTime(false, tmpValue,
                        iIncrement, tmpWhichDatetime);
            }*/
            
            double oldTmpValue;

            if (tmpValue > axis.iMaximum) {
              do {
                oldTmpValue = tmpValue;
                tmpValue = axis.incDecDateTime(false, tmpValue, iIncrement, tmpWhichDatetime);
              }
              while ((tmpValue > axis.iMaximum) && (Double.compare(oldTmpValue, tmpValue) != 0));
            }

            /* Draw the labels... */
            if (axis.iRangezero)
            {
                internalDrawLabel(false);
            }
            /* Maximum is equal to Minimum. Draw one label */
            else
            {
                /* do the loop and draw labels... */
                if (axis.getChart().getParent().getAxisLabelResolver()!=null)
                {
                  boolean tmpStop = false;
                  double tmpGap = tmpValue;
                  NextAxisLabelValue ev = new NextAxisLabelValue(-1, tmpGap, tmpStop);
                  axis.getChart().getParent().getAxisLabelResolver().getNextLabel(axis, -1, ev);
                  tmpGap=tmpValue+ev.getLabelValue();
                  ev = new NextAxisLabelValue(-1, tmpGap, tmpStop);
                  axis.getChart().getParent().getAxisLabelResolver().getNextLabel(axis, -1, ev);
                  tmpValue = tmpValue + +ev.getLabelValue();
                }
                /* do the loop and draw labels... */
                //if ((Math.abs(axis.iMaximum-axis.iMinimum)<1e-10) || (tmpValue.ToString()==(tmpValue-iIncrement).ToString()))
                if ((Double.compare(axis.iMaximum, axis.iMinimum) == 0)
                        || (Double.compare(tmpValue, tmpValue - iIncrement) == 0))
                {
                    /* fix zooming when axis Max=Min */
                    internalDrawLabel(false);
                }
                else
                {
                	/* draw labels until "tmpVale" is less than minimum */
                	if (axis.iMinimum >= 0)
                        tmp = (axis.iMinimum - axis.iMinAxisIncrement) / (1.0 + axis.iMinAxisIncrement);
                	else
                		tmp = axis.iMinimum;
                    
                    /*tmp = (axis.iMinimum
                            - axis.iMinAxisIncrement)
                            / (1.0 + axis.iMinAxisIncrement);*/

                    while (tmpValue >= tmp)
                    {
                       if (axis.getAxisCalcResolver() != null)
                       {
                          AxisLabelValueEventArgs ev = new AxisLabelValueEventArgs(tmpValue);
                          axis.getAxisCalcResolver().AxisLabelValueDelegate(axis, ev);
                          tmpValue = ev.getTmpValue();
                          if (tmpValue >= tmp)
                            internalDrawLabel(true);
                        }
                        else
                          internalDrawLabel(true);
                    }
                }
            }
        }
    }

    private void doDefaultLogLabels()
    {

        if (axis.iMinimum != axis.iMaximum)
        {
            if (axis.iMinimum <= 0)
            {
                if (axis.iMinimum == 0)
                {
                    axis.iMinimum = 0.1;
                }
                else
                {
                    axis.iMinimum = Axis.MINAXISRANGE;
                }
                axis.internalCalcLogRange();
                tmpValue = axis.iMinimum;
            }
            else
            {
                tmpValue = intPower(axis.getLogarithmicBase(),
                        Utils.round(logBaseN(axis.getLogarithmicBase(),
                        axis.iMinimum)));
            }

            // speed optimization
            boolean showMinorGrid = (axis.minorGrid != null)
                    && (axis.minorGrid.getVisible());

            if (showMinorGrid)
            {

                double tmpValue2 = tmpValue;

                if (tmpValue2 >= axis.iMinimum)
                {
                 tmpValue2 = Math.pow(axis.getLogarithmicBase(), logBaseN(axis.getLogarithmicBase(), axis.iMinimum) - 1);
                }

                if (tmpValue2 < axis.iMinimum)
                {
                    addTick(axis.calcPosValue(tmpValue2));
                }
            }
      if (tryDrawLogLabels(true, 0, axis.getLogarithmicBase()) == 0)
          {
            tryDrawLogLabels(false, 1, axis.getLogarithmicBase());
          }
//            if (axis.getLogarithmicBase() > 1)
//            {
//                while (tmpValue <= axis.iMaximum)
//                {
//                    if (tmpValue >= axis.iMinimum)
//                    {
//                        internalDrawLabel(false);
//                    }
//                    tmpValue *= axis.getLogarithmicBase();
//                }
//            }


            // For minor grids only...
            if (showMinorGrid && (tmpValue > axis.iMaximum))
            {
                addTick(axis.calcPosValue(tmpValue));
            }
        }
    }
      private boolean doDraw(int tmp)

      {
        boolean result = false;

        if (axis.getLabels().getOnAxis())
        {
          if ((tmp >= (axis.iStartPos - 1)) && (tmp <= (axis.iEndPos + 1)))
          {
            result = true;
          }
        }
        else
        {
          if ((tmp > axis.iStartPos) && (tmp < axis.iEndPos))
          {
            result = true;
          }
        }

        return result;
      }
    private void doNotCustomLabels()
    {
        if (axis.getLogarithmic() && (axis.getIncrement() == 0))
        {
            doDefaultLogLabels();
        }
        else
        {
            doDefaultLabels();
        }
    }
    private static final double DIFFLOAT = 0.0000001;

    private void doCustomLabels()
    {
        boolean labelInside = false;

        tmpValue = axis.iMinimum;

        int labelIndex = 0;
        NextAxisLabelValue r = new NextAxisLabelValue();
        r.setStop(true);

        // maximum 2000 labels...
        do
        {
            if (axis.chart.getParent() != null)
            {
                r = axis.chart.getParent().getAxisLabelResolver().getNextLabel(
                        axis,
                        labelIndex,
                        r);
                tmpValue = r.getLabelValue();
            }

            if (r.getStop())
            {
                if (labelIndex == 0)
                {
                    doNotCustomLabels();
                }
                return;
            }
            else
            {
                labelInside = ((tmpValue
                        >= (axis.iMinimum - DIFFLOAT))
                        && (tmpValue
                        <= (axis.iMaximum + DIFFLOAT)));
                if (labelInside)
                {
                    internalDrawLabel(false);
                }
                labelIndex++;
            }
        }
        while (labelInside || (labelIndex < 2000) || (!r.getStop()));
    }

    final class IntRange
    {

        int first;
        int last;

        public IntRange()
        {
            this(0, 0);
        }

        public IntRange(int first, int last)
        {
            this.first = first;
            this.last = last;
        }
    }

    private IntRange calcFirstLastAllSeries(Rectangle rect)
    {
        IntRange tmp = new IntRange(Integer.MAX_VALUE, -1);

        for (int t = 0; t < axis.iSeriesList.size(); t++)
        {
            Series s = axis.iSeriesList.getSeries(t);

            s.calcFirstLastVisibleIndex();

            if ((s.getFirstVisible() < tmp.first)
                    && (s.getFirstVisible() != -1))
            {
                tmp.first = s.getFirstVisible();
            }

            if (s.getLastVisible() > tmp.last)
            {
                tmp.last = s.getLastVisible();
            }
        }

        return tmp;
    }

    private void calcAllSeries()
    {
        if (axis.iSeriesList == null)
        {
            axis.iSeriesList = new SeriesCollection(chart);
        }

        axis.iSeriesList.clear();

        for (int t = 0; t < axis.chart.getSeriesCount(); t++)
        {
            Series s = axis.chart.getSeries(t);
            if (s.getActive() && s.associatedToAxis(axis))
            {
                axis.iSeriesList.internalAdd(s);
            }
        }
    }
    private AxisLabelStyle tmpLabelStyle;

    final class GetAxisSeriesLabelResults
    {

        boolean result;
        double value;
        String label;

        public GetAxisSeriesLabelResults()
        {
            super();
        }
    }

    private GetAxisSeriesLabelResults getAxisSeriesLabel(int aIndex)
    {
        final GetAxisSeriesLabelResults result = new GetAxisSeriesLabelResults();
        result.result = false;

        for (int t = 0; t < axis.iSeriesList.size(); t++)
        {

            final Series tmpSeries = axis.iSeriesList.getSeries(t);

            if ((aIndex >= tmpSeries.getFirstVisible())
                    && (aIndex <= tmpSeries.getLastVisible()))
            {

                // even if the series has no text labels
                if (tmpLabelStyle == AxisLabelStyle.MARK)
                {
                    result.label = tmpSeries.getValueMarkText(aIndex);
                }
                else if (tmpLabelStyle == AxisLabelStyle.TEXT)
                {
                    result.label = tmpSeries.getLabels().getString(aIndex);
                }

                if (axis.chart.getParent() != null)
                {
                    result.label = axis.chart.getParent().getAxisLabelResolver().
                            getLabel(
                            axis,
                            tmpSeries,
                            aIndex,
                            result.label);
                }

                if (result.label.length() != 0)
                {
                    result.result = true;

                    if(axis.isDepthAxis) {
                        result.value = tmpSeries.valueListOfAxis(axis).value[aIndex];
                    }
                    else if (axis.horizontal)
                    {
                        result.value = tmpSeries.getXValues().value[aIndex];
                    }
                    else
                    {
                        result.value = tmpSeries.getYValues().value[aIndex];
                    }

                    break;
                }
            }
        }

        return result;
    }

    private void depthAxisLabels()
    {

        if (axis.chart.countActiveSeries() > 0)
        {

            for (int t = (int) axis.iMinimum; t <= axis.iMaximum; t++)
            {

                int tmp = axis.calcYPosValue(axis.iMaximum - t - 0.5);

                if (!axis.getTickOnLabelsOnly())
                {
                    addTick(tmp);
                }
                if (axis.getLabels().getVisible())
                {
                    String tmpSt = axis.chart.getSeriesTitleLegend(t, true);

                    if (axis.chart.getParent() != null)
                    {
                        tmpSt = axis.chart.getParent().getAxisLabelResolver().
                                getLabel(
                                axis,
                                null,
                                t,
                                tmpSt);
                    }

                    drawThisLabel(tmp, tmpSt, null);
                }
            }
        }
    }
    private int depthAxisPos()
    {
      int result;
      if (axis.getOtherSide())
          result = axis.getChart().getChartRect().getBottom() - axis.calcZPos();
        else
          result = axis.getChart().getChartRect().getTop() + axis.calcZPos();
        return result;

    }
    private void axisLabelsSeries(final Rectangle rect)
    {
        /* Select all active Series that have "Labels" */
        int tmp;
        int tmpNum = 0;

        //int tmpFirst;
        //int tmpLast;
      
        String tmpSt = "";
        double tmpValue = 0;
        int tmpLabelSize;
        boolean tmpDraw;

        axis.calcAllSeries();
        final IntRange tmpRange = calcFirstLastAllSeries(rect);

        if (tmpRange.first != Integer.MAX_VALUE)
        {
            int oldPosLabel = -1;
            int oldSizeLabel = 0;
            boolean tmpLabelW = axis.horizontal;
            switch (axis.getLabels().iAngle)
            {
                case 90:
                case 270:
                    tmpLabelW = !tmpLabelW;
                    break;
                default:
                    break;
            }

            int tempInc = Utils.round(axis.calcXYIncrement(axis.getSizeLabels(false)));

            if (tempInc == 0 || (tmpRange.last / tempInc) < 2 || calcLabelCountAllSeries() <= (tmpRange.last + 1))  //TF02013438 //TF02014629 //TF02015083
            {
                tempInc = 1;
            }

            //axis.getLabels().labelPos.Clear();
            for (int t = tmpRange.first; t <= tmpRange.last; t+=tempInc)
            {
                final GetAxisSeriesLabelResults r = getAxisSeriesLabel(t);

                if (r.result)
                {
                    tmpValue = r.value;
                    tmpSt = r.label;

                    if ((tmpValue >= axis.iMinimum)
                            && (tmpValue <= axis.iMaximum))
                    {
                        tmp = axis.calcPosValue(tmpValue);
                        if (!axis.getTickOnLabelsOnly())
                        {
                            addTick(tmp);
                        }

                        if (axis.getLabels().getVisible()
                                && (tmpSt.length() != 0))
                        {

                            MultiLine tmpMulti = axis.chart.multiLineTextWidth(
                                    tmpSt);

                            tmpLabelSize = tmpMulti.width;
                            tmpNum = tmpMulti.count;

                            if (!tmpLabelW)
                            {
                                tmpLabelSize = chart.getGraphics3D().
                                        getFontHeight() * tmpNum;
                            }
                            if ((axis.getLabels().iSeparation != 0)
                                    && (oldPosLabel != -1))
                            {
                                tmpLabelSize += (int) (0.02 * tmpLabelSize
                                        * axis.getLabels().iSeparation);
                                tmpLabelSize *= 0.5;

                                if (tmp >= oldPosLabel)
                                {
                                    tmpDraw = ((tmp
                                            - tmpLabelSize)
                                            >= (oldPosLabel
                                            + oldSizeLabel));
                                }
                                else
                                {
                                    tmpDraw = ((tmp
                                            + tmpLabelSize)
                                            <= (oldPosLabel
                                            - oldSizeLabel));
                                }

                                if (tmpDraw)
                                {
                                    drawThisLabel(tmp, tmpSt, null);
                                    oldPosLabel = tmp;
                                    oldSizeLabel = tmpLabelSize;
                                }
                            }
                            else
                            {
                                drawThisLabel(tmp, tmpSt, null);
                                oldPosLabel = tmp;
                                oldSizeLabel = tmpLabelSize / 2;
                            }
                        }
                    }
                }
            }

            axis.iSeriesList.clear(false);
        }
    }
    
    private int calcLabelCountAllSeries() {
        int result = 0;
        for (int i=0; i<axis.iSeriesList.size(); i++) {
          result = Math.max(result, axis.iSeriesList.getSeries(i).getLabels().getCount());
        }
        return result;
    }

    private void drawCustomLabels()
    {

        for (int t = 0; t < axis.getLabels().getItems().size(); t++)
        {

            AxisLabelItem tmpItem = axis.getLabels().getItems().getItem(t);

            if (tmpItem.getValue() >= axis.getMinimum()
                    && tmpItem.getValue() <= axis.getMaximum())
            {

                int tmp = axis.calcPosValue(tmpItem.getValue());

                if (!axis.getTickOnLabelsOnly())
                {
                    addTick(tmp);
                }
                if (tmpItem.getVisible())
                {
                    String tmpSt = tmpItem.getText();
                    if (tmpSt.equals(""))
                    {
                        tmpSt = axis.getLabels().labelValue(tmpItem.getValue());
                    }

                    drawThisLabel(tmp, tmpSt, tmpItem);
                }
            }
        }
    }

    /**
     * Displays an Axis at the specified screen positions with the current
     * axis scales.
     * 
     * @param calcPosAxis
     */
    public void draw(boolean calcPosAxis)
    {
        axis.iAxisDateTime = axis.isDateTime();

        if (calcPosAxis)
        {
            axis.posAxis = axis.applyPosition(axis.getRectangleEdge(axis.chart.getChartRect()),
                    axis.chart.getChartRect());
        }

        drawAxisTitle();
        tmpNumTicks = 0;
        tmpAlternate = axis.horizontal;

        if (axis.getLabels().getItems().size() == 0)
        {
            tmpLabelStyle = axis.calcLabelStyle();

            if (tmpLabelStyle != AxisLabelStyle.NONE)
            {
                // Assign font before CalcIncrement !
                chart.getGraphics3D().setFont(axis.getLabels().getFont());
                iIncrement = axis.getCalcIncrement();

                if (axis.iAxisDateTime
                        && axis.getLabels().getExactDateTime()
                        && (axis.getIncrement() != 0))
                {
                    tmpWhichDatetime = Axis.findDateTimeStep(
                            axis.getIncrement());
                    if (tmpWhichDatetime != DateTimeStep.NONE)
                    {
                        while ((iIncrement
                                > (DateTimeStep.STEP[tmpWhichDatetime]))
                                && (tmpWhichDatetime
                                != DateTimeStep.ONEYEAR))
                        {
                            tmpWhichDatetime += 1;
                        }
                    }
                }
                else
                {
                    tmpWhichDatetime = DateTimeStep.NONE;
                }

                if (((iIncrement > 0) || ((tmpWhichDatetime>= DateTimeStep.HALFMONTH)  && (tmpWhichDatetime
                        <= DateTimeStep.ONEYEAR)))
                        && (axis.iMaximum >= axis.iMinimum))
                {

                    if (tmpLabelStyle == AxisLabelStyle.VALUE)
                    {

                        doCustomLabels();
                        /*if ((axis.chart.getParent() != null) && --> doNotCustomLabels is called from doCustomLabels
                        (axis.chart.getParent().
                        checkGetAxisLabelAssigned())) {
                        doCustomLabels();
                        } else {
                        doNotCustomLabels();
                        }*/
                    }
                    else if (tmpLabelStyle == AxisLabelStyle.MARK)
                    {
                        axisLabelsSeries(axis.chart.getChartRect());
                    }
                    else if (tmpLabelStyle == AxisLabelStyle.TEXT)
                    {
                        if (axis.isDepthAxis && ((DepthAxis)axis).getLabelsAsSeriesTitles())
                        {
                            depthAxisLabels();
                        }
                        else
                        {
                            axisLabelsSeries(axis.chart.getChartRect());
                        }
                    }
                }
            }
        }
        else
        {
            drawCustomLabels();
        }
       drawTicksAndGrid.drawTicksGrid(ticks, tmpNumTicks, tmpValue);
    }

   public void setChart(IBaseChart chart)
    {
        this.chart = chart;

        if (drawTicksAndGrid != null)
        {
            drawTicksAndGrid.setChart(chart);
        }
    }
}
