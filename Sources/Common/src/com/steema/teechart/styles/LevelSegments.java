package com.steema.teechart.styles;

import java.util.ArrayList;

/**
 *
 * @author Administrator
 */
public class LevelSegments extends ArrayList {
  
  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

public LevelSegments()
  {
    super();
  }
 
  public LevelSegment getItem(int index) {
    return (LevelSegment)get(index);
  }

}
