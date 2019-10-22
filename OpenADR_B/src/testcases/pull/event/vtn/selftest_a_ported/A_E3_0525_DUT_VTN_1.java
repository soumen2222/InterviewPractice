package testcases.pull.event.vtn.selftest_a_ported;

import javax.xml.datatype.Duration;
import javax.xml.datatype.XMLGregorianCalendar;

import com.qualitylogic.openadr.core.action.IDistributeEventAction;
import com.qualitylogic.openadr.core.action.IResponseEventAction;
import com.qualitylogic.openadr.core.action.ResponseEventActionList;
import com.qualitylogic.openadr.core.action.impl.Default_Pull_TwoEvent_DistributeEventAction;
import com.qualitylogic.openadr.core.action.impl.Default_ResponseEventAction;
import com.qualitylogic.openadr.core.action.impl.E_0525_DistributeEventAction2;
import com.qualitylogic.openadr.core.base.PULLBaseTestCase;
import com.qualitylogic.openadr.core.oadrpoll.OadrPollQueue;
import com.qualitylogic.openadr.core.signal.EiEventType;
import com.qualitylogic.openadr.core.signal.EventStatusEnumeratedType;
import com.qualitylogic.openadr.core.signal.OadrDistributeEventType;
import com.qualitylogic.openadr.core.signal.OadrDistributeEventType.OadrEvent;
import com.qualitylogic.openadr.core.util.Clone;
import com.qualitylogic.openadr.core.util.OadrUtil;
import com.qualitylogic.openadr.core.vtn.VTNService;

public class A_E3_0525_DUT_VTN_1 extends PULLBaseTestCase {

	public static void main(String[] args) {
		try {
			A_E3_0525_DUT_VTN_1 testClass = new A_E3_0525_DUT_VTN_1();
			testClass.setEnableLogging(false);
			testClass.executeTestCase();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void test() throws Exception {

		IDistributeEventAction distributeEventAction1 = new Default_Pull_TwoEvent_DistributeEventAction();

		OadrDistributeEventType oadrDistributeEvent = distributeEventAction1
				.getDistributeEvent();
		// Event 1
		EiEventType event1 = oadrDistributeEvent.getOadrEvent().get(0).getEiEvent();
		XMLGregorianCalendar commonCreatedDate = oadrDistributeEvent
				.getOadrEvent().get(0).getEiEvent().getEventDescriptor()
				.getCreatedDateTime();
		Duration duration40Min = OadrUtil.createDuration("PT40M");
		XMLGregorianCalendar event1StartDate = (XMLGregorianCalendar) commonCreatedDate
				.clone();
		event1StartDate.add(duration40Min);
		oadrDistributeEvent.getOadrEvent().get(0).getEiEvent()
				.getEiActivePeriod().getProperties().getDtstart()
				.setDateTime(event1StartDate);

		event1.getEiEventSignals().getEiEventSignal().get(0).getIntervals()
				.getInterval().remove(1);
		event1.getEiEventSignals().getEiEventSignal().get(0).getIntervals()
				.getInterval().remove(1);

		event1.getEiActivePeriod().getProperties().getDuration()
				.setDuration("PT10M");
		event1.getEventDescriptor().setPriority(new Long(0));
		event1.getEiEventSignals().getEiEventSignal().get(0).getIntervals()
				.getInterval().get(0).getDuration().setDuration("PT10M");

		// Event 2
		EiEventType event2 = oadrDistributeEvent.getOadrEvent().get(1).getEiEvent();

		oadrDistributeEvent.getOadrEvent().get(1).getEiEvent()
				.getEventDescriptor().setCreatedDateTime(commonCreatedDate);
		oadrDistributeEvent.getOadrEvent().get(1).getEiEvent()
				.getEventDescriptor()
				.setEventStatus(EventStatusEnumeratedType.ACTIVE);
		oadrDistributeEvent.getOadrEvent().get(1).getEiEvent()
				.getEiEventSignals().getEiEventSignal().get(0)
				.getCurrentValue().getPayloadFloat().setValue((float) 1.0);
		event2.getEiEventSignals().getEiEventSignal().get(0).getIntervals()
				.getInterval().remove(1);
		event2.getEiEventSignals().getEiEventSignal().get(0).getIntervals()
				.getInterval().remove(1);
		event2.getEiEventSignals().getEiEventSignal().get(0).getIntervals()
				.getInterval().get(0).getDuration().setDuration("PT30M");

		XMLGregorianCalendar startDtTimeEvent2 = (XMLGregorianCalendar) commonCreatedDate
				.clone();

		event2.getEiActivePeriod().getProperties().getDuration()
				.setDuration("PT30M");
		event2.getEiActivePeriod().getProperties().getDtstart()
				.setDateTime(startDtTimeEvent2);

		OadrEvent OadrEvent1clone = Clone.clone(oadrDistributeEvent
				.getOadrEvent().get(0));
		OadrEvent OadrEvent2clone = Clone.clone(oadrDistributeEvent
				.getOadrEvent().get(1));

		oadrDistributeEvent.getOadrEvent().clear();
		// Flipping event order
		oadrDistributeEvent.getOadrEvent().add(0, OadrEvent2clone);
		oadrDistributeEvent.getOadrEvent().add(1, OadrEvent1clone);

		OadrPollQueue.addToQueue(distributeEventAction1); 

		IDistributeEventAction distributeEventAction2 = new E_0525_DistributeEventAction2();
		OadrPollQueue.addToQueue(distributeEventAction2); 

		IResponseEventAction responseEventAction1 = new Default_ResponseEventAction();
		IResponseEventAction responseEventAction2 = new Default_ResponseEventAction();
		ResponseEventActionList.addResponseEventAction(responseEventAction1);
		ResponseEventActionList.addResponseEventAction(responseEventAction2);
		responseEventAction2.setlastEvent(true);
		
		VTNService.startVTNService();

		pauseForTestCaseTimeout(); 

		pause(15);

	}

	@Override
	public void cleanUp() throws Exception {
		VTNService.stopVTNService();
	}
}
