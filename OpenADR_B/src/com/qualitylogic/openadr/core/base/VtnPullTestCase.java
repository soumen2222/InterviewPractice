package com.qualitylogic.openadr.core.base;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.qualitylogic.openadr.core.action.ICreatedEventResult;
import com.qualitylogic.openadr.core.action.IDistributeEventAction;
import com.qualitylogic.openadr.core.action.IResponseCancelPartyRegistrationAckAction;
import com.qualitylogic.openadr.core.action.IResponseCancelReportTypeAckAction;
import com.qualitylogic.openadr.core.action.IResponseCreateReportTypeAckAction;
import com.qualitylogic.openadr.core.action.IResponseEventAction;
import com.qualitylogic.openadr.core.action.IResponseRegisterReportTypeAckAction;
import com.qualitylogic.openadr.core.action.impl.Default_CreatedEventResultOptINOptOut;
import com.qualitylogic.openadr.core.bean.ModeType;
import com.qualitylogic.openadr.core.common.TestSession;
import com.qualitylogic.openadr.core.exception.FailedException;
import com.qualitylogic.openadr.core.oadrpoll.OadrPollQueue;
import com.qualitylogic.openadr.core.signal.OadrCanceledPartyRegistrationType;
import com.qualitylogic.openadr.core.signal.OadrCanceledReportType;
import com.qualitylogic.openadr.core.signal.OadrCreatedReportType;
import com.qualitylogic.openadr.core.signal.OadrRegisteredReportType;
import com.qualitylogic.openadr.core.util.OadrUtil;

public abstract class VtnPullTestCase extends VtnTestCase {
	
	protected void newInit() {
		super.newInit();
		TestSession.setMode(ModeType.PULL);
	}
	
	protected void queueResponse(Object item) {
		OadrPollQueue.addToQueue(item);
	}

	protected OadrRegisteredReportType waitForRegisteredReportRequest(int size) {
		long testCaseTimeout = OadrUtil.getTestCaseTimeout();
		while (System.currentTimeMillis() < testCaseTimeout) {
			pause(1);
			
			if (TestSession.getOadrRegisteredReportTypeReceivedList().size() >= size) {
				break;
			}
		}
		pause(5);
		
		checkRegisteredReportRequest(size);
		
		return TestSession.getOadrRegisteredReportTypeReceivedList().get(size - 1);
	}
	
	private void waitForRegisterReportAction(IResponseRegisterReportTypeAckAction registerReportAction) {
		long testCaseTimeout = OadrUtil.getTestCaseTimeout();
		while (System.currentTimeMillis() < testCaseTimeout) {
			pause(1);
			
			if (registerReportAction.isEventCompleted()) {
				break;
			}
		}
	}

	protected void waitForCreatedReport(int size, String responseCode) {
		OadrCreatedReportType createdReport = waitForCreatedReport(size);
		String eiResponseCode = createdReport.getEiResponse().getResponseCode();
		if (!eiResponseCode.equals(responseCode)) {
			throw new FailedException("Expected OadrCreatedReport responseCode of " + responseCode + " has not been received. Got " + eiResponseCode + ".");
		}
	}
	
	protected OadrCreatedReportType waitForCreatedReport(int size) {
		long testCaseTimeout = OadrUtil.getTestCaseTimeout();
		while (System.currentTimeMillis() < testCaseTimeout) {
			pause(1);
			
			if (TestSession.getOadrCreatedReportTypeReceivedList().size() >= size) {
				break;
			} else if (TestSession.isAtleastOneValidationErrorPresent()) {
				break;
			}
		}
		
		int listSize = TestSession.getOadrCreatedReportTypeReceivedList().size();
		if (listSize != size) {
			throw new FailedException("Expected " + size + " CreatedReport(s), received " + listSize);
		}

		checkForValidationErrors();
		
		return TestSession.getOadrCreatedReportTypeReceivedList().get(size - 1);
	}
	
	protected void checkRegisteredReportRequest(int size) {
		List<OadrRegisteredReportType> registeredReports = TestSession.getOadrRegisteredReportTypeReceivedList();
		if (registeredReports.size() != size) {
			throw new FailedException("Expected " + size + " OadrRegisteredReport(s), received " + registeredReports.size());
		}
	}

	protected void checkDistributeEvent(IDistributeEventAction distributeEvent) {
		if (!distributeEvent.isEventCompleted()) {
			throw new FailedException("Expected completed DistributeEvent");
		}
	}

	protected void checkCreateReport(IResponseCreateReportTypeAckAction createReport) {
		if (!createReport.isEventCompleted()) {
			throw new FailedException("Expected completed CreateReport");
		}
	}

	protected void checkCreatedReport(int size) {
		List<OadrCreatedReportType> createdReports = TestSession.getOadrCreatedReportTypeReceivedList();
		if (createdReports.size() != size) {
			throw new FailedException("Expected " + size + " OadrCreatedReport(s), received " + createdReports.size());
		}
	}
	
	protected void checkCanceledPartyRegistration(int size) {
		List<OadrCanceledPartyRegistrationType> canceledPartyRegistrations = TestSession.getCanceledPartyRegistrationReceivedToList();
		if (canceledPartyRegistrations.size() != size) {
			throw new FailedException("Expected " + size + " OadrCanceledPartyRegistration(s), received " + canceledPartyRegistrations.size());
		}
	}

	protected ICreatedEventResult queueDistributeEvent(IDistributeEventAction distributeEvent) {
		ICreatedEventResult createdEvent = new Default_CreatedEventResultOptINOptOut();
		createdEvent.setExpectedOptInCount(1);
		createdEvent.addDistributeEvent(distributeEvent);
		distributeEvent.setCreatedEventSuccessCriteria(createdEvent);
		
		OadrPollQueue.addToQueue(distributeEvent);
		return createdEvent;
	}

	protected void waitForDistributeEvent(IDistributeEventAction distributeEvent) {
		long testCaseTimeout = OadrUtil.getTestCaseTimeout();
		while (System.currentTimeMillis() < testCaseTimeout) {
			pause(1);
			
			if (distributeEvent.isEventCompleted()) {
				break;	
			} else if (TestSession.isAtleastOneValidationErrorPresent()) {
				break;
			}
		}
		pause(2);

		if (!distributeEvent.isEventCompleted()) {
			throw new FailedException("DistributeEvent has not been sent.");
		}
		
		checkForValidationErrors();
	}

	protected void waitForCancelReport(IResponseCancelReportTypeAckAction cancelReport) {
		long testCaseTimeout = OadrUtil.getTestCaseTimeout();
		while (System.currentTimeMillis() < testCaseTimeout) {
			pause(1);
			
			if (cancelReport.isEventCompleted()) {
				break;	
			} else if (TestSession.isAtleastOneValidationErrorPresent()) {
				break;
			}
		}
		pause(2);

		if (!cancelReport.isEventCompleted()) {
			throw new FailedException("CancelReport has not been sent.");
		}
		
		checkForValidationErrors();
	}

	protected void waitForCanceledPartyRegistration(int size, String responseCode) {
		long testCaseTimeout = OadrUtil.getTestCaseTimeout();
		while (System.currentTimeMillis() < testCaseTimeout) {
			pause(1);
			
			if (TestSession.getCanceledPartyRegistrationReceivedToList().size() >= size) {
				break;
			} else if (TestSession.isAtleastOneValidationErrorPresent()) {
				break;
			}
		}
		pause(2);

		int listSize = TestSession.getCanceledPartyRegistrationReceivedToList().size();
		if (listSize != size) {
			throw new FailedException("Expected " + size + " CanceledPartyRegistration(s), received " + listSize);
		}
		
		checkForValidationErrors();
		
		OadrCanceledPartyRegistrationType canceledPartyRegistration = TestSession.getCanceledPartyRegistrationReceivedToList().get(listSize - 1);
		String eiResponseCode = canceledPartyRegistration.getEiResponse().getResponseCode();
		if (!eiResponseCode.equals(responseCode)) {
			throw new FailedException("Expected OadrCanceledPartyRegistration responseCode of " + responseCode + " has not been received. Got " + eiResponseCode + ".");
		}
	}

	protected void checkRegistrationIdAndVenId(IResponseCancelPartyRegistrationAckAction cancelPartyRegistration,
			OadrCanceledPartyRegistrationType canceledPartyRegistration) {
		String canceledPartyRegistrationVenID = canceledPartyRegistration.getVenID();
		String cancelPartyRegistrationVenID = cancelPartyRegistration.getOadrCancelPartyRegistration().getVenID();
		if (StringUtils.isNotBlank(cancelPartyRegistrationVenID) && !cancelPartyRegistrationVenID.equals(canceledPartyRegistrationVenID)) {
			throw new FailedException("venID mismatch between CanceledPartyRegistration and CanceledPartyRegistration or values are null");
		}

		String registrationID = canceledPartyRegistration.getRegistrationID();
		if (!StringUtils.isBlank(registrationID) && !registrationID.equals(cancelPartyRegistration.getOadrCancelPartyRegistration().getRegistrationID())) {
			throw new FailedException("registrationID mismatch between CanceledPartyRegistration and CanceledPartyRegistration or values are null");
		}
	}

	protected OadrCanceledReportType waitForCanceledReport(int size) {
		long testCaseTimeout = OadrUtil.getTestCaseTimeout();
		while (System.currentTimeMillis() < testCaseTimeout) {
			pause(1);
			
			if (TestSession.getOadrCanceledReportTypeReceivedList().size() >= size) {
				break;
			} else if (TestSession.isAtleastOneValidationErrorPresent()) {
				break;
			}
		}
		pause(2);
		
		int listSize = TestSession.getOadrCanceledReportTypeReceivedList().size();
		if (listSize != size) {
			throw new FailedException("Expected " + size + " CanceledReport(s), received " + listSize);
		}

		checkForValidationErrors();
		
		return TestSession.getOadrCanceledReportTypeReceivedList().get(size - 1);
	}

	protected void checkResponse(IResponseEventAction response) {
		if (!response.isEventCompleted()) {
			throw new FailedException("Expected completed Response");
		}
	}
}
