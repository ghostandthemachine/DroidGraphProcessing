package com.droidgraph.transformation;

import com.droidgraph.fx.DGFXShape;

public class Transformation implements Runnable {
	
	public DGFXShape node;
	
	public Transformation(DGFXShape node) {
		this.node = node;
	}
	
	public void setPGraphics(DGFXShape node) {
		this.node = node;
	}
	
	public float x = 0;
	public float y = 0;
	public float z = 0;
	
	// Honeycomb animation syntax 
	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public float getZ() {
		return z;
	}

	public void setZ(float z) {
		this.z = z;
	}
	
	@Override
	public void run() {
	}
	
	

}
