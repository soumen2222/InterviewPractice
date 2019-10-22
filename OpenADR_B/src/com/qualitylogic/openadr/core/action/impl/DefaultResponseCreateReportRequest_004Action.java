package com.qualitylogic.openadr.core.action.impl;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.xml.bind.JAXBException;

import com.qualitylogic.openadr.core.action.IResponseRegisteredReportTypeAckAction;
import com.qualitylogic.openadr.core.base.BaseCreateReportAction;
import com.qualitylogic.openadr.core.base.BaseRegisterReportAction;
import com.qualitylogic.openadr.core.base.BaseRegisteredReportAction;
import com.qualitylogic.openadr.core.bean.ServiceType;
import com.qualitylogic.openadr.core.signal.OadrCreateReportType;
import com.qualitylogic.openadr.core.signal.OadrCreatedOptType;
import com.qualitylogic.openadr.core.signal.OadrCreatedPartyRegistrationType;
import com.qualitylogic.openadr.core.signal.OadrRegisterReportType;
import com.qualitylogic.openadr.core.signal.OadrRegisteredReportType;
import com.qualitylogic.openadr.core.signal.OadrTransportType;
import com.qualitylogic.openadr.core.signal.OadrProfiles.OadrProfile;
import com.qualitylogic.openadr.core.signal.OadrTransports.OadrTransport;
import com.qualitylogic.openadr.core.signal.helper.CreateReportEventHelper;
import com.qualitylogic.openadr.core.signal.helper.CreatedOptEventHelper;
import com.qualitylogic.openadr.core.signal.helper.RegisterReportEventHelper;
import com.qualitylogic.openadr.core.signal.helper.RegisteredReportEventHelper;
import com.qualitylogic.openadr.core.util.Clone;
import com.qualitylogic.openadr.core.util.PropertiesFileReader;

public class DefaultResponseCreateReportRequest_004Action extends BaseCreateReportAction{
	ServiceType serviceType=null;
	
	public DefaultResponseCreateReportRequest_004Action(ServiceType vtnorven){
		serviceType=vtnorven;
	}

	public OadrCreateReportType getOadrCreateReportResponse() {

		if(oadrCreateReportType!=null) return oadrCreateReportType;
		try {
			oadrCreateReportType = CreateReportEventHelper.loadOadrCreateReport_Request_004();
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