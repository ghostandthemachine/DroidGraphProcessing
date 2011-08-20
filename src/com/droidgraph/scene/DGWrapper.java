package com.droidgraph.scene;

import java.util.Collections;
import java.util.List;

import com.droidgraph.transformation.Vec3f;
import com.droidgraph.translation.Bounds;


public abstract class DGWrapper extends DGParent{
	   private List<DGNode> singletonList;
	    
	    protected abstract DGNode getRoot();
	    
	    protected void initParent() {
	        // TODO: this is a hack; we could just make it the responsibility
	        // of the subclass to do this, but SGNode.setParent() is package-private
	        getRoot().setParent(this);
	    }
	    
	    @Override
		public List<DGNode> getChildren() {
	        DGNode root = getRoot();
	        if (root == null) {
	            return Collections.emptyList();
	        } else {
	            if (singletonList == null || singletonList.get(0) != root) {
	                singletonList = Collections.singletonList(root);
	            }
	            return singletonList;
	        }
	    }
	    
	    @Override
	    public Bounds getBounds(Vec3f transform) {
	        return getRoot().getBounds(transform);
	    }
}
