package com.steema.teechart;

import com.steema.teechart.drawing.IGraphics3D;
import com.steema.teechart.drawing.Point;
import com.steema.teechart.misc.Utils;

/**
 *
 * <p>Title: ArrowPoint class</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public class ArrowPoint {

    public double x, y;
    public int z;
    public double sinA, cosA;
    public IGraphics3D g;
/**
 * 
 * @return Point
 */
    public Point calc() {
        final Point p = new Point(Utils.round(x * cosA + y * sinA),
                                  Utils.round( -x * sinA + y * cosA));
        return g.calc3DPoint(p, z);
    }
}

