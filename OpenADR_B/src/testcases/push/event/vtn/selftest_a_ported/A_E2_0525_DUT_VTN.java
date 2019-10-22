package testcases.push.event.vtn.selftest_a_ported;

import javax.xml.datatype.Duration;
import javax.xml.datatype.XMLGregorianCalendar;

import com.qualitylogic.openadr.core.action.DistributeEventActionList;
import com.qualitylogic.openadr.core.action.IDistributeEventAction;
import com.qualitylogic.openadr.core.action.IResponseEventAction;
import com.qualitylogic.openadr.core.action.ResponseEventActionList;
import com.qualitylogic.openadr.core.action.impl.Default_ResponseEventAction;
import com.qualitylogic.openadr.core.action.impl.E_0525_DistributeEventAction2;
import com.qualitylogic.openadr.core.action.impl.Push_CPP_DistributeEventAction_twoEvents;
import com.qualitylogic.openadr.core.base.PUSHBaseTestCase;
import com.qualitylogic.openadr.core.bean.ModeType;
import com.qualitylogic.openadr.core.common.TestSession;
import com.qualitylogic.openadr.core.oadrpoll.OadrPollQueue;
import com.qualitylogic.openadr.core.signal.EiEventType;
import com.qualitylogic.openadr.core.signal.EventStatusEnumeratedType;
import com.qualitylogic.openadr.core.signal.OadrDistributeEventType;
import com.qualitylogic.openadr.core.signal.OadrDistributeEventType.OadrEvent;
import com.qualitylogic.openadr.core.signal.helper.SchemaHelper;
import com.qualitylogic.openadr.core.util.Clone;
import com.qualitylogic.openadr.core.util.OadrUtil;
import com.qualitylogic.openadr.core.vtn.VTNService;
import com.qualitylogic.openadr.core.vtn.VtnToVenClient;

public class A_E2_0525_DUT_VTN extends PUSHBaseTestCase {

	public static void main(String[] args) {
		try {
			A_E2_0525_DUT_VTN testClass = new A_E2_0525_DUT_VTN();
			testClass.executeTestCase();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void test() throws Exception {
		TestSession.setMode(ModeType.PUSH);

		// ProfileA
		IDistributeEventAction distributeEventAction1 = new Push_CPP_DistributeEventAction_twoEvents();
		OadrPollQueue.addToQueue(distributeEventAction1);
		OadrDistributeEventType oadrDistributeEvent = distributeEventAction1.getDistributeEvent();
		// String strOadrDistributeEvent = SchemaHelper.getDistributeEventAsString(oadrDistributeEvent);
		/*
		IDistributeEventAction distributeEventAction1 = new Push_CPP_DistributeEventAction_twoEvents();

		OadrDistributeEventType oadrDistributeEvent = distributeEventAction1
				.getDistributeEvent();
		*/
		
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
		// Duration durationToAddToStTime2=OadrUtil.createDuration(20, 0);
		// startDtTimeEvent2.add(durationToAddToStTime2);

		event2.getEiActivePeriod().getProperties().getDuration()
				.setDuration("PT30M");
		event2.getEiActivePeriod().getProperties().getDtstart()
				.setDateTime(startDtTimeEvent2);
		// event2.getEventDescriptor().setPriority(new Long(0));

		OadrEvent OadrEvent1clone = Clone.clone(oadrDistributeEvent
				.getOadrEvent().get(0));
		OadrEvent OadrEvent2clone = Clone.clone(oadrDistributeEvent
				.getOadrEvent().get(1));

		oadrDistributeEvent.getOadrEvent().clear();
		// Flipping event order
		oadrDistributeEvent.getOadrEvent().add(0, OadrEvent2clone);
		oadrDistributeEvent.getOadrEvent().add(1, OadrEvent1clone);

		DistributeEventActionList
				.addDistributeEventAction(distributeEventAction1);

		String strOadrDistributeEvent1 = SchemaHelper
				.getDistributeEventAsString(oadrDistributeEvent);

		VTNService.startVTNService();

		// Simulates event sent as soon as the event is added.
		// /////////////////////////////////////////////////////////////////////

		OadrDistributeEventType distributeEvent1 = Clone.clone(oadrDistributeEvent);
		distributeEvent1.getOadrEvent().remove(1);

		pause(10);
		// /////////////////////////////////////////////////////////////////////

		VtnToVenClient.post(strOadrDistributeEvent1);

		IDistributeEventAction distributeEventAction2 = new E_0525_DistributeEventAction2();
		DistributeEventActionList
				.addDistributeEventAction(distributeEventAction2);

		pause(10);

		String strOadrDistributeEvent2 = SchemaHelper
				.getDistributeEventAsString(distributeEventAction2
						.getDistributeEvent());

		VtnToVenClient.post(strOadrDistributeEvent2);

		IResponseEventAction responseEventAction1 = new Default_ResponseEventAction();
		IResponseEventAction responseEventAction2 = new Default_ResponseEventAction();
		ResponseEventActionList.addResponseEventAction(responseEventAction1);
		ResponseEventActionList.addResponseEventAction(responseEventAction2);

		pause(20);

		VTNService.stopVTNService();

	}

	@Override
	public void cleanUp() throws Exception {

	}

}
