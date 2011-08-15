package com.droidgraph.shape;

import processing.core.PApplet;
import processing.core.PGraphics;

import com.droidgraph.util.Shared;

public class DGText extends DGPShape2D {
	
	private String text = "";
	private PApplet pApplet;
	
	public DGText() {
		pApplet = Shared.pApplet;
	}
	
	public DGText(String t) {
		text = t;
		pApplet = Shared.pApplet;
	}
	
	public void setText(String t) {
		text = t;
	}
	
	public void paint(PGraphics p) {
		p.text(text, 0, 0);
	}

}
