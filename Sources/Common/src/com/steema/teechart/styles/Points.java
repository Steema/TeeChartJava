package com.steema.teechart.styles;


import com.steema.teechart.languages.Language;
import com.steema.teechart.drawing.Color;
import com.steema.teechart.IBaseChart;
import com.steema.teechart.drawing.ChartPen;
import com.steema.teechart.editors.gallery.Gallery;

/**
 *
 * <p>Title: Points class</p>
 *
 * <p>Description: Point Series.</p>
 *
 * <p>Example:
 * <pre><font face="Courier" size="4">
 *  pointSeries = new com.steema.teechart.styles.Points(myChart.getChart());
 *  pointSeries.fillSampleValues(20);
 * </font></pre></p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public class Points extends CustomPoint {
	
	private static final long serialVersionUID = 1L;

    public Points(IBaseChart c) {
        super(c);

        if (point == null) {
            getPointer().setColor(Color.EMPTY); // initialize, change White to Empty
        }
        getMarks().getCallout().setDefaultLength(0);
    }

    public Points() {
        this((IBaseChart)null);
    }
    
    public void assign(Series source) {
        if (source instanceof Points) {
            Points tmp = (Points)source;
            tmp.setColor(((Points)source).getColor());
            tmp.setColorEach(((Points) source).getColorEach());  
        }
        super.assign(source);
    }     

    /**
     * Sets the Pen for the Point connecting Lines.
     *
     * @return ChartPen
     */
    public ChartPen getLinePen() {
        return super.getLinePen();
    }

    public Color getColor() {
        return (point == null) ? Color.EMPTY : getPointer().getColor();
    }

    public void setColor(Color value) {
        super.setColor(value);
        getPointer().setColor(value);
    }

    public void setColorEach(boolean value) {
        super.setColorEach(value);
        if (value) {
            point.getBrush().setForegroundColor(Color.EMPTY);
        }
    }

    /**
     * Gets descriptive text.
     *
     * @return String
     */
    public String getDescription() {
        return Language.getString("GalleryPoint");
    }

    protected boolean canDoExtra() {
        return true;
    }

    public void createSubGallery(Gallery addSubChart) {
        super.createSubGallery(addSubChart);

        addSubChart.createSubChart(Language.getString("Colors"));
        addSubChart.createSubChart(Language.getString("Marks"));
        addSubChart.createSubChart(Language.getString("Hollow"));
        addSubChart.createSubChart(Language.getString("NoBorder"));
        addSubChart.createSubChart(Language.getString("Gradient"));

        if (canDoExtra()) {
            addSubChart.createSubChart(Language.getString("Point2D"));
            addSubChart.createSubChart(Language.getString("Triangle"));
            addSubChart.createSubChart(Language.getString("Star"));
            addSubChart.createSubChart(Language.getString("Circle"));
            addSubChart.createSubChart(Language.getString("DownTri"));
            addSubChart.createSubChart(Language.getString("Cross"));
            addSubChart.createSubChart(Language.getString("Diamond"));
        }
    }

    public void setSubGallery(int index) {
        switch (index) {
        case 1:
            setColorEach(true);
            break;
        case 2:
            getMarks().setVisible(true);
            break;
        case 3:
            getPointer().getBrush().setVisible(false);
            break;
        case 4:
            getPointer().getPen().setVisible(false);
            break;
        case 5:
            getPointer().getGradient().setVisible(true);
            break;
        default: {
            if (canDoExtra()) {
                switch (index) {
                case 6:
                    getPointer().setDraw3D(false);
                    break;
                case 7:
                    getPointer().setStyle(PointerStyle.TRIANGLE);
                    break;
                case 8:
                    getPointer().setStyle(PointerStyle.STAR);
                    break;
                case 9: {
                    getPointer().setStyle(PointerStyle.CIRCLE);
                    getPointer().setHorizSize(8);
                    getPointer().setVertSize(8);
                }
                break;
                case 10:
                    getPointer().setStyle(PointerStyle.DOWNTRIANGLE);
                    break;
                case 11:
                    getPointer().setStyle(PointerStyle.CROSS);
                    break;
                case 12:
                    getPointer().setStyle(PointerStyle.DIAMOND);
                    break;
                default:
                    break;
                }
            }
        }
        break;
        }
    }
}

