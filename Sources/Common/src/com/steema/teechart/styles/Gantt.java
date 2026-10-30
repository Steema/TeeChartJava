package com.steema.teechart.styles;


import com.steema.teechart.languages.Language;
import com.steema.teechart.DateTime;
import com.steema.teechart.drawing.Color;
import com.steema.teechart.drawing.IGraphics3D;
import com.steema.teechart.IBaseChart;
import com.steema.teechart.drawing.ChartPen;
import com.steema.teechart.misc.Utils;

/**
 *
 * <p>Title: Gantt class</p>
 *
 * <p>Description: Gantt Series.</p>
 *
 * <p>Example:
 * <pre><font face="Courier" size="4">
 *   ganttSeries = new com.steema.teechart.styles.Gantt(myChart.getChart());
 *   ganttSeries.fillSampleValues();
 * </font></pre></p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights
 * Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public class Gantt extends Points {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final int NUMGANTTSAMPLES = 10;

    private ValueList endValues;
    private ValueList nextTask;

    /**
     * Creates a new Gantt series.
     */
    public Gantt() {
        this((IBaseChart)null);
    }

    public Gantt(IBaseChart c) {
        super(c);

        setHorizontal();
        calcVisiblePoints = false; // draw all points

        vxValues.name = Language.getString("ValuesGanttStart");
        vxValues.setDateTime(true);
        vxValues.setOrder(ValueListOrder.NONE);

        bColorEach = true;

        getLinePen().setColor(Color.BLACK);

        endValues = new ValueList(this, Language.getString("ValuesGanttEnd"));
        endValues.setDateTime(true);

        nextTask = new ValueList(this, Language.getString("ValuesGanttNextTask"));
        point.setStyle(PointerStyle.RECTANGLE); // <-- a Horizontal Gantt Bar (by default)
    }

    /**
     * Defines the starting Gantt bar date values.<br>
     * The ending Gantt bar point date is stored at TGanttSeries.EndValues.<br>
     * StartValues and EndValues can be specified both as DateTime or double
     * values. <br>
     * Both are standard TChartValueList components. That means you can access
     * their values with same methods as you can access X or Y values.<br>
     * The TGanttSeries.Add method must be used to add Gantt bar points. <br>
     *
     * @return ValueList
     */
    public ValueList getStartValues() {
        return vxValues;
    }

    /**
     * Defines the ending Gantt bar date value.<br>
     * The starting Gantt bar point is stored at TGanttSeries.StartValues<br>
     * StartValues and EndValues can be specified both as DateTime or double
     * values. <br>
     * Both are standard TChartValueList components. That means you can access
     * their values with same methods as you can access X or Y values. <br>
     * The TGanttSeries.Add  method must be used to add Gantt bar points. <br>
     *
     * @return ValueList
     */
    public ValueList getEndValues() {
        return endValues;
    }

    /**
     * Holds the Gantt bar index each Gantt bar is connected to. <br>
     * When a Gantt bar is added to TGanttSeries, it's NextTask value is
     * assigned to -1 by default. That means the Gantt bar is NOT connected
     * to any other Gantt Bar. <br>
     * You need to set a valid bar index to NextTask. <br>
     *
     * @return ValueList
     */
    public ValueList getNextTasks() {
        return nextTask;
    }

    static private String ganttSampleStr(int index) {
        switch (index) {
        case 0:
            return Language.getString("GanttSample1");
        case 1:
            return Language.getString("GanttSample2");
        case 2:
            return Language.getString("GanttSample3");
        case 3:
            return Language.getString("GanttSample4");
        case 4:
            return Language.getString("GanttSample5");
        case 5:
            return Language.getString("GanttSample6");
        case 6:
            return Language.getString("GanttSample7");
        case 7:
            return Language.getString("GanttSample8");
        case 8:
            return Language.getString("GanttSample9");
        default:
            return Language.getString("GanttSample1");
        }
    }

    /**
     * Adds random values to series.
     * @param numValues int
     */
    protected void addSampleValues(int numValues) {

        SeriesRandom r = randomBounds(numValues);

        // some sample values to see something at design mode
        for (int t = 0;
                     t <=
                     Math.min(numValues,
                              NUMGANTTSAMPLES + Utils.round(20 * r.Random()));
                     t++) {

            double tmpStartTask = DateTime.getToday().toDouble() + t * 3 +
                           5 * r.Random();
            double tmpEndTask = tmpStartTask + 9 + 16 * r.Random();
            int tmpY = (t % 10);

            int addedGantt = add(tmpStartTask, // Start
                            tmpEndTask, // End
                            tmpY, // Y (vertical position)
                            ganttSampleStr(tmpY) // some sample label text
                        );

            // Connect Gantt points
            for (int tt = 0; tt < addedGantt; tt++) {
                if ((nextTask.value[tt] == -1) && (tmpStartTask > endValues.value[tt])) {
                    nextTask.value[tt] = addedGantt;
                    break;
                }
            }
        }
    }

    /**
     * Determines the pen to draw the optional lines that connect Gantt
     * Bars. <br>
     *
     * @return ChartPen
     */
    public ChartPen getConnectingPen() {
        return linePen;
    }

    /**
     * Called internally. Draws the "ValueIndex" point of the Series.
     *
     * @param valueIndex int
     */
    public void drawValue(int valueIndex) {
        // This overrided method is the main paint method for Gantt bar points.
        if (point.getVisible()) {
            Color c = getValueColor(valueIndex);
            point.prepareCanvas(chart.getGraphics3D(), c);

            int x1 = calcXPos(valueIndex);
            int x2 = calcXPosValue(endValues.value[valueIndex]);
            int tmpHalfHorizSize = (x2 - x1) / 2;
            int y = calcYPos(valueIndex);

            PointerStyle tmpStyle = onGetPointerStyle(valueIndex, point.getStyle());

            IGraphics3D g = chart.getGraphics3D();

            point.draw(g,
                       chart.getAspect().getView3D(),
                       x1 + tmpHalfHorizSize,
                       y,
                       tmpHalfHorizSize,
                       point.getVertSize(),
                       c, tmpStyle);

            if (getConnectingPen().getVisible()) {
                int tmpNextTask = Utils.round(nextTask.value[valueIndex]);

                if ((tmpNextTask >= 0) && (tmpNextTask < getCount())) {
                    g.setPen(getConnectingPen());
                    g.getBrush().setVisible(false);

                    int xNext = calcXPos(tmpNextTask);
                    int halfWay = x2 + ((xNext - x2) / 2);
                    int yNext = calcYPos(tmpNextTask);
                    g.line(x2, y, halfWay, y, getMiddleZ());
                    g.lineTo(halfWay, yNext, getMiddleZ());
                    g.lineTo(xNext, yNext, getMiddleZ());
                }
            }
        }
    }

    /**
     * For internal use.
     *
     * @param valueIndex int
     * @param tmpX int
     * @param tmpY int
     * @param x int
     * @param y int
     * @return boolean
     */
    public boolean clickedPointer(int valueIndex, int tmpX, int tmpY, int x,
                                  int y) {
        return (x >= tmpX) && (x <= calcXPosValue(getEndValues().value[valueIndex])) &&
                (Math.abs(tmpY - y) < getPointer().getVertSize());
    }

    protected void drawMark(int valueIndex, String s,
                            SeriesMarksPosition aPosition) {
        aPosition.leftTop.x +=
                (calcXPosValue(endValues.value[valueIndex]) - aPosition.arrowFrom.x) /
                2;
        aPosition.leftTop.y += aPosition.height / 2;
        super.drawMark(valueIndex, s, aPosition);
    }

    public void prepareForGallery(boolean isEnabled) {
        super.prepareForGallery(isEnabled);
        setColorEach(isEnabled);
        point.setVertSize(3);
    }

    /*
         public void Add(DataView view) {
            int labelField = -1;
            int colorField = -1;
            Color tmpColor = Color.EMPTY;
            String tmpLabel = "";

            int[] fields = new int[ValuesLists.Count];

            int fieldCount = 0;
            foreach(ValueList v in ValuesLists)
            if (v.DataMember.length() != 0) {
                fields[ValuesLists.IndexOf(v)] = view.Table.Columns.IndexOf(v.DataMember);
                fieldCount++;
            }

            if (labelMember.length() != 0) {
                labelField = view.Table.Columns.IndexOf(labelMember);
            }
            if (colorMember.length() != 0) {
                colorField = view.Table.Columns.IndexOf(colorMember);
            }

            if (fieldCount == ValuesLists.Count - 1) {
                foreach(DataRowView rv in view) {
                    DataRow r = rv.Row;
                    if (colorField != -1) {
                        tmpColor = (Color) (r[colorField]);
                    }
                    if (labelField != -1) {
                        tmpLabel = Convert.ToString(r[labelField]);
                    }

                    foreach(ValueList v in ValuesLists) {
                        int fieldIndex = fields[ValuesLists.IndexOf(v)];
                        if (r[fieldIndex] is DateTime) {
                            v.TempValue = misc.Utils.DateTime((System.DateTime) r[
                                    fieldIndex]);
                        } else {
                            v.TempValue = Convert.ToDouble(r[fieldIndex]);
                        }
                    }

     Add(XValues.TempValue, endValues.TempValue, YValues.TempValue,
                        tmpLabel, tmpColor);
                }
            }
        }
     */

    public int add(DateTime start, DateTime endDate, double y, String text) {
        return add(start, endDate, y, text, Color.EMPTY);
    }

    public int add(DateTime start, DateTime endDate, double y, String text,
                   Color color) {
        return add(start.toDouble(), endDate.toDouble(), y, text,
                   color);
    }

    /**
     * Adds a new Gantt point to the series, at the start and endDate positions.
     * @param start DateTime
     * @param endDate DateTime
     * @param y double
     * @param color Color
     * @return int
     */
    public int add(DateTime start, DateTime endDate, double y, Color color) {
        return add(start, endDate, y, "", color);
    }

    /**
     * Adds a new Gantt bar with start and end coordinates and color.
     *
     * @param start double
     * @param endDate double
     * @param y double
     * @param color Color
     * @return int
     */
    public int add(double start, double endDate, double y, Color color) {
        return add(start, endDate, y, "", color);
    }

    /**
     * Adds a new Gantt bar with start and end coordinates to the Series.
     *
     * @param start double
     * @param endDate double
     * @param y double
     * @return int
     */
    public int add(double start, double endDate, double y) {
        return add(start, endDate, y, "", Color.EMPTY);
    }

    /**
     * Adds a new Gantt bar with start and end coordinates and label.
     *
     * @param start double
     * @param endDate double
     * @param y double
     * @param text String
     * @return int
     */
    public int add(double start, double endDate, double y, String text) {
        return add(start, endDate, y, text, Color.EMPTY);
    }

    /**
     * Adds a new Gantt bar with start and end coordinates, label and color.
     *
     * @param start double
     * @param endDate double
     * @param y double
     * @param text String
     * @param color Color
     * @return int
     */
    public int add(double start, double endDate, double y, String text,
                   Color color) {
        endValues.tempValue = endDate;
        nextTask.tempValue = -1;
        return add(start, y, text, color);
    }

    /**
     * True if Series source is Gantt.<br>
     * It returns false if the Value parameter is the same as Self. <br>
     * It's used to validate DataSource both at design and run-time. <br>
     *
     * @param value Series
     * @return boolean
     */
    public boolean isValidSourceOf(Series value) {
        return value instanceof Gantt; // Only Gantt can be assigned to Gantt
    }

    /**
     * Returns the Maximum Value of the Series X Values List.
     *
     * @return double
     */
    public double getMaxXValue() {
        return Math.max(super.getMaxXValue(), endValues.getMaximum());
    }

    /**
     * Gets descriptive text.
     *
     * @return String
     */
    public String getDescription() {
        return Language.getString("GalleryGantt");
    }
}

