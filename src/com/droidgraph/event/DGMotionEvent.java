package com.droidgraph.event;

import android.view.MotionEvent;

import com.droidgraph.fx.DGFXNode;
import com.droidgraph.scene.DGNode;
import com.droidgraph.transformation.Vec3f;
import com.droidgraph.util.Shared;

public class DGMotionEvent {

	/*
	 * the global Android pointer ID
	 */
	public int id;

	/*
	 * the local pointer ID for the attached node
	 */
	public int localID = -1;

	/*
	 * if this event is attached to a node in the graph
	 */
	public boolean attached = false;

	/*
	 * this events Android action
	 */
	public int action;

	/*
	 * the pointers X coordinate
	 */
	public float x;

	/*
	 * the pointers Y coordinate
	 */
	public float y;

	/*
	 * the local X coordinate relative to the attached node (if there is one)
	 */
	public float localX;

	/*
	 * the local Y coordinate relative to the attached node (if there is one)
	 */
	public float localY;

	/*
	 * the motion VelocityX of this pointer if it is not a new pointer
	 */
	public float velocityX = 0;

	/*
	 * the motion VelocityY of this pointer if it is not a new pointer
	 */
	public float velocityY = 0;

	/*
	 * the pressure of this pointer
	 */
	public float pressure;

	/*
	 * the time this event took place
	 */
	public long time;

	/*
	 * the parent package of this event
	 */
	public DGMotionPackage pack;

	/*
	 * the attached node if there is one
	 */
	public DGNode attachedNode;

	public DGMotionEvent(DGMotionPackage p, int id, int action, float x,
			float y, float pressure) {
		this.id = id;
		this.action = action;
		this.x = x;
		this.y = y;
		this.pressure = pressure;
		this.time = p.getTime();
		this.pack = p;

		this.attached = false;
		this.attachedNode = null;
	}

	public void attachNode(DGNode node) {

		attachedNode = node;
		attached = true;

		Vec3f local = node.globalToLocal(new Vec3f(x, y, 0), null);
		localX = local.x;
		localY = local.y;
	}

	public void detachNode() {
		attachedNode = null;
		attached = false;
	}

	public DGNode getAttachedNode() {
		return attachedNode;
	}

	public boolean isAttached() {
		return attached;
	}

//	private Vec3f globalToLocal(Vec3f global) {
//		Vec3f local = new Vec3f();
//		if (attachedNode != null) {
//			if(attachedNode instanceof DGFXNode) {
//				Shared.p("DGMOtionEvent, globaltolocal call");
//				attachedNode.globalToLocal(global, local);
//			}
//		}
//		return local;
//	}

	/**
	 * @return the localX
	 */
	public float getLocalX() {
		return localX;
	}

	/**
	 * @param localX
	 *            the localX to set
	 */
	public void setLocalX(float localX) {
		this.localX = localX;
	}

	/**
	 * @return the localY
	 */
	public float getLocalY() {
		return localY;
	}

	/**
	 * @param localY
	 *            the localY to set
	 */
	public void setLocalY(float localY) {
		this.localY = localY;
	}

	/**
	 * @param velocityX
	 *            the velocityX to set
	 */
	public void setVelocityX(float velocityX) {
		this.velocityX = velocityX;
	}

	/**
	 * @param velocityY
	 *            the velocityY to set
	 */
	public void setVelocityY(float velocityY) {
		this.velocityY = velocityY;
	}

	/**
	 * @param time
	 *            the time to set
	 */
	public void setTime(long time) {
		this.time = time;
	}

	/**
	 * @return the id
	 */
	public int getID() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	public DGMotionPackage getPackage() {
		return pack;
	}

	/**
	 * @return the localID. If returns (-1), then this event has no local id
	 *         assigned
	 */
	public int getLocalID() {
		return localID;
	}

	/**
	 * @param localID
	 *            the localID to set
	 */
	public void setLocalID(int localID) {
		this.localID = localID;
	}

	/**
	 * @return the action
	 */
	public int getAction() {
		return action;
	}

	public int getActionMasked() {
		return action & MotionEvent.ACTION_MASK;
	}

	/**
	 * @param action
	 *            the action to set
	 */
	public void setAction(int action) {
		this.action = action;
	}

	/**
	 * @return the x
	 */
	public float getX() {
		return x;
	}

	/**
	 * @param x
	 *            the x to set
	 */
	public void setX(float x) {
		this.x = x;
	}

	/**
	 * @return the y
	 */
	public float getY() {
		return y;
	}

	/**
	 * @param y
	 *            the y to set
	 */
	public void setY(float y) {
		this.y = y;
	}

	/**
	 * @return the pressure
	 */
	public float getPressure() {
		return pressure;
	}

	/**
	 * @param pressure
	 *            the pressure to set
	 */
	public void setPressure(float pressure) {
		this.pressure = pressure;
	}

	/**
	 * @return the time stamp
	 */
	public long getTime() {
		return time;
	}

	/**
	 * @return the number of pointers present when this event was created
	 */
	public int getPointerCount() {
		return pack.getPointerCount();
	}

	public void setVelocity(float vx, float vy) {
		velocityX = vx;
		velocityY = vy;
	}

	public float getVelocityX() {
		return velocityX;
	}

	public float getVelocityY() {
		return velocityY;
	}

	/**
	 * @return string representing this DGMotionEvent
	 */
	public String toString() {
		StringBuilder s = new StringBuilder();
		s.append("DGMotionEvent");
		s.append(" - PointerID:" + id);
		s.append(" LocalID:" + localID);
		s.append(" Action:" + action);
		s.append(" x:" + x);
		s.append(" y:" + y);
		s.append(" pressure:" + pressure);
		return s.toString();
	}

}
