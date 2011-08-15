package com.droidgraph.picking;

import android.graphics.Color;

import com.droidgraph.event.DGMotionEvent;
import com.droidgraph.input.MultiTouchManager;
import com.droidgraph.util.Shared;

public class Pick {

	String TAG = "Pick";

	int touchDownX;
	int touchDownY;

	MultiTouchManager manager;
	int pid;
	DGMotionEvent dgme;

	public Pick(MultiTouchManager manager, DGMotionEvent dgme) {
		this.manager = manager;
		this.dgme = dgme;
		this.pid = dgme.getId();
		touchDownX = (int) (dgme.getX());
		touchDownY = (int) (dgme.getY());
	}

	public void pick() {
		int bc = Shared.offscreenBuffer.get(touchDownX, touchDownY);
		int r = Color.red(bc);
		int g = Color.green(bc);
		int b = Color.blue(bc);
		int c = r | (g << 8) | (b << 16);
		// if not touching the background rgba(0,0,0,0) then call motionManager
		if (bc != -1) {

//			Shared.p("Pick - pick() , get(), id", bc, c);
			manager.handlePickSelection(dgme, c, pid);
		}
	}
}
