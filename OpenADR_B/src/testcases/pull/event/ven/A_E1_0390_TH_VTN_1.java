package testcases.pull.event.ven;

import java.util.ArrayList;

import com.qualitylogic.openadr.core.action.IDistributeEventAction;
import com.qualitylogic.openadr.core.action.IResponseEventAction;
import com.qualitylogic.openadr.core.action.ResponseEventActionList;
import com.qualitylogic.openadr.core.action.impl.E1_0390_Pull_CPP_DistributeEventAction;
import com.qualitylogic.openadr.core.action.impl.OptionalElements_ResponseEventAction;
import com.qualitylogic.openadr.core.base.PULLBaseTestCase;
import com.qualitylogic.openadr.core.common.TestSession;
import com.qualitylogic.openadr.core.common.UIUserPrompt;
import com.qualitylogic.openadr.core.oadrpoll.OadrPollQueue;
import com.qualitylogic.openadr.core.signal.OadrCreatedEventType;
import com.qualitylogic.openadr.core.signal.helper.LogHelper;
import com.qualitylogic.openadr.core.util.ConformanceRuleValidator;
import com.qualitylogic.openadr.core.util.LogResult;
import com.qualitylogic.openadr.core.util.PropertiesFileReader;
import com.qualitylogic.openadr.core.util.ResourceFileReader;
import com.qualitylogic.openadr.core.util.Trace;
import com.qualitylogic.openadr.core.vtn.VTNService;

public class A_E1_0390_TH_VTN_1 extends PULLBaseTestCase {

	public static void main(String[] args) {
		try {
			A_E1_0390_TH_VTN_1 testClass = new A_E1_0390_TH_VTN_1();
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
		ConformanceRuleValidator
		.setDisableRequestEvent_isAllPrevCreatedEvntsRecvd_Check(true);
		// Prompt to make sure events are cleared from DUT
		ResourceFileReader resourceFileReader = new ResourceFileReader();
		UIUserPrompt uiUserPrompt = new UIUserPrompt();
		uiUserPrompt.Prompt(resourceFileReader.Prereq_NoEvents_Message());

		if (TestSession.isUserClickedContinuePlay()) {

			IDistributeEventAction distributeEventAction1 = new E1_0390_Pull_CPP_DistributeEventAction();
			OadrPollQueue.addToQueue(distributeEventAction1); 

			IResponseEventAction responseEventAction = new OptionalElements_ResponseEventAction();
			ResponseEventActionList.addResponseEventAction(responseEventAction);

			VTNService.startVTNService();

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

			PropertiesFileReader propertiesFileReader = new PropertiesFileReader();

			long totalDurationInput = Long.valueOf(propertiesFileReader
					.get("asyncResponseTimeout"));

			long testCaseTimeout = System.currentTimeMillis()
					+ totalDurationInput;

			ArrayList<OadrCreatedEventType> oadrCreatedEventReceived = TestSession
					.getCreatedEventReceivedList();
			int size = 0;
			while (System.currentTimeMillis() < testCaseTimeout) {
				pause(10);

				size = TestSession.getCreatedEventReceivedList().size();

				if (size > 0)
					break;

			}
			pause(20);
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
		} else { 
			LogHelper.setResult(LogResult.NA);
			LogHelper.addTrace("TestCase execution was cancelled by the user");
		}
	}

	@Override
	public void cleanUp() throws Exception {
		VTNService.stopVTNService();
	}
}
