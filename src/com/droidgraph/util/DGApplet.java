package com.droidgraph.util;

import processing.core.PApplet;
import processing.core.PConstants;
import android.util.Log;

public class DGApplet extends PApplet implements PConstants {
	
	String TAG = "DGApplet";
	
	private int lc = 0;
	
	public void rect(float x, float y, float w, float h) {
		Log.d(TAG, lc + " rect");
		super.rect(x,y,w,h);
		lc++;
	}
	
	public void fill( float r, float g, float b, float a) {
		super.fill(r,g,b,a);
		Log.d(TAG, lc + " fill(f,f,f,f)");
		lc++;
	}

}
