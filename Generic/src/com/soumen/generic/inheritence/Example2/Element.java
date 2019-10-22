package com.soumen.generic.inheritence.Example2;

public class Element<T> {
	protected T value;
	
	public void setValue(T t) {
        this.value = t;
    }

	
	 public T getValue() {
	        return value;
	    }

}
