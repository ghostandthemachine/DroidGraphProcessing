package com.droidgraph.scene;

import java.util.List;

import processing.core.PGraphics;

import com.droidgraph.renderer.PickBuffer;
import com.droidgraph.util.Shared;

public abstract class DGParent extends DGNode {

	public abstract List<DGNode> getChildren();

	public abstract void remove(DGNode node);

	@Override
	public void render() {
		for (DGNode child : ((DGParent) this).getChildren()) {
			child.render();
		}
	}

	@Override
	public void renderToPickBuffer(PGraphics p) {
		for (DGNode child : ((DGParent) this).getChildren()) {
			((PickBuffer) Shared.offscreenBuffer).setCurrentIDIndex(child.sceneID);
			child.renderToPickBuffer(p);
		}
	}

}
