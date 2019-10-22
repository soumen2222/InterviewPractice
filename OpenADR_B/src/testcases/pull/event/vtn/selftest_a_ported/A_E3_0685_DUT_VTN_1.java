package testcases.pull.event.vtn.selftest_a_ported;

import java.util.List;

import com.qualitylogic.openadr.core.action.DistributeEventActionList;
import com.qualitylogic.openadr.core.action.IDistributeEventAction;
import com.qualitylogic.openadr.core.action.impl.Default_Pull_CPP_DistributeEventAction;
import com.qualitylogic.openadr.core.base.VtnPullTestCase;
import com.qualitylogic.openadr.core.oadrpoll.OadrPollQueue;
import com.qualitylogic.openadr.core.signal.EiEventType;
import com.qualitylogic.openadr.core.signal.EventStatusEnumeratedType;
import com.qualitylogic.openadr.core.signal.IntervalType;
import com.qualitylogic.openadr.core.signal.OadrDistributeEventType;
import com.qualitylogic.openadr.core.signal.helper.DistributeEventSignalHelper;
import com.qualitylogic.openadr.core.util.Clone;
import com.qualitylogic.openadr.core.util.ConformanceRuleValidator;

public class A_E3_0685_DUT_VTN_1 extends VtnPullTestCase {

	public static void main(String[] args) {
		executeDut(new A_E3_0685_DUT_VTN_1());
	}
	
	@Override
	public void test() throws Exception {
		ConformanceRuleValidator.setDisableDistributeEvent_EventStatusValid_ValidCheck(true);
		
		IDistributeEventAction distributeEventAction1 = new Default_Pull_CPP_DistributeEventAction();
		OadrDistributeEventType distributeEvent1 = distributeEventAction1.getDistributeEvent();
		setupDistributeEvent(distributeEvent1);
		
		OadrPollQueue.addToQueue(distributeEventAction1);		
		
		IDistributeEventAction distributeEventAction2 = Clone.clone(distributeEventAction1);
		OadrDistributeEventType distributeEvent2 = distributeEventAction2.getDistributeEvent();
		distributeEvent2.getOadrEvent().get(0).getEiEvent().getEventDescriptor().setEventStatus(EventStatusEnumeratedType.COMPLETED);
		DistributeEventActionList.addDistributeEventAction(distributeEventAction2);

		addResponse();
		addResponse().setlastEvent(true);
		
		listenForRequests();

		waitFor5Minutes(distributeEventAction2);
	}

	private void setupDistributeEvent(OadrDistributeEventType distributeEvent1) {
		EiEventType eiEvent = distributeEvent1.getOadrEvent().get(0).getEiEvent();
		eiEvent.getEiActivePeriod().getProperties().getDuration().setDuration("PT1M");
		
		List<IntervalType> interval = eiEvent.getEiEventSignals().getEiEventSignal().get(0).getIntervals().getInterval();
		interval.remove(1);
		interval.remove(1);

		int firstIntervalStartTimeDelayMin = 1;
		DistributeEventSignalHelper.setNewReqIDEvntIDStartTimeAndMarketCtx1(distributeEvent1, firstIntervalStartTimeDelayMin);
	}

	private void waitFor5Minutes(IDistributeEventAction distributeEvent) {
		long testCaseTimeout = System.currentTimeMillis() + (5 * 1000 * 60);
		while (System.currentTimeMillis() < testCaseTimeout) {
			pause(2);
			
			if (distributeEvent.isEventCompleted()) {
				break;	
			}
		}
		pause(2);
	}
}
