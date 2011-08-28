package com.droidgraph.scene;

import java.util.List;

import processing.core.PGraphics;

import com.droidgraph.renderer.PickBuffer;
import com.droidgraph.util.Shared;

public abstract class DGParent extends DGNode {

	public abstract List<DGNode> getChildren();

	public abstract void remove(DGNode node);

	@Override
	public void render(PGraphics p) {
		for (DGNode child : ((DGParent) this).getChildren()) {
			child.render(p);
		}
	}

	@Override
	public void renderToPickBuffer(PGraphics p) {
		for (DGNode child : ((DGParent) this).getChildren()) {
			((PickBuffer) Shared.offscreenBuffer)
					.setCurrentIDIndex(child.sceneID);
			child.renderToPickBuffer(p);
		}
	}

	/*
	 * Dirty state/region management below...
	 */

	@Override
	void clearDirty() {
		if (!isDirty()) {
			// no need to visit descendents
			return;
		}

		// clear this node's dirty state
		super.clearDirty();

		// then do the same for all its children (and so on, down the tree)
		for (DGNode child : getChildren()) {
			child.clearDirty();
		}
	}

	// TODO: figure out a way to do this more lazily
	@Override
	protected void invalidateAccumBounds() {
		// invalidate this node's cached accumulated transform/bounds
		super.invalidateAccumBounds();

		// then do the same for all its children (and so on, down the tree)
		for (DGNode child : getChildren()) {
			child.invalidateAccumBounds();
		}
	}
}
