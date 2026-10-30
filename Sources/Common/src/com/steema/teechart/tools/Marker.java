/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.steema.teechart.tools;

import com.steema.teechart.Chart;
import com.steema.teechart.IBaseChart;
import com.steema.teechart.Rectangle;
import com.steema.teechart.drawing.Color;
import com.steema.teechart.drawing.Graphics3D;
import com.steema.teechart.drawing.IGraphics3D;
import com.steema.teechart.drawing.StringAlignment;

/**
 *
 * @author Administrator
 */
public class Marker extends Annotation {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public Rectangle iRectangle;
    private boolean usePalette = false;
    private boolean centered;
    private boolean visible = true;
    private String textAuxiliar;
    private double barfontSpace = 0.4;
    private double dotfontSpace = 1.0 / 3.0;
    private double textWidth = 0.8;

    public Marker(String text, int fontsize, AnnotationPosition pos, StringAlignment align, Color color1, Color color2) {
        this((Chart) null, text, fontsize, pos, align, color1, color2);
    }

    public Marker() {
        this((Chart) null, "", 11, AnnotationPosition.LEFTBOTTOM, StringAlignment.CENTER, Color.BLACK, Color.WHITE);

    }

    public Marker(IBaseChart c) {
        this(c, "", 11, AnnotationPosition.LEFTBOTTOM, StringAlignment.CENTER, Color.BLACK, Color.WHITE);
        usePalette = false;
    }

    public Marker(IBaseChart c, String text, int fontsize, AnnotationPosition pos, StringAlignment align, Color color1, Color color2) {
        usePalette = true;
        centered = false;
        getShape().getFont().setName("Arial");
        setTextAlign(align);
        setText(text);
        getShape().getFont().setSize(fontsize);
        getShape().getPen().setVisible(false);
        getShape().getFont().setColor(color1);
        getShape().setColor(color2);
        getShape().getShadow().setVisible(false);
        setPosition(pos);
        if(getPosition() == AnnotationPosition.CENTER)
        {
		centered = true;
	}

    }

    public Marker(String text, int fontsize, int left, int top, StringAlignment align, Color color1, Color color2) {
        this((Chart) null, text, fontsize, AnnotationPosition.LEFTTOP, align, color1, color2); 
    }

    public int textWidth() {
        int tmp = 0;
        return (multiLineTextWidth(getChart().getGraphics3D(), getText(), tmp));

    }

    public void setCentered(boolean b) {
        centered = b;
    }

    private int textWidth(IGraphics3D g, String text) {

        int result = 0;
        int tmpW = (int) Math.round(g.textWidth("W") * textWidth);
        int tmpC = 0;
        int tmpL = 0;
        /**
        if (g.getFont().getName().equals("DS-Digital")) {
        
        tmpC = (int) Math.round(g.textWidth(",") * barfontSpace);
        
        } else if (g.getFont().getName().equals("Elektra")) {
        
        
        tmpC = (int) Math.round(g.textWidth(",") * dotfontSpace);
        tmpL = (int) Math.round(g.textWidth("'") * dotfontSpace);
        
        }
         **/
        tmpC = (int) Math.round(g.textWidth(",") * barfontSpace);
        char c;
        for (int i = 0; i < text.length(); i++) {
            c = text.charAt(i);
            if (c == '.') {
                /**
                if (g.getFont().getName().equals("DS-Digital")) {
                result += tmpC;
                } else if (g.getFont().getName().equals("Elektra")) {
                result += Math.round(tmpC / barfontSpace);
                }
                 * */
                result += tmpC;
            } else if (c == ',') {
                /**
                if (g.getFont().getName().equals("DS-Digital")) {
                result += tmpC;
                } else if (g.getFont().getName().equals("Elektra")) {
                result += Math.round(tmpC / barfontSpace);
                }
                 * **/
                result += tmpC;
            } else {
                result += tmpW;
            }
        }
        return result;
    }

    private int multiLineTextWidth(IGraphics3D g, String s, int numLines) {
        int i = s.indexOf('\n');
        int result = 0;
        numLines = 0;
        while (i > 0) {
            result = Math.max(result, textWidth(g, s.substring(1, i - 1)));
            result = Math.round(Math.max(result, textWidth(g, s.substring(1, i - 1))));
            ++numLines;
            s = (String) s.subSequence(i, s.length());
            i = s.indexOf('\n');
        }
        if (s != "") {
            result = Math.round(Math.max(result, textWidth(g, s)));
            ++numLines;
        }

        return result;
    }

   protected void calcTempPosition(int[] xyPosition, int tmpW, int tmpH, int horizMargin, int vertMargin) {

        int tmpX = iRectangle.getRight() - tmpW - 2 * horizMargin;
        int tmpY = iRectangle.getBottom() - tmpH - 4 * vertMargin;
        if (centered) {
            setHeight(tmpH);
            setWidth(iRectangle.width - 2 * horizMargin);
            xyPosition[0] = iRectangle.getLeft() + 2 * horizMargin;
            int tmp = (int) Math.round((iRectangle.getHeight() - tmpH) / 2);
            xyPosition[1] = iRectangle.getTop() + tmp;
        } else {
            if (getPosition().equals(AnnotationPosition.LEFTTOP)) {
                xyPosition[0] = iRectangle.getLeft() + 2 * horizMargin;
               xyPosition[1] = iRectangle.getTop() + 4 * vertMargin;
            } else if (getPosition().equals(AnnotationPosition.LEFTBOTTOM)) {
               xyPosition[0] = iRectangle.getLeft() + 2 * horizMargin;
                xyPosition[1] = tmpY;
            } else if (getPosition().equals(AnnotationPosition.RIGHTTOP)) {
               xyPosition[0] = tmpX;
               xyPosition[1] = iRectangle.getTop() + 4 * vertMargin;
            } else {
                xyPosition[0]= tmpX;
                xyPosition[1]= tmpY;
            }
        }
    }

    protected void drawString(Graphics3D g, int x, int y, int t, int tmpHeight, String[] s) {
              super.drawString(g, x, y, t, tmpHeight, s);
    }

    public boolean getCentered() {
        return centered;
    }

    public void setCenter(boolean value) {
        centered = value;

    }

    public boolean getUsePalette() {
        return usePalette;
    }

    public void setUsePalette(boolean value) {
        usePalette = value;
    }

    public boolean getVisible() {
        return visible;
    }

    public void setVisible(boolean value) {
        visible = value;
        if (visible == false) {
            getShape().setVisible(false);
            textAuxiliar = getText();
            setText("");
        } else if (visible == true) {
            getShape().setVisible(true);
            setText(textAuxiliar);
        }
    }
}
