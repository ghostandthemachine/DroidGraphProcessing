package com.droidgraph.scene;

import processing.core.PApplet;
import android.os.Handler;

import com.droidgraph.transformation.Bounds2D;
import com.droidgraph.util.Shared;

public class DGScene {

	private Bounds2D bounds;

	private PApplet parent;
	private DGGroup root;
	private int[] bg = { 0, 0, 0, 255 };

	public Handler mHandler;

	public DGScene(PApplet parent, String renderMode) {
		this.parent = parent;

		bounds = new Bounds2D(0, 0, parent.screenWidth, parent.screenHeight);

		// Set the global variables in the Helper class
		Shared.setPApplet(parent);
		Shared.setRenderer(renderMode);

		root = new DGGroup();
		root.setParent(this);
		root.bounds = bounds;
		root.setTouchBlocker(false);
	}

	public void add(DGNode node) {
		root.add(node);
	}

	public PApplet getParentApplet() {
		return parent;
	}

	public DGGroup getRoot() {
		return root;
	}

	void clearScene() {
		root = null;
	}

	public void draw() {

		parent.background(bg[0], bg[1], bg[2], bg[3]);
		root.render();
	}

	public void setBackground(int r, int g, int b, int a) {
		bg[0] = r;
		bg[1] = g;
		bg[2] = b;
		bg[3] = a;
	}



	// private void p(String s) {
	// Log.d("DGScene", s);
	// }

}
