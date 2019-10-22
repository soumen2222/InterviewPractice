package testcases.push.event.ven;

import java.util.ArrayList;

import javax.xml.bind.JAXBContext;

import com.qualitylogic.openadr.core.action.DistributeEventActionList;
import com.qualitylogic.openadr.core.action.IDistributeEventAction;
import com.qualitylogic.openadr.core.action.IResponseEventAction;
import com.qualitylogic.openadr.core.action.ResponseEventActionList;
import com.qualitylogic.openadr.core.action.impl.Default_ResponseEventAction;
import com.qualitylogic.openadr.core.action.impl.E0_0300_Push_CPP_DistributeEventAction;
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
import com.qualitylogic.openadr.core.util.Trace;
import com.qualitylogic.openadr.core.vtn.VTNService;
import com.qualitylogic.openadr.core.vtn.VtnToVenClient;

@SuppressWarnings("restriction")
public class A_E0_0300_TH_VTN_1 extends PUSHBaseTestCase {

	public static void main(String[] args) {
		try {
			A_E0_0300_TH_VTN_1 testClass = new A_E0_0300_TH_VTN_1();
			testClass.setEnableLogging(true);
			testClass.executeTestCase();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void test() throws Exception {
		ConformanceRuleValidator.setDisable_VenIDValid_Check(true);

		ResourceFileReader resourceFileReader = new ResourceFileReader();
		UIUserPrompt uiUserPrompt = new UIUserPrompt();
		uiUserPrompt.Prompt(resourceFileReader.Prereq_NoEvents_Message());

		if (TestSession.isUserClickedContinuePlay()) {

			VTNService.startVTNService();
			ConformanceRuleValidator
					.setDisable_AtLeastOneEiTargetMatch_ValidCheck(true);
			ConformanceRuleValidator
					.setDisableCreatedEvent_ResponseCodeSuccess_Check(true);

			IDistributeEventAction distributeEventAction1 = new E0_0300_Push_CPP_DistributeEventAction();
			DistributeEventActionList
					.addDistributeEventAction(distributeEventAction1);

			IResponseEventAction responseEventAction = new Default_ResponseEventAction();
			ResponseEventActionList.addResponseEventAction(responseEventAction);

			OadrDistributeEventType distributeEvent = distributeEventAction1
					.getDistributeEvent();
			distributeEventAction1.setEventCompleted(true);

			JAXBContext context = JAXBContext
					.newInstance(OadrDistributeEventType.class);
			String strOadrDistributeEvent = SchemaHelper.getDistributeEventAsString(
					distributeEvent);

			VtnToVenClient.post(strOadrDistributeEvent);

			PropertiesFileReader propertiesFileReader = new PropertiesFileReader();

			long totalDurationInput = Long.valueOf(propertiesFileReader
					.get("asyncResponseTimeout"));

			long testCaseTimeout = System.currentTimeMillis()
					+ totalDurationInput;

			while (System.currentTimeMillis() < testCaseTimeout) {
				pause(5);
				ArrayList<OadrCreatedEventType> createdEventList = TestSession
				.getCreatedEventReceivedList();
			
				if (createdEventList.size() > 0) {
					break;
				}
			}

			Trace trace = TestSession.getTraceObj();

			ArrayList<OadrCreatedEventType> createdEventList = TestSession
					.getCreatedEventReceivedList();
			if (TestSession.isAtleastOneValidationErrorPresent()
					&& trace != null) {
				LogHelper.setResult(LogResult.FAIL);
				LogHelper.addTrace("Validation Error(s) are present");
				LogHelper.addTrace("Test Case has Failed");
				return;
			}

			if (createdEventList.size() == 1) {
				OadrCreatedEventType oadrCreatedEvent = createdEventList.get(0);
				String responseCode = oadrCreatedEvent.getEiCreatedEvent()
						.getEventResponses().getEventResponse().get(0)
						.getResponseCode();
				boolean isExpectedError = responseCode.startsWith("4");

				if (isExpectedError) {
					LogHelper.setResult(LogResult.PASS);
					LogHelper
							.addTrace("Received Error Response Code in CreatedEvent "
									+ responseCode);
					LogHelper.addTrace("Test Case has Passed");
					return;
				}
			}
			LogHelper.addTrace("Test Case has Failed");
			LogHelper.setResult(LogResult.FAIL);
		} else { // User closed the action dialog
			LogHelper.setResult(LogResult.NA);
			LogHelper.addTrace("TestCase execution was cancelled by the user");
		}

	}

	@Override
	public void cleanUp() throws Exception {
		VTNService.stopVTNService();
		;
	}
}
