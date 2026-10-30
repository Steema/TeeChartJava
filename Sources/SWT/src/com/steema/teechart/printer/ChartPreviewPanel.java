package com.steema.teechart.printer;

import java.awt.Graphics;
import java.awt.print.*;
import java.awt.Graphics2D;
import java.util.Iterator;
import java.util.Vector;

import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Composite;

import java.awt.image.BufferedImage;
import org.eclipse.swt.layout.FillLayout;

import com.steema.teechart.swt.Graphics3DSWT;
import com.steema.teechart.Chart;
import com.steema.teechart.Rectangle;
import com.steema.teechart.Shadow;
import com.steema.teechart.drawing.ChartPen;
import com.steema.teechart.drawing.DashStyle;
import com.steema.teechart.drawing.Gradient;
import com.steema.teechart.drawing.GradientDirection;
import com.steema.teechart.drawing.IGraphics3D;
import com.steema.teechart.drawing.Image;
import com.steema.teechart.misc.ImageUtils;
import com.steema.teechart.misc.MathUtils;


public class ChartPreviewPanel extends Composite implements Printable {

	private Chart chart;
	private Chart origChart;
	private double chartWidthHeightRatio;

	transient protected IGraphics3D graphics3D;
	private GC g;
	private Device device;

	private boolean allowResize = true;
	private boolean allowMove = true;
	private boolean asBitmap = false;
	private Vector panels;
	private boolean dragImage = false;
	private PreviewChartPen margins;
	private int orientation = PageFormat.PORTRAIT;

	private com.steema.teechart.drawing.Color paperColor = 
		com.steema.teechart.drawing.Color.WHITE;
	private Shadow paperShadow;
	private Gradient gradient;
	private boolean showImage = true;
	//private Composite RenderPanel1;
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

	public ChartPreviewPanel(Composite parent, int style, Chart c) {
		super(parent, style);

		chart = c;
		origChart = c;
		
		chartWidthHeightRatio=(double)origChart.getChartBounds().width/(double)origChart.getChartBounds().height;

		initGUI();
		
    prn = chart.getPrinter();

    panels = new Vector();
    panels.add(chart);

    dragImage = false;
    orientation = PageFormat.PORTRAIT;

    margins = new PreviewChartPen(new ChartPen(com.steema.teechart.drawing.Color.BLACK));
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
    
		addPaintListener(new PaintListener() {

			public void paintControl(PaintEvent e) {
				paint(chart.getChartBounds(),e);
			}

		});

	}

	private void initGUI() {
		try {
			FillLayout thisLayout = new FillLayout(org.eclipse.swt.SWT.HORIZONTAL);
			this.setLayout(thisLayout);
			this.setSize(438, 210);
			this.layout();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
  private void preparePanel() {
    gradient = new Gradient();
    gradient.setStartColor(com.steema.teechart.drawing.Color.GRAY);
    gradient.setDirection(GradientDirection.BACKDIAGONAL);
    gradient.setVisible(true);

    paperShadow = new Shadow(chart, 4, com.steema.teechart.drawing.Color.DARK_GRAY);
    paperShadow.setTransparency(70);
 }

	public void checkGraphics3D() {
		if (graphics3D == null) {
			setGraphics3D(new com.steema.teechart.swt.Graphics3DSWT(chart));
		}
	}

	public void setGraphics3D(IGraphics3D value) {
		if (value != null) {
			graphics3D = value;
		}
	}

	public IGraphics3D getGraphics3D() {
		checkGraphics3D();
		return graphics3D;
	}

	private Rectangle getClientRect() {
		org.eclipse.swt.graphics.Rectangle r = getBounds();
		return new Rectangle(r.x, r.y, r.width, r.height);
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

  public com.steema.teechart.drawing.Color getPaperColor() {
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

  		redraw(); //invalidate();
  	}
  }
  
  private Chart getPanel() {
  	/*    if ((panels!=null) && (getPanels().getCount()>0))
            return getPanels[0];
        else*/
  	return chart;
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
			MathUtils.round(clientHeight / margin);
			tmpTop = (MathUtils.round(clientHeight / (2 * margin)));
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
			MathUtils.round(clientWidth / margin);
			tmpLeft = (MathUtils.round(clientWidth / (2 * margin)));
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

	private Color color(java.awt.Color value) {
		if ((g!=null) && (!g.isDisposed()))
			g.setAlpha(value.getAlpha());
		return new Color(device, value.getRed(), value.getGreen(), value.getBlue());
	}


	public void drawPaper(GC g) {
		getPaperShadow().draw(getGraphics3D(), paperRect);
		Rectangle r = new Rectangle(paperRect);
		g.setBackground(color(com.steema.teechart.drawing.Color.WHITE));
		g.setAlpha(255);

		g.fillRectangle(new org.eclipse.swt.graphics.Rectangle(paperRect.getLeft(), paperRect.getTop(), paperRect.width,
				paperRect.height));
	}

	protected void drawMargins(Rectangle r, GC g) {
		if (margins.getVisible()) {
			g.setForeground(color(margins.getColor()));
			g.setLineDash(new int[]{1,2});
			//vert left
			g.drawLine(imageRect.getLeft(), paperRect.getTop(),
					imageRect.getLeft(), paperRect.getBottom());
			//vert right
			g.drawLine(imageRect.getRight(), paperRect.getTop(),
					imageRect.getRight(), paperRect.getBottom());
			//horiz top
			g.drawLine(paperRect.getLeft(), imageRect.getTop(),
					paperRect.getRight(), imageRect.getTop());
			//horiz bottom
			g.drawLine(paperRect.getLeft(), imageRect.getBottom(),
					paperRect.getRight(), imageRect.getBottom());
		}
	}


	public void setChart(Chart chart) {
		this.chart = chart;
		if (chart != null) {
			this.redraw();
		}
	}

	private Image getPrintingImage(Chart chart, Rectangle imageRect) {
		int winWidth = 0;
		int winHeight = 0;
		int tmpW = 0;
		int tmpH = 0;

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

			com.steema.teechart.drawing.Color oldColor = tmpChart.getPanel().getColor();
			tmpChart.getPanel().setColor(paperColor);
			tmpImage =tmpChart.image(tmpWidth, tmpHeight);
			tmpChart.getPanel().setColor(oldColor);
			return tmpImage;
		}

		catch (Exception err) {}
		return null;
	}

	private Rectangle fitToBounds(Rectangle boundingRect, Chart origChart)
	{
      int printableWidth=boundingRect.width;
      int printableHeight=boundingRect.height;
      int tmpChartHeight;
      int tmpChartWidth;

      if (!prn.getProportional())
        return boundingRect;
      else 
      {
        if (origChart.getChartBounds().height>origChart.getChartBounds().width) 
        {
          tmpChartHeight=printableHeight;
          if ((tmpChartHeight*chartWidthHeightRatio)<printableWidth) 
          {
            tmpChartWidth=(int)Math.round(tmpChartHeight*chartWidthHeightRatio);
          }
          else 
          {
            tmpChartWidth=printableWidth;
            tmpChartHeight=(int)Math.round(printableWidth/chartWidthHeightRatio);
          }
        }
        else 
        {
          tmpChartWidth=printableWidth;
          if ((printableWidth/chartWidthHeightRatio)<printableHeight) 
          {
            tmpChartHeight=(int)Math.round(printableWidth/chartWidthHeightRatio);
          }
          else 
          {
            tmpChartHeight=printableHeight;
            tmpChartWidth=(int)Math.round(tmpChartHeight*chartWidthHeightRatio);
          }
        }

				int x =boundingRect.x+((int)Math.round((boundingRect.getRight()-boundingRect.x)/2.0F)-(int)Math.round(tmpChartWidth/2.0F));
				int y =boundingRect.y+((int)Math.round((boundingRect.getBottom()-boundingRect.y)/2.0F)-(int)Math.round(tmpChartHeight/2.0F));

				return new Rectangle(x,y,tmpChartWidth,tmpChartHeight);
      }
	}

	public void paint(Rectangle r,PaintEvent e) {

		if (prn == null) {
			return;
		}

		if (!prn.isPrinting) {
			clientWidth = getClientRect().width;
			clientHeight = getClientRect().height;
			calcPaperRectangles();
			getGraphics3D().setGraphics(e.gc);
			drawPaper(e.gc);

			imageRect = fitToBounds(prn.calcImagePrintMargins(origChart, paperRect),origChart);

			drawMargins(imageRect, e.gc);
		} else {
			calcPaperRectangles();
			imageRect = prn.calcImagePrintMargins(getPanel(), paperRect);
		}

		if (!prn.isPrinting) {
			if (e.gc != null) {
				Chart tmpChart = null;
				Iterator t = panels.iterator();
				try {
					while (t.hasNext()) {

						tmpChart = (Chart) t.next();

						Graphics3DSWT g3d = new Graphics3DSWT(chart);
						g3d.setGraphics(e.gc);
						tmpChart.setGraphics3D(g3d);

						tmpChart.paint(g3d, imageRect);
					}
				} catch (Exception err) {}
			}
		} else {
			if (g != null) {
				Chart tmpChart = null;
				Iterator t = panels.iterator();
				try {
					while (t.hasNext()) {
						tmpChart = (Chart) t.next();
						tmpChart.getGraphics3D().setGraphics(g);
						tmpChart.paint(tmpChart.getGraphics3D(), imageRect);
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
            imageRect = fitToBounds(prn.calcImagePrintMargins(origChart, paper, pageFormat),origChart);
        } else {
//            if (pageFormat.getOrientation() == PageFormat.LANDSCAPE) {
//                renderRect = new Rectangle((int) paper.getImageableX(),
//                        (int) paper.getImageableY(),
//                        (int) paper.getImageableHeight(),
//                        (int) paper.getImageableWidth());
//            } else {
//                renderRect = new Rectangle((int) paper.getImageableX(),
//                        (int) paper.getImageableY(),
//                        (int) paper.getImageableWidth(),
//                        (int) paper.getImageableHeight());
//            }
//            imageRect = prn.calcImagePrintMargins(getPanel(), renderRect);
    			calcPaperRectangles();
    			imageRect = prn.calcImagePrintMargins(getPanel(), paperRect);
        }

        if (g != null) {
            Chart tmpChart = null;
            Iterator t = panels.iterator();
            try {
                while (t.hasNext()) {
                	
                    tmpChart = (Chart) t.next();
                    
                    Graphics3DSWT g3d = new Graphics3DSWT(chart);
                    tmpChart.setGraphics3D(g3d);
        						g3d.setGraphics(g);
                    
                    Graphics2D g2d = (Graphics2D) g;
                    
                    if (pageFormat.getOrientation() ==
                        PageFormat.LANDSCAPE) {
                        g2d.translate((int) paper.getWidth(), 0);
                        g2d.rotate(90.0 * Math.PI / 180.0);
                    }

                    com.steema.teechart.drawing.Color tmpColor = com.steema.teechart.drawing.Color.EMPTY;
                    boolean noBackground = false;
                    boolean gradientVisible = chart.getPanel().getGradient().getVisible();
                    int bevelWidth = chart.getPanel().getBevel().getWidth();
                    if ((!chart.getPanel().getGradient().getVisible())
                        || (!prn.getPrintPanelBackground())) {
                        noBackground = true;
                        chart.getPanel().getGradient().setVisible(false);
                        chart.getPanel().getBevel().setWidth(0);
                        tmpColor = tmpChart.getPanel().getColor();
                        tmpChart.getPanel().setColor(com.steema.teechart.drawing.Color.WHITE);
                    }

                    if (prn.getGrayscale()) {
                    	BufferedImage m=tmpChart.image(imageRect.width,
                                imageRect.height).grayBuffered();
                        g.drawImage(m.getScaledInstance(imageRect.width,
                                -1, java.awt.Image.SCALE_SMOOTH),
                                    imageRect.getLeft(),
                                    imageRect.getTop(),
                                    imageRect.width, imageRect.height, null);
                    } else {
                    	BufferedImage m = ImageUtils.bufferedFrom(tmpChart.image(imageRect.width, imageRect.height));
                    	
                    	if (tmpChart.getParent() != null) {
                    		tmpChart.getParent().doChartPrint();
                    	}
                    	
                    	g.drawImage(m,imageRect.getLeft(),
                          imageRect.getTop(),
                          imageRect.width, imageRect.height,null);
                    	
//                    	g.drawImage(m.getScaledInstance(imageRect.width, -1, java.awt.Image.SCALE_SMOOTH),
//                          imageRect.getLeft(),
//                          imageRect.getTop(),
//                          imageRect.width, imageRect.height, null);
                    	
                       if (noBackground) {
                      	   chart.getPanel().getBevel().setWidth(bevelWidth);
                           chart.getPanel().setColor(tmpColor);
                           chart.getPanel().getGradient().setVisible(gradientVisible);
                       }
                   }
                }
            } catch (Exception err) {}

        }
    }

    return (PAGE_EXISTS);
}
}

