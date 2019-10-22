package testcases.push.event.ven;

import java.util.ArrayList;

import javax.xml.bind.JAXBContext;

import com.qualitylogic.openadr.core.action.DistributeEventActionList;
import com.qualitylogic.openadr.core.action.ICreatedEventResult;
import com.qualitylogic.openadr.core.action.IDistributeEventAction;
import com.qualitylogic.openadr.core.action.IResponseEventAction;
import com.qualitylogic.openadr.core.action.ResponseEventActionList;
import com.qualitylogic.openadr.core.action.impl.Default_CreatedEventResultOptINOptOut;
import com.qualitylogic.openadr.core.action.impl.Default_ResponseEventAction;
import com.qualitylogic.openadr.core.action.impl.E0_0285_DistributeEventAction_1;
import com.qualitylogic.openadr.core.base.PUSHBaseTestCase;
import com.qualitylogic.openadr.core.bean.CreatedEventBean;
import com.qualitylogic.openadr.core.common.TestSession;
import com.qualitylogic.openadr.core.common.UIUserPrompt;
import com.qualitylogic.openadr.core.signal.OadrCreatedEventType;
import com.qualitylogic.openadr.core.signal.OadrDistributeEventType;
import com.qualitylogic.openadr.core.signal.OptTypeType;
import com.qualitylogic.openadr.core.signal.helper.DistributeEventSignalHelper;
import com.qualitylogic.openadr.core.signal.helper.LogHelper;
import com.qualitylogic.openadr.core.signal.helper.SchemaHelper;
import com.qualitylogic.openadr.core.util.ConformanceRuleValidator;
import com.qualitylogic.openadr.core.util.LogResult;
import com.qualitylogic.openadr.core.util.OadrUtil;
import com.qualitylogic.openadr.core.util.PropertiesFileReader;
import com.qualitylogic.openadr.core.util.ResourceFileReader;
import com.qualitylogic.openadr.core.util.Trace;
import com.qualitylogic.openadr.core.vtn.VTNService;
import com.qualitylogic.openadr.core.vtn.VtnToVenClient;

@SuppressWarnings("restriction")
public class A_E0_0285_TH_VTN_1 extends PUSHBaseTestCase {

	public static void main(String[] args) {
		try {
			A_E0_0285_TH_VTN_1 testClass = new A_E0_0285_TH_VTN_1();
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
		uiUserPrompt.Prompt(resourceFileReader.Prereq_NoEvents_Message(),
				uiUserPrompt.SMALL);

		if (TestSession.isUserClickedContinuePlay()) {

			IDistributeEventAction distributeEventAction1 = new E0_0285_DistributeEventAction_1();

			DistributeEventActionList
					.addDistributeEventAction(distributeEventAction1);

			ICreatedEventResult createdEventResult1 = new Default_CreatedEventResultOptINOptOut();
			createdEventResult1.setExpectedOptOutCount(1);
			createdEventResult1.setExpectedOptInCount(1);
			createdEventResult1.addDistributeEvent(distributeEventAction1);
			distributeEventAction1
					.setCreatedEventSuccessCriteria(createdEventResult1);
			createdEventResult1.setLastCreatedEventResult(true);

			// Upto 4 CreatedEvents can be received.
			IResponseEventAction responseEventAction1 = new Default_ResponseEventAction();
			IResponseEventAction responseEventAction2 = new Default_ResponseEventAction();
			ResponseEventActionList
					.addResponseEventAction(responseEventAction1);
			ResponseEventActionList
					.addResponseEventAction(responseEventAction2);

			VTNService.startVTNService();
			distributeEventAction1.setEventCompleted(true);
			OadrDistributeEventType distributeEvent = distributeEventAction1
					.getDistributeEvent();
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
				if (createdEventResult1.isExpectedCreatedEventReceived()) {
					break;
				}

			}

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

			boolean isOneEventOptInOptOut = createdEventResult1
					.isExpectedCreatedEventReceived();
			createdEventResult1.setExpectedOptOutCount(0);
			createdEventResult1.setExpectedOptInCount(2);
			boolean isTwoEventOptIn = createdEventResult1
					.isExpectedCreatedEventReceived();

			// Get the created event received by VTN.
			ArrayList<OadrCreatedEventType> createdEventReceivedList = TestSession
					.getCreatedEventReceivedList();
			ArrayList<CreatedEventBean> createdEventBeanList1 = OadrUtil
					.transformToCreatedEventBeanList(createdEventReceivedList);

			OptTypeType optType = DistributeEventSignalHelper
					.getOptTypeInOptedCreatedEvent(distributeEvent
							.getOadrEvent().get(0).getEiEvent(),
							distributeEvent, createdEventBeanList1);

			if (isTwoEventOptIn) {
				LogHelper.setResult(LogResult.PASS);
				LogHelper
						.addTrace("VEN did OptIn on both overlapping events in different market contexts.");
				LogHelper.addTrace("Test Case has Passed");
			} else if (optType!=null && isOneEventOptInOptOut && optType == OptTypeType.OPT_OUT) {
				LogHelper.setResult(LogResult.PASS);
				LogHelper
						.addTrace("VEN opted in to the higher priority event only");
				LogHelper.addTrace("Test Case has Passed");
			} else {
				LogHelper.setResult(LogResult.FAIL);
				LogHelper
						.addTrace("VEN did not OptIn to both events or to just the higher priority event as expected");
				LogHelper.addTrace("Test Case has Failed");

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