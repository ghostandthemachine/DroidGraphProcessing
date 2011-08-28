package com.droidgraph.scene;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import processing.core.PGraphics;

import com.droidgraph.affine.DGAffineTransform;
import com.droidgraph.event.DGMotionEvent;
import com.droidgraph.fx.DGFXNode;
import com.droidgraph.motionlistener.MotionListener;
import com.droidgraph.renderer.PickBuffer;
import com.droidgraph.transformation.Bounds;
import com.droidgraph.transformation.Vec3f;
import com.droidgraph.util.Shared;

public abstract class DGNode {
	
	private String TAG = "DGNode - ";

	private boolean DEBUG = false;
	private boolean DEBUG_TRANSFORM = false;
	private boolean DEBUG_PICK = false;

	public boolean visible = true;

	private boolean isEventBlocker = false;

	private DGParent parent;

	protected Bounds bounds = new Bounds(0, 0, 0, 0);

	protected int sceneID;

	private ArrayList<MotionListener> motionListeners = new ArrayList<MotionListener>();

	private boolean alive = true;

	private Bounds cachedAccumBounds;
	private Vec3f cachedAccumXform;
	
    /*
     * Dirty state/region management below...
     */

    /**
     * This node is completely clean, and so are all of its descendents.
     */
    static final int DIRTY_NONE            = (0 << 0);
    /**
     * This node has changed its overall visual state.
     */
    static final int DIRTY_VISUAL          = (1 << 0);
    /**
     * This node has changed only a subregion of its overall visual state.
     * (Only applicable to SGLLeaf nodes.)
     */
    static final int DIRTY_SUBREGION       = (1 << 1);
    /**
     * This node has changed its bounds, so it is important to account for
     * both the former bounds and its new, updated bounds.
     */
    static final int DIRTY_BOUNDS          = (1 << 2);
    /**
     * One or more of this node's descendents has changed its visual state.
     * (Only applicable to SGLGroup and SGLFilter nodes.)
     */
    static final int DIRTY_CHILDREN_VISUAL = (1 << 3);
    /**
     * One or more of this node's descendents has had a change in bounds,
     * which means that the overall bounds of this group will need recalculation.
     * (Only applicable to SGLGroup and SGLFilter nodes.)
     */
    static final int DIRTY_CHILDREN_BOUNDS = (1 << 4);
    
    /**
     * The dirty state of this node.  This is initialized to DIRTY_VISUAL
     * so that this node is painted for the very first paint cycle.
     */
    private int dirtyState = DIRTY_VISUAL;
    
    private Bounds lastPaintedBounds;

	public DGNode() {
		// Register this node in the scene map
		// DGAndroidScene scene = ((DGAndroidScene) Shared.scene);
		DGScene scene = ((DGScene) Shared.scene);

		sceneID = scene.registerNode(this);
	}

	   
    void markDirty(int state) {
        // only mark us if we haven't been marked with this particular
        // dirty state before...
        // and only propagate if we are visible
        if (isVisible() && (dirtyState & state) == 0) {
            // mark this node dirty
            dirtyState |= state;

            // walk up the tree and mark the entire branch dirty
            if (parent instanceof DGNode) {
                if (state == DIRTY_VISUAL || state == DIRTY_SUBREGION) {
                    // tell our ancestors that at least one descendent has
                    // changed its visual state
                    state = DIRTY_CHILDREN_VISUAL;
                } else if (state == DIRTY_BOUNDS) {
                    // tell our ancestors that at least one descendent has
                    // changed its bounds
                    state = DIRTY_CHILDREN_BOUNDS;
                }
                ((DGNode)parent).markDirty(state);
            }
        }
    }
    
    protected final void markDirty(boolean boundsChanged) {
        if (boundsChanged) {
            markDirty(DIRTY_BOUNDS);
            // we have no choice but to always walk up the entire tree
            // and invalidate all cached local/accum bounds
            invalidateLocalBounds();
        } else {
            markDirty(DIRTY_VISUAL);
        }
    }
    
    final void markSubregionDirty() {
        markDirty(DIRTY_SUBREGION);
    }
    
    void clearDirty() {
        dirtyState = DIRTY_NONE;
    }
    
    void invalidateAccumBounds() {
        // this change affects this node and any/all descendents
        cachedAccumXform = null;
        cachedAccumBounds = null;
    }
    
    void invalidateLocalBounds() {
        // this change affects this node and any/all ancestors
        // (either this group's overall bounds have changed due to
        // a transform change, or there's been a change in the
        // bounds of one or more descendents; either way, we need
        // to invalidate the current cached bounds)
        cachedAccumBounds = null;
        
        
        // walk up the tree and mark the invalidate the cached bounds
        // of every node in this branch
        // TODO: is there some way to minimize the amount of work done here?
        DGNode parent = getParent();
        if (parent != null) {
            parent.invalidateLocalBounds();
        }
    }
    
    final void setLastPaintedBounds(Bounds bounds) {
        // no clone necessary since lastPaintedBounds will not be mutated nor
        // passed outside this object
        this.lastPaintedBounds = bounds;
    }
    
    final boolean isDirty() {
        return (dirtyState != DIRTY_NONE);
    }
    
    final int getDirtyState() {
        return dirtyState;
    }

    /**
     * Safely accumulates the {@code newrect} rectangle into an existing
     * {@code accumulator} rectangle and returns the accumulated result.
     * The result may be {@code null} if the existing {@code accumulator}
     * was {@code null} and the {@code newrect} is either null or empty.
     * If the existing {@code accumulator} was not {@code null} then it
     * is returned, possibly augmented with the union of the bounds of the
     * two rectangles.
     * If a non-{@code null} result is returned then it is guaranteed to
     * be non-empty.
     * The result is never the same object as {@code newrect}.
     * <p>
     * This method provides a convenient mechanism to perform the task
     * of accumulating rectangles used throughout various parts of
     * scene graph management while providing workarounds for unexpected
     * behaviors in the {@link Rectangle2D#add} method which sometimes
     * produces a non-empty result from combining two empty rectangles.
     * 
     * @param accumulator the existing accumulation of rectangle bounds
     * @param newrect a new rectangle to accumulate
     * @return the non-empty result of accumulation, or null if the
     *         accumulation is still empty
     */
    static Bounds accumulate(Bounds accumulator,
                                  Bounds newrect)
    {
        return accumulate(accumulator, newrect, false);
    }

    /*
     * TODO: We may want to consider maintaining an Area/Region object
     * instead to preserve non-contiguous dirty regions; using Rectangle
     * means we're forced to use union(), which will create larger areas
     * than neccessary in many cases; the downside of Area/Region is that
     * we may force Java 2D into complex clipping situations, which may
     * sometimes be slower than the rectangular fast path.
     */
    final Bounds accumulateDirty(Bounds r) {
        if (((dirtyState & DIRTY_BOUNDS) != 0) ||
            ((dirtyState & DIRTY_CHILDREN_BOUNDS) != 0))
        {
            // add in the node's original bounds
            if (lastPaintedBounds != null) {
                r = accumulate(r, lastPaintedBounds, false);
            }
        }
        if (!isVisible()) {
            return r;
        }
        
        if ((dirtyState & DIRTY_SUBREGION) == 0) {
            // add in the node's latest bounds
            r = accumulate(r, getTransformedBoundsRelativeToRoot(), false);
        } else {
            // add in only the affected subregion, transformed
            // relative to the root and intersected with the overall
            // transformed bounds of this node
        	Bounds subregionBounds = ((DGLeaf)this).getSubregionBounds();
            Bounds fullBounds = getTransformedBoundsRelativeToRoot();
            if (subregionBounds != null) {
                Vec3f accumXform = getCumulativeTransform();
                subregionBounds =
                    accumXform.createTransformedShape(subregionBounds).getBounds();
                subregionBounds = subregionBounds.createIntersection(fullBounds);
            } else {
                subregionBounds = fullBounds;
            }
            if (!subregionBounds.isEmpty()) {
                boolean srBoundsShareable = (subregionBounds != fullBounds);
                r = accumulate(r, subregionBounds, srBoundsShareable);
            }
        }

        return r;
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
	public final Vec3f getCumulativeTransform() {
		if (cachedAccumXform == null) {
			if (DEBUG_TRANSFORM) {
				Shared.p("DGNode getCumulativeTransform, xAccum == null");
			}
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
	protected Vec3f calculateCumulativeTransform() {
	       DGNode parent = getParent();
	        if (parent == null) {
	            return new Vec3f();
	        } else {
	            return parent.getCumulativeTransform();
	        }
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
		if (local == null) {
			local = new Vec3f();
		}
		local.x = global.x - ct.x;
		local.y = global.y - ct.y;
		return local;
	}
	
	public Vec3f globalToLocal(float gx, float gy) {
		return globalToLocal(new Vec3f(gx, gy), new Vec3f());
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

	static Bounds accumulate(Bounds accumulator, Bounds newrect,
			boolean newrectshareable) {
		if (newrect == null || newrect.isEmpty()) {
			return accumulator;
		}
		if (accumulator == null) {
			// TODO: We really shouldn't be so trusting of the incoming
			// Rectangle type - we should instantiate a (platform sensitive)
			// specific type like R2D.Double (desktop) or R2D.Float (phone)
			return (newrectshareable ? newrect : (Bounds) newrect.clone());
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

	public void render(PGraphics g) {
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
		if(DEBUG_PICK) {
			Shared.p(TAG, "handleMotionEvent(), DGMotionEvent in:", me);
		}
		for (MotionListener listener : motionListeners) {
			listener.handleMotionEvent(me);
		}
	}

	public void setPointerShift(int id) {
		for (MotionListener listener : motionListeners) {
			listener.setPointerShift(id);
		}
	}

	public Bounds getBounds2D() {
		return bounds;
	}

	public void setBounds2D(float x, float y, float x2, float y2) {
		bounds.setX(x);
		bounds.setY(y);
		bounds.setWidth(x2 - x);
		bounds.setHeight(y2 - y);
	}

	public boolean contains(Vec3f vec) {
		if(vec == null) {
			Shared.p("ERROR, null point in DGNode - contains(Vec3f vec)");
		}
		Bounds bounds = getBounds(null);
		return bounds.contains(vec);
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
	
    boolean hasOverlappingContents() {
        return true;
    }

}
