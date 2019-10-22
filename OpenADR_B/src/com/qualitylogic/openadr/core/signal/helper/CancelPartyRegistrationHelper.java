package com.qualitylogic.openadr.core.signal.helper;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.w3c.dom.Node;

import com.qualitylogic.openadr.core.bean.ServiceType;
import com.qualitylogic.openadr.core.signal.OadrCancelPartyRegistrationType;
import com.qualitylogic.openadr.core.signal.OadrPayload;
import com.qualitylogic.openadr.core.signal.OadrReportType;
import com.qualitylogic.openadr.core.signal.OadrSignedObject;
import com.qualitylogic.openadr.core.util.Clone;
import com.qualitylogic.openadr.core.util.OadrUtil;
import com.qualitylogic.openadr.core.util.PropertiesFileReader;
import com.qualitylogic.openadr.core.util.XMLDBUtil;


public class CancelPartyRegistrationHelper {
	
	
	public static OadrCancelPartyRegistrationType loadOadrCancelPartyRegistrationRequest(){
		
		PropertiesFileReader properties = new PropertiesFileReader();
		XMLDBUtil xmlDBUtil=new XMLDBUtil();
		OadrCancelPartyRegistrationType cancelPartyRegistrationType=loadOadrCancelPartyRegistrationTypeRequest("oadrCancelPartyRegistration.xml");

		cancelPartyRegistrationType.setRequestID(OadrUtil.createUniqueCancelRequestID());
		cancelPartyRegistrationType.setRegistrationID(xmlDBUtil.getRegistrationID());		
		cancelPartyRegistrationType.setVenID(properties.getVenID());

		return cancelPartyRegistrationType;
	}
	
	
	public static OadrCancelPartyRegistrationType loadOadrCancelPartyRegistrationTypeRequest(String filePath){
			PropertiesFileReader propertiesFileReader = new PropertiesFileReader();
			OadrCancelPartyRegistrationType oadrCancelPartyRegistration = (OadrCancelPartyRegistrationType) new SchemaHelper().loadTestDataXMLFile(filePath).getOadrCancelPartyRegistration();
			oadrCancelPartyRegistration.setRegistrationID(new XMLDBUtil().getRegistrationID());
			oadrCancelPartyRegistration.setRequestID("REQ"+OadrUtil.createUniqueID());
			oadrCancelPartyRegistration.setVenID(new PropertiesFileReader().getVenID());
			boolean isUseStaticVENID = propertiesFileReader.isUseStaticVENID();
			
			/*
			if(isUseStaticVENID){
				oadrRequestEvent.setVenID(propertiesFileReader.getVenID());
			}else{
				oadrRequestEvent.setVenID("");
			}
			oadrRequestEvent.setRequestID(OadrUtil.createCreatePartyRegistrationReqID());
			oadrRequestEvent.setOadrProfileName(propertiesFileReader.getCreatePartyRegistration_ProfileName());
			oadrRequestEvent.setOadrTransportAddress(propertiesFileReader.getCreatePartyRegistration_TransportAddress());
			oadrRequestEvent.setOadrReportOnly(propertiesFileReader.getCreatePartyRegistration_ReportOnly());
			oadrRequestEvent.setOadrReportOnly(propertiesFileReader.getCreatePartyRegistration_XmlSignature());
			oadrRequestEvent.setOadrHttpPullModel(propertiesFileReader.getCreatePartyRegistration_HttpPullModel());
			oadrRequestEvent.setOadrVenName(propertiesFileReader.getCreatePartyRegistration_oadrVenName());
			
			
			if(propertiesFileReader.getCreatePartyRegistration_TransportName().equalsIgnoreCase("SIMPLE_HTTP")){
				oadrRequestEvent.setOadrTransportName(OadrTransportType.SIMPLE_HTTP);
			}else if(propertiesFileReader.getCreatePartyRegistration_TransportName().equalsIgnoreCase("XMPP")){
				oadrRequestEvent.setOadrTransportName(OadrTransportType.XMPP);
			}else{
				System.out.println("Wrong Profile Transport "+propertiesFileReader.getCreatePartyRegistration_TransportName()+" configured");
				System.exit(0);
			}
			*/
		return oadrCancelPartyRegistration;
	}

	@SuppressWarnings("unchecked")
	public static OadrCancelPartyRegistrationType createOadrCancelPartyRegistrationTypeFromString(String data){
		OadrCancelPartyRegistrationType OadrCancelPartyRegistrationType = null;
		if (data == null || data.length() < 1)
			return null;

		try {

			JAXBContext testcontext = JAXBContext
					.newInstance("com.qualitylogic.openadr.core.signal");
			InputStream is = new ByteArrayInputStream(data.getBytes("UTF-8"));
			Unmarshaller unmarshall = testcontext.createUnmarshaller();
			//OadrCancelPartyRegistrationType = (OadrCancelPartyRegistrationType) ((JAXBElement<Object>)unmarshall.unmarshal(is)).getValue();
			OadrSignedObject oadrSignedObject = ((OadrPayload)unmarshall.unmarshal(is)).getOadrSignedObject();
			OadrCancelPartyRegistrationType = oadrSignedObject.getOadrCancelPartyRegistration();

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return OadrCancelPartyRegistrationType;
	}
}
