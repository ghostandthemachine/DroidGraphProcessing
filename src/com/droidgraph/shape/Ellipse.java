package com.droidgraph.shape;

import processing.core.PGraphics;

public class Ellipse extends DGPShape2D {
	
	public Ellipse(float x, float y, float width, float height) {
		
		bounds.setBounds(x, y, width, height);
	}
	
	@Override
	public void paint(PGraphics p) {
		p.ellipse(getX(), getY(), getWidth(), getHeight());
	}

}
