package com.soumen.HeadFirstDesign.SimpleFactoryMethod;

public class SimplePizzafactory {

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
