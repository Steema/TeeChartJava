package com.steema.teechart.drawing;

import com.steema.teechart.misc.Enum;
import java.util.ArrayList;

/**
 *
 * <p>Title: GradientDirection class</p>
 *
 * <p>Description: Describes the possible values of Gradient Direction.</p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 */
final public class GradientDirection extends Enum {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static int upperBound=0;
    private static ArrayList<GradientDirection> values = 
            new ArrayList<GradientDirection>();

    public static final GradientDirection VERTICAL = new GradientDirection();
    public static final GradientDirection HORIZONTAL = new GradientDirection();
    public static final GradientDirection FORWARDDIAGONAL = new GradientDirection();
    public static final GradientDirection BACKDIAGONAL = new GradientDirection();
    public static final GradientDirection RADIAL = new GradientDirection();

    private GradientDirection() {
        super(upperBound++);
        values.add(this);
    }

    public static int size() { return upperBound; }

    public static GradientDirection atIndex(int index) {
        return (GradientDirection)values.get(index);
    }
}

