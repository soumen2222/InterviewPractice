package com.qualitylogic.openadr.core.ven.channel;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import com.qualitylogic.openadr.core.channel.Handler;
import com.qualitylogic.openadr.core.signal.OadrCancelPartyRegistrationType;
import com.qualitylogic.openadr.core.signal.OadrCancelReportType;
import com.qualitylogic.openadr.core.signal.OadrCreateReportType;
import com.qualitylogic.openadr.core.signal.OadrCreatedReportType;
import com.qualitylogic.openadr.core.signal.OadrDistributeEventType;
import com.qualitylogic.openadr.core.signal.OadrPayload;
import com.qualitylogic.openadr.core.signal.OadrRegisterReportType;
import com.qualitylogic.openadr.core.signal.OadrRequestReregistrationType;
import com.qualitylogic.openadr.core.signal.OadrSignedObject;
import com.qualitylogic.openadr.core.signal.OadrUpdateReportType;
import com.qualitylogic.openadr.core.signal.helper.SchemaHelper;

public class ResourceHandler implements Handler {
	
	private final EiReportVENHandler eiReportVenHandler = new EiReportVENHandler();
	
	private final VENHandler venHandler = new VENHandler();
	
	private final EiRegisterPartyVENHandler eiRegisterPartyVENHandler = new EiRegisterPartyVENHandler();

	@Override
	public String handle(final String data) {
		OadrSignedObject oadrSignedObject = getOadrSignedObject(data);
		Object payload = new SchemaHelper().getPayloadFromOadrSignedObject(oadrSignedObject);
		Class<? extends Object> payloadClass = payload.getClass();
		
		Handler handler = getHandler(payloadClass);
		return handler.handle(data);
	}

	private Handler getHandler(Class<? extends Object> payloadClass) {
		Handler handler = null;
		if ((payloadClass == OadrRegisterReportType.class) || (payloadClass == OadrCreateReportType.class) || (payloadClass == OadrUpdateReportType.class) || (payloadClass == OadrCancelReportType.class) || (payloadClass == OadrCreatedReportType.class)) {
			handler = eiReportVenHandler;
		} else if (payloadClass == OadrDistributeEventType.class) {
			handler = venHandler;
		} else if ((payloadClass == OadrCancelPartyRegistrationType.class) || (payloadClass == OadrRequestReregistrationType.class)) {
			handler = eiRegisterPartyVENHandler;
		} else {
			throw new IllegalStateException("Unsupported testClass=" + payloadClass);
		}
		return handler;
	}

	private OadrSignedObject getOadrSignedObject(final String data) {
		OadrSignedObject oadrSignedObject;
		try {
			JAXBContext testcontext = JAXBContext.newInstance("com.qualitylogic.openadr.core.signal");
			InputStream is = new ByteArrayInputStream(data.getBytes("UTF-8"));
			Unmarshaller unmarshall = testcontext.createUnmarshaller();
			oadrSignedObject = ((OadrPayload) unmarshall.unmarshal(is)).getOadrSignedObject();
		} catch (UnsupportedEncodingException | JAXBException e) {
			throw new IllegalStateException("Failed to unmarshal string data", e);
		}
		
		return oadrSignedObject;
	}
}
