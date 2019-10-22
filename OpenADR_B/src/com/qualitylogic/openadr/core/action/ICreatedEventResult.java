package com.qualitylogic.openadr.core.action;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.qualitylogic.openadr.core.bean.ServiceType;
import com.qualitylogic.openadr.core.signal.OadrCreateOptType;

public interface ICreatedEventResult extends Serializable {
	public boolean isExpectedCreatedEventReceived();

	public void setLastCreatedEventResult(boolean lastCreatedEventResult);

	public boolean isLastCreatedEventResult();

	public void addDistributeEvent(IDistributeEventAction distributeEventAction);

	public ArrayList<IDistributeEventAction> getDistributeEventActionList();

	public void setExpectedOptInCount(int expectedOptINCount);

	public void setExpectedOptOutCount(int expectedOptOutCount);

	public int getExpectedOptInCount();

	public int getExpectedOptOutCount();
////////////////////////////////
	
	public void setExpectedCreateOpt_OptInCount(int expectedCreateOpt_OptINCount);

	public void setExpectedCreateOpt_OptOutCount(int expectedCreateOpt_OptOutCount);

	public int getExpectedCreateOpt_OptInCount();

	public int getExpectedCreateOpt_OptOutCount();

	
	///////////////////////////
	public void setExpectedTotalCount(int expectedTotal);

	public int getExpectedTotalCount();

	public int getIncludingPingOptIn_MinimumExpected();

	public void setIncludingPingOptIn_MinimumExpected(int minimumExpected);

	public int getIncludingPingOptOut_MinimumExpected();

	public void setIncludingPingOptOut_MinimumExpected(int minimumExpected);

	//public void setExpectedOadrCreateOptType(List<ResourceResult> resourceResultList);
	
	//public List<ResourceResult> getExpectedOadrCreateOptType();
}
