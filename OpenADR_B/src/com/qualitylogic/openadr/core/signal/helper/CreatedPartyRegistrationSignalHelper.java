package com.qualitylogic.openadr.core.signal.helper;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import com.qualitylogic.openadr.core.signal.OadrCreatePartyRegistrationType;
import com.qualitylogic.openadr.core.signal.OadrCreatedPartyRegistrationType;
import com.qualitylogic.openadr.core.signal.OadrPayload;
import com.qualitylogic.openadr.core.signal.OadrPollType;
import com.qualitylogic.openadr.core.signal.OadrRequestEventType;
import com.qualitylogic.openadr.core.signal.OadrSignedObject;


public class CreatedPartyRegistrationSignalHelper {
	
	public static OadrCreatedPartyRegistrationType loadCreatePartyRegistrationRequest(String filePath)
			throws JAXBException, FileNotFoundException,
			UnsupportedEncodingException {
		OadrCreatedPartyRegistrationType oadrCreatedPartyRegistration = (OadrCreatedPartyRegistrationType) new SchemaHelper().loadTestDataXMLFile(filePath).getOadrCreatedPartyRegistration();
		return oadrCreatedPartyRegistration;
	}

	@SuppressWarnings("unchecked")
	public static OadrCreatedPartyRegistrationType createCreatedPartyRegistrationTypeFromString(String data){
		OadrCreatedPartyRegistrationType oadrCreatedPartyRegistrationType = null;
		if (data == null || data.length() < 1)
			return null;

		try {

			JAXBContext testcontext = JAXBContext
					.newInstance("com.qualitylogic.openadr.core.signal");
			InputStream is = new ByteArrayInputStream(data.getBytes("UTF-8"));
			Unmarshaller unmarshall = testcontext.createUnmarshaller();
			//oadrCreatedPartyRegistrationType = (OadrCreatedPartyRegistrationType) ((JAXBElement<Object>)unmarshall.unmarshal(is)).getValue();

			OadrSignedObject oadrSignedObject = ((OadrPayload)unmarshall.unmarshal(is)).getOadrSignedObject();
			oadrCreatedPartyRegistrationType = oadrSignedObject.getOadrCreatedPartyRegistration();
		
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return oadrCreatedPartyRegistrationType;
	}
	
	
}
