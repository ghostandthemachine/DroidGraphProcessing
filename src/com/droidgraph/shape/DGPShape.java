package com.droidgraph.shape;

import com.droidgraph.translation.Bounds;

import processing.core.PGraphics;

public interface DGPShape {

	public void paint(PGraphics pg);

	public Bounds getBounds();
	
}