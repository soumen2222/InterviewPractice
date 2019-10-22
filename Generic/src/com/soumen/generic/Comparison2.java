package com.soumen.generic;

abstract class Fruit {
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

	protected int compareTo(Fruit that) {
		return this.size < that.size ? -1 : this.size == that.size ? 0 : 1;
	}
}

class Apple extends Fruit implements Comparable<Apple> {
	public Apple(int size) {
		super("Apple", size);
	}

	public int compareTo(Apple a) {
		return super.compareTo(a);
	}
}

class Orange extends Fruit implements Comparable<Orange> {
	public Orange(int size) {
		super("Orange", size);
	}

	public int compareTo(Orange o) {
		return super.compareTo(o);
	}
}