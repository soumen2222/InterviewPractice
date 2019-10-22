package com.qualitylogic.openadr.core.action.impl;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBException;

import com.qualitylogic.openadr.core.action.IResponseCanceledReportTypeAckAction;
import com.qualitylogic.openadr.core.common.IPrompt;
import com.qualitylogic.openadr.core.signal.OadrCanceledReportType;
import com.qualitylogic.openadr.core.signal.helper.CanceledReportEventHelper;

public class DefaultResponseCanceledReportTypeAckAction implements IResponseCanceledReportTypeAckAction{

	boolean islastEvent;
	
	private boolean isEventCompleted;
	private String responseCode = "200";
	private String responseDescription = "OK";
	
	OadrCanceledReportType oadrCanceledReportType = null;
	// Use this Constructor if you want to set specific response code other than
	// 200.
	public DefaultResponseCanceledReportTypeAckAction(String responseCode, String responseDescription) {
		this.responseCode = responseCode;
		this.responseDescription = responseDescription;
	}

	// Default Constructor sets response code to 200.
	public DefaultResponseCanceledReportTypeAckAction(){
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

	public OadrCanceledReportType getOadrCanceledReportResponse() {

		
		try {
			oadrCanceledReportType = CanceledReportEventHelper.loadOadrCanceledReportType("oadrCanceledReport.xml");
			oadrCanceledReportType.getEiResponse().setResponseCode(responseCode);
			oadrCanceledReportType.getEiResponse().setResponseDescription(responseDescription);
			
			for (String reportRequestID : reportRequestIDs) {
				oadrCanceledReportType.getOadrPendingReports().getReportRequestID().add(reportRequestID);
			}
			
		} catch (FileNotFoundException | UnsupportedEncodingException
				| JAXBException e) {
			e.printStackTrace();
		}
			
		return oadrCanceledReportType;
	}

	public void resetToInitialState() {
		isEventCompleted = false;
		oadrCanceledReportType = null;
	}
	
	
	public boolean islastEvent(){
		return islastEvent;
	}
	public void setlastEvent(boolean islastEvent){
		this.islastEvent=islastEvent;
	}
	
	private IPrompt prompt;
	
	public void setPrompt(IPrompt prompt){
		this.prompt=prompt;
	}
	public IPrompt getPrompt(){
		return prompt;
	}

	@Override
	public void addReportRequestID(String reportRequestID) {
		reportRequestIDs.add(reportRequestID);
	}
	
	private List<String> reportRequestIDs = new ArrayList<>();
}