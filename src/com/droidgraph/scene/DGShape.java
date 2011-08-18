package com.droidgraph.scene;

import processing.core.PGraphics;

import com.droidgraph.renderer.PickBuffer;
import com.droidgraph.shape.DGPShape2D;
import com.droidgraph.util.Shared;

public class DGShape extends DGAbstractShape {

	protected DGPShape2D shape;
	
	private float strokeWeight = 1.0f;


	public void strokeWeight(float stroke) {
		this.strokeWeight = stroke;
	}
	
	@Override
	public void paint() {
		shape.paint(Shared.offscreenBuffer);
	}

	@Override
	public void paint(PGraphics p) {
		if (shape != null) {
			p.fill(fillColor[0], fillColor[1], fillColor[2], fillColor[3]);
			p.stroke(strokeColor[0], strokeColor[1], strokeColor[2],
					strokeColor[3]);
			p.strokeWeight(strokeWeight);
			shape.paint(p);
		}
	}
	
	
	public void renderToPickBuffer(PGraphics p) {
		((PickBuffer) Shared.offscreenBuffer).setCurrentIDIndex(sceneID);
		p.fill(fillColor[0], fillColor[1], fillColor[2], fillColor[3]);
		p.stroke(strokeColor[0], strokeColor[1], strokeColor[2],
				strokeColor[3]);
		p.strokeWeight(strokeWeight);
		paint(Shared.offscreenBuffer);
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

	
	

}
