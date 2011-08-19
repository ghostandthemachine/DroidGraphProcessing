package com.droidgraph.shape;

import processing.core.PGraphics;

import com.droidgraph.transformation.Bounds2D;

public abstract class DGPShape2D implements DGPShape{

	protected Bounds2D bounds = new Bounds2D(this, 0, 0, 0, 0);

	@Override
	public void paint(PGraphics p) {
	}

	public void setBounds2D(float x, float y, float width, float height) {
		bounds.setBounds(x, y, width, height);
	}
	
	public void setBounds(float x, float y, float z, float width, float height, float depth) {
		bounds.setBounds(x, y, z, width, height, depth);
	}

	public void setDimensions(float width, float height) {
		bounds.setWidth(width);
		bounds.setHeight(height);
	}

	public Bounds2D getBounds() {
		return bounds;
	}
	
	public void setX(float x) {
		bounds.setX(x);
	}

	public float getX() {
		return bounds.getX();
	}
	
	public void setY(float y) {
		bounds.setY(y);
	}
	
	public float getY() {
		return bounds.getY();
	}
	
	public void setZ(float z) {
		bounds.setZ(z);
	}

	public float getZ() {
		return bounds.getZ();
	}
	
	public void setWidth(float width) {
		bounds.setWidth(width);
	}
	
	public float getWidth() {
		return bounds.getWidth();
	}

	public void setHeight(float height) {
		bounds.setHeight(height);
	}
	
	public float getHeight() {
		return bounds.getHeight();
	}
	
	public void setDepth(float depth) {
		bounds.setDepth(depth);
	}
	
	public float getDepth() {
		return bounds.getDepth();
	}
	
}
