package com.soumen.regex;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

abstract class Fruit implements Comparable<Fruit> {
	protected String name;
	protected int size;

	protected Fruit(String name, int size) {
		this.name = name;
		this.size = size;
	}

	public boolean equals(Object o) {
		if (o instanceof Fruit) {
			Fruit that = (Fruit) o;
			return this.name.equals(that.name) && this.size == that.size;
		} else
			return false;
	}

	public int hashCode() {
		return name.hashCode() * 29 + size;
	}

	public int compareTo(Fruit that) {
		return this.size < that.size ? -1 : this.size == that.size ? 0 : 1;
	}
}

class Apple extends Fruit {
	public Apple(int size) {
		super("Apple", size);
	}
}

class Orange extends Fruit {
	public Orange(int size) {
		super("Orange", size);
	}
}
