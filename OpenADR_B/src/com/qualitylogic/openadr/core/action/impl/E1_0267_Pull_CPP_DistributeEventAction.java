package com.qualitylogic.openadr.core.action.impl;

import com.qualitylogic.openadr.core.action.IDistributeEventAction;
import com.qualitylogic.openadr.core.base.BaseDistributeEventAction;
import com.qualitylogic.openadr.core.common.TestSession;
import com.qualitylogic.openadr.core.signal.EventStatusEnumeratedType;
import com.qualitylogic.openadr.core.signal.OadrDistributeEventType;
import com.qualitylogic.openadr.core.signal.OadrRequestEventType;
import com.qualitylogic.openadr.core.util.Clone;
import com.qualitylogic.openadr.core.util.OadrUtil;

public class E1_0267_Pull_CPP_DistributeEventAction extends
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

			IDistributeEventAction distributeEventAction1 = TestSession
					.getDistributeEventActionList().get(0);

			IDistributeEventAction distributeEventAction2 = Clone
					.clone(distributeEventAction1);
			oadrDistributeEvent = distributeEventAction2.getDistributeEvent();
			oadrDistributeEvent.setRequestID(OadrUtil
					.createoadrDistributeRequestID());
			oadrDistributeEvent.getOadrEvent().get(0).getEiEvent()
					.getEventDescriptor()
					.setCreatedDateTime(OadrUtil.getCurrentTime());
			oadrDistributeEvent.getOadrEvent().get(0).getEiEvent()
					.getEventDescriptor()
					.setEventStatus(EventStatusEnumeratedType.CANCELLED);
			oadrDistributeEvent.getOadrEvent().get(0).getEiEvent()
					.getEiEventSignals().getEiEventSignal().get(0)
					.getCurrentValue().getPayloadFloat().setValue(0);
			oadrDistributeEvent.getOadrEvent().get(0).getEiEvent()
					.getEventDescriptor().setModificationNumber(1);

			oadrDistributeEvent = distributeEventAction2.getDistributeEvent();

		}
		return oadrDistributeEvent;
	}

}