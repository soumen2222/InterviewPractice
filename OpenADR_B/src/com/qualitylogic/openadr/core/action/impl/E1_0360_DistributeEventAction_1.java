package com.qualitylogic.openadr.core.action.impl;

import java.util.List;

import javax.xml.datatype.Duration;
import javax.xml.datatype.XMLGregorianCalendar;

import com.qualitylogic.openadr.core.base.BaseDistributeEventAction;
import com.qualitylogic.openadr.core.signal.EiEventType;
import com.qualitylogic.openadr.core.signal.OadrDistributeEventType;
import com.qualitylogic.openadr.core.signal.OadrRequestEventType;
import com.qualitylogic.openadr.core.signal.OadrDistributeEventType.OadrEvent;
import com.qualitylogic.openadr.core.signal.helper.DistributeEventSignalHelper;
import com.qualitylogic.openadr.core.util.OadrUtil;

public class E1_0360_DistributeEventAction_1 extends BaseDistributeEventAction {

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
					.loadOadrDistributeEvent("oadrDistributeEvent_Pull_CPP_twoEvents_Default.xml");
			int firstIntervalStartTimeDelayMin = 60;
			DistributeEventSignalHelper
					.setNewReqIDEvntIDStartTimeAndMarketCtx1(
							oadrDistributeEvent, firstIntervalStartTimeDelayMin);

			EiEventType secondEvent = oadrDistributeEvent.getOadrEvent().get(1)
					.getEiEvent();
			XMLGregorianCalendar secondEventDateTime = (XMLGregorianCalendar) secondEvent
					.getEventDescriptor().getCreatedDateTime().clone();
			Duration durationToAdd = OadrUtil.createDuration(120, 0);
			secondEventDateTime.add(durationToAdd);
			secondEvent.getEiActivePeriod().getProperties().getDtstart()
					.setDateTime(secondEventDateTime);
/*			secondEvent.getEiEventSignals().getEiEventSignal().get(0)
					.setSignalName("notsimple");*/

			List<OadrEvent> oadrEventList = oadrDistributeEvent.getOadrEvent();
			oadrEventList.get(0).getEiEvent().getEventDescriptor()
			.getEiMarketContext()
			.setMarketContext("INVALID_MARKET_CONTEXT");
			
		}
		return oadrDistributeEvent;

	}
}