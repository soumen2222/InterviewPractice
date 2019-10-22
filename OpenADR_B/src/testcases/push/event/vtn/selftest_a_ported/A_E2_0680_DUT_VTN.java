package testcases.push.event.vtn.selftest_a_ported;

import java.util.List;

import javax.xml.datatype.Duration;
import javax.xml.datatype.XMLGregorianCalendar;

import com.qualitylogic.openadr.core.action.DistributeEventActionList;
import com.qualitylogic.openadr.core.action.IDistributeEventAction;
import com.qualitylogic.openadr.core.action.IResponseEventAction;
import com.qualitylogic.openadr.core.action.ResponseEventActionList;
import com.qualitylogic.openadr.core.action.impl.Default_Push_CPP_DistributeEventAction;
import com.qualitylogic.openadr.core.action.impl.Default_ResponseEventAction;
import com.qualitylogic.openadr.core.base.PUSHBaseTestCase;
import com.qualitylogic.openadr.core.bean.ModeType;
import com.qualitylogic.openadr.core.common.TestSession;
import com.qualitylogic.openadr.core.oadrpoll.OadrPollQueue;
import com.qualitylogic.openadr.core.signal.IntervalType;
import com.qualitylogic.openadr.core.signal.OadrDistributeEventType;
import com.qualitylogic.openadr.core.signal.OadrDistributeEventType.OadrEvent;
import com.qualitylogic.openadr.core.signal.helper.DistributeEventSignalHelper;
import com.qualitylogic.openadr.core.signal.helper.SchemaHelper;
import com.qualitylogic.openadr.core.util.Clone;
import com.qualitylogic.openadr.core.util.OadrUtil;
import com.qualitylogic.openadr.core.vtn.VTNService;
import com.qualitylogic.openadr.core.vtn.VtnToVenClient;

public class A_E2_0680_DUT_VTN extends PUSHBaseTestCase {

	public static void main(String[] args) {
		try {
			A_E2_0680_DUT_VTN testClass = new A_E2_0680_DUT_VTN();
			testClass.executeTestCase();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void test() throws Exception {
		TestSession.setMode(ModeType.PUSH);
		
		// ProfileA
		IDistributeEventAction distributeEventAction1 = new Default_Push_CPP_DistributeEventAction();
		OadrPollQueue.addToQueue(distributeEventAction1);
		OadrDistributeEventType distributeEvent = distributeEventAction1.getDistributeEvent();
		
		/*
		IDistributeEventAction distributeEventAction1 = new Default_Push_CPP_DistributeEventAction();
		OadrDistributeEventType distributeEvent = distributeEventAction1
				.getDistributeEvent();
		*/
		OadrEvent oadrEvent1 = distributeEventAction1.getDistributeEvent()
				.getOadrEvent().get(0);
		List<IntervalType> intervals = oadrEvent1.getEiEvent().getEiEventSignals()
				.getEiEventSignal().get(0).getIntervals().getInterval();
		intervals.remove(0);
		intervals.remove(0);
		OadrEvent oadrEvent2 = Clone.clone(oadrEvent1);
		oadrEvent2.getEiEvent().getEventDescriptor()
				.setEventID(OadrUtil.createDescriptorEventID());
		distributeEventAction1.getDistributeEvent().getOadrEvent()
				.add(oadrEvent2);
		int firstIntervalStartTimeDelayMin = 41;

		DistributeEventSignalHelper.setNewReqIDEvntIDStartTimeAndMarketCtx1(
				distributeEventAction1.getDistributeEvent(),
				firstIntervalStartTimeDelayMin);
		XMLGregorianCalendar startDateTime = (XMLGregorianCalendar) distributeEventAction1
				.getDistributeEvent().getOadrEvent().get(1).getEiEvent()
				.getEventDescriptor().getCreatedDateTime().clone();
		startDateTime.add(OadrUtil.createDuration("PT10M"));

		distributeEventAction1.getDistributeEvent().getOadrEvent().get(1)
				.getEiEvent().getEiActivePeriod().getProperties().getDtstart()
				.setDateTime(startDateTime);

		oadrEvent1.getEiEvent().getEiActivePeriod().getProperties()
				.getDuration().setDuration("PT10M");

		intervals.get(0).getDuration().setDuration("PT10M");
		intervals.get(0).getUid().setText("0");
		oadrEvent2.getEiEvent().getEiActivePeriod().getProperties()
				.getDuration().setDuration("PT30M");
		List<IntervalType> intervals2 = oadrEvent2.getEiEvent().getEiEventSignals()
				.getEiEventSignal().get(0).getIntervals().getInterval();
		intervals2.get(0).getDuration().setDuration("PT30M");
		intervals2.get(0).getUid().setText("0");
		DistributeEventActionList
				.addDistributeEventAction(distributeEventAction1);

		distributeEventAction1.getDistributeEvent().getOadrEvent()
				.add(0, oadrEvent2);
		distributeEventAction1.getDistributeEvent().getOadrEvent()
				.add(1, oadrEvent1);
		distributeEventAction1.getDistributeEvent().getOadrEvent().remove(3);
		distributeEventAction1.getDistributeEvent().getOadrEvent().remove(2);

		DistributeEventActionList
				.addDistributeEventAction(distributeEventAction1);

		IResponseEventAction responseEventAction = new Default_ResponseEventAction();
		ResponseEventActionList.addResponseEventAction(responseEventAction);

		IResponseEventAction responseEventAction2 = new Default_ResponseEventAction();
		ResponseEventActionList.addResponseEventAction(responseEventAction2);

		IResponseEventAction responseEventAction3 = new Default_ResponseEventAction();
		responseEventAction3.setlastEvent(true);
		ResponseEventActionList.addResponseEventAction(responseEventAction3);

		distributeEvent = distributeEventAction1.getDistributeEvent();

		VTNService.startVTNService();

		// Simulates event sent as soon as the event is added.
		// /////////////////////////////////////////////////////////////////////
		/*
		 * OadrDistributeEventType distributeEvent1= Clone.clone(distributeEvent);
		 * distributeEvent1.getOadrEvent().remove(1);
		 * 
		 * String strOadrDistributeEvnt1 =
		 * SchemaHelper.getDistributeEventAsString(distributeEvent1); String
		 * responseOneEvent1 = VtnToVenClient.post(strOadrDistributeEvnt1);
		 * 
		 * pause(10);
		 */
		// /////////////////////////////////////////////////////////////////////

		String strOadrDistributeEvent = SchemaHelper
				.getDistributeEventAsString(distributeEvent);

		VtnToVenClient.post(strOadrDistributeEvent);
		pause(5);
		distributeEvent.setRequestID(OadrUtil.createoadrDistributeRequestID());
		
		distributeEvent.setRequestID(OadrUtil.createoadrDistributeRequestID());
		OadrEvent oadrEvent3 = Clone.clone(distributeEvent.getOadrEvent()
				.get(0));
		oadrEvent3.getEiEvent().getEventDescriptor()
				.setCreatedDateTime(OadrUtil.getCurrentTime());
		XMLGregorianCalendar stDateDate = (XMLGregorianCalendar) oadrEvent3
				.getEiEvent().getEventDescriptor().getCreatedDateTime().clone();
		Duration duration = OadrUtil.createDuration(120, 0);
		stDateDate.add(duration);
		oadrEvent3.getEiEvent().getEiActivePeriod().getProperties()
				.getDtstart().setDateTime(stDateDate);
		oadrEvent3.getEiEvent().getEventDescriptor()
				.setEventID(OadrUtil.createDescriptorEventID());
		distributeEvent.getOadrEvent().add(oadrEvent3);

		strOadrDistributeEvent = SchemaHelper
				.getDistributeEventAsString(distributeEvent);
		pause(5);
		
		// Modified Distribute Event		
		VtnToVenClient.post(strOadrDistributeEvent);

		pauseForTestCaseTimeout();
		
		VTNService.stopVTNService();

	}

	@Override
	public void cleanUp() throws Exception {
		
	}

}
