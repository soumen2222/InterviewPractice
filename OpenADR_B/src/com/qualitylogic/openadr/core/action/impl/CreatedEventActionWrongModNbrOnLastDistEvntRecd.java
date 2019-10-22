package com.qualitylogic.openadr.core.action.impl;

import java.util.ArrayList;
import java.util.List;

import com.qualitylogic.openadr.core.base.Base_CreatedEventAction;
import com.qualitylogic.openadr.core.common.TestSession;
import com.qualitylogic.openadr.core.signal.OadrCreatedEventType;
import com.qualitylogic.openadr.core.signal.OadrDistributeEventType;
import com.qualitylogic.openadr.core.signal.EventResponses.EventResponse;
import com.qualitylogic.openadr.core.signal.helper.CreatedEventHelper;
import com.qualitylogic.openadr.core.signal.helper.SchemaHelper;
import com.qualitylogic.openadr.core.util.Trace;
import com.qualitylogic.openadr.core.ven.VENServerResource;

public class CreatedEventActionWrongModNbrOnLastDistEvntRecd extends
		Base_CreatedEventAction {

	boolean includeModificationNumber;
	boolean isOptIn;
	String responseDescription = null;

	public CreatedEventActionWrongModNbrOnLastDistEvntRecd(
			boolean includeModificationNumber, boolean isOptIn) {
		this.includeModificationNumber = includeModificationNumber;
		this.isOptIn = isOptIn;
	}

	public CreatedEventActionWrongModNbrOnLastDistEvntRecd(
			boolean includeModificationNumber, boolean isOptIn,
			String responseDescription) {
		this.includeModificationNumber = includeModificationNumber;
		this.isOptIn = isOptIn;
		this.responseDescription = responseDescription;
	}

	public String getCreateEvent() {
		String strOadrCreatedEvent = null;
		if (oadrCreatedEvent == null) {
			ArrayList<OadrDistributeEventType> oadrDistributeEventList = VENServerResource
					.getOadrDistributeEventReceivedsList();
			OadrDistributeEventType OadrDistributeEventType = oadrDistributeEventList
					.get(oadrDistributeEventList.size() - 1);

			try {
				String strDistributeEvent = SchemaHelper
						.getDistributeEventAsString(OadrDistributeEventType);
				ArrayList<String> distributeEventList = new ArrayList<String>();
				distributeEventList.add(strDistributeEvent);

				strOadrCreatedEvent = CreatedEventHelper.createCreatedEvent(
						distributeEventList, includeModificationNumber,
						isOptIn, responseDescription);
				OadrCreatedEventType createdEvent = CreatedEventHelper
						.createCreatedEventFromString(strOadrCreatedEvent);
				List<EventResponse> eventResponses = createdEvent
						.getEiCreatedEvent().getEventResponses()
						.getEventResponse();

				for (EventResponse eachEventResponse : eventResponses) {
					eachEventResponse.getQualifiedEventID()
							.setModificationNumber(new Long(10));
				}

				strOadrCreatedEvent = SchemaHelper
						.getCreatedEventAsString(createdEvent);

			} catch (Exception e) {
				Trace trace = TestSession.getTraceObj();

				trace.getLogFileContentTrace().append(e.getMessage());
				e.printStackTrace();
			}
		}
		return strOadrCreatedEvent;
	}

}