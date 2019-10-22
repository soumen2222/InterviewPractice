package com.qualitylogic.openadr.core.signal.helper;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import com.qualitylogic.openadr.core.signal.OadrCreatePartyRegistrationType;
import com.qualitylogic.openadr.core.signal.OadrPayload;
import com.qualitylogic.openadr.core.signal.OadrPollType;
import com.qualitylogic.openadr.core.signal.OadrRequestEventType;
import com.qualitylogic.openadr.core.signal.OadrSignedObject;
import com.qualitylogic.openadr.core.signal.OadrTransportType;
import com.qualitylogic.openadr.core.util.OadrUtil;
import com.qualitylogic.openadr.core.util.PropertiesFileReader;
import com.qualitylogic.openadr.core.util.XMLDBUtil;


public class CreatePartyRegistrationReqSignalHelper {
	
	public static OadrCreatePartyRegistrationType loadCreatePartyRegistrationRequest() throws FileNotFoundException, UnsupportedEncodingException, JAXBException{
		OadrCreatePartyRegistrationType createPartyRegistrationRequest=loadCreatePartyRegistrationRequest("oadrCreatePartyRegistration.xml");
		return createPartyRegistrationRequest;
	}
	
	public static OadrCreatePartyRegistrationType loadCreatePartyRegistrationRequest(String filePath)
			throws JAXBException, FileNotFoundException,
			UnsupportedEncodingException {
			PropertiesFileReader propertiesFileReader = new PropertiesFileReader();
			OadrCreatePartyRegistrationType oadrRequestEvent = (OadrCreatePartyRegistrationType) new SchemaHelper().loadTestDataXMLFile(filePath).getOadrCreatePartyRegistration();
			boolean isUseStaticVENID = propertiesFileReader.isUseStaticVENID();
			if(isUseStaticVENID){
				oadrRequestEvent.setVenID(propertiesFileReader.getVenID());
			}else{
				oadrRequestEvent.setVenID("");
			}
			oadrRequestEvent.setRequestID(OadrUtil.createUniqueCreatePartyRegistrationReqID());
			oadrRequestEvent.setOadrProfileName(propertiesFileReader.getCreatePartyRegistration_ProfileName());
			oadrRequestEvent.setOadrReportOnly(propertiesFileReader.getCreatePartyRegistration_ReportOnly());
			oadrRequestEvent.setOadrXmlSignature(propertiesFileReader.getCreatePartyRegistration_XmlSignature());
			oadrRequestEvent.setOadrVenName(propertiesFileReader.getCreatePartyRegistration_oadrVenName());
			
			String registrationID = new XMLDBUtil().getRegistrationID();
			if(registrationID==null || registrationID.length()<1){
				oadrRequestEvent.setRegistrationID(null);
				oadrRequestEvent.setVenID(null);	
			}else{
				oadrRequestEvent.setRegistrationID(registrationID);
				oadrRequestEvent.setVenID(propertiesFileReader.getVenID());				
			}
			
			if (propertiesFileReader.isXmppTransportType()) {
				oadrRequestEvent.setOadrTransportName(OadrTransportType.XMPP);
				oadrRequestEvent.setOadrHttpPullModel(false);
			} else {
				oadrRequestEvent.setOadrTransportName(OadrTransportType.SIMPLE_HTTP);
				oadrRequestEvent.setOadrHttpPullModel(OadrUtil.isCreatePartyRegistration_PullModel());
			}
			
			if(!OadrUtil.isCreatePartyRegistration_PullModel()){

				oadrRequestEvent.setOadrTransportAddress(propertiesFileReader.getCreatePartyRegistrationTransportAddress());
				
			}else{
				oadrRequestEvent.setOadrTransportAddress(null);		
			}
			
			
		return oadrRequestEvent;
	}

	@SuppressWarnings("unchecked")
	public static OadrCreatePartyRegistrationType createCreatePartyRegistrationRequestFromString(String data){
		OadrCreatePartyRegistrationType oadrCreatePartyRegistrationType = null;
		if (data == null || data.length() < 1)
			return null;

		try {

			JAXBContext testcontext = JAXBContext
					.newInstance("com.qualitylogic.openadr.core.signal");
			InputStream is = new ByteArrayInputStream(data.getBytes("UTF-8"));
			Unmarshaller unmarshall = testcontext.createUnmarshaller();
			//oadrCreatePartyRegistrationType = (OadrCreatePartyRegistrationType) ((JAXBElement<Object>)unmarshall.unmarshal(is)).getValue();
			OadrSignedObject oadrSignedObject = ((OadrPayload)unmarshall.unmarshal(is)).getOadrSignedObject();
			oadrCreatePartyRegistrationType = oadrSignedObject.getOadrCreatePartyRegistration();

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return oadrCreatePartyRegistrationType;
	}
}
