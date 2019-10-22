package com.honeywell.factorymethod;

public class ProductB2 implements AbstractProductB {

	ProductB2(String args)
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
