package com.steema.teechart.axis;

/**
 * <p>Title: TeeChart for Java</p>
 *
 * <p>Description: TeeChart for Java</p>
 *
 * <p>Copyright (c) 2004-2019 by Steema Software SL. All Rights Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public final class NextAxisLabelValue {

    private int labelIndex;
    private double labelValue;
    private boolean stop = false;

    public NextAxisLabelValue() {
        this.stop = false;
    };

    public NextAxisLabelValue(double labelValue, boolean stop) {
        this();
        this.labelValue = labelValue;
        this.stop = stop;
    }
    
    public NextAxisLabelValue(int labelIndex, double labelValue, boolean doStop)
    {
      labelIndex=labelIndex;
      labelValue=labelValue;
      this.stop=doStop;
    }
    /**
     * Specifies  the desired Axis Label value.
     *
     * @param labelValue double
     */
    public void setLabelValue(double labelValue) {
        this.labelValue = labelValue;
    }
    /**
     * Returns the desired Axis Label value.
     *
     * @return double
     */
    public double getLabelValue() {
        return labelValue;
    }
    /**
     * Specifies  the desired Axis Label index.
     *
     * @param labelValue double
     */
    public void setLabelIndex(int labelIndex) {
        this.labelIndex = labelIndex;
    }
    /**
     * Returns the desired Axis Label index.
     *
     * @return double
     */
    public int getLabelIndex() {
        return labelIndex;
    }
    /**
     * The Stop  parameter is  false  by default, meaning that if it's not<br>
     * set to  true  the first time this event gets called, TeeChart will<br>
     * draw the default Axis Labels.<br><br>
     * Default value: false
     *
     * @param stop boolean
     */
    public void setStop(boolean stop) {
        this.stop = stop;
    }
    /**
     * The Stop  parameter is  false  by default, meaning that if it's not<br>
     * set to  true  the first time this event gets called, TeeChart will<br>
     * draw the default Axis Labels.<br><br>
     * Default value: false
     *
     * @return boolean
     */
    public boolean getStop() {
        return stop;
    }
}

