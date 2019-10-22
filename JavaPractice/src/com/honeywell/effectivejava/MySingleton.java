package com.honeywell.effectivejava;

public enum MySingleton {

	INSTANCE;
	
	public void sayHello()
	{
		System.out.println("Hi Soumen");
	}
	
	public void sayBye()
	{
		System.out.println("Bye Soumen");
	}
}


