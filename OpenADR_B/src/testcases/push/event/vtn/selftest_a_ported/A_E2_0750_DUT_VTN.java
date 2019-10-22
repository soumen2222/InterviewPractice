package testcases.push.event.vtn.selftest_a_ported;

import com.qualitylogic.openadr.core.action.DistributeEventActionList;
import com.qualitylogic.openadr.core.action.IDistributeEventAction;
import com.qualitylogic.openadr.core.action.IResponseEventAction;
import com.qualitylogic.openadr.core.action.ResponseEventActionList;
import com.qualitylogic.openadr.core.action.impl.Default_ResponseEventAction;
import com.qualitylogic.openadr.core.action.impl.Push_CPP_DistributeEventAction_twoEvents;
import com.qualitylogic.openadr.core.base.PUSHBaseTestCase;
import com.qualitylogic.openadr.core.signal.OadrDistributeEventType;
import com.qualitylogic.openadr.core.signal.helper.SchemaHelper;
import com.qualitylogic.openadr.core.util.Clone;
import com.qualitylogic.openadr.core.vtn.VTNService;
import com.qualitylogic.openadr.core.vtn.VtnToVenClient;

public class A_E2_0750_DUT_VTN extends PUSHBaseTestCase {

	public static void main(String[] args) {
		try {
			A_E2_0750_DUT_VTN testClass = new A_E2_0750_DUT_VTN();
			testClass.executeTestCase();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void test() throws Exception {

		IDistributeEventAction distributeEventAction1 = new Push_CPP_DistributeEventAction_twoEvents();

		DistributeEventActionList
				.addDistributeEventAction(distributeEventAction1);

		IResponseEventAction responseEventAction = new Default_ResponseEventAction();
		ResponseEventActionList.addResponseEventAction(responseEventAction);

		IResponseEventAction responseEventAction2 = new Default_ResponseEventAction();
		ResponseEventActionList.addResponseEventAction(responseEventAction2);

		OadrDistributeEventType distributeEvent = distributeEventAction1
				.getDistributeEvent();

		VTNService.startVTNService();

		// Simulates event sent as soon as the event is added.
		// /////////////////////////////////////////////////////////////////////

		OadrDistributeEventType distributeEvent1 = Clone.clone(distributeEvent);
		distributeEvent1.getOadrEvent().remove(1);

		String strOadrDistributeEvnt1 = SchemaHelper
				.getDistributeEventAsString(distributeEvent1);
		VtnToVenClient.post(strOadrDistributeEvnt1);

		pause(10);

		String strOadrDistributeEvent = SchemaHelper
				.getDistributeEventAsString(distributeEvent);

		VtnToVenClient.post(strOadrDistributeEvent);

		pause(20);

		VTNService.stopVTNService();

	}

	@Override
	public void cleanUp() throws Exception {

	}

}
