package com.droidgraph.widget;

import com.droidgraph.event.DGMotionEvent;
import com.droidgraph.fx.DGFXGroup;
import com.droidgraph.fx.DGFXShape;
import com.droidgraph.motionlistener.ActionListener;
import com.droidgraph.shape.Rect;
import com.droidgraph.transformation.Vec3f;
import com.droidgraph.util.Shared;

public class Slider extends DGFXGroup {
	
	DGFXShape valueRect = new DGFXShape();
	DGFXShape backing = new DGFXShape();
	
	float value = 0.75f;

	float sWidth = 50.0f;
	float sHeight = 250.0f;
	
	float xOffset = 5.0f;
	float yOffset = 5.0f;
	
	Rect valueRectShape;
	
	public Slider s = this;
	
	public Slider() {
		buildSlider();
	}

	public Slider(float width, float height) {
		sWidth = width;
		sHeight = height;
		
		buildSlider();
	}
	
	private void buildSlider() {
		Shared.p("SLider, builderSlider", xOffset, yOffset, sWidth - (xOffset * 2), sHeight - (yOffset * 2));
		valueRectShape = new Rect(xOffset, yOffset, sWidth - (xOffset * 2), sHeight - (yOffset * 2));
		
		backing.setShape(new Rect(0,0,sWidth,sHeight));
		backing.setFillColor(0,0,150,255);
		
		addMotionListener(new ActionListener(this){
			
			@Override
			public boolean actionDown(DGMotionEvent me){ 
				return super.actionDown(me);
			}
			@Override
			public boolean actionMove(DGMotionEvent me){ 
				Vec3f local = getParent().globalToLocal(me.getX(), me.getY());
				
				float x = xOffset;
				float y = Shared.minMaxF(yOffset, sHeight - (yOffset * 2), local.y);
				
				float height = Shared.minMaxF(0, sHeight - (yOffset * 2), (sHeight - (yOffset * 2) - local.y + yOffset));
				
				valueRect.setShape(new Rect(x, y, sWidth - (xOffset * 2),  height));
				
				return super.actionMove(me);
			}
		});
		add(backing);
		
		valueRect.setShape(valueRectShape);
		valueRect.setFillColor(0,255,0,255);
		add(valueRect);
	}

}
