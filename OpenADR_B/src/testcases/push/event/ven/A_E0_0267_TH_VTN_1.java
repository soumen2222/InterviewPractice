package testcases.push.event.ven;

import java.util.List;

import javax.xml.bind.JAXBContext;

import com.qualitylogic.openadr.core.action.DistributeEventActionList;
import com.qualitylogic.openadr.core.action.ICreatedEventResult;
import com.qualitylogic.openadr.core.action.IDistributeEventAction;
import com.qualitylogic.openadr.core.action.IResponseEventAction;
import com.qualitylogic.openadr.core.action.ResponseEventActionList;
import com.qualitylogic.openadr.core.action.impl.Default_CreatedEventResultOptINOptOut;
import com.qualitylogic.openadr.core.action.impl.Default_Push_CPP_DistributeEventAction;
import com.qualitylogic.openadr.core.action.impl.Default_ResponseEventAction;
import com.qualitylogic.openadr.core.base.PUSHBaseTestCase;
import com.qualitylogic.openadr.core.common.TestSession;
import com.qualitylogic.openadr.core.common.UIUserPrompt;
import com.qualitylogic.openadr.core.common.UIUserPromptDualAction;
import com.qualitylogic.openadr.core.signal.EiActivePeriodType;
import com.qualitylogic.openadr.core.signal.EventStatusEnumeratedType;
import com.qualitylogic.openadr.core.signal.IntervalType;
import com.qualitylogic.openadr.core.signal.Intervals;
import com.qualitylogic.openadr.core.signal.OadrDistributeEventType;
import com.qualitylogic.openadr.core.signal.OptTypeType;
import com.qualitylogic.openadr.core.signal.helper.DistributeEventSignalHelper;
import com.qualitylogic.openadr.core.signal.helper.LogHelper;
import com.qualitylogic.openadr.core.signal.helper.SchemaHelper;
import com.qualitylogic.openadr.core.signal.xcal.Properties;
import com.qualitylogic.openadr.core.signal.xcal.Properties.Tolerance;
import com.qualitylogic.openadr.core.signal.xcal.Properties.Tolerance.Tolerate;
import com.qualitylogic.openadr.core.util.LogResult;
import com.qualitylogic.openadr.core.util.OadrUtil;
import com.qualitylogic.openadr.core.util.ResourceFileReader;
import com.qualitylogic.openadr.core.vtn.RandomizedCancellationTimeoutThread;
import com.qualitylogic.openadr.core.vtn.VTNService;
import com.qualitylogic.openadr.core.vtn.VtnToVenClient;

@SuppressWarnings("restriction")
public class A_E0_0267_TH_VTN_1 extends PUSHBaseTestCase {

	public static void main(String[] args) {
		try {
			A_E0_0267_TH_VTN_1 testClass = new A_E0_0267_TH_VTN_1();
			testClass.setEnableLogging(true);
			testClass.executeTestCase();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void test() throws Exception {

		// Prompt to make sure events are cleared from DUT
		ResourceFileReader resourceFileReader = new ResourceFileReader();
		UIUserPrompt uiUserPrompt = new UIUserPrompt();
		uiUserPrompt.Prompt(resourceFileReader.Prereq_NoEvents_Message()
				+ "\n\n" + resourceFileReader.TestCase_0267_UIUserPromptText()+ "\n\n" + resourceFileReader.TestCase_RandomizedCancellationFailWarningPrompt(),
				uiUserPrompt.LARGE);

		if (!TestSession.isUserClickedContinuePlay()) {
			LogHelper.setResult(LogResult.NA);
			LogHelper.addTrace("TestCase execution was cancelled by the user");
			return;
		}

		// Create DistributeEventAction Action
		IDistributeEventAction distributeEventAction1 = new Default_Push_CPP_DistributeEventAction();
		OadrDistributeEventType oadrDistributeEvent = distributeEventAction1
				.getDistributeEvent();
		DistributeEventSignalHelper.setNewReqIDEvntIDStartTimeAndMarketCtx1(
				oadrDistributeEvent, 1);

		EiActivePeriodType eiActivePeriod = oadrDistributeEvent.getOadrEvent()
				.get(0).getEiEvent().getEiActivePeriod();
		Properties properties = eiActivePeriod.getProperties();
		Tolerate tolerate = new Tolerate();
		tolerate.setStartafter("PT2M");
		Tolerance tolerance = new Tolerance();
		properties.setTolerance(tolerance);
		tolerance.setTolerate(tolerate);

		eiActivePeriod.getProperties().getDuration().setDuration("PT5M");

		Intervals intervals = oadrDistributeEvent.getOadrEvent().get(0)
				.getEiEvent().getEiEventSignals().getEiEventSignal().get(0)
				.getIntervals();
		List<IntervalType> intervalList = intervals.getInterval();
		intervalList.remove(2);

		IntervalType interval1 = intervalList.get(0);
		interval1.getDuration().setDuration("PT3M");
		IntervalType interval2 = intervalList.get(1);
		interval2.getDuration().setDuration("PT2M");

		oadrDistributeEvent.getOadrEvent().get(0).getEiEvent()
				.getEiEventSignals().getEiEventSignal().get(0)
				.getCurrentValue().getPayloadFloat().setValue((float) 0.0);
		distributeEventAction1.setEventCompleted(true);

		// Add DistributeEventAction to DistributeEventActionList
		DistributeEventActionList
				.addDistributeEventAction(distributeEventAction1);

		// Create CreatedEventResult
		ICreatedEventResult createdEventResult = new Default_CreatedEventResultOptINOptOut();
		createdEventResult.addDistributeEvent(distributeEventAction1);
		createdEventResult.setExpectedOptInCount(1);
		createdEventResult.setExpectedOptOutCount(0);
		createdEventResult.setLastCreatedEventResult(true);

		// Set the CreatedEventResult to
		// distributeEventAction.setCreatedEventSuccessCriteria
		distributeEventAction1
				.setCreatedEventSuccessCriteria(createdEventResult);

		// Create ResponseEventAction
		IResponseEventAction responseEventAction = new Default_ResponseEventAction();

		// Add ResponseEventAction to ResponseEventActionList
		ResponseEventActionList.addResponseEventAction(responseEventAction);

		// Start VTN Server
		VTNService.startVTNService();

		OadrDistributeEventType distributeEvent = distributeEventAction1
				.getDistributeEvent();
		JAXBContext context = JAXBContext
				.newInstance(OadrDistributeEventType.class);
		String strOadrDistributeEvent = SchemaHelper.getDistributeEventAsString(
				distributeEvent);

		VtnToVenClient.post(strOadrDistributeEvent);
		distributeEventAction1.startCreatedEventTimeoutActionThread();

		// Pause for Timeout before proceeding with TestCase validation.
		distributeEventAction1.startCreatedEventTimeoutActionThread();
		

		pauseForTestCaseTimeout();

		// Fail if validation errors are found.
		boolean atleastOneValidationErrorPresent = TestSession
				.isAtleastOneValidationErrorPresent();
		if (atleastOneValidationErrorPresent) {
			LogHelper.setResult(LogResult.FAIL);
			LogHelper.addTrace("Validation Error(s) are present");
			LogHelper.addTrace("Test Case has Failed");
			return;
		}

		// If Expected Created Event has been Received.
		if (createdEventResult.isExpectedCreatedEventReceived()) {
			LogHelper.addTrace("Received matching OadrCreatedEventType with "
					+ OptTypeType.OPT_IN);

			UIUserPrompt uiUserPrompt2 = new UIUserPrompt();
			uiUserPrompt2.Prompt(
					resourceFileReader.TestCase_0267_SecondUIUserPromptText(),
					uiUserPrompt.MEDIUM);

			if (!TestSession.isUserClickedContinuePlay()) {
				LogHelper.setResult(LogResult.NA);
				LogHelper
						.addTrace("TestCase execution was cancelled by the user");
				return;
			}

			// Create CreatedEventResult
			ICreatedEventResult createdEventResult1 = new Default_CreatedEventResultOptINOptOut();
			createdEventResult1.addDistributeEvent(distributeEventAction1);
			createdEventResult1.setExpectedOptInCount(1);
			createdEventResult1.setExpectedOptOutCount(0);
			createdEventResult1.setLastCreatedEventResult(true);

			// Set the CreatedEventResult to
			// distributeEventAction.setCreatedEventSuccessCriteria
			distributeEventAction1
					.setCreatedEventSuccessCriteria(createdEventResult1);

			distributeEvent.setRequestID(OadrUtil
					.createoadrDistributeRequestID());
			distributeEvent.getOadrEvent().get(0).getEiEvent()
					.getEventDescriptor()
					.setCreatedDateTime(OadrUtil.getCurrentTime());
			distributeEvent.getOadrEvent().get(0).getEiEvent()
					.getEventDescriptor()
					.setEventStatus(EventStatusEnumeratedType.CANCELLED);
			distributeEvent.getOadrEvent().get(0).getEiEvent()
					.getEiEventSignals().getEiEventSignal().get(0)
					.getCurrentValue().getPayloadFloat().setValue(0);
			distributeEvent.getOadrEvent().get(0).getEiEvent()
					.getEventDescriptor().setModificationNumber(1);

			strOadrDistributeEvent = SchemaHelper
					.getDistributeEventAsString(distributeEvent);

			VtnToVenClient.post(strOadrDistributeEvent);
			RandomizedCancellationTimeoutThread randomizedTimeout=new RandomizedCancellationTimeoutThread();
			randomizedTimeout.start2andHalfMinTimer();
			
			distributeEventAction1.startCreatedEventTimeoutActionThread();
			pauseForTestCaseTimeout();

			if (!createdEventResult1.isExpectedCreatedEventReceived()) {
				randomizedTimeout.pauseIfRandomizedCancellationNotComplete();
				LogHelper
						.addTrace("Did not receive matching OadrCreatedEventType with "
								+ OptTypeType.OPT_IN
								+ " for the cancelled event.");
				LogHelper.addTrace("Test Case has Failed");
				LogHelper.setResult(LogResult.FAIL);
				
				return;
			} else {
				LogHelper.addTrace("Received matching OadrCreatedEventType with "
						+ OptTypeType.OPT_IN + " for the cancelled event.");

			}

			// Fail if there were any validation errors
			atleastOneValidationErrorPresent = TestSession
					.isAtleastOneValidationErrorPresent();
			

			UIUserPromptDualAction uiUserPromptDualAction = new UIUserPromptDualAction();
			uiUserPromptDualAction.Prompt(resourceFileReader
					.TestCase_0267_UIUserPromptDualActionQuestion(),
					uiUserPromptDualAction.SMALL);

			// If user did not close the dual action dialog
			if (!TestSession.isUserCancelledCompleteUIDualAction()) {

				randomizedTimeout.pauseIfRandomizedCancellationNotComplete();
			
				if (atleastOneValidationErrorPresent) {
					LogHelper.setResult(LogResult.FAIL);
					LogHelper.addTrace("Validation Error(s) are present");
					LogHelper.addTrace("Test Case has Failed");
					return;
				}
				
				// If user choose Yes option
				if (TestSession.isUiDualActionYesOptionClicked()) {
					LogHelper.setResult(LogResult.PASS);
					LogHelper.addTrace(resourceFileReader
							.LogMessage_VerifiedActiveEvent());
					LogHelper.addTrace("Test Case has Passed");

				} else {
					// If user choose No option
					LogHelper.setResult(LogResult.FAIL);
					LogHelper.addTrace(resourceFileReader
							.LogMessage_UnableTOVerifyActiveEvent());
					LogHelper.addTrace("Test Case has Failed");
				}
			} else {
				// If user chose dual action dialog
				LogHelper.setResult(LogResult.NA);
				
				randomizedTimeout.pauseIfRandomizedCancellationNotComplete();
				
				LogHelper
						.addTrace("TestCase execution was cancelled by the user");
			}

		} else { // No matching Created event received
			LogHelper.setResult(LogResult.FAIL);
			LogHelper
					.addTrace("Did not receive matching OadrCreatedEventType with "
							+ OptTypeType.OPT_IN);
		}

	}

	@Override
	public void cleanUp() throws Exception {
		VTNService.stopVTNService();
	}
}
