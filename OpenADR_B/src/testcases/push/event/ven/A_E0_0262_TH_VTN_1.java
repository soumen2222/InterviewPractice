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
import com.qualitylogic.openadr.core.action.impl.E0_0260_DistributeEventAction_2;
import com.qualitylogic.openadr.core.base.PUSHBaseTestCase;
import com.qualitylogic.openadr.core.common.TestSession;
import com.qualitylogic.openadr.core.common.UIUserPrompt;
import com.qualitylogic.openadr.core.common.UIUserPromptDualAction;
import com.qualitylogic.openadr.core.signal.IntervalType;
import com.qualitylogic.openadr.core.signal.Intervals;
import com.qualitylogic.openadr.core.signal.OadrDistributeEventType;
import com.qualitylogic.openadr.core.signal.OadrDistributeEventType.OadrEvent;
import com.qualitylogic.openadr.core.signal.OptTypeType;
import com.qualitylogic.openadr.core.signal.helper.DistributeEventSignalHelper;
import com.qualitylogic.openadr.core.signal.helper.LogHelper;
import com.qualitylogic.openadr.core.signal.helper.SchemaHelper;
import com.qualitylogic.openadr.core.signal.xcal.Properties.Tolerance;
import com.qualitylogic.openadr.core.signal.xcal.Properties.Tolerance.Tolerate;
import com.qualitylogic.openadr.core.util.LogResult;
import com.qualitylogic.openadr.core.util.ResourceFileReader;
import com.qualitylogic.openadr.core.vtn.RandomizedCancellationTimeoutThread;
import com.qualitylogic.openadr.core.vtn.VTNService;
import com.qualitylogic.openadr.core.vtn.VtnToVenClient;

@SuppressWarnings("restriction")
public class A_E0_0262_TH_VTN_1 extends PUSHBaseTestCase {

	public static void main(String[] args) {
		try {
			A_E0_0262_TH_VTN_1 testClass = new A_E0_0262_TH_VTN_1();
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
				+ "\n\n" + resourceFileReader.TestCase_0262_UIUserPromptText()+ "\n\n" + resourceFileReader.TestCase_RandomizedCancellationFailWarningPrompt(),
				2);

		if (TestSession.isUserClickedContinuePlay()) {
			IDistributeEventAction distributeEventAction1 = new Default_Push_CPP_DistributeEventAction();
			OadrDistributeEventType distributeEvent = distributeEventAction1
					.getDistributeEvent();
			DistributeEventSignalHelper
					.setNewReqIDEvntIDStartTimeAndMarketCtx1(distributeEvent,
							30);

			OadrEvent oadrEvent = distributeEvent.getOadrEvent().get(0);
			Intervals intervals = oadrEvent.getEiEvent().getEiEventSignals()
					.getEiEventSignal().get(0).getIntervals();

			List<IntervalType> intervalList = intervals.getInterval();
			intervalList.remove(2);
			IntervalType interval1 = intervalList.get(0);
			interval1.getDuration().setDuration("PT1M");
			IntervalType interval2 = intervalList.get(1);
			interval2.getDuration().setDuration("PT2M");

			Tolerance tolerance = new Tolerance();

			Tolerate tolerate = new Tolerate();
			tolerate.setStartafter("PT3M");

			tolerance.setTolerate(tolerate);
			oadrEvent.getEiEvent().getEiActivePeriod().getProperties()
					.setTolerance(tolerance);

			ICreatedEventResult createdEventResult1 = new Default_CreatedEventResultOptINOptOut();
			createdEventResult1.setExpectedOptInCount(1);
			createdEventResult1.addDistributeEvent(distributeEventAction1);
			createdEventResult1.setLastCreatedEventResult(true);
			distributeEventAction1
					.setCreatedEventSuccessCriteria(createdEventResult1);

			DistributeEventActionList
					.addDistributeEventAction(distributeEventAction1);

			IResponseEventAction responseEventAction1 = new Default_ResponseEventAction();
			ResponseEventActionList
					.addResponseEventAction(responseEventAction1);
			distributeEventAction1.setEventCompleted(true);
			JAXBContext context = JAXBContext
					.newInstance(OadrDistributeEventType.class);
			String strOadrDistributeEvent = SchemaHelper.getDistributeEventAsString(
					distributeEventAction1.getDistributeEvent());

			VTNService.startVTNService();

			VtnToVenClient.post(strOadrDistributeEvent);
			pause(5);

			distributeEventAction1
					.startCreatedEventTimeoutActionThreadWithPause();
			pause(5);

			boolean atleastOneValidationErrorPresent = TestSession
					.isAtleastOneValidationErrorPresent();
			if (atleastOneValidationErrorPresent) {
				LogHelper.setResult(LogResult.FAIL);
				LogHelper.addTrace("Validation Error(s) are present");
				LogHelper.addTrace("Test Case has Failed");
				return;
			}

			if (!createdEventResult1.isExpectedCreatedEventReceived()) {
				LogHelper.addTrace("No OadrCreatedEventType with "
						+ OptTypeType.OPT_IN
						+ " has been received for the prerequisite");
				LogHelper.addTrace("Test Case has Failed");
				LogHelper.setResult(LogResult.FAIL);
				return;
			} else {

				// Fail if there were any validation errors
				atleastOneValidationErrorPresent = TestSession
						.isAtleastOneValidationErrorPresent();
				if (atleastOneValidationErrorPresent) {
					LogHelper.setResult(LogResult.FAIL);
					LogHelper.addTrace("Validation Error(s) are present");
					LogHelper.addTrace("Test Case has Failed");
					return;
				}


				IDistributeEventAction distributeEventAction2 = new E0_0260_DistributeEventAction_2();
				DistributeEventActionList
						.addDistributeEventAction(distributeEventAction2);
				ICreatedEventResult createdEventResult2 = new Default_CreatedEventResultOptINOptOut();
				createdEventResult2.setExpectedOptInCount(1);
				createdEventResult2.addDistributeEvent(distributeEventAction2);
				distributeEventAction2
						.setCreatedEventSuccessCriteria(createdEventResult2);
				createdEventResult2.setLastCreatedEventResult(true);
				distributeEventAction2.setEventCompleted(true);
				IResponseEventAction responseEventAction2 = new Default_ResponseEventAction();
				ResponseEventActionList
						.addResponseEventAction(responseEventAction2);

				strOadrDistributeEvent = SchemaHelper.getDistributeEventAsString( 
						distributeEventAction2.getDistributeEvent());

				VtnToVenClient.post(strOadrDistributeEvent);
				pause(5);
				distributeEventAction2
						.startCreatedEventTimeoutActionThreadWithPause();
				pause(5);
				// Fail if there were any validation errors
				if (atleastOneValidationErrorPresent) {
					LogHelper.setResult(LogResult.FAIL);
					LogHelper.addTrace("Validation Error(s) are present");
					LogHelper.addTrace("Test Case has Failed");
					return;
				}

				if (createdEventResult2.isExpectedCreatedEventReceived()) {
					LogHelper
							.addTrace("Received matching OadrCreatedEventType with "
									+ OptTypeType.OPT_IN);

					String uiUserPromptDualActionQuestion = resourceFileReader
							.TestCase_0262_UIUserPromptDualActionQuestion();
					UIUserPromptDualAction uiUserPromptDualAction = new UIUserPromptDualAction();
					uiUserPromptDualAction
							.Prompt(uiUserPromptDualActionQuestion);

					if (!TestSession.isUserCancelledCompleteUIDualAction()) {

						// If user choose Yes option
						if (TestSession.isUiDualActionYesOptionClicked()) {
							LogHelper.setResult(LogResult.PASS);
							LogHelper.addTrace(resourceFileReader
									.LogMessage_VerifiedActiveEvent());
							LogHelper.addTrace("Test Case has Passed");
							return;
						} else {
							// If user choose No option
							LogHelper.setResult(LogResult.FAIL);
							LogHelper.addTrace(resourceFileReader
									.LogMessage_UnableTOVerifyActiveEvent());
							LogHelper.addTrace("Test Case has Failed");
							return;
						}
					} else {
						// User closed the action dialog
						LogHelper.setResult(LogResult.NA);
						LogHelper
								.addTrace("TestCase execution was cancelled by the user");
						return;
					}

				} else {
					LogHelper.addTrace("No matching OadrCreatedEventType with "
							+ OptTypeType.OPT_IN + " or " + OptTypeType.OPT_OUT
							+ " has been received");
					LogHelper.addTrace("Test Case has Failed");
					LogHelper.setResult(LogResult.FAIL);
					return;
				}
			}

		} else { // User closed the action dialog
			LogHelper.setResult(LogResult.NA);
			LogHelper.addTrace("TestCase execution was cancelled by the user");
		}

	}

	@Override
	public void cleanUp() throws Exception {
		RandomizedCancellationTimeoutThread randomizedTimeout=new RandomizedCancellationTimeoutThread();
		randomizedTimeout.start3MinTimerWithPause();

		VTNService.stopVTNService();
	}
}
