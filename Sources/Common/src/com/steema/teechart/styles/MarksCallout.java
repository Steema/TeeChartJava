package com.steema.teechart.styles;

/**
 *
 * <p>Title: MarksCallout class</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright (c)
 * 2005-2013 by Steema Software SL. All Rights
 * Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public class MarksCallout extends Callout {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected int length = 8;
    transient private int defaultLength;

    public MarksCallout(ISeries s) {
        super(s);
        readResolve();
        setDefaultVisible(false);
    }

    protected Object readResolve() {
        defaultLength=8;
        return super.readResolve();
    }

    /**
     * Length between series data points and Marks.
     *
     * @return int
     */
    public int getLength() {
        return length;
    }

    /**
     * Specifies the Length between series data points and Marks.
     *
     * @param value int
     */
    public void setLength(int value) {
        if (length != value) {
            length = value;
            invalidate();
        }
    }

    void setDefaultLength(int value) {
        defaultLength = value;
        length = value;
    }

}

