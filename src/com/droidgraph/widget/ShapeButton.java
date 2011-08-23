package com.droidgraph.widget;

import com.droidgraph.event.DGMotionEvent;
import com.droidgraph.fx.DGFXGroup;
import com.droidgraph.fx.DGFXShape;
import com.droidgraph.motionlistener.ActionListener;
import com.droidgraph.shape.DGPShape;
import com.droidgraph.shape.DGText;
import com.droidgraph.shape.RoundRect;

class ShapeButton extends DGFXGroup {

	private DGFXShape base = new DGFXShape();
	private DGFXShape text = new DGFXShape();
	private DGText msg = new DGText("off");

	public ShapeButton() {
		add(base);
		add(text);

		text.setShape(msg);
		text.setFillColor(255, 255, 255, 255);
		text.setTranslation(20, 20);

		base.setShape(new RoundRect(0, 0, 100, 50, 13));
		base.setFillColor(100, 255, 100, 255);
		base.addMotionListener(new ActionListener(base) {

			boolean toggle = false;

			@Override
			public boolean clicked(DGMotionEvent me) {
				if (toggle) {
					toggle = false;
					base.setFillColor(255, 255, 100, 255);
					text.setShape(new DGText("off"));
				} else {
					toggle = true;
					base.setFillColor(100, 255, 100, 255);
					text.setShape(new DGText("on"));
				}
				return super.clicked(me);
			}
		});

	}
	
	public void setShape(DGPShape shape) {
		base.setShape(shape);
	}

}