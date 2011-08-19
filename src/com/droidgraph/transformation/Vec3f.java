package com.droidgraph.transformation;

public class Vec3f {
	
	public float x;
	public float y;
	public float z;
	
	public Vec3f(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public Vec3f() {
		this(0,0,0);
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
	 * @return the z
	 */
	public float getZ() {
		return z;
	}

	/**
	 * @param z the z to set
	 */
	public void setZ(float z) {
		this.z = z;
	}
	
	/**
	 * @param add one Vec3f to another
	 */
	public void add(Vec3f vec3f) {
		x += vec3f.x;
		y += vec3f.y;
		z += vec3f.z;
	}
	

}
