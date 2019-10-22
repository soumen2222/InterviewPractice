package com.qualitylogic.openadr.core.action.impl;

import java.util.ArrayList;

import com.qualitylogic.openadr.core.base.BaseCreateOptEventAction;
import com.qualitylogic.openadr.core.common.TestSession;
import com.qualitylogic.openadr.core.signal.OadrCreateOptType;
import com.qualitylogic.openadr.core.signal.OadrDistributeEventType;
import com.qualitylogic.openadr.core.signal.OptTypeType;
import com.qualitylogic.openadr.core.signal.helper.CreateOptEventHelper;
import com.qualitylogic.openadr.core.signal.helper.LogHelper;
import com.qualitylogic.openadr.core.util.Trace;
import com.qualitylogic.openadr.core.ven.VENServerResource;

public class Default_CreateOptEventActionOnLastDistEvntRecd extends BaseCreateOptEventAction{
	
	OadrCreateOptType  oadrCreateOptType;
	OptTypeType optType;
	
	@Override
	public boolean isPreConditionsMet() {
		ArrayList<OadrDistributeEventType> oadrDistributeEventList = VENServerResource
				.getOadrDistributeEventReceivedsList();
		OadrDistributeEventType oadrDistributeEventType = oadrDistributeEventList
				.get(oadrDistributeEventList.size() - 1);

		if(oadrDistributeEventType.getOadrEvent().size()==1){
			return true;
		}else{
			LogHelper.addTrace("Precondition check failed. Expected two resources in DistributeEvent"); 
		}
		return false;
	}
	
	public Default_CreateOptEventActionOnLastDistEvntRecd(OptTypeType optType){
		this.optType=optType;
	}
	
	@Override
	public OadrCreateOptType getOadrCreateOpt() {
		
		if(oadrCreateOptType!=null) return oadrCreateOptType;
		
		ArrayList<OadrDistributeEventType> oadrDistributeEventList = VENServerResource
				.getOadrDistributeEventReceivedsList();
		OadrDistributeEventType oadrDistributeEventType = oadrDistributeEventList
				.get(oadrDistributeEventList.size() - 1);

		try {
						
			OadrCreateOptType  oadrCreateOptType  = CreateOptEventHelper.createCreateOptTypeEvent(oadrDistributeEventType,optType, 1);
			
			
			return oadrCreateOptType;
		} catch (Exception e) {
			Trace trace = TestSession.getTraceObj();

			trace.getLogFileContentTrace().append(e.getMessage());
			e.printStackTrace();
		}
		return null;
	}





}