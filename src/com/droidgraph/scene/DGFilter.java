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
		
		markDirty(true);
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
	
    public boolean canSkipRendering() {
        return false;
    }

    public boolean canSkipChildren() {
        return false;
    }
	
    @Override
    public Bounds getBounds(Vec3f transform) {
        if (child == null) {
            // just an empty rectangle
            return new Bounds();
        } else {
            return child.getBounds(transform);
        }
    }
    
    /**
     * Calculates the accumulated bounds object representing the
     * global bounds relative to the root of the tree.
     * Since most filter nodes have the same accumulated bounds as
     * their child, this implementation will simply report the
     * accumulated bounds of its child.
     * Subclasses may override this behavior if they do not conform
     * to the above assumption.
     */
    @Override
    Bounds calculateAccumBounds() {
        if (child == null) {
            return new Bounds();
        } else {
            return child.getTransformedBoundsRelativeToRoot();
        }
    }

    @Override
    boolean hasOverlappingContents() {
        return child.hasOverlappingContents();
    }
}
