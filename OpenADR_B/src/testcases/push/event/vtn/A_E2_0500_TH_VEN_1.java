package testcases.push.event.vtn;

import java.util.ArrayList;

import com.qualitylogic.openadr.core.action.ICreatedEventAction;
import com.qualitylogic.openadr.core.action.impl.Default_CreatedEventActionOnLastDistributeEventReceived;
import com.qualitylogic.openadr.core.base.PUSHBaseTestCase;
import com.qualitylogic.openadr.core.common.TestSession;
import com.qualitylogic.openadr.core.common.UIUserPrompt;
import com.qualitylogic.openadr.core.signal.EventResponses.EventResponse;
import com.qualitylogic.openadr.core.signal.OadrCreatedEventType;
import com.qualitylogic.openadr.core.signal.OadrDistributeEventType;
import com.qualitylogic.openadr.core.signal.OadrResponseType;
import com.qualitylogic.openadr.core.signal.OptTypeType;
import com.qualitylogic.openadr.core.signal.ResponseRequiredType;
import com.qualitylogic.openadr.core.signal.helper.CreatedEventHelper;
import com.qualitylogic.openadr.core.signal.helper.DistributeEventSignalHelper;
import com.qualitylogic.openadr.core.signal.helper.LogHelper;
import com.qualitylogic.openadr.core.signal.helper.ResponseHelper;
import com.qualitylogic.openadr.core.signal.helper.SchemaHelper;
import com.qualitylogic.openadr.core.util.Clone;
import com.qualitylogic.openadr.core.util.LogResult;
import com.qualitylogic.openadr.core.util.OadrUtil;
import com.qualitylogic.openadr.core.util.PropertiesFileReader;
import com.qualitylogic.openadr.core.util.ResourceFileReader;
import com.qualitylogic.openadr.core.ven.VENServerResource;
import com.qualitylogic.openadr.core.ven.VENService;
import com.qualitylogic.openadr.core.ven.VenToVtnClient;

public class A_E2_0500_TH_VEN_1 extends PUSHBaseTestCase {

	public static void main(String[] args) {
		try {
			A_E2_0500_TH_VEN_1 testClass = new A_E2_0500_TH_VEN_1();
			testClass.setEnableLogging(true);
			testClass.executeTestCase();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void test() throws Exception {
		UIUserPrompt uiUserPrompt = new UIUserPrompt();
		ResourceFileReader resourceFileReader = new ResourceFileReader();

		VENService.startVENService();

		String message = resourceFileReader.TestCase_0500_UIUserPromptText();
		uiUserPrompt.Prompt(message, UIUserPrompt.MEDIUM);
		if (TestSession.isUserClickedContinuePlay() == false) {
			LogHelper.setResult(LogResult.FAIL);
			LogHelper.addTrace("Test Case has Failed");
			return;
		}

		PropertiesFileReader propertiesFileReader = new PropertiesFileReader();
		long totalDurationInput = Long.valueOf(propertiesFileReader
				.get("asyncResponseTimeout"));
		long testCaseTimeout = System.currentTimeMillis() + totalDurationInput;

		ArrayList<OadrDistributeEventType> oadrDistributeEventList = null;
		while (System.currentTimeMillis() < testCaseTimeout) {
			oadrDistributeEventList = VENServerResource
					.getOadrDistributeEventReceivedsList();
			pause(2);
			if (oadrDistributeEventList.size() == 1) {
			
				break;
			}

		}
		int receivedDistributeEventSize = oadrDistributeEventList.size();
		if (receivedDistributeEventSize != 1) {
			LogHelper.setResult(LogResult.FAIL);
			LogHelper
					.addTrace("Did not receive the expected single DistributeEvent");
			LogHelper.addTrace("Number of DistributeEvent received "
					+ oadrDistributeEventList.size());
			LogHelper.addTrace("Test Case has Failed");
			return;
		}

		ICreatedEventAction createdAction2 = new Default_CreatedEventActionOnLastDistributeEventReceived(
				true, true);

		String createdEventStr = createdAction2.getCreateEvent();
		OadrCreatedEventType createdEvent = CreatedEventHelper
				.createCreatedEventFromString(createdEventStr);

		OadrCreatedEventType createdEvent2 = Clone.clone(createdEvent);
		EventResponse eventResponse = createdEvent2.getEiCreatedEvent()
				.getEventResponses().getEventResponse().get(0);
		eventResponse.setOptType(OptTypeType.OPT_OUT);

		String createdEvent1ToPost = SchemaHelper
				.getCreatedEventAsString(createdEvent);
		String createdEvent2ToPost = SchemaHelper
				.getCreatedEventAsString(createdEvent2);

		String oadrResponse1Str = VenToVtnClient.post(createdEvent1ToPost);
		String oadrResponse2Str = VenToVtnClient.post(createdEvent2ToPost);

		if (TestSession.isValidationErrorFound()) {
			LogHelper.setResult(LogResult.FAIL);
			LogHelper.addTrace("Validation Error(s) are present");
			LogHelper.addTrace("Test Case has Failed");

			return;
		}

		if(!DistributeEventSignalHelper.isExpectedReceived("P",ResponseRequiredType.ALWAYS,0, VENServerResource
				.getOadrDistributeEventReceivedsList().get(0).getOadrEvent().get(0))){
			LogHelper.setResult(LogResult.FAIL);
			LogHelper.addTrace("Test Case has Failed");
			return;
		}
		
		OadrResponseType oadrResponse1 = ResponseHelper
				.createOadrResponseFromString(oadrResponse1Str);
		pause(5);
		OadrResponseType oadrResponse2 = ResponseHelper
				.createOadrResponseFromString(oadrResponse2Str);

		String expectedResponseCode = "200";
		boolean isExpectedResponse1 = ResponseHelper
				.isExpectedResponseReceived(oadrResponse1, expectedResponseCode);
		boolean isExpectedResponse2 = ResponseHelper
				.isExpectedResponseReceived(oadrResponse2, expectedResponseCode);

		LogHelper.addTrace("OadrResponseType for CreatedEvent 1 : responseCode "
				+ ResponseHelper.getOadrResponseCode(oadrResponse1));
		LogHelper.addTrace("OadrResponseType for CreatedEvent 2 : responseCode "
				+ ResponseHelper.getOadrResponseCode(oadrResponse2));

		if (isExpectedResponse1 && isExpectedResponse2) {
			LogHelper.setResult(LogResult.PASS);
			LogHelper.addTrace("Test Case has Passed");
			return;
		} else {
			LogHelper.setResult(LogResult.FAIL);
			LogHelper.addTrace("Test Case has Failed");
			return;
		}

	}

	@Override
	public void cleanUp() throws Exception {
		OadrUtil.Push_VTN_Shutdown_Prompt();

		VENService.stopVENService();
	}

}
