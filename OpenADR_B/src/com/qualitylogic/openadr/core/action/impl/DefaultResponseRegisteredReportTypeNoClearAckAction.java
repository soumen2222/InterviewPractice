package com.qualitylogic.openadr.core.action.impl;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import javax.xml.bind.JAXBException;

import com.qualitylogic.openadr.core.base.BaseRegisteredReportAction;
import com.qualitylogic.openadr.core.common.TestSession;
import com.qualitylogic.openadr.core.signal.OadrRegisterReportType;
import com.qualitylogic.openadr.core.signal.OadrRegisteredReportType;
import com.qualitylogic.openadr.core.signal.helper.RegisteredReportEventHelper;

public class DefaultResponseRegisteredReportTypeNoClearAckAction extends BaseRegisteredReportAction{


	public DefaultResponseRegisteredReportTypeNoClearAckAction(){
	}


	public OadrRegisteredReportType getOadrRegisteredReportResponse() {

		try {
			oadrRegisteredReportType = RegisteredReportEventHelper.loadOadrRegisteredReport("oadrRegisteredReport.xml");
			
			ArrayList<OadrRegisterReportType> registerParty = TestSession.getOadrRegisterReportTypeReceivedList();
			
			OadrRegisterReportType oadrRegisterReportReceived = registerParty.get(registerParty.size()-1);
			oadrRegisteredReportType.getEiResponse().setRequestID(oadrRegisterReportReceived.getRequestID());
			oadrRegisteredReportType.getEiResponse().setResponseCode("200");
			oadrRegisteredReportType.getEiResponse().setResponseDescription("OK");
		} catch (FileNotFoundException | UnsupportedEncodingException
				| JAXBException e) {
			e.printStackTrace();
		}
			
		return oadrRegisteredReportType;
	}

	
	public void resetToInitialState() {
		isEventCompleted = false;
		oadrRegisteredReportType = null;
	}
}