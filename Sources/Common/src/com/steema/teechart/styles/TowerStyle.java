package com.steema.teechart.styles;

import com.steema.teechart.misc.Enum;

/**
 *
 * <p>Title: TowerStyle class</p>
 *
 * <p>Description: Describes the possible values of the Tower.TowerStyle
 * method.</p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 *
 * @see com.steema.teechart.styles.Tower#getTowerStyle
 */
final public class TowerStyle extends Enum {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
     * Defines Tower series points as cubes.
     */
    public static final TowerStyle CUBE = new TowerStyle(0);
    /**
     * Defines Tower series points as rectangles.
     */
    public static final TowerStyle RECTANGLE = new TowerStyle(1);
    /**
     * Defines Tower series points as covers.
     */
    public static final TowerStyle COVER = new TowerStyle(2);
    /**
     * Defines Tower series points as cylinders.
     */
    public static final TowerStyle CYLINDER = new TowerStyle(3);
    /**
     * Defines Tower series points as arrows.
     */
    public static final TowerStyle ARROW = new TowerStyle(4);
    /**
     * Defines Tower series points as cones.
     */
    public static final TowerStyle CONE = new TowerStyle(5);
    /**
     * Defines Tower series points as pyramids.
     */
    public static final TowerStyle PYRAMID = new TowerStyle(6);

    private TowerStyle(int value) {
        super(value);
    }
}

