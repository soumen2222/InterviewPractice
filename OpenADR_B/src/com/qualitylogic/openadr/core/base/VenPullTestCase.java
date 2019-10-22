package com.qualitylogic.openadr.core.base;

import com.qualitylogic.openadr.core.bean.ModeType;
import com.qualitylogic.openadr.core.bean.VTNServiceType;
import com.qualitylogic.openadr.core.common.TestSession;
import com.qualitylogic.openadr.core.exception.FailedException;
import com.qualitylogic.openadr.core.signal.EiResponseType;
import com.qualitylogic.openadr.core.signal.OadrCancelPartyRegistrationType;
import com.qualitylogic.openadr.core.signal.OadrCancelReportType;
import com.qualitylogic.openadr.core.signal.OadrCanceledPartyRegistrationType;
import com.qualitylogic.openadr.core.signal.OadrCanceledReportType;
import com.qualitylogic.openadr.core.signal.OadrCreateReportType;
import com.qualitylogic.openadr.core.signal.OadrCreatedReportType;
import com.qualitylogic.openadr.core.signal.OadrDistributeEventType;
import com.qualitylogic.openadr.core.signal.OadrPendingReportsType;
import com.qualitylogic.openadr.core.signal.OadrPollType;
import com.qualitylogic.openadr.core.signal.OadrRegisterReportType;
import com.qualitylogic.openadr.core.signal.OadrRegisteredReportType;
import com.qualitylogic.openadr.core.signal.OadrRequestEventType;
import com.qualitylogic.openadr.core.signal.OadrRequestReregistrationType;
import com.qualitylogic.openadr.core.signal.OadrResponseType;
import com.qualitylogic.openadr.core.signal.helper.CancelPartyRegistrationHelper;
import com.qualitylogic.openadr.core.signal.helper.CancelReportEventHelper;
import com.qualitylogic.openadr.core.signal.helper.CanceledPartyRegistrationHelper;
import com.qualitylogic.openadr.core.signal.helper.CanceledReportEventHelper;
import com.qualitylogic.openadr.core.signal.helper.CreateReportEventHelper;
import com.qualitylogic.openadr.core.signal.helper.PollEventSignalHelper;
import com.qualitylogic.openadr.core.signal.helper.RegisterReportEventHelper;
import com.qualitylogic.openadr.core.signal.helper.RegisteredReportEventHelper;
import com.qualitylogic.openadr.core.signal.helper.RequestEventSignalHelper;
import com.qualitylogic.openadr.core.signal.helper.RequestReregistrationSignalHelper;
import com.qualitylogic.openadr.core.signal.helper.ResponseHelper;
import com.qualitylogic.openadr.core.signal.helper.SchemaHelper;
import com.qualitylogic.openadr.core.util.OadrUtil;
import com.qualitylogic.openadr.core.util.XMLDBUtil;
import com.qualitylogic.openadr.core.ven.VenToVtnClient;

public abstract class VenPullTestCase extends VenTestCase {
	
	protected void newInit() {
		super.newInit();
		TestSession.setMode(ModeType.PULL);
	}
	
	protected void sendPoll(Class<?> expected) throws Exception {
		OadrPollType poll = PollEventSignalHelper.loadOadrPollType();
		String text = SchemaHelper.getOadrPollTypeAsString(poll);
		String response = VenToVtnClient.post(text, VTNServiceType.OadrPoll);
		if (!OadrUtil.isExpected(response, expected)){
			throw new FailedException("Expected " + OadrUtil.getClassName(expected) + " has not been received");
		}
	}

	protected void sendRegisteredReport() throws Exception {
		OadrRegisteredReportType registeredReport = RegisteredReportEventHelper.loadOadrRegisteredReport();
		registeredReport.getOadrReportRequest().clear();
		
		String text = SchemaHelper.getRegisteredReportTypeAsString(registeredReport);
		String response = VenToVtnClient.post(text, VTNServiceType.EiReport);
		if (!OadrUtil.isExpected(response, OadrResponseType.class)) {
			throw new FailedException("Expected OadrResponse has not been received");
		}
	}

	protected void sendRegisteredReport(OadrRegisteredReportType registeredReport, OadrRegisterReportType registerReport) throws Exception {
		registeredReport.getEiResponse().setRequestID(registerReport.getRequestID());
		
		String text = SchemaHelper.getRegisteredReportTypeAsString(registeredReport);
		String response = VenToVtnClient.post(text,VTNServiceType.EiReport);
		if (!OadrUtil.isExpected(response, OadrCreatedReportType.class)) {
			throw new FailedException("Expected OadrCreatedReport has not been received");
		}
	}
	
	protected String pollDistributeEventString() {
		String distributeEvent = VenToVtnClient.poll(OadrDistributeEventType.class);
		
		if (!OadrUtil.isExpected(distributeEvent, OadrDistributeEventType.class)) {
			throw new FailedException("Expected OadrDistributeEvent has not been received");
		}
		
		return distributeEvent;
	}

	protected OadrRegisterReportType pollRegisterReport() {
		String registerReport = VenToVtnClient.poll(OadrRegisterReportType.class);

		if (!OadrUtil.isExpected(registerReport, OadrRegisterReportType.class)) {
			throw new FailedException("Expected OadrRegisterReport has not been received");
		}

		return RegisterReportEventHelper.createOadrRegisterReportFromString(registerReport);			
	}
	
	protected OadrCreateReportType pollCreateReport() {
		String createReportText = VenToVtnClient.poll(OadrCreateReportType.class);
		
		if (!OadrUtil.isExpected(createReportText, OadrCreateReportType.class)) {
			throw new FailedException("Expected OadrCreateReport has not been received");
		}
		
		return CreateReportEventHelper.createOadrCreateReportFromString(createReportText);
	}

	protected OadrCancelPartyRegistrationType pollCancelPartyRegistration() throws Exception {
		String response = VenToVtnClient.poll(OadrCancelPartyRegistrationType.class);
		
		if (!OadrUtil.isExpected(response, OadrCancelPartyRegistrationType.class)) {
			throw new FailedException("Expected OadrCancelPartyRegistration has not been received");
		}
		
		return CancelPartyRegistrationHelper.createOadrCancelPartyRegistrationTypeFromString(response);
	}
	
	protected OadrRequestReregistrationType pollRequestReregistration() throws Exception {
		String response = VenToVtnClient.poll(OadrRequestReregistrationType.class);
		
		if (!OadrUtil.isExpected(response, OadrRequestReregistrationType.class)) {
			throw new FailedException("Expected OadrRequestReregistration has not been received");
		}
		
		return RequestReregistrationSignalHelper.createOadrRequestReregistrationTypeFromString(response);
	}
	
	protected String sendRequestEvent() throws Exception {
		OadrRequestEventType requestEvent = RequestEventSignalHelper.getOadrRequestEvent();
		String text = SchemaHelper.getRequestEventAsString(requestEvent);
		return VenToVtnClient.post(text);
	}
	
	protected void checkOadrEvent(OadrDistributeEventType distributeEvent) {
		if (distributeEvent.getOadrEvent() == null || distributeEvent.getOadrEvent().size() != 1) {
			throw new FailedException("Expected OadrEvent is not present in the OadrDistributeEvent");
		}
	}

	protected void sendCanceledPartyRegistration(OadrCancelPartyRegistrationType cancelPartyRegistration) throws Exception {
		sendCanceledPartyRegistration(cancelPartyRegistration, ErrorConst.OK_200);
	}
	
	protected void sendCanceledPartyRegistration(OadrCancelPartyRegistrationType cancelPartyRegistration, String responseCode) throws Exception {
		OadrCanceledPartyRegistrationType canceledPartyRegistration = CanceledPartyRegistrationHelper.loadOadrCanceledPartyRegistrationType();
		canceledPartyRegistration.setRegistrationID(new XMLDBUtil().getRegistrationID());
		EiResponseType eiResponse = canceledPartyRegistration.getEiResponse();
		eiResponse.setRequestID(cancelPartyRegistration.getRequestID());
		eiResponse.setResponseCode(responseCode);
		eiResponse.setResponseDescription(responseCode.equals(ErrorConst.OK_200) ? "OK" : "ERROR");
		
		canceledPartyRegistration.setRegistrationID(cancelPartyRegistration.getRegistrationID());
		canceledPartyRegistration.setVenID(cancelPartyRegistration.getVenID());
		
		String text = SchemaHelper.getCanceledPartyRegistrationAsString(canceledPartyRegistration);
		String response = VenToVtnClient.post(text, VTNServiceType.EiRegistration);
		if (!OadrUtil.isExpected(response, OadrResponseType.class)) {
			throw new FailedException("Expected OadrResponse has not been received");
		}
	}

	protected OadrResponseType pollResponse() {
		String responseText = VenToVtnClient.poll(OadrResponseType.class);
		
		if (!OadrUtil.isExpected(responseText, OadrResponseType.class)) {
			throw new FailedException("Expected OadrResponseType has not been received");
		}
		
		return ResponseHelper.createOadrResponseFromString(responseText);
	}
	
	protected void sendResponse() throws Exception {
		OadrResponseType response = ResponseHelper.loadOadrResponse();
		String text = SchemaHelper.getOadrResponseAsString(response);
		VenToVtnClient.post(text,VTNServiceType.EiRegistration);
	}

	protected void checkResponseCode(OadrResponseType response, String expectedResponseCode) {
		String responseCode = response.getEiResponse().getResponseCode();
		if (!responseCode.equals(expectedResponseCode)) {
			throw new FailedException("Expected OadrResponse responseCode of " + expectedResponseCode + " has not been received. Got " + responseCode + ".");
		}
	}

	protected void sendCanceledReport(OadrCancelReportType cancelReport, String reportRequestID) throws Exception {
		sendCanceledReport(cancelReport, ErrorConst.OK_200, reportRequestID);
	}
	
	protected void sendCanceledReport(OadrCancelReportType cancelReport, String responseCode, String reportRequestID) throws Exception {
		OadrCanceledReportType canceledReport = CanceledReportEventHelper.loadOadrCanceledReportType(cancelReport);
		canceledReport.getEiResponse().setResponseCode(responseCode);
		if (!responseCode.equals(ErrorConst.OK_200)) {
			canceledReport.getEiResponse().setResponseDescription("ERROR");
		}
		
		OadrPendingReportsType pendingReports = canceledReport.getOadrPendingReports();
		pendingReports.getReportRequestID().clear();
		pendingReports.getReportRequestID().add(reportRequestID);
		
		String text = SchemaHelper.getCanceledReportTypeAsString(canceledReport);
		String response = VenToVtnClient.post(text, VTNServiceType.EiReport);
		if (!OadrUtil.isExpected(response, OadrResponseType.class)) {
			throw new FailedException("Expected OadrResponse has not been received");
		}
	}

	protected OadrCancelReportType pollCancelReport() throws Exception {
		String response = VenToVtnClient.poll(OadrCancelReportType.class);
		
		if (!OadrUtil.isExpected(response, OadrCancelReportType.class)) {
			throw new FailedException("Expected OadrCancelReportType has not been received");
		}
		
		return CancelReportEventHelper.createOadrCancelReportTypeFromString(response);
	}
}
