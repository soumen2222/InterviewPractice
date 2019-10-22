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

public class DefaultDistributeEvent_001AndEvent_002Action extends
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
			
			OadrDistributeEventType oadrDistributeEventType001 = new DefaultDistributeEvent_001Action().getDistributeEvent();
			OadrDistributeEventType oadrDistributeEventType002 = new DefaultDistributeEvent_002Action().getDistributeEvent();

			oadrDistributeEvent = oadrDistributeEventType001;
			oadrDistributeEvent.getOadrEvent().addAll(oadrDistributeEventType002.getOadrEvent());
			
			int firstIntervalStartTimeDelayMin = 1;
			DistributeEventSignalHelper
					.setNewReqIDEvntIDStartTimeAndMarketCtx1(
							oadrDistributeEvent, firstIntervalStartTimeDelayMin);

			OadrEvent firstEvent = oadrDistributeEvent.getOadrEvent().get(0);
			OadrEvent secondEvent = oadrDistributeEvent.getOadrEvent().get(1);
				
			EiTargetType targetType = firstEvent.getEiEvent().getEiTarget();
			
			// firstEvent.getEiEvent().getEiTarget().getGroupID().add(propertiesFileReader.getGroupID());
			// firstEvent.getEiEvent().getEiTarget().getPartyID().add(propertiesFileReader.getParty_ID());
			
			firstEvent.getEiEvent().getEventDescriptor().setModificationDateTime(OadrUtil.getCurrentTime());
			firstEvent.getEiEvent().getEventDescriptor().setModificationNumber(0);
			
			List<String> resourceList=firstEvent.getEiEvent().getEiTarget().getResourceID();
			resourceList.clear();
			
			String propFileResourceID[]= propertiesFileReader.getFourResourceID();
			
			targetType.getResourceID().clear();
			targetType.getResourceID().add(propFileResourceID[0]);
			targetType.getResourceID().add(propFileResourceID[1]);
			
			EiTargetType targetTypeSecondEvent = secondEvent.getEiEvent().getEiTarget();
			
			secondEvent.getEiEvent().getEiTarget().getGroupID().add(propertiesFileReader.getGroupID());
			secondEvent.getEiEvent().getEiTarget().getPartyID().add(propertiesFileReader.getParty_ID());
			
			secondEvent.getEiEvent().getEventDescriptor().setModificationDateTime(OadrUtil.getCurrentTime());
			secondEvent.getEiEvent().getEventDescriptor().setModificationNumber(0);
			
			List<String> resourceListSecondEvent=secondEvent.getEiEvent().getEiTarget().getResourceID();
			resourceListSecondEvent.clear();
			
			targetTypeSecondEvent.getResourceID().clear();
			targetTypeSecondEvent.getResourceID().add(propFileResourceID[2]);
			targetTypeSecondEvent.getResourceID().add(propFileResourceID[3]);
		}
		return oadrDistributeEvent;
	}
}