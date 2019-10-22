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
import com.qualitylogic.openadr.core.action.impl.DefaultOadrCreateOptEvent;
import com.qualitylogic.openadr.core.common.TestSession;
import com.qualitylogic.openadr.core.signal.EiTargetType;
import com.qualitylogic.openadr.core.signal.OadrCreateOptType;
import com.qualitylogic.openadr.core.signal.OadrCreatedEventType;
import com.qualitylogic.openadr.core.signal.OadrDistributeEventType;
import com.qualitylogic.openadr.core.signal.OadrSignedObject;
import com.qualitylogic.openadr.core.signal.OadrPayload;
import com.qualitylogic.openadr.core.signal.OptTypeType;
import com.qualitylogic.openadr.core.signal.QualifiedEventIDType;
import com.qualitylogic.openadr.core.signal.EventResponses.EventResponse;
import com.qualitylogic.openadr.core.signal.OadrDistributeEventType.OadrEvent;
import com.qualitylogic.openadr.core.signal.xcal.AvailableType;
import com.qualitylogic.openadr.core.signal.xcal.Dtstart;
import com.qualitylogic.openadr.core.signal.xcal.DurationPropType;
import com.qualitylogic.openadr.core.signal.xcal.Properties;
import com.qualitylogic.openadr.core.signal.xcal.Properties.Tolerance;
import com.qualitylogic.openadr.core.signal.xcal.Properties.Tolerance.Tolerate;
import com.qualitylogic.openadr.core.util.OadrUtil;
import com.qualitylogic.openadr.core.util.Trace;

public class CreateOptEventHelper {

	public static OadrCreateOptType createCreateOptTypeEvent(OadrDistributeEventType oadrDistributeEventType,OptTypeType optType, int eventToSelect){
		
		return createCreateOptTypeEvent(oadrDistributeEventType,optType,"participating",eventToSelect,null,null,null);
	}
	
	public static OadrCreateOptType createCreateOptTypeEvent(OadrDistributeEventType oadrDistributeEventType,OptTypeType optType, int eventToSelect,List<Integer> resourcePositionInPayloadToInclude){
		
		return createCreateOptTypeEvent(oadrDistributeEventType,optType,"participating",eventToSelect,resourcePositionInPayloadToInclude,null,null);
	}
	
	public static OadrCreateOptType createCreateOptTypeEvent(OadrDistributeEventType oadrDistributeEventType,OptTypeType optType,String optReason,int eventToSelect,List<Integer> resourcePositionInPayloadToInclude,List<String> endDeviceAsset,List<AvailableType> pAvailabilityList){

	//Trace trace = TestSession.getTraceObj();

		//if(oadrDistributeEventType!=null && oadrDistributeEventType.getOadrEvent().size() >=eventToSelect && resourcePositionInPayloadToInclude!=null && resourcePositionInPayloadToInclude.size()>0){
		if(oadrDistributeEventType!=null && oadrDistributeEventType.getOadrEvent().size() >=eventToSelect){
			
			OadrEvent oadrEvent=oadrDistributeEventType.getOadrEvent().get(eventToSelect-1);
			
			//List<String> resources=oadrEvent.getEiEvent().getEiTarget().getResourceID();
			
			//Integer resourceIndexValue = resourcePositionInPayloadToInclude.get(resourcePositionInPayloadToInclude.size()-1);
					
			/*if(!(resourceIndexValue.intValue()<=resources.size())){
				LogHelper.addTrace("Unable to create CreateOptType");
				return null;	
			}*/
			//String optID=OadrUtil.createUniqueOadrCreateOptID();

			String marketContext=oadrEvent.getEiEvent().getEventDescriptor().getEiMarketContext().getMarketContext();
			String reqID=oadrDistributeEventType.getRequestID();
			String eventID=oadrEvent.getEiEvent().getEventDescriptor().getEventID();
			long modificationNumber=oadrEvent.getEiEvent().getEventDescriptor().getModificationNumber();
			ArrayList<String>resourceIDs=new ArrayList<String>();
			
/*			for(Integer eachResourceIndex:resourcePositionInPayloadToInclude){
				int index=(eachResourceIndex.intValue()-1);
				resourceIDs.add(resources.get(index));
			}
*/			
			OadrCreateOptType oadrCreateOpt= new DefaultOadrCreateOptEvent().getOadrCreateOptEvent(optType,optReason,marketContext,eventID,modificationNumber,resourceIDs,endDeviceAsset,pAvailabilityList);
			oadrCreateOpt.setRequestID(reqID);
			
			return oadrCreateOpt;
		}else{
			LogHelper.addTrace("Unable to create CreateOptType");
			return null;
		}
		
	}
	
	public static OadrCreateOptType createOadrCreateOptType_Resource(String marketContext,String reqId,String pEventID,OptTypeType optType,ArrayList<String> resourceList){
		OadrCreateOptType oadrCreateOptType=new OadrCreateOptType();
		oadrCreateOptType.setMarketContext(marketContext);
		oadrCreateOptType.setRequestID(reqId);
			QualifiedEventIDType eventID=new QualifiedEventIDType();
								 eventID.setEventID(pEventID);
			oadrCreateOptType.setQualifiedEventID(eventID);
								 
		EiTargetType target=new EiTargetType();
		oadrCreateOptType.setEiTarget(target);
		oadrCreateOptType.setOptType(optType);
		
		List<String> resourceIDList= target.getResourceID();
		
		for(String eachResourceName:resourceList){
			resourceIDList.add(eachResourceName);
		}
		
		return oadrCreateOptType;
	}
	
	public static OadrCreateOptType createCreateOptTypeEventFromString(String data) {
		OadrCreateOptType oadrCreatedEventType = null;
		if (data == null || data.length() < 1)
			return null;

		try {

			JAXBContext testcontext = JAXBContext
					.newInstance("com.qualitylogic.openadr.core.signal");
			InputStream is = new ByteArrayInputStream(data.getBytes("UTF-8"));
			Unmarshaller unmarshall = testcontext.createUnmarshaller();
			//OadrCreatedEventType = (OadrCreatedEventType) unmarshall.unmarshal(is);

			//oadrCreatedEventType = (OadrCreateOptType)((JAXBElement<Object>)unmarshall.unmarshal(is)).getValue();
			OadrSignedObject oadrSignedObject = ((OadrPayload)unmarshall.unmarshal(is)).getOadrSignedObject();
			oadrCreatedEventType = oadrSignedObject.getOadrCreateOpt();

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return oadrCreatedEventType;
	}

	public static OadrCreateOptType loadOadrCreateEvent(String fileName)
			throws JAXBException, FileNotFoundException,
			UnsupportedEncodingException {

		OadrCreateOptType oadrCreateOptType = ((OadrCreateOptType) new SchemaHelper()
				.loadTestDataXMLFile(fileName).getOadrCreateOpt());

		return oadrCreateOptType;
	}
	
	public static OadrCreateOptType getMatchingCreateOptReceived(OptTypeType optType,String VENID,String eventID,ArrayList<String> resourceList){
		ArrayList<OadrCreateOptType> oadrCreateOptTypeList = TestSession.getCreateOptEventReceivedList();
		
		for(OadrCreateOptType eachOadrCreateOptType:oadrCreateOptTypeList){
			OptTypeType eachOptType = eachOadrCreateOptType.getOptType();	
			String eachVENID = eachOadrCreateOptType.getVenID();
			String eachEventID = eachOadrCreateOptType.getQualifiedEventID().getEventID();
			List<String> eachResourceIDList = eachOadrCreateOptType.getEiTarget().getResourceID();
			int matchedCount=0;
			if(optType==eachOptType && VENID.equals(eachVENID) && eventID.equals(eachEventID) && resourceList!=null && eachResourceIDList!=null && resourceList.size()==eachResourceIDList.size()){
				
				for(String eachResource:eachResourceIDList){
					for(String pEachResource:resourceList){
						if(pEachResource.trim().equals(eachResource)){
							matchedCount++;
							break;
						}
					}
					
				}
					if(matchedCount==resourceList.size()){
						return eachOadrCreateOptType;
					}
			}
			
		}
		
		return null;
	}
		
	/*
	public static OadrCreateOptType createOadrCreateOptType(String optID, OptTypeType optInOut,String reason, String venID,String reqID,String eventID,long modificationNumber,List<String> resourceIDs,List<String> endDeviceAsset,List<AvailableType> pAvailabilityList) throws Exception{
		

		XMLGregorianCalendar createdDateTime =OadrUtil.getCurrentTime();
		
		DefaultOadrCreateOptEvent defaultCreateOpt=new DefaultOadrCreateOptEvent();
		//OadrCreateOptType  oadrCreateOptType  = defaultCreateOpt.getOadrCreateOptEvent();

		XMLGregorianCalendar createdDateTime =OadrUtil.getCurrentTime();
		
		DefaultOadrCreateOptEvent defaultCreateOpt=new DefaultOadrCreateOptEvent();

		oadrCreateOptType.setOptID(optID);
		oadrCreateOptType.setOptType(optInOut);
		oadrCreateOptType.setOptReason(reason);
		oadrCreateOptType.setVenID(venID);
		
		oadrCreateOptType.setCreatedDateTime(createdDateTime);
		oadrCreateOptType.setRequestID(reqID);
		
		oadrCreateOptType.getQualifiedEventID().setEventID(eventID);
		oadrCreateOptType.getQualifiedEventID().setModificationNumber(modificationNumber);
		
		
		EiTargetType resourceTarget = oadrCreateOptType.getEiTarget();
			List<String> resourceIDList = resourceTarget.getResourceID();
			resourceIDList.clear();
			resourceIDList.addAll(resourceIDs);
		
		EiTargetType deviceTarget = oadrCreateOptType.getOadrDeviceClass();
			 List<EndDeviceAssetType> endDeviceAssetList = deviceTarget.getEndDeviceAsset();
			 endDeviceAssetList.clear();
			 
		 	
		 	if(endDeviceAsset!=null){
		 		for(String eachEndDeviceAssetStr:endDeviceAsset){
		 			 EndDeviceAssetType eachEndDeviceAssetType = new EndDeviceAssetType();
				 	eachEndDeviceAssetType.setMrid(eachEndDeviceAssetStr);
		 			endDeviceAssetList.add(eachEndDeviceAssetType);			 		

		 		}
		 	}
		 	
		
		
		VavailabilityType vavAilability = oadrCreateOptType.getVavailability();
		if(vavAilability!=null){
			ArrayOfVavailabilityContainedComponents  availability=vavAilability.getComponents();	
			List<AvailableType> availabilityList = availability.getAvailable();
			availabilityList.clear();
			availabilityList.addAll(pAvailabilityList);
											
		}
		
		
		String strOadrCreateOptType = SchemaHelper.getOadrCreateOptAsString(oadrCreateOptType);
		System.out.println(strOadrCreateOptType);

		return null;
	}
	*/
	public static void main(String args[]) throws Exception{
		////////////////////////
		

			
			
		String optID="Opt_1234";
		OptTypeType optInOut=OptTypeType.OPT_IN;
		String reason = "participating";
		String marketContext="Test marketcontext";
		//String venID = "VEN_3214";
		String reqID = "REQ_12343";
		String eventID = "Event_12345";
		long modificationNumber = 1;
		List<String> resourceIDs =new ArrayList<String>();
			resourceIDs.add("RES_123534");
			
		List<String> endDeviceAsset =new ArrayList<String>();
			endDeviceAsset.add("Exterior_Lighting");


			List<AvailableType> pAvailabilityList =new ArrayList<AvailableType>();
			
			
				AvailableType eachAvailable = new AvailableType();
					Dtstart durationStartTime=new Dtstart();
						durationStartTime.setDateTime(OadrUtil.getCurrentTime());
				
					pAvailabilityList.add(eachAvailable);
					eachAvailable.setProperties(new Properties());
					Properties eachAvailableProperties =  eachAvailable.getProperties();
								eachAvailableProperties.setDtstart(durationStartTime);
								//OadrUtil.createDuration("PT5M");
								DurationPropType durationProp=new DurationPropType();
								durationProp.setDuration("PT5M");
								eachAvailableProperties.setDuration(durationProp);
								
								Tolerance tolerance = new Tolerance();

								Tolerate tolerate = new Tolerate();
								tolerate.setStartafter("PT3M");
								tolerance.setTolerate(tolerate);
								
								eachAvailableProperties.setTolerance(tolerance);
									
									DurationPropType durationPropType = new DurationPropType();
										durationPropType.setDuration("PT3M");
										eachAvailableProperties.setXEiNotification(durationPropType);
										
///////////////////////////////////////////////
										//pAvailabilityList = null;
										OadrCreateOptType oadrCreateOpt= new DefaultOadrCreateOptEvent().getOadrCreateOptEvent(optInOut,reason,marketContext,eventID,modificationNumber,resourceIDs,endDeviceAsset,pAvailabilityList);
										
										String strOadrCreateOptType = SchemaHelper.getOadrCreateOptAsString(oadrCreateOpt);
										System.out.println(strOadrCreateOptType);
			
				}
	

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