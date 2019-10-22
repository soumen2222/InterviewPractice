package com.soumen.HeadFirstDesign.FactoryMethod;
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
		
		
		PizzaStore nypizzaStore = new NYStylePizzaStore();
		nypizzaStore.orderPizza("clam");
		
	}

}
