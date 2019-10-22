package testcases.push.event.vtn;

import com.qualitylogic.openadr.core.base.ErrorConst;
import com.qualitylogic.openadr.core.base.VenPushTestCase;
import com.qualitylogic.openadr.core.exception.FailedException;
import com.qualitylogic.openadr.core.exception.NotApplicableException;
import com.qualitylogic.openadr.core.signal.OadrDistributeEventType;
import com.qualitylogic.openadr.core.signal.helper.SchemaHelper;
import com.qualitylogic.openadr.core.util.ConformanceRuleValidator;
import com.qualitylogic.openadr.core.util.OadrUtil;

public class E0_6070_TH_VEN_1 extends VenPushTestCase {

	public static void main(String[] args) {
		execute(new E0_6070_TH_VEN_1());
	}

	@Override
	public void test() throws Exception {
		ConformanceRuleValidator.setDisableCreatedEvent_ResponseCodeSuccess_Check(true);
		
		boolean yesClicked = promptYes(resources.prompt_036());
		if (yesClicked) {
			throw new NotApplicableException("Placeholder test case for self test, skipping execution for real device.");
		} else {
			listenForRequests();

			OadrDistributeEventType distributeEvent = waitForDistributeEvent(1);

			String signalName = OadrUtil.getSignalName(distributeEvent);
			if (!"x-CUSTOM_SIGNAL".equals(signalName)) {
				throw new FailedException("x-CUSTOM_SIGNAL expected. Got " + signalName);
			}

			String distributeEventText = SchemaHelper.getDistributeEventAsString(distributeEvent);
			sendCreatedEventWithResponseCode(distributeEventText, ErrorConst.OK_200, ErrorConst.SIGNAL_NOT_SUPPORTED_460);
		}
	}
}
