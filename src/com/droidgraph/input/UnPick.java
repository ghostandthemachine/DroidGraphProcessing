package com.droidgraph.input;

import java.util.HashMap;

import com.droidgraph.event.DGMotionEvent;
import com.droidgraph.scene.DGNode;

public class UnPick {

	long startTime;
	HashMap<Integer, DGNode> pointerToNodeMap;
	DGMotionEvent me;
	MultiTouchManager manager;

	public UnPick(MultiTouchManager manager) {
		this.manager = manager;
	}

	public void unPick() {
//		manager.handlePointerUp(me, true);
	}

	public void updateUnPick(DGMotionEvent me) {
		this.me = me;
	}

};