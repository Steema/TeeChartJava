package com.steema.teechart;

import com.steema.teechart.drawing.Point;
import com.steema.teechart.drawing.StringAlignment;
import com.steema.teechart.drawing.Color;
import com.steema.teechart.drawing.IGraphics3D;
import com.steema.teechart.misc.Utils;

/**
 *
 * <p>Title: Title class</p>
 *
 * <p>Description: Underlying Title characteristics.</p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights
 * Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public class Title extends TextShapePosition {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// fixed pixels distance between title/foot and chart
    transient private static final int TITLEFOOTDISTANCE = 5;

    private boolean adjustFrame = true;
    private StringAlignment alignment = StringAlignment.CENTER;

    transient protected boolean onTop = true;

    transient private int fontH, tmpXPosTitle, tmpFrameWidth, tmpMargin;

    public Title(IBaseChart c) {
        super(c);
        getBrush().setDefaultColor(Color.SILVER);
        drawText = false;
        bTransparent = true;
    }

    protected Object readResolve() {
        onTop=true;
        return this;
    }

    /**
     * Resizes Header and Footer frames to full Chart dimensions when true.<br>
     * When false it resizes to the Title text width. It only has effect when
     * Chart.Header or Chart.Footer.Visible is true.<br>
     * Default value: true
     *
     * @return boolean
     */
    public boolean getAdjustFrame() {
        return adjustFrame;
    }

    /**
     * Resizes Header and Footer frames to full Chart dimensions when true.<br>
     * When false it resizes to the Title text width. It only has effect when
     * Chart.Header or Chart.Footer.Visible is true.<br>
     * Default value: true
     *
     * @param value boolean
     */
    public void setAdjustFrame(boolean value) {
        adjustFrame = setBooleanProperty(adjustFrame, value);
    }

    /**
     * Determines how tChart Header and Footer text will be aligned within the
     * Chart rectangle. <br>
     * The Header or Footer can optionally be surrounded by a Frame. <br>
     * Default value: Center
     *
     * @return StringAlignment
     */
    public StringAlignment getAlignment() {
        return alignment;
    }

    /**
     * Sets how tChart Header and Footer text will be aligned within the
     * Chart rectangle. <br>
     * The Header or Footer can optionally be surrounded by a Frame. <br>
     * Default value: Center
     *
     * @param value StringAlignment
     */
    public void setAlignment(StringAlignment value) {
        if (alignment != value) {
            alignment = value;
            invalidate();
        }
    }

    /*
     DB: Not necessary ??

        /// <summary>
        /// Gets/Sets Left position of Header or Footer Text.
        /// </summary>
        //[Description("Gets/Sets Left position of Header or Footer Text.")]
        public new int Left
        {
          get { return super.Left; }
          set { super.Left=value; }
        }


        /// <summary>
        /// Gets/Sets Top position of Header or Footer Text.
        /// </summary>
        //[Description("Gets/Sets Top position of Header or Footer Text.")]
        public new int Top
        {
          get { return super.Top; }
          set { super.Top=value; }
        }
     */

    /* draw a title text line */
    private void drawTitleLine(int aIndex, IGraphics3D g) {
        String st = getLines()[aIndex];

        if (st != null) {
            int aPos = aIndex * fontH + tmpFrameWidth / 2;

            if (onTop) {
                aPos += getShapeBounds().getTop();
            } else {
                aPos = getShapeBounds().getBottom() - (fontH + aPos);
            }
            if (alignment == StringAlignment.FAR) {
                tmpXPosTitle = shapeBounds.getRight() -
                               Utils.round(g.textWidth(st)) -
                               (tmpMargin / 2);
            } else
            if (alignment == StringAlignment.CENTER) {
                tmpXPosTitle = Utils.round(((shapeBounds.x +
                                            shapeBounds.getRight()) / 2) -
                                          (g.textWidth(st) / 2));
            }

            g.textOut(tmpXPosTitle, aPos, st);
        }
    }

    /**
     * Returns if mouse cursor is inside TChartTitle bound rectangle.<br><br>
     * The Title.Visible property must be true. <br>
     * The Title rectangle size depends on Title.Pen.Visible and
     * Title.AdjustFrame.
     *
     * @param p Point
     * @return boolean
     */
    public boolean clicked(Point p) {
        return visible && getShapeBounds().contains(p.x, p.y);
    }

    /**
     * Returns if mouse cursor is inside TChartTitle bound rectangle.<br><br>
     * The Title.Visible property must be true. <br>
     * The Title rectangle size depends on Title.Pen.Visible and
     * Title.AdjustFrame.
     *
     * @param x int
     * @param y int
     * @return boolean
     */
    public boolean clicked(int x, int y) {
        return visible && getShapeBounds().contains(x, y);
    }

    protected Rectangle doDraw(IGraphics3D g, Rectangle rect,
                               boolean customOnly) {
        if (bCustomPosition == customOnly) {
            return draw(g, rect);
        } else {
            return rect;
        }
    }

    protected Rectangle draw(IGraphics3D g, Rectangle rect) {

        int linesCount = getLinesLength();

        if (visible && (linesCount > 0)) {

            // calculate title shape margin
            boolean tmpFrameVisible = getPen().getVisible() &&
                                      (!getPen().getColor().isEmpty());
            if (tmpFrameVisible) {
                tmpFrameWidth = getPen().getWidth();
            } else {
                tmpFrameWidth = 0;
            }

            if (getBevel().getInner() != BevelStyle.NONE) {
                tmpFrameWidth += getBevel().getWidth();
            }

            // apply title margins
            if (!bCustomPosition) {
                setShapeBounds(rect);
                if (onTop) {
                    shapeBounds.y += tmpFrameWidth;
                }
            }

            // prepare title font
            g.setFont(getFont());
            g.setTextAlign(StringAlignment.NEAR);
            fontH = g.getFontHeight();

            // autosize title height on number of text lines
            if (onTop || bCustomPosition) {
                shapeBounds.height = linesCount * fontH + tmpFrameWidth;
            } else {
                int old = shapeBounds.getBottom();
                shapeBounds.height= linesCount * fontH + tmpFrameWidth;
                shapeBounds.y = old - shapeBounds.height;
            }

            tmpMargin = Utils.round(g.textWidth("W"));

            int tmp = 0;

            // resize Title to maximum Chart width
            if (adjustFrame) {

                int tmpMaxWidth = 0;

                for (int t = 0; t < linesCount; t++) {
                    tmp = Utils.round(g.textWidth(getLines()[t]));

                    if (tmp > tmpMaxWidth) {
                        tmpMaxWidth = tmp;
                    }
                }

                tmpMaxWidth += tmpMargin + tmpFrameWidth;

                if (alignment == StringAlignment.NEAR) {
                    shapeBounds.width = tmpMaxWidth;
                } else
                if (alignment == StringAlignment.FAR) {
                    shapeBounds.x = shapeBounds.getRight() - tmpMaxWidth;
                    shapeBounds.width = tmpMaxWidth;
                } else {
                    if (bCustomPosition) {
                        shapeBounds.width = tmpMaxWidth;
                    }
                    tmp = (shapeBounds.x + shapeBounds.getRight()) / 2;
                    shapeBounds.x = tmp - (tmpMaxWidth / 2);
                    shapeBounds.width = tmpMaxWidth;
                }
            }

            // draw title shape
            super.paint(g, getShapeBounds());

            if (alignment == StringAlignment.NEAR) {
                tmpXPosTitle = getShapeBounds().getLeft() + (tmpMargin / 2);
            }

            // draw all Title text lines
            for (int t = 0; t < linesCount; t++) {
                drawTitleLine(t, g);
            }

            // calculate Chart positions after drawing the titles / footers
            if (!bCustomPosition) {
                tmp = TITLEFOOTDISTANCE + tmpFrameWidth;

                if ((!getTransparent()) && getShadow().getVisible()) {
                    tmp += getShadow().getHeight();
                }

                if (onTop) {
                    int tmpY = rect.y;
                    rect.y = shapeBounds.getBottom() + tmp;
                    rect.height -= (rect.y - tmpY);
                } else {
                    rect.height -= (tmp + linesCount * fontH);
                }
            }
        }

        return chart.recalcWidthHeight(rect); // DB
    }
}

