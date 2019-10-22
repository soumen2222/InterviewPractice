package testcases.push.event.vtn;

import java.util.ArrayList;

import com.qualitylogic.openadr.core.action.CreatedEventActionList;
import com.qualitylogic.openadr.core.action.ICreatedEventAction;
import com.qualitylogic.openadr.core.action.impl.CreatedEventAction_One;
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
import com.qualitylogic.openadr.core.util.ConformanceRuleValidator;
import com.qualitylogic.openadr.core.util.LogResult;
import com.qualitylogic.openadr.core.util.OadrUtil;
import com.qualitylogic.openadr.core.util.PropertiesFileReader;
import com.qualitylogic.openadr.core.util.ResourceFileReader;
import com.qualitylogic.openadr.core.ven.VENServerResource;
import com.qualitylogic.openadr.core.ven.VENService;
import com.qualitylogic.openadr.core.ven.VenToVtnClient;

public class A_E2_0450_TH_VEN_1 extends PUSHBaseTestCase {

	public static void main(String[] args) {
		try {
			A_E2_0450_TH_VEN_1 testClass = new A_E2_0450_TH_VEN_1();
			testClass.setEnableLogging(true);
			testClass.executeTestCase();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void test() throws Exception {

		ICreatedEventAction createdAction1 = new CreatedEventAction_One(true,
				true);
		CreatedEventActionList.addCreatedEventAction(createdAction1);

		VENService.startVENService();

		UIUserPrompt uiUserPrompt = new UIUserPrompt();
		ResourceFileReader resourceFileReader = new ResourceFileReader();
		String message = resourceFileReader.TestCase_0450_UIUserPromptText();
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

		OadrDistributeEventType expectedDistributeEvent = null;

		while (System.currentTimeMillis() < testCaseTimeout) {
			oadrDistributeEventList = (ArrayList<OadrDistributeEventType>) VENServerResource
					.getOadrDistributeEventReceivedsList().clone();
			pause(5);
			for (OadrDistributeEventType eachOadrDistributeEvent : oadrDistributeEventList) {
				if (eachOadrDistributeEvent.getOadrEvent() != null) {
					int size = eachOadrDistributeEvent.getOadrEvent().size();
					if (size == 2) {
						expectedDistributeEvent = eachOadrDistributeEvent;
						break;
					}
				}
			}
			if (expectedDistributeEvent != null) {
				break;
			}
		}
		pause(5);

		if (expectedDistributeEvent == null) {
			LogHelper.setResult(LogResult.FAIL);
			LogHelper
					.addTrace("Did not receive two OadrEvent in a single OadrDistributeEventType payload within the expected time");
			LogHelper.addTrace("Test Case has Failed");
			return;
		}

	

		ICreatedEventAction createdAction2 = new Default_CreatedEventActionOnLastDistributeEventReceived(
				false, true);

		String createdEventStr = createdAction2.getCreateEvent();
		OadrCreatedEventType createdEvent = CreatedEventHelper
				.createCreatedEventFromString(createdEventStr);
		EventResponse eventResponse = createdEvent.getEiCreatedEvent()
				.getEventResponses().getEventResponse().get(1);
		eventResponse.setOptType(OptTypeType.OPT_OUT);

		OadrCreatedEventType createdEvent2 = Clone.clone(createdEvent);
		createdEvent2.getEiCreatedEvent().getEventResponses()
				.getEventResponse().remove(0);
		createdEvent.getEiCreatedEvent().getEventResponses().getEventResponse()
				.remove(1);

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

		OadrResponseType oadrResponse1 = ResponseHelper
				.createOadrResponseFromString(oadrResponse1Str);
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

		LogHelper.addTrace("---Event 1--");
		if(!DistributeEventSignalHelper.isExpectedReceived("P",ResponseRequiredType.ALWAYS,0, expectedDistributeEvent.getOadrEvent().get(0))){
			LogHelper.setResult(LogResult.FAIL);
			LogHelper.addTrace("Test Case has Failed");
			return;
		}
		LogHelper.addTrace("-----------------------------");

		
		LogHelper.addTrace("---Event 2--");
		if(!DistributeEventSignalHelper.isExpectedReceived("P",ResponseRequiredType.ALWAYS,0, expectedDistributeEvent.getOadrEvent().get(1))){
			LogHelper.setResult(LogResult.FAIL);
			LogHelper.addTrace("Test Case has Failed");
			return;
		}
		LogHelper.addTrace("-----------------------------");
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
