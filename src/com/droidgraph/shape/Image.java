package com.droidgraph.shape;

import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PImage;

import com.droidgraph.renderer.PickBuffer;
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
		setBounds(0, 0, 0, mImage.width, mImage.height, 0);
	}
	
	public Image(PImage image) {
		mImage = image;
		setBounds(0, 0, 0, mImage.width, mImage.height, 0);
	}
	
	public Image(PImage image, float x, float y, float width, float height) {
		this.mImage = image;
		setBounds(x, y, 0, width, height, 0);
		
	}
	
	public Image(PImage image, float x, float y, float z, float width, float height) {
		this.mImage = image;
		setBounds(x, y, z, width, height, 0);
		
	}

	public void loadImage(String filename) {
		mImage = pa.loadImage(filename);
	}
	
	public void setImage(PImage image) {
		mImage = image;
	}

	public void paint(PGraphics p) {
		if (mImage != null) {
			if(p instanceof PickBuffer) {
				p.rect(getX(), getY(), getWidth(), getHeight());
			} else {
				p.image(mImage, getX(), getY(), getWidth(), getHeight());
			}
		}
	}
	
	public void setDimensions(float width, float height) {
		setWidth(width);
		setHeight(height);
	}

}
