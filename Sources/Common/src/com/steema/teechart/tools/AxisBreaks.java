package com.steema.teechart.tools;

import java.util.ArrayList;

/**
 * Collection of AxisBreak
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights
 * Reserved.</p>
 *
 * @see com.steema.teechart.tools.AxisBreak
 */
public class AxisBreaks extends ArrayList  {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected  AxisBreaksTool tool;

    public int add(AxisBreak l)
    {
      int n = super.indexOf(l);
      if (n == -1)
          super.add(l);
      return super.size();
    }

    public void clear()
    {
      super.clear();
      tool.invalidate();
    }

    public void remove(AxisBreak s)
    {
      this.remove(indexOf(s));
      tool.invalidate();
    }

    public AxisBreak get(int index)
    {
        return (AxisBreak) super.get(index);
    }

//    public AxisBreak this[int index]
//    {
//      get { return (AxisBreak)this.List[index]; }
//    }

   // public int IndexOf(AxisBreak l) { return this.indexOf((l); }
//#endif

}
