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

public class CreatedEvent_TwoEvent_Action extends Base_CreatedEventAction {

	boolean includeModificationNumber;
	boolean isOptInFirstEvent;
	boolean isOptInSecondEvent;

	public CreatedEvent_TwoEvent_Action(boolean includeModificationNumber,
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
			OadrDistributeEventType OadrDistributeEventType = oadrDistributeEventList
					.get(oadrDistributeEventList.size() - 1);

			if (OadrDistributeEventType.getOadrEvent() == null
					|| OadrDistributeEventType.getOadrEvent().size() < 1) {
				LogHelper
						.addTrace("Expected two Events in OadrDistributeEventType; Unable to create OadrCreatedEventType");
				LogHelper.addTrace("Created Error OadrCreatedEventType");
				return CreatedEventHelper.createCreatedEventError();
			}

			try {
				String strDistributeEvent = SchemaHelper
						.getDistributeEventAsString(OadrDistributeEventType);
				ArrayList<String> distributeEventList = new ArrayList<String>();
				distributeEventList.add(strDistributeEvent);

				strOadrCreatedEvent = CreatedEventHelper.createCreatedEvent(
						distributeEventList, includeModificationNumber, true,
						null);

				OadrCreatedEventType OadrCreatedEventType = CreatedEventHelper
						.createCreatedEventFromString(strOadrCreatedEvent);
				List<EventResponse> eventResponses = OadrCreatedEventType
						.getEiCreatedEvent().getEventResponses()
						.getEventResponse();
				if (isOptInFirstEvent) {
					eventResponses.get(0).setOptType(OptTypeType.OPT_IN);
				} else {
					eventResponses.get(0).setOptType(OptTypeType.OPT_OUT);
				}

				if (OadrDistributeEventType.getOadrEvent().size() > 1) {
					if (isOptInSecondEvent) {
						eventResponses.get(1).setOptType(OptTypeType.OPT_IN);
					} else {
						eventResponses.get(1).setOptType(OptTypeType.OPT_OUT);
					}
				}

				strOadrCreatedEvent = SchemaHelper
						.getCreatedEventAsString(OadrCreatedEventType);
			} catch (Exception e) {
				Trace trace = TestSession.getTraceObj();

				trace.getLogFileContentTrace().append(e.getMessage());
				e.printStackTrace();
			}
		}
		return strOadrCreatedEvent;
	}

}