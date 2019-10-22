package com.qualitylogic.openadr.core.action.impl;


import com.qualitylogic.openadr.core.action.DistributeEventActionList;
import com.qualitylogic.openadr.core.action.ICreatedEventResult;
import com.qualitylogic.openadr.core.action.IDistributeEventAction;
import com.qualitylogic.openadr.core.base.BaseDistributeEventAction;
import com.qualitylogic.openadr.core.bean.CalculatedEventStatusBean;
import com.qualitylogic.openadr.core.signal.EiEventType;
import com.qualitylogic.openadr.core.signal.EventStatusEnumeratedType;
import com.qualitylogic.openadr.core.signal.IntervalType;
import com.qualitylogic.openadr.core.signal.Intervals;
import com.qualitylogic.openadr.core.signal.OadrDistributeEventType;
import com.qualitylogic.openadr.core.signal.OadrRequestEventType;
import com.qualitylogic.openadr.core.signal.OadrDistributeEventType.OadrEvent;
import com.qualitylogic.openadr.core.signal.helper.DistributeEventSignalHelper;
import com.qualitylogic.openadr.core.util.Clone;
import com.qualitylogic.openadr.core.util.OadrUtil;

public class DistEvent_002Modify10Min_SecondAction extends
		BaseDistributeEventAction {

	private static final long serialVersionUID = 1L;
	protected boolean isEventCompleted;
	private OadrDistributeEventType oadrDistributeEvent = null;

	public boolean isPreConditionsMet(OadrRequestEventType oadrRequestEvent) {
		if(oadrRequestEvent==null) return true;
		boolean isCommonPreConditionsMet = isCommonPreConditionsMet(oadrRequestEvent);
		IDistributeEventAction  distributeEventAction = DistributeEventActionList.getDistributeEventActionList().get(0);
		ICreatedEventResult  createdEventResult  = distributeEventAction.getCreatedEventTimeoutAction();

		if(isCommonPreConditionsMet && createdEventResult.isExpectedCreatedEventReceived()){
			return true;
		}else{
			return false;
		}
		
	}

	public boolean isEventCompleted() {
		return isEventCompleted;
	}

	public void setEventCompleted(boolean isEventCompleted) {
		this.isEventCompleted = isEventCompleted;
	}

	public OadrDistributeEventType getDistributeEvent() {

		if (oadrDistributeEvent == null) {
			
			IDistributeEventAction  distributeEventAction = DistributeEventActionList.getDistributeEventActionList().get(0);
			oadrDistributeEvent = Clone.clone(distributeEventAction.getDistributeEvent());
			
			oadrDistributeEvent.getOadrEvent().get(0).getEiEvent().getEventDescriptor().setModificationDateTime(OadrUtil.getCurrentTime());
			oadrDistributeEvent.getOadrEvent().get(0).getEiEvent().getEventDescriptor().setModificationNumber(1);
			EiEventType eiEvent = oadrDistributeEvent.getOadrEvent().get(0).getEiEvent();
			CalculatedEventStatusBean eventStatus=DistributeEventSignalHelper.calculateEventStatus(eiEvent);
			oadrDistributeEvent.getOadrEvent().get(0).getEiEvent().getEventDescriptor().setEventStatus(eventStatus.getEventStatus());
			oadrDistributeEvent.getOadrEvent().get(0).getEiEvent().getEiActivePeriod().getProperties().getDuration().setDuration("PT10M");
			oadrDistributeEvent.getOadrEvent().get(0).getEiEvent().getEiEventSignals().getEiEventSignal().get(0).getIntervals().getInterval().get(0).getDuration().setDuration("PT10M");			
		}
		return oadrDistributeEvent;

	}
}