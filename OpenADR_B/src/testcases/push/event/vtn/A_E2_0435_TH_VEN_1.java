package testcases.push.event.vtn;

import java.util.ArrayList;
import java.util.List;

import com.qualitylogic.openadr.core.action.CreatedEventActionList;
import com.qualitylogic.openadr.core.action.ICreatedEventAction;
import com.qualitylogic.openadr.core.action.impl.Default_CreatedEventActionOnLastDistributeEventReceived;
import com.qualitylogic.openadr.core.base.PUSHBaseTestCase;
import com.qualitylogic.openadr.core.common.TestSession;
import com.qualitylogic.openadr.core.common.UIUserPrompt;
import com.qualitylogic.openadr.core.signal.EiTargetType;
import com.qualitylogic.openadr.core.signal.OadrDistributeEventType;
import com.qualitylogic.openadr.core.signal.OadrDistributeEventType.OadrEvent;
import com.qualitylogic.openadr.core.signal.OadrResponseType;
import com.qualitylogic.openadr.core.signal.ResponseRequiredType;
import com.qualitylogic.openadr.core.signal.helper.DistributeEventConformanceValidationHelper;
import com.qualitylogic.openadr.core.signal.helper.DistributeEventSignalHelper;
import com.qualitylogic.openadr.core.signal.helper.LogHelper;
import com.qualitylogic.openadr.core.signal.helper.ResponseHelper;
import com.qualitylogic.openadr.core.util.ConformanceRuleValidator;
import com.qualitylogic.openadr.core.util.LogResult;
import com.qualitylogic.openadr.core.util.OadrUtil;
import com.qualitylogic.openadr.core.util.ResourceFileReader;
import com.qualitylogic.openadr.core.ven.VENServerResource;
import com.qualitylogic.openadr.core.ven.VENService;

public class A_E2_0435_TH_VEN_1 extends PUSHBaseTestCase {

	public static void main(String[] args) {
		try {
			A_E2_0435_TH_VEN_1 testClass = new A_E2_0435_TH_VEN_1();
			testClass.setEnableLogging(true);
			testClass.executeTestCase();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void test() throws Exception {

		ConformanceRuleValidator
				.setDisable_AtLeastOneEiTargetMatch_ValidCheck(true);
		// Create a list of CreatedEventAction
		ICreatedEventAction createdAction1 = new Default_CreatedEventActionOnLastDistributeEventReceived(
				true, true, "Response Description");
		createdAction1.setLastCreateEvent(true);
		CreatedEventActionList.addCreatedEventAction(createdAction1);

		VENService.startVENService();

		new UIUserPrompt().Prompt(
				new ResourceFileReader().TestCase_0435_UIUserPromptText(), 1);

		if (!TestSession.isUserClickedContinuePlay()) {
			LogHelper.setResult(LogResult.NA);
			LogHelper.addTrace("TestCase execution was cancelled by the user");
			return;
		}

		pauseForTestCaseTimeout();

		String responseCode = "";

		if (TestSession.isValidationErrorFound()) {
			LogHelper.setResult(LogResult.FAIL);
			LogHelper.addTrace("Validation Error(s) are present");
			LogHelper.addTrace("Test Case has Failed");
			return;
		}

		ArrayList<OadrResponseType> oadrResponseList = TestSession
				.getOadrResponse();

		ArrayList<OadrDistributeEventType> distributeEventList = VENServerResource
				.getOadrDistributeEventReceivedsList();

		if (distributeEventList.size() != 1) {
			LogHelper.setResult(LogResult.FAIL);
			LogHelper.addTrace("Expected only one DistributeEvent");
			LogHelper.addTrace("Test Case has Failed");
			return;
		}

		List<OadrEvent> eiEventList = distributeEventList.get(0).getOadrEvent();
		if (eiEventList.size() != 1) {
			LogHelper.setResult(LogResult.FAIL);
			LogHelper.addTrace("Expected only one EiEvent");
			LogHelper.addTrace("Test Case has Failed");
			return;

		}

		EiTargetType eiTarget = distributeEventList.get(0).getOadrEvent().get(0)
				.getEiEvent().getEiTarget();
		List<String> resourceIDList = eiTarget.getResourceID();
		List<String> venIDList = eiTarget.getVenID();

		boolean atLeastOneTargetReceived = false;

		if (resourceIDList != null && resourceIDList.size() > 0) {
			atLeastOneTargetReceived = true;
		}
		if (venIDList != null && venIDList.size() > 0) {
			atLeastOneTargetReceived = true;
		}
		if (!atLeastOneTargetReceived) {
			LogHelper.setResult(LogResult.FAIL);
			LogHelper.addTrace("Did not receive even one EiTarget element.");
			LogHelper.addTrace("Test Case has Failed");
			return;
		} else {
			LogHelper.addTrace("Receive atleast one EiTarget element.");
		}

		boolean isAtLeastOneEiTargetMatch = DistributeEventConformanceValidationHelper
				.isAtLeastOneEiTargetMatch(distributeEventList.get(0));

		if (!isAtLeastOneEiTargetMatch) {
			LogHelper.setResult(LogResult.FAIL);
			LogHelper
					.addTrace("None of the EiTarget elements match the test system properties configuration.");
			LogHelper.addTrace("Test Case has Failed");
			return;
		} else {
			LogHelper
					.addTrace("Atleast one EiTarget elements match the test system properties configuration.");

		}

		long modificationNumber = eiEventList.get(0).getEiEvent()
				.getEventDescriptor().getModificationNumber();

		if (oadrResponseList.size() == 1) {
			OadrResponseType oadrResponse = oadrResponseList.get(0);

			String expectedResponseCode = "200";
			boolean isExpectedResponse = ResponseHelper
					.isExpectedResponseReceived(oadrResponse,
							expectedResponseCode);

			if (TestSession.isValidationErrorFound()) {
				LogHelper.setResult(LogResult.FAIL);
				LogHelper.addTrace("Validation Error(s) are present");
				LogHelper.addTrace("Test Case has Failed");
				return;
			}

			LogHelper
					.addTrace("Received OadrDistributeEventType with modificationNumber "
							+ modificationNumber);

			if (isExpectedResponse && modificationNumber == 0 && DistributeEventSignalHelper.isExpectedReceived("P",ResponseRequiredType.ALWAYS,0, distributeEventList.get(0).getOadrEvent().get(0))) {
				LogHelper.setResult(LogResult.PASS);
				LogHelper.addTrace("Received OadrResponseType with responseCode "
						+ expectedResponseCode);
				LogHelper.addTrace("Test Case has Passed");
			} else {
				LogHelper.setResult(LogResult.FAIL);
				LogHelper.addTrace("Test Case has Failed");
				LogHelper.addTrace("Received OadrResponseType with responseCode "
						+ responseCode);
			}
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
