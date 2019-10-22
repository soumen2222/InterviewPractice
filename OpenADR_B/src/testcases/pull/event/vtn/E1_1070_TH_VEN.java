package testcases.pull.event.vtn;

import com.qualitylogic.openadr.core.base.ErrorConst;
import com.qualitylogic.openadr.core.base.VenPullTestCase;
import com.qualitylogic.openadr.core.exception.FailedException;
import com.qualitylogic.openadr.core.exception.NotApplicableException;
import com.qualitylogic.openadr.core.signal.OadrDistributeEventType;
import com.qualitylogic.openadr.core.signal.helper.DistributeEventSignalHelper;
import com.qualitylogic.openadr.core.util.ConformanceRuleValidator;
import com.qualitylogic.openadr.core.util.OadrUtil;

public class E1_1070_TH_VEN extends VenPullTestCase {

	public static void main(String[] args) {
		execute(new E1_1070_TH_VEN());
	}

	@Override
	public void test() throws Exception {
		ConformanceRuleValidator.setDisableCreatedEvent_ResponseCodeSuccess_Check(true);
		
		boolean yesClicked = promptYes(resources.prompt_036());
		if (yesClicked) {
			throw new NotApplicableException("Placeholder test case for self test, skipping execution for real device.");
		} else {
			String distributeEventText = pollDistributeEventString();
			OadrDistributeEventType distributeEvent = DistributeEventSignalHelper.createOadrDistributeEventFromString(distributeEventText);
			
			String signalName = OadrUtil.getSignalName(distributeEvent);
			if (!"x-CUSTOM_SIGNAL".equals(signalName)) {
				throw new FailedException("x-CUSTOM_SIGNAL expected. Got " + signalName);
			}

			sendCreatedEventWithResponseCode(distributeEventText, ErrorConst.OK_200, ErrorConst.SIGNAL_NOT_SUPPORTED_460);
		}
	}
}
