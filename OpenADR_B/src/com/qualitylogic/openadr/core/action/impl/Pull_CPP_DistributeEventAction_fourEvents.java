package com.qualitylogic.openadr.core.action.impl;

import java.util.ArrayList;
import java.util.List;

import javax.xml.datatype.Duration;
import javax.xml.datatype.XMLGregorianCalendar;

import com.qualitylogic.openadr.core.base.BaseDistributeEventAction;
import com.qualitylogic.openadr.core.signal.EiEventType;
import com.qualitylogic.openadr.core.signal.EventStatusEnumeratedType;
import com.qualitylogic.openadr.core.signal.IntervalType;
import com.qualitylogic.openadr.core.signal.OadrDistributeEventType;
import com.qualitylogic.openadr.core.signal.OadrRequestEventType;
import com.qualitylogic.openadr.core.signal.xcal.DurationPropType;
import com.qualitylogic.openadr.core.signal.helper.DistributeEventSignalHelper;
import com.qualitylogic.openadr.core.util.Clone;
import com.qualitylogic.openadr.core.util.OadrUtil;

@SuppressWarnings("serial")
public class Pull_CPP_DistributeEventAction_fourEvents extends
		BaseDistributeEventAction {

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
					.loadOadrDistributeEvent("oadrDistributeEvent_Pull_CPP_fourEvents_Default.xml");

			int firstIntervalStartTimeDelay = 0;
			DistributeEventSignalHelper
					.setNewReqIDEvntIDStartTimeAndMarketCtx1(
							oadrDistributeEvent, firstIntervalStartTimeDelay);
			XMLGregorianCalendar createdTime = oadrDistributeEvent
					.getOadrEvent().get(0).getEiEvent().getEventDescriptor()
					.getCreatedDateTime();

			
			// Event 1
			EiEventType event1 = oadrDistributeEvent.getOadrEvent().get(0)
					.getEiEvent();
			event1.getEiActivePeriod().getProperties().getDuration()
					.setDuration("PT10M");

			event1.getEiActivePeriod().getProperties().getDtstart()
			.setDateTime(createdTime);

			event1.getEventDescriptor().setPriority(new Long(1));

			// Event 2
			EiEventType event2 = oadrDistributeEvent.getOadrEvent().get(1)
					.getEiEvent();

			XMLGregorianCalendar startDtTimeEvent2 = (XMLGregorianCalendar) createdTime
					.clone();
			Duration durationToAddToStTime2 = OadrUtil.createDuration(20, 0);
			startDtTimeEvent2.add(durationToAddToStTime2);

			event2.getEiActivePeriod().getProperties().getDuration()
					.setDuration("PT10M");
			event2.getEiActivePeriod().getProperties().getDtstart()
					.setDateTime(startDtTimeEvent2);
			event2.getEventDescriptor().setPriority(new Long(0));

			// Event 3
			EiEventType event3 = oadrDistributeEvent.getOadrEvent().get(2)
					.getEiEvent();
			DistributeEventSignalHelper.setMarketContext2(event3);
			Duration durationToAddToStTime3 = OadrUtil.createDuration(30, 0);
			XMLGregorianCalendar startDtTimeEvent3 = (XMLGregorianCalendar) createdTime
					.clone();
			startDtTimeEvent3.add(durationToAddToStTime3);
			event3.getEventDescriptor().setEventStatus(
					EventStatusEnumeratedType.ACTIVE);
			event3.getEiActivePeriod().getProperties().getDuration()
					.setDuration("PT10M");
			event3.getEiActivePeriod().getProperties().getDtstart()
					.setDateTime(event3.getEventDescriptor().getCreatedDateTime());
			event3.getEventDescriptor().setPriority(new Long(1));

			// Event 4
			EiEventType event4 = oadrDistributeEvent.getOadrEvent().get(3)
					.getEiEvent();
			DistributeEventSignalHelper.setMarketContext2(event4);
			XMLGregorianCalendar startDtTimeEvent4 = (XMLGregorianCalendar) createdTime
					.clone();
			Duration durationToAddToStTime4 = OadrUtil.createDuration(60, 0);

			startDtTimeEvent4.add(durationToAddToStTime4);

			event4.getEiActivePeriod().getProperties().getDuration()
					.setDuration("PT10M");
			event4.getEiActivePeriod().getProperties().getDtstart()
					.setDateTime(startDtTimeEvent4);
			event4.getEventDescriptor().setPriority(new Long(0));

			DurationPropType xEIDuration = new DurationPropType();
			xEIDuration.setDuration("PT0M");
			event1.getEiActivePeriod().getProperties()
					.setXEiRampUp(xEIDuration);
			event1.getEventDescriptor().setEventStatus(
					EventStatusEnumeratedType.ACTIVE);
			event1.getEiEventSignals().getEiEventSignal().get(0)
					.getCurrentValue().getPayloadFloat().setValue((float) 1.0);
			List<IntervalType> intervalList = event1.getEiEventSignals()
					.getEiEventSignal().get(0).getIntervals().getInterval();
			IntervalType interval1 = intervalList.get(0);
			IntervalType interval2 = intervalList.get(1);
			IntervalType interval3 = intervalList.get(2);
			interval1.getDuration().setDuration("PT5M");
			interval2.getDuration().setDuration("PT4M");
			interval3.getDuration().setDuration("PT1M");

			List<IntervalType> intervalList2 = event2.getEiEventSignals()
					.getEiEventSignal().get(0).getIntervals().getInterval();
			IntervalType interval2_1 = intervalList2.get(0);
			IntervalType interval2_2 = intervalList2.get(1);
			IntervalType interval2_3 = intervalList2.get(2);
			interval2_1.getDuration().setDuration("PT5M");
			interval2_2.getDuration().setDuration("PT4M");
			interval2_3.getDuration().setDuration("PT1M");

			List<IntervalType> intervalList3 = event3.getEiEventSignals()
					.getEiEventSignal().get(0).getIntervals().getInterval();
			IntervalType interval3_1 = intervalList3.get(0);
			IntervalType interval3_2 = intervalList3.get(1);
			IntervalType interval3_3 = intervalList3.get(2);
			interval3_1.getDuration().setDuration("PT5M");
			interval3_2.getDuration().setDuration("PT4M");
			interval3_3.getDuration().setDuration("PT1M");

			List<IntervalType> intervalList4 = event4.getEiEventSignals()
					.getEiEventSignal().get(0).getIntervals().getInterval();
			IntervalType interval4_1 = intervalList4.get(0);
			IntervalType interval4_2 = intervalList4.get(1);
			IntervalType interval4_3 = intervalList4.get(2);
			interval4_1.getDuration().setDuration("PT5M");
			interval4_2.getDuration().setDuration("PT4M");
			interval4_3.getDuration().setDuration("PT1M");
			

			List<EiEventType> oadrEventList=new ArrayList<EiEventType>();
			oadrEventList.add(Clone.clone(event1));
			oadrEventList.add(Clone.clone(event3));
			oadrEventList.add(Clone.clone(event2));
			oadrEventList.add(Clone.clone(event4));
			
			oadrDistributeEvent.getOadrEvent().get(0).setEiEvent(oadrEventList.get(0));
			oadrDistributeEvent.getOadrEvent().get(1).setEiEvent(oadrEventList.get(1));
			oadrDistributeEvent.getOadrEvent().get(2).setEiEvent(oadrEventList.get(2));
			oadrDistributeEvent.getOadrEvent().get(3).setEiEvent(oadrEventList.get(3));
		}
		return oadrDistributeEvent;
	}

}