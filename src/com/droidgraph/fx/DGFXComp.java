package com.droidgraph.fx;

import processing.core.PApplet;
import processing.core.PGraphics;

import com.droidgraph.shape.DGCompContainer;

public class DGFXComp extends DGFXShape {

	private float[] backgroundColor = { 0.0f, 0.0f, 0.0f, 0.0f };
	private boolean background = false;

	public void setPicking(boolean b) {
		if (shape != null && shape instanceof DGCompContainer) {
			((DGCompContainer) shape).setPicking(true);
		}
	}
	
	public void setBackgroundFill(float r, float g, float b, float a) {
		backgroundColor[0] = r;
		backgroundColor[1] = g;
		backgroundColor[2] = b;
		backgroundColor[3] = a;
		// if there is any background then it is there. mostly for picking. 
		background = (backgroundColor[0] + backgroundColor[1] + backgroundColor[2] + backgroundColor[3] > 0) ? true : false;
	}
	
	public void setBackgroundFill(float r, float g, float b) {
		setBackgroundFill(r, g, b, 255);
	}

	public float getBackgroundRed() {
		return backgroundColor[0];
	}

	public float getBackgroundGreen() {
		return backgroundColor[1];
	}

	public float getBackgroundBlue() {
		return backgroundColor[2];
	}

	public float getBackgroundAlpha() {
		return backgroundColor[3];
	}
	
	public boolean isBackground() {
		return background;
	}

	protected void fxPaintOverride(PGraphics pg) {
		
		if (background) {
//			pg.pushStyle();
			pg.rectMode(PApplet.CORNER);
			pg.fill(backgroundColor[0], backgroundColor[1], backgroundColor[2],
					backgroundColor[3]);
			pg.rect(translateX, translateY, width, height);
//			pg.popStyle();
		}
	}
}
