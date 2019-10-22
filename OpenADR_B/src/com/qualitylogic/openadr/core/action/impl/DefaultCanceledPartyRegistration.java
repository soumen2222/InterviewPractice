package com.qualitylogic.openadr.core.action.impl;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.xml.bind.JAXBException;

import com.qualitylogic.openadr.core.base.BaseCanceledOptEventAction;
import com.qualitylogic.openadr.core.base.BaseCanceledPartyRegistrationAction;
import com.qualitylogic.openadr.core.base.ErrorConst;
import com.qualitylogic.openadr.core.signal.EiResponseType;
import com.qualitylogic.openadr.core.signal.OadrCanceledPartyRegistrationType;
import com.qualitylogic.openadr.core.signal.OadrCreatedOptType;
import com.qualitylogic.openadr.core.signal.OadrCreatedPartyRegistrationType;
import com.qualitylogic.openadr.core.signal.OadrTransportType;
import com.qualitylogic.openadr.core.signal.OadrProfiles.OadrProfile;
import com.qualitylogic.openadr.core.signal.OadrTransports.OadrTransport;
import com.qualitylogic.openadr.core.signal.helper.CanceledPartyRegistrationHelper;
import com.qualitylogic.openadr.core.signal.helper.CreatedOptEventHelper;
import com.qualitylogic.openadr.core.util.Clone;
import com.qualitylogic.openadr.core.util.PropertiesFileReader;

public class DefaultCanceledPartyRegistration extends BaseCanceledPartyRegistrationAction{


	private boolean isEventCompleted;
	private String responseCode = "200";

	// Use this Constructor if you want to set specific response code other than
	// 200.
	public DefaultCanceledPartyRegistration(String responseCode) {
		this.responseCode = responseCode;
	}

	// Default Constructor sets response code to 200.
	public DefaultCanceledPartyRegistration(){
	}

	public boolean isPreConditionsMet() {
		return true;
	}

	public boolean isEventCompleted() {
		return isEventCompleted;
	}

	public void setEventCompleted(boolean isEventCompleted) {
		this.isEventCompleted = isEventCompleted;
	}

	public OadrCanceledPartyRegistrationType getOadrCanceledPartyRegistration(){

		OadrCanceledPartyRegistrationType oadrCanceledPartyRegistrationType = CanceledPartyRegistrationHelper.loadOadrCanceledPartyRegistrationType();
		EiResponseType eiResponse = oadrCanceledPartyRegistrationType.getEiResponse();
		eiResponse.setResponseCode(responseCode);
		if (!responseCode.equals(ErrorConst.OK_200)) {
			eiResponse.setResponseDescription("ERROR");
		}

		return oadrCanceledPartyRegistrationType;
	}
}