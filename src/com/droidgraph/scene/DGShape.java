package com.droidgraph.scene;

import processing.core.PGraphics;
import android.util.Log;

import com.droidgraph.shape.DGPShape2D;

public class DGShape extends DGAbstractShape {

	private DGPShape2D shape;

	private int[] color = { 255, 255, 255, 255 };

	public void paint(PGraphics p) {

		p.fill(color[0], color[1], color[2], color[3]);

		shape.paint(p);
	}

	@Override
	public DGPShape2D getShape() {
		return shape;
	}

	@Override
	public void setShape(DGPShape2D shape) {
		this.shape = shape;
		bounds.accumulate(shape.getBounds());
		Log.d("DGShape   ", bounds.x + " " + bounds.y + " " + bounds.width + " " + bounds.height);
	}

	public void setColor(int[] color) {
		this.color = color;
	}

	public void setColor(int r, int g, int b, int a) {
		color[0] = r;
		color[1] = g;
		color[2] = b;
		color[3] = a;
	}

}
