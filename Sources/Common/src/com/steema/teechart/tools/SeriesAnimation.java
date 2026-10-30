package com.steema.teechart.tools;

import com.steema.teechart.events.DefaultResolvers;
import com.steema.teechart.languages.Language;

import com.steema.teechart.styles.ValueList;
import com.steema.teechart.IBaseChart;
import com.steema.teechart.events.NumberResolver;
import com.steema.teechart.styles.Series;

/**
 *
 * <p>Title: SeriesAnimation class</p>
 *
 * <p>Description: Series Animation tool.</p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public class SeriesAnimation extends ToolSeries {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Series backup;
    private int drawEvery;
    private double startValue;
    private int steps = 100;
    private boolean startAtMin = true;

    private boolean iStop = true;

    public SeriesAnimation(Series s) {
        this(s.getChart());
        iSeries = s;
    }

    public SeriesAnimation() {
        this((IBaseChart)null);
    }

    public SeriesAnimation(IBaseChart c) {
        super(c);
    }

    transient protected NumberResolver customStep;

    public void setStepResolver(NumberResolver resolver) {
        if (resolver != null) {
            this.customStep = resolver;
        } else {
            removeStepResolver();
        }
    }

    public void removeStepResolver() {
        this.customStep = DefaultResolvers.NUMBER_RESOLVER;
    }

    protected Object readResolve() {
        customStep = DefaultResolvers.NUMBER_RESOLVER;
        return super.readResolve();
    }

    /**
     * Gets descriptive text.
     *
     * @return String
     */
    public String getDescription() {
        return Language.getString("SeriesAnimTool");
    }

    /**
     * Gets detailed descriptive text.
     *
     * @return String
     */
    public String getSummary() {
        return Language.getString("SeriesAnimationSummary");
    }

    private void updateChart() {
        chart.getParent().refreshControl();
    }

    private void doAnimation() {
        ValueList tmpList = iSeries.getMandatory();

        double tmpStart = getStartAtMin() ? tmpList.getMinimum() : getStartValue();

        // clear values
        for (int t = 0; t < iSeries.getCount(); t++) {
            tmpList.value[t] = tmpStart;
        }

        updateChart();

        double tmpSpeed = 1.0 / getSteps();

        ValueList tmpBack = backup.getMandatory();

        int tmpPoints;

        if (getDrawEvery() == 0) {
            tmpPoints = 0;
        } else {
            tmpPoints = iSeries.getCount() / getDrawEvery();
            if ((iSeries.getCount() % getDrawEvery()) == 0) {
                tmpPoints--;
            }
        }

        int tmpFrom;
        int tmpTo;

        for (int tmpPointStep = 0; tmpPointStep <= tmpPoints; tmpPointStep++) {
            tmpFrom = tmpPointStep * getDrawEvery();
            if (getDrawEvery() == 0) {
                tmpTo = iSeries.getCount() - 1;
            } else {
                tmpTo = Math.min(iSeries.getCount() - 1, tmpFrom + getDrawEvery() - 1);
            }

            for (int t = 0; t <= getSteps(); t++) {
                for (int tt = tmpFrom; tt <= tmpTo; tt++) {

                    tmpList.value[tt] = tmpStart +
                                        ((tmpBack.value[tt] - tmpStart) * t *
                                         tmpSpeed);
                }

                t = customStep.getNumber(this, t);

                updateChart();

                if (iStop) {
                    break;
                }
            }
        }
    }

    //      ValueList tmpList=series.mandatory;
    //
    //      double tmpStart= StartAtMin ? tmpList.Minimum : StartValue;
    //
    //      // Clear values
    //      for (int t=0; t<series.Count; t++)
    //        tmpList.Value[t]=tmpStart;
    //
    //      UpdateChart();
    //
    //      double tmpSpeed=1/Steps;
    //
    //      ValueList tmpBack=backup.mandatory;
    //
    //      int tmpPoints;
    //
    //      if (DrawEvery==0) tmpPoints=0;
    //      else
    //      {
    //        tmpPoints=series.Count / DrawEvery;
    //        if ((series.Count % DrawEvery)==0) tmpPoints--;
    //      }
    //
    //      int tmpFrom;
    //      int tmpTo;
    //
    //      for (int tmpPointStep=0; tmpPointStep<=tmpPoints; tmpPointStep++)
    //      {
    //        tmpFrom=tmpPointStep*DrawEvery;
    //        if (DrawEvery==0)
    //          tmpTo=series.Count-1;
    //        else
    //          tmpTo=Math.min(series.Count-1,tmpFrom+DrawEvery-1);
    //
    //        for (int t=0; t<=Steps; t++)
    //        {
    //          for (int tt=tmpFrom; tt<=tmpTo; tt++)
    //            tmpList.Value[tt]=tmpStart+((tmpBack.Value[tt]-tmpStart)*t*tmpSpeed);
    //
    //          if (Step!=null) Step(this,new AnimationStepEventArgs(t));
    //
    //          UpdateChart();
    //
    //          if (IStop) break;
    //        }
    //      }

    public void execute() throws InstantiationException,IllegalAccessException{
        if ((iSeries != null) && iStop) {
            backup = Series.newFromType(iSeries.getClass());
            try {
                backup.assignValues(iSeries);
                iSeries.beginUpdate();
                iStop = false;
                try {
                    doAnimation();
                } finally {
                    iStop = true;
                    // not necessary--> Series.AssignValues(backup);
                    iSeries.endUpdate();
                }
            } finally {
                backup.dispose();
            }
        }
    }

    /**
     * Determines the points to perform animation on.<br>
     * By default, DrawEvery is zero.<br> This means the animation will be
     * performed using all series points at once. <br>
     * Setting DrawEvery to, for example, one, means the animation will be
     * performed on each series point individually. <br>
     * Default value: 0
     *
     * @return int
     */
    public int getDrawEvery() {
        return drawEvery;
    }

    /**
     * Determines the points to perform animation on.<br>
     * Default value: 0
     *
     * @param value int
     */
    public void setDrawEvery(int value) {
        drawEvery = value;
    }

    /**
     * Changes all series point values to the lowest series value. <br>
     * Setting StartAtMin to false, and setting the StartValue method, makes
     * the animation start at that value. <br>
     * Default value: true
     *
     * @return boolean
     */
    public boolean getStartAtMin() {
        return startAtMin;
    }

    /**
     * Changes all series point values to the lowest series value. <br>
     * Default value: true
     *
     * @param value boolean
     */
    public void setStartAtMin(boolean value) {
        startAtMin = value;
    }

    /**
     * All series points from when the animation starts. <br>
     * Default value: 0
     *
     * @return double
     */
    public double getStartValue() {
        return startValue;
    }

    /**
     * Sets all series points from when the animation starts. <br>
     * Default value: 0
     *
     * @param value double
     */
    public void setStartValue(double value) {
        startValue = value;
    }

    /**
     * Number of animation steps. <br>
     * When the number of steps is bigger, the animation is slower. <br>
     * Default alue: 100
     *
     * @return int
     */
    public int getSteps() {
        return steps;
    }

    /**
     * Number of animation steps. <br>
     * When the number of steps is bigger, the animation is slower. <br>
     * Default alue: 100
     *
     * @param value int
     */
    public void setSteps(int value) {
        steps = value;
    }
}

