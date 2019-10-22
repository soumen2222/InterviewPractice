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
import com.qualitylogic.openadr.core.base.ErrorConst;
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
import com.qualitylogic.openadr.core.vtn.VTNService;
import com.qualitylogic.openadr.core.vtn.VtnToVenClient;

@SuppressWarnings("restriction")
public class A_E0_0310_TH_VTN_1 extends PUSHBaseTestCase {

	public static void main(String[] args) {
		try {
			A_E0_0310_TH_VTN_1 testClass = new A_E0_0310_TH_VTN_1();
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
				.setDisableResponseEvent_ResponseCodeSuccess_Check(true);

		ResourceFileReader resourceFileReader = new ResourceFileReader();
		UIUserPrompt uiUserPrompt = new UIUserPrompt();
		uiUserPrompt.Prompt(resourceFileReader.Prereq_NoEvents_Message());

		if (TestSession.isUserClickedContinuePlay()) {

			VTNService.startVTNService();

			IDistributeEventAction distributeEventAction1 = new Default_Push_CPP_DistributeEventAction();
			DistributeEventActionList
					.addDistributeEventAction(distributeEventAction1);
			ICreatedEventResult createdEventResult = new Default_CreatedEventResultOptINOptOut();
			createdEventResult.addDistributeEvent(distributeEventAction1);
			createdEventResult.setExpectedOptInCount(1);
			createdEventResult.setLastCreatedEventResult(true);

			distributeEventAction1
					.setCreatedEventSuccessCriteria(createdEventResult);

			IResponseEventAction responseEventAction = new Default_ResponseEventAction(
					ErrorConst.COMPLIANCE_ERROR_459);

			ResponseEventActionList.addResponseEventAction(responseEventAction);

			IResponseEventAction responseEventAction1 = new Default_ResponseEventAction();
			ResponseEventActionList
					.addResponseEventAction(responseEventAction1);

			OadrDistributeEventType distributeEvent = distributeEventAction1
					.getDistributeEvent();
			distributeEventAction1.setEventCompleted(true);

			JAXBContext context = JAXBContext
					.newInstance(OadrDistributeEventType.class);
			String strOadrDistributeEvent = SchemaHelper.getDistributeEventAsString(
					distributeEvent);

			VtnToVenClient.post(strOadrDistributeEvent);
			// Start Expected Created Event Timeout thread without pause.
			distributeEventAction1.startCreatedEventTimeoutActionThread();

			pauseForTestCaseTimeout();

			if (createdEventResult.isExpectedCreatedEventReceived()) {

				if (!createdEventResult.isExpectedCreatedEventReceived()) {
					LogHelper
							.addTraceAndConsole("Pausing for 30 seconds to wait for another Created Event");
					pause(30);
				} else {
					LogHelper
							.addTraceAndConsole("30 second wait is not required as another Created Event has already been received.");
				}

			}

			if (TestSession.isAtleastOneValidationErrorPresent()) {
				LogHelper.setResult(LogResult.FAIL);
				LogHelper.addTrace("Validation Error(s) are present");
				LogHelper.addTrace("Test Case has Failed");
				return;
			}

			if (createdEventResult.isExpectedCreatedEventReceived()) {
				LogHelper.setResult(LogResult.PASS);
				LogHelper.addTrace("Received matching OadrCreatedEventType with "
						+ OptTypeType.OPT_IN);
				LogHelper.addTrace("Test Case has Passed");
			} else {
				LogHelper.addTrace("No OadrCreatedEventType with "
						+ OptTypeType.OPT_IN + " has been received");
				LogHelper.addTrace("Test Case has Failed");
				LogHelper.setResult(LogResult.FAIL);

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
