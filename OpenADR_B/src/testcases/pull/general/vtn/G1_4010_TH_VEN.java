package testcases.pull.general.vtn;

import com.qualitylogic.openadr.core.base.VenPullTestCase;
import com.qualitylogic.openadr.core.common.TestSession;
import com.qualitylogic.openadr.core.signal.OptTypeType;
import com.qualitylogic.openadr.core.util.ConformanceRuleValidator;

public class G1_4010_TH_VEN extends VenPullTestCase {

	public static void main(String[] args) {
		execute(new G1_4010_TH_VEN());
	}

	@Override
	public void test() throws Exception {
		// ConformanceRuleValidator.setDisableDistributeEvent_PayloadFloatLimitValid_Check(true);

		checkSecurity();
		
		TestSession.setSecurityDebug("true");

		String securityMode = properties.getSecurityMode();
		prompt(String.format(resources.prompt_042(), securityMode));
		
		boolean isSha256 = securityMode.endsWith("SHA256_Security");
		if (isSha256) {
			TestSession.setClientTLS12Ciphers(new String[] {"TLS_RSA_WITH_AES_128_CBC_SHA256"});
		} else {
			TestSession.setClientTLS10Ciphers(new String[] {"TLS_RSA_WITH_AES_128_CBC_SHA"});
		}
		
		prompt(resources.prompt_010());
		
		String distributeEvent1 = pollDistributeEventString();
		sendCreatedEvent(distributeEvent1, OptTypeType.OPT_IN);
		
		prompt(resources.prompt_011());

		if (isSha256) {
			TestSession.setClientTLS12Ciphers(new String[] {"TLS_RSA_WITH_AES_128_CBC_SHA256"}); // "TLS_ECDHE_ECDSA_WITH_AES_128_CBC_SHA256"
		} else {
			TestSession.setClientTLS10Ciphers(new String[] {"TLS_RSA_WITH_AES_128_CBC_SHA"}); // "TLS_ECDHE_ECDSA_WITH_AES_128_CBC_SHA"
		}
		
		String distributeEvent2 = pollDistributeEventString();
		sendCreatedEvent(distributeEvent2, OptTypeType.OPT_IN);
	}
}
