package com.qualitylogic.openadr.core.signal.helper;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import com.qualitylogic.openadr.core.signal.OadrCanceledOptType;
import com.qualitylogic.openadr.core.signal.OadrPayload;
import com.qualitylogic.openadr.core.signal.OadrSignedObject;

public class CanceledOptEventHelper {

	public static OadrCanceledOptType createCanceledOptTypeEventFromString(String data) {
		OadrCanceledOptType oadrCanceledOptType = null;
		if (data == null || data.length() < 1)
			return null;

		try {

			JAXBContext testcontext = JAXBContext
					.newInstance("com.qualitylogic.openadr.core.signal");
			InputStream is = new ByteArrayInputStream(data.getBytes("UTF-8"));
			Unmarshaller unmarshall = testcontext.createUnmarshaller();
			OadrSignedObject oadrSignedObject = ((OadrPayload)unmarshall.unmarshal(is)).getOadrSignedObject();
			
			oadrCanceledOptType = oadrSignedObject.getOadrCanceledOpt();

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return oadrCanceledOptType;
	}

	public static OadrCanceledOptType loadOadrCanceledEvent(String fileName)
			throws JAXBException, FileNotFoundException,
			UnsupportedEncodingException {

		OadrCanceledOptType oadrCanceledOptType = ((OadrCanceledOptType) new SchemaHelper()
				.loadTestDataXMLFile(fileName).getOadrCanceledOpt());

		return oadrCanceledOptType;
	}
	


}