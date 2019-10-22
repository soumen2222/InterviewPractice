package testcases.push.opt.vtn;

import com.qualitylogic.openadr.core.base.VenPushTestCase;
import com.qualitylogic.openadr.core.signal.OadrCreateOptType;

public class P0_7040_TH_VEN extends VenPushTestCase {

	public static void main(String[] args) {
		execute(new P0_7040_TH_VEN());
	}

	@Override
	public void test() throws Exception {
		OadrCreateOptType schedule001 = getCreateOptSchedule_001();
		String optID = schedule001.getOptID();
		sendCreateOpt(schedule001);
		
		OadrCreateOptType schedule003 = getCreateOptSchedule_003();
		schedule003.setOptID(optID);
		sendCreateOpt(schedule003);
		
		sendCancelOpt(optID);
	}
}
