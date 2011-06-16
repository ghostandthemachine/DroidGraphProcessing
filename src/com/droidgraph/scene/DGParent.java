package com.droidgraph.scene;

import java.util.List;


public abstract class DGParent extends DGNode{
	
    public abstract List<DGNode> getChildren();
    public abstract void remove(DGNode node);

}
