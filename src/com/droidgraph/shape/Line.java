package com.droidgraph.shape;

import processing.core.PApplet;
import processing.core.PGraphics;

import com.droidgraph.util.Shared;

public class Line extends DGPShape2D {

	PApplet pa = Shared.pApplet;

	private float[][] points = { { 0.0f, 0.0f, 0.0f }, { 0.0f, 0.0f, 0.0f } };

	public Line(float x1, float y1, float x2, float y2) {
		bounds.setWidth(pa.abs(x1 - x2));
		bounds.setHeight(pa.abs(y1 - y2));

		bounds.setX((x1 < x2) ? x1 : x2);
		bounds.setY((y1 < y2) ? y1 : y2);
		
		points[0][0] = x1;
		points[0][1] = y1;
		
		points[1][0] = x2;
		points[1][1] = y2;
	}

	public Line(float x1, float y1, float z1, float x2, float y2, float z2) {
		bounds.setX((x1 < x2) ? x1 : x2);
		bounds.setY((y1 < y2) ? y1 : y2);
		bounds.setZ((z1 < z2) ? z1 : z2);

		bounds.setWidth(pa.abs(x1 - x2));
		bounds.setHeight(pa.abs(y1 - y2));
		bounds.setDepth(pa.abs(z1 - z2));
		
		points[0][0] = x1;
		points[0][1] = y1;
		points[0][2] = z1;
		
		points[1][0] = x2;
		points[1][1] = y2;
		points[1][2] = z2;
	}

	@Override
	public void paint(PGraphics p) {
		p.line(points[0][0], points[0][1], points[0][2], points[1][0], points[1][1], points[1][2]);
	}

	public void setLine(float x1, float y1, float x2, float y2) {
		points[0][0] = x1;
		points[0][1] = y1;
		
		points[1][0] = x2;
		points[1][1] = y2;
	}

	public void setLine(float x1, float y1, float z1, float x2, float y2, float z2) {
		points[0][0] = x1;
		points[0][1] = y1;
		points[0][2] = z1;
		
		points[1][0] = x2;
		points[1][1] = y2;
		points[1][2] = z2;
	}

}
