package com.steema.teechart.drawing;

import java.io.Serializable;

public class Point extends android.graphics.Point implements Serializable {

	public final static class Double implements Serializable {

		public double x;
		public double y;
		
		public Double(final double ax, final double ay) {
			x=ax;
			y=ay;
		}

		public Double() {
			super();
		}

	}

	public Point(final int ax, final int ay) {
		super(ax,ay);
	}

	public Point() {
		super();
	}

	public Point(final Point point) {
		super(point.x,point.y);
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public void set(final Point p) {
		x=p.x;
		y=p.y;
	}
}
