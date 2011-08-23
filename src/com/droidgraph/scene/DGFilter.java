package com.droidgraph.scene;

import java.util.Collections;
import java.util.List;

import com.droidgraph.transformation.Bounds;
import com.droidgraph.transformation.Vec3f;

public class DGFilter extends DGParent {

	/**
	 * Flag indicating that the filter implementation does not need access to
	 * the source as a raster image.
	 */
	public static final int NONE = (0 << 0);
	/**
	 * Flag indicating that the filter implementation needs access to the source
	 * as a raster image (in the original, local coordinate space of the child
	 * node).
	 */
	public static final int UNTRANSFORMED = (1 << 0);
	/**
	 * Flag indicating that the filter implementation needs access to the source
	 * as a raster image (in the transformed coordinate space of the child
	 * node).
	 */
	public static final int TRANSFORMED = (1 << 1);
	/**
	 * Flag indicating that the filter implementation needs access to the source
	 * as a raster image in both untransformed and transformed formats. This is
	 * equivalent to {@code (UNTRANSFORMED | TRANSFORMED)}.
	 */
	public static final int BOTH = UNTRANSFORMED | TRANSFORMED;
	/**
	 * Flag indicating that the filter implementation has already cached the
	 * rendering of the source and can render it via renderFromCache().
	 */
	public static final int CACHED = (1 << 2);

	private DGNode child;
	private List<DGNode> singletonList;

	/** Creates a new instance of SGFilter */
	public DGFilter() {
	}

	@Override
	public final List<DGNode> getChildren() {
		if (child == null) {
			return Collections.emptyList();
		} else {
			if (singletonList == null) {
				singletonList = Collections.singletonList(child);
			}
			return singletonList;
		}
	}

	public final DGNode getChild() {
		return child;
	}

	public void setChild(DGNode child) {
		if (child == null) {
			throw new IllegalArgumentException("null child");
		}
		if (child == this.child) {
			return;
		}
		DGParent oldParent = child.getParent();
		if (oldParent != null) {
			oldParent.remove(child);
		}
		this.singletonList = null;
		this.child = child;
		child.setParent(this);
		bounds.accumulateChild(child.getBounds());
	}

	@Override
	public void remove(DGNode node) {
		if (node == child) {
			remove();
		}
	}

	public void remove() {
		this.child.setParent(null);
		this.child = null;
		this.singletonList = null;
	}

//    public final Bounds getBounds(Vec3f transform) {
//        Bounds2D bounds = null;
//        if (isVisible() && getChildren() != null && !getChildren().isEmpty()) {
//            // for now, just create the union of all the bounding boxes
//            // of all the children; later, we may want to create something
//            // more minimal, such as the overall convex hull, or a
//            // Region/Area object containing only the actual child bounds
//            for (int i = 0; i < getChildren().size(); i++) {
//                DGNode child = getChildren().get(i);
//                if (child.isVisible()) {
//                    Bounds2D rc = (Bounds2D) child.getBounds(transform);
//                    bounds = accumulate(bounds, rc, true);
//                }
//            }
//        }
//        if (bounds == null) {
//            // just an empty rectangle
//            bounds = new Bounds2D();
//        }
//        Shared.p("DGFIlter - getBounds():", bounds);
//        return bounds;
//    }
	
	public final Bounds getBounds(Vec3f transform) {
		Bounds bounds = new Bounds();
		
		if(isVisible() && getChildren() != null && !getChildren().isEmpty()) {
		      for (int i = 0; i < getChildren().size(); i++) {
                DGNode child = getChildren().get(i);
                if (child.isVisible()) {
                    Bounds rc = (Bounds) child.getBounds(transform);
                    bounds = accumulate(bounds, rc, true);
                }
            }
		}
		
		return bounds;
	}
}
