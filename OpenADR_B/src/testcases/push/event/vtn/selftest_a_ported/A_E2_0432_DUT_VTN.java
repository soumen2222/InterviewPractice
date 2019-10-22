package testcases.push.event.vtn.selftest_a_ported;

import java.util.List;
import com.qualitylogic.openadr.core.action.DistributeEventActionList;
import com.qualitylogic.openadr.core.action.IDistributeEventAction;
import com.qualitylogic.openadr.core.action.IResponseEventAction;
import com.qualitylogic.openadr.core.action.ResponseEventActionList;
import com.qualitylogic.openadr.core.action.impl.Default_ResponseEventAction;
import com.qualitylogic.openadr.core.action.impl.Push_DistributeEventAction_OneEvent_TwoInterval;
import com.qualitylogic.openadr.core.base.PUSHBaseTestCase;
import com.qualitylogic.openadr.core.signal.IntervalType;
import com.qualitylogic.openadr.core.signal.Intervals;
import com.qualitylogic.openadr.core.signal.OadrDistributeEventType;
import com.qualitylogic.openadr.core.signal.helper.SchemaHelper;
import com.qualitylogic.openadr.core.util.Clone;
import com.qualitylogic.openadr.core.vtn.VTNService;
import com.qualitylogic.openadr.core.vtn.VtnToVenClient;

public class A_E2_0432_DUT_VTN extends PUSHBaseTestCase {

	public static void main(String[] args) {
		try {
			A_E2_0432_DUT_VTN testClass = new A_E2_0432_DUT_VTN();
			testClass.executeTestCase();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void test() throws Exception {

		IDistributeEventAction distributeEventAction1 = new Push_DistributeEventAction_OneEvent_TwoInterval();

		OadrDistributeEventType distributeEvent = distributeEventAction1
				.getDistributeEvent();
		Intervals intervals = distributeEvent.getOadrEvent().get(0)
				.getEiEvent().getEiEventSignals().getEiEventSignal().get(0)
				.getIntervals();
		List<IntervalType> intervalList = intervals.getInterval();
		IntervalType interval1 = intervalList.get(0);
		interval1.getDuration().setDuration("PT5M");
		IntervalType interval2 = intervalList.get(1);
		interval2.getDuration().setDuration("PT5M");
		IntervalType interval3 = Clone.clone(interval2);
		interval3.getUid().setText("2");
		intervalList.add(2, interval3);

		distributeEvent.getOadrEvent().get(0).getEiEvent().getEiActivePeriod()
				.getProperties().getDuration().setDuration("PT15M");
		DistributeEventActionList
				.addDistributeEventAction(distributeEventAction1);

		IResponseEventAction responseEventAction = new Default_ResponseEventAction();
		responseEventAction.setlastEvent(true);

		ResponseEventActionList.addResponseEventAction(responseEventAction);

		
		VTNService.startVTNService();

		String strOadrDistributeEvent = SchemaHelper.getDistributeEventAsString( 
				distributeEvent);

		VtnToVenClient.post(strOadrDistributeEvent);

		pauseForTestCaseTimeout();

		VTNService.stopVTNService();

	}

	@Override
	public void cleanUp() throws Exception {


	}

}
