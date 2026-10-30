package com.steema.teechart.axis;

import com.steema.teechart.drawing.IGraphics3D;
import com.steema.teechart.languages.Language;
import com.steema.teechart.TeeBase;

import com.steema.teechart.IBaseChart;

/**
 *
 * <p>Title: Axes class</p>
 *
 * <p>Description: Accesses list of all TChart Axes. Includes Custom and Depth
 * Axes</p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights
 * Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 * Example:
 * <pre><font face="Courier" size="4"> public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source == editButton) {
            if (myChart.getAxes().getCustom().size()==0) {
                DialogFactory.showModal(myChart.getAxes().getLeft());
            } else {
                DialogFactory.showModal(myChart.getAxes().getCustom().getAxis(0));
            }
        }
    }

    public void itemStateChanged(ItemEvent e) {
        Object source = e.getItemSelectable();
        boolean isSelected = (e.getStateChange() == ItemEvent.SELECTED);
        if (source == showButton) {
            for (int t=0; t < myChart.getAxes().getCustom().size(); t++) {
                myChart.getAxes().getCustom().getAxis(t).setVisible(isSelected);
            }
        }
    }</font></pre>

 */
public class Axes extends TeeBase {
	
	private static final long serialVersionUID = 1L;

    public static final int STANDARD_AXES = 6;

    protected CustomAxes custom = new CustomAxes();

    protected DepthAxis depth;
    protected DepthAxis depthTop;

    private Axis left;
    private Axis right;
    private Axis top;
    private Axis bottom;
    private boolean visible = true;
    private boolean drawBehind = true;

    static public Axis createNewAxis(IBaseChart chart) {
        return new Axis(chart);
    }

    public Axes() {
        this(null);
    }

    public Axes(IBaseChart c) {
        super(c);

        custom.setChart(chart);

        left = new Axis(chart);
        left.getTitle().setInitialAngle(90);

        right = new Axis(false, true, chart);
        right.zPosition = 100;
        right.getGrid().setZPosition(100);
        right.getTitle().setInitialAngle(270);

        top = new Axis(true, true, chart);
        top.zPosition = 100;
        top.getGrid().setZPosition(100);

        bottom = new Axis(true, false, chart);
        depth = new DepthAxis(false, true, chart);
        depthTop = new DepthAxis(false, false, chart);
    }

    public String[] stringItems() { //CDI Axis tools cannot be associated with Depth axes as yet.
        String[] names = new String[4 + custom.size()];
        names[0] = Language.getString("LeftAxis");
        names[1] = Language.getString("TopAxis");
        names[2] = Language.getString("RightAxis");
        names[3] = Language.getString("BottomAxis");
        //s[4]=Language.getString("DepthAxis");

        for (int t = 0; t < custom.size(); t++) {
            names[4 + t] = "Custom " + Integer.toString(t);
        }

        return names;
    }
    /**
     * Obtains the index of a given axis in the axes collection.<br>
     *
     * @param a Axis
     * @return int
     */
    public int indexOf(Axis a) {
        if (a == left) {
            return 0;
        } else if (a == top) {
            return 1;
        } else if (a == right) {
            return 2;
        } else if (a == bottom) {
            return 3;
        } else if (a == depth) {
            return 4;
        } else if (a == depthTop) {
            return 5;
        } else {
            return custom.indexOf(a) + STANDARD_AXES;
        }
    }

    /**
     * Accesses indexed axis
     *
     * @param index int
     * @return Axis
     */
    public Axis getAxis(int index) {

        if (index <= getCount()) {
            if (index < 6) {
                switch (index) {
                case 0:
                    return left;
                case 1:
                    return top;
                case 2:
                    return right;
                case 3:
                    return bottom;
                case 4:
                    return depth;
                case 5:
                    return depthTop;
                }
            } else {
                return custom.getAxis(index - STANDARD_AXES);
            }
        }
        return null;
    }


//[DefaultValue(null)]
//		public Object InternalCustomAxes
//		{
//			get {
//				return internalCustAxes; }
//			set {
//				internalCustAxes=value;
//				if (internalCustAxes is Array)
//				{
//					Array a=(Array)(internalCustAxes);
//					if (a.Length>0)
//					{
//						//custom.clear();
//						foreach(Object o in a)
//							if (o is Axis)
//								custom.Add((Axis)o);
//					}
//				}
//			}
//		}

    /**
     * Returns the number of axes.
     *
     * @return int Number of axes
     */
    public int getCount() {
        return custom.size() + STANDARD_AXES;
    }

    /**
     * Draws axes behind or in front of Series.<br>
     * Enables/disables the painting of the Axes before the Series.<br>
     * When false, the Axes will appear over the Chart Series.<br>
     * Default value: true
     *
     * @return boolean
     */
    public boolean getDrawBehind() {
        return drawBehind;
    }

    /**
     * Draws axes behind the Series when true.
     *
     * @param value boolean
     */
    public void setDrawBehind(boolean value) {
        drawBehind = setBooleanProperty(drawBehind, value);
    }

    public void doZoom(int x0, int y0, int x1, int y1) {
        chart.doZoom(
                getTop().calcPosPoint(x0), getTop().calcPosPoint(x1),
                getBottom().calcPosPoint(x0), getBottom().calcPosPoint(x1),
                getLeft().calcPosPoint(y1), getLeft().calcPosPoint(y0),
                getRight().calcPosPoint(y1), getRight().calcPosPoint(y0));
    }

    protected Axis checkAxis(Axis a) {
        return (a == null) ? new Axis(chart) : a;
    }

    /**
     * Accesses the Custom axes List.
     *
     * @return CustomAxes
     */
    public CustomAxes getCustom() {
        return custom;
    }
    /**
     * Accesses the Custom axes List.
     *
     * @param value CustomAxes
     */
    public void setCustom(CustomAxes value) {
        custom = value;
    }

    /**
     * Calls adjustMaxMin method of all axes and custom axes
     */
    public void adjustMaxMin() {
        left.adjustMaxMin();
        top.adjustMaxMin();
        right.adjustMaxMin();
        bottom.adjustMaxMin();
        depth.adjustMaxMin();
        depthTop.adjustMaxMin();

        for (int t = 0; t < custom.size(); t++) {
            custom.getAxis(t).adjustMaxMin();
        }
    }

    public void internalCalcPositions() {
        left.internalCalcPositions();
        top.internalCalcPositions();
        right.internalCalcPositions();
        bottom.internalCalcPositions();
        depth.internalCalcPositions();
        depthTop.internalCalcPositions();

        for (int t = 0; t < custom.size(); t++) {
            custom.getAxis(t).internalCalcPositions();
        }
    }

    /**
     * Determines the Labels and formatting attributes of Left Chart side.<br>
     * It also controls where Series points will be placed. <br>
     * Every TChart class has five Axes: Left, Top, Right, Bottom and z depth.
     *
     * @return Axis
     */
    public Axis getLeft() {
        return checkAxis(left);
    }
    /**
     * Determines the Labels and formatting attributes of Left Chart side.<br>
     * It also controls where Series points will be placed. <br>
     * Every TChart class has five Axes: Left, Top, Right, Bottom and z depth.
     *
     * @param value Axis
     */
    public void setLeft(Axis value) {
        left = value;
    }


    /**
     * Determines the Labels and formatting attributes of Top Chart side.<br>
     * It also controls where Series points will be placed.<br>
     * Every TChart class has five Axes: Left, Top, Right, Bottom and z depth.
     * The Top is pre-defined to be: <br>
     * Horizontal = true<br>
     * OtherSide = true<br>
     *
     * @return Axis
     * @see Axis
     */
    public Axis getTop() {
        return checkAxis(top);
    }
    /**
     * Determines the Labels and formatting attributes of Top Chart side.<br>
     * It also controls where Series points will be placed.<br>
     * Every TChart class has five Axes: Left, Top, Right, Bottom and z depth.
     * The Top is pre-defined to be: <br>
     * Horizontal = true<br>
     * OtherSide = true<br>
     *
     * @param value Axis
     */
    public void setTop(Axis value) {
        top = value;
    }


    /**
     * Determines the Labels and formatting attributes of Right Chart side.<br>
     * It also controls where Series points will be placed.<br>
     * Every TChart class has five Axes: Left, Top, Right, Bottom and z depth.
     *
     * @return Axis
     * @see Axis
     */
    public Axis getRight() {
        return checkAxis(right);
    }
    /**
     * Determines the Labels and formatting attributes of Right Chart side.<br>
     * It also controls where Series points will be placed.<br>
     * Every TChart class has five Axes: Left, Top, Right, Bottom and z depth.
     *
     * @param value Axis
     */
    public void setRight(Axis value) {
        right = value;
    }

    /**
     * Determines the Labels and formatting attributes of Bottom Chart side.<br>
     * It also controls where Series points will be placed.<br>
     * Every TChart class has five Axes: Left, Top, Right, Bottom and z depth.
     *
     * @return Axis
     * @see Axis
     */
    public Axis getBottom() {
        return checkAxis(bottom);
    }
    /**
     * Determines the Labels and formatting attributes of Bottom Chart side.<br>
     * It also controls where Series points will be placed.<br>
     * Every TChart class has five Axes: Left, Top, Right, Bottom and z depth.
     *
     * @param value Axis
     */
    public void setBottom(Axis value) {
        bottom = value;
    }

    /**
     * Accesses characteristics of the Depth Axis, or z axis as it is also
     * known.<br>
     * Every TChart class has five Axes: Left, Top, Right, Bottom and z depth.
     *
     * @return Axis
     * @see Axis
     */
    public DepthAxis getDepth() {
        if (depth == null) {
            depth = new DepthAxis(false, true, chart);
        }
        return depth;
    }
    /**
     * Accesses characteristics of the Depth Axis, or z axis as it is also
     * known.<br>
     * Every TChart class has five Axes: Left, Top, Right, Bottom and z depth.
     *
     * @param value DepthAxis
     */
    public void setDepth(DepthAxis value) {
        depth = value;
    }
    /**
     * Returns the Depth chart axis located at the left-top edge of chart.
     *
     * @return Axis
     */
    public Axis getDepthTop() {
        if (depthTop == null) {
            depthTop = new DepthAxis(false, false, chart);
        }
        return depthTop;
    }
    /**
     * Sets the Depth chart axis located at the left-top edge of chart.
     *
     * @param value DepthAxis
     */
    public void setDepthTop(DepthAxis value) {
        depthTop = value;
    }

    /**
     * Shows or hides the five Chart Axes at once.<br>
     * Each Axis will be drawn depending also on their Visible property.<br>
     * Default value: true
     *
     * @return boolean
     */
    public boolean getVisible() {
        return visible;
    }

    /**
     * Determines whether all five Chart Axes are visible or not.
     *
     * @param value boolean
     */
    public void setVisible(boolean value) {
        visible = setBooleanProperty(visible, value);
    }

    public void setChart(IBaseChart value) {
        super.setChart(value);

        left.setChart(chart);
        top.setChart(chart);
        right.setChart(chart);
        bottom.setChart(chart);
        depth.setChart(chart);
        depthTop.setChart(chart);

        custom.setChart(chart);
    }
    /**
     * This method paints the complete Axis (Ticks, Grid, Labels, Title)<br>
     * using the Axis.ParentChart.Canvas.<br>
     * Normally you do not need to call the Draw method directly.
     */
    public void draw() {
        draw(chart.getGraphics3D());
    }

    public void draw(IGraphics3D g) {
        if (chart.getParent() != null) {
            chart.getParent().doBeforeDrawAxes();
        }

        if (chart.isAxisVisible(left)) {
            left.draw(true);
        }
        if (chart.isAxisVisible(right)) {
            right.draw(true);
        }
        if (chart.isAxisVisible(top)) {
            top.draw(true);
        }
        if (chart.isAxisVisible(bottom)) {
        	if (! chart.drawBackWallAfter(0))
        		bottom.draw(true);
        }
        if (chart.isAxisVisible(depth)) {
            depth.draw(true);
        }
        if (chart.isAxisVisible(depthTop)) {
            depthTop.draw(true);
        }

        for (int t = 0; t < custom.size(); t++) {
            Axis a = custom.getAxis(t);
            if (a.getVisible()) {
                a.draw(true);
            }
        }
    }
}

