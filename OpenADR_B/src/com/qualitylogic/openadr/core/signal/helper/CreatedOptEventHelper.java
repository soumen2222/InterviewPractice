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

import com.qualitylogic.openadr.core.bean.CreatedEventBean;
import com.qualitylogic.openadr.core.signal.EiCreatedEvent;
import com.qualitylogic.openadr.core.signal.OadrPayload;
import com.qualitylogic.openadr.core.signal.EiResponseType;
import com.qualitylogic.openadr.core.signal.EiTargetType;
import com.qualitylogic.openadr.core.signal.OadrCreateOptType;
import com.qualitylogic.openadr.core.signal.OadrCreatedOptType;
import com.qualitylogic.openadr.core.signal.OadrCreatedPartyRegistrationType;
import com.qualitylogic.openadr.core.signal.OadrDistributeEventType;
import com.qualitylogic.openadr.core.signal.OadrResponseType;
import com.qualitylogic.openadr.core.signal.OadrSignedObject;
import com.qualitylogic.openadr.core.signal.OptTypeType;
import com.qualitylogic.openadr.core.signal.QualifiedEventIDType;
import com.qualitylogic.openadr.core.signal.EventResponses.EventResponse;
import com.qualitylogic.openadr.core.signal.OadrDistributeEventType.OadrEvent;
import com.qualitylogic.openadr.core.util.OadrUtil;
import com.qualitylogic.openadr.core.util.PropertiesFileReader;

public class CreatedOptEventHelper {

	public static OadrCreatedOptType createCreatedOptTypeEventFromString(String data) {
		OadrCreatedOptType oadrCreatedOptType = null;
		if (data == null || data.length() < 1)
			return null;

		try {

			JAXBContext testcontext = JAXBContext
					.newInstance("com.qualitylogic.openadr.core.signal");
			InputStream is = new ByteArrayInputStream(data.getBytes("UTF-8"));
			Unmarshaller unmarshall = testcontext.createUnmarshaller();
			//OadrCreatedEventType = (OadrCreatedEventType) unmarshall.unmarshal(is);

			//oadrCreatedOptType = (OadrCreatedOptType)((JAXBElement<Object>)unmarshall.unmarshal(is)).getValue();
			OadrSignedObject oadrSignedObject = ((OadrPayload)unmarshall.unmarshal(is)).getOadrSignedObject();
			oadrCreatedOptType = oadrSignedObject.getOadrCreatedOpt();

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return oadrCreatedOptType;
	}

	public static OadrCreatedOptType loadOadrCreatedEvent(String fileName)
			throws JAXBException, FileNotFoundException,
			UnsupportedEncodingException {

		OadrCreatedOptType oadrCreatedOptType = ((OadrCreatedOptType) new SchemaHelper()
				.loadTestDataXMLFile(fileName).getOadrCreatedOpt());

		return oadrCreatedOptType;
	}

	public static OadrCreatedPartyRegistrationType loadOadrCreatedPartyRegistration(String fileName)
			throws JAXBException, FileNotFoundException,
			UnsupportedEncodingException {

		OadrCreatedPartyRegistrationType oadrCreatedPartyRegistrationType = ((OadrCreatedPartyRegistrationType) new SchemaHelper()
				.loadTestDataXMLFile(fileName).getOadrCreatedPartyRegistration());

		return oadrCreatedPartyRegistrationType;
	}

/*	public static boolean isExpectedOadrCreateOptReceived(List<OadrCreateOptType> expectedOadrCreateOptTypeList,List<OadrCreateOptType> receivedOadrCreateOptTypeList){
		
		
		if(expectedOadrCreateOptTypeList==null ||receivedOadrCreateOptTypeList==null ||expectedOadrCreateOptTypeList.size()<1 ||receivedOadrCreateOptTypeList.size()<1||receivedOadrCreateOptTypeList.size()<expectedOadrCreateOptTypeList.size()){
			return false;
		}
		
		
		
		int expectedToMatchCount=expectedOadrCreateOptTypeList.size();
		int matchedCount = 0;
		for(OadrCreateOptType oadrCreateOptType:expectedOadrCreateOptTypeList){
			
			String expMarketContext=oadrCreateOptType.getMarketContext();
			String expReqId=oadrCreateOptType.getRequestID();
			String expPEventID=oadrCreateOptType.getQualifiedEventID().getEventID();
			OptTypeType expOptType=oadrCreateOptType.getOptType();
			//List<String> expResourceList=oadrCreateOptType.getEiTarget().getResourceID();
			//int numberOfResourceMatched=-1;
			
				for(OadrCreateOptType eachReceivedOadrCreateOpt:receivedOadrCreateOptTypeList){
					String recMarketContext="";
					if(expMarketContext!=null && expMarketContext.trim().length()>1){
						recMarketContext=eachReceivedOadrCreateOpt.getMarketContext();
						if(recMarketContext==null) recMarketContext ="";
					}else{
						expMarketContext ="";
					}
					
					String recReqId=eachReceivedOadrCreateOpt.getRequestID();
					String recPEventID=eachReceivedOadrCreateOpt.getQualifiedEventID().getEventID();
					OptTypeType recOptType=eachReceivedOadrCreateOpt.getOptType();
					//ArrayList<String> recResourceList=(ArrayList<String>)eachReceivedOadrCreateOpt.getEiTarget().getResourceID();
					
					//if(expResourceList.size()!=recResourceList.size()) continue;
					
					if(expMarketContext.endsWith(recMarketContext) && expReqId.equals(recReqId) && expPEventID.equals(recPEventID) && expOptType.equals(recOptType)){
						matchedCount++;
					//if(expReqId.equals(recReqId) && expPEventID.equals(recPEventID) && expOptType.equals(recOptType)){
							for(String eachExpRes:expResourceList){
								if(OadrUtil.isIDFoundInList(eachExpRes, recResourceList)){
									if(numberOfResourceMatched==-1){
										numberOfResourceMatched=0;
									}
									numberOfResourceMatched++;
								}
							}
						}else{
							continue;
						}
				}
				
				if(expResourceList.size()!=numberOfResourceMatched){
					return false;
				}
		}
		
		if(expectedToMatchCount==matchedCount)return true;
		
		return false;
	}*/
	
	


/*
	public static String createCreatedEventError() {
		try {
			OadrCreatedEventType OadrCreatedEventType = CreatedEventHelper
					.loadOadrCreatedEvent("oadrCreatedEvent_error_Default.xml");

			PropertiesFileReader ctnPropertiesFileReader = new PropertiesFileReader();
			OadrCreatedEventType.getEiCreatedEvent().setVenID(venIDInConfig);

			String createdEventStr = SchemaHelper
					.getCreatedEventAsString(OadrCreatedEventType);
			return createdEventStr;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return "";
	}
	*/
/*
	public static synchronized String createCreatedEventError(
			ArrayList<String> distributeEventList,
			String eiResponseresponseCode,
			ArrayList<String> createdEventResponseCodeList,
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
			Object test = unmarshall.unmarshal(is);

			OadrDistributeEventType oadrDistributeEventReceived = null;
			if (test.getClass() == OadrDistributeEventType.class) {
				oadrDistributeEventReceived = (OadrDistributeEventType) test;
				if (oadrDistributeEventReceived.getOadrEvent().size() < 1)
					continue;

				if (count == 0) {
					OadrCreatedEventType = CreatedEventHelper
							.loadOadrCreatedEvent("oadrCreatedEvent_oneEvent_optIn_Default.xml");
					OadrCreatedEventType.getEiCreatedEvent().getEiResponse()
							.setResponseCode(eiResponseresponseCode);


					if (OadrCreatedEventType.getEiCreatedEvent().getEiResponse() != null) {
						OadrCreatedEventType.getEiCreatedEvent().getEiResponse()
								.setRequestID("");
					}

					eventResponseList = OadrCreatedEventType.getEiCreatedEvent()
							.getEventResponses().getEventResponse();
					eventResponseList.remove(0);
				}

				List<OadrEvent> eiOadrEventList = oadrDistributeEventReceived
						.getOadrEvent();

				for (OadrEvent eachOadrEvent : eiOadrEventList) {
					String eachEventID = eachOadrEvent.getEiEvent()
							.getEventDescriptor().getEventID();
					EventResponse newEventResponse = new EventResponse();

					String responseCode = "200";
					if (createdEventResponseCodeList.size() >= count) {
						responseCode = createdEventResponseCodeList.get(count);
						newEventResponse.setResponseCode(responseCode);
					} else {
						newEventResponse.setResponseCode(responseCode);
					}

					newEventResponse.setRequestID(oadrDistributeEventReceived
							.getRequestID());
					QualifiedEventIDType qualifiedEventID = new QualifiedEventIDType();
					qualifiedEventID.setEventID(eachEventID);
					newEventResponse.setQualifiedEventID(qualifiedEventID);
					if (isOptIn && responseCode.equals("200")) {
						newEventResponse.setOptType(OptTypeType.OPT_IN);
					} else {
						newEventResponse.setOptType(OptTypeType.OPT_OUT);
					}
					eventResponseList.add(newEventResponse);
					if (includeModificationNumber) {
						qualifiedEventID.setModificationNumber(eachOadrEvent
								.getEiEvent().getEventDescriptor()
								.getModificationNumber());
					}

				}
				count++;

			}

		}

		String eiCreatedEvent = null;
		if (OadrCreatedEventType != null) {
			JAXBContext context = JAXBContext
					.newInstance(OadrCreatedEventType.class);
			eiCreatedEvent = SchemaHelper.asString(context, OadrCreatedEventType);

		}
		return eiCreatedEvent;

	}
	
	*/
/*
	public static synchronized String createCreatedEvent(
			ArrayList<String> distributeEventList,
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
			
			@SuppressWarnings("unchecked")
			Object test = ((JAXBElement<Object>)unmarshall.unmarshal(is)).getValue();

			OadrDistributeEventType oadrDistributeEventReceived = null;
			if (test.getClass() == OadrDistributeEventType.class) {
				oadrDistributeEventReceived = (OadrDistributeEventType) test;
				if (oadrDistributeEventReceived.getOadrEvent().size() < 1)
					continue;

				if (count == 0) {
					OadrCreatedEventType = CreatedEventHelper
							.loadOadrCreatedEvent("oadrCreatedEvent_oneEvent_optIn_Default.xml");
					
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

			eiCreatedEvent = SchemaHelper.asString(context, OadrCreatedEventType);

		}
		return eiCreatedEvent;

	}
*/
	
	/*
	public static ArrayList<String> getEventIDs(
			OadrCreateOptType oadrCreateOptType) {

		ArrayList<String> eventIDList = new ArrayList<String>();
		if (oadrCreateOptType.getEiCreatedEvent() == null
				|| OadrCreatedEventType.getEiCreatedEvent().getEventResponses() == null
				|| OadrCreatedEventType.getEiCreatedEvent().getEventResponses()
						.getEventResponse() == null)
			return eventIDList;
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
*/
	/*
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
*/
	/*
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
*/
	/*
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
*/
	/*
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
*/

}