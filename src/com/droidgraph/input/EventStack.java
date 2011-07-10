package com.droidgraph.input;

import java.util.ArrayList;
import java.util.Stack;

public class EventStack<T,S> {
	
	private Stack<T> tStack = new Stack<T>();
	private Stack<S> sStack = new Stack<S>();
	
	public EventStack() {
	}

	public void clear() {
		tStack.clear();
		sStack.clear();
	}
	
	public void push(T partA, S partB) {
		tStack.push(partA);
		sStack.push(partB);
	}
	
	public Object[] pop() {
		return new Object[] {tStack.pop(), sStack.pop()};
	}
	
	public S[] getValues(T key) {
		ArrayList<S> list = new ArrayList<S>();
		for (S s : sStack) {
			list.add(s);
		}
		return (S[]) list.toArray();
	}

}
