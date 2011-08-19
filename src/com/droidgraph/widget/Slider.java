package com.droidgraph.widget;

import com.droidgraph.event.DGMotionEvent;
import com.droidgraph.fx.DGFXGroup;
import com.droidgraph.fx.DGFXShape;
import com.droidgraph.motionlistener.ActionListener;
import com.droidgraph.shape.Rect;
import com.droidgraph.shape.RoundRect;
import com.droidgraph.util.Shared;

public class Slider extends DGFXGroup {
	
	DGFXShape valueRect = new DGFXShape();
	DGFXShape backing = new DGFXShape();
	
	float value = 0.75f;

	float sWidth = 100.0f;
	float sHeight = 500.0f;
	
	Rect valueRectShape = new Rect(10,10,80,480);
	
	public Slider s = this;
	
	
	public Slider() {
		backing.setShape(new RoundRect(0,0,sWidth,sHeight,20));
		backing.setFillColor(150,150,150,255);
		backing.addMotionListener(new ActionListener(this){
			float min = 10.0f;
			float max = sHeight - 10.0f;
			
			@Override
			public boolean actionDown(DGMotionEvent me){ 
				return super.actionDown(me);
			}
			@Override
			public boolean actionMove(DGMotionEvent me){ 
				float x1 = 10;
				float y1 = Shared.minMaxF(130, 570, me.getY()) - 100;
				float x2 = 90;
				float y2 = y1;
//				Shared.p("Slider - actionMove", this, me);
				// set the rect shape by corners instead of dimensions
				valueRectShape.setCorners(x1, y1, x2, y2, 10
						, 480, 80, 480);
				return super.actionMove(me);
			}
		});
		add(backing);
		
		valueRect.setShape(valueRectShape);
		valueRect.setFillColor(0,255,0,255);
		add(valueRect);
		
	}

}
