package com.qualitylogic.openadr.core.signal.helper;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import com.qualitylogic.openadr.core.common.TestSession;
import com.qualitylogic.openadr.core.signal.OadrCreateReportType;
import com.qualitylogic.openadr.core.signal.OadrCreatedReportType;
import com.qualitylogic.openadr.core.signal.OadrPayload;
import com.qualitylogic.openadr.core.signal.OadrReportRequestType;
import com.qualitylogic.openadr.core.signal.OadrSignedObject;
import com.qualitylogic.openadr.core.util.PropertiesFileReader;

public class CreatedReportEventHelper {

	public static OadrCreatedReportType createOadrCreatedReportFromString(String data) {
		OadrCreatedReportType oadrCreatedReportType = null;
		if (data == null || data.length() < 1)
			return null;

		try {

			JAXBContext testcontext = JAXBContext
					.newInstance("com.qualitylogic.openadr.core.signal");
			InputStream is = new ByteArrayInputStream(data.getBytes("UTF-8"));
			Unmarshaller unmarshall = testcontext.createUnmarshaller();

			//oadrCreatedReportType = (OadrCreatedReportType)((JAXBElement<Object>)unmarshall.unmarshal(is)).getValue();
			OadrSignedObject oadrSignedObject = ((OadrPayload)unmarshall.unmarshal(is)).getOadrSignedObject();
			oadrCreatedReportType = oadrSignedObject.getOadrCreatedReport();

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return oadrCreatedReportType;
	}

	public static OadrCreatedReportType loadOadrCreatedReport(String fileName)
			throws JAXBException, FileNotFoundException,
			UnsupportedEncodingException {

		OadrCreatedReportType oadrCreatedReportType = ((OadrCreatedReportType) new SchemaHelper()
				.loadTestDataXMLFile(fileName).getOadrCreatedReport());
		oadrCreatedReportType.setVenID(new PropertiesFileReader().getVenID());

		return oadrCreatedReportType;
	}

	public static OadrCreatedReportType loadOadrCreatedReport()
			throws JAXBException, FileNotFoundException,
			UnsupportedEncodingException {

		OadrCreatedReportType oadrCreatedReportType = loadOadrCreatedReport("oadrCreatedReport.xml");
				 ArrayList<OadrCreateReportType>  oadrCreateReportListReceived = TestSession.getOadrCreateReportTypeReceivedList();
		
				if(oadrCreateReportListReceived!=null && oadrCreateReportListReceived.size()>0){
					OadrCreateReportType oadrCreateReportType = oadrCreateReportListReceived.get(oadrCreateReportListReceived.size()-1);
					
					oadrCreatedReportType.getEiResponse().setResponseCode("200");
					oadrCreatedReportType.getEiResponse().setResponseDescription("OK");
					oadrCreatedReportType.getEiResponse().setRequestID(oadrCreateReportType.getRequestID());
					oadrCreatedReportType.getOadrPendingReports().getReportRequestID().clear();
					
					 List<OadrReportRequestType>  reportsReceivedinCreateReport = oadrCreateReportType.getOadrReportRequest();
					 
					 for(OadrReportRequestType eachReport:reportsReceivedinCreateReport){
						 oadrCreatedReportType.getOadrPendingReports().getReportRequestID().add(eachReport.getReportRequestID());
					 }
					 
				
				}
		
		return oadrCreatedReportType;
	}
}