package com.qualitylogic.openadr.core.action.impl;

import com.qualitylogic.openadr.core.action.IResponseEventAction;
import com.qualitylogic.openadr.core.base.BaseResponseAction;
import com.qualitylogic.openadr.core.signal.OadrResponseType;
import com.qualitylogic.openadr.core.signal.helper.ResponseHelper;

public class E1_0320_Default_ResponseEventAction extends
BaseResponseAction {

	private String responseCode = "200";

	// Use this Constructor if you want to set specific response code other than
	// 200.
	public E1_0320_Default_ResponseEventAction(String responseCode) {
		this.responseCode = responseCode;
	}

	// Default Constructor sets response code to 200.
	public E1_0320_Default_ResponseEventAction() {
	}


	public OadrResponseType getResponse() {
		OadrResponseType oadrResponse = null;
		try {
			oadrResponse = ResponseHelper
					.loadOadrResponse("oadrResponse_successdefault.xml");
			oadrResponse.getEiResponse().setResponseCode(responseCode);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return oadrResponse;
	}

}