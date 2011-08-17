package com.droidgraph.transformation;

import com.droidgraph.affine.DGAffineTransform;
import com.droidgraph.fx.DGFXShape;
import com.droidgraph.util.Shared;

public class Bounds2D {

	public boolean DEBUG = false;

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

	public void accumulate(Bounds2D... bs) {
		if (DEBUG) {
			Shared.p("Bounds2D - accumulate - start", this);
		}
		for (Bounds2D b : bs) {
			float tx = b.x;
			float ty = b.y;
			float tw = b.width;
			float th = b.height;
			
			if(tx < x ) {
				x = tx;
			}
			
			if(tw + tx > x + width) {
				width = (tx + tw) - x;
			}
			
			if(ty < y) {
				y = ty;
			}
			
			if(ty + th > y + height) {
				height = (ty + th) - y;
			}
			
		}
		if (DEBUG) {
			Shared.p("Bounds2D - accumulate - finish", x, y, width, height);
		}

	}

	public void accumulate(DGAffineTransform g) {
		if (DEBUG) {
			Shared.p("Bounds2D - accumulate - start", g, this);
		}
		
		Bounds2D b = g.getBounds2D();
		
		float tx = g.getTranslateX();
		float ty = g.getTranslateY();
		float tw = g.getWidth();
		float th = g.getHeight();
		
		if(tx < x ) {
			if(DEBUG) {
				Shared.p("tx < x", x, tx);
			}
			x = tx;
		}
		
		if(tw + tx > x + width) {
			if(DEBUG) {
				Shared.p("tw + tx > x + width",tw + tx, x + width);
			}
			width = (tx + tw) - x;
		}
		
		if(ty < y) {
			if(DEBUG) {
				Shared.p("ty < y", y, ty);
			}
			y = ty;
		}
		
		if(ty + th > y + height) {
			if(DEBUG) {
				Shared.p("th + ty > y + height",th + ty, y + height);
			}
			height = (ty + th) - y;
		}
		
		if (DEBUG) {
			Shared.p("Bounds2D - accumulate - finish", g, this);
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

	public void accumulate(DGFXShape g) {
		if (DEBUG) {
			Shared.p("Bounds2D - accumulate - start, DGFXShape", g.getTranslateX(), g.getTranslateY(), g.getWidth(), g.getHeight(), this);
		}
		Bounds2D b = g.getBounds2D();
		
		float tx = g.getTranslateX();
		float ty = g.getTranslateY();
		float tw = g.getWidth();
		float th = g.getHeight();
		
		if(tx < x ) {
			if(DEBUG) {
				Shared.p("tx < x", x, tx);
			}
			x = tx;
		}
		
		if(tw + tx > x + width) {
			if(DEBUG) {
				Shared.p("tw + tx > x + width",tw + tx, x + width);
			}
			width = (tx + tw) - x;
		}
		
		if(ty < y) {
			if(DEBUG) {
				Shared.p("ty < y", y, ty);
			}
			y = ty;
		}
		
		if(ty + th > y + height) {
			if(DEBUG) {
				Shared.p("th + ty > y + height",th + ty, y + height);
			}
			height = (ty + th) - y;
		}
		
		if (DEBUG) {
			Shared.p("Bounds2D - accumulate - finish", g, this);
		}
	}

}
