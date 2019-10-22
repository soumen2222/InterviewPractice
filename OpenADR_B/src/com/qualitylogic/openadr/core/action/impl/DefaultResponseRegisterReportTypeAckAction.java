package com.qualitylogic.openadr.core.action.impl;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;

import javax.xml.bind.JAXBException;

import org.apache.commons.lang3.StringUtils;

import com.qualitylogic.openadr.core.base.BaseRegisterReportAction;
import com.qualitylogic.openadr.core.signal.OadrRegisterReportType;
import com.qualitylogic.openadr.core.signal.helper.RegisterReportEventHelper;

public class DefaultResponseRegisterReportTypeAckAction extends BaseRegisterReportAction{

	private String reportRequestID;
	
	public DefaultResponseRegisterReportTypeAckAction(){
	}

	public OadrRegisterReportType getOadrRegisterReportResponse() {

		try {
			registerReportResponse = RegisterReportEventHelper.loadOadrRegisterTelemetryStatusReportType();
			if (StringUtils.isNotBlank(reportRequestID)) {
				registerReportResponse.setReportRequestID(reportRequestID);
			}
		} catch (FileNotFoundException | UnsupportedEncodingException
				| JAXBException e) {
			e.printStackTrace();
		}
			
		return registerReportResponse;
	}

	
	public void resetToInitialState() {
		isEventCompleted = false;
		registerReportResponse = null;
	}

	public void setReportRequestID(String reportRequestID) {
		this.reportRequestID = reportRequestID;
	}
}