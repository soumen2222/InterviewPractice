package com.soumen.HeadFirstDesign.FactoryMethod;

public class CHStylePizzaStore extends PizzaStore{

	public Pizza createPizza(String type) {

		Pizza pizza = null;
		
		if(type.equals("Cheese"))
		{
			pizza = new CheesePizza();
		}else if(type.equals("pepperoni"))
		{
			pizza = new PepperoniPizza();
		}else if(type.equals("clam"))
		{
			pizza = new ClamPizza();
		}
		return pizza;
	}
	
	

}
