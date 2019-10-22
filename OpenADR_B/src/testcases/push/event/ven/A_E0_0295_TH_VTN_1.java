package testcases.push.event.ven;

import java.util.List;

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
import com.qualitylogic.openadr.core.signal.IntervalType;
import com.qualitylogic.openadr.core.signal.OadrDistributeEventType;
import com.qualitylogic.openadr.core.signal.OptTypeType;
import com.qualitylogic.openadr.core.signal.helper.DistributeEventSignalHelper;
import com.qualitylogic.openadr.core.signal.helper.LogHelper;
import com.qualitylogic.openadr.core.signal.helper.SchemaHelper;
import com.qualitylogic.openadr.core.util.ConformanceRuleValidator;
import com.qualitylogic.openadr.core.util.LogResult;
import com.qualitylogic.openadr.core.util.PropertiesFileReader;
import com.qualitylogic.openadr.core.util.ResourceFileReader;
import com.qualitylogic.openadr.core.vtn.VTNService;
import com.qualitylogic.openadr.core.vtn.VtnToVenClient;

public class A_E0_0295_TH_VTN_1 extends PUSHBaseTestCase {

	public static void main(String[] args) {
		try {
			A_E0_0295_TH_VTN_1 testClass = new A_E0_0295_TH_VTN_1();
			testClass.setEnableLogging(true);
			testClass.executeTestCase();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void test() throws Exception {
		ConformanceRuleValidator
				.setDisableDistributeEvent_AllPreviousEvntPresent_Check(true);
		PropertiesFileReader propertiesFileReader = new PropertiesFileReader();
		ResourceFileReader resourceFileReader = new ResourceFileReader();
		UIUserPrompt uiUserPrompt = new UIUserPrompt();
		uiUserPrompt.Prompt(resourceFileReader.Prereq_NoEvents_Message()
				+ "\n\n" + resourceFileReader.TestCase_0295_UIUserPromptText(),
				1);

		if (TestSession.isUserClickedContinuePlay()) {
			VTNService.startVTNService();

			IDistributeEventAction distributeEventAction1 = new Default_Push_CPP_DistributeEventAction();
			DistributeEventActionList
					.addDistributeEventAction(distributeEventAction1);

			OadrDistributeEventType distributeEvent1 = distributeEventAction1
					.getDistributeEvent();
			DistributeEventSignalHelper
					.setNewReqIDEvntIDStartTimeAndMarketCtx1(distributeEvent1,
							1);
			distributeEvent1.setVtnID(propertiesFileReader.get("VTN_ID"));
			List<IntervalType> intervals = distributeEvent1.getOadrEvent().get(0)
					.getEiEvent().getEiEventSignals().getEiEventSignal().get(0)
					.getIntervals().getInterval();
			intervals.remove(2);

			IntervalType interval2 = intervals.get(1);

			interval2.getDuration().setDuration("PT2M");

			ICreatedEventResult createdEventResult1 = new Default_CreatedEventResultOptINOptOut();
			createdEventResult1.addDistributeEvent(distributeEventAction1);
			createdEventResult1.setExpectedOptInCount(1);

			distributeEventAction1
					.setCreatedEventSuccessCriteria(createdEventResult1);

			IResponseEventAction responseEventAction1 = new Default_ResponseEventAction();
			ResponseEventActionList
					.addResponseEventAction(responseEventAction1);

			String strOadrDistributeEvent1 = SchemaHelper
					.getDistributeEventAsString(distributeEvent1);

			distributeEventAction1.setEventCompleted(true);
			VtnToVenClient.post(strOadrDistributeEvent1);


			// Start timeout monitor thread and pause for Expected Created Event Timeout
			distributeEventAction1
					.startCreatedEventTimeoutActionThreadWithPause();

			if (!createdEventResult1.isExpectedCreatedEventReceived()) {

				LogHelper.addTrace("No OadrCreatedEventType with "
						+ OptTypeType.OPT_IN + " has been received for "
						+ propertiesFileReader.get("VTN_ID"));
				LogHelper.addTrace("Test Case has Failed");
				LogHelper.setResult(LogResult.FAIL);

				return;
			} else {
				LogHelper.addTrace("Received matching OadrCreatedEventType with "
						+ OptTypeType.OPT_IN + " for "
						+ propertiesFileReader.get("VTN_ID"));

			}

			// Pause for 5 seconds
			pause(5);

			IDistributeEventAction distributeEventAction2 = new Default_Push_CPP_DistributeEventAction();
			DistributeEventActionList
					.addDistributeEventAction(distributeEventAction1);

			OadrDistributeEventType distributeEvent2 = distributeEventAction2
					.getDistributeEvent();
			DistributeEventSignalHelper
					.setNewReqIDEvntIDStartTimeAndMarketCtx1(distributeEvent2,
							1);
			distributeEvent2.setVtnID(propertiesFileReader.get("VTN_ID_2"));

			List<IntervalType> intervals2 = distributeEvent2.getOadrEvent().get(0)
					.getEiEvent().getEiEventSignals().getEiEventSignal().get(0)
					.getIntervals().getInterval();
			intervals2.remove(2);

			IntervalType interval2a = intervals2.get(1);

			interval2a.getDuration().setDuration("PT2M");

			DistributeEventActionList
					.addDistributeEventAction(distributeEventAction2);

			ICreatedEventResult createdEventResult2 = new Default_CreatedEventResultOptINOptOut();
			createdEventResult2.addDistributeEvent(distributeEventAction2);
			createdEventResult2.setExpectedOptInCount(1);
			createdEventResult2.setLastCreatedEventResult(true);
			distributeEventAction2.setEventCompleted(false);

			distributeEventAction2
					.setCreatedEventSuccessCriteria(createdEventResult2);

			IResponseEventAction responseEventAction2 = new Default_ResponseEventAction();
			ResponseEventActionList
					.addResponseEventAction(responseEventAction2);

			String strOadrDistributeEvent2 = SchemaHelper
					.getDistributeEventAsString(distributeEvent2);

			distributeEventAction2.setEventCompleted(true);
			VtnToVenClient.post(strOadrDistributeEvent2);

			// Start timeout monitor thread without pause
			distributeEventAction2.startCreatedEventTimeoutActionThread();

			pauseForTestCaseTimeout();

			if (TestSession.isAtleastOneValidationErrorPresent()) {
				LogHelper.setResult(LogResult.FAIL);
				LogHelper.addTrace("Validation Error(s) are present");
				LogHelper.addTrace("Test Case has Failed");
				return;
			}

			if (createdEventResult2.isExpectedCreatedEventReceived()) {
				LogHelper.setResult(LogResult.PASS);
				LogHelper.addTrace("Received matching OadrCreatedEventType with "
						+ OptTypeType.OPT_IN + " for "
						+ propertiesFileReader.get("VTN_ID_2"));
				LogHelper.addTrace("Test Case has Passed");
			} else {
				LogHelper.addTrace("No OadrCreatedEventType with "
						+ OptTypeType.OPT_IN + " has been received for "
						+ propertiesFileReader.get("VTN_ID_2"));
				LogHelper.addTrace("Test Case has Failed");
				LogHelper.setResult(LogResult.FAIL);

			}
		} else { // User closed the single action dialog
			LogHelper.setResult(LogResult.NA);
			LogHelper.addTrace("TestCase execution was cancelled by the user");
		}

	}

	@Override
	public void cleanUp() throws Exception {
		VTNService.stopVTNService();
	}
}
