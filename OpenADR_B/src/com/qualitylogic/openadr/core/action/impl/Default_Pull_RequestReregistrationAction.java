package com.qualitylogic.openadr.core.action.impl;

import java.util.ArrayList;
import java.util.List;

import com.qualitylogic.openadr.core.base.BaseDistributeEventAction;
import com.qualitylogic.openadr.core.base.BaseReregistrationAction;
import com.qualitylogic.openadr.core.bean.ServiceType;
import com.qualitylogic.openadr.core.common.TestSession;
import com.qualitylogic.openadr.core.signal.EiTargetType;
import com.qualitylogic.openadr.core.signal.EventStatusEnumeratedType;
import com.qualitylogic.openadr.core.signal.OadrCreateOptType;
import com.qualitylogic.openadr.core.signal.OadrDistributeEventType;
import com.qualitylogic.openadr.core.signal.OadrRequestEventType;
import com.qualitylogic.openadr.core.signal.OadrRequestReregistrationType;
import com.qualitylogic.openadr.core.signal.OptTypeType;
import com.qualitylogic.openadr.core.signal.QualifiedEventIDType;
import com.qualitylogic.openadr.core.signal.helper.CreatedOptEventHelper;
import com.qualitylogic.openadr.core.signal.helper.DistributeEventSignalHelper;
import com.qualitylogic.openadr.core.signal.helper.RequestReregistrationSignalHelper;
import com.qualitylogic.openadr.core.util.OadrUtil;
import com.qualitylogic.openadr.core.util.PropertiesFileReader;

public class Default_Pull_RequestReregistrationAction extends
	BaseReregistrationAction {



	public boolean isEventCompleted() {
		return isEventCompleted;
	}

	@Override
	public void setEventCompleted(boolean isEventCompleted) {
		this.isEventCompleted = isEventCompleted;
	}

	@Override
	public OadrRequestReregistrationType getOadrRequestReregistration() {

		if (oadrRequestReregistrationType == null) {
			oadrRequestReregistrationType = RequestReregistrationSignalHelper
					.loadOadrRequestReregistrationType();
		
			oadrRequestReregistrationType.setVenID(new PropertiesFileReader().getVenID());
		}

		return oadrRequestReregistrationType;
	}



}