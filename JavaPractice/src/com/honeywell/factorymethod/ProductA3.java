package com.honeywell.factorymethod;

public class ProductA3 implements AbstractProductA {

	ProductA3(String args)
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
