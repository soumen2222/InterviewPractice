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
import com.qualitylogic.openadr.core.signal.OadrQueryRegistrationType;
import com.qualitylogic.openadr.core.signal.OadrRequestEventType;
import com.qualitylogic.openadr.core.signal.OadrSignedObject;
import com.qualitylogic.openadr.core.util.PropertiesFileReader;


public class PollEventSignalHelper {

	public static OadrPollType loadOadrPollType(String filePath)
			throws JAXBException, FileNotFoundException,
			UnsupportedEncodingException {
		/*OadrPollType oadrPollType = (OadrPollType) new SchemaHelper().loadTestDataXMLFile(filePath).getOadrPoll();
		oadrPollType.setVenID(new PropertiesFileReader().getVenID());
		*/
		OadrPollType oadrPollType = (OadrPollType) new SchemaHelper().loadTestDataXMLFile(filePath).getOadrPoll();
		return oadrPollType;
	}

	public static OadrPollType loadOadrPollType()
			throws JAXBException, FileNotFoundException,
			UnsupportedEncodingException {
		OadrPollType oadrPollType = (OadrPollType) new SchemaHelper().loadTestDataXMLFile(new PropertiesFileReader().get("testDataPath")
				+ "oadrPoll.xml").getOadrPoll();
		
		oadrPollType.setVenID(new PropertiesFileReader().getVenID());
		
		return oadrPollType;
	}
	
	public static OadrPollType createOadrPollTypeFromString(String data){
		OadrPollType oadrPollType = null;
		if (data == null || data.length() < 1)
			return null;

		try {

			JAXBContext testcontext = JAXBContext
					.newInstance("com.qualitylogic.openadr.core.signal");
			InputStream is = new ByteArrayInputStream(data.getBytes("UTF-8"));
			Unmarshaller unmarshall = testcontext.createUnmarshaller();
			OadrSignedObject oadrSignedObject = ((OadrPayload)unmarshall.unmarshal(is)).getOadrSignedObject();
			oadrPollType = oadrSignedObject.getOadrPoll();

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return oadrPollType;
	}
	
}
