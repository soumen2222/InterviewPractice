package testcases.pull.event.vtn;

import com.qualitylogic.openadr.core.base.ErrorConst;
import com.qualitylogic.openadr.core.base.VenPullTestCase;
import com.qualitylogic.openadr.core.common.TestSession;
import com.qualitylogic.openadr.core.signal.OadrResponseType;
import com.qualitylogic.openadr.core.signal.OptTypeType;
import com.qualitylogic.openadr.core.util.ConformanceRuleValidator;

public class E1_1080_TH_VEN extends VenPullTestCase {

	public static void main(String[] args) {
		execute(new E1_1080_TH_VEN());
	}

	@Override
	public void test() throws Exception {
		ConformanceRuleValidator.setDisable_VenIDValid_Check(true);
		ConformanceRuleValidator.setDisable_VtnIDValid_Check(true);
		ConformanceRuleValidator.setDisableResponseEvent_ResponseCodeSuccess_Check(true);
		ConformanceRuleValidator.setDisableCreatedEvent_ResponseCodeSuccess_Check(true);
		
		boolean yesClicked = promptYes(resources.prompt_050());
		if (yesClicked) {
			prompt(resources.prompt_065());
			
			String distributeEvent = pollDistributeEventString();
			
			TestSession.setSwapCaseVenID(true);
			OadrResponseType response = sendCreatedEvent(distributeEvent, OptTypeType.OPT_IN);
			
			checkResponse(response, ErrorConst.INVALID_ID_452);
		} else {
			String distributeEventText = pollDistributeEventString();
			sendCreatedEventWithResponseCode(distributeEventText, ErrorConst.INVALID_ID_452, ErrorConst.OK_200);
		}

	}
}