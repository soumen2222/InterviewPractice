package com.qualitylogic.openadr.core.action.impl;

import javax.xml.datatype.Duration;
import javax.xml.datatype.XMLGregorianCalendar;
import com.qualitylogic.openadr.core.base.BaseDistributeEventAction;
import com.qualitylogic.openadr.core.common.TestSession;
import com.qualitylogic.openadr.core.signal.OadrDistributeEventType;
import com.qualitylogic.openadr.core.signal.OadrRequestEventType;
import com.qualitylogic.openadr.core.util.Clone;
import com.qualitylogic.openadr.core.util.OadrUtil;

public class E0_0260_DistributeEventAction_2 extends BaseDistributeEventAction {

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
			OadrDistributeEventType distributeEvent = TestSession
					.getDistributeEventActionList().get(0).getDistributeEvent();
			oadrDistributeEvent = Clone.clone(distributeEvent);
			String oadrRequestID = OadrUtil.createoadrDistributeRequestID();
			oadrDistributeEvent.setRequestID(oadrRequestID);
			oadrDistributeEvent.getOadrEvent().get(0).getEiEvent()
					.getEventDescriptor().setModificationNumber(1);

			XMLGregorianCalendar createdTime = OadrUtil.getCurrentTime();
			XMLGregorianCalendar startTime = (XMLGregorianCalendar) createdTime
					.clone();
			Duration durationToAdd = OadrUtil.createDuration("PT1M");
			startTime.add(durationToAdd);

			oadrDistributeEvent.getOadrEvent().get(0).getEiEvent()
					.getEventDescriptor().setCreatedDateTime(createdTime);

			oadrDistributeEvent.getOadrEvent().get(0).getEiEvent()
					.getEiActivePeriod().getProperties().getDtstart()
					.setDateTime(startTime);

		}
		return oadrDistributeEvent;

	}
}