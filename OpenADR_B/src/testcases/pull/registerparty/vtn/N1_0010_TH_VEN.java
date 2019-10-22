package testcases.pull.registerparty.vtn;

import testcases.push.registerparty.vtn.N0_5010_TH_VEN;

import com.qualitylogic.openadr.core.base.VenPullTestCase;

public class N1_0010_TH_VEN extends VenPullTestCase {

	public static void main(String[] args) {
		execute(new N1_0010_TH_VEN());
	}

	@Override
	public void test() throws Exception {
		new N0_5010_TH_VEN().test();
	}
}
