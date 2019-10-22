package testcases.push.event.vtn;

import java.util.ArrayList;

import com.qualitylogic.openadr.core.action.CreatedEventActionList;
import com.qualitylogic.openadr.core.action.ICreatedEventAction;
import com.qualitylogic.openadr.core.action.impl.Default_CreatedEventActionOnLastDistributeEventReceived;
import com.qualitylogic.openadr.core.base.PUSHBaseTestCase;
import com.qualitylogic.openadr.core.common.TestSession;
import com.qualitylogic.openadr.core.common.UIUserPrompt;
import com.qualitylogic.openadr.core.signal.EventStatusEnumeratedType;
import com.qualitylogic.openadr.core.signal.OadrDistributeEventType;
import com.qualitylogic.openadr.core.signal.ResponseRequiredType;
import com.qualitylogic.openadr.core.signal.helper.DistributeEventSignalHelper;
import com.qualitylogic.openadr.core.signal.helper.LogHelper;
import com.qualitylogic.openadr.core.util.LogResult;
import com.qualitylogic.openadr.core.util.OadrUtil;
import com.qualitylogic.openadr.core.util.ResourceFileReader;
import com.qualitylogic.openadr.core.ven.VENServerResource;
import com.qualitylogic.openadr.core.ven.VENService;

public class A_E2_0657_TH_VEN_1 extends PUSHBaseTestCase {

	public static void main(String[] args) {
		try {
			A_E2_0657_TH_VEN_1 testClass = new A_E2_0657_TH_VEN_1();
			testClass.setEnableLogging(true);
			testClass.executeTestCase();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void test() throws Exception {

		ResourceFileReader resourceFileReader = new ResourceFileReader();

		ICreatedEventAction createdAction1 = new Default_CreatedEventActionOnLastDistributeEventReceived(
				true, true);
		createdAction1.setLastCreateEvent(true);
		CreatedEventActionList.addCreatedEventAction(createdAction1);

		String message2 = resourceFileReader
				.TestCase_0657_Second_UIUserPromptText();
		UIUserPrompt uiUserPrompt1 = new UIUserPrompt(message2, 1);
		createdAction1.setPromptForResponseReceipt(uiUserPrompt1);

		VENService.startVENService();
		UIUserPrompt uiUserPrompt = new UIUserPrompt();
		uiUserPrompt.Prompt(
				resourceFileReader.TestCase_0657_UIUserPromptText(), 1);

		if (!TestSession.isUserClickedContinuePlay()) {
			LogHelper.setResult(LogResult.NA);
			LogHelper.addTrace("TestCase execution was cancelled by the user");
			return;
		}

		pauseForTestCaseTimeout();

		String message3 = resourceFileReader
				.TestCase_0657_Third_UIUserPromptText();
		UIUserPrompt uiUserPrompt3 = new UIUserPrompt();
		uiUserPrompt3.Prompt(message3);


		ArrayList<OadrDistributeEventType> oadrDistributeEventList = VENServerResource
				.getOadrDistributeEventReceivedsList();

		boolean atleastOneValidationErrorPresent = TestSession
				.isAtleastOneValidationErrorPresent();
		if (atleastOneValidationErrorPresent) {

			LogHelper.setResult(LogResult.FAIL);
			LogHelper.addTrace("Validation Error(s) are present");
			LogHelper.addTrace("Test Case has Failed");
			return;
		}

		if (oadrDistributeEventList.size() < 3) {
			LogHelper.setResult(LogResult.FAIL);
			LogHelper
					.addTrace("Expected at least three DistributeEvent. i.e First DistributeEvent,Second Cancelled DistributeEvent and more retries from then");
			LogHelper.addTrace("Test Case has Failed");
			return;
		}

		
		if(!DistributeEventSignalHelper.isExpectedReceived("C",ResponseRequiredType.ALWAYS,1, VENServerResource
				.getOadrDistributeEventReceivedsList().get(oadrDistributeEventList.size() - 2).getOadrEvent().get(0))){
			LogHelper.setResult(LogResult.FAIL);
			LogHelper.addTrace("Test Case has Failed");
			return;
		}
		
		LogHelper.addTrace("Checking for retries..");
		if(!DistributeEventSignalHelper.isExpectedReceived("C",ResponseRequiredType.ALWAYS,1, VENServerResource
				.getOadrDistributeEventReceivedsList().get(oadrDistributeEventList.size() - 1).getOadrEvent().get(0))){
			LogHelper.setResult(LogResult.FAIL);
			LogHelper.addTrace("Test Case has Failed");
			return;
		}
		
		
		if (oadrDistributeEventList.get(oadrDistributeEventList.size() - 1)
				.getOadrEvent().get(0).getEiEvent().getEventDescriptor()
				.getEventStatus().equals(EventStatusEnumeratedType.CANCELLED)) {
			LogHelper.setResult(LogResult.PASS);
			LogHelper.addTrace("Test Case has Passed");

		} else {
			LogHelper.setResult(LogResult.FAIL);
			LogHelper.addTrace("Test Case has Failed");
		}
	}

	@Override
	public void cleanUp() throws Exception {
		OadrUtil.Push_VTN_Shutdown_Prompt();

		VENService.stopVENService();
	}

}
