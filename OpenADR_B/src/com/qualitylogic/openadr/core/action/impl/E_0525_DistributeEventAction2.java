package com.qualitylogic.openadr.core.action.impl;

import javax.xml.datatype.XMLGregorianCalendar;

import com.qualitylogic.openadr.core.action.IDistributeEventAction;
import com.qualitylogic.openadr.core.base.BaseDistributeEventAction;
import com.qualitylogic.openadr.core.common.TestSession;
import com.qualitylogic.openadr.core.signal.EventStatusEnumeratedType;
import com.qualitylogic.openadr.core.signal.OadrDistributeEventType;
import com.qualitylogic.openadr.core.signal.OadrRequestEventType;
import com.qualitylogic.openadr.core.util.Clone;
import com.qualitylogic.openadr.core.util.OadrUtil;

public class E_0525_DistributeEventAction2 extends BaseDistributeEventAction {

	private static final long serialVersionUID = 1L;

	public E_0525_DistributeEventAction2() {
	}

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
			XMLGregorianCalendar createdDateTime = OadrUtil.getCurrentTime();
			IDistributeEventAction distributeEventAction = TestSession
					.getDistributeEventActionList().get(0);
			oadrDistributeEvent = Clone.clone(distributeEventAction
					.getDistributeEvent());

			oadrDistributeEvent.getOadrEvent().get(1).getEiEvent()
					.getEventDescriptor().setCreatedDateTime(createdDateTime);

			oadrDistributeEvent.setRequestID(OadrUtil
					.createoadrDistributeRequestID());
			oadrDistributeEvent.getOadrEvent().get(0).getEiEvent()
					.getEventDescriptor().setModificationNumber(1);
			oadrDistributeEvent.getOadrEvent().get(0).getEiEvent()
					.getEventDescriptor().setCreatedDateTime(createdDateTime);
			oadrDistributeEvent.getOadrEvent().get(0).getEiEvent()
					.getEventDescriptor()
					.setEventStatus(EventStatusEnumeratedType.CANCELLED);
			oadrDistributeEvent.getOadrEvent().get(0).getEiEvent()
					.getEiEventSignals().getEiEventSignal().get(0)
					.getCurrentValue().getPayloadFloat().setValue((float) 0.0);

		}
		return oadrDistributeEvent;
	}

}