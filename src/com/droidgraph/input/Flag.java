package com.droidgraph.input;

public class Flag {
	
	public boolean flag = false;
	public int pointerID = -1;
	
	public Flag(int id, boolean b) {
		pointerID = id;
		flag = b;
	}

	/**
	 * @return the flag
	 */
	public boolean isFlag() {
		return flag;
	}

	/**
	 * @param flag the flag to set
	 */
	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	/**
	 * @return the pointerID
	 */
	public int getPointerID() {
		return pointerID;
	}

	/**
	 * @param pointerID the pointerID to set
	 */
	public void setPointerID(int pointerID) {
		this.pointerID = pointerID;
	}

}
