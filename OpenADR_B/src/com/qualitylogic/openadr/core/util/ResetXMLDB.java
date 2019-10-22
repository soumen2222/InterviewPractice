package com.qualitylogic.openadr.core.util;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class ResetXMLDB {

	public static void main(String args[]){
		

	}
	
	public static void clearVTN(){
		String line="\n";
		try {
			BufferedWriter out = new BufferedWriter(new FileWriter(new PropertiesFileReader().getXMLDBFile_VTN()));
			out.write("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>");
			out.write(line);
			out.write("<db>");
			out.write(line);
			out.write("<VEN>");
			out.write(line);
			out.write("<ID/>");
			out.write(line);
			out.write("<REGISTRATION_ID/>");
			out.write(line);
			out.write("<TRANSPORT_ADDRESS/>");
			out.write(line);
			out.write("</VEN>");
			out.write(line);
			out.write("<VTN_REGISTER_REPORT_RECEIVED/>");
			out.write(line);
			out.write("<VEN_REGISTER_REPORT_RECEIVED/>");
			out.write(line);
			out.write("</db>");
			out.close();
			} catch (IOException e){ 
				e.printStackTrace();

				}
	}

	public static void clearVEN(){
		String line="\n";
		try {
			BufferedWriter out = new BufferedWriter(new FileWriter(new PropertiesFileReader().getXMLDBFile_VEN()));
			out.write("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>");
			out.write(line);
			out.write("<db>");
			out.write(line);
			out.write("<VEN>");
			out.write(line);
			out.write("<ID/>");
			out.write(line);
			out.write("<REGISTRATION_ID/>");
			out.write(line);
			out.write("<TRANSPORT_ADDRESS/>");
			out.write(line);
			out.write("</VEN>");
			out.write(line);
			out.write("<VTN_REGISTER_REPORT_RECEIVED/>");
			out.write(line);
			out.write("<VEN_REGISTER_REPORT_RECEIVED/>");
			out.write(line);
			out.write("</db>");
			out.close();
			} catch (IOException e){ 
				e.printStackTrace();

				}
	}
}
