package com.qualitylogic.openadr.core.base;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import javax.xml.bind.JAXBElement;
import javax.xml.datatype.XMLGregorianCalendar;

import org.w3c.dom.Node;

import com.qualitylogic.openadr.core.action.ICreatedEventResult;
import com.qualitylogic.openadr.core.action.IRequestReregistrationAction;
import com.qualitylogic.openadr.core.action.IResponseCanceledOptAckAction;
import com.qualitylogic.openadr.core.action.IResponseCanceledReportTypeAckAction;
import com.qualitylogic.openadr.core.action.IResponseCreatedOptAckAction;
import com.qualitylogic.openadr.core.action.IResponseCreatedPartyRegistrationAckAction;
import com.qualitylogic.openadr.core.action.ResponseCanceledOptAckActionList;
import com.qualitylogic.openadr.core.action.ResponseCanceledReportTypeAckActionList;
import com.qualitylogic.openadr.core.action.ResponseCreatedOptAckActionList;
import com.qualitylogic.openadr.core.action.ResponseCreatedPartyRegistrationAckActionList;
import com.qualitylogic.openadr.core.action.ResponseCreatedPartyRegistrationToQueryAckActionList;
import com.qualitylogic.openadr.core.action.impl.DefaultCreatedPartyRegistration;
import com.qualitylogic.openadr.core.action.impl.DefaultOadrCanceledOptEvent;
import com.qualitylogic.openadr.core.action.impl.DefaultOadrCreatedOptEvent;
import com.qualitylogic.openadr.core.action.impl.DefaultResponseCanceledReportTypeAckAction;
import com.qualitylogic.openadr.core.bean.VENServiceType;
import com.qualitylogic.openadr.core.common.TestSession;
import com.qualitylogic.openadr.core.exception.FailedException;
import com.qualitylogic.openadr.core.signal.EventResponses.EventResponse;
import com.qualitylogic.openadr.core.signal.OadrCancelOptType;
import com.qualitylogic.openadr.core.signal.OadrCanceledReportType;
import com.qualitylogic.openadr.core.signal.OadrCreateOptType;
import com.qualitylogic.openadr.core.signal.OadrCreatePartyRegistrationType;
import com.qualitylogic.openadr.core.signal.OadrCreateReportType;
import com.qualitylogic.openadr.core.signal.OadrCreatedEventType;
import com.qualitylogic.openadr.core.signal.OadrCreatedReportType;
import com.qualitylogic.openadr.core.signal.OadrPendingReportsType;
import com.qualitylogic.openadr.core.signal.OadrQueryRegistrationType;
import com.qualitylogic.openadr.core.signal.OadrRegisteredReportType;
import com.qualitylogic.openadr.core.signal.OadrReportPayloadType;
import com.qualitylogic.openadr.core.signal.OadrReportRequestType;
import com.qualitylogic.openadr.core.signal.OadrReportType;
import com.qualitylogic.openadr.core.signal.OadrUpdateReportType;
import com.qualitylogic.openadr.core.signal.OptTypeType;
import com.qualitylogic.openadr.core.signal.StreamPayloadBaseType;
import com.qualitylogic.openadr.core.util.OadrUtil;
import com.qualitylogic.openadr.core.util.XMLDBUtil;
import com.qualitylogic.openadr.core.util.XMLDBUtil.DataPoint;
import com.qualitylogic.openadr.core.vtn.VTNService;
import com.qualitylogic.openadr.core.vtn.VtnToVenClient;

public abstract class VtnTestCase extends NewTestCase {
	
	protected void listenForRequests() throws Exception {
		VTNService.startVTNService();
	}

	@Override
	public void cleanUp() throws Exception {
		VTNService.stopVTNService();
	}

	protected String post(String message, String type) throws Exception {
		VENServiceType venService = null;
		switch (type) {
			case "EiRegistration" : venService = VENServiceType.EiRegistration; break;
			case "EiReport" : venService = VENServiceType.EiReport; break;
			default : throw new IllegalArgumentException("Unknown type" + type);
		}
		
		return VtnToVenClient.post(message, venService);
	}
	
	protected IResponseCreatedPartyRegistrationAckAction addCreatedPartyRegistrationResponse() {
		IResponseCreatedPartyRegistrationAckAction createdPartyRegistration = new DefaultCreatedPartyRegistration(OadrCreatePartyRegistrationType.class);
		ResponseCreatedPartyRegistrationAckActionList.addResponsePartyRegistrationAckAction(createdPartyRegistration);
		return createdPartyRegistration;
	}

	protected IResponseCreatedPartyRegistrationAckAction addCreatedPartyRegistrationResponseToQuery() {
		IResponseCreatedPartyRegistrationAckAction createdPartyRegistration = new DefaultCreatedPartyRegistration(OadrQueryRegistrationType.class);
		ResponseCreatedPartyRegistrationToQueryAckActionList.addResponsePartyRegistrationAckAction(createdPartyRegistration);
		return createdPartyRegistration;
	}

	protected IResponseCreatedOptAckAction addCreatedOptResponse() {
		IResponseCreatedOptAckAction createdOpt = new DefaultOadrCreatedOptEvent();
		ResponseCreatedOptAckActionList.addResponseCreatedOptAckAction(createdOpt);
		return createdOpt;
	}
	
	protected IResponseCanceledOptAckAction addCanceledOptResponse() {
		IResponseCanceledOptAckAction canceledOpt = new DefaultOadrCanceledOptEvent();
		ResponseCanceledOptAckActionList.addResponseCanceledOptAckAction(canceledOpt);
		return canceledOpt;
	}
	
	protected void checkQueryRegistrationRequest(int size) {
		List<OadrQueryRegistrationType> queryRegistrations = TestSession.getOadrQueryRegistrationTypeReceivedList();
		if (queryRegistrations.size() != size) {
			throw new FailedException("Expected " + size + " OadrQueryRegistrationType(s), received " + queryRegistrations.size());
		}
	}

	protected void checkCreateOptRequest(int size) {
		List<OadrCreateOptType> createOpts = TestSession.getCreateOptEventReceivedList();
		if (createOpts.size() != size) {
			throw new FailedException("Expected " + size + " OadrCreateOpt(s), received " + createOpts.size());
		}
	}

	protected void checkCancelOptRequest(int size) {
		List<OadrCancelOptType> cancelOpts = TestSession.getCancelOptTypeReceivedList();
		if (cancelOpts.size() != size) {
			throw new FailedException("Expected " + size + " OadrCancelOpt(s), received " + cancelOpts.size());
		}
	}

	protected void checkCreatedEventReceived(ICreatedEventResult createdEvent) {
		if (!createdEvent.isExpectedCreatedEventReceived()) {
			throw new FailedException("OadrCreatedEvent has not been received.");
		}
	}

	protected void checkRequestRegistration(IRequestReregistrationAction requestReregistration) {
		if (!requestReregistration.isEventCompleted()) {
			throw new FailedException("OadRrequestReregistration has not been received.");
		}
	}
	protected void checkUpdateReport(OadrUpdateReportType updateReport, int minutesOffset, int minIntervalCount) {
		XMLGregorianCalendar dtstart = updateReport.getOadrReport().get(0).getDtstart().getDateTime();
		if (!isCalendarWithinMinutes(dtstart, minutesOffset, 2)) {
			throw new FailedException("UpdateReport dtstart is not within plus or minus 2 minutes of current time minus " + minutesOffset);
		}
		
		int intervalSize = updateReport.getOadrReport().get(0).getIntervals().getInterval().size();
		if (intervalSize < minIntervalCount) {
			throw new FailedException("At least " + minIntervalCount + " intervals of data are returned. Got " + intervalSize);
		}
	}

	protected void checkUpdateReport_x150(OadrUpdateReportType updateReport) {
		checkUpdateReport(updateReport, 0, 1);
		
		Node reportNode = new XMLDBUtil().getReportFromVenByName("TELEMETRY_USAGE");
		
		DataPoint energyRealDataPoint = getDataPoint(reportNode, "EnergyReal");
		DataPoint powerRealDataPoint = getDataPoint(reportNode, "PowerReal");
		if (energyRealDataPoint == null) {
			throw new FailedException("Expected EnergyReal rID in CreateReport.");
		}
		if (powerRealDataPoint == null) {
			throw new FailedException("Expected PowerReal rID in CreateReport.");
		}

		List<JAXBElement<? extends StreamPayloadBaseType>> streamPayloadBases = updateReport.getOadrReport().get(0).getIntervals().getInterval().get(0).getStreamPayloadBase();
		for (JAXBElement<? extends StreamPayloadBaseType> streamPayloadBase : streamPayloadBases) {
			OadrReportPayloadType reportPayload = (OadrReportPayloadType) streamPayloadBase.getValue();
			
			String rID = reportPayload.getRID();
			if (!rID.equals(energyRealDataPoint.getRID()) && !rID.equals(powerRealDataPoint.getRID())) {
				throw new FailedException("CreateReport rIDs are not found in XML database.");
			}
		}
	}

	protected void checkUpdateReport_x160(OadrUpdateReportType updateReport) {
		int intervalSize = updateReport.getOadrReport().get(0).getIntervals().getInterval().size();
		if (intervalSize != 2) {
			throw new FailedException("Expected 2 intervals of data are returned. Got " + intervalSize);
		}
		
		Node reportNode = new XMLDBUtil().getReportFromVenByName("TELEMETRY_USAGE");
		
		DataPoint powerRealDataPoint = getDataPoint(reportNode, "PowerReal");
		if (powerRealDataPoint == null) {
			throw new FailedException("Expected PowerReal rID in CreateReport.");
		}

		List<JAXBElement<? extends StreamPayloadBaseType>> streamPayloadBases = updateReport.getOadrReport().get(0).getIntervals().getInterval().get(0).getStreamPayloadBase();
		for (JAXBElement<? extends StreamPayloadBaseType> streamPayloadBase : streamPayloadBases) {
			OadrReportPayloadType reportPayload = (OadrReportPayloadType) streamPayloadBase.getValue();
			
			String rID = reportPayload.getRID();
			if (!rID.equals(powerRealDataPoint.getRID())) {
				throw new FailedException("CreateReport rIDs are not found in XML database.");
			}
		}
	}

	protected void checkUpdateReports_x160(OadrUpdateReportType updateReport1, OadrUpdateReportType updateReport2) {
		checkUpdateReport_x160(updateReport1);
		checkUpdateReport_x160(updateReport2);
		
		GregorianCalendar cal1 = updateReport1.getOadrReport().get(0).getDtstart().getDateTime().toGregorianCalendar();
		GregorianCalendar cal2 = updateReport2.getOadrReport().get(0).getDtstart().getDateTime().toGregorianCalendar();
		long diff = (cal2.getTimeInMillis() - cal1.getTimeInMillis()) / 1000;
		if (diff < 105 || diff > 135) { // 2 minutes apart +/-15 seconds
			throw new FailedException("Expected 2 OadrUpdateReports approximately 2 minutes apart. Diff=" + diff);
		}
	}

	protected void checkSameReportRequestID(OadrCreateReportType createReport, OadrCreatedReportType createdReport) {
		OadrPendingReportsType pendingReports = createdReport.getOadrPendingReports();
		List<String> reportRequestIDs = pendingReports.getReportRequestID();
		if (pendingReports == null || reportRequestIDs.isEmpty()) {
			throw new FailedException("Expected oadrPendingReports/reportRequestID in CreatedReport payload");
		}

		boolean found = false;
		String createReportRequestID = createReport.getOadrReportRequest().get(0).getReportRequestID();
		for (String createdReportRequestID : reportRequestIDs) {
			if (createReportRequestID.equals(createdReportRequestID)) {
				found = true;
				break;
			}
		}

		if (!found) {
			throw new FailedException("ReportRequestID CreateReport/CreatedReport mismatch, expected " + createReportRequestID);
		}
	}
	
	protected void checkDifferentReportRequestID(OadrCreateReportType createReport, OadrCreatedReportType createdReport) {
		String createReportRequestID = createReport.getOadrReportRequest().get(0).getReportRequestID();
		String createdReportRequestID = createdReport.getOadrPendingReports().getReportRequestID().get(0);
		if (createReportRequestID.equals(createdReportRequestID)) {
			throw new FailedException("ReportRequestID match, " + createReportRequestID + " == " + createdReportRequestID);
		}
	}
	
	protected void checkSameReportRequestID(OadrCreateReportType createReport, OadrUpdateReportType updateReport) {
		String createReportRequestID = createReport.getOadrReportRequest().get(0).getReportRequestID();
		String updateReportRequestID = updateReport.getOadrReport().get(0).getReportRequestID();
		if (!createReportRequestID.equals(updateReportRequestID)) {
			throw new FailedException("ReportRequestID CreateReport/UpdateReport mismatch, " + createReportRequestID + " != " + updateReportRequestID);
		}
	}
	
	protected void checkCanceledReportPendingReports(OadrCanceledReportType canceledReport, int size) {
		int listSize = canceledReport.getOadrPendingReports().getReportRequestID().size();
		if (listSize != size) {
			throw new FailedException("Expected " + size + " OadrCanceledReport OadrPendingReport(s). Got " + listSize);
		}
	}

	private void checkUpdateReport_x170(OadrCreateReportType createReport, OadrUpdateReportType updateReport) {
		checkSameReportRequestID(createReport, updateReport);
		
		List<String> reportNames = new ArrayList<>();
		reportNames.add("METADATA_TELEMETRY_STATUS");
		reportNames.add("METADATA_TELEMETRY_USAGE");
		reportNames.add("METADATA_HISTORY_USAGE");
		
		for (OadrReportType report : updateReport.getOadrReport()) {
			// if (!report.getReportSpecifierID().equals("METADATA")) {
			//	throw new FailedException("Expected METADATA reportSpecifier. Got " + report.getReportSpecifierID());
			// }

			String reportName = report.getReportName();
			int index = reportNames.indexOf(reportName);
			if (index == -1) {
				throw new FailedException("Expected ReportName " + reportName + " not found.");
			} else {
				reportNames.remove(index);
			}
		}
		
		if (reportNames.size() > 0) {
			throw new FailedException("Expected ReportName " + reportNames.get(0) + " not found.");
		}
	}

	protected OadrUpdateReportType waitForUpdateReport(int size) {
		long testCaseTimeout = OadrUtil.getTestCaseTimeout();
		while (System.currentTimeMillis() < testCaseTimeout) {
			pause(1);
			
			if (TestSession.getOadrUpdateReportTypeReceivedList().size() >= size) {
				break;
			} else if (TestSession.isAtleastOneValidationErrorPresent()) {
				break;
			}
		}
		pause(2);
		
		int listSize = TestSession.getOadrUpdateReportTypeReceivedList().size();
		if (listSize != size) {
			throw new FailedException("Expected " + size + " UpdateReport(s), received " + listSize);
		}

		checkForValidationErrors();

		return TestSession.getOadrUpdateReportTypeReceivedList().get(size - 1);
	}

	protected void checkCreateReportReceived(int size) {
		List<OadrCreateReportType> createReports = TestSession.getOadrCreateReportTypeReceivedList();
		if (createReports.size() != size) {
			throw new FailedException("Expected " + size + " OadrCreateReport(s), received " + createReports.size());
		}
	}

	protected IResponseCanceledReportTypeAckAction addCanceledReportResponse() {
		IResponseCanceledReportTypeAckAction canceledReportAction = new DefaultResponseCanceledReportTypeAckAction();
		ResponseCanceledReportTypeAckActionList.addResponseCanceledReportAckAction(canceledReportAction);
		return canceledReportAction;
	}

	protected void checkReportRequest(OadrRegisteredReportType registeredReport) {
		List<OadrReportRequestType> reportRequests = registeredReport.getOadrReportRequest();
		if (reportRequests == null || reportRequests.size() < 1) {
			throw new FailedException("Did not receive the expected OadrReportRequest with the OadrRegisteredReport received");
		}
	}

	protected OadrCreatedEventType waitForCreatedEvent(int size) {
		long testCaseTimeout = OadrUtil.getTestCaseTimeout();
		while (System.currentTimeMillis() < testCaseTimeout) {
			pause(1);
			
			if (TestSession.getCreatedEventReceivedList().size() >= size) {
				break;
			} else if (TestSession.isAtleastOneValidationErrorPresent()) {
				break;
			}
		}
		pause(2);
		
		int listSize = TestSession.getCreatedEventReceivedList().size();
		if (listSize != size) {
			throw new FailedException("Expected " + size + " CreatedEvent(s), received " + listSize);
		}

		checkForValidationErrors();
		
		return TestSession.getCreatedEventReceivedList().get(size - 1);
	}

	protected OadrCreatedEventType checkCreatedEvent(int size) {
		int listSize = TestSession.getCreatedEventReceivedList().size();
		if (listSize != size) {
			throw new FailedException("Expected " + size + " CreatedEvent(s), received " + listSize);
		}

		return TestSession.getCreatedEventReceivedList().get(size - 1);
	}
	
	protected void checkCreatedEvent(int size, String responseCode) {
		OadrCreatedEventType createdEvent = checkCreatedEvent(size);
		String receivedResponseCode = createdEvent.getEiCreatedEvent().getEiResponse().getResponseCode();
		if (!receivedResponseCode.equals(responseCode)) {
			throw new FailedException("Expected OadrCreatedEvent " + responseCode + ", received " + receivedResponseCode);
		}
	}

	protected void checkUpdateReport_Rx180() {
		List<OadrUpdateReportType> updateReports = TestSession.getOadrUpdateReportTypeReceivedList();

		if (updateReports.size() == 1) {
			OadrUpdateReportType updateReport = updateReports.get(0); 
			List<OadrReportType> reports = updateReport.getOadrReport();
			if (reports.size() != 2) {
				throw new FailedException("Expected 2 OadrUpdateReport OadrReport(s), received " + reports.size());
			}
		} else if (updateReports.size() == 2) {
			OadrUpdateReportType updateReport1 = updateReports.get(0); 
			OadrUpdateReportType updateReport2 = updateReports.get(1); 
			
			List<OadrReportType> reports1 = updateReport1.getOadrReport();
			List<OadrReportType> reports2 = updateReport2.getOadrReport();
			int totalSize = reports1.size() + reports2.size(); 
			// if (reports1.size() != 2 || reports2.size() != 2 || totalSize != 4) {
			if (reports1.size() != 1 || reports2.size() != 1 || totalSize != 2) {
				throw new FailedException("Expected total of 2 OadrUpdateReport OadrReport(s), 1 OadrReport from each OadrUpdateReport");
			}
		} else {
			throw new FailedException("Expected 1 or 2 OadrUpdateReport(s), received " + updateReports.size());
		}
	}

	protected void checkCreatedEvent(OadrCreatedEventType createdEvent, String eventResponseCode) {
		String eiResponseCode = createdEvent.getEiCreatedEvent().getEiResponse().getResponseCode();
		if (!eiResponseCode.equals(ErrorConst.OK_200)) {
			throw new FailedException("Expected a responseCode of 200 in EiResponse and responseCode of " + eventResponseCode + " in EventResponse, got " + eiResponseCode + " EiResponse");
		}
		
		String receivedEventResponseCode = createdEvent.getEiCreatedEvent().getEventResponses().getEventResponse().get(0).getResponseCode();
		if (!receivedEventResponseCode.equals(eventResponseCode)) {
			throw new FailedException("Expected a responseCode of 200 in EiResponse and responseCode of " + eventResponseCode + " in EventResponse, got " + receivedEventResponseCode + " EventResponse");
		}
	}

	protected void checkCreatedEvent_Ex040() {
		int listSize = TestSession.getCreatedEventReceivedList().size();

		if (listSize == 1) {
			OadrCreatedEventType createdEvent = TestSession.getCreatedEventReceivedList().get(0);
			
			List<EventResponse> eventResponses = createdEvent.getEiCreatedEvent().getEventResponses().getEventResponse();
			if (eventResponses.size() != 2) {
				throw new FailedException("Expected 2 EventResponses, received " + eventResponses.size());
			}
			
			OptTypeType optType1 = eventResponses.get(0).getOptType();
			OptTypeType optType2 = eventResponses.get(1).getOptType();
			if ((optType1 != OptTypeType.OPT_IN) || (optType2 != OptTypeType.OPT_IN)) {
				throw new FailedException("Expected 2 EventResponses with OPT_IN");
			}
			
			System.out.println("1 Created Event");			
		} else if (listSize == 2) {
			OadrCreatedEventType createdEvent1 = TestSession.getCreatedEventReceivedList().get(0);	
			OadrCreatedEventType createdEvent2 = TestSession.getCreatedEventReceivedList().get(1);	
			
			OptTypeType optType1 = createdEvent1.getEiCreatedEvent().getEventResponses().getEventResponse().get(0).getOptType();
			OptTypeType optType2 = createdEvent2.getEiCreatedEvent().getEventResponses().getEventResponse().get(0).getOptType();
			if ((optType1 != OptTypeType.OPT_IN) || (optType2 != OptTypeType.OPT_IN)) {
				throw new FailedException("Expected 2 EventResponses with OPT_IN");
			}
			
			System.out.println("2 Created Events");			
		} else {
			throw new FailedException("Expected 1 or 2 CreatedEvent(s), received " + listSize);
		}
	}

	protected void waitForOptionalCreatedEvent() {
		long testCaseTimeout = 30 * 1000;
		while (System.currentTimeMillis() < testCaseTimeout) {
			pause(1);
			
			if (TestSession.getCreatedEventReceivedList().size() >= 2) {
				break;
			} else if (TestSession.isAtleastOneValidationErrorPresent()) {
				break;
			}
		}
		pause(2);
		
		checkForValidationErrors();
	}
}
