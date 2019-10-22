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
import com.qualitylogic.openadr.core.signal.OadrPollType;
import com.qualitylogic.openadr.core.signal.OadrRequestEventType;
import com.qualitylogic.openadr.core.signal.OadrSignedObject;
import com.qualitylogic.openadr.core.util.PropertiesFileReader;


public class RequestEventSignalHelper {
	public static OadrRequestEventType loadOadrRequestEvent(String filePath)
			throws JAXBException, FileNotFoundException,
			UnsupportedEncodingException {
		@SuppressWarnings("unchecked")
		//OadrRequestEventType oadrRequestEvent = (OadrRequestEventType) ((JAXBElement<OadrRequestEventType>)new SchemaHelper()
			//	.loadTestDataXMLFile(filePath)).getValue();

		OadrRequestEventType oadrRequestEvent = (OadrRequestEventType) new SchemaHelper().loadTestDataXMLFile(filePath).getOadrRequestEvent();
		return oadrRequestEvent;
	}

	public static OadrRequestEventType getOadrRequestEvent()
			throws JAXBException, FileNotFoundException,
			UnsupportedEncodingException {
		OadrRequestEventType oadrRequestEvent = (OadrRequestEventType) new SchemaHelper().loadTestDataXMLFile("oadrRequestEvent_Default.xml").getOadrRequestEvent();
		oadrRequestEvent.getEiRequestEvent().setVenID(
				new PropertiesFileReader().getVenID());
		
		return oadrRequestEvent;
	}
	
	public static OadrRequestEventType createOadrRequestEventFromString(String data) {
		OadrRequestEventType oadrRequestEvent = null;
		if (data == null || data.length() < 1)
			return null;

		try {

			JAXBContext testcontext = JAXBContext
					.newInstance("com.qualitylogic.openadr.core.signal");
			InputStream is = new ByteArrayInputStream(data.getBytes("UTF-8"));
			Unmarshaller unmarshall = testcontext.createUnmarshaller();
			//oadrRequestEvent = (OadrRequestEventType) ((JAXBElement<Object>)unmarshall.unmarshal(is)).getValue();
			OadrSignedObject oadrSignedObject = ((OadrPayload)unmarshall.unmarshal(is)).getOadrSignedObject();
			oadrRequestEvent = oadrSignedObject.getOadrRequestEvent();

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return oadrRequestEvent;
	}

	

}
