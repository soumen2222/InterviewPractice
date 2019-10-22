package testcases.push.event.ven;

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
import com.qualitylogic.openadr.core.signal.OadrCreatedEventType;
import com.qualitylogic.openadr.core.signal.OadrDistributeEventType;
import com.qualitylogic.openadr.core.signal.helper.LogHelper;
import com.qualitylogic.openadr.core.signal.helper.SchemaHelper;
import com.qualitylogic.openadr.core.util.ConformanceRuleValidator;
import com.qualitylogic.openadr.core.util.LogResult;
import com.qualitylogic.openadr.core.util.ResourceFileReader;
import com.qualitylogic.openadr.core.util.Trace;
import com.qualitylogic.openadr.core.vtn.VTNService;
import com.qualitylogic.openadr.core.vtn.VtnToVenClient;

@SuppressWarnings("restriction")
public class A_E0_0325_TH_VTN_1 extends PUSHBaseTestCase {

	public static void main(String[] args) {
		try {
			A_E0_0325_TH_VTN_1 testClass = new A_E0_0325_TH_VTN_1();
			testClass.setEnableLogging(true);
			testClass.executeTestCase();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void test() throws Exception {
		// Disable validation Check
		// Disable validation Check
		ConformanceRuleValidator
				.setDisable_MaketContext_Check(true);
		ConformanceRuleValidator
				.setDisableCreatedEvent_ResponseCodeSuccess_Check(true);

		// Prompt to make sure events are cleared from DUT
		ResourceFileReader resourceFileReader = new ResourceFileReader();
		UIUserPrompt uiUserPrompt = new UIUserPrompt();
		uiUserPrompt.Prompt(resourceFileReader.Prereq_NoEvents_Message());

		if (TestSession.isUserClickedContinuePlay()) {

			IDistributeEventAction distributeEventAction1 = new Default_Push_CPP_DistributeEventAction();
			distributeEventAction1.getDistributeEvent().getOadrEvent().get(0)
					.getEiEvent().getEventDescriptor().getEiMarketContext()
					.setMarketContext("http://nonMatchingContext");
			DistributeEventActionList
					.addDistributeEventAction(distributeEventAction1);
			distributeEventAction1.setEventCompleted(true);
			ICreatedEventResult createdEventResult1 = new Default_CreatedEventResultOptINOptOut();
			createdEventResult1.setExpectedOptOutCount(1);
			createdEventResult1.addDistributeEvent(distributeEventAction1);
			distributeEventAction1
					.setCreatedEventSuccessCriteria(createdEventResult1);
			createdEventResult1.setLastCreatedEventResult(true);

			IResponseEventAction responseEventAction = new Default_ResponseEventAction();
			responseEventAction.setlastEvent(true);
			ResponseEventActionList.addResponseEventAction(responseEventAction);

			VTNService.startVTNService();

			JAXBContext context = JAXBContext
					.newInstance(OadrDistributeEventType.class);
			String strOadrDistributeEvent = SchemaHelper.getDistributeEventAsString(
					distributeEventAction1.getDistributeEvent());

			VtnToVenClient.post(strOadrDistributeEvent);

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

			// See if expected error is returned from DUT
			boolean isExpectedError = false;
			if (!TestSession.getCreatedEventReceivedList().isEmpty()) {
				OadrCreatedEventType oadrCreatedEvent = TestSession
						.getCreatedEventReceivedList().get(0);
				String responseCode = oadrCreatedEvent.getEiCreatedEvent()
						.getEventResponses().getEventResponse().get(0)
						.getResponseCode();
				isExpectedError = responseCode.startsWith("4");
			}

			if (trace != null) {

				if (createdEventResult1.isExpectedCreatedEventReceived()
						&& isExpectedError) {
					LogHelper.setResult(LogResult.PASS);
					LogHelper
							.addTrace("Received eventResponse 4xx error with optOut from oadrCreatedEvent");
					LogHelper.addTrace("Test Case has Passed");
				} else {
					LogHelper.setResult(LogResult.FAIL);
					LogHelper
							.addTrace("Did not receive eventResponse 4xx error with optOut from oadrCreatedEvent");
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
