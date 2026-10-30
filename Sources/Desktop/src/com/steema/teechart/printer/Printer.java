package com.steema.teechart.printer;

import java.awt.print.PrinterJob;
import java.awt.print.Paper;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.print.PrinterException;
import java.util.ArrayList;

import com.steema.teechart.TeeBase;
import com.steema.teechart.Rectangle;
import com.steema.teechart.Chart;
import com.steema.teechart.IBaseChart;
import com.steema.teechart.misc.Utils;
import com.steema.teechart.drawing.Graphics3D;
import com.steema.teechart.drawing.Image;

/**
 *
 * <p>Title: Printer class</p>
 *
 * <p>Description: Chart printing characteristics</p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public final class Printer extends TeeBase implements Printable {
	
	private static final long serialVersionUID = 20131209L;

    private double chartWidthHeightRatio;
    private boolean proportional = true;
    private boolean landscape;
    private boolean grayscale;
    private boolean useAntiAlias;

    //private boolean maxDetail;

    private int chartWidth;
    private int chartHeight;
    private PrintMarginUnit marginUnits = PrintMarginUnit.PERCENT;
    private PrintMargins margins;
    private transient PrinterJob document;
    private boolean printPanelBackground=false;

    transient public boolean isPrinting = false;

    public boolean isPartial;

    private ArrayList chartList;
    private int resolution = 1;
    private Rectangle paperRect;

    //private Component componentToBePrinted;

    public Printer(IBaseChart c) {
        super(c);
        margins = new PrintMargins(c, 20, 20, 20, 20);
    }

    protected PrinterJob getDocument() {
        if (document == null) {
            document = PrinterJob.getPrinterJob();

            /** @todo REMOVE? */
            //document.PrintController = new StandardPrintController();
            //document.PrintPage += new PrintPageEventHandler(pd_PrintPage);
        }
        return document;
    }

    public void getOptions() {
        getOptions(new PageFormat());
    }

    public void getOptions(PageFormat pageFormat) {

        Rectangle r = chart.getChartBounds();
        chartWidth = r.width;
        chartHeight = r.height;
        chartWidthHeightRatio = (double) chartWidth / (double) chartHeight;

        pageFormat.setOrientation(getOrientation());
    }

    /**
     * shows the Preview screen of the Chart print.
     */
    public void preview() {
        getOptions();

        /** @todo SHOW PRINT PREVIEW DIALOG */
        com.steema.teechart.editors.PreviewDialog.showModal((Chart)chart);
//        Editors.PrintPreview.ShowModal(chart, this);
    }

    /**
     * Print dimensions of Chart proportional to those onscreen.<br>
     * Default value: true
     *
     * @return boolean
     */
    public boolean getProportional() {
        return proportional;
    }

    /**
     * Sets print dimensions of Chart proportional to those onscreen.<br>
     * Default value: true
     *
     * @param value boolean
     */
    public void setProportional(boolean value) {
        proportional = setBooleanProperty(proportional, value);
    }

    /**
     * Prints transparent Chart Panel when false.<br>
     * Default value: false
     *
     * @return boolean
     */
    public boolean getPrintPanelBackground() {
        return printPanelBackground;
    }

    /**
     * Prints transparent Chart Panel when false.<br>
     * Default value: false
     *
     * @param value boolean
     */
    public void setPrintPanelBackground(boolean value) {
        printPanelBackground = value;
    }

    /**
     * Sets the Chart to detail resolution for printing.<br>
     * Valid values from from 1 to 100.<br>
     * Default value: 1
     *
     * @return int
     */
    public int getResolution() {
        return resolution;
    }

    /**
     * Sets the Chart to detail resolution for printing.<br>
     * Valid values from from 1 to 100.<br>
     * Default value: 1
     *

     * @param value int
     */
    public void setResolution(int value) {
        if ((value >= 1) | (value <= 100)) {
            resolution = value;
        }
    }

    /**
     * Printer Margin Units as a percentage or hundredths of an inch.<br>
     * Default value: HundredthsInch
     *
     *
     * @return PrintMarginUnit
     */
    public PrintMarginUnit getMarginUnits() {
        return marginUnits;
    }

    /**
     * Sets Printer Margin Units.<br>
     * Default value: HundredthsInch
     *
     *
     * @param value PrintMarginUnit
     */
    public void setMarginUnits(PrintMarginUnit value) {
        marginUnits = value;
    }

    /**
     * Applies anti-alias to plotting Pen when true.<br>
     * Default value: false
     *
     * @return boolean
     */
    public boolean getUseAntiAlias() {
        return useAntiAlias;
    }

    /**
     * Applies anti-alias to plotting Pen when true.<br>
     * Default value: false
     *
     * @param value boolean
     */
    public void setUseAntiAlias(boolean value) {
        useAntiAlias = setBooleanProperty(useAntiAlias, value);
    }

    /**
     * Prints a Grayscale Chart when true.<br>
     * Default value: false
     *
     * @return boolean
     */
    public boolean getGrayscale() {
        return grayscale;
    }

    /**
     * Prints a Grayscale Chart when true.<br>
     * Default value: false
     *
     * @param value boolean
     */
    public void setGrayscale(boolean value) {
        grayscale = setBooleanProperty(grayscale, value);
    }

    /**
     * Sets the current Printer orientation to Landscape.<br>
     * Default value: false
     *
     * @return boolean
     */
    public boolean getLandscape() {
        return landscape;
    }

    /**
     * Sets the current Printer orientation to Landscape when true.<br>
     * Default value: false
     *
     * @param value boolean
     */
    public void setLandscape(boolean value) {
        landscape = setBooleanProperty(landscape, value);
    }

    public ArrayList getChartList() {
        return chartList;
    }

    public void setArrayList(ArrayList value) {
        chartList = value;
    }

    //private boolean shouldSerializeMargins() {
    //    return (margins.getLeft() != 20) || (margins.getTop() != 20) ||
    //            (margins.getRight() != 20) || (margins.getBottom() != 20);
    //}

    /**
     * The four margins as a percentage of paper dimensions.<br>
     *
     * @return PrintMargins
     */
    public PrintMargins getMargins() {
        return margins;
    }

    /**
     * Sets the four margins as a percentage or fixed 1/72nd
     * dimension.<br>
     *
     * @param value PrintMargins
     */
    public void setMargins(PrintMargins value) {
        margins = value;
    }

    /**
     * Sets the four margins of paper dimensions.<br>
     * Please see PrintMarginUnit
     *
     *
     * @param left int
     * @param top int
     * @param right int
     * @param bottom int
     */
    public void setMargins(int left, int top, int right, int bottom) {
        margins = new PrintMargins(chart,left,top,right,bottom);
    }


    /**
     * Selects the Chart print job to be sent to the currently selected Printer.
     *
     * @return PrinterJob
     */
    public PrinterJob getPrinterJob() {
        return getDocument();
    }

    /**
     * Returns
     *
     */
    public Rectangle getPaperRect()
    {
        return paperRect;
    }

    /**
     * Selects the Chart print job to be sent to the currently selected Printer.
     *
     * @param value PrinterJob
     */
    public void setPrinterJob(PrinterJob value) {
        if (document != value) {
            document = value;
        }
    }

    private int Default_PrintMargin = 15;

    private int calcMargin(int Size1, int Size2, double aRatio,
                           boolean tmpPrinterOk, Printer prn) {

        double tmpPrinterRatio = 1;

        if (tmpPrinterOk) {
            tmpPrinterRatio = 1; //prn.getPrinterJob().defaultPage().getPaper().
        }

        double tmpDefaultMargin = (2.0 * Default_PrintMargin) * Size1 * 0.01;

        return Utils.round((100.0 *
                               (Size2 -
                                ((Size1 - tmpDefaultMargin) * aRatio * tmpPrinterRatio))) /
                              Size2) / 2;
    }

    public Rectangle calcProportionalMargins(Chart aPanel, Rectangle bounds) {
        boolean tmpPrinterOk = false;
        int tmp;
        int tmpPrinterW = 0;
        int tmpPrinterH = 0;

        double tmpWidth = aPanel.getWidth();
        double tmpHeight = aPanel.getHeight();


            if (getLandscape()) {
                tmpPrinterH = (int) getPrinterJob().defaultPage().
                              getWidth();
                tmpPrinterW = (int) getPrinterJob().defaultPage().
                              getHeight();
            } else {
                tmpPrinterW = (int) getPrinterJob().defaultPage().
                              getWidth();
                tmpPrinterH = (int) getPrinterJob().defaultPage().
                              getHeight();
            }
            tmpPrinterOk = true;


        if (tmpWidth > tmpHeight) {
            tmp = calcMargin(tmpPrinterW, tmpPrinterH, tmpHeight / tmpWidth,
                             tmpPrinterOk, this);
            return new Rectangle(Default_PrintMargin, tmp, 0, 0);
        } else {
            tmp = calcMargin(tmpPrinterH, tmpPrinterW, tmpWidth / tmpHeight,
                             tmpPrinterOk, this);
            return new Rectangle(tmp, Default_PrintMargin, 0, 0);
        }
    }

    protected Rectangle calcImagePrintMargins(Chart aPanel,Paper paper,
                                              PageFormat pageFormat) {

        if (pageFormat.getOrientation() == PageFormat.LANDSCAPE) {
            paperRect = new Rectangle(0, 0,
                                      (int) paper.getHeight(),
                                      (int) paper.getWidth());
        } else {
            paperRect = new Rectangle(0, 0,
                                      (int) paper.getWidth(),
                                      (int) paper.getHeight());
        }

        return calcImagePrintMargins(aPanel,paperRect);
    }

    protected Rectangle calcImagePrintMargins(Chart aPanel,Rectangle paperRect) {

        int paperWidth = paperRect.width;
        int paperHeight = paperRect.height;

        if (getMargins() != null) {
            if (getProportional()) {
                Rectangle tmpRect=calcProportionalMargins(aPanel,
                        paperRect);
                setMargins(new PrintMargins(aPanel,tmpRect.getLeft(),
                                                        tmpRect.getTop(),tmpRect.getRight(),
                                                        tmpRect.getBottom()));
            } else {
                setMargins(getMargins());
            }
        }
        else {
                setMargins(new PrintMargins(aPanel,Default_PrintMargin,
                        Default_PrintMargin, Default_PrintMargin, Default_PrintMargin));
            }



        Rectangle r = new Rectangle();

        if (getMarginUnits()==PrintMarginUnit.PERCENT)
        {
            r.x = paperRect.getLeft() +
                  ((getMargins().getLeft() * paperWidth) / 100);
            r.setRight(paperRect.getRight() -
                       ((getMargins().getRight() * paperWidth) /
                        100));
            r.y = paperRect.getTop() +
                  ((getMargins().getTop() * paperHeight) / 100);
            r.setBottom(paperRect.getBottom() -
                        ((getMargins().getBottom() *
                          paperHeight) / 100));
        }
        else
        {
        	int printerWidth = 0;
        	int printerHeight = 0;
        	if (getLandscape()) {
        		printerWidth = (int) getPrinterJob().defaultPage().getHeight();
        		printerHeight = (int) getPrinterJob().defaultPage().getWidth();
        	} else {
        		printerWidth = (int) getPrinterJob().defaultPage().getWidth();
        		printerHeight = (int) getPrinterJob().defaultPage().getHeight();
        	}
        	
        	double portionWidth=paperRect.width*1.0/printerWidth*1.0;
        	double portionHeight=paperRect.height*1.0/printerHeight*1.0;
        	
          r.x = paperRect.getLeft() + (int)Math.round((getMargins().getLeft()*portionWidth));
          r.setRight(paperRect.getRight() - (int)Math.round((getMargins().getRight()*portionWidth)));
          r.y = paperRect.getTop() + (int)Math.round((getMargins().getTop()*portionHeight));
          r.setBottom(paperRect.getBottom() - (int)Math.round((getMargins().getBottom()*portionHeight)));
        }

        return r;
    }
    
    public Graphics3D checkGraphics(Graphics g) {
      Graphics3D g3D = (Graphics3D) chart.getGraphics3D();

      if ((g3D == null) || !(g3D instanceof com.steema.teechart.drawing.Graphics3DAWT)) {
          g3D = new com.steema.teechart.drawing.Graphics3DAWT(chart);
          chart.setGraphics3D(g3D);
      }

      if (g3D instanceof com.steema.teechart.drawing.Graphics3DAWT) {
          g3D.setGraphics(g);
      }

      return g3D;
    }

    public int print(Graphics g, PageFormat pageFormat, int pageIndex) {
        if (pageIndex > 0) {
            return NO_SUCH_PAGE;
        } else {
            getOptions(pageFormat);

            Paper paper = getDocument().defaultPage().getPaper();

            Rectangle imageRect;
            imageRect = calcImagePrintMargins((Chart)chart, paper, pageFormat);

            checkGraphics(g);
            //chart.getGraphics3D().setGraphics(g);
            Graphics2D g2d = (Graphics2D) g;

            if (pageFormat.getOrientation() == PageFormat.LANDSCAPE) {
                g2d.translate((int) paper.getWidth(), 0);
                g2d.rotate(90.0 * Math.PI / 180.0);
            }

            if (grayscale)
            {
                Image i=chart.image(imageRect.width,imageRect.height);
                g.drawImage(i.grayBuffered(),imageRect.getLeft(),imageRect.getTop(),imageRect.width,imageRect.height,null);
            }
            else
            {
                g.translate(imageRect.getLeft(), imageRect.getTop());
                chart.paint(chart.getGraphics3D(), new Rectangle(0, 0, imageRect.width, imageRect.height));
            }
            return PAGE_EXISTS;
        }
    }

    /**
     * Sends the Chart to the currently selected Printer.
     *
     * @throws PrinterException
     */
    public void print() throws java.awt.print.PrinterException {

        isPartial = false;

        PrinterJob printJob = PrinterJob.getPrinterJob();
        printJob.setPrintable(this);

        //if (printJob.printDialog())
        try {
            printJob.print();
        } catch (PrinterException pe) {
            System.out.println("Error printing: " + pe);
        }
    }

    /**
     * Sends the Chart to the currently selected Printer.<br>
     * The current Printer orientation (Portrait or Landscape) can be changed
     * prior to printing.
     * Param "landscape", when true, prints page as landscape.
     *
     * @param landscape boolean
     * @throws PrinterException
     */
    public void print(boolean landscape) throws java.awt.print.PrinterException {
        isPartial = false;
        getOptions();

        document.defaultPage().setOrientation(getOrientation());
        document.print();
    }

    public int getOrientation() {
        return landscape ? PageFormat.LANDSCAPE : PageFormat.PORTRAIT;
    }

    public void setOrientation(int orientation) {
        landscape = (orientation == PageFormat.LANDSCAPE);
    }


    private void getPartialOptions() {
        /** @todo NOT FOUND "Color" */
//      document.defaultPage().setColor(!grayscale);
        document.defaultPage().setOrientation(getOrientation());
    }

    /**
     * Prints Chart c at Rectangle (Left, Top, Width,Height).<br>
     *
     * @param c IBaseChart
     * @param r Rectangle
     */
    public void print(IBaseChart c, Rectangle r) {
        getPartialOptions();
        chartList.add(new ChartPrintJob(c, r));
    }

    /**
     * Prints at Rectangle (Left, Top, Width,Height).
     *
     * @param r Rectangle
     */
    public void print(Rectangle r) {
        getPartialOptions();
        chartList.add(new ChartPrintJob(getChart(), r));
    }

    /**
     * Start multiple Chart print job.
     *
     */
    public void beginPrint() {
        isPartial = true;
        chartList = new ArrayList();
    }

    /**
     * End multiple Chart print job.
     *
     * @throws PrinterException
     */
    public void endPrint() throws java.awt.print.PrinterException {
        document.print();
        isPartial = false;
    }

    private void pd_PrintPage(Object sender, PrintPage e) {
        /** @todo NOT FOUND "Color" */
//        chart.getGraphics3D().setMonochrome( !document.defaultPage().getColor());

        if (!isPartial) {
            chart.setPrinting(true);
//            boolean oldMono = chart.getGraphics3D().getMonochrome();
            try {
                printToFullPage(sender, e);
            } finally {
//                chart.getGraphics3D().setMonochrome(oldMono);
                chart.setPrinting(false);
            }
        } else {
            printToPartialPage(sender, e);
        }
    }

    private void printToPartialPage(Object sender, PrintPage e) {
        Image m;

        for (int t = 0; t < chartList.size(); t++) {
            ChartPrintJob j = (ChartPrintJob) chartList.get(t);
            j.getChart().setPrinting(true);

//            boolean oldMono = j.getChart().getGraphics3D().getMonochrome();
            try {
                if (resolution > 1) {
                    int tmpChartWidth = j.getChart().getWidth();
                    int tmpChartHeight = j.getChart().getHeight();

                    j.getChart().setWidth(Utils.round(j.getChartRect().
                            width *
                            (1 + ((double) resolution / 100))));
                    j.getChart().setHeight(Utils.round(j.getChartRect().
                            height *
                            (1 + ((double) resolution / 100))));

                    m = chart.image(j.getChart().getWidth(),
                                    j.getChart().getHeight());
                    if (chart.getParent() != null) {
                        chart.getParent().doChartPrint();
                    }
                    e.drawImage(m, j.getChartRect(),
                                j.getChart().getChartBounds());
                    j.getChart().setWidth(tmpChartWidth);
                    j.getChart().setHeight(tmpChartHeight);
                } else {
                    m = chart.image(j.getChartRect().width,
                                    j.getChartRect().height);
                    if (chart.getParent() != null) {
                        chart.getParent().doChartPrint();
                    }
                    e.drawImage(m, j.getChartRect(),
                                chart.getChartBounds());
                }
            } finally {
//                j.getChart().getGraphics3D().setMonochrome(oldMono);
                j.getChart().setPrinting(false);
            }
        }
    }

    private void printToFullPage(Object sender, PrintPage e) {
        Rectangle r = fitToBounds(e);
        Image m;

        if (resolution > 1) {
            m = chart.image(Utils.round(r.width *
                                             (1 + ((double) resolution / 100))),
                            Utils.round(r.height *
                                             (1 + ((double) resolution / 100))));

            //m=chart.Metafile(chart,chart.Width,chart.Height);
            if (chart.getParent() != null) {
                chart.getParent().doChartPrint();
            }
            e.drawImage(m, r, chart.getChartBounds());
        } else {
            m = chart.image(chartWidth, chartHeight);
            if (chart.getParent() != null) {
                chart.getParent().doChartPrint();
            }
            e.drawImage(m, r, chart.getChartBounds());
        }
        //chart.draw(e.Graphics,r); //bitmap to size
    }

    private Rectangle fitToBounds(PrintPage e) {

        Rectangle tmp = e.getMarginBounds();

        int printableWidth = tmp.getRight() - tmp.getLeft();
        int printableHeight = tmp.getBottom() - tmp.getTop();
        int tmpChartHeight;
        int tmpChartWidth;

        if (!proportional) {
            return new Rectangle(tmp.getLeft(),
                                 tmp.getTop(),
                                 tmp.getRight() - tmp.getLeft(),
                                 tmp.getBottom() - tmp.getTop());
        } else {
            if (chartHeight > chartWidth) {
                tmpChartHeight = printableHeight;
                if ((tmpChartHeight * chartWidthHeightRatio) < printableWidth) {
                    tmpChartWidth = (int) (tmpChartHeight *
                                           chartWidthHeightRatio);
                } else {
                    tmpChartWidth = printableWidth;
                    tmpChartHeight = (int) (printableWidth /
                                            chartWidthHeightRatio);
                }
            } else {
                tmpChartWidth = printableWidth;
                if ((printableWidth / chartWidthHeightRatio) < printableHeight) {
                    tmpChartHeight = (int) (printableWidth /
                                            chartWidthHeightRatio);
                } else {
                    tmpChartHeight = printableHeight;
                    tmpChartWidth = (int) (tmpChartHeight *
                                           chartWidthHeightRatio);
                }
            }

            Rectangle page = e.getPageBounds();

            if ((tmp.getLeft() == (page.width - tmp.getRight())) &&
                (tmp.getTop() == (page.height - tmp.getBottom()))) {
                return new Rectangle((page.width / 2) -
                                     (tmpChartWidth / 2),
                                     (page.height / 2) -
                                     (tmpChartHeight / 2),
                                     tmpChartWidth,
                                     tmpChartHeight);
            } else {
                return new Rectangle(tmp.getLeft() +
                                     ((tmp.getRight() -
                                       tmp.getLeft()) / 2) -
                                     (tmpChartWidth / 2),
                                     tmp.getTop() +
                                     ((tmp.getBottom() -
                                       tmp.getTop()) / 2) -
                                     (tmpChartHeight / 2),
                                     tmpChartWidth,
                                     tmpChartHeight);
            }
        }
    }
}

