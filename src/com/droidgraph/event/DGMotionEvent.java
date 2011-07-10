package com.droidgraph.event;

import android.view.MotionEvent;

import com.droidgraph.scene.DGNode;

public class DGMotionEvent {

	public int id = -1;
	
	public int targetID = 0;

	public float x = 0;
	public float y = 0;

	public float localX = 0;
	public float localY = 0;

	public float lastX = -9999;
	public float lastY = -9999;

	public float vx = 0;
	public float vy = 0;

	public int action = 1;

	public DGNode parent;
	
	public int numPointers = 0;

	public DGMotionEvent(DGNode node, int id, int np) {
		this.id = id;
		this.parent = node;
		this.numPointers = np;
	}

	public void setTarget(DGNode node) {
		this.parent = node;
	}

	public void update(MotionEvent me) {
		if (id < me.getPointerCount()) {
			action = me.getAction();
			lastX = x;
			lastY = y;
			x = me.getX(id);
			y = me.getY(id);
			if (lastX != -9999) {
				vx = x - lastX;
				vy = y - lastY;
			}
		}
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the targetID
	 */
	public int getTargetID() {
		return targetID;
	}

	/**
	 * @param targetID the targetID to set
	 */
	public void setTargetID(int targetID) {
		this.targetID = targetID;
	}

	/**
	 * @return the x
	 */
	public float getX() {
		return x;
	}

	/**
	 * @return the y
	 */
	public float getY() {
		return y;
	}

	public float getLocalX() {
		return x - parent.getBounds2D().x;
	}

	public float getLocalY() {
		return y - parent.getBounds2D().y;
	}

	/**
	 * @return the lastX
	 */
	public float getLastX() {
		return lastX;
	}

	/**
	 * @return the lastY
	 */
	public float getLastY() {
		return lastY;
	}

	/**
	 * @return the vx
	 */
	public float getVelocityx() {
		return vx;
	}

	/**
	 * @return the vy
	 */
	public float getVelocityy() {
		return vy;
	}

	public int getAction() {
		return action;
	}

	public void setNumPointers(int i) {
		numPointers = i;
	}
	
	public int getNumPointers() {
		return numPointers;
	}
}
