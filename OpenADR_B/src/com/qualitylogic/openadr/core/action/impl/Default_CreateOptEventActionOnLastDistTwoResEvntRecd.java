package com.qualitylogic.openadr.core.action.impl;

import java.util.ArrayList;
import java.util.List;

import com.qualitylogic.openadr.core.base.BaseCreateOptEventAction;
import com.qualitylogic.openadr.core.common.TestSession;
import com.qualitylogic.openadr.core.signal.OadrCreateOptType;
import com.qualitylogic.openadr.core.signal.OadrDistributeEventType;
import com.qualitylogic.openadr.core.signal.OptTypeType;
import com.qualitylogic.openadr.core.signal.helper.CreateOptEventHelper;
import com.qualitylogic.openadr.core.signal.helper.LogHelper;
import com.qualitylogic.openadr.core.util.Trace;
import com.qualitylogic.openadr.core.ven.VENServerResource;

public class Default_CreateOptEventActionOnLastDistTwoResEvntRecd extends BaseCreateOptEventAction{
	
	OadrCreateOptType  oadrCreateOptType;
	OptTypeType optType;
	int eventToSelect;
	List<Integer> selectResourcePositionInPayload;
	
	@Override
	public boolean isPreConditionsMet() {
		ArrayList<OadrDistributeEventType> oadrDistributeEventList = VENServerResource
				.getOadrDistributeEventReceivedsList();
		OadrDistributeEventType oadrDistributeEventType = oadrDistributeEventList
				.get(oadrDistributeEventList.size() - 1);

		if(oadrDistributeEventType.getOadrEvent().size()==1 && oadrDistributeEventType.getOadrEvent().get(0).getEiEvent().getEiTarget()!=null && oadrDistributeEventType.getOadrEvent().get(0).getEiEvent().getEiTarget().getResourceID().size()==2){
			return true;
		}else{
			LogHelper.addTrace("Precondition check failed. Expected two resources in DistributeEvent"); 
		}
		return false;
	}
	
	public Default_CreateOptEventActionOnLastDistTwoResEvntRecd(OptTypeType optType,int eventToSelect,List<Integer> selectResourcePositionInPayload){
		this.optType=optType;
		this.eventToSelect=eventToSelect;
		this.selectResourcePositionInPayload=selectResourcePositionInPayload;
	}
	
	@Override
	public OadrCreateOptType getOadrCreateOpt() {
		
		if(oadrCreateOptType!=null) return oadrCreateOptType;
		
		ArrayList<OadrDistributeEventType> oadrDistributeEventList = VENServerResource
				.getOadrDistributeEventReceivedsList();
		OadrDistributeEventType oadrDistributeEventType = oadrDistributeEventList
				.get(oadrDistributeEventList.size() - 1);

		try {
						
			OadrCreateOptType  oadrCreateOptType  = CreateOptEventHelper.createCreateOptTypeEvent(oadrDistributeEventType,optType, eventToSelect,selectResourcePositionInPayload);
			
			
			return oadrCreateOptType;
		} catch (Exception e) {
			Trace trace = TestSession.getTraceObj();

			trace.getLogFileContentTrace().append(e.getMessage());
			e.printStackTrace();
		}
		return null;
	}





}