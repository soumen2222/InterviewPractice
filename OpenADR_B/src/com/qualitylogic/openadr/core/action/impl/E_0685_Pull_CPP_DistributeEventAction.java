package com.qualitylogic.openadr.core.action.impl;

import javax.xml.datatype.Duration;
import javax.xml.datatype.XMLGregorianCalendar;

import com.qualitylogic.openadr.core.action.IDistributeEventAction;
import com.qualitylogic.openadr.core.base.BaseDistributeEventAction;
import com.qualitylogic.openadr.core.bean.CalculatedEventStatusBean;
import com.qualitylogic.openadr.core.common.TestSession;
import com.qualitylogic.openadr.core.signal.OadrDistributeEventType;
import com.qualitylogic.openadr.core.signal.OadrRequestEventType;
import com.qualitylogic.openadr.core.signal.OadrDistributeEventType.OadrEvent;
import com.qualitylogic.openadr.core.signal.helper.DistributeEventSignalHelper;
import com.qualitylogic.openadr.core.util.Clone;
import com.qualitylogic.openadr.core.util.OadrUtil;

public class E_0685_Pull_CPP_DistributeEventAction extends
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
			int firstIntervalStartTimeDelayMin = 2;
			
			IDistributeEventAction distributeEventAction1 = TestSession
					.getDistributeEventActionList().get(0);

			OadrEvent oadrEvent1 = distributeEventAction1.getDistributeEvent()
					.getOadrEvent().get(0);

			IDistributeEventAction distributeEventAction2 = Clone
					.clone(distributeEventAction1);
			distributeEventAction2.getDistributeEvent().getOadrEvent()
					.remove(0);
			OadrEvent oadrEvent2 = Clone.clone(oadrEvent1);
			distributeEventAction2.getDistributeEvent().getOadrEvent()
					.add(oadrEvent2);
			DistributeEventSignalHelper
					.setNewReqIDEvntIDStartTimeAndMarketCtx1(
							distributeEventAction2.getDistributeEvent(),
							firstIntervalStartTimeDelayMin);

			// Modify start time to cut the need to wait in the DUT.
			Duration durationToRemove = OadrUtil.createDuration("-PT3M");
			XMLGregorianCalendar currentTime = OadrUtil.getCurrentTime();

			XMLGregorianCalendar modifiedStartTime = (XMLGregorianCalendar) currentTime
					.clone();
			modifiedStartTime.add(durationToRemove);

			oadrEvent1.getEiEvent().getEiActivePeriod().getProperties()
					.getDtstart().setDateTime(modifiedStartTime);

			oadrEvent1.getEiEvent().getEventDescriptor()
					.setCreatedDateTime(OadrUtil.getCurrentTime());
			CalculatedEventStatusBean eventBeanStatus = DistributeEventSignalHelper
					.calculateEventStatus(oadrEvent1.getEiEvent());
			oadrEvent1.getEiEvent().getEventDescriptor()
					.setEventStatus(eventBeanStatus.getEventStatus());

			distributeEventAction2.getDistributeEvent().getOadrEvent()
					.add(oadrEvent1);

			oadrDistributeEvent = distributeEventAction2.getDistributeEvent();
			oadrDistributeEvent.getOadrEvent().remove(0);
			oadrDistributeEvent.getOadrEvent().get(0).getEiEvent().getEventDescriptor().setModificationNumber(1);
		}
		return oadrDistributeEvent;
	}

}