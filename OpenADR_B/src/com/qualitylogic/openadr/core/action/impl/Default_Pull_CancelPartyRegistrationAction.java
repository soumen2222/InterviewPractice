package com.qualitylogic.openadr.core.action.impl;

import com.qualitylogic.openadr.core.base.BaseCancelPartyRegistrationAction;
import com.qualitylogic.openadr.core.channel.util.StringUtil;
import com.qualitylogic.openadr.core.signal.OadrCancelPartyRegistrationType;
import com.qualitylogic.openadr.core.signal.helper.CancelPartyRegistrationHelper;
import com.qualitylogic.openadr.core.util.PropertiesFileReader;

public class Default_Pull_CancelPartyRegistrationAction extends BaseCancelPartyRegistrationAction {
	private String registrationID; 

	private boolean hasVenID = true;
	
	public boolean isEventCompleted() {
		return isEventCompleted;
	}

	public void setEventCompleted(boolean isEventCompleted) {
		this.isEventCompleted = isEventCompleted;
	}

	public void setHasVenID(boolean hasVenID) {
		this.hasVenID = hasVenID;
	}
	
	@Override
	public OadrCancelPartyRegistrationType getOadrCancelPartyRegistration() {
		
		if (oadrCancelPartyRegistrationType == null) {
			oadrCancelPartyRegistrationType = new CancelPartyRegistrationHelper()
					.loadOadrCancelPartyRegistrationRequest();
		
			if (hasVenID) {
				oadrCancelPartyRegistrationType.setVenID(new PropertiesFileReader().getVenID());
			} else {
				oadrCancelPartyRegistrationType.setVenID(null);
			}
			
			if (!StringUtil.isBlank(registrationID)) {
				oadrCancelPartyRegistrationType.setRegistrationID(registrationID);
			}
		}

		return oadrCancelPartyRegistrationType;		
	}

	public void setRegistrationID(String registrationID) {
		this.registrationID = registrationID;
	}
}