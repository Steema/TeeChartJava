package com.steema.teechart.styles;

import com.steema.teechart.Chart;
import com.steema.teechart.IBaseChart;
import com.steema.teechart.Rectangle;
import com.steema.teechart.axis.Axis;
import com.steema.teechart.drawing.Color;
import com.steema.teechart.drawing.Graphics3D;
import com.steema.teechart.drawing.IGraphics3D;
import com.steema.teechart.drawing.StringAlignment;
import com.steema.teechart.editors.gallery.Gallery;
import com.steema.teechart.languages.Language;
import com.steema.teechart.tools.AnnotationPosition;
import com.steema.teechart.tools.Marker;
import com.steema.teechart.tools.MarkersCollection;
import com.steema.teechart.tools.Tool;

public class NumericGauge extends CustomGauge {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NumericGauge() {
        this((Chart) null);
    }

    public NumericGauge(IBaseChart c) {
        super(c);
        add(0);
        setGaugeColorPalette(LCDPalette);
        fmaximum = Double.POSITIVE_INFINITY;
        valueFormat = "N";
        markers = new MarkersCollection(c);
        valueMarker = new Marker("", 36, AnnotationPosition.CENTER, StringAlignment.FAR, CustomGauge.getGaugePaletteColor(20, getGaugeColorPalette()), CustomGauge.getGaugePaletteColor(4, getGaugeColorPalette()));
        markers.add(valueMarker);
        unitsMarker = new Marker("MHz", 18, AnnotationPosition.LEFTBOTTOM, StringAlignment.CENTER, CustomGauge.getGaugePaletteColor(20, getGaugeColorPalette()), CustomGauge.getGaugePaletteColor(4, getGaugeColorPalette()));
        markers.add(unitsMarker);
        textMarker = new Marker("FREQ", 11, AnnotationPosition.LEFTTOP, StringAlignment.CENTER, CustomGauge.getGaugePaletteColor(4, getGaugeColorPalette()), CustomGauge.getGaugePaletteColor(20, getGaugeColorPalette()));
        markers.add(textMarker);

        getFaceBrush().getGradient().setVisible(true);
    }
    private Marker valueMarker;
    private Marker unitsMarker;
    private Marker textMarker;
    public MarkersCollection markers;
    
    public void assign(Series source) {
        if (source instanceof NumericGauge) {
            NumericGauge tmp = (NumericGauge)source;
            tmp.setChart(((NumericGauge) source).getChart());
            tmp.setMarkersCollection(((NumericGauge)source).getMarkersCollection());
            tmp.setTextMarker(((NumericGauge)source).getTextMarker());
            tmp.setUnitsMarker(((NumericGauge)source).getUnitsMarker());
            tmp.setValueMarker(((NumericGauge)source).getValueMarker());
        }
        super.assign(source);
    }     

    public void setChart(IBaseChart value) {
        super.setChart(value);
        if (value != null)
        {
            useAxis = false;
            setShowInLegend(false);
            calcVisiblePoints = false;
            getFrame().setChart(value);
            getMarkersCollection().setParentChart(value);

        }
    }

    public double truncate(double d) {
        d = d * 100;
        Float f = new Float(d);
        d = f.intValue();
        d = d / 100;
        return d;
    }

    private void setDefaultAxis() {
        if (getAxis() != null) {
            getAxis().getAxisPen().setVisible(true);
            getAxis().getLabels().setVisible(true);
            getAxis().getLabels().getFont().setColor(Color.RED);
            getAxis().getTitle().getFont().setColor(Color.RED);
            getAxis().getTitle().setVisible(true);
            getAxis().getLabels().getFont().setBold(true);
            getAxis().getLabels().getFont().setSize(12);
            getAxis().getLabels().getFont().setName("Arial");
            getAxis().getTicks().setLength(4);
            getAxis().getMinorTicks().setLength(2);
        }
    }

    protected void drawFace(IGraphics3D g) {
        g.rectangle(iNewRectangle);
    }

    public Axis getAxis() {
        return getHorizAxis();
    }

    public void prepareForGallery(boolean isEnabled) {
        super.prepareForGallery(isEnabled);
        unitsMarker.setActive(false);
        textMarker.setActive(false);
        valueMarker.getShape().getFont().setSize(7);
        valueMarker.setCentered(true);
        valueMarker.getShape().getShadow().setVisible(false);
        valueMarker.getShape().getPen().setVisible(false);
        valueMarker.setText("360");
    }

    public void dispose() 
    {
        for (int i = 0; i < markers.size(); ++i) {
            markers.getMarker(i).dispose();
        }
        super.dispose();
    }

    protected void drawHand(IGraphics3D g) {
        Marker marker;
        int i = 0;
        Tool s;
        for (i = 0; i < markers.size(); i++) {
            s = markers.getTool(i);
            if (s.getActive()) {
                marker = (Marker) s;
                marker.iRectangle = (Rectangle)iNewRectangle.copy();
                if (marker.getUsePalette()) {
                    if (marker == textMarker) {
                        marker.getShape().getFont().setColor(CustomGauge.getGaugePaletteColor(4, getGaugeColorPalette()));
                        marker.getShape().setColor(CustomGauge.getGaugePaletteColor(20, getGaugeColorPalette()));
                    } else {
                        marker.getShape().getFont().setColor(CustomGauge.getGaugePaletteColor(20, getGaugeColorPalette()));
                        marker.getShape().setColor(CustomGauge.getGaugePaletteColor(4, getGaugeColorPalette()));
                    }
                }
                marker.getShape().getFont().setName("Arial");
                
                if(getValueMarker() == marker) {
                    getValueMarker().setText(Double.toString(truncate(getValue())));
                    getValueMarker().drawText((Graphics3D) g);
                }
            }
        }
    }


    public void createSubGallery(Gallery AddSubChart) {
        super.createSubGallery(AddSubChart);
    }
    
     /**
     * Gets descriptive text.
     *
     * @return String
     */
    public String getDescription()
    {
        return Language.getString("GalleryNumericGauge");
    }

    protected void calcOrigRectangle() {
        super.calcOrigRectangle();
        if (getCustomBounds().isEmpty()) {

            boolean canMix = true;
            int maxHeight = iOrigRectangle.height / 2;
            int height,   width,   x,   y;
            int seriesCount = getChart().getSeries().size();
            int seriesIndex = getChart().getSeries().indexOf(this);
            int step = iOrigRectangle.height / seriesCount;
            for (int t = 0; t < seriesCount; ++t) {
                if (!(super.getChart().getSeries(t) instanceof CustomGauge)) {
                    canMix = false;
                    break;
                }
            }
            if (canMix) {
                height = Math.min(maxHeight, step);
                height -= 3;
                width = Math.round((iOrigRectangle.width / 3) * 2);
                x = iOrigRectangle.x + Math.round(iOrigRectangle.width / 6);
                y = iOrigRectangle.y;
                if (seriesCount == 1) {
                    y += (maxHeight / 2);
                } else {
                    y += step * seriesIndex;
                }

                iOrigRectangle = new Rectangle(x, y, width, height);
            }
        }
    }

    public Marker getValueMarker() {
        return valueMarker;
    }

    public Marker getTextMarker() {
        return textMarker;
    }

    public Marker getUnitsMarker() {
        return unitsMarker;
    }

    public void setValueMarker(Marker value) {
        valueMarker = value;
    }

    public void setTextMarker(Marker value) {
        textMarker = value;
    }

    public void setUnitsMarker(Marker value) {
        unitsMarker = value;
    }

    public MarkersCollection getMarkersCollection() {
        if (markers == null) {
            markers = new MarkersCollection(getChart());
        }
        return markers;
    }

    public void setMarkersCollection(MarkersCollection value) {
        markers = value;
    }
    public static Color[] LCDPalette = new Color[]{
        Color.fromArgb(40, 40, 40),
        Color.fromArgb(50, 50, 50),
        Color.fromArgb(100, 100, 100),
        
        Color.fromArgb(170, 170, 130), //3
        Color.fromArgb(170, 170, 130),
        Color.fromArgb(170, 170, 130),
        
        Color.fromArgb(100, 40, 40), //6
        Color.EMPTY,
        Color.fromArgb(100, 100, 100),
        
        Color.fromArgb(100, 100, 100), //9     
        Color.EMPTY,
        Color.fromArgb(30, 200, 30),
        
        Color.fromArgb(200, 115, 60), //12       
        Color.fromArgb(200, 115, 60),
        Color.fromArgb(200, 115, 60),
        Color.fromArgb(150, 50, 10),
        
        Color.fromArgb(130, 130, 130), //16       
        Color.fromArgb(130, 130, 130),
        Color.EMPTY,
        Color.fromArgb(30, 30, 30),
        
        Color.fromArgb(40, 40, 40),
        Color.fromArgb(100, 100, 100),
        
        Color.fromArgb(100, 100, 100),
        Color.fromArgb(100, 100, 100)
    };
    public static Color[] LEDPalette = new Color[]{
        Color.fromArgb(5, 55, 125),
        Color.fromArgb(10, 120, 200),
        Color.fromArgb(5, 90, 160),
        Color.fromArgb(10, 10, 10), //3
        Color.fromArgb(10, 10, 10),
        Color.fromArgb(10, 10, 10),
        Color.fromArgb(100, 40, 40), //6
        Color.EMPTY,
        Color.fromArgb(100, 100, 100),
        Color.fromArgb(100, 100, 100), //9   
        Color.EMPTY,
        Color.fromArgb(30, 200, 30),
        Color.fromArgb(200, 115, 60), //12
        Color.fromArgb(200, 115, 60),
        Color.fromArgb(200, 115, 60),
        Color.fromArgb(150, 50, 10),
        Color.fromArgb(130, 130, 130), //16
        Color.fromArgb(130, 130, 130),
        Color.EMPTY,
        Color.fromArgb(30, 30, 30),
        Color.fromArgb(255, 30, 30),
        Color.fromArgb(100, 100, 100),
        Color.fromArgb(100, 100, 100),
        Color.fromArgb(100, 100, 100)
    };

    public void setTextMarker(String value) {
        valueMarker.setText(value);
    }
}
