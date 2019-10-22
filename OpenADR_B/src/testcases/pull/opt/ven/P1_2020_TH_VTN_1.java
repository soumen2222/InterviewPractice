package testcases.pull.opt.ven;

import testcases.push.opt.ven.P0_7020_TH_VTN_1;

import com.qualitylogic.openadr.core.base.VtnPullTestCase;

public class P1_2020_TH_VTN_1 extends VtnPullTestCase {

	public static void main(String[] args) {
		execute(new P1_2020_TH_VTN_1());
	}

	@Override
	public void test() throws Exception {
		new P0_7020_TH_VTN_1().test();
	}
}
