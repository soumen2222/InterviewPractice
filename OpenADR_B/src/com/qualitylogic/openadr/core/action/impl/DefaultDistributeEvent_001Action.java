package com.qualitylogic.openadr.core.action.impl;

import java.util.List;
import com.qualitylogic.openadr.core.base.BaseDistributeEventAction;
import com.qualitylogic.openadr.core.signal.EiTargetType;
import com.qualitylogic.openadr.core.signal.OadrDistributeEventType;
import com.qualitylogic.openadr.core.signal.OadrRequestEventType;
import com.qualitylogic.openadr.core.signal.OadrDistributeEventType.OadrEvent;
import com.qualitylogic.openadr.core.signal.helper.DistributeEventSignalHelper;
import com.qualitylogic.openadr.core.util.OadrUtil;
import com.qualitylogic.openadr.core.util.PropertiesFileReader;

public class DefaultDistributeEvent_001Action extends
		BaseDistributeEventAction {

	private static final long serialVersionUID = 1L;
	private OadrDistributeEventType oadrDistributeEvent = null;

	public boolean isPreConditionsMet(OadrRequestEventType oadrRequestEvent) {
		boolean isCommonPreConditionsMet = isCommonPreConditionsMet(oadrRequestEvent);
		return isCommonPreConditionsMet;
	}



	public OadrDistributeEventType getDistributeEvent() {
		PropertiesFileReader propertiesFileReader=new PropertiesFileReader();
		if (oadrDistributeEvent == null) {
			
			oadrDistributeEvent = new DistributeEventSignalHelper()
					.loadOadrDistributeEvent("Event_001.xml");
			
			int firstIntervalStartTimeDelayMin = 1;
			DistributeEventSignalHelper
					.setNewReqIDEvntIDStartTimeAndMarketCtx1(
							oadrDistributeEvent, firstIntervalStartTimeDelayMin);

			OadrEvent firstEvent = oadrDistributeEvent.getOadrEvent().get(0);

			EiTargetType targetType = firstEvent.getEiEvent().getEiTarget();
			
			firstEvent.getEiEvent().getEiTarget().getGroupID().add(propertiesFileReader.getGroupID());
			firstEvent.getEiEvent().getEiTarget().getPartyID().add(propertiesFileReader.getParty_ID());
			
			firstEvent.getEiEvent().getEventDescriptor().setModificationDateTime(OadrUtil.getCurrentTime());
			firstEvent.getEiEvent().getEventDescriptor().setModificationNumber(0);
			
			List<String> resourceList=firstEvent.getEiEvent().getEiTarget().getResourceID();
			resourceList.clear();
			
			String propFileResourceID[]= propertiesFileReader.getTwoResourceID();
			
			targetType.getResourceID().clear();
			targetType.getResourceID().add(propFileResourceID[0]);
			targetType.getResourceID().add(propFileResourceID[1]);
			

		}
		return oadrDistributeEvent;

	}
}