package com.qualitylogic.openadr.core.action.impl;

import java.util.List;

import javax.xml.datatype.Duration;
import javax.xml.datatype.XMLGregorianCalendar;

import com.qualitylogic.openadr.core.base.BaseDistributeEventAction;
import com.qualitylogic.openadr.core.signal.EiEventType;
import com.qualitylogic.openadr.core.signal.EiTargetType;
import com.qualitylogic.openadr.core.signal.OadrDistributeEventType;
import com.qualitylogic.openadr.core.signal.OadrRequestEventType;
import com.qualitylogic.openadr.core.signal.OadrDistributeEventType.OadrEvent;
import com.qualitylogic.openadr.core.signal.helper.DistributeEventSignalHelper;
import com.qualitylogic.openadr.core.signal.xcal.Dtstart;
import com.qualitylogic.openadr.core.util.OadrUtil;
import com.qualitylogic.openadr.core.util.PropertiesFileReader;

public class DefaultDistributeEvent_005Action extends BaseDistributeEventAction {

	private static final long serialVersionUID = 8308068819168533406L;

	public boolean isPreConditionsMet(OadrRequestEventType oadrRequestEvent) {
		boolean isCommonPreConditionsMet = isCommonPreConditionsMet(oadrRequestEvent);
		return isCommonPreConditionsMet;
	}

	public OadrDistributeEventType getDistributeEvent() {
		PropertiesFileReader propertiesFileReader=new PropertiesFileReader();
		if (oadrDistributeEvent == null) {
			oadrDistributeEvent = new DistributeEventSignalHelper().loadOadrDistributeEvent("Event_005.xml");
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

			List<OadrEvent> events = oadrDistributeEvent.getOadrEvent();

			// set Baseline Dtstart
			XMLGregorianCalendar currentTime = OadrUtil.getCurrentTime();
			Duration durationDays = OadrUtil.createDuration(false, 0, 0, 1, 0, 0, 0);
			
			currentTime.add(durationDays);
			Dtstart dtstart = new Dtstart();
			dtstart.setDateTime(currentTime);
			
			for (OadrEvent event : events) {
				event.getEiEvent().getEiEventSignals().getEiEventBaseline().setDtstart(dtstart);
			}
		}
		
		return oadrDistributeEvent;
	}
}
