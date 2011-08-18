package com.droidgraph.scene;

import java.util.ArrayList;

import processing.core.PGraphics;
import android.util.Log;

import com.droidgraph.event.DGMotionEvent;
import com.droidgraph.motionlistener.MotionListener;
import com.droidgraph.renderer.PickBuffer;
import com.droidgraph.transformation.Bounds2D;
import com.droidgraph.util.Shared;

public abstract class DGNode {
	
	private boolean DEBUG = false;

	public boolean visible = true;

	private boolean isEventBlocker = false;

	private DGParent parent;

	protected Bounds2D bounds = new Bounds2D(this, 0, 0, 0, 0);

	protected int sceneID;

	private ArrayList<MotionListener> motionListeners = new ArrayList<MotionListener>();

	private boolean alive = true;

	public DGNode() {
		// Register this node in the scene map
//		DGAndroidScene scene = ((DGAndroidScene) Shared.scene);
		DGScene scene = ((DGScene) Shared.scene);
		
		sceneID = scene.registerNode(this);
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
	public final boolean isEventBlocker() {
		if(DEBUG) {
			Shared.p("DGNode, isEventBlocker()", this, "motion listener size:", motionListeners.size());
		}
		return !motionListeners.isEmpty() || isEventBlocker;
	}

	public final void setEventBlocker(boolean value) {
		if (value != isEventBlocker) {
			isEventBlocker = value;
		}
	}

	public DGParent getParent() {
		return parent;
	}

	public final void setParent(Object parent) {
		assert (parent == null || parent instanceof DGParent || parent instanceof DGScene);
		this.parent = (DGParent) parent;
	}

	public void render() {
	}

	public void renderToPickBuffer(PGraphics p) {
		if (Shared.offscreenBuffer instanceof PickBuffer) {
			((PickBuffer) Shared.offscreenBuffer).setCurrentIDIndex(sceneID);
		} else {
			// Shared.p("Can not render to a PickBuffer context. Picking will not work now");
		}
	}

	protected int[] idToRGBA() {
		int r = sceneID & 0xFF;
		int g = (sceneID >> 8) & 0xFF;
		int b = (sceneID >> 16) & 0xFF;

		int[] returnColor = { r, g, b, 255 };
		return returnColor;
	}

	public void addMotionListener(MotionListener motionListener) {
		if(DEBUG) {
			Shared.p("DGNode - addMotionListener(", motionListener,")", this, "num listeners:", motionListeners.size());
		}
		motionListeners.add(motionListener);
		if(DEBUG) {
			Shared.p("DGNode - addMotionListener(", motionListener,")", this, "num listeners after add:", motionListeners.size());
		}
	}

	public void removeMotionListener(MotionListener ml) {
		motionListeners.remove(ml);
	}

	public void handleMotionEvent(DGMotionEvent me) {
		for (MotionListener listener : motionListeners) {
			listener.handleMotionEvent(me);
		}
	}
	
	public void setPointerShift(int id) {
		for (MotionListener listener : motionListeners) {
			listener.setPointerShift(id);
		}
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

	public void update() {

	}

	public boolean isAlive() {
		return alive;
	}

	public void setAlive(boolean alive) {
		this.alive = alive;
	}

	public int getSceneID() {
		return sceneID;
	}
	
	public float getWidth() {
		return bounds.width;
	}
	
	public float getHeight() {
		return bounds.height;
	}


}
