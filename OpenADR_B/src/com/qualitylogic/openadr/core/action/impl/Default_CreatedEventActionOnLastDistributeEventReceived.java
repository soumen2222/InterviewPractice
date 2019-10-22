package com.qualitylogic.openadr.core.action.impl;

import java.util.ArrayList;

import com.qualitylogic.openadr.core.action.ICreateOptEventAction;
import com.qualitylogic.openadr.core.base.Base_CreatedEventAction;
import com.qualitylogic.openadr.core.common.TestSession;
import com.qualitylogic.openadr.core.signal.OadrDistributeEventType;
import com.qualitylogic.openadr.core.signal.ResponseRequiredType;
import com.qualitylogic.openadr.core.signal.helper.CreatedEventHelper;
import com.qualitylogic.openadr.core.signal.helper.LogHelper;
import com.qualitylogic.openadr.core.signal.helper.SchemaHelper;
import com.qualitylogic.openadr.core.util.LogResult;
import com.qualitylogic.openadr.core.util.Trace;
import com.qualitylogic.openadr.core.ven.VENServerResource;

public class Default_CreatedEventActionOnLastDistributeEventReceived extends
		Base_CreatedEventAction {

	boolean includeModificationNumber;
	boolean isOptIn;
	String responseDescription = null;

	public Default_CreatedEventActionOnLastDistributeEventReceived(
			boolean includeModificationNumber, boolean isOptIn) {
		this.includeModificationNumber = includeModificationNumber;
		this.isOptIn = isOptIn;
	}

	public Default_CreatedEventActionOnLastDistributeEventReceived(
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

		if (OadrDistributeEventType.getOadrEvent() == null
				|| OadrDistributeEventType.getOadrEvent().size() < 1) {
			LogHelper.setResult(LogResult.FAIL);
			LogHelper
					.addTrace("Created Event will not be returned as no OadrEvent is present in the OadrDistributeEventType");
			return false;
		}

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