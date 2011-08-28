package com.droidgraph.widget;

import com.droidgraph.event.DGMotionEvent;
import com.droidgraph.fx.DGFXGroup;
import com.droidgraph.fx.DGFXShape;
import com.droidgraph.motionlistener.ActionListener;
import com.droidgraph.shape.Rect;
import com.droidgraph.util.Shared;

public class VSlider extends DGFXGroup {

	public static final int VERTICAL = 0;
	public static final int HORIZONTAL = 1;

	private float x;
	private float y;

	private float width;
	private float height;

	private int type;

	// styling for the slider
	private float sliderXOffset = 5.0f;
	private float sliderYOffset = 5.0f;

	private float[] sliderColor = { 200, 200, 200, 255 };
	private float[] sliderStrokeColor = { 200, 200, 200, 255 };

	// styling for the back rect
	private float[] backColor = { 100, 100, 100, 255 };
	private float[] backStrokeColor = { 100, 100, 100, 255 };
	
	public VSlider(float x, float y, float width, float height, int type) {

		this.x = x;
		this.y = y;

		this.width = width;
		this.height = height;

		this.type = type;
		
		initSlider();
	}
	
	public VSlider(float width, float height, int type) {
		this(0, 0, width, height, type);
	}

	public VSlider() {
		this(0, 0, 50, 400, VSlider.VERTICAL);
	}

	private void initSlider() {
		final DGFXShape s = new DGFXShape();
		s.setShape(new Rect(0, 0, width - (sliderXOffset * 2), height - (sliderYOffset * 2)));
		s.setFillColor(sliderColor[0], sliderColor[1], sliderColor[2], sliderColor[3]);
		s.setStrokeColor(sliderStrokeColor[0], sliderStrokeColor[1], sliderStrokeColor[2], sliderStrokeColor[3]);
		s.setTranslation(x + sliderXOffset, y + sliderYOffset);

		final DGFXShape sBack = new DGFXShape();
		sBack.setShape(new Rect(0, 0, width, height));
		sBack.setFillColor(backColor[0], backColor[1], backColor[2], backColor[3]);
		sBack.setStrokeColor(backStrokeColor[0], backStrokeColor[1], backStrokeColor[2], backStrokeColor[3]);
		sBack.setTranslation(x, y);
		
		// add the nodes to the group
		add(sBack);
		add(s);

		// add the motion listener
		addMotionListener(new ActionListener(this) {

			@Override
			public boolean actionDown(DGMotionEvent me) {
				if(type == VSlider.VERTICAL) {
					float ty = Shared.minMaxF(sliderYOffset, height - sliderYOffset, me.getLocalY());
					s.setShape(new Rect(0, ty, width - (sliderXOffset * 2),	(height - (sliderYOffset * 2)) - ty));
				} else {
					float tx = Shared.minMaxF(sliderXOffset, width - sliderXOffset, me.getLocalX());
					s.setShape(new Rect(0, 0, tx - (sliderXOffset * 2), height - (sliderYOffset * 2)));
				}
				return super.actionDown(me);
			}

			@Override
			public boolean actionMove(DGMotionEvent me) {
				if(type == VSlider.VERTICAL) {
					float ty = Shared.minMaxF(sliderYOffset, height - sliderYOffset, me.getLocalY());
					s.setShape(new Rect(0, ty, width - (sliderXOffset * 2),	(height - (sliderYOffset * 2)) - ty));
				} else {
					float tx = Shared.minMaxF(sliderXOffset, width - sliderXOffset, me.getLocalX());
					s.setShape(new Rect(0, 0, tx - (sliderXOffset * 2), height - (sliderYOffset * 2)));
				}
				return super.actionMove(me);
			}

			@Override
			public boolean actionUp(DGMotionEvent me) {
				return super.actionUp(me);
			}

		});
	}
}
