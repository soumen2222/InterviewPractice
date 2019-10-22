package testcases.push.event.vtn;

import java.util.ArrayList;
import java.util.List;

import javax.xml.datatype.Duration;
import javax.xml.datatype.XMLGregorianCalendar;

import com.qualitylogic.openadr.core.action.CreatedEventActionList;
import com.qualitylogic.openadr.core.action.ICreatedEventAction;
import com.qualitylogic.openadr.core.action.impl.Default_CreatedEventActionOnLastDistributeEventReceived;
import com.qualitylogic.openadr.core.base.PUSHBaseTestCase;
import com.qualitylogic.openadr.core.common.TestSession;
import com.qualitylogic.openadr.core.common.UIUserPrompt;
import com.qualitylogic.openadr.core.signal.OadrDistributeEventType;
import com.qualitylogic.openadr.core.signal.OadrDistributeEventType.OadrEvent;
import com.qualitylogic.openadr.core.signal.ResponseRequiredType;
import com.qualitylogic.openadr.core.signal.helper.DistributeEventSignalHelper;
import com.qualitylogic.openadr.core.signal.helper.LogHelper;
import com.qualitylogic.openadr.core.util.ConformanceRuleValidator;
import com.qualitylogic.openadr.core.util.LogResult;
import com.qualitylogic.openadr.core.util.OadrUtil;
import com.qualitylogic.openadr.core.util.ResourceFileReader;
import com.qualitylogic.openadr.core.ven.VENServerResource;
import com.qualitylogic.openadr.core.ven.VENService;

public class A_E2_0685_TH_VEN_1 extends PUSHBaseTestCase {

	public static void main(String[] args) {
		try {
			A_E2_0685_TH_VEN_1 testClass = new A_E2_0685_TH_VEN_1();
			testClass.setEnableLogging(true);
			testClass.executeTestCase();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void test() throws Exception {

		ICreatedEventAction createdAction1 = new Default_CreatedEventActionOnLastDistributeEventReceived(
				true, true, "");
		createdAction1.setLastCreateEvent(true);
		CreatedEventActionList.addCreatedEventAction(createdAction1);

		VENService.startVENService();

		new UIUserPrompt().Prompt(
				new ResourceFileReader().TestCase_0685_UIUserPromptText(), 1);

		if (!TestSession.isUserClickedContinuePlay()) {
			LogHelper.setResult(LogResult.NA);
			LogHelper.addTrace("TestCase execution was cancelled by the user");
			return;
		}

		pauseForTestCaseTimeout();

		String responseCode = "";


		ArrayList<OadrDistributeEventType> distributeEventList = VENServerResource
				.getOadrDistributeEventReceivedsList();

		if (distributeEventList.size() != 1) {
			LogHelper.setResult(LogResult.FAIL);
			LogHelper.addTrace("Expected only one DistributeEvent");
			LogHelper.addTrace("Test Case has Failed");
			return;
		}
		
		OadrEvent initialDistributeEvent_OadrEvent = distributeEventList.get(0).getOadrEvent().get(0);


		List<OadrEvent> eiEventList = distributeEventList.get(0).getOadrEvent();
		if (eiEventList.size() != 1) {
			LogHelper.setResult(LogResult.FAIL);
			LogHelper.addTrace("Expected only one EiEvent");
			LogHelper.addTrace("Test Case has Failed");
			return;
		}

		String eventID1=eiEventList.get(0).getEiEvent()
		.getEventDescriptor().getEventID();
		

		if (TestSession.isValidationErrorFound()) {
				LogHelper.setResult(LogResult.FAIL);
				LogHelper.addTrace("Validation Error(s) are present");
				LogHelper.addTrace("Test Case has Failed");
				return;
		}


			if (!DistributeEventSignalHelper.isExpectedReceived("P",ResponseRequiredType.ALWAYS,0, distributeEventList.get(0).getOadrEvent().get(0))) {		
				
				LogHelper.setResult(LogResult.FAIL);
				LogHelper.addTrace("Test Case has Failed");
				LogHelper.addTrace("Received OadrResponseType with responseCode "
						+ responseCode);	
			}
			
			int distributeEventCount=2;
	


				// pause(165);
				pause(175);

			
				
				ICreatedEventAction createdActionNext = new Default_CreatedEventActionOnLastDistributeEventReceived(
						true, true, "");
				createdAction1.setLastCreateEvent(true);
				CreatedEventActionList.addCreatedEventAction(createdActionNext);
				
				
				new UIUserPrompt().Prompt(
						new ResourceFileReader().TestCase_E2_0685_UIUserPromptText(), 1);

				
				if (!TestSession.isUserClickedContinuePlay()) {
					LogHelper.setResult(LogResult.NA);
					LogHelper.addTrace("TestCase execution was cancelled by the user");
					return;
				}
				
				pause(5);
		
				if (TestSession.isValidationErrorFound()) {
					LogHelper.setResult(LogResult.FAIL);
					LogHelper.addTrace("Validation Error(s) are present");
					LogHelper.addTrace("Test Case has Failed");
					return;
			   }
	
				
				distributeEventList = VENServerResource
						.getOadrDistributeEventReceivedsList();
				
				
				if(!(distributeEventList.size()==(distributeEventCount))){
					LogHelper.setResult(LogResult.FAIL);
					LogHelper.addTrace("Did not receive the Expected Distribute Event");
					LogHelper.addTrace("Test Case has Failed");
					return;
					
				}
				
				OadrDistributeEventType oadrDistributeEventNext=distributeEventList.get((distributeEventCount-1));
				
				eiEventList = oadrDistributeEventNext.getOadrEvent();
				

				if (eiEventList.size() == 0) {
					LogHelper.setResult(LogResult.FAIL);
					LogHelper.addTrace("No EiEvent has been received");
					LogHelper.addTrace("Test Case has Failed");
					return;
				}
				
				if (eiEventList.size() != 2) {
					LogHelper.addTrace("Expected two EiEvent");
				}
				
				OadrEvent expectedCompletedEvent = null;
				OadrEvent newEvent = null;
				
				for(int i=0;i<eiEventList.size();i++){
					OadrEvent oadrEvent=eiEventList.get(i);
					String ventID=oadrEvent.getEiEvent().getEventDescriptor().getEventID();
					if(ventID.equals(eventID1)){
						expectedCompletedEvent=oadrEvent;
					}else{
						newEvent=oadrEvent;	
					}
				}

				if(expectedCompletedEvent==null){
					
					//New Event
					XMLGregorianCalendar newEventCreatedDateTime = newEvent.getEiEvent().getEventDescriptor().getCreatedDateTime();
					
					//From initial OadrDistributeEvent
					XMLGregorianCalendar dtStart = initialDistributeEvent_OadrEvent.getEiEvent().getEiActivePeriod().getProperties().getDtstart().getDateTime();
					String durationStr = initialDistributeEvent_OadrEvent.getEiEvent().getEiActivePeriod().getProperties().getDuration().getDuration();
					String startAfterStr = initialDistributeEvent_OadrEvent.getEiEvent().getEiActivePeriod().getProperties().getTolerance().getTolerate().getStartafter();

					Duration duration=OadrUtil.createDuration(durationStr);
					Duration startAfter=OadrUtil.createDuration(startAfterStr);
					
					dtStart.add(duration);
					dtStart.add(startAfter);
					
					if(newEventCreatedDateTime.compare(dtStart)>0){
						LogHelper.addTrace("The user took too long to add the new event, so the test could not complete execution");					
						LogHelper.setResult(LogResult.NA);
						return;
					}else{
						LogHelper.addTrace("Expected completed event was not received");					
						LogHelper.setResult(LogResult.FAIL);
						LogHelper.addTrace("Test Case has Failed");		
						return;
					}
				}else{

					if(!DistributeEventSignalHelper.isExpectedReceived("CO",null,0, distributeEventList.get((distributeEventCount-1)).getOadrEvent().get(1))){
						UIUserPrompt uiUserPrompt1 = new UIUserPrompt();
						uiUserPrompt1.Prompt("Please rerun this test case as the random event start selected by the VEN was longer than the test case expected. ");
						LogHelper.setResult(LogResult.NA);
						LogHelper.addTrace("Rerun test case as random time selected by VEN was too long");
						return;
					}else{
						LogHelper.addTrace("Expected event was received");
						LogHelper.setResult(LogResult.PASS);
						LogHelper.addTrace("Test Case has Passed");
						return;
					}		

				}

	}

	@Override
	public void cleanUp() throws Exception {
		OadrUtil.Push_VTN_Shutdown_Prompt();

		VENService.stopVENService();
	}

}
