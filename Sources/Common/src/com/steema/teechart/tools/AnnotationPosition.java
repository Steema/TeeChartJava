package com.steema.teechart.tools;

import com.steema.teechart.misc.Enum;

/**
 *
 * <p>Title: AnnotationPosition</p>
 *
 * <p>Description: Describes the possible values of the Annotation.Position
 * method.</p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights Reserved.</p>
 *
 * <p>Company: Steema Software</p>
 *
 *
 * @see com.steema.teechart.tools.Annotation#getPosition
 */
final public class AnnotationPosition extends Enum {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * Draws the Annotation Tool in the top left of the Chart Panel.
     */
    public static final AnnotationPosition LEFTTOP = new AnnotationPosition(0);

    /**
     * Draws the Annotation Tool in the bottom left of the Chart Panel.
     */
    public static final AnnotationPosition LEFTBOTTOM = new AnnotationPosition(1);

    /**
     * Draws the Annotation Tool in the top right of the Chart Panel.
     */
    public static final AnnotationPosition RIGHTTOP = new AnnotationPosition(2);

    /**
     * Draws the Annotation Tool in the bottom right of the Chart Panel.
     */
    public static final AnnotationPosition RIGHTBOTTOM = new AnnotationPosition(3);
    
    public static final AnnotationPosition CENTER = new AnnotationPosition(-1);
    
    private AnnotationPosition(int value) {
        super(value);
    }
}

