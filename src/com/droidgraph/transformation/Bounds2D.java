package com.droidgraph.transformation;

import com.droidgraph.translation.Bounds;
import com.droidgraph.util.Shared;

public class Bounds2D implements Bounds {

	public boolean DEBUG = false;

	private float x = 0;
	private float y = 0;
	private float z = 0;

	private float width = 0;
	private float height = 0;
	private float depth = 0;

	Object parent;

	public Bounds2D(float width, float height) {
		this.width = width;
		this.height = height;
	}

	public Bounds2D(Object node, float x, float y, float width, float height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.parent = node;
	}

	public boolean contains(float px, float py) {
		return px > x && px < (x + width) && py > y && py < (y + height);
	}

	public void accumulate(Bounds... bs) {
		if (DEBUG) {
			Shared.p("start", parent, this);
		}
		for (Bounds b : bs) {

			if (x == 0 && y == 0 && width == 0 && height == 0) {
				
				// If the current bounds are nothing, then set them to the first bounds to accumulate 
				x = b.getX();
				y = b.getY();
				width = b.getWidth();
				height = b.getHeight();
				
			} else {

				float tx = b.getX();
				float ty = b.getY();
				float tw = b.getWidth();
				float th = b.getHeight();

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
		}
		if (DEBUG) {
			Shared.p("finish", parent, this);
		}
	}

	public void setBounds(float x, float y, float width, float height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	public void setBounds(float x, float y, float z, float width, float height, float depth) {
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

	@Override
	public Bounds getBounds() {
		// TODO Auto-generated method stub
		return this;
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
	 * @param width the width to set
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
	 * @param height the height to set
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

}
