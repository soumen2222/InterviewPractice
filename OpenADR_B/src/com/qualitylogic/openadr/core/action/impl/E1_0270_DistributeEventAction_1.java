package com.qualitylogic.openadr.core.action.impl;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;

import javax.xml.bind.JAXBException;
import javax.xml.datatype.Duration;
import javax.xml.datatype.XMLGregorianCalendar;

import com.qualitylogic.openadr.core.base.BaseDistributeEventAction;
import com.qualitylogic.openadr.core.signal.EiEventType;
import com.qualitylogic.openadr.core.signal.OadrDistributeEventType;
import com.qualitylogic.openadr.core.signal.OadrRequestEventType;
import com.qualitylogic.openadr.core.signal.helper.DistributeEventSignalHelper;
import com.qualitylogic.openadr.core.util.OadrUtil;

public class E1_0270_DistributeEventAction_1 extends BaseDistributeEventAction {

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
					.loadOadrDistributeEvent("oadrDistributeEvent_Pull_CPP_twoEvents_OneInterval.xml");
			int firstIntervalStartTimeDelayMin = 1;
			DistributeEventSignalHelper
					.setNewReqIDEvntIDStartTimeAndMarketCtx1(
							oadrDistributeEvent, firstIntervalStartTimeDelayMin);

			EiEventType secondEvent = oadrDistributeEvent.getOadrEvent().get(1)
					.getEiEvent();
			XMLGregorianCalendar secondEventDateTime = (XMLGregorianCalendar) secondEvent
					.getEventDescriptor().getCreatedDateTime().clone();
			Duration durationToAdd = OadrUtil.createDuration(0, 3, 0);
			secondEventDateTime.add(durationToAdd);
			secondEvent.getEiActivePeriod().getProperties().getDtstart()
					.setDateTime(secondEventDateTime);

			EiEventType eiEvent = oadrDistributeEvent.getOadrEvent().get(0)
					.getEiEvent();
			try {
				DistributeEventSignalHelper
						.modificationRuleStartDtCurrentPlusMinutes(eiEvent,
								eiEvent.getEventDescriptor()
										.getCreatedDateTime(), 1);


			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			} catch (JAXBException e) {
				e.printStackTrace();
			}

		}
		return oadrDistributeEvent;

	}
}