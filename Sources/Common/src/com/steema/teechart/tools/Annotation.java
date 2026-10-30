package com.steema.teechart.tools;

import com.steema.teechart.drawing.Graphics3D;
import com.steema.teechart.events.ChartDrawEvent;
import com.steema.teechart.languages.Language;
import com.steema.teechart.Rectangle;
import com.steema.teechart.drawing.Point;
import com.steema.teechart.drawing.StringAlignment;

import com.steema.teechart.drawing.Color;
import com.steema.teechart.Cursor;
import com.steema.teechart.TextShapePosition;
import com.steema.teechart.MultiLine;
import com.steema.teechart.drawing.IGraphics3D;
import com.steema.teechart.IBaseChart;
import com.steema.teechart.PositionUnits;
import com.steema.teechart.events.ToolMouseListener;
import com.steema.teechart.misc.MathUtils;
import com.steema.teechart.events.FrameworkMouseEvent;
import com.steema.teechart.misc.StringFormat;
import com.steema.teechart.misc.Utils;

/**
 *
 * <p>Title: Annotation class</p>
 *
 * <p>Description: Annotation tool.</p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public class Annotation extends Tool {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private AnnotationPosition position = AnnotationPosition.LEFTTOP;
    private TextShapePosition shape;
    private StringAlignment textAlign = StringAlignment.NEAR;
    private Cursor cursor = Cursor.DEFAULT;
    private AnnotationCallout callout;
    private boolean customSize;
    private boolean cliptext = true;
    private PositionUnits positionUnits;

    public Annotation() {
        this((IBaseChart) null);
    }

    public Annotation(IBaseChart c) {
        super(c);
        getShape().setDrawText(false);
        shape.getShadow().setVisible(true);
        getCallout().setChart(chart);
        positionUnits = com.steema.teechart.PositionUnits.PIXELS;
        shape.setCustomPosition(false);
        shape.drawText = false;
    }

    public Annotation(IBaseChart c, String text) {
        this(c);
        setText(text);
    }

    public Annotation(String text) {
        this(null, text);
    }

    public void addToolMouseListener(ToolMouseListener l) {
        listenerList.add(ToolMouseListener.class, l);
    }

    public void removeToolMouseListener(ToolMouseListener l) {
        listenerList.remove(ToolMouseListener.class, l);
    }

    public AnnotationCallout getCallout() {
        if (callout == null) {
            callout = new AnnotationCallout(null);
        }
        return callout;
    }

    /**
     * The Cursor type for when the user moves the mouse over the
     * Annotation bounds. <br>
     * Default value: Default
     *
     * @return Cursor
     */
    public Cursor getCursor() {
        return cursor;
    }

    /**
     * Selects the Cursor type for when the user moves the mouse over the
     * Annotation bounds. <br>
     * Default value: Default
     *
     * @param value Cursor
     */
    public void setCursor(Cursor value) {
        cursor = value;
    }

    public void setChart(IBaseChart c) {
        super.setChart(c);
        getShape().setChart(chart);
        getCallout().setChart(chart);
    }
    
    /**
     * Horizontal alignment of displayed text.<br>
     * Default value: StringAlignment.Near
     *
     * @return StringAlignment
     */
    public StringAlignment getTextAlign() {
        return textAlign;
    }

    /**
     * Sets the horizontal alignment of displayed text.<br>
     * Default value: StringAlignment.Near
     *
     * @param value StringAlignment
     */
    public void setTextAlign(StringAlignment value) {
        if (textAlign != value) {
            textAlign = value;
            invalidate();
        }
    }

    //private boolean ShouldSerializeLeft() {
    //    return shape.getCustomPosition();
    //}
    /**
     * The horizontal displacement in pixels of text box from Chart's left
     * edge.
     *
     * @return int
     */
    public int getLeft() {
        return shape.getLeft();
    }

    /**
     * Sets horizontal displacement in pixels of text box from Chart's left
     * edge.
     *
     * @param value int
     */
    public void setLeft(int value) {
        shape.setLeft(value);
    }

    //private boolean ShouldSerializeTop() {
    //    return shape.getCustomPosition();
    //}
    /**
     * The vertical displacement in pixels of text box from Chart's top edge.
     *
     * @return int
     */
    public int getTop() {
        return shape.getTop();
    }

    /**
     * Sets vertical displacement in pixels of text box from Chart's top edge.
     *
     * @param value int
     */
    public void setTop(int value) {
        shape.setTop(value);
    }

    /**
     * The default position for the Annotation Tool text box and text.
     * <br>
     * Default value: AnnotationPosition.LeftTop
     *
     *
     * @return AnnotationPosition
     */
    public AnnotationPosition getPosition() {
        return position;
    }

    /**
     * Defines a default position for the Annotation Tool text box and text.
     * <br>
     * Default value: AnnotationPosition.LeftTop
     *
     *
     * @param value AnnotationPosition
     */
    public void setPosition(AnnotationPosition value) {
        if (position != value) {
            position = value;
            shape.setCustomPosition(false);
            invalidate();
        }
    }

    /**
     * The characteristics of the Annotation Tool text and text box Shape.
     *
     * @return TextShapePosition
     */
    public TextShapePosition getShape() {
        if (shape == null) {
            shape = new TextShapePosition(chart);
        }
        return shape;
    }

    protected String getInnerText() {
        return shape.getText();
    }

    /**
     * The text for the Annotation Tool.<br>
     * Default value: ""
     *
     * @return String
     */
    public String getText() {
        return getInnerText();
    }

    /**
     * Defines the text for the Annotation Tool.<br>
     * Default value: ""
     *
     * @param value String
     */
    public void setText(String value) {
        getShape().setText(value);
    }

    /**
     * Gets descriptive text.
     *
     * @return String
     */
    public String getDescription() {
        return Language.getString("AnnotationTool");
    }

    /**
     * Gets detailed descriptive text.
     *
     * @return String
     */
    public String getSummary() {
        return Language.getString("AnnotationSummary");
    }

    /**
     * Returns the annotation height in pixels
     * @return int
     */
    public int getHeight() {
        return shape.getHeight();
    }

    /**
     * Sets the annotation height in pixels
     * @param value int
     */
    public void setHeight(int value) {
        customSize = true;
        shape.setHeight(value);
    }

    /**
     * Returns the annotation width in pixels
     * @return int
     */
    public int getWidth() {
        return shape.getWidth();
    }

    /**
     * Sets the annotation width in pixels
     * @param value int
     */
    public void setWidth(int value) {
        customSize = true;
        shape.setWidth(value);
    }

    /// Set to true to permit custom sizing of TextShape.
    public boolean getCustomSize() {
        return customSize;
    }

    public void setCustomSize(boolean value) {
        customSize = value;
    }

    void drawString(Graphics3D g, int x, int y, int t, int tmpHeight, String[] s) {
        g.textOut(x, y + (t - 1) * tmpHeight, s[t - 1]);
    }

    protected MultiLine calcTempWidth(Graphics3D g, String tmp) 
    {
        MultiLine aux = g.getChart().multiLineTextWidth(tmp);
        return aux;
    }

    private Rectangle getTextBounds(Graphics3D g,int[] bounds ) {
        String tmp = getText();
        int tmpH;
        int tmpHOk;
        int tmpWOk;
        int tmpPen;
        int horizMargin = 4;
        int vertMargin = 2;
        int tmpHeight;
        int tmpW;
        int NumLines=0;
        int TextWidth=0;
        int x=0;
        int y=0;
        if (tmp.length() == 0) {
            tmp = " ";  // at least one space character... 
        }
        g.setFont(shape.getFont());

        tmpHeight = g.getFontHeight();
        MultiLine aux = calcTempWidth(g, tmp);
        tmpW = aux.width;
        NumLines = aux.count;
        bounds[0] = NumLines;
        tmpH = bounds[0] * tmpHeight;
        tmpPen = (shape.getPen().getVisible()) ? (1 + shape.getPen().getWidth()) / 2 : 0;

        tmpH += tmpPen;
        tmpW += tmpPen;


        if (shape.getCustomPosition()) {
            if (this.positionUnits == com.steema.teechart.PositionUnits.PIXELS)
            {
                x = shape.getShapeBounds().getLeft() + horizMargin;
                y = shape.getShapeBounds().getTop() + vertMargin;
            } 
            else
            {
                x = Utils.round(shape.getShapeBounds().getLeft() * 0.01F * this.getChart().getWidth());
                y = Utils.round(shape.getShapeBounds().getTop() * 0.01F * this.getChart().getHeight());
            }

        } 
        else {
            int[] xyPosition = new int[2];
            calcTempPosition(xyPosition, tmpW, tmpH, horizMargin, vertMargin);
            x= xyPosition[0];
            y= xyPosition[1];
        }

        if (customSize) {
            tmpWOk = shape.getWidth();
            tmpHOk = shape.getHeight();
        } else {
            tmpWOk = tmpW + horizMargin + 4;
            tmpHOk = tmpH + vertMargin + 2;
        }

        TextWidth = tmpW;
        bounds[1]=TextWidth;
        bounds[2]=x;
        bounds[3]=y;
        return Rectangle.fromLTRB(x - horizMargin, y - vertMargin, x - horizMargin + tmpWOk,y - vertMargin + tmpHOk);
    }

    protected void calcTempPosition(int[] xyPosition, int tmpW, int tmpH, int horizMargin, int vertMargin) {
        int XPadding = 10;
        int YPadding = 10;
        
        int tmpX = chart.getWidth() - tmpW - 2 * horizMargin;
        int tmpY = chart.getHeight() - tmpH - 4 * vertMargin;

        if (position.equals(AnnotationPosition.LEFTTOP)) {
            xyPosition[0] = XPadding;
            xyPosition[1] = YPadding;
        } else if (position.equals(AnnotationPosition.LEFTBOTTOM)) {
            xyPosition[0] = XPadding;
            xyPosition[1] = tmpY-YPadding;
        } else if (position.equals(AnnotationPosition.RIGHTTOP)) {
            xyPosition[0]= tmpX-XPadding;
            xyPosition[1] = YPadding;
        } else if (position.equals(AnnotationPosition.RIGHTBOTTOM)) {
            xyPosition[0] = tmpX-XPadding;
            xyPosition[1] = tmpY-YPadding;
        } else { //AnnotationPosition.CENTER
            xyPosition[0] = tmpX / 2;
            xyPosition[1] = tmpY / 2;
        }
    }

    public void drawText(Graphics3D g)
    {
        
        String tmp = getText();

        Rectangle OldBounds;

        g.setFont(getShape().getFont());
        
        int tmpHeight = g.getFontHeight();
        int tmpN = 0, tmpW = 0, x = 0, y = 0;
        
        if (tmp.length() == 0) {
            tmp = " ";  // at least one space character... 
        }
        int[] bounds = new int[4];
        Rectangle tmpR = getTextBounds(g,bounds);
        tmpW = bounds[1];
        x=bounds[2];
        y=bounds[3];
        tmpN = bounds[0];
        OldBounds = (Rectangle)shape.getShapeBounds().copy();
        shape.setShapeBounds(tmpR);
        if (shape.getVisible()) {
            shape.paint(g, tmpR);
        }

        int tmpMargin = Utils.round(g.textWidth("W"));

        if (textAlign.equals(StringAlignment.NEAR)) {
            x = shape.getShapeBounds().getLeft() + 2;
        } else if (textAlign.equals(StringAlignment.FAR)) {
            x = shape.getShapeBounds().getRight() - tmpW - Utils.round(tmpMargin / g.getFont().getSize());
        } else if (textAlign.equals(StringAlignment.CENTER)) {
            x = Utils.round((shape.getShapeBounds().getLeft() + shape.getShapeBounds().getRight()) / 2) - Utils.round(tmpW / 2);
        }


         String[] s = tmp.split(Language.lineSeparator);
         for (int t = 1; t <= tmpN; t++)
         {
             drawString(g, x, y, t, tmpHeight, s);
         }


        //Callout
        if (callout.getVisible() || callout.getArrow().getVisible()) {
            Point tmpTo = new Point(callout.getXPosition(), callout.getYPosition());
            Point tmpFrom = callout.closerPoint(shape.getShapeBounds(), tmpTo);

            if (callout.getDistance() != 0) {
                tmpTo = Utils.pointAtDistance(tmpFrom, tmpTo, callout.getDistance());
            }
            callout.draw(Color.EMPTY, tmpFrom, tmpTo, callout.getZPosition());
        }

        if (customSize)
        {
            shape.setShapeBounds(OldBounds);
        } 
        else 
        {
            x = shape.getShapeBounds().getRight() - shape.getShapeBounds().getLeft();
            y = shape.getShapeBounds().getBottom() - shape.getShapeBounds().getTop();
            tmpR = Rectangle.fromLTRB(OldBounds.getLeft(), OldBounds.getTop(), OldBounds.getLeft() + x, OldBounds.getTop() + y);
            shape.setShapeBounds(tmpR);
        }
    }
    
    public void drawText() {
        drawText((Graphics3D)this.getChart().getGraphics3D());
    }

    /*public void drawText() {
        String tmp = getText();

        if (tmp.length() == 0) {
            tmp = " "; // at least one space character...
        }

        IGraphics3D g = chart.getGraphics3D();

        g.setFont(shape.getFont());
        int tmpHeight = g.getFontHeight();

        MultiLine m = chart.multiLineTextWidth(tmp);
        int tmpW = m.width;
        int tmpN = m.count;
        int tmpH = tmpN * tmpHeight;

        int x = 0;
        int y = 0;

        if (shape.getCustomPosition()) {
            x = shape.getShapeBounds().getLeft() + 4;
            y = shape.getShapeBounds().getTop() + 2;
        } else {
            //int tmpX = chart.getWidth() - tmpW - 20; // - 8;
            //int tmpY = chart.getHeight() - tmpH - 20; // - 8;
            int tmpX = (int)chart.getChartBounds().getWidth()
                       - chart.getPanel().getBevelWidth() - tmpW - 8;
            int tmpY = (int)chart.getChartRect().getHeight()
                       - chart.getPanel().getBevelWidth() - tmpH - 8;


            if (position == AnnotationPosition.LEFTTOP) {
                x = 10;
                y = 10;
            } else if (position == AnnotationPosition.LEFTBOTTOM) {
                x = 10;
                y = tmpY;
            } else if (position == AnnotationPosition.RIGHTTOP) {
                x = tmpX;
                y = 10;
            } else {
                x = tmpX;
                y = tmpY;
            }
        }

        if (cliptext) {
            g.unClip();
        }

        shape.setShapeBounds(new Rectangle(x - 4, y - 2, x + tmpW + 2 - (x - 4),
                y + tmpH + 2 - (y - 2)));

        if (shape.getVisible()) {
            shape.paint();
        }

        g.getBrush().setVisible(false);

        g.setTextAlign(textAlign);

        if (textAlign == StringAlignment.CENTER) {
            x = 2 +
                    ((shape.getShapeBounds().x + shape.getShapeBounds().getRight()) /
                    2);
        } else if (textAlign == StringAlignment.FAR) {
            x = shape.getShapeBounds().getRight() - 2;
        }

        String[] s = StringFormat.split(tmp, Language.lineSeparator);

        for (int t = 1; t <= tmpN; t++) {
            //g.textOut(x, y + tmpHeight + ((t - 1) * tmpHeight), s[t - 1]);
            g.textOut(x, y + ((t - 1) * tmpHeight), s[t - 1]);
        }

        // Draw callout
        if (callout.getVisible() || callout.getArrow().getVisible()) {
            Point tmpTo = new Point(callout.getXPosition(),
                    callout.getYPosition());
            Point tmpFrom = callout.closerPoint(shape.getShapeBounds(), tmpTo);

            if (callout.getDistance() != 0) {
                tmpTo = MathUtils.pointAtDistance(tmpFrom, tmpTo,
                        callout.getDistance());
            }

            callout.draw(Color.EMPTY, tmpTo, tmpFrom, callout.getZPosition());
        }
    }*/

    public void chartEvent(ChartDrawEvent e) {
        super.chartEvent(e);
        if ((e.getID() == ChartDrawEvent.PAINTED) && (e.getDrawPart() == ChartDrawEvent.CHART)) {
            drawText();
        }
    }

    /**
     * Returns true is point parameter is inside annotation bounds
     * @param p Point
     * @return boolean
     */
    public boolean clicked(Point p) {
        return getShape().getShapeBounds().contains(p);
    }

    public Cursor mouseEvent(FrameworkMouseEvent e, Cursor c) {
        if (e.getID() == FrameworkMouseEvent.MOUSE_PRESSED) {
            if (clicked(e.getPoint())) {
                e.widget = null; //mm todo
                e.setSource(this);
                fireClicked(e);
            }
        } else {
            if ((e.getID() == FrameworkMouseEvent.MOUSE_MOVED)/* && //mm
                    (cursor != getCursor())*/) {
                if (clicked(e.getPoint())) {
                    c = cursor;
                }
            }
        }

        return c;
    }
}

