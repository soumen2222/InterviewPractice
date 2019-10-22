package com.qualitylogic.openadr.core.base;

import org.apache.commons.lang3.StringUtils;

import com.qualitylogic.openadr.core.action.DistributeEventActionList;
import com.qualitylogic.openadr.core.action.ICreatedEventResult;
import com.qualitylogic.openadr.core.action.IDistributeEventAction;
import com.qualitylogic.openadr.core.action.impl.Default_CreatedEventResultOptINOptOut;
import com.qualitylogic.openadr.core.bean.ModeType;
import com.qualitylogic.openadr.core.bean.VENServiceType;
import com.qualitylogic.openadr.core.channel.util.StringUtil;
import com.qualitylogic.openadr.core.common.TestSession;
import com.qualitylogic.openadr.core.exception.FailedException;
import com.qualitylogic.openadr.core.signal.OadrDistributeEventType;
import com.qualitylogic.openadr.core.signal.OadrResponseType;
import com.qualitylogic.openadr.core.signal.helper.ResponseHelper;
import com.qualitylogic.openadr.core.signal.helper.SchemaHelper;
import com.qualitylogic.openadr.core.util.OadrUtil;
import com.qualitylogic.openadr.core.vtn.VTNService;
import com.qualitylogic.openadr.core.vtn.VtnToVenClient;

public abstract class VtnPushTestCase extends VtnTestCase {
	
	protected void newInit(){
		super.newInit();
		TestSession.setMode(ModeType.PUSH);
	}

	protected void listenForRequests() throws Exception {
		VTNService.startVTNService();
	}

	@Override
	public void cleanUp() throws Exception {
		VTNService.stopVTNService();
	}

	protected void sendDistributeEventWithEmptyResponse(IDistributeEventAction distributeEventAction) throws Exception {
		OadrDistributeEventType distributeEvent = distributeEventAction.getDistributeEvent();
		String text = SchemaHelper.getDistributeEventAsString(distributeEvent);
		String response = VtnToVenClient.post(text);
		if (!StringUtil.isBlank(response)) {
			throw new FailedException("Expected null response, received " + response);
		}
	}
	
	protected ICreatedEventResult sendDistributeEvent(IDistributeEventAction distributeEventAction) throws Exception {
		ICreatedEventResult createdEvent = new Default_CreatedEventResultOptINOptOut();
		createdEvent.setExpectedOptInCount(1);
		createdEvent.addDistributeEvent(distributeEventAction);

		distributeEventAction.setCreatedEventSuccessCriteria(createdEvent);
		distributeEventAction.setEventCompleted(true);
		DistributeEventActionList.addDistributeEventAction(distributeEventAction);
	
		OadrDistributeEventType distributeEvent = distributeEventAction.getDistributeEvent();
		String text = SchemaHelper.getDistributeEventAsString(distributeEvent);
		VtnToVenClient.post(text, VENServiceType.EiEvent);
		
		distributeEventAction.startCreatedEventTimeoutActionThread();
		return createdEvent;
	}

	protected OadrResponseType sendDistributeEvent(IDistributeEventAction distributeEventAction, String responseCode) throws Exception {
		OadrDistributeEventType distributeEvent = distributeEventAction.getDistributeEvent();
		String text = SchemaHelper.getDistributeEventAsString(distributeEvent);

        distributeEventAction.setEventCompleted(true);
        DistributeEventActionList.addDistributeEventAction(distributeEventAction);
        
		String responseText = VtnToVenClient.post(text);
		
		OadrResponseType response = null;
		if (!StringUtils.isBlank(responseText)) {
			response = ResponseHelper.createOadrResponseFromString(responseText);
			String eiResponseCode = response.getEiResponse().getResponseCode();
			if (!eiResponseCode.equals(responseCode)) {
				throw new FailedException("Expected OadrResponse " + responseCode + ", received " + eiResponseCode);
			}
		}
		
		return response;
	}

	protected void waitForCreatedEvent(ICreatedEventResult createdEvent) {
		while (System.currentTimeMillis() < OadrUtil.getCreatedEventAsynchTimeout()) {
			pause(1);
			
			if (createdEvent.isExpectedCreatedEventReceived()) {
				break;
			} else if (TestSession.isAtleastOneValidationErrorPresent()) {
				break;
			}
		}

		if (!createdEvent.isExpectedCreatedEventReceived()) {
			throw new FailedException("CreatedEvent has not been received.");
		}

		checkForValidationErrors();
	}
}
