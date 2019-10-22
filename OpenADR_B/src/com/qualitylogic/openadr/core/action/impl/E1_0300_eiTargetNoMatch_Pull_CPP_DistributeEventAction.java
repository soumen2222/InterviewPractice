package com.qualitylogic.openadr.core.action.impl;

import com.qualitylogic.openadr.core.base.BaseDistributeEventAction;
import com.qualitylogic.openadr.core.signal.EiTargetType;
import com.qualitylogic.openadr.core.signal.OadrDistributeEventType;
import com.qualitylogic.openadr.core.signal.OadrRequestEventType;
import com.qualitylogic.openadr.core.signal.helper.DistributeEventSignalHelper;

public class E1_0300_eiTargetNoMatch_Pull_CPP_DistributeEventAction extends
		BaseDistributeEventAction {


	private static final long serialVersionUID = 1L;

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
					.loadOadrDistributeEvent("oadrDistributeEvent_Pull_CPP_OptionalElementsTargetOnly.xml");
			int firstIntervalStartTimeDelayMin = 2;
			DistributeEventSignalHelper
					.setNewReqIDEvntIDStartTimeAndMarketCtx1(
							oadrDistributeEvent, firstIntervalStartTimeDelayMin);

			// Add target values from properties
			EiTargetType eiTarget = oadrDistributeEvent.getOadrEvent().get(0).getEiEvent().getEiTarget();
			eiTarget.getVenID().set(0, "NoMatchVenID");
			eiTarget.getPartyID().clear(); // set(0, "NoMatchParty_ID");
			eiTarget.getResourceID().set(0, "NoMatchResourceID");
			eiTarget.getGroupID().clear(); // .set(0, "NoMatchGroupID");
			eiTarget.getPartyID().clear(); // .add(1, "NoMatch_1_Party_ID");
			eiTarget.getResourceID().add(1, "NoMatch_1_ResourceID");
			eiTarget.getGroupID().clear(); // .add(1, "NoMatch_1_GroupID");
		}
		return oadrDistributeEvent;
	}

}