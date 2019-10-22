package com.qualitylogic.openadr.core.signal.helper;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.List;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import com.mysql.jdbc.Util;
import com.qualitylogic.openadr.core.common.TestSession;
import com.qualitylogic.openadr.core.signal.EiCreatedEvent;
import com.qualitylogic.openadr.core.signal.EiRequestEvent;
import com.qualitylogic.openadr.core.signal.EiResponseType;
import com.qualitylogic.openadr.core.signal.EventResponses;
import com.qualitylogic.openadr.core.signal.EventResponses.EventResponse;
import com.qualitylogic.openadr.core.signal.OadrCancelOptType;
import com.qualitylogic.openadr.core.signal.OadrCancelPartyRegistrationType;
import com.qualitylogic.openadr.core.signal.OadrCancelReportType;
import com.qualitylogic.openadr.core.signal.OadrCanceledOptType;
import com.qualitylogic.openadr.core.signal.OadrCanceledPartyRegistrationType;
import com.qualitylogic.openadr.core.signal.OadrCanceledReportType;
import com.qualitylogic.openadr.core.signal.OadrCreateOptType;
import com.qualitylogic.openadr.core.signal.OadrCreatePartyRegistrationType;
import com.qualitylogic.openadr.core.signal.OadrCreateReportType;
import com.qualitylogic.openadr.core.signal.OadrCreatedEventType;
import com.qualitylogic.openadr.core.signal.OadrCreatedOptType;
import com.qualitylogic.openadr.core.signal.OadrCreatedPartyRegistrationType;
import com.qualitylogic.openadr.core.signal.OadrCreatedReportType;
import com.qualitylogic.openadr.core.signal.OadrDistributeEventType;
import com.qualitylogic.openadr.core.signal.OadrPayload;
import com.qualitylogic.openadr.core.signal.OadrPollType;
import com.qualitylogic.openadr.core.signal.OadrQueryRegistrationType;
import com.qualitylogic.openadr.core.signal.OadrRegisterReportType;
import com.qualitylogic.openadr.core.signal.OadrRegisteredReportType;
import com.qualitylogic.openadr.core.signal.OadrRequestEventType;
import com.qualitylogic.openadr.core.signal.OadrRequestReregistrationType;
import com.qualitylogic.openadr.core.signal.OadrResponseType;
import com.qualitylogic.openadr.core.signal.OadrSignedObject;
import com.qualitylogic.openadr.core.signal.OadrUpdateReportType;
import com.qualitylogic.openadr.core.signal.OadrUpdatedReportType;
import com.qualitylogic.openadr.core.signal.ObjectFactory;
import com.qualitylogic.openadr.core.signal.OptTypeType;
import com.qualitylogic.openadr.core.signal.QualifiedEventIDType;
import com.qualitylogic.openadr.core.util.OadrUtil;
import com.qualitylogic.openadr.core.util.PropertiesFileReader;

public class SchemaHelper {
	static DatatypeFactory DF;
	static Schema schema = null;

	public static synchronized Schema getSchema() {

		PropertiesFileReader propertiesFileReader = new PropertiesFileReader();

		String schemaFile = propertiesFileReader.get("SchemaFile");
		if (schemaFile.trim().length() < 1) {
			String msg = "*** WARNING: SchemaFile not defined in the openadrconfig.properties file ***";
			TestSession.setValidationErrorFound(true);
			System.out.println(msg);
			LogHelper.addTrace(msg);

			return null;
		}

		if (schema != null)
			return schema;
		String schemaName = "com/qualitylogic/openadr/core/schema/"
				+ schemaFile;

		SchemaFactory sf = SchemaFactory
				.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
		try {

			URL resource = null;

			resource = Thread.currentThread().getContextClassLoader()
					.getResource(schemaName);
			schema = sf.newSchema(resource);

		} catch (Exception se) {
			String msg = "*** WARNING: Unable to load the SchemaFile "
					+ schemaFile
					+ " defined in the openadrconfig.properties file *** ";
			System.out.println(msg);
			System.out.println(se.getMessage());

			LogHelper.addTrace(msg);
			TestSession.setValidationErrorFound(true);
		}

		return schema;

	}

	public static String getRequestEventAsString(
			OadrRequestEventType OadrRequestEventType) throws JAXBException {
		if (OadrRequestEventType == null)
			return "";

		//JAXBContext context = JAXBContext.newInstance(OadrRequestEventType.class);
		//String stroadrRequestEvent = SchemaHelper.asString(context,
			//	OadrRequestEventType);

		OadrPayload oadrPayload = createOadrPayload();
		oadrPayload.getOadrSignedObject().setOadrRequestEvent(OadrRequestEventType);
		
		
	 return getoadrPayloadObjectAsString(oadrPayload);
		
	}

	public static String getDistributeEventAsString(
			OadrDistributeEventType distributeEvent) throws JAXBException {
		if (distributeEvent == null)
			return "";

		JAXBContext context = JAXBContext
				.newInstance(OadrDistributeEventType.class);

		OadrPayload oadrPayload = createOadrPayload();
		oadrPayload.getOadrSignedObject().setOadrDistributeEvent(distributeEvent);
		
		return getoadrPayloadObjectAsString(oadrPayload);
	}

	public static String getCreatedEventAsString(
			OadrCreatedEventType OadrCreatedEventType) throws JAXBException {
		if (OadrCreatedEventType == null)
			return "";

/*		JAXBContext context = JAXBContext.newInstance(OadrCreatedEventType.class);
		String strOadrCreatedEvent = SchemaHelper.asString(context,
				OadrCreatedEventType);
*/

		OadrPayload oadrPayload = createOadrPayload();
		oadrPayload.getOadrSignedObject().setOadrCreatedEvent(OadrCreatedEventType);

		return getoadrPayloadObjectAsString(oadrPayload);

	}

	public static String getOadrCreateOptAsString(
			OadrCreateOptType oadrCreateOpt) throws JAXBException {
		if (oadrCreateOpt == null)
			return "";

/*		JAXBContext context = JAXBContext
				.newInstance(OadrCreateOptType.class);
		String strOadrCreateOptType = SchemaHelper.asString(context,
				oadrCreateOpt);
*/

		OadrPayload oadrPayload = createOadrPayload();
		oadrPayload.getOadrSignedObject().setOadrCreateOpt(oadrCreateOpt);
		
		return getoadrPayloadObjectAsString(oadrPayload);
	}
	
	public static String getCreatePartyRegistrationAsString(OadrCreatePartyRegistrationType createPartyRegistration){

		if (createPartyRegistration == null)
			return "";

			ObjectFactory objectFactory = new ObjectFactory();
			OadrPayload oadrPayload = objectFactory.createOadrPayload();

			OadrSignedObject oadrSignedObject = objectFactory.createOadrSignedObject();
			oadrPayload.setOadrSignedObject(oadrSignedObject);
			
			oadrSignedObject.setOadrCreatePartyRegistration(createPartyRegistration);
					
			/*
				JAXBContext context = JAXBContext
						.newInstance(OadrCreatePartyRegistrationType.class);
				String strOadrCreatePartyRegistration = SchemaHelper.asString(context,
						createPartyRegistration);
			 */
			return getoadrPayloadObjectAsString(oadrPayload);
	}
	
	public static String getoadrPayloadObjectAsString(OadrPayload oadrPayload){

		JAXBContext context;
		String strOadrSignedObject = "";
		try {
			context = JAXBContext
					.newInstance(OadrPayload.class);
			
			strOadrSignedObject = SchemaHelper.asString(context,
					oadrPayload);
			
		} catch (JAXBException e) {
			
			LogHelper.addTrace("Unable to create OadrPayload");
			LogHelper.addTrace(e.getMessage());
			
			e.printStackTrace();
		}
	
		return strOadrSignedObject;
	}

	public static String getOadrQueryRegistrationTypeAsString(
			OadrQueryRegistrationType oadrQueryRegistrationType) throws JAXBException {
		if (oadrQueryRegistrationType == null)
			return "";

		OadrPayload oadrPayload = createOadrPayload();
		
		oadrPayload.getOadrSignedObject().setOadrQueryRegistration(oadrQueryRegistrationType);
		
/*
		JAXBContext context = JAXBContext
				.newInstance(OadrQueryRegistrationType.class);
		String strOadrQueryRegistrationType = SchemaHelper.asString(context,
				oadrQueryRegistrationType);

*/		return getoadrPayloadObjectAsString(oadrPayload);
	}
		
	public static OadrPayload createOadrPayload(){
		ObjectFactory objectFactory = new ObjectFactory();
		OadrPayload oadrPayload = objectFactory.createOadrPayload();
		OadrSignedObject oadrSignedObject = objectFactory.createOadrSignedObject();
		oadrPayload.setOadrSignedObject(oadrSignedObject);
		
		return oadrPayload;
		
	}
	public static String getCreatedPartyRegistrationAsString(
			OadrCreatedPartyRegistrationType createdPartyRegistration) throws JAXBException {
		if (createdPartyRegistration == null)
			return "";
		OadrPayload oadrPayload = createOadrPayload();
		oadrPayload.getOadrSignedObject().setOadrCreatedPartyRegistration(createdPartyRegistration);
		
		/*
		JAXBContext context = JAXBContext
				.newInstance(OadrCreatedPartyRegistrationType.class);
		String strOadrCreatedPartyRegistration = SchemaHelper.asString(context,
				createdPartyRegistration);
		 */

		return getoadrPayloadObjectAsString(oadrPayload);
	}
	
	
	public static String getCancelPartyRegistrationAsString(
			OadrCancelPartyRegistrationType cancelPartyRegistrationType) throws JAXBException {
		if (cancelPartyRegistrationType == null)
			return "";
		OadrPayload oadrPayload = createOadrPayload();
		oadrPayload.getOadrSignedObject().setOadrCancelPartyRegistration(cancelPartyRegistrationType);
		
		/*
			JAXBContext context = JAXBContext
					.newInstance(OadrCancelPartyRegistrationType.class);
			String strOadrCancelPartyRegistrationType = SchemaHelper.asString(context,
					cancelPartyRegistrationType);
		 */

		return getoadrPayloadObjectAsString(oadrPayload);

	}

	public static String getCanceledPartyRegistrationAsString(
			OadrCanceledPartyRegistrationType oadrCanceledPartyRegistrationType) throws JAXBException {
		if (oadrCanceledPartyRegistrationType == null)
			return "";

		OadrPayload oadrPayload = createOadrPayload();
		oadrPayload.getOadrSignedObject().setOadrCanceledPartyRegistration(oadrCanceledPartyRegistrationType);
		
		
		/*
		JAXBContext context = JAXBContext
				.newInstance(OadrCanceledPartyRegistrationType.class);
		String strOadrCanceledPartyRegistrationType = SchemaHelper.asString(context,
				oadrCanceledPartyRegistrationType);*/

		return getoadrPayloadObjectAsString(oadrPayload);
	}
	
	
	public static String getRegisteredReportTypeAsString(
			OadrRegisteredReportType oadrRegisteredReportType) throws JAXBException {
		if (oadrRegisteredReportType == null)
			return "";
		
		OadrPayload oadrPayload = createOadrPayload();
		oadrPayload.getOadrSignedObject().setOadrRegisteredReport(oadrRegisteredReportType);
						
		/*
		JAXBContext context = JAXBContext
				.newInstance(OadrRegisteredReportType.class);
		String strOadrRegisteredReportType = SchemaHelper.asString(context,
				oadrRegisteredReportType);
		 */
		return getoadrPayloadObjectAsString(oadrPayload);

	}
	
	
	public static String getRegisterReportTypeAsString(
			OadrRegisterReportType oadrRegisterReportType) throws JAXBException {
		if (oadrRegisterReportType == null)
			return "";

		OadrPayload oadrPayload = createOadrPayload();
		oadrPayload.getOadrSignedObject().setOadrRegisterReport(oadrRegisterReportType);
		
				
/*		JAXBContext context = JAXBContext
				.newInstance(OadrRegisterReportType.class);
		String strOadrRegisterReportType = SchemaHelper.asString(context,
				oadrRegisterReportType);
*/
		return getoadrPayloadObjectAsString(oadrPayload);
	}
		
	public static String getCreateReportTypeAsString(
			OadrCreateReportType oadrCreateReportType) throws JAXBException {
		if (oadrCreateReportType == null)
			return "";

		OadrPayload oadrPayload = createOadrPayload();
		oadrPayload.getOadrSignedObject().setOadrCreateReport(oadrCreateReportType);
		
		/*JAXBContext context = JAXBContext
				.newInstance(OadrCreateReportType.class);
		String strOadrCreateReportType = SchemaHelper.asString(context,
				oadrCreateReportType);*/

		return getoadrPayloadObjectAsString(oadrPayload);

	}
	
	public static String getCreatedReportTypeAsString(
			OadrCreatedReportType oadrCreatedReportType) throws JAXBException {
		if (oadrCreatedReportType == null)
			return "";

		OadrPayload oadrPayload = createOadrPayload();
		oadrPayload.getOadrSignedObject().setOadrCreatedReport(oadrCreatedReportType);

/*		JAXBContext context = JAXBContext
				.newInstance(OadrCreatedReportType.class);
		String strOadrCreatedReportType = SchemaHelper.asString(context,
				oadrCreatedReportType);
*/
		return getoadrPayloadObjectAsString(oadrPayload);
	}

	public static String getUpdateReportTypeAsString(
			OadrUpdateReportType oadrUpdateReportType) throws JAXBException {
		if (oadrUpdateReportType == null)
			return "";

		OadrPayload oadrPayload = createOadrPayload();
		oadrPayload.getOadrSignedObject().setOadrUpdateReport(oadrUpdateReportType);

/*		JAXBContext context = JAXBContext
				.newInstance(OadrUpdateReportType.class);
		String strOadrUpdateReportType = SchemaHelper.asString(context,
				oadrUpdateReportType);
*/
		return getoadrPayloadObjectAsString(oadrPayload);
	}

	public static String getUpdateReportTypeWithSignedIdAsString(
			OadrUpdateReportType oadrUpdateReportType) throws JAXBException {
		if (oadrUpdateReportType == null)
			return "";

		OadrPayload oadrPayload = createOadrPayload();
		OadrSignedObject signedObject = oadrPayload.getOadrSignedObject();
		signedObject.setOadrUpdateReport(oadrUpdateReportType);
		signedObject.setId("A" + String.valueOf(System.currentTimeMillis()));

		return getoadrPayloadObjectAsString(oadrPayload);
	}
	
	public static String getOadrPollTypeAsString(
			OadrPollType oadrPollType) throws JAXBException {
		if (oadrPollType == null)
			return "";

		OadrPayload oadrPayload = createOadrPayload();
		oadrPayload.getOadrSignedObject().setOadrPoll(oadrPollType);

/*		JAXBContext context = JAXBContext
				.newInstance(OadrUpdateReportType.class);
		String strOadrUpdateReportType = SchemaHelper.asString(context,
				oadrUpdateReportType);
*/
		return getoadrPayloadObjectAsString(oadrPayload);
	}
	
	
	public static String getUpdatedReportTypeAsString(
			OadrUpdatedReportType oadrUpdatedReportType) throws JAXBException {
		if (oadrUpdatedReportType == null)
			return "";

		OadrPayload oadrPayload = createOadrPayload();
		oadrPayload.getOadrSignedObject().setOadrUpdatedReport(oadrUpdatedReportType);

	/*	JAXBContext context = JAXBContext
				.newInstance(OadrUpdatedReportType.class);
		String strOadrUpdatedReportType = SchemaHelper.asString(context,
				oadrUpdatedReportType);*/

		return getoadrPayloadObjectAsString(oadrPayload);
	}
	
	public static String getUpdatedReportTypeAsString(
			OadrUpdatedReportType oadrUpdatedReportType, String signedId) throws JAXBException {
		if (oadrUpdatedReportType == null)
			return "";

		OadrPayload oadrPayload = createOadrPayload();
		OadrSignedObject signedObject = oadrPayload.getOadrSignedObject();
		signedObject.setOadrUpdatedReport(oadrUpdatedReportType);
		signedObject.setId(signedId);

		return getoadrPayloadObjectAsString(oadrPayload);
	}
	
	public static String getCancelReportTypeAsString(
			OadrCancelReportType oadrCancelReportType) throws JAXBException {
		if (oadrCancelReportType == null)
			return "";

		OadrPayload oadrPayload = createOadrPayload();
		oadrPayload.getOadrSignedObject().setOadrCancelReport(oadrCancelReportType);
				
/*		JAXBContext context = JAXBContext
				.newInstance(OadrCancelReportType.class);
		String strOadrCancelReportType = SchemaHelper.asString(context,
				oadrCancelReportType);*/

		return getoadrPayloadObjectAsString(oadrPayload);
	}

	public static String getCanceledReportTypeAsString(
			OadrCanceledReportType oadrCanceledReportType) throws JAXBException {
		if (oadrCanceledReportType == null)
			return "";

		OadrPayload oadrPayload = createOadrPayload();
		oadrPayload.getOadrSignedObject().setOadrCanceledReport(oadrCanceledReportType);
				
	/*	JAXBContext context = JAXBContext
				.newInstance(OadrCanceledReportType.class);
		String strOadrCanceledReportType = SchemaHelper.asString(context,
				oadrCanceledReportType);
*/
		return getoadrPayloadObjectAsString(oadrPayload);
	}
	

	public static String getOadrResponseAsString(
			OadrResponseType oadrResponseType) throws JAXBException {
		if (oadrResponseType == null)
			return "";
		

		OadrPayload oadrPayload = createOadrPayload();
		oadrPayload.getOadrSignedObject().setOadrResponse(oadrResponseType);
						

	/*	JAXBContext context = JAXBContext
				.newInstance(OadrResponseType.class);
		String stroadrResponseType = SchemaHelper.asString(context,
				oadrResponseType);*/

		return getoadrPayloadObjectAsString(oadrPayload);
	}

	public static String getOadrRequestReregistrationTypeAsString(
			OadrRequestReregistrationType oadrRequestReregistrationType) throws JAXBException {
		if (oadrRequestReregistrationType == null)
			return "";
		
		OadrPayload oadrPayload = createOadrPayload();
		oadrPayload.getOadrSignedObject().setOadrRequestReregistration(oadrRequestReregistrationType);
				
/*		JAXBContext context = JAXBContext
				.newInstance(OadrRequestReregistrationType.class);
		String strOadrRequestReregistrationType = SchemaHelper.asString(context,
				oadrRequestReregistrationType);*/

		return getoadrPayloadObjectAsString(oadrPayload);
	}	

	public static String getOadrCancelOptTypeAsString(
			OadrCancelOptType oadrCancelOptType) throws JAXBException {
		if (oadrCancelOptType == null)
			return "";
		OadrPayload oadrPayload = createOadrPayload();
		oadrPayload.getOadrSignedObject().setOadrCancelOpt(oadrCancelOptType);				


/*		JAXBContext context = JAXBContext
				.newInstance(OadrCancelOptType.class);
		String strOadrCancelOptType = SchemaHelper.asString(context,
				oadrCancelOptType);
*/
		return getoadrPayloadObjectAsString(oadrPayload);
		
	}	
	
	public static String getOadrCanceledOptTypeAsString(
			OadrCanceledOptType oadrCanceledOptType) throws JAXBException {
		if (oadrCanceledOptType == null)
			return "";

		OadrPayload oadrPayload = createOadrPayload();
		oadrPayload.getOadrSignedObject().setOadrCanceledOpt(oadrCanceledOptType);
		
		/*
		JAXBContext context = JAXBContext
				.newInstance(OadrCancelOptType.class);
		String strOadrCanceledOptType = SchemaHelper.asString(context,
				oadrCanceledOptType);
*/
		return getoadrPayloadObjectAsString(oadrPayload);
		
	}	
	public static String asString(JAXBContext pContext, Object pObject)
			throws JAXBException {

		java.io.StringWriter sw = new StringWriter();

		Marshaller marshaller = pContext.createMarshaller();
		marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
		//marshaller.marshal(pObject, sw);
/*
		if(pObject.getClass()==OadrRequestEventType.class){
		marshaller.marshal(new JAXBElement(
				new QName("uri", "local"),
				pObject.getClass(), pObject), sw);
		}else{
			*/
			marshaller.marshal(pObject, sw);
			
		//}
		//marshaller.marshal( new JAXBElement(  new QName("uri","local"), OadrRequestEventType.class, pObject ));
		
		//marshaller.marshal( new JAXBElement(pContext, pObject ));
		
		//marshaller.marshal();
		
		return sw.toString();
	}

	public static OadrCreatedEventType sample_createOADRCreatedEvent() {
		com.qualitylogic.openadr.core.signal.ObjectFactory oadr_20b = new com.qualitylogic.openadr.core.signal.ObjectFactory();
		com.qualitylogic.openadr.core.signal.ObjectFactory eiObjectFactory = new com.qualitylogic.openadr.core.signal.ObjectFactory();
		com.qualitylogic.openadr.core.signal.ObjectFactory ei20b = new com.qualitylogic.openadr.core.signal.ObjectFactory();

		OadrCreatedEventType OadrCreatedEventType = oadr_20b.createOadrCreatedEventType();
		EiCreatedEvent eiCreatedEvent = eiObjectFactory.createEiCreatedEvent();
		EiResponseType eiResponse = ei20b.createEiResponseType();
		EventResponses eventResponses = ei20b.createEventResponses();

		eiCreatedEvent.setEiResponse(eiResponse);
		eiCreatedEvent.setEventResponses(eventResponses);
		eiCreatedEvent.setVenID("Test VEN ID");

		OadrCreatedEventType.setEiCreatedEvent(eiCreatedEvent);

		eiResponse.setRequestID("testReqID");
		eiResponse.setResponseCode("000");
		eiResponse.setResponseDescription("testResponseDescription");

		List<EventResponse> eventResponseList = eventResponses
				.getEventResponse();

		EventResponse eachEventResponse0 = ei20b
				.createEventResponsesEventResponse();
		eachEventResponse0.setOptType(OptTypeType.OPT_OUT);
		eachEventResponse0.setResponseCode("000");
		eachEventResponse0.setResponseDescription("testResponseCode");
		eachEventResponse0.setRequestID("testRequestID");

		QualifiedEventIDType qualifiedEventIDType = ei20b
				.createQualifiedEventIDType();
		qualifiedEventIDType.setEventID("testEventID");
		qualifiedEventIDType.setModificationNumber(new Long(0));

		eachEventResponse0.setQualifiedEventID(qualifiedEventIDType);
		eventResponseList.add(eachEventResponse0);

		return OadrCreatedEventType;
	}

	public static OadrRequestEventType sample_createOadrRequestEvent() {
		
		com.qualitylogic.openadr.core.signal.ObjectFactory oadr_20b = new com.qualitylogic.openadr.core.signal.ObjectFactory();

		OadrRequestEventType OadrRequestEventType = oadr_20b.createOadrRequestEventType();
		EiRequestEvent eiRequestEvent = new EiRequestEvent();
		eiRequestEvent.setRequestID("test Req ID");
		// List<String> eventID= eiRequestEvent.getEventID();
		// eventID.add("test Event ID");

		// List<String> marketContext= eiRequestEvent.getMarketContext();
		// marketContext.add("test Market Context");

		eiRequestEvent.setVenID("test Ven ID");
		// eiRequestEvent.setEventFilter(EventFilterType.ALL);
		eiRequestEvent.setReplyLimit(new Long(1));
		// marketContextList.add("testMarket Context");
		OadrRequestEventType.setEiRequestEvent(eiRequestEvent);
		return OadrRequestEventType;
	}


	
	
	public Object getPayloadFromOadrSignedObject(OadrSignedObject oadrSignedObject){

		if(oadrSignedObject.getOadrDistributeEvent()!=null){
			return oadrSignedObject.getOadrDistributeEvent();
		}
		if(oadrSignedObject.getOadrCreatedEvent()!=null){
			return oadrSignedObject.getOadrCreatedEvent();
		}
		if(oadrSignedObject.getOadrRequestEvent()!=null){
			return oadrSignedObject.getOadrRequestEvent();
		}
		if(oadrSignedObject.getOadrResponse()!=null){
			return oadrSignedObject.getOadrResponse();
		}
	    
		if(oadrSignedObject.getOadrCancelOpt()!=null){
			return oadrSignedObject.getOadrCancelOpt();
		}
		if(oadrSignedObject.getOadrCanceledOpt()!=null){
			return oadrSignedObject.getOadrCanceledOpt();
		}
		if(oadrSignedObject.getOadrCreateOpt()!=null){
			return oadrSignedObject.getOadrCreateOpt();
		}
		if(oadrSignedObject.getOadrCreatedOpt()!=null){
			return oadrSignedObject.getOadrCreatedOpt();
		}
		if(oadrSignedObject.getOadrCancelReport()!=null){
			return oadrSignedObject.getOadrCancelReport();
		}
		if(oadrSignedObject.getOadrCanceledReport()!=null){
			return oadrSignedObject.getOadrCanceledReport();
		}
		
		if(oadrSignedObject.getOadrCreateReport()!=null){
			return oadrSignedObject.getOadrCreateReport();
		}
		
		if(oadrSignedObject.getOadrCreatedReport()!=null){
			return oadrSignedObject.getOadrCreatedReport();
		}
		if(oadrSignedObject.getOadrRegisterReport()!=null){
			return oadrSignedObject.getOadrRegisterReport();
		}
	    
		if(oadrSignedObject.getOadrRegisteredReport()!=null){
			return oadrSignedObject.getOadrRegisteredReport();
		}
		
		if(oadrSignedObject.getOadrUpdateReport()!=null){
			return oadrSignedObject.getOadrUpdateReport();
		}
		if(oadrSignedObject.getOadrUpdatedReport()!=null){
			return oadrSignedObject.getOadrUpdatedReport();
		}
		
		if(oadrSignedObject.getOadrCancelPartyRegistration()!=null){
			return oadrSignedObject.getOadrCancelPartyRegistration();
		}
		
		if(oadrSignedObject.getOadrCanceledPartyRegistration()!=null){
			return oadrSignedObject.getOadrCanceledPartyRegistration();
		}
		
		if(oadrSignedObject.getOadrCreatePartyRegistration()!=null){
			return oadrSignedObject.getOadrCreatePartyRegistration();
		}
		if(oadrSignedObject.getOadrCreatedPartyRegistration()!=null){
			return oadrSignedObject.getOadrCreatedPartyRegistration();
		}
		if(oadrSignedObject.getOadrRequestReregistration()!=null){
			return oadrSignedObject.getOadrRequestReregistration();
		}
		if(oadrSignedObject.getOadrQueryRegistration()!=null){
			return oadrSignedObject.getOadrQueryRegistration();
		}
		
		if(oadrSignedObject.getOadrPoll()!=null){
			return oadrSignedObject.getOadrPoll();
		}

		return null;
	}
	
	@SuppressWarnings("rawtypes")
	public static Class getObjectType(String data) throws JAXBException,
			UnsupportedEncodingException {
		if (data == null || data.length() < 1)
			return null;
		JAXBContext testcontext = JAXBContext
				.newInstance("com.qualitylogic.openadr.core.signal");
		InputStream is = new ByteArrayInputStream(data.getBytes("UTF-8"));
		Unmarshaller unmarshall = testcontext.createUnmarshaller();
		//new SchemaHelper();
		unmarshall.setSchema(SchemaHelper.getSchema());
		//Object test = unmarshall.unmarshal(is);
		
		OadrSignedObject oadrSignedObject  =((OadrPayload)unmarshall.unmarshal(is)).getOadrSignedObject();

		//OadrSignedObject oadrSignedObject  =((OadrSignedObject)unmarshall.unmarshal(is));

		if(oadrSignedObject.getOadrDistributeEvent()!=null){
			return OadrDistributeEventType.class;
		}
		if(oadrSignedObject.getOadrCreatedEvent()!=null){
			return OadrCreatedEventType.class;
		}
		if(oadrSignedObject.getOadrRequestEvent()!=null){
			return OadrRequestEventType.class;
		}
		if(oadrSignedObject.getOadrResponse()!=null){
			return OadrResponseType.class;
		}
	    
		if(oadrSignedObject.getOadrCancelOpt()!=null){
			return OadrCancelOptType.class;
		}
		if(oadrSignedObject.getOadrCanceledOpt()!=null){
			return OadrCanceledOptType.class;
		}
		if(oadrSignedObject.getOadrCreateOpt()!=null){
			return OadrCreateOptType.class;
		}
		if(oadrSignedObject.getOadrCreatedOpt()!=null){
			return OadrCreatedOptType.class;
		}
		if(oadrSignedObject.getOadrCancelReport()!=null){
			return OadrCancelReportType.class;
		}
		if(oadrSignedObject.getOadrCanceledReport()!=null){
			return OadrCanceledReportType.class;
		}
		
		if(oadrSignedObject.getOadrCreateReport()!=null){
			return OadrCreateReportType.class;
		}
		
		if(oadrSignedObject.getOadrCreatedReport()!=null){
			return OadrCreatedReportType.class;
		}
		if(oadrSignedObject.getOadrRegisterReport()!=null){
			return OadrRegisterReportType.class;
		}
	    
		if(oadrSignedObject.getOadrRegisteredReport()!=null){
			return OadrRegisteredReportType.class;
		}
		
		if(oadrSignedObject.getOadrUpdateReport()!=null){
			return OadrUpdateReportType.class;
		}
		if(oadrSignedObject.getOadrUpdatedReport()!=null){
			return OadrUpdatedReportType.class;
		}
		
		if(oadrSignedObject.getOadrCancelPartyRegistration()!=null){
			return OadrCancelPartyRegistrationType.class;
		}
		
		if(oadrSignedObject.getOadrCanceledPartyRegistration()!=null){
			return OadrCanceledPartyRegistrationType.class;
		}
		
		if(oadrSignedObject.getOadrCreatePartyRegistration()!=null){
			return OadrCreatePartyRegistrationType.class;
		}
		if(oadrSignedObject.getOadrCreatedPartyRegistration()!=null){
			return OadrCreatedPartyRegistrationType.class;
		}
		if(oadrSignedObject.getOadrRequestReregistration()!=null){
			return OadrRequestReregistrationType.class;
		}
		if(oadrSignedObject.getOadrQueryRegistration()!=null){
			return OadrQueryRegistrationType.class;
		}
		if(oadrSignedObject.getOadrPoll()!=null){
			return OadrPollType.class;
		}
	
		
		//Class cls = test.getClass();
		//@SuppressWarnings("unchecked")
		//Class cls = ((JAXBElement<Object>)test).getValue().getClass();
		return null;
	}

	public static String getEventTypeName(String data) throws JAXBException,
			UnsupportedEncodingException {
		if (data == null || data.length() < 1)
			return "";
		return OadrUtil.getClassName(getObjectType(data));
	}

	@SuppressWarnings("unchecked")
	public OadrSignedObject loadTestDataXMLFile(String fileName) {

		JAXBContext context;
		OadrSignedObject testDataFile = null;
		try {
			context = JAXBContext
					.newInstance("com.qualitylogic.openadr.core.signal");

			Unmarshaller unmarshall = context.createUnmarshaller();
			InputStream is = this
					.getClass()
					.getClassLoader()
					.getResourceAsStream(
							"com/qualitylogic/openadr/core/testdata/"
									+ fileName);

			testDataFile = ((OadrPayload)unmarshall.unmarshal(is)).getOadrSignedObject();
					
		} catch (Exception e) {
			e.printStackTrace();

			String exceptionString = Util.stackTraceToString(e);
			LogHelper.addTrace(exceptionString);
		}
		
		return testDataFile;
	}

	
	public static void main(String args[]){
		
		try {
			getSchema();
			System.out.println("Test1");
		}catch(Exception ex){
			ex.printStackTrace();
		}
		
		System.out.println("Test");
	}

}
