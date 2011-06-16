package com.droidgraph.transformation;

public class Bounds2D {
	
	public float x = 0;
	public float y = 0;
	
	public float width = 0;
	public float height = 0;

	public Bounds2D(float width, float height) {
		this.width = width;
		this.height = height;
	}
	
	public Bounds2D(float x, float y, float width, float height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	
	public boolean contains(float px, float py) {
		return px > x && px < (x + width) && py > y && py < (y + height);
	}
	
	public void accumulate(Bounds2D ... bs) {
		for (Bounds2D b : bs) {
			x = b.x < x ? b.x : x;
			y = b.y < y ? b.y : y;
			width = (b.x + b.width) < (x + width) ? width : (width + ((b.x + b.width) - (x + width)));
			height = (b.y + b.height) < (y + height) ? height : (height + ((b.y + b.height) - (y + height)));
		}
	}

	public void setBounds(float x, float y, float width, float height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	
}
