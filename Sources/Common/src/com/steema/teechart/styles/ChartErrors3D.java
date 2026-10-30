package com.steema.teechart.styles;

public class ChartErrors3D extends ChartErrorsBase
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ErrorsFormat3D format;
    private ValueList front, back;

    public ChartErrors3D(Series AOwner)
    {
	super(AOwner);
    
    
      front = new ValueList(ISeries, "FrontErrors");
      back = new ValueList(ISeries, "BackErrors");

      format = new ErrorsFormat3D(ISeries.getChart());
    }

    public ErrorsFormat3D getFormat() { return format; }
    
    public ValueList getFront() { return front; }

    public ValueList getBack() { return back; }
  }
