package com.qualitylogic.openadr.core.action.impl;

import com.qualitylogic.openadr.core.base.BaseDistributeEventAction;
import com.qualitylogic.openadr.core.signal.OadrDistributeEventType;
import com.qualitylogic.openadr.core.signal.OadrRequestEventType;
import com.qualitylogic.openadr.core.signal.OadrDistributeEventType.OadrEvent;
import com.qualitylogic.openadr.core.signal.helper.DistributeEventSignalHelper;
import com.qualitylogic.openadr.core.util.Clone;
import com.qualitylogic.openadr.core.util.OadrUtil;
import com.qualitylogic.openadr.core.util.PropertiesFileReader;

public class E0_0285_DistributeEventAction_1 extends BaseDistributeEventAction {

	private static final long serialVersionUID = 1L;
	protected boolean isEventCompleted;
	private OadrDistributeEventType oadrDistributeEvent = null;

	public boolean isPreConditionsMet(OadrRequestEventType oadrRequestEvent) {

		boolean isCommonPreConditionsMet = isCommonPreConditionsMet(oadrRequestEvent);

		return isCommonPreConditionsMet;
	}

	public boolean isEventCompleted() {
		return isEventCompleted;
	}

	public void setEventCompleted(boolean isEventCompleted) {
		this.isEventCompleted = isEventCompleted;
	}

	public OadrDistributeEventType getDistributeEvent() {

		if (oadrDistributeEvent == null) {
			oadrDistributeEvent = new DistributeEventSignalHelper()
					.loadOadrDistributeEvent("oadrDistributeEvent_Push_CPP_twoEvents_Default.xml");
			int firstIntervalStartTimeDelayMin = 5;
			DistributeEventSignalHelper
					.setNewReqIDEvntIDStartTimeAndMarketCtx1(
							oadrDistributeEvent, firstIntervalStartTimeDelayMin);

			OadrEvent firstEvent = Clone.clone(oadrDistributeEvent
					.getOadrEvent().get(0));

			oadrDistributeEvent.getOadrEvent().set(1, firstEvent);
			oadrDistributeEvent.getOadrEvent().get(1).getEiEvent()
					.getEventDescriptor()
					.setEventID(OadrUtil.createDescriptorEventID());

		}
		// Two different market contexts, different priorities.
		oadrDistributeEvent.getOadrEvent().get(0).getEiEvent()
				.getEventDescriptor().setPriority((long) 2);
		oadrDistributeEvent.getOadrEvent().get(1).getEiEvent()
				.getEventDescriptor().setPriority((long) 1);
		PropertiesFileReader propertiesFileReader = new PropertiesFileReader();
		oadrDistributeEvent
				.getOadrEvent()
				.get(1)
				.getEiEvent()
				.getEventDescriptor()
				.getEiMarketContext()
				.setMarketContext(
						propertiesFileReader.get("DR_MarketContext_2_Name"));
		return oadrDistributeEvent;

	}
}