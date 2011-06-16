package com.droidgraph.shape;

import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PImage;

import com.droidgraph.util.Shared;

public class Image extends DGPShape2D {

	PImage mImage;
	PApplet pa;

	public Image() {
		pa = Shared.pApplet;
	}
	
	public Image(String filename) {
		pa = Shared.pApplet;
		mImage = pa.loadImage(filename);
		width = mImage.width;
		height = mImage.height;
		bounds.setBounds(x, y, width, height);
	}
	
	public void loadImage(String filename) {
		mImage = pa.loadImage(filename);
	}

	public void paint(PGraphics p) {
		if (mImage != null) {
			p.image(mImage, x, y, width, height);
		}
	}
	
	public void setDimensions(float width, float height) {
		this.width = width;
		this.height = height;
		bounds.setBounds(x, y, width, height);
	}

}
