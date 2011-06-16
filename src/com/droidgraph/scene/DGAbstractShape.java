package com.droidgraph.scene;

import java.util.ArrayList;

import processing.core.PGraphics;

import com.droidgraph.material.Material;
import com.droidgraph.shape.DGPShape2D;

public abstract class DGAbstractShape extends DGNode{
	
//	protected String TAG = "DGAbstractShape";
	
	public static int STROKE = 0;
	public static int FILL = 1;
	public static int FILL_STROKE = 2;
	
	public int[] uniqueColorID;
	
	public DGAbstractShape() {
	}
	
	protected ArrayList<Material> materials = new ArrayList<Material>();
	
	public abstract DGPShape2D getShape();
	public abstract void setShape(DGPShape2D shape);
	
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

	public void paint(PGraphics p) {

	}
	
}
