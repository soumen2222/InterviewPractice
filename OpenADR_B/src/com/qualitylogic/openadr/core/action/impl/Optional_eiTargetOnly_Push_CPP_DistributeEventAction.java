package com.qualitylogic.openadr.core.action.impl;

import com.qualitylogic.openadr.core.base.BaseDistributeEventAction;
import com.qualitylogic.openadr.core.signal.EiTargetType;
import com.qualitylogic.openadr.core.signal.OadrDistributeEventType;
import com.qualitylogic.openadr.core.signal.OadrRequestEventType;
import com.qualitylogic.openadr.core.signal.helper.DistributeEventSignalHelper;
import com.qualitylogic.openadr.core.util.PropertiesFileReader;

public class Optional_eiTargetOnly_Push_CPP_DistributeEventAction extends
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
			PropertiesFileReader propertiesFileReader = new PropertiesFileReader();
			oadrDistributeEvent = new DistributeEventSignalHelper()
					.loadOadrDistributeEvent("oadrDistributeEvent_Push_CPP_OptionalElementsTargetOnly.xml");
			int firstIntervalStartTimeDelayMin = 2;
			DistributeEventSignalHelper
					.setNewReqIDEvntIDStartTimeAndMarketCtx1(
							oadrDistributeEvent, firstIntervalStartTimeDelayMin);

			// Add target values from properties
			EiTargetType eiTarget = oadrDistributeEvent.getOadrEvent().get(0).getEiEvent()
					.getEiTarget();
			eiTarget.getVenID().set(0, propertiesFileReader.getVenID());
			eiTarget.getPartyID().clear(); // set(0, propertiesFileReader.getParty_ID());
			eiTarget.getResourceID().set(0, propertiesFileReader.getOneResourceID());
			eiTarget.getGroupID().clear(); // set(0, propertiesFileReader.getGroupID());
		}
		return oadrDistributeEvent;
	}

}