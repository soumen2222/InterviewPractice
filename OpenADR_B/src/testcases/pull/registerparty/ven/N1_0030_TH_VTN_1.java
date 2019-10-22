package testcases.pull.registerparty.ven;

import testcases.push.registerparty.ven.N0_5030_TH_VTN_1;

import com.qualitylogic.openadr.core.base.VtnPullTestCase;

public class N1_0030_TH_VTN_1 extends VtnPullTestCase {

	public static void main(String[] args) {
		execute(new N1_0030_TH_VTN_1());
	}

	@Override
	public void test() throws Exception {
		new N0_5030_TH_VTN_1().test();
	}
}
