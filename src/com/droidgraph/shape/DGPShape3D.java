package com.droidgraph.shape;

import processing.core.PGraphics;

import com.droidgraph.transformation.Bounds3D;
import com.droidgraph.translation.Bounds;

public abstract class DGPShape3D implements DGPShape{

	protected Bounds3D bounds = new Bounds3D(this, 0, 0, 0, 0, 0, 0);

	@Override
	public void paint(PGraphics p) {
		// paint the 3d shape here
	}

	public void setBounds3D(float x, float y, float z, float width, float height, float depth) {
		bounds.setBounds(x, y, z, width, height, depth);
	}

	public void setDimensions(float width, float height, float depth) {
		bounds.setWidth(width);
		bounds.setHeight(height);
		bounds.setDepth(depth);
	}
	
	public void setTranslation(float x, float y, float z) {
		bounds.setX(x);
		bounds.setY(y);
		bounds.setZ(z);
	}

	public Bounds getBounds() {
		return bounds;
	}
}
