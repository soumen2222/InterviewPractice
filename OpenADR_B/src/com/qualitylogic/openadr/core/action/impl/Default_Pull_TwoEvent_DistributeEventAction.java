package com.qualitylogic.openadr.core.action.impl;

import javax.xml.datatype.Duration;
import javax.xml.datatype.XMLGregorianCalendar;

import com.qualitylogic.openadr.core.base.BaseDistributeEventAction;
import com.qualitylogic.openadr.core.signal.OadrDistributeEventType;
import com.qualitylogic.openadr.core.signal.OadrRequestEventType;
import com.qualitylogic.openadr.core.signal.OadrDistributeEventType.OadrEvent;
import com.qualitylogic.openadr.core.signal.helper.DistributeEventSignalHelper;
import com.qualitylogic.openadr.core.util.OadrUtil;

public class Default_Pull_TwoEvent_DistributeEventAction extends
		BaseDistributeEventAction {

	private static final long serialVersionUID = 1L;
	protected boolean isEventCompleted;
	private OadrDistributeEventType oadrDistributeEvent = null;

	public boolean isPreConditionsMet(OadrRequestEventType oadrRequestEvent) {
		boolean isCommonPreConditionsMet = isCommonPreConditionsMet(oadrRequestEvent);
		return isCommonPreConditionsMet;
	}

	public boolean isEventCompleted(){
		return isEventCompleted;
	}

	public void setEventCompleted(boolean isEventCompleted) {
		this.isEventCompleted = isEventCompleted;
	}

	public OadrDistributeEventType getDistributeEvent() {

		if (oadrDistributeEvent == null) {
			oadrDistributeEvent = new DistributeEventSignalHelper()
					.loadOadrDistributeEvent("oadrDistributeEvent_Pull_CPP_twoEvents_Default.xml");
			int firstIntervalStartTimeDelayMin = 5;
			DistributeEventSignalHelper
					.setNewReqIDEvntIDStartTimeAndMarketCtx1(
							oadrDistributeEvent, firstIntervalStartTimeDelayMin);

			OadrEvent secondEvent = oadrDistributeEvent.getOadrEvent().get(1);
			XMLGregorianCalendar secondEventDateTime = (XMLGregorianCalendar) secondEvent
					.getEiEvent().getEventDescriptor().getCreatedDateTime()
					.clone();
			Duration durationToAdd = OadrUtil.createDuration(4, 21, 12);
			secondEventDateTime.add(durationToAdd);
			secondEvent.getEiEvent().getEiActivePeriod().getProperties()
					.getDtstart().setDateTime(secondEventDateTime);

		}
		return oadrDistributeEvent;

	}
}