package com.droidgraph.scene;

import com.droidgraph.transformation.Bounds;

public abstract class DGLeaf extends DGNode {

	public abstract void paint();

	private Bounds subregionBounds;

//	final static boolean DO_PAINT;

	/*
	 * Dirty state/region management below...
	 */

	/**
	 * This method must be called whenever a change is made to a node that
	 * affects its visual state. If {@code boundsChanged} is true, it indicates
	 * that there has also been a change in the overall bounds of the node, and
	 * therefore both the former and current bounds of the node need to be
	 * repainted.
	 * <p>
	 * Usage example (change in visual/bounds state):
	 * 
	 * <pre>
	 * public void setThickness(float thickness) {
	 * 	this.thickness = thickness;
	 * 	repaint(true);
	 * }
	 * </pre>
	 * <p>
	 * Usage example (change in visual state only):
	 * 
	 * <pre>
	 * public void setColor(Color color) {
	 * 	this.color = color;
	 * 	repaint(false);
	 * }
	 * </pre>
	 * 
	 * @param boundsChanged
	 *            if true, a change in the overall node bounds has been made; if
	 *            false, only a change in the node's visual state has been made
	 */
	protected final void repaint(boolean boundsChanged) {
		markDirty(boundsChanged);
	}

	/**
	 * This method must be called whenever a change is made to a node that
	 * affects only a subregion of its overall visual state. Calling this method
	 * is similar to calling {@code repaint(false)}, but potentially more
	 * efficient in cases where only a small portion of this node is changing at
	 * any given time.
	 * <p>
	 * Usage example:
	 * 
	 * <pre>
	 * public void setIndicatorEnabled(boolean b) {
	 * 	this.indicatorEnabled = indicatorEnabled;
	 * 	repaint(indicatorBounds);
	 * }
	 * </pre>
	 * 
	 * @param subregionBounds
	 *            a rectangle representing the subregion (in the untransformed
	 *            coordinate space of this leaf node) that needs to be repainted
	 * @throws IllegalArgumentException
	 *             if {@code subregionBounds} is null
	 */
	protected final void repaint(Bounds subregionBounds) {
		if (subregionBounds == null) {
			throw new IllegalArgumentException(
					"subregion bounds must be non-null");
		}
		Bounds oldBounds = this.subregionBounds;
		Bounds newBounds = accumulate(oldBounds, subregionBounds, false);
		if (oldBounds == null && newBounds != null) {
			markSubregionDirty();
		}
		this.subregionBounds = newBounds;
	}

	final Bounds getSubregionBounds() {
		return subregionBounds;
	}

	@Override
	void clearDirty() {
		super.clearDirty();
		this.subregionBounds = null;
	}
}
