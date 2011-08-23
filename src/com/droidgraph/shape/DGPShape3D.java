package com.droidgraph.shape;

import processing.core.PGraphics;

import com.droidgraph.transformation.Bounds;
import com.droidgraph.transformation.Vec3f;

public abstract class DGPShape3D implements DGPShape{

	protected Bounds bounds = new Bounds(0, 0, 0, 0, 0, 0);

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
		return bounds.getBounds();
	}
	
	public Bounds getBounds(Vec3f transform) {
		return bounds.getBounds(transform);
	}
}
