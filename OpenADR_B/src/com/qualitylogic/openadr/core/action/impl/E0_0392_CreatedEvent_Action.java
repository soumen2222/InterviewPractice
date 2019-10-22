package com.qualitylogic.openadr.core.action.impl;

import java.util.ArrayList;
import java.util.List;

import com.qualitylogic.openadr.core.base.Base_CreatedEventAction;
import com.qualitylogic.openadr.core.common.TestSession;
import com.qualitylogic.openadr.core.signal.OadrCreatedEventType;
import com.qualitylogic.openadr.core.signal.OadrDistributeEventType;
import com.qualitylogic.openadr.core.signal.OptTypeType;
import com.qualitylogic.openadr.core.signal.EventResponses.EventResponse;
import com.qualitylogic.openadr.core.signal.helper.CreatedEventHelper;
import com.qualitylogic.openadr.core.signal.helper.LogHelper;
import com.qualitylogic.openadr.core.signal.helper.SchemaHelper;
import com.qualitylogic.openadr.core.util.Trace;
import com.qualitylogic.openadr.core.ven.VENServerResource;

public class E0_0392_CreatedEvent_Action extends Base_CreatedEventAction {

	boolean includeModificationNumber;
	boolean isOptInFirstEvent;
	boolean isOptInSecondEvent;

	public E0_0392_CreatedEvent_Action(boolean includeModificationNumber,
			boolean isOptInFirstEvent, boolean isOptInSecondEvent) {
		this.includeModificationNumber = includeModificationNumber;
		this.isOptInFirstEvent = isOptInFirstEvent;
		this.isOptInSecondEvent = isOptInSecondEvent;
	}

	public String getCreateEvent() {
		String strOadrCreatedEvent = null;
		if (oadrCreatedEvent == null) {
			ArrayList<OadrDistributeEventType> oadrDistributeEventList = VENServerResource
					.getOadrDistributeEventReceivedsList();
			OadrDistributeEventType oadrDistributeEvent = oadrDistributeEventList
					.get(oadrDistributeEventList.size() - 1);

			if (oadrDistributeEvent.getOadrEvent() == null
					|| oadrDistributeEvent.getOadrEvent().size() < 1) {
				LogHelper
						.addTrace("Expected two Events in OadrDistributeEventType; Unable to create OadrCreatedEvent");
				LogHelper.addTrace("Created Error OadrCreatedEvent");
				return CreatedEventHelper.createCreatedEventError();
			}

			try {
				String strDistributeEvent = SchemaHelper
						.getDistributeEventAsString(oadrDistributeEvent);
				ArrayList<String> distributeEventList = new ArrayList<String>();
				distributeEventList.add(strDistributeEvent);

				strOadrCreatedEvent = CreatedEventHelper.createCreatedEvent(
						distributeEventList, includeModificationNumber, true,
						null);

				OadrCreatedEventType oadrCreatedEvent = CreatedEventHelper
						.createCreatedEventFromString(strOadrCreatedEvent);
				List<EventResponse> eventResponses = oadrCreatedEvent
						.getEiCreatedEvent().getEventResponses()
						.getEventResponse();
				if (isOptInFirstEvent) {
					eventResponses.get(0).setOptType(OptTypeType.OPT_IN);
				} else {
					eventResponses.get(0).setOptType(OptTypeType.OPT_OUT);
				}

				if (oadrDistributeEvent.getOadrEvent().size() > 1) {
					if (isOptInSecondEvent) {
						eventResponses.get(1).setOptType(OptTypeType.OPT_IN);
					} else {
						eventResponses.get(1).setOptType(OptTypeType.OPT_OUT);
					}
				}

				strOadrCreatedEvent = SchemaHelper
						.getCreatedEventAsString(oadrCreatedEvent);
			} catch (Exception e) {
				Trace trace = TestSession.getTraceObj();

				trace.getLogFileContentTrace().append(e.getMessage());
				e.printStackTrace();
			}
		}
		return strOadrCreatedEvent;
	}

	public boolean isPreConditionsMet() {
		ArrayList<OadrDistributeEventType> distributeEventList = VENServerResource
				.getOadrDistributeEventReceivedsList();
		int size = distributeEventList.size();
		OadrDistributeEventType oadrDistributeEvent = distributeEventList.get(size);
		int oadrEventSize = oadrDistributeEvent.getOadrEvent().size();

		if (oadrEventSize == 1) {
			return true;
		}

		return false;
	}
}