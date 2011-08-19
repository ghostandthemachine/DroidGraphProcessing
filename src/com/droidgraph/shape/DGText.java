package com.droidgraph.shape;

import processing.core.PFont;
import processing.core.PGraphics;

public class DGText extends DGPShape2D {
	
	private String text = "";
	private PFont font;
	
	public DGText() {
	}
	
	public DGText(String t) {
		text = t;
	}
	
	public void setText(String t) {
		text = t;
	}
	
	public void paint(PGraphics p) {
		p.text(text, 0, 0);
	}

}
