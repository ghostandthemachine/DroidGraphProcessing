package com.droidgraph.event;

public class DGMotionEvent {

	
	public int id;
	public int localID = -1;
	public int action;
	public float x;
	public float y;
	public float velocityX = 0;
	public float velocityY = 0;
	public float pressure;
	public long time;
	public DGMotionPackage pack;
	
	
	public DGMotionEvent(DGMotionPackage p, int id, int action, float x, float y, float pressure) {
		this.id = id;
		this.action = action;
		this.x = x;
		this.y = y;
		this.pressure = pressure;
		this.time = p.getTime();
		this.pack = p;
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}
	
	public DGMotionPackage getPackage() {
		return pack;
	}


	/**
	 * @return the localID. If returns (-1), then this event has no local id assigned
	 */
	public int getLocalID() {
		return localID;
	}

	/**
	 * @param localID the localID to set
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
		return pack.getActionMasked();
	}

	/**
	 * @param action the action to set
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
	 * @param x the x to set
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
	 * @param y the y to set
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
	 * @param pressure the pressure to set
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
