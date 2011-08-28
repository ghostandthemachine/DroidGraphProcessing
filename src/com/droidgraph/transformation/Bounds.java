package com.droidgraph.transformation;

import processing.core.PApplet;

import com.droidgraph.util.Shared;

public class Bounds {

	public boolean DEBUG = false;
	private final String TAG = "Bounds - ";

	private float x = 0;
	private float y = 0;
	private float z = 0;

	private float width = 0;
	private float height = 0;
	private float depth = 0;

	public Bounds(float width, float height, float depth) {
		this.width = width;
		this.height = height;
		this.depth = depth;
	}

	public Bounds(float width, float height) {
		this.width = width;
		this.height = height;
	}

	public Bounds(float x, float y, float width, float height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	public Bounds(float x, float y, float z, float width,
			float height, float depth) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.width = width;
		this.height = height;
		this.depth = depth;
	}

	public Bounds() {

	}

	public Bounds(Bounds bounds) {
		this.x = bounds.x;
		this.y = bounds.y;
		this.z = bounds.z;
		this.width = bounds.width;
		this.height = bounds.height;
		this.depth = bounds.depth;
	}

	public boolean contains(float px, float py) {
		return px > x && px < (x + width) && py > y && py < (y + height);
	}
	
	public boolean contains(float px, float py, float pz) {
		return px > x && px < (x + width) && py > y && py < (y + height) && pz > z && pz < (z + depth);
	}

	public void accumulate(Bounds bounds) {
		if (DEBUG) {
			Shared.p(TAG,"accumulate(), start", this);
		}

		if (x == 0 && y == 0 && width == 0 && height == 0) {

			// If the current bounds are nothing, then set them to the first
			// bounds to accumulate
			x = bounds.getX();
			y = bounds.getY();
			width = bounds.getWidth();
			height = bounds.getHeight();

		} else {

			float tx = bounds.getX();
			float ty = bounds.getY();
			float tw = bounds.getWidth();
			float th = bounds.getHeight();

			if (tx < x) {
				x = tx;
			}

			if (tw + tx > x + width) {
				width = (tx + tw) - x;
			}

			if (ty < y) {
				y = ty;
			}

			if (ty + th > y + height) {
				height = (ty + th) - y;
			}

		}
		if (DEBUG) {
			Shared.p(TAG,"accumulate(), finish", this);
		}
	}

	public void setBounds(float x, float y, float width, float height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	public void setBounds(float x, float y, float z, float width, float height,
			float depth) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.width = width;
		this.height = height;
		this.depth = depth;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder("Bounds2D - ");
		sb.append("x:" + x);
		sb.append(", ");
		sb.append("y:" + y);
		sb.append(", ");
		sb.append("width:" + width);
		sb.append(", ");
		sb.append("height:" + height);
		return sb.toString();
	}

	public Bounds getBounds() {
		return this;
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

	public float getZ() {
		return z;
	}

	public void setZ(float z) {
		this.z = z;
	}

	public void setDepth(float depth) {
		this.depth = depth;
	}

	public float getDepth() {
		return depth;
	}

	/**
	 * @return the width
	 */
	public float getWidth() {
		return width;
	}

	/**
	 * @param width
	 *            the width to set
	 */
	public void setWidth(float width) {
		this.width = width;
	}

	/**
	 * @return the height
	 */
	public float getHeight() {
		return height;
	}

	/**
	 * @param height
	 *            the height to set
	 */
	public void setHeight(float height) {
		this.height = height;
	}

	public void accumulateChild(Bounds bounds) {
		this.x = bounds.getX();
		this.y = bounds.getY();
		this.z = bounds.getZ();

		this.width = bounds.getWidth();
		this.height = bounds.getHeight();
		this.depth = bounds.getDepth();
	}

	public void set(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public boolean isEmpty() {
		return (x == 0 && y == 0 && z == 0 && width == 0 && height == 0 && depth == 0);
	}

	public void add(Bounds newrect) {
		this.x += newrect.x;
		this.y += newrect.y;
		this.z += newrect.z;
		this.width += newrect.width;
		this.height += newrect.height;
		this.depth += newrect.depth;
	}

	public Bounds clone() {
		return new Bounds(this);
	}

	public Bounds getBounds(Vec3f transform) {
		Bounds bounds = new Bounds(x - transform.x, y - transform.y, z
				- transform.y, this.width, this.height, this.depth);
		return bounds;
	}

	public void transform(Vec3f vec) {
		x = vec.x;
		y = vec.y;
		z = vec.z;
	}

	public Bounds createIntersection(Bounds b2) {
		Bounds b1 = this;
		Bounds result = new Bounds(
				PApplet.max(b1.getLeft(), b2.getLeft()), 
				PApplet.max(b1.getTop(), b2.getTop()),
				PApplet.max(b1.getFront(), b2.getFront()),  
				PApplet.min(b1.getRight(), b2.getRight()), 
				PApplet.min(b1.getBottom(), b2.getBottom()), 
				PApplet.min(b1.getBack(), b2.getBack()));
		if(DEBUG) {
			Shared.p("Bounds - createIntersection(), result:", result);
		}
		return result;
	}

	public boolean intersects(Bounds b) {
		return !(x > b.x + width || x + width < b.x || y > b.y + b.height
				|| y + height < b.y || z > b.z + b.depth || z + depth < b.z);
	}
	
	public float getLeft() {
		return x;
	}
	
	public float getRight() {
		return x + width;
	}
	
	public float getTop() {
		return y;
	}
	
	public float getBottom() {
		return y + height;
	}
	
	public float getFront() {
		return z;
	}
	
	public float getBack() {
		return z + depth;
	}

	public boolean contains(Vec3f vec) {
		contains(vec.x, vec.y, vec.z);
		return false;
	}
}
