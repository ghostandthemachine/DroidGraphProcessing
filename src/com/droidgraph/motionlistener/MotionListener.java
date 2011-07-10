package com.droidgraph.motionlistener;

import com.droidgraph.event.DGMotionEvent;
import com.droidgraph.scene.DGNode;
import com.droidgraph.util.Shared;

public abstract class MotionListener {
	
	String TAG = "MotionListener";
	
	protected DGNode parent;
	private boolean touchOne = false;
	
	
	public MotionListener(DGNode node) {
		parent = node;
		parent.setTouchable(true);
		Shared.addListenerNode(node);
	}

	public boolean actionDown(DGMotionEvent me, int pid) {
		touchOne = true;
		return true;
	}

	public boolean actionMove(DGMotionEvent me, int pid) {
		return true;
	}

	public boolean actionUp(DGMotionEvent me, int pid) {
		if(touchOne) {
			touchOne = false;
			clicked(me, pid);
		}
		return true;
	}
	
	public boolean clicked(DGMotionEvent me, int pid) {
		
		return true;
	}
	
	public DGNode getParent() {
		return parent;
	}

	public boolean actionPointerDown(DGMotionEvent me, int pid) {
		return true;
	}

	public boolean actionPointerUp(DGMotionEvent me, int pid) {
		return true;
	}

}
