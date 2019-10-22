package com.qualitylogic.openadr.core.action.impl;

import java.util.ArrayList;

import com.qualitylogic.openadr.core.base.Base_CreatedEventAction;
import com.qualitylogic.openadr.core.common.TestSession;
import com.qualitylogic.openadr.core.signal.OadrDistributeEventType;
import com.qualitylogic.openadr.core.signal.helper.CreatedEventHelper;
import com.qualitylogic.openadr.core.signal.helper.SchemaHelper;
import com.qualitylogic.openadr.core.util.Trace;
import com.qualitylogic.openadr.core.ven.VENServerResource;

public class CreatedEventAction_AccumulatedDistributeEventsPrecondition extends
		Base_CreatedEventAction {

	boolean includeModificationNumber;
	boolean isOptIn;
	int preconditionEventAccumulatedTotal;

	public CreatedEventAction_AccumulatedDistributeEventsPrecondition(
			boolean includeModificationNumber, boolean isOptIn,
			int preconditionEventAccumulatedTotal) {
		this.includeModificationNumber = includeModificationNumber;
		this.isOptIn = isOptIn;
		this.preconditionEventAccumulatedTotal = preconditionEventAccumulatedTotal;
	}

	@Override
	public boolean isPreConditionsMet() {
		ArrayList<OadrDistributeEventType> distributedEventsReceived = VENServerResource
				.getOadrDistributeEventReceivedsList();
		int totalEventsReceived = 0;
		for (OadrDistributeEventType eachOadrDistributeEvent : distributedEventsReceived) {
			totalEventsReceived = totalEventsReceived
					+ eachOadrDistributeEvent.getOadrEvent().size();
		}
		if (totalEventsReceived >= preconditionEventAccumulatedTotal) {
			return true;
		} else {
			return true;

		}
	}

	public String getCreateEvent() {
		String strOadrCreatedEvent = null;
		if (oadrCreatedEvent == null) {
			ArrayList<OadrDistributeEventType> oadrDistributeEventList = VENServerResource
					.getOadrDistributeEventReceivedsList();

			try {
				ArrayList<String> distributeEventList = new ArrayList<String>();

				for (OadrDistributeEventType eachOadrDistributeEvent : oadrDistributeEventList) {
					String strDistributeEvent = SchemaHelper
							.getDistributeEventAsString(eachOadrDistributeEvent);
					distributeEventList.add(strDistributeEvent);

				}

				strOadrCreatedEvent = CreatedEventHelper.createCreatedEvent(
						distributeEventList, includeModificationNumber,
						isOptIn, null);
			} catch (Exception e) {
				Trace trace = TestSession.getTraceObj();

				trace.getLogFileContentTrace().append(e.getMessage());
				e.printStackTrace();
			}
		}
		return strOadrCreatedEvent;
	}
}