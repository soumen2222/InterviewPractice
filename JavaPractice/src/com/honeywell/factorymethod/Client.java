package com.honeywell.factorymethod;

public class Client {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		AbstractFactory pf = FactoryMaker.getFactory("a");
		System.out.println("got the Factory");
		AbstractProductA productA = pf.createProductA();
		System.out.println("got the product A");
		productA.operationA1();
		
		AbstractProductB productB = pf.createProductB();
		System.out.println("got the product B");
		productB.operationB1();
	}

}
