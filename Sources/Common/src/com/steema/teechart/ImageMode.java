package com.steema.teechart;

import com.steema.teechart.misc.Enum;

/**
 *
 * <p>Title: ImageMode class</p>
 *
 * <p>Description: Displays characteristics for an Image</p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
final public class ImageMode extends Enum {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
     * Display unmodfied
     */
    public static final ImageMode NORMAL = new ImageMode();
    /**
     * Stretch to bounding area dimensions
     */
    public static final ImageMode STRETCH = new ImageMode();
    /**
     * Repeat Tile small image over larger bounding area
     */
    public static final ImageMode TILE = new ImageMode();
    /**
     * Place unmodified image at Center of bounding area
     */
    public static final ImageMode CENTER = new ImageMode();

    private ImageMode() {
        super();
    }
}

