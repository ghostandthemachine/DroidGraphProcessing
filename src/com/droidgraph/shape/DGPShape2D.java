package com.droidgraph.shape;

import processing.core.PGraphics;

import com.droidgraph.transformation.Bounds2D;

public abstract class DGPShape2D implements DGPShape{

	protected Bounds2D bounds = new Bounds2D(0, 0, 0, 0);

	protected float x = 0;
	protected float y = 0;

	protected float width = 0;
	protected float height = 0;

	@Override
	public void paint(PGraphics p) {
	}

	public void setBounds2D(float x, float y, float width, float height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		bounds.setBounds(x, y, width, height);
	}

	public void setDimensions(float width, float height) {
		this.width = width;
		this.height = height;

	}

	public Bounds2D getBounds() {
		return bounds;
	}
}
