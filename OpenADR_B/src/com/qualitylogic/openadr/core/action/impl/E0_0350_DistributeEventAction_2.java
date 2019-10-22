package com.qualitylogic.openadr.core.action.impl;

import java.util.ArrayList;
import java.util.List;

import javax.xml.datatype.XMLGregorianCalendar;

import com.qualitylogic.openadr.core.base.BaseDistributeEventAction;
import com.qualitylogic.openadr.core.bean.CreatedEventBean;
import com.qualitylogic.openadr.core.common.TestSession;
import com.qualitylogic.openadr.core.signal.EiEventType;
import com.qualitylogic.openadr.core.signal.OadrCreatedEventType;
import com.qualitylogic.openadr.core.signal.OadrDistributeEventType;
import com.qualitylogic.openadr.core.signal.OadrRequestEventType;
import com.qualitylogic.openadr.core.signal.ResponseRequiredType;
import com.qualitylogic.openadr.core.signal.OadrDistributeEventType.OadrEvent;
import com.qualitylogic.openadr.core.signal.helper.DistributeEventSignalHelper;
import com.qualitylogic.openadr.core.util.Clone;
import com.qualitylogic.openadr.core.util.OadrUtil;
import com.qualitylogic.openadr.core.util.PropertiesFileReader;

public class E0_0350_DistributeEventAction_2 extends BaseDistributeEventAction {

	private static final long serialVersionUID = 1L;
	protected boolean isEventCompleted;
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

			XMLGregorianCalendar TempCreatedDateTime = OadrUtil
					.getCurrentTime();

			oadrDistributeEventToModify.getOadrEvent().get(0).getEiEvent()
					.getEventDescriptor()
					.setCreatedDateTime(TempCreatedDateTime);

			OadrDistributeEventType newOadrDistributeEvent = new DistributeEventSignalHelper()
					.loadOadrDistributeEvent("oadrDistributeEvent_Push_CPP_Default.xml");
			EiEventType newEiEvent = newOadrDistributeEvent.getOadrEvent().get(0)
					.getEiEvent();
			newEiEvent.getEventDescriptor().setEventID(
					OadrUtil.createDescriptorEventID());
			newEiEvent
					.getEventDescriptor()
					.getEiMarketContext()
					.setMarketContext(
							propertiesFileReader.get("DR_MarketContext_1_Name"));

			EiEventType lastEiEvent = oadrDistributeEvent.getOadrEvent().get(0)
					.getEiEvent();

			// Set dtstart 1 minute out
			XMLGregorianCalendar nextStartDateTime = DistributeEventSignalHelper
					.calculateNextStartCurrentTime(lastEiEvent
							.getEiActivePeriod().getProperties().getDtstart()
							.getDateTime(), "PT1M");
			newEiEvent.getEventDescriptor().setCreatedDateTime(
					TempCreatedDateTime);
			newEiEvent.getEiActivePeriod().getProperties().getDtstart()
					.setDateTime(nextStartDateTime);

			OadrEvent oadrEvent = new OadrEvent();
			oadrEvent.setOadrResponseRequired(ResponseRequiredType.ALWAYS);
			oadrEvent.setEiEvent(newEiEvent);

			oadrDistributeEventToModify.getOadrEvent().add(1, oadrEvent);

			List<OadrEvent> oadrEventList = oadrDistributeEventToModify
					.getOadrEvent();
			XMLGregorianCalendar currentTime = OadrUtil.getCurrentTime();

			for (OadrEvent eachOadrEvent : oadrEventList) {
				XMLGregorianCalendar createdDateTime = (XMLGregorianCalendar) currentTime
						.clone();
				eachOadrEvent.getEiEvent().getEventDescriptor()
						.setCreatedDateTime(createdDateTime);
			}

		}
		return oadrDistributeEventToModify;

	}
}