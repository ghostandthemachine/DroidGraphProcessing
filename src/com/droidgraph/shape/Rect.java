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

}
