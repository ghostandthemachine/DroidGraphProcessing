package com.droidgraph.shape;

import com.droidgraph.transformation.Bounds2D;

import processing.core.PGraphics;

public abstract class DGPShape2D {
	
	protected Bounds2D bounds = new Bounds2D(0,0,0,0);

	public float x = 0;
	public float y = 0;
	
	public float width = 0;
	public float height = 0;
	
	public void paint(PGraphics p) {}
	
	public void setDimensions(float width, float height) {
		this.width = width;
		this.height = height;
		bounds.setBounds(x,y, width, height);
	}

	public Bounds2D getBounds() {
		return bounds;
	}
}
