package com.qualitylogic.openadr.core.action.impl;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;

import javax.xml.bind.JAXBException;

import com.qualitylogic.openadr.core.action.IResponseCreateReportTypeAckAction;
import com.qualitylogic.openadr.core.base.BaseCancelReportAction;
import com.qualitylogic.openadr.core.bean.ServiceType;
import com.qualitylogic.openadr.core.signal.OadrCancelReportType;
import com.qualitylogic.openadr.core.signal.helper.CancelReportEventHelper;

public class DefaultResponseCancelReportTypeAckAction extends BaseCancelReportAction{
	ServiceType service;
	IResponseCreateReportTypeAckAction responseCreateReportTypeAckAction;
	public DefaultResponseCancelReportTypeAckAction(ServiceType service,IResponseCreateReportTypeAckAction responseCreateReportTypeAckAction){
		this.service = service;
		this.responseCreateReportTypeAckAction=responseCreateReportTypeAckAction;
	}


	public OadrCancelReportType getOadrCancelReportResponse() {

		try {
			oadrCancelReportType = CancelReportEventHelper.loadOadrCancelReportType(responseCreateReportTypeAckAction.getOadrCreateReportResponse());
			oadrCancelReportType.setReportToFollow(reportToFollow);
		} catch (FileNotFoundException | UnsupportedEncodingException
				| JAXBException e) {
			e.printStackTrace();
		}
			
		return oadrCancelReportType;
	}

	
	public void resetToInitialState() {
		isEventCompleted = false;
		oadrCancelReportType = null;
	}


	@Override
	public void setReportToFollow(boolean reportToFollow) {
		this.reportToFollow = reportToFollow;
	}
	
	private boolean reportToFollow;
}
