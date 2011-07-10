package com.droidgraph.scene;

import processing.core.PGraphics;

import com.droidgraph.shape.DGPShape2D;
import com.droidgraph.util.Shared;

public class DGShape extends DGAbstractShape {

	protected DGPShape2D shape;

	protected float[] fill = { 255, 255, 255, 255 };
	protected float[] strokeColor = { 0, 0, 0, 0 };

	@Override
	public void paint() {
		shape.paint(Shared.offscreenBuffer);
	}

	public void paint(PGraphics p, int[] cid) {
		if (shape != null) {
			p.fill(cid[0], cid[1], cid[2], cid[3]);
			p.stroke(cid[0], cid[1], cid[2], cid[3]);
			shape.paint(p);
		}
	}

	public void paint(PGraphics p) {
		if (shape != null) {
			p.fill(fill[0], fill[1], fill[2], fill[3]);
			p.stroke(strokeColor[0], strokeColor[1], strokeColor[2],
					strokeColor[3]);
			shape.paint(p);
		}
	}

	@Override
	public DGPShape2D getShape() {
		return shape;
	}

	@Override
	public void setShape(DGPShape2D shape) {
		this.shape = shape;
		bounds.accumulate(shape.getBounds());
	}

	public void setStroke(float[] strokeColor) {
		this.strokeColor = strokeColor;
	}

	public void setFill(float[] fill) {
		this.fill = fill;
	}

	public float[] getFill() {
		return this.fill;
	}

	public void setFill(float r, float g, float b, float a) {
		fill[0] = r;
		fill[1] = g;
		fill[2] = b;
		fill[3] = a;
	}

	public float getRed() {
		return fill[0];
	}

	public float getGreen() {
		return fill[1];
	}

	public float getBlue() {
		return fill[2];
	}

	public float getAlpha() {
		return fill[3];
	}
	
	public float getStrokeRed() {
		return strokeColor[0];
	}

	public float getStrokeGreen() {
		return strokeColor[1];
	}

	public float getStrokeBlue() {
		return strokeColor[2];
	}

	public float getStrokeAlpha() {
		return strokeColor[3];
	}
	

}
