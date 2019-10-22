package testcases.push.event.vtn.selftest_a_ported;

import com.qualitylogic.openadr.core.action.DistributeEventActionList;
import com.qualitylogic.openadr.core.action.IDistributeEventAction;
import com.qualitylogic.openadr.core.action.IResponseEventAction;
import com.qualitylogic.openadr.core.action.ResponseEventActionList;
import com.qualitylogic.openadr.core.action.impl.Default_Pull_TwoEvent_DistributeEventAction;
import com.qualitylogic.openadr.core.action.impl.Default_ResponseEventAction;
import com.qualitylogic.openadr.core.base.PUSHBaseTestCase;
import com.qualitylogic.openadr.core.signal.OadrDistributeEventType;
import com.qualitylogic.openadr.core.signal.helper.SchemaHelper;
import com.qualitylogic.openadr.core.util.Clone;
import com.qualitylogic.openadr.core.vtn.VTNService;
import com.qualitylogic.openadr.core.vtn.VtnToVenClient;

public class A_E2_0440_DUT_VTN extends PUSHBaseTestCase {

	public static void main(String[] args) {
		try {
			A_E2_0440_DUT_VTN testClass = new A_E2_0440_DUT_VTN();
			testClass.executeTestCase();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void test() throws Exception {

		IDistributeEventAction distributeEventAction1 = new Default_Pull_TwoEvent_DistributeEventAction();
		DistributeEventActionList
				.addDistributeEventAction(distributeEventAction1);

		IResponseEventAction responseEventAction = new Default_ResponseEventAction();
		ResponseEventActionList.addResponseEventAction(responseEventAction);

		IResponseEventAction responseEventAction2 = new Default_ResponseEventAction();
		ResponseEventActionList.addResponseEventAction(responseEventAction2);
		responseEventAction2.setlastEvent(true);

		
		OadrDistributeEventType distributeEvent = distributeEventAction1
				.getDistributeEvent();

		VTNService.startVTNService();

		String strOadrDistributeEvent = SchemaHelper.getDistributeEventAsString( 
				distributeEvent);

		// Simulates event sent as soon as the event is added.
		///////////////////////////////////////////////////////////////////////
		OadrDistributeEventType distributeEventOneEvent = Clone
				.clone(distributeEvent);
		distributeEventOneEvent.getOadrEvent().remove(1);
		String strOadrDistributeEventOneEvent = SchemaHelper.getDistributeEventAsString( 
				distributeEventOneEvent);
		VtnToVenClient
				.post(strOadrDistributeEventOneEvent);
		pause(10);
		///////////////////////////////////////////////////////////////////////

		VtnToVenClient.post(strOadrDistributeEvent);

		pauseForTestCaseTimeout();


		VTNService.stopVTNService();

	}

	@Override
	public void cleanUp() throws Exception {


	}

}
