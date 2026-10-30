package com.steema.teechart.drawing;

public final class Polygon extends android.graphics.Path {

	public void addPoint(int x, int y) {
		this.lineTo(x, y);
	}

}
