package com.crackingcoding.algorithm;

public abstract class Animal {

	protected String name;
	private int order;
	
	public Animal(String name) {
		this.name =name;
	}

	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}
	
	public boolean isOlderThan(Animal a) {
		return this.order < a.getOrder();
	}
	
}
