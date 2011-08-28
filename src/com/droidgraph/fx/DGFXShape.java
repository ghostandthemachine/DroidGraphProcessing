package com.droidgraph.fx;

import com.droidgraph.scene.DGShape;
import com.droidgraph.shape.DGPShape;

public class DGFXShape extends DGFXAbstractShape {

	private DGShape shapeNode;

	public DGFXShape() {
		super(new DGShape());
		this.shapeNode = (DGShape)getLeaf();
	}
	
	public void setShape(DGPShape shape) {
		shapeNode.setShape(shape);
		this.bounds.accumulateChild(shape.getBounds());
		
	}
	
	public void setOpacity(float opacity) {
		shapeNode.setOpacity(opacity);
		if(shapeNode.getOpacity() == 0) {
			shapeNode.setVisible(false);
		} else if(shapeNode.getOpacity() > 0 && !shapeNode.isVisible()) {
			shapeNode.setVisible(true);
		}
//		Shared.p(opacity);
	}
	
	public float getOpacity() {
		return shapeNode.getOpacity();
	}
}