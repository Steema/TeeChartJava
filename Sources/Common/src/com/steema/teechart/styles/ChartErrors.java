package com.steema.teechart.styles;

public class ChartErrors extends ChartErrorsBase
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ErrorsFormat format;

    public ChartErrors(Series AOwner)
    {
        super(AOwner);
        format = new ErrorsFormat(ISeries.getChart());
    }

    public ErrorsFormat getFormat() { return format; }
 }
