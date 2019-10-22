package testcases.push.event.ven;

import java.util.ArrayList;

import com.qualitylogic.openadr.core.action.DistributeEventActionList;
import com.qualitylogic.openadr.core.action.IDistributeEventAction;
import com.qualitylogic.openadr.core.action.IResponseEventAction;
import com.qualitylogic.openadr.core.action.ResponseEventActionList;
import com.qualitylogic.openadr.core.action.impl.Default_Push_CPP_DistributeEventAction;
import com.qualitylogic.openadr.core.action.impl.Default_ResponseEventAction;
import com.qualitylogic.openadr.core.base.PUSHBaseTestCase;
import com.qualitylogic.openadr.core.common.TestSession;
import com.qualitylogic.openadr.core.common.UIUserPrompt;
import com.qualitylogic.openadr.core.signal.OadrCreatedEventType;
import com.qualitylogic.openadr.core.signal.OadrDistributeEventType;
import com.qualitylogic.openadr.core.signal.helper.LogHelper;
import com.qualitylogic.openadr.core.signal.helper.SchemaHelper;
import com.qualitylogic.openadr.core.util.ConformanceRuleValidator;
import com.qualitylogic.openadr.core.util.LogResult;
import com.qualitylogic.openadr.core.util.PropertiesFileReader;
import com.qualitylogic.openadr.core.util.ResourceFileReader;
import com.qualitylogic.openadr.core.vtn.VTNService;
import com.qualitylogic.openadr.core.vtn.VtnToVenClient;

public class A_E0_0390_TH_VTN_1 extends PUSHBaseTestCase {

	public static void main(String[] args) {
		try {
			A_E0_0390_TH_VTN_1 testClass = new A_E0_0390_TH_VTN_1();
			testClass.setEnableLogging(true);
			testClass.executeTestCase();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void test() throws Exception {

		ConformanceRuleValidator
				.setDisable_VtnIDValid_Check(true);
		ConformanceRuleValidator
				.setDisableCreatedEvent_ResponseCodeSuccess_Check(true);
		ResourceFileReader resourceFileReader = new ResourceFileReader();
		UIUserPrompt uiUserPrompt = new UIUserPrompt();
		uiUserPrompt.Prompt(resourceFileReader.Prereq_NoEvents_Message());

		if (TestSession.isUserClickedContinuePlay()) {
			VTNService.startVTNService();

			IDistributeEventAction distributeEventAction1 = new Default_Push_CPP_DistributeEventAction();
			DistributeEventActionList
					.addDistributeEventAction(distributeEventAction1);

			IResponseEventAction responseEventAction = new Default_ResponseEventAction();
			ResponseEventActionList.addResponseEventAction(responseEventAction);

			OadrDistributeEventType distributeEvent = distributeEventAction1
					.getDistributeEvent();
			distributeEvent.setVtnID("Unknown_VTN_ID");
			distributeEventAction1.setEventCompleted(true);

			String strOadrDistributeEvent = SchemaHelper
					.getDistributeEventAsString(distributeEvent);

			VtnToVenClient.post(strOadrDistributeEvent);

			PropertiesFileReader propertiesFileReader = new PropertiesFileReader();

			long totalDurationInput = Long.valueOf(propertiesFileReader
					.get("asyncResponseTimeout"));

			long testCaseTimeout = System.currentTimeMillis()
					+ totalDurationInput;

			ArrayList<OadrCreatedEventType> oadrCreatedEventReceived = TestSession
					.getCreatedEventReceivedList();
			int size = 0;
			while (System.currentTimeMillis() < testCaseTimeout) {
				pause(5);

				size = TestSession.getCreatedEventReceivedList().size();

				if (size > 0)
					break;

			}

			boolean atleastOneValidationErrorPresent = TestSession
					.isAtleastOneValidationErrorPresent();
			if (atleastOneValidationErrorPresent) {
				LogHelper.setResult(LogResult.FAIL);
				LogHelper.addTrace("Validation Error(s) are present");
				LogHelper.addTrace("Test Case has Failed");
				return;
			}

			pause(10);
			size = TestSession.getCreatedEventReceivedList().size();
			if (size == 0) {
				LogHelper.addTrace("No OadrCreatedEventType has been received");
				LogHelper.addTrace("Test Case has Failed");
				LogHelper.setResult(LogResult.FAIL);
				return;
			}

			OadrCreatedEventType createdEvent = oadrCreatedEventReceived.get(0);
			String createdEventResponseCode = createdEvent.getEiCreatedEvent()
					.getEiResponse().getResponseCode();

			if (TestSession.isAtleastOneValidationErrorPresent()) {
				LogHelper.setResult(LogResult.FAIL);
				LogHelper.addTrace("Validation Error(s) are present");
				LogHelper.addTrace("Test Case has Failed");
				return;
			}

			if (createdEventResponseCode.startsWith("4")) {
				LogHelper.setResult(LogResult.PASS);
				LogHelper
						.addTrace("Received expected OadrCreatedEventType with error response code "
								+ createdEventResponseCode);
				LogHelper.addTrace("Test Case has Passed");
			} else {
				LogHelper
						.addTrace("Received OadrCreatedEventType with response code "
								+ createdEventResponseCode);
				LogHelper
						.addTrace("OadrCreatedEventType received did not indicate error in the response code");
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
