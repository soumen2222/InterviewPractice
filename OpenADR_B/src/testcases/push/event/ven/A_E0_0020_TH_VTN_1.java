package testcases.push.event.ven;

import java.util.ArrayList;

import javax.xml.bind.JAXBContext;

import com.qualitylogic.openadr.core.action.DistributeEventActionList;
import com.qualitylogic.openadr.core.action.IDistributeEventAction;
import com.qualitylogic.openadr.core.action.IResponseEventAction;
import com.qualitylogic.openadr.core.action.ResponseEventActionList;
import com.qualitylogic.openadr.core.action.impl.Default_Push_Dispatch_DistributeEventAction;
import com.qualitylogic.openadr.core.action.impl.Default_ResponseEventAction;
import com.qualitylogic.openadr.core.base.PUSHBaseTestCase;
import com.qualitylogic.openadr.core.bean.ModeType;
import com.qualitylogic.openadr.core.common.TestSession;
import com.qualitylogic.openadr.core.common.UIUserPrompt;
import com.qualitylogic.openadr.core.signal.OadrCreatedEventType;
import com.qualitylogic.openadr.core.signal.OadrDistributeEventType;
import com.qualitylogic.openadr.core.signal.helper.LogHelper;
import com.qualitylogic.openadr.core.signal.helper.SchemaHelper;
import com.qualitylogic.openadr.core.util.LogResult;
import com.qualitylogic.openadr.core.util.ResourceFileReader;
import com.qualitylogic.openadr.core.util.Trace;
import com.qualitylogic.openadr.core.vtn.VTNService;
import com.qualitylogic.openadr.core.vtn.VtnToVenClient;

@SuppressWarnings("restriction")
public class A_E0_0020_TH_VTN_1 extends PUSHBaseTestCase {

	public static void main(String[] args) {
		try {
			A_E0_0020_TH_VTN_1 testClass = new A_E0_0020_TH_VTN_1();
			testClass.setEnableLogging(true);
			testClass.executeTestCase();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void test() throws Exception {
		TestSession.setMode(ModeType.PUSH);
		
		ResourceFileReader resourceFileReader = new ResourceFileReader();
		UIUserPrompt uiUserPrompt = new UIUserPrompt();
		uiUserPrompt.Prompt(resourceFileReader.Prereq_NoEvents_Message());

		if (!TestSession.isUserClickedContinuePlay()) {
			// User closed the action dialog
			LogHelper.setResult(LogResult.NA);
			LogHelper.addTrace("TestCase execution was cancelled by the user");
		}

		VTNService.startVTNService();

		IDistributeEventAction distributeEventAction1 = new Default_Push_Dispatch_DistributeEventAction();
		distributeEventAction1.getDistributeEvent().getOadrEvent().clear();
		DistributeEventActionList
				.addDistributeEventAction(distributeEventAction1);
		IResponseEventAction responseEventAction = new Default_ResponseEventAction();
		ResponseEventActionList.addResponseEventAction(responseEventAction);
		
		OadrDistributeEventType distributeEvent = distributeEventAction1
				.getDistributeEvent();
		distributeEventAction1.setEventCompleted(true);

		String strOadrDistributeEvent = SchemaHelper.getDistributeEventAsString(distributeEvent);
	
		VtnToVenClient.post(strOadrDistributeEvent);

		pause(40);

		Trace trace = TestSession.getTraceObj();

		if (TestSession.isAtleastOneValidationErrorPresent() && trace != null) {
			LogHelper.setResult(LogResult.FAIL);
			LogHelper.addTrace("Validation Error(s) are present");
			LogHelper.addTrace("Test Case has Failed");
			return;
		}
		ArrayList<OadrCreatedEventType> createdEventReceivedList = TestSession
				.getCreatedEventReceivedList();

		if (createdEventReceivedList.size() == 0) {
			LogHelper.setResult(LogResult.PASS);
			LogHelper.addTrace("Received no OadrCreatedEventType as expected");
			LogHelper.addTrace("Test Case has Passed");
		} else {
			LogHelper.addTrace("No OadrCreatedEventType was expected. Received "
					+ createdEventReceivedList.size() + " OadrCreatedEvent");
			LogHelper.addTrace("Test Case has Failed");
			LogHelper.setResult(LogResult.FAIL);

		}

	}

	@Override
	public void cleanUp() throws Exception {
		VTNService.stopVTNService();
	}
}
