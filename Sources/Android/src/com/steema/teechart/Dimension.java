package com.steema.teechart;

public class Dimension {

	public int height;
	public int width;

	public Dimension(int w, int h) {
		width=w;
		height=h;
	}

	public double getHeight() {
		return height;
	}

	public double getWidth() {
		return width;
	}

	public void setSize(double w, double h) {
		width=(int) Math.round(w);
		height=(int) Math.round(h);
	}

}
