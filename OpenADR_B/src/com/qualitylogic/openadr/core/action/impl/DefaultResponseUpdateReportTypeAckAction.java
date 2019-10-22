package com.qualitylogic.openadr.core.action.impl;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBException;

import com.qualitylogic.openadr.core.action.IResponseRegisteredReportTypeAckAction;
import com.qualitylogic.openadr.core.base.BaseCreateReportAction;
import com.qualitylogic.openadr.core.base.BaseRegisterReportAction;
import com.qualitylogic.openadr.core.base.BaseRegisteredReportAction;
import com.qualitylogic.openadr.core.base.BaseUpdateReportAction;
import com.qualitylogic.openadr.core.bean.ServiceType;
import com.qualitylogic.openadr.core.common.TestSession;
import com.qualitylogic.openadr.core.signal.OadrCreateReportType;
import com.qualitylogic.openadr.core.signal.OadrCreatedOptType;
import com.qualitylogic.openadr.core.signal.OadrCreatedPartyRegistrationType;
import com.qualitylogic.openadr.core.signal.OadrCreatedReportType;
import com.qualitylogic.openadr.core.signal.OadrRegisterReportType;
import com.qualitylogic.openadr.core.signal.OadrRegisteredReportType;
import com.qualitylogic.openadr.core.signal.OadrTransportType;
import com.qualitylogic.openadr.core.signal.OadrProfiles.OadrProfile;
import com.qualitylogic.openadr.core.signal.OadrTransports.OadrTransport;
import com.qualitylogic.openadr.core.signal.OadrUpdateReportType;
import com.qualitylogic.openadr.core.signal.helper.CreateReportEventHelper;
import com.qualitylogic.openadr.core.signal.helper.CreatedOptEventHelper;
import com.qualitylogic.openadr.core.signal.helper.CreatedReportEventHelper;
import com.qualitylogic.openadr.core.signal.helper.RegisterReportEventHelper;
import com.qualitylogic.openadr.core.signal.helper.RegisteredReportEventHelper;
import com.qualitylogic.openadr.core.signal.helper.UpdateReportEventHelper;
import com.qualitylogic.openadr.core.util.Clone;
import com.qualitylogic.openadr.core.util.PropertiesFileReader;

public class DefaultResponseUpdateReportTypeAckAction extends BaseUpdateReportAction{
	ServiceType serivceType;
	boolean isCreaterOfRegisterReport;
	OadrCreateReportType oadrCreateReportType;
	public DefaultResponseUpdateReportTypeAckAction(ServiceType serivceType,boolean isCreaterOfRegisterReport,OadrCreateReportType oadrCreateReportType){
		this.serivceType=serivceType;
		this.isCreaterOfRegisterReport=isCreaterOfRegisterReport;
		this.oadrCreateReportType=oadrCreateReportType;
	}


	public OadrUpdateReportType getOadrUpdateReportType() {

		try {
			if(oadrUpdateReportType!=null) return oadrUpdateReportType;
	/*		
			if(oadrCreateReportType==null){
				ArrayList<OadrCreateReportType>  oadrCreateReportTypeReceived=TestSession.getOadrCreateReportTypeReceivedList();
				oadrCreateReportType=oadrCreateReportTypeReceived.get(oadrCreateReportTypeReceived.size()-1);
			}*/
			oadrUpdateReportType = UpdateReportEventHelper.loadOadrUpdateReport(serivceType,isCreaterOfRegisterReport,oadrCreateReportType);
		} catch (FileNotFoundException | UnsupportedEncodingException
				| JAXBException e) {
			e.printStackTrace();
		}
			
		return oadrUpdateReportType;
	}

	
	public void resetToInitialState() {
		isEventCompleted = false;
		oadrUpdateReportType = null;
	}

}