package com.droidgraph.scene;

import processing.core.PGraphics;

import com.droidgraph.renderer.PickBuffer;
import com.droidgraph.shape.DGPShape;
import com.droidgraph.transformation.Bounds;
import com.droidgraph.transformation.Vec3f;
import com.droidgraph.util.Shared;

public class DGShape extends DGAbstractShape {

	protected DGPShape shape;

	private float strokeWeight = 1.0f;
	
	public void strokeWeight(float stroke) {
		this.strokeWeight = stroke;
	}

	@Override
	public void paint() {
	}

	@Override
	public void paint(PGraphics p) {
		if (shape != null) {
			
			switch (mode) {

			case DGShape.FILL:
				p.noStroke();
				p.fill(fillColor[0], fillColor[1], fillColor[2], fillOpacity);
				break;

			case DGShape.STROKE:
				p.strokeWeight(strokeWeight);
				p.stroke(strokeColor[0], strokeColor[1], strokeColor[2],
						strokeOpacity);
				break;

			case DGShape.FILL_STROKE:
				p.fill(fillColor[0], fillColor[1], fillColor[2], fillOpacity);
				p.strokeWeight(strokeWeight);
				p.stroke(strokeColor[0], strokeColor[1], strokeColor[2],
						strokeOpacity);
				break;
			}
			shape.paint(p);
		}
	}

	public void renderToPickBuffer(PGraphics p) {
		((PickBuffer) Shared.offscreenBuffer).setCurrentIDIndex(sceneID);
		p.fill(fillColor[0], fillColor[1], fillColor[2]);
		p.stroke(strokeColor[0], strokeColor[1], strokeColor[2]);
		p.strokeWeight(strokeWeight);
		paint(Shared.offscreenBuffer);
	}

	@Override
	public DGPShape getShape() {
		return shape;
	}

	@Override
	public void setShape(DGPShape shape) {
		this.shape = shape;
		bounds.accumulate(shape.getBounds());
		repaint(true);
	}

	@Override
	public Bounds getBounds(Vec3f transform) {
		if(transform == null) {
			transform = new Vec3f(0,0,0);
		}
		if(shape == null) {
			Bounds nB = new Bounds();
			nB.set(transform.x, transform.y, transform.z);
			return nB;
		} else {
			Bounds b = (Bounds) shape.getBounds();
			b.set(transform.x, transform.y, transform.z);
			return b;
			}
		}

}
