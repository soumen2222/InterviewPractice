package com.honeywell.factorymethod;

public class ProductA2 implements AbstractProductA {

	ProductA2(String args)
	{
		System.out.println("Hello :" + args);
	}
	
	@Override
	public void operationA1() {
		// TODO Auto-generated method stub
		System.out.println("operationA1" );
		
	}

	@Override
	public void operationA2() {
		// TODO Auto-generated method stub
		System.out.println("operationA2" );
		
	}

}
