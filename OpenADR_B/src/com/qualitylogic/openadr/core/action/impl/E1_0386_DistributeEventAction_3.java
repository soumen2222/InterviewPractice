package com.qualitylogic.openadr.core.action.impl;

import java.util.List;

import javax.xml.datatype.XMLGregorianCalendar;

import com.qualitylogic.openadr.core.base.BaseDistributeEventAction;
import com.qualitylogic.openadr.core.common.TestSession;
import com.qualitylogic.openadr.core.signal.OadrDistributeEventType;
import com.qualitylogic.openadr.core.signal.OadrRequestEventType;
import com.qualitylogic.openadr.core.signal.ResponseRequiredType;
import com.qualitylogic.openadr.core.signal.OadrDistributeEventType.OadrEvent;
import com.qualitylogic.openadr.core.util.Clone;
import com.qualitylogic.openadr.core.util.OadrUtil;

public class E1_0386_DistributeEventAction_3 extends BaseDistributeEventAction {

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
					.getDistributeEventActionList().get(1).getDistributeEvent();

			OadrDistributeEventType distributeEvent2 = Clone.clone(distributeEvent);
			String newDistributeEventID = OadrUtil
					.createoadrDistributeRequestID();
			XMLGregorianCalendar currentDateTime = OadrUtil.getCurrentTime();
			distributeEvent2.getOadrEvent().get(0).getEiEvent()
					.getEventDescriptor().setCreatedDateTime(currentDateTime);
			distributeEvent2.getEiResponse().setRequestID("");
			distributeEvent2.setRequestID(newDistributeEventID);

			oadrDistributeEvent = distributeEvent2;

			List<OadrEvent> oadrEventList = oadrDistributeEvent.getOadrEvent();
			for (OadrEvent eachOadrEvent : oadrEventList) {
				eachOadrEvent
						.setOadrResponseRequired(ResponseRequiredType.NEVER);
			}

		}
		return oadrDistributeEvent;

	}
}