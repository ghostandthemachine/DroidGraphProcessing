package com.droidgraph.transformation;

import com.droidgraph.translation.Bounds;
import com.droidgraph.util.Shared;

public class Bounds3D implements Bounds {

	private boolean DEBUG = false;

	private float x = 0;
	private float y = 0;
	private float z = 0;

	private float width = 0;
	private float height = 0;
	private float depth = 0;

	Object parent;

	public Bounds3D(float width, float height, float depth) {
		this.width = width;
		this.height = height;
		this.depth = depth;
	}

	public Bounds3D(Object node, float x, float y, float z, float width, float height, float depth) {
		this.x = x;
		this.y = y;
		this.z = z;
		
		this.width = width;
		this.height = height;
		this.depth = depth;
		
		this.parent = node;
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

	/**
	 * @return the depth
	 */
	public float getDepth() {
		return depth;
	}

	/**
	 * @param depth the depth to set
	 */
	public void setDepth(float depth) {
		this.depth = depth;
	}

	public boolean contains(float px, float py, float pz) {
		return px > x && px < (x + width) && py > y && py < (y + height) && pz > z && pz < (z + depth);
	}

	public void accumulate(Bounds3D... bs) {
		if (DEBUG) {
			Shared.p("start", parent, this);
		}
		for (Bounds3D b : bs) {

			if (x == 0 && y == 0 && width == 0 && height == 0 && z == 0 && depth == 0) {
				
				// If the current bounds are nothing, then set them to the first bounds to accumulate 
				x = b.x;
				y = b.y;
				z = b.z;
				
				width = b.width;
				height = b.height;
				depth = b.depth;
				
			} else {

				float tx = b.x;
				float ty = b.y;
				float tz = b.z;
				
				float tw = b.width;
				float th = b.height;
				float td = b.depth;

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
				
				if (tz < z) {
					z = tz;
				}

				if (tz + td > z + depth) {
					depth = (tz + td) - z;
				}

			}
		}
		if (DEBUG) {
			Shared.p("finish", parent, this);
		}
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
		StringBuilder sb = new StringBuilder("Bounds3D - ");
		sb.append("x:" + x);
		sb.append(", ");
		sb.append("y:" + y);
		sb.append(", ");
		sb.append("z:" + z);
		sb.append(", ");
		sb.append("width:" + width);
		sb.append(", ");
		sb.append("height:" + height);
		sb.append(", ");
		sb.append("depth:" + depth);
		return sb.toString();
	}

	@Override
	public Bounds getBounds() {
		return this;
	}

}
