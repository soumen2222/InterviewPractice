package com.qualitylogic.openadr.core.signal.helper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import javax.xml.bind.JAXBElement;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.Duration;
import javax.xml.datatype.XMLGregorianCalendar;

import com.qualitylogic.openadr.core.action.IDistributeEventAction;
import com.qualitylogic.openadr.core.action.impl.DefaultDistributeEvent_004Action;
import com.qualitylogic.openadr.core.bean.CalculatedEventStatusBean;
import com.qualitylogic.openadr.core.bean.DeviceClass;
import com.qualitylogic.openadr.core.bean.EventBean;
import com.qualitylogic.openadr.core.bean.ServiceType;
import com.qualitylogic.openadr.core.common.TestSession;
import com.qualitylogic.openadr.core.signal.CurrencyItemDescriptionType;
import com.qualitylogic.openadr.core.signal.CurrencyType;
import com.qualitylogic.openadr.core.signal.EiEventBaselineType;
import com.qualitylogic.openadr.core.signal.EiEventSignalType;
import com.qualitylogic.openadr.core.signal.EiEventType;
import com.qualitylogic.openadr.core.signal.EiTargetType;
import com.qualitylogic.openadr.core.signal.EndDeviceAssetType;
import com.qualitylogic.openadr.core.signal.EventStatusEnumeratedType;
import com.qualitylogic.openadr.core.signal.IntervalType;
import com.qualitylogic.openadr.core.signal.Intervals;
import com.qualitylogic.openadr.core.signal.ItemBaseType;
import com.qualitylogic.openadr.core.signal.OadrDistributeEventType;
import com.qualitylogic.openadr.core.signal.PayloadFloatType;
import com.qualitylogic.openadr.core.signal.PowerApparentType;
import com.qualitylogic.openadr.core.signal.PowerReactiveType;
import com.qualitylogic.openadr.core.signal.PowerRealType;
import com.qualitylogic.openadr.core.signal.SignalPayloadType;
import com.qualitylogic.openadr.core.signal.OadrDistributeEventType.OadrEvent;
import com.qualitylogic.openadr.core.signal.StreamPayloadBaseType;
import com.qualitylogic.openadr.core.signal.xcal.Dtstart;
import com.qualitylogic.openadr.core.signal.SignalTypeEnumeratedType;
import com.qualitylogic.openadr.core.util.Direction;
import com.qualitylogic.openadr.core.util.OadrUtil;
import com.qualitylogic.openadr.core.util.PropertiesFileReader;
import com.qualitylogic.openadr.core.ven.VENServerResource;
import com.qualitylogic.openadr.core.ven.VenToVtnClient;
import com.qualitylogic.openadr.core.vtn.VtnToVenClient;

public class DistributeEventConformanceValidationHelper {

	public static boolean isResponseCodeSuccess(
			OadrDistributeEventType oadrDistributeEvent) {
		if (oadrDistributeEvent.getEiResponse() != null
				&& oadrDistributeEvent.getEiResponse() != null
				&& !oadrDistributeEvent.getEiResponse().getResponseCode()
						.startsWith("2")) {
			return false;
		}
		return true;
	}

	public static void main_(String args[]) {
		System.out.println("--------" + OadrUtil.getCurrentTime());

		XMLGregorianCalendar currentDateTimeStart = OadrUtil.getCurrentTime();
		XMLGregorianCalendar currentDateTimeEnd = (XMLGregorianCalendar) currentDateTimeStart
				.clone();
		XMLGregorianCalendar createdDateTimeReceived = (XMLGregorianCalendar) currentDateTimeStart
				.clone();
		Duration current = OadrUtil.createDuration("-PT1M1S");
		createdDateTimeReceived.add(current);

		Duration plusOneMin = OadrUtil.createDuration("PT1M1S");
		Duration minusOneMin = OadrUtil.createDuration("-PT1M1S");

		currentDateTimeStart.add(minusOneMin);
		currentDateTimeEnd.add(plusOneMin);

		System.out.println("Received :"
				+ createdDateTimeReceived.toGregorianCalendar().getTime());
		System.out.println("Start :"
				+ currentDateTimeStart.toGregorianCalendar().getTime());
		System.out.println("End :"
				+ currentDateTimeEnd.toGregorianCalendar().getTime());
		if (createdDateTimeReceived.toGregorianCalendar().getTime()
				.after(currentDateTimeStart.toGregorianCalendar().getTime())
				&& createdDateTimeReceived
						.toGregorianCalendar()
						.getTime()
						.before(currentDateTimeEnd.toGregorianCalendar()
								.getTime())) {
			System.out.println("In between");
		} else {
			System.out.println("NOT In between");
		}

	}

/*	public static String isCreatedDateTimeWithinOneMin(
			OadrDistributeEventType oadrDistributeEvent)
			throws DatatypeConfigurationException {
		XMLGregorianCalendar currentDateTimeStart = OadrUtil.getCurrentTime();
		String currentTime = currentDateTimeStart.toString();
		XMLGregorianCalendar currentDateTimeEnd = (XMLGregorianCalendar) currentDateTimeStart
				.clone();

		Duration plusOneMin = OadrUtil.createDuration("PT1M1S");
		Duration minusOneMin = OadrUtil.createDuration("-PT1M1S");
		currentDateTimeStart.add(minusOneMin);
		currentDateTimeEnd.add(plusOneMin);

		List<OadrEvent> eiOadrEventList = oadrDistributeEvent.getOadrEvent();
		for (OadrEvent eachOadrEventEvent : eiOadrEventList) {

			XMLGregorianCalendar createdDateTimeReceived = eachOadrEventEvent
					.getEiEvent().getEventDescriptor().getCreatedDateTime();

			if (!(createdDateTimeReceived
					.toGregorianCalendar()
					.getTime()
					.after(currentDateTimeStart.toGregorianCalendar().getTime()) && createdDateTimeReceived
					.toGregorianCalendar().getTime()
					.before(currentDateTimeEnd.toGregorianCalendar().getTime()))) {
				return currentTime;
			}

		}
		return "";
	}*/
	
	public static String isCreatedDateTimeWithinFiveMin(
			OadrDistributeEventType oadrDistributeEvent)
			throws DatatypeConfigurationException {
		XMLGregorianCalendar currentDateTimeStart = OadrUtil.getCurrentTime();
		String currentTime = currentDateTimeStart.toString();

		List<OadrEvent> eiOadrEventList = oadrDistributeEvent.getOadrEvent();
		for (OadrEvent eachOadrEventEvent : eiOadrEventList) {

			XMLGregorianCalendar createdDateTimeReceived = eachOadrEventEvent
					.getEiEvent().getEventDescriptor().getCreatedDateTime();

			boolean isCurrentDateTimeWithinWithinFiveMin = OadrUtil.isCurrentDateTimeWithinFiveMin(createdDateTimeReceived,currentDateTimeStart);
			
			if (!isCurrentDateTimeWithinWithinFiveMin) {
				return currentTime;
			}

		}
		return "";
	}

	public static boolean isEventIDsUnique(
			OadrDistributeEventType oadrDistributeEvent) {
		ArrayList<String> eventIDsReceived = DistributeEventSignalHelper
				.getEventIDs(oadrDistributeEvent);
		HashSet<String> uniqueSet = new HashSet<String>();
		for (String eachEventID : eventIDsReceived) {
			uniqueSet.add(eachEventID);
		}
		if (eventIDsReceived != null && eventIDsReceived.size() > 0) {
			if (eventIDsReceived.size() != uniqueSet.size()) {
				return false;
			}
		}
		return true;
	}

	
	public static boolean isEventStatusValid(
			OadrDistributeEventType oadrDistributeEvent)
			throws DatatypeConfigurationException {
		List<OadrEvent> eiOadrEventList = oadrDistributeEvent.getOadrEvent();
		for (OadrEvent eachOadrEventEvent : eiOadrEventList) {
				EventStatusEnumeratedType eventStatusReceived = eachOadrEventEvent
					.getEiEvent().getEventDescriptor().getEventStatus();

			CalculatedEventStatusBean calculatedEventStatusBean = DistributeEventSignalHelper
					.calculateEventStatus(eachOadrEventEvent.getEiEvent());
			EventStatusEnumeratedType eventStatusExpected = calculatedEventStatusBean
					.getEventStatus();
			if (eventStatusReceived != eventStatusExpected) {
				LogHelper
						.addTrace("---------------------------------------------------------");
				String eventID = eachOadrEventEvent.getEiEvent()
						.getEventDescriptor().getEventID();
				LogHelper
						.addTrace("Conformance Validation Error : EventStatus of Event "
								+ eventID
								+ " in payload is "
								+ eventStatusReceived
								+ " but expected(Calculate as below) is "
								+ eventStatusExpected);

				ArrayList<String> eventStatusLogTrace = calculatedEventStatusBean
						.getEventStatusLogTrace();
				for (String eventTraceLogItem : eventStatusLogTrace) {
					LogHelper.addTrace(eventTraceLogItem);
				}
				return false;
			}

		}
		return true;
	}

	public static boolean isAtLeastOneEiTargetMatch(
			OadrDistributeEventType oadrDistributeEvent) {
		List<OadrEvent> eiOadrEventList = oadrDistributeEvent.getOadrEvent();

		for (OadrEvent eachOadrEvent : eiOadrEventList) {
			
			EiTargetType eiTarget = eachOadrEvent.getEiEvent().getEiTarget();
			if(!OadrUtil.isAtLeastOneEiTargetMatch(eiTarget)){
				return false;
			}
		}

		return true;
	}


	
	public static boolean isRequestIDPresent(
			OadrDistributeEventType oadrDistributeEvent) {
		if (oadrDistributeEvent.getRequestID() != null
				&& oadrDistributeEvent.getRequestID().trim().length() > 0) {
			return true;
		} else {
			return false;
		}

	}

	public static boolean isEIResponsePresent(
			OadrDistributeEventType oadrDistributeEvent) {
		if (oadrDistributeEvent.getEiResponse() == null) {
			return false;
		} else {
			return true;
		}

	}

	public static boolean isEventOrderValid(
			OadrDistributeEventType oadrDistributeEvent) {
		List<OadrEvent> eiOadrEventList = oadrDistributeEvent.getOadrEvent();
		ArrayList<EventBean> eiEventListReceived = new ArrayList<EventBean>();
		for (OadrEvent eachOadrEvent : eiOadrEventList) {

			EventBean eachEventEvent = new EventBean();
			eachEventEvent.setEventID(eachOadrEvent.getEiEvent()
					.getEventDescriptor().getEventID());
			eachEventEvent.setStartDate(eachOadrEvent.getEiEvent()
					.getEiActivePeriod().getProperties().getDtstart()
					.getDateTime());

			if (eachOadrEvent.getEiEvent().getEventDescriptor()
					.getEventStatus()
					.equals(EventStatusEnumeratedType.CANCELLED)) {
				CalculatedEventStatusBean eventStatusBean = DistributeEventSignalHelper
						.calculateEventStatus(eachOadrEvent.getEiEvent(), true);
				eachEventEvent.setEventStatus(eventStatusBean.getEventStatus());
			} else {
				eachEventEvent.setEventStatus(eachOadrEvent.getEiEvent()
						.getEventDescriptor().getEventStatus());
			}

			if (eachOadrEvent.getEiEvent().getEventDescriptor().getPriority() != null) {
				eachEventEvent.setPriority(eachOadrEvent.getEiEvent()
						.getEventDescriptor().getPriority());
			} else {
				eachEventEvent.setPriority(9999);

			}
			eiEventListReceived.add(eachEventEvent);
		}
		if (eiEventListReceived.size() < 1)
			return true;

		@SuppressWarnings({ "unchecked" })
		ArrayList<EventBean> eiEventListReceivedExpectedSorting = (ArrayList<EventBean>) eiEventListReceived
				.clone();
		Collections.sort(eiEventListReceivedExpectedSorting);
		int i = 0;
		for (EventBean eachEventBeanReceived : eiEventListReceived) {
			String receivedEventID = eachEventBeanReceived.getEventID();
			EventStatusEnumeratedType receivedEventStatus = eachEventBeanReceived
					.getEventStatus();
			long receivedPriority = eachEventBeanReceived.getPriority();
			XMLGregorianCalendar receivedStartDate = eachEventBeanReceived
					.getStartDate();

			EventBean eachExpectedSorted = eiEventListReceivedExpectedSorting
					.get(i);
			String expectedSortedEventID = eachExpectedSorted.getEventID();
			EventStatusEnumeratedType expectedSortedEventStatus = eachExpectedSorted
					.getEventStatus();
			long expectedSortedPriority = eachExpectedSorted.getPriority();
			XMLGregorianCalendar expectedSortedStartDate = eachExpectedSorted
					.getStartDate();

			if (!(eachExpectedSorted.getEventStatus().equals(
					EventStatusEnumeratedType.COMPLETED) && eachEventBeanReceived
					.getEventStatus().equals(
							EventStatusEnumeratedType.COMPLETED))
					&& (!receivedEventID.equals(expectedSortedEventID)
							|| !receivedEventStatus
									.equals(expectedSortedEventStatus)
							|| receivedPriority != expectedSortedPriority || !receivedStartDate
							.equals(expectedSortedStartDate))) {
				LogHelper
						.addTrace("\n----------------------------------------------------\n");
				LogHelper
						.addTrace("\nConformance Validation Error : Received order\n");
				int orderID = 1;
				for (EventBean received : eiEventListReceived) {
					long priority = 0;
					if (received.getPriority() != 9999) {
						priority = received.getPriority();
					}
					LogHelper.addTrace("\n " + orderID + ")"
							+ received.getEventID() + ","
							+ received.getEventStatus() + "," + priority + ","
							+ received.getStartDate() + " \n");
					orderID++;
				}

				LogHelper
						.addTrace("\nConformance Validation Error : Expected order\n");
				orderID = 1;
				for (EventBean expected : eiEventListReceivedExpectedSorting) {
					long priority = 0;
					if (expected.getPriority() != 9999) {
						priority = expected.getPriority();
					}
					LogHelper.addTrace("\n " + orderID + ")"
							+ expected.getEventID() + ","
							+ expected.getEventStatus() + "," + priority + ","
							+ expected.getStartDate() + " \n");
					orderID++;
				}
				LogHelper
						.addTrace("\n----------------------------------------------------\n");

				return false;
			}

			i++;
		}

		return true;
	}

	public static boolean isActiveCurrentValueMatchCurrentPayload(
			OadrDistributeEventType oadrDistributeEvent) {
		List<OadrEvent> eiOadrEventList = oadrDistributeEvent.getOadrEvent();
		for (OadrEvent eachOadrEvent : eiOadrEventList) {
			List<EiEventSignalType> eiEventSignalList = eachOadrEvent.getEiEvent()
					.getEiEventSignals().getEiEventSignal();

			for (EiEventSignalType eachEiEventSignal : eiEventSignalList) {

				if(eachEiEventSignal.getCurrentValue()==null || eachEiEventSignal.getCurrentValue().getPayloadFloat()==null) continue;
					
				
				float currentValuePayloadFloat = eachEiEventSignal
						.getCurrentValue().getPayloadFloat().getValue();

				EventStatusEnumeratedType eventStatusEnumeratedType = eachOadrEvent
						.getEiEvent().getEventDescriptor().getEventStatus();

				if (eventStatusEnumeratedType == EventStatusEnumeratedType.ACTIVE) {

					XMLGregorianCalendar currentDateTime = eachOadrEvent
							.getEiEvent().getEventDescriptor()
							.getCreatedDateTime();

					IntervalType activeInterval = DistributeEventSignalHelper
							.getCurrentActiveInterval(
									eachOadrEvent.getEiEvent(),
									eachEiEventSignal, eachOadrEvent
											.getEiEvent().getEiActivePeriod()
											.getProperties().getDtstart()
											.getDateTime(), currentDateTime);
					if (activeInterval == null) {
						LogHelper
								.addTrace("\nConformance Validation Error : No active Interval found matching the current time "
										+ currentDateTime
										+ " for the Active Event in payload. Event ID : "
										+ eachOadrEvent.getEiEvent()
												.getEventDescriptor()
												.getEventID() + "\n");
						return false;
					}
					
					
					//SignalPayloadType activeIntervalPayloadFloat = (SignalPayloadType)activeInterval.getStreamPayloadBase().getValue();

					SignalPayloadType activeIntervalPayloadFloat = (SignalPayloadType)activeInterval.getStreamPayloadBase().get(0).getValue();
					//PayloadFloatType payloadFloat =activeIntervalPayloadFloat.getPayloadFloat();
					PayloadFloatType payloadFloat =(PayloadFloatType)activeIntervalPayloadFloat.getPayloadBase().getValue();
					float signalPayloadFloat = payloadFloat.getValue();
					
					
					if (currentValuePayloadFloat == signalPayloadFloat) {
						return true;
					} else {
						LogHelper
								.addTrace("\nConformance Validation Error : SignalPayloadFloat value "
										+ signalPayloadFloat
										+ " did not match "
										+ currentValuePayloadFloat
										+ " for the Active Event : "
										+ eachOadrEvent.getEiEvent()
												.getEventDescriptor()
												.getEventID()
										+ " The interval selection was based on the CreatedDateTime "
										+ currentDateTime + "\n");
						return false;
					}

				}

			}
		}
		return true;
	}

	public static boolean isNonActiveCurrentValueValid(
			OadrDistributeEventType oadrDistributeEvent) {
		List<OadrEvent> eiOadrEventList = oadrDistributeEvent.getOadrEvent();
		for (OadrEvent eachOadrEvent : eiOadrEventList) {
			
			List<EiEventSignalType> eiEventSignalList = eachOadrEvent.getEiEvent()
					.getEiEventSignals().getEiEventSignal();

			for (EiEventSignalType eachEiEventSignal : eiEventSignalList) {
				
				if(eachEiEventSignal.getSignalName()==null || !eachEiEventSignal.getSignalName().equals("SIMPLE")){
					continue;
				}
				
				if(eachEiEventSignal.getCurrentValue()==null || eachEiEventSignal.getCurrentValue()
						.getPayloadFloat()==null) continue;
				
				float currentValue = eachEiEventSignal.getCurrentValue()
						.getPayloadFloat().getValue();

				EventStatusEnumeratedType eventStatusEnumeratedType = eachOadrEvent
						.getEiEvent().getEventDescriptor().getEventStatus();

				if (eventStatusEnumeratedType != EventStatusEnumeratedType.ACTIVE
						&& currentValue != 0) {
					LogHelper
							.addTrace("\nConformance Validation Error : CurrentValue "
									+ currentValue
									+ " was received for the "
									+ eventStatusEnumeratedType
									+ " Event : "
									+ eachOadrEvent.getEiEvent()
											.getEventDescriptor().getEventID()
									+ "\n");
					return false;
				}

			}
		}
		return true;
	}

	public static boolean isPayloadFloatLimitValid(
			OadrDistributeEventType oadrDistributeEvent) {
		List<OadrEvent> oadrEventList = oadrDistributeEvent.getOadrEvent();
		for (OadrEvent eachOadrEvent : oadrEventList) {
			List<EiEventSignalType> eiEventSignalList = eachOadrEvent.getEiEvent()
					.getEiEventSignals().getEiEventSignal();

			for (EiEventSignalType eachEiEventSignal : eiEventSignalList) {
						
						if(eachEiEventSignal.getSignalName()!=null && eachEiEventSignal.getSignalName().equalsIgnoreCase("SIMPLE")){
							
						Intervals interval = eachEiEventSignal.getIntervals();
						List<IntervalType> intervalList = interval.getInterval();
		
						for (IntervalType eachInterval : intervalList) {
		
							
							SignalPayloadType activeIntervalPayloadFloat = (SignalPayloadType)eachInterval.getStreamPayloadBase().get(0).getValue();
							
							PayloadFloatType payloadFloat =(PayloadFloatType)activeIntervalPayloadFloat.getPayloadBase().getValue();
							
							float currentValue = payloadFloat.getValue();
							
		
							if (currentValue != 0 && currentValue != 1
									&& currentValue != 2 && currentValue != 3) {
								LogHelper
										.addTrace("\nConformance Validation Error : CurrentValue "
												+ currentValue
												+ " was received for the Event : "
												+ eachOadrEvent.getEiEvent()
														.getEventDescriptor()
														.getEventID() + "\n");
		
								return false;
							}
		
						}
					}
			}
		}
		return true;
	}

	
	public static boolean isOnePayloadFloatPresentInEachInterval(
			OadrDistributeEventType oadrDistributeEvent) {
		List<OadrEvent> oadrEventList = oadrDistributeEvent.getOadrEvent();
		for (OadrEvent eachOadrEvent : oadrEventList) {
			List<EiEventSignalType> eiEventSignalList = eachOadrEvent.getEiEvent()
					.getEiEventSignals().getEiEventSignal();

			for (EiEventSignalType eachEiEventSignal : eiEventSignalList) {
						
						Intervals interval = eachEiEventSignal.getIntervals();
						List<IntervalType> intervalList = interval.getInterval();
						boolean isOnePayloadFloatElementPresent = OadrUtil.isOnePayloadFloatElementPresent(intervalList);
						if(!isOnePayloadFloatElementPresent){
							return false;
						}
			}
		}
		return true;
	}

	public static boolean isActivePeriodDurationValid(
			OadrDistributeEventType oadrDistributeEvent) {
		List<OadrEvent> eiOadrEventList = oadrDistributeEvent.getOadrEvent();
		for (OadrEvent eachOadrEvent : eiOadrEventList) {
			List<EiEventSignalType> eiEventSignalList = eachOadrEvent.getEiEvent()
					.getEiEventSignals().getEiEventSignal();

			for (EiEventSignalType eachEiEventSignal : eiEventSignalList) {
				
				Intervals interval = eachEiEventSignal.getIntervals();
				List<IntervalType> intervalList = interval.getInterval();

				String activePeriodDurationStr = eachOadrEvent.getEiEvent()
						.getEiActivePeriod().getProperties().getDuration()
						.getDuration();

				Duration activePeriodDuration = OadrUtil
						.createDuration(activePeriodDurationStr);

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
					boolean isEqual = duration.equals(activePeriodDuration);
					if (!isEqual) {
						LogHelper
								.addTrace("\nConformance Validation Error : Sum of Duration is "
										+ duration
										+ ", but active period duration in payload is "
										+ activePeriodDuration
										+ " for the Event : "
										+ eachOadrEvent.getEiEvent()
												.getEventDescriptor()
												.getEventID() + "\n");

						return false;

					}
				}
			}
		}
		return true;
	}

/*	public static boolean isSignalNameSimple(
			OadrDistributeEventType oadrDistributeEvent) {
		List<OadrEvent> oadrEventList = oadrDistributeEvent.getOadrEvent();
		for (OadrEvent eachoadrEvent : oadrEventList) {
			List<EiEventSignalType> eiEventSignalList = eachoadrEvent.getEiEvent()
					.getEiEventSignals().getEiEventSignal();

			for (EiEventSignalType eachEiEventSignal : eiEventSignalList) {
				if (!eachEiEventSignal.getSignalName().equals("simple")) {
					LogHelper
							.addTrace("\nConformance Validation Error : Signal name received is "
									+ eachEiEventSignal.getSignalName()
									+ " for the Event : "
									+ eachoadrEvent.getEiEvent()
											.getEventDescriptor().getEventID()
									+ "\n");

					return false;
				}

			}
		}
		return true;
	}*/

	public static boolean isMultipleVENIDFound(
			OadrDistributeEventType oadrDistributeEvent) {
		List<OadrEvent> oadrEventList = oadrDistributeEvent.getOadrEvent();
		for (OadrEvent eachoadrEvent : oadrEventList) {

			int numberOfVENIdFound = eachoadrEvent.getEiEvent().getEiTarget()
					.getVenID().size();

			if (numberOfVENIdFound > 1) {
				LogHelper.addTrace("\nConformance Validation Error : "
						+ numberOfVENIdFound
						+ " VEN IDs found in OadrDistributeEventType\n");

				return false;
			}

		}
		return true;
	}

	public static boolean isUIDValid(OadrDistributeEventType oadrDistributeEvent) {
		List<OadrEvent> oadrEventList = oadrDistributeEvent.getOadrEvent();

		
		for (OadrEvent eachOadrEvent : oadrEventList) {
			
			if(eachOadrEvent.getEiEvent()==null || eachOadrEvent.getEiEvent().getEiEventSignals()==null){
				continue;
			}
				
						
			if(eachOadrEvent.getEiEvent().getEiEventSignals().getEiEventBaseline()!=null){
				EiEventBaselineType baseline = eachOadrEvent.getEiEvent().getEiEventSignals().getEiEventBaseline();

				Intervals interval = baseline.getIntervals();
				List<IntervalType> intervalList = interval.getInterval();

				int expectedUID = 0;
				for (IntervalType eachInterval : intervalList) {

					com.qualitylogic.openadr.core.signal.xcal.Uid uid = eachInterval.getUid();
					
					if(!OadrUtil.isUIDIntValue(uid)){
						return false;
					}
					
					int currentUID = Integer.parseInt(uid.getText());
					if (currentUID != expectedUID) {
						LogHelper
								.addTrace("\nConformance Validation Error : UID received is "
										+ currentUID
										+ " but expected is "
										+ expectedUID
										+ " for the Event : "
										+ eachOadrEvent.getEiEvent()
												.getEventDescriptor()
												.getEventID() + "\n");

						return false;

					}
					expectedUID++;
				}
				
			}
			
			
			
			List<EiEventSignalType> eiEventSignalList = eachOadrEvent.getEiEvent()
					.getEiEventSignals().getEiEventSignal();

			for (EiEventSignalType eachEiEventSignal : eiEventSignalList) {
				
				Intervals interval = eachEiEventSignal.getIntervals();
				List<IntervalType> intervalList = interval.getInterval();

				int expectedUID = 0;
				for (IntervalType eachInterval : intervalList) {

					com.qualitylogic.openadr.core.signal.xcal.Uid uid = eachInterval.getUid();
					
					if(!OadrUtil.isUIDIntValue(uid)){
						return false;
					}
					
					
					int currentUID = Integer.parseInt(uid.getText());
					if (currentUID != expectedUID) {
						LogHelper
								.addTrace("\nConformance Validation Error : UID received is "
										+ currentUID
										+ " but epected is "
										+ expectedUID
										+ " for the Event : "
										+ eachOadrEvent.getEiEvent()
												.getEventDescriptor()
												.getEventID() + "\n");

						return false;

					}
					expectedUID++;
				}
			}
		}
		return true;
	}

	public static boolean isOnlyOneEventSignalPresent(
			OadrDistributeEventType oadrDistributeEvent) {
		List<OadrEvent> oadrEventList = oadrDistributeEvent.getOadrEvent();
		for (OadrEvent eachEiEvent : oadrEventList) {
			List<EiEventSignalType> eiEventSignalList = eachEiEvent.getEiEvent()
					.getEiEventSignals().getEiEventSignal();

			if (eiEventSignalList != null && eiEventSignalList.size() > 1) {
				LogHelper.addTrace("Conformance Validation Error : Event "
						+ eachEiEvent.getEiEvent().getEventDescriptor()
								.getEventID() + " Contains "
						+ eiEventSignalList.size() + " EventSignals\n");
				return false;
			}
		}
		return true;
	}

	public static boolean isVtnIDValid(OadrDistributeEventType oadrDistributeEvent) {

		if (oadrDistributeEvent == null)
			return false;
		String vtnIDInPayload = "";

		vtnIDInPayload = oadrDistributeEvent.getVtnID();

		PropertiesFileReader ctnPropertiesFileReader = new PropertiesFileReader();
		String vtnIDInConfig = ctnPropertiesFileReader.get("VTN_ID");
		String vtnIDInConfig2 = ctnPropertiesFileReader.get("VTN_ID_2");

		if (vtnIDInPayload.equals(vtnIDInConfig)
				|| vtnIDInPayload.equals(vtnIDInConfig2)) {
			return true;
		} else {
			LogHelper
					.addTrace("\nConformance Validation Error : Unknown VTN ID "
							+ vtnIDInPayload
							+ " has been received expected is "
							+ vtnIDInConfig
							+ "\n");

			return false;
		}

	}

	public static boolean isMarketContextValid(
			OadrDistributeEventType oadrDistributeEvent) {
		PropertiesFileReader propertiesFileReader = new PropertiesFileReader();

		String pDR_MarketContext_1_Name = propertiesFileReader
				.get("DR_MarketContext_1_Name");
		String pDR_MarketContext_2_Name = propertiesFileReader
				.get("DR_MarketContext_2_Name");

		List<OadrEvent> oadrEventList = oadrDistributeEvent.getOadrEvent();
		for (OadrEvent eachOadrEvent : oadrEventList) {
			if (eachOadrEvent.getEiEvent().getEventDescriptor() == null
					|| eachOadrEvent.getEiEvent().getEventDescriptor()
							.getEiMarketContext() == null)
				return true;
			String marketContext = eachOadrEvent.getEiEvent()
					.getEventDescriptor().getEiMarketContext()
					.getMarketContext();
			if (!marketContext.equalsIgnoreCase(pDR_MarketContext_1_Name)
					&& !marketContext.equalsIgnoreCase(pDR_MarketContext_2_Name)) {

				return false;
			}
		}

		return true;
	}

	public static boolean isEventNonOverlapping(
			OadrDistributeEventType oadrDistributeEvent) {

		PropertiesFileReader propertiesFileReader = new PropertiesFileReader();

		String marketContext1 = propertiesFileReader
				.get("DR_MarketContext_1_Name");
		String marketContext2 = propertiesFileReader
				.get("DR_MarketContext_2_Name");

		ArrayList<OadrEvent> oadrEventListDistributeEventMarketCtx1 = new ArrayList<OadrEvent>();
		ArrayList<OadrEvent> oadrEventListDistributeEventMarketCtx2 = new ArrayList<OadrEvent>();

		ArrayList<OadrEvent> oadrEventAllList = new ArrayList<OadrEvent>();

		List<OadrEvent> oadrEventDistribute = oadrDistributeEvent
				.getOadrEvent();
		for (OadrEvent eachOadrEvent : oadrEventDistribute) {
			oadrEventAllList.add(eachOadrEvent);

			if (eachOadrEvent.getEiEvent().getEventDescriptor()
					.getEiMarketContext().getMarketContext()
					.equals(marketContext1)) {
				oadrEventListDistributeEventMarketCtx1.add(eachOadrEvent);
			} else if (eachOadrEvent.getEiEvent().getEventDescriptor()
					.getEiMarketContext().getMarketContext()
					.equals(marketContext2)) {
				oadrEventListDistributeEventMarketCtx2.add(eachOadrEvent);
			}

		}

		int distributeEventSize = oadrEventListDistributeEventMarketCtx1.size();
		for (int i = 0; i < distributeEventSize; i++) {
			OadrEvent eachOadrEvnt = oadrEventListDistributeEventMarketCtx1
					.get(i);
			OadrEvent nextOadrEvnt = null;

			if ((distributeEventSize - 1) != i) {
				nextOadrEvnt = oadrEventListDistributeEventMarketCtx1
						.get(i + 1);
			}

			if (nextOadrEvnt != null) {
				boolean isEventsOverlapping = isEventsOverlapping(eachOadrEvnt,
						nextOadrEvnt);
				if (isEventsOverlapping) {
					LogHelper.addTrace("Conformance Validation Error : Event "
							+ eachOadrEvnt.getEiEvent().getEventDescriptor()
									.getEventID()
							+ " and Event "
							+ nextOadrEvnt.getEiEvent().getEventDescriptor()
									.getEventID() + " overlap");
					return false;
				}

			}
		}

		int distributeEventMrkt2Size = oadrEventListDistributeEventMarketCtx2
				.size();
		for (int i = 0; i < distributeEventMrkt2Size; i++) {
			OadrEvent eachOadrEvnt = oadrEventListDistributeEventMarketCtx2
					.get(i);
			OadrEvent nextOadrEvnt = null;

			if ((distributeEventMrkt2Size - 1) != i) {
				nextOadrEvnt = oadrEventListDistributeEventMarketCtx2
						.get(i + 1);
			}

			if (nextOadrEvnt != null) {
				boolean isEventsOverlapping = isEventsOverlapping(eachOadrEvnt,
						nextOadrEvnt);
				if (isEventsOverlapping) {
					LogHelper.addTrace("Conformance Validation Error : Event "
							+ eachOadrEvnt.getEiEvent().getEventDescriptor()
									.getEventID()
							+ " and Event "
							+ nextOadrEvnt.getEiEvent().getEventDescriptor()
									.getEventID() + " overlap");
					return false;
				}

			}
		}

		int distributeEventAllMrktContext = oadrEventAllList.size();
		for (int i = 0; i < distributeEventAllMrktContext; i++) {
			OadrEvent eachOadrEvnt = oadrEventAllList.get(i);
			OadrEvent nextOadrEvnt = null;

			if ((distributeEventAllMrktContext - 1) != i) {
				nextOadrEvnt = oadrEventAllList.get(i + 1);
			}

			if (nextOadrEvnt != null) {
				boolean isEventsOverlapping = isEventsOverlapping(eachOadrEvnt,
						nextOadrEvnt);
				if (isEventsOverlapping) {
					LogHelper
							.addTrace("WARNING : Conformance Validation : Event "
									+ eachOadrEvnt.getEiEvent()
											.getEventDescriptor().getEventID()
									+ " and Event "
									+ nextOadrEvnt.getEiEvent()
											.getEventDescriptor().getEventID()
									+ " overlap");
					LogHelper
							.addTrace("Conformance check warning: There are overlapping events in different market contexts.");

				}

			}
		}

		return true;
	}

	private static boolean isEventsOverlapping(OadrEvent eachOadrEvnt,
			OadrEvent nextOadrEvnt) {
		EventStatusEnumeratedType currentEventStatus = eachOadrEvnt
				.getEiEvent().getEventDescriptor().getEventStatus();


		if (currentEventStatus.equals(EventStatusEnumeratedType.CANCELLED)
				|| currentEventStatus
						.equals(EventStatusEnumeratedType.COMPLETED)
				|| nextOadrEvnt.equals(EventStatusEnumeratedType.CANCELLED)
				|| nextOadrEvnt.equals(EventStatusEnumeratedType.COMPLETED)) {

			return false;
		}

		XMLGregorianCalendar firstEventStartDate = eachOadrEvnt.getEiEvent()
				.getEiActivePeriod().getProperties().getDtstart().getDateTime();
		XMLGregorianCalendar firstEventEndDate = (XMLGregorianCalendar) firstEventStartDate
				.clone();
		firstEventEndDate.add(OadrUtil.createDuration(eachOadrEvnt.getEiEvent()
				.getEiActivePeriod().getProperties().getDuration()
				.getDuration()));

		XMLGregorianCalendar secondEventStartDate = nextOadrEvnt.getEiEvent()
				.getEiActivePeriod().getProperties().getDtstart().getDateTime();
		XMLGregorianCalendar secondEventEndDate = (XMLGregorianCalendar) secondEventStartDate
				.clone();
		secondEventEndDate.add(OadrUtil.createDuration(nextOadrEvnt
				.getEiEvent().getEiActivePeriod().getProperties().getDuration()
				.getDuration()));

		if (!(firstEventStartDate.compare(secondEventEndDate) > 0 || firstEventEndDate
				.compare(secondEventStartDate) < 0)) {
			return true;
		}
		return false;
	}

	public static boolean isAllPreviousEventsReceived(
			OadrDistributeEventType oadrDistributeEvent, ServiceType service) {

		ArrayList<OadrDistributeEventType> previousDistributeEvents = null;
		if (service.equals(ServiceType.VEN)) {
			previousDistributeEvents = VenToVtnClient
					.getOadrDistributeEvntReceived();
			if (previousDistributeEvents == null
					|| previousDistributeEvents.size() < 1) {
				previousDistributeEvents = VENServerResource
						.getOadrDistributeEventReceivedsList();
			}
		} else if (service.equals(ServiceType.VTN)) {
			previousDistributeEvents = VtnToVenClient
					.getOadrDistributeEvntSent();

		} else if (service.equals(ServiceType.VTN_PULL_MODE)) {
			ArrayList<IDistributeEventAction> distributeEventActionList = TestSession
					.getDistributeEventActionList();
			for (IDistributeEventAction eachDistributeEventAction : distributeEventActionList) {
				if (eachDistributeEventAction.isEventCompleted()) {
					if (previousDistributeEvents == null) {
						previousDistributeEvents = new ArrayList<OadrDistributeEventType>();
					}
					previousDistributeEvents.add(eachDistributeEventAction
							.getDistributeEvent());

				}
			}
			if (!oadrDistributeEvent.getRequestID().startsWith("PING")) {
				int previousSize = 0;
				if (previousDistributeEvents != null) {
					previousSize = previousDistributeEvents.size();
				}
				if (previousDistributeEvents != null && previousSize > 0) {
					previousDistributeEvents.remove(previousSize - 1);
				}
			}

		} else {
			return true;
		}

		if (previousDistributeEvents == null
				|| previousDistributeEvents.size() < 1)
			return true;

		int totalReceivedSize = previousDistributeEvents.size();
		OadrDistributeEventType previousOadrDistributeEvent = previousDistributeEvents
				.get(totalReceivedSize - 1);

		List<OadrEvent> prevOadrList = previousOadrDistributeEvent
				.getOadrEvent();

		for (OadrEvent oadrEventToFind : prevOadrList) {
			if (!isEventFound(oadrEventToFind,
					oadrDistributeEvent.getOadrEvent())) {

				LogHelper
						.addTrace("Conformance Validation Error : Previous Event "
								+ oadrEventToFind.getEiEvent()
										.getEventDescriptor().getEventID()
								+ " was not found in the current OadrDistributeEventType");
				return false;
			}
		}

		return true;
	}

	private static boolean isEventFound(OadrEvent oadrEventToFind,
			List<OadrEvent> oadrListToFindIn) {

		if (oadrEventToFind != null
				&& oadrEventToFind.getEiEvent().getEventDescriptor()
						.getEventStatus()
						.equals(EventStatusEnumeratedType.CANCELLED)) {
			return true;
		}

		if (oadrEventToFind == null || oadrListToFindIn == null) {
			return true;
		}

		if (oadrListToFindIn.size() < 1) {
			return false;
		}

		for (OadrEvent eachOadrEvent : oadrListToFindIn) {

			if (eachOadrEvent
					.getEiEvent()
					.getEventDescriptor()
					.getEventID()
					.equals(oadrEventToFind.getEiEvent().getEventDescriptor()
							.getEventID())) {
				return true;
			}

		}

		CalculatedEventStatusBean eventStatusBean = DistributeEventSignalHelper
				.calculateEventStatus(oadrEventToFind.getEiEvent(),
						oadrListToFindIn.get(0).getEiEvent()
								.getEiActivePeriod().getProperties()
								.getDtstart().getDateTime());

		if (eventStatusBean.getEventStatus().equals(
				EventStatusEnumeratedType.COMPLETED)) {
			return true;
		}

		return false;
	}


	
	public static boolean isDTStartNotInEventSignalsOrEventBaselines(
			OadrDistributeEventType oadrDistributeEvent) {
		
		List<OadrEvent> eiOadrEventList = oadrDistributeEvent.getOadrEvent();

		for (OadrEvent eachOadrEvent : eiOadrEventList) {
			
			EiEventType eiEvent = eachOadrEvent.getEiEvent();
			
			if(eiEvent!=null && eiEvent.getEiEventSignals()!=null && eiEvent.getEiEventSignals().getEiEventBaseline()!=null){
				EiEventBaselineType  eiEventBaselineType  = eiEvent.getEiEventSignals().getEiEventBaseline();
			
				if(eiEventBaselineType!=null && eiEventBaselineType.getIntervals()!=null && eiEventBaselineType.getIntervals().getInterval()!=null){
					List<IntervalType> intervalList = eiEventBaselineType.getIntervals().getInterval();
					for(IntervalType eachInterval:intervalList){
						if(eachInterval.getDtstart()!=null){
						LogHelper
						.addTrace("Conformance Validation Error : dtstart is present in EiEventBaseline.");
						return false;
						}
					}
				}
				
			}
			
			if(eiEvent!=null && eiEvent.getEiEventSignals()!=null && eiEvent.getEiEventSignals().getEiEventSignal()!=null){
				List<EiEventSignalType> iEventSignalList =eiEvent.getEiEventSignals().getEiEventSignal();
				
				for(EiEventSignalType eachEiEventSignal:iEventSignalList){
					List<IntervalType>  intervalList = eachEiEventSignal.getIntervals().getInterval();
					for(IntervalType eachInterval:intervalList){
						Dtstart dtStart = eachInterval.getDtstart();
						if(dtStart!=null){
							LogHelper
							.addTrace("Conformance Validation Error : dtstart is present in EiEventSignal.");
							return false;
						}
					}
				}
			}

		}
		
		return true;
	}
	
	
	public static boolean isSignalIDUnique(
			OadrDistributeEventType oadrDistributeEvent) {
		
		List<OadrEvent> eiOadrEventList = oadrDistributeEvent.getOadrEvent();

		for (OadrEvent eachOadrEvent : eiOadrEventList) {
			
			EiEventType eiEvent = eachOadrEvent.getEiEvent();
			if(eiEvent==null || eiEvent.getEiEventSignals()==null || eiEvent.getEiEventSignals().getEiEventSignal()==null) continue;
			
			List<EiEventSignalType> iEventSignalList =eiEvent.getEiEventSignals().getEiEventSignal();
			
			for(EiEventSignalType eachEiEventSignal:iEventSignalList){

				String signalID = eachEiEventSignal.getSignalID();
				int signalIDCount = signalIDCount(signalID,eachOadrEvent);
				
				if(signalIDCount!=1){
						LogHelper
						.addTrace("Conformance Validation Error : SignalID "+signalID+" is not unique");
						return false;
				}
				
			}
			
		}
		
		return true;
	}
	
	private static int signalIDCount(String signalIDToFind,OadrEvent eachOadrEvent){
		int foundCount = 0;
		
			EiEventType eiEvent = eachOadrEvent.getEiEvent();
			List<EiEventSignalType> iEventSignalList =eiEvent.getEiEventSignals().getEiEventSignal();

			for(EiEventSignalType eachEiEventSignal:iEventSignalList){

				String signalID = eachEiEventSignal.getSignalID();
				
				if(signalIDToFind.equals(signalID)){
					foundCount++;
				}
				
			}
		
		
		return foundCount;
	}
	
	
	public static boolean isVenIDValid(OadrDistributeEventType oadrDistributeEventType, Direction direction) {

		List<OadrEvent>  oadrEventList = oadrDistributeEventType.getOadrEvent();
		
		for(OadrEvent eachOadrEvent:oadrEventList){
			if(eachOadrEvent.getEiEvent()==null || eachOadrEvent.getEiEvent().getEiTarget()==null) continue;
			
			List<String> venIDList = eachOadrEvent.getEiEvent().getEiTarget().getVenID();
			
			for(String eachVENID:venIDList){
				if(!OadrUtil.isVENIDValid(eachVENID,direction)){
					return false;
				}
				
			}
		}

		return true;
	}
	
	
	public static void main(String []args){
		DefaultDistributeEvent_004Action action004=new DefaultDistributeEvent_004Action();
		OadrDistributeEventType  oadrDistributeEventType  = action004.getDistributeEvent();
	
		isSignalTypeAndUnitValidForSignalName(oadrDistributeEventType);
	}
	
	public static boolean isSignalTypeAndUnitValidForSignalName(
			OadrDistributeEventType oadrDistributeEvent) {
		List<OadrEvent> eiOadrEventList = oadrDistributeEvent.getOadrEvent();
		for (OadrEvent eachOadrEvent : eiOadrEventList) {
			
			List<EiEventSignalType> eiEventSignalList = eachOadrEvent.getEiEvent()
					.getEiEventSignals().getEiEventSignal();

			for (EiEventSignalType eachEiEventSignal : eiEventSignalList) {
				
				if(eachEiEventSignal.getSignalName()==null){
					LogHelper
					.addTrace("Conformance Validation Error : SignalName is empty");
					return false;
				}
			

				if(eachEiEventSignal.getSignalName().equalsIgnoreCase("SIMPLE")){
					if(!eachEiEventSignal.getSignalType().equals(SignalTypeEnumeratedType.LEVEL)){
						LogHelper
						.addTrace("Conformance Validation Error : For SignalName SIMPLE the SignalType received is "+eachEiEventSignal.getSignalType()+". Expected is "+SignalTypeEnumeratedType.LEVEL);
						return false;
					}
					
					
					if(eachEiEventSignal.getItemBase()!=null){
						LogHelper
						.addTrace("Conformance Validation Error : For SignalName SIMPLE ItemBase should not be present");
						return false;
					}
					
				}
				
				if(eachEiEventSignal.getSignalName().equals("ELECTRICITY_PRICE")){
					
					if(!eachEiEventSignal.getSignalType().equals(SignalTypeEnumeratedType.PRICE)){
						LogHelper
						.addTrace("Conformance Validation Error : For SignalName ELECTRICTY_PRICE the SignalType received is "+eachEiEventSignal.getSignalType()+". Expected is "+SignalTypeEnumeratedType.PRICE);
						return false;
					}
					
					if(eachEiEventSignal.getItemBase()==null || eachEiEventSignal.getItemBase().getValue()==null || !eachEiEventSignal.getItemBase().getValue().getClass().equals(CurrencyType.class)){
						LogHelper
						.addTrace("Conformance Validation Error : Expected CurrencyType in ItemBase");
						return false;
					}
					
					CurrencyItemDescriptionType currencyItemDescriptionType = ((CurrencyType)eachEiEventSignal.getItemBase().getValue()).getItemDescription();
					
					if(!currencyItemDescriptionType.equals(com.qualitylogic.openadr.core.signal.CurrencyItemDescriptionType.CURRENCY_PER_K_WH)){
						LogHelper
						.addTrace("Conformance Validation Error : For SignalName ELECTRICTY_PRICE the ItemBase received is "+eachEiEventSignal.getItemBase().getValue().getClass()+". Expected is "+com.qualitylogic.openadr.core.signal.CurrencyItemDescriptionType.CURRENCY_PER_K_WH+".");
						return false;
					}
					
				}
				
	
				
				if(eachEiEventSignal.getSignalName().equals("LOAD_DISPATCH")){
					
					if(!eachEiEventSignal.getSignalType().equals(SignalTypeEnumeratedType.SETPOINT)){
						LogHelper
						.addTrace("Conformance Validation Error : For SignalName LOAD_DISPATCH the SignalType received is "+eachEiEventSignal.getSignalType()+". Expected is SETPOINT");
						return false;
					}
					
					if(!eachEiEventSignal.getItemBase().getValue().getClass().equals(PowerRealType.class)&&!eachEiEventSignal.getItemBase().getValue().getClass().equals(PowerApparentType.class)&&!eachEiEventSignal.getItemBase().getValue().getClass().equals(PowerReactiveType.class)){
						LogHelper
						.addTrace("Conformance Validation Error : For SignalName LOAD_DISPATCH the ItemBase received is "+eachEiEventSignal.getItemBase().getValue().getClass()+". Expected is PowerReal or PowerApparent or PowerReactive");
						return false;
					}
				}
				
			}
		}
		return true;
	}
	

	public static boolean isBaselineDurationValid(OadrDistributeEventType oadrDistributeEvent) {
		List<OadrEvent> oadrEventList = oadrDistributeEvent.getOadrEvent();

		for (OadrEvent eachOadrEvent : oadrEventList) {
			
			if(eachOadrEvent.getEiEvent()==null || eachOadrEvent.getEiEvent().getEiEventSignals()==null || eachOadrEvent.getEiEvent().getEiEventSignals().getEiEventBaseline()==null){
				continue;
			}
										
			if(eachOadrEvent.getEiEvent().getEiEventSignals().getEiEventBaseline()!=null){
				EiEventBaselineType baseline = eachOadrEvent.getEiEvent().getEiEventSignals().getEiEventBaseline();

				
				Intervals interval = baseline.getIntervals();
				
				
				List<IntervalType> intervalList = interval.getInterval();

				String baselineDurationStr = baseline.getDuration().getDuration();

				Duration baselineDuration = OadrUtil
						.createDuration(baselineDurationStr);

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
					boolean isEqual = duration.equals(baselineDuration);
					if (!isEqual) {
						LogHelper
								.addTrace("\nConformance Validation Error : Sum of EiEventBaseline Interval Duration is "
										+ duration
										+ ", but EventSignal:eiEventBaseline:duration in payload is "
										+ baselineDuration
										+ " for the Event : "
										+ eachOadrEvent.getEiEvent()
												.getEventDescriptor()
												.getEventID() + "\n");

						return false;

					}
				}
				
			}
			
		}
		return true;
	}

	public static boolean isEINotificationSubElementOfActivePeriod(OadrDistributeEventType oadrDistributeEventType) {

		List<OadrEvent>  oadrEventList = oadrDistributeEventType.getOadrEvent();
		
		for(OadrEvent eachOadrEvent:oadrEventList){
			if(eachOadrEvent.getEiEvent()==null || eachOadrEvent.getEiEvent().getEiActivePeriod()==null) continue;
			
			if(eachOadrEvent.getEiEvent().getEiActivePeriod().getProperties()==null || eachOadrEvent.getEiEvent().getEiActivePeriod().getProperties().getXEiNotification()==null || eachOadrEvent.getEiEvent().getEiActivePeriod().getProperties().getXEiNotification().getDuration()==null){
				LogHelper
				.addTrace("Conformance Validation Error : eiNotification is not found under the activePeriod for the event with Event ID : "
						+ eachOadrEvent.getEiEvent()
						.getEventDescriptor()
						.getEventID() );
			}	
		}
		return true;
	}
	
	
	public static boolean isDeviceClassValuesPresent(OadrDistributeEventType oadrDistributeEvent) {
		List<OadrEvent> oadrEventList = oadrDistributeEvent.getOadrEvent();

		for (OadrEvent eachOadrEvent : oadrEventList) {
			
			if(eachOadrEvent.getEiEvent()==null || eachOadrEvent.getEiEvent().getEiEventSignals()==null || eachOadrEvent.getEiEvent().getEiEventSignals().getEiEventSignal()==null){
				continue;
			}
				
			List<EiEventSignalType> eiEventSignalList = eachOadrEvent.getEiEvent().getEiEventSignals().getEiEventSignal();
			
			for(EiEventSignalType eachEiEventSignalType:eiEventSignalList){
				if(eachEiEventSignalType.getEiTarget()==null || eachEiEventSignalType.getEiTarget().getEndDeviceAsset()==null) continue;
				
				if(!OadrUtil.isDeviceClassFound(eachEiEventSignalType.getEiTarget().getEndDeviceAsset())){
					return false;
				}

			}	
		}
		return true;
	}
	

	
}