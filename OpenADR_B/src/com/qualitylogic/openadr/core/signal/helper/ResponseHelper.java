package com.qualitylogic.openadr.core.signal.helper;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import com.qualitylogic.openadr.core.signal.OadrCreatedEventType;
import com.qualitylogic.openadr.core.signal.OadrPayload;
import com.qualitylogic.openadr.core.signal.OadrResponseType;
import com.qualitylogic.openadr.core.signal.OadrSignedObject;
import com.qualitylogic.openadr.core.util.PropertiesFileReader;

public class ResponseHelper {
	public static OadrResponseType loadOadrResponse(String filePath)
			throws JAXBException, FileNotFoundException,
			UnsupportedEncodingException {

		OadrResponseType OadrResponseType = ((OadrResponseType) new SchemaHelper()
				.loadTestDataXMLFile(filePath).getOadrResponse());

		return OadrResponseType;
	}

	public static OadrResponseType loadOadrResponse()
			throws JAXBException, FileNotFoundException,
			UnsupportedEncodingException {
		
		OadrResponseType  oadrResponseType  = loadOadrResponse("oadrResponse_successdefault.xml");
		
				oadrResponseType.getEiResponse().setResponseCode("200");
				oadrResponseType.setVenID(new PropertiesFileReader().getVenID());
		
		return oadrResponseType;
	}

	@SuppressWarnings("unchecked")
	public static OadrResponseType createOadrResponseFromString(String data) {
		if (data == null || data.length() < 1)
			return null;
		OadrResponseType OadrResponseType = null;
		try {
			JAXBContext testcontext = JAXBContext
					.newInstance("com.qualitylogic.openadr.core.signal");
			InputStream is = new ByteArrayInputStream(data.getBytes("UTF-8"));
			Unmarshaller unmarshall = testcontext.createUnmarshaller();
			//OadrResponseType = (OadrResponseType) ((JAXBElement<Object>)unmarshall.unmarshal(is)).getValue();
			OadrSignedObject oadrSignedObject = ((OadrPayload)unmarshall.unmarshal(is)).getOadrSignedObject();
			OadrResponseType = oadrSignedObject.getOadrResponse();

			
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return OadrResponseType;
	}

	public static OadrCreatedEventType createOadrCreatedEventFromString(String data) {
		if (data == null || data.length() < 1)
			return null;
		OadrCreatedEventType oadrCreatedEventType = null;
		try {
			JAXBContext testcontext = JAXBContext
					.newInstance("com.qualitylogic.openadr.core.signal");
			InputStream is = new ByteArrayInputStream(data.getBytes("UTF-8"));
			Unmarshaller unmarshall = testcontext.createUnmarshaller();
			//OadrCreatedEventType = (OadrCreatedEventType) unmarshall.unmarshal(is);
			//oadrCreatedEventType = (OadrCreatedEventType)((JAXBElement<Object>)unmarshall.unmarshal(is)).getValue();
			
			OadrSignedObject oadrSignedObject = ((OadrPayload)unmarshall.unmarshal(is)).getOadrSignedObject();
			oadrCreatedEventType = oadrSignedObject.getOadrCreatedEvent();

			
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return oadrCreatedEventType;
	}

	public static String getOadrResponseCode(OadrResponseType OadrResponseType) {
		if (OadrResponseType == null)
			return "";
		String responseCode = OadrResponseType.getEiResponse().getResponseCode();

		return responseCode;
	}

	public static boolean isExpectedResponseReceived(OadrResponseType OadrResponseType,
			String expectedResponseCode) {

		if (OadrResponseType == null)
			return false;

		String responseCode = "";
		responseCode = ResponseHelper.getOadrResponseCode(OadrResponseType);

		if (responseCode.equals(expectedResponseCode.trim())) {
			return true;
		} else {
			if (expectedResponseCode.startsWith("4")) {
				if (responseCode.startsWith("4")) {
					return true;
				}
			}

			return false;

		}
	}

}