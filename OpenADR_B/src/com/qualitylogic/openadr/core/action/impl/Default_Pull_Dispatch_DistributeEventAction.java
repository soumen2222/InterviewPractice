package com.qualitylogic.openadr.core.action.impl;

import java.util.ArrayList;
import java.util.List;

import com.qualitylogic.openadr.core.base.BaseDistributeEventAction;
import com.qualitylogic.openadr.core.common.TestSession;
import com.qualitylogic.openadr.core.signal.EiTargetType;
import com.qualitylogic.openadr.core.signal.EventStatusEnumeratedType;
import com.qualitylogic.openadr.core.signal.OadrCreateOptType;
import com.qualitylogic.openadr.core.signal.OadrDistributeEventType;
import com.qualitylogic.openadr.core.signal.OadrRequestEventType;
import com.qualitylogic.openadr.core.signal.OptTypeType;
import com.qualitylogic.openadr.core.signal.QualifiedEventIDType;
import com.qualitylogic.openadr.core.signal.helper.CreateOptEventHelper;
import com.qualitylogic.openadr.core.signal.helper.CreatedOptEventHelper;
import com.qualitylogic.openadr.core.signal.helper.DistributeEventSignalHelper;
import com.qualitylogic.openadr.core.util.OadrUtil;

public class Default_Pull_Dispatch_DistributeEventAction extends
		BaseDistributeEventAction {

	private static final long serialVersionUID = 1L;

	
	public boolean isPreConditionsMet(OadrRequestEventType oadrRequestEvent) {

		boolean isCommonPreConditionsMet = isCommonPreConditionsMet(oadrRequestEvent);
		 
		List<OadrCreateOptType> oadrCreateOptType = getExpectedOadrCreateOptList();
		 
		return isCommonPreConditionsMet;
	}
	
	public void test(){
		List<OadrCreateOptType> oadrCreateOptType = getExpectedOadrCreateOptList();
		
	}

	
	
	public static void main(String args[]){
		////////////////////////////////////////////////////////////////////
						//////Create expected result
		//Created OadrCreateOptType list. Check 
		List<OadrCreateOptType> oadrCreateOptTypeExpectationList =new ArrayList<OadrCreateOptType>();
		String marketContext="Market_Context_1";
		String reqId="Req_ID_1";
		String pEventID="Event_ID_1";
		ArrayList<String> resourceList=new ArrayList<String>();
			resourceList.add("Res_1");
			resourceList.add("Res_2");
			
		OptTypeType optType=OptTypeType.OPT_IN;
			
		OadrCreateOptType oadrCreateOptType=CreateOptEventHelper.createOadrCreateOptType_Resource(marketContext,reqId,pEventID,optType,resourceList);		
			oadrCreateOptTypeExpectationList.add(oadrCreateOptType);
						
							//TestSession.addCreateOptEventReceivedToList(oadrCreateOptType);
		/////////////////////////////////////////////////////////////////////
					//////Create actual received
			ArrayList<String> resourceListRec=new ArrayList<String>();
			resourceListRec.add("Res_1");
			resourceListRec.add("Res_2");
			OptTypeType optTypeRec=OptTypeType.OPT_IN;
			OadrCreateOptType oadrCreateOptTypeRec=CreateOptEventHelper.createOadrCreateOptType_Resource(marketContext,reqId,pEventID,optTypeRec,resourceListRec);		
			
			TestSession.getCreateOptEventReceivedList().add(oadrCreateOptTypeRec);
		/////////////////////////////////////////////////////////////////
			//////Test to see if what received match with the expected.
			ArrayList<OadrCreateOptType> oadrCreateOptTypeReceivedList = TestSession.getCreateOptEventReceivedList();
								
							
		Default_Pull_Dispatch_DistributeEventAction distAction=new Default_Pull_Dispatch_DistributeEventAction();
		distAction.setExpectedOadrCreateOptList(oadrCreateOptTypeExpectationList);
		List<OadrCreateOptType> pOadrCreateOptTypeExpectationList = distAction.getExpectedOadrCreateOptList();
		//boolean isExpectedOadrCreateOptReceived = CreatedOptEventHelper.isExpectedOadrCreateOptReceived(pOadrCreateOptTypeExpectationList,oadrCreateOptTypeReceivedList);
		
		//System.out.println(isExpectedOadrCreateOptReceived);
		
		/*
		List<OadrCreateOptType> oadrCreateOptTypeList = distAction.getExpectedOadrCreateOptListPreConditions();
		if(oadrCreateOptTypeList!=null){
			for(OadrCreateOptType eachExpectedOadrCreateOptType:oadrCreateOptTypeList){
				QualifiedEventIDType qualifiedEventIDType = eachExpectedOadrCreateOptType.getQualifiedEventID();
				String evntID = qualifiedEventIDType.getEventID();
				OptTypeType optTypeType = eachExpectedOadrCreateOptType.getOptType();
				
				EiTargetType eiTarget= eachExpectedOadrCreateOptType.getEiTarget();
				if(eiTarget!=null){
					List<String> resourceIDList = eiTarget.getResourceID();
					if(resourceIDList!=null){
						for(String eachresourceID:resourceIDList){
							
						}
					}
				}
			}
		}
			*/
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
					.loadOadrDistributeEvent("oadrDistributeEvent_Pull_Dispatch_Default.xml");
			int firstIntervalStartTimeDelayMin = -1;
			DistributeEventSignalHelper
					.setNewReqIDEvntIDStartTimeAndMarketCtx1(
							oadrDistributeEvent, firstIntervalStartTimeDelayMin);
		}
		oadrDistributeEvent.getOadrEvent().get(0).getEiEvent()
				.getEventDescriptor()
				.setEventStatus(EventStatusEnumeratedType.ACTIVE);

		oadrDistributeEvent.getOadrEvent().get(0).getEiEvent()
				.getEiEventSignals().getEiEventSignal().get(0)
				.getCurrentValue().getPayloadFloat().setValue(2);

		return oadrDistributeEvent;
	}

}