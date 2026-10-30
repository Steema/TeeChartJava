/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.steema.teechart.styles;

/**
 * <p>Title: MultiPies - enum constants</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public class MultiPies extends com.steema.teechart.misc.Enum 
{
  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
/**
   * Multiple Pie series are displayed using an automatically 
   * calculated chart space portion. (default)
   */
  public static final MultiPies AUTOMATIC = new MultiPies(0);
  /**
   * Multiple Pie series are displayed using custom manual ChartRect 
   * positions, or overlapped.
   */
  public static final MultiPies DISABLED = new MultiPies(1);
  /**
   * Multiple Pie series are drawn concentric.
   */
  public static final MultiPies CONCENTRIC = new MultiPies(2);

  private MultiPies(int value) {
      super(value);
  }
}
