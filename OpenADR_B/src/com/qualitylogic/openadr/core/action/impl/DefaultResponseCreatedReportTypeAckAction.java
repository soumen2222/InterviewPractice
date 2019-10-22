package com.qualitylogic.openadr.core.action.impl;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBException;

import com.qualitylogic.openadr.core.action.IResponseCreatedReportTypeAckAction;
import com.qualitylogic.openadr.core.base.ErrorConst;
import com.qualitylogic.openadr.core.common.IPrompt;
import com.qualitylogic.openadr.core.signal.EiResponseType;
import com.qualitylogic.openadr.core.signal.OadrCreatedReportType;
import com.qualitylogic.openadr.core.signal.helper.CreatedReportEventHelper;

public class DefaultResponseCreatedReportTypeAckAction implements IResponseCreatedReportTypeAckAction{


	private boolean isEventCompleted;
	private String responseCode = "200";
	OadrCreatedReportType oadrCreatedReportType = null;
	boolean islastEvent;
	IPrompt prompt;

	// Use this Constructor if you want to set specific response code other than
	// 200.
	public DefaultResponseCreatedReportTypeAckAction(String responseCode) {
		this.responseCode = responseCode;
	}

	// Default Constructor sets response code to 200.
	public DefaultResponseCreatedReportTypeAckAction(){
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

	public OadrCreatedReportType getOadrCreatedReportResponse() {

		try {
			oadrCreatedReportType = CreatedReportEventHelper.loadOadrCreatedReport();
			if (!responseCode.equals(ErrorConst.OK_200)) {
				EiResponseType eiResponse = oadrCreatedReportType.getEiResponse();
				eiResponse.setResponseCode(responseCode);
				eiResponse.setResponseDescription("ERROR");
			}
			
			for (String reportRequestID : reportRequestIDs) {
				oadrCreatedReportType.getOadrPendingReports().getReportRequestID().add(reportRequestID);
			}
			
		} catch (FileNotFoundException | UnsupportedEncodingException
				| JAXBException e) {
			e.printStackTrace();
		}
			
		return oadrCreatedReportType;
	}

	
	public boolean islastEvent(){
		return islastEvent;
	}
	public void setlastEvent(boolean islastEvent){
		this.islastEvent=islastEvent;
	}
	
	public void resetToInitialState() {
		isEventCompleted = false;
		oadrCreatedReportType = null;
	}
	
	@Override	
	public void setPrompt(IPrompt prompt){
		this.prompt=prompt;
	}
	@Override
	public IPrompt getPrompt(){
		return prompt;
	}

	@Override
	public void addReportRequestID(String reportRequestID) {
		reportRequestIDs.add(reportRequestID);
	}
	
	private List<String> reportRequestIDs = new ArrayList<>();
}