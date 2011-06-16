package com.droidgraph.shape;

import processing.core.PGraphics;

public class Triangle extends DGPShape2D {
	
	public float x1, y1, x2, y2, x3, y3;
	
	public Triangle(float x1, float y1, float x2, float y2, float x3, float y3) {
		this.x = x1;
		this.y = y2;
		
		this.width = x1 - x3;
		this.height = y2 - y3;
		
		this.x1 = x1;
		this.x2 = x2;
		this.x3 = x3;
		this.y1 = y1;
		this.y2 = y2;
		this.y3 = y3;
		
		bounds.setBounds(x1, y2, width, height);
	}
	
	public void paint(PGraphics p) {
		p.triangle(x1, y1, x2, y2, x3, y3);
	}

}
