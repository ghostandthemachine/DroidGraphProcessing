package com.droidgraph.fx;

import java.util.List;

import com.droidgraph.scene.DGGroup;
import com.droidgraph.scene.DGNode;

public class DGFXGroup extends DGFXNode {

	private DGGroup groupNode;

	public DGFXGroup() {
		super(new DGGroup());
		this.groupNode = (DGGroup) getLeaf();
	}

	public final List<DGNode> getFXChildren() {
		return groupNode.getChildren();
	}

	public final void add(int index, DGNode child) {
		groupNode.add(index, child);
	}

	public final void add(DGNode child) {
		groupNode.add(-1, child);
	}

	@Override
	public final void remove(DGNode child) {
		groupNode.remove(child);
	}

	public final void remove(int index) {
		groupNode.remove(index);
	}
}
