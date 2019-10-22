package testcases.push.opt.vtn;

import com.qualitylogic.openadr.core.base.VenPushTestCase;

public class P0_7015_TH_VEN extends VenPushTestCase {

	public static void main(String[] args) {
		execute(new P0_7015_TH_VEN());
	}

	@Override
	public void test() throws Exception {
		sendCreateOpt(getCreateOptSchedule_004());
	}
}
