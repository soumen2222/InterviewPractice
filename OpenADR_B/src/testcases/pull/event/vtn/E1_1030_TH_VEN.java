package testcases.pull.event.vtn;

import com.qualitylogic.openadr.core.base.VenPullTestCase;
import com.qualitylogic.openadr.core.signal.OadrDistributeEventType;
import com.qualitylogic.openadr.core.signal.OptTypeType;
import com.qualitylogic.openadr.core.signal.helper.DistributeEventSignalHelper;
import com.qualitylogic.openadr.core.util.ConformanceRuleValidator;

public class E1_1030_TH_VEN extends VenPullTestCase {

	public static void main(String[] args) {
		execute(new E1_1030_TH_VEN());
	}

	@Override
	public void test() throws Exception {
				
		prompt(resources.prompt_010());
		
		String distributeEventText1 = pollDistributeEventString();
		sendCreatedEvent(distributeEventText1, OptTypeType.OPT_IN);

		OadrDistributeEventType distributeEvent1 = DistributeEventSignalHelper.createOadrDistributeEventFromString(distributeEventText1);
		checkSignalNames(distributeEvent1, "ELECTRICITY_PRICE");
		
		prompt(resources.prompt_011());
		
		String distributeEventText2 = pollDistributeEventString();
		sendCreatedEvent(distributeEventText2, OptTypeType.OPT_IN);

		OadrDistributeEventType distributeEvent2 = DistributeEventSignalHelper.createOadrDistributeEventFromString(distributeEventText2);
		checkSignalNames(distributeEvent2, "ELECTRICITY_PRICE");
	}
}
