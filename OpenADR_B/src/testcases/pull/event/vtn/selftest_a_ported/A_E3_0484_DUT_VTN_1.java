package testcases.pull.event.vtn.selftest_a_ported;

import javax.xml.datatype.Duration;
import javax.xml.datatype.XMLGregorianCalendar;

import com.qualitylogic.openadr.core.action.IDistributeEventAction;
import com.qualitylogic.openadr.core.action.IResponseEventAction;
import com.qualitylogic.openadr.core.action.ResponseEventActionList;
import com.qualitylogic.openadr.core.action.impl.Default_Pull_CPP_DistributeEventAction;
import com.qualitylogic.openadr.core.action.impl.Default_ResponseEventAction;
import com.qualitylogic.openadr.core.base.PULLBaseTestCase;
import com.qualitylogic.openadr.core.oadrpoll.OadrPollQueue;
import com.qualitylogic.openadr.core.signal.EventStatusEnumeratedType;
import com.qualitylogic.openadr.core.signal.OadrDistributeEventType;
import com.qualitylogic.openadr.core.util.OadrUtil;
import com.qualitylogic.openadr.core.vtn.VTNService;

public class A_E3_0484_DUT_VTN_1 extends PULLBaseTestCase {

	public static void main(String[] args) {
		try {
			A_E3_0484_DUT_VTN_1 testClass = new A_E3_0484_DUT_VTN_1();
			testClass.executeTestCase();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void test() throws Exception {

		IDistributeEventAction distributeEventAction1 = new Default_Pull_CPP_DistributeEventAction();
		OadrDistributeEventType distributeEvent = distributeEventAction1.getDistributeEvent();
		

		XMLGregorianCalendar startDt= distributeEvent.getOadrEvent().get(0).getEiEvent().getEiActivePeriod().getProperties().getDtstart().getDateTime();
		Duration duration = OadrUtil.createDuration("-PT3M");
		startDt.add(duration);
		
		distributeEvent.getOadrEvent().get(0).getEiEvent().getEventDescriptor()
		.setEventStatus(EventStatusEnumeratedType.ACTIVE);
		distributeEvent.getOadrEvent().get(0).getEiEvent().getEiEventSignals().getEiEventSignal().get(0).getCurrentValue().getPayloadFloat().setValue((float)1.0);
		
		
		
		OadrPollQueue.addToQueue(distributeEventAction1); 

		IResponseEventAction responseEventAction = new Default_ResponseEventAction();
		responseEventAction.setlastEvent(true);
		ResponseEventActionList.addResponseEventAction(responseEventAction);

		VTNService.startVTNService();

		pauseForTestCaseTimeout(); 

	}

	@Override
	public void cleanUp() throws Exception {
		VTNService.stopVTNService();
	}
}
