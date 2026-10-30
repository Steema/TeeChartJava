package com.steema.teechart.functions;

import java.util.ArrayList;
import com.steema.teechart.languages.Language;

import com.steema.teechart.IBaseChart;
import com.steema.teechart.styles.Series;

/**
 *
 * <p>Title: HistogramFunction class</p>
 *
 * <p>Description: Histogram function.</p>
 *
 *<p>Example:
 * <pre><font face="Courier" size="4">
 * function = new com.steema.teechart.functions.Histogram();
 * function.setChart(myChart.getChart());
 * function.setPeriod(0); //all points
 *
 * Line functionSeries = new Line(myChart.getChart());
 * functionSeries.setTitle("Histogram");
 * functionSeries.setDataSource(series);
 * functionSeries.getXValues().setOrder(ValueListOrder.NONE);
 * //functionSeries.setVerticalAxis(VerticalAxis.RIGHT);
 * functionSeries.setFunction(function);
 * </font></pre></p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights
 * Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public class HistogramFunction extends Function {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private boolean cumulative;
    private int numbins = 20;

    public HistogramFunction() {
        this((IBaseChart)null);
    }

    public HistogramFunction(IBaseChart c) {
        super(c);

        canUsePeriod = false;
        SingleSource = true;
    }

    /**
     * Gets cumulative histogram.<br>
     *
     * @return boolean
     */    
    public boolean getCumulative() {
        return cumulative;
    }
    
    /**
     * Sets cumulative histogram. <br>
     * If true, cumulative histogram is calculated.<br>
     *
     * @param value boolean
     */    
    public void setCumulative(boolean value){
        cumulative = setBooleanProperty(cumulative, value);
    }
    
    /**
     * Gets Number of bins (centerpoints) in histogram.<br>
     * Default value: 20
     *
     * @return int
     */    
    public int getNumBins() {
        return numbins;
    }
    
    /**
     * Sets Number of bins (centerpoints) in histogram.<br>
     * Default value: 20
     *
     * @param value int
     */    
    public void setNumBins(int value){
        numbins = setIntegerProperty(numbins, value);
    }
    
    private class CalcHistogram {
        double[] bins, counts;
    }

    /**
     * Constructs histogram from data double array.<br>
     *
     * @param data double[]. Defines data for histogram.
     * @param lb int. Data lower bound.
     * @param ub int. Data upper bound.
     * @param bins double[]. Returns bins centerpoints.
     * @param counts double[]. Returns each bin count.
     * @param nbins int. Number of bins used in histogram. 
     * Value must match the bins and counts size.
     * @param min double. Minimum value used for histogram. 
     * Usually it's data minimum value.
     * @param max double. Maximum value used for histogram. 
     * Usually it's data maximum value.
     * 
     */
    private CalcHistogram histogram(double[] data, int lb, int ub, 
            double[] bins, double[] counts, int nbins, double min, double max) {
        // In case minimum is equal to maximum
        if (min==max) {
            min = min - Math.floor(0.5*nbins) - 0.5;            
            max = max + Math.ceil(0.5*nbins) + 0.5;
        }

        double range = max - min;
        double binwidth = range/(double)nbins;
        double invbinwidh = (double)nbins/range;
        
        CalcHistogram histoData = new CalcHistogram();
        histoData.bins = bins;
        histoData.counts = counts;

        // Setup bins centerpoints and count for each bin
        for (int i=0; i<nbins; i++) {
            histoData.bins[i] = min + 0.5 * binwidth + binwidth*i;
            histoData.counts[i] = 0;
        }

        // pretty fast, but can be a bit inaccurate if values are in range of 
        // SQRT(EPS)
        int j;
        int righttail = 0;
        for (int i = lb; i<=ub; i++) {
            j = (int)((data[i]-min)*invbinwidh);
            if ((j>=0) && (j<nbins)) {
                histoData.counts[j] += 1;
            }
            else {
                if (j>=nbins) { 
                    righttail += 1;
                }
            }
        }
        
        histoData.counts[nbins-1] += righttail;
        
        return histoData;
    }

    /**
     * Gets all points from Source series, performs a function operation on 
     * points and stores results in ParentSeries.<br>
     *
     * @param source ArrayList
     */
    public void addPoints(ArrayList source) {
        
      if ( ! updating )
        if ( source!=null )  {
            if (source.size()>0)  {
                Series s=(Series) source.get(0); // single series only!
                series.clear();
                int N = s.getCount();

                if (N>0) {                
                    double[] ibins = new double[numbins];
                    double[] icounts = new double[numbins];
                    
                    CalcHistogram tmp = histogram(s.getMandatory().getValues(), 
                            0, N-1, ibins, icounts, numbins, 
                            s.getMandatory().getMinimum(), 
                            s.getMandatory().getMaximum());

                    if (cumulative) {
                        for (int i = 1; i < numbins; i++) {
                            tmp.counts[i] += tmp.counts[i - 1];
                        }
                    }

                    series.getNotMandatory().setCount(numbins);

                    series.getNotMandatory().setValues(tmp.bins);
                    series.getMandatory().setValues(tmp.counts);

                    series.getMandatory().setCount(numbins);
                }
            }
        }
    }    

    /**
     * Gets descriptive text.
     *
     * @return String
     */
    public String getDescription() {
        return Language.getString("FunctionHistogram");
    }
}

