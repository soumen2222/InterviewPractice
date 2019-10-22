package com.honeywell.factorymethod;

public class FactoryMaker {
	public static AbstractFactory pf = null;
	
	static AbstractFactory getFactory(String choice) {
		if (choice.equals("a")){
			pf = new ConcreteFactory1();
		}
		
		else if (choice.equals("b")){
			pf = new ConcreteFactory2();
		}
		else {
			pf = new ConcreteFactory3();
		}
		return pf;
	}

}
