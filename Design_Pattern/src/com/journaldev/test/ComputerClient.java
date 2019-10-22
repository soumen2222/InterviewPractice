package com.journaldev.test;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FilterInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.journaldev.abstractfactory.ComputerFactory;
import com.journaldev.beans.Computer;
import com.journaldev.beans.LaptopFactory;
import com.journaldev.beans.PCFactory;
import com.journaldev.beans.ServerFactory;

public class ComputerClient {

	public static void main(String[] args) throws FileNotFoundException {
		
		BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
		InputStream inputstream = new FileInputStream("c:\\data\\input-text.txt");
		
		
		FilterInputStream fis = new BufferedInputStream(inputstream); 

		Computer pc = ComputerFactory.createComputer(new PCFactory("2 GB","500 GB","2.4 GHz"));		
		
		Computer server = ComputerFactory.createComputer(new ServerFactory("2 GB","500 GB","2.4 GHz"));
		
		Computer laptop = ComputerFactory.createComputer(new LaptopFactory());
	}

}
