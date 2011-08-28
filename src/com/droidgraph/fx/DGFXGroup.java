package com.droidgraph.fx;

import java.util.List;

import com.droidgraph.scene.DGGroup;
import com.droidgraph.scene.DGNode;
import com.droidgraph.scene.DGShape;
import com.droidgraph.util.Shared;

public class DGFXGroup extends DGFXNode {
	
	private boolean DEBUG = false;

	private DGGroup groupNode;
	
	private float opacity = 255;

	public DGFXGroup() {
		super(new DGGroup());
		this.groupNode = (DGGroup) getLeaf();
	}

	public final List<DGNode> getFXChildren() {
		return groupNode.getChildren();
	}

	public final void add(int index, DGNode child) {
		groupNode.add(index, child);
		bounds.accumulateChild(child.getBounds2D());
	}

	public final void add(DGNode child) {
		groupNode.add(-1, child);
		bounds.accumulateChild(child.getBounds2D());
	}

	@Override
	public final void remove(DGNode child) {
		groupNode.remove(child);
	}

	public final void remove(int index) {
		groupNode.remove(index);
	}

	public void setOpacity(float o) {
		opacity = o;

		for (DGNode child : getChildren()) {
			if(DEBUG) {
				Shared.p("DGFXGroup - setOpacity(), class type of child:", child.getClass(), child.getClass().getSuperclass());
			}
			if (child instanceof DGFXShape) {
				DGFXShape shapeNode = (DGFXShape) child;
				if(DEBUG) {
					Shared.p("DGFXGroup - DGFXShape, setOpacity():", opacity);
				}
				shapeNode.setOpacity(opacity);
				if (shapeNode.getOpacity() == 0) {
					shapeNode.setVisible(false);
				} else if (shapeNode.getOpacity() > 0 && !shapeNode.isVisible()) {
					shapeNode.setVisible(true);
				}

			} else if (child instanceof DGShape) {
				DGShape shapeNode = (DGShape) child;
				if(DEBUG) {
					Shared.p("DGFXGroup -  DGShape, setOpacity(),:", opacity);
				}
				shapeNode.setOpacity(opacity);
				if (shapeNode.getOpacity() == 0) {
					shapeNode.setVisible(false);
				} else if (shapeNode.getOpacity() > 0 && !shapeNode.isVisible()) {
					shapeNode.setVisible(true);
				}

			}
		}

	}

	public float getOpacity() {
		return opacity;
	}

}
