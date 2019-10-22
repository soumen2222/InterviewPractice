package com.honeywell.factorymethod;

public class ConcreteFactory3 implements AbstractFactory{

	@Override
	public AbstractProductA createProductA() {
		// TODO Auto-generated method stub
		return new ProductA3("ProductA3");
	}

	@Override
	public AbstractProductB createProductB() {
		// TODO Auto-generated method stub
		return new ProductB3("ProductB3");
	}

}
