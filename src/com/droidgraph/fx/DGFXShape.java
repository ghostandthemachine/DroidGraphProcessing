package com.droidgraph.fx;

import com.droidgraph.scene.DGShape;
import com.droidgraph.shape.DGPShape2D;

public class DGFXShape extends DGFXAbstractShape {

	private DGShape shapeNode;

	public DGFXShape() {
		super(new DGShape());
		this.shapeNode = (DGShape)getLeaf();
	}
	
	public void setShape(DGPShape2D shape) {
		shapeNode.setShape(shape);
		this.bounds.accumulate(shape.getBounds());
		
	}
	
}