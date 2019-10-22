package testcases.push.event.vtn.selftest_a_ported;

import com.qualitylogic.openadr.core.action.IDistributeEventAction;
import com.qualitylogic.openadr.core.action.impl.Push_CPP_DistributeEventAction_fourEvents;
import com.qualitylogic.openadr.core.base.PUSHBaseTestCase;
import com.qualitylogic.openadr.core.bean.ModeType;
import com.qualitylogic.openadr.core.common.TestSession;
import com.qualitylogic.openadr.core.oadrpoll.OadrPollQueue;
import com.qualitylogic.openadr.core.signal.OadrDistributeEventType;
import com.qualitylogic.openadr.core.signal.helper.SchemaHelper;
import com.qualitylogic.openadr.core.vtn.VTNService;
import com.qualitylogic.openadr.core.vtn.VtnToVenClient;

public class A_E2_0520_DUT_VTN extends PUSHBaseTestCase {

	public static void main(String[] args) {
		try {
			A_E2_0520_DUT_VTN testClass = new A_E2_0520_DUT_VTN();
			testClass.executeTestCase();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void test() throws Exception {
		TestSession.setMode(ModeType.PUSH);

		// ProfileA
		IDistributeEventAction distributeEventAction1 = new Push_CPP_DistributeEventAction_fourEvents();
		//OadrPollQueue.addToQueue(distributeEventAction1);
		OadrDistributeEventType distributeEvent = distributeEventAction1.getDistributeEvent();
		String strOadrDistributeEvent = SchemaHelper.getDistributeEventAsString(distributeEvent);
		/*
		IDistributeEventAction distributeEventAction1 = new Push_CPP_DistributeEventAction_fourEvents();
		DistributeEventActionList
				.addDistributeEventAction(distributeEventAction1);

		IResponseEventAction responseEventAction = new Default_ResponseEventAction();
		ResponseEventActionList.addResponseEventAction(responseEventAction);
		OadrDistributeEventType distributeEvent = distributeEventAction1
				.getDistributeEvent();
		*/

		VTNService.startVTNService();

		// Simulates event sent as soon as the event is added.
		// /////////////////////////////////////////////////////////////////////
		/*
		 * OadrDistributeEventType distributeEvent1= Clone.clone(distributeEvent);
		 * distributeEvent1.getOadrEvent().remove(3);
		 * distributeEvent1.getOadrEvent().remove(2);
		 * distributeEvent1.getOadrEvent().remove(1);
		 * 
		 * OadrDistributeEventType distributeEvent2= Clone.clone(distributeEvent);
		 * distributeEvent2.getOadrEvent().remove(3);
		 * distributeEvent2.getOadrEvent().remove(2);
		 * 
		 * OadrDistributeEventType distributeEvent3= Clone.clone(distributeEvent);
		 * distributeEvent3.getOadrEvent().remove(3);
		 * 
		 * 
		 * String strOadrDistributeEvent1 =
		 * SchemaHelper.getDistributeEventAsString(distributeEvent1); String
		 * responseOneEvent1 = VtnToVenClient.post(strOadrDistributeEvent1);
		 * 
		 * String strOadrDistributeEvent2 =
		 * SchemaHelper.getDistributeEventAsString(distributeEvent2); String
		 * responseOneEvent2 = VtnToVenClient.post(strOadrDistributeEvent2);
		 * 
		 * String strOadrDistributeEvent3 =
		 * SchemaHelper.getDistributeEventAsString(distributeEvent3); String
		 * responseOneEvent3 = VtnToVenClient.post(strOadrDistributeEvent3);
		 */
		// /////////////////////////////////////////////////////////////////////

		/*
		JAXBContext context = JAXBContext
				.newInstance(OadrDistributeEventType.class);
		String strOadrDistributeEvent = SchemaHelper.getDistributeEventAsString( 
				distributeEvent);
		*/

		VtnToVenClient.post(strOadrDistributeEvent);

		pause(25);
		
		VTNService.stopVTNService();

	}

	@Override
	public void cleanUp() throws Exception {
	

	}

}
