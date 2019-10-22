package com.soumen.HeadFirstDesign.SimpleFactoryMethod;
/*
 * Factory pattern
 * Want to create objects implementing some interface
 * or having same parent
 * Creating an object is complex
 * e.g. lots of constructor parameters
 * Possible to simplify choice of objects
 */


public class App {

	
	public static void main(String[] args) {
		
		SimplePizzafactory simplePizzafactory = new SimplePizzafactory();
		PizzaStore pizzaStore = new PizzaStore(simplePizzafactory);
		pizzaStore.orderPizza("clam");
		
	}

}
