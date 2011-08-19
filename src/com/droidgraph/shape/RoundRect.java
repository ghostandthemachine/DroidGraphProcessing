package com.droidgraph.shape;

import processing.core.PGraphics;

public class RoundRect extends DGPShape2D {

	
	float radius;
	
	public RoundRect(float x, float y, float width, float height, float radius) {
		setBounds2D(x, y, width, height);
		this.radius = radius;
	}
	
	@Override
	public void paint(PGraphics pg) {
		pg.rect(bounds.getX(), bounds.getY(), bounds.getWidth(), bounds.getHeight(), radius);
	}
	
	
	
}
