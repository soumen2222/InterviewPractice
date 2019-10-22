package testcases.push.event.vtn.selftest_a_ported;

import javax.xml.datatype.Duration;
import javax.xml.datatype.XMLGregorianCalendar;

import com.qualitylogic.openadr.core.action.DistributeEventActionList;
import com.qualitylogic.openadr.core.action.IDistributeEventAction;
import com.qualitylogic.openadr.core.action.IResponseEventAction;
import com.qualitylogic.openadr.core.action.ResponseEventActionList;
import com.qualitylogic.openadr.core.action.impl.Default_Push_CPP_DistributeEventAction;
import com.qualitylogic.openadr.core.action.impl.Default_ResponseEventAction;
import com.qualitylogic.openadr.core.base.PUSHBaseTestCase;
import com.qualitylogic.openadr.core.signal.EventStatusEnumeratedType;
import com.qualitylogic.openadr.core.signal.OadrDistributeEventType;
import com.qualitylogic.openadr.core.signal.helper.SchemaHelper;
import com.qualitylogic.openadr.core.util.OadrUtil;
import com.qualitylogic.openadr.core.vtn.VTNService;
import com.qualitylogic.openadr.core.vtn.VtnToVenClient;

public class A_E2_0484_DUT_VTN extends PUSHBaseTestCase {

	public static void main(String[] args) {
		try {
			A_E2_0484_DUT_VTN testClass = new A_E2_0484_DUT_VTN();
			testClass.executeTestCase();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void test() throws Exception {

		IDistributeEventAction distributeEventAction1 = new Default_Push_CPP_DistributeEventAction();
		DistributeEventActionList
				.addDistributeEventAction(distributeEventAction1);

		IResponseEventAction responseEventAction = new Default_ResponseEventAction();
		ResponseEventActionList.addResponseEventAction(responseEventAction);
		OadrDistributeEventType distributeEvent = distributeEventAction1
				.getDistributeEvent();

		XMLGregorianCalendar startDt= distributeEvent.getOadrEvent().get(0).getEiEvent().getEiActivePeriod().getProperties().getDtstart().getDateTime();
		Duration duration = OadrUtil.createDuration("-PT3M");
		startDt.add(duration);
		
		distributeEvent.getOadrEvent().get(0).getEiEvent().getEventDescriptor()
		.setEventStatus(EventStatusEnumeratedType.ACTIVE);
		distributeEvent.getOadrEvent().get(0).getEiEvent().getEiEventSignals().getEiEventSignal().get(0).getCurrentValue().getPayloadFloat().setValue((float)1.0);
		
		
		VTNService.startVTNService();

		String strOadrDistributeEvent = SchemaHelper.getDistributeEventAsString( 
				distributeEvent);

		VtnToVenClient.post(strOadrDistributeEvent);

		pause(20);
		//pauseForTestCaseTimeout();
		
		VTNService.stopVTNService();

	}

	@Override
	public void cleanUp() throws Exception {

	}

}
