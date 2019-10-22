package testcases.push.event.vtn;

import java.util.ArrayList;

import com.qualitylogic.openadr.core.action.CreatedEventActionList;
import com.qualitylogic.openadr.core.action.ICreatedEventAction;
import com.qualitylogic.openadr.core.action.impl.Default_CreatedEventActionOnLastDistributeEventReceived;
import com.qualitylogic.openadr.core.base.PUSHBaseTestCase;
import com.qualitylogic.openadr.core.common.TestSession;
import com.qualitylogic.openadr.core.common.UIUserPrompt;
import com.qualitylogic.openadr.core.signal.OadrDistributeEventType;
import com.qualitylogic.openadr.core.signal.OadrResponseType;
import com.qualitylogic.openadr.core.signal.ResponseRequiredType;
import com.qualitylogic.openadr.core.signal.helper.DistributeEventSignalHelper;
import com.qualitylogic.openadr.core.signal.helper.LogHelper;
import com.qualitylogic.openadr.core.util.LogResult;
import com.qualitylogic.openadr.core.util.OadrUtil;
import com.qualitylogic.openadr.core.util.ResourceFileReader;
import com.qualitylogic.openadr.core.ven.VENServerResource;
import com.qualitylogic.openadr.core.ven.VENService;

public class A_E2_0480_TH_VEN_1 extends PUSHBaseTestCase {

	public static void main(String[] args) {
		try {
			A_E2_0480_TH_VEN_1 testClass = new A_E2_0480_TH_VEN_1();
			testClass.setEnableLogging(true);
			testClass.executeTestCase();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void test() throws Exception {
		long modificationNumber2 = -1;

		ResourceFileReader resourceFileReader = new ResourceFileReader();

		ICreatedEventAction createdAction1 = new Default_CreatedEventActionOnLastDistributeEventReceived(
				true, true);
		CreatedEventActionList.addCreatedEventAction(createdAction1);

		String message2 = resourceFileReader
				.TestCase_0480_Second_UIUserPromptText();
		UIUserPrompt uiUserPrompt1 = new UIUserPrompt(message2, 1);
		createdAction1.setPromptForResponseReceipt(uiUserPrompt1);
		createdAction1.setLastCreateEvent(true);
/*
		ICreateEventAction createdAction2 = new Default_CreatedEventActionOnLastDistributeEventReceived(
				true, true);
		createdAction2.setLastCreateEvent(true);
		CreatedEventActionList.addCreatedEventAction(createdAction2);
*/
		VENService.startVENService();

		UIUserPrompt uiUserPrompt = new UIUserPrompt();
		uiUserPrompt.Prompt(
				resourceFileReader.TestCase_0480_UIUserPromptText(), 1);

		if (!TestSession.isUserClickedContinuePlay()) {
			LogHelper.setResult(LogResult.NA);
			LogHelper.addTrace("TestCase execution was cancelled by the user");
			return;
		}

		//pauseForTestCaseTimeout();
		long testCaseTimeout = OadrUtil.getTestCaseTimeout();
		while (System.currentTimeMillis() < testCaseTimeout) {
			pause(2);
			
			if(VENServerResource.getOadrDistributeEventReceivedsList().size()>1) break;
			
		}
		ArrayList<OadrDistributeEventType> distributeEventList = VENServerResource
		.getOadrDistributeEventReceivedsList();
		
		if (distributeEventList.size() == 2) {
			OadrDistributeEventType oadrDistributeEvent2 = VENServerResource
					.getOadrDistributeEventReceivedsList().get(1);
			modificationNumber2 = DistributeEventSignalHelper
					.getFirstEventModificationNumber(oadrDistributeEvent2);

			if (modificationNumber2 != -1) {
				LogHelper
						.addTrace("Received Modification OadrDistributeEvent_1 with modificationNumber "
								+ modificationNumber2);
			}

			boolean atleastOneValidationErrorPresent = TestSession
					.isAtleastOneValidationErrorPresent();
			if (atleastOneValidationErrorPresent) {

				LogHelper.setResult(LogResult.FAIL);
				LogHelper.addTrace("Validation Error(s) are present");
				LogHelper.addTrace("Test Case has Failed");
				return;
			}
			ResponseRequiredType responseReqType = null;
			if (oadrDistributeEvent2 != null) {
				responseReqType = oadrDistributeEvent2.getOadrEvent().get(0)
						.getOadrResponseRequired();
			}

			LogHelper.addTrace("---DistributeEvent 1--");
			if(!DistributeEventSignalHelper.isExpectedReceived("P",ResponseRequiredType.ALWAYS,0, VENServerResource
					.getOadrDistributeEventReceivedsList().get(0).getOadrEvent().get(0))){
				LogHelper.setResult(LogResult.FAIL);
				LogHelper.addTrace("Test Case has Failed");
				return;
			}
			
			LogHelper.addTrace("---DistributeEvent 2--");
			if(!DistributeEventSignalHelper.isExpectedReceived("P",ResponseRequiredType.NEVER,1, VENServerResource
					.getOadrDistributeEventReceivedsList().get(1).getOadrEvent().get(0))){
				LogHelper.setResult(LogResult.FAIL);
				LogHelper.addTrace("Test Case has Failed");
				return;
			}
			
			if (responseReqType != null
					&& responseReqType.equals(ResponseRequiredType.NEVER)) {
				LogHelper.setResult(LogResult.PASS);
				LogHelper.addTrace("Test Case has Passed");
			} else {
				LogHelper.setResult(LogResult.FAIL);
				LogHelper
						.addTrace("OadrDisributeEvent not sent with oadrResponseRequired set to never");
				LogHelper.addTrace("Test Case has Failed");
			}
		} else {
			
			
			LogHelper.addTrace("Expected two OadrDistributeEvents received "+distributeEventList.size() );
			
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
