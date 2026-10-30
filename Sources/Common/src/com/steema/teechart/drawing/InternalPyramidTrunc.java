package com.steema.teechart.drawing;

import com.steema.teechart.Rectangle;

/**
 *
 * <p>Title: InternalPyramidTrunc class</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights
 * Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public class InternalPyramidTrunc {

    protected Rectangle r;
    protected int midX;
    protected int midZ;
    protected int truncX;
    protected int truncZ;
    protected int startZ, endZ;
    protected IGraphics3D g;

    private void frontWall(int startZ, int endZ) {
        Point[] p = {
                    new Point(midX - truncX, r.y),
                    new Point(midX + truncX, r.y),
                    new Point(r.getRight(), r.getBottom()),
                    new Point(r.x, r.getBottom())};

        g.planeFour3D(startZ, endZ, p);
    }

    private void sideWall(int horizPos1, int horizPos2, int startZ, int endZ) {
        Point[] tmp = {
                      g.calc3DPoint(horizPos2, r.y, midZ - truncZ),
                      g.calc3DPoint(horizPos2, r.y, midZ + truncZ),
                      g.calc3DPoint(horizPos1, r.getBottom(), endZ),
                      g.calc3DPoint(horizPos1, r.getBottom(), startZ)
        };
        g.polygon(tmp);
    }

    private void bottomCover() {
        g.rectangleY(r.x, r.getBottom(), r.getRight(), startZ, endZ);
    }

    private void topCover() {
        if (truncX != 0) {
            g.rectangleY(midX - truncX, r.y, midX + truncX, midZ - truncZ,
                         midZ + truncZ);
        }
    }

    public void draw(IGraphics3D gr) {
        g = gr;
        midX = (r.x + r.getRight()) / 2;
        midZ = (startZ + endZ) / 2;

        if (r.getBottom() > r.y) {
            bottomCover();
        } else {
            topCover();
        }

        frontWall(midZ + truncZ, endZ);
        sideWall(r.x, midX - truncX, startZ, endZ);
        frontWall(midZ - truncZ, startZ);
        sideWall(r.getRight(), midX + truncX, startZ, endZ);

        if (r.getBottom() > r.y) {
            topCover();
        } else {
            bottomCover();
        }
    }
}

