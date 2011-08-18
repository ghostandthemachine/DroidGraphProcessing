package com.droidgraph.fx;

import com.droidgraph.scene.DGAbstractShape;
import com.droidgraph.shape.DGPShape2D;

public class DGFXAbstractShape extends DGFXNode{

    private DGAbstractShape shapeNode;
    
    DGFXAbstractShape(DGAbstractShape shape) {
        super(shape);
        this.shapeNode = shape;
    }
    
    public final DGPShape2D getShape() {
        return shapeNode.getShape();
    }
    
    public void setFillColor(float r, float g, float b, float a) {
        shapeNode.setFillColor(r, g, b, a);
    }
    
    public final float[] getFillColor() {
        return shapeNode.getFillColor();
    }

    public void setStrokeColor(float r, float g, float b, float a) {
        shapeNode.setStrokeColor(r, g, b, a);
    }

    public final float[] getStrokeColor() {
        return shapeNode.getStrokeColor();
    }
}
