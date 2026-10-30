package com.steema.teechart.styles;

import com.steema.teechart.languages.Language;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.Timer;


import com.steema.teechart.DateTime;
import com.steema.teechart.drawing.IGraphics3D;
import com.steema.teechart.drawing.ChartPen;
import com.steema.teechart.drawing.Point;
import com.steema.teechart.IBaseChart;
import com.steema.teechart.drawing.Color;
import com.steema.teechart.misc.Utils;

/**
 *
 * <p>Title: Clock class</p>
 *
 * <p>Description: Clock Series</p>
 *
 *
 * <p>Example:
 * <pre><font face="Courier" size="4">
 * clock = new Clock(myChart.getChart());
 * clock.setColor(Color.RED);
 * clock.setShowInLegend(false);
 * clock.getBrush().setColor(Color.WHITE);
 * clock.getBrush().setColor(Color.RED);
 * clock.getPenHours().setColor(Color.BLACK);
 * clock.getPenMinutes().setColor(Color.BLACK);
 * clock.getPenSeconds().setColor(Color.BLACK);
 * </font></pre></p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights
 * Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public class Clock extends Custom2DPolar {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private final static String[] ROMANNUMBER = {
                                                "I",
                                                "II",
                                                "III",
                                                "IV",
                                                "V",
                                                "VI",
                                                "VII",
                                                "VIII",
                                                "IX",
                                                "X",
                                                "XI",
                                                "XII"
    };

    // private GetTime  : TClockSeriesGetTimeEvent;
    private ChartPen penHours;
    private ChartPen penMinutes;
    private ChartPen penSeconds;
    private ClockStyle style = ClockStyle.ROMAN;

    transient private Timer timer;

    public Clock() {
        this(null);
    }

    public Clock(IBaseChart c) {
        super(c);

        penHours = new ChartPen(c, Color.BLACK);
        penMinutes = new ChartPen(c, Color.BLACK);
        penSeconds = new ChartPen(c, Color.BLACK);

        getPointer().setDefaultVisible(false);

        setShowInLegend(false);
        getBrush().setVisible(true);
        getBrush().setSolid(true);
        setCircleLabels(true);
        setRotationAngle(90);
        setCircled(true);
        add(0);

        startTimer();
    }
    
    @Override
    public void assign(Series source) {
            super.assign(source);
            if(source instanceof Clock)
            {
                this.penHours.assign(((Clock)source).penHours);
                this.penMinutes.assign(((Clock)source).penMinutes);
                this.penSeconds.assign(((Clock)source).penSeconds);
            }
    } 

    private void startTimer() {
        if (timer == null) {
            timer = new Timer(1000, new ActionListener() {
                public void actionPerformed(ActionEvent evt) {
                    timer.stop();
                    repaint();
                    timer.start();
                }
            });
        }

        timer.start();
    }

    protected Object readResolve() {
        startTimer();
        return super.readResolve();
    }

    public void setChart(IBaseChart c) {

        super.setChart(c);

        setAngleIncrement(30);

        if (chart != null) {
            chart.getAxes().setVisible(false);
        }
        getPenHours().setChart(chart);
        getPenMinutes().setChart(chart);
        getPenSeconds().setChart(chart);

        if (chart == null) {
            timer.stop();
        }
    }

    private Point calcPos(double angle, double size) {
        return angleToPos(angle * PIDEGREE, size * getXRadius() * 0.5,
                          size * getYRadius() * 0.5);
    }

    public void dispose() {
        timer.stop();
        super.dispose();
    }

    protected void draw() {
        DateTime tmp = DateTime.getNow();

        // if ( GetTime != null ) GetTime(this,tmp);

        int h = tmp.getHour();
        int m = tmp.getMinute();
        int s = tmp.getSecond();
        int mSec = tmp.getMillisecond();

        IGraphics3D g = chart.getGraphics3D();
        g.setBrush(getBrush());
        g.getBrush().setColor(getColor());

        if (penHours.getVisible()) {
            g.setPen(penHours);
            Point p = calcPos(360.0 - (360.0 * (60.0 * h + m) / (12.0 * 60.0)),
                              1.3);
            g.arrow(true, new Point(getCircleXCenter(), getCircleYCenter()),
                    p, 14, 20, getEndZ());
        }

        if (penMinutes.getVisible()) {
            g.setPen(penMinutes);
            Point p = calcPos(360.0 - (360.0 * m / 60.0), 1.7);
            g.arrow(true, new Point(getCircleXCenter(), getCircleYCenter()),
                    p, 10, 16, getEndZ());
        }

        Point p = calcPos(360.0 - (360.0 * (s + (mSec / 1000.0)) / 60.0), 1.8); // 5.02

        if (penSeconds.getVisible()) {
            g.setPen(penSeconds);
            g.moveTo(getCircleXCenter(), getCircleYCenter(), getEndZ());
            g.lineTo(p, getEndZ());
        }

        if (iPointer.getVisible()) {
            Color tmpColor = iPointer.getBrush().getColor();
            iPointer.prepareCanvas(g, tmpColor);
            iPointer.draw(p, tmpColor);
        }
    }

    // Returns the String corresponding to the "Angle" degree parameter
    protected String getCircleLabel(double angle, int index) {
        int tmpAngle = Utils.round((360.0 - angle) / 30.0);

        if (tmpAngle == 0) {
            tmpAngle = 12;
        }

        return (style == ClockStyle.DECIMAL) ? Double.toString(tmpAngle) :
                ROMANNUMBER[tmpAngle - 1];
    }

    protected int numSampleValues() {
        return 1;
    }

    /**
     * Returns the Timer used by the clock series.<br>
     * Internal timer used to display the clock seconds arrow in realtime. <br>
     * Setting the Timer Interval property to less than one second can be cpu
     * consuming.
     *
     * @return Timer
     */
    public Timer getTimer() {
        return timer;
    }

    /**
     * Gets descriptive text.
     *
     * @return String
     */
    public String getDescription() {
        return Language.getString("GalleryClock");
    }

    /**
     * Sets the Pen for the Clock's hour hand.
     *
     * @return ChartPen
     */
    public ChartPen getPenHours() {
    	if (penHours==null)
    	  penHours = new ChartPen(chart, Color.BLACK);
      return penHours;
    }

    /**
     * Sets the Pen for the Clock's minute hand.
     *
     * @return ChartPen
     */
    public ChartPen getPenMinutes() {
    	if (penMinutes==null)
    	  penMinutes = new ChartPen(chart, Color.BLACK);
      return penMinutes;
    }

    /**
     * Sets the Pen for the Clock's second hand.
     *
     * @return ChartPen
     */
    public ChartPen getPenSeconds() {
    	if (penSeconds==null)
    	  penSeconds = new ChartPen(chart, Color.BLACK);
      return penSeconds;
    }

    /**
     * The style of the clock figures, Roman or Decimal.<br>
     * Default value: ClockStyle.Roman
     *
     *
     * @return ClockStyle
     */
    public ClockStyle getStyle() {
        return style;
    }

    /**
     * Sets the style of the clock figures, Roman or Decimal.<br>
     * Default value: ClockStyle.Roman
     *
     *
     * @param value ClockStyle
     */
    public void setStyle(ClockStyle value) {
        if (style != value) {
            style = value;
            invalidate();
        }
    }
}

