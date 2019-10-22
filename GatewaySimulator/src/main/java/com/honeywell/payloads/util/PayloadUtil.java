package com.honeywell.payloads.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;

import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.jboss.logging.Logger;

import com.honeywell.openadr.core.signal.OadrCancelOptType;
import com.honeywell.openadr.core.signal.OadrCancelPartyRegistrationType;
import com.honeywell.openadr.core.signal.OadrCancelReportType;
import com.honeywell.openadr.core.signal.OadrCanceledOptType;
import com.honeywell.openadr.core.signal.OadrCanceledPartyRegistrationType;
import com.honeywell.openadr.core.signal.OadrCreateOptType;
import com.honeywell.openadr.core.signal.OadrCreatePartyRegistrationType;
import com.honeywell.openadr.core.signal.OadrCreateReportType;
import com.honeywell.openadr.core.signal.OadrCreatedEventType;
import com.honeywell.openadr.core.signal.OadrCreatedOptType;
import com.honeywell.openadr.core.signal.OadrCreatedPartyRegistrationType;
import com.honeywell.openadr.core.signal.OadrCreatedReportType;
import com.honeywell.openadr.core.signal.OadrDistributeEventType;
import com.honeywell.openadr.core.signal.OadrPayload;
import com.honeywell.openadr.core.signal.OadrPollType;
import com.honeywell.openadr.core.signal.OadrQueryRegistrationType;
import com.honeywell.openadr.core.signal.OadrRegisterReportType;
import com.honeywell.openadr.core.signal.OadrRegisteredReportType;
import com.honeywell.openadr.core.signal.OadrRequestEventType;
import com.honeywell.openadr.core.signal.OadrRequestReregistrationType;
import com.honeywell.openadr.core.signal.OadrResponseType;
import com.honeywell.openadr.core.signal.OadrSignedObject;
import com.honeywell.openadr.core.signal.OadrUpdateReportType;
import com.honeywell.openadr.core.signal.OadrUpdatedReportType;


/**
 * Helper class
 * @author sunil
 *
 */
public class PayloadUtil {
	
	private static Logger log = Logger.getLogger(PayloadUtil.class);
	
	private static final String LOG_PAYLOAD = "com.honeywell.oadr2b.log.payload";
	
	public static String convertStreamToString(InputStream is)
			throws IOException {
		if (is != null) {
			Writer writer = new StringWriter();

			char[] buffer = new char[1024];
			try {
				Reader reader = new BufferedReader(new InputStreamReader(is,
						"UTF-8"));
				int n;
				while ((n = reader.read(buffer)) != -1) {
					writer.write(buffer, 0, n);
				}
			} finally {
				is.close();
			}
			return writer.toString();
		} else {
			return "";
		}
	}

	public static void printResponse(InputStream i) throws JAXBException, IOException {
		String val = System.getProperty(LOG_PAYLOAD);
		if (!"false".equalsIgnoreCase(val)) {
			String p = convertStreamToString(i);
			log.info("********* RESPONSE PAYLOAD ***********");
			log.info(p);
			log.info("\n######################################\n");
		}
	}

	public static void printResponse(Object o) throws JAXBException {
		String val = System.getProperty(LOG_PAYLOAD);
		if (!"false".equalsIgnoreCase(val)) {
			Marshaller marshaller = PayloadHelper.getMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			log.info("********* RESPONSE PAYLOAD ***********");
	
			marshallToStdOut(o, marshaller);
			log.info("\n######################################\n");
		}
	}

	
	private static void marshallToStdOut(Object o, Marshaller marshaller) throws JAXBException{
		
		
		
		
		OadrPayload oadrPayload = new OadrPayload();
		OadrSignedObject signedObject = new OadrSignedObject();
		
		
		if(o instanceof OadrDistributeEventType){
			signedObject.setOadrDistributeEvent((OadrDistributeEventType)o);
		}else if(o instanceof OadrRequestEventType){
			signedObject.setOadrRequestEvent((OadrRequestEventType)o);

		}else if(o instanceof OadrCreatedEventType){
			signedObject.setOadrCreatedEvent((OadrCreatedEventType)o);

		}else if(o instanceof OadrResponseType){
			signedObject.setOadrResponse((OadrResponseType)o);

		}else if(o instanceof OadrQueryRegistrationType){
			signedObject.setOadrQueryRegistration((OadrQueryRegistrationType)o);

		}else if(o instanceof OadrCreatedPartyRegistrationType){
			signedObject.setOadrCreatedPartyRegistration((OadrCreatedPartyRegistrationType)o);

		}else if(o instanceof OadrCreatePartyRegistrationType){
			signedObject.setOadrCreatePartyRegistration((OadrCreatePartyRegistrationType)o);

		}else if(o instanceof OadrCancelPartyRegistrationType){
			signedObject.setOadrCancelPartyRegistration((OadrCancelPartyRegistrationType)o);

		}else if(o instanceof OadrCanceledPartyRegistrationType){
			signedObject.setOadrCanceledPartyRegistration((OadrCanceledPartyRegistrationType)o);

		}else if(o instanceof OadrPollType){
			signedObject.setOadrPoll((OadrPollType)o);

		}else if(o instanceof OadrRegisterReportType){
			signedObject.setOadrRegisterReport((OadrRegisterReportType)o);

		}else if(o instanceof OadrRegisteredReportType){
			signedObject.setOadrRegisteredReport((OadrRegisteredReportType)o);

		}else if(o instanceof OadrUpdateReportType){
			signedObject.setOadrUpdateReport((OadrUpdateReportType)o);

		}else if(o instanceof OadrUpdatedReportType){
			signedObject.setOadrUpdatedReport((OadrUpdatedReportType)o);

		}else if(o instanceof OadrCreateReportType){
			signedObject.setOadrCreateReport((OadrCreateReportType)o);

		}else if(o instanceof OadrCreateOptType){
			signedObject.setOadrCreateOpt((OadrCreateOptType)o);

		}else if(o instanceof OadrCreatedOptType){
			signedObject.setOadrCreatedOpt((OadrCreatedOptType)o);

		}else if(o instanceof OadrCancelOptType){
			signedObject.setOadrCancelOpt((OadrCancelOptType)o);

		}else if(o instanceof OadrCanceledOptType){
			signedObject.setOadrCanceledOpt((OadrCanceledOptType)o);

		}else if(o instanceof OadrCreatedReportType){
			signedObject.setOadrCreatedReport((OadrCreatedReportType)o);

		}else if (o instanceof OadrCancelReportType) {
			signedObject.setOadrCancelReport((OadrCancelReportType) o);

		}else if(o instanceof OadrRequestReregistrationType){
			signedObject.setOadrRequestReregistration((OadrRequestReregistrationType)o);

		}else if(o instanceof OadrPayload){
			oadrPayload = (OadrPayload)o;
			signedObject = oadrPayload.getOadrSignedObject();

		}else if(o instanceof OadrSignedObject){
			signedObject = ((OadrSignedObject)o);

		}
	
		
		oadrPayload.setOadrSignedObject(signedObject );
		
		StringWriter writer = new StringWriter();
		marshaller.marshal(oadrPayload, writer);	
		log.info(writer.toString());
		

		
		
	}
	
	public static void printRequest(Object o) throws JAXBException {
		String val = System.getProperty(LOG_PAYLOAD);
		if (!"false".equalsIgnoreCase(val)) {
			Marshaller marshaller = PayloadHelper.getMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			log.info("********* REQUEST PAYLOAD ***********");
			marshallToStdOut(o, marshaller);
			log.info("\n######################################\n");
		}
	}
	
	public static boolean isExpected(Object o, Class<?> expected){
		
		Class tomatch = OadrCancelReportType.class ;
		
		OadrSignedObject signedObject = ((OadrPayload) o)
				.getOadrSignedObject();
		if (signedObject != null) {
			o = signedObject.getOadrCanceledOpt();
			if (o == null) {
				o = signedObject.getOadrCanceledPartyRegistration();
			}
			if (o == null) {
				o = signedObject.getOadrCanceledReport();
			}
			if (o == null) {
				o = signedObject.getOadrCancelOpt();
			}
			if (o == null) {
				o = signedObject.getOadrCancelPartyRegistration();
			}
			if (o == null) {
				o = signedObject.getOadrCancelReport();
			}
			if (o == null) {
				o = signedObject.getOadrCreatedEvent();
			}
			if (o == null) {
				o = signedObject.getOadrCreatedOpt();
			}
			if (o == null) {
				o = signedObject.getOadrCreatedPartyRegistration();
			}
			if (o == null) {
				o = signedObject.getOadrCreatedReport();
			}
			if (o == null) {
				o = signedObject.getOadrCreateOpt();
			}
			if (o == null) {
				o = signedObject.getOadrCreatePartyRegistration();
			}
			if (o == null) {
				o = signedObject.getOadrCreateReport();
			}
			if (o == null) {
				o = signedObject.getOadrDistributeEvent();
			}
			if (o == null) {
				o = signedObject.getOadrPoll();
			}
			if (o == null) {
				o = signedObject.getOadrQueryRegistration();
			}
			if (o == null) {
				o = signedObject.getOadrRegisteredReport();
			}
			if (o == null) {
				o = signedObject.getOadrRegisterReport();
			}
			if (o == null) {
				o = signedObject.getOadrRequestEvent();
			}
			if (o == null) {
				o = signedObject.getOadrRequestReregistration();
			}
			if (o == null) {
				o = signedObject.getOadrResponse();
			}
			if (o == null) {
				o = signedObject.getOadrUpdatedReport();
			}
			if (o == null) {
				o = signedObject.getOadrUpdateReport();
			}
			if (o == null) {
				o = signedObject.getOadrCanceledReport();
			}
		}

		
	
	if (o instanceof OadrDistributeEventType) {
			tomatch = OadrDistributeEventType.class;		
	} else if (o instanceof OadrCreateReportType) {
		tomatch = OadrCreateReportType.class;
		
	}else if (o instanceof OadrRegisterReportType) {		
		tomatch = OadrRegisterReportType.class;
	}else if (o instanceof OadrCancelReportType) {
		tomatch = OadrCancelReportType.class;
		
	}else if (o instanceof OadrUpdateReportType) {
		tomatch = OadrUpdateReportType.class;		
	}else if (o instanceof OadrCancelPartyRegistrationType) {
		tomatch = OadrCancelPartyRegistrationType.class;
		
	}else if (o instanceof OadrRequestReregistrationType) {
		tomatch = OadrRequestReregistrationType.class;		
	}	
	
	if(tomatch.equals(expected))
		{return true;}
	else 
	{return false;}
		
	}
	

}
