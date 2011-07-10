package com.droidgraph.input;

public class MTAtom {
	
	public int id;
	public float mx;
	public float my;
	public float vx;
	public float vy;
	
	public MTAtom (int id, float mx, float my) {
		this.id = id;
		this.mx = mx;
		this.my = my;
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
	/**
	 * @return the mx
	 */
	public float getMx() {
		return mx;
	}
	/**
	 * @param mx the mx to set
	 */
	public void setMx(float mx) {
		this.mx = mx;
	}
	/**
	 * @return the my
	 */
	public float getMy() {
		return my;
	}
	/**
	 * @param my the my to set
	 */
	public void setMy(float my) {
		this.my = my;
	}
	/**
	 * @return the vx
	 */
	public float getVx() {
		return vx;
	}
	/**
	 * @param vx the vx to set
	 */
	public void setVx(float vx) {
		this.vx = vx;
	}
	/**
	 * @return the vy
	 */
	public float getVy() {
		return vy;
	}
	/**
	 * @param vy the vy to set
	 */
	public void setVy(float vy) {
		this.vy = vy;
	}

}
