package testcases.push.event.vtn.selftest_a_ported;

import java.util.List;

import com.qualitylogic.openadr.core.action.DistributeEventActionList;
import com.qualitylogic.openadr.core.action.IDistributeEventAction;
import com.qualitylogic.openadr.core.action.IResponseEventAction;
import com.qualitylogic.openadr.core.action.ResponseEventActionList;
import com.qualitylogic.openadr.core.action.impl.Default_Push_CPP_DistributeEventAction;
import com.qualitylogic.openadr.core.action.impl.Default_ResponseEventAction;
import com.qualitylogic.openadr.core.base.PUSHBaseTestCase;
import com.qualitylogic.openadr.core.bean.CalculatedEventStatusBean;
import com.qualitylogic.openadr.core.signal.CurrentValueType;
import com.qualitylogic.openadr.core.signal.EventStatusEnumeratedType;
import com.qualitylogic.openadr.core.signal.IntervalType;
import com.qualitylogic.openadr.core.signal.OadrDistributeEventType;
import com.qualitylogic.openadr.core.signal.OadrDistributeEventType.OadrEvent;
import com.qualitylogic.openadr.core.signal.PayloadFloatType;
import com.qualitylogic.openadr.core.signal.helper.DistributeEventSignalHelper;
import com.qualitylogic.openadr.core.signal.helper.SchemaHelper;
import com.qualitylogic.openadr.core.signal.xcal.Properties.Tolerance;
import com.qualitylogic.openadr.core.signal.xcal.Properties.Tolerance.Tolerate;
import com.qualitylogic.openadr.core.util.OadrUtil;
import com.qualitylogic.openadr.core.vtn.VTNService;
import com.qualitylogic.openadr.core.vtn.VtnToVenClient;

public class A_E2_0685_DUT_VTN extends PUSHBaseTestCase {

	public static void main(String[] args) {
		try {
			A_E2_0685_DUT_VTN testClass = new A_E2_0685_DUT_VTN();
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

		distributeEvent.getOadrEvent().get(0).getEiEvent().getEiActivePeriod()
		.getProperties().getDuration().setDuration("PT1M");
		
		distributeEvent.getOadrEvent().get(0).getEiEvent().getEiEventSignals().getEiEventSignal().get(0).getIntervals().getInterval().remove(1);
		distributeEvent.getOadrEvent().get(0).getEiEvent().getEiEventSignals().getEiEventSignal().get(0).getIntervals().getInterval().remove(1);

		Tolerance tolerance = new Tolerance();

		Tolerate tolerate = new Tolerate();
		tolerate.setStartafter("PT2M");
		tolerance.setTolerate(tolerate);
		
		tolerance.setTolerate(tolerate);
		
		distributeEvent.getOadrEvent().get(0).getEiEvent()
		.getEiActivePeriod().getProperties().setTolerance(tolerance);
		
		VTNService.startVTNService();
		String strOadrDistributeEvent = SchemaHelper.getDistributeEventAsString( 
				distributeEvent);

		VtnToVenClient.post(strOadrDistributeEvent);
	
		
		pause(180);


			distributeEvent.setRequestID(OadrUtil.createoadrDistributeRequestID());
			OadrEvent event1=distributeEvent.getOadrEvent().get(0);
			
			distributeEvent.getOadrEvent().get(0).getEiEvent().getEventDescriptor().setCreatedDateTime(OadrUtil.getCurrentTime());
			
			CalculatedEventStatusBean eventBeanStatus = DistributeEventSignalHelper
					.calculateEventStatus(event1.getEiEvent());
			
			distributeEvent.getOadrEvent().get(0).getEiEvent().getEventDescriptor().setEventStatus(eventBeanStatus.getEventStatus());
			if(eventBeanStatus.getEventStatus().equals(EventStatusEnumeratedType.ACTIVE)){
			
				PayloadFloatType currentVal=new PayloadFloatType();
				currentVal.setValue((float)1.0);
				CurrentValueType currentValType=new CurrentValueType();
				currentValType.setPayloadFloat(currentVal);
				distributeEvent.getOadrEvent().get(0).getEiEvent().getEiEventSignals().getEiEventSignal().get(0).setCurrentValue(currentValType);
				
			}else{
				PayloadFloatType currentVal=new PayloadFloatType();
				currentVal.setValue((float)0);
				CurrentValueType currentValType=new CurrentValueType();
				currentValType.setPayloadFloat(currentVal);
				distributeEvent.getOadrEvent().get(0).getEiEvent().getEiEventSignals().getEiEventSignal().get(0).setCurrentValue(currentValType);
				
			}
			
			Default_Push_CPP_DistributeEventAction distributeEventAction=new Default_Push_CPP_DistributeEventAction();
			OadrDistributeEventType distributeEvnt = distributeEventAction.getDistributeEvent();
			List<IntervalType> intervals=distributeEvnt.getOadrEvent().get(0).getEiEvent().getEiEventSignals().getEiEventSignal().get(0).getIntervals().getInterval();

			DistributeEventSignalHelper.setNewReqIDEvntIDStartTimeAndMarketCtx1(
					distributeEvnt, 1);
			
			
			intervals.remove(1);
			intervals.remove(1);
			distributeEvnt.getOadrEvent().get(0).getEiEvent().getEiActivePeriod().getProperties().getDuration().setDuration("PT1M");
			
			
			// Adding new event
			distributeEvent.getOadrEvent().add(0,distributeEvnt.getOadrEvent().get(0));

			//Remove completed event begin
			//distributeEvent.getOadrEvent().remove(1);
			//Remove completed event end
			
			String strOadrDistributeEventNext = SchemaHelper.getDistributeEventAsString(distributeEvent);
			// String strOadrDistributeEventNext = SchemaHelper.asString(contextNext, distributeEvent);

			VtnToVenClient.post(strOadrDistributeEventNext);
	
		//}

		
		
		pause(20);
		
	}

	@Override
	public void cleanUp() throws Exception {
		VTNService.stopVTNService();

	}

}
