package com.qualitylogic.openadr.core.signal.helper;

import java.io.InputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import com.mysql.jdbc.Util;

public class TestClass {

	public static void main(String args[]){
		System.out.println(new TestClass().loadTestDataXMLFile("oadrDistributeEvent_Pull_CPP_twoEvents_OneInterval.xml"));
		
		
	}
	public Object loadTestDataXMLFile(String fileName) {

		JAXBContext context;
		Object testDataFile = null;
		try {
			context = JAXBContext
					.newInstance("com.qualitylogic.openadr.core.signal");

			Unmarshaller unmarshall = context.createUnmarshaller();
			InputStream inputStream = this
					.getClass()
					.getClassLoader()
					.getResourceAsStream(
							"com/qualitylogic/openadr/core/testdata/"
									+ fileName);

			testDataFile = unmarshall.unmarshal(inputStream);
		} catch (Exception e) {
			e.printStackTrace();

			String exceptionString = Util.stackTraceToString(e);
			LogHelper.addTrace(exceptionString);
		}
		return testDataFile;
	}
	
}
