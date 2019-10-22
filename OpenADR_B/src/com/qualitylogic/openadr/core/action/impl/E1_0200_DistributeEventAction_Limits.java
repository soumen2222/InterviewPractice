package com.qualitylogic.openadr.core.action.impl;

import com.qualitylogic.openadr.core.base.BaseDistributeEventAction;
import com.qualitylogic.openadr.core.signal.OadrDistributeEventType;
import com.qualitylogic.openadr.core.signal.OadrRequestEventType;
import com.qualitylogic.openadr.core.signal.helper.DistributeEventSignalHelper;
import com.qualitylogic.openadr.core.util.PropertiesFileReader;

public class E1_0200_DistributeEventAction_Limits extends
		BaseDistributeEventAction {

	private static final long serialVersionUID = 1L;
	private OadrDistributeEventType oadrDistributeEvent = null;

	public boolean isPreConditionsMet(OadrRequestEventType oadrRequestEvent) {

		boolean isCommonPreConditionsMet = isCommonPreConditionsMet(oadrRequestEvent);

		return isCommonPreConditionsMet;
	}

	public boolean isEventCompleted() {
		return isEventCompleted;
	}

	public void setEventCompleted(boolean isEventCompleted) {
		this.isEventCompleted = isEventCompleted;
	}

	public OadrDistributeEventType getDistributeEvent() {

		if (oadrDistributeEvent == null) {
			PropertiesFileReader propertiesFileReader = new PropertiesFileReader();
			oadrDistributeEvent = new DistributeEventSignalHelper()
					.loadOadrDistributeEvent("oadrDistributeEvent_Pull_CPP_limits.xml");
			int firstIntervalStartTimeDelayMin = 5;
			DistributeEventSignalHelper
					.setNewReqIDEvntIDStartTimeAndMarketCtx1(
							oadrDistributeEvent, firstIntervalStartTimeDelayMin);

			// Set Max Values
			oadrDistributeEvent
					.setRequestID("A0123456789012345678901234567890123456789012345678");
			oadrDistributeEvent.setVtnID(propertiesFileReader
					.get("MaxLen_VTN_ID"));
			// EventID
			oadrDistributeEvent
					.getOadrEvent()
					.get(0)
					.getEiEvent()
					.getEventDescriptor()
					.setEventID(
							"A0123456789012345678901234567890123456789012345678");
			oadrDistributeEvent
					.getOadrEvent()
					.get(1)
					.getEiEvent()
					.getEventDescriptor()
					.setEventID(
							"B0123456789012345678901234567890123456789012345678");
			oadrDistributeEvent
					.getOadrEvent()
					.get(2)
					.getEiEvent()
					.getEventDescriptor()
					.setEventID(
							"C0123456789012345678901234567890123456789012345678");
			oadrDistributeEvent
					.getOadrEvent()
					.get(3)
					.getEiEvent()
					.getEventDescriptor()
					.setEventID(
							"D0123456789012345678901234567890123456789012345678");
			// VenID
			oadrDistributeEvent.getOadrEvent().get(0).getEiEvent()
					.getEiTarget().getVenID()
					.set(0, propertiesFileReader.getVenID());
			oadrDistributeEvent.getOadrEvent().get(1).getEiEvent()
					.getEiTarget().getVenID()
					.set(0, propertiesFileReader.getVenID());
			oadrDistributeEvent.getOadrEvent().get(2).getEiEvent()
					.getEiTarget().getVenID()
					.set(0, propertiesFileReader.getVenID());
			oadrDistributeEvent.getOadrEvent().get(3).getEiEvent()
					.getEiTarget().getVenID()
					.set(0, propertiesFileReader.getVenID());
			// MarketContext
			oadrDistributeEvent
					.getOadrEvent()
					.get(0)
					.getEiEvent()
					.getEventDescriptor()
					.getEiMarketContext()
					.setMarketContext(
							propertiesFileReader.get("MaxLen_MarketContext"));
			oadrDistributeEvent
					.getOadrEvent()
					.get(1)
					.getEiEvent()
					.getEventDescriptor()
					.getEiMarketContext()
					.setMarketContext(
							propertiesFileReader.get("MaxLen_MarketContext"));
			oadrDistributeEvent
					.getOadrEvent()
					.get(2)
					.getEiEvent()
					.getEventDescriptor()
					.getEiMarketContext()
					.setMarketContext(
							propertiesFileReader.get("MaxLen_MarketContext"));
			oadrDistributeEvent
					.getOadrEvent()
					.get(3)
					.getEiEvent()
					.getEventDescriptor()
					.getEiMarketContext()
					.setMarketContext(
							propertiesFileReader.get("MaxLen_MarketContext"));
			// Set eiResponse requestID
			oadrDistributeEvent.getEiResponse().setRequestID("");

		}
		return oadrDistributeEvent;

	}
}