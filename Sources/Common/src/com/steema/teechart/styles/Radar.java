package com.steema.teechart.styles;


import com.steema.teechart.languages.Language;
import com.steema.teechart.IBaseChart;

/**
 *
 * <p>Title: Radar class</p>
 *
 * <p>Description: Radar Series.</p>
 *
 * <p>Example:
 * <pre><font face="Courier" size="4">
 * series1 = new com.steema.teechart.styles.Radar(myChart.getChart());
 * series1.setCircleBackColor(Color.WHITE);
 * series1.setCircleLabels(true);
 * series1.getCircleLabelsFont().setItalic(true);
 * series1.getCirclePen().setColor(Color.AQUA);
 * </font></pre></p>

 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights
 * Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public class Radar extends CustomPolar {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Radar() {
        this((IBaseChart)null);
    }

    public Radar(IBaseChart c) {
        super(c);
    }

    protected void addSampleValues(int numValues) {
        String[] sample = new String[5];
        sample[0] = Language.getString("PieSample1");
        sample[1] = Language.getString("PieSample2");
        sample[2] = Language.getString("PieSample3");
        sample[3] = Language.getString("PieSample4");
        sample[4] = Language.getString("PieSample5");
        SeriesRandom r = randomBounds(numValues);
        for (int t = 0; t < numValues; t++) {
            add(1000 * r.Random(), sample[t % 5]);
        }
    }

    public void doBeforeDrawChart() {
        super.doBeforeDrawChart();

        setRotationAngle(90);
        iMaxValuesCount = chart.getMaxValuesCount();

        if (iMaxValuesCount > 0) {
            setAngleIncrement(360.0 / iMaxValuesCount);
        }
    }

    protected String getCircleLabel(double angle, int index) {
        String result = sLabels.getString(index);
        if (result.length() == 0) {
            return Integer.toString(index);
        } else {
            return result;
        }
    }

    protected double getXValue(int valueIndex) {
        return (iMaxValuesCount > 0) ? valueIndex * (360.0 / iMaxValuesCount) :
                0;
    }

    public void prepareForGallery(boolean isEnabled) {
        super.prepareForGallery(isEnabled);
        fillSampleValues();
    }

    protected int numSampleValues() {
        return 5;
    }

    /**
     * Gets descriptive text.
     *
     * @return String
     */
    public String getDescription() {
        return Language.getString("GalleryRadar");
    }
}

