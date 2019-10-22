package testcases.push.general.vtn;

import com.qualitylogic.openadr.core.action.ICreatedEventAction;
import com.qualitylogic.openadr.core.action.impl.CreatedEventAction_One;
import com.qualitylogic.openadr.core.base.VenPushTestCase;
import com.qualitylogic.openadr.core.common.TestSession;
import com.qualitylogic.openadr.core.common.UIUserPrompt;
import com.qualitylogic.openadr.core.util.ConformanceRuleValidator;

public class G0_9010_TH_VEN_1 extends VenPushTestCase {

	public static void main(String[] args) {
		execute(new G0_9010_TH_VEN_1());
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
	
		ICreatedEventAction createEvent1 = new CreatedEventAction_One(true,true);
		addCreatedEventResponse(createEvent1);
		
		ICreatedEventAction createEvent2 = new CreatedEventAction_One(true,true);
		addCreatedEventResponse(createEvent2).setLastCreateEvent(true);
		
		listenForRequests();
		
		prompt(resources.prompt_010());

		waitForCreatedEvent(createEvent2);
		
		if (isSha256) {
			TestSession.setClientTLS12Ciphers(new String[] {"TLS_RSA_WITH_AES_128_CBC_SHA256"}); // "TLS_ECDHE_ECDSA_WITH_AES_128_CBC_SHA256"
		} else {
			TestSession.setClientTLS10Ciphers(new String[] {"TLS_RSA_WITH_AES_128_CBC_SHA"}); // "TLS_ECDHE_ECDSA_WITH_AES_128_CBC_SHA"
		}

		prompt(resources.prompt_011());
		
		waitForCompletion();
		
		checkCreatedEventCompleted(createEvent1);
		checkCreatedEventCompleted(createEvent2);
	}
}
