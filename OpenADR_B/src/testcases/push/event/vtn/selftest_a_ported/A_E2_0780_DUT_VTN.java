package testcases.push.event.vtn.selftest_a_ported;

import com.qualitylogic.openadr.core.action.IDistributeEventAction;
import com.qualitylogic.openadr.core.action.IResponseEventAction;
import com.qualitylogic.openadr.core.action.ResponseEventActionList;
import com.qualitylogic.openadr.core.action.impl.Default_Push_CPP_DistributeEventAction;
import com.qualitylogic.openadr.core.action.impl.Default_ResponseEventAction;
import com.qualitylogic.openadr.core.base.PUSHBaseTestCase;
import com.qualitylogic.openadr.core.bean.ModeType;
import com.qualitylogic.openadr.core.common.TestSession;
import com.qualitylogic.openadr.core.oadrpoll.OadrPollQueue;
import com.qualitylogic.openadr.core.signal.OadrDistributeEventType;
import com.qualitylogic.openadr.core.signal.helper.SchemaHelper;
import com.qualitylogic.openadr.core.vtn.VTNService;
import com.qualitylogic.openadr.core.vtn.VtnToVenClient;

public class A_E2_0780_DUT_VTN extends PUSHBaseTestCase {

	public static void main(String[] args) {
		try {
			A_E2_0780_DUT_VTN testClass = new A_E2_0780_DUT_VTN();
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
		String strOadrDistributeEvent = SchemaHelper.getDistributeEventAsString(distributeEvent);

		IResponseEventAction responseEventAction = new Default_ResponseEventAction(
				"400");
		responseEventAction.setlastEvent(true);
		ResponseEventActionList.addResponseEventAction(responseEventAction);
		
		/*
		IDistributeEventAction distributeEventAction1 = new Default_Push_CPP_DistributeEventAction();
		DistributeEventActionList
				.addDistributeEventAction(distributeEventAction1);

		IResponseEventAction responseEventAction = new Default_ResponseEventAction(
				"410");
		ResponseEventActionList.addResponseEventAction(responseEventAction);
		OadrDistributeEventType distributeEvent = distributeEventAction1
				.getDistributeEvent();
		*/

		VTNService.startVTNService();

		/*
		JAXBContext context = JAXBContext
				.newInstance(OadrDistributeEventType.class);
		String strOadrDistributeEvent = SchemaHelper.getDistributeEventAsString( 
				distributeEvent);
		*/

		VtnToVenClient.post(strOadrDistributeEvent);

		pause(20);


		VTNService.stopVTNService();

	}

	@Override
	public void cleanUp() throws Exception {

	}

}
