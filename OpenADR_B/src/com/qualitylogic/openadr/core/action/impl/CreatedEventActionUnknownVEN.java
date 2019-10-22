package com.qualitylogic.openadr.core.action.impl;

import java.util.ArrayList;

import com.qualitylogic.openadr.core.base.Base_CreatedEventAction;
import com.qualitylogic.openadr.core.common.TestSession;
import com.qualitylogic.openadr.core.signal.OadrCreatedEventType;
import com.qualitylogic.openadr.core.signal.OadrDistributeEventType;
import com.qualitylogic.openadr.core.signal.ResponseRequiredType;
import com.qualitylogic.openadr.core.signal.helper.CreatedEventHelper;
import com.qualitylogic.openadr.core.signal.helper.LogHelper;
import com.qualitylogic.openadr.core.signal.helper.SchemaHelper;
import com.qualitylogic.openadr.core.util.Trace;
import com.qualitylogic.openadr.core.ven.VENServerResource;

public class CreatedEventActionUnknownVEN extends Base_CreatedEventAction {

	boolean includeModificationNumber = true;
	boolean isOptIn;

	public CreatedEventActionUnknownVEN(boolean isOptIn) {
		this.isOptIn = isOptIn;
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
						distributeEventList, true, isOptIn, null);

				OadrCreatedEventType createdEvent = CreatedEventHelper
						.createCreatedEventFromString(strOadrCreatedEvent);
				createdEvent.getEiCreatedEvent().setVenID("UNKNOWN_VEN_ID");
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

	@Override
	public boolean isPreConditionsMet() {
		ArrayList<OadrDistributeEventType> oadrDistributeEventList = VENServerResource
				.getOadrDistributeEventReceivedsList();
		OadrDistributeEventType OadrDistributeEventType = oadrDistributeEventList
				.get(oadrDistributeEventList.size() - 1);

		if (OadrDistributeEventType.getOadrEvent().get(0).getOadrResponseRequired()
				.equals(ResponseRequiredType.NEVER)) {
			LogHelper
					.addTrace("Created Event will not be returned as the DistributeEvent ResponseRequired is NEVER");
			return false;
		} else {
			return true;
		}

	}

}