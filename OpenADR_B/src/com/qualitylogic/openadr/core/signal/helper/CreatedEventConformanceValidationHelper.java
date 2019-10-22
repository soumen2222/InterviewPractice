package com.qualitylogic.openadr.core.signal.helper;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBException;

import com.qualitylogic.openadr.core.action.DistributeEventActionList;
import com.qualitylogic.openadr.core.action.IDistributeEventAction;
import com.qualitylogic.openadr.core.signal.EventResponses;
import com.qualitylogic.openadr.core.signal.OadrCreatedEventType;
import com.qualitylogic.openadr.core.signal.OadrDistributeEventType;
import com.qualitylogic.openadr.core.signal.EventResponses.EventResponse;

import com.qualitylogic.openadr.core.util.OadrUtil;
import com.qualitylogic.openadr.core.util.PropertiesFileReader;

public class CreatedEventConformanceValidationHelper {

	// Additional Checks
	public static boolean isResponseCodeSuccess(
			OadrCreatedEventType oadrCreatedEvent) {
/*		if (oadrCreatedEvent != null
				&& oadrCreatedEvent.getEiCreatedEvent() != null
				&& oadrCreatedEvent.getEiCreatedEvent().getEiResponse() != null
				&& !oadrCreatedEvent.getEiCreatedEvent().getEiResponse()
						.getResponseCode().startsWith("2")) {
			return false;
		}*/
		
		if (oadrCreatedEvent.getEiCreatedEvent() == null) return true;
				
		return  OadrUtil.isSuccessResponse(oadrCreatedEvent.getEiCreatedEvent().getEiResponse());
	}

	public static boolean isEventResponseCodeSuccess(
			OadrCreatedEventType oadrCreatedEvent) {
		if (oadrCreatedEvent == null
				|| oadrCreatedEvent.getEiCreatedEvent() == null
				|| oadrCreatedEvent.getEiCreatedEvent().getEventResponses() == null
				|| oadrCreatedEvent.getEiCreatedEvent().getEventResponses()
						.getEventResponse() == null)
			return true;
		List<EventResponse> eventResponseList = oadrCreatedEvent
				.getEiCreatedEvent().getEventResponses().getEventResponse();
		for (EventResponse eachEventResponse : eventResponseList) {
			if (!eachEventResponse.getResponseCode().startsWith("2")) {
				return false;
			}
		}

		return true;
	}

	public static boolean isRequestIDValid(OadrCreatedEventType oadrCreatedEvent) {
		if (oadrCreatedEvent == null)
			return false;
		ArrayList<IDistributeEventAction> distributeEventActionSentList = DistributeEventActionList
				.getDistributeEventActionCompletedList();
		ArrayList<OadrDistributeEventType> oadrDistributeEventList = new ArrayList<OadrDistributeEventType>();

		for (IDistributeEventAction eachDistributeEventAction : distributeEventActionSentList) {
			oadrDistributeEventList.add(eachDistributeEventAction
					.getDistributeEvent());
		}

		ArrayList<String> distributeEventSentReqIdList = DistributeEventSignalHelper
				.getRequestIDs(oadrDistributeEventList);

		ArrayList<String> receivedRequestIDList = CreatedEventHelper
				.getRequestIDs(oadrCreatedEvent);

		if (receivedRequestIDList.size() > 0) {

			if (oadrCreatedEvent.getEiCreatedEvent().getEiResponse()
					.getRequestID() != null
					&& oadrCreatedEvent.getEiCreatedEvent().getEiResponse()
							.getRequestID().length() > 0) {
				LogHelper.addTrace("Conformance Validation Error : RequestID "
						+ oadrCreatedEvent.getEiCreatedEvent().getEiResponse()
								.getRequestID()
						+ " present in eiResponse:requestID.");
				LogHelper
						.addTrace("Conformance check failed : The root eiResponse:requestID in oadrCreatedEvent shall be left empty if the payload contains eventResponses.");

				return false;
			}
		}

		for (String eachReceivedRequestID : receivedRequestIDList) {
			if (eachReceivedRequestID.startsWith("PING_"))
				continue;
			if (!OadrUtil.isIDFoundInList(eachReceivedRequestID,
					distributeEventSentReqIdList)){
				LogHelper
						.addTrace("Conformance Validation Error : Unable to map the RequestID "
								+ eachReceivedRequestID
								+ " to any DistributeEvent sent.");
				LogHelper
						.addTrace("Conformance check failed : A VEN receiving an OadrDistributeEventType event must use the received requestID value in the EiCreatedEvent.");

				return false;
			}
		}
		return true;

	}

	public static boolean isEventIDValid(OadrCreatedEventType oadrCreatedEvent) {
		if (oadrCreatedEvent == null)
			return false;
		ArrayList<IDistributeEventAction> distributeEventActionSentList = DistributeEventActionList
				.getDistributeEventActionCompletedList();
		ArrayList<OadrDistributeEventType> oadrDistributeEventList = new ArrayList<OadrDistributeEventType>();
		for (IDistributeEventAction eachDistributeEventAction : distributeEventActionSentList) {
			oadrDistributeEventList.add(eachDistributeEventAction
					.getDistributeEvent());
		}

		ArrayList<String> expectedEventIDList = DistributeEventSignalHelper
				.getEventIDs(oadrDistributeEventList);

		ArrayList<String> receivedEventIDList = CreatedEventHelper
				.getEventIDs(oadrCreatedEvent);

		for (String eachReceivedEventID : receivedEventIDList) {
			if (!OadrUtil.isIDFoundInList(eachReceivedEventID,
					expectedEventIDList)) {
				LogHelper
						.addTrace("\nConformance Validation Error : Unable to map the received EventID "
								+ eachReceivedEventID
								+ " to any Distribute Event\n");

				return false;
			}
		}
		return true;

	}

	public static void main(String[] args) throws FileNotFoundException,
			UnsupportedEncodingException, JAXBException {
		String fileName = "oadrCreatedEvent_oneEvent_optIn_Default.xml";
		PropertiesFileReader propertiesFileReader = new PropertiesFileReader();
		String testDataPath = propertiesFileReader.get("testDataPath");

		new CreatedEventHelper();
		OadrCreatedEventType createdEvent = CreatedEventHelper
				.loadOadrCreatedEvent(testDataPath + fileName);
		System.out.println(isEIResponsesValid(createdEvent));
	}

	public static boolean isEIResponsesValid(OadrCreatedEventType oadrCreatedEvent) {
		if (oadrCreatedEvent == null)
			return false;
		String errorCode = oadrCreatedEvent.getEiCreatedEvent().getEiResponse()
				.getResponseCode();

		if (errorCode.startsWith("2")) {
			EventResponses eventResponses = oadrCreatedEvent
					.getEiCreatedEvent().getEventResponses();

			if (eventResponses == null
					|| eventResponses.getEventResponse() == null
					|| eventResponses.getEventResponse().size() < 1) {
				return false;
			}
		}

		return true;
	}

	public static boolean isVenIDValid(OadrCreatedEventType oadrCreatedEvent) {

		if (oadrCreatedEvent == null)
			return false;
		String venIDInPayload = "";

		if (oadrCreatedEvent.getEiCreatedEvent()!=null && oadrCreatedEvent.getEiCreatedEvent().getVenID() != null) {
			venIDInPayload = oadrCreatedEvent.getEiCreatedEvent().getVenID();
		}

		PropertiesFileReader ctnPropertiesFileReader = new PropertiesFileReader();
		String venIDInConfig = ctnPropertiesFileReader.getVenID();

		if (venIDInPayload.equals(venIDInConfig)) {
			return true;
		} else {
			LogHelper
					.addTrace("\nConformance Validation Error : Unknown VEN ID "
							+ venIDInPayload + " has been received\n");

			return false;
		}

	}
}
