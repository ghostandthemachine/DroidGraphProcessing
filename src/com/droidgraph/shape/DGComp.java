//package com.droidgraph.shape;
//
//import processing.core.PApplet;
//import processing.core.PConstants;
//import processing.core.PGraphics;
//import processing.core.PGraphicsAndroid2D;
//import processing.core.PGraphicsAndroid3D;
//
//import com.droidgraph.transformation.Bounds2D;
//import com.droidgraph.util.Shared;
//
//public class DGComp3D extends PGraphicsAndroid3D implements PConstants {
//
//	private PApplet pa;
//	private Bounds2D bounds;
//
//	public DGComp3D(int width, int height) {
//		bounds = new Bounds2D(0, 0, width, height);
//		pa = Shared.pApplet;
//
//		// Create the buffer to be drawn to
//			mPg = (PGraphicsAndroid3D) pa.createGraphics(width, height, P3D);
//
//	}
//
//	public void paint(PGraphics pg) {
//
//	}
//
//	public Bounds2D getBounds() {
//		return bounds;
//	}
//}
