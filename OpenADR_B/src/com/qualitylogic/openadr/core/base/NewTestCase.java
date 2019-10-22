package com.qualitylogic.openadr.core.base;

import java.util.Date;
import java.util.List;

import javax.xml.datatype.Duration;
import javax.xml.datatype.XMLGregorianCalendar;

import org.joda.time.DateTime;
import org.w3c.dom.Node;

import com.qualitylogic.openadr.core.action.IResponseCanceledPartyRegistrationAckAction;
import com.qualitylogic.openadr.core.action.IResponseCreatedReportTypeAckAction;
import com.qualitylogic.openadr.core.action.IResponseEventAction;
import com.qualitylogic.openadr.core.action.IResponseRegisteredReportTypeAckAction;
import com.qualitylogic.openadr.core.action.IResponseUpdatedReportTypeAckAction;
import com.qualitylogic.openadr.core.action.ResponseCanceledPartyRegistrationAckActionList;
import com.qualitylogic.openadr.core.action.ResponseCreatedReportTypeAckActionList;
import com.qualitylogic.openadr.core.action.ResponseEventActionList;
import com.qualitylogic.openadr.core.action.ResponseRegisteredReportTypeAckActionList;
import com.qualitylogic.openadr.core.action.ResponseUpdatedReportTypeAckActionList;
import com.qualitylogic.openadr.core.action.impl.DefaultCanceledPartyRegistration;
import com.qualitylogic.openadr.core.action.impl.DefaultResponseCreatedReportTypeAckAction;
import com.qualitylogic.openadr.core.action.impl.DefaultResponseRegisteredReportTypeAckAction;
import com.qualitylogic.openadr.core.action.impl.DefaultResponseUpdatedReportTypeAckAction;
import com.qualitylogic.openadr.core.action.impl.Default_ResponseEventAction;
import com.qualitylogic.openadr.core.channel.util.StringUtil;
import com.qualitylogic.openadr.core.common.TestSession;
import com.qualitylogic.openadr.core.common.UIUserPrompt;
import com.qualitylogic.openadr.core.common.UIUserPromptDualAction;
import com.qualitylogic.openadr.core.exception.CancelledException;
import com.qualitylogic.openadr.core.exception.FailedException;
import com.qualitylogic.openadr.core.exception.ValidationException;
import com.qualitylogic.openadr.core.internal.BatchTestRunner;
import com.qualitylogic.openadr.core.signal.EiResponseType;
import com.qualitylogic.openadr.core.signal.OadrCancelPartyRegistrationType;
import com.qualitylogic.openadr.core.signal.OadrCancelReportType;
import com.qualitylogic.openadr.core.signal.OadrCanceledPartyRegistrationType;
import com.qualitylogic.openadr.core.signal.OadrCanceledReportType;
import com.qualitylogic.openadr.core.signal.OadrCreatePartyRegistrationType;
import com.qualitylogic.openadr.core.signal.OadrCreateReportType;
import com.qualitylogic.openadr.core.signal.OadrCreatedReportType;
import com.qualitylogic.openadr.core.signal.OadrDistributeEventType;
import com.qualitylogic.openadr.core.signal.OadrRegisterReportType;
import com.qualitylogic.openadr.core.signal.OadrRegisteredReportType;
import com.qualitylogic.openadr.core.signal.OadrReportRequestType;
import com.qualitylogic.openadr.core.signal.OadrRequestReregistrationType;
import com.qualitylogic.openadr.core.signal.OadrResponseType;
import com.qualitylogic.openadr.core.signal.OadrUpdateReportType;
import com.qualitylogic.openadr.core.signal.OadrUpdatedReportType;
import com.qualitylogic.openadr.core.signal.helper.CancelReportEventHelper;
import com.qualitylogic.openadr.core.signal.helper.CanceledPartyRegistrationHelper;
import com.qualitylogic.openadr.core.signal.helper.CanceledReportEventHelper;
import com.qualitylogic.openadr.core.signal.helper.CreatedReportEventHelper;
import com.qualitylogic.openadr.core.signal.helper.RegisteredReportEventHelper;
import com.qualitylogic.openadr.core.signal.helper.SchemaHelper;
import com.qualitylogic.openadr.core.signal.helper.UpdatedReportEventHelper;
import com.qualitylogic.openadr.core.signal.xcal.AvailableType;
import com.qualitylogic.openadr.core.signal.xcal.Dtstart;
import com.qualitylogic.openadr.core.signal.xcal.DurationPropType;
import com.qualitylogic.openadr.core.signal.xcal.Properties;
import com.qualitylogic.openadr.core.util.OadrUtil;
import com.qualitylogic.openadr.core.util.PropertiesFileReader;
import com.qualitylogic.openadr.core.util.ResourceFileReader;
import com.qualitylogic.openadr.core.util.XMLDBUtil;
import com.qualitylogic.openadr.core.util.XMLDBUtil.DataPoint;
import com.qualitylogic.openadr.core.ven.VENServerResource;

public abstract class NewTestCase extends BaseTestCase {
	
	protected static ResourceFileReader resources = new ResourceFileReader();
	protected static PropertiesFileReader properties = new PropertiesFileReader();

	protected void newInit(){
		// System.setErr(new PrintStream(new ByteArrayOutputStream()));
		newTest = true;
		OadrUtil.setServiceType(this.getClass().getName());
	}

	public void execute() {
		try {
			newInit();
			setEnableLogging(true);
			executeTestCase();		
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	protected static void execute(BaseTestCase testCase) {
		try {
			testCase.newInit();
			testCase.setEnableLogging(true);
			testCase.executeTestCase();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected static void executeDut(BaseTestCase testCase) {
		try {
			testCase.newInit();
			testCase.setEnableLogging(false);
			testCase.executeTestCase();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	protected void prompt(String message) {
		if (!properties.isTestPrompt()) {
			return;
		}
		
		new UIUserPrompt().Prompt(message);
		if (!TestSession.isUserClickedContinuePlay()) {
			throw new CancelledException();
		}
		
		checkForValidationErrors();
	}

	protected void checkForValidationErrors() {
		if (TestSession.isAtleastOneValidationErrorPresent()) {
			throw new ValidationException();
		}
	}
	
	protected boolean promptYes(String message) {
		if (!properties.isTestPrompt()) {
			if (message.equals(resources.prompt_036())) {
				return false;
			} else if (message.equals(resources.prompt_050())) {
				String className = this.getClass().getName();
				boolean isVen = (className.contains("_VEN"));
				boolean result = (isVen == BatchTestRunner.PROMPT_050_VEN_YES);
				System.out.println("prompt_050=" + result);
				return result;
			} else {
				return true;
			}
		}
		
		new UIUserPromptDualAction().Prompt(message);
		
		if(TestSession.isUserClickedToCancelUIDualAction()){
			throw new CancelledException();
		}

		return TestSession.isUiDualActionYesOptionClicked();
	}
	
	protected void alert(String message) {
		if (!properties.isTestPrompt()) {
			return;
		}
		
		new UIUserPrompt().Prompt(message);
	}

	protected void waitForCompletion() {
		long timeout = Long.valueOf(properties.get("asyncResponseTimeout"));
		waitForCompletion(timeout);
	}

	protected void waitForCompletion(long timeout) {
		pauseForTestCaseTimeout(timeout);

		checkForValidationErrors();
	}

	protected IResponseRegisteredReportTypeAckAction addRegisteredReportResponse() {
		IResponseRegisteredReportTypeAckAction registeredReport = new DefaultResponseRegisteredReportTypeAckAction();
		ResponseRegisteredReportTypeAckActionList.addResponseRegisteredReportAckAction(registeredReport);
		return registeredReport;
	}

	protected void addRegisteredReportResponse(IResponseRegisteredReportTypeAckAction registeredReport) {
		ResponseRegisteredReportTypeAckActionList.addResponseRegisteredReportAckAction(registeredReport);
	}
	
	protected void checkRegisterReportRequest(int size) {
		List<OadrRegisterReportType> registerReports = TestSession.getOadrRegisterReportTypeReceivedList();
		if (registerReports.size() != size) {
			throw new FailedException("Expected " + size + " OadrRegisterReport(s), received " + registerReports.size());
		}
	}

	protected void checkRegisterReportRequestLess(int size) {
		List<OadrRegisterReportType> registerReports = TestSession.getOadrRegisterReportTypeReceivedList();
		if (registerReports.size() < size) {
			throw new FailedException("Expected " + size + " OadrRegisterReport(s), received " + registerReports.size());
		}
	}
	
	protected AvailableType getAvailable(int startDays, int durationHours) {
		XMLGregorianCalendar currentTime = OadrUtil.getCurrentTime();
		
		Duration durationDays = OadrUtil.createDuration(startDays * 24, 0, 0);
		currentTime.add(durationDays);
		Dtstart durationStartTime = new Dtstart();
		durationStartTime.setDateTime(currentTime);

		Duration duration = OadrUtil.createDuration(durationHours, 0, 0);
		DurationPropType durationProp = new DurationPropType();
		durationProp.setDuration(duration.toString());
		
		AvailableType available = new AvailableType();
		available.setProperties(new Properties());
		Properties properties = available.getProperties();
		properties.setDtstart(durationStartTime);
		properties.setDuration(durationProp);
		
		return available;
	}

	protected AvailableType getAvailable(String duration) {
		Dtstart durationStartTime = new Dtstart();
		durationStartTime.setDateTime(OadrUtil.getCurrentTime());

		DurationPropType durationProp = new DurationPropType();
		durationProp.setDuration(duration);

		AvailableType available = new AvailableType();
		available.setProperties(new Properties());
		Properties properties = available.getProperties();
		properties.setDtstart(durationStartTime);
		properties.setDuration(durationProp);
		
		return available;
	}
	
	protected IResponseCanceledPartyRegistrationAckAction addCanceledPartyRegistrationResponse() {
		return addCanceledPartyRegistrationResponse(ErrorConst.OK_200);
	}

	protected IResponseCanceledPartyRegistrationAckAction addCanceledPartyRegistrationResponse(String responseCode) {
		IResponseCanceledPartyRegistrationAckAction canceledPartyRegistration = new DefaultCanceledPartyRegistration(responseCode);
		ResponseCanceledPartyRegistrationAckActionList.addResponseCanceledPartyRegistrationAckAction(canceledPartyRegistration);
		return canceledPartyRegistration;
	}

	protected void waitForCancelPartyRegistration(int size) {
		long testCaseTimeout = OadrUtil.getTestCaseTimeout();
		while (System.currentTimeMillis() < testCaseTimeout) {
			pause(1);
			
			if (TestSession.getCancelPartyRegistrationTypeListReceived().size() >= size) {
				break;
			} else if (TestSession.isAtleastOneValidationErrorPresent()) {
				break;
			}
		}
		pause(2);
		
		int listSize = TestSession.getCancelPartyRegistrationTypeListReceived().size();
		if (listSize != size) {
			throw new FailedException("Expected " + size + " CancelPartyRegistration(s), received " + listSize);
		}

		checkForValidationErrors();
	}

	protected void waitForRequestReregistration(int size) {
		long testCaseTimeout = OadrUtil.getTestCaseTimeout();
		while (System.currentTimeMillis() < testCaseTimeout) {
			pause(1);
			
			if (TestSession.getOadrRequestReregistrationReceivedList().size() >= size) {
				break;
			} else if (TestSession.isAtleastOneValidationErrorPresent()) {
				break;
			}
		}
		pause(2);
		
		int listSize = TestSession.getOadrRequestReregistrationReceivedList().size();
		if (listSize != size) {
			throw new FailedException("Expected " + size + " RequestReregistration(s), received " + listSize);
		}

		checkForValidationErrors();
	}
	
	protected void waitForCancelOpt(int size) {
		long testCaseTimeout = OadrUtil.getTestCaseTimeout();
		while (System.currentTimeMillis() < testCaseTimeout) {
			pause(1);
			if (TestSession.getCancelOptTypeReceivedList().size() >= size) {
				break;
			} else if (TestSession.isAtleastOneValidationErrorPresent()) {
				break;
			}
		}
		pause(2);
		
		int listSize = TestSession.getCancelOptTypeReceivedList().size();
		if (listSize != size) {
			throw new FailedException("Expected " + size + " CancelOpt(s), received " + listSize);
		}
		
		checkForValidationErrors();
	}
	
	protected void waitForCreatePartyRegistration(int size) {
		long testCaseTimeout = OadrUtil.getTestCaseTimeout();
		while (System.currentTimeMillis() < testCaseTimeout) {
			pause(1);
			if (TestSession.getCreatePartyRegistrationTypeReceivedList().size() >= size) {
				break;
			} else if (TestSession.isAtleastOneValidationErrorPresent()) {
				break;
			}
		}
		pause(2);

		checkCreatePartyRegistrationRequest(size);
		
		checkForValidationErrors();
	}
	
	protected OadrCanceledPartyRegistrationType sendCancelPartyRegistration(OadrCancelPartyRegistrationType cancelPartyRegistration) throws Exception {
		return sendCancelPartyRegistration(cancelPartyRegistration, ErrorConst.OK_200);
	}
	
	protected OadrCanceledPartyRegistrationType sendCancelPartyRegistration(OadrCancelPartyRegistrationType cancelPartyRegistration, String responseCode) throws Exception {
		String text = SchemaHelper.getCancelPartyRegistrationAsString(cancelPartyRegistration);
		String response = post(text, "EiRegistration");
		if (!OadrUtil.isExpected(response, OadrCanceledPartyRegistrationType.class)){
			throw new FailedException("Expected OadrCanceledPartyRegistration has not been received");
		}

		OadrCanceledPartyRegistrationType canceledPartyRegistration = CanceledPartyRegistrationHelper.createOadrCanceledPartyRegistrationTypeFromString(response);
		String eiResponseCode = canceledPartyRegistration.getEiResponse().getResponseCode();
		if (!eiResponseCode.equals(responseCode)) {
			throw new FailedException("Expected OadrCanceledPartyRegistration responseCode of " + responseCode + " has not been received. Got " + eiResponseCode + ".");
		}
		return canceledPartyRegistration;
	}

	protected void sendRequestReregistration(OadrRequestReregistrationType requestReregistration) throws Exception {
		String text = SchemaHelper.getOadrRequestReregistrationTypeAsString(requestReregistration);
		String response = post(text, "EiRegistration");
		if (!OadrUtil.isExpected(response, OadrResponseType.class)){
			throw new FailedException("Expected OadrRequestReregistration has not been received");
		}
	}
	
	protected IResponseEventAction addResponse() {
		return addResponse(ErrorConst.OK_200);
	}
	
	protected IResponseEventAction addResponse(String responseCode) {
		IResponseEventAction response = new Default_ResponseEventAction(responseCode);
		ResponseEventActionList.addResponseEventAction(response);
		return response;
	}
	
	protected OadrDistributeEventType waitForDistributeEvent(int size) {
		long testCaseTimeout = OadrUtil.getTestCaseTimeout();
		while (System.currentTimeMillis() < testCaseTimeout) {
			pause(1);
			
			if (VENServerResource.getOadrDistributeEventReceivedsList().size() >= size) {
				break;
			} else if (TestSession.isAtleastOneValidationErrorPresent()) {
				break;
			}
		}
		pause(2);
		
		int listSize = VENServerResource.getOadrDistributeEventReceivedsList().size();
		if (listSize != size) {
			throw new FailedException("Expected " + size + " DistributeEvent(s), received " + listSize);
		}
		
		checkForValidationErrors();
		
		return VENServerResource.getOadrDistributeEventReceivedsList().get(size - 1);
	}

	protected void waitForResponse(int size) {
		long testCaseTimeout = OadrUtil.getTestCaseTimeout();
		while (System.currentTimeMillis() < testCaseTimeout) {
			pause(1);
			
			if (TestSession.getOadrResponse().size() >= size) {
				break;
			} else if (TestSession.isAtleastOneValidationErrorPresent()) {
				break;
			}
		}

		int listSize = TestSession.getOadrResponse().size();
		if (listSize != size) {
			throw new FailedException("Expected " + size + " Response(s), received " + listSize);
		}
		
		checkForValidationErrors();
	}
	
	protected void waitForCreatedOpt(int size) {
		long testCaseTimeout = OadrUtil.getTestCaseTimeout();
		while (System.currentTimeMillis() < testCaseTimeout) {
			pause(1);
			
			if (TestSession.getCreatedOptReceivedList().size() >= size) {
				break;
			} else if (TestSession.isAtleastOneValidationErrorPresent()) {
				break;
			}
		}
		pause(2);

		int listSize = TestSession.getCreatedOptReceivedList().size();
		if (listSize != size) {
			throw new FailedException("Expected " + size + " CreatedOpt(s), received " + listSize);
		}
		
		checkForValidationErrors();
	}

	protected void pauseForTestCaseTimeout(long timeout) {
		long testCaseBufferTimeout = Long.valueOf(properties.get("testCaseBufferTimeout"));
		long testCaseTimeout = System.currentTimeMillis() + timeout;

		while (System.currentTimeMillis() < testCaseTimeout) {
			if (TestSession.isAtleastOneValidationErrorPresent()) {
				System.out.println("Validation error(s) is present " + new Date());
				break;
			}

			if (TestSession.isCreatedEventNotReceivedTillTimeout()) {
				System.out.println("Created Event not received breaking out of loop " + new Date());
				break;
			}

			if (TestSession.isTestCaseDone()) {
				long testCaseBufferTime = System.currentTimeMillis() + testCaseBufferTimeout;

				while (System.currentTimeMillis() < testCaseBufferTime) {
					try {
						Thread.sleep(200);
					} catch (InterruptedException e) {
					}
				}
				break;
			}

			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
			}
		}
	}

	protected void pauseInMillis(int millis) {
		long pauseTimeout = System.currentTimeMillis() + (millis);
		while (System.currentTimeMillis() < pauseTimeout) {
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// ignore
			}
		}
	}
	
	protected void checkCreatePartyRegistrationRequest(int size) {
		List<OadrCreatePartyRegistrationType> createPartyRegistrations = TestSession.getCreatePartyRegistrationTypeReceivedList();
		if (createPartyRegistrations.size() != size) {
			throw new FailedException("Expected " + size + " OadrCreatePartyRegistration(s), received " + createPartyRegistrations.size());
		}
	}

	protected IResponseUpdatedReportTypeAckAction addUpdatedReportResponse() {
		IResponseUpdatedReportTypeAckAction updatedReport = new DefaultResponseUpdatedReportTypeAckAction();
		ResponseUpdatedReportTypeAckActionList.addResponseUpdatedReportAckAction(updatedReport);
		return updatedReport;
	}
	
	protected IResponseUpdatedReportTypeAckAction addUpdatedReportResponse(IResponseUpdatedReportTypeAckAction updatedReport) {
		ResponseUpdatedReportTypeAckActionList.addResponseUpdatedReportAckAction(updatedReport);
		return updatedReport;
	}

	protected IResponseUpdatedReportTypeAckAction addUpdatedReportResponse(String responseCode) {
		IResponseUpdatedReportTypeAckAction updatedReport = new DefaultResponseUpdatedReportTypeAckAction(responseCode);
		ResponseUpdatedReportTypeAckActionList.addResponseUpdatedReportAckAction(updatedReport);
		return updatedReport;
	}
	
	protected void checkNoActiveRegistration() {
		String registrationID = new XMLDBUtil().getRegistrationID();
		if (!StringUtil.isBlank(registrationID)){
			throw new FailedException("Registration is active.");
		}
	}
	
	protected static boolean isCalendarWithinMinutes(XMLGregorianCalendar calendar, int minutesOffset, int minutes) {
		Date time = calendar.toGregorianCalendar().getTime();
		
		DateTime dateTime = new DateTime().minusMinutes(minutesOffset);
		Date start = dateTime.minusMinutes(minutes).toDate();
		Date end = dateTime.plusMinutes(minutes).toDate();
		
		return (time.after(start) && time.before(end));
	}

	protected DataPoint getDataPoint(Node reportNode, String type) {
		DataPoint result = null;
		
		XMLDBUtil xmlDb = new XMLDBUtil();
		List<DataPoint> dataPoints = xmlDb.getDataPoints(reportNode);
		for (DataPoint dataPoint : dataPoints) {
			if (dataPoint.getType().equals(type)) {
				result = dataPoint;
			}
		}
		return result;
	}

	protected void pause(int seconds, String message) {
		System.out.println(message);
		pause(seconds);
	}
	
	protected IResponseCreatedReportTypeAckAction addCreatedReportResponse() {
		return addCreatedReportResponse(ErrorConst.OK_200);
	}

	protected IResponseCreatedReportTypeAckAction addCreatedReportResponse(String responseCode) {
		IResponseCreatedReportTypeAckAction createdReport = new DefaultResponseCreatedReportTypeAckAction(responseCode);
		ResponseCreatedReportTypeAckActionList.addResponseCreatedReportAckAction(createdReport);
		return createdReport;
	}

	protected OadrCreateReportType waitForCreateReport(int size) {
		long testCaseTimeout = OadrUtil.getTestCaseTimeout();
		while (System.currentTimeMillis() < testCaseTimeout) {
			pause(1);
			
			if (TestSession.getOadrCreateReportTypeReceivedList().size() >= size) {
				break;
			} else if (TestSession.isAtleastOneValidationErrorPresent()) {
				break;
			}
		}
		
		int listSize = TestSession.getOadrCreateReportTypeReceivedList().size();
		if (listSize != size) {
			throw new FailedException("Expected " + size + " CreateReport(s), received " + listSize);
		}

		checkForValidationErrors();
		
		return TestSession.getOadrCreateReportTypeReceivedList().get(size - 1);
	}
	
	protected OadrUpdatedReportType sendUpdateReport(OadrUpdateReportType updateReport) throws Exception {
		return sendUpdateReport(updateReport, ErrorConst.OK_200);
	}
	
	protected OadrUpdatedReportType sendUpdateReport(OadrUpdateReportType updateReport, String responseCode) throws Exception {
		String text = SchemaHelper.getUpdateReportTypeAsString(updateReport);
		String response = post(text, "EiReport");
	
		if (!OadrUtil.isExpected(response, OadrUpdatedReportType.class)) {
			throw new FailedException("Expected OadrUpdatedReport has not been received");
		}

		OadrUpdatedReportType updatedReport = UpdatedReportEventHelper.loadReportFromString(response);
		String eiResponseCode = updatedReport.getEiResponse().getResponseCode();
		if (!eiResponseCode.equals(responseCode)) {
			throw new FailedException("Expected OadrUpdateReport responseCode of " + responseCode + " has not been received. Got " + eiResponseCode + ".");
		}
		
		return updatedReport;
	}
	
	protected void sendUpdateReportWithSignedId(OadrUpdateReportType updateReport) throws Exception {
		String text = SchemaHelper.getUpdateReportTypeWithSignedIdAsString(updateReport);
		String response = post(text, "EiReport");
	
		if (!OadrUtil.isExpected(response, OadrUpdatedReportType.class)) {
			throw new FailedException("Expected OadrUpdatedReport has not been received");
		}
	}

	protected OadrCreatedReportType sendCreateReport(OadrCreateReportType createReport) throws Exception {
		return sendCreateReport(createReport, ErrorConst.OK_200);
	}
	
	protected OadrCreatedReportType sendCreateReport(OadrCreateReportType createReport, String responseCode) throws Exception {
		// TODO move to VtnToVenClient
		TestSession.getOadrCreateReportTypeReceivedList().add(createReport);
		
		String text = SchemaHelper.getCreateReportTypeAsString(createReport);
		String responseText = post(text, "EiReport");

		if (!OadrUtil.isExpected(responseText, OadrCreatedReportType.class)) {
			throw new FailedException("Expected OadrCreatedReport has not been received");
		}
		
		OadrCreatedReportType createdReport = CreatedReportEventHelper.createOadrCreatedReportFromString(responseText);
		String eiResponseCode = createdReport.getEiResponse().getResponseCode();
		if (!eiResponseCode.equals(responseCode)) {
			throw new FailedException("Expected OadrCreatedReport responseCode of " + responseCode + " has not been received. Got " + eiResponseCode + ".");
		}
		
		return createdReport;
	}
	
	protected OadrUpdateReportType checkUpdateReport(int size) {
		List<OadrUpdateReportType> updateReports = TestSession.getOadrUpdateReportTypeReceivedList();
		if (updateReports.size() != size){
			throw new FailedException("Expected " + size + " OadrUpdateReport(s), received " + updateReports.size());
		}

		return TestSession.getOadrUpdateReportTypeReceivedList().get(size - 1);
	}

	protected OadrCanceledReportType checkCanceledReport(int size) {
		List<OadrCanceledReportType> canceledReports = TestSession.getOadrCanceledReportTypeReceivedList();
		if (canceledReports.size() != size){
			throw new FailedException("Expected " + size + " OadrCanceledReport(s), received " + canceledReports.size());
		}

		return TestSession.getOadrCanceledReportTypeReceivedList().get(size - 1);
	}
	
	protected OadrRegisterReportType waitForRegisterReport(int size) {
		long testCaseTimeout = OadrUtil.getTestCaseTimeout();
		while (System.currentTimeMillis() < testCaseTimeout) {
			pause(1);
			
			if (TestSession.getOadrRegisterReportTypeReceivedList().size() >= size) {
				break;
			} else if (TestSession.isAtleastOneValidationErrorPresent()) {
				break;
			}
		}
		pause(2);
		
		int listSize = TestSession.getOadrRegisterReportTypeReceivedList().size();
		if (listSize != size) {
			throw new FailedException("Expected " + size + " RegisterReport(s), received " + listSize);
		}

		checkForValidationErrors();

		return TestSession.getOadrRegisterReportTypeReceivedList().get(size - 1);
	}

	protected void checkCancelPartyRegistrationRequest(int size) {
		List<OadrCancelPartyRegistrationType> cancelPartyRegistrations = TestSession.getCancelPartyRegistrationTypeListReceived();
		if (cancelPartyRegistrations.size() != size) {
			throw new FailedException("Expected " + size + " OadrCancelPartyRegistration(s), received " + cancelPartyRegistrations.size());
		}
	}

	protected void waitForReportBackDuration(OadrCreateReportType createReport) {
		String reportBackDuration = null;
		try {
			reportBackDuration = createReport.getOadrReportRequest().get(0).getReportSpecifier().getReportBackDuration().getDuration();
		} catch (Exception e) {
			throw new FailedException("Unable to get reportBackDuration");
		}
		
		Duration duration = OadrUtil.createDuration(reportBackDuration);
		XMLGregorianCalendar calendar = OadrUtil.getCurrentTime();
		calendar.add(duration);
		
		System.out.println("Pausing for " + reportBackDuration + "...");
		long expiration = OadrUtil.getTimeMilliseconds(calendar);
		while (System.currentTimeMillis() < expiration) {
			pause(1);
		}
	}
	
	protected OadrCanceledReportType sendCancelReport(OadrCreateReportType createReport) throws Exception {
		return sendCancelReport(createReport, ErrorConst.OK_200);
	}
	
	protected OadrCanceledReportType sendCancelReport(OadrCreateReportType createReport, String responseCode) throws Exception {
		OadrCancelReportType cancelReport = CancelReportEventHelper.loadOadrCancelReportType(createReport);
		return sendCancelReport(cancelReport, responseCode);
	}
	
	protected OadrCanceledReportType sendCancelReport(OadrCancelReportType cancelReport) throws Exception {
		return sendCancelReport(cancelReport, ErrorConst.OK_200);
	}

	protected OadrCanceledReportType sendCancelReport(OadrCancelReportType cancelReport, String responseCode) throws Exception {
		String text = SchemaHelper.getCancelReportTypeAsString(cancelReport);
		String responseText = post(text, "EiReport");
		if (!OadrUtil.isExpected(responseText, OadrCanceledReportType.class)) {
			throw new FailedException("Expected OadrCanceledReport has not been received");
		}
	
		OadrCanceledReportType response = CanceledReportEventHelper.loadCanceledReportTypeFromString(responseText);
		String eiResponseCode = response.getEiResponse().getResponseCode();
		if (!eiResponseCode.equals(responseCode)) {
			throw new FailedException("Expected OadrResponse " + responseCode + ", received " + eiResponseCode);
		}
		
		return response;
	}
	
	protected void sendCreatedReport() throws Exception {
		sendCreatedReport(ErrorConst.OK_200);
	}

	protected void sendCreatedReport(String responseCode) throws Exception {
		OadrCreatedReportType createdReport = CreatedReportEventHelper.loadOadrCreatedReport();
		
		EiResponseType eiResponse = createdReport.getEiResponse();
		eiResponse.setResponseCode(responseCode);
		eiResponse.setResponseDescription(responseCode.equals(ErrorConst.OK_200) ? "OK" : "ERROR");
		
		String text = SchemaHelper.getCreatedReportTypeAsString(createdReport);
		String response = post(text, "EiReport");
		if (!OadrUtil.isExpected(response, OadrResponseType.class)) {
			throw new FailedException("Expected OadrResponse has not been received");
		}
	}

	protected void sendCreatedReport(OadrCreatedReportType createdReport) throws Exception {
		String text = SchemaHelper.getCreatedReportTypeAsString(createdReport);
		String response = post(text, "EiReport");
		if (!OadrUtil.isExpected(response, OadrResponseType.class)) {
			throw new FailedException("Expected OadrResponse has not been received");
		}
	}

	protected void checkCreatedReportPendingReports(OadrCreatedReportType createdReport, int size) {
		int listSize = createdReport.getOadrPendingReports().getReportRequestID().size();
		if (listSize != size) {
			throw new FailedException("Expected " + size + " OadrPendingReport(s). Got " + listSize);
		}
	}

	protected OadrCancelReportType waitForCancelReport(int size) {
		long testCaseTimeout = OadrUtil.getTestCaseTimeout();
		while (System.currentTimeMillis() < testCaseTimeout) {
			pause(1);
			
			if (TestSession.getOadrCancelReportTypeReceivedList().size() >= size) {
				break;
			} else if (TestSession.isAtleastOneValidationErrorPresent()) {
				break;
			}
		}
		pause(2);
		
		int listSize = TestSession.getOadrCancelReportTypeReceivedList().size();
		if (listSize != size) {
			throw new FailedException("Expected " + size + " CancelReport(s), received " + listSize);
		}

		checkForValidationErrors();
		
		return TestSession.getOadrCancelReportTypeReceivedList().get(size - 1);
	}

	protected void checkReportRequest(OadrCreateReportType createReport, int size) {
		List<OadrReportRequestType> reportRequest = createReport.getOadrReportRequest();
		if (reportRequest.size() != size) {
			throw new FailedException("Expected " + size + " OadrRegisterReport OadrReportRequest(s), received " + reportRequest.size());
		}
	}
	
	protected void checkSecurity() {
		if (!properties.isSecurity_Enabled()) {
			throw new FailedException("Security is required for this test case.");
		}
	}

	protected OadrRegisteredReportType sendRegisterReport(OadrRegisterReportType registerReport) throws Exception {
		String text = SchemaHelper.getRegisterReportTypeAsString(registerReport);
		String response = post(text, "EiReport");
		if (!OadrUtil.isExpected(response, OadrRegisteredReportType.class)) {
			throw new FailedException("Expected OadrRegisteredReport has not been received");
		}
		
		return RegisteredReportEventHelper.createOadrRegisteredReportFromString(response);
	}

	abstract protected String post(String message, String type) throws Exception;
}
