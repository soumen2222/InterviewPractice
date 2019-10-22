package com.journaldev.beans;

import com.journaldev.abstractfactory.ComputerAbstractFactory;

public class LaptopFactory implements ComputerAbstractFactory {

	@Override
	public Computer createComputer() {
		return new Laptop();
	}

}
