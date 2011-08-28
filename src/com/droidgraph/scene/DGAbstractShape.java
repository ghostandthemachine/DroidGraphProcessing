package com.droidgraph.scene;

import java.util.ArrayList;

import processing.core.PGraphics;

import com.droidgraph.material.Material;
import com.droidgraph.shape.DGPShape;
import com.droidgraph.util.DGConstants;

public abstract class DGAbstractShape extends DGLeaf implements DGConstants {
	
//	protected String TAG = "DGAbstractShape";
	public final static int STROKE = 0;
	public final static int FILL = 1;
	public final static int FILL_STROKE = 2;
	
	protected int mode = FILL;
	
	public int[] uniqueColorID;
	
	protected float[] fillColor = {255, 255, 255};
	protected float fillOpacity = 255;
	
	protected float[] strokeColor = {255, 255, 255};
	protected float strokeOpacity = 255;
	
	public DGAbstractShape() {
	}
	
	protected ArrayList<Material> materials = new ArrayList<Material>();
	
	public abstract DGPShape getShape();
	public abstract void setShape(DGPShape shape);
	
	public final Material[] getMaterial() {
		return (Material[]) materials.toArray();
	}

	public void addMaterial(Material material) {
		addMaterial(0, material);
		material.setParent(this);
	}

	public void addMaterial(int position, Material material) {
		materials.add(position, material);
	}

	public void removeMaterial(Material material) {
		materials.remove(material);
	}
	
	public void setStrokeAndFill(int saf) {
		mode = (saf <= 2 && saf >= 0) ? saf : mode;
		repaint(true);
	}

	public void paint(PGraphics p) {

	}
	
	@Override
	public void render(PGraphics p) {
		paint(p);
	}
	
	public void setFillColor(float r, float g, float b, float a) {
		fillColor[0] = r;
		fillColor[1] = g;
		fillColor[2] = b;
		fillOpacity = a;
		repaint(true);
	}
	
	public void setFillColor(float r, float g, float b) {
		fillColor[0] = r;
		fillColor[1] = g;
		fillColor[2] = b;
		repaint(true);
	}
	
	public void setOpacity(float opacity) {
		strokeOpacity = opacity;
		fillOpacity = opacity;
		if(opacity == 0) {
			setVisible(false);
		} else if(opacity > 0 && !isVisible()) {
			setVisible(true);
		}
		repaint(true);
	}
	
	public float getOpacity() {
		return (strokeOpacity + fillOpacity) / 2;
	}
	
	
	public float[] getFillColor() {
		return fillColor;
	}
	
	public void setStrokeColor(float r, float g, float b, float a) {
		strokeColor[0] = r;
		strokeColor[1] = g;
		strokeColor[2] = b;
		strokeOpacity = a;
		repaint(true);
	}
	
	public void setStrokeColor(float r, float g, float b) {
		strokeColor[0] = r;
		strokeColor[1] = g;
		strokeColor[2] = b;
		repaint(true);
	}
	
	public float[] getStrokeColor() {
		return strokeColor;
	}
	


	public float getFillRed() {
		return fillColor[0];
	}

	public float getFillGreen() {
		return fillColor[1];
	}

	public float getFillBlue() {
		return fillColor[2];
	}
	
	public float getFillOpacity() {
		return fillOpacity;
	}
	
	public float getStrokeRed() {
		return strokeColor[0];
	}

	public float getStrokeGreen() {
		return strokeColor[1];
	}

	public float getStrokeBlue() {
		return strokeColor[2];
	}

	public float getStrokeOpacity() {
		return strokeOpacity;
	}

}
