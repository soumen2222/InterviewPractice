package com.soumen.HeadFirstDesign.FactoryMethod;


//It will use Pizza factory to prepare the pizza.

public abstract class PizzaStore {

			
	
	public Pizza orderPizza(String Type)
	{
		Pizza pizza;
		
		pizza = createPizza(Type);
		
		pizza.prepare();
		pizza.bake();
		pizza.cut();
		pizza.type();
		
		return pizza;
	}

	public abstract Pizza createPizza(String type);
	
}
