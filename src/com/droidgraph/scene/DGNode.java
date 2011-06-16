package com.droidgraph.scene;

import processing.core.PApplet;
import android.util.Log;
import android.view.MotionEvent;

import com.droidgraph.transformation.Bounds2D;
import com.droidgraph.util.Shared;

public abstract class DGNode {

	public boolean visible = true;

	private boolean isTouchBlocker = false;

	private Object parent;
	private PApplet p;

	protected Bounds2D bounds = new Bounds2D(0, 0, 0, 0);

	private boolean touchable = false;

	public DGNode() {
		p = Shared.pApplet;
	}

	public final boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		if (this.visible != visible) {
			if (visible) {
				this.visible = true;
			} else {
				this.visible = false;
			}
		}
	}

	/**
	 * @return {@code true} if motion event should not be dispatched to the
	 *         nodes underneath this one.
	 */
	public final boolean isTouchBlocker() {
		return isTouchBlocker;
	}

	public final void setTouchBlocker(boolean value) {
		if (value != isTouchBlocker) {
			isTouchBlocker = value;
		}
	}

	public Object getParent() {
		return parent;
	}

	public final void setParent(Object parent) {
		assert (parent == null || parent instanceof DGParent || parent instanceof DGScene);
		this.parent = parent;

	}

	public void motionEvent(MotionEvent event) {
		float mx = event.getX();
		float my = event.getY();
		int action = event.getAction();

		if (this instanceof DGParent) {
			if (action == MotionEvent.ACTION_DOWN) {
				for (DGNode child : ((DGParent) this).getChildren()) {
					if (child.isTouchable()) {
						if (child.contains(mx, my)) {
							Shared.setCurrentTouchedNode(child);
							child.motionEvent(event);
						}
					}
				}

			}
		} else if (this instanceof DGNode) {
			if (touchable) {
				
			}
		}

	}

	public void render() {
		if (!isVisible()) {
			return;
		}

		/*
		 * render children if a parent
		 */
		if (this instanceof DGParent) {

			for (DGNode child : ((DGParent) this).getChildren()) {
				child.render();
			}
		}
		/*
		 * else, paint if you are a shape
		 */
		else if (this instanceof DGAbstractShape) {
			((DGAbstractShape) this).paint(p.g);
		}

	}

	public void update() {

	}

	public void setTouchable(boolean t) {
		touchable = t;
	}

	public boolean isTouchable() {
		return touchable;
	}

	public Bounds2D getBounds2D() {
		return bounds;
	}

	public boolean contains(float mx, float my) {
		if (this instanceof DGParent) {
			for (DGNode child : ((DGParent) this).getChildren()) {
				if (child.contains(mx, my)) {
					Log.d("DGNode instanceof DGParent", " " + child);
					return true;
				}
			}
		} else if (bounds.contains(mx, my)) {
			Log.d("DGNode instanceof DGNode", " " + this);
			return true;
		}
		return false;
	}

}
