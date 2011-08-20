package com.droidgraph.scene;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import processing.core.PGraphics;

import com.droidgraph.renderer.PickBuffer;
import com.droidgraph.transformation.Bounds2D;
import com.droidgraph.transformation.Vec3f;
import com.droidgraph.translation.Bounds;
import com.droidgraph.util.Shared;

/**
 * Defines a transformed coordinate system for a list of SGNodes.
 * 
 */
public class DGGroup extends DGParent {
	
	private boolean DEBUG = false;
	
	
	public DGGroup() {
		super();
	}

	protected ArrayList<DGNode> children;
	private List<DGNode> childrenUnmodifiable;

	protected long lifetime = 0;

	public final List<DGNode> getChildren() {
		if (children == null) {
			return Collections.emptyList();
		} else {
			if (childrenUnmodifiable == null) {
				childrenUnmodifiable = Collections.unmodifiableList(children);
			}
			return childrenUnmodifiable;
		}
	}

	public void add(int index, DGNode child) {
		if(DEBUG) {
			Shared.p("add(",index,", ", child, ")");
			Shared.p("before add", this, bounds, child, child.getBounds2D());
		}
		if (child == null) {
			throw new IllegalArgumentException("null child");
		}
		if (children == null) {
			children = new ArrayList<DGNode>(1); // common case: one child
		}
		if ((index < -1) || (index > children.size())) {
			throw new IndexOutOfBoundsException("invalid index");
		}

		DGParent oldParent = (DGParent) child.getParent();
		if (oldParent == this) {
			children.remove(child);
		} else if (oldParent != null) {
			oldParent.remove(child);
		}
		if (index == -1) {
			children.add(child);
		} else {
			children.add(index, child);
		}
		child.setParent(this);
		accumulateBounds(child);
		if(DEBUG) {
			Shared.p("after add", this, bounds, child, child.getBounds2D());
		}
	}

	public final void add(DGNode child) {
		add(-1, child);
		accumulateBounds(child);
		Shared.p(bounds);
	}
	
	private void accumulateBounds(DGNode node) {
		bounds.accumulate(node.getBounds2D());
	}
	
	@Override
	public void remove(DGNode child) {
		if (child == null) {
			throw new IllegalArgumentException("null child");
		}
		if (children != null) {
			children.remove(child);
			child.setParent(null);
		}
		Shared.scene.unregisterNode(child);
	}
    
    public final void remove(int index) {
        if (children != null) {
            DGNode child = children.get(index);
            if (child != null) {
                remove(child);
            }
        }
    }

	public long getLifeTime() {
		return lifetime;
	}

	public void bringToFront(DGNode node) {
		children.remove(node); // update the local node depth in the list
		children.add(node);
	}

	public void setNodeDepth(DGNode node, int depth) {
		if (children.contains(node)) {
			children.remove(node); // update the local node depth in the list
			children.add(0, node);
		}
	}
	
	@Override
	public void renderToPickBuffer(PGraphics p) {
		((PickBuffer) Shared.offscreenBuffer).setCurrentIDIndex(sceneID);
		p.rect(bounds.getX(), bounds.getY(), bounds.getWidth(), bounds.getHeight());
		super.renderToPickBuffer(p);
	}
	
    @Override
    public final Bounds getBounds(Vec3f transform) {
        Bounds2D bounds = null;
        if (isVisible() && children != null && !children.isEmpty()) {
            // for now, just create the union of all the bounding boxes
            // of all the children; later, we may want to create something
            // more minimal, such as the overall convex hull, or a
            // Region/Area object containing only the actual child bounds
            for (int i = 0; i < children.size(); i++) {
                DGNode child = children.get(i);
                if (child.isVisible()) {
                    Bounds2D rc = (Bounds2D) child.getBounds(transform);
                    bounds = accumulate(bounds, rc, true);
                }
            }
        }
        if (bounds == null) {
            // just an empty rectangle
            bounds = new Bounds2D();
        }
        return bounds;
    }
}
