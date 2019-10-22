package testcases.push.opt.vtn;

import com.qualitylogic.openadr.core.base.ErrorConst;
import com.qualitylogic.openadr.core.base.VenPushTestCase;
import com.qualitylogic.openadr.core.signal.OadrCreateOptType;
import com.qualitylogic.openadr.core.util.ConformanceRuleValidator;

public class P0_7050_TH_VEN extends VenPushTestCase {

	public static void main(String[] args) {
		execute(new P0_7050_TH_VEN());
	}

	@Override
	public void test() throws Exception {
		ConformanceRuleValidator.setDisableCancelOpt_isOptIDValid_Check(true);
		ConformanceRuleValidator.setDisableCanceledOpt_ResponseCodeSuccess_Check(true);
		
		OadrCreateOptType createOpt = getCreateOptSchedule_001();
		sendCreateOpt(createOpt);
		
		sendCancelOpt("INVALID_OPT_ID", ErrorConst.INVALID_ID_452);
	}
}
