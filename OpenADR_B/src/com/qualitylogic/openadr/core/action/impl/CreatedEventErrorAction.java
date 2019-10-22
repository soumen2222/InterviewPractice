package com.qualitylogic.openadr.core.action.impl;

import com.qualitylogic.openadr.core.base.Base_CreatedEventAction;
import com.qualitylogic.openadr.core.common.TestSession;
import com.qualitylogic.openadr.core.signal.helper.CreatedEventHelper;
import com.qualitylogic.openadr.core.util.Trace;

public class CreatedEventErrorAction extends Base_CreatedEventAction {
	private String responseCode;
	
	public CreatedEventErrorAction() {
	}

	public CreatedEventErrorAction(String responseCode) {
		this.responseCode = responseCode;
	}

	public String getCreateEvent() {
		String strOadrCreatedEvent = null;
		try {
			if (responseCode == null) {
				strOadrCreatedEvent = CreatedEventHelper.createCreatedEventError();
			} else {
				strOadrCreatedEvent = CreatedEventHelper.createCreatedEventError(responseCode);
				
				/*
				ArrayList<OadrDistributeEventType> receivedDistributeEvents = VENServerResource.getOadrDistributeEventReceivedsList();
				OadrDistributeEventType distributeEvent = receivedDistributeEvents.get(receivedDistributeEvents.size() - 1);
				String distributeEventText = SchemaHelper.getDistributeEventAsString(distributeEvent);
				
				List<String> distributeEvents = new ArrayList<String>();
				distributeEvents.add(distributeEventText);
				strOadrCreatedEvent = CreatedEventHelper.createCreatedEventError(distributeEvents , responseCode, Collections.<String>emptyList(), false, true);
				*/
			}
			
			oadrCreatedEvent = CreatedEventHelper
					.createCreatedEventFromString(strOadrCreatedEvent);

		} catch (Exception e) {
			Trace trace = TestSession.getTraceObj();

			trace.getLogFileContentTrace().append(e.getMessage());
		}
		return strOadrCreatedEvent;
	}

}