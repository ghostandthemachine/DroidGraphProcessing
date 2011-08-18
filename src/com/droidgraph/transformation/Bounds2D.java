package com.droidgraph.transformation;

import com.droidgraph.util.Shared;

public class Bounds2D {

	public boolean DEBUG = false;

	public float x = 0;
	public float y = 0;

	public float width = 0;
	public float height = 0;

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

	public void accumulate(Bounds2D... bs) {
		if (DEBUG) {
			Shared.p("start", parent, this);
		}
		for (Bounds2D b : bs) {

			if (x == 0 && y == 0 && width == 0 && height == 0) {
				
				// If the current bounds are nothing, then set them to the first bounds to accumulate 
				x = b.x;
				y = b.y;
				width = b.width;
				height = b.height;
				
			} else {

				float tx = b.x;
				float ty = b.y;
				float tw = b.width;
				float th = b.height;

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

}
