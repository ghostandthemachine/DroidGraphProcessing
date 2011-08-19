package com.droidgraph.shape;

import processing.core.PGraphics;

public class Rect extends DGPShape2D {
	
	public Rect(float x, float y, float width, float height) {
		bounds.setBounds(x, y, width, height);
	}
	
	@Override
	public void paint(PGraphics p) {
		p.rect(getX(), getY(), getWidth(), getHeight());
	}

	public void setCorners(float x1, float y1, float x2, float y2, float x3, float y3, float x4, float y4) {
		float x = x1;
		float y = y1;
		float width = x2 - x1;
		float height = y2 - y1;
		bounds.setBounds(x, y, width, height);
	}

}
