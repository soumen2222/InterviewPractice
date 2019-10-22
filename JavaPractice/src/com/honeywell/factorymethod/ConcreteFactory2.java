package com.honeywell.factorymethod;

public class ConcreteFactory2 implements AbstractFactory{

	@Override
	public AbstractProductA createProductA() {
		// TODO Auto-generated method stub
		return new ProductA2("ProductA2");
	}

	@Override
	public AbstractProductB createProductB() {
		// TODO Auto-generated method stub
		return new ProductB2("ProductB2");
	}

}
