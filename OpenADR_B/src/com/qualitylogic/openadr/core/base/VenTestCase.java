package com.qualitylogic.openadr.core.base;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.xml.datatype.Duration;
import javax.xml.datatype.XMLGregorianCalendar;

import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.Node;

import com.qualitylogic.openadr.core.action.ICreatedEventAction;
import com.qualitylogic.openadr.core.action.IResponseCanceledReportTypeAckAction;
import com.qualitylogic.openadr.core.action.ResponseCanceledReportTypeAckActionList;
import com.qualitylogic.openadr.core.action.impl.DefaultOadrCreateOptEvent;
import com.qualitylogic.openadr.core.action.impl.DefaultResponseCanceledReportTypeAckAction;
import com.qualitylogic.openadr.core.bean.VTNServiceType;
import com.qualitylogic.openadr.core.channel.util.StringUtil;
import com.qualitylogic.openadr.core.common.TestSession;
import com.qualitylogic.openadr.core.exception.FailedException;
import com.qualitylogic.openadr.core.signal.OadrCancelOptType;
import com.qualitylogic.openadr.core.signal.OadrCanceledOptType;
import com.qualitylogic.openadr.core.signal.OadrCreateOptType;
import com.qualitylogic.openadr.core.signal.OadrCreatePartyRegistrationType;
import com.qualitylogic.openadr.core.signal.OadrCreateReportType;
import com.qualitylogic.openadr.core.signal.OadrCreatedEventType;
import com.qualitylogic.openadr.core.signal.OadrCreatedOptType;
import com.qualitylogic.openadr.core.signal.OadrCreatedPartyRegistrationType;
import com.qualitylogic.openadr.core.signal.OadrDistributeEventType;
import com.qualitylogic.openadr.core.signal.OadrDistributeEventType.OadrEvent;
import com.qualitylogic.openadr.core.signal.OadrQueryRegistrationType;
import com.qualitylogic.openadr.core.signal.OadrResponseType;
import com.qualitylogic.openadr.core.signal.OptTypeType;
import com.qualitylogic.openadr.core.signal.ReportSpecifierType;
import com.qualitylogic.openadr.core.signal.SpecifierPayloadType;
import com.qualitylogic.openadr.core.signal.helper.CancelOptEventHelper;
import com.qualitylogic.openadr.core.signal.helper.CanceledOptEventHelper;
import com.qualitylogic.openadr.core.signal.helper.CreatedEventHelper;
import com.qualitylogic.openadr.core.signal.helper.CreatedPartyRegistrationSignalHelper;
import com.qualitylogic.openadr.core.signal.helper.QueryRegistrationSignalHelper;
import com.qualitylogic.openadr.core.signal.helper.ResponseHelper;
import com.qualitylogic.openadr.core.signal.helper.SchemaHelper;
import com.qualitylogic.openadr.core.signal.xcal.AvailableType;
import com.qualitylogic.openadr.core.signal.xcal.Dtstart;
import com.qualitylogic.openadr.core.util.OadrUtil;
import com.qualitylogic.openadr.core.util.XMLDBUtil;
import com.qualitylogic.openadr.core.util.XMLDBUtil.DataPoint;
import com.qualitylogic.openadr.core.ven.VENService;
import com.qualitylogic.openadr.core.ven.VenToVtnClient;

public abstract class VenTestCase extends NewTestCase {
	protected void listenForRequests() throws Exception {
		VENService.startVENService();
	}

	@Override
	public void cleanUp() throws Exception {
		VENService.stopVENService();
	}

	protected String post(String message, String type) throws Exception {
		VTNServiceType vtnService = null;
		switch (type) {
			case "EiRegistration" : vtnService = VTNServiceType.EiRegistration; break;
			case "EiReport" : vtnService = VTNServiceType.EiReport; break;
			default : throw new IllegalArgumentException("Unknown type" + type);
		}
		
		return VenToVtnClient.post(message, vtnService);
	}
	
	protected OadrCreatedPartyRegistrationType sendCreatePartyRegistration(OadrCreatePartyRegistrationType createPartyRegistration) throws Exception {
		String text = SchemaHelper.getCreatePartyRegistrationAsString(createPartyRegistration);
		String response = VenToVtnClient.post(text, VTNServiceType.EiRegistration);
		if (!OadrUtil.isExpected(response, OadrCreatedPartyRegistrationType.class)) {
			throw new FailedException("Expected OadrCreatedPartyRegistration has not been received");
		}
		
		return CreatedPartyRegistrationSignalHelper.createCreatedPartyRegistrationTypeFromString(response);
	}

	protected OadrCreatedPartyRegistrationType sendQueryRegistration() throws Exception {
		OadrQueryRegistrationType queryRegistration = QueryRegistrationSignalHelper.loadOadrQueryRegistrationRequest();
		String text = SchemaHelper.getOadrQueryRegistrationTypeAsString(queryRegistration);
		String response = VenToVtnClient.post(text, VTNServiceType.EiRegistration);
		if (!OadrUtil.isExpected(response, OadrCreatedPartyRegistrationType.class)) {
			throw new FailedException("Expected OadrCreatedPartyRegistration has not been received");
		}
		
		OadrCreatedPartyRegistrationType createdPartyRegistration = CreatedPartyRegistrationSignalHelper.createCreatedPartyRegistrationTypeFromString(response);
		return createdPartyRegistration;
	}

	protected void sendCreateOpt(OadrCreateOptType createOpt) throws Exception {
		String text = SchemaHelper.getOadrCreateOptAsString(createOpt);
		String response = VenToVtnClient.post(text, VTNServiceType.EiOpt);
		if (!OadrUtil.isExpected(response, OadrCreatedOptType.class)) {
			throw new FailedException("Expected OadrCreatedOpt has not been received");
		}
	}

	protected void sendCancelOpt(String optID) throws Exception {
		sendCancelOpt(optID, "200");
	}
	
	protected void sendCancelOpt(String optID, String responseCode) throws Exception {
		OadrCancelOptType cancelOpt = CancelOptEventHelper.createOadrCancelOptEvent(optID);
		String text = SchemaHelper.getOadrCancelOptTypeAsString(cancelOpt);
		String response = VenToVtnClient.post(text, VTNServiceType.EiOpt);
		if (!OadrUtil.isExpected(response, OadrCanceledOptType.class)) {
			throw new FailedException("Expected OadrCanceledOpt has not been received");
		}

		OadrCanceledOptType canceledOpt = CanceledOptEventHelper.createCanceledOptTypeEventFromString(response);
		String receivedResponseCode = canceledOpt.getEiResponse().getResponseCode();
		if (!receivedResponseCode.equals(responseCode)) {
			throw new FailedException("Expected OadrCanceledOpt responseCode of " + responseCode + " has not been received. Got " + receivedResponseCode + ".");
		}
	}
	
	protected OadrCreateOptType getCreateOptSchedule_001() {
		List<AvailableType> availables = new ArrayList<AvailableType>();
		availables.add(getAvailable(1, 4));
		availables.add(getAvailable(3, 8));

		return getCreateOpt(OptTypeType.OPT_IN, availables);
	}

	protected OadrCreateOptType getCreateOptSchedule_002() {
		List<AvailableType> availables = new ArrayList<AvailableType>();
		availables.add(getAvailable(2, 4));
		availables.add(getAvailable(4, 8));

		return getCreateOpt(OptTypeType.OPT_OUT, availables);
	}
	
	protected OadrCreateOptType getCreateOptSchedule_003() {
		List<AvailableType> availables = new ArrayList<AvailableType>();
		availables.add(getAvailable(2, 6));
		availables.add(getAvailable(4, 8));

		return getCreateOpt(OptTypeType.OPT_IN, availables);
	}
	
	protected OadrCreateOptType getCreateOptSchedule_004() {
		List<AvailableType> availables = new ArrayList<AvailableType>();
		availables.add(getAvailable(1, 0));

		return getCreateOpt(OptTypeType.OPT_IN, availables);
	}

	private static OadrCreateOptType getCreateOpt(OptTypeType optInOut, List<AvailableType> availables) {
		String reason = "participating";
		String marketContext = properties.get("DR_MarketContext_1_Name");
		DefaultOadrCreateOptEvent creatOptEvent = new DefaultOadrCreateOptEvent();
		OadrCreateOptType createOpt = creatOptEvent.getOadrCreateOptEvent(
				optInOut, reason, marketContext, Collections.<String>emptyList(), Collections.<String>emptyList(), availables);
		return createOpt;
	}
	
	protected OadrCreateOptType getCreateOptOptOut(OadrEvent oadrEvent) {
		OptTypeType optInOut = OptTypeType.OPT_OUT;
		String reason = "participating";
		String marketContext = properties.get("DR_MarketContext_1_Name");
		String eventID = oadrEvent.getEiEvent().getEventDescriptor().getEventID();
		long modificationNumber = 0;
		
		List<AvailableType> availables = new ArrayList<AvailableType>();
		availables.add(getAvailable("PT5M"));
										
		DefaultOadrCreateOptEvent createOptEvent = new DefaultOadrCreateOptEvent();
		OadrCreateOptType createOpt = createOptEvent.getOadrCreateOptEvent(
				optInOut, reason, marketContext, eventID, modificationNumber, Collections.<String>emptyList() , Collections.<String>emptyList(), availables);
		return createOpt;
	}

	protected OadrCreateOptType getCreateOptOptIn(OadrEvent oadrEvent, String resourceID) {
		OptTypeType optInOut = OptTypeType.OPT_IN;
		String reason = "participating";
		String marketContext = properties.get("DR_MarketContext_1_Name");
		String eventID = oadrEvent.getEiEvent().getEventDescriptor().getEventID();
		long modificationNumber = 0;
		
		List<AvailableType> availables = new ArrayList<AvailableType>();
		availables.add(getAvailable("PT5M"));
										
		DefaultOadrCreateOptEvent createOptEvent = new DefaultOadrCreateOptEvent();
		List<String> resourceIDs = new ArrayList<>(); 
		resourceIDs.add(oadrEvent.getEiEvent().getEiTarget().getResourceID().get(1));
		
		OadrCreateOptType createOpt = createOptEvent.getOadrCreateOptEvent(
				optInOut, reason, marketContext, eventID, modificationNumber, resourceIDs , Collections.<String>emptyList(), availables);
		return createOpt;
	}

	protected void sendCreatedEvent(ICreatedEventAction createdEvent) throws Exception {
		String response = VenToVtnClient.post(createdEvent.getCreateEvent());
		if (!OadrUtil.isExpected(response, OadrResponseType.class)) {
			throw new FailedException("Expected OadrResponse has not been received");
		}
	}

	protected void checkRegistrationIdAndVenId(OadrCreatedPartyRegistrationType createdPartyRegistration) {
		if (StringUtil.isBlank(createdPartyRegistration.getRegistrationID())) {
			throw new FailedException("venID not in payload. VEN should be in registered state.");
		}
		if (StringUtil.isBlank(createdPartyRegistration.getVenID())) {
			throw new FailedException("registrationID not in payload. VEN should be in registered state.");
		}
	}

	protected void checkRegistrationIdAndVenId(OadrCreatePartyRegistrationType createPartyRegistration, OadrCreatedPartyRegistrationType createdPartyRegistration) {
		if (!createPartyRegistration.getRegistrationID().equals(createdPartyRegistration.getRegistrationID())) {
			throw new FailedException("registrationID mismatch");
		}
		if (!createPartyRegistration.getVenID().equals(createdPartyRegistration.getVenID())) {
			throw new FailedException("venID mismatch");
		}
	}

	protected void checkDuration(OadrCreateReportType createReport) {
		String durationText = createReport.getOadrReportRequest().get(0).getReportSpecifier().getReportInterval().getProperties().getDuration().getDuration();
		Duration duration = OadrUtil.createDuration(durationText);
		long timeInMillis = duration.getTimeInMillis(new Date());
		// System.out.println("millis=" + timeInMillis);
		if (!(timeInMillis == 0 || timeInMillis >= _60_MINUTES)) {
			throw new FailedException("Duration of CreateReport should either be zero or a value greater than or equal to 60 minutes.");
		}
	}

	private static final int _60_MINUTES = 60 * 60 * 1000;


	protected void checkCreateReport_x130(OadrCreateReportType createReport) {
		Node reportNode = new XMLDBUtil().getReportFromVenByName("HISTORY_USAGE");
		ReportSpecifierType reportSpecifier = createReport.getOadrReportRequest().get(0).getReportSpecifier();
		
		checkReportSpecifier(reportNode, reportSpecifier);
		
		DataPoint energyRealDataPoint = getDataPoint(reportNode, "EnergyReal");
		DataPoint powerRealDataPoint = getDataPoint(reportNode, "PowerReal");
		if (energyRealDataPoint == null) {
			throw new FailedException("Expected EnergyReal rID in CreateReport.");
		}
		if (powerRealDataPoint == null) {
			throw new FailedException("Expected PowerReal rID in CreateReport.");
		}

		List<SpecifierPayloadType> specifierPayloads = reportSpecifier.getSpecifierPayload();
		if (specifierPayloads.size() != 2) {
			throw new FailedException("Expected 2 SpecifierPayloads in CreateReport.");
		}
		
		for (SpecifierPayloadType specifierPayload : specifierPayloads) {
			String rID = specifierPayload.getRID();
			if (!rID.equals(energyRealDataPoint.getRID()) && !rID.equals(powerRealDataPoint.getRID())) {
				throw new FailedException("CreateReport rIDs are not found in XML database.");
			}
		}
		
		XMLGregorianCalendar dtstart = reportSpecifier.getReportInterval().getProperties().getDtstart().getDateTime();
		if (!isCalendarWithinMinutes(dtstart, 0, 2)) {
			throw new FailedException("CreateReport dtstart is not within plus or minus 2 minutes of current time.");
		}
		
		checkDuration(createReport);
	}

	protected void checkCreateReport_x140(OadrCreateReportType createReport) {
		Node reportNode = new XMLDBUtil().getReportFromVenByName("HISTORY_USAGE");
		ReportSpecifierType reportSpecifier = createReport.getOadrReportRequest().get(0).getReportSpecifier();

		checkReportSpecifier(reportNode, reportSpecifier);
		
		DataPoint energyRealDataPoint = getDataPoint(reportNode, "EnergyReal");
		if (energyRealDataPoint == null) {
			throw new FailedException("Expected EnergyReal rID in CreateReport.");
		}
		
		List<SpecifierPayloadType> specifierPayloads = reportSpecifier.getSpecifierPayload();
		if (specifierPayloads.size() != 1) {
			throw new FailedException("Expected 1 SpecifierPayload in CreateReport.");
		}
		
		if (!energyRealDataPoint.getRID().contains(specifierPayloads.get(0).getRID())) {
			throw new FailedException("CreateReport rID is not found in XML database.");
		}
		
		XMLGregorianCalendar dtstart = reportSpecifier.getReportInterval().getProperties().getDtstart().getDateTime();
		if (!isCalendarWithinMinutes(dtstart, 30, 2)) {
			throw new FailedException("CreateReport dtstart is not within plus or minus 2 minutes of current time minus 30.");
		}
	}
	
	protected void checkCreateReport_x150(OadrCreateReportType createReport) {
		Node reportNode = new XMLDBUtil().getReportFromVenByName("TELEMETRY_USAGE");
		ReportSpecifierType reportSpecifier = createReport.getOadrReportRequest().get(0).getReportSpecifier();
		
		checkReportSpecifier(reportNode, reportSpecifier);
		
		DataPoint energyRealDataPoint = getDataPoint(reportNode, "EnergyReal");
		DataPoint powerRealDataPoint = getDataPoint(reportNode, "PowerReal");
		if (energyRealDataPoint == null) {
			throw new FailedException("Expected EnergyReal rID in CreateReport.");
		}
		if (powerRealDataPoint == null) {
			throw new FailedException("Expected PowerReal rID in CreateReport.");
		}

		List<SpecifierPayloadType> specifierPayloads = reportSpecifier.getSpecifierPayload();
		if (specifierPayloads.size() != 2) {
			throw new FailedException("Expected 2 SpecifierPayloads in CreateReport.");
		}
		
		for (SpecifierPayloadType specifierPayload : specifierPayloads) {
			String rID = specifierPayload.getRID();
			if (!rID.equals(energyRealDataPoint.getRID()) && !rID.equals(powerRealDataPoint.getRID())) {
				throw new FailedException("CreateReport rIDs are not found in XML database.");
			}
		}
		
		String durationText = reportSpecifier.getReportBackDuration().getDuration();
		if (!durationEquals(durationText, "PT0M")) {
			throw new FailedException("Expected zero reportBackDuration, got " + durationText);
		}
	}
	
	protected void checkCreateReport_x160(OadrCreateReportType createReport) {
		Node reportNode = new XMLDBUtil().getReportFromVenByName("TELEMETRY_USAGE");
		ReportSpecifierType reportSpecifier = createReport.getOadrReportRequest().get(0).getReportSpecifier();
		
		checkReportSpecifier(reportNode, reportSpecifier);
		
		DataPoint powerRealDataPoint = getDataPoint(reportNode, "PowerReal");
		if (powerRealDataPoint == null) {
			throw new FailedException("Expected PowerReal rID in CreateReport.");
		}

		List<SpecifierPayloadType> specifierPayloads = reportSpecifier.getSpecifierPayload();
		if (specifierPayloads.size() != 1) {
			throw new FailedException("Expected 1 SpecifierPayloads in CreateReport.");
		}
		
		String rID = specifierPayloads.get(0).getRID();
		if (!rID.equals(powerRealDataPoint.getRID())) {
			throw new FailedException("CreateReport rIDs are not found in XML database.");
		}
		
		String reportBackDuration = reportSpecifier.getReportBackDuration().getDuration();
		if (!durationEquals(reportBackDuration, "PT2M")) { 
			throw new FailedException("Expected 2 minutes reportBackDuration.");
		}

		String granularityText = reportSpecifier.getGranularity().getDuration();
		if (!durationEquals(granularityText, "PT1M")) {
			throw new FailedException("Expected 1 minute granularity.");
		}
	}
	
	protected void checkReportSpecifier(Node reportNode, ReportSpecifierType reportSpecifier) {
		String reportSpecifierID = reportSpecifier.getReportSpecifierID();
		String xmlReportSpecifierID = reportNode.getAttributes().getNamedItem("reportSpecifierID").getNodeValue();
		if (!reportSpecifierID.equals(xmlReportSpecifierID)) {
			throw new FailedException("ReportSpecifierID mismatch, " + reportSpecifierID + " != " + reportSpecifierID);
		}
	}

	protected IResponseCanceledReportTypeAckAction addCanceledReportResponse() {
		IResponseCanceledReportTypeAckAction canceledReportAction = new DefaultResponseCanceledReportTypeAckAction();
		ResponseCanceledReportTypeAckActionList.addResponseCanceledReportAckAction(canceledReportAction);
		
		return canceledReportAction;
	}

	protected IResponseCanceledReportTypeAckAction addCanceledReportResponse(String responseCode, String responseDescription) {
		IResponseCanceledReportTypeAckAction canceledReportAction = new DefaultResponseCanceledReportTypeAckAction(responseCode, responseDescription);
		ResponseCanceledReportTypeAckActionList.addResponseCanceledReportAckAction(canceledReportAction);
		
		return canceledReportAction;
	}

	protected void checkCreateReport_x170(OadrCreateReportType createReport) {
		ReportSpecifierType reportSpecifier = createReport.getOadrReportRequest().get(0).getReportSpecifier();
		if (!reportSpecifier.getReportSpecifierID().equals("METADATA")) {
			throw new FailedException("Expected METADATA reportSpecifier. Got " + reportSpecifier.getReportSpecifierID());
		}
		
		String reportBackDurationText = reportSpecifier.getReportBackDuration().getDuration();
		if (!durationEquals(reportBackDurationText, "PT1M")) {
			throw new FailedException("Expected PT1M reportBackDuration. Got " + reportBackDurationText);
		}

		String granularityText = reportSpecifier.getGranularity().getDuration();
		if (!durationEquals(granularityText, "PT0M")) {
			throw new FailedException("Expected PT0M reportSpecifier granularity. Got " + granularityText);
		}
	}

	protected void waitForDtstart(OadrCreateReportType createReport) {
		Dtstart dtstart = null;
		try {
			dtstart = createReport.getOadrReportRequest().get(0).getReportSpecifier().getReportInterval().getProperties().getDtstart();
		} catch (Exception e) {
			throw new FailedException("Unable to get dtstart");
		}
		
		System.out.println("Pausing until " + dtstart.getDateTime() + "...");
		long expiration = dtstart.getDateTime().toGregorianCalendar().getTimeInMillis();
		while (System.currentTimeMillis() < expiration) {
			pause(1);
		}
	}

	protected void checkResponse(int size, String responseCode) {
		OadrResponseType response = checkResponse(size);
		String receivedResponseCode = response.getEiResponse().getResponseCode();
		if (!receivedResponseCode.equals(responseCode)) {
			throw new FailedException("Expected OadrResponse responseCode of " + responseCode + " has not been received. Got " + receivedResponseCode + ".");
		}
	}
	
	protected void checkResponse(OadrResponseType response, String responseCode) {
		String receivedResponseCode = response.getEiResponse().getResponseCode();
		if (!receivedResponseCode.equals(responseCode)) {
			throw new FailedException("Expected OadrResponse responseCode of " + responseCode + " has not been received.");
		}
	}
	
	protected OadrResponseType checkResponse(int size) {
		List<OadrResponseType> responses = TestSession.getOadrResponse();
		if (responses.size() != size) {
			throw new FailedException("Expected " + size + " OadrResponse(s), received " + responses.size());
		}
		
		return responses.get(size - 1);
	}

	protected void checkIDs_Nx015(OadrCreatedPartyRegistrationType createdPartyRegistration) {
		String receivedRegistrationID = createdPartyRegistration.getRegistrationID();
		String receivedVenID = createdPartyRegistration.getVenID();

		XMLDBUtil xmldbUtil = new XMLDBUtil();
		
		if (StringUtils.isNotBlank(receivedRegistrationID)) {
			String registrationID = xmldbUtil.getRegistrationID();
			if (!receivedRegistrationID.equals(registrationID)) {
				throw new FailedException("RegistrationID mismatch. " + registrationID + "!=" + receivedRegistrationID);
			}
		}

		if (StringUtils.isNotBlank(receivedVenID)) {
			String venID = xmldbUtil.getVENID();
			if (!receivedVenID.equals(venID)) {
				throw new FailedException("VenID mismatch. " + venID + "!=" + receivedVenID);
			}
		}
	}

	protected void checkSignalNames(OadrDistributeEventType distributeEvent, String... signalNames) {
		int signalSize = distributeEvent.getOadrEvent().size();
		if (signalNames.length != signalSize) {
			throw new FailedException("Expected " + signalNames.length + " EiEventSignal(s). Got " + signalSize);
		}
		
		for (int i = 0; i < signalNames.length; i++) {
			String signalName = signalNames[i];
			String receivedSignalName = getSignalName(distributeEvent, i);
			if (!signalName.equals(receivedSignalName)) {
				throw new FailedException("Did not receive expected signal name " + signalName + ". Got " + receivedSignalName);
			}
		}
	}

	private String getSignalName(OadrDistributeEventType distributeEvent, int index) {
		String receivedSignalName = null;
		try {
			OadrEvent event = distributeEvent.getOadrEvent().get(index);
			receivedSignalName = event.getEiEvent().getEiEventSignals().getEiEventSignal().get(0).getSignalName();
		} catch (Exception e) {
			e.printStackTrace();
			// ignore
		}
		return receivedSignalName;
	}

	protected OadrResponseType sendCreatedEvent(String distributeEvent, OptTypeType optType) throws Exception {
		List<String> distributeEvents = new ArrayList<String>();
		distributeEvents.add(distributeEvent);

		String text = CreatedEventHelper.createCreatedEvent(distributeEvents, false, (optType == OptTypeType.OPT_IN), null);
		String responseText = VenToVtnClient.post(text);
		if (!OadrUtil.isExpected(responseText, OadrResponseType.class)) {
			throw new FailedException("Expected OadrResponse has not been received");
		}
		
		return ResponseHelper.createOadrResponseFromString(responseText);
	}
	
	protected OadrResponseType sendCreatedEventWithResponseCode(String distributeEvent, String eiResponseCode, String eventResponseCode) throws Exception {
		List<String> distributeEvents = new ArrayList<String>();
		distributeEvents.add(distributeEvent);
		
		String text = CreatedEventHelper.createCreatedEventError(distributeEvents, eiResponseCode, eventResponseCode, Collections.<String>emptyList(), false, true);
		String response = VenToVtnClient.post(text);
		if (!OadrUtil.isExpected(response, OadrResponseType.class)) {
			throw new FailedException("Expected OadrResponse has not been received");
		}
		
		return ResponseHelper.createOadrResponseFromString(response);
	}
	
	protected void checkDistributeEvent_Ex040(OadrDistributeEventType distributeEvent) {
		checkSignalNames(distributeEvent, "SIMPLE", "ELECTRICITY_PRICE");
		
		List<OadrEvent> events = distributeEvent.getOadrEvent();
		if (events == null || events.size() != 2) {
			int eventSize = (events == null) ? 0 : events.size();
			throw new FailedException("Expected 2 events in the OadrDistributeEvent payload. Received " + eventSize + " Event(s)");
		}
	}

	protected void sendCreatedEvent_Ex040(String distributeEvent, int eventResponseIndexToRemove) throws Exception {
		List<String> distributeEvents = new ArrayList<String>();
		distributeEvents.add(distributeEvent);

		String text = CreatedEventHelper.createCreatedEvent(distributeEvents, false, true, null);
		
		OadrCreatedEventType createdEvent = CreatedEventHelper.createCreatedEventFromString(text);
		createdEvent.getEiCreatedEvent().getEventResponses().getEventResponse().remove(eventResponseIndexToRemove);
		
		String response = VenToVtnClient.post(text);
		if (!OadrUtil.isExpected(response, OadrResponseType.class)) {
			throw new FailedException("Expected OadrResponse has not been received");
		}
	}

	private boolean durationEquals(String durationText1, String durationText2) {
		// System.out.println("durationText " + durationText1 + "==" + durationText2);
		Duration duration1 = OadrUtil.createDuration(durationText1);
		Duration duration2 = OadrUtil.createDuration(durationText2);
		return (duration1.compare(duration2) == 0);
	}
}
