package com.droidgraph.scene;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.droidgraph.transformation.Bounds2D;


/**
 * Defines a transformed coordinate system for a list of SGNodes.
 * 
 */
public class DGGroup extends DGParent {
	
	protected Bounds2D bounds = new Bounds2D(0,0,0,0);
	
	protected List<DGNode> children;
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
	}

	public final void add(DGNode child) {
		add(-1, child);
		bounds.accumulate(child.getBounds2D());
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
		children.remove(node);
		children.add(node);
	}



}
