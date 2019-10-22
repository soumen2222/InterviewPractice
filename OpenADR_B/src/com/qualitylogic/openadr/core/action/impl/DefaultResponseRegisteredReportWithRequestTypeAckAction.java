package com.qualitylogic.openadr.core.action.impl;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBException;

import com.qualitylogic.openadr.core.action.IResponseCreateReportTypeAckAction;
import com.qualitylogic.openadr.core.action.IResponseRegisteredReportTypeAckAction;
import com.qualitylogic.openadr.core.base.BaseRegisteredReportAction;
import com.qualitylogic.openadr.core.bean.ServiceType;
import com.qualitylogic.openadr.core.common.TestSession;
import com.qualitylogic.openadr.core.signal.OadrCreateReportType;
import com.qualitylogic.openadr.core.signal.OadrCreatedOptType;
import com.qualitylogic.openadr.core.signal.OadrCreatedPartyRegistrationType;
import com.qualitylogic.openadr.core.signal.OadrRegisterReportType;
import com.qualitylogic.openadr.core.signal.OadrRegisteredReportType;
import com.qualitylogic.openadr.core.signal.OadrReportRequestType;
import com.qualitylogic.openadr.core.signal.OadrResponseType;
import com.qualitylogic.openadr.core.signal.OadrTransportType;
import com.qualitylogic.openadr.core.signal.OadrProfiles.OadrProfile;
import com.qualitylogic.openadr.core.signal.OadrTransports.OadrTransport;
import com.qualitylogic.openadr.core.signal.helper.CreatedOptEventHelper;
import com.qualitylogic.openadr.core.signal.helper.RegisteredReportEventHelper;
import com.qualitylogic.openadr.core.util.Clone;
import com.qualitylogic.openadr.core.util.PropertiesFileReader;

public class DefaultResponseRegisteredReportWithRequestTypeAckAction extends BaseRegisteredReportAction{

	ServiceType serviceType;
	
	public DefaultResponseRegisteredReportWithRequestTypeAckAction(ServiceType serviceType){
		this.serviceType=serviceType;
	}


	public OadrRegisteredReportType getOadrRegisteredReportResponse() {

		try {
			oadrRegisteredReportType = RegisteredReportEventHelper.loadOadrRegisteredReport("oadrRegisteredReport.xml");
			
			ArrayList<OadrRegisterReportType> registerParty = TestSession.getOadrRegisterReportTypeReceivedList();
			
			OadrRegisterReportType oadrRegisterReportReceived = registerParty.get(registerParty.size()-1);
			oadrRegisteredReportType.getEiResponse().setRequestID(oadrRegisterReportReceived.getRequestID());
			oadrRegisteredReportType.getEiResponse().setResponseCode("200");
			oadrRegisteredReportType.getEiResponse().setResponseDescription("OK");
			oadrRegisteredReportType.getOadrReportRequest().clear();
			
			IResponseCreateReportTypeAckAction responseCreateReportTypeAckAction = new DefaultResponseCreateReportRequest_001Action(serviceType);
			
			OadrCreateReportType oadrCreateReportType  = responseCreateReportTypeAckAction.getOadrCreateReportResponse();
			//List<OadrReportRequestType>  oadrReportRequestList = oadrRegisteredReportType.getOadrReportRequest();
			oadrRegisteredReportType.getOadrReportRequest().addAll(oadrCreateReportType.getOadrReportRequest());
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