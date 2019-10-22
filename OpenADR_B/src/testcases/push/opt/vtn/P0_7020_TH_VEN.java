package testcases.push.opt.vtn;

import com.qualitylogic.openadr.core.base.VenPushTestCase;
import com.qualitylogic.openadr.core.signal.OadrCreateOptType;

public class P0_7020_TH_VEN extends VenPushTestCase {

	public static void main(String[] args) {
		execute(new P0_7020_TH_VEN());
	}

	@Override
	public void test() throws Exception {
		OadrCreateOptType createOpt = getCreateOptSchedule_001();
		createOpt.getEiTarget().getVenID().clear();
		createOpt.setMarketContext(null);
		sendCreateOpt(createOpt);
		
		sendCancelOpt(createOpt.getOptID());
	}
}
