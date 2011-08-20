package com.droidgraph.renderer;

import processing.core.PGraphicsAndroid3D;
import processing.core.PImage;

import com.droidgraph.util.Shared;

public class DebugPickBuffer extends PGraphicsAndroid3D {

	private int currentIDIndex = 0;
	private int[] currentColor = new int[4];

	private int[] debugColor = { 255, 0, 0, 255 };
	
	private float debugStrokeWeight = 15.0f;
	
	public DebugPickBuffer() {
		strokeWeight(debugStrokeWeight);
		
	}
	
	public void pushStyle() {
		
	}
	
	public void popStyle() {
		
	}
	
	public void strokeWeight(float sw) {
		
	}
	
	public boolean displayable() {
		return true;
	}

	public void callCheckSettings() {
		super.checkSettings();
	}

	public void background(int arg) {
		super.background(0,0,0,0);
	}

	public void background(float arg) {
		super.background(0,0,0,0);
	}

	public void background(float arg, float arg_1) {
		super.background(0,0,0,0);
	}

	public void background(int arg, float arg_1) {
		super.background(0,0,0,0);
	}

	public void background(float arg, float arg_1, float arg_2) {
		super.background(0,0,0,0);
	}

	public void background(float arg, float arg_1, float arg_2, float arg_3) {
		super.background(0,0,0,0);
	}

	public void background(PImage arg) {
		super.background(0,0,0,0);
	}

	public void lights() {
	}

	public void smooth() {
	}

	public void fill(int arg) {
		super.fill(0, 0, 0, 0);
	}

	public void fill(float arg) {
		super.fill(0, 0, 0, 0);
	}

	public void fill(float arg, float arg_1) {
		super.fill(0, 0, 0, 0);
	}

	public void fill(int arg, float arg_1) {
		super.fill(0, 0, 0, 0);
	}

	public void fill(float arg, float arg_1, float arg_2) {
		super.fill(0, 0, 0, 0);
	}

	public void fill(float arg, float arg_1, float arg_2, float arg_3) {
		super.fill(0, 0, 0, 0);
	}

	public void stroke(int arg) {
		super.stroke(debugColor[0], debugColor[1], debugColor[2],
				debugColor[3]);
	}

	public void stroke(float arg) {
		super.stroke(debugColor[0], debugColor[1], debugColor[2],
				debugColor[3]);
	}

	public void stroke(float arg, float arg_1) {
		super.stroke(debugColor[0], debugColor[1], debugColor[2],
				debugColor[3]);
	}

	public void stroke(int arg, float arg_1) {
		super.stroke(debugColor[0], debugColor[1], debugColor[2],
				debugColor[3]);
	}

	public void stroke(float arg, float arg_1, float arg_2) {
		super.stroke(debugColor[0], debugColor[1], debugColor[2],
				debugColor[3]);
	}

	public void stroke(float arg, float arg_1, float arg_2, float arg_3) {
		super.stroke(debugColor[0], debugColor[1], debugColor[2],
				debugColor[3]);
	}

	public void textureMode(int arg) {
	}

	public void texture(PImage arg) {
	}

	public void vertex(float x, float y, float z, float u, float v) {
		super.vertex(x, y, z);
	}

	public void image(PImage arg, float arg_1, float arg_2) {
	}

	public void image(PImage arg, float arg_1, float arg_2, float arg_3,
			float arg_4) {
	}

	public void image(PImage arg, float arg_1, float arg_2, float arg_3,
			float arg_4, int arg_5, int arg_6, int arg_7, int arg_8) {
	}

	protected void imageImpl(PImage image, float x1, float y1, float x2,
			float y2, int u1, int v1, int u2, int v2) {
	}

	public void setCurrentIDIndex(int id) {
		currentIDIndex = id;
		setCurrentColorIdToRGBA(currentIDIndex);
		Shared.offscreenBuffer.fill(currentColor[0], currentColor[1],
				currentColor[2], currentColor[3]);
	}

	protected void setCurrentColorIdToRGBA(int sceneID) {
		int r = sceneID & 0xFF;
		int g = (sceneID >> 8) & 0xFF;
		int b = (sceneID >> 16) & 0xFF;

		currentColor[0] = r;
		currentColor[1] = g;
		currentColor[2] = b;
		currentColor[3] = 255;
	}

}
