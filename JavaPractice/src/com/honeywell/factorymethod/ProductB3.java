package com.honeywell.factorymethod;

public class ProductB3 implements AbstractProductB {

	ProductB3(String args)
	{
		System.out.println("Hello :" + args);
	}
	
	@Override
	public void operationB1() {
		// TODO Auto-generated method stub
		System.out.println("operationB1" );
		
	}

	@Override
	public void operationB2() {
		// TODO Auto-generated method stub
		System.out.println("operationB2" );
		
	}

}
