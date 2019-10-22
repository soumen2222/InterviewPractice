package com.qualitylogic.openadr.core.action;

import java.io.Serializable;
import java.util.List;

import com.qualitylogic.openadr.core.common.IPrompt;
import com.qualitylogic.openadr.core.signal.OadrCreateOptType;
import com.qualitylogic.openadr.core.signal.OadrDistributeEventType;
import com.qualitylogic.openadr.core.signal.OadrRequestEventType;

public interface IDistributeEventAction extends Serializable {

	public abstract boolean isPreConditionsMet(OadrRequestEventType oadrRequestEvent);
	
	public abstract void setExpectedOadrCreateOptList(List<OadrCreateOptType> resourceResultList);
	
	public abstract List<OadrCreateOptType> getExpectedOadrCreateOptList();
	
	
	//public abstract void setExpectedOadrCancelOptListPreConditions(List<OadrCancelOptType> oadrCancelOptTypeList);
	//public abstract List<OadrCancelOptType> getExpectedOadrCancelOptTypeListPreConditions();
	
	
	public abstract boolean isEventCompleted();

	public void setEventCompleted(boolean isEventCompleted);

	public OadrDistributeEventType getDistributeEvent();

	public void resetToInitialState();

	public void setCreatedEventSuccessCriteria(
			ICreatedEventResult createdEventResult);

	public ICreatedEventResult getCreatedEventTimeoutAction();

	public void startCreatedEventTimeoutActionThread();

	public void startCreatedEventTimeoutActionThreadWithPause();

	public boolean isCommonPreConditionsMet(OadrRequestEventType oadrRequestEvent);

	public void setPrompt(IPrompt prompt);
	public IPrompt getPrompt();
}