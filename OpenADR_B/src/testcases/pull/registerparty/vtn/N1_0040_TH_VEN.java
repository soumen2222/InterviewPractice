package testcases.pull.registerparty.vtn;

import com.qualitylogic.openadr.core.base.VenPullTestCase;
import com.qualitylogic.openadr.core.signal.OadrCancelPartyRegistrationType;
import com.qualitylogic.openadr.core.util.ConformanceRuleValidator;

public class N1_0040_TH_VEN extends VenPullTestCase {

	public static void main(String[] args) {
		execute(new N1_0040_TH_VEN());
	}

	@Override
	public void test() throws Exception {
		ConformanceRuleValidator.setDisableRequestIDValid_Check(true);
		
		prompt(resources.prompt_006());

		checkActiveRegistration();
		
		prompt(resources.prompt_007());
		
		OadrCancelPartyRegistrationType cancelPartyRegistration = pollCancelPartyRegistration();
		sendCanceledPartyRegistration(cancelPartyRegistration);

		alert(resources.prompt_005());
	}
}
