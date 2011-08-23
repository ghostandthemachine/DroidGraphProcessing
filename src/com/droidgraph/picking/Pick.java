package com.droidgraph.picking;

import android.graphics.Color;

import com.droidgraph.event.DGMotionEvent;
import com.droidgraph.input.MultiTouchManager;
import com.droidgraph.util.Shared;

public class Pick {
	
	public boolean DEBUG = false;

	String TAG = "Pick";

	int touchDownX;
	int touchDownY;

	MultiTouchManager manager;
	int pid;
	DGMotionEvent motionEvent;

	public Pick(MultiTouchManager manager, DGMotionEvent me) {
		this.manager = manager;
		this.motionEvent = me;
		this.pid = me.getID();
		touchDownX = (int) (me.getX());
		touchDownY = (int) (me.getY());
	}

	public void pick() {
		int bc = Shared.offscreenBuffer.get(touchDownX, touchDownY);
		int r = Color.red(bc);
		int g = Color.green(bc);
		int b = Color.blue(bc);
		int c = r | (g << 8) | (b << 16);
		// if not touching the background rgba(0,0,0,0) then call motionManager
		if (bc != -1) {

			if(DEBUG) {
				Shared.p("Pick - pick() , get(), id", bc, c);
			}
			
			manager.handlePickSelection(motionEvent, c, pid);
		}
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Pick - ");
		sb.append("pointer id:" + pid);
		sb.append(", DGMotionEvent:" + motionEvent);
		return sb.toString();
	}

	public int getPointerID() {
		return motionEvent.getID();
	}
}
