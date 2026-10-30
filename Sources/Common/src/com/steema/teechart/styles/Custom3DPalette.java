package com.steema.teechart.styles;

import com.steema.teechart.editors.gallery.Gallery;
import com.steema.teechart.languages.Language;
import java.text.DecimalFormat;

import com.steema.teechart.drawing.Color;
import com.steema.teechart.Rectangle;
import com.steema.teechart.legend.LegendStyle;
import com.steema.teechart.legend.LegendTextStyle;
import com.steema.teechart.drawing.ChartPen;
import com.steema.teechart.IBaseChart;
import com.steema.teechart.drawing.Gradient;
import com.steema.teechart.drawing.ChartBrush;
import com.steema.teechart.drawing.IGraphics3D;
import com.steema.teechart.misc.Utils;
import com.steema.teechart.themes.ColorPalettes;
import com.steema.teechart.themes.Theme;

/**
 *
 * <p>Title: Custom3DPalette class</p>
 *
 * <p>Description: Custom3DPalette Series Style.</p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public class Custom3DPalette extends Custom3D {

    protected int iPaletteSteps = 32;
    protected boolean bUseColorRange = true;
    protected boolean bUsePalette;

    private Color startColor;
    private Color endColor;
    private Color midColor;
    protected boolean sameBrush;

    protected  boolean setSameBrush()
    {
      return (bUseColorRange || bUsePalette) && (getBrush().getImage() ==null);
    }
    private PaletteStyle paletteStyle;
    private double paletteMin;
    private double paletteStep;
    private double paletteRange;
    private boolean usePaletteMin;

    transient private PaletteList palette;

    private ChartPen pen;

    private double redFactor = 2.0;
    private double greenFactor = 1;
    private double blueFactor = 1;

    /**/
    transient private Color iMid, iEnd;
    transient private int iRangeA, iRangeR, iRangeG, iRangeB;
    transient private int iRangeMidA, iRangeMidR, iRangeMidG, iRangeMidB;

    transient private double iValueRangeInv;

    public Custom3DPalette(IBaseChart c) {
        super(c);
        initFields();
    }

    public Custom3DPalette() {
        this(null);
    }

    transient private ColorResolver colorResolver;

    public void setColorResolver(ColorResolver resolver) {
        this.colorResolver = resolver;
    }

    public void removeColorResolver() {
        this.colorResolver = null;
    }

    private void initFields() {
        palette = new PaletteList();
        paletteStyle = PaletteStyle.PALE;
        startColor = Color.NAVY;
        endColor = Color.WHITE;
        midColor = Color.TRANSPARENT;
        calcColorRange();
    }
    @Override
    protected int countLegendItems()
    {
      if ((getCount() > 0) && sameBrush)//CDI to get legend 'Palette' style to work with IsoSurface 
      {

        if (getChart().getLegend().getLegendStyle().equals(LegendStyle.PALETTE) || getChart().getLegend().getLegendStyle().equals(LegendStyle.AUTO))

        {
          if (getChart().getLegend().getMaxNumRows() <= palette.size())
            return getChart().getLegend().getMaxNumRows();
          else
            return palette.size();
        }
        else
        {
          return palette.size();
        }
      }
      else
      {
        return super.countLegendItems();
      }
    }
    protected Object readResolve() {
        initFields();
        return super.readResolve();
    }

    /**
     * Defines the Pen to draw the Surface polygons.<br>
     * You can make it invisible by turning the Pen.Visible to false.
     *
     * @return ChartPen
     */
    public ChartPen getPen() {
        if (pen == null) {
            pen = new ChartPen(chart, Color.BLACK);
        }
        return pen;
    }

    /**
     * Defines the range of colors in the default color Palette.<br>
     * Default value: O
     *
     * @return double
     */
    public double getPaletteRange() {
        return paletteRange;
    }

    /**
     * Sets the range of colors in the default color Palette.<br>
     * Default value: O
     *
     * @param value double
     */
    public void setPaletteRange(double value) {
        paletteRange = setDoubleProperty(paletteRange, value);
    }

    /**
     * The number of entries in the default color Palette.<br>
     * The greater the number of steps, the more colors will be used to fill
     * the Surface polygons. Setting this method forces Surface series to
     * recreate the default color palette.<br>
     * Default value: false
     *
     * @return int
     */
    public int getPaletteSteps() {
        return iPaletteSteps;
    }

    /**
     * Sets the number of entries in the default color Palette.<br>
     * Default value: false
     *
     * @param value int
     */
    public void setPaletteSteps(int value) {
        iPaletteSteps = value;
        createDefaultPalette(iPaletteSteps);
    }

    /**
     * Defines the alpha value for the Red of the ARGB component.<br>
     * Defalut value: 2
     *
     * @return double
     */
    public double getRedFactor() {
        return redFactor;
    }

    /**
     * Sets the alpha value for the Red of the ARGB component.<br>
     * Defalut value: 2
     *
     * @param value double
     */
    public void setRedFactor(double value) {
        redFactor = setDoubleProperty(redFactor, value);
    }

    /**
     * Defines the alpha value for the Green of the ARGB component.<br>
     * Default value: 1
     *
     * @return double
     */
    public double getGreenFactor() {
        return greenFactor;
    }

    /**
     * Sets the alpha value for the Green of the ARGB component.<br>
     * Defalut value: 1
     *
     * @param value double
     */
    public void setGreenFactor(double value) {
        greenFactor = setDoubleProperty(greenFactor, value);
    }

    /**
     * Defines the alpha value for the Blue of the ARGB component.<br>
     * Default value: 1
     *
     * @return double
     */
    public double getBlueFactor() {
        return blueFactor;
    }

    /**
     * Sets the alpha value for the Blue of the ARGB component.<br>
     * Default value: 1
     *
     * @param value double
     */
    public void setBlueFactor(double value) {
        blueFactor = setDoubleProperty(blueFactor, value);
    }

    protected void drawLegendShape(IGraphics3D g, int valueIndex,
                                   Rectangle rect) {
        if ((valueIndex == -1) && getUseColorRange()) {
            g.getBrush().setVisible(false);
            g.setPen(new ChartPen(Color.BLACK));
            g.rectangle(rect); // <-- rectangle

            Gradient tmpGrad = new Gradient();

            // set inverted Colors
            tmpGrad.setStartColor(getEndColor());
            tmpGrad.setMiddleColor(getMidColor());
            tmpGrad.setEndColor(getStartColor());
            rect.offset(1, 1);
            tmpGrad.draw(g, rect);

        } else {
            super.drawLegendShape(g, valueIndex, rect);
        }
    }

    /**
     * Determines the last Range palette color.<br>
     * When {@link Custom3DPalette#getUseColorRange} is true, Surface series
     * creates a color palette using StartColor, MidColor and EndColor methods.
     * <br>
     * Default value: WHITE
     *
     *
     * @return Color
     */
    public Color getEndColor() {
        return endColor;
    }

    /**
     * Sets the last Range palette color.<br>
     * Default value: WHITE
     *
     *
     * @param value Color
     */
    public void setEndColor(Color value) {
        endColor = setColorProperty(endColor, value);
        calcColorRange();
    }

    /**
     * Determines the middle Range palette color.<br>
     * When {@link Custom3DPalette#getUseColorRange} is true, Surface series
     * creates a color palette using StartColor, MidColor and EndColor methods.
     * <br>
     * Default value: Transparent
     *
     * @return Color
     */
    public Color getMidColor() {
        return midColor;
    }

    /**
     * Sets the middle Range palette color.<br>
     * Default value: Transparent
     *
     * @param value Color
     */
    public void setMidColor(Color value) {
        midColor = setColorProperty(midColor, value);
        calcColorRange();
    }

    /**
     * Determines 1 of 3 Colors used to create the palette to fill the Surface
     * polygons.<br>
     * When {@link Custom3DPalette#getUseColorRange} is true, Surface series
     * creates a color palette using StartColor, MidColor and EndColor methods.
     * <br>
     * Default value: NAVY
     *
     *
     * @return Color
     */
    public Color getStartColor() {
        return startColor;
    }

    /**
     * Sets 1 of 3 Colors used to create the palette to fill the Surface
     * polygons.<br>
     * Default value: NAVY
     *
     *
     * @param value Color
     */
    public void setStartColor(Color value) {
        startColor = setColorProperty(startColor, value);
        calcColorRange();
    }

    /**
     * Defines the Brush to fill the Surface polygons.<br>
     * It only works when {@link Custom3DPalette#getUsePalette} and
     * {@link Custom3DPalette#getUseColorRange} are false.
     *
     * @return ChartBrush
     */
    public ChartBrush getBrush() {
        return bBrush;
    }

    /**
     * Sets Pale or Strong color palette.<br>
     * Default value: PaletteStyles.Pale
     *
     * <p>Example:
     * <pre><font face="Courier" size="4">
     * series = new com.steema.teechart.styles.Surface(myChart.getChart());
     * series.setPaletteStyle(PaletteStyle.STRONG);
     * series.setUsePalette(true);
     * series.getPen().setVisible(false);
     * series.setPaletteSteps(15);
     * series.setUseColorRange(false);
     * series.fillSampleValues(30);
     * </font></pre></p>
     *
     * @param value PaletteStyles
     */
    public void setPaletteStyle(PaletteStyle value) {
        if (paletteStyle != value) {
            paletteStyle = value;
            clearPalette();
            invalidate();
        }
    }

    /**
     * Selects Pale or Strong color palette.<br>
     * Default value: PaletteStyles.Pale
     *
     * @return PaletteStyles
     */
    public PaletteStyle getPaletteStyle() {
        return paletteStyle;
    }

    /**
     * The interval amount between palette Colors.<br>
     * Use {@link Custom3DPalette#getPaletteMin} and
     * {@link Custom3DPalette#getPaletteSteps}<br>
     * Default value: 0
     *
     * @return double
     */
    public double getPaletteStep() {
        return paletteStep;
    }

    /**
     * Sets interval between palette Colors.<br>
     * Use {@link Custom3DPalette#getPaletteMin} and
     * {@link Custom3DPalette#getPaletteSteps}<br>
     * Default value: 0
     *
     * @param value double
     */
    public void setPaletteStep(double value) {
        paletteStep = setDoubleProperty(paletteStep, value);
    }

    /**
     * Access the Color Palette list
     *
     * @return PaletteList
     */
    public PaletteList getPalette()

    {
        return palette;
    }

    /**
     * The starting value used to calculate the color palette when
     * Custom3DPalette.UsePaletteMin is true.<br>
     * Default value: 0
     *
     * @see Custom3DPalette#getUsePaletteMin
     * @return double
     */
    public double getPaletteMin()

    {
        return paletteMin;
    }

    /**
     * Sets starting value used to calculate the color palette when
     * {@link Custom3DPalette#getUsePaletteMin} is true.<br>
     * Default value: 0
     *
     * @see Custom3DPalette#getUsePaletteMin
     * @param value double
     */
    public void setPaletteMin(double value) {
        paletteMin = setDoubleProperty(paletteMin, value);
    }

    protected void calcColorRange() {
        iEnd = getEndColor();

        if (!getMidColor().isNull()) {
            iMid = getMidColor();
            iRangeMidA = getStartColor().getAlpha() - iMid.getAlpha();
            iRangeMidR = getStartColor().getRed() - iMid.getRed();
            iRangeMidG = getStartColor().getGreen() - iMid.getGreen();
            iRangeMidB = getStartColor().getBlue() - iMid.getBlue();

            iRangeA = iMid.getAlpha() - iEnd.getAlpha();
            iRangeR = iMid.getRed() - iEnd.getRed();
            iRangeG = iMid.getGreen() - iEnd.getGreen();
            iRangeB = iMid.getBlue() - iEnd.getBlue();
        } else {
            iRangeA = getStartColor().getAlpha() - iEnd.getAlpha();
            iRangeR = getStartColor().getRed() - iEnd.getRed();
            iRangeG = getStartColor().getGreen() - iEnd.getGreen();
            iRangeB = getStartColor().getBlue() - iEnd.getBlue();
        }
    }

    /**
     * Deletes all Palette information.
     */
    public void clearPalette() {
        if (palette != null) {
            palette.clear();
        }
    }

    /**
     * Add Color to Palette list
     *
     * @param aValue double data value
     * @param aColor Color color
     * @return int New Palette Color Index
     */
    public int addPalette(double aValue, Color aColor) {
        for (int t = 0; t < palette.size() - 1; t++) {
            if (aValue < palette.getPalette(t).upToValue) {
                palette.add(t, new GridPalette(aValue, aColor));
                return t;
            }
        }

        palette.add(new GridPalette(aValue, aColor));
        return palette.size() - 1;
    }

    public void createSubGallery(Gallery addSubChart) {
        super.createSubGallery(addSubChart);
        addSubChart.createSubChart(Language.getString("ColorRange"));
    }

    protected void createDefaultPalette(int numSteps) {

        final double delta = 127.0;
        
        clearPalette();

        double scale = Math.PI / numSteps;

        if (paletteStyle == PaletteStyle.STRONG) {
            scale *= 2.0;
        } else if ((paletteStyle == PaletteStyle.GRAYSCALE)
                || (paletteStyle == PaletteStyle.INVERTED_GRAYSCALE)) {
            scale = 255.0 / numSteps;
        }

        double scaleValue = paletteStep;

        if (paletteStep == 0) {
            if (paletteRange == 0) {
                scaleValue = mandatory.getRange() / Math.max(1, numSteps - 1);
            } else {
                scaleValue = paletteRange / numSteps;
            }
        } else {
            scaleValue = paletteStep;
        }

        double tmpMin = usePaletteMin ? paletteMin : mandatory.getMinimum();

        Color tmpColor;

        for (int t = 0; t < numSteps; t++) {
            double tmp = scale * t;
            if (paletteStyle == PaletteStyle.RAINBOW) {
                tmpColor = Theme.RainbowPalette[t % Theme.RainbowPalette.length];
            } else if (paletteStyle == PaletteStyle.GRAYSCALE) {
                int colorInt = (int) (tmp);
                tmpColor = Color.fromArgb(colorInt, colorInt, colorInt);
            } else if (paletteStyle == PaletteStyle.INVERTED_GRAYSCALE) {
                int colorInt = 255 - (int) (tmp);
                tmpColor = Color.fromArgb(colorInt, colorInt, colorInt);
            } else {
                tmpColor = Color.fromArgb(
                        (int) (delta * (Math.sin(tmp / getRedFactor()) + 1)),
                        (int) (delta * (Math.sin(tmp / getGreenFactor()) + 1)),
                        (int) (delta * (Math.cos(tmp / getBlueFactor()) + 1)));
            }

            addPalette(tmpMin + scaleValue * t, tmpColor);
        }

        invalidate();
    }

    public void setSubGallery(int index) {
        if (index == 1) {
            setUseColorRange(true);
            setUsePalette(false);
        } else {
            super.setSubGallery(index);
        }
    }

    private Color getSurfacePaletteColor(double y) {
        for (int t = 0; t < palette.size(); t++) {
            GridPalette p = palette.getPalette(t);
            if (p.upToValue >= y) {
                return p.color;
            }
        }
        return palette.getPalette(palette.size() - 1).color;
    }

    private Color rangePercent(double percent) {
        if (midColor.isNull()) {
            return Color.fromArgb(iEnd.getAlpha(),
                                  (iEnd.getRed() +
                                   Utils.round(percent * iRangeR)),
                                  (iEnd.getGreen() +
                                   Utils.round(percent * iRangeG)),
                                  (iEnd.getBlue() +
                                   Utils.round(percent * iRangeB)));
        } else if (percent < 0.5) {
            return Color.fromArgb(iEnd.getAlpha(),
                                  (iEnd.getRed() +
                                   Utils.round((2.0 * percent) * iRangeR)),
                                  (iEnd.getGreen() +
                                   Utils.round((2.0 * percent) * iRangeG)),
                                  (iEnd.getBlue() +
                                   Utils.round((2.0 * percent) * iRangeB)));
        } else {
            return Color.fromArgb(iMid.getAlpha(),
                                  (iMid.getRed() +
                                   Utils.round(2.0 * (percent - 0.5) *
                    iRangeMidR)),
                                  (iMid.getGreen() +
                                   Utils.round(2.0 * (percent - 0.5) *
                    iRangeMidG)),
                                  (iMid.getBlue() +
                                   Utils.round(2.0 * (percent - 0.5) *
                    iRangeMidB)));
        }
    }

    /**
     * Gradient color palette to fill Surface polygons.<br>
     * The Palette is defined with three colors:
     * {@link Custom3DPalette#getStartColor}
     * {@link Custom3DPalette#getMidColor} and
     * {@link Custom3DPalette#getEndColor}. When both
     * {@link Custom3DPalette#getUseColorRange} and
     * {@link Custom3DPalette#getUsePalette} are false, all Surface
     * polygons are filled with Surface's Series.Color color. <br>
     * Default value: true
     *
     * @return boolean
     */
    public boolean getUseColorRange() {
        return bUseColorRange;
    }

    /**
     * Sets gradient color palette to fill Surface polygons.<br>
     * Default value: true
     *
     * @param value boolean
     */
    public void setUseColorRange(boolean value) {
        bUseColorRange = setBooleanProperty(bUseColorRange, value);
        if (value) {
            setColorEach(false);
        }

    }

    /**
     * Determines multi-color palette to fill Surface polygons.<br>
     * PaletteSteps determines the number of entries in the palette.<br>
     * Alternatively to {@link Custom3DPalette#getPaletteSteps} you may use
     * {@link Custom3DPalette#getUseColorRange}UseColorRange or the Series
     * ColorEachPoint method to define colour for the Surface Series.  <br>
     * Default value: false
     *
     * @return boolean
     */
    public boolean getUsePalette() {
        return bUsePalette;
    }

    /**
     * Sets multi-color palette to fill Surface polygons.<br>
     * Default value: false
     *
     * @param value boolean
     */
    public void setUsePalette(boolean value) {
        bUsePalette = setBooleanProperty(bUsePalette, value);
        if (value) {
            setColorEach(false);
            checkPaletteEmpty();
        }
    }

    /**
     * Forces the color palette to start at
     * {@link Custom3DPalette#getUsePaletteMin} value when true.<br>
     * Default value: false
     *
     * @see Custom3DPalette#getUsePaletteMin
     * @return boolean
     */
    public boolean getUsePaletteMin() {
        return usePaletteMin;
    }

    /**
     * Set to true to force the color palette to start at
     * {@link Custom3DPalette#getUsePaletteMin} value.<br>
     * Default value: false
     *
     * @see Custom3DPalette#getUsePaletteMin
     * @param value boolean
     */
    public void setUsePaletteMin(boolean value) {
        usePaletteMin = setBooleanProperty(usePaletteMin, value);
    }

    protected Color getValueColorValue(double aValue) {
        Color result=Color.EMPTY;
        if (bUseColorRange) {
            if (iValueRangeInv == 0) {
                return getEndColor();
            } else {
//                return rangePercent((aValue - mandatory.getMinimum()) *
//                                    iValueRangeInv);
              double tmp = (aValue - mandatory.getMinimum());
              if (tmp < 0)
                result = endColor;
              else
                if (aValue > mandatory.getMaximum())
                  result = startColor;
                else
                  result = rangePercent(tmp * iValueRangeInv);
                }
        } else 
        {
            if (getUsePalette() && (palette.size() > 0)) {
                return getSurfacePaletteColor(aValue);
            } else {
                return getColor();
            }
//          if (this.sameBrush)	//CDI to get legend 'Palette' style to work with IsoSurface 
//            result = GetSurfacePaletteColor(aValue);
//          else
//            result = Color;
        }
        
        return result;
    }

    /**
     * Returns the colour of the index point.<br>
     * Index gives the position of the point, where 0 is the first, 1 is the
     * second, and so on.
     *
     * @param valueIndex int
     * @return Color
     */
    public Color getValueColor(int valueIndex) {
        Color tmpResult;

        if ((iColors == null) || (iColors.getCount() < valueIndex)) {
          tmpResult = Color.EMPTY;  
        }
        else
        {
          tmpResult = iColors.getColor(valueIndex);
        }

        if (Utils.colorIsEmpty(tmpResult)) {
            if (bColorEach) {
                tmpResult = super.getValueColor(valueIndex);
            }
            else {
                if (sameBrush) {
                    tmpResult = getValueColorValue(mandatory.value[valueIndex]);
                }
                else {
                    if (Utils.colorIsEmpty(tmpResult) || !sameBrush) {
                        tmpResult = getColor();
                    }
                }
            }
        }

//        //*************
//            if ((!tmpResult.isNull()) && (!bColorEach)) {
//                if (bUseColorRange || bUsePalette) {
//                    tmpResult = getValueColorValue(mandatory.value[valueIndex]);
//                } else
//                if (tmpResult.isEmpty()) {
//                    tmpResult = getColor();
//                }
//            }
//        }
//        else
//          tmpResult = super.getValueColor(valueIndex);
//
//        if (colorResolver != null) {
//            tmpResult = colorResolver.getColor(this, valueIndex, tmpResult);
//        }

        return tmpResult;
    }

    public int getCountLegendItems() {
        if ((getCount() > 0) && (bUseColorRange || bUsePalette)) {
            if (chart.getLegend().getLegendStyle() ==
                com.steema.teechart.legend.LegendStyle.PALETTE
                ||
                chart.getLegend().getLegendStyle() ==
                com.steema.teechart.legend.LegendStyle.AUTO) {

                if (chart.getLegend().getMaxNumRows() <= palette.size()) {
                    return chart.getLegend().getMaxNumRows();
                } else {
                    return palette.size();
                }
            } else {
                return palette.size();
            }
        } else {
            return super.getCountLegendItems();
        }
    }

    /**
     * Returns LegendString for LegendIndexth item.
     *
     * @param legendIndex int
     * @param legendTextStyle LegendTextStyles
     * @return String
     */
    public String getLegendString(int legendIndex,
                               LegendTextStyle legendTextStyle) {
        String result = "";
        if (this.getCount() > 0 && (bUseColorRange || bUsePalette)) {
            int count = getCountLegendItems();
            if ((chart.getLegend().getLegendStyle() ==
                 com.steema.teechart.legend.LegendStyle.PALETTE
                 ||
                 chart.getLegend().getLegendStyle() ==
                 com.steema.teechart.legend.LegendStyle.AUTO) &&
                count > 0 && palette.size() > 0) {

                int last = count - 1;
                count = (palette.size() - 1) / (count - 1);
                count = count * (last - legendIndex);

                if (legendIndex == 0) {
                    double tmp = palette.getPalette(palette.size() - 1).
                                 upToValue;
                    result = (new DecimalFormat(getValueFormat())).format(tmp);
                } else if (legendIndex == last) {
                    double tmp = palette.getPalette(0).upToValue;
                    result = (new DecimalFormat(getValueFormat())).format(tmp);
                } else {
                    double tmp = palette.getPalette(count).upToValue;
                    result = (new DecimalFormat(getValueFormat())).format(tmp);
                }
            } else if (chart.getLegend().getLegendStyle() ==
                       com.steema.teechart.legend.LegendStyle.LASTVALUES
                       && palette.size() > 0) {

                double tmp = palette.getPalette(0).upToValue;
                result = (new DecimalFormat(getValueFormat())).format(tmp);
            } else if (palette.size() > 0) {
                double tmp = palette.getPalette(palette.size() - legendIndex -
                                                1).upToValue;
                result = (new DecimalFormat(getValueFormat())).format(tmp);
            }

        } else {
            return super.getLegendString(legendIndex, legendTextStyle);
        }

        return result;
    }

    public Color legendItemColor(int legendIndex) {
        Color result = Color.EMPTY;
        if (getCount() > 0 && (bUseColorRange || bUsePalette)) {
            if (chart.getLegend().getLegendStyle() ==
                com.steema.teechart.legend.LegendStyle.PALETTE
                ||
                chart.getLegend().getLegendStyle() ==
                com.steema.teechart.legend.LegendStyle.AUTO) {

                int count = getCountLegendItems();
                int last = count - 1;
                count = (palette.size() - 1) / (count - 1);
                count *= (last - legendIndex);

                if (legendIndex == 0) {
                    result = getValueColorValue(palette.getPalette(palette.size() -
                            1).upToValue);
                } else if (legendIndex == last) {
                    result = getValueColorValue(palette.getPalette(0).upToValue);
                } else {
                    result = getValueColorValue(palette.getPalette(count).
                                                upToValue);
                }
            } else {
                result = getValueColorValue(palette.getPalette(palette.size() -
                        legendIndex - 1).upToValue);
            }
        } else {
            result = super.legendItemColor(legendIndex);
        }
        return result;
    }

    public void setChart(IBaseChart c) {
        super.setChart(c);
        if (pen != null) {
            pen.setChart(c);
        }
    }

    public void prepareForGallery(boolean isEnabled) {
        super.prepareForGallery(isEnabled);
        setUseColorRange(false);
        getPen().setColor(isEnabled ? Color.BLACK : Color.GRAY);
        setUsePalette(isEnabled);
    }

    private void checkPaletteEmpty() {
        if ((getCount() > 0) && (palette.size() == 0)) {
            createDefaultPalette(iPaletteSteps);
        }
    }

    public void doBeforeDrawChart() {
        super.doBeforeDrawChart();
        checkPaletteEmpty();

        sameBrush = setSameBrush();
        iValueRangeInv = mandatory.getRange();
        if (iValueRangeInv != 0) {
            iValueRangeInv = 1 / iValueRangeInv;
        }
    }

    public void galleryChanged3D(boolean is3D) {
        super.galleryChanged3D(is3D);
        chart.getAspect().setZoom(70);
        chart.getAspect().setVertOffset( -2);
    }

    public interface ColorResolver {
        public Color getColor(ISeries series, int valueIndex, Color color);
    }
}

