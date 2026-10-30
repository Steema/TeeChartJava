package com.steema.teechart.drawing;

@SuppressWarnings("serial")
public class Point extends java.awt.Point {

	public Point(final int x, final int y) {
		super(x, y);
	}

	public Point() {
		super();
	}

	public Point(final Point point) {
		super(point);
	}

	public void set(final Point p) {
		x=p.x;
		y=p.y;
	}
}
