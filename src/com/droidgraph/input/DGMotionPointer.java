package com.droidgraph.input;

import android.view.MotionEvent;

import com.droidgraph.event.DGMotionEvent;
import com.droidgraph.scene.DGNode;

public class DGMotionPointer {

	// the target node to send the motion events to when locked
	private DGNode targetNode = null;

	// lock on to a target
	private boolean lock = false;

	// the global pointer id
	private int globalPointerID;

	// This id stores the local pointer count id for the currently locked target
	private int targetNodeID = 0;

	// Custom event class to manager more easily
	public DGMotionEvent event;

	// The parent motion manager
	private MotionManager motionManager;

	public DGMotionPointer(MotionManager motionManager, int id) {
		this.motionManager = motionManager;
		this.globalPointerID = id;
		this.event = new DGMotionEvent(null, id, motionManager.getNumPointers());
	}

	public void update(MotionEvent me, int action) {
		if (event != null) {
			// update the custom event holder
			event.update(me);
		}
		if (targetNode != null) {
			// have the target process the event
			targetNode.processMotionEvent(this, action);
		}
	}

	public void lock(DGNode targetNode, int targetNodeID) {
		lock = true;
		this.targetNode = targetNode;
		this.targetNodeID = targetNodeID;
		event.targetID = targetNodeID;
		targetNode.addPointer(globalPointerID);
	}

	public void unlock() {
		if (targetNode != null) {
			targetNode.removePointer(targetNodeID);
		}
		lock = false;
		targetNode = null;
		targetNodeID = -1;

	}

	public boolean locked() {
		return lock;
	}

	public int getID() {
		return globalPointerID;
	}

	public int getTargetPointerID() {
		return targetNodeID;
	}

	public void setTarget(DGNode node) {
		targetNode = node;
		event.setTarget(node);
	}

	public DGNode getTarget() {
		return targetNode;
	}

	public int getNumPointers() {
		return motionManager.getNumPointers();
	}

	public void clear() {
		targetNode = null;
		event = new DGMotionEvent(null, globalPointerID, motionManager.getNumPointers());
	}

	public String s() {
		return "DGMotionPointer : " + this;
	}
}
