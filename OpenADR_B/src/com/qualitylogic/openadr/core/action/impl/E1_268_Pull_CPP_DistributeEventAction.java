package com.qualitylogic.openadr.core.action.impl;

import java.util.List;

import com.qualitylogic.openadr.core.base.BaseDistributeEventAction;
import com.qualitylogic.openadr.core.signal.EiActivePeriodType;
import com.qualitylogic.openadr.core.signal.IntervalType;
import com.qualitylogic.openadr.core.signal.Intervals;
import com.qualitylogic.openadr.core.signal.OadrDistributeEventType;
import com.qualitylogic.openadr.core.signal.OadrRequestEventType;
import com.qualitylogic.openadr.core.signal.xcal.Properties;
import com.qualitylogic.openadr.core.signal.xcal.Properties.Tolerance;
import com.qualitylogic.openadr.core.signal.xcal.Properties.Tolerance.Tolerate;
import com.qualitylogic.openadr.core.signal.helper.DistributeEventSignalHelper;

public class E1_268_Pull_CPP_DistributeEventAction extends
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
			oadrDistributeEvent = new DistributeEventSignalHelper()
					.loadOadrDistributeEvent("oadrDistributeEvent_Pull_CPP_Default.xml");
			int firstIntervalStartTimeDelayMin = 1;
			DistributeEventSignalHelper
					.setNewReqIDEvntIDStartTimeAndMarketCtx1(
							oadrDistributeEvent, firstIntervalStartTimeDelayMin);

			EiActivePeriodType eiActivePeriod = oadrDistributeEvent.getOadrEvent()
					.get(0).getEiEvent().getEiActivePeriod();
			Properties properties = eiActivePeriod.getProperties();
			Tolerate tolerate = new Tolerate();
			tolerate.setStartafter("PT2M");
			Tolerance tolerance = new Tolerance();
			properties.setTolerance(tolerance);
			tolerance.setTolerate(tolerate);

			eiActivePeriod.getProperties().getDuration().setDuration("PT5M");

			Intervals intervals = oadrDistributeEvent.getOadrEvent().get(0)
					.getEiEvent().getEiEventSignals().getEiEventSignal().get(0)
					.getIntervals();
			List<IntervalType> intervalList = intervals.getInterval();
			intervalList.remove(2);

			IntervalType interval1 = intervalList.get(0);
			interval1.getDuration().setDuration("PT3M");
			IntervalType interval2 = intervalList.get(1);
			interval2.getDuration().setDuration("PT2M");

			oadrDistributeEvent.getOadrEvent().get(0).getEiEvent()
					.getEiEventSignals().getEiEventSignal().get(0)
					.getCurrentValue().getPayloadFloat().setValue((float) 0.0);

		}
		return oadrDistributeEvent;
	}

}