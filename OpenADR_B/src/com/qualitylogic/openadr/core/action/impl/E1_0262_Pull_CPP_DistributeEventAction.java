package com.qualitylogic.openadr.core.action.impl;

import java.util.List;

import com.qualitylogic.openadr.core.base.BaseDistributeEventAction;
import com.qualitylogic.openadr.core.signal.IntervalType;
import com.qualitylogic.openadr.core.signal.Intervals;
import com.qualitylogic.openadr.core.signal.OadrDistributeEventType;
import com.qualitylogic.openadr.core.signal.OadrRequestEventType;
import com.qualitylogic.openadr.core.signal.OadrDistributeEventType.OadrEvent;
import com.qualitylogic.openadr.core.signal.helper.DistributeEventSignalHelper;
import com.qualitylogic.openadr.core.signal.xcal.Properties.Tolerance;
import com.qualitylogic.openadr.core.signal.xcal.Properties.Tolerance.Tolerate;

public class E1_0262_Pull_CPP_DistributeEventAction extends
		BaseDistributeEventAction {

	private static final long serialVersionUID = 1L;
	int firstIntervalStartTimeDelay = -999;

	public E1_0262_Pull_CPP_DistributeEventAction(
			int firstIntervalStartTimeDelayMin) {
		this.firstIntervalStartTimeDelay = firstIntervalStartTimeDelayMin;
	}

	public E1_0262_Pull_CPP_DistributeEventAction() {

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
			oadrDistributeEvent = new DistributeEventSignalHelper()
					.loadOadrDistributeEvent("oadrDistributeEvent_Pull_CPP_Default.xml");
			int firstIntervalStartTimeDelayMin = 30;

			DistributeEventSignalHelper
					.setNewReqIDEvntIDStartTimeAndMarketCtx1(
							oadrDistributeEvent, firstIntervalStartTimeDelayMin);

			OadrEvent oadrEvent = oadrDistributeEvent.getOadrEvent().get(0);
			Intervals intervals = oadrEvent.getEiEvent().getEiEventSignals()
					.getEiEventSignal().get(0).getIntervals();

			List<IntervalType> intervalList = intervals.getInterval();
			intervalList.remove(2);
			IntervalType interval1 = intervalList.get(0);
			interval1.getDuration().setDuration("PT1M");
			IntervalType interval2 = intervalList.get(1);
			interval2.getDuration().setDuration("PT2M");
			// Tolerance tolerance =
			// oadrEvent.getEiEvent().getEiActivePeriod().getProperties().getTolerance();
			Tolerance tolerance = new Tolerance();

			Tolerate tolerate = new Tolerate();
			tolerate.setStartafter("PT3M");

			tolerance.setTolerate(tolerate);
			oadrEvent.getEiEvent().getEiActivePeriod().getProperties()
					.setTolerance(tolerance);

		}
		return oadrDistributeEvent;
	}

}