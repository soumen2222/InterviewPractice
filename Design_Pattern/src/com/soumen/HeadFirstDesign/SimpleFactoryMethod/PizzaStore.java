package com.soumen.HeadFirstDesign.SimpleFactoryMethod;


//It will use Pizza factory to prepare the pizza.

public class PizzaStore {

	
	SimplePizzafactory factory;

	public PizzaStore(SimplePizzafactory factory) {
		this.factory = factory;
	}
	
	
	public Pizza orderPizza(String Type)
	{
		Pizza pizza;
		
		pizza = factory.createPizza(Type);
		
		pizza.prepare();
		pizza.bake();
		pizza.cut();
		pizza.type();
		
		return pizza;
	}
	
}
