package com.qualitylogic.openadr.core.action.impl;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;

import javax.xml.bind.JAXBException;

import com.qualitylogic.openadr.core.action.IResponseUpdatedReportTypeAckAction;
import com.qualitylogic.openadr.core.base.ErrorConst;
import com.qualitylogic.openadr.core.common.IPrompt;
import com.qualitylogic.openadr.core.signal.EiResponseType;
import com.qualitylogic.openadr.core.signal.OadrUpdatedReportType;
import com.qualitylogic.openadr.core.signal.helper.UpdatedReportEventHelper;

public class DefaultResponseUpdatedReportTypeAckAction implements IResponseUpdatedReportTypeAckAction{


	private boolean isEventCompleted;
	private String responseCode = "200";
	OadrUpdatedReportType oadrUpdatedReportType = null;
	boolean islastEvent;
	
	// Use this Constructor if you want to set specific response code other than
	// 200.
	public DefaultResponseUpdatedReportTypeAckAction(String responseCode) {
		this.responseCode = responseCode;
	}

	// Default Constructor sets response code to 200.
	public DefaultResponseUpdatedReportTypeAckAction(){
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
			oadrUpdatedReportType = UpdatedReportEventHelper.loadOadrUpdatedReportType("oadrUpdatedReport.xml");
			if (!responseCode.equals(ErrorConst.OK_200)) {
				EiResponseType eiResponse = oadrUpdatedReportType.getEiResponse();
				eiResponse.setResponseCode(responseCode);
				eiResponse.setResponseDescription("ERROR");
			}
			
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