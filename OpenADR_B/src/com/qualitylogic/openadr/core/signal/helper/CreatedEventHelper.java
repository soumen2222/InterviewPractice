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

import com.qualitylogic.openadr.core.base.ErrorConst;
import com.qualitylogic.openadr.core.bean.CreatedEventBean;
import com.qualitylogic.openadr.core.signal.EiCreatedEvent;
import com.qualitylogic.openadr.core.signal.OadrPayload;
import com.qualitylogic.openadr.core.signal.EiResponseType;
import com.qualitylogic.openadr.core.signal.OadrCreatedEventType;
import com.qualitylogic.openadr.core.signal.OadrDistributeEventType;
import com.qualitylogic.openadr.core.signal.OadrSignedObject;
import com.qualitylogic.openadr.core.signal.OptTypeType;
import com.qualitylogic.openadr.core.signal.QualifiedEventIDType;
import com.qualitylogic.openadr.core.signal.EventResponses.EventResponse;
import com.qualitylogic.openadr.core.signal.OadrDistributeEventType.OadrEvent;
import com.qualitylogic.openadr.core.util.PropertiesFileReader;

public class CreatedEventHelper {

	public static OadrCreatedEventType createCreatedEventFromString(String data) {
		OadrCreatedEventType oadrCreatedEventType = null;
		if (data == null || data.length() < 1)
			return null;

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

	public static OadrCreatedEventType loadOadrCreatedEvent(String fileName)
			throws JAXBException, FileNotFoundException,
			UnsupportedEncodingException {

		OadrCreatedEventType OadrCreatedEventType = ((OadrCreatedEventType) new SchemaHelper()
				.loadTestDataXMLFile(fileName).getOadrCreatedEvent());

		return OadrCreatedEventType;
	}

	public static String createCreatedEventError() {
		try {
			OadrCreatedEventType OadrCreatedEventType = CreatedEventHelper
					.loadOadrCreatedEvent("oadrCreatedEvent_error_Default.xml");

			PropertiesFileReader ctnPropertiesFileReader = new PropertiesFileReader();
			String venIDInConfig = ctnPropertiesFileReader.getVenID();
			OadrCreatedEventType.getEiCreatedEvent().setVenID(venIDInConfig);

			String createdEventStr = SchemaHelper
					.getCreatedEventAsString(OadrCreatedEventType);
			return createdEventStr;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return "";
	}

	public static String createCreatedEventError(String responseCode) {
		try {
			OadrCreatedEventType oadrCreatedEventType = CreatedEventHelper
					.loadOadrCreatedEvent("oadrCreatedEvent_error_Default.xml");

			PropertiesFileReader ctnPropertiesFileReader = new PropertiesFileReader();
			String venIDInConfig = ctnPropertiesFileReader.getVenID();
			oadrCreatedEventType.getEiCreatedEvent().setVenID(venIDInConfig);
			
			EiResponseType eiResponse = oadrCreatedEventType.getEiCreatedEvent().getEiResponse();
			eiResponse.setResponseCode(responseCode);
			eiResponse.setResponseDescription(responseCode.equals(ErrorConst.OK_200) ? "OK" : "ERROR");

			String createdEventStr = SchemaHelper.getCreatedEventAsString(oadrCreatedEventType);
			return createdEventStr;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return "";
	}

	public static synchronized String createCreatedEventError(
			List<String> distributeEventList,
			String eiResponseCode,
			String eventResponseCode,
			List<String> createdEventResponseCodeList,
			boolean includeModificationNumber, boolean isOptIn)
			throws Exception {

		if (distributeEventList == null || distributeEventList.size() < 1)
			return null;
		int count = 0;

		JAXBContext testcontext = JAXBContext
				.newInstance("com.qualitylogic.openadr.core.signal");
		OadrCreatedEventType OadrCreatedEventType = null;
		List<EventResponse> eventResponseList = null;
		for (String eachDistributeEvent : distributeEventList) {
			if (eachDistributeEvent == null || eachDistributeEvent.length() < 1)
				continue;

			PropertiesFileReader propertiesFileReader = new PropertiesFileReader();

			InputStream is = new ByteArrayInputStream(
					eachDistributeEvent.getBytes("UTF-8"));
			Unmarshaller unmarshall = testcontext.createUnmarshaller();
			
			// Object test = unmarshall.unmarshal(is);

			OadrSignedObject oadrSignedObject = ((OadrPayload)unmarshall.unmarshal(is)).getOadrSignedObject();
			Object test = new SchemaHelper().getPayloadFromOadrSignedObject(oadrSignedObject);
			
			OadrDistributeEventType oadrDistributeEventReceived = null;
			if (test.getClass() == OadrDistributeEventType.class) {
				oadrDistributeEventReceived = (OadrDistributeEventType) test;
				if (oadrDistributeEventReceived.getOadrEvent().size() < 1)
					continue;

				if (count == 0) {
					OadrCreatedEventType = CreatedEventHelper
							.loadOadrCreatedEvent("oadrCreatedEvent_oneEvent_optIn_Default.xml");
					OadrCreatedEventType.getEiCreatedEvent().setVenID(
							propertiesFileReader.getVenID());
					OadrCreatedEventType.getEiCreatedEvent().getEiResponse()
							.setResponseCode(eiResponseCode);

					if (OadrCreatedEventType.getEiCreatedEvent().getEiResponse() != null) {
						OadrCreatedEventType.getEiCreatedEvent().getEiResponse()
								.setRequestID("");
					}

					eventResponseList = OadrCreatedEventType.getEiCreatedEvent()
							.getEventResponses().getEventResponse();
					eventResponseList.remove(0);
				}

				if (ErrorConst.OK_200.equals(eiResponseCode)) {
					// only add event responses if the eiResponse is 200
					
					List<OadrEvent> eiOadrEventList = oadrDistributeEventReceived
							.getOadrEvent();
	
					for (OadrEvent eachEiEvent : eiOadrEventList) {
						String eachEventID = eachEiEvent.getEiEvent()
								.getEventDescriptor().getEventID();
						EventResponse newEventResponse = new EventResponse();
	
						if (!createdEventResponseCodeList.isEmpty() && createdEventResponseCodeList.size() >= count) {
							eventResponseCode = createdEventResponseCodeList.get(count);
							newEventResponse.setResponseCode(eventResponseCode);
						} else {
							newEventResponse.setResponseCode(eventResponseCode);
						}
	
						newEventResponse.setRequestID(oadrDistributeEventReceived
								.getRequestID());
						QualifiedEventIDType qualifiedEventID = new QualifiedEventIDType();
						qualifiedEventID.setEventID(eachEventID);
						newEventResponse.setQualifiedEventID(qualifiedEventID);
						if (isOptIn && eventResponseCode.equals("200")) {
							newEventResponse.setOptType(OptTypeType.OPT_IN);
						} else {
							newEventResponse.setOptType(OptTypeType.OPT_OUT);
						}
						eventResponseList.add(newEventResponse);
						if (includeModificationNumber) {
							qualifiedEventID.setModificationNumber(eachEiEvent
									.getEiEvent().getEventDescriptor()
									.getModificationNumber());
						}
					}
				}
				
				count++;

			}

		}

		String eiCreatedEvent = null;
		if (OadrCreatedEventType != null) {
			JAXBContext context = JAXBContext
					.newInstance(OadrCreatedEventType.class);
			// eiCreatedEvent = SchemaHelper.asString(context, OadrCreatedEventType);
			eiCreatedEvent = SchemaHelper.getCreatedEventAsString(OadrCreatedEventType);

		}
		return eiCreatedEvent;

	}

	public static synchronized String createCreatedEvent(
			List<String> distributeEventList,
			boolean includeModificationNumber, boolean isOptIn,
			String responseDescription) throws Exception {

		if (distributeEventList == null || distributeEventList.size() < 1)
			return null;
		int count = 0;
		includeModificationNumber = true;
		JAXBContext testcontext = JAXBContext
				.newInstance("com.qualitylogic.openadr.core.signal");
		OadrCreatedEventType OadrCreatedEventType = null;
		List<EventResponse> eventResponseList = null;
		for (String eachDistributeEvent : distributeEventList) {
			if (eachDistributeEvent == null || eachDistributeEvent.length() < 1)
				continue;

			PropertiesFileReader propertiesFileReader = new PropertiesFileReader();

			InputStream is = new ByteArrayInputStream(
					eachDistributeEvent.getBytes("UTF-8"));
			Unmarshaller unmarshall = testcontext.createUnmarshaller();
			
			//@SuppressWarnings("unchecked")
			//Object test = ((JAXBElement<Object>)unmarshall.unmarshal(is)).getValue();

			OadrSignedObject oadrSignedObject = ((OadrPayload)unmarshall.unmarshal(is)).getOadrSignedObject();
			Object test = new SchemaHelper().getPayloadFromOadrSignedObject(oadrSignedObject);
					
			OadrDistributeEventType oadrDistributeEventReceived = null;
			if (test.getClass() == OadrDistributeEventType.class) {
				oadrDistributeEventReceived = (OadrDistributeEventType) test;
				if (oadrDistributeEventReceived.getOadrEvent().size() < 1)
					continue;

				if (count == 0) {
					OadrCreatedEventType = CreatedEventHelper
							.loadOadrCreatedEvent("oadrCreatedEvent_oneEvent_optIn_Default.xml");
					OadrCreatedEventType.getEiCreatedEvent().setVenID(
							propertiesFileReader.getVenID());

					/*
					 * if(distributeEventList.size()==1){
					 * OadrCreatedEventType.getEiCreatedEvent
					 * ().getEiResponse().setRequestID
					 * (oadrDistributeEventReceived.getRequestID()); }else{
					 * OadrCreatedEventType
					 * .getEiCreatedEvent().getEiResponse().setRequestID(""); }
					 */

					if (OadrCreatedEventType.getEiCreatedEvent().getEiResponse() != null) {
						OadrCreatedEventType.getEiCreatedEvent().getEiResponse()
								.setRequestID("");
					}

					if (responseDescription != null) {
						if (OadrCreatedEventType.getEiCreatedEvent()
								.getEiResponse() != null) {
							OadrCreatedEventType
									.getEiCreatedEvent()
									.getEiResponse()
									.setResponseDescription(responseDescription);
						} else {
							EiResponseType EiResponseType = new EiResponseType();
							OadrCreatedEventType.getEiCreatedEvent().setEiResponse(
									EiResponseType);
							OadrCreatedEventType
									.getEiCreatedEvent()
									.getEiResponse()
									.setResponseDescription(responseDescription);

						}
					}
					eventResponseList = OadrCreatedEventType.getEiCreatedEvent()
							.getEventResponses().getEventResponse();
					eventResponseList.remove(0);
				}
				count++;
				
				List<OadrEvent> eiOadrEventList = oadrDistributeEventReceived
						.getOadrEvent();

				for (OadrEvent eachEiEvent : eiOadrEventList) {
					// if(eachEiEvent.getEiEvent().getEventDescriptor().getEventStatus()
					// != EventStatusEnumeratedType.COMPLETED){
					String eachEventID = eachEiEvent.getEiEvent()
							.getEventDescriptor().getEventID();
					EventResponse newEventResponse = new EventResponse();
					newEventResponse.setResponseCode("200");
					newEventResponse.setRequestID(oadrDistributeEventReceived
							.getRequestID());
					QualifiedEventIDType qualifiedEventID = new QualifiedEventIDType();
					qualifiedEventID.setEventID(eachEventID);
					newEventResponse.setQualifiedEventID(qualifiedEventID);
					if (isOptIn) {
						newEventResponse.setOptType(OptTypeType.OPT_IN);
					} else {
						newEventResponse.setOptType(OptTypeType.OPT_OUT);
					}
					eventResponseList.add(newEventResponse);
					if (includeModificationNumber) {
						qualifiedEventID.setModificationNumber(eachEiEvent
								.getEiEvent().getEventDescriptor()
								.getModificationNumber());
					}

					// }
				}

			}

		}

		String eiCreatedEvent = null;
		if (OadrCreatedEventType != null) {
			JAXBContext context = JAXBContext
					.newInstance(OadrCreatedEventType.class);

			//eiCreatedEvent = SchemaHelper.asString(context, OadrCreatedEventType);
			eiCreatedEvent = SchemaHelper.getCreatedEventAsString(OadrCreatedEventType);
		}
		return eiCreatedEvent;

	}

	public static ArrayList<String> getEventIDs(
			OadrCreatedEventType oadrCreatedEventType) {

		ArrayList<String> eventIDList = new ArrayList<String>();
		if (oadrCreatedEventType.getEiCreatedEvent() == null
				|| oadrCreatedEventType.getEiCreatedEvent().getEventResponses() == null
				|| oadrCreatedEventType.getEiCreatedEvent().getEventResponses()
						.getEventResponse() == null)
			return eventIDList;
		List<EventResponse> eventResponseList = oadrCreatedEventType
				.getEiCreatedEvent().getEventResponses().getEventResponse();

		for (EventResponse eachEventResponse : eventResponseList) {
			QualifiedEventIDType evntIDType = eachEventResponse
					.getQualifiedEventID();
			String eventID = evntIDType.getEventID();
			eventIDList.add(eventID);
		}

		return eventIDList;
	}

	public static ArrayList<String> getRequestIDs(
			OadrCreatedEventType OadrCreatedEventType) {

		ArrayList<String> reqIDList = new ArrayList<String>();
		if (OadrCreatedEventType.getEiCreatedEvent() == null
				|| OadrCreatedEventType.getEiCreatedEvent().getEventResponses() == null
				|| OadrCreatedEventType.getEiCreatedEvent().getEventResponses()
						.getEventResponse() == null)
			return reqIDList;
		List<EventResponse> eventResponseList = OadrCreatedEventType
				.getEiCreatedEvent().getEventResponses().getEventResponse();

		for (EventResponse eachEventResponse : eventResponseList) {
			String reqID = eachEventResponse.getRequestID();
			reqIDList.add(reqID);
		}

		return reqIDList;
	}

	public static ArrayList<String> getEventResponse(
			OadrCreatedEventType OadrCreatedEventType) {

		ArrayList<String> eventIDList = new ArrayList<String>();
		List<EventResponse> eventResponseList = OadrCreatedEventType
				.getEiCreatedEvent().getEventResponses().getEventResponse();

		for (EventResponse eachEventResponse : eventResponseList) {
			QualifiedEventIDType evntIDType = eachEventResponse
					.getQualifiedEventID();
			String eventID = evntIDType.getEventID();
			eventIDList.add(eventID);
		}

		return eventIDList;
	}

	public static synchronized EventResponse getCreatedEventEventResponse(
			ArrayList<OadrCreatedEventType> oadrCreatedEventList, String eventID) {
		for (OadrCreatedEventType eachOadrCreatedEvent : oadrCreatedEventList) {
			EiCreatedEvent eachEiEvent = eachOadrCreatedEvent
					.getEiCreatedEvent();
			List<EventResponse> eventResponseList = eachEiEvent
					.getEventResponses().getEventResponse();
			for (EventResponse eachEventResponse : eventResponseList) {
				if (eachEventResponse.getQualifiedEventID().getEventID()
						.equals(eventID)) {
					return eachEventResponse;
				}
			}
		}
		return null;
	}

	public synchronized static boolean isCreatedEventFound(
			String distributeEvntReqID, OadrEvent eachOadrEvent,
			ArrayList<CreatedEventBean> createdEventBeanList) {

		for (CreatedEventBean eachCreatedEventBean : createdEventBeanList) {
			String createdEventReqID = eachCreatedEventBean.getRequestID();
			String createdEventEvtID = eachCreatedEventBean.getEventID();
			long createdEvntmodNbr = eachCreatedEventBean
					.getModificationnumber();

			if (distributeEvntReqID.equals(createdEventReqID)
					&& eachOadrEvent.getEiEvent().getEventDescriptor()
							.getEventID().equals(createdEventEvtID)
					&& eachOadrEvent.getEiEvent().getEventDescriptor()
							.getModificationNumber() == createdEvntmodNbr) {
				return true;
			}

		}
		return false;
	}

}