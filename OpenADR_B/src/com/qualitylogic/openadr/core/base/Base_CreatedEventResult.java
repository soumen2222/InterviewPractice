package com.qualitylogic.openadr.core.base;

import java.util.ArrayList;
import java.util.List;

import com.qualitylogic.openadr.core.action.ICreatedEventResult;
import com.qualitylogic.openadr.core.action.IDistributeEventAction;
import com.qualitylogic.openadr.core.signal.OadrCreateOptType;

public abstract class Base_CreatedEventResult implements ICreatedEventResult {
	
	private static final long serialVersionUID = 1L;
	ArrayList<IDistributeEventAction> distributeEventActionList = new ArrayList<IDistributeEventAction>();
	int expectedOptINCount = 0;
	int expectedOptOutCount = 0;
	int expectedTotalCount = -1;
	boolean lastCreatedEventResult;

	int expectedCreateOpt_OptINCount = -1;
	int expectedCreateOpt_OptOutCount = -1;
	
	//List<OadrCreateOptType> resourceResultList = null;
	
	int includingPingOptIn_MinimumExpected = -1;
	int includingPingOptOut_MinimumExpected = -1;
/*
 * 
	public void setExpectedOadrCreateOptListPreConditions(List<OadrCreateOptType> resourceResultList){
		this.resourceResultList = resourceResultList;
	}
	
	public List<OadrCreateOptType> getExpectedOadrCreateOptListPreConditions(){
		return resourceResultList;
	}
	*/
	
	public int getIncludingPingOptIn_MinimumExpected() {
		return includingPingOptIn_MinimumExpected;
	}

	public void setIncludingPingOptIn_MinimumExpected(
			int includePingOptIn_MinimumExpected) {
		this.includingPingOptIn_MinimumExpected = includePingOptIn_MinimumExpected;
	}

	public int getIncludingPingOptOut_MinimumExpected() {
		return includingPingOptOut_MinimumExpected;
	}

	public void setIncludingPingOptOut_MinimumExpected(
			int includePingOptOut_MinimumIfFound) {
		this.includingPingOptOut_MinimumExpected = includePingOptOut_MinimumIfFound;
	}

	public synchronized void addDistributeEvent(
			IDistributeEventAction distributeEventAction) {
		distributeEventActionList.add(distributeEventAction);
	}

	public synchronized ArrayList<IDistributeEventAction> getDistributeEventActionList() {
		return distributeEventActionList;
	}

	public void setExpectedOptInCount(int expectedOptINCount) {
		this.expectedOptINCount = expectedOptINCount;
	}

	public void setExpectedOptOutCount(int expectedOptOutCount) {
		this.expectedOptOutCount = expectedOptOutCount;
	}

	public int getExpectedOptInCount() {
		return expectedOptINCount;
	}

	public int getExpectedOptOutCount() {
		return expectedOptOutCount;
	}

	public int getExpectedTotalCount() {
		return expectedTotalCount;
	}

	public void setExpectedTotalCount(int expectedTotalCount) {
		this.expectedTotalCount = expectedTotalCount;
	}

	public void setLastCreatedEventResult(boolean lastCreatedEventResult) {
		this.lastCreatedEventResult = lastCreatedEventResult;
	}

	public boolean isLastCreatedEventResult() {
		return lastCreatedEventResult;
	}
	
	@Override
	public void setExpectedCreateOpt_OptInCount(int expectedCreateOpt_OptINCount) {
		this.expectedCreateOpt_OptINCount=expectedCreateOpt_OptINCount;
	}


	@Override
	public void setExpectedCreateOpt_OptOutCount(
			int expectedCreateOpt_OptOutCount) {
		this.expectedCreateOpt_OptOutCount=expectedCreateOpt_OptOutCount;
		
	}


	@Override
	public int getExpectedCreateOpt_OptInCount() {
		return expectedCreateOpt_OptINCount;
	}


	@Override
	public int getExpectedCreateOpt_OptOutCount() {
		return expectedCreateOpt_OptOutCount;
	}
}