package testcases.pull.event.vtn.selftest_a_ported;

import java.util.List;

import javax.xml.datatype.XMLGregorianCalendar;

import com.qualitylogic.openadr.core.action.IDistributeEventAction;
import com.qualitylogic.openadr.core.action.IResponseEventAction;
import com.qualitylogic.openadr.core.action.ResponseEventActionList;
import com.qualitylogic.openadr.core.action.impl.Default_Pull_CPP_DistributeEventAction;
import com.qualitylogic.openadr.core.action.impl.Default_ResponseEventAction;
import com.qualitylogic.openadr.core.base.PULLBaseTestCase;
import com.qualitylogic.openadr.core.oadrpoll.OadrPollQueue;
import com.qualitylogic.openadr.core.signal.IntervalType;
import com.qualitylogic.openadr.core.signal.OadrDistributeEventType;
import com.qualitylogic.openadr.core.signal.OadrDistributeEventType.OadrEvent;
import com.qualitylogic.openadr.core.signal.helper.DistributeEventSignalHelper;
import com.qualitylogic.openadr.core.util.Clone;
import com.qualitylogic.openadr.core.util.OadrUtil;
import com.qualitylogic.openadr.core.vtn.VTNService;

public class A_E3_0680_DUT_VTN_1 extends PULLBaseTestCase {

	public static void main(String[] args) {
		try {
			A_E3_0680_DUT_VTN_1 testClass = new A_E3_0680_DUT_VTN_1();
			testClass.setEnableLogging(false);
			testClass.executeTestCase();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void test() throws Exception {

		IDistributeEventAction distributeEventAction1 = new Default_Pull_CPP_DistributeEventAction();
		OadrDistributeEventType distributeEvent1 = distributeEventAction1
				.getDistributeEvent();

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
				distributeEvent1, firstIntervalStartTimeDelayMin);
		XMLGregorianCalendar startDateTime = (XMLGregorianCalendar) distributeEvent1
				.getOadrEvent().get(1).getEiEvent().getEventDescriptor()
				.getCreatedDateTime().clone();
		startDateTime.add(OadrUtil.createDuration("PT10M"));

		distributeEvent1.getOadrEvent().get(1).getEiEvent().getEiActivePeriod()
				.getProperties().getDtstart().setDateTime(startDateTime);

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
		OadrPollQueue.addToQueue(distributeEventAction1); 

		distributeEvent1.getOadrEvent().add(0, oadrEvent2);
		distributeEvent1.getOadrEvent().add(1, oadrEvent1);
		distributeEvent1.getOadrEvent().remove(3);
		distributeEvent1.getOadrEvent().remove(2);

		IDistributeEventAction distributeEventAction2 = Clone
				.clone(distributeEventAction1);
		distributeEventAction2.getDistributeEvent().setRequestID(
				OadrUtil.createoadrDistributeRequestID());

		OadrPollQueue.addToQueue(distributeEventAction2); 

		IResponseEventAction responseEventAction1 = new Default_ResponseEventAction();
		IResponseEventAction responseEventAction2 = new Default_ResponseEventAction();
		ResponseEventActionList.addResponseEventAction(responseEventAction1);
		ResponseEventActionList.addResponseEventAction(responseEventAction2);
		responseEventAction2.setlastEvent(true);

		VTNService.startVTNService();

		pauseForTestCaseTimeout(); 

		pause(20);

	}

	@Override
	public void cleanUp() throws Exception {
		VTNService.stopVTNService();
	}
}
