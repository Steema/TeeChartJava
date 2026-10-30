package com.steema.teechart;

import com.steema.teechart.drawing.ChartFont;
import com.steema.teechart.drawing.Graphics3D;
import com.steema.teechart.languages.Language;
import com.steema.teechart.drawing.IGraphics3D;
import com.steema.teechart.drawing.StringAlignment;
import com.steema.teechart.misc.StringFormat;

/**
 *
 * <p>Title: TextShape class</p>
 *
 * <p>Description: Base class for Chart shape elements with text</p>
 *
 * <p>Copyright (c)
 * 2005-2019 by Steema Software SL. All Rights Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public class TextShape extends Shape {
	
	private static final long serialVersionUID = 1L;

    private static final int DEFAULTROUNDSIZE = 16;
    transient public boolean drawText = true;
    private boolean cliptext = true;
    transient protected String defaultText = "";
    private StringAlignment textAlign = StringAlignment.NEAR;
    private String[] lines;
    private TextShapeStyle shapeStyle = TextShapeStyle.RECTANGLE;
    private ChartFont font;
    private TextFormat textFormat = TextFormat.NORMAL;
    private boolean autosize = true;
    public TextShape() {
        this((IBaseChart) null);
    }

    public TextShape(IBaseChart c) {
        super(c);
        readResolve();
    }

    protected Object readResolve() {
        drawText = true;
        defaultText = "";
        return this;
    }

    public void setChart(IBaseChart c) {
        super.setChart(c);
        if (font != null) {
            font.setChart(chart);
        }
    }

    /**
     * Shape may be rectagular or rounded rectangular in shape. <br>
     * Default value: TextShapeStyle.Rectangle
     *
     * @return TextShapeStyle
     */
    public TextShapeStyle getShapeStyle() {
        return shapeStyle;
    }

    /**
     * 
     * 
     * @param value
     */
    public void setDrawText(boolean value) {
        drawText = value;
    }

    /**
     * Shape may be rectagular or rounded rectangular in shape. <br>
     * Default value: TextShapeStyle.Rectangle
     *
     * @param value TextShapeStyle
     */
    public void setShapeStyle(TextShapeStyle value) {
        if (shapeStyle != value) {
            shapeStyle = value;
            invalidate();
        }
    }

    /**
     * Determines if Text is drawn as Normal or HTML styles.
     * Default value: TextFormat.Normal
     */
    public TextFormat getTextFormat() {
        return textFormat;
    }

    public void setTextFormat(TextFormat value) {
        if (textFormat != value) {
            textFormat = value;
        }
        invalidate();
    }

    /**
     * Obsolete.&nbsp;Please use Shadow.<!-- -->Size.
     *
     * @return int
     */
    public int getShadowSize() {
        return getShadow().getWidth();
    }

    /**
     * Obsolete.&nbsp;Please use Shadow.<!-- -->Size.
     *
     * @param value int
     */
    public void setShadowSize(int value) {
        getShadow().setWidth(value);
    }

    //protected boolean ShouldSerializeLines() {
    //    return getText() != defaultText;
    //}
    protected int getLinesLength() {
        return (lines == null) ? 0 : lines.length;
    }

    static private String stringJoin(String separator, String[] source) {
        String result = "";
        for (int t = 0; t < source.length; t++) {
            result += source[t] + separator;
        }
        return result;
    }

    /**
     * Displays customized strings inside Shapes. <br>
     * You can use Font and Aligment to control Text display.  <br><br>
     * Note: You would maybe need to change Shape Font size to a different
     * value when creating metafiles or when zooming Charts.
     *
     * @return String
     */
    public String getText() {
        return (getLinesLength() == 0) ? "" : stringJoin(Language.lineSeparator, lines);
    }

    /**
     * Displays customized strings inside Shapes. <br>
     *
     * @param value String
     */
    public void setText(final String value) {
        if (!getText().equals(value)) {
            lines = null;

            // one separator can be single char, so use a trick and replace \r\n with \n
            String istr = value.replaceAll(Language.getString("crlf"), Language.lineSeparator);
            lines = StringFormat.split(istr, Language.lineSeparator);
            invalidate();
        }
    }

    /**
     * Accesses the array of Text lines.<br>
     * Use lines to add multiline text to TeeChart's text objects
     * (TeeChart Header, TeeChart Axis Titles etc.). <br>
     * Default value: null
     *
     * @return String[]
     */
    public String[] getLines() {
        return lines;
    }

    /**
     * Accesses the array of Text lines.<br>
     * Default value: null
    
     * @param value String[]
     */
    public void setLines(String[] value) {
        lines = value;
        invalidate();
    }

    /**
     * Determines the font attributes used to output
     * ShapeSeries.<!-- -->Text Strings.
     *
     * @return ChartFont
     */
    public ChartFont getFont() {
        if (font == null) {
            font = new ChartFont(chart);
        }
        return font;
    }

    /**
     * Assign all properties from a TextShape to another.
     *
     * @param s TextShape
     */
    public void assign(TextShape s) {
        if (s != null) {
            super.assign(s);

            if (s.font != null) {
                getFont().assign(s.font);
            }
            lines = s.lines;
            shapeStyle = s.shapeStyle;
             textAlign = s.textAlign;
        }
    }

    /**
     * Paints the TextShape object on the Chart Canvas.
     */
    public void paint() {
        paint(chart.getGraphics3D(), getShapeBounds());
    }

    public void paint(Graphics3D g, Rectangle rect) {
        this.drawText(g, rect);
    }

    protected void drawText(Graphics3D g, Rectangle rect) {

        if (bBevel != null) {
            if (bBevel.inner != BevelStyle.NONE) {
                rect.inflate(1, 1);
            }
            if (bBevel.outer != BevelStyle.NONE) {
                rect.inflate(1, 1);
            }
        }

        if (shapeStyle.equals(TextShapeStyle.RECTANGLE)) {
            setBorderRound(0);
        } else if (shapeStyle.equals(TextShapeStyle.ROUNDRECTANGLE)) {
            setBorderRound(8);
        }

        // Draw shape
        super.paint(g, rect);

        // If needed, draw text
        if ((drawText) && (lines != null)) {
            g.setFont(getFont());
            int fh = g.getFontHeight();
            String tmp = getText();
            MultiLine aux = chart.multiLineTextWidth(tmp);
            int x;
            int numLines = aux.count;
            int tw = aux.width;


            if (textAlign.equals(StringAlignment.FAR)) {
                x = rect.getRight() - tw - 2;
            } else if (textAlign.equals(StringAlignment.CENTER)) {
                x = (rect.getLeft() + rect.getRight() - tw - 2) / 2;
            } else {
                x = rect.x + 2;
            }
            if (cliptext) {
                g.clipRectangle(rect);
            }

            for (int t = 0; t < numLines; t++) {
                drawString(g, x, rect.y + 2, t, fh, lines);
            }

            if (cliptext) {
                g.unClip();
            }
        }
    }

    protected void drawString(Graphics3D g, int x, int y, int t, int tmpHeight, String[] s) {

        g.textOut(x, y + t * tmpHeight, s[t]);

    }

    /**
     * Paints the TextShape object on the Chart Canvas.
     *
     * @param g IGraphics3D
     * @param rect Rectangle
     */
    public void paint(IGraphics3D g, Rectangle rect) {
        String tmpText = getText();

        if (drawText && (lines != null)) {
            g.setFont(font);

            int tmpW = g.textWidth(tmpText);
            int x = rect.getRight();

            rect.width = tmpW;
            rect.height = g.textHeight(tmpText);

            rect.x = ((rect.x + x) / 2) - tmpW;
        }

        if (bBevel != null) {
            if (bBevel.inner != BevelStyle.NONE) {
                rect.grow(1, 1);
            }
            if (bBevel.outer != BevelStyle.NONE) {
                rect.grow(1, 1);
            }
        }

        //CDI Fix for TextShapeStyle
        if (shapeStyle == TextShapeStyle.RECTANGLE) {
            setBorderRound(0);
        } else if (shapeStyle == TextShapeStyle.ROUNDRECTANGLE) {
            setBorderRound(8);
        }

        // super.paint(g, rect);  Not valid here !!!

        drawRectRotated(g, rect, 0, 0);

        if (drawText && (lines != null)) {
            g.textOut(rect.x, rect.y + 2, tmpText);
        }
    }

    private void internalDrawShape(IGraphics3D g, Rectangle aRect,
            int defaultRoundSize, int angle, int aZ) {
        if (angle > 0) {
            g.polygon(aZ, g.rotateRectangle(aRect, angle));
        } else {
            if (g.getSupportsFullRotation()) {
                g.rectangle(aRect, aZ);
            } else {
                if (shapeStyle == TextShapeStyle.RECTANGLE) {
                    g.rectangle(aRect);
                } else {
                    g.roundRectangle(aRect, defaultRoundSize, defaultRoundSize);
                }
            }
        }
    }

    /**
     * Draws the Shape rectangle rotated by Angle degrees.
     *
     * @param g IGraphics3D
     * @param rect Rectangle
     * @param angle int
     * @param aZ int
     */
    public void drawRectRotated(IGraphics3D g, Rectangle rect, int angle,
            int aZ) {
        if (!bTransparent) {
            if (getShadow().getVisible() && getBrush().getVisible()) {
                if (!g.getSupportsFullRotation()) {
                    switch (getShapeStyle().getValue()) {
                        case 0: //TextShapeStyle.RECTANGLE
                            shadow.draw(g, rect, angle, aZ, false); // internaldrawshape !
                            break;
                        default: //TextShapeStyle.ROUNDRECTANGLE
                            shadow.draw(g, rect, getBorderRound(), angle, aZ, false); // internaldrawshape !
                            break;
                    }
                    
                }
            }

            if ((getBrush().getGradientVisible()) && (angle == 0)) {
                // int tmp;
                // if ( shapeStyle==ChartObjectShapeStyle.RoundRectangle )
                //   tmp=defaultRoundSize;
                // else
                //   tmp=0;
                //MM pending RoundRectSize in Gradient (tmp)

                getGradient().draw(g, rect);

                g.getBrush().setVisible(false);
            } else {
                g.setBrush(getBrush());
            }

            g.setPen(getPen());

            internalDrawShape(g, rect, getBorderRound(), angle, aZ);
        }

        if (bBevel != null) {
            bBevel.draw(g, rect);
        }
    }

    public boolean getAutoSize()
    {
        return autosize;
    }
    public void setAutoSize(boolean value)
    {
        if (autosize != value)
        {
          autosize = value;
          invalidate();
        }
    }
}

