package testcases.push.event.ven;

import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.datatype.Duration;
import javax.xml.datatype.XMLGregorianCalendar;

import com.qualitylogic.openadr.core.action.DistributeEventActionList;
import com.qualitylogic.openadr.core.action.ICreatedEventResult;
import com.qualitylogic.openadr.core.action.IDistributeEventAction;
import com.qualitylogic.openadr.core.action.IResponseEventAction;
import com.qualitylogic.openadr.core.action.ResponseEventActionList;
import com.qualitylogic.openadr.core.action.impl.Default_CreatedEventResultOptINOptOut;
import com.qualitylogic.openadr.core.action.impl.Default_ResponseEventAction;
import com.qualitylogic.openadr.core.action.impl.Push_DistributeEventAction_OneEvent_TwoInterval;
import com.qualitylogic.openadr.core.base.PUSHBaseTestCase;
import com.qualitylogic.openadr.core.common.TestSession;
import com.qualitylogic.openadr.core.common.UIUserPrompt;
import com.qualitylogic.openadr.core.signal.EiEventType;
import com.qualitylogic.openadr.core.signal.IntervalType;
import com.qualitylogic.openadr.core.signal.OadrDistributeEventType;
import com.qualitylogic.openadr.core.signal.OadrDistributeEventType.OadrEvent;
import com.qualitylogic.openadr.core.signal.OptTypeType;
import com.qualitylogic.openadr.core.signal.helper.DistributeEventSignalHelper;
import com.qualitylogic.openadr.core.signal.helper.LogHelper;
import com.qualitylogic.openadr.core.signal.helper.SchemaHelper;
import com.qualitylogic.openadr.core.util.Clone;
import com.qualitylogic.openadr.core.util.ConformanceRuleValidator;
import com.qualitylogic.openadr.core.util.LogResult;
import com.qualitylogic.openadr.core.util.OadrUtil;
import com.qualitylogic.openadr.core.util.PropertiesFileReader;
import com.qualitylogic.openadr.core.util.ResourceFileReader;
import com.qualitylogic.openadr.core.vtn.VTNService;
import com.qualitylogic.openadr.core.vtn.VtnToVenClient;

public class A_E0_0185_TH_VTN_1 extends PUSHBaseTestCase {

	public static void main(String[] args) {
		try {
			A_E0_0185_TH_VTN_1 testClass = new A_E0_0185_TH_VTN_1();
			testClass.setEnableLogging(true);
			testClass.executeTestCase();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void test() throws Exception {
		ConformanceRuleValidator
				.setDisableRequestIDValid_Check(true);

		// Prompt to make sure events are cleared from DUT
		ResourceFileReader resourceFileReader = new ResourceFileReader();
		UIUserPrompt uiUserPrompt = new UIUserPrompt();
		uiUserPrompt.Prompt(resourceFileReader.Prereq_NoEvents_Message(),
				uiUserPrompt.SMALL);

		if (!TestSession.isUserClickedContinuePlay()) {
			LogHelper.setResult(LogResult.NA);
			LogHelper.addTrace("TestCase execution was cancelled by the user");
		}

		IDistributeEventAction distributeEventAction1 = new Push_DistributeEventAction_OneEvent_TwoInterval();
		distributeEventAction1.setEventCompleted(true);
		OadrDistributeEventType oadrDistributeEvent = distributeEventAction1
				.getDistributeEvent();
		EiEventType eiEvent = oadrDistributeEvent.getOadrEvent().get(0)
				.getEiEvent();
		DistributeEventSignalHelper.modificationRuleStartDtCurrentPlusMinutes(
				eiEvent, eiEvent.getEventDescriptor().getCreatedDateTime(), 5);
		eiEvent.getEiActivePeriod().getProperties().getDuration()
				.setDuration("PT5M");

		List<IntervalType> intervalList = eiEvent.getEiEventSignals()
				.getEiEventSignal().get(0).getIntervals().getInterval();
		intervalList.remove(1);
		intervalList.get(0).getDuration().setDuration("PT5M");

		DistributeEventActionList
				.addDistributeEventAction(distributeEventAction1);

		ICreatedEventResult createdEventResult1 = new Default_CreatedEventResultOptINOptOut();
		createdEventResult1.setExpectedOptInCount(1);
		createdEventResult1.addDistributeEvent(Clone
				.clone(distributeEventAction1));
	
		createdEventResult1.setLastCreatedEventResult(true);
		distributeEventAction1.setEventCompleted(true);
		// Up to 4 CreatedEvents can be received.
		IResponseEventAction responseEventAction1 = new Default_ResponseEventAction();
		IResponseEventAction responseEventAction2 = new Default_ResponseEventAction();
		ResponseEventActionList.addResponseEventAction(responseEventAction1);
		ResponseEventActionList.addResponseEventAction(responseEventAction2);

		VTNService.startVTNService();

		OadrDistributeEventType distributeEvent = distributeEventAction1
				.getDistributeEvent();
		JAXBContext context = JAXBContext
				.newInstance(OadrDistributeEventType.class);
		String strOadrDistributeEvent = SchemaHelper.getDistributeEventAsString(
				distributeEvent);

		VtnToVenClient.post(strOadrDistributeEvent);

		XMLGregorianCalendar currentDate = OadrUtil.getCurrentTime();
		distributeEvent.setRequestID(OadrUtil.createoadrDistributeRequestID());
		distributeEvent.getOadrEvent().get(0).getEiEvent().getEventDescriptor()
				.setCreatedDateTime(currentDate);

		OadrEvent oadrEvent2 = Clone.clone(distributeEvent.getOadrEvent()
				.get(0));
		oadrEvent2.getEiEvent().getEventDescriptor()
				.setEventID(OadrUtil.createDescriptorEventID());
		distributeEvent.getOadrEvent().add(oadrEvent2);

		XMLGregorianCalendar event2StartDate = (XMLGregorianCalendar) currentDate
				.clone();

		Duration duration = OadrUtil.createDuration("PT20M");
		event2StartDate.add(duration);

		oadrEvent2.getEiEvent().getEiActivePeriod().getProperties()
				.getDtstart().setDateTime(event2StartDate);

		String strOadrDistributeEvent2 = SchemaHelper.getDistributeEventAsString(
				distributeEvent);

		ICreatedEventResult createdEventResult2 = new Default_CreatedEventResultOptINOptOut();
		createdEventResult2.setExpectedOptInCount(2);
		createdEventResult2.addDistributeEvent(Clone
				.clone(distributeEventAction1));
		distributeEventAction1
				.setCreatedEventSuccessCriteria(createdEventResult2);
		createdEventResult2.setLastCreatedEventResult(true);

		//********************
		Thread.sleep(5000);
		//********************
		
		
		VtnToVenClient.post(strOadrDistributeEvent2);

		PropertiesFileReader propertiesFileReader = new PropertiesFileReader();
		long totalDurationInput = Long.valueOf(propertiesFileReader
				.get("asyncResponseTimeout"));
		long testCaseTimeout = System.currentTimeMillis() + totalDurationInput;

		while (System.currentTimeMillis() < testCaseTimeout) {
			pause(5);
			if (createdEventResult1.isExpectedCreatedEventReceived()) {
				if (createdEventResult2.isExpectedCreatedEventReceived()) {
					break;
				}
			}
		}

		boolean atleastOneValidationErrorPresent = TestSession
				.isAtleastOneValidationErrorPresent();


		if (atleastOneValidationErrorPresent) {
			LogHelper.setResult(LogResult.FAIL);
			LogHelper.addTrace("Validation Error(s) are present");
			LogHelper.addTrace("Test Case has Failed");
			return;
		}

		if (createdEventResult1.isExpectedCreatedEventReceived()
				&& createdEventResult2.isExpectedCreatedEventReceived()) {

			LogHelper.setResult(LogResult.PASS);
			LogHelper.addTrace("Receive the expected matching OadrCreatedEventType with "
					+ OptTypeType.OPT_IN+" for the first and second DistributeEvent payload");
			LogHelper.addTrace("Test Case has Passed");

		} else {
			
			LogHelper.setResult(LogResult.FAIL);
			if(!createdEventResult1.isExpectedCreatedEventReceived()){
				LogHelper
				.addTrace("Did not receive expected matching OadrCreatedEventType with "
						+ OptTypeType.OPT_IN +" for the first DistributeEvent payload");	
			}
			if(!createdEventResult2.isExpectedCreatedEventReceived()){
				LogHelper
				.addTrace("Did not receive expected matching OadrCreatedEventType with "
						+ OptTypeType.OPT_IN +" for the second DistributeEvent payload");
				
			}

			LogHelper.addTrace("Test Case has Failed");

		}

	}

	@Override
	public void cleanUp() throws Exception {
		VTNService.stopVTNService();
	}
}