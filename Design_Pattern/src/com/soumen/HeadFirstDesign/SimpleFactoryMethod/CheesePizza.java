package com.soumen.HeadFirstDesign.SimpleFactoryMethod;

public class CheesePizza extends AbstarctPizza implements Pizza {

	@Override
	public void type() {
		System.out.println("CheesePizza");
		
	}


}
