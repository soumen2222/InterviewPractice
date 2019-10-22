package com.qualitylogic.openadr.core.action.impl;

import java.util.ArrayList;

import javax.xml.datatype.Duration;
import javax.xml.datatype.XMLGregorianCalendar;

import com.qualitylogic.openadr.core.base.BaseDistributeEventAction;
import com.qualitylogic.openadr.core.bean.CreatedEventBean;
import com.qualitylogic.openadr.core.common.TestSession;
import com.qualitylogic.openadr.core.signal.EiEventType;
import com.qualitylogic.openadr.core.signal.EventStatusEnumeratedType;
import com.qualitylogic.openadr.core.signal.OadrCreatedEventType;
import com.qualitylogic.openadr.core.signal.OadrDistributeEventType;
import com.qualitylogic.openadr.core.signal.OadrRequestEventType;
import com.qualitylogic.openadr.core.signal.ResponseRequiredType;
import com.qualitylogic.openadr.core.signal.OadrDistributeEventType.OadrEvent;
import com.qualitylogic.openadr.core.signal.helper.DistributeEventSignalHelper;
import com.qualitylogic.openadr.core.util.Clone;
import com.qualitylogic.openadr.core.util.OadrUtil;
import com.qualitylogic.openadr.core.util.PropertiesFileReader;

public class E1_0180_DistributeEventAction_2 extends BaseDistributeEventAction {

	private static final long serialVersionUID = 1L;
	private OadrDistributeEventType oadrDistributeEventToModify = null;

	public boolean isPreConditionsMet(OadrRequestEventType oadrRequestEvent) {

		boolean isCommonPreConditionsMet = isCommonPreConditionsMet(oadrRequestEvent);
		if (!isCommonPreConditionsMet)
			return false;
		ArrayList<OadrCreatedEventType> createdEventList = TestSession
				.getCreatedEventReceivedList();
		OadrDistributeEventType distributeEvent = TestSession
				.getDistributeEventActionList().get(0).getDistributeEvent();

		ArrayList<CreatedEventBean> createdEventBeanList = OadrUtil
				.transformToCreatedEventBeanList(createdEventList);

		int optIncount = DistributeEventSignalHelper
				.numberOfOptInCreatedEventReceived(distributeEvent,
						createdEventBeanList);
		if (optIncount == 2) {
			return true;
		}

		return false;
	}

	public boolean isEventCompleted() {
		return isEventCompleted;
	}

	public void setEventCompleted(boolean isEventCompleted) {
		this.isEventCompleted = isEventCompleted;
	}

	public OadrDistributeEventType getDistributeEvent() {

		OadrDistributeEventType oadrDistributeEvent = TestSession
				.getDistributeEventActionList().get(0).getDistributeEvent();
		PropertiesFileReader propertiesFileReader = new PropertiesFileReader();
		if (oadrDistributeEventToModify == null) {
			oadrDistributeEventToModify = Clone.clone(oadrDistributeEvent);
			String distributeRequestID = OadrUtil
					.createoadrDistributeRequestID();
			oadrDistributeEventToModify.setRequestID(distributeRequestID);
			oadrDistributeEventToModify.getEiResponse().setRequestID("");

			// Modify first event - push time out 1 minute
			oadrDistributeEventToModify.getOadrEvent().get(0).getEiEvent()
					.getEventDescriptor().setModificationNumber(1);
			XMLGregorianCalendar EventDateTime = (XMLGregorianCalendar) oadrDistributeEventToModify
					.getOadrEvent().get(0).getEiEvent().getEiActivePeriod()
					.getProperties().getDtstart().getDateTime().clone();
			Duration durationToAdd = OadrUtil.createDuration(1, 0);
			EventDateTime.add(durationToAdd);
			oadrDistributeEventToModify.getOadrEvent().get(0).getEiEvent()
					.getEiActivePeriod().getProperties().getDtstart()
					.setDateTime(EventDateTime);

			// Cancel second event
			oadrDistributeEventToModify.getOadrEvent().get(1).getEiEvent()
					.getEventDescriptor()
					.setEventStatus(EventStatusEnumeratedType.CANCELLED);
			oadrDistributeEventToModify.getOadrEvent().get(1).getEiEvent()
					.getEventDescriptor().setModificationNumber(1); 

			// Add new event
			OadrDistributeEventType newOadrDistributeEvent = new DistributeEventSignalHelper()
					.loadOadrDistributeEvent("oadrDistributeEvent_Pull_CPP_Default.xml");
			EiEventType newEiEvent = newOadrDistributeEvent.getOadrEvent().get(0)
					.getEiEvent();
			newEiEvent.getEventDescriptor().setEventID(
					OadrUtil.createDescriptorEventID());
			newEiEvent
					.getEventDescriptor()
					.getEiMarketContext()
					.setMarketContext(
							propertiesFileReader.get("DR_MarketContext_1_Name"));
			XMLGregorianCalendar nextStartDateTime = DistributeEventSignalHelper
					.calculateNextStartCurrentTime(oadrDistributeEventToModify
							.getOadrEvent().get(1).getEiEvent()
							.getEiActivePeriod().getProperties().getDtstart()
							.getDateTime(), "PT5M");
			newEiEvent.getEiActivePeriod().getProperties().getDtstart()
					.setDateTime(nextStartDateTime);

			OadrEvent oadrEvent = new OadrEvent();
			oadrEvent.setEiEvent(newEiEvent);
			oadrEvent.setOadrResponseRequired(ResponseRequiredType.ALWAYS);

			oadrDistributeEventToModify.getOadrEvent().add(2, oadrEvent);

			// Update Current Time
			XMLGregorianCalendar TempCreatedDateTime1 = OadrUtil
					.getCurrentTime();
			oadrDistributeEventToModify.getOadrEvent().get(0).getEiEvent()
					.getEventDescriptor()
					.setCreatedDateTime(TempCreatedDateTime1);
			oadrDistributeEventToModify.getOadrEvent().get(1).getEiEvent()
					.getEventDescriptor()
					.setCreatedDateTime(TempCreatedDateTime1);
			oadrDistributeEventToModify.getOadrEvent().get(2).getEiEvent()
					.getEventDescriptor()
					.setCreatedDateTime(TempCreatedDateTime1);

		}
		return oadrDistributeEventToModify;

	}
}