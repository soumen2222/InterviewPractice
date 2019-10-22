package com.qualitylogic.openadr.core.action.impl;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.xml.bind.JAXBException;

import com.qualitylogic.openadr.core.action.IResponseCreatedReportTypeAckAction;
import com.qualitylogic.openadr.core.action.IResponseRegisteredReportTypeAckAction;
import com.qualitylogic.openadr.core.action.IResponseUpdatedReportTypeAckAction;
import com.qualitylogic.openadr.core.common.IPrompt;
import com.qualitylogic.openadr.core.signal.OadrCreatedOptType;
import com.qualitylogic.openadr.core.signal.OadrCreatedPartyRegistrationType;
import com.qualitylogic.openadr.core.signal.OadrCreatedReportType;
import com.qualitylogic.openadr.core.signal.OadrRegisteredReportType;
import com.qualitylogic.openadr.core.signal.OadrTransportType;
import com.qualitylogic.openadr.core.signal.OadrProfiles.OadrProfile;
import com.qualitylogic.openadr.core.signal.OadrTransports.OadrTransport;
import com.qualitylogic.openadr.core.signal.OadrUpdatedReportType;
import com.qualitylogic.openadr.core.signal.helper.CreatedOptEventHelper;
import com.qualitylogic.openadr.core.signal.helper.CreatedReportEventHelper;
import com.qualitylogic.openadr.core.signal.helper.RegisteredReportEventHelper;
import com.qualitylogic.openadr.core.signal.helper.UpdatedReportEventHelper;
import com.qualitylogic.openadr.core.util.Clone;
import com.qualitylogic.openadr.core.util.PropertiesFileReader;

public class DefaultResponseUpdatedReport_001Action implements IResponseUpdatedReportTypeAckAction{


	private boolean isEventCompleted;
	private String responseCode = "200";
	OadrUpdatedReportType oadrUpdatedReportType = null;
	boolean islastEvent;
	
	// Use this Constructor if you want to set specific response code other than
	// 200.
	public DefaultResponseUpdatedReport_001Action(String responseCode) {
		this.responseCode = responseCode;
	}

	// Default Constructor sets response code to 200.
	public DefaultResponseUpdatedReport_001Action(){
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

	public OadrUpdatedReportType getOadrUpdatedReportResponse() {

		try {
			oadrUpdatedReportType = UpdatedReportEventHelper.loadOadrUpdatedReportType();
		} catch (FileNotFoundException | UnsupportedEncodingException
				| JAXBException e) {
			e.printStackTrace();
		}
			
		
		return oadrUpdatedReportType;
	}

	public boolean islastEvent(){
		return islastEvent;
	}
	public void setlastEvent(boolean islastEvent){
		this.islastEvent=islastEvent;
	}
	public void resetToInitialState() {
		isEventCompleted = false;
		oadrUpdatedReportType = null;
	}
	
	private IPrompt prompt;
	
	public void setPrompt(IPrompt prompt){
		this.prompt=prompt;
	}
	public IPrompt getPrompt(){
		return prompt;
	}
}