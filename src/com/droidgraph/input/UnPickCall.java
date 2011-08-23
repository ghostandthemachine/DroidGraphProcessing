package com.droidgraph.input;

import com.droidgraph.event.DGMotionEvent;

public class UnPickCall {
	
	private MultiTouchManager manager;
	private DGMotionEvent motionEvent;
	private int numChecks = 1;

	public UnPickCall(MultiTouchManager manager, DGMotionEvent me, int numChecks) {
		this.manager = manager;
		this.motionEvent = me;
		this.numChecks = numChecks;
	}

	public void unPick() {
		// call another handlePOinterUp
		numChecks++;
		manager.handlePointerUp(motionEvent, true);
	}
	
	public int getID() {
		return motionEvent.getID();
	}
	
	

}
