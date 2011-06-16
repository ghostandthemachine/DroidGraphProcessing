package com.droidgraph.shape;

import processing.core.PGraphics;

public class Ellipse extends DGPShape2D {
	
	public Ellipse(float x, float y, float width, float height) {
		this.x = x;
		this.y = y;
		
		this.width = width;
		this.height = height;
		
		bounds.setBounds(x, y, width, height);
	}
	
	@Override
	public void paint(PGraphics p) {
		p.ellipse(x, y, width, height);
	}

}
