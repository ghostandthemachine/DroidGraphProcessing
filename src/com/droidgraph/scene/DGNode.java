package com.droidgraph.scene;

import java.util.ArrayList;

import processing.core.PApplet;
import android.util.Log;
import android.view.MotionEvent;

import com.droidgraph.event.DGMotionEvent;
import com.droidgraph.input.DGMotionPointer;
import com.droidgraph.motionlistener.MotionListener;
import com.droidgraph.transformation.Bounds2D;
import com.droidgraph.util.Shared;

public abstract class DGNode {

	public boolean visible = true;

	private boolean isTouchBlocker = false;

	private Object parent;
	private PApplet p;

	protected Bounds2D bounds = new Bounds2D(0, 0, 0, 0);

	private boolean touchable = false;

	private int pointerCount = 0;
	private int[] pointerCounts = { -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 };

	private ArrayList<MotionListener> motionListeners = new ArrayList<MotionListener>();

	private boolean alive = true;

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

	public void addMotionListener(MotionListener ml) {
		motionListeners.add(ml);
	}

	public void removeMotionListener(MotionListener ml) {
		motionListeners.remove(ml);
	}

	public void processMotionEvent(DGMotionPointer mp, int action) {
		DGMotionEvent me = mp.event;
//		Shared.p("DGNode, processMotionEvent", this, mp.getID(), action);
		for (MotionListener listener : motionListeners) {
			switch (action) {
			case MotionEvent.ACTION_DOWN:
//				Shared.p(this, "ACtionDOWN");
				listener.actionDown(me, me.targetID);
				break;
			case MotionEvent.ACTION_POINTER_DOWN:
				listener.actionPointerDown(me, me.targetID);
				break;
			case MotionEvent.ACTION_MOVE:
				listener.actionMove(me, me.targetID);
				break;
			case MotionEvent.ACTION_UP:
				listener.actionUp(me, me.targetID);
				break;
			case MotionEvent.ACTION_POINTER_UP:
				if (me.targetID == 0) {
					listener.actionPointerUp(me, me.targetID);
				} else {
					listener.actionPointerUp(me, me.targetID);
				}
				break;
			}
		}
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
	
	public void setBounds2D(float x, float y, float x2, float y2) {
		bounds.x = x;
		bounds.y = y;
		bounds.width = x2 - x;
		bounds.height = y2 - y;
		
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

	public float[] globalToLocal(float[] input, float[] output) {
		if (input.length == 2) {
			output[0] = input[0] - bounds.x;
			output[1] = input[1] - bounds.y;
			return output;
		}
		return null;
	}

	public int addPointer(int globalPointerID) {
		pointerCount++;
		pointerCounts[pointerCount - 1] = globalPointerID;
//		Shared.p("DGNode", this, "addPointer", "pointerCount =",getPointerCount());
		return pointerCount;
	}

	public int getPointerCount() {
		return pointerCount;
	}

	public void removePointer(int targetID) {
//		Shared.p("DGNode, removePointer", targetID);
		pointerCounts[targetID] = -1;
		pointerCount--;
	}

	public void clearPointers() {
		pointerCount = 0;
		pointerCounts = new int[] { -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 };
	}
	
	public void updateNode() {
		if (!isAlive()) {
			return;
		}

		/*
		 * render children if a parent
		 */
		if (this instanceof DGParent) {

			for (DGNode child : ((DGParent) this).getChildren()) {
				child.update();
			}
		}
		/*
		 * else, paint if you are a shape
		 */
		else {
			this.update();
		}
	}
	
	public void update(){
		
	}

	public boolean isAlive() {
		return alive ;
	}
	
	public void setAlive(boolean alive) {
		this.alive = alive;
	}

}
