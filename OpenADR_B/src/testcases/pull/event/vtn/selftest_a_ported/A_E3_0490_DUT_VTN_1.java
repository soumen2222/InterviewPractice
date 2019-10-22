package testcases.pull.event.vtn.selftest_a_ported;

import javax.xml.datatype.Duration;
import javax.xml.datatype.XMLGregorianCalendar;

import com.qualitylogic.openadr.core.action.IDistributeEventAction;
import com.qualitylogic.openadr.core.action.IResponseEventAction;
import com.qualitylogic.openadr.core.action.ResponseEventActionList;
import com.qualitylogic.openadr.core.action.impl.Default_Pull_CPP_DistributeEventAction;
import com.qualitylogic.openadr.core.action.impl.Default_ResponseEventAction;
import com.qualitylogic.openadr.core.action.impl.Pull_CPP_Previous_DistributeEventAction2;
import com.qualitylogic.openadr.core.base.PULLBaseTestCase;
import com.qualitylogic.openadr.core.oadrpoll.OadrPollQueue;
import com.qualitylogic.openadr.core.signal.EventStatusEnumeratedType;
import com.qualitylogic.openadr.core.signal.OadrDistributeEventType;
import com.qualitylogic.openadr.core.util.OadrUtil;
import com.qualitylogic.openadr.core.vtn.VTNService;

public class A_E3_0490_DUT_VTN_1 extends PULLBaseTestCase {

	public static void main(String[] args) {
		try {
			A_E3_0490_DUT_VTN_1 testClass = new A_E3_0490_DUT_VTN_1();
			testClass.setEnableLogging(false);
			testClass.executeTestCase();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void test() throws Exception {

		IDistributeEventAction distributeEventAction1 = new Default_Pull_CPP_DistributeEventAction();
		OadrPollQueue.addToQueue(distributeEventAction1); 

		OadrDistributeEventType distributeEvent=distributeEventAction1.getDistributeEvent();

		XMLGregorianCalendar startDt= distributeEvent.getOadrEvent().get(0).getEiEvent().getEiActivePeriod().getProperties().getDtstart().getDateTime();
		Duration duration = OadrUtil.createDuration("-PT3M");
		startDt.add(duration);
		
		distributeEvent.getOadrEvent().get(0).getEiEvent().getEventDescriptor()
		.setEventStatus(EventStatusEnumeratedType.ACTIVE);
		distributeEvent.getOadrEvent().get(0).getEiEvent().getEiEventSignals().getEiEventSignal().get(0).getCurrentValue().getPayloadFloat().setValue((float)1.0);
		
		
		
		IDistributeEventAction distributeEventAction2 = new Pull_CPP_Previous_DistributeEventAction2();
		OadrPollQueue.addToQueue(distributeEventAction2); 

		OadrDistributeEventType distributeEvent2 = distributeEventAction2.getDistributeEvent();
		distributeEvent2.getOadrEvent().get(0).getEiEvent().getEventDescriptor().setModificationNumber(1);
		distributeEvent2.getOadrEvent().get(0).getEiEvent().getEiActivePeriod().getProperties().getDuration().setDuration("PT4M");
		distributeEvent2.getOadrEvent().get(0).getEiEvent().getEiEventSignals().getEiEventSignal().get(0).getIntervals().getInterval().get(1).getDuration().setDuration("PT2M");
		
		
		IResponseEventAction responseEventAction1 = new Default_ResponseEventAction();
		IResponseEventAction responseEventAction2 = new Default_ResponseEventAction();
		ResponseEventActionList.addResponseEventAction(responseEventAction1);
		ResponseEventActionList.addResponseEventAction(responseEventAction2);
		responseEventAction2.setlastEvent(true);

		VTNService.startVTNService();

		pauseForTestCaseTimeout(); 


	}

	@Override
	public void cleanUp() throws Exception {
		VTNService.stopVTNService();
	}
}
