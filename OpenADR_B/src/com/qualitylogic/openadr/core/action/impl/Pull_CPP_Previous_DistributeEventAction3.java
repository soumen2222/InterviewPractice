package com.qualitylogic.openadr.core.action.impl;

import com.qualitylogic.openadr.core.action.IDistributeEventAction;
import com.qualitylogic.openadr.core.base.BaseDistributeEventAction;
import com.qualitylogic.openadr.core.common.TestSession;
import com.qualitylogic.openadr.core.signal.OadrDistributeEventType;
import com.qualitylogic.openadr.core.signal.OadrRequestEventType;
import com.qualitylogic.openadr.core.util.Clone;
import com.qualitylogic.openadr.core.util.OadrUtil;

public class Pull_CPP_Previous_DistributeEventAction3 extends
		BaseDistributeEventAction {

	private static final long serialVersionUID = 1L;

	public Pull_CPP_Previous_DistributeEventAction3() {
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
			IDistributeEventAction distributeEventAction = TestSession
					.getDistributeEventActionList().get(1);
			oadrDistributeEvent = Clone.clone(distributeEventAction
					.getDistributeEvent());
			oadrDistributeEvent.setRequestID(OadrUtil
					.createoadrDistributeRequestID());

		}
		return oadrDistributeEvent;
	}

}