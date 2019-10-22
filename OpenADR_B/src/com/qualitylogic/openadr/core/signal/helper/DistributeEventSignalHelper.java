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
import com.qualitylogic.openadr.core.signal.OadrCreateOptType;
import com.qualitylogic.openadr.core.signal.OadrPayload;
import com.qualitylogic.openadr.core.signal.OadrDistributeEventType;
import com.qualitylogic.openadr.core.signal.OadrRequestEventType;
import com.qualitylogic.openadr.core.signal.OadrSignedObject;
import com.qualitylogic.openadr.core.signal.OptTypeType;
import com.qualitylogic.openadr.core.signal.PayloadFloatType;
import com.qualitylogic.openadr.core.signal.ResponseRequiredType;
import com.qualitylogic.openadr.core.signal.SignalPayloadType;
import com.qualitylogic.openadr.core.signal.OadrDistributeEventType.OadrEvent;
import com.qualitylogic.openadr.core.util.Clone;
import com.qualitylogic.openadr.core.util.OadrUtil;
import com.qualitylogic.openadr.core.util.PropertiesFileReader;

public class DistributeEventSignalHelper {

	public static OadrDistributeEventType createDistributeEventPayloadWithoutEvent() {
		String fileName = "oadrDistributeEvent_NoEvent_Default.xml";
		PropertiesFileReader propertiesFileReader = new PropertiesFileReader();
		String testDataPath = propertiesFileReader.get("testDataPath");
		String distributeRequestID = OadrUtil.createoadrDistributeRequestID();
		String vtnID = propertiesFileReader.get("VTN_ID");

		OadrDistributeEventType OadrDistributeEventType = new DistributeEventSignalHelper()
				.loadOadrDistributeEvent(testDataPath + fileName);
		OadrDistributeEventType.setRequestID("Default_" + distributeRequestID);
		OadrDistributeEventType.getEiResponse().setRequestID("");
		OadrDistributeEventType.setVtnID(vtnID);

		return OadrDistributeEventType;
	}

	public OadrDistributeEventType loadOadrDistributeEvent(String file) {

		OadrDistributeEventType OadrDistributeEventType = null;
		try {

			//OadrDistributeEventType = (OadrDistributeEventType) ((JAXBElement<Object>)new SchemaHelper()
				//	.loadTestDataXMLFile(file)).getValue();

			OadrDistributeEventType = (OadrDistributeEventType) new SchemaHelper()
			.loadTestDataXMLFile(file).getOadrDistributeEvent();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return OadrDistributeEventType;
	}

	public static int numberOfOptInCreatedEventReceived(
			OadrDistributeEventType eachOadrDistributeEvent,
			ArrayList<CreatedEventBean> createdEventList) {
		int optInCount = 0;

		try {

			String distributeRequestID = eachOadrDistributeEvent.getRequestID();
			List<OadrEvent> oadrEventList = eachOadrDistributeEvent
					.getOadrEvent();

			for (OadrEvent eachOadrEvent : oadrEventList) {
				String distribute_EventID = eachOadrEvent.getEiEvent()
						.getEventDescriptor().getEventID();

				for (CreatedEventBean createdEventBean : createdEventList) {

					boolean reqIdMatch = distributeRequestID
							.equals(createdEventBean.getRequestID());
					boolean eventIdMatch = distribute_EventID
							.equals(createdEventBean.getEventID());
					boolean optTypeIsOptIn = createdEventBean.getOptType()
							.equals(OptTypeType.OPT_IN);
					boolean isModificationNumbersMatch = true;
					if (createdEventBean.getModificationnumber() != null) {
						isModificationNumbersMatch = createdEventBean
								.getModificationnumber().equals(
										eachOadrEvent.getEiEvent()
												.getEventDescriptor()
												.getModificationNumber());

					}

					if (reqIdMatch && eventIdMatch && optTypeIsOptIn
							&& isModificationNumbersMatch) {
						optInCount++;
					}
				}
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return optInCount;
	}

	public static int numberOfOptOutCreatedEventReceived(
			OadrDistributeEventType eachOadrDistributeEvent,
			ArrayList<CreatedEventBean> createdEventList) {
		int optInCount = 0;

		try {

			String distributeRequestID = eachOadrDistributeEvent.getRequestID();
			List<OadrEvent> oadrEventList = eachOadrDistributeEvent
					.getOadrEvent();

			for (OadrEvent oadrEvent : oadrEventList) {
				String distribute_EventID = oadrEvent.getEiEvent()
						.getEventDescriptor().getEventID();

				for (CreatedEventBean createdEventBean : createdEventList) {

					boolean reqIdMatch = distributeRequestID
							.equals(createdEventBean.getRequestID());
					boolean eventIdMatch = distribute_EventID
							.equals(createdEventBean.getEventID());
					boolean optTypeIsOptIn = createdEventBean.getOptType()
							.equals(OptTypeType.OPT_OUT);
					boolean isModificationNumbersMatch = true;
					if (createdEventBean.getModificationnumber() != null) {
						isModificationNumbersMatch = createdEventBean
								.getModificationnumber().equals(
										oadrEvent.getEiEvent()
												.getEventDescriptor()
												.getModificationNumber());

					}

					if (reqIdMatch && eventIdMatch && optTypeIsOptIn
							&& isModificationNumbersMatch) {
						optInCount++;
					}
				}
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return optInCount;
	}


	public static int numberOfOptInCreateOptReceived(
			OadrDistributeEventType eachOadrDistributeEvent,
			ArrayList<OadrCreateOptType> createOptList) {
		int optInCount = 0;

		try {

			//String distributeRequestID = eachOadrDistributeEvent.getRequestID();
			List<OadrEvent> oadrEventList = eachOadrDistributeEvent
					.getOadrEvent();

			for (OadrEvent eachOadrEvent : oadrEventList) {
				String distribute_EventID = eachOadrEvent.getEiEvent()
						.getEventDescriptor().getEventID();

				for (OadrCreateOptType oadrCreateOptType : createOptList) {

					/*boolean reqIdMatch = distributeRequestID
							.equals(oadrCreateOptType.getRequestID());
					 */					
					boolean eventIdMatch = distribute_EventID
							.equals(oadrCreateOptType.getQualifiedEventID().getEventID());
					boolean optTypeIsOptIn = oadrCreateOptType.getOptType()
							.equals(OptTypeType.OPT_IN);
					
					if (eventIdMatch && optTypeIsOptIn) {
						optInCount++;
					}
				}
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return optInCount;
	}

	public static int numberOfOptOutCreateOptReceived(
			OadrDistributeEventType eachOadrDistributeEvent,
			ArrayList<OadrCreateOptType> createOptList) {
		int optOutCount = 0;

		try {

			//String distributeRequestID = eachOadrDistributeEvent.getRequestID();
			List<OadrEvent> oadrEventList = eachOadrDistributeEvent
					.getOadrEvent();

			for (OadrEvent eachOadrEvent : oadrEventList) {
				String distribute_EventID = eachOadrEvent.getEiEvent()
						.getEventDescriptor().getEventID();

				for (OadrCreateOptType oadrCreateOptType : createOptList) {

					/*boolean reqIdMatch = distributeRequestID
							.equals(oadrCreateOptType.getRequestID());*/
					boolean eventIdMatch = distribute_EventID
							.equals(oadrCreateOptType.getQualifiedEventID().getEventID());
					boolean optTypeIsOptIn = oadrCreateOptType.getOptType()
							.equals(OptTypeType.OPT_OUT);
					
					if (eventIdMatch && optTypeIsOptIn) {
						optOutCount++;
					}
				}
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return optOutCount;
	}
	@SuppressWarnings("unchecked")
	public static OadrDistributeEventType createOadrDistributeEventFromString(
			String data) {
		OadrDistributeEventType OadrDistributeEventType = null;
		if (data == null || data.length() < 1)
			return null;

		try {

			JAXBContext testcontext = JAXBContext
					.newInstance("com.qualitylogic.openadr.core.signal");
			InputStream is = new ByteArrayInputStream(data.getBytes("UTF-8"));
			Unmarshaller unmarshall = testcontext.createUnmarshaller();

			OadrSignedObject oadrSignedObject = ((OadrPayload)unmarshall.unmarshal(is)).getOadrSignedObject();
	
			OadrDistributeEventType = oadrSignedObject.getOadrDistributeEvent();
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return OadrDistributeEventType;
	}

	public static long getFirstEventModificationNumber(
			OadrDistributeEventType distributeEvent) {

		long modificationNumber = -1;
		if (distributeEvent == null)
			return modificationNumber;
		if (distributeEvent.getOadrEvent().size() < 1)
			return -1;

		List<OadrEvent> oadrEventList = distributeEvent.getOadrEvent();

		if (oadrEventList != null && oadrEventList.size() > 0) {
			modificationNumber = oadrEventList.get(0).getEiEvent()
					.getEventDescriptor().getModificationNumber();
		}

		return modificationNumber;
	}

	public static void modificationRuleIncrementModificationNumber(
			EiEventType eachEiEvent) {
		long modificationNumber = eachEiEvent.getEventDescriptor()
				.getModificationNumber();
		eachEiEvent.getEventDescriptor().setModificationNumber(
				modificationNumber + 1);
	}

	public static void modificationRuleStartDtCurrentPlusMinutes(
			EiEventType eiEvent, XMLGregorianCalendar time, int minutesToAdd)
			throws FileNotFoundException, UnsupportedEncodingException,
			JAXBException {
		XMLGregorianCalendar startDate = (XMLGregorianCalendar) time.clone();
		Duration eachDuration = com.qualitylogic.openadr.core.util.OadrUtil
				.createDuration(minutesToAdd, 0);
		startDate.add(eachDuration);
		eiEvent.getEiActivePeriod().getProperties().getDtstart()
				.setDateTime(startDate);
	}

	public static void setNewReqIDEvntIDStartTimeAndMarketCtx1(
			OadrDistributeEventType eachOadrDistributeEvent,
			int firstIntervalStartTimeDelayMin) {
		setNewReqIDEvntIDAndStartTime(eachOadrDistributeEvent,
				firstIntervalStartTimeDelayMin);
		setMarketContext1(eachOadrDistributeEvent);
	}

	public static void setNewReqIDEvntIDStartTimeAndMarketCtx2(
			OadrDistributeEventType eachOadrDistributeEvent,
			int firstIntervalStartTimeDelayMin) {
		setNewReqIDEvntIDAndStartTime(eachOadrDistributeEvent,
				firstIntervalStartTimeDelayMin);
		setMarketContext1(eachOadrDistributeEvent);
	}

	public static void setNewReqIDEvntIDAndStartTime(
			OadrDistributeEventType eachOadrDistributeEvent,
			int firstIntervalStartTimeDelayMin) {
		PropertiesFileReader propertiesFileReader = new PropertiesFileReader();
		String distributeRequestID = OadrUtil.createoadrDistributeRequestID();
		eachOadrDistributeEvent.setRequestID(distributeRequestID);
		eachOadrDistributeEvent.setVtnID(propertiesFileReader.getVtnID());
		if (eachOadrDistributeEvent.getEiResponse() != null) {
			eachOadrDistributeEvent.getEiResponse().setRequestID("");
		}
		List<OadrEvent> oadrEventList = eachOadrDistributeEvent.getOadrEvent();
		int eventCount = 0;

		OadrEvent previousEiEvent = null;
		XMLGregorianCalendar createdDateTime = OadrUtil.getCurrentTime();

		for (OadrEvent eachOadrEvent : oadrEventList) {

			eachOadrEvent.getEiEvent().getEventDescriptor()
					.setCreatedDateTime(createdDateTime);
			eachOadrEvent
					.getEiEvent()
					.getEventDescriptor()
					.setEventID(
							OadrUtil.createDescriptorEventID() + "_"
									+ eventCount);

			if (previousEiEvent == null) {

				XMLGregorianCalendar firstIntervalTime = OadrUtil
						.getCurrentTime();
				Duration durationOffset = OadrUtil.createDuration(
						firstIntervalStartTimeDelayMin, 0);
				firstIntervalTime.add(durationOffset);
				eachOadrEvent.getEiEvent().getEiActivePeriod().getProperties()
						.getDtstart().setDateTime(firstIntervalTime);

			} else {
				XMLGregorianCalendar nextStartCurrentTime = calculateNextStartCurrentTime(
						previousEiEvent.getEiEvent().getEiActivePeriod()
								.getProperties().getDtstart().getDateTime(),
						previousEiEvent.getEiEvent().getEiActivePeriod()
								.getProperties().getDuration().getDuration());
				// Add two more minutes as buffer so events are not back to back
				nextStartCurrentTime = calculateNextStartCurrentTime(
						nextStartCurrentTime, "PT2M");

				eachOadrEvent.getEiEvent().getEiActivePeriod().getProperties()
						.getDtstart().setDateTime(nextStartCurrentTime);
			}

			eventCount++;
			previousEiEvent = eachOadrEvent;
		}

	}

	public static XMLGregorianCalendar calculateNextStartCurrentTime(
			XMLGregorianCalendar createdDateTime, String duration) {
		if (duration == null || createdDateTime == null)
			return OadrUtil.getCurrentTime();

		XMLGregorianCalendar previousCreatedDate = (XMLGregorianCalendar) createdDateTime
				.clone();
		Duration previousDuration = OadrUtil.createDuration(duration);
		previousCreatedDate.add(previousDuration);
		return previousCreatedDate;

	}

	public static boolean isReqReceivedMeetMinimumCumulativeNbrOfDistributeResponses(
			OadrDistributeEventType OadrDistributeEventType,
			OadrRequestEventType OadrRequestEventType) {

		if (OadrRequestEventType!=null && OadrRequestEventType.getEiRequestEvent() != null
				&& OadrRequestEventType.getEiRequestEvent().getReplyLimit() != null) {
			Long replyLimit = OadrRequestEventType.getEiRequestEvent()
					.getReplyLimit();
			if (OadrDistributeEventType.getOadrEvent() != null
					&& OadrDistributeEventType.getOadrEvent().size() <= replyLimit) {
				return true;
			} else {
				return false;
			}
		}

		return true;
	}

	public static void setMarketContext1(
			OadrDistributeEventType eachOadrDistributeEvent) {

		List<OadrEvent> oadrEventList = eachOadrDistributeEvent.getOadrEvent();
		for (OadrEvent eachEiEvent : oadrEventList) {
			setMarketContext1(eachEiEvent.getEiEvent());
		}
	}

	public static void setMarketContext2(
			OadrDistributeEventType eachOadrDistributeEvent) {

		List<OadrEvent> oadrEventList = eachOadrDistributeEvent.getOadrEvent();

		for (OadrEvent eachOadrEvent : oadrEventList) {
			setMarketContext2(eachOadrEvent.getEiEvent());
		}
	}

	public static void setMarketContext1(EiEventType eiEvent) {
		PropertiesFileReader propertiesFileReader = new PropertiesFileReader();
		if (eiEvent.getEventDescriptor().getEiMarketContext() != null) {
			eiEvent.getEventDescriptor()
					.getEiMarketContext()
					.setMarketContext(
							propertiesFileReader.get("DR_MarketContext_1_Name"));
		}
	}

	public static void setMarketContext2(EiEventType eiEvent) {
		PropertiesFileReader propertiesFileReader = new PropertiesFileReader();
		if (eiEvent.getEventDescriptor().getEiMarketContext() != null) {
			eiEvent.getEventDescriptor()
					.getEiMarketContext()
					.setMarketContext(
							propertiesFileReader.get("DR_MarketContext_2_Name"));
		}
	}

	/*
	 * public static OadrDistributeEventType
	 * preloadRuleFilteredOadrDistributeEvent(EventFilterType
	 * filterType,List<String> eventIDList,List<String> marketContextList,Long
	 * replyLimit,OadrDistributeEventType OadrDistributeEventType){
	 * 
	 * String fileName="oadrDistributeEvent_Pull_CPP_Default.xml";
	 * PropertiesFileReader propertiesFileReader=new PropertiesFileReader();
	 * 
	 * 
	 * String pEventID1=propertiesFileReader.get("FilterTestCase_VTN_EventID1");
	 * String pEventID2=propertiesFileReader.get("FilterTestCase_VTN_EventID2");
	 * String pEventID3=propertiesFileReader.get("FilterTestCase_VTN_EventID3");
	 * String pEventID4=propertiesFileReader.get("FilterTestCase_VTN_EventID4");
	 * String pEventID5=propertiesFileReader.get("FilterTestCase_VTN_EventID5");
	 * String
	 * pDR_MarketContext_1_Name=propertiesFileReader.get("DR_MarketContext_1_Name"
	 * ); String
	 * pDR_MarketContext_2_Name=propertiesFileReader.get("DR_MarketContext_2_Name"
	 * );
	 * 
	 * String marketContext1=null; String marketContext2=null;
	 * 
	 * 
	 * 
	 * if(marketContextList!=null && marketContextList.size()>0){ for(String
	 * eachMarketContext:marketContextList){
	 * 
	 * if(eachMarketContext.equals(pDR_MarketContext_1_Name)){
	 * marketContext1=eachMarketContext; }
	 * if(eachMarketContext.equals(pDR_MarketContext_2_Name)){
	 * marketContext2=eachMarketContext; } } } String eventID1=null; String
	 * eventID2=null;
	 * 
	 * if(eventIDList!=null && eventIDList.size()==1){ eventID1=
	 * eventIDList.get(0); } if(eventIDList!=null && eventIDList.size()==2){
	 * eventID1= eventIDList.get(0); eventID2= eventIDList.get(1); }
	 * OadrDistributeEventType filteredDistributeEvent =new
	 * DistributeEventSignalHelper().loadOadrDistributeEvent(fileName);
	 * filteredDistributeEvent
	 * .setEiResponse(OadrDistributeEventType.getEiResponse());
	 * filteredDistributeEvent.setRequestID(OadrDistributeEventType.getRequestID());
	 * filteredDistributeEvent.setRequestID(OadrDistributeEventType.getRequestID());
	 * filteredDistributeEvent.setVtnID(OadrDistributeEventType.getVtnID());
	 * filteredDistributeEvent.getEiEvent().remove(0);
	 * 
	 * //ReplyLimit=1, marketContext2 if(replyLimit!=null && replyLimit==1 &&
	 * marketContext2!=null && marketContext2.equals(pDR_MarketContext_2_Name)){
	 * filteredDistributeEvent
	 * .getEiEvent().add(OadrDistributeEventType.getEiEvent().get(4)); }
	 * //FilterType=Pending, marketContext1 else if(filterType!=null &&
	 * filterType.equals(EventFilterType.PENDING) && marketContext1!=null &&
	 * marketContext1.equals(pDR_MarketContext_1_Name)){
	 * filteredDistributeEvent.
	 * getEiEvent().add(OadrDistributeEventType.getEiEvent().get(1)); }
	 * //FilterType=ALL else if(filterType!=null &&
	 * filterType.equals(EventFilterType.ALL)){
	 * filteredDistributeEvent.getEiEvent
	 * ().add(OadrDistributeEventType.getEiEvent().get(0));
	 * filteredDistributeEvent.getEiEvent
	 * ().add(OadrDistributeEventType.getEiEvent().get(1));
	 * filteredDistributeEvent.getEiEvent
	 * ().add(OadrDistributeEventType.getEiEvent().get(4)); } //FilterType=ACTIVE
	 * else if(filterType!=null && filterType.equals(EventFilterType.ACTIVE)){
	 * filteredDistributeEvent
	 * .getEiEvent().add(OadrDistributeEventType.getEiEvent().get(0)); }
	 * //FilterType=PENDING else if(filterType!=null &&
	 * filterType.equals(EventFilterType.PENDING)){
	 * filteredDistributeEvent.getEiEvent
	 * ().add(OadrDistributeEventType.getEiEvent().get(1)); } //FilterType=COMPLETED
	 * else if(filterType!=null &&
	 * filterType.equals(EventFilterType.COMPLETED)){
	 * filteredDistributeEvent.getEiEvent
	 * ().add(OadrDistributeEventType.getEiEvent().get(2)); } //FilterType=CANCELLED
	 * else if(filterType!=null &&
	 * filterType.equals(EventFilterType.CANCELLED)){
	 * filteredDistributeEvent.getEiEvent
	 * ().add(OadrDistributeEventType.getEiEvent().get(3)); } //EventID1 and
	 * EventID3 else if(eventID1!=null && eventID1.equals(pEventID1) &&
	 * eventID2!=null && eventID2.equals(pEventID3)){
	 * filteredDistributeEvent.getEiEvent
	 * ().add(OadrDistributeEventType.getEiEvent().get(0));
	 * filteredDistributeEvent.getEiEvent
	 * ().add(OadrDistributeEventType.getEiEvent().get(2)); } //EventID1 else
	 * if(eventID1!=null && eventID1.equals(pEventID1)){
	 * filteredDistributeEvent.
	 * getEiEvent().add(OadrDistributeEventType.getEiEvent().get(0)); }
	 * //MarketContext1 and MarketContext2 else if((marketContext1!=null &&
	 * marketContext2!=null && marketContext1.equals(pDR_MarketContext_1_Name)
	 * &&
	 * marketContext2.equals(pDR_MarketContext_2_Name))||(marketContext1!=null
	 * && marketContext1.equals(pDR_MarketContext_1_Name)&& marketContext2!=null
	 * && marketContext2.equals(pDR_MarketContext_1_Name)) ){
	 * filteredDistributeEvent
	 * .getEiEvent().add(OadrDistributeEventType.getEiEvent().get(0));
	 * filteredDistributeEvent
	 * .getEiEvent().add(OadrDistributeEventType.getEiEvent().get(1));
	 * filteredDistributeEvent
	 * .getEiEvent().add(OadrDistributeEventType.getEiEvent().get(2));
	 * filteredDistributeEvent
	 * .getEiEvent().add(OadrDistributeEventType.getEiEvent().get(3)); }
	 * //MarketContext1 else if(marketContext1!=null &&
	 * marketContext1.equals(pDR_MarketContext_1_Name)){
	 * filteredDistributeEvent.
	 * getEiEvent().add(OadrDistributeEventType.getEiEvent().get(0));
	 * filteredDistributeEvent
	 * .getEiEvent().add(OadrDistributeEventType.getEiEvent().get(1)); }
	 * //ReplyLimit 1 else if(replyLimit!=null && replyLimit==1){
	 * filteredDistributeEvent
	 * .getEiEvent().add(OadrDistributeEventType.getEiEvent().get(0)); }
	 * //ReplyLimit 2 else if(replyLimit!=null && replyLimit==2){
	 * filteredDistributeEvent
	 * .getEiEvent().add(OadrDistributeEventType.getEiEvent().get(0));
	 * filteredDistributeEvent
	 * .getEiEvent().add(OadrDistributeEventType.getEiEvent().get(4)); }
	 * 
	 * 
	 * return filteredDistributeEvent; }
	 */

	public static OadrDistributeEventType createOadrDistributeEventsForFilter()
			throws FileNotFoundException, UnsupportedEncodingException,
			JAXBException {
		PropertiesFileReader propertiesFileReader = new PropertiesFileReader();
		String fileName = "oadrDistributeEvent_FiveEvents_FilterTemplate.xml";

		String pEventID1 = propertiesFileReader
				.get("FilterTestCase_VTN_EventID1");
		String pEventID2 = propertiesFileReader
				.get("FilterTestCase_VTN_EventID2");
		String pEventID3 = propertiesFileReader
				.get("FilterTestCase_VTN_EventID3");
		String pEventID4 = propertiesFileReader
				.get("FilterTestCase_VTN_EventID4");
		String pEventID5 = propertiesFileReader
				.get("FilterTestCase_VTN_EventID5");
		String pDR_MarketContext_1_Name = propertiesFileReader
				.get("DR_MarketContext_1_Name");
		String pDR_MarketContext_2_Name = propertiesFileReader
				.get("DR_MarketContext_2_Name");

		OadrDistributeEventType OadrDistributeEventType = new DistributeEventSignalHelper()
				.loadOadrDistributeEvent(fileName);

		String distributeRequestID = OadrUtil.createoadrDistributeRequestID();
		OadrDistributeEventType.setRequestID(distributeRequestID);
		OadrDistributeEventType.setVtnID(propertiesFileReader.get("VTN_ID"));

		if (OadrDistributeEventType.getEiResponse() != null) {
			OadrDistributeEventType.getEiResponse().setRequestID("");
		}

		List<OadrEvent> oadrEventList = OadrDistributeEventType.getOadrEvent();

		// Market Context 1, Active Event.
		EiEventType eventID1 = oadrEventList.get(0).getEiEvent();
		eventID1.getEventDescriptor().setEventID(pEventID1);
		eventID1.getEventDescriptor().setEventStatus(
				EventStatusEnumeratedType.ACTIVE);
		eventID1.getEventDescriptor().setCreatedDateTime(
				OadrUtil.getCurrentTime());
		eventID1.getEventDescriptor()
				.getEiMarketContext()
				.setMarketContext(
						propertiesFileReader.get("DR_MarketContext_1_Name"));
		eventID1.getEiActivePeriod().getProperties().getDtstart()
				.setDateTime(OadrUtil.getCurrentTime());

		// Market Context 1, Pending Event.
		EiEventType eventID2 = oadrEventList.get(1).getEiEvent();
		eventID2.getEventDescriptor().setEventID(pEventID2);
		eventID2.getEventDescriptor().setEventStatus(
				EventStatusEnumeratedType.FAR);
		eventID2.getEventDescriptor().setCreatedDateTime(
				OadrUtil.getCurrentTime());
		eventID2.getEventDescriptor().getEiMarketContext()
				.setMarketContext(pDR_MarketContext_1_Name);
		Duration twoHourDuration = OadrUtil.createDuration(120, 0);
		XMLGregorianCalendar startTime = OadrUtil.getCurrentTime();
		startTime.add(twoHourDuration);
		eventID1.getEiActivePeriod().getProperties().getDtstart()
				.setDateTime(startTime);

		// Market Context 2, Pending Event.
		EiEventType eventID3 = oadrEventList.get(2).getEiEvent();
		eventID3.getEventDescriptor().setEventID(pEventID3);
		eventID3.getEventDescriptor().setEventStatus(
				EventStatusEnumeratedType.NEAR);
		eventID3.getEventDescriptor().setCreatedDateTime(
				OadrUtil.getCurrentTime());
		eventID3.getEventDescriptor().getEiMarketContext()
				.setMarketContext(pDR_MarketContext_1_Name);
		eventID3.getEiActivePeriod().getProperties().getDtstart()
				.setDateTime(OadrUtil.getCurrentTime());

		// Market Context 2, Cancelled Event.
		EiEventType eventID4 = oadrEventList.get(3).getEiEvent();
		eventID4.getEventDescriptor().setEventID(pEventID4);
		eventID4.getEventDescriptor().setEventStatus(
				EventStatusEnumeratedType.CANCELLED);
		eventID4.getEventDescriptor().setCreatedDateTime(
				OadrUtil.getCurrentTime());
		eventID4.getEventDescriptor().getEiMarketContext()
				.setMarketContext(pDR_MarketContext_1_Name);
		eventID4.getEiActivePeriod().getProperties().getDtstart()
				.setDateTime(OadrUtil.getCurrentTime());

		// Market Context 2, Pending Event.
		EiEventType eventID5 = oadrEventList.get(4).getEiEvent();
		eventID5.getEventDescriptor().setEventID(pEventID5);
		eventID5.getEventDescriptor().setEventStatus(
				EventStatusEnumeratedType.NEAR);
		eventID5.getEventDescriptor().setCreatedDateTime(
				OadrUtil.getCurrentTime());
		eventID5.getEventDescriptor().getEiMarketContext()
				.setMarketContext(pDR_MarketContext_2_Name);
		Duration oneHourDuration = OadrUtil.createDuration(60, 0);
		XMLGregorianCalendar startTime5 = OadrUtil.getCurrentTime();
		startTime5.add(oneHourDuration);
		eventID1.getEiActivePeriod().getProperties().getDtstart()
				.setDateTime(startTime5);

		return OadrDistributeEventType;

	}

	public static synchronized boolean isEventIDsMatch(
			OadrDistributeEventType OadrDistributeEventType,
			ArrayList<String> expectedEventIDs) {
		ArrayList<String> receivedEventIDs = getEventIDsReceived(OadrDistributeEventType);
		int expectedCount = expectedEventIDs.size();
		if (expectedCount != receivedEventIDs.size()) {
			return false;
		}

		int matchedCount = 0;
		for (String eachExpectedEventID : expectedEventIDs) {
			if (OadrUtil.isIDFoundInList(eachExpectedEventID, receivedEventIDs)) {
				matchedCount++;
			}
		}

		if (expectedCount == matchedCount) {
			return true;
		}

		return false;
	}

	public static synchronized ArrayList<String> getEventIDsReceived(
			OadrDistributeEventType OadrDistributeEventType) {
		ArrayList<String> eventIDs = new ArrayList<String>();
		List<OadrEvent> oadrEventList = OadrDistributeEventType.getOadrEvent();
		for (OadrEvent eachOadrEvent : oadrEventList) {
			if (eachOadrEvent.getEiEvent().getEventDescriptor() != null) {
				eventIDs.add(eachOadrEvent.getEiEvent().getEventDescriptor()
						.getEventID());
			}
		}
		return eventIDs;
	}

	public static boolean isPendingEvent(EiEventType eachEiEvent) {
		if (calculateEventStatus(eachEiEvent).getEventStatus().equals(
				EventStatusEnumeratedType.FAR)
				|| calculateEventStatus(eachEiEvent).getEventStatus().equals(
						EventStatusEnumeratedType.NEAR)) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean isActiveAndPendingEvent(EiEventType eachEiEvent) {
		if (calculateEventStatus(eachEiEvent).getEventStatus().equals(
				EventStatusEnumeratedType.FAR)
				|| calculateEventStatus(eachEiEvent).getEventStatus().equals(
						EventStatusEnumeratedType.NEAR)
				|| calculateEventStatus(eachEiEvent).getEventStatus().equals(
						EventStatusEnumeratedType.ACTIVE)) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean isExpectedReceived(String expectedEventStatus,ResponseRequiredType expectedResponseRequired,long expectedModificationNumber, OadrEvent eventReceived){
		LogHelper.addTrace("Checking DistributeEvent for Expected EventStatus, ResponseRequired and ModificationNumber...");
		
		if(expectedResponseRequired!=null){
			if(expectedResponseRequired.equals(eventReceived.getOadrResponseRequired())){
				LogHelper.addTrace("Received ResponseRequiredType "+expectedResponseRequired+" as expected");
			}else{
				LogHelper.addTrace("Did not receive ResponseRequiredType "+expectedResponseRequired+" as expected");
				return false;
			}
		}
		if(expectedEventStatus.equalsIgnoreCase("P")){
			if(DistributeEventSignalHelper.isPendingEvent(eventReceived)){
				LogHelper.addTrace("Received Pending Event as expected");
			}else{
				LogHelper.addTrace("Did not receive a Pending Event");
				return false;
			}
		}else if(expectedEventStatus.equalsIgnoreCase("A")){
			if(eventReceived.getEiEvent().getEventDescriptor().getEventStatus().equals(EventStatusEnumeratedType.ACTIVE)){
				LogHelper.addTrace("Received Active Event as expected");
			}else{
				LogHelper.addTrace("Did not receive Active Event");
				return false;
			}
		}else if(expectedEventStatus.equalsIgnoreCase("C")){
			if(eventReceived.getEiEvent().getEventDescriptor().getEventStatus().equals(EventStatusEnumeratedType.CANCELLED)){
				LogHelper.addTrace("Received Cancelled Event as expected");
			}else{
				LogHelper.addTrace("Did not receive a Cancelled Event");
				return false;
			}
		}else if(expectedEventStatus.equalsIgnoreCase("CO")){
			if(eventReceived.getEiEvent().getEventDescriptor().getEventStatus().equals(EventStatusEnumeratedType.COMPLETED)){
				LogHelper.addTrace("Received Completed Event as expected");
			}else{
				LogHelper.addTrace("Did not receive a Completed Event");
				return false;
			}
		}else{
			LogHelper.addTrace("Unknown event status");
			return false;
		}

		if(expectedModificationNumber==eventReceived.getEiEvent().getEventDescriptor().getModificationNumber()){
			LogHelper.addTrace("Received ModificationNumber "+expectedModificationNumber +" as expected");
		}else{
			LogHelper.addTrace("Expected ModificationNumber "+expectedModificationNumber);
			return false;
		}
		
		return true;
	}
	
	public static boolean isPendingEvent(OadrEvent oadrEvent) {
		if (oadrEvent.getEiEvent().getEventDescriptor().getEventStatus().equals(
				EventStatusEnumeratedType.FAR)
				|| oadrEvent.getEiEvent().getEventDescriptor().getEventStatus().equals(
						EventStatusEnumeratedType.NEAR)) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean isEventStarted(XMLGregorianCalendar eventStartDate,
			XMLGregorianCalendar currentDateTime) {
		if (eventStartDate.compare(currentDateTime) <= 0) {
			return true;
		} else {
			return false;
		}
	}

	public static CalculatedEventStatusBean calculateEventStatus(
			EiEventType eachEiEvent, boolean isForEventOrdering) {
		return calculateEventStatus(eachEiEvent, isForEventOrdering, null);
	}

	public static CalculatedEventStatusBean calculateEventStatus(
			EiEventType eachEiEvent, XMLGregorianCalendar anotherCreatedDateToUse) {
		return calculateEventStatus(eachEiEvent, false, anotherCreatedDateToUse);
	}

	public static CalculatedEventStatusBean calculateEventStatus(
			EiEventType eachEiEvent) {
		return calculateEventStatus(eachEiEvent, false, null);
	}

	public static CalculatedEventStatusBean calculateEventStatus(
			EiEventType eachEiEvent, boolean isForEventOrdering,
			XMLGregorianCalendar anotherCreatedDateToUse) {

		// XMLGregorianCalendar currentDateTime=OadrUtil.getCurrentTime();
		CalculatedEventStatusBean calculatedEventStatusBean = new CalculatedEventStatusBean();

		calculatedEventStatusBean.addLogTrace("Event ID "
				+ eachEiEvent.getEventDescriptor().getEventID());

		if (!isForEventOrdering) {
			if (eachEiEvent.getEventDescriptor().getEventStatus()
					.equals(EventStatusEnumeratedType.CANCELLED)) {
				calculatedEventStatusBean
						.addLogTrace("Payload indicates that Event had been cancelled.");
				calculatedEventStatusBean
						.setEventStatus(EventStatusEnumeratedType.CANCELLED);
				return calculatedEventStatusBean;
			}
		}

		XMLGregorianCalendar eventStartDate = (XMLGregorianCalendar) eachEiEvent
				.getEiActivePeriod().getProperties().getDtstart().getDateTime()
				.clone();

		calculatedEventStatusBean.addLogTrace("EventStartDate in payload "
				+ eventStartDate);

		Duration rampUp = OadrUtil.createDuration(0, 0);
		if (eachEiEvent.getEiActivePeriod().getProperties().getXEiRampUp() != null) {
			rampUp = OadrUtil.createDuration(eachEiEvent.getEiActivePeriod()
					.getProperties().getXEiRampUp().getDuration());

			calculatedEventStatusBean.addLogTrace("XEiRampUp in payload is "
					+ rampUp);

		} else {
			calculatedEventStatusBean
					.addLogTrace("XEiRampUp is not found in payload");
		}
		XMLGregorianCalendar currentDateTime = null;

		if (anotherCreatedDateToUse != null) {
			currentDateTime = anotherCreatedDateToUse;
			calculatedEventStatusBean
					.addLogTrace("Another CreatedDateTime provided(not from this payload) is used for calculation : "
							+ currentDateTime);

		} else {
			currentDateTime = eachEiEvent.getEventDescriptor()
					.getCreatedDateTime();
			calculatedEventStatusBean
					.addLogTrace("CreatedDateTime is used for calculation is "
							+ currentDateTime);

		}

		boolean isEventStarted = isEventStarted(eventStartDate, currentDateTime);

		if (isEventStarted) {
			calculatedEventStatusBean.addLogTrace("Event has started");
		} else {
			calculatedEventStatusBean.addLogTrace("Event has not started");
		}

		Duration eventDuration = OadrUtil.createDuration(eachEiEvent
				.getEiActivePeriod().getProperties().getDuration()
				.getDuration());

		if (isEventStarted
				&& eventDuration.equals(OadrUtil.createDuration("PT0M"))) {
			calculatedEventStatusBean
					.addLogTrace("Event Status is found to be ACTIVE as the event has started and ActiveEvent:duration:duration is equal to 0");
			calculatedEventStatusBean
					.setEventStatus(EventStatusEnumeratedType.ACTIVE);
			return calculatedEventStatusBean;
		}
		// • If the eiEvent:eiActivePeriod:properties:dtstart time has not
		// started and eiEvent:eiActivePeriod:properties:eiNotification is 0
		if (!isEventStarted && rampUp.equals(OadrUtil.createDuration("PT0M"))) {

			calculatedEventStatusBean
					.addLogTrace("Event Status is found to be FAR as 'eiEvent:eiActivePeriod:properties:dtstart time has not started and eiEvent:eiActivePeriod:properties:x-eiRampUp is 0 or underfined'");
			calculatedEventStatusBean
					.setEventStatus(EventStatusEnumeratedType.FAR);
			return calculatedEventStatusBean;
		}

		Duration rampUpToRemove = OadrUtil.createDuration(false,
				rampUp.getYears(), rampUp.getMonths(), rampUp.getDays(),
				rampUp.getHours(), rampUp.getMinutes(), rampUp.getSeconds());

		XMLGregorianCalendar eventStartDateMinusRampUp = ((XMLGregorianCalendar) eventStartDate
				.clone());
		eventStartDateMinusRampUp.add(rampUpToRemove);
		calculatedEventStatusBean.addLogTrace("EventStart Date("
				+ eventStartDate + ") minus x-eiRampUp(" + rampUpToRemove
				+ ") = " + eventStartDateMinusRampUp);

		boolean isEventStartedWithinRampUp = isEventStarted(
				eventStartDateMinusRampUp, currentDateTime);

		if (!isEventStarted && isEventStartedWithinRampUp) {
			calculatedEventStatusBean
					.addLogTrace("Event Status is found to be NEAR as 'eiEvent:eiActivePeriod:properties:dtstart time has not started, but the current time is past dtstart minus x-eiRampUp");
			calculatedEventStatusBean
					.setEventStatus(EventStatusEnumeratedType.NEAR);
			return calculatedEventStatusBean;
		}

		XMLGregorianCalendar eventStartDatePlusDuration = ((XMLGregorianCalendar) eventStartDate
				.clone());

		eventStartDatePlusDuration.add(eventDuration);
		calculatedEventStatusBean.addLogTrace("EventStart Date("
				+ eventStartDate + ") plus duration(" + eventDuration + ") = "
				+ eventStartDatePlusDuration);

		boolean isEventEndAfterDuration = isEventStarted(
				eventStartDatePlusDuration, currentDateTime);

		if (isEventStarted && !isEventEndAfterDuration) {
			calculatedEventStatusBean
					.addLogTrace("Event Status is found to be ACTIVE as 'eiEvent:eiActivePeriod:properties:dtstart time is greater that currentTime and current time is less that dtstart plus duration");
			calculatedEventStatusBean
					.setEventStatus(EventStatusEnumeratedType.ACTIVE);
			return calculatedEventStatusBean;
		}

		if (!isEventStarted && !isEventEndAfterDuration) {
			calculatedEventStatusBean
					.addLogTrace("Event Status is found to be FAR as 'eiEvent:eiActivePeriod:properties:dtstart time is greater that currentTime and current time is greater that dtstart plus duration");
			calculatedEventStatusBean
					.setEventStatus(EventStatusEnumeratedType.FAR);
			return calculatedEventStatusBean;
		}

		if (isEventEndAfterDuration) {
			calculatedEventStatusBean
					.addLogTrace("Event Status is found to be COMPLETED as 'eiEvent:eiActivePeriod:properties:dtstart plus duration is greater that current time");
			calculatedEventStatusBean
					.setEventStatus(EventStatusEnumeratedType.COMPLETED);
			return calculatedEventStatusBean;
		}

		return null;
	}

	public static synchronized IntervalType getCurrentActiveInterval(
			EiEventType eachEiEvent, EiEventSignalType eachEiEventSignal,
			XMLGregorianCalendar startDateTime,
			XMLGregorianCalendar currentDateTime) {
		XMLGregorianCalendar eventStartDateTime = (XMLGregorianCalendar) startDateTime
				.clone();
		List<IntervalType> intervalList = eachEiEventSignal.getIntervals()
				.getInterval();

		for (IntervalType eachInterval : intervalList) {

			Duration eachDuration = OadrUtil.createDuration(eachInterval
					.getDuration().getDuration());
			eventStartDateTime.add(eachDuration);

			if (eventStartDateTime.compare(currentDateTime) >= 0) {
				return eachInterval;
			}

		}

		Duration activePeriodDuration = OadrUtil.createDuration(eachEiEvent
				.getEiActivePeriod().getProperties().getDuration()
				.getDuration());
		Duration zeroDuration = OadrUtil.createDuration("PT0M");

		if (activePeriodDuration.equals(zeroDuration)
				&& intervalList.size() > 0) {
			return intervalList.get(0);
		}

		return null;
	}

	public static synchronized ArrayList<String> getEventIDs(
			OadrDistributeEventType OadrDistributeEventType) {
		ArrayList<String> eventIDs = new ArrayList<String>();
		List<OadrEvent> oadrEventList = OadrDistributeEventType.getOadrEvent();
		for (OadrEvent eachOadrEvent : oadrEventList) {
			if (eachOadrEvent.getEiEvent().getEventDescriptor() != null) {
				eventIDs.add(eachOadrEvent.getEiEvent().getEventDescriptor()
						.getEventID());
			}
		}
		return eventIDs;
	}

	public static synchronized ArrayList<String> getRequestIDs(
			ArrayList<OadrDistributeEventType> oadrDistributeEventList) {
		ArrayList<String> requestIDs = new ArrayList<String>();
		for (OadrDistributeEventType eachDistributeEvent : oadrDistributeEventList) {
			requestIDs.add(eachDistributeEvent.getRequestID());
		}
		return requestIDs;
	}

	public static synchronized ArrayList<String> getPendingEventIDs(
			OadrDistributeEventType OadrDistributeEventType) {
		ArrayList<String> eventIDs = new ArrayList<String>();
		List<OadrEvent> oadrEventList = OadrDistributeEventType.getOadrEvent();
		for (OadrEvent eachOadrEvent : oadrEventList) {
			if (eachOadrEvent.getEiEvent().getEventDescriptor() != null) {

				if (isPendingEvent(eachOadrEvent.getEiEvent())) {
					eventIDs.add(eachOadrEvent.getEiEvent()
							.getEventDescriptor().getEventID());
				}

			}
		}
		return eventIDs;
	}

	public static synchronized ArrayList<String> getActiveAndPendingEventIDs(
			OadrDistributeEventType OadrDistributeEventType) {
		ArrayList<String> eventIDs = new ArrayList<String>();
		List<OadrEvent> oadrEventList = OadrDistributeEventType.getOadrEvent();
		for (OadrEvent eachOadrEvent : oadrEventList) {
			if (eachOadrEvent.getEiEvent().getEventDescriptor() != null) {

				if (isActiveAndPendingEvent(eachOadrEvent.getEiEvent())) {
					eventIDs.add(eachOadrEvent.getEiEvent()
							.getEventDescriptor().getEventID());
				}

			}
		}
		return eventIDs;
	}

	public static synchronized ArrayList<String> getEventIDs(
			ArrayList<OadrDistributeEventType> oadrDistributeEventList) {
		ArrayList<String> eventIDs = new ArrayList<String>();
		for (OadrDistributeEventType eachOadrDistributeEvent : oadrDistributeEventList) {
			eventIDs.addAll(getEventIDs(eachOadrDistributeEvent));
		}
		return eventIDs;
	}

	public static synchronized ArrayList<String> getPendingEventIDs(
			ArrayList<OadrDistributeEventType> oadrDistributeEventList) {
		ArrayList<String> eventIDs = new ArrayList<String>();
		for (OadrDistributeEventType eachOadrDistributeEvent : oadrDistributeEventList) {
			eventIDs.addAll(getPendingEventIDs(eachOadrDistributeEvent));
		}
		return eventIDs;
	}

	public static synchronized ArrayList<String> getActiveAndPendingEventIDs(
			ArrayList<OadrDistributeEventType> oadrDistributeEventList) {
		ArrayList<String> eventIDs = new ArrayList<String>();
		for (OadrDistributeEventType eachOadrDistributeEvent : oadrDistributeEventList) {
			eventIDs.addAll(getActiveAndPendingEventIDs(eachOadrDistributeEvent));
		}
		return eventIDs;
	}

	public static synchronized OadrDistributeEventType resetEventStatusAndFilterOutCancelled(
			OadrDistributeEventType OadrDistributeEventType) {
		OadrDistributeEventType oadrDistributeEventCloned = Clone
				.clone(OadrDistributeEventType);
		oadrDistributeEventCloned.setRequestID("PING_"
				+ OadrUtil.createoadrDistributeRequestID());
		oadrDistributeEventCloned.getOadrEvent().clear();

		List<OadrEvent> oadrEventList = OadrDistributeEventType.getOadrEvent();

		for (OadrEvent eachOadrEvent : oadrEventList) {
			if (eachOadrEvent.getEiEvent().getEventDescriptor() != null) {
				OadrEvent eachOadrEventCloned = Clone.clone(eachOadrEvent);

				eachOadrEventCloned.getEiEvent().getEventDescriptor()
						.setCreatedDateTime(OadrUtil.getCurrentTime());
				eachOadrEventCloned
						.getEiEvent()
						.getEventDescriptor()
						.setEventStatus(
								calculateEventStatus(
										eachOadrEventCloned.getEiEvent())
										.getEventStatus());
				if (!eachOadrEventCloned.getEiEvent().getEventDescriptor()
						.getEventStatus()
						.equals(EventStatusEnumeratedType.CANCELLED)) {
					oadrDistributeEventCloned.getOadrEvent().add(
							eachOadrEventCloned);
					eachOadrEventCloned.getEiEvent().getEventDescriptor()
							.setCreatedDateTime(OadrUtil.getCurrentTime());
					if (eachOadrEventCloned.getEiEvent().getEventDescriptor()
							.getEventStatus()
							.equals(EventStatusEnumeratedType.ACTIVE)) {

						IntervalType activeInterval = DistributeEventSignalHelper
								.getCurrentActiveInterval(
										eachOadrEventCloned.getEiEvent(),
										eachOadrEventCloned.getEiEvent()
												.getEiEventSignals()
												.getEiEventSignal().get(0),
										eachOadrEventCloned.getEiEvent()
												.getEiActivePeriod()
												.getProperties().getDtstart()
												.getDateTime(),
										eachOadrEventCloned.getEiEvent()
												.getEventDescriptor()
												.getCreatedDateTime());
						
						//PayloadFloatType activeIntervalPayloadFloat = activeInterval
						//		.getSignalPayload().getPayloadFloat();
						
						//SignalPayloadType activeIntervalPayloadFloat = (SignalPayloadType)activeInterval.getStreamPayloadBase().getValue();
						SignalPayloadType activeIntervalPayloadFloat = (SignalPayloadType)activeInterval.getStreamPayloadBase().get(0).getValue();
						//PayloadFloatType PayloadFloatType =activeIntervalPayloadFloat.getPayloadFloat();
						PayloadFloatType PayloadFloatType =(PayloadFloatType)activeIntervalPayloadFloat.getPayloadBase().getValue();
						eachOadrEventCloned.getEiEvent().getEiEventSignals()
								.getEiEventSignal().get(0).getCurrentValue()
								.setPayloadFloat(PayloadFloatType);
					} else if (eachOadrEventCloned.getEiEvent()
							.getEventDescriptor().getEventStatus()
							.equals(EventStatusEnumeratedType.COMPLETED)) {

						PayloadFloatType PayloadFloatType = new PayloadFloatType();
						PayloadFloatType.setValue((float) 0.0);
						eachOadrEventCloned.getEiEvent().getEiEventSignals()
								.getEiEventSignal().get(0).getCurrentValue()
								.setPayloadFloat(PayloadFloatType);
					}
				}

			}
		}

		List<OadrEvent> oadrEvents = oadrDistributeEventCloned.getOadrEvent();
		List<OadrEvent> oadrEventsCompleted = new ArrayList<OadrEvent>();
		List<OadrEvent> oadrEventsOtherThanCompleted = new ArrayList<OadrEvent>();

		for (OadrEvent eachOadrEvent : oadrEvents) {
			if (eachOadrEvent.getEiEvent().getEventDescriptor()
					.getEventStatus()
					.equals(EventStatusEnumeratedType.COMPLETED)) {
				oadrEventsCompleted.add(Clone.clone(eachOadrEvent));
			} else {
				oadrEventsOtherThanCompleted.add(Clone.clone(eachOadrEvent));
			}
		}

		if (oadrEventsCompleted.size() > 0) {
			oadrEventsOtherThanCompleted.addAll(oadrEventsCompleted);
		}

		oadrDistributeEventCloned.getOadrEvent().clear();
		oadrDistributeEventCloned.getOadrEvent().addAll(
				oadrEventsOtherThanCompleted);
		return oadrDistributeEventCloned;
	}

	public static void resetTotalDuration(
			OadrDistributeEventType OadrDistributeEventType) {
		List<OadrEvent> oadrEventList = OadrDistributeEventType.getOadrEvent();
		for (OadrEvent eachOadrEvent : oadrEventList) {
			List<EiEventSignalType> eiEventSignalList = eachOadrEvent.getEiEvent()
					.getEiEventSignals().getEiEventSignal();

			for (EiEventSignalType eachEiEventSignal : eiEventSignalList) {
				Intervals interval = eachEiEventSignal.getIntervals();
				List<IntervalType> intervalList = interval.getInterval();

				Duration duration = null;
				for (IntervalType eachInterval : intervalList) {

					Duration eachDuration = OadrUtil
							.createDuration(eachInterval.getDuration()
									.getDuration());

					if (duration == null) {

						duration = eachDuration;
					} else {
						duration = duration.add(eachDuration);
					}

				}

				if (duration != null) {
					eachOadrEvent.getEiEvent().getEiActivePeriod()
							.getProperties().getDuration()
							.setDuration(duration.toString());
				}
			}
		}
	}

	public static OptTypeType getOptTypeInOptedCreatedEvent(
			EiEventType eiEventToFind, OadrDistributeEventType distributeEvent,
			ArrayList<CreatedEventBean> createdEventList) {

		try {

			String distribute_EventID = eiEventToFind.getEventDescriptor()
					.getEventID();
			String distributeRequestID = distributeEvent.getRequestID();

			for (CreatedEventBean createdEventBean : createdEventList) {

				boolean reqIdMatch = distributeRequestID
						.equals(createdEventBean.getRequestID());
				boolean eventIdMatch = distribute_EventID
						.equals(createdEventBean.getEventID());

				boolean isModificationNumbersMatch = true;
				if (createdEventBean.getModificationnumber() != null) {
					isModificationNumbersMatch = createdEventBean
							.getModificationnumber().equals(
									eiEventToFind.getEventDescriptor()
											.getModificationNumber());

				}

				if (reqIdMatch && eventIdMatch && isModificationNumbersMatch) {
					return createdEventBean.getOptType();
				}
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

}