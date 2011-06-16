package com.droidgraph.util;

import processing.core.PApplet;

import com.droidgraph.picking.Picker;
import com.droidgraph.scene.DGNode;

public class Shared {
	
	public static PApplet pApplet = null;
	public static void setPApplet(PApplet p) {
		pApplet = p;
	}

	public static Picker picking;
	public static void setPicking(Picker p) {
		picking = p;
	}
	
	public static String renderer;
	public static void setRenderer(String r) {
		renderer = r;
	}
	
	public static DGNode curenntTouchedNode;
	public static void setCurrentTouchedNode(DGNode node) {
		curenntTouchedNode = node;
	}
}
