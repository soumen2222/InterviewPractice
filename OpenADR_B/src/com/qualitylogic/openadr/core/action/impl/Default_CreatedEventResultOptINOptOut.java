package com.qualitylogic.openadr.core.action.impl;

import java.util.ArrayList;

import com.qualitylogic.openadr.core.action.IDistributeEventAction;
import com.qualitylogic.openadr.core.base.Base_CreatedEventResult;
import com.qualitylogic.openadr.core.bean.CreatedEventBean;
import com.qualitylogic.openadr.core.bean.PingDistributeEventMap;
import com.qualitylogic.openadr.core.common.TestSession;
import com.qualitylogic.openadr.core.signal.OadrCreateOptType;
import com.qualitylogic.openadr.core.signal.OadrCreatedEventType;
import com.qualitylogic.openadr.core.signal.OadrDistributeEventType;
import com.qualitylogic.openadr.core.signal.helper.DistributeEventSignalHelper;
import com.qualitylogic.openadr.core.util.OadrUtil;

public class Default_CreatedEventResultOptINOptOut extends
		Base_CreatedEventResult {

	private static final long serialVersionUID = 1L;


	public synchronized boolean isExpectedCreatedEventReceived() {

		ArrayList<IDistributeEventAction> distributeEventActionList = getDistributeEventActionList();

		if (distributeEventActionList == null
				|| distributeEventActionList.size() < 1)
			return false;

		ArrayList<OadrCreatedEventType> createdEventList = TestSession
				.getCreatedEventReceivedList();
		ArrayList<CreatedEventBean> createdEventBeanList = OadrUtil
				.transformToCreatedEventBeanList(createdEventList);
		int optIncount = 0;
		int optOutCount = 0;
		for (IDistributeEventAction eachDistributeEventAction : distributeEventActionList) {
			if (!eachDistributeEventAction.isEventCompleted()){
				continue;
			}
			//if (!eachDistributeEventAction.isEventPickedUpFromOadrPoll())
				
			OadrDistributeEventType oadrDistributeEventType = eachDistributeEventAction
					.getDistributeEvent();
			optIncount = optIncount
					+ DistributeEventSignalHelper
							.numberOfOptInCreatedEventReceived(
									oadrDistributeEventType, createdEventBeanList);
			optOutCount = optOutCount
					+ DistributeEventSignalHelper
							.numberOfOptOutCreatedEventReceived(
									oadrDistributeEventType, createdEventBeanList);
			
//////////////////
			//ArrayList<OadrCreateOptType>  oadrCreateOptTypeExpectedList=(ArrayList<OadrCreateOptType>)eachDistributeEventAction.getExpectedOadrCreateOptList();
			ArrayList<OadrCreateOptType>  oadrCreateOptReceivedList=TestSession.getCreateOptEventReceivedList();
			
			
			int expectedCreateOpt_OptINCount = getExpectedCreateOpt_OptInCount();
			int expectedCreateOpt_OptOutCount = getExpectedCreateOpt_OptOutCount();
			
			if(expectedCreateOpt_OptINCount!=-1 ){
				int receivedNumberOfOptInCreateOpt = DistributeEventSignalHelper.numberOfOptInCreateOptReceived(oadrDistributeEventType,oadrCreateOptReceivedList);

				if(expectedCreateOpt_OptINCount != receivedNumberOfOptInCreateOpt){
					return false;
				}
			}
			
			
			if(expectedCreateOpt_OptOutCount!=-1 ){
				int receivedNumberOfOptOutCreateOpt = DistributeEventSignalHelper.numberOfOptOutCreateOptReceived(oadrDistributeEventType,oadrCreateOptReceivedList);

				if(expectedCreateOpt_OptOutCount != receivedNumberOfOptOutCreateOpt){
					return false;
				}
			}
			
/*			if(oadrCreateOptTypeExpectedList!=null && oadrCreateOptTypeExpectedList.size()>0){
				ArrayList<OadrCreateOptType>  oadrCreateOptTypeReceivedList=TestSession.getCreateOptEventReceivedList();
				
				boolean isExpectedOadrCreateOptReceived = CreatedOptEventHelper.isExpectedOadrCreateOptReceived(oadrCreateOptTypeExpectedList,oadrCreateOptTypeReceivedList);
				if(!isExpectedOadrCreateOptReceived){
					System.out.print("");
					return false;
				}	
				
				int expectedCreateOpt_OPT_IN_Count = 0;
				int expectedCreateOpt_OPT_OUT_Count = 0;

				if(oadrCreateOptTypeExpectedList!=null){
					for(OadrCreateOptType eachOadrCreateOptType:oadrCreateOptTypeExpectedList){
						if(eachOadrCreateOptType.getOptType().equals(OptTypeType.OPT_IN)){
							expectedCreateOpt_OPT_IN_Count++;
						}
						
						if(eachOadrCreateOptType.getOptType().equals(OptTypeType.OPT_OUT)){
							expectedCreateOpt_OPT_OUT_Count++;
						}
					}
				}
	
				
			}*/
			
		}
		if (getExpectedTotalCount() != -1) {

			if (getExpectedTotalCount() == (optIncount + optOutCount)) {

				if (isLastCreatedEventResult()) {
					
					TestSession.setTestCaseDone(true);
				}
				return true;
			}
		} else {
			if (optIncount == getExpectedOptInCount()
					&& optOutCount == getExpectedOptOutCount()) {
				if (isLastCreatedEventResult()) {
					TestSession.setTestCaseDone(true);
				}
				return true;
			}
		}

		int includingPingOptIn_MinimumExpected = getIncludingPingOptIn_MinimumExpected();
		int includingPingOptOut_MinimumExpected = getIncludingPingOptOut_MinimumExpected();

		if (includingPingOptIn_MinimumExpected == -1
				&& includingPingOptOut_MinimumExpected == -1)
			return false;

		
		// Get the list of PING Distribute Event Map
		ArrayList<PingDistributeEventMap> pingDistributeEventMap = TestSession
				.getPingDistributeEventMap();
		if (pingDistributeEventMap == null || pingDistributeEventMap.size() < 1)
			return false;
		// Run through the completed distribute event action
		for (IDistributeEventAction eachDistributeEventAction : distributeEventActionList) {
			if (!eachDistributeEventAction.isEventCompleted())
				continue;

			OadrDistributeEventType oadrDistributeEventType = eachDistributeEventAction
					.getDistributeEvent();
			
			
			// Run through the ping distribute event list
			for (PingDistributeEventMap eachPingDistributeEvent : pingDistributeEventMap) {
				String parentPingDistributeEvent = eachPingDistributeEvent
						.getDistributeEventRequestID();

				// If the parent ID of PING match the distribute event request
				// ID
				if (parentPingDistributeEvent.equals(oadrDistributeEventType
						.getRequestID())) {
					ArrayList<OadrDistributeEventType> eachPingDistributeEventList = eachPingDistributeEvent
							.getDistributeEventList();

					// From the list of pings that match the request ID add the
					// optincount and optoutcount.
					for (OadrDistributeEventType eachChildPingOadrDistributeEvent : eachPingDistributeEventList) {
						optIncount = optIncount
								+ DistributeEventSignalHelper
										.numberOfOptInCreatedEventReceived(
												eachChildPingOadrDistributeEvent,
												createdEventBeanList);
						optOutCount = optOutCount
								+ DistributeEventSignalHelper
										.numberOfOptOutCreatedEventReceived(
												eachChildPingOadrDistributeEvent,
												createdEventBeanList);

					}
				}

			}

		}

		boolean checkOptIn = false;
		boolean checkOptOut = false;
		boolean checkBothOut = false;

		if (includingPingOptIn_MinimumExpected != -1) {
			checkOptIn = true;
		}
		if (includingPingOptOut_MinimumExpected != -1) {
			checkOptOut = true;
		}

		if (checkOptIn == true && checkOptOut == true) {
			checkBothOut = true;
		}

		
		if (checkBothOut) {
			if (includingPingOptIn_MinimumExpected >= optIncount
					&& includingPingOptOut_MinimumExpected >= optOutCount) {
				
				
				return true;
			}
		} else if (checkOptIn == true) {
			if (optIncount >= includingPingOptIn_MinimumExpected) {
				return true;
			}
		} else if (checkOptOut == true) {
			if (optOutCount >= includingPingOptOut_MinimumExpected) {
				return true;
			}
		}

		return false;
	}

}