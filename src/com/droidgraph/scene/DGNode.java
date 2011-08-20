package com.droidgraph.scene;

import java.awt.geom.Point2D;
import java.util.ArrayList;

import processing.core.PGraphics;
import android.util.Log;

import com.droidgraph.affine.DGAffineTransform;
import com.droidgraph.event.DGMotionEvent;
import com.droidgraph.fx.DGFXNode;
import com.droidgraph.motionlistener.MotionListener;
import com.droidgraph.renderer.PickBuffer;
import com.droidgraph.transformation.Bounds2D;
import com.droidgraph.transformation.Vec3f;
import com.droidgraph.translation.Bounds;
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

	private Bounds cachedAccumBounds;
	private Vec3f cachedAccumXform;

	private boolean DEBUG_TRANSFORM = false;

	public DGNode() {
		// Register this node in the scene map
		// DGAndroidScene scene = ((DGAndroidScene) Shared.scene);
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
	 * Returns the bounding box of this node in the coordinate space inherited
	 * from the parent. This is a convenience method, equivalent to calling
	 * {@code getBounds(null)}.
	 */
	public final Bounds getBounds() {
		return getBounds(null);
	}

	/**
	 * Returns the bounding box of this node relative to the specified
	 * coordinate space.
	 * 
	 * @param transform
	 *            the transform applied to the geometry
	 */
	public abstract Bounds getBounds(Vec3f transform);

	/**
	 * Transforms the bounds of this node by the "cumulative transform", and
	 * then returns the bounding box of that transformed shape.
	 */
	final Bounds getTransformedBoundsRelativeToRoot() {
		if (cachedAccumBounds == null) {
			cachedAccumBounds = calculateAccumBounds();
		}
		return cachedAccumBounds;
	}

	/**
	 * Calculate the accumulated bounds object representing the global bounds
	 * relative to the root of the tree. The default implementation calculates
	 * new bounds based on the accumulated transform, but SGFilter nodes
	 * override this method to return a shared accumulated bounds object from
	 * their child.
	 */
	Bounds calculateAccumBounds() {
		return getBounds(getCumulativeTransform());
	}

	/**
	 * Returns the "cumulative transform", which is the concatenation of all
	 * ancestor transforms plus the transform of this node (if present).
	 */
	final Vec3f getCumulativeTransform() {
		if (cachedAccumXform == null) {
			Shared.p("DGNode getCumulativeTransform, xAccum == null");
			cachedAccumXform = calculateCumulativeTransform();
		}
		return calculateCumulativeTransform();
	}

	/**
	 * Calculates the accumulated product of all transforms back to the root of
	 * the tree. The default implementation simply returns a shared value from
	 * the parent, but SGTransform nodes will override this method to return a
	 * new modified transform.
	 */
	public Vec3f calculateCumulativeTransform() {
		return accumTransform(new Vec3f());
	}

	public Vec3f accumTransform(Vec3f v) {
		Vec3f out = new Vec3f();
		DGNode parent = getParent();
		if (DEBUG_TRANSFORM) {
			Shared.p("DGNode accumTransform start", this, this.getClass()
					.getGenericSuperclass(), this.getClass().getSuperclass());
		}
		if (parent != null) {
			if (this instanceof com.droidgraph.fx.DGFXNode
					|| this.getClass().getGenericSuperclass() instanceof com.droidgraph.fx.DGFXNode
					|| this instanceof com.droidgraph.fx.DGFXShape
					|| this.getClass().getGenericSuperclass() instanceof com.droidgraph.fx.DGFXShape
					|| this instanceof com.droidgraph.fx.DGFXGroup
					|| this.getClass().getGenericSuperclass() instanceof com.droidgraph.fx.DGFXGroup) {
				if (DEBUG_TRANSFORM) {
					Shared.p("instanceof fxnode", v,
							((DGFXNode) this).getTranslation(), this);
				}
				v.add(((DGFXNode) this).getTranslation());
				if (DEBUG_TRANSFORM) {
					Shared.p("instanceof DGFXNode", v);
				}
			} else if (this instanceof DGAffineTransform
					|| this.getClass().getGenericSuperclass() instanceof DGAffineTransform) {
				return parent.accumTransform(v);
			}
			if (DEBUG_TRANSFORM) {
				Shared.p("call on parent", this, parent);
			}
			return parent.accumTransform(v);
		}
		out = v;
		if (DEBUG_TRANSFORM) {
			Shared.p("AccumtTransform end", out, this);
		}
		return out;
	}

	/**
	 * Transforms a point from the global coordinate system of the root node
	 * (typically a {@link JSGPanel}) into the local coordinate space of this
	 * SGNode. The {@code global} parameter must not be null. If the
	 * {@code local} parameter is null then a new {@link Point2D} object will be
	 * created and returned after transforming the point. The {@code global} and
	 * {@code local} parameters may be the same object and the coordinates will
	 * be correctly updated with the transformed coordinates.
	 * 
	 * @param global
	 *            the coordinates in the global coordinate system to be
	 *            transformed
	 * @param local
	 *            a {@code Point2D} object to store the results in
	 * @return a {@code Point2D} object containig the transformed coordinates
	 */
	public Vec3f globalToLocal(Vec3f global, Vec3f local) {
		Vec3f ct = getCumulativeTransform();
		if(local == null) {
			local = new Vec3f();
		}
		local.x = global.x - ct.x;
		local.y = global.y - ct.y;
		return local;
	}

	/**
	 * Transforms a point from the local coordinate space of this SGNode into
	 * the global coordinate system of the root node (typically a
	 * {@link JSGPanel}). The {@code local} parameter must not be null. If the
	 * {@code global} parameter is null then a new {@link Point2D} object will
	 * be created and returned after transforming the point. The {@code local}
	 * and {@code global} parameters may be the same object and the coordinates
	 * will be correctly updated with the transformed coordinates.
	 * 
	 * @param local
	 *            the coordinates in the local coordinate system to be
	 *            transformed
	 * @param global
	 *            a {@code Point2D} object to store the results in
	 * @return a {@code Point2D} object containig the transformed coordinates
	 */
	public Vec3f localToGlobal(Vec3f local, Vec3f global) {
		return getCumulativeTransform().transform(global, local);
	}

	static Bounds2D accumulate(Bounds2D accumulator, Bounds2D newrect,
			boolean newrectshareable) {
		if (newrect == null || newrect.isEmpty()) {
			return accumulator;
		}
		if (accumulator == null) {
			// TODO: We really shouldn't be so trusting of the incoming
			// Rectangle type - we should instantiate a (platform sensitive)
			// specific type like R2D.Double (desktop) or R2D.Float (phone)
			return (newrectshareable ? newrect : (Bounds2D) newrect.clone());
		}
		accumulator.add(newrect);
		return accumulator;
	}

	/**
	 * @return {@code true} if motion event should not be dispatched to the
	 *         nodes underneath this one.
	 */
	public final boolean isEventBlocker() {
		if (DEBUG) {
			Shared.p("DGNode, isEventBlocker()", this, "motion listener size:",
					motionListeners.size());
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
		Shared.p("DGNode, renderToPickBuffer()", this);
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
		if (DEBUG) {
			Shared.p("DGNode - addMotionListener(", motionListener, ")", this,
					"num listeners:", motionListeners.size());
		}
		motionListeners.add(motionListener);
		if (DEBUG) {
			Shared.p("DGNode - addMotionListener(", motionListener, ")", this,
					"num listeners after add:", motionListeners.size());
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
		bounds.setX(x);
		bounds.setY(y);
		bounds.setWidth(x2 - x);
		bounds.setHeight(y2 - y);
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
			output[0] = input[0] - bounds.getX();
			output[1] = input[1] - bounds.getY();
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
		return bounds.getWidth();
	}

	public float getHeight() {
		return bounds.getHeight();
	}

}
