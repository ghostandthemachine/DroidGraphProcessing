package com.droidgraph.shape;

import processing.core.PGraphics;

public class Line extends DGPShape2D {
	
	public float z = 0;
	
	public float x2;
	public float y2;
	public float z2 = 0;
	
	public float depth;
	
	public Line(float x1, float y1, float x2, float y2) {
		this.x = x1;
		this.y = y1;
		
		this.x2 = x2;
		this.y2 = y2;
		
		this.width = 0;
		this.height = 0;
		
		bounds.setBounds(x1, y1, width, height);
	}
	
	public Line(float x1, float y1, float z1, float x2, float y2, float z2) {
		this.x = x1;
		this.y = y1;
		this.z = z1;
		
		this.x2 = x2;
		this.y2 = y2;
		this.z2 = z2;
		
		this.width = 0;
		this.height = 0;
		this.depth = 0;
		
		bounds.setBounds(x1, y1, width, height);
	}
	
	@Override
	public void paint(PGraphics p) {
		p.line(x, y, z, x2, y2, z2);
	}

	public void setLine(float x1, float y1, float x2, float y2) {
		this.x = x1;
		this.y = y1;
		this.x2 = x2;
		this.y2 = y2;
	}
	
	public void setLine(float x1, float y1, float z1, float x2, float y2, float z2) {
		this.x = x1;
		this.y = y1;
		this.z = z1;
		this.x2 = x2;
		this.y2 = y2;
		this.z2 = z2;
	}

}
