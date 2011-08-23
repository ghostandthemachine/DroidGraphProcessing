package com.droidgraph.shape;

import processing.core.PGraphics;

import com.droidgraph.transformation.Bounds;
import com.droidgraph.transformation.Vec3f;

public interface DGPShape {

	public void paint(PGraphics pg);

	public Bounds getBounds();
	
	public Bounds getBounds(Vec3f transform);
	
}