package com.qualitylogic.openadr.core.signal.helper;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import com.qualitylogic.openadr.core.signal.OadrCancelPartyRegistrationType;
import com.qualitylogic.openadr.core.signal.OadrCanceledPartyRegistrationType;
import com.qualitylogic.openadr.core.signal.OadrCreatePartyRegistrationType;
import com.qualitylogic.openadr.core.signal.OadrPayload;
import com.qualitylogic.openadr.core.signal.OadrPollType;
import com.qualitylogic.openadr.core.signal.OadrRequestEventType;
import com.qualitylogic.openadr.core.signal.OadrSignedObject;
import com.qualitylogic.openadr.core.signal.OadrTransportType;
import com.qualitylogic.openadr.core.util.OadrUtil;
import com.qualitylogic.openadr.core.util.PropertiesFileReader;


public class CanceledPartyRegistrationHelper {
	
	
	public static OadrCanceledPartyRegistrationType loadOadrCanceledPartyRegistrationType(){
		OadrCanceledPartyRegistrationType canceledPartyRegistrationType=loadOadrCanceledPartyRegistrationType("oadrCanceledPartyRegistration.xml");
		return canceledPartyRegistrationType;
	}
	
	
	public static OadrCanceledPartyRegistrationType loadOadrCanceledPartyRegistrationType(String filePath)
			{
			PropertiesFileReader propertiesFileReader = new PropertiesFileReader();
			OadrCanceledPartyRegistrationType oadrCanceledPartyRegistrationType = (OadrCanceledPartyRegistrationType) new SchemaHelper().loadTestDataXMLFile(filePath).getOadrCanceledPartyRegistration();
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
		return oadrCanceledPartyRegistrationType;
	}

	@SuppressWarnings("unchecked")
	public static OadrCanceledPartyRegistrationType createOadrCanceledPartyRegistrationTypeFromString(String data){
		OadrCanceledPartyRegistrationType oadrCanceledPartyRegistrationType = null;
		if (data == null || data.length() < 1)
			return null;

		try {

			JAXBContext testcontext = JAXBContext
					.newInstance("com.qualitylogic.openadr.core.signal");
			InputStream is = new ByteArrayInputStream(data.getBytes("UTF-8"));
			Unmarshaller unmarshall = testcontext.createUnmarshaller();
			//oadrCanceledPartyRegistrationType = (OadrCanceledPartyRegistrationType) ((JAXBElement<Object>)unmarshall.unmarshal(is)).getValue();

			OadrSignedObject oadrSignedObject = ((OadrPayload)unmarshall.unmarshal(is)).getOadrSignedObject();
			oadrCanceledPartyRegistrationType = oadrSignedObject.getOadrCanceledPartyRegistration();
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return oadrCanceledPartyRegistrationType;
	}
}
