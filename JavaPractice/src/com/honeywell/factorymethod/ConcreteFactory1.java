package com.honeywell.factorymethod;

public class ConcreteFactory1 implements AbstractFactory{

	@Override
	public AbstractProductA createProductA() {
		// TODO Auto-generated method stub
		return new ProductA1("ProductA1");
	}

	@Override
	public AbstractProductB createProductB() {
		// TODO Auto-generated method stub
		return new ProductB1("ProductB1");
	}

}
