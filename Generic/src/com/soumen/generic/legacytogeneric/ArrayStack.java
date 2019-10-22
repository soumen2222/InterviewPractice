


package com.soumen.generic.legacytogeneric;

import java.util.ArrayList;
import java.util.List;

public class ArrayStack implements Stack {

	private List list;
	public ArrayStack() { list = new ArrayList(); }
	public boolean empty() { return list.size() == 0; }
	public void push(Object elt) { list.add(elt); }
	public Object pop() {
	Object elt = list.remove(list.size()-1);
	return elt;
	}
	public String toString() { return "stack"+list.toString(); }
	
}

