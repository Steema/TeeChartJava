package com.steema.teechart.tools;

/**
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights
 * Reserved.</p>
 *
 */
public class AxisBreak {

    private AxisBreaksTool tool;
    private boolean enabled = true;
    private double endValue = 0;
    private double startValue = 0;
    private AxisBreakStyle style = AxisBreakStyle.ZIGZAG;

    public AxisBreak()
    {
	this((AxisBreaksTool)null);
    }

    public AxisBreak(AxisBreaksTool owner)
    {
      SetOwner(owner);
      enabled = true;
    }

protected void SetOwner(AxisBreaksTool owner)
    {
      tool = owner;
      if (tool != null)
      {
        //tool.  .getBreaks().Add(this);
      }
    }

    protected void Assign(AxisBreak t)
    {
      t.setStartValue(startValue);
      t.setEndValue(endValue);
      t.setEnabled(enabled);

      //super.Assign(t);
    }

    /// <summary>
    /// Sets the End value of the Axis break.
    /// </summary>
//#if DESIGNATTRIBUTES
//[DefaultValue(0), Description("Sets the End value of the Axis break.")]
//#endif
    public double getEndValue()
    { return endValue; }

    public void setEndValue(double value) 
    { endValue = value; tool.invalidate(); }

    /// <summary>
    /// Sets the Start value of the Axis break.
    /// </summary>
//#if DESIGNATTRIBUTES
//[DefaultValue(0), Description("Sets the Start value of the Axis break.")]
//#endif
    public double getStartValue()
    { return startValue; }

    public void setStartValue(double value) 
    { startValue = value; tool.invalidate();}

    /// <summary>
    /// Enables or disables the axis break.
    /// </summary>
//#if DESIGNATTRIBUTES
//[DefaultValue(true), Description("Enables or disables the axis break.")]
//#endif
    public boolean getEnabled()
    { return enabled; }
      
    public void setEnabled(boolean value)
    { enabled = value; } // no need to repaint

    /// <summary>
    /// Sets the style of the break.
    /// </summary>
//#if DESIGNATTRIBUTES
//[DefaultValue(AxisBreakStyle.ZigZag),
//Description("Sets the style of the break.")]
//#endif
    public AxisBreakStyle getStyle()
    { return style; }

    public void setStyle(AxisBreakStyle value) 
    { if (style != value) { style = value; tool.invalidate(); } }

}
