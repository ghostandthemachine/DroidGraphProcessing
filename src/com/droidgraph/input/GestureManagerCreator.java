package com.droidgraph.input;

import android.view.GestureDetector;

import com.droidgraph.util.Shared;

public class GestureManagerCreator implements Runnable {
	
	private DGGestureManager gestureManager;
	private GestureDetector gestureDetector;
	
	public GestureManagerCreator(GestureDetector gd, DGGestureManager dgm) {
		this.gestureManager = dgm;
		this.gestureDetector = gd;
	}

	@Override
	public void run() {

		this.gestureManager = new DGGestureManager();
		this.gestureDetector = new GestureDetector(gestureManager);
		Shared.multiTouchManager.setGestureDetector(this.gestureDetector);
	}

}
