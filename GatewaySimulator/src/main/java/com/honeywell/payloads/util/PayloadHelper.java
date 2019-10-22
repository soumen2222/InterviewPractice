package com.honeywell.payloads.util;


import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import com.honeywell.openadr.core.signal.ObjectFactory;


public class PayloadHelper {
	private static JAXBContext jc = null;

	private static ObjectFactory factory = null;

	private static long UID = 82595127686589L;

	public static JAXBContext getJAXBContext() throws JAXBException {
		if (jc == null) {
			jc = JAXBContext
					.newInstance(
							com.honeywell.openadr.core.signal.OadrPayload.class,
							com.honeywell.openadr.core.signal.greenbutton.ObjectFactory.class) ;	
			}
		return jc;
	}

	public static Marshaller getMarshaller() throws JAXBException {
		return getJAXBContext().createMarshaller();

	}

	public static Unmarshaller getUnmarshaller() throws JAXBException {
		return getJAXBContext().createUnmarshaller();
	}

	public static ObjectFactory getObjectFactory() throws JAXBException {
		if (factory == null) {
			factory = new ObjectFactory();
		}
		return factory;
	}

	public static String getUID() {
		return "" + UID++;
	}
}
