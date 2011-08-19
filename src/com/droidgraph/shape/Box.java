package com.droidgraph.shape;

import processing.core.PGraphics;

public class Box extends DGPShape3D {

	public Box(float size) {
		bounds.setBounds(0, 0, 0, size, size, size);
	}

	public Box(float width, float height, float depth) {
		bounds.setBounds(0, 0, 0, width, height, depth);
	}

	@Override
	public void paint(PGraphics p) {
		p.box(bounds.getWidth(), bounds.getHeight(), bounds.getDepth());
	}
}
