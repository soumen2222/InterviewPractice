package com.qualitylogic.openadr.core.signal.helper;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import com.qualitylogic.openadr.core.common.TestSession;
import com.qualitylogic.openadr.core.signal.OadrCreateReportType;
import com.qualitylogic.openadr.core.signal.OadrPayload;
import com.qualitylogic.openadr.core.signal.OadrRegisterReportType;
import com.qualitylogic.openadr.core.signal.OadrRegisteredReportType;
import com.qualitylogic.openadr.core.signal.OadrSignedObject;
import com.qualitylogic.openadr.core.util.PropertiesFileReader;

public class RegisteredReportEventHelper {

	@SuppressWarnings("unchecked")
	public static OadrRegisteredReportType createOadrRegisteredReportFromString(String data) {
		OadrRegisteredReportType oadrRegisteredReportType = null;
		if (data == null || data.length() < 1)
			return null;

		try {

			JAXBContext testcontext = JAXBContext
					.newInstance("com.qualitylogic.openadr.core.signal");
			InputStream is = new ByteArrayInputStream(data.getBytes("UTF-8"));
			Unmarshaller unmarshall = testcontext.createUnmarshaller();

			//oadrRegisteredReportType = (OadrRegisteredReportType)((JAXBElement<Object>)unmarshall.unmarshal(is)).getValue();
			OadrSignedObject oadrSignedObject = ((OadrPayload)unmarshall.unmarshal(is)).getOadrSignedObject();
			oadrRegisteredReportType = oadrSignedObject.getOadrRegisteredReport();
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return oadrRegisteredReportType;
	}

	public static OadrRegisteredReportType loadOadrRegisteredReport(String fileName)
			throws JAXBException, FileNotFoundException,
			UnsupportedEncodingException {

		ArrayList<OadrRegisterReportType> registeredReports = TestSession.getOadrRegisterReportTypeReceivedList();
		OadrRegisterReportType registeredReport = registeredReports.get(registeredReports.size() - 1);
		
		OadrRegisteredReportType oadrRegisteredReportType = ((OadrRegisteredReportType) new SchemaHelper()
				.loadTestDataXMLFile(fileName).getOadrRegisteredReport());
		oadrRegisteredReportType.getEiResponse().setRequestID(registeredReport.getRequestID());
		oadrRegisteredReportType.setVenID(new PropertiesFileReader().getVenID());

		return oadrRegisteredReportType;
	}
	
	public static OadrRegisteredReportType loadOadrRegisteredReport()
			throws JAXBException, FileNotFoundException,
			UnsupportedEncodingException {

		return loadOadrRegisteredReport("oadrRegisteredReport.xml");
	}	
	
	public static OadrCreateReportType createCreateReport(OadrRegisteredReportType registeredReport){
		OadrCreateReportType oadrCreateReportType = null;
		try {
				oadrCreateReportType = CreateReportEventHelper.loadOadrCreateReport();
				oadrCreateReportType.getOadrReportRequest().clear();
				oadrCreateReportType.getOadrReportRequest().addAll(registeredReport.getOadrReportRequest());
				oadrCreateReportType.setVenID(new PropertiesFileReader().getVenID());

				//oadrCreateReportType.getOadrReportRequest().get(0).getReportSpecifier()
				
		} catch (FileNotFoundException | UnsupportedEncodingException
				| JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return oadrCreateReportType;
	}
	
	
	public static void main(String args[]) throws FileNotFoundException, UnsupportedEncodingException, JAXBException{
		OadrRegisteredReportType oadrRegisteredReportType = RegisteredReportEventHelper.loadOadrRegisteredReport("oadrRegisteredReport.xml");
		String registeredReportTypeAsString = SchemaHelper.getRegisteredReportTypeAsString(oadrRegisteredReportType);
	
		System.out.println(registeredReportTypeAsString);
	}
}