package com.droidgraph.event;

import android.view.MotionEvent;

public class DGEventParsed {

	public int action;
	public int globalPointerID;
	public MotionEvent me;
	
	public DGEventParsed(int action, int pid, MotionEvent me) {
		this.globalPointerID = pid;
		this.me = me;
		this.action = action;
	}

	/**
	 * @return the action
	 */
	public int getAction() {
		return action;
	}

	/**
	 * @param action the action to set
	 */
	public void setAction(int action) {
		this.action = action;
	}

	/**
	 * @return the pid
	 */
	public int getPid() {
		return globalPointerID;
	}

	/**
	 * @param pid the pid to set
	 */
	public void setPid(int pid) {
		this.globalPointerID = pid;
	}

	/**
	 * @return the me
	 */
	public MotionEvent getMe() {
		return me;
	}

	/**
	 * @param me the me to set
	 */
	public void setMe(MotionEvent me) {
		this.me = me;
	}

}
