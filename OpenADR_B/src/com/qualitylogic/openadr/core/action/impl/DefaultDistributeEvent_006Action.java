package com.qualitylogic.openadr.core.action.impl;

import java.util.List;
import com.qualitylogic.openadr.core.base.BaseDistributeEventAction;
import com.qualitylogic.openadr.core.signal.EiEventType;
import com.qualitylogic.openadr.core.signal.EiTargetType;
import com.qualitylogic.openadr.core.signal.OadrDistributeEventType;
import com.qualitylogic.openadr.core.signal.OadrRequestEventType;
import com.qualitylogic.openadr.core.signal.OadrDistributeEventType.OadrEvent;
import com.qualitylogic.openadr.core.signal.helper.DistributeEventSignalHelper;
import com.qualitylogic.openadr.core.util.OadrUtil;
import com.qualitylogic.openadr.core.util.PropertiesFileReader;

public class DefaultDistributeEvent_006Action extends BaseDistributeEventAction {

	private static final long serialVersionUID = 1634869627817995082L;
	
	public boolean isPreConditionsMet(OadrRequestEventType oadrRequestEvent) {
		return isCommonPreConditionsMet(oadrRequestEvent);
	}

	public OadrDistributeEventType getDistributeEvent() {
		PropertiesFileReader propertiesFileReader=new PropertiesFileReader();
		if (oadrDistributeEvent == null) {
			oadrDistributeEvent = new DistributeEventSignalHelper().loadOadrDistributeEvent("Event_006.xml");
			int firstIntervalStartTimeDelayMin = 10;
			DistributeEventSignalHelper.setNewReqIDEvntIDStartTimeAndMarketCtx1(oadrDistributeEvent, firstIntervalStartTimeDelayMin);
			OadrEvent firstEvent = oadrDistributeEvent.getOadrEvent().get(0);
		
			EiEventType eiEvent = firstEvent.getEiEvent();
			
			EiTargetType eiTarget = eiEvent.getEiTarget();
			eiTarget.getGroupID().add(propertiesFileReader.getGroupID());
			eiTarget.getPartyID().add(propertiesFileReader.getParty_ID());
			
			eiEvent.getEventDescriptor().setModificationDateTime(OadrUtil.getCurrentTime());
			eiEvent.getEventDescriptor().setModificationNumber(0);
			
			String[] resourceIDs = propertiesFileReader.getTwoResourceID();
			List<String> resourceID = eiTarget.getResourceID();
			resourceID.clear();
			resourceID.add(resourceIDs[0]);
			resourceID.add(resourceIDs[1]);
		}
		
		return oadrDistributeEvent;
	}
}
