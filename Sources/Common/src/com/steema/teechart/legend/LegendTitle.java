package com.steema.teechart.legend;

import com.steema.teechart.drawing.IGraphics3D;
import com.steema.teechart.misc.Utils;
import com.steema.teechart.TextShape;
import com.steema.teechart.TextFormat;
import com.steema.teechart.drawing.StringAlignment;
import com.steema.teechart.IBaseChart;
import com.steema.teechart.Rectangle;
import com.steema.teechart.drawing.Color;;


/**
 *
 * <p>Title: LegendTitle class</p>
 *
 * <p>Description: Legend title characteristics</p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights
 * Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */

public class LegendTitle extends TextShape {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private StringAlignment textAlign=StringAlignment.CENTER;
    private int tmpFrameWidth;
    private int tmpXPosTitle;
    private int tmpMargin;


    public LegendTitle(IBaseChart c) {
            super.setChart(c);

            getFont().setColor(Color.BLACK);
            getFont().setBold(true);
            getFont().getBrush().setDefaultColor(Color.BLACK);
            getPen().setVisible(false);
    }

    // Determines if text is displayed at left, center or right side of chart legend title.
    // Description("Horizontal alignment of displayed text.")

    public StringAlignment getTextAlign()  {
        return textAlign;
    }

    public void setTextAlign(StringAlignment value) {
        if (textAlign!=value) {
            textAlign=value;
            invalidate();
        }
    }

    public void calcHeight() {
        getChart().getGraphics3D().getFont().assign(getFont());
        setHeight(Utils.round(getChart().getGraphics3D().textHeight("W") * getLines().length));

        if(!getTransparent()) {
            setHeight(getHeight()+2);
            if (getPen().getVisible())
                setHeight(getHeight()+(2 * getPen().getWidth()));
            }
    }

    private int FontH;

    public void drawLineTitle(int AIndex) {
        String St;
        int APos;

        St=getLines()[AIndex];
        APos=getShapeBounds().getTop();
        APos+=(AIndex * FontH) + tmpFrameWidth;

        if (textAlign == StringAlignment.FAR) {
            tmpXPosTitle = getShapeBounds().getRight() - Utils.round(getChart().getGraphics3D().textWidth(St)) - (tmpMargin / 2);
        }
        else
        if (textAlign == StringAlignment.CENTER) {
            tmpXPosTitle = Utils.round((getShapeBounds().getLeft()+getShapeBounds().getRight()) / 2) - Utils.round(getChart().getGraphics3D().textWidth(St) / 2);
        }

        getChart().getGraphics3D().textOut(tmpXPosTitle,APos,St);
    }

    public void drawText() {
        if(getPen().getVisible())
            tmpFrameWidth=getPen().getWidth();
        else
            tmpFrameWidth = 1;

        tmpMargin = Utils.round(getChart().getGraphics3D().textWidth("W"));
        FontH = Utils.round(getChart().getGraphics3D().textHeight("W"));

        if (textAlign == StringAlignment.NEAR) {
            tmpXPosTitle=getShapeBounds().getLeft() + (tmpMargin/2);
        }

        if (getTextFormat() == TextFormat.NORMAL) {
            for(int t=0; t < getLines().length; ++t) {
                drawLineTitle(t);
            }
        } else {
            getChart().getGraphics3D().setTextAlign(textAlign);
            getChart().getGraphics3D().textOut(tmpXPosTitle,tmpFrameWidth+getShapeBounds().getTop(),getText());
        }
    }

    public void internalDraw(IGraphics3D g, Rectangle Rect) {
        calcShapeBounds(Rect);
        drawRectRotated(g, getShapeBounds(), 0, 0);
        drawText();
    }

    public int getTotalWidth() {
        getChart().getGraphics3D().getFont().assign(getFont());
        int result = 0;
        for(int t=0; t < getLines().length; ++t) {
            result=Math.max(result,Utils.round(getChart().getGraphics3D().textWidth(getLines()[t])));
        }
        result=result+Utils.round(getChart().getGraphics3D().textWidth("W"));

        if(!getTransparent()) {
            if(getPen().getVisible())
                result += getPen().getWidth() * 2;
            if(getShadow().getVisible())
                result += getShadow().getWidth();
        }

        return result;
    }

    private void calcShapeBounds(Rectangle R) {
        setShapeBounds(Rectangle.fromLTRB(R.getLeft()+2, R.getTop() +2, R.getRight()-2,R.getTop()+4+ getHeight()));
        if(!getTransparent() && getShadow().getVisible()) {
            if(getShadow().getWidth() > 0) {
                setRight(getRight()-getShadow().getWidth());
            } else {
                shapeBounds.x-=getShadow().getWidth();
            }

            if(getShadow().getHeight() < 0) {
                shapeBounds.y-=getShadow().getHeight();
            }
        }
    }
}

