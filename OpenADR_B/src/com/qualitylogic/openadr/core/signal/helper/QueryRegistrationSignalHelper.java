package com.qualitylogic.openadr.core.signal.helper;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import com.qualitylogic.openadr.core.signal.OadrPayload;
import com.qualitylogic.openadr.core.signal.OadrQueryRegistrationType;
import com.qualitylogic.openadr.core.signal.OadrSignedObject;
import com.qualitylogic.openadr.core.util.OadrUtil;


public class QueryRegistrationSignalHelper {
	
	public static OadrQueryRegistrationType loadOadrQueryRegistrationRequest() throws FileNotFoundException, UnsupportedEncodingException, JAXBException{
		OadrQueryRegistrationType oadrQueryRegistrationType=loadOadrQueryRegistrationTypeRequest("oadrQueryRegistration.xml");
		return oadrQueryRegistrationType;
	}
	
	public static OadrQueryRegistrationType loadOadrQueryRegistrationTypeRequest(String filePath)
			throws JAXBException, FileNotFoundException,
			UnsupportedEncodingException {
			OadrQueryRegistrationType oadrQueryRegistrationType = (OadrQueryRegistrationType) new SchemaHelper().loadTestDataXMLFile(filePath).getOadrQueryRegistration();
			oadrQueryRegistrationType.setRequestID(OadrUtil.createUniqueQueryRegistrationRequestID());
			return oadrQueryRegistrationType;
	}

	@SuppressWarnings("unchecked")
	public static OadrQueryRegistrationType createOadrQueryRegistrationTypeRequestFromString(String data){
		OadrQueryRegistrationType oadrQueryRegistrationType = null;
		if (data == null || data.length() < 1)
			return null;

		try {

			JAXBContext testcontext = JAXBContext
					.newInstance("com.qualitylogic.openadr.core.signal");
			InputStream is = new ByteArrayInputStream(data.getBytes("UTF-8"));
			Unmarshaller unmarshall = testcontext.createUnmarshaller();
			//oadrQueryRegistrationType = (OadrQueryRegistrationType) ((JAXBElement<Object>)unmarshall.unmarshal(is)).getValue();
			OadrSignedObject oadrSignedObject = ((OadrPayload)unmarshall.unmarshal(is)).getOadrSignedObject();
			oadrQueryRegistrationType = oadrSignedObject.getOadrQueryRegistration();

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return oadrQueryRegistrationType;
	}
}
