package com.qualitylogic.openadr.core.action.impl;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;

import javax.xml.bind.JAXBException;

import com.qualitylogic.openadr.core.base.BaseCreateReportAction;
import com.qualitylogic.openadr.core.signal.OadrCreateReportType;
import com.qualitylogic.openadr.core.signal.helper.CreateReportEventHelper;

public class DefaultResponseCreateReportRequest_013Action extends BaseCreateReportAction{
	
	public OadrCreateReportType getOadrCreateReportResponse() {

		if(oadrCreateReportType!=null) return oadrCreateReportType;
		try {
			oadrCreateReportType = CreateReportEventHelper.loadOadrCreateReport_Request_013();
			
		} catch (FileNotFoundException | UnsupportedEncodingException
				| JAXBException e) {
			e.printStackTrace();
		}
			
		return oadrCreateReportType;
	}

	
	public void resetToInitialState() {
		isEventCompleted = false;
		oadrCreateReportType = null;
	}
}