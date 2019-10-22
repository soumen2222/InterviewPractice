package com.qualitylogic.openadr.core.signal.helper;

import java.util.ArrayList;
import java.util.List;

import com.qualitylogic.openadr.core.action.IDistributeEventAction;
import com.qualitylogic.openadr.core.bean.CreatedEventBean;
import com.qualitylogic.openadr.core.common.TestSession;
import com.qualitylogic.openadr.core.signal.OadrCreatedEventType;
import com.qualitylogic.openadr.core.signal.OadrDistributeEventType;
import com.qualitylogic.openadr.core.signal.OadrRequestEventType;
import com.qualitylogic.openadr.core.signal.ResponseRequiredType;
import com.qualitylogic.openadr.core.signal.OadrDistributeEventType.OadrEvent;
import com.qualitylogic.openadr.core.util.OadrUtil;
import com.qualitylogic.openadr.core.util.PropertiesFileReader;

public class RequestEventConformanceValidationHelper {


	public static boolean isVenIDValid(OadrRequestEventType oadrRequestEvent) {

		if (oadrRequestEvent == null
				|| oadrRequestEvent.getEiRequestEvent() == null || oadrRequestEvent.getEiRequestEvent().getVenID()==null)
			return false;

		String venIDInPayload = oadrRequestEvent.getEiRequestEvent().getVenID();

		PropertiesFileReader ctnPropertiesFileReader = new PropertiesFileReader();
		String venIDInConfig = ctnPropertiesFileReader.getVenID();

		if (venIDInPayload.equals(venIDInConfig)) {
			return true;
		} else {
			LogHelper
					.addTrace("\nConformance Validation Error : Unknown VEN ID "
							+ venIDInPayload
							+ " has been received. Expected is "
							+ venIDInConfig + "\n");
			return false;
		}

	}

	public static boolean isAllPreviousCreatedEventsReceived() {

		ArrayList<OadrDistributeEventType> distributeEventsReceived = new ArrayList<OadrDistributeEventType>();
		ArrayList<IDistributeEventAction> distributeEventAction = TestSession
				.getDistributeEventActionList();

		for (IDistributeEventAction eachDistributeEventAction : distributeEventAction) {
			if (eachDistributeEventAction.isEventCompleted()) {
			//if (eachDistributeEventAction.isEventPickedUpFromOadrPoll()) {
				distributeEventsReceived.add(eachDistributeEventAction
						.getDistributeEvent());
			}
		}
		if (distributeEventsReceived != null
				&& distributeEventsReceived.size() > 0) {

			ArrayList<OadrCreatedEventType> recievedOadrCreatedEventList = TestSession
					.getCreatedEventReceivedList();
			ArrayList<CreatedEventBean> createdEventBeanList = OadrUtil
					.transformToCreatedEventBeanList(recievedOadrCreatedEventList);

			int size = distributeEventsReceived.size();
			OadrDistributeEventType previousOadrDistributeEvent = distributeEventsReceived
					.get(size - 1);

			String distributeEventrequestID = previousOadrDistributeEvent
					.getRequestID();
			List<OadrEvent> OadrEventList = previousOadrDistributeEvent
					.getOadrEvent();
			for (OadrEvent eachOadrEvent : OadrEventList) {
				boolean isCreatedEventReceived = false;
				String distEventEventID = eachOadrEvent.getEiEvent()
						.getEventDescriptor().getEventID();
				long distEventmodNumber = eachOadrEvent.getEiEvent()
						.getEventDescriptor().getModificationNumber();

				if (eachOadrEvent.getOadrResponseRequired().equals(
						ResponseRequiredType.ALWAYS)) {

					isCreatedEventReceived = CreatedEventHelper
							.isCreatedEventFound(distributeEventrequestID,
									eachOadrEvent, createdEventBeanList);

					if (!isCreatedEventReceived) {

						LogHelper
								.addTrace("Conformance Validation Error : CreatedEvent has not been received. ResponseRequiredType.ALWAYS was recieved for OadrDistributeEvent with RequestID '"
										+ distributeEventrequestID
										+ "', EventID '"
										+ distEventEventID
										+ "' and Modification Number '"
										+ distEventmodNumber + "'.");
						return false;
					}

				} else {
							isCreatedEventReceived = CreatedEventHelper
							.isCreatedEventFound(distributeEventrequestID,
									eachOadrEvent, createdEventBeanList);

					if (isCreatedEventReceived) {
						LogHelper
								.addTrace("Conformance Validation Error : CreatedEvent was received. ResponseRequiredType.NEVER was recieved for OadrDistributeEvent with RequestID '"
										+ distributeEventrequestID
										+ "', EventID '"
										+ distEventEventID
										+ "' and Modification Number '"
										+ distEventmodNumber + "'.");
						return false;
					}

				}
			}

		}
		return true;

	}

}
