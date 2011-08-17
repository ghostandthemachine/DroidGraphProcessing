package com.droidgraph.shape;

import processing.core.PGraphics;

public class Rect extends DGPShape2D {
	
	public Rect(float x, float y, float width, float height) {
		this.x = x;
		this.y = y;
		
		this.width = width;
		this.height = height;
		
		bounds.setBounds(x, y, width, height);
	}
	
	@Override
	public void paint(PGraphics p) {
		p.rect(x, y, width, height);
	}

	public void setCorners(float x1, float y1, float x2, float y2, float x3, float y3, float x4, float y4) {
		x = x1;
		y = y1;
		width = x2 - x1;
		height = y2 - y1;
		bounds.setBounds(x, y, width, height);
	}

}
