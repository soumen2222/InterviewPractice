package testcases.push.event.ven;

import javax.xml.bind.JAXBContext;

import com.qualitylogic.openadr.core.action.DistributeEventActionList;
import com.qualitylogic.openadr.core.action.ICreatedEventResult;
import com.qualitylogic.openadr.core.action.IDistributeEventAction;
import com.qualitylogic.openadr.core.action.IResponseEventAction;
import com.qualitylogic.openadr.core.action.ResponseEventActionList;
import com.qualitylogic.openadr.core.action.impl.Default_CreatedEventResultOptINOptOut;
import com.qualitylogic.openadr.core.action.impl.Default_ResponseEventAction;
import com.qualitylogic.openadr.core.action.impl.E0_0200_DistributeEventAction_Limits;
import com.qualitylogic.openadr.core.base.PUSHBaseTestCase;
import com.qualitylogic.openadr.core.common.TestSession;
import com.qualitylogic.openadr.core.common.UIUserPrompt;
import com.qualitylogic.openadr.core.signal.OadrDistributeEventType;
import com.qualitylogic.openadr.core.signal.OptTypeType;
import com.qualitylogic.openadr.core.signal.helper.LogHelper;
import com.qualitylogic.openadr.core.signal.helper.SchemaHelper;
import com.qualitylogic.openadr.core.util.ConformanceRuleValidator;
import com.qualitylogic.openadr.core.util.LogResult;
import com.qualitylogic.openadr.core.util.ResourceFileReader;
import com.qualitylogic.openadr.core.util.Trace;
import com.qualitylogic.openadr.core.vtn.VTNService;
import com.qualitylogic.openadr.core.vtn.VtnToVenClient;

@SuppressWarnings("restriction")
public class A_E0_0200_TH_VTN_1 extends PUSHBaseTestCase {

	public static void main(String[] args) {
		try {
			A_E0_0200_TH_VTN_1 testClass = new A_E0_0200_TH_VTN_1();
			testClass.setEnableLogging(true);
			testClass.executeTestCase();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void test() throws Exception {
		// Disable validation Check
		ConformanceRuleValidator
				.setDisable_MaketContext_Check(true);
		ConformanceRuleValidator
				.setDisable_VtnIDValid_Check(true);

		// Prompt to make sure events are cleared from DUT
		ResourceFileReader resourceFileReader = new ResourceFileReader();
		UIUserPrompt uiUserPrompt = new UIUserPrompt();
		uiUserPrompt.Prompt(resourceFileReader.Prereq_NoEvents_Message()
				+ "\n\n" + resourceFileReader.TestCase_0200_UIUserPromptText(),
				uiUserPrompt.MEDIUM);

		if (TestSession.isUserClickedContinuePlay()) {

			IDistributeEventAction distributeEventAction1 = new E0_0200_DistributeEventAction_Limits();
			DistributeEventActionList
					.addDistributeEventAction(distributeEventAction1);

			ICreatedEventResult createdEventResult1 = new Default_CreatedEventResultOptINOptOut();
			createdEventResult1.setExpectedOptInCount(4);
			createdEventResult1.addDistributeEvent(distributeEventAction1);
			distributeEventAction1
					.setCreatedEventSuccessCriteria(createdEventResult1);
			createdEventResult1.setLastCreatedEventResult(true);
			distributeEventAction1.setEventCompleted(true);
			// Upto 4 CreatedEvents can be received.
			IResponseEventAction responseEventAction1 = new Default_ResponseEventAction();
			IResponseEventAction responseEventAction2 = new Default_ResponseEventAction();
			IResponseEventAction responseEventAction3 = new Default_ResponseEventAction();
			IResponseEventAction responseEventAction4 = new Default_ResponseEventAction();

			ResponseEventActionList
					.addResponseEventAction(responseEventAction1);
			ResponseEventActionList
					.addResponseEventAction(responseEventAction2);
			ResponseEventActionList
					.addResponseEventAction(responseEventAction3);
			ResponseEventActionList
					.addResponseEventAction(responseEventAction4);

			VTNService.startVTNService();

			OadrDistributeEventType distributeEvent = distributeEventAction1
					.getDistributeEvent();

			JAXBContext context = JAXBContext
					.newInstance(OadrDistributeEventType.class);
			String strOadrDistributeEvent = SchemaHelper.getDistributeEventAsString(
					distributeEvent);

			VtnToVenClient.post(strOadrDistributeEvent);
			distributeEventAction1.startCreatedEventTimeoutActionThreadWithPause();
			pauseForTestCaseTimeout();

			boolean atleastOneValidationErrorPresent = TestSession
					.isAtleastOneValidationErrorPresent();


			Trace trace = TestSession.getTraceObj();
			if (atleastOneValidationErrorPresent && trace != null) {
				trace.setResult("Fail");
				trace.getLogFileContentTrace().append(
						"\nValidation Error(s) are present \n");
				trace.getLogFileContentTrace().append(
						"\nTest Case has Failed \n");
				return;
			}

			if (trace != null) {

				if (createdEventResult1.isExpectedCreatedEventReceived()) {
					LogHelper.setResult(LogResult.PASS);
					LogHelper
							.addTrace("Received matching OadrCreatedEventType with "
									+ OptTypeType.OPT_IN);
					LogHelper.addTrace("Test Case has Passed");
				} else {
					LogHelper.setResult(LogResult.FAIL);
					LogHelper
							.addTrace("Did not receive expected matching OadrCreatedEventType with "
									+ OptTypeType.OPT_IN);
					LogHelper.addTrace("Test Case has Failed");

				}
			}
		} else { // User closed the action dialog
			LogHelper.setResult(LogResult.NA);
			LogHelper.addTrace("TestCase execution was cancelled by the user");
		}
	}

	@Override
	public void cleanUp() throws Exception {
		VTNService.stopVTNService();
	}
}