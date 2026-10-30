package com.steema.teechart.printer;

import java.awt.image.BufferedImage;
import java.awt.event.MouseEvent;
import java.util.Vector;
import java.util.Iterator;
import java.awt.Cursor;

import com.steema.teechart.drawing.Color;
import com.steema.teechart.drawing.DashStyle;
import com.steema.teechart.drawing.IGraphics3D;
import com.steema.teechart.drawing.Point;
import com.steema.teechart.Shadow;
import com.steema.teechart.Chart;
import com.steema.teechart.Rectangle;

import javax.swing.JComponent;
import java.awt.GridLayout;
import javax.swing.JPanel;
import java.awt.Graphics;

import com.steema.teechart.drawing.ChartPen;
import com.steema.teechart.drawing.Gradient;
import com.steema.teechart.drawing.GradientDirection;

import java.awt.Image;
import java.awt.event.MouseMotionAdapter;
import java.awt.print.Printable;
import java.awt.print.PageFormat;
import java.awt.Graphics2D;
import java.awt.print.Paper;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;
import com.steema.teechart.misc.Utils;

/**
 * <p>Title: PreviewPanel class</p>
 *
 * <p>Description: Preview Panel</p>
 *
 * <p>Example:
 * <pre><font face="Courier" size="4">
 * preview = new PreviewPanel(myChart.getChart(), myChart.getPrinter());
 * preview.setPaperColor(Color.LIGHT_YELLOW);
 * preview.getPaperShadow().setColor(Color.BLACK);
 * preview.getPaperShadow().setVertSize(4);
 * preview.getPaperShadow().setHorizSize(4);
 * preview.getPaperShadow().setTransparency(70);
 * preview.setOrientation(PageFormat.LANDSCAPE);
 * </font></pre></p>
 *
 * <p>Copyright (c) 2004-2019 by Steema Software SL. All Rights
 * Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public class PreviewPanel extends JComponent {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Chart chart;

    transient protected IGraphics3D graphics3D;

    private boolean allowResize = true;
    private boolean allowMove = true;
    private boolean asBitmap = false;
    private Vector<Chart> panels;
    private boolean dragImage = false;
    private PreviewChartPen margins;
    private int orientation = PageFormat.PORTRAIT;

    private Color paperColor = Color.WHITE;
    private Shadow paperShadow;
    private Gradient gradient;
    private boolean showImage = true;
    private boolean smoothBitmap = true;
    private String title;

    /* internal */
    private PreviewZone dragged;
    private int oldX;
    private int oldY;
    private Rectangle oldRect;
    private boolean oldShowImage;
    private int clientWidth;
    private int clientHeight;

    private Printer prn;

    public Rectangle imageRect;
    public Rectangle paperRect;
    GridLayout gridLayout1 = new GridLayout();

    /*protected void paintComponent(Graphics g) {
        internalDraw(getClientRect());
         }*/

// to do    FOnChangeMargins : TOnChangeMarginsEvent;
// to do    FOnGetPaperRect  : TOnGetPaperRect;

    public void checkGraphics3D() {
        if (graphics3D == null) {
            setGraphics3D(new com.steema.teechart.drawing.Graphics3DAWT());
        }
    }

    public void setGraphics3D(IGraphics3D value) {
        if (value != null) {
            graphics3D = value;
            invalidate();
        }
    }

    public IGraphics3D getGraphics3D() {
        checkGraphics3D();
        return graphics3D;
    }

    private Rectangle getClientRect() {
        java.awt.Rectangle r = getBounds();
        return new Rectangle(r.x, r.y, r.width, r.height);
    }

    public class RenderPanel extends JPanel implements Printable {

        public void paint(Graphics g) {

            if (prn == null) {
                return;
            }

            if (!prn.isPrinting) {
                clientWidth = getClientRect().width;
                clientHeight = getClientRect().height;
                calcPaperRectangles();
                getGraphics3D().setGraphics(g);
                gradient.draw(graphics3D,
                              new Rectangle(0, 0, getClientRect().width,
                                            getClientRect().height));
                drawPaper(g);

                imageRect = prn.calcImagePrintMargins(getPanel(), paperRect); //calcImagePrintMargins(getPanel());
            } else {
                calcPaperRectangles();
                imageRect = prn.calcImagePrintMargins(getPanel(), paperRect);
            }

            if (!prn.isPrinting) {
                if (g != null) {
                    
                	//Chart tmpChart = null;
                    
                	Iterator t = panels.iterator();
                    try {
                        while (t.hasNext()) {
                            g.drawImage((Image) (getPrintingImage((Chart) t.
                                    next(),
                                    imageRect)).
                                        getScaledInstance(imageRect.width,
                                    imageRect.height, 4), //4 = SCALE_SMOOTH
                                        imageRect.getLeft(), imageRect.getTop(), null);
                        }
                    } catch (Exception err) {}
                }
                drawMargins(imageRect);
            } else {
                if (g != null) {
                    Chart tmpChart = null;
                    Iterator t = panels.iterator();
                    try {
                        while (t.hasNext()) {
                            tmpChart = (Chart) t.next();
                            tmpChart.getGraphics3D().setGraphics(g);

                            int minX = (int) prn.getPrinterJob().defaultPage().
                                       getImageableX();
                            int minY = (int) prn.getPrinterJob().defaultPage().
                                       getImageableY();
                            imageRect.x = imageRect.x - minX;
                            imageRect.y = imageRect.y - minY;

                            tmpChart.paint(tmpChart.getGraphics3D(), imageRect);

                            //******** alternative technique, respects axis scaling and fixed
                             //******** location custom draw objects
                              //******** however not vectorial
                               //int minX=(int)prn.getPrinterJob().defaultPage().getImageableX();
                               //int minY=(int)prn.getPrinterJob().defaultPage().getImageableY();
                               //g.drawImage((Image)(getPrintingImage(tmpChart,imageRect)).
                               //            getScaledInstance(imageRect.width,
                               //        imageRect.height, 4), imageRect.x-minX, imageRect.y-minY, null);

                        }
                    } catch (Exception err) {}

                }
            }
        }

        public int print(Graphics g, PageFormat pageFormat, int pageIndex) {
            if (pageIndex > 0) {
                return (NO_SUCH_PAGE);
            } else {
                prn.getOptions(pageFormat);

                Paper paper = prn.getDocument().defaultPage().getPaper();

                //document.defaultPage().setOrientation(PageFormat.LANDSCAPE);
                Rectangle renderRect;
                Rectangle imageRect;
                if (prn.getProportional()) {
                    imageRect = prn.calcImagePrintMargins(getPanel(), paper,
                            pageFormat);
                } else {
                    if (pageFormat.getOrientation() == PageFormat.LANDSCAPE) {
                        renderRect = new Rectangle((int) paper.getImageableX(),
                                (int) paper.getImageableY(),
                                (int) paper.getImageableHeight(),
                                (int) paper.getImageableWidth());
                    } else {
                        renderRect = new Rectangle((int) paper.getImageableX(),
                                (int) paper.getImageableY(),
                                (int) paper.getImageableWidth(),
                                (int) paper.getImageableHeight());
                    }
                    imageRect = renderRect;
                }

                if (g != null) {
                    Chart tmpChart = null;
                    Iterator t = panels.iterator();
                    try {
                        while (t.hasNext()) {
                            tmpChart = (Chart) t.next();
                            tmpChart.getGraphics3D().setGraphics(g);
                            Graphics2D g2d = (Graphics2D) g;

                            tmpChart.getPanel().setColor(Color.white);

                            if (pageFormat.getOrientation() ==
                                PageFormat.LANDSCAPE) {
                                g2d.translate((int) paper.getWidth(), 0);
                                g2d.rotate(90.0 * Math.PI / 180.0);
                            }

                            /*Color tmpColor = Color.EMPTY;
                            boolean noBackground = false;
                            boolean gradientVisible = chart.getPanel().getGradient().getVisible();;
                            if ((!chart.getPanel().getGradient().getVisible())
                                || (!prn.getPrintPanelBackground())) {
                                noBackground = true;
                                chart.getPanel().getGradient().setVisible(false);
                                tmpColor = tmpChart.getPanel().getColor();
                                tmpChart.getPanel().setColor(Color.WHITE);
                            }*/

                            if (prn.getGrayscale()) {
                                BufferedImage BufImage = new BufferedImage(
                                        imageRect.width,
                                        imageRect.height,
                                        BufferedImage.TYPE_USHORT_GRAY);
                                Image i = tmpChart.image(imageRect.width,
                                        imageRect.height);
                                java.awt.image.ColorConvertOp colorConvert = new
                                        java.awt.image.
                                        ColorConvertOp(
                                                java.awt.color.ColorSpace.
                                                getInstance(java.awt.color.
                                        ColorSpace.
                                        CS_GRAY), null);

                                colorConvert.filter((BufferedImage) i, BufImage);

                                g.drawImage(((Image) BufImage).
                                            getScaledInstance(imageRect.width,
                                        -1, java.awt.Image.SCALE_SMOOTH),
                                            imageRect.getLeft(),
                                            imageRect.getTop(),
                                            imageRect.width, imageRect.height, null);
                            } else {
                                tmpChart.paint(tmpChart.getGraphics3D(),
                                               imageRect);
                            }
                            //******** alternative technique, respects axis scaling and fixed
                             //******** location custom draw objects
                              //******** however not vectorial
                               //int minX=(int)prn.getPrinterJob().defaultPage().getImageableX();
                               //int minY=(int)prn.getPrinterJob().defaultPage().getImageableY();
                               //g.drawImage((Image)(getPrintingImage(tmpChart,imageRect)).
                               //            getScaledInstance(imageRect.width,
                               //        imageRect.height, 4), imageRect.x-minX, imageRect.y-minY, null);
                               /*if (noBackground) {
                                   chart.getPanel().setColor(tmpColor);
                                   chart.getPanel().getGradient().setVisible(gradientVisible);
                               }*/
                        }
                    } catch (Exception err) {}

                }
            }

            return (PAGE_EXISTS);
        }
    }


    public RenderPanel preview = new RenderPanel();

    public PreviewPanel() {
        this(null, null);
    }

    public PreviewPanel(Chart chart, Printer prn) {
        super();

        //      FPanels:=TTeePanelsList.Create;

        this.chart = chart;
        this.prn = prn;

        panels = new Vector<Chart>();
        panels.add(chart);

        dragImage = false;
        orientation = PageFormat.PORTRAIT;

        margins = new PreviewChartPen(new ChartPen(Color.BLACK));
        margins.setStyle(DashStyle.DOT);

        showImage = true;
        oldShowImage = true;
        dragged = PreviewZone.NONE;
        asBitmap = false;
        smoothBitmap = true;
        paperColor = com.steema.teechart.drawing.Color.WHITE;
        allowResize = true;
        allowMove = true;
        title = "";

        preparePanel();

        setSize(432, 312);
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        if (chart != null) {
            preview.invalidate();
        }

        preview.addAncestorListener(new AncestorListener() {
            public void ancestorMoved(AncestorEvent event) {
                preview.repaint();
            }

            public void ancestorAdded(AncestorEvent event) {}

            public void ancestorRemoved(AncestorEvent event) {}
        });

    }

    private void preparePanel() {
        gradient = new Gradient();
        gradient.setStartColor(Color.GRAY);
        gradient.setDirection(GradientDirection.BACKDIAGONAL);
        gradient.setVisible(true);

        paperShadow = new Shadow(chart, 4, Color.DARK_GRAY);
        paperShadow.setTransparency(70);
    }

    public void drawPaper(Graphics g) {
        paperShadow.draw(graphics3D, paperRect);
        Rectangle r = new Rectangle(paperRect);
        g.setColor(Color.WHITE);
        g.fillRect(paperRect.getLeft(), paperRect.getTop(), paperRect.width,
                   paperRect.height);
    }

    public void setChart(Chart chart) {
        this.chart = chart;
        if (chart != null) {
            preview.invalidate();
        }
    }

    private Image getPrintingImage(Chart chart, Rectangle imageRect) {
        int winWidth = 0;
        int winHeight = 0;
        int tmpW = 0;
        int tmpH = 0;

        //aPanel.setPrinting(true);

        Chart tmpChart = null;

        try {

            Image tmpImage = null;
            Rectangle outputRect;

            // if (!isPrinting)
            outputRect = calcChartRenderBounds(imageRect, chart.getChartBounds()); //  chart.getChartRect());
            // else
            //    outputRect = imageRect;


            int tmpWidth = outputRect.width;
            int tmpHeight = outputRect.height;
            tmpChart = chart;

            Color oldColor = tmpChart.getPanel().getColor();
            tmpChart.getPanel().setColor(paperColor);
            tmpImage = tmpChart.image(tmpWidth, tmpHeight);
            tmpChart.getPanel().setColor(oldColor);
            return tmpImage;
        }

        catch (Exception err) {}
        return null;
    }

    public void setAsBitmap(boolean value) {
        asBitmap = value;
    }

    public void setPaperColor(Color value) {
        paperColor = value;
    }

    public void setShadowSize(int value) { // obsolete
        paperShadow.setSize(new com.steema.teechart.Dimension(value, value));
    }

    public void setShowImage(boolean value) {
        showImage = value;
    }

    public void setMargins(PreviewChartPen value) {
        margins = value;
    }

    private void setPanel(Chart value) {
        if ((chart != value) && (value != getPanel())) { // 7.01
            Iterator i = panels.iterator();

            if (!panels.isEmpty()) {
                panels.remove(0);
            }

            if (value != null) {
                if (panels.indexOf(value) == -1) {
                    panels.add(value);
                    //value.freeNotification(this); // 5.01
                }
            }

            invalidate();
        }
    }

    private Rectangle calcChartRenderBounds(Rectangle imageRect,
                                            Rectangle chartRect) {
        int tmpLeft;
        int tmpTop;
        int tmpRight;
        int tmpBottom;
        int winWidth;
        int winHeight;

        winWidth = chartRect.width;
        winHeight = chartRect.height;

        tmpLeft = (imageRect.getLeft() * winWidth / imageRect.width);
        tmpRight = (imageRect.getRight() * winWidth / imageRect.width);
        tmpTop = (imageRect.getTop() * winHeight / imageRect.height);
        tmpBottom = (imageRect.getBottom() * winHeight / imageRect.height);

        return new Rectangle(tmpLeft, tmpTop, tmpRight - tmpLeft,
                             tmpBottom - tmpTop);
    }

    private void checkPrinterOrientation(int tmpPrinterWidth,
                                         int tmpPrinterHeight) {
        if (prn.getLandscape()) {
            tmpPrinterHeight = (int) prn.getPrinterJob().defaultPage().
                               getWidth();
            tmpPrinterWidth = (int) prn.getPrinterJob().defaultPage().
                              getHeight();
        } else {
            tmpPrinterWidth = (int) prn.getPrinterJob().defaultPage().
                              getWidth();
            tmpPrinterHeight = (int) prn.getPrinterJob().defaultPage().
                               getHeight();
        }
    }

    private void calcPaperRectangles() {
        int margin = 5;
        Rectangle r;
        int tmpTop;
        int tmpBottom;
        int tmpLeft;
        int tmpRight;
        int tmpWidth;
        int tmpHeight;
        int printerWidth = 0;
        int printerHeight = 0;

        //checkPrinterOrientation(printerWidth,printerHeight);

        if (prn.getLandscape()) {
            printerWidth = (int) prn.getPrinterJob().defaultPage().getHeight();
            printerHeight = (int) prn.getPrinterJob().defaultPage().getWidth();
        } else {
            printerWidth = (int) prn.getPrinterJob().defaultPage().getWidth();
            printerHeight = (int) prn.getPrinterJob().defaultPage().getHeight();
        }

        if ((getClientRect().width * printerHeight) >
            (getClientRect().height * printerWidth)) {
            tmpHeight = getClientRect().height -
                        Utils.round(clientHeight / margin);
            tmpTop = (Utils.round(clientHeight / (2 * margin)));
            tmpBottom = (tmpTop + tmpHeight);

            if (printerHeight > 0) {
                tmpWidth = (tmpHeight * printerWidth) / printerHeight;
            } else {
                tmpWidth = clientWidth;
            }

            tmpLeft = ((clientWidth - tmpWidth) / 2);
            tmpRight = (tmpLeft + tmpWidth);
        } else {
            tmpWidth = clientWidth -
                       Utils.round(clientWidth / margin);
            tmpLeft = (Utils.round(clientWidth / (2 * margin)));
            tmpRight = (tmpLeft + tmpWidth);

            if (printerWidth > 0) {
                tmpHeight = (tmpWidth * printerHeight) / printerWidth;
            } else {
                tmpHeight = clientHeight;
            }

            tmpTop = ((clientHeight - tmpHeight) / 2);
            tmpBottom = (tmpTop + tmpHeight);
        }

        if (!prn.isPrinting) {
            paperRect = new Rectangle(tmpLeft, tmpTop, tmpRight - tmpLeft,
                                      tmpBottom - tmpTop);
        } else {
            paperRect = new Rectangle(0, 0, printerWidth, printerHeight);
        }
    }

    /*protected void internalDraw(Rectangle userRectangle) {
        chart.getPanel().paint(chart.getGraphics3D(), userRectangle);
        chart.recalcWidthHeight(userRectangle);
        calcPaperRectangles();
         }*/

    protected void drawMargins(Rectangle r) {
        if (margins.getVisible()) {
            graphics3D.setPen(margins);
            //getCanvas().getPen().setMode(pmNotXor);
            //vert left
            graphics3D.line(new Point(imageRect.getLeft(), paperRect.getTop()),
                            new Point(imageRect.getLeft(), paperRect.getBottom()));
            //vert right
            graphics3D.line(new Point(imageRect.getRight(), paperRect.getTop()),
                            new Point(imageRect.getRight(), paperRect.getBottom()));
            //horiz top
            graphics3D.line(new Point(paperRect.getLeft(), imageRect.getTop()),
                            new Point(paperRect.getRight(), imageRect.getTop()));
            //horiz bottom
            graphics3D.line(new Point(paperRect.getLeft(), imageRect.getBottom()),
                            new Point(paperRect.getRight(), imageRect.getBottom()));
        }
    }

    protected void drawAsMetafile(Rectangle panelRect) {
        Rectangle tmpR;
        Image tmpMeta;
        int winWidth;
        int winHeight;
        int tmpW;
        int tmpH;

        tmpR = panelRect;
    }

    protected int whereIsCursor(int x, int y) {
        int minPixels = 5;
        int xLeft;
        int xRight;
        int yTop;
        int yBottom;

        xLeft = Math.abs(x - imageRect.getLeft());
        xRight = Math.abs(x - imageRect.getRight());
        yTop = Math.abs(y - imageRect.getTop());
        yBottom = Math.abs(y - imageRect.getBottom());

        if ((xLeft < minPixels) && (yTop < minPixels)) {
            dragged = PreviewZone.LEFTTOP;
            return Cursor.NW_RESIZE_CURSOR;
        } else
        if ((xLeft < minPixels) && (yBottom < minPixels)) {
            dragged = PreviewZone.LEFTBOTTOM;
            return Cursor.SW_RESIZE_CURSOR;
        } else
        if ((xRight < minPixels) && (yTop < minPixels)) {
            dragged = PreviewZone.RIGHTTOP;
            return Cursor.NE_RESIZE_CURSOR;
        } else
        if ((xRight < minPixels) && (yBottom < minPixels)) {
            dragged = PreviewZone.RIGHTBOTTOM;
            return Cursor.SE_RESIZE_CURSOR;
        } else
        if (xLeft < minPixels) {
            dragged = PreviewZone.LEFT;
            return Cursor.W_RESIZE_CURSOR;
        } else
        if (xRight < minPixels) {
            dragged = PreviewZone.RIGHT;
            return Cursor.E_RESIZE_CURSOR;
        } else
        if (yTop < minPixels) {
            dragged = PreviewZone.TOP;
            return Cursor.N_RESIZE_CURSOR;
        } else
        if (yBottom < minPixels) {
            dragged = PreviewZone.BOTTOM;
            return Cursor.S_RESIZE_CURSOR;
        } else {
            return Cursor.DEFAULT_CURSOR;
        }
    }

    protected Cursor previewCursors(int i) {
        return new Cursor(i);
    }

    protected void mouseMove(int x, int y) {
        Rectangle tmpR = null;
        int paperWidth;
        int paperHeight;
        Rectangle r;

        if (paperRect==null) {
            return;
        }

        if (com.steema.teechart.drawing.Graphics3D.pointInRect(paperRect, x, y)) {
            if (dragged == PreviewZone.NONE) {
                setCursor(new Cursor(whereIsCursor(x, y)));
                return;
            } else {
                if (!dragImage) {
                    drawMargins(imageRect);
                }

                // sides
                if (dragged == PreviewZone.LEFT) {
                    if ((x >= paperRect.getLeft())
                        && (x < imageRect.getRight())) {
                        imageRect.x = x;
                    }
                } else
                if (dragged == PreviewZone.TOP) {
                    if ((y >= paperRect.getTop())
                        && (y < imageRect.getBottom())) {
                        imageRect.y = y;
                    }
                } else
                if (dragged == PreviewZone.RIGHT) {
                    if ((x <= paperRect.getRight())
                        && (x > imageRect.getLeft())) {
                        imageRect.setRight(x);
                    }
                } else
                if (dragged == PreviewZone.BOTTOM) {
                    if ((y <= paperRect.getBottom())
                        && (y > imageRect.getTop())) {
                        imageRect.setBottom(y);
                    }
                } else
                if (dragged == PreviewZone.LEFTTOP) {
                    if ((x >= paperRect.getLeft())
                        && (x < imageRect.getRight()) &&
                        (y >= paperRect.getTop()) && (y < imageRect.getBottom())) {
                        imageRect.x = x;
                        imageRect.y = y;
                    }
                } else
                if (dragged == PreviewZone.LEFTBOTTOM) {
                    if ((x >= paperRect.getLeft()) &&
                        (x < imageRect.getRight()) &&
                        (y <= paperRect.getBottom()) && (y > imageRect.getTop())) {
                        imageRect.x = x;
                        imageRect.setBottom(y);
                    }
                } else
                if (dragged == PreviewZone.RIGHTTOP) {
                    if ((x <= paperRect.getRight()) &&
                        (x > imageRect.getLeft()) &&
                        (y >= paperRect.getTop()) && (y < imageRect.getBottom())) {
                        imageRect.setRight(x);
                        imageRect.y = y;
                    }
                } else
                if (dragged == PreviewZone.RIGHTBOTTOM) {
                    if ((x <= paperRect.getRight()) &&
                        (x > imageRect.getLeft()) &&
                        (y <= paperRect.getBottom()) && (y > imageRect.getTop())) {
                        imageRect.setRight(x);
                        imageRect.setBottom(y);
                    }
                }
            }

            if (dragImage) {
                invalidate();
            } else {
                drawMargins(imageRect);
            }
        }
    }

    protected void mouseUp(MouseEvent button, int x, int y) {
        chart.setCancelMouse(false);

        if (dragged != PreviewZone.NONE) {
            dragged = PreviewZone.NONE;
            if (!dragImage) {
                invalidate();
            }
        }
    }

    protected void mouseDown(MouseEvent button, int x, int y) {
        whereIsCursor(x, y);
    }

    public void setOrientation(int value) {
        if (value != orientation) {
            prn.getPrinterJob().defaultPage().setOrientation(PageFormat.
                    LANDSCAPE);
            prn.setLandscape(true);
            invalidate();
            repaint();
        }
    }

    private void setShadowColor(Color value) { // obsolete
        paperShadow.setColor(value);
    }

    private Color getShadowColor() { // obsolete
        return paperShadow.getColor();
    }

    private void setPaperShadow(Shadow value) {
        paperShadow = value;
    }

    private Chart getPanel() {
        /*    if ((panels!=null) && (getPanels().getCount()>0))
                return getPanels[0];
            else*/
        return chart;
    }

    private void setSmoothBitmap(boolean value) {
        smoothBitmap = value;
    }

    public boolean getAllowResize() {
        return allowResize;
    }

    public void setAllowResize(boolean value) {
        allowResize = value;
    }

    public boolean getAllowMove() {
        return allowMove;
    }

    public void setAllowMove(boolean value) {
        allowMove = value;
    }

    public boolean getAsBitmap() {
        return asBitmap;
    }

    public boolean getDragImage() {
        return dragImage;
    }

    public void setDragImage(boolean value) {
        dragImage = value;
    }

    public PreviewChartPen getMargins() {
        return margins;
    }

    public int getOrientation() {
        return orientation;
    }

    public Color getPaperColor() {
        return paperColor;
    }

    public Shadow getPaperShadow() {
        return paperShadow;
    }

    public boolean getShowImage() {
        return showImage;
    }

    public boolean getSmoothBitmap() {
        return smoothBitmap;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String value) {
        title = value;
    }

    public Printer getPrinter() {
        return prn;
    }

    public void setPrinter(Printer value) {
        prn = value;
    }


    private void jbInit() throws Exception {
        this.setLayout(gridLayout1);
        this.addMouseMotionListener(new PreviewPanel_this_mouseMotionAdapter(this));
        this.add(preview);
    }

    public void this_mouseMoved(MouseEvent e) {
        mouseMove(e.getX(), e.getY());
    }
}


class PreviewPanel_this_mouseMotionAdapter extends MouseMotionAdapter {
    private PreviewPanel adaptee;
    PreviewPanel_this_mouseMotionAdapter(PreviewPanel adaptee) {
        this.adaptee = adaptee;
    }

    public void mouseMoved(MouseEvent e) {

        adaptee.this_mouseMoved(e);
    }
}

