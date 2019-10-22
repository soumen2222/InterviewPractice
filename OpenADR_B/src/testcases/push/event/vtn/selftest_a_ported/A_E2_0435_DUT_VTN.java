package testcases.push.event.vtn.selftest_a_ported;

import com.qualitylogic.openadr.core.action.DistributeEventActionList;
import com.qualitylogic.openadr.core.action.IDistributeEventAction;
import com.qualitylogic.openadr.core.action.IResponseEventAction;
import com.qualitylogic.openadr.core.action.ResponseEventActionList;
import com.qualitylogic.openadr.core.action.impl.Default_Push_CPP_DistributeEventAction;
import com.qualitylogic.openadr.core.action.impl.Default_ResponseEventAction;
import com.qualitylogic.openadr.core.base.PUSHBaseTestCase;
import com.qualitylogic.openadr.core.signal.EiTargetType;
import com.qualitylogic.openadr.core.signal.OadrDistributeEventType;
import com.qualitylogic.openadr.core.signal.helper.SchemaHelper;
import com.qualitylogic.openadr.core.util.PropertiesFileReader;
import com.qualitylogic.openadr.core.vtn.VTNService;
import com.qualitylogic.openadr.core.vtn.VtnToVenClient;

public class A_E2_0435_DUT_VTN extends PUSHBaseTestCase {

	public static void main(String[] args) {
		try {
			A_E2_0435_DUT_VTN testClass = new A_E2_0435_DUT_VTN();
			testClass.executeTestCase();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void test() throws Exception {

		IDistributeEventAction distributeEventAction1 = new Default_Push_CPP_DistributeEventAction();
		OadrDistributeEventType distributeEvent = distributeEventAction1
				.getDistributeEvent();

		EiTargetType eiTarget = new EiTargetType();
		eiTarget.getVenID().add(new PropertiesFileReader().getVenID());
		distributeEvent.getOadrEvent().get(0).getEiEvent()
				.setEiTarget(eiTarget);

		DistributeEventActionList
				.addDistributeEventAction(distributeEventAction1);

		IResponseEventAction responseEventAction = new Default_ResponseEventAction();
		ResponseEventActionList.addResponseEventAction(responseEventAction);
		responseEventAction.setlastEvent(true);

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
