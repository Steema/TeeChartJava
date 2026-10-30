package com.steema.teechart.editors.gallery;

import java.awt.event.KeyEvent;

import java.awt.Container;
import java.awt.Frame;
import java.awt.event.MouseEvent;
import javax.swing.JFrame;

import com.steema.teechart.Cursors;
import com.steema.teechart.TChart;
import com.steema.teechart.drawing.ChartFont;
import com.steema.teechart.drawing.Color;
import com.steema.teechart.BevelStyle;
import com.steema.teechart.events.ChartDrawEvent;
import com.steema.teechart.events.ChartPaintAdapter;
import com.steema.teechart.drawing.Graphics3D;
import com.steema.teechart.drawing.DashStyle;
import com.steema.teechart.drawing.GradientDirection;
import com.steema.teechart.drawing.Point;

/**
 *
 * <p>Title: GalleryChart class</p>
 *
 * <p>Description: Internal Chart class used to display at Gallery panel.</p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights
 * Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
final public class GalleryChart extends TChart {

    /**
     * The color used to change selected Chart's Gradient.
     */
    final static public Color GALLERYCOLOR = Color.WHITE;

    int tag;
    Object initTag;
    private boolean canDrawFrame;
    private boolean frameDisplayed;

    /**
     * Creates a new Gallery dialog.
     */
    public GalleryChart() {
        this(true);
    }

    /**
     * Creates a new Gallery dialog.
     *
     * @param smooth boolean is true to show anti-aliased charts at gallery
     */
    public GalleryChart(boolean smooth) {
        super();

        getLegend().setVisible(false);

        getAxes().getLeft().getLabels().setVisible(false);
        getAxes().getBottom().getLabels().setVisible(false);

        getZoom().setAnimated(true);

        getHeader().getFont().setColor(Color.NAVY);
        getHeader().getBevel().setInner(BevelStyle.NONE);
        getHeader().getBevel().setOuter(BevelStyle.NONE);
        getHeader().setTransparent(true);
        getHeader().getPen().setVisible(false);

        getAspect().setOrthogonal(false);
        getAspect().setZoom(90);
        getAspect().setPerspective(55);
        getAspect().setRotation(335);
        getAspect().setElevation(350);

        getAspect().setChart3DPercent(100);
        getAspect().setClipPoints(false);
        getGraphics3D().setSmoothingMode(smooth);

        getPanel().getBevel().setWidth(2);
        getPanel().getBevel().setOuter(BevelStyle.NONE);

        getWalls().getLeft().setColor(Color.WHITE);
        getWalls().getLeft().setSize(4);
        getWalls().getLeft().getPen().setColor(Color.DARK_GRAY);

        getWalls().getBottom().setColor(Color.WHITE);
        getWalls().getBottom().setSize(4);
        getWalls().getBottom().getPen().setColor(Color.DARK_GRAY);

        getWalls().getBack().getPen().setVisible(false);
        getWalls().getBack().setVisible(false);

        this.addChartPaintListener(new ChartPaintAdapter() {
            public void chartPainted(ChartDrawEvent e) {
                doAfterDraw();
            };
        });
    }

    protected void processKeyEvent(KeyEvent e) {
        super.processKeyEvent(e);
        ((GalleryPanel) getParent()).processKeyEvent(e);
    }

    protected void processMouseEvent(MouseEvent e) {
        super.processMouseEvent(e);

        if (e.getID() == MouseEvent.MOUSE_ENTERED) {
            drawFrame3D(false);
        } else
        if (e.getID() == MouseEvent.MOUSE_EXITED) {
            drawFrame3D(true);
        }
    }

    private static final int TMPH = 6;
    private static final int TMPSTART = 4;
    private static final int TMPW = 7;

    public void doAfterDraw() {

        if (getPanel().getGradient().getVisible()) {

            if (((GalleryPanel) (getParent())).getDisplaySub()) {
                Graphics3D g = (Graphics3D) getGraphics3D();
                g.getBrush().setSolid(true);
                g.getBrush().setColor(Color.BLACK);
                g.getBrush().setVisible(true);
                g.getBrush().getGradient().setVisible(false);
                g.getPen().setVisible(false);
                g.polygon(new Point(TMPSTART, getHeight() - TMPH - 2),
                          new Point(TMPSTART + TMPW, getHeight() - TMPH - 2),
                          new Point(TMPSTART + (TMPW / 2), getHeight() - 4));

                g.getPen().setStyle(DashStyle.SOLID);
            }
        }

        canDrawFrame = true;
    }

    public void setMargins() {
        int tmp = getAspect().getView3D() ? 4 : 5;
        getPanel().setMarginTop(tmp);
        getPanel().setMarginBottom(tmp);
        getPanel().setMarginLeft(tmp);
        getPanel().setMarginRight(tmp);
    }

    private void drawFrame3D(boolean erase) {
        if (erase)
            repaint();

        if ((getCursor() != Cursors.NO) && canDrawFrame) {

            if ((!erase) || (frameDisplayed)) {

                getGraphics().setXORMode(Color.BLACK);
                getGraphics().draw3DRect(0,0,getBounds().width-2, getBounds().height - 2, true);
                getGraphics().setPaintMode();

                frameDisplayed = !erase;
            }
        }
    }

    void doFocus(boolean is3D) {
        getPanel().getGradient().setVisible(true);
        getPanel().getGradient().setDirection(GradientDirection.VERTICAL);
        getPanel().getGradient().setStartColor(Color.SILVER);
        getPanel().getGradient().setEndColor(GALLERYCOLOR);
        getAspect().setRotation(345);

        if (getSeriesCount() > 0) {
            getSeries(0).galleryChanged3D(is3D);
        }

        getHeader().getFont().setBold(true);
        getHeader().getFont().setColor(Color.BLACK);
        getHeader().getFont().setSize(ChartFont.DEFAULTSIZE+1);
        getPanel().getBevel().setOuter(BevelStyle.RAISED);

        requestFocus();
    }

    void unFocus(boolean is3D) {
        if (getPanel().getGradient().getVisible()) {
            getPanel().getGradient().setVisible(false);
            getPanel().getBevel().setOuter(BevelStyle.NONE);
            getHeader().getFont().setBold(false);
            getHeader().getFont().setColor(Color.NAVY);
            getHeader().getFont().setSize(ChartFont.DEFAULTSIZE);
        }
        getAspect().setRotation(335);

        if (getSeriesCount() > 0) {
            getSeries(0).galleryChanged3D(is3D);
        }
    }

    private JFrame findForm() {
        Container c = getParent();

        do {
            if (c instanceof JFrame) {
                return (JFrame) (c);
            }
            c = c.getParent();
        } while (c != null);

        return null;
    }

    void checkShowLabels() {
        if (GalleryPanel.checkMaximize && getAxes().getVisible()) {

            JFrame f = findForm();

            if (f != null) {
                getAxes().getLeft().getLabels().setVisible((f.getExtendedState() &
                        Frame.MAXIMIZED_BOTH) ==
                        Frame.MAXIMIZED_BOTH);
                getAxes().getBottom().getLabels().setVisible(getAxes().getLeft().
                        getLabels().getVisible());
            }
        }
    }
}

