package com.qualitylogic.openadr.core.signal.helper;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.datatype.Duration;
import javax.xml.datatype.XMLGregorianCalendar;

import com.qualitylogic.openadr.core.bean.CalculatedEventStatusBean;
import com.qualitylogic.openadr.core.bean.CreatedEventBean;
import com.qualitylogic.openadr.core.signal.EiEventSignalType;
import com.qualitylogic.openadr.core.signal.EiEventType;
import com.qualitylogic.openadr.core.signal.EventStatusEnumeratedType;
import com.qualitylogic.openadr.core.signal.IntervalType;
import com.qualitylogic.openadr.core.signal.Intervals;
import com.qualitylogic.openadr.core.signal.OadrDistributeEventType;
import com.qualitylogic.openadr.core.signal.OadrPayload;
import com.qualitylogic.openadr.core.signal.OadrRequestEventType;
import com.qualitylogic.openadr.core.signal.OadrRequestReregistrationType;
import com.qualitylogic.openadr.core.signal.OadrSignedObject;
import com.qualitylogic.openadr.core.signal.OptTypeType;
import com.qualitylogic.openadr.core.signal.PayloadFloatType;
import com.qualitylogic.openadr.core.signal.ResponseRequiredType;
import com.qualitylogic.openadr.core.signal.SignalPayloadType;
import com.qualitylogic.openadr.core.signal.OadrDistributeEventType.OadrEvent;
import com.qualitylogic.openadr.core.util.Clone;
import com.qualitylogic.openadr.core.util.OadrUtil;
import com.qualitylogic.openadr.core.util.PropertiesFileReader;

public class RequestReregistrationSignalHelper {

	public static OadrRequestReregistrationType loadOadrRequestReregistrationType() {

		return loadOadrRequestReregistrationType("oadrRequestReregistration.xml");
	}

	public static OadrRequestReregistrationType loadOadrRequestReregistrationType(String file) {

		OadrRequestReregistrationType oadrRequestReregistrationType = null;
		try {

			oadrRequestReregistrationType = (OadrRequestReregistrationType) new SchemaHelper()
			.loadTestDataXMLFile(file).getOadrRequestReregistration();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return oadrRequestReregistrationType;
	}

	@SuppressWarnings("unchecked")
	public static OadrRequestReregistrationType createOadrRequestReregistrationTypeFromString(
			String data) {
		OadrRequestReregistrationType oadrRequestReregistrationType = null;
		if (data == null || data.length() < 1)
			return null;

		try {

			JAXBContext testcontext = JAXBContext
					.newInstance("com.qualitylogic.openadr.core.signal");
			InputStream is = new ByteArrayInputStream(data.getBytes("UTF-8"));
			Unmarshaller unmarshall = testcontext.createUnmarshaller();
			OadrSignedObject oadrSignedObject = ((OadrPayload)unmarshall.unmarshal(is)).getOadrSignedObject();
			oadrRequestReregistrationType = oadrSignedObject.getOadrRequestReregistration();

			
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return oadrRequestReregistrationType;
	}

}