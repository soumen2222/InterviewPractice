package testcases.pull.opt.vtn;

import testcases.push.opt.vtn.P0_7030_TH_VEN;

import com.qualitylogic.openadr.core.base.VenPullTestCase;

public class P1_2030_TH_VEN extends VenPullTestCase {

	public static void main(String[] args) {
		execute(new P1_2030_TH_VEN());
	}

	@Override
	public void test() throws Exception {
		new P0_7030_TH_VEN().test();
	}
}
