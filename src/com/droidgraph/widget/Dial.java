package com.droidgraph.widget;

import processing.core.PApplet;

import com.droidgraph.event.DGMotionEvent;
import com.droidgraph.fx.DGFXGroup;
import com.droidgraph.fx.DGFXShape;
import com.droidgraph.motionlistener.ActionListener;
import com.droidgraph.scene.DGAbstractShape;
import com.droidgraph.scene.DGShape;
import com.droidgraph.shape.Ellipse;
import com.droidgraph.shape.Rect;
import com.droidgraph.util.Shared;

public class Dial extends DGFXGroup {

	private float size = 50;

	private float indicatorOffset = 5.0f;

	private DGFXGroup dial;
	private DGFXShape indicator;
	private DGFXShape circle;

	private DGShape back;

	public Dial() {
		this(50);
	}

	public Dial(float size) {
		this.size = size;

		dial = new DGFXGroup();
		dial.setRotationCenter(size/2, size/2);

		back = new DGShape();
//		back.setShape(new RoundRect(0, 0, size, size, Shared.minMaxF(4, 12,	(size / 4))));
		back.setShape(new Rect(0,0,size, size));
		back.setFillColor(150, 150, 150);
		add(back);

		circle = new DGFXShape();
		circle.setShape(new Ellipse(0, 0, size - indicatorOffset, size - indicatorOffset));
		circle.setTranslation(size / 2, size / 2);
		circle.setFillColor(210, 210, 210);
		circle.setStrokeAndFill(DGAbstractShape.FILL_STROKE);
		circle.setStrokeColor(255, 255, 255);

		indicator = new DGFXShape();
		indicator.setShape(new Ellipse(0, 0, 9, 9));
		indicator.setTranslation((size / 2), indicatorOffset * 2);
		indicator.setFillColor(255, 255, 255);

		dial.add(circle);
		dial.add(indicator);

		
		add(dial);

		
		// now add them motion listener
		addMotionListener(new ActionListener(back) {

			@Override
			public boolean actionDown(DGMotionEvent me) {
				Shared.p(getParent().getBounds());
				return super.actionDown(me);
			}

			@Override
			public boolean actionMove(DGMotionEvent me) {
				dial.setRotationZ(dial.getRotationZ() - PApplet.radians(me.getVelocityX()));
				return super.actionMove(me);
			}

		});

	}

}
