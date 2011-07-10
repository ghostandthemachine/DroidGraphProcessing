package com.droidgraph.fx;

import processing.core.PGraphics;

public class DGCompGraphics extends PGraphics {

	// Picking
	private boolean picking = false;

	public void setPicking(boolean b) {
		picking = b;
	}

	public boolean isPicking() {
		return picking;
	}

	// For the Droid Graph picking to work correctly, the fill calls must be
	// skipped
	// to keep the unique color ids consistant. For this reason, ONLY ONE DRAWN
	// OBJECT
	// may be detected. This means if more than one shape is drawn, picking any
	// one of
	// them will lock a pointer onto this DGComp

	// Fill overrides
	@Override
	public void fill(float r, float g, float b, float a) {
		if (!picking) {
			super.fill(r, g, b, a);
		}
	}

	@Override
	public void fill(float rgb) {
		if (!picking) {
			super.fill(rgb);
		}
	}

	@Override
	public void fill(int rgb) {
		if (!picking) {
			super.fill(rgb);
		}
	}

	@Override
	public void fill(float g, float a) {
		if (!picking) {
			super.fill(g, a);
		}
	}

	@Override
	public void fill(int g, float a) {
		if (!picking) {
			super.fill(g, a);
		}
	}

	@Override
	public void fill(float r, float g, float b) {
		if (!picking) {
			super.fill(r, g, b);
		}
	}

	// Background overrides

	@Override
	public void background(int rgb) {
		if (!picking) {
			super.background(rgb);
		}
	}

	@Override
	public void background(int rgb, float a) {
		if (!picking) {
			super.background(rgb, a);
		}
	}

	@Override
	public void background(float g) {
		if (!picking) {
			super.background(g);
		}
	}

	@Override
	public void background(float g, float a) {
		if (!picking) {
			super.background(g, a);
		}
	}

	@Override
	public void background(float r, float g, float b) {
		if (!picking) {
			super.background(r, g, b);
		}
	}

	@Override
	public void background(float r, float g, float b, float a) {
		if (!picking) {
			super.background(r, g, b, a);
		}
	}

}
