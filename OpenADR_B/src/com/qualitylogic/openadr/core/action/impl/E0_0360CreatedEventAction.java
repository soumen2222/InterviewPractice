package com.qualitylogic.openadr.core.action.impl;

import java.util.ArrayList;
import com.qualitylogic.openadr.core.base.Base_CreatedEventAction;
import com.qualitylogic.openadr.core.common.TestSession;
import com.qualitylogic.openadr.core.signal.OadrCreatedEventType;
import com.qualitylogic.openadr.core.signal.OadrDistributeEventType;
import com.qualitylogic.openadr.core.signal.OptTypeType;
import com.qualitylogic.openadr.core.signal.helper.CreatedEventHelper;
import com.qualitylogic.openadr.core.signal.helper.ResponseHelper;
import com.qualitylogic.openadr.core.signal.helper.SchemaHelper;
import com.qualitylogic.openadr.core.util.Trace;
import com.qualitylogic.openadr.core.ven.VENServerResource;

public class E0_0360CreatedEventAction extends Base_CreatedEventAction {

	boolean includeModificationNumber;
	boolean isOptIn;
	String responseDescription = null;

	public E0_0360CreatedEventAction(boolean includeModificationNumber) {
		this.includeModificationNumber = includeModificationNumber;
	}

	public E0_0360CreatedEventAction(boolean includeModificationNumber,
			String responseDescription) {
		this.includeModificationNumber = includeModificationNumber;
		this.responseDescription = responseDescription;
	}

	public String getCreateEvent() {
		String strOadrCreatedEvent = null;
		if (oadrCreatedEvent == null) {
			ArrayList<OadrDistributeEventType> oadrDistributeEventList = VENServerResource
					.getOadrDistributeEventReceivedsList();
			OadrDistributeEventType oadrDistributeEvent = oadrDistributeEventList
					.get(oadrDistributeEventList.size() - 1);

			try {
				String strDistributeEvent = SchemaHelper
						.getDistributeEventAsString(oadrDistributeEvent);
				ArrayList<String> distributeEventList = new ArrayList<String>();
				distributeEventList.add(strDistributeEvent);

				String oadrCreatedEventStr = CreatedEventHelper
						.createCreatedEvent(distributeEventList, false, true,
								null);

				OadrCreatedEventType oadrCreatedEventObj = ResponseHelper
						.createOadrCreatedEventFromString(oadrCreatedEventStr);

				// Set Error
				oadrCreatedEventObj.getEiCreatedEvent().getEventResponses()
						.getEventResponse().get(0).setResponseCode("409");
				oadrCreatedEventObj.getEiCreatedEvent().getEventResponses()
						.getEventResponse().get(0)
						.setOptType(OptTypeType.OPT_OUT);

				strOadrCreatedEvent = SchemaHelper
						.getCreatedEventAsString(oadrCreatedEventObj);

			} catch (Exception e) {
				Trace trace = TestSession.getTraceObj();

				trace.getLogFileContentTrace().append(e.getMessage());
				e.printStackTrace();
			}
		}
		return strOadrCreatedEvent;
	}

}